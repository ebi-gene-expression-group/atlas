var expressionAtlasDifferentialExpression =
webpackJsonp_name_([5],{

/***/ 0:
/*!***************************************************!*\
  !*** multi expressionAtlasDifferentialExpression ***!
  \***************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! babel-polyfill */978);
	module.exports = __webpack_require__(/*! ./atlas_bundles/differential-expression */2770);


/***/ },

/***/ 978:
/*!***************************************!*\
  !*** ./~/babel-polyfill/lib/index.js ***!
  \***************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global) {"use strict";
	
	__webpack_require__(/*! core-js/shim */ 979);
	
	__webpack_require__(/*! regenerator-runtime/runtime */ 1270);
	
	__webpack_require__(/*! core-js/fn/regexp/escape */ 1271);
	
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

/***/ 979:
/*!********************************************!*\
  !*** ./~/babel-polyfill/~/core-js/shim.js ***!
  \********************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./modules/es6.symbol */ 980);
	__webpack_require__(/*! ./modules/es6.object.create */ 1029);
	__webpack_require__(/*! ./modules/es6.object.define-property */ 1030);
	__webpack_require__(/*! ./modules/es6.object.define-properties */ 1031);
	__webpack_require__(/*! ./modules/es6.object.get-own-property-descriptor */ 1032);
	__webpack_require__(/*! ./modules/es6.object.get-prototype-of */ 1034);
	__webpack_require__(/*! ./modules/es6.object.keys */ 1037);
	__webpack_require__(/*! ./modules/es6.object.get-own-property-names */ 1038);
	__webpack_require__(/*! ./modules/es6.object.freeze */ 1039);
	__webpack_require__(/*! ./modules/es6.object.seal */ 1040);
	__webpack_require__(/*! ./modules/es6.object.prevent-extensions */ 1041);
	__webpack_require__(/*! ./modules/es6.object.is-frozen */ 1042);
	__webpack_require__(/*! ./modules/es6.object.is-sealed */ 1043);
	__webpack_require__(/*! ./modules/es6.object.is-extensible */ 1044);
	__webpack_require__(/*! ./modules/es6.object.assign */ 1045);
	__webpack_require__(/*! ./modules/es6.object.is */ 1047);
	__webpack_require__(/*! ./modules/es6.object.set-prototype-of */ 1049);
	__webpack_require__(/*! ./modules/es6.object.to-string */ 1051);
	__webpack_require__(/*! ./modules/es6.function.bind */ 1053);
	__webpack_require__(/*! ./modules/es6.function.name */ 1056);
	__webpack_require__(/*! ./modules/es6.function.has-instance */ 1057);
	__webpack_require__(/*! ./modules/es6.parse-int */ 1058);
	__webpack_require__(/*! ./modules/es6.parse-float */ 1062);
	__webpack_require__(/*! ./modules/es6.number.constructor */ 1064);
	__webpack_require__(/*! ./modules/es6.number.to-fixed */ 1066);
	__webpack_require__(/*! ./modules/es6.number.to-precision */ 1069);
	__webpack_require__(/*! ./modules/es6.number.epsilon */ 1070);
	__webpack_require__(/*! ./modules/es6.number.is-finite */ 1071);
	__webpack_require__(/*! ./modules/es6.number.is-integer */ 1072);
	__webpack_require__(/*! ./modules/es6.number.is-nan */ 1074);
	__webpack_require__(/*! ./modules/es6.number.is-safe-integer */ 1075);
	__webpack_require__(/*! ./modules/es6.number.max-safe-integer */ 1076);
	__webpack_require__(/*! ./modules/es6.number.min-safe-integer */ 1077);
	__webpack_require__(/*! ./modules/es6.number.parse-float */ 1078);
	__webpack_require__(/*! ./modules/es6.number.parse-int */ 1079);
	__webpack_require__(/*! ./modules/es6.math.acosh */ 1080);
	__webpack_require__(/*! ./modules/es6.math.asinh */ 1082);
	__webpack_require__(/*! ./modules/es6.math.atanh */ 1083);
	__webpack_require__(/*! ./modules/es6.math.cbrt */ 1084);
	__webpack_require__(/*! ./modules/es6.math.clz32 */ 1086);
	__webpack_require__(/*! ./modules/es6.math.cosh */ 1087);
	__webpack_require__(/*! ./modules/es6.math.expm1 */ 1088);
	__webpack_require__(/*! ./modules/es6.math.fround */ 1090);
	__webpack_require__(/*! ./modules/es6.math.hypot */ 1091);
	__webpack_require__(/*! ./modules/es6.math.imul */ 1092);
	__webpack_require__(/*! ./modules/es6.math.log10 */ 1093);
	__webpack_require__(/*! ./modules/es6.math.log1p */ 1094);
	__webpack_require__(/*! ./modules/es6.math.log2 */ 1095);
	__webpack_require__(/*! ./modules/es6.math.sign */ 1096);
	__webpack_require__(/*! ./modules/es6.math.sinh */ 1097);
	__webpack_require__(/*! ./modules/es6.math.tanh */ 1098);
	__webpack_require__(/*! ./modules/es6.math.trunc */ 1099);
	__webpack_require__(/*! ./modules/es6.string.from-code-point */ 1100);
	__webpack_require__(/*! ./modules/es6.string.raw */ 1101);
	__webpack_require__(/*! ./modules/es6.string.trim */ 1102);
	__webpack_require__(/*! ./modules/es6.string.iterator */ 1103);
	__webpack_require__(/*! ./modules/es6.string.code-point-at */ 1108);
	__webpack_require__(/*! ./modules/es6.string.ends-with */ 1109);
	__webpack_require__(/*! ./modules/es6.string.includes */ 1113);
	__webpack_require__(/*! ./modules/es6.string.repeat */ 1114);
	__webpack_require__(/*! ./modules/es6.string.starts-with */ 1115);
	__webpack_require__(/*! ./modules/es6.string.anchor */ 1116);
	__webpack_require__(/*! ./modules/es6.string.big */ 1118);
	__webpack_require__(/*! ./modules/es6.string.blink */ 1119);
	__webpack_require__(/*! ./modules/es6.string.bold */ 1120);
	__webpack_require__(/*! ./modules/es6.string.fixed */ 1121);
	__webpack_require__(/*! ./modules/es6.string.fontcolor */ 1122);
	__webpack_require__(/*! ./modules/es6.string.fontsize */ 1123);
	__webpack_require__(/*! ./modules/es6.string.italics */ 1124);
	__webpack_require__(/*! ./modules/es6.string.link */ 1125);
	__webpack_require__(/*! ./modules/es6.string.small */ 1126);
	__webpack_require__(/*! ./modules/es6.string.strike */ 1127);
	__webpack_require__(/*! ./modules/es6.string.sub */ 1128);
	__webpack_require__(/*! ./modules/es6.string.sup */ 1129);
	__webpack_require__(/*! ./modules/es6.date.now */ 1130);
	__webpack_require__(/*! ./modules/es6.date.to-json */ 1131);
	__webpack_require__(/*! ./modules/es6.date.to-iso-string */ 1132);
	__webpack_require__(/*! ./modules/es6.date.to-string */ 1133);
	__webpack_require__(/*! ./modules/es6.date.to-primitive */ 1134);
	__webpack_require__(/*! ./modules/es6.array.is-array */ 1136);
	__webpack_require__(/*! ./modules/es6.array.from */ 1137);
	__webpack_require__(/*! ./modules/es6.array.of */ 1143);
	__webpack_require__(/*! ./modules/es6.array.join */ 1144);
	__webpack_require__(/*! ./modules/es6.array.slice */ 1146);
	__webpack_require__(/*! ./modules/es6.array.sort */ 1147);
	__webpack_require__(/*! ./modules/es6.array.for-each */ 1148);
	__webpack_require__(/*! ./modules/es6.array.map */ 1152);
	__webpack_require__(/*! ./modules/es6.array.filter */ 1153);
	__webpack_require__(/*! ./modules/es6.array.some */ 1154);
	__webpack_require__(/*! ./modules/es6.array.every */ 1155);
	__webpack_require__(/*! ./modules/es6.array.reduce */ 1156);
	__webpack_require__(/*! ./modules/es6.array.reduce-right */ 1158);
	__webpack_require__(/*! ./modules/es6.array.index-of */ 1159);
	__webpack_require__(/*! ./modules/es6.array.last-index-of */ 1160);
	__webpack_require__(/*! ./modules/es6.array.copy-within */ 1161);
	__webpack_require__(/*! ./modules/es6.array.fill */ 1164);
	__webpack_require__(/*! ./modules/es6.array.find */ 1166);
	__webpack_require__(/*! ./modules/es6.array.find-index */ 1167);
	__webpack_require__(/*! ./modules/es6.array.species */ 1168);
	__webpack_require__(/*! ./modules/es6.array.iterator */ 1170);
	__webpack_require__(/*! ./modules/es6.regexp.constructor */ 1172);
	__webpack_require__(/*! ./modules/es6.regexp.to-string */ 1174);
	__webpack_require__(/*! ./modules/es6.regexp.flags */ 1175);
	__webpack_require__(/*! ./modules/es6.regexp.match */ 1176);
	__webpack_require__(/*! ./modules/es6.regexp.replace */ 1178);
	__webpack_require__(/*! ./modules/es6.regexp.search */ 1179);
	__webpack_require__(/*! ./modules/es6.regexp.split */ 1180);
	__webpack_require__(/*! ./modules/es6.promise */ 1181);
	__webpack_require__(/*! ./modules/es6.map */ 1188);
	__webpack_require__(/*! ./modules/es6.set */ 1191);
	__webpack_require__(/*! ./modules/es6.weak-map */ 1192);
	__webpack_require__(/*! ./modules/es6.weak-set */ 1194);
	__webpack_require__(/*! ./modules/es6.typed.array-buffer */ 1195);
	__webpack_require__(/*! ./modules/es6.typed.data-view */ 1198);
	__webpack_require__(/*! ./modules/es6.typed.int8-array */ 1199);
	__webpack_require__(/*! ./modules/es6.typed.uint8-array */ 1201);
	__webpack_require__(/*! ./modules/es6.typed.uint8-clamped-array */ 1202);
	__webpack_require__(/*! ./modules/es6.typed.int16-array */ 1203);
	__webpack_require__(/*! ./modules/es6.typed.uint16-array */ 1204);
	__webpack_require__(/*! ./modules/es6.typed.int32-array */ 1205);
	__webpack_require__(/*! ./modules/es6.typed.uint32-array */ 1206);
	__webpack_require__(/*! ./modules/es6.typed.float32-array */ 1207);
	__webpack_require__(/*! ./modules/es6.typed.float64-array */ 1208);
	__webpack_require__(/*! ./modules/es6.reflect.apply */ 1209);
	__webpack_require__(/*! ./modules/es6.reflect.construct */ 1210);
	__webpack_require__(/*! ./modules/es6.reflect.define-property */ 1211);
	__webpack_require__(/*! ./modules/es6.reflect.delete-property */ 1212);
	__webpack_require__(/*! ./modules/es6.reflect.enumerate */ 1213);
	__webpack_require__(/*! ./modules/es6.reflect.get */ 1214);
	__webpack_require__(/*! ./modules/es6.reflect.get-own-property-descriptor */ 1215);
	__webpack_require__(/*! ./modules/es6.reflect.get-prototype-of */ 1216);
	__webpack_require__(/*! ./modules/es6.reflect.has */ 1217);
	__webpack_require__(/*! ./modules/es6.reflect.is-extensible */ 1218);
	__webpack_require__(/*! ./modules/es6.reflect.own-keys */ 1219);
	__webpack_require__(/*! ./modules/es6.reflect.prevent-extensions */ 1221);
	__webpack_require__(/*! ./modules/es6.reflect.set */ 1222);
	__webpack_require__(/*! ./modules/es6.reflect.set-prototype-of */ 1223);
	__webpack_require__(/*! ./modules/es7.array.includes */ 1224);
	__webpack_require__(/*! ./modules/es7.string.at */ 1225);
	__webpack_require__(/*! ./modules/es7.string.pad-start */ 1226);
	__webpack_require__(/*! ./modules/es7.string.pad-end */ 1228);
	__webpack_require__(/*! ./modules/es7.string.trim-left */ 1229);
	__webpack_require__(/*! ./modules/es7.string.trim-right */ 1230);
	__webpack_require__(/*! ./modules/es7.string.match-all */ 1231);
	__webpack_require__(/*! ./modules/es7.symbol.async-iterator */ 1232);
	__webpack_require__(/*! ./modules/es7.symbol.observable */ 1233);
	__webpack_require__(/*! ./modules/es7.object.get-own-property-descriptors */ 1234);
	__webpack_require__(/*! ./modules/es7.object.values */ 1235);
	__webpack_require__(/*! ./modules/es7.object.entries */ 1237);
	__webpack_require__(/*! ./modules/es7.object.define-getter */ 1238);
	__webpack_require__(/*! ./modules/es7.object.define-setter */ 1240);
	__webpack_require__(/*! ./modules/es7.object.lookup-getter */ 1241);
	__webpack_require__(/*! ./modules/es7.object.lookup-setter */ 1242);
	__webpack_require__(/*! ./modules/es7.map.to-json */ 1243);
	__webpack_require__(/*! ./modules/es7.set.to-json */ 1246);
	__webpack_require__(/*! ./modules/es7.system.global */ 1247);
	__webpack_require__(/*! ./modules/es7.error.is-error */ 1248);
	__webpack_require__(/*! ./modules/es7.math.iaddh */ 1249);
	__webpack_require__(/*! ./modules/es7.math.isubh */ 1250);
	__webpack_require__(/*! ./modules/es7.math.imulh */ 1251);
	__webpack_require__(/*! ./modules/es7.math.umulh */ 1252);
	__webpack_require__(/*! ./modules/es7.reflect.define-metadata */ 1253);
	__webpack_require__(/*! ./modules/es7.reflect.delete-metadata */ 1255);
	__webpack_require__(/*! ./modules/es7.reflect.get-metadata */ 1256);
	__webpack_require__(/*! ./modules/es7.reflect.get-metadata-keys */ 1257);
	__webpack_require__(/*! ./modules/es7.reflect.get-own-metadata */ 1258);
	__webpack_require__(/*! ./modules/es7.reflect.get-own-metadata-keys */ 1259);
	__webpack_require__(/*! ./modules/es7.reflect.has-metadata */ 1260);
	__webpack_require__(/*! ./modules/es7.reflect.has-own-metadata */ 1261);
	__webpack_require__(/*! ./modules/es7.reflect.metadata */ 1262);
	__webpack_require__(/*! ./modules/es7.asap */ 1263);
	__webpack_require__(/*! ./modules/es7.observable */ 1264);
	__webpack_require__(/*! ./modules/web.timers */ 1265);
	__webpack_require__(/*! ./modules/web.immediate */ 1268);
	__webpack_require__(/*! ./modules/web.dom.iterable */ 1269);
	module.exports = __webpack_require__(/*! ./modules/_core */ 986);

/***/ },

/***/ 980:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.symbol.js ***!
  \**********************************************************/
[3789, 981, 982, 983, 985, 995, 999, 984, 1000, 1001, 996, 1002, 1003, 1004, 1006, 1019, 1022, 989, 1009, 993, 994, 1023, 1026, 1028, 988, 1007, 1027, 1021, 1020, 1005, 987],

/***/ 981:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_global.js ***!
  \*******************************************************/
486,

/***/ 982:
/*!****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_has.js ***!
  \****************************************************/
503,

/***/ 983:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_descriptors.js ***!
  \************************************************************/
[3755, 984],

/***/ 984:
/*!******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_fails.js ***!
  \******************************************************/
496,

/***/ 985:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_export.js ***!
  \*******************************************************/
/***/ function(module, exports, __webpack_require__) {

	var global    = __webpack_require__(/*! ./_global */ 981)
	  , core      = __webpack_require__(/*! ./_core */ 986)
	  , hide      = __webpack_require__(/*! ./_hide */ 987)
	  , redefine  = __webpack_require__(/*! ./_redefine */ 995)
	  , ctx       = __webpack_require__(/*! ./_ctx */ 997)
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

/***/ 986:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_core.js ***!
  \*****************************************************/
487,

/***/ 987:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_hide.js ***!
  \*****************************************************/
[3751, 988, 994, 983],

/***/ 988:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-dp.js ***!
  \**********************************************************/
[3752, 989, 991, 993, 983],

/***/ 989:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_an-object.js ***!
  \**********************************************************/
[3753, 990],

/***/ 990:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_is-object.js ***!
  \**********************************************************/
493,

/***/ 991:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_ie8-dom-define.js ***!
  \***************************************************************/
[3754, 983, 984, 992],

/***/ 992:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_dom-create.js ***!
  \***********************************************************/
[3756, 990, 981],

/***/ 993:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-primitive.js ***!
  \*************************************************************/
[3757, 990],

/***/ 994:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_property-desc.js ***!
  \**************************************************************/
499,

/***/ 995:
/*!*********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_redefine.js ***!
  \*********************************************************/
/***/ function(module, exports, __webpack_require__) {

	var global    = __webpack_require__(/*! ./_global */ 981)
	  , hide      = __webpack_require__(/*! ./_hide */ 987)
	  , has       = __webpack_require__(/*! ./_has */ 982)
	  , SRC       = __webpack_require__(/*! ./_uid */ 996)('src')
	  , TO_STRING = 'toString'
	  , $toString = Function[TO_STRING]
	  , TPL       = ('' + $toString).split(TO_STRING);
	
	__webpack_require__(/*! ./_core */ 986).inspectSource = function(it){
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

/***/ 996:
/*!****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_uid.js ***!
  \****************************************************/
514,

/***/ 997:
/*!****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_ctx.js ***!
  \****************************************************/
[3750, 998],

/***/ 998:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_a-function.js ***!
  \***********************************************************/
489,

/***/ 999:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_meta.js ***!
  \*****************************************************/
[3790, 996, 990, 982, 988, 984],

/***/ 1000:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_shared.js ***!
  \*******************************************************/
[3767, 981],

/***/ 1001:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_set-to-string-tag.js ***!
  \******************************************************************/
[3781, 988, 982, 1002],

/***/ 1002:
/*!****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_wks.js ***!
  \****************************************************/
[3782, 1000, 996, 981],

/***/ 1003:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_wks-ext.js ***!
  \********************************************************/
[3786, 1002],

/***/ 1004:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_wks-define.js ***!
  \***********************************************************/
[3791, 981, 986, 1005, 1003, 988],

/***/ 1005:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_library.js ***!
  \********************************************************/
/***/ function(module, exports) {

	module.exports = false;

/***/ },

/***/ 1006:
/*!******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_keyof.js ***!
  \******************************************************/
[3792, 1007, 1009],

/***/ 1007:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-keys.js ***!
  \************************************************************/
[3759, 1008, 1018],

/***/ 1008:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-keys-internal.js ***!
  \*********************************************************************/
[3760, 982, 1009, 1013, 1017],

/***/ 1009:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-iobject.js ***!
  \***********************************************************/
[3761, 1010, 1012],

/***/ 1010:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iobject.js ***!
  \********************************************************/
[3762, 1011],

/***/ 1011:
/*!****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_cof.js ***!
  \****************************************************/
506,

/***/ 1012:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_defined.js ***!
  \********************************************************/
507,

/***/ 1013:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-includes.js ***!
  \***************************************************************/
[3763, 1009, 1014, 1016],

/***/ 1014:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-length.js ***!
  \**********************************************************/
[3764, 1015],

/***/ 1015:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-integer.js ***!
  \***********************************************************/
510,

/***/ 1016:
/*!*********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-index.js ***!
  \*********************************************************/
[3765, 1015],

/***/ 1017:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_shared-key.js ***!
  \***********************************************************/
[3766, 1000, 996],

/***/ 1018:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_enum-bug-keys.js ***!
  \**************************************************************/
515,

/***/ 1019:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_enum-keys.js ***!
  \**********************************************************/
[3793, 1007, 1020, 1021],

/***/ 1020:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-gops.js ***!
  \************************************************************/
516,

/***/ 1021:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-pie.js ***!
  \***********************************************************/
517,

/***/ 1022:
/*!*********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_is-array.js ***!
  \*********************************************************/
[3794, 1011],

/***/ 1023:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-create.js ***!
  \**************************************************************/
[3778, 989, 1024, 1018, 1017, 992, 1025],

/***/ 1024:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-dps.js ***!
  \***********************************************************/
[3779, 988, 989, 1007, 983],

/***/ 1025:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_html.js ***!
  \*****************************************************/
[3780, 981],

/***/ 1026:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-gopn-ext.js ***!
  \****************************************************************/
[3795, 1009, 1027],

/***/ 1027:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-gopn.js ***!
  \************************************************************/
[3796, 1008, 1018],

/***/ 1028:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-gopd.js ***!
  \************************************************************/
[3797, 1021, 994, 1009, 993, 982, 991, 983],

/***/ 1029:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.create.js ***!
  \*****************************************************************/
[3807, 985, 1023],

/***/ 1030:
/*!**************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.define-property.js ***!
  \**************************************************************************/
[3913, 985, 983, 988],

/***/ 1031:
/*!****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.define-properties.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 985);
	// 19.1.2.3 / 15.2.3.7 Object.defineProperties(O, Properties)
	$export($export.S + $export.F * !__webpack_require__(/*! ./_descriptors */ 983), 'Object', {defineProperties: __webpack_require__(/*! ./_object-dps */ 1024)});

/***/ },

/***/ 1032:
/*!**************************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.get-own-property-descriptor.js ***!
  \**************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.6 Object.getOwnPropertyDescriptor(O, P)
	var toIObject                 = __webpack_require__(/*! ./_to-iobject */ 1009)
	  , $getOwnPropertyDescriptor = __webpack_require__(/*! ./_object-gopd */ 1028).f;
	
	__webpack_require__(/*! ./_object-sap */ 1033)('getOwnPropertyDescriptor', function(){
	  return function getOwnPropertyDescriptor(it, key){
	    return $getOwnPropertyDescriptor(toIObject(it), key);
	  };
	});

/***/ },

/***/ 1033:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-sap.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// most Object methods by ES6 should accept primitives
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , core    = __webpack_require__(/*! ./_core */ 986)
	  , fails   = __webpack_require__(/*! ./_fails */ 984);
	module.exports = function(KEY, exec){
	  var fn  = (core.Object || {})[KEY] || Object[KEY]
	    , exp = {};
	  exp[KEY] = exec(fn);
	  $export($export.S + $export.F * fails(function(){ fn(1); }), 'Object', exp);
	};

/***/ },

/***/ 1034:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.get-prototype-of.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.9 Object.getPrototypeOf(O)
	var toObject        = __webpack_require__(/*! ./_to-object */ 1035)
	  , $getPrototypeOf = __webpack_require__(/*! ./_object-gpo */ 1036);
	
	__webpack_require__(/*! ./_object-sap */ 1033)('getPrototypeOf', function(){
	  return function getPrototypeOf(it){
	    return $getPrototypeOf(toObject(it));
	  };
	});

/***/ },

/***/ 1035:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_to-object.js ***!
  \**********************************************************/
[3768, 1012],

/***/ 1036:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-gpo.js ***!
  \***********************************************************/
[3783, 982, 1035, 1017],

/***/ 1037:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.keys.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.14 Object.keys(O)
	var toObject = __webpack_require__(/*! ./_to-object */ 1035)
	  , $keys    = __webpack_require__(/*! ./_object-keys */ 1007);
	
	__webpack_require__(/*! ./_object-sap */ 1033)('keys', function(){
	  return function keys(it){
	    return $keys(toObject(it));
	  };
	});

/***/ },

/***/ 1038:
/*!*********************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.get-own-property-names.js ***!
  \*********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.7 Object.getOwnPropertyNames(O)
	__webpack_require__(/*! ./_object-sap */ 1033)('getOwnPropertyNames', function(){
	  return __webpack_require__(/*! ./_object-gopn-ext */ 1026).f;
	});

/***/ },

/***/ 1039:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.freeze.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.5 Object.freeze(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 990)
	  , meta     = __webpack_require__(/*! ./_meta */ 999).onFreeze;
	
	__webpack_require__(/*! ./_object-sap */ 1033)('freeze', function($freeze){
	  return function freeze(it){
	    return $freeze && isObject(it) ? $freeze(meta(it)) : it;
	  };
	});

/***/ },

/***/ 1040:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.seal.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.17 Object.seal(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 990)
	  , meta     = __webpack_require__(/*! ./_meta */ 999).onFreeze;
	
	__webpack_require__(/*! ./_object-sap */ 1033)('seal', function($seal){
	  return function seal(it){
	    return $seal && isObject(it) ? $seal(meta(it)) : it;
	  };
	});

/***/ },

/***/ 1041:
/*!*****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.prevent-extensions.js ***!
  \*****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.15 Object.preventExtensions(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 990)
	  , meta     = __webpack_require__(/*! ./_meta */ 999).onFreeze;
	
	__webpack_require__(/*! ./_object-sap */ 1033)('preventExtensions', function($preventExtensions){
	  return function preventExtensions(it){
	    return $preventExtensions && isObject(it) ? $preventExtensions(meta(it)) : it;
	  };
	});

/***/ },

/***/ 1042:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.is-frozen.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.12 Object.isFrozen(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 990);
	
	__webpack_require__(/*! ./_object-sap */ 1033)('isFrozen', function($isFrozen){
	  return function isFrozen(it){
	    return isObject(it) ? $isFrozen ? $isFrozen(it) : false : true;
	  };
	});

/***/ },

/***/ 1043:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.is-sealed.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.13 Object.isSealed(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 990);
	
	__webpack_require__(/*! ./_object-sap */ 1033)('isSealed', function($isSealed){
	  return function isSealed(it){
	    return isObject(it) ? $isSealed ? $isSealed(it) : false : true;
	  };
	});

/***/ },

/***/ 1044:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.is-extensible.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.11 Object.isExtensible(O)
	var isObject = __webpack_require__(/*! ./_is-object */ 990);
	
	__webpack_require__(/*! ./_object-sap */ 1033)('isExtensible', function($isExtensible){
	  return function isExtensible(it){
	    return isObject(it) ? $isExtensible ? $isExtensible(it) : true : false;
	  };
	});

/***/ },

/***/ 1045:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.assign.js ***!
  \*****************************************************************/
[3748, 985, 1046],

/***/ 1046:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-assign.js ***!
  \**************************************************************/
[3758, 1007, 1020, 1021, 1035, 1010, 984],

/***/ 1047:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.is.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.1.3.10 Object.is(value1, value2)
	var $export = __webpack_require__(/*! ./_export */ 985);
	$export($export.S, 'Object', {is: __webpack_require__(/*! ./_same-value */ 1048)});

/***/ },

/***/ 1048:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_same-value.js ***!
  \***********************************************************/
/***/ function(module, exports) {

	// 7.2.9 SameValue(x, y)
	module.exports = Object.is || function is(x, y){
	  return x === y ? x !== 0 || 1 / x === 1 / y : x != x && y != y;
	};

/***/ },

/***/ 1049:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.set-prototype-of.js ***!
  \***************************************************************************/
[3803, 985, 1050],

/***/ 1050:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_set-proto.js ***!
  \**********************************************************/
[3804, 990, 989, 997, 1028],

/***/ 1051:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.object.to-string.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 19.1.3.6 Object.prototype.toString()
	var classof = __webpack_require__(/*! ./_classof */ 1052)
	  , test    = {};
	test[__webpack_require__(/*! ./_wks */ 1002)('toStringTag')] = 'z';
	if(test + '' != '[object z]'){
	  __webpack_require__(/*! ./_redefine */ 995)(Object.prototype, 'toString', function toString(){
	    return '[object ' + classof(this) + ']';
	  }, true);
	}

/***/ },

/***/ 1052:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_classof.js ***!
  \********************************************************/
[3831, 1011, 1002],

/***/ 1053:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.function.bind.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 19.2.3.2 / 15.3.4.5 Function.prototype.bind(thisArg, args...)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.P, 'Function', {bind: __webpack_require__(/*! ./_bind */ 1054)});

/***/ },

/***/ 1054:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_bind.js ***!
  \*****************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var aFunction  = __webpack_require__(/*! ./_a-function */ 998)
	  , isObject   = __webpack_require__(/*! ./_is-object */ 990)
	  , invoke     = __webpack_require__(/*! ./_invoke */ 1055)
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

/***/ 1055:
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

/***/ 1056:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.function.name.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var dP         = __webpack_require__(/*! ./_object-dp */ 988).f
	  , createDesc = __webpack_require__(/*! ./_property-desc */ 994)
	  , has        = __webpack_require__(/*! ./_has */ 982)
	  , FProto     = Function.prototype
	  , nameRE     = /^\s*function ([^ (]*)/
	  , NAME       = 'name';
	
	var isExtensible = Object.isExtensible || function(){
	  return true;
	};
	
	// 19.2.4.2 name
	NAME in FProto || __webpack_require__(/*! ./_descriptors */ 983) && dP(FProto, NAME, {
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

/***/ 1057:
/*!*************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.function.has-instance.js ***!
  \*************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var isObject       = __webpack_require__(/*! ./_is-object */ 990)
	  , getPrototypeOf = __webpack_require__(/*! ./_object-gpo */ 1036)
	  , HAS_INSTANCE   = __webpack_require__(/*! ./_wks */ 1002)('hasInstance')
	  , FunctionProto  = Function.prototype;
	// 19.2.3.6 Function.prototype[@@hasInstance](V)
	if(!(HAS_INSTANCE in FunctionProto))__webpack_require__(/*! ./_object-dp */ 988).f(FunctionProto, HAS_INSTANCE, {value: function(O){
	  if(typeof this != 'function' || !isObject(O))return false;
	  if(!isObject(this.prototype))return O instanceof this;
	  // for environment w/o native `@@hasInstance` logic enough `instanceof`, but add this:
	  while(O = getPrototypeOf(O))if(this.prototype === O)return true;
	  return false;
	}});

/***/ },

/***/ 1058:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.parse-int.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , $parseInt = __webpack_require__(/*! ./_parse-int */ 1059);
	// 18.2.5 parseInt(string, radix)
	$export($export.G + $export.F * (parseInt != $parseInt), {parseInt: $parseInt});

/***/ },

/***/ 1059:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_parse-int.js ***!
  \**********************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $parseInt = __webpack_require__(/*! ./_global */ 981).parseInt
	  , $trim     = __webpack_require__(/*! ./_string-trim */ 1060).trim
	  , ws        = __webpack_require__(/*! ./_string-ws */ 1061)
	  , hex       = /^[\-+]?0[xX]/;
	
	module.exports = $parseInt(ws + '08') !== 8 || $parseInt(ws + '0x16') !== 22 ? function parseInt(str, radix){
	  var string = $trim(String(str), 3);
	  return $parseInt(string, (radix >>> 0) || (hex.test(string) ? 16 : 10));
	} : $parseInt;

/***/ },

/***/ 1060:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-trim.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 985)
	  , defined = __webpack_require__(/*! ./_defined */ 1012)
	  , fails   = __webpack_require__(/*! ./_fails */ 984)
	  , spaces  = __webpack_require__(/*! ./_string-ws */ 1061)
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

/***/ 1061:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-ws.js ***!
  \**********************************************************/
/***/ function(module, exports) {

	module.exports = '\x09\x0A\x0B\x0C\x0D\x20\xA0\u1680\u180E\u2000\u2001\u2002\u2003' +
	  '\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u202F\u205F\u3000\u2028\u2029\uFEFF';

/***/ },

/***/ 1062:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.parse-float.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export     = __webpack_require__(/*! ./_export */ 985)
	  , $parseFloat = __webpack_require__(/*! ./_parse-float */ 1063);
	// 18.2.4 parseFloat(string)
	$export($export.G + $export.F * (parseFloat != $parseFloat), {parseFloat: $parseFloat});

/***/ },

/***/ 1063:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_parse-float.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $parseFloat = __webpack_require__(/*! ./_global */ 981).parseFloat
	  , $trim       = __webpack_require__(/*! ./_string-trim */ 1060).trim;
	
	module.exports = 1 / $parseFloat(__webpack_require__(/*! ./_string-ws */ 1061) + '-0') !== -Infinity ? function parseFloat(str){
	  var string = $trim(String(str), 3)
	    , result = $parseFloat(string);
	  return result === 0 && string.charAt(0) == '-' ? -0 : result;
	} : $parseFloat;

/***/ },

/***/ 1064:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.constructor.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var global            = __webpack_require__(/*! ./_global */ 981)
	  , has               = __webpack_require__(/*! ./_has */ 982)
	  , cof               = __webpack_require__(/*! ./_cof */ 1011)
	  , inheritIfRequired = __webpack_require__(/*! ./_inherit-if-required */ 1065)
	  , toPrimitive       = __webpack_require__(/*! ./_to-primitive */ 993)
	  , fails             = __webpack_require__(/*! ./_fails */ 984)
	  , gOPN              = __webpack_require__(/*! ./_object-gopn */ 1027).f
	  , gOPD              = __webpack_require__(/*! ./_object-gopd */ 1028).f
	  , dP                = __webpack_require__(/*! ./_object-dp */ 988).f
	  , $trim             = __webpack_require__(/*! ./_string-trim */ 1060).trim
	  , NUMBER            = 'Number'
	  , $Number           = global[NUMBER]
	  , Base              = $Number
	  , proto             = $Number.prototype
	  // Opera ~12 has broken Object#toString
	  , BROKEN_COF        = cof(__webpack_require__(/*! ./_object-create */ 1023)(proto)) == NUMBER
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
	  for(var keys = __webpack_require__(/*! ./_descriptors */ 983) ? gOPN(Base) : (
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
	  __webpack_require__(/*! ./_redefine */ 995)(global, NUMBER, $Number);
	}

/***/ },

/***/ 1065:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_inherit-if-required.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var isObject       = __webpack_require__(/*! ./_is-object */ 990)
	  , setPrototypeOf = __webpack_require__(/*! ./_set-proto */ 1050).set;
	module.exports = function(that, target, C){
	  var P, S = target.constructor;
	  if(S !== C && typeof S == 'function' && (P = S.prototype) !== C.prototype && isObject(P) && setPrototypeOf){
	    setPrototypeOf(that, P);
	  } return that;
	};

/***/ },

/***/ 1066:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.to-fixed.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export      = __webpack_require__(/*! ./_export */ 985)
	  , toInteger    = __webpack_require__(/*! ./_to-integer */ 1015)
	  , aNumberValue = __webpack_require__(/*! ./_a-number-value */ 1067)
	  , repeat       = __webpack_require__(/*! ./_string-repeat */ 1068)
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
	) || !__webpack_require__(/*! ./_fails */ 984)(function(){
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

/***/ 1067:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_a-number-value.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var cof = __webpack_require__(/*! ./_cof */ 1011);
	module.exports = function(it, msg){
	  if(typeof it != 'number' && cof(it) != 'Number')throw TypeError(msg);
	  return +it;
	};

/***/ },

/***/ 1068:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-repeat.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var toInteger = __webpack_require__(/*! ./_to-integer */ 1015)
	  , defined   = __webpack_require__(/*! ./_defined */ 1012);
	
	module.exports = function repeat(count){
	  var str = String(defined(this))
	    , res = ''
	    , n   = toInteger(count);
	  if(n < 0 || n == Infinity)throw RangeError("Count can't be negative");
	  for(;n > 0; (n >>>= 1) && (str += str))if(n & 1)res += str;
	  return res;
	};

/***/ },

/***/ 1069:
/*!***********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.to-precision.js ***!
  \***********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export      = __webpack_require__(/*! ./_export */ 985)
	  , $fails       = __webpack_require__(/*! ./_fails */ 984)
	  , aNumberValue = __webpack_require__(/*! ./_a-number-value */ 1067)
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

/***/ 1070:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.epsilon.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.1 Number.EPSILON
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Number', {EPSILON: Math.pow(2, -52)});

/***/ },

/***/ 1071:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.is-finite.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.2 Number.isFinite(number)
	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , _isFinite = __webpack_require__(/*! ./_global */ 981).isFinite;
	
	$export($export.S, 'Number', {
	  isFinite: function isFinite(it){
	    return typeof it == 'number' && _isFinite(it);
	  }
	});

/***/ },

/***/ 1072:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.is-integer.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.3 Number.isInteger(number)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Number', {isInteger: __webpack_require__(/*! ./_is-integer */ 1073)});

/***/ },

/***/ 1073:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_is-integer.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.3 Number.isInteger(number)
	var isObject = __webpack_require__(/*! ./_is-object */ 990)
	  , floor    = Math.floor;
	module.exports = function isInteger(it){
	  return !isObject(it) && isFinite(it) && floor(it) === it;
	};

/***/ },

/***/ 1074:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.is-nan.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.4 Number.isNaN(number)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Number', {
	  isNaN: function isNaN(number){
	    return number != number;
	  }
	});

/***/ },

/***/ 1075:
/*!**************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.is-safe-integer.js ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.5 Number.isSafeInteger(number)
	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , isInteger = __webpack_require__(/*! ./_is-integer */ 1073)
	  , abs       = Math.abs;
	
	$export($export.S, 'Number', {
	  isSafeInteger: function isSafeInteger(number){
	    return isInteger(number) && abs(number) <= 0x1fffffffffffff;
	  }
	});

/***/ },

/***/ 1076:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.max-safe-integer.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.6 Number.MAX_SAFE_INTEGER
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Number', {MAX_SAFE_INTEGER: 0x1fffffffffffff});

/***/ },

/***/ 1077:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.min-safe-integer.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.1.2.10 Number.MIN_SAFE_INTEGER
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Number', {MIN_SAFE_INTEGER: -0x1fffffffffffff});

/***/ },

/***/ 1078:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.parse-float.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export     = __webpack_require__(/*! ./_export */ 985)
	  , $parseFloat = __webpack_require__(/*! ./_parse-float */ 1063);
	// 20.1.2.12 Number.parseFloat(string)
	$export($export.S + $export.F * (Number.parseFloat != $parseFloat), 'Number', {parseFloat: $parseFloat});

/***/ },

/***/ 1079:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.number.parse-int.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , $parseInt = __webpack_require__(/*! ./_parse-int */ 1059);
	// 20.1.2.13 Number.parseInt(string, radix)
	$export($export.S + $export.F * (Number.parseInt != $parseInt), 'Number', {parseInt: $parseInt});

/***/ },

/***/ 1080:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.acosh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.3 Math.acosh(x)
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , log1p   = __webpack_require__(/*! ./_math-log1p */ 1081)
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

/***/ 1081:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_math-log1p.js ***!
  \***********************************************************/
/***/ function(module, exports) {

	// 20.2.2.20 Math.log1p(x)
	module.exports = Math.log1p || function log1p(x){
	  return (x = +x) > -1e-8 && x < 1e-8 ? x - x * x / 2 : Math.log(1 + x);
	};

/***/ },

/***/ 1082:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.asinh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.5 Math.asinh(x)
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $asinh  = Math.asinh;
	
	function asinh(x){
	  return !isFinite(x = +x) || x == 0 ? x : x < 0 ? -asinh(-x) : Math.log(x + Math.sqrt(x * x + 1));
	}
	
	// Tor Browser bug: Math.asinh(0) -> -0 
	$export($export.S + $export.F * !($asinh && 1 / $asinh(0) > 0), 'Math', {asinh: asinh});

/***/ },

/***/ 1083:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.atanh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.7 Math.atanh(x)
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $atanh  = Math.atanh;
	
	// Tor Browser bug: Math.atanh(-0) -> 0 
	$export($export.S + $export.F * !($atanh && 1 / $atanh(-0) < 0), 'Math', {
	  atanh: function atanh(x){
	    return (x = +x) == 0 ? x : Math.log((1 + x) / (1 - x)) / 2;
	  }
	});

/***/ },

/***/ 1084:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.cbrt.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.9 Math.cbrt(x)
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , sign    = __webpack_require__(/*! ./_math-sign */ 1085);
	
	$export($export.S, 'Math', {
	  cbrt: function cbrt(x){
	    return sign(x = +x) * Math.pow(Math.abs(x), 1 / 3);
	  }
	});

/***/ },

/***/ 1085:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_math-sign.js ***!
  \**********************************************************/
/***/ function(module, exports) {

	// 20.2.2.28 Math.sign(x)
	module.exports = Math.sign || function sign(x){
	  return (x = +x) == 0 || x != x ? x : x < 0 ? -1 : 1;
	};

/***/ },

/***/ 1086:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.clz32.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.11 Math.clz32(x)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Math', {
	  clz32: function clz32(x){
	    return (x >>>= 0) ? 31 - Math.floor(Math.log(x + 0.5) * Math.LOG2E) : 32;
	  }
	});

/***/ },

/***/ 1087:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.cosh.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.12 Math.cosh(x)
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , exp     = Math.exp;
	
	$export($export.S, 'Math', {
	  cosh: function cosh(x){
	    return (exp(x = +x) + exp(-x)) / 2;
	  }
	});

/***/ },

/***/ 1088:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.expm1.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.14 Math.expm1(x)
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $expm1  = __webpack_require__(/*! ./_math-expm1 */ 1089);
	
	$export($export.S + $export.F * ($expm1 != Math.expm1), 'Math', {expm1: $expm1});

/***/ },

/***/ 1089:
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

/***/ 1090:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.fround.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.16 Math.fround(x)
	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , sign      = __webpack_require__(/*! ./_math-sign */ 1085)
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

/***/ 1091:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.hypot.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.17 Math.hypot([value1[, value2[, … ]]])
	var $export = __webpack_require__(/*! ./_export */ 985)
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

/***/ 1092:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.imul.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.18 Math.imul(x, y)
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $imul   = Math.imul;
	
	// some WebKit versions fails with big numbers, some has wrong arity
	$export($export.S + $export.F * __webpack_require__(/*! ./_fails */ 984)(function(){
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

/***/ 1093:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.log10.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.21 Math.log10(x)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Math', {
	  log10: function log10(x){
	    return Math.log(x) / Math.LN10;
	  }
	});

/***/ },

/***/ 1094:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.log1p.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.20 Math.log1p(x)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Math', {log1p: __webpack_require__(/*! ./_math-log1p */ 1081)});

/***/ },

/***/ 1095:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.log2.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.22 Math.log2(x)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Math', {
	  log2: function log2(x){
	    return Math.log(x) / Math.LN2;
	  }
	});

/***/ },

/***/ 1096:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.sign.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.28 Math.sign(x)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Math', {sign: __webpack_require__(/*! ./_math-sign */ 1085)});

/***/ },

/***/ 1097:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.sinh.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.30 Math.sinh(x)
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , expm1   = __webpack_require__(/*! ./_math-expm1 */ 1089)
	  , exp     = Math.exp;
	
	// V8 near Chromium 38 has a problem with very small numbers
	$export($export.S + $export.F * __webpack_require__(/*! ./_fails */ 984)(function(){
	  return !Math.sinh(-2e-17) != -2e-17;
	}), 'Math', {
	  sinh: function sinh(x){
	    return Math.abs(x = +x) < 1
	      ? (expm1(x) - expm1(-x)) / 2
	      : (exp(x - 1) - exp(-x - 1)) * (Math.E / 2);
	  }
	});

/***/ },

/***/ 1098:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.tanh.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.33 Math.tanh(x)
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , expm1   = __webpack_require__(/*! ./_math-expm1 */ 1089)
	  , exp     = Math.exp;
	
	$export($export.S, 'Math', {
	  tanh: function tanh(x){
	    var a = expm1(x = +x)
	      , b = expm1(-x);
	    return a == Infinity ? 1 : b == Infinity ? -1 : (a - b) / (exp(x) + exp(-x));
	  }
	});

/***/ },

/***/ 1099:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.math.trunc.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.2.2.34 Math.trunc(x)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Math', {
	  trunc: function trunc(it){
	    return (it > 0 ? Math.floor : Math.ceil)(it);
	  }
	});

/***/ },

/***/ 1100:
/*!**************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.from-code-point.js ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export        = __webpack_require__(/*! ./_export */ 985)
	  , toIndex        = __webpack_require__(/*! ./_to-index */ 1016)
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

/***/ 1101:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.raw.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , toIObject = __webpack_require__(/*! ./_to-iobject */ 1009)
	  , toLength  = __webpack_require__(/*! ./_to-length */ 1014);
	
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

/***/ 1102:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.trim.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 21.1.3.25 String.prototype.trim()
	__webpack_require__(/*! ./_string-trim */ 1060)('trim', function($trim){
	  return function trim(){
	    return $trim(this, 3);
	  };
	});

/***/ },

/***/ 1103:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.iterator.js ***!
  \*******************************************************************/
[3773, 1104, 1105],

/***/ 1104:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-at.js ***!
  \**********************************************************/
[3774, 1015, 1012],

/***/ 1105:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iter-define.js ***!
  \************************************************************/
[3775, 1005, 985, 995, 987, 982, 1106, 1107, 1001, 1036, 1002],

/***/ 1106:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iterators.js ***!
  \**********************************************************/
529,

/***/ 1107:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iter-create.js ***!
  \************************************************************/
[3777, 1023, 994, 1001, 987, 1002],

/***/ 1108:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.code-point-at.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $at     = __webpack_require__(/*! ./_string-at */ 1104)(false);
	$export($export.P, 'String', {
	  // 21.1.3.3 String.prototype.codePointAt(pos)
	  codePointAt: function codePointAt(pos){
	    return $at(this, pos);
	  }
	});

/***/ },

/***/ 1109:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.ends-with.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 21.1.3.6 String.prototype.endsWith(searchString [, endPosition])
	'use strict';
	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , toLength  = __webpack_require__(/*! ./_to-length */ 1014)
	  , context   = __webpack_require__(/*! ./_string-context */ 1110)
	  , ENDS_WITH = 'endsWith'
	  , $endsWith = ''[ENDS_WITH];
	
	$export($export.P + $export.F * __webpack_require__(/*! ./_fails-is-regexp */ 1112)(ENDS_WITH), 'String', {
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

/***/ 1110:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-context.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// helper for String#{startsWith, endsWith, includes}
	var isRegExp = __webpack_require__(/*! ./_is-regexp */ 1111)
	  , defined  = __webpack_require__(/*! ./_defined */ 1012);
	
	module.exports = function(that, searchString, NAME){
	  if(isRegExp(searchString))throw TypeError('String#' + NAME + " doesn't accept regex!");
	  return String(defined(that));
	};

/***/ },

/***/ 1111:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_is-regexp.js ***!
  \**********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 7.2.8 IsRegExp(argument)
	var isObject = __webpack_require__(/*! ./_is-object */ 990)
	  , cof      = __webpack_require__(/*! ./_cof */ 1011)
	  , MATCH    = __webpack_require__(/*! ./_wks */ 1002)('match');
	module.exports = function(it){
	  var isRegExp;
	  return isObject(it) && ((isRegExp = it[MATCH]) !== undefined ? !!isRegExp : cof(it) == 'RegExp');
	};

/***/ },

/***/ 1112:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_fails-is-regexp.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var MATCH = __webpack_require__(/*! ./_wks */ 1002)('match');
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

/***/ 1113:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.includes.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 21.1.3.7 String.prototype.includes(searchString, position = 0)
	'use strict';
	var $export  = __webpack_require__(/*! ./_export */ 985)
	  , context  = __webpack_require__(/*! ./_string-context */ 1110)
	  , INCLUDES = 'includes';
	
	$export($export.P + $export.F * __webpack_require__(/*! ./_fails-is-regexp */ 1112)(INCLUDES), 'String', {
	  includes: function includes(searchString /*, position = 0 */){
	    return !!~context(this, searchString, INCLUDES)
	      .indexOf(searchString, arguments.length > 1 ? arguments[1] : undefined);
	  }
	});

/***/ },

/***/ 1114:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.repeat.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.P, 'String', {
	  // 21.1.3.13 String.prototype.repeat(count)
	  repeat: __webpack_require__(/*! ./_string-repeat */ 1068)
	});

/***/ },

/***/ 1115:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.starts-with.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 21.1.3.18 String.prototype.startsWith(searchString [, position ])
	'use strict';
	var $export     = __webpack_require__(/*! ./_export */ 985)
	  , toLength    = __webpack_require__(/*! ./_to-length */ 1014)
	  , context     = __webpack_require__(/*! ./_string-context */ 1110)
	  , STARTS_WITH = 'startsWith'
	  , $startsWith = ''[STARTS_WITH];
	
	$export($export.P + $export.F * __webpack_require__(/*! ./_fails-is-regexp */ 1112)(STARTS_WITH), 'String', {
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

/***/ 1116:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.anchor.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.2 String.prototype.anchor(name)
	__webpack_require__(/*! ./_string-html */ 1117)('anchor', function(createHTML){
	  return function anchor(name){
	    return createHTML(this, 'a', 'name', name);
	  }
	});

/***/ },

/***/ 1117:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-html.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 985)
	  , fails   = __webpack_require__(/*! ./_fails */ 984)
	  , defined = __webpack_require__(/*! ./_defined */ 1012)
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

/***/ 1118:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.big.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.3 String.prototype.big()
	__webpack_require__(/*! ./_string-html */ 1117)('big', function(createHTML){
	  return function big(){
	    return createHTML(this, 'big', '', '');
	  }
	});

/***/ },

/***/ 1119:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.blink.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.4 String.prototype.blink()
	__webpack_require__(/*! ./_string-html */ 1117)('blink', function(createHTML){
	  return function blink(){
	    return createHTML(this, 'blink', '', '');
	  }
	});

/***/ },

/***/ 1120:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.bold.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.5 String.prototype.bold()
	__webpack_require__(/*! ./_string-html */ 1117)('bold', function(createHTML){
	  return function bold(){
	    return createHTML(this, 'b', '', '');
	  }
	});

/***/ },

/***/ 1121:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.fixed.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.6 String.prototype.fixed()
	__webpack_require__(/*! ./_string-html */ 1117)('fixed', function(createHTML){
	  return function fixed(){
	    return createHTML(this, 'tt', '', '');
	  }
	});

/***/ },

/***/ 1122:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.fontcolor.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.7 String.prototype.fontcolor(color)
	__webpack_require__(/*! ./_string-html */ 1117)('fontcolor', function(createHTML){
	  return function fontcolor(color){
	    return createHTML(this, 'font', 'color', color);
	  }
	});

/***/ },

/***/ 1123:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.fontsize.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.8 String.prototype.fontsize(size)
	__webpack_require__(/*! ./_string-html */ 1117)('fontsize', function(createHTML){
	  return function fontsize(size){
	    return createHTML(this, 'font', 'size', size);
	  }
	});

/***/ },

/***/ 1124:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.italics.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.9 String.prototype.italics()
	__webpack_require__(/*! ./_string-html */ 1117)('italics', function(createHTML){
	  return function italics(){
	    return createHTML(this, 'i', '', '');
	  }
	});

/***/ },

/***/ 1125:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.link.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.10 String.prototype.link(url)
	__webpack_require__(/*! ./_string-html */ 1117)('link', function(createHTML){
	  return function link(url){
	    return createHTML(this, 'a', 'href', url);
	  }
	});

/***/ },

/***/ 1126:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.small.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.11 String.prototype.small()
	__webpack_require__(/*! ./_string-html */ 1117)('small', function(createHTML){
	  return function small(){
	    return createHTML(this, 'small', '', '');
	  }
	});

/***/ },

/***/ 1127:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.strike.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.12 String.prototype.strike()
	__webpack_require__(/*! ./_string-html */ 1117)('strike', function(createHTML){
	  return function strike(){
	    return createHTML(this, 'strike', '', '');
	  }
	});

/***/ },

/***/ 1128:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.sub.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.13 String.prototype.sub()
	__webpack_require__(/*! ./_string-html */ 1117)('sub', function(createHTML){
	  return function sub(){
	    return createHTML(this, 'sub', '', '');
	  }
	});

/***/ },

/***/ 1129:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.string.sup.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// B.2.3.14 String.prototype.sup()
	__webpack_require__(/*! ./_string-html */ 1117)('sup', function(createHTML){
	  return function sup(){
	    return createHTML(this, 'sup', '', '');
	  }
	});

/***/ },

/***/ 1130:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.date.now.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 20.3.3.1 / 15.9.4.4 Date.now()
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Date', {now: function(){ return new Date().getTime(); }});

/***/ },

/***/ 1131:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.date.to-json.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export     = __webpack_require__(/*! ./_export */ 985)
	  , toObject    = __webpack_require__(/*! ./_to-object */ 1035)
	  , toPrimitive = __webpack_require__(/*! ./_to-primitive */ 993);
	
	$export($export.P + $export.F * __webpack_require__(/*! ./_fails */ 984)(function(){
	  return new Date(NaN).toJSON() !== null || Date.prototype.toJSON.call({toISOString: function(){ return 1; }}) !== 1;
	}), 'Date', {
	  toJSON: function toJSON(key){
	    var O  = toObject(this)
	      , pv = toPrimitive(O);
	    return typeof pv == 'number' && !isFinite(pv) ? null : O.toISOString();
	  }
	});

/***/ },

/***/ 1132:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.date.to-iso-string.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 20.3.4.36 / 15.9.5.43 Date.prototype.toISOString()
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , fails   = __webpack_require__(/*! ./_fails */ 984)
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

/***/ 1133:
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
	  __webpack_require__(/*! ./_redefine */ 995)(DateProto, TO_STRING, function toString(){
	    var value = getTime.call(this);
	    return value === value ? $toString.call(this) : INVALID_DATE;
	  });
	}

/***/ },

/***/ 1134:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.date.to-primitive.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var TO_PRIMITIVE = __webpack_require__(/*! ./_wks */ 1002)('toPrimitive')
	  , proto        = Date.prototype;
	
	if(!(TO_PRIMITIVE in proto))__webpack_require__(/*! ./_hide */ 987)(proto, TO_PRIMITIVE, __webpack_require__(/*! ./_date-to-primitive */ 1135));

/***/ },

/***/ 1135:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_date-to-primitive.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var anObject    = __webpack_require__(/*! ./_an-object */ 989)
	  , toPrimitive = __webpack_require__(/*! ./_to-primitive */ 993)
	  , NUMBER      = 'number';
	
	module.exports = function(hint){
	  if(hint !== 'string' && hint !== NUMBER && hint !== 'default')throw TypeError('Incorrect hint');
	  return toPrimitive(anObject(this), hint != NUMBER);
	};

/***/ },

/***/ 1136:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.is-array.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.2.2 / 15.4.3.2 Array.isArray(arg)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Array', {isArray: __webpack_require__(/*! ./_is-array */ 1022)});

/***/ },

/***/ 1137:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.from.js ***!
  \**************************************************************/
[3826, 997, 985, 1035, 1138, 1139, 1014, 1140, 1141, 1142],

/***/ 1138:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iter-call.js ***!
  \**********************************************************/
[3827, 989],

/***/ 1139:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_is-array-iter.js ***!
  \**************************************************************/
[3828, 1106, 1002],

/***/ 1140:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_create-property.js ***!
  \****************************************************************/
[3829, 988, 994],

/***/ 1141:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/core.get-iterator-method.js ***!
  \************************************************************************/
[3830, 1052, 1002, 1106, 986],

/***/ 1142:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iter-detect.js ***!
  \************************************************************/
[3832, 1002],

/***/ 1143:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.of.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export        = __webpack_require__(/*! ./_export */ 985)
	  , createProperty = __webpack_require__(/*! ./_create-property */ 1140);
	
	// WebKit Array.of isn't generic
	$export($export.S + $export.F * __webpack_require__(/*! ./_fails */ 984)(function(){
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

/***/ 1144:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.join.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 22.1.3.13 Array.prototype.join(separator)
	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , toIObject = __webpack_require__(/*! ./_to-iobject */ 1009)
	  , arrayJoin = [].join;
	
	// fallback for not array-like strings
	$export($export.P + $export.F * (__webpack_require__(/*! ./_iobject */ 1010) != Object || !__webpack_require__(/*! ./_strict-method */ 1145)(arrayJoin)), 'Array', {
	  join: function join(separator){
	    return arrayJoin.call(toIObject(this), separator === undefined ? ',' : separator);
	  }
	});

/***/ },

/***/ 1145:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_strict-method.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var fails = __webpack_require__(/*! ./_fails */ 984);
	
	module.exports = function(method, arg){
	  return !!method && fails(function(){
	    arg ? method.call(null, function(){}, 1) : method.call(null);
	  });
	};

/***/ },

/***/ 1146:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.slice.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export    = __webpack_require__(/*! ./_export */ 985)
	  , html       = __webpack_require__(/*! ./_html */ 1025)
	  , cof        = __webpack_require__(/*! ./_cof */ 1011)
	  , toIndex    = __webpack_require__(/*! ./_to-index */ 1016)
	  , toLength   = __webpack_require__(/*! ./_to-length */ 1014)
	  , arraySlice = [].slice;
	
	// fallback for not array-like ES3 strings and DOM objects
	$export($export.P + $export.F * __webpack_require__(/*! ./_fails */ 984)(function(){
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

/***/ 1147:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.sort.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , aFunction = __webpack_require__(/*! ./_a-function */ 998)
	  , toObject  = __webpack_require__(/*! ./_to-object */ 1035)
	  , fails     = __webpack_require__(/*! ./_fails */ 984)
	  , $sort     = [].sort
	  , test      = [1, 2, 3];
	
	$export($export.P + $export.F * (fails(function(){
	  // IE8-
	  test.sort(undefined);
	}) || !fails(function(){
	  // V8 bug
	  test.sort(null);
	  // Old WebKit
	}) || !__webpack_require__(/*! ./_strict-method */ 1145)($sort)), 'Array', {
	  // 22.1.3.25 Array.prototype.sort(comparefn)
	  sort: function sort(comparefn){
	    return comparefn === undefined
	      ? $sort.call(toObject(this))
	      : $sort.call(toObject(this), aFunction(comparefn));
	  }
	});

/***/ },

/***/ 1148:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.for-each.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export  = __webpack_require__(/*! ./_export */ 985)
	  , $forEach = __webpack_require__(/*! ./_array-methods */ 1149)(0)
	  , STRICT   = __webpack_require__(/*! ./_strict-method */ 1145)([].forEach, true);
	
	$export($export.P + $export.F * !STRICT, 'Array', {
	  // 22.1.3.10 / 15.4.4.18 Array.prototype.forEach(callbackfn [, thisArg])
	  forEach: function forEach(callbackfn /* , thisArg */){
	    return $forEach(this, callbackfn, arguments[1]);
	  }
	});

/***/ },

/***/ 1149:
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
	var ctx      = __webpack_require__(/*! ./_ctx */ 997)
	  , IObject  = __webpack_require__(/*! ./_iobject */ 1010)
	  , toObject = __webpack_require__(/*! ./_to-object */ 1035)
	  , toLength = __webpack_require__(/*! ./_to-length */ 1014)
	  , asc      = __webpack_require__(/*! ./_array-species-create */ 1150);
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

/***/ 1150:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-species-create.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 9.4.2.3 ArraySpeciesCreate(originalArray, length)
	var speciesConstructor = __webpack_require__(/*! ./_array-species-constructor */ 1151);
	
	module.exports = function(original, length){
	  return new (speciesConstructor(original))(length);
	};

/***/ },

/***/ 1151:
/*!**************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-species-constructor.js ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(/*! ./_is-object */ 990)
	  , isArray  = __webpack_require__(/*! ./_is-array */ 1022)
	  , SPECIES  = __webpack_require__(/*! ./_wks */ 1002)('species');
	
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

/***/ 1152:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.map.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $map    = __webpack_require__(/*! ./_array-methods */ 1149)(1);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1145)([].map, true), 'Array', {
	  // 22.1.3.15 / 15.4.4.19 Array.prototype.map(callbackfn [, thisArg])
	  map: function map(callbackfn /* , thisArg */){
	    return $map(this, callbackfn, arguments[1]);
	  }
	});

/***/ },

/***/ 1153:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.filter.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $filter = __webpack_require__(/*! ./_array-methods */ 1149)(2);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1145)([].filter, true), 'Array', {
	  // 22.1.3.7 / 15.4.4.20 Array.prototype.filter(callbackfn [, thisArg])
	  filter: function filter(callbackfn /* , thisArg */){
	    return $filter(this, callbackfn, arguments[1]);
	  }
	});

/***/ },

/***/ 1154:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.some.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $some   = __webpack_require__(/*! ./_array-methods */ 1149)(3);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1145)([].some, true), 'Array', {
	  // 22.1.3.23 / 15.4.4.17 Array.prototype.some(callbackfn [, thisArg])
	  some: function some(callbackfn /* , thisArg */){
	    return $some(this, callbackfn, arguments[1]);
	  }
	});

/***/ },

/***/ 1155:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.every.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $every  = __webpack_require__(/*! ./_array-methods */ 1149)(4);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1145)([].every, true), 'Array', {
	  // 22.1.3.5 / 15.4.4.16 Array.prototype.every(callbackfn [, thisArg])
	  every: function every(callbackfn /* , thisArg */){
	    return $every(this, callbackfn, arguments[1]);
	  }
	});

/***/ },

/***/ 1156:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.reduce.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $reduce = __webpack_require__(/*! ./_array-reduce */ 1157);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1145)([].reduce, true), 'Array', {
	  // 22.1.3.18 / 15.4.4.21 Array.prototype.reduce(callbackfn [, initialValue])
	  reduce: function reduce(callbackfn /* , initialValue */){
	    return $reduce(this, callbackfn, arguments.length, arguments[1], false);
	  }
	});

/***/ },

/***/ 1157:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-reduce.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var aFunction = __webpack_require__(/*! ./_a-function */ 998)
	  , toObject  = __webpack_require__(/*! ./_to-object */ 1035)
	  , IObject   = __webpack_require__(/*! ./_iobject */ 1010)
	  , toLength  = __webpack_require__(/*! ./_to-length */ 1014);
	
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

/***/ 1158:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.reduce-right.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $reduce = __webpack_require__(/*! ./_array-reduce */ 1157);
	
	$export($export.P + $export.F * !__webpack_require__(/*! ./_strict-method */ 1145)([].reduceRight, true), 'Array', {
	  // 22.1.3.19 / 15.4.4.22 Array.prototype.reduceRight(callbackfn [, initialValue])
	  reduceRight: function reduceRight(callbackfn /* , initialValue */){
	    return $reduce(this, callbackfn, arguments.length, arguments[1], true);
	  }
	});

/***/ },

/***/ 1159:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.index-of.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export       = __webpack_require__(/*! ./_export */ 985)
	  , $indexOf      = __webpack_require__(/*! ./_array-includes */ 1013)(false)
	  , $native       = [].indexOf
	  , NEGATIVE_ZERO = !!$native && 1 / [1].indexOf(1, -0) < 0;
	
	$export($export.P + $export.F * (NEGATIVE_ZERO || !__webpack_require__(/*! ./_strict-method */ 1145)($native)), 'Array', {
	  // 22.1.3.11 / 15.4.4.14 Array.prototype.indexOf(searchElement [, fromIndex])
	  indexOf: function indexOf(searchElement /*, fromIndex = 0 */){
	    return NEGATIVE_ZERO
	      // convert -0 to +0
	      ? $native.apply(this, arguments) || 0
	      : $indexOf(this, searchElement, arguments[1]);
	  }
	});

/***/ },

/***/ 1160:
/*!***********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.last-index-of.js ***!
  \***********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export       = __webpack_require__(/*! ./_export */ 985)
	  , toIObject     = __webpack_require__(/*! ./_to-iobject */ 1009)
	  , toInteger     = __webpack_require__(/*! ./_to-integer */ 1015)
	  , toLength      = __webpack_require__(/*! ./_to-length */ 1014)
	  , $native       = [].lastIndexOf
	  , NEGATIVE_ZERO = !!$native && 1 / [1].lastIndexOf(1, -0) < 0;
	
	$export($export.P + $export.F * (NEGATIVE_ZERO || !__webpack_require__(/*! ./_strict-method */ 1145)($native)), 'Array', {
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

/***/ 1161:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.copy-within.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.3.3 Array.prototype.copyWithin(target, start, end = this.length)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.P, 'Array', {copyWithin: __webpack_require__(/*! ./_array-copy-within */ 1162)});
	
	__webpack_require__(/*! ./_add-to-unscopables */ 1163)('copyWithin');

/***/ },

/***/ 1162:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-copy-within.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.3.3 Array.prototype.copyWithin(target, start, end = this.length)
	'use strict';
	var toObject = __webpack_require__(/*! ./_to-object */ 1035)
	  , toIndex  = __webpack_require__(/*! ./_to-index */ 1016)
	  , toLength = __webpack_require__(/*! ./_to-length */ 1014);
	
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

/***/ 1163:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_add-to-unscopables.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.3.31 Array.prototype[@@unscopables]
	var UNSCOPABLES = __webpack_require__(/*! ./_wks */ 1002)('unscopables')
	  , ArrayProto  = Array.prototype;
	if(ArrayProto[UNSCOPABLES] == undefined)__webpack_require__(/*! ./_hide */ 987)(ArrayProto, UNSCOPABLES, {});
	module.exports = function(key){
	  ArrayProto[UNSCOPABLES][key] = true;
	};

/***/ },

/***/ 1164:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.fill.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.3.6 Array.prototype.fill(value, start = 0, end = this.length)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.P, 'Array', {fill: __webpack_require__(/*! ./_array-fill */ 1165)});
	
	__webpack_require__(/*! ./_add-to-unscopables */ 1163)('fill');

/***/ },

/***/ 1165:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-fill.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 22.1.3.6 Array.prototype.fill(value, start = 0, end = this.length)
	'use strict';
	var toObject = __webpack_require__(/*! ./_to-object */ 1035)
	  , toIndex  = __webpack_require__(/*! ./_to-index */ 1016)
	  , toLength = __webpack_require__(/*! ./_to-length */ 1014);
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

/***/ 1166:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.find.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 22.1.3.8 Array.prototype.find(predicate, thisArg = undefined)
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $find   = __webpack_require__(/*! ./_array-methods */ 1149)(5)
	  , KEY     = 'find'
	  , forced  = true;
	// Shouldn't skip holes
	if(KEY in [])Array(1)[KEY](function(){ forced = false; });
	$export($export.P + $export.F * forced, 'Array', {
	  find: function find(callbackfn/*, that = undefined */){
	    return $find(this, callbackfn, arguments.length > 1 ? arguments[1] : undefined);
	  }
	});
	__webpack_require__(/*! ./_add-to-unscopables */ 1163)(KEY);

/***/ },

/***/ 1167:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.find-index.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 22.1.3.9 Array.prototype.findIndex(predicate, thisArg = undefined)
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $find   = __webpack_require__(/*! ./_array-methods */ 1149)(6)
	  , KEY     = 'findIndex'
	  , forced  = true;
	// Shouldn't skip holes
	if(KEY in [])Array(1)[KEY](function(){ forced = false; });
	$export($export.P + $export.F * forced, 'Array', {
	  findIndex: function findIndex(callbackfn/*, that = undefined */){
	    return $find(this, callbackfn, arguments.length > 1 ? arguments[1] : undefined);
	  }
	});
	__webpack_require__(/*! ./_add-to-unscopables */ 1163)(KEY);

/***/ },

/***/ 1168:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.species.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_set-species */ 1169)('Array');

/***/ },

/***/ 1169:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_set-species.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var global      = __webpack_require__(/*! ./_global */ 981)
	  , dP          = __webpack_require__(/*! ./_object-dp */ 988)
	  , DESCRIPTORS = __webpack_require__(/*! ./_descriptors */ 983)
	  , SPECIES     = __webpack_require__(/*! ./_wks */ 1002)('species');
	
	module.exports = function(KEY){
	  var C = global[KEY];
	  if(DESCRIPTORS && C && !C[SPECIES])dP.f(C, SPECIES, {
	    configurable: true,
	    get: function(){ return this; }
	  });
	};

/***/ },

/***/ 1170:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.array.iterator.js ***!
  \******************************************************************/
[3785, 1163, 1171, 1106, 1009, 1105],

/***/ 1171:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_iter-step.js ***!
  \**********************************************************/
540,

/***/ 1172:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.constructor.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var global            = __webpack_require__(/*! ./_global */ 981)
	  , inheritIfRequired = __webpack_require__(/*! ./_inherit-if-required */ 1065)
	  , dP                = __webpack_require__(/*! ./_object-dp */ 988).f
	  , gOPN              = __webpack_require__(/*! ./_object-gopn */ 1027).f
	  , isRegExp          = __webpack_require__(/*! ./_is-regexp */ 1111)
	  , $flags            = __webpack_require__(/*! ./_flags */ 1173)
	  , $RegExp           = global.RegExp
	  , Base              = $RegExp
	  , proto             = $RegExp.prototype
	  , re1               = /a/g
	  , re2               = /a/g
	  // "new" creates a new object, old webkit buggy here
	  , CORRECT_NEW       = new $RegExp(re1) !== re1;
	
	if(__webpack_require__(/*! ./_descriptors */ 983) && (!CORRECT_NEW || __webpack_require__(/*! ./_fails */ 984)(function(){
	  re2[__webpack_require__(/*! ./_wks */ 1002)('match')] = false;
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
	  __webpack_require__(/*! ./_redefine */ 995)(global, 'RegExp', $RegExp);
	}
	
	__webpack_require__(/*! ./_set-species */ 1169)('RegExp');

/***/ },

/***/ 1173:
/*!******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_flags.js ***!
  \******************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 21.2.5.3 get RegExp.prototype.flags
	var anObject = __webpack_require__(/*! ./_an-object */ 989);
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

/***/ 1174:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.to-string.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	__webpack_require__(/*! ./es6.regexp.flags */ 1175);
	var anObject    = __webpack_require__(/*! ./_an-object */ 989)
	  , $flags      = __webpack_require__(/*! ./_flags */ 1173)
	  , DESCRIPTORS = __webpack_require__(/*! ./_descriptors */ 983)
	  , TO_STRING   = 'toString'
	  , $toString   = /./[TO_STRING];
	
	var define = function(fn){
	  __webpack_require__(/*! ./_redefine */ 995)(RegExp.prototype, TO_STRING, fn, true);
	};
	
	// 21.2.5.14 RegExp.prototype.toString()
	if(__webpack_require__(/*! ./_fails */ 984)(function(){ return $toString.call({source: 'a', flags: 'b'}) != '/a/b'; })){
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

/***/ 1175:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.flags.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 21.2.5.3 get RegExp.prototype.flags()
	if(__webpack_require__(/*! ./_descriptors */ 983) && /./g.flags != 'g')__webpack_require__(/*! ./_object-dp */ 988).f(RegExp.prototype, 'flags', {
	  configurable: true,
	  get: __webpack_require__(/*! ./_flags */ 1173)
	});

/***/ },

/***/ 1176:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.match.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// @@match logic
	__webpack_require__(/*! ./_fix-re-wks */ 1177)('match', 1, function(defined, MATCH, $match){
	  // 21.1.3.11 String.prototype.match(regexp)
	  return [function match(regexp){
	    'use strict';
	    var O  = defined(this)
	      , fn = regexp == undefined ? undefined : regexp[MATCH];
	    return fn !== undefined ? fn.call(regexp, O) : new RegExp(regexp)[MATCH](String(O));
	  }, $match];
	});

/***/ },

/***/ 1177:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_fix-re-wks.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var hide     = __webpack_require__(/*! ./_hide */ 987)
	  , redefine = __webpack_require__(/*! ./_redefine */ 995)
	  , fails    = __webpack_require__(/*! ./_fails */ 984)
	  , defined  = __webpack_require__(/*! ./_defined */ 1012)
	  , wks      = __webpack_require__(/*! ./_wks */ 1002);
	
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

/***/ 1178:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.replace.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// @@replace logic
	__webpack_require__(/*! ./_fix-re-wks */ 1177)('replace', 2, function(defined, REPLACE, $replace){
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

/***/ 1179:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.search.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// @@search logic
	__webpack_require__(/*! ./_fix-re-wks */ 1177)('search', 1, function(defined, SEARCH, $search){
	  // 21.1.3.15 String.prototype.search(regexp)
	  return [function search(regexp){
	    'use strict';
	    var O  = defined(this)
	      , fn = regexp == undefined ? undefined : regexp[SEARCH];
	    return fn !== undefined ? fn.call(regexp, O) : new RegExp(regexp)[SEARCH](String(O));
	  }, $search];
	});

/***/ },

/***/ 1180:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.regexp.split.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// @@split logic
	__webpack_require__(/*! ./_fix-re-wks */ 1177)('split', 2, function(defined, SPLIT, $split){
	  'use strict';
	  var isRegExp   = __webpack_require__(/*! ./_is-regexp */ 1111)
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

/***/ 1181:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.promise.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var LIBRARY            = __webpack_require__(/*! ./_library */ 1005)
	  , global             = __webpack_require__(/*! ./_global */ 981)
	  , ctx                = __webpack_require__(/*! ./_ctx */ 997)
	  , classof            = __webpack_require__(/*! ./_classof */ 1052)
	  , $export            = __webpack_require__(/*! ./_export */ 985)
	  , isObject           = __webpack_require__(/*! ./_is-object */ 990)
	  , aFunction          = __webpack_require__(/*! ./_a-function */ 998)
	  , anInstance         = __webpack_require__(/*! ./_an-instance */ 1182)
	  , forOf              = __webpack_require__(/*! ./_for-of */ 1183)
	  , speciesConstructor = __webpack_require__(/*! ./_species-constructor */ 1184)
	  , task               = __webpack_require__(/*! ./_task */ 1185).set
	  , microtask          = __webpack_require__(/*! ./_microtask */ 1186)()
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
	      , FakePromise = (promise.constructor = {})[__webpack_require__(/*! ./_wks */ 1002)('species')] = function(exec){ exec(empty, empty); };
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
	  Internal.prototype = __webpack_require__(/*! ./_redefine-all */ 1187)($Promise.prototype, {
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
	__webpack_require__(/*! ./_set-to-string-tag */ 1001)($Promise, PROMISE);
	__webpack_require__(/*! ./_set-species */ 1169)(PROMISE);
	Wrapper = __webpack_require__(/*! ./_core */ 986)[PROMISE];
	
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
	$export($export.S + $export.F * !(USE_NATIVE && __webpack_require__(/*! ./_iter-detect */ 1142)(function(iter){
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

/***/ 1182:
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

/***/ 1183:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_for-of.js ***!
  \*******************************************************/
/***/ function(module, exports, __webpack_require__) {

	var ctx         = __webpack_require__(/*! ./_ctx */ 997)
	  , call        = __webpack_require__(/*! ./_iter-call */ 1138)
	  , isArrayIter = __webpack_require__(/*! ./_is-array-iter */ 1139)
	  , anObject    = __webpack_require__(/*! ./_an-object */ 989)
	  , toLength    = __webpack_require__(/*! ./_to-length */ 1014)
	  , getIterFn   = __webpack_require__(/*! ./core.get-iterator-method */ 1141)
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

/***/ 1184:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_species-constructor.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 7.3.20 SpeciesConstructor(O, defaultConstructor)
	var anObject  = __webpack_require__(/*! ./_an-object */ 989)
	  , aFunction = __webpack_require__(/*! ./_a-function */ 998)
	  , SPECIES   = __webpack_require__(/*! ./_wks */ 1002)('species');
	module.exports = function(O, D){
	  var C = anObject(O).constructor, S;
	  return C === undefined || (S = anObject(C)[SPECIES]) == undefined ? D : aFunction(S);
	};

/***/ },

/***/ 1185:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_task.js ***!
  \*****************************************************/
/***/ function(module, exports, __webpack_require__) {

	var ctx                = __webpack_require__(/*! ./_ctx */ 997)
	  , invoke             = __webpack_require__(/*! ./_invoke */ 1055)
	  , html               = __webpack_require__(/*! ./_html */ 1025)
	  , cel                = __webpack_require__(/*! ./_dom-create */ 992)
	  , global             = __webpack_require__(/*! ./_global */ 981)
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
	  if(__webpack_require__(/*! ./_cof */ 1011)(process) == 'process'){
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

/***/ 1186:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_microtask.js ***!
  \**********************************************************/
/***/ function(module, exports, __webpack_require__) {

	var global    = __webpack_require__(/*! ./_global */ 981)
	  , macrotask = __webpack_require__(/*! ./_task */ 1185).set
	  , Observer  = global.MutationObserver || global.WebKitMutationObserver
	  , process   = global.process
	  , Promise   = global.Promise
	  , isNode    = __webpack_require__(/*! ./_cof */ 1011)(process) == 'process';
	
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

/***/ 1187:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_redefine-all.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var redefine = __webpack_require__(/*! ./_redefine */ 995);
	module.exports = function(target, src, safe){
	  for(var key in src)redefine(target, key, src[key], safe);
	  return target;
	};

/***/ },

/***/ 1188:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.map.js ***!
  \*******************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var strong = __webpack_require__(/*! ./_collection-strong */ 1189);
	
	// 23.1 Map Objects
	module.exports = __webpack_require__(/*! ./_collection */ 1190)('Map', function(get){
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

/***/ 1189:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_collection-strong.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var dP          = __webpack_require__(/*! ./_object-dp */ 988).f
	  , create      = __webpack_require__(/*! ./_object-create */ 1023)
	  , redefineAll = __webpack_require__(/*! ./_redefine-all */ 1187)
	  , ctx         = __webpack_require__(/*! ./_ctx */ 997)
	  , anInstance  = __webpack_require__(/*! ./_an-instance */ 1182)
	  , defined     = __webpack_require__(/*! ./_defined */ 1012)
	  , forOf       = __webpack_require__(/*! ./_for-of */ 1183)
	  , $iterDefine = __webpack_require__(/*! ./_iter-define */ 1105)
	  , step        = __webpack_require__(/*! ./_iter-step */ 1171)
	  , setSpecies  = __webpack_require__(/*! ./_set-species */ 1169)
	  , DESCRIPTORS = __webpack_require__(/*! ./_descriptors */ 983)
	  , fastKey     = __webpack_require__(/*! ./_meta */ 999).fastKey
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

/***/ 1190:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_collection.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var global            = __webpack_require__(/*! ./_global */ 981)
	  , $export           = __webpack_require__(/*! ./_export */ 985)
	  , redefine          = __webpack_require__(/*! ./_redefine */ 995)
	  , redefineAll       = __webpack_require__(/*! ./_redefine-all */ 1187)
	  , meta              = __webpack_require__(/*! ./_meta */ 999)
	  , forOf             = __webpack_require__(/*! ./_for-of */ 1183)
	  , anInstance        = __webpack_require__(/*! ./_an-instance */ 1182)
	  , isObject          = __webpack_require__(/*! ./_is-object */ 990)
	  , fails             = __webpack_require__(/*! ./_fails */ 984)
	  , $iterDetect       = __webpack_require__(/*! ./_iter-detect */ 1142)
	  , setToStringTag    = __webpack_require__(/*! ./_set-to-string-tag */ 1001)
	  , inheritIfRequired = __webpack_require__(/*! ./_inherit-if-required */ 1065);
	
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

/***/ 1191:
/*!*******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.set.js ***!
  \*******************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var strong = __webpack_require__(/*! ./_collection-strong */ 1189);
	
	// 23.2 Set Objects
	module.exports = __webpack_require__(/*! ./_collection */ 1190)('Set', function(get){
	  return function Set(){ return get(this, arguments.length > 0 ? arguments[0] : undefined); };
	}, {
	  // 23.2.3.1 Set.prototype.add(value)
	  add: function add(value){
	    return strong.def(this, value = value === 0 ? 0 : value, value);
	  }
	}, strong);

/***/ },

/***/ 1192:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.weak-map.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var each         = __webpack_require__(/*! ./_array-methods */ 1149)(0)
	  , redefine     = __webpack_require__(/*! ./_redefine */ 995)
	  , meta         = __webpack_require__(/*! ./_meta */ 999)
	  , assign       = __webpack_require__(/*! ./_object-assign */ 1046)
	  , weak         = __webpack_require__(/*! ./_collection-weak */ 1193)
	  , isObject     = __webpack_require__(/*! ./_is-object */ 990)
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
	var $WeakMap = module.exports = __webpack_require__(/*! ./_collection */ 1190)('WeakMap', wrapper, methods, weak, true, true);
	
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

/***/ 1193:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_collection-weak.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var redefineAll       = __webpack_require__(/*! ./_redefine-all */ 1187)
	  , getWeak           = __webpack_require__(/*! ./_meta */ 999).getWeak
	  , anObject          = __webpack_require__(/*! ./_an-object */ 989)
	  , isObject          = __webpack_require__(/*! ./_is-object */ 990)
	  , anInstance        = __webpack_require__(/*! ./_an-instance */ 1182)
	  , forOf             = __webpack_require__(/*! ./_for-of */ 1183)
	  , createArrayMethod = __webpack_require__(/*! ./_array-methods */ 1149)
	  , $has              = __webpack_require__(/*! ./_has */ 982)
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

/***/ 1194:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.weak-set.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var weak = __webpack_require__(/*! ./_collection-weak */ 1193);
	
	// 23.4 WeakSet Objects
	__webpack_require__(/*! ./_collection */ 1190)('WeakSet', function(get){
	  return function WeakSet(){ return get(this, arguments.length > 0 ? arguments[0] : undefined); };
	}, {
	  // 23.4.3.1 WeakSet.prototype.add(value)
	  add: function add(value){
	    return weak.def(this, value, true);
	  }
	}, weak, false, true);

/***/ },

/***/ 1195:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.array-buffer.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export      = __webpack_require__(/*! ./_export */ 985)
	  , $typed       = __webpack_require__(/*! ./_typed */ 1196)
	  , buffer       = __webpack_require__(/*! ./_typed-buffer */ 1197)
	  , anObject     = __webpack_require__(/*! ./_an-object */ 989)
	  , toIndex      = __webpack_require__(/*! ./_to-index */ 1016)
	  , toLength     = __webpack_require__(/*! ./_to-length */ 1014)
	  , isObject     = __webpack_require__(/*! ./_is-object */ 990)
	  , ArrayBuffer  = __webpack_require__(/*! ./_global */ 981).ArrayBuffer
	  , speciesConstructor = __webpack_require__(/*! ./_species-constructor */ 1184)
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
	
	$export($export.P + $export.U + $export.F * __webpack_require__(/*! ./_fails */ 984)(function(){
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
	
	__webpack_require__(/*! ./_set-species */ 1169)(ARRAY_BUFFER);

/***/ },

/***/ 1196:
/*!******************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_typed.js ***!
  \******************************************************/
/***/ function(module, exports, __webpack_require__) {

	var global = __webpack_require__(/*! ./_global */ 981)
	  , hide   = __webpack_require__(/*! ./_hide */ 987)
	  , uid    = __webpack_require__(/*! ./_uid */ 996)
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

/***/ 1197:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_typed-buffer.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var global         = __webpack_require__(/*! ./_global */ 981)
	  , DESCRIPTORS    = __webpack_require__(/*! ./_descriptors */ 983)
	  , LIBRARY        = __webpack_require__(/*! ./_library */ 1005)
	  , $typed         = __webpack_require__(/*! ./_typed */ 1196)
	  , hide           = __webpack_require__(/*! ./_hide */ 987)
	  , redefineAll    = __webpack_require__(/*! ./_redefine-all */ 1187)
	  , fails          = __webpack_require__(/*! ./_fails */ 984)
	  , anInstance     = __webpack_require__(/*! ./_an-instance */ 1182)
	  , toInteger      = __webpack_require__(/*! ./_to-integer */ 1015)
	  , toLength       = __webpack_require__(/*! ./_to-length */ 1014)
	  , gOPN           = __webpack_require__(/*! ./_object-gopn */ 1027).f
	  , dP             = __webpack_require__(/*! ./_object-dp */ 988).f
	  , arrayFill      = __webpack_require__(/*! ./_array-fill */ 1165)
	  , setToStringTag = __webpack_require__(/*! ./_set-to-string-tag */ 1001)
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

/***/ 1198:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.data-view.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 985);
	$export($export.G + $export.W + $export.F * !__webpack_require__(/*! ./_typed */ 1196).ABV, {
	  DataView: __webpack_require__(/*! ./_typed-buffer */ 1197).DataView
	});

/***/ },

/***/ 1199:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.int8-array.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1200)('Int8', 1, function(init){
	  return function Int8Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1200:
/*!************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_typed-array.js ***!
  \************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	if(__webpack_require__(/*! ./_descriptors */ 983)){
	  var LIBRARY             = __webpack_require__(/*! ./_library */ 1005)
	    , global              = __webpack_require__(/*! ./_global */ 981)
	    , fails               = __webpack_require__(/*! ./_fails */ 984)
	    , $export             = __webpack_require__(/*! ./_export */ 985)
	    , $typed              = __webpack_require__(/*! ./_typed */ 1196)
	    , $buffer             = __webpack_require__(/*! ./_typed-buffer */ 1197)
	    , ctx                 = __webpack_require__(/*! ./_ctx */ 997)
	    , anInstance          = __webpack_require__(/*! ./_an-instance */ 1182)
	    , propertyDesc        = __webpack_require__(/*! ./_property-desc */ 994)
	    , hide                = __webpack_require__(/*! ./_hide */ 987)
	    , redefineAll         = __webpack_require__(/*! ./_redefine-all */ 1187)
	    , toInteger           = __webpack_require__(/*! ./_to-integer */ 1015)
	    , toLength            = __webpack_require__(/*! ./_to-length */ 1014)
	    , toIndex             = __webpack_require__(/*! ./_to-index */ 1016)
	    , toPrimitive         = __webpack_require__(/*! ./_to-primitive */ 993)
	    , has                 = __webpack_require__(/*! ./_has */ 982)
	    , same                = __webpack_require__(/*! ./_same-value */ 1048)
	    , classof             = __webpack_require__(/*! ./_classof */ 1052)
	    , isObject            = __webpack_require__(/*! ./_is-object */ 990)
	    , toObject            = __webpack_require__(/*! ./_to-object */ 1035)
	    , isArrayIter         = __webpack_require__(/*! ./_is-array-iter */ 1139)
	    , create              = __webpack_require__(/*! ./_object-create */ 1023)
	    , getPrototypeOf      = __webpack_require__(/*! ./_object-gpo */ 1036)
	    , gOPN                = __webpack_require__(/*! ./_object-gopn */ 1027).f
	    , getIterFn           = __webpack_require__(/*! ./core.get-iterator-method */ 1141)
	    , uid                 = __webpack_require__(/*! ./_uid */ 996)
	    , wks                 = __webpack_require__(/*! ./_wks */ 1002)
	    , createArrayMethod   = __webpack_require__(/*! ./_array-methods */ 1149)
	    , createArrayIncludes = __webpack_require__(/*! ./_array-includes */ 1013)
	    , speciesConstructor  = __webpack_require__(/*! ./_species-constructor */ 1184)
	    , ArrayIterators      = __webpack_require__(/*! ./es6.array.iterator */ 1170)
	    , Iterators           = __webpack_require__(/*! ./_iterators */ 1106)
	    , $iterDetect         = __webpack_require__(/*! ./_iter-detect */ 1142)
	    , setSpecies          = __webpack_require__(/*! ./_set-species */ 1169)
	    , arrayFill           = __webpack_require__(/*! ./_array-fill */ 1165)
	    , arrayCopyWithin     = __webpack_require__(/*! ./_array-copy-within */ 1162)
	    , $DP                 = __webpack_require__(/*! ./_object-dp */ 988)
	    , $GOPD               = __webpack_require__(/*! ./_object-gopd */ 1028)
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

/***/ 1201:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.uint8-array.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1200)('Uint8', 1, function(init){
	  return function Uint8Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1202:
/*!*****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.uint8-clamped-array.js ***!
  \*****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1200)('Uint8', 1, function(init){
	  return function Uint8ClampedArray(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	}, true);

/***/ },

/***/ 1203:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.int16-array.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1200)('Int16', 2, function(init){
	  return function Int16Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1204:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.uint16-array.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1200)('Uint16', 2, function(init){
	  return function Uint16Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1205:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.int32-array.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1200)('Int32', 4, function(init){
	  return function Int32Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1206:
/*!**********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.uint32-array.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1200)('Uint32', 4, function(init){
	  return function Uint32Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1207:
/*!***********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.float32-array.js ***!
  \***********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1200)('Float32', 4, function(init){
	  return function Float32Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1208:
/*!***********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.typed.float64-array.js ***!
  \***********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ./_typed-array */ 1200)('Float64', 8, function(init){
	  return function Float64Array(data, byteOffset, length){
	    return init(this, data, byteOffset, length);
	  };
	});

/***/ },

/***/ 1209:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.apply.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.1 Reflect.apply(target, thisArgument, argumentsList)
	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , aFunction = __webpack_require__(/*! ./_a-function */ 998)
	  , anObject  = __webpack_require__(/*! ./_an-object */ 989)
	  , rApply    = (__webpack_require__(/*! ./_global */ 981).Reflect || {}).apply
	  , fApply    = Function.apply;
	// MS Edge argumentsList argument is optional
	$export($export.S + $export.F * !__webpack_require__(/*! ./_fails */ 984)(function(){
	  rApply(function(){});
	}), 'Reflect', {
	  apply: function apply(target, thisArgument, argumentsList){
	    var T = aFunction(target)
	      , L = anObject(argumentsList);
	    return rApply ? rApply(T, thisArgument, L) : fApply.call(T, thisArgument, L);
	  }
	});

/***/ },

/***/ 1210:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.construct.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.2 Reflect.construct(target, argumentsList [, newTarget])
	var $export    = __webpack_require__(/*! ./_export */ 985)
	  , create     = __webpack_require__(/*! ./_object-create */ 1023)
	  , aFunction  = __webpack_require__(/*! ./_a-function */ 998)
	  , anObject   = __webpack_require__(/*! ./_an-object */ 989)
	  , isObject   = __webpack_require__(/*! ./_is-object */ 990)
	  , fails      = __webpack_require__(/*! ./_fails */ 984)
	  , bind       = __webpack_require__(/*! ./_bind */ 1054)
	  , rConstruct = (__webpack_require__(/*! ./_global */ 981).Reflect || {}).construct;
	
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

/***/ 1211:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.define-property.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.3 Reflect.defineProperty(target, propertyKey, attributes)
	var dP          = __webpack_require__(/*! ./_object-dp */ 988)
	  , $export     = __webpack_require__(/*! ./_export */ 985)
	  , anObject    = __webpack_require__(/*! ./_an-object */ 989)
	  , toPrimitive = __webpack_require__(/*! ./_to-primitive */ 993);
	
	// MS Edge has broken Reflect.defineProperty - throwing instead of returning false
	$export($export.S + $export.F * __webpack_require__(/*! ./_fails */ 984)(function(){
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

/***/ 1212:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.delete-property.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.4 Reflect.deleteProperty(target, propertyKey)
	var $export  = __webpack_require__(/*! ./_export */ 985)
	  , gOPD     = __webpack_require__(/*! ./_object-gopd */ 1028).f
	  , anObject = __webpack_require__(/*! ./_an-object */ 989);
	
	$export($export.S, 'Reflect', {
	  deleteProperty: function deleteProperty(target, propertyKey){
	    var desc = gOPD(anObject(target), propertyKey);
	    return desc && !desc.configurable ? false : delete target[propertyKey];
	  }
	});

/***/ },

/***/ 1213:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.enumerate.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 26.1.5 Reflect.enumerate(target)
	var $export  = __webpack_require__(/*! ./_export */ 985)
	  , anObject = __webpack_require__(/*! ./_an-object */ 989);
	var Enumerate = function(iterated){
	  this._t = anObject(iterated); // target
	  this._i = 0;                  // next index
	  var keys = this._k = []       // keys
	    , key;
	  for(key in iterated)keys.push(key);
	};
	__webpack_require__(/*! ./_iter-create */ 1107)(Enumerate, 'Object', function(){
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

/***/ 1214:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.get.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.6 Reflect.get(target, propertyKey [, receiver])
	var gOPD           = __webpack_require__(/*! ./_object-gopd */ 1028)
	  , getPrototypeOf = __webpack_require__(/*! ./_object-gpo */ 1036)
	  , has            = __webpack_require__(/*! ./_has */ 982)
	  , $export        = __webpack_require__(/*! ./_export */ 985)
	  , isObject       = __webpack_require__(/*! ./_is-object */ 990)
	  , anObject       = __webpack_require__(/*! ./_an-object */ 989);
	
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

/***/ 1215:
/*!***************************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.get-own-property-descriptor.js ***!
  \***************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.7 Reflect.getOwnPropertyDescriptor(target, propertyKey)
	var gOPD     = __webpack_require__(/*! ./_object-gopd */ 1028)
	  , $export  = __webpack_require__(/*! ./_export */ 985)
	  , anObject = __webpack_require__(/*! ./_an-object */ 989);
	
	$export($export.S, 'Reflect', {
	  getOwnPropertyDescriptor: function getOwnPropertyDescriptor(target, propertyKey){
	    return gOPD.f(anObject(target), propertyKey);
	  }
	});

/***/ },

/***/ 1216:
/*!****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.get-prototype-of.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.8 Reflect.getPrototypeOf(target)
	var $export  = __webpack_require__(/*! ./_export */ 985)
	  , getProto = __webpack_require__(/*! ./_object-gpo */ 1036)
	  , anObject = __webpack_require__(/*! ./_an-object */ 989);
	
	$export($export.S, 'Reflect', {
	  getPrototypeOf: function getPrototypeOf(target){
	    return getProto(anObject(target));
	  }
	});

/***/ },

/***/ 1217:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.has.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.9 Reflect.has(target, propertyKey)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Reflect', {
	  has: function has(target, propertyKey){
	    return propertyKey in target;
	  }
	});

/***/ },

/***/ 1218:
/*!*************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.is-extensible.js ***!
  \*************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.10 Reflect.isExtensible(target)
	var $export       = __webpack_require__(/*! ./_export */ 985)
	  , anObject      = __webpack_require__(/*! ./_an-object */ 989)
	  , $isExtensible = Object.isExtensible;
	
	$export($export.S, 'Reflect', {
	  isExtensible: function isExtensible(target){
	    anObject(target);
	    return $isExtensible ? $isExtensible(target) : true;
	  }
	});

/***/ },

/***/ 1219:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.own-keys.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.11 Reflect.ownKeys(target)
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Reflect', {ownKeys: __webpack_require__(/*! ./_own-keys */ 1220)});

/***/ },

/***/ 1220:
/*!*********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_own-keys.js ***!
  \*********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// all object keys, includes non-enumerable and symbols
	var gOPN     = __webpack_require__(/*! ./_object-gopn */ 1027)
	  , gOPS     = __webpack_require__(/*! ./_object-gops */ 1020)
	  , anObject = __webpack_require__(/*! ./_an-object */ 989)
	  , Reflect  = __webpack_require__(/*! ./_global */ 981).Reflect;
	module.exports = Reflect && Reflect.ownKeys || function ownKeys(it){
	  var keys       = gOPN.f(anObject(it))
	    , getSymbols = gOPS.f;
	  return getSymbols ? keys.concat(getSymbols(it)) : keys;
	};

/***/ },

/***/ 1221:
/*!******************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.prevent-extensions.js ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.12 Reflect.preventExtensions(target)
	var $export            = __webpack_require__(/*! ./_export */ 985)
	  , anObject           = __webpack_require__(/*! ./_an-object */ 989)
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

/***/ 1222:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.set.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.13 Reflect.set(target, propertyKey, V [, receiver])
	var dP             = __webpack_require__(/*! ./_object-dp */ 988)
	  , gOPD           = __webpack_require__(/*! ./_object-gopd */ 1028)
	  , getPrototypeOf = __webpack_require__(/*! ./_object-gpo */ 1036)
	  , has            = __webpack_require__(/*! ./_has */ 982)
	  , $export        = __webpack_require__(/*! ./_export */ 985)
	  , createDesc     = __webpack_require__(/*! ./_property-desc */ 994)
	  , anObject       = __webpack_require__(/*! ./_an-object */ 989)
	  , isObject       = __webpack_require__(/*! ./_is-object */ 990);
	
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

/***/ 1223:
/*!****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es6.reflect.set-prototype-of.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// 26.1.14 Reflect.setPrototypeOf(target, proto)
	var $export  = __webpack_require__(/*! ./_export */ 985)
	  , setProto = __webpack_require__(/*! ./_set-proto */ 1050);
	
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

/***/ 1224:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.array.includes.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/tc39/Array.prototype.includes
	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , $includes = __webpack_require__(/*! ./_array-includes */ 1013)(true);
	
	$export($export.P, 'Array', {
	  includes: function includes(el /*, fromIndex = 0 */){
	    return $includes(this, el, arguments.length > 1 ? arguments[1] : undefined);
	  }
	});
	
	__webpack_require__(/*! ./_add-to-unscopables */ 1163)('includes');

/***/ },

/***/ 1225:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.at.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/mathiasbynens/String.prototype.at
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $at     = __webpack_require__(/*! ./_string-at */ 1104)(true);
	
	$export($export.P, 'String', {
	  at: function at(pos){
	    return $at(this, pos);
	  }
	});

/***/ },

/***/ 1226:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.pad-start.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/tc39/proposal-string-pad-start-end
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $pad    = __webpack_require__(/*! ./_string-pad */ 1227);
	
	$export($export.P, 'String', {
	  padStart: function padStart(maxLength /*, fillString = ' ' */){
	    return $pad(this, maxLength, arguments.length > 1 ? arguments[1] : undefined, true);
	  }
	});

/***/ },

/***/ 1227:
/*!***********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_string-pad.js ***!
  \***********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/tc39/proposal-string-pad-start-end
	var toLength = __webpack_require__(/*! ./_to-length */ 1014)
	  , repeat   = __webpack_require__(/*! ./_string-repeat */ 1068)
	  , defined  = __webpack_require__(/*! ./_defined */ 1012);
	
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

/***/ 1228:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.pad-end.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/tc39/proposal-string-pad-start-end
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $pad    = __webpack_require__(/*! ./_string-pad */ 1227);
	
	$export($export.P, 'String', {
	  padEnd: function padEnd(maxLength /*, fillString = ' ' */){
	    return $pad(this, maxLength, arguments.length > 1 ? arguments[1] : undefined, false);
	  }
	});

/***/ },

/***/ 1229:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.trim-left.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/sebmarkbage/ecmascript-string-left-right-trim
	__webpack_require__(/*! ./_string-trim */ 1060)('trimLeft', function($trim){
	  return function trimLeft(){
	    return $trim(this, 1);
	  };
	}, 'trimStart');

/***/ },

/***/ 1230:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.trim-right.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/sebmarkbage/ecmascript-string-left-right-trim
	__webpack_require__(/*! ./_string-trim */ 1060)('trimRight', function($trim){
	  return function trimRight(){
	    return $trim(this, 2);
	  };
	}, 'trimEnd');

/***/ },

/***/ 1231:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.string.match-all.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://tc39.github.io/String.prototype.matchAll/
	var $export     = __webpack_require__(/*! ./_export */ 985)
	  , defined     = __webpack_require__(/*! ./_defined */ 1012)
	  , toLength    = __webpack_require__(/*! ./_to-length */ 1014)
	  , isRegExp    = __webpack_require__(/*! ./_is-regexp */ 1111)
	  , getFlags    = __webpack_require__(/*! ./_flags */ 1173)
	  , RegExpProto = RegExp.prototype;
	
	var $RegExpStringIterator = function(regexp, string){
	  this._r = regexp;
	  this._s = string;
	};
	
	__webpack_require__(/*! ./_iter-create */ 1107)($RegExpStringIterator, 'RegExp String', function next(){
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

/***/ 1232:
/*!*************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.symbol.async-iterator.js ***!
  \*************************************************************************/
[3798, 1004],

/***/ 1233:
/*!*********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.symbol.observable.js ***!
  \*********************************************************************/
[3799, 1004],

/***/ 1234:
/*!***************************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.get-own-property-descriptors.js ***!
  \***************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/tc39/proposal-object-getownpropertydescriptors
	var $export        = __webpack_require__(/*! ./_export */ 985)
	  , ownKeys        = __webpack_require__(/*! ./_own-keys */ 1220)
	  , toIObject      = __webpack_require__(/*! ./_to-iobject */ 1009)
	  , gOPD           = __webpack_require__(/*! ./_object-gopd */ 1028)
	  , createProperty = __webpack_require__(/*! ./_create-property */ 1140);
	
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

/***/ 1235:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.values.js ***!
  \*****************************************************************/
[3816, 985, 1236],

/***/ 1236:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-to-array.js ***!
  \****************************************************************/
[3817, 1007, 1009, 1021],

/***/ 1237:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.entries.js ***!
  \******************************************************************/
[3821, 985, 1236],

/***/ 1238:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.define-getter.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export         = __webpack_require__(/*! ./_export */ 985)
	  , toObject        = __webpack_require__(/*! ./_to-object */ 1035)
	  , aFunction       = __webpack_require__(/*! ./_a-function */ 998)
	  , $defineProperty = __webpack_require__(/*! ./_object-dp */ 988);
	
	// B.2.2.2 Object.prototype.__defineGetter__(P, getter)
	__webpack_require__(/*! ./_descriptors */ 983) && $export($export.P + __webpack_require__(/*! ./_object-forced-pam */ 1239), 'Object', {
	  __defineGetter__: function __defineGetter__(P, getter){
	    $defineProperty.f(toObject(this), P, {get: aFunction(getter), enumerable: true, configurable: true});
	  }
	});

/***/ },

/***/ 1239:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_object-forced-pam.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// Forced replacement prototype accessors methods
	module.exports = __webpack_require__(/*! ./_library */ 1005)|| !__webpack_require__(/*! ./_fails */ 984)(function(){
	  var K = Math.random();
	  // In FF throws only define methods
	  __defineSetter__.call(null, K, function(){ /* empty */});
	  delete __webpack_require__(/*! ./_global */ 981)[K];
	});

/***/ },

/***/ 1240:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.define-setter.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export         = __webpack_require__(/*! ./_export */ 985)
	  , toObject        = __webpack_require__(/*! ./_to-object */ 1035)
	  , aFunction       = __webpack_require__(/*! ./_a-function */ 998)
	  , $defineProperty = __webpack_require__(/*! ./_object-dp */ 988);
	
	// B.2.2.3 Object.prototype.__defineSetter__(P, setter)
	__webpack_require__(/*! ./_descriptors */ 983) && $export($export.P + __webpack_require__(/*! ./_object-forced-pam */ 1239), 'Object', {
	  __defineSetter__: function __defineSetter__(P, setter){
	    $defineProperty.f(toObject(this), P, {set: aFunction(setter), enumerable: true, configurable: true});
	  }
	});

/***/ },

/***/ 1241:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.lookup-getter.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export                  = __webpack_require__(/*! ./_export */ 985)
	  , toObject                 = __webpack_require__(/*! ./_to-object */ 1035)
	  , toPrimitive              = __webpack_require__(/*! ./_to-primitive */ 993)
	  , getPrototypeOf           = __webpack_require__(/*! ./_object-gpo */ 1036)
	  , getOwnPropertyDescriptor = __webpack_require__(/*! ./_object-gopd */ 1028).f;
	
	// B.2.2.4 Object.prototype.__lookupGetter__(P)
	__webpack_require__(/*! ./_descriptors */ 983) && $export($export.P + __webpack_require__(/*! ./_object-forced-pam */ 1239), 'Object', {
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

/***/ 1242:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.object.lookup-setter.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $export                  = __webpack_require__(/*! ./_export */ 985)
	  , toObject                 = __webpack_require__(/*! ./_to-object */ 1035)
	  , toPrimitive              = __webpack_require__(/*! ./_to-primitive */ 993)
	  , getPrototypeOf           = __webpack_require__(/*! ./_object-gpo */ 1036)
	  , getOwnPropertyDescriptor = __webpack_require__(/*! ./_object-gopd */ 1028).f;
	
	// B.2.2.5 Object.prototype.__lookupSetter__(P)
	__webpack_require__(/*! ./_descriptors */ 983) && $export($export.P + __webpack_require__(/*! ./_object-forced-pam */ 1239), 'Object', {
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

/***/ 1243:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.map.to-json.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/DavidBruant/Map-Set.prototype.toJSON
	var $export  = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.P + $export.R, 'Map', {toJSON: __webpack_require__(/*! ./_collection-to-json */ 1244)('Map')});

/***/ },

/***/ 1244:
/*!*******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_collection-to-json.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/DavidBruant/Map-Set.prototype.toJSON
	var classof = __webpack_require__(/*! ./_classof */ 1052)
	  , from    = __webpack_require__(/*! ./_array-from-iterable */ 1245);
	module.exports = function(NAME){
	  return function toJSON(){
	    if(classof(this) != NAME)throw TypeError(NAME + "#toJSON isn't generic");
	    return from(this);
	  };
	};

/***/ },

/***/ 1245:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_array-from-iterable.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var forOf = __webpack_require__(/*! ./_for-of */ 1183);
	
	module.exports = function(iter, ITERATOR){
	  var result = [];
	  forOf(iter, false, result.push, result, ITERATOR);
	  return result;
	};


/***/ },

/***/ 1246:
/*!***************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.set.to-json.js ***!
  \***************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/DavidBruant/Map-Set.prototype.toJSON
	var $export  = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.P + $export.R, 'Set', {toJSON: __webpack_require__(/*! ./_collection-to-json */ 1244)('Set')});

/***/ },

/***/ 1247:
/*!*****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.system.global.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/ljharb/proposal-global
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'System', {global: __webpack_require__(/*! ./_global */ 981)});

/***/ },

/***/ 1248:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.error.is-error.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/ljharb/proposal-is-error
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , cof     = __webpack_require__(/*! ./_cof */ 1011);
	
	$export($export.S, 'Error', {
	  isError: function isError(it){
	    return cof(it) === 'Error';
	  }
	});

/***/ },

/***/ 1249:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.math.iaddh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://gist.github.com/BrendanEich/4294d5c212a6d2254703
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Math', {
	  iaddh: function iaddh(x0, x1, y0, y1){
	    var $x0 = x0 >>> 0
	      , $x1 = x1 >>> 0
	      , $y0 = y0 >>> 0;
	    return $x1 + (y1 >>> 0) + (($x0 & $y0 | ($x0 | $y0) & ~($x0 + $y0 >>> 0)) >>> 31) | 0;
	  }
	});

/***/ },

/***/ 1250:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.math.isubh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://gist.github.com/BrendanEich/4294d5c212a6d2254703
	var $export = __webpack_require__(/*! ./_export */ 985);
	
	$export($export.S, 'Math', {
	  isubh: function isubh(x0, x1, y0, y1){
	    var $x0 = x0 >>> 0
	      , $x1 = x1 >>> 0
	      , $y0 = y0 >>> 0;
	    return $x1 - (y1 >>> 0) - ((~$x0 & $y0 | ~($x0 ^ $y0) & $x0 - $y0 >>> 0) >>> 31) | 0;
	  }
	});

/***/ },

/***/ 1251:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.math.imulh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://gist.github.com/BrendanEich/4294d5c212a6d2254703
	var $export = __webpack_require__(/*! ./_export */ 985);
	
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

/***/ 1252:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.math.umulh.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://gist.github.com/BrendanEich/4294d5c212a6d2254703
	var $export = __webpack_require__(/*! ./_export */ 985);
	
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

/***/ 1253:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.define-metadata.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata                  = __webpack_require__(/*! ./_metadata */ 1254)
	  , anObject                  = __webpack_require__(/*! ./_an-object */ 989)
	  , toMetaKey                 = metadata.key
	  , ordinaryDefineOwnMetadata = metadata.set;
	
	metadata.exp({defineMetadata: function defineMetadata(metadataKey, metadataValue, target, targetKey){
	  ordinaryDefineOwnMetadata(metadataKey, metadataValue, anObject(target), toMetaKey(targetKey));
	}});

/***/ },

/***/ 1254:
/*!*********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_metadata.js ***!
  \*********************************************************/
/***/ function(module, exports, __webpack_require__) {

	var Map     = __webpack_require__(/*! ./es6.map */ 1188)
	  , $export = __webpack_require__(/*! ./_export */ 985)
	  , shared  = __webpack_require__(/*! ./_shared */ 1000)('metadata')
	  , store   = shared.store || (shared.store = new (__webpack_require__(/*! ./es6.weak-map */ 1192)));
	
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

/***/ 1255:
/*!***************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.delete-metadata.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata               = __webpack_require__(/*! ./_metadata */ 1254)
	  , anObject               = __webpack_require__(/*! ./_an-object */ 989)
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

/***/ 1256:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.get-metadata.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata               = __webpack_require__(/*! ./_metadata */ 1254)
	  , anObject               = __webpack_require__(/*! ./_an-object */ 989)
	  , getPrototypeOf         = __webpack_require__(/*! ./_object-gpo */ 1036)
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

/***/ 1257:
/*!*****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.get-metadata-keys.js ***!
  \*****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var Set                     = __webpack_require__(/*! ./es6.set */ 1191)
	  , from                    = __webpack_require__(/*! ./_array-from-iterable */ 1245)
	  , metadata                = __webpack_require__(/*! ./_metadata */ 1254)
	  , anObject                = __webpack_require__(/*! ./_an-object */ 989)
	  , getPrototypeOf          = __webpack_require__(/*! ./_object-gpo */ 1036)
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

/***/ 1258:
/*!****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.get-own-metadata.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata               = __webpack_require__(/*! ./_metadata */ 1254)
	  , anObject               = __webpack_require__(/*! ./_an-object */ 989)
	  , ordinaryGetOwnMetadata = metadata.get
	  , toMetaKey              = metadata.key;
	
	metadata.exp({getOwnMetadata: function getOwnMetadata(metadataKey, target /*, targetKey */){
	  return ordinaryGetOwnMetadata(metadataKey, anObject(target)
	    , arguments.length < 3 ? undefined : toMetaKey(arguments[2]));
	}});

/***/ },

/***/ 1259:
/*!*********************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.get-own-metadata-keys.js ***!
  \*********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata                = __webpack_require__(/*! ./_metadata */ 1254)
	  , anObject                = __webpack_require__(/*! ./_an-object */ 989)
	  , ordinaryOwnMetadataKeys = metadata.keys
	  , toMetaKey               = metadata.key;
	
	metadata.exp({getOwnMetadataKeys: function getOwnMetadataKeys(target /*, targetKey */){
	  return ordinaryOwnMetadataKeys(anObject(target), arguments.length < 2 ? undefined : toMetaKey(arguments[1]));
	}});

/***/ },

/***/ 1260:
/*!************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.has-metadata.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata               = __webpack_require__(/*! ./_metadata */ 1254)
	  , anObject               = __webpack_require__(/*! ./_an-object */ 989)
	  , getPrototypeOf         = __webpack_require__(/*! ./_object-gpo */ 1036)
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

/***/ 1261:
/*!****************************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.has-own-metadata.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata               = __webpack_require__(/*! ./_metadata */ 1254)
	  , anObject               = __webpack_require__(/*! ./_an-object */ 989)
	  , ordinaryHasOwnMetadata = metadata.has
	  , toMetaKey              = metadata.key;
	
	metadata.exp({hasOwnMetadata: function hasOwnMetadata(metadataKey, target /*, targetKey */){
	  return ordinaryHasOwnMetadata(metadataKey, anObject(target)
	    , arguments.length < 3 ? undefined : toMetaKey(arguments[2]));
	}});

/***/ },

/***/ 1262:
/*!********************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.reflect.metadata.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var metadata                  = __webpack_require__(/*! ./_metadata */ 1254)
	  , anObject                  = __webpack_require__(/*! ./_an-object */ 989)
	  , aFunction                 = __webpack_require__(/*! ./_a-function */ 998)
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

/***/ 1263:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.asap.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/rwaldron/tc39-notes/blob/master/es6/2014-09/sept-25.md#510-globalasap-for-enqueuing-a-microtask
	var $export   = __webpack_require__(/*! ./_export */ 985)
	  , microtask = __webpack_require__(/*! ./_microtask */ 1186)()
	  , process   = __webpack_require__(/*! ./_global */ 981).process
	  , isNode    = __webpack_require__(/*! ./_cof */ 1011)(process) == 'process';
	
	$export($export.G, {
	  asap: function asap(fn){
	    var domain = isNode && process.domain;
	    microtask(domain ? domain.bind(fn) : fn);
	  }
	});

/***/ },

/***/ 1264:
/*!**************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/es7.observable.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// https://github.com/zenparsing/es-observable
	var $export     = __webpack_require__(/*! ./_export */ 985)
	  , global      = __webpack_require__(/*! ./_global */ 981)
	  , core        = __webpack_require__(/*! ./_core */ 986)
	  , microtask   = __webpack_require__(/*! ./_microtask */ 1186)()
	  , OBSERVABLE  = __webpack_require__(/*! ./_wks */ 1002)('observable')
	  , aFunction   = __webpack_require__(/*! ./_a-function */ 998)
	  , anObject    = __webpack_require__(/*! ./_an-object */ 989)
	  , anInstance  = __webpack_require__(/*! ./_an-instance */ 1182)
	  , redefineAll = __webpack_require__(/*! ./_redefine-all */ 1187)
	  , hide        = __webpack_require__(/*! ./_hide */ 987)
	  , forOf       = __webpack_require__(/*! ./_for-of */ 1183)
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
	
	__webpack_require__(/*! ./_set-species */ 1169)('Observable');

/***/ },

/***/ 1265:
/*!**********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/web.timers.js ***!
  \**********************************************************/
/***/ function(module, exports, __webpack_require__) {

	// ie9- setTimeout & setInterval additional parameters fix
	var global     = __webpack_require__(/*! ./_global */ 981)
	  , $export    = __webpack_require__(/*! ./_export */ 985)
	  , invoke     = __webpack_require__(/*! ./_invoke */ 1055)
	  , partial    = __webpack_require__(/*! ./_partial */ 1266)
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

/***/ 1266:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_partial.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var path      = __webpack_require__(/*! ./_path */ 1267)
	  , invoke    = __webpack_require__(/*! ./_invoke */ 1055)
	  , aFunction = __webpack_require__(/*! ./_a-function */ 998);
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

/***/ 1267:
/*!*****************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/_path.js ***!
  \*****************************************************/
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(/*! ./_global */ 981);

/***/ },

/***/ 1268:
/*!*************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/web.immediate.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $task   = __webpack_require__(/*! ./_task */ 1185);
	$export($export.G + $export.B, {
	  setImmediate:   $task.set,
	  clearImmediate: $task.clear
	});

/***/ },

/***/ 1269:
/*!****************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/web.dom.iterable.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var $iterators    = __webpack_require__(/*! ./es6.array.iterator */ 1170)
	  , redefine      = __webpack_require__(/*! ./_redefine */ 995)
	  , global        = __webpack_require__(/*! ./_global */ 981)
	  , hide          = __webpack_require__(/*! ./_hide */ 987)
	  , Iterators     = __webpack_require__(/*! ./_iterators */ 1106)
	  , wks           = __webpack_require__(/*! ./_wks */ 1002)
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

/***/ 1270:
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
	
	  var Op = Object.prototype;
	  var hasOwn = Op.hasOwnProperty;
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
	
	  // This is a polyfill for %IteratorPrototype% for environments that
	  // don't natively support it.
	  var IteratorPrototype = {};
	  IteratorPrototype[iteratorSymbol] = function () {
	    return this;
	  };
	
	  var getProto = Object.getPrototypeOf;
	  var NativeIteratorPrototype = getProto && getProto(getProto(values([])));
	  if (NativeIteratorPrototype &&
	      NativeIteratorPrototype !== Op &&
	      hasOwn.call(NativeIteratorPrototype, iteratorSymbol)) {
	    // This environment has a native %IteratorPrototype%; use it instead
	    // of the polyfill.
	    IteratorPrototype = NativeIteratorPrototype;
	  }
	
	  var Gp = GeneratorFunctionPrototype.prototype =
	    Generator.prototype = Object.create(IteratorPrototype);
	  GeneratorFunction.prototype = Gp.constructor = GeneratorFunctionPrototype;
	  GeneratorFunctionPrototype.constructor = GeneratorFunction;
	  GeneratorFunctionPrototype[toStringTagSymbol] =
	    GeneratorFunction.displayName = "GeneratorFunction";
	
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
	  // `hasOwn.call(value, "__await")` to determine if the yielded value is
	  // meant to be awaited.
	  runtime.awrap = function(arg) {
	    return { __await: arg };
	  };
	
	  function AsyncIterator(generator) {
	    function invoke(method, arg, resolve, reject) {
	      var record = tryCatch(generator[method], generator, arg);
	      if (record.type === "throw") {
	        reject(record.arg);
	      } else {
	        var result = record.arg;
	        var value = result.value;
	        if (value &&
	            typeof value === "object" &&
	            hasOwn.call(value, "__await")) {
	          return Promise.resolve(value.__await).then(function(value) {
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
	  runtime.AsyncIterator = AsyncIterator;
	
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
	
	      context.method = method;
	      context.arg = arg;
	
	      while (true) {
	        var delegate = context.delegate;
	        if (delegate) {
	          var delegateResult = maybeInvokeDelegate(delegate, context);
	          if (delegateResult) {
	            if (delegateResult === ContinueSentinel) continue;
	            return delegateResult;
	          }
	        }
	
	        if (context.method === "next") {
	          // Setting context._sent for legacy support of Babel's
	          // function.sent implementation.
	          context.sent = context._sent = context.arg;
	
	        } else if (context.method === "throw") {
	          if (state === GenStateSuspendedStart) {
	            state = GenStateCompleted;
	            throw context.arg;
	          }
	
	          context.dispatchException(context.arg);
	
	        } else if (context.method === "return") {
	          context.abrupt("return", context.arg);
	        }
	
	        state = GenStateExecuting;
	
	        var record = tryCatch(innerFn, self, context);
	        if (record.type === "normal") {
	          // If an exception is thrown from innerFn, we leave state ===
	          // GenStateExecuting and loop back for another invocation.
	          state = context.done
	            ? GenStateCompleted
	            : GenStateSuspendedYield;
	
	          if (record.arg === ContinueSentinel) {
	            continue;
	          }
	
	          return {
	            value: record.arg,
	            done: context.done
	          };
	
	        } else if (record.type === "throw") {
	          state = GenStateCompleted;
	          // Dispatch the exception by looping back around to the
	          // context.dispatchException(context.arg) call above.
	          context.method = "throw";
	          context.arg = record.arg;
	        }
	      }
	    };
	  }
	
	  // Call delegate.iterator[context.method](context.arg) and handle the
	  // result, either by returning a { value, done } result from the
	  // delegate iterator, or by modifying context.method and context.arg,
	  // setting context.delegate to null, and returning the ContinueSentinel.
	  function maybeInvokeDelegate(delegate, context) {
	    var method = delegate.iterator[context.method];
	    if (method === undefined) {
	      // A .throw or .return when the delegate iterator has no .throw
	      // method always terminates the yield* loop.
	      context.delegate = null;
	
	      if (context.method === "throw") {
	        if (delegate.iterator.return) {
	          // If the delegate iterator has a return method, give it a
	          // chance to clean up.
	          context.method = "return";
	          context.arg = undefined;
	          maybeInvokeDelegate(delegate, context);
	
	          if (context.method === "throw") {
	            // If maybeInvokeDelegate(context) changed context.method from
	            // "return" to "throw", let that override the TypeError below.
	            return ContinueSentinel;
	          }
	        }
	
	        context.method = "throw";
	        context.arg = new TypeError(
	          "The iterator does not provide a 'throw' method");
	      }
	
	      return ContinueSentinel;
	    }
	
	    var record = tryCatch(method, delegate.iterator, context.arg);
	
	    if (record.type === "throw") {
	      context.method = "throw";
	      context.arg = record.arg;
	      context.delegate = null;
	      return ContinueSentinel;
	    }
	
	    var info = record.arg;
	
	    if (! info) {
	      context.method = "throw";
	      context.arg = new TypeError("iterator result is not an object");
	      context.delegate = null;
	      return ContinueSentinel;
	    }
	
	    if (info.done) {
	      // Assign the result of the finished delegate to the temporary
	      // variable specified by delegate.resultName (see delegateYield).
	      context[delegate.resultName] = info.value;
	
	      // Resume execution at the desired location (see delegateYield).
	      context.next = delegate.nextLoc;
	
	      // If context.method was "throw" but the delegate handled the
	      // exception, let the outer generator proceed normally. If
	      // context.method was "next", forget context.arg since it has been
	      // "consumed" by the delegate iterator. If context.method was
	      // "return", allow the original .return call to continue in the
	      // outer generator.
	      if (context.method !== "return") {
	        context.method = "next";
	        context.arg = undefined;
	      }
	
	    } else {
	      // Re-yield the result returned by the delegate method.
	      return info;
	    }
	
	    // The delegate iterator is finished, so forget it and continue with
	    // the outer generator.
	    context.delegate = null;
	    return ContinueSentinel;
	  }
	
	  // Define Generator.prototype.{next,throw,return} in terms of the
	  // unified ._invoke helper method.
	  defineIteratorMethods(Gp);
	
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
	
	      this.method = "next";
	      this.arg = undefined;
	
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
	
	        if (caught) {
	          // If the dispatched exception was caught by a catch block,
	          // then let that catch block handle the exception normally.
	          context.method = "next";
	          context.arg = undefined;
	        }
	
	        return !! caught;
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
	        this.method = "next";
	        this.next = finallyEntry.finallyLoc;
	        return ContinueSentinel;
	      }
	
	      return this.complete(record);
	    },
	
	    complete: function(record, afterLoc) {
	      if (record.type === "throw") {
	        throw record.arg;
	      }
	
	      if (record.type === "break" ||
	          record.type === "continue") {
	        this.next = record.arg;
	      } else if (record.type === "return") {
	        this.rval = this.arg = record.arg;
	        this.method = "return";
	        this.next = "end";
	      } else if (record.type === "normal" && afterLoc) {
	        this.next = afterLoc;
	      }
	
	      return ContinueSentinel;
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
	
	      if (this.method === "next") {
	        // Deliberately forget the last sent value so that we don't
	        // accidentally pass it on to the delegate.
	        this.arg = undefined;
	      }
	
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
	
	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }()), __webpack_require__(/*! ./../process/browser.js */ 30)))

/***/ },

/***/ 1271:
/*!********************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/fn/regexp/escape.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(/*! ../../modules/core.regexp.escape */ 1272);
	module.exports = __webpack_require__(/*! ../../modules/_core */ 986).RegExp.escape;

/***/ },

/***/ 1272:
/*!******************************************************************!*\
  !*** ./~/babel-polyfill/~/core-js/modules/core.regexp.escape.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// https://github.com/benjamingr/RexExp.escape
	var $export = __webpack_require__(/*! ./_export */ 985)
	  , $re     = __webpack_require__(/*! ./_replacer */ 1273)(/[\\^$*+?.()|[\]{}]/g, '\\$&');
	
	$export($export.S, 'RegExp', {escape: function escape(it){ return $re(it); }});


/***/ },

/***/ 1273:
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

/***/ 2770:
/*!********************************************************!*\
  !*** ./atlas_bundles/differential-expression/index.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/differentialRenderer.js */ 2771);

/***/ },

/***/ 2771:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/differentialRenderer.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 2);
	var ReactDOM = __webpack_require__(/*! react-dom */ 35);
	
	//*------------------------------------------------------------------*
	
	var DifferentialRouter = __webpack_require__(/*! ./DifferentialRouter.jsx */ 2772);
	
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

/***/ 2772:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialRouter.jsx ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var React = __webpack_require__(/*! react */ 2);
	
	var $ = __webpack_require__(/*! jquery */ 2773);
	$.ajaxSetup({ traditional: true });
	
	var Url = __webpack_require__(/*! url */ 2774);
	
	//*------------------------------------------------------------------*
	
	var Results = __webpack_require__(/*! ./DifferentialResults.jsx */ 2780);
	var Facets = __webpack_require__(/*! ./DifferentialFacetsTree.jsx */ 2949);
	var UrlManager = __webpack_require__(/*! ./urlManager.js */ 2952);
	
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
	            { className: 'row' },
	            React.createElement(
	                'div',
	                { className: 'small-3 columns', id: 'gxaDifferentialFacetsContainerDiv' },
	                Object.keys(this.state.facetsTreeData).length ? React.createElement(Facets, {
	                    facets: this._prepareFacetTreeData(filteredResults),
	                    setChecked: this._setChecked
	                }) : React.createElement('div', null)
	            ),
	            React.createElement(
	                'div',
	                { className: 'small-9 columns', id: 'gxaDifferentialResultsContainerDiv' },
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
	
	        differentialFacetsUrlObject.pathname = 'gxa/json/search/differential_facets';
	        differentialResultsUrlObject.pathname = 'gxa/json/search/differential_results';
	
	        var queryParams = { geneQuery: this.props.geneQuery, conditionQuery: this.props.conditionQuery, species: this.props.species };
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

/***/ 2773:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery/dist/jquery.js ***!
  \***********************************************************************/
890,

/***/ 2774:
/*!************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/url/url.js ***!
  \************************************************************/
[3734, 2775, 2776, 2777],

/***/ 2775:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/url/~/punycode/punycode.js ***!
  \****************************************************************************/
414,

/***/ 2776:
/*!*************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/url/util.js ***!
  \*************************************************************/
415,

/***/ 2777:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/querystring/index.js ***!
  \**********************************************************************/
[3735, 2778, 2779],

/***/ 2778:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/querystring/decode.js ***!
  \***********************************************************************/
417,

/***/ 2779:
/*!***********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/querystring/encode.js ***!
  \***********************************************************************/
418,

/***/ 2780:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.jsx ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var $ = __webpack_require__(/*! jquery */ 2773);
	__webpack_require__(/*! jquery.browser */ 2781);
	
	var React = __webpack_require__(/*! react */ 2);
	var ReactDOM = __webpack_require__(/*! react-dom */ 35);
	
	//*------------------------------------------------------------------*
	
	var DisplayLevelsButton = __webpack_require__(/*! expression-atlas-display-levels-button */ 2782);
	var Legend = __webpack_require__(/*! expression-atlas-legend */ 2785).LegendDifferential;
	var CellDifferential = __webpack_require__(/*! expression-atlas-cell-differential */ 2801);
	var DifferentialDownloadButton = __webpack_require__(/*! ./DifferentialDownloadButton.jsx */ 2809);
	var ContrastTooltips = __webpack_require__(/*! expression-atlas-contrast-tooltips */ 2812);
	var AtlasFeedback = __webpack_require__(/*! expression-atlas-feedback */ 2817);
	var EbiSpeciesIcon = __webpack_require__(/*! react-ebi-species */ 2941).Icon;
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialResults.css */ 2947);
	
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

/***/ 2781:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery.browser/dist/jquery.browser.js ***!
  \***************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;/*!
	 * jQuery Browser Plugin 0.1.0
	 * https://github.com/gabceb/jquery-browser-plugin
	 *
	 * Original jquery-browser code Copyright 2005, 2015 jQuery Foundation, Inc. and other contributors
	 * http://jquery.org/license
	 *
	 * Modifications Copyright 2015 Gabriel Cebrian
	 * https://github.com/gabceb
	 *
	 * Released under the MIT license
	 *
	 * Date: 05-07-2015
	 */
	/*global window: false */
	
	(function (factory) {
	  if (true) {
	    // AMD. Register as an anonymous module.
	    !(__WEBPACK_AMD_DEFINE_ARRAY__ = [__webpack_require__(/*! jquery */ 2773)], __WEBPACK_AMD_DEFINE_RESULT__ = function ($) {
	      return factory($);
	    }.apply(exports, __WEBPACK_AMD_DEFINE_ARRAY__), __WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
	  } else if (typeof module === 'object' && typeof module.exports === 'object') {
	    // Node-like environment
	    module.exports = factory(require('jquery'));
	  } else {
	    // Browser globals
	    factory(window.jQuery);
	  }
	}(function(jQuery) {
	  "use strict";
	
	  function uaMatch( ua ) {
	    // If an UA is not provided, default to the current browser UA.
	    if ( ua === undefined ) {
	      ua = window.navigator.userAgent;
	    }
	    ua = ua.toLowerCase();
	
	    var match = /(edge)\/([\w.]+)/.exec( ua ) ||
	        /(opr)[\/]([\w.]+)/.exec( ua ) ||
	        /(chrome)[ \/]([\w.]+)/.exec( ua ) ||
	        /(iemobile)[\/]([\w.]+)/.exec( ua ) ||
	        /(version)(applewebkit)[ \/]([\w.]+).*(safari)[ \/]([\w.]+)/.exec( ua ) ||
	        /(webkit)[ \/]([\w.]+).*(version)[ \/]([\w.]+).*(safari)[ \/]([\w.]+)/.exec( ua ) ||
	        /(webkit)[ \/]([\w.]+)/.exec( ua ) ||
	        /(opera)(?:.*version|)[ \/]([\w.]+)/.exec( ua ) ||
	        /(msie) ([\w.]+)/.exec( ua ) ||
	        ua.indexOf("trident") >= 0 && /(rv)(?::| )([\w.]+)/.exec( ua ) ||
	        ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec( ua ) ||
	        [];
	
	    var platform_match = /(ipad)/.exec( ua ) ||
	        /(ipod)/.exec( ua ) ||
	        /(windows phone)/.exec( ua ) ||
	        /(iphone)/.exec( ua ) ||
	        /(kindle)/.exec( ua ) ||
	        /(silk)/.exec( ua ) ||
	        /(android)/.exec( ua ) ||
	        /(win)/.exec( ua ) ||
	        /(mac)/.exec( ua ) ||
	        /(linux)/.exec( ua ) ||
	        /(cros)/.exec( ua ) ||
	        /(playbook)/.exec( ua ) ||
	        /(bb)/.exec( ua ) ||
	        /(blackberry)/.exec( ua ) ||
	        [];
	
	    var browser = {},
	        matched = {
	          browser: match[ 5 ] || match[ 3 ] || match[ 1 ] || "",
	          version: match[ 2 ] || match[ 4 ] || "0",
	          versionNumber: match[ 4 ] || match[ 2 ] || "0",
	          platform: platform_match[ 0 ] || ""
	        };
	
	    if ( matched.browser ) {
	      browser[ matched.browser ] = true;
	      browser.version = matched.version;
	      browser.versionNumber = parseInt(matched.versionNumber, 10);
	    }
	
	    if ( matched.platform ) {
	      browser[ matched.platform ] = true;
	    }
	
	    // These are all considered mobile platforms, meaning they run a mobile browser
	    if ( browser.android || browser.bb || browser.blackberry || browser.ipad || browser.iphone ||
	      browser.ipod || browser.kindle || browser.playbook || browser.silk || browser[ "windows phone" ]) {
	      browser.mobile = true;
	    }
	
	    // These are all considered desktop platforms, meaning they run a desktop browser
	    if ( browser.cros || browser.mac || browser.linux || browser.win ) {
	      browser.desktop = true;
	    }
	
	    // Chrome, Opera 15+ and Safari are webkit based browsers
	    if ( browser.chrome || browser.opr || browser.safari ) {
	      browser.webkit = true;
	    }
	
	    // IE11 has a new token so we will assign it msie to avoid breaking changes
	    if ( browser.rv || browser.iemobile) {
	      var ie = "msie";
	
	      matched.browser = ie;
	      browser[ie] = true;
	    }
	
	    // Edge is officially known as Microsoft Edge, so rewrite the key to match
	    if ( browser.edge ) {
	      delete browser.edge;
	      var msedge = "msedge";
	
	      matched.browser = msedge;
	      browser[msedge] = true;
	    }
	
	    // Blackberry browsers are marked as Safari on BlackBerry
	    if ( browser.safari && browser.blackberry ) {
	      var blackberry = "blackberry";
	
	      matched.browser = blackberry;
	      browser[blackberry] = true;
	    }
	
	    // Playbook browsers are marked as Safari on Playbook
	    if ( browser.safari && browser.playbook ) {
	      var playbook = "playbook";
	
	      matched.browser = playbook;
	      browser[playbook] = true;
	    }
	
	    // BB10 is a newer OS version of BlackBerry
	    if ( browser.bb ) {
	      var bb = "blackberry";
	
	      matched.browser = bb;
	      browser[bb] = true;
	    }
	
	    // Opera 15+ are identified as opr
	    if ( browser.opr ) {
	      var opera = "opera";
	
	      matched.browser = opera;
	      browser[opera] = true;
	    }
	
	    // Stock Android browsers are marked as Safari on Android.
	    if ( browser.safari && browser.android ) {
	      var android = "android";
	
	      matched.browser = android;
	      browser[android] = true;
	    }
	
	    // Kindle browsers are marked as Safari on Kindle
	    if ( browser.safari && browser.kindle ) {
	      var kindle = "kindle";
	
	      matched.browser = kindle;
	      browser[kindle] = true;
	    }
	
	     // Kindle Silk browsers are marked as Safari on Kindle
	    if ( browser.safari && browser.silk ) {
	      var silk = "silk";
	
	      matched.browser = silk;
	      browser[silk] = true;
	    }
	
	    // Assign the name and platform variable
	    browser.name = matched.browser;
	    browser.platform = matched.platform;
	    return browser;
	  }
	
	  // Run the matching process, also assign the function to the returned object
	  // for manual, jQuery-free use if desired
	  window.jQBrowser = uaMatch( window.navigator.userAgent );
	  window.jQBrowser.uaMatch = uaMatch;
	
	  // Only assign to jQuery.browser if jQuery is loaded
	  if ( jQuery ) {
	    jQuery.browser = window.jQBrowser;
	  }
	
	  return window.jQBrowser;
	}));


/***/ },

/***/ 2782:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/index.js ***!
  \*************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/DisplayLevelsButton.jsx */ 2783);

/***/ },

/***/ 2783:
/*!********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-display-levels-button/src/DisplayLevelsButton.jsx ***!
  \********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 2);
	var ReactDOM = __webpack_require__(/*! react-dom */ 35);
	
	var $ = __webpack_require__(/*! jquery */ 2773);
	__webpack_require__(/*! jquery-ui-bundle */ 2784);
	
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

/***/ 2784:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/jquery-ui-bundle/jquery-ui.js ***!
  \*******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var __WEBPACK_AMD_DEFINE_FACTORY__, __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;/*! jQuery UI - v1.11.4 - 2015-03-11
	* http://jqueryui.com
	* Includes: core.js, widget.js, mouse.js, position.js, accordion.js, autocomplete.js, button.js, datepicker.js, dialog.js, draggable.js, droppable.js, effect.js, effect-blind.js, effect-bounce.js, effect-clip.js, effect-drop.js, effect-explode.js, effect-fade.js, effect-fold.js, effect-highlight.js, effect-puff.js, effect-pulsate.js, effect-scale.js, effect-shake.js, effect-size.js, effect-slide.js, effect-transfer.js, menu.js, progressbar.js, resizable.js, selectable.js, selectmenu.js, slider.js, sortable.js, spinner.js, tabs.js, tooltip.js
	* Copyright 2015 jQuery Foundation and other contributors; Licensed MIT */
	
	(function( factory ) {
		if ( true ) {
	
			// AMD. Register as an anonymous module.
			!(__WEBPACK_AMD_DEFINE_ARRAY__ = [ __webpack_require__(/*! jquery */ 2773) ], __WEBPACK_AMD_DEFINE_FACTORY__ = (factory), __WEBPACK_AMD_DEFINE_RESULT__ = (typeof __WEBPACK_AMD_DEFINE_FACTORY__ === 'function' ? (__WEBPACK_AMD_DEFINE_FACTORY__.apply(exports, __WEBPACK_AMD_DEFINE_ARRAY__)) : __WEBPACK_AMD_DEFINE_FACTORY__), __WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
		} else {
	
			// Browser globals
			factory( jQuery );
		}
	}(function( $ ) {
	/*!
	 * jQuery UI Core 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/category/ui-core/
	 */
	
	
	// $.ui might exist from components with no dependencies, e.g., $.ui.position
	$.ui = $.ui || {};
	
	$.extend( $.ui, {
		version: "1.11.4",
	
		keyCode: {
			BACKSPACE: 8,
			COMMA: 188,
			DELETE: 46,
			DOWN: 40,
			END: 35,
			ENTER: 13,
			ESCAPE: 27,
			HOME: 36,
			LEFT: 37,
			PAGE_DOWN: 34,
			PAGE_UP: 33,
			PERIOD: 190,
			RIGHT: 39,
			SPACE: 32,
			TAB: 9,
			UP: 38
		}
	});
	
	// plugins
	$.fn.extend({
		scrollParent: function( includeHidden ) {
			var position = this.css( "position" ),
				excludeStaticParent = position === "absolute",
				overflowRegex = includeHidden ? /(auto|scroll|hidden)/ : /(auto|scroll)/,
				scrollParent = this.parents().filter( function() {
					var parent = $( this );
					if ( excludeStaticParent && parent.css( "position" ) === "static" ) {
						return false;
					}
					return overflowRegex.test( parent.css( "overflow" ) + parent.css( "overflow-y" ) + parent.css( "overflow-x" ) );
				}).eq( 0 );
	
			return position === "fixed" || !scrollParent.length ? $( this[ 0 ].ownerDocument || document ) : scrollParent;
		},
	
		uniqueId: (function() {
			var uuid = 0;
	
			return function() {
				return this.each(function() {
					if ( !this.id ) {
						this.id = "ui-id-" + ( ++uuid );
					}
				});
			};
		})(),
	
		removeUniqueId: function() {
			return this.each(function() {
				if ( /^ui-id-\d+$/.test( this.id ) ) {
					$( this ).removeAttr( "id" );
				}
			});
		}
	});
	
	// selectors
	function focusable( element, isTabIndexNotNaN ) {
		var map, mapName, img,
			nodeName = element.nodeName.toLowerCase();
		if ( "area" === nodeName ) {
			map = element.parentNode;
			mapName = map.name;
			if ( !element.href || !mapName || map.nodeName.toLowerCase() !== "map" ) {
				return false;
			}
			img = $( "img[usemap='#" + mapName + "']" )[ 0 ];
			return !!img && visible( img );
		}
		return ( /^(input|select|textarea|button|object)$/.test( nodeName ) ?
			!element.disabled :
			"a" === nodeName ?
				element.href || isTabIndexNotNaN :
				isTabIndexNotNaN) &&
			// the element and all of its ancestors must be visible
			visible( element );
	}
	
	function visible( element ) {
		return $.expr.filters.visible( element ) &&
			!$( element ).parents().addBack().filter(function() {
				return $.css( this, "visibility" ) === "hidden";
			}).length;
	}
	
	$.extend( $.expr[ ":" ], {
		data: $.expr.createPseudo ?
			$.expr.createPseudo(function( dataName ) {
				return function( elem ) {
					return !!$.data( elem, dataName );
				};
			}) :
			// support: jQuery <1.8
			function( elem, i, match ) {
				return !!$.data( elem, match[ 3 ] );
			},
	
		focusable: function( element ) {
			return focusable( element, !isNaN( $.attr( element, "tabindex" ) ) );
		},
	
		tabbable: function( element ) {
			var tabIndex = $.attr( element, "tabindex" ),
				isTabIndexNaN = isNaN( tabIndex );
			return ( isTabIndexNaN || tabIndex >= 0 ) && focusable( element, !isTabIndexNaN );
		}
	});
	
	// support: jQuery <1.8
	if ( !$( "<a>" ).outerWidth( 1 ).jquery ) {
		$.each( [ "Width", "Height" ], function( i, name ) {
			var side = name === "Width" ? [ "Left", "Right" ] : [ "Top", "Bottom" ],
				type = name.toLowerCase(),
				orig = {
					innerWidth: $.fn.innerWidth,
					innerHeight: $.fn.innerHeight,
					outerWidth: $.fn.outerWidth,
					outerHeight: $.fn.outerHeight
				};
	
			function reduce( elem, size, border, margin ) {
				$.each( side, function() {
					size -= parseFloat( $.css( elem, "padding" + this ) ) || 0;
					if ( border ) {
						size -= parseFloat( $.css( elem, "border" + this + "Width" ) ) || 0;
					}
					if ( margin ) {
						size -= parseFloat( $.css( elem, "margin" + this ) ) || 0;
					}
				});
				return size;
			}
	
			$.fn[ "inner" + name ] = function( size ) {
				if ( size === undefined ) {
					return orig[ "inner" + name ].call( this );
				}
	
				return this.each(function() {
					$( this ).css( type, reduce( this, size ) + "px" );
				});
			};
	
			$.fn[ "outer" + name] = function( size, margin ) {
				if ( typeof size !== "number" ) {
					return orig[ "outer" + name ].call( this, size );
				}
	
				return this.each(function() {
					$( this).css( type, reduce( this, size, true, margin ) + "px" );
				});
			};
		});
	}
	
	// support: jQuery <1.8
	if ( !$.fn.addBack ) {
		$.fn.addBack = function( selector ) {
			return this.add( selector == null ?
				this.prevObject : this.prevObject.filter( selector )
			);
		};
	}
	
	// support: jQuery 1.6.1, 1.6.2 (http://bugs.jquery.com/ticket/9413)
	if ( $( "<a>" ).data( "a-b", "a" ).removeData( "a-b" ).data( "a-b" ) ) {
		$.fn.removeData = (function( removeData ) {
			return function( key ) {
				if ( arguments.length ) {
					return removeData.call( this, $.camelCase( key ) );
				} else {
					return removeData.call( this );
				}
			};
		})( $.fn.removeData );
	}
	
	// deprecated
	$.ui.ie = !!/msie [\w.]+/.exec( navigator.userAgent.toLowerCase() );
	
	$.fn.extend({
		focus: (function( orig ) {
			return function( delay, fn ) {
				return typeof delay === "number" ?
					this.each(function() {
						var elem = this;
						setTimeout(function() {
							$( elem ).focus();
							if ( fn ) {
								fn.call( elem );
							}
						}, delay );
					}) :
					orig.apply( this, arguments );
			};
		})( $.fn.focus ),
	
		disableSelection: (function() {
			var eventType = "onselectstart" in document.createElement( "div" ) ?
				"selectstart" :
				"mousedown";
	
			return function() {
				return this.bind( eventType + ".ui-disableSelection", function( event ) {
					event.preventDefault();
				});
			};
		})(),
	
		enableSelection: function() {
			return this.unbind( ".ui-disableSelection" );
		},
	
		zIndex: function( zIndex ) {
			if ( zIndex !== undefined ) {
				return this.css( "zIndex", zIndex );
			}
	
			if ( this.length ) {
				var elem = $( this[ 0 ] ), position, value;
				while ( elem.length && elem[ 0 ] !== document ) {
					// Ignore z-index if position is set to a value where z-index is ignored by the browser
					// This makes behavior of this function consistent across browsers
					// WebKit always returns auto if the element is positioned
					position = elem.css( "position" );
					if ( position === "absolute" || position === "relative" || position === "fixed" ) {
						// IE returns 0 when zIndex is not specified
						// other browsers return a string
						// we ignore the case of nested elements with an explicit value of 0
						// <div style="z-index: -10;"><div style="z-index: 0;"></div></div>
						value = parseInt( elem.css( "zIndex" ), 10 );
						if ( !isNaN( value ) && value !== 0 ) {
							return value;
						}
					}
					elem = elem.parent();
				}
			}
	
			return 0;
		}
	});
	
	// $.ui.plugin is deprecated. Use $.widget() extensions instead.
	$.ui.plugin = {
		add: function( module, option, set ) {
			var i,
				proto = $.ui[ module ].prototype;
			for ( i in set ) {
				proto.plugins[ i ] = proto.plugins[ i ] || [];
				proto.plugins[ i ].push( [ option, set[ i ] ] );
			}
		},
		call: function( instance, name, args, allowDisconnected ) {
			var i,
				set = instance.plugins[ name ];
	
			if ( !set ) {
				return;
			}
	
			if ( !allowDisconnected && ( !instance.element[ 0 ].parentNode || instance.element[ 0 ].parentNode.nodeType === 11 ) ) {
				return;
			}
	
			for ( i = 0; i < set.length; i++ ) {
				if ( instance.options[ set[ i ][ 0 ] ] ) {
					set[ i ][ 1 ].apply( instance.element, args );
				}
			}
		}
	};
	
	
	/*!
	 * jQuery UI Widget 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/jQuery.widget/
	 */
	
	
	var widget_uuid = 0,
		widget_slice = Array.prototype.slice;
	
	$.cleanData = (function( orig ) {
		return function( elems ) {
			var events, elem, i;
			for ( i = 0; (elem = elems[i]) != null; i++ ) {
				try {
	
					// Only trigger remove when necessary to save time
					events = $._data( elem, "events" );
					if ( events && events.remove ) {
						$( elem ).triggerHandler( "remove" );
					}
	
				// http://bugs.jquery.com/ticket/8235
				} catch ( e ) {}
			}
			orig( elems );
		};
	})( $.cleanData );
	
	$.widget = function( name, base, prototype ) {
		var fullName, existingConstructor, constructor, basePrototype,
			// proxiedPrototype allows the provided prototype to remain unmodified
			// so that it can be used as a mixin for multiple widgets (#8876)
			proxiedPrototype = {},
			namespace = name.split( "." )[ 0 ];
	
		name = name.split( "." )[ 1 ];
		fullName = namespace + "-" + name;
	
		if ( !prototype ) {
			prototype = base;
			base = $.Widget;
		}
	
		// create selector for plugin
		$.expr[ ":" ][ fullName.toLowerCase() ] = function( elem ) {
			return !!$.data( elem, fullName );
		};
	
		$[ namespace ] = $[ namespace ] || {};
		existingConstructor = $[ namespace ][ name ];
		constructor = $[ namespace ][ name ] = function( options, element ) {
			// allow instantiation without "new" keyword
			if ( !this._createWidget ) {
				return new constructor( options, element );
			}
	
			// allow instantiation without initializing for simple inheritance
			// must use "new" keyword (the code above always passes args)
			if ( arguments.length ) {
				this._createWidget( options, element );
			}
		};
		// extend with the existing constructor to carry over any static properties
		$.extend( constructor, existingConstructor, {
			version: prototype.version,
			// copy the object used to create the prototype in case we need to
			// redefine the widget later
			_proto: $.extend( {}, prototype ),
			// track widgets that inherit from this widget in case this widget is
			// redefined after a widget inherits from it
			_childConstructors: []
		});
	
		basePrototype = new base();
		// we need to make the options hash a property directly on the new instance
		// otherwise we'll modify the options hash on the prototype that we're
		// inheriting from
		basePrototype.options = $.widget.extend( {}, basePrototype.options );
		$.each( prototype, function( prop, value ) {
			if ( !$.isFunction( value ) ) {
				proxiedPrototype[ prop ] = value;
				return;
			}
			proxiedPrototype[ prop ] = (function() {
				var _super = function() {
						return base.prototype[ prop ].apply( this, arguments );
					},
					_superApply = function( args ) {
						return base.prototype[ prop ].apply( this, args );
					};
				return function() {
					var __super = this._super,
						__superApply = this._superApply,
						returnValue;
	
					this._super = _super;
					this._superApply = _superApply;
	
					returnValue = value.apply( this, arguments );
	
					this._super = __super;
					this._superApply = __superApply;
	
					return returnValue;
				};
			})();
		});
		constructor.prototype = $.widget.extend( basePrototype, {
			// TODO: remove support for widgetEventPrefix
			// always use the name + a colon as the prefix, e.g., draggable:start
			// don't prefix for widgets that aren't DOM-based
			widgetEventPrefix: existingConstructor ? (basePrototype.widgetEventPrefix || name) : name
		}, proxiedPrototype, {
			constructor: constructor,
			namespace: namespace,
			widgetName: name,
			widgetFullName: fullName
		});
	
		// If this widget is being redefined then we need to find all widgets that
		// are inheriting from it and redefine all of them so that they inherit from
		// the new version of this widget. We're essentially trying to replace one
		// level in the prototype chain.
		if ( existingConstructor ) {
			$.each( existingConstructor._childConstructors, function( i, child ) {
				var childPrototype = child.prototype;
	
				// redefine the child widget using the same prototype that was
				// originally used, but inherit from the new version of the base
				$.widget( childPrototype.namespace + "." + childPrototype.widgetName, constructor, child._proto );
			});
			// remove the list of existing child constructors from the old constructor
			// so the old child constructors can be garbage collected
			delete existingConstructor._childConstructors;
		} else {
			base._childConstructors.push( constructor );
		}
	
		$.widget.bridge( name, constructor );
	
		return constructor;
	};
	
	$.widget.extend = function( target ) {
		var input = widget_slice.call( arguments, 1 ),
			inputIndex = 0,
			inputLength = input.length,
			key,
			value;
		for ( ; inputIndex < inputLength; inputIndex++ ) {
			for ( key in input[ inputIndex ] ) {
				value = input[ inputIndex ][ key ];
				if ( input[ inputIndex ].hasOwnProperty( key ) && value !== undefined ) {
					// Clone objects
					if ( $.isPlainObject( value ) ) {
						target[ key ] = $.isPlainObject( target[ key ] ) ?
							$.widget.extend( {}, target[ key ], value ) :
							// Don't extend strings, arrays, etc. with objects
							$.widget.extend( {}, value );
					// Copy everything else by reference
					} else {
						target[ key ] = value;
					}
				}
			}
		}
		return target;
	};
	
	$.widget.bridge = function( name, object ) {
		var fullName = object.prototype.widgetFullName || name;
		$.fn[ name ] = function( options ) {
			var isMethodCall = typeof options === "string",
				args = widget_slice.call( arguments, 1 ),
				returnValue = this;
	
			if ( isMethodCall ) {
				this.each(function() {
					var methodValue,
						instance = $.data( this, fullName );
					if ( options === "instance" ) {
						returnValue = instance;
						return false;
					}
					if ( !instance ) {
						return $.error( "cannot call methods on " + name + " prior to initialization; " +
							"attempted to call method '" + options + "'" );
					}
					if ( !$.isFunction( instance[options] ) || options.charAt( 0 ) === "_" ) {
						return $.error( "no such method '" + options + "' for " + name + " widget instance" );
					}
					methodValue = instance[ options ].apply( instance, args );
					if ( methodValue !== instance && methodValue !== undefined ) {
						returnValue = methodValue && methodValue.jquery ?
							returnValue.pushStack( methodValue.get() ) :
							methodValue;
						return false;
					}
				});
			} else {
	
				// Allow multiple hashes to be passed on init
				if ( args.length ) {
					options = $.widget.extend.apply( null, [ options ].concat(args) );
				}
	
				this.each(function() {
					var instance = $.data( this, fullName );
					if ( instance ) {
						instance.option( options || {} );
						if ( instance._init ) {
							instance._init();
						}
					} else {
						$.data( this, fullName, new object( options, this ) );
					}
				});
			}
	
			return returnValue;
		};
	};
	
	$.Widget = function( /* options, element */ ) {};
	$.Widget._childConstructors = [];
	
	$.Widget.prototype = {
		widgetName: "widget",
		widgetEventPrefix: "",
		defaultElement: "<div>",
		options: {
			disabled: false,
	
			// callbacks
			create: null
		},
		_createWidget: function( options, element ) {
			element = $( element || this.defaultElement || this )[ 0 ];
			this.element = $( element );
			this.uuid = widget_uuid++;
			this.eventNamespace = "." + this.widgetName + this.uuid;
	
			this.bindings = $();
			this.hoverable = $();
			this.focusable = $();
	
			if ( element !== this ) {
				$.data( element, this.widgetFullName, this );
				this._on( true, this.element, {
					remove: function( event ) {
						if ( event.target === element ) {
							this.destroy();
						}
					}
				});
				this.document = $( element.style ?
					// element within the document
					element.ownerDocument :
					// element is window or document
					element.document || element );
				this.window = $( this.document[0].defaultView || this.document[0].parentWindow );
			}
	
			this.options = $.widget.extend( {},
				this.options,
				this._getCreateOptions(),
				options );
	
			this._create();
			this._trigger( "create", null, this._getCreateEventData() );
			this._init();
		},
		_getCreateOptions: $.noop,
		_getCreateEventData: $.noop,
		_create: $.noop,
		_init: $.noop,
	
		destroy: function() {
			this._destroy();
			// we can probably remove the unbind calls in 2.0
			// all event bindings should go through this._on()
			this.element
				.unbind( this.eventNamespace )
				.removeData( this.widgetFullName )
				// support: jquery <1.6.3
				// http://bugs.jquery.com/ticket/9413
				.removeData( $.camelCase( this.widgetFullName ) );
			this.widget()
				.unbind( this.eventNamespace )
				.removeAttr( "aria-disabled" )
				.removeClass(
					this.widgetFullName + "-disabled " +
					"ui-state-disabled" );
	
			// clean up events and states
			this.bindings.unbind( this.eventNamespace );
			this.hoverable.removeClass( "ui-state-hover" );
			this.focusable.removeClass( "ui-state-focus" );
		},
		_destroy: $.noop,
	
		widget: function() {
			return this.element;
		},
	
		option: function( key, value ) {
			var options = key,
				parts,
				curOption,
				i;
	
			if ( arguments.length === 0 ) {
				// don't return a reference to the internal hash
				return $.widget.extend( {}, this.options );
			}
	
			if ( typeof key === "string" ) {
				// handle nested keys, e.g., "foo.bar" => { foo: { bar: ___ } }
				options = {};
				parts = key.split( "." );
				key = parts.shift();
				if ( parts.length ) {
					curOption = options[ key ] = $.widget.extend( {}, this.options[ key ] );
					for ( i = 0; i < parts.length - 1; i++ ) {
						curOption[ parts[ i ] ] = curOption[ parts[ i ] ] || {};
						curOption = curOption[ parts[ i ] ];
					}
					key = parts.pop();
					if ( arguments.length === 1 ) {
						return curOption[ key ] === undefined ? null : curOption[ key ];
					}
					curOption[ key ] = value;
				} else {
					if ( arguments.length === 1 ) {
						return this.options[ key ] === undefined ? null : this.options[ key ];
					}
					options[ key ] = value;
				}
			}
	
			this._setOptions( options );
	
			return this;
		},
		_setOptions: function( options ) {
			var key;
	
			for ( key in options ) {
				this._setOption( key, options[ key ] );
			}
	
			return this;
		},
		_setOption: function( key, value ) {
			this.options[ key ] = value;
	
			if ( key === "disabled" ) {
				this.widget()
					.toggleClass( this.widgetFullName + "-disabled", !!value );
	
				// If the widget is becoming disabled, then nothing is interactive
				if ( value ) {
					this.hoverable.removeClass( "ui-state-hover" );
					this.focusable.removeClass( "ui-state-focus" );
				}
			}
	
			return this;
		},
	
		enable: function() {
			return this._setOptions({ disabled: false });
		},
		disable: function() {
			return this._setOptions({ disabled: true });
		},
	
		_on: function( suppressDisabledCheck, element, handlers ) {
			var delegateElement,
				instance = this;
	
			// no suppressDisabledCheck flag, shuffle arguments
			if ( typeof suppressDisabledCheck !== "boolean" ) {
				handlers = element;
				element = suppressDisabledCheck;
				suppressDisabledCheck = false;
			}
	
			// no element argument, shuffle and use this.element
			if ( !handlers ) {
				handlers = element;
				element = this.element;
				delegateElement = this.widget();
			} else {
				element = delegateElement = $( element );
				this.bindings = this.bindings.add( element );
			}
	
			$.each( handlers, function( event, handler ) {
				function handlerProxy() {
					// allow widgets to customize the disabled handling
					// - disabled as an array instead of boolean
					// - disabled class as method for disabling individual parts
					if ( !suppressDisabledCheck &&
							( instance.options.disabled === true ||
								$( this ).hasClass( "ui-state-disabled" ) ) ) {
						return;
					}
					return ( typeof handler === "string" ? instance[ handler ] : handler )
						.apply( instance, arguments );
				}
	
				// copy the guid so direct unbinding works
				if ( typeof handler !== "string" ) {
					handlerProxy.guid = handler.guid =
						handler.guid || handlerProxy.guid || $.guid++;
				}
	
				var match = event.match( /^([\w:-]*)\s*(.*)$/ ),
					eventName = match[1] + instance.eventNamespace,
					selector = match[2];
				if ( selector ) {
					delegateElement.delegate( selector, eventName, handlerProxy );
				} else {
					element.bind( eventName, handlerProxy );
				}
			});
		},
	
		_off: function( element, eventName ) {
			eventName = (eventName || "").split( " " ).join( this.eventNamespace + " " ) +
				this.eventNamespace;
			element.unbind( eventName ).undelegate( eventName );
	
			// Clear the stack to avoid memory leaks (#10056)
			this.bindings = $( this.bindings.not( element ).get() );
			this.focusable = $( this.focusable.not( element ).get() );
			this.hoverable = $( this.hoverable.not( element ).get() );
		},
	
		_delay: function( handler, delay ) {
			function handlerProxy() {
				return ( typeof handler === "string" ? instance[ handler ] : handler )
					.apply( instance, arguments );
			}
			var instance = this;
			return setTimeout( handlerProxy, delay || 0 );
		},
	
		_hoverable: function( element ) {
			this.hoverable = this.hoverable.add( element );
			this._on( element, {
				mouseenter: function( event ) {
					$( event.currentTarget ).addClass( "ui-state-hover" );
				},
				mouseleave: function( event ) {
					$( event.currentTarget ).removeClass( "ui-state-hover" );
				}
			});
		},
	
		_focusable: function( element ) {
			this.focusable = this.focusable.add( element );
			this._on( element, {
				focusin: function( event ) {
					$( event.currentTarget ).addClass( "ui-state-focus" );
				},
				focusout: function( event ) {
					$( event.currentTarget ).removeClass( "ui-state-focus" );
				}
			});
		},
	
		_trigger: function( type, event, data ) {
			var prop, orig,
				callback = this.options[ type ];
	
			data = data || {};
			event = $.Event( event );
			event.type = ( type === this.widgetEventPrefix ?
				type :
				this.widgetEventPrefix + type ).toLowerCase();
			// the original event may come from any element
			// so we need to reset the target on the new event
			event.target = this.element[ 0 ];
	
			// copy original event properties over to the new event
			orig = event.originalEvent;
			if ( orig ) {
				for ( prop in orig ) {
					if ( !( prop in event ) ) {
						event[ prop ] = orig[ prop ];
					}
				}
			}
	
			this.element.trigger( event, data );
			return !( $.isFunction( callback ) &&
				callback.apply( this.element[0], [ event ].concat( data ) ) === false ||
				event.isDefaultPrevented() );
		}
	};
	
	$.each( { show: "fadeIn", hide: "fadeOut" }, function( method, defaultEffect ) {
		$.Widget.prototype[ "_" + method ] = function( element, options, callback ) {
			if ( typeof options === "string" ) {
				options = { effect: options };
			}
			var hasOptions,
				effectName = !options ?
					method :
					options === true || typeof options === "number" ?
						defaultEffect :
						options.effect || defaultEffect;
			options = options || {};
			if ( typeof options === "number" ) {
				options = { duration: options };
			}
			hasOptions = !$.isEmptyObject( options );
			options.complete = callback;
			if ( options.delay ) {
				element.delay( options.delay );
			}
			if ( hasOptions && $.effects && $.effects.effect[ effectName ] ) {
				element[ method ]( options );
			} else if ( effectName !== method && element[ effectName ] ) {
				element[ effectName ]( options.duration, options.easing, callback );
			} else {
				element.queue(function( next ) {
					$( this )[ method ]();
					if ( callback ) {
						callback.call( element[ 0 ] );
					}
					next();
				});
			}
		};
	});
	
	var widget = $.widget;
	
	
	/*!
	 * jQuery UI Mouse 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/mouse/
	 */
	
	
	var mouseHandled = false;
	$( document ).mouseup( function() {
		mouseHandled = false;
	});
	
	var mouse = $.widget("ui.mouse", {
		version: "1.11.4",
		options: {
			cancel: "input,textarea,button,select,option",
			distance: 1,
			delay: 0
		},
		_mouseInit: function() {
			var that = this;
	
			this.element
				.bind("mousedown." + this.widgetName, function(event) {
					return that._mouseDown(event);
				})
				.bind("click." + this.widgetName, function(event) {
					if (true === $.data(event.target, that.widgetName + ".preventClickEvent")) {
						$.removeData(event.target, that.widgetName + ".preventClickEvent");
						event.stopImmediatePropagation();
						return false;
					}
				});
	
			this.started = false;
		},
	
		// TODO: make sure destroying one instance of mouse doesn't mess with
		// other instances of mouse
		_mouseDestroy: function() {
			this.element.unbind("." + this.widgetName);
			if ( this._mouseMoveDelegate ) {
				this.document
					.unbind("mousemove." + this.widgetName, this._mouseMoveDelegate)
					.unbind("mouseup." + this.widgetName, this._mouseUpDelegate);
			}
		},
	
		_mouseDown: function(event) {
			// don't let more than one widget handle mouseStart
			if ( mouseHandled ) {
				return;
			}
	
			this._mouseMoved = false;
	
			// we may have missed mouseup (out of window)
			(this._mouseStarted && this._mouseUp(event));
	
			this._mouseDownEvent = event;
	
			var that = this,
				btnIsLeft = (event.which === 1),
				// event.target.nodeName works around a bug in IE 8 with
				// disabled inputs (#7620)
				elIsCancel = (typeof this.options.cancel === "string" && event.target.nodeName ? $(event.target).closest(this.options.cancel).length : false);
			if (!btnIsLeft || elIsCancel || !this._mouseCapture(event)) {
				return true;
			}
	
			this.mouseDelayMet = !this.options.delay;
			if (!this.mouseDelayMet) {
				this._mouseDelayTimer = setTimeout(function() {
					that.mouseDelayMet = true;
				}, this.options.delay);
			}
	
			if (this._mouseDistanceMet(event) && this._mouseDelayMet(event)) {
				this._mouseStarted = (this._mouseStart(event) !== false);
				if (!this._mouseStarted) {
					event.preventDefault();
					return true;
				}
			}
	
			// Click event may never have fired (Gecko & Opera)
			if (true === $.data(event.target, this.widgetName + ".preventClickEvent")) {
				$.removeData(event.target, this.widgetName + ".preventClickEvent");
			}
	
			// these delegates are required to keep context
			this._mouseMoveDelegate = function(event) {
				return that._mouseMove(event);
			};
			this._mouseUpDelegate = function(event) {
				return that._mouseUp(event);
			};
	
			this.document
				.bind( "mousemove." + this.widgetName, this._mouseMoveDelegate )
				.bind( "mouseup." + this.widgetName, this._mouseUpDelegate );
	
			event.preventDefault();
	
			mouseHandled = true;
			return true;
		},
	
		_mouseMove: function(event) {
			// Only check for mouseups outside the document if you've moved inside the document
			// at least once. This prevents the firing of mouseup in the case of IE<9, which will
			// fire a mousemove event if content is placed under the cursor. See #7778
			// Support: IE <9
			if ( this._mouseMoved ) {
				// IE mouseup check - mouseup happened when mouse was out of window
				if ($.ui.ie && ( !document.documentMode || document.documentMode < 9 ) && !event.button) {
					return this._mouseUp(event);
	
				// Iframe mouseup check - mouseup occurred in another document
				} else if ( !event.which ) {
					return this._mouseUp( event );
				}
			}
	
			if ( event.which || event.button ) {
				this._mouseMoved = true;
			}
	
			if (this._mouseStarted) {
				this._mouseDrag(event);
				return event.preventDefault();
			}
	
			if (this._mouseDistanceMet(event) && this._mouseDelayMet(event)) {
				this._mouseStarted =
					(this._mouseStart(this._mouseDownEvent, event) !== false);
				(this._mouseStarted ? this._mouseDrag(event) : this._mouseUp(event));
			}
	
			return !this._mouseStarted;
		},
	
		_mouseUp: function(event) {
			this.document
				.unbind( "mousemove." + this.widgetName, this._mouseMoveDelegate )
				.unbind( "mouseup." + this.widgetName, this._mouseUpDelegate );
	
			if (this._mouseStarted) {
				this._mouseStarted = false;
	
				if (event.target === this._mouseDownEvent.target) {
					$.data(event.target, this.widgetName + ".preventClickEvent", true);
				}
	
				this._mouseStop(event);
			}
	
			mouseHandled = false;
			return false;
		},
	
		_mouseDistanceMet: function(event) {
			return (Math.max(
					Math.abs(this._mouseDownEvent.pageX - event.pageX),
					Math.abs(this._mouseDownEvent.pageY - event.pageY)
				) >= this.options.distance
			);
		},
	
		_mouseDelayMet: function(/* event */) {
			return this.mouseDelayMet;
		},
	
		// These are placeholder methods, to be overriden by extending plugin
		_mouseStart: function(/* event */) {},
		_mouseDrag: function(/* event */) {},
		_mouseStop: function(/* event */) {},
		_mouseCapture: function(/* event */) { return true; }
	});
	
	
	/*!
	 * jQuery UI Position 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/position/
	 */
	
	(function() {
	
	$.ui = $.ui || {};
	
	var cachedScrollbarWidth, supportsOffsetFractions,
		max = Math.max,
		abs = Math.abs,
		round = Math.round,
		rhorizontal = /left|center|right/,
		rvertical = /top|center|bottom/,
		roffset = /[\+\-]\d+(\.[\d]+)?%?/,
		rposition = /^\w+/,
		rpercent = /%$/,
		_position = $.fn.position;
	
	function getOffsets( offsets, width, height ) {
		return [
			parseFloat( offsets[ 0 ] ) * ( rpercent.test( offsets[ 0 ] ) ? width / 100 : 1 ),
			parseFloat( offsets[ 1 ] ) * ( rpercent.test( offsets[ 1 ] ) ? height / 100 : 1 )
		];
	}
	
	function parseCss( element, property ) {
		return parseInt( $.css( element, property ), 10 ) || 0;
	}
	
	function getDimensions( elem ) {
		var raw = elem[0];
		if ( raw.nodeType === 9 ) {
			return {
				width: elem.width(),
				height: elem.height(),
				offset: { top: 0, left: 0 }
			};
		}
		if ( $.isWindow( raw ) ) {
			return {
				width: elem.width(),
				height: elem.height(),
				offset: { top: elem.scrollTop(), left: elem.scrollLeft() }
			};
		}
		if ( raw.preventDefault ) {
			return {
				width: 0,
				height: 0,
				offset: { top: raw.pageY, left: raw.pageX }
			};
		}
		return {
			width: elem.outerWidth(),
			height: elem.outerHeight(),
			offset: elem.offset()
		};
	}
	
	$.position = {
		scrollbarWidth: function() {
			if ( cachedScrollbarWidth !== undefined ) {
				return cachedScrollbarWidth;
			}
			var w1, w2,
				div = $( "<div style='display:block;position:absolute;width:50px;height:50px;overflow:hidden;'><div style='height:100px;width:auto;'></div></div>" ),
				innerDiv = div.children()[0];
	
			$( "body" ).append( div );
			w1 = innerDiv.offsetWidth;
			div.css( "overflow", "scroll" );
	
			w2 = innerDiv.offsetWidth;
	
			if ( w1 === w2 ) {
				w2 = div[0].clientWidth;
			}
	
			div.remove();
	
			return (cachedScrollbarWidth = w1 - w2);
		},
		getScrollInfo: function( within ) {
			var overflowX = within.isWindow || within.isDocument ? "" :
					within.element.css( "overflow-x" ),
				overflowY = within.isWindow || within.isDocument ? "" :
					within.element.css( "overflow-y" ),
				hasOverflowX = overflowX === "scroll" ||
					( overflowX === "auto" && within.width < within.element[0].scrollWidth ),
				hasOverflowY = overflowY === "scroll" ||
					( overflowY === "auto" && within.height < within.element[0].scrollHeight );
			return {
				width: hasOverflowY ? $.position.scrollbarWidth() : 0,
				height: hasOverflowX ? $.position.scrollbarWidth() : 0
			};
		},
		getWithinInfo: function( element ) {
			var withinElement = $( element || window ),
				isWindow = $.isWindow( withinElement[0] ),
				isDocument = !!withinElement[ 0 ] && withinElement[ 0 ].nodeType === 9;
			return {
				element: withinElement,
				isWindow: isWindow,
				isDocument: isDocument,
				offset: withinElement.offset() || { left: 0, top: 0 },
				scrollLeft: withinElement.scrollLeft(),
				scrollTop: withinElement.scrollTop(),
	
				// support: jQuery 1.6.x
				// jQuery 1.6 doesn't support .outerWidth/Height() on documents or windows
				width: isWindow || isDocument ? withinElement.width() : withinElement.outerWidth(),
				height: isWindow || isDocument ? withinElement.height() : withinElement.outerHeight()
			};
		}
	};
	
	$.fn.position = function( options ) {
		if ( !options || !options.of ) {
			return _position.apply( this, arguments );
		}
	
		// make a copy, we don't want to modify arguments
		options = $.extend( {}, options );
	
		var atOffset, targetWidth, targetHeight, targetOffset, basePosition, dimensions,
			target = $( options.of ),
			within = $.position.getWithinInfo( options.within ),
			scrollInfo = $.position.getScrollInfo( within ),
			collision = ( options.collision || "flip" ).split( " " ),
			offsets = {};
	
		dimensions = getDimensions( target );
		if ( target[0].preventDefault ) {
			// force left top to allow flipping
			options.at = "left top";
		}
		targetWidth = dimensions.width;
		targetHeight = dimensions.height;
		targetOffset = dimensions.offset;
		// clone to reuse original targetOffset later
		basePosition = $.extend( {}, targetOffset );
	
		// force my and at to have valid horizontal and vertical positions
		// if a value is missing or invalid, it will be converted to center
		$.each( [ "my", "at" ], function() {
			var pos = ( options[ this ] || "" ).split( " " ),
				horizontalOffset,
				verticalOffset;
	
			if ( pos.length === 1) {
				pos = rhorizontal.test( pos[ 0 ] ) ?
					pos.concat( [ "center" ] ) :
					rvertical.test( pos[ 0 ] ) ?
						[ "center" ].concat( pos ) :
						[ "center", "center" ];
			}
			pos[ 0 ] = rhorizontal.test( pos[ 0 ] ) ? pos[ 0 ] : "center";
			pos[ 1 ] = rvertical.test( pos[ 1 ] ) ? pos[ 1 ] : "center";
	
			// calculate offsets
			horizontalOffset = roffset.exec( pos[ 0 ] );
			verticalOffset = roffset.exec( pos[ 1 ] );
			offsets[ this ] = [
				horizontalOffset ? horizontalOffset[ 0 ] : 0,
				verticalOffset ? verticalOffset[ 0 ] : 0
			];
	
			// reduce to just the positions without the offsets
			options[ this ] = [
				rposition.exec( pos[ 0 ] )[ 0 ],
				rposition.exec( pos[ 1 ] )[ 0 ]
			];
		});
	
		// normalize collision option
		if ( collision.length === 1 ) {
			collision[ 1 ] = collision[ 0 ];
		}
	
		if ( options.at[ 0 ] === "right" ) {
			basePosition.left += targetWidth;
		} else if ( options.at[ 0 ] === "center" ) {
			basePosition.left += targetWidth / 2;
		}
	
		if ( options.at[ 1 ] === "bottom" ) {
			basePosition.top += targetHeight;
		} else if ( options.at[ 1 ] === "center" ) {
			basePosition.top += targetHeight / 2;
		}
	
		atOffset = getOffsets( offsets.at, targetWidth, targetHeight );
		basePosition.left += atOffset[ 0 ];
		basePosition.top += atOffset[ 1 ];
	
		return this.each(function() {
			var collisionPosition, using,
				elem = $( this ),
				elemWidth = elem.outerWidth(),
				elemHeight = elem.outerHeight(),
				marginLeft = parseCss( this, "marginLeft" ),
				marginTop = parseCss( this, "marginTop" ),
				collisionWidth = elemWidth + marginLeft + parseCss( this, "marginRight" ) + scrollInfo.width,
				collisionHeight = elemHeight + marginTop + parseCss( this, "marginBottom" ) + scrollInfo.height,
				position = $.extend( {}, basePosition ),
				myOffset = getOffsets( offsets.my, elem.outerWidth(), elem.outerHeight() );
	
			if ( options.my[ 0 ] === "right" ) {
				position.left -= elemWidth;
			} else if ( options.my[ 0 ] === "center" ) {
				position.left -= elemWidth / 2;
			}
	
			if ( options.my[ 1 ] === "bottom" ) {
				position.top -= elemHeight;
			} else if ( options.my[ 1 ] === "center" ) {
				position.top -= elemHeight / 2;
			}
	
			position.left += myOffset[ 0 ];
			position.top += myOffset[ 1 ];
	
			// if the browser doesn't support fractions, then round for consistent results
			if ( !supportsOffsetFractions ) {
				position.left = round( position.left );
				position.top = round( position.top );
			}
	
			collisionPosition = {
				marginLeft: marginLeft,
				marginTop: marginTop
			};
	
			$.each( [ "left", "top" ], function( i, dir ) {
				if ( $.ui.position[ collision[ i ] ] ) {
					$.ui.position[ collision[ i ] ][ dir ]( position, {
						targetWidth: targetWidth,
						targetHeight: targetHeight,
						elemWidth: elemWidth,
						elemHeight: elemHeight,
						collisionPosition: collisionPosition,
						collisionWidth: collisionWidth,
						collisionHeight: collisionHeight,
						offset: [ atOffset[ 0 ] + myOffset[ 0 ], atOffset [ 1 ] + myOffset[ 1 ] ],
						my: options.my,
						at: options.at,
						within: within,
						elem: elem
					});
				}
			});
	
			if ( options.using ) {
				// adds feedback as second argument to using callback, if present
				using = function( props ) {
					var left = targetOffset.left - position.left,
						right = left + targetWidth - elemWidth,
						top = targetOffset.top - position.top,
						bottom = top + targetHeight - elemHeight,
						feedback = {
							target: {
								element: target,
								left: targetOffset.left,
								top: targetOffset.top,
								width: targetWidth,
								height: targetHeight
							},
							element: {
								element: elem,
								left: position.left,
								top: position.top,
								width: elemWidth,
								height: elemHeight
							},
							horizontal: right < 0 ? "left" : left > 0 ? "right" : "center",
							vertical: bottom < 0 ? "top" : top > 0 ? "bottom" : "middle"
						};
					if ( targetWidth < elemWidth && abs( left + right ) < targetWidth ) {
						feedback.horizontal = "center";
					}
					if ( targetHeight < elemHeight && abs( top + bottom ) < targetHeight ) {
						feedback.vertical = "middle";
					}
					if ( max( abs( left ), abs( right ) ) > max( abs( top ), abs( bottom ) ) ) {
						feedback.important = "horizontal";
					} else {
						feedback.important = "vertical";
					}
					options.using.call( this, props, feedback );
				};
			}
	
			elem.offset( $.extend( position, { using: using } ) );
		});
	};
	
	$.ui.position = {
		fit: {
			left: function( position, data ) {
				var within = data.within,
					withinOffset = within.isWindow ? within.scrollLeft : within.offset.left,
					outerWidth = within.width,
					collisionPosLeft = position.left - data.collisionPosition.marginLeft,
					overLeft = withinOffset - collisionPosLeft,
					overRight = collisionPosLeft + data.collisionWidth - outerWidth - withinOffset,
					newOverRight;
	
				// element is wider than within
				if ( data.collisionWidth > outerWidth ) {
					// element is initially over the left side of within
					if ( overLeft > 0 && overRight <= 0 ) {
						newOverRight = position.left + overLeft + data.collisionWidth - outerWidth - withinOffset;
						position.left += overLeft - newOverRight;
					// element is initially over right side of within
					} else if ( overRight > 0 && overLeft <= 0 ) {
						position.left = withinOffset;
					// element is initially over both left and right sides of within
					} else {
						if ( overLeft > overRight ) {
							position.left = withinOffset + outerWidth - data.collisionWidth;
						} else {
							position.left = withinOffset;
						}
					}
				// too far left -> align with left edge
				} else if ( overLeft > 0 ) {
					position.left += overLeft;
				// too far right -> align with right edge
				} else if ( overRight > 0 ) {
					position.left -= overRight;
				// adjust based on position and margin
				} else {
					position.left = max( position.left - collisionPosLeft, position.left );
				}
			},
			top: function( position, data ) {
				var within = data.within,
					withinOffset = within.isWindow ? within.scrollTop : within.offset.top,
					outerHeight = data.within.height,
					collisionPosTop = position.top - data.collisionPosition.marginTop,
					overTop = withinOffset - collisionPosTop,
					overBottom = collisionPosTop + data.collisionHeight - outerHeight - withinOffset,
					newOverBottom;
	
				// element is taller than within
				if ( data.collisionHeight > outerHeight ) {
					// element is initially over the top of within
					if ( overTop > 0 && overBottom <= 0 ) {
						newOverBottom = position.top + overTop + data.collisionHeight - outerHeight - withinOffset;
						position.top += overTop - newOverBottom;
					// element is initially over bottom of within
					} else if ( overBottom > 0 && overTop <= 0 ) {
						position.top = withinOffset;
					// element is initially over both top and bottom of within
					} else {
						if ( overTop > overBottom ) {
							position.top = withinOffset + outerHeight - data.collisionHeight;
						} else {
							position.top = withinOffset;
						}
					}
				// too far up -> align with top
				} else if ( overTop > 0 ) {
					position.top += overTop;
				// too far down -> align with bottom edge
				} else if ( overBottom > 0 ) {
					position.top -= overBottom;
				// adjust based on position and margin
				} else {
					position.top = max( position.top - collisionPosTop, position.top );
				}
			}
		},
		flip: {
			left: function( position, data ) {
				var within = data.within,
					withinOffset = within.offset.left + within.scrollLeft,
					outerWidth = within.width,
					offsetLeft = within.isWindow ? within.scrollLeft : within.offset.left,
					collisionPosLeft = position.left - data.collisionPosition.marginLeft,
					overLeft = collisionPosLeft - offsetLeft,
					overRight = collisionPosLeft + data.collisionWidth - outerWidth - offsetLeft,
					myOffset = data.my[ 0 ] === "left" ?
						-data.elemWidth :
						data.my[ 0 ] === "right" ?
							data.elemWidth :
							0,
					atOffset = data.at[ 0 ] === "left" ?
						data.targetWidth :
						data.at[ 0 ] === "right" ?
							-data.targetWidth :
							0,
					offset = -2 * data.offset[ 0 ],
					newOverRight,
					newOverLeft;
	
				if ( overLeft < 0 ) {
					newOverRight = position.left + myOffset + atOffset + offset + data.collisionWidth - outerWidth - withinOffset;
					if ( newOverRight < 0 || newOverRight < abs( overLeft ) ) {
						position.left += myOffset + atOffset + offset;
					}
				} else if ( overRight > 0 ) {
					newOverLeft = position.left - data.collisionPosition.marginLeft + myOffset + atOffset + offset - offsetLeft;
					if ( newOverLeft > 0 || abs( newOverLeft ) < overRight ) {
						position.left += myOffset + atOffset + offset;
					}
				}
			},
			top: function( position, data ) {
				var within = data.within,
					withinOffset = within.offset.top + within.scrollTop,
					outerHeight = within.height,
					offsetTop = within.isWindow ? within.scrollTop : within.offset.top,
					collisionPosTop = position.top - data.collisionPosition.marginTop,
					overTop = collisionPosTop - offsetTop,
					overBottom = collisionPosTop + data.collisionHeight - outerHeight - offsetTop,
					top = data.my[ 1 ] === "top",
					myOffset = top ?
						-data.elemHeight :
						data.my[ 1 ] === "bottom" ?
							data.elemHeight :
							0,
					atOffset = data.at[ 1 ] === "top" ?
						data.targetHeight :
						data.at[ 1 ] === "bottom" ?
							-data.targetHeight :
							0,
					offset = -2 * data.offset[ 1 ],
					newOverTop,
					newOverBottom;
				if ( overTop < 0 ) {
					newOverBottom = position.top + myOffset + atOffset + offset + data.collisionHeight - outerHeight - withinOffset;
					if ( newOverBottom < 0 || newOverBottom < abs( overTop ) ) {
						position.top += myOffset + atOffset + offset;
					}
				} else if ( overBottom > 0 ) {
					newOverTop = position.top - data.collisionPosition.marginTop + myOffset + atOffset + offset - offsetTop;
					if ( newOverTop > 0 || abs( newOverTop ) < overBottom ) {
						position.top += myOffset + atOffset + offset;
					}
				}
			}
		},
		flipfit: {
			left: function() {
				$.ui.position.flip.left.apply( this, arguments );
				$.ui.position.fit.left.apply( this, arguments );
			},
			top: function() {
				$.ui.position.flip.top.apply( this, arguments );
				$.ui.position.fit.top.apply( this, arguments );
			}
		}
	};
	
	// fraction support test
	(function() {
		var testElement, testElementParent, testElementStyle, offsetLeft, i,
			body = document.getElementsByTagName( "body" )[ 0 ],
			div = document.createElement( "div" );
	
		//Create a "fake body" for testing based on method used in jQuery.support
		testElement = document.createElement( body ? "div" : "body" );
		testElementStyle = {
			visibility: "hidden",
			width: 0,
			height: 0,
			border: 0,
			margin: 0,
			background: "none"
		};
		if ( body ) {
			$.extend( testElementStyle, {
				position: "absolute",
				left: "-1000px",
				top: "-1000px"
			});
		}
		for ( i in testElementStyle ) {
			testElement.style[ i ] = testElementStyle[ i ];
		}
		testElement.appendChild( div );
		testElementParent = body || document.documentElement;
		testElementParent.insertBefore( testElement, testElementParent.firstChild );
	
		div.style.cssText = "position: absolute; left: 10.7432222px;";
	
		offsetLeft = $( div ).offset().left;
		supportsOffsetFractions = offsetLeft > 10 && offsetLeft < 11;
	
		testElement.innerHTML = "";
		testElementParent.removeChild( testElement );
	})();
	
	})();
	
	var position = $.ui.position;
	
	
	/*!
	 * jQuery UI Accordion 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/accordion/
	 */
	
	
	var accordion = $.widget( "ui.accordion", {
		version: "1.11.4",
		options: {
			active: 0,
			animate: {},
			collapsible: false,
			event: "click",
			header: "> li > :first-child,> :not(li):even",
			heightStyle: "auto",
			icons: {
				activeHeader: "ui-icon-triangle-1-s",
				header: "ui-icon-triangle-1-e"
			},
	
			// callbacks
			activate: null,
			beforeActivate: null
		},
	
		hideProps: {
			borderTopWidth: "hide",
			borderBottomWidth: "hide",
			paddingTop: "hide",
			paddingBottom: "hide",
			height: "hide"
		},
	
		showProps: {
			borderTopWidth: "show",
			borderBottomWidth: "show",
			paddingTop: "show",
			paddingBottom: "show",
			height: "show"
		},
	
		_create: function() {
			var options = this.options;
			this.prevShow = this.prevHide = $();
			this.element.addClass( "ui-accordion ui-widget ui-helper-reset" )
				// ARIA
				.attr( "role", "tablist" );
	
			// don't allow collapsible: false and active: false / null
			if ( !options.collapsible && (options.active === false || options.active == null) ) {
				options.active = 0;
			}
	
			this._processPanels();
			// handle negative values
			if ( options.active < 0 ) {
				options.active += this.headers.length;
			}
			this._refresh();
		},
	
		_getCreateEventData: function() {
			return {
				header: this.active,
				panel: !this.active.length ? $() : this.active.next()
			};
		},
	
		_createIcons: function() {
			var icons = this.options.icons;
			if ( icons ) {
				$( "<span>" )
					.addClass( "ui-accordion-header-icon ui-icon " + icons.header )
					.prependTo( this.headers );
				this.active.children( ".ui-accordion-header-icon" )
					.removeClass( icons.header )
					.addClass( icons.activeHeader );
				this.headers.addClass( "ui-accordion-icons" );
			}
		},
	
		_destroyIcons: function() {
			this.headers
				.removeClass( "ui-accordion-icons" )
				.children( ".ui-accordion-header-icon" )
					.remove();
		},
	
		_destroy: function() {
			var contents;
	
			// clean up main element
			this.element
				.removeClass( "ui-accordion ui-widget ui-helper-reset" )
				.removeAttr( "role" );
	
			// clean up headers
			this.headers
				.removeClass( "ui-accordion-header ui-accordion-header-active ui-state-default " +
					"ui-corner-all ui-state-active ui-state-disabled ui-corner-top" )
				.removeAttr( "role" )
				.removeAttr( "aria-expanded" )
				.removeAttr( "aria-selected" )
				.removeAttr( "aria-controls" )
				.removeAttr( "tabIndex" )
				.removeUniqueId();
	
			this._destroyIcons();
	
			// clean up content panels
			contents = this.headers.next()
				.removeClass( "ui-helper-reset ui-widget-content ui-corner-bottom " +
					"ui-accordion-content ui-accordion-content-active ui-state-disabled" )
				.css( "display", "" )
				.removeAttr( "role" )
				.removeAttr( "aria-hidden" )
				.removeAttr( "aria-labelledby" )
				.removeUniqueId();
	
			if ( this.options.heightStyle !== "content" ) {
				contents.css( "height", "" );
			}
		},
	
		_setOption: function( key, value ) {
			if ( key === "active" ) {
				// _activate() will handle invalid values and update this.options
				this._activate( value );
				return;
			}
	
			if ( key === "event" ) {
				if ( this.options.event ) {
					this._off( this.headers, this.options.event );
				}
				this._setupEvents( value );
			}
	
			this._super( key, value );
	
			// setting collapsible: false while collapsed; open first panel
			if ( key === "collapsible" && !value && this.options.active === false ) {
				this._activate( 0 );
			}
	
			if ( key === "icons" ) {
				this._destroyIcons();
				if ( value ) {
					this._createIcons();
				}
			}
	
			// #5332 - opacity doesn't cascade to positioned elements in IE
			// so we need to add the disabled class to the headers and panels
			if ( key === "disabled" ) {
				this.element
					.toggleClass( "ui-state-disabled", !!value )
					.attr( "aria-disabled", value );
				this.headers.add( this.headers.next() )
					.toggleClass( "ui-state-disabled", !!value );
			}
		},
	
		_keydown: function( event ) {
			if ( event.altKey || event.ctrlKey ) {
				return;
			}
	
			var keyCode = $.ui.keyCode,
				length = this.headers.length,
				currentIndex = this.headers.index( event.target ),
				toFocus = false;
	
			switch ( event.keyCode ) {
				case keyCode.RIGHT:
				case keyCode.DOWN:
					toFocus = this.headers[ ( currentIndex + 1 ) % length ];
					break;
				case keyCode.LEFT:
				case keyCode.UP:
					toFocus = this.headers[ ( currentIndex - 1 + length ) % length ];
					break;
				case keyCode.SPACE:
				case keyCode.ENTER:
					this._eventHandler( event );
					break;
				case keyCode.HOME:
					toFocus = this.headers[ 0 ];
					break;
				case keyCode.END:
					toFocus = this.headers[ length - 1 ];
					break;
			}
	
			if ( toFocus ) {
				$( event.target ).attr( "tabIndex", -1 );
				$( toFocus ).attr( "tabIndex", 0 );
				toFocus.focus();
				event.preventDefault();
			}
		},
	
		_panelKeyDown: function( event ) {
			if ( event.keyCode === $.ui.keyCode.UP && event.ctrlKey ) {
				$( event.currentTarget ).prev().focus();
			}
		},
	
		refresh: function() {
			var options = this.options;
			this._processPanels();
	
			// was collapsed or no panel
			if ( ( options.active === false && options.collapsible === true ) || !this.headers.length ) {
				options.active = false;
				this.active = $();
			// active false only when collapsible is true
			} else if ( options.active === false ) {
				this._activate( 0 );
			// was active, but active panel is gone
			} else if ( this.active.length && !$.contains( this.element[ 0 ], this.active[ 0 ] ) ) {
				// all remaining panel are disabled
				if ( this.headers.length === this.headers.find(".ui-state-disabled").length ) {
					options.active = false;
					this.active = $();
				// activate previous panel
				} else {
					this._activate( Math.max( 0, options.active - 1 ) );
				}
			// was active, active panel still exists
			} else {
				// make sure active index is correct
				options.active = this.headers.index( this.active );
			}
	
			this._destroyIcons();
	
			this._refresh();
		},
	
		_processPanels: function() {
			var prevHeaders = this.headers,
				prevPanels = this.panels;
	
			this.headers = this.element.find( this.options.header )
				.addClass( "ui-accordion-header ui-state-default ui-corner-all" );
	
			this.panels = this.headers.next()
				.addClass( "ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" )
				.filter( ":not(.ui-accordion-content-active)" )
				.hide();
	
			// Avoid memory leaks (#10056)
			if ( prevPanels ) {
				this._off( prevHeaders.not( this.headers ) );
				this._off( prevPanels.not( this.panels ) );
			}
		},
	
		_refresh: function() {
			var maxHeight,
				options = this.options,
				heightStyle = options.heightStyle,
				parent = this.element.parent();
	
			this.active = this._findActive( options.active )
				.addClass( "ui-accordion-header-active ui-state-active ui-corner-top" )
				.removeClass( "ui-corner-all" );
			this.active.next()
				.addClass( "ui-accordion-content-active" )
				.show();
	
			this.headers
				.attr( "role", "tab" )
				.each(function() {
					var header = $( this ),
						headerId = header.uniqueId().attr( "id" ),
						panel = header.next(),
						panelId = panel.uniqueId().attr( "id" );
					header.attr( "aria-controls", panelId );
					panel.attr( "aria-labelledby", headerId );
				})
				.next()
					.attr( "role", "tabpanel" );
	
			this.headers
				.not( this.active )
				.attr({
					"aria-selected": "false",
					"aria-expanded": "false",
					tabIndex: -1
				})
				.next()
					.attr({
						"aria-hidden": "true"
					})
					.hide();
	
			// make sure at least one header is in the tab order
			if ( !this.active.length ) {
				this.headers.eq( 0 ).attr( "tabIndex", 0 );
			} else {
				this.active.attr({
					"aria-selected": "true",
					"aria-expanded": "true",
					tabIndex: 0
				})
				.next()
					.attr({
						"aria-hidden": "false"
					});
			}
	
			this._createIcons();
	
			this._setupEvents( options.event );
	
			if ( heightStyle === "fill" ) {
				maxHeight = parent.height();
				this.element.siblings( ":visible" ).each(function() {
					var elem = $( this ),
						position = elem.css( "position" );
	
					if ( position === "absolute" || position === "fixed" ) {
						return;
					}
					maxHeight -= elem.outerHeight( true );
				});
	
				this.headers.each(function() {
					maxHeight -= $( this ).outerHeight( true );
				});
	
				this.headers.next()
					.each(function() {
						$( this ).height( Math.max( 0, maxHeight -
							$( this ).innerHeight() + $( this ).height() ) );
					})
					.css( "overflow", "auto" );
			} else if ( heightStyle === "auto" ) {
				maxHeight = 0;
				this.headers.next()
					.each(function() {
						maxHeight = Math.max( maxHeight, $( this ).css( "height", "" ).height() );
					})
					.height( maxHeight );
			}
		},
	
		_activate: function( index ) {
			var active = this._findActive( index )[ 0 ];
	
			// trying to activate the already active panel
			if ( active === this.active[ 0 ] ) {
				return;
			}
	
			// trying to collapse, simulate a click on the currently active header
			active = active || this.active[ 0 ];
	
			this._eventHandler({
				target: active,
				currentTarget: active,
				preventDefault: $.noop
			});
		},
	
		_findActive: function( selector ) {
			return typeof selector === "number" ? this.headers.eq( selector ) : $();
		},
	
		_setupEvents: function( event ) {
			var events = {
				keydown: "_keydown"
			};
			if ( event ) {
				$.each( event.split( " " ), function( index, eventName ) {
					events[ eventName ] = "_eventHandler";
				});
			}
	
			this._off( this.headers.add( this.headers.next() ) );
			this._on( this.headers, events );
			this._on( this.headers.next(), { keydown: "_panelKeyDown" });
			this._hoverable( this.headers );
			this._focusable( this.headers );
		},
	
		_eventHandler: function( event ) {
			var options = this.options,
				active = this.active,
				clicked = $( event.currentTarget ),
				clickedIsActive = clicked[ 0 ] === active[ 0 ],
				collapsing = clickedIsActive && options.collapsible,
				toShow = collapsing ? $() : clicked.next(),
				toHide = active.next(),
				eventData = {
					oldHeader: active,
					oldPanel: toHide,
					newHeader: collapsing ? $() : clicked,
					newPanel: toShow
				};
	
			event.preventDefault();
	
			if (
					// click on active header, but not collapsible
					( clickedIsActive && !options.collapsible ) ||
					// allow canceling activation
					( this._trigger( "beforeActivate", event, eventData ) === false ) ) {
				return;
			}
	
			options.active = collapsing ? false : this.headers.index( clicked );
	
			// when the call to ._toggle() comes after the class changes
			// it causes a very odd bug in IE 8 (see #6720)
			this.active = clickedIsActive ? $() : clicked;
			this._toggle( eventData );
	
			// switch classes
			// corner classes on the previously active header stay after the animation
			active.removeClass( "ui-accordion-header-active ui-state-active" );
			if ( options.icons ) {
				active.children( ".ui-accordion-header-icon" )
					.removeClass( options.icons.activeHeader )
					.addClass( options.icons.header );
			}
	
			if ( !clickedIsActive ) {
				clicked
					.removeClass( "ui-corner-all" )
					.addClass( "ui-accordion-header-active ui-state-active ui-corner-top" );
				if ( options.icons ) {
					clicked.children( ".ui-accordion-header-icon" )
						.removeClass( options.icons.header )
						.addClass( options.icons.activeHeader );
				}
	
				clicked
					.next()
					.addClass( "ui-accordion-content-active" );
			}
		},
	
		_toggle: function( data ) {
			var toShow = data.newPanel,
				toHide = this.prevShow.length ? this.prevShow : data.oldPanel;
	
			// handle activating a panel during the animation for another activation
			this.prevShow.add( this.prevHide ).stop( true, true );
			this.prevShow = toShow;
			this.prevHide = toHide;
	
			if ( this.options.animate ) {
				this._animate( toShow, toHide, data );
			} else {
				toHide.hide();
				toShow.show();
				this._toggleComplete( data );
			}
	
			toHide.attr({
				"aria-hidden": "true"
			});
			toHide.prev().attr({
				"aria-selected": "false",
				"aria-expanded": "false"
			});
			// if we're switching panels, remove the old header from the tab order
			// if we're opening from collapsed state, remove the previous header from the tab order
			// if we're collapsing, then keep the collapsing header in the tab order
			if ( toShow.length && toHide.length ) {
				toHide.prev().attr({
					"tabIndex": -1,
					"aria-expanded": "false"
				});
			} else if ( toShow.length ) {
				this.headers.filter(function() {
					return parseInt( $( this ).attr( "tabIndex" ), 10 ) === 0;
				})
				.attr( "tabIndex", -1 );
			}
	
			toShow
				.attr( "aria-hidden", "false" )
				.prev()
					.attr({
						"aria-selected": "true",
						"aria-expanded": "true",
						tabIndex: 0
					});
		},
	
		_animate: function( toShow, toHide, data ) {
			var total, easing, duration,
				that = this,
				adjust = 0,
				boxSizing = toShow.css( "box-sizing" ),
				down = toShow.length &&
					( !toHide.length || ( toShow.index() < toHide.index() ) ),
				animate = this.options.animate || {},
				options = down && animate.down || animate,
				complete = function() {
					that._toggleComplete( data );
				};
	
			if ( typeof options === "number" ) {
				duration = options;
			}
			if ( typeof options === "string" ) {
				easing = options;
			}
			// fall back from options to animation in case of partial down settings
			easing = easing || options.easing || animate.easing;
			duration = duration || options.duration || animate.duration;
	
			if ( !toHide.length ) {
				return toShow.animate( this.showProps, duration, easing, complete );
			}
			if ( !toShow.length ) {
				return toHide.animate( this.hideProps, duration, easing, complete );
			}
	
			total = toShow.show().outerHeight();
			toHide.animate( this.hideProps, {
				duration: duration,
				easing: easing,
				step: function( now, fx ) {
					fx.now = Math.round( now );
				}
			});
			toShow
				.hide()
				.animate( this.showProps, {
					duration: duration,
					easing: easing,
					complete: complete,
					step: function( now, fx ) {
						fx.now = Math.round( now );
						if ( fx.prop !== "height" ) {
							if ( boxSizing === "content-box" ) {
								adjust += fx.now;
							}
						} else if ( that.options.heightStyle !== "content" ) {
							fx.now = Math.round( total - toHide.outerHeight() - adjust );
							adjust = 0;
						}
					}
				});
		},
	
		_toggleComplete: function( data ) {
			var toHide = data.oldPanel;
	
			toHide
				.removeClass( "ui-accordion-content-active" )
				.prev()
					.removeClass( "ui-corner-top" )
					.addClass( "ui-corner-all" );
	
			// Work around for rendering bug in IE (#5421)
			if ( toHide.length ) {
				toHide.parent()[ 0 ].className = toHide.parent()[ 0 ].className;
			}
			this._trigger( "activate", null, data );
		}
	});
	
	
	/*!
	 * jQuery UI Menu 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/menu/
	 */
	
	
	var menu = $.widget( "ui.menu", {
		version: "1.11.4",
		defaultElement: "<ul>",
		delay: 300,
		options: {
			icons: {
				submenu: "ui-icon-carat-1-e"
			},
			items: "> *",
			menus: "ul",
			position: {
				my: "left-1 top",
				at: "right top"
			},
			role: "menu",
	
			// callbacks
			blur: null,
			focus: null,
			select: null
		},
	
		_create: function() {
			this.activeMenu = this.element;
	
			// Flag used to prevent firing of the click handler
			// as the event bubbles up through nested menus
			this.mouseHandled = false;
			this.element
				.uniqueId()
				.addClass( "ui-menu ui-widget ui-widget-content" )
				.toggleClass( "ui-menu-icons", !!this.element.find( ".ui-icon" ).length )
				.attr({
					role: this.options.role,
					tabIndex: 0
				});
	
			if ( this.options.disabled ) {
				this.element
					.addClass( "ui-state-disabled" )
					.attr( "aria-disabled", "true" );
			}
	
			this._on({
				// Prevent focus from sticking to links inside menu after clicking
				// them (focus should always stay on UL during navigation).
				"mousedown .ui-menu-item": function( event ) {
					event.preventDefault();
				},
				"click .ui-menu-item": function( event ) {
					var target = $( event.target );
					if ( !this.mouseHandled && target.not( ".ui-state-disabled" ).length ) {
						this.select( event );
	
						// Only set the mouseHandled flag if the event will bubble, see #9469.
						if ( !event.isPropagationStopped() ) {
							this.mouseHandled = true;
						}
	
						// Open submenu on click
						if ( target.has( ".ui-menu" ).length ) {
							this.expand( event );
						} else if ( !this.element.is( ":focus" ) && $( this.document[ 0 ].activeElement ).closest( ".ui-menu" ).length ) {
	
							// Redirect focus to the menu
							this.element.trigger( "focus", [ true ] );
	
							// If the active item is on the top level, let it stay active.
							// Otherwise, blur the active item since it is no longer visible.
							if ( this.active && this.active.parents( ".ui-menu" ).length === 1 ) {
								clearTimeout( this.timer );
							}
						}
					}
				},
				"mouseenter .ui-menu-item": function( event ) {
					// Ignore mouse events while typeahead is active, see #10458.
					// Prevents focusing the wrong item when typeahead causes a scroll while the mouse
					// is over an item in the menu
					if ( this.previousFilter ) {
						return;
					}
					var target = $( event.currentTarget );
					// Remove ui-state-active class from siblings of the newly focused menu item
					// to avoid a jump caused by adjacent elements both having a class with a border
					target.siblings( ".ui-state-active" ).removeClass( "ui-state-active" );
					this.focus( event, target );
				},
				mouseleave: "collapseAll",
				"mouseleave .ui-menu": "collapseAll",
				focus: function( event, keepActiveItem ) {
					// If there's already an active item, keep it active
					// If not, activate the first item
					var item = this.active || this.element.find( this.options.items ).eq( 0 );
	
					if ( !keepActiveItem ) {
						this.focus( event, item );
					}
				},
				blur: function( event ) {
					this._delay(function() {
						if ( !$.contains( this.element[0], this.document[0].activeElement ) ) {
							this.collapseAll( event );
						}
					});
				},
				keydown: "_keydown"
			});
	
			this.refresh();
	
			// Clicks outside of a menu collapse any open menus
			this._on( this.document, {
				click: function( event ) {
					if ( this._closeOnDocumentClick( event ) ) {
						this.collapseAll( event );
					}
	
					// Reset the mouseHandled flag
					this.mouseHandled = false;
				}
			});
		},
	
		_destroy: function() {
			// Destroy (sub)menus
			this.element
				.removeAttr( "aria-activedescendant" )
				.find( ".ui-menu" ).addBack()
					.removeClass( "ui-menu ui-widget ui-widget-content ui-menu-icons ui-front" )
					.removeAttr( "role" )
					.removeAttr( "tabIndex" )
					.removeAttr( "aria-labelledby" )
					.removeAttr( "aria-expanded" )
					.removeAttr( "aria-hidden" )
					.removeAttr( "aria-disabled" )
					.removeUniqueId()
					.show();
	
			// Destroy menu items
			this.element.find( ".ui-menu-item" )
				.removeClass( "ui-menu-item" )
				.removeAttr( "role" )
				.removeAttr( "aria-disabled" )
				.removeUniqueId()
				.removeClass( "ui-state-hover" )
				.removeAttr( "tabIndex" )
				.removeAttr( "role" )
				.removeAttr( "aria-haspopup" )
				.children().each( function() {
					var elem = $( this );
					if ( elem.data( "ui-menu-submenu-carat" ) ) {
						elem.remove();
					}
				});
	
			// Destroy menu dividers
			this.element.find( ".ui-menu-divider" ).removeClass( "ui-menu-divider ui-widget-content" );
		},
	
		_keydown: function( event ) {
			var match, prev, character, skip,
				preventDefault = true;
	
			switch ( event.keyCode ) {
			case $.ui.keyCode.PAGE_UP:
				this.previousPage( event );
				break;
			case $.ui.keyCode.PAGE_DOWN:
				this.nextPage( event );
				break;
			case $.ui.keyCode.HOME:
				this._move( "first", "first", event );
				break;
			case $.ui.keyCode.END:
				this._move( "last", "last", event );
				break;
			case $.ui.keyCode.UP:
				this.previous( event );
				break;
			case $.ui.keyCode.DOWN:
				this.next( event );
				break;
			case $.ui.keyCode.LEFT:
				this.collapse( event );
				break;
			case $.ui.keyCode.RIGHT:
				if ( this.active && !this.active.is( ".ui-state-disabled" ) ) {
					this.expand( event );
				}
				break;
			case $.ui.keyCode.ENTER:
			case $.ui.keyCode.SPACE:
				this._activate( event );
				break;
			case $.ui.keyCode.ESCAPE:
				this.collapse( event );
				break;
			default:
				preventDefault = false;
				prev = this.previousFilter || "";
				character = String.fromCharCode( event.keyCode );
				skip = false;
	
				clearTimeout( this.filterTimer );
	
				if ( character === prev ) {
					skip = true;
				} else {
					character = prev + character;
				}
	
				match = this._filterMenuItems( character );
				match = skip && match.index( this.active.next() ) !== -1 ?
					this.active.nextAll( ".ui-menu-item" ) :
					match;
	
				// If no matches on the current filter, reset to the last character pressed
				// to move down the menu to the first item that starts with that character
				if ( !match.length ) {
					character = String.fromCharCode( event.keyCode );
					match = this._filterMenuItems( character );
				}
	
				if ( match.length ) {
					this.focus( event, match );
					this.previousFilter = character;
					this.filterTimer = this._delay(function() {
						delete this.previousFilter;
					}, 1000 );
				} else {
					delete this.previousFilter;
				}
			}
	
			if ( preventDefault ) {
				event.preventDefault();
			}
		},
	
		_activate: function( event ) {
			if ( !this.active.is( ".ui-state-disabled" ) ) {
				if ( this.active.is( "[aria-haspopup='true']" ) ) {
					this.expand( event );
				} else {
					this.select( event );
				}
			}
		},
	
		refresh: function() {
			var menus, items,
				that = this,
				icon = this.options.icons.submenu,
				submenus = this.element.find( this.options.menus );
	
			this.element.toggleClass( "ui-menu-icons", !!this.element.find( ".ui-icon" ).length );
	
			// Initialize nested menus
			submenus.filter( ":not(.ui-menu)" )
				.addClass( "ui-menu ui-widget ui-widget-content ui-front" )
				.hide()
				.attr({
					role: this.options.role,
					"aria-hidden": "true",
					"aria-expanded": "false"
				})
				.each(function() {
					var menu = $( this ),
						item = menu.parent(),
						submenuCarat = $( "<span>" )
							.addClass( "ui-menu-icon ui-icon " + icon )
							.data( "ui-menu-submenu-carat", true );
	
					item
						.attr( "aria-haspopup", "true" )
						.prepend( submenuCarat );
					menu.attr( "aria-labelledby", item.attr( "id" ) );
				});
	
			menus = submenus.add( this.element );
			items = menus.find( this.options.items );
	
			// Initialize menu-items containing spaces and/or dashes only as dividers
			items.not( ".ui-menu-item" ).each(function() {
				var item = $( this );
				if ( that._isDivider( item ) ) {
					item.addClass( "ui-widget-content ui-menu-divider" );
				}
			});
	
			// Don't refresh list items that are already adapted
			items.not( ".ui-menu-item, .ui-menu-divider" )
				.addClass( "ui-menu-item" )
				.uniqueId()
				.attr({
					tabIndex: -1,
					role: this._itemRole()
				});
	
			// Add aria-disabled attribute to any disabled menu item
			items.filter( ".ui-state-disabled" ).attr( "aria-disabled", "true" );
	
			// If the active item has been removed, blur the menu
			if ( this.active && !$.contains( this.element[ 0 ], this.active[ 0 ] ) ) {
				this.blur();
			}
		},
	
		_itemRole: function() {
			return {
				menu: "menuitem",
				listbox: "option"
			}[ this.options.role ];
		},
	
		_setOption: function( key, value ) {
			if ( key === "icons" ) {
				this.element.find( ".ui-menu-icon" )
					.removeClass( this.options.icons.submenu )
					.addClass( value.submenu );
			}
			if ( key === "disabled" ) {
				this.element
					.toggleClass( "ui-state-disabled", !!value )
					.attr( "aria-disabled", value );
			}
			this._super( key, value );
		},
	
		focus: function( event, item ) {
			var nested, focused;
			this.blur( event, event && event.type === "focus" );
	
			this._scrollIntoView( item );
	
			this.active = item.first();
			focused = this.active.addClass( "ui-state-focus" ).removeClass( "ui-state-active" );
			// Only update aria-activedescendant if there's a role
			// otherwise we assume focus is managed elsewhere
			if ( this.options.role ) {
				this.element.attr( "aria-activedescendant", focused.attr( "id" ) );
			}
	
			// Highlight active parent menu item, if any
			this.active
				.parent()
				.closest( ".ui-menu-item" )
				.addClass( "ui-state-active" );
	
			if ( event && event.type === "keydown" ) {
				this._close();
			} else {
				this.timer = this._delay(function() {
					this._close();
				}, this.delay );
			}
	
			nested = item.children( ".ui-menu" );
			if ( nested.length && event && ( /^mouse/.test( event.type ) ) ) {
				this._startOpening(nested);
			}
			this.activeMenu = item.parent();
	
			this._trigger( "focus", event, { item: item } );
		},
	
		_scrollIntoView: function( item ) {
			var borderTop, paddingTop, offset, scroll, elementHeight, itemHeight;
			if ( this._hasScroll() ) {
				borderTop = parseFloat( $.css( this.activeMenu[0], "borderTopWidth" ) ) || 0;
				paddingTop = parseFloat( $.css( this.activeMenu[0], "paddingTop" ) ) || 0;
				offset = item.offset().top - this.activeMenu.offset().top - borderTop - paddingTop;
				scroll = this.activeMenu.scrollTop();
				elementHeight = this.activeMenu.height();
				itemHeight = item.outerHeight();
	
				if ( offset < 0 ) {
					this.activeMenu.scrollTop( scroll + offset );
				} else if ( offset + itemHeight > elementHeight ) {
					this.activeMenu.scrollTop( scroll + offset - elementHeight + itemHeight );
				}
			}
		},
	
		blur: function( event, fromFocus ) {
			if ( !fromFocus ) {
				clearTimeout( this.timer );
			}
	
			if ( !this.active ) {
				return;
			}
	
			this.active.removeClass( "ui-state-focus" );
			this.active = null;
	
			this._trigger( "blur", event, { item: this.active } );
		},
	
		_startOpening: function( submenu ) {
			clearTimeout( this.timer );
	
			// Don't open if already open fixes a Firefox bug that caused a .5 pixel
			// shift in the submenu position when mousing over the carat icon
			if ( submenu.attr( "aria-hidden" ) !== "true" ) {
				return;
			}
	
			this.timer = this._delay(function() {
				this._close();
				this._open( submenu );
			}, this.delay );
		},
	
		_open: function( submenu ) {
			var position = $.extend({
				of: this.active
			}, this.options.position );
	
			clearTimeout( this.timer );
			this.element.find( ".ui-menu" ).not( submenu.parents( ".ui-menu" ) )
				.hide()
				.attr( "aria-hidden", "true" );
	
			submenu
				.show()
				.removeAttr( "aria-hidden" )
				.attr( "aria-expanded", "true" )
				.position( position );
		},
	
		collapseAll: function( event, all ) {
			clearTimeout( this.timer );
			this.timer = this._delay(function() {
				// If we were passed an event, look for the submenu that contains the event
				var currentMenu = all ? this.element :
					$( event && event.target ).closest( this.element.find( ".ui-menu" ) );
	
				// If we found no valid submenu ancestor, use the main menu to close all sub menus anyway
				if ( !currentMenu.length ) {
					currentMenu = this.element;
				}
	
				this._close( currentMenu );
	
				this.blur( event );
				this.activeMenu = currentMenu;
			}, this.delay );
		},
	
		// With no arguments, closes the currently active menu - if nothing is active
		// it closes all menus.  If passed an argument, it will search for menus BELOW
		_close: function( startMenu ) {
			if ( !startMenu ) {
				startMenu = this.active ? this.active.parent() : this.element;
			}
	
			startMenu
				.find( ".ui-menu" )
					.hide()
					.attr( "aria-hidden", "true" )
					.attr( "aria-expanded", "false" )
				.end()
				.find( ".ui-state-active" ).not( ".ui-state-focus" )
					.removeClass( "ui-state-active" );
		},
	
		_closeOnDocumentClick: function( event ) {
			return !$( event.target ).closest( ".ui-menu" ).length;
		},
	
		_isDivider: function( item ) {
	
			// Match hyphen, em dash, en dash
			return !/[^\-\u2014\u2013\s]/.test( item.text() );
		},
	
		collapse: function( event ) {
			var newItem = this.active &&
				this.active.parent().closest( ".ui-menu-item", this.element );
			if ( newItem && newItem.length ) {
				this._close();
				this.focus( event, newItem );
			}
		},
	
		expand: function( event ) {
			var newItem = this.active &&
				this.active
					.children( ".ui-menu " )
					.find( this.options.items )
					.first();
	
			if ( newItem && newItem.length ) {
				this._open( newItem.parent() );
	
				// Delay so Firefox will not hide activedescendant change in expanding submenu from AT
				this._delay(function() {
					this.focus( event, newItem );
				});
			}
		},
	
		next: function( event ) {
			this._move( "next", "first", event );
		},
	
		previous: function( event ) {
			this._move( "prev", "last", event );
		},
	
		isFirstItem: function() {
			return this.active && !this.active.prevAll( ".ui-menu-item" ).length;
		},
	
		isLastItem: function() {
			return this.active && !this.active.nextAll( ".ui-menu-item" ).length;
		},
	
		_move: function( direction, filter, event ) {
			var next;
			if ( this.active ) {
				if ( direction === "first" || direction === "last" ) {
					next = this.active
						[ direction === "first" ? "prevAll" : "nextAll" ]( ".ui-menu-item" )
						.eq( -1 );
				} else {
					next = this.active
						[ direction + "All" ]( ".ui-menu-item" )
						.eq( 0 );
				}
			}
			if ( !next || !next.length || !this.active ) {
				next = this.activeMenu.find( this.options.items )[ filter ]();
			}
	
			this.focus( event, next );
		},
	
		nextPage: function( event ) {
			var item, base, height;
	
			if ( !this.active ) {
				this.next( event );
				return;
			}
			if ( this.isLastItem() ) {
				return;
			}
			if ( this._hasScroll() ) {
				base = this.active.offset().top;
				height = this.element.height();
				this.active.nextAll( ".ui-menu-item" ).each(function() {
					item = $( this );
					return item.offset().top - base - height < 0;
				});
	
				this.focus( event, item );
			} else {
				this.focus( event, this.activeMenu.find( this.options.items )
					[ !this.active ? "first" : "last" ]() );
			}
		},
	
		previousPage: function( event ) {
			var item, base, height;
			if ( !this.active ) {
				this.next( event );
				return;
			}
			if ( this.isFirstItem() ) {
				return;
			}
			if ( this._hasScroll() ) {
				base = this.active.offset().top;
				height = this.element.height();
				this.active.prevAll( ".ui-menu-item" ).each(function() {
					item = $( this );
					return item.offset().top - base + height > 0;
				});
	
				this.focus( event, item );
			} else {
				this.focus( event, this.activeMenu.find( this.options.items ).first() );
			}
		},
	
		_hasScroll: function() {
			return this.element.outerHeight() < this.element.prop( "scrollHeight" );
		},
	
		select: function( event ) {
			// TODO: It should never be possible to not have an active item at this
			// point, but the tests don't trigger mouseenter before click.
			this.active = this.active || $( event.target ).closest( ".ui-menu-item" );
			var ui = { item: this.active };
			if ( !this.active.has( ".ui-menu" ).length ) {
				this.collapseAll( event, true );
			}
			this._trigger( "select", event, ui );
		},
	
		_filterMenuItems: function(character) {
			var escapedCharacter = character.replace( /[\-\[\]{}()*+?.,\\\^$|#\s]/g, "\\$&" ),
				regex = new RegExp( "^" + escapedCharacter, "i" );
	
			return this.activeMenu
				.find( this.options.items )
	
				// Only match on items, not dividers or other content (#10571)
				.filter( ".ui-menu-item" )
				.filter(function() {
					return regex.test( $.trim( $( this ).text() ) );
				});
		}
	});
	
	
	/*!
	 * jQuery UI Autocomplete 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/autocomplete/
	 */
	
	
	$.widget( "ui.autocomplete", {
		version: "1.11.4",
		defaultElement: "<input>",
		options: {
			appendTo: null,
			autoFocus: false,
			delay: 300,
			minLength: 1,
			position: {
				my: "left top",
				at: "left bottom",
				collision: "none"
			},
			source: null,
	
			// callbacks
			change: null,
			close: null,
			focus: null,
			open: null,
			response: null,
			search: null,
			select: null
		},
	
		requestIndex: 0,
		pending: 0,
	
		_create: function() {
			// Some browsers only repeat keydown events, not keypress events,
			// so we use the suppressKeyPress flag to determine if we've already
			// handled the keydown event. #7269
			// Unfortunately the code for & in keypress is the same as the up arrow,
			// so we use the suppressKeyPressRepeat flag to avoid handling keypress
			// events when we know the keydown event was used to modify the
			// search term. #7799
			var suppressKeyPress, suppressKeyPressRepeat, suppressInput,
				nodeName = this.element[ 0 ].nodeName.toLowerCase(),
				isTextarea = nodeName === "textarea",
				isInput = nodeName === "input";
	
			this.isMultiLine =
				// Textareas are always multi-line
				isTextarea ? true :
				// Inputs are always single-line, even if inside a contentEditable element
				// IE also treats inputs as contentEditable
				isInput ? false :
				// All other element types are determined by whether or not they're contentEditable
				this.element.prop( "isContentEditable" );
	
			this.valueMethod = this.element[ isTextarea || isInput ? "val" : "text" ];
			this.isNewMenu = true;
	
			this.element
				.addClass( "ui-autocomplete-input" )
				.attr( "autocomplete", "off" );
	
			this._on( this.element, {
				keydown: function( event ) {
					if ( this.element.prop( "readOnly" ) ) {
						suppressKeyPress = true;
						suppressInput = true;
						suppressKeyPressRepeat = true;
						return;
					}
	
					suppressKeyPress = false;
					suppressInput = false;
					suppressKeyPressRepeat = false;
					var keyCode = $.ui.keyCode;
					switch ( event.keyCode ) {
					case keyCode.PAGE_UP:
						suppressKeyPress = true;
						this._move( "previousPage", event );
						break;
					case keyCode.PAGE_DOWN:
						suppressKeyPress = true;
						this._move( "nextPage", event );
						break;
					case keyCode.UP:
						suppressKeyPress = true;
						this._keyEvent( "previous", event );
						break;
					case keyCode.DOWN:
						suppressKeyPress = true;
						this._keyEvent( "next", event );
						break;
					case keyCode.ENTER:
						// when menu is open and has focus
						if ( this.menu.active ) {
							// #6055 - Opera still allows the keypress to occur
							// which causes forms to submit
							suppressKeyPress = true;
							event.preventDefault();
							this.menu.select( event );
						}
						break;
					case keyCode.TAB:
						if ( this.menu.active ) {
							this.menu.select( event );
						}
						break;
					case keyCode.ESCAPE:
						if ( this.menu.element.is( ":visible" ) ) {
							if ( !this.isMultiLine ) {
								this._value( this.term );
							}
							this.close( event );
							// Different browsers have different default behavior for escape
							// Single press can mean undo or clear
							// Double press in IE means clear the whole form
							event.preventDefault();
						}
						break;
					default:
						suppressKeyPressRepeat = true;
						// search timeout should be triggered before the input value is changed
						this._searchTimeout( event );
						break;
					}
				},
				keypress: function( event ) {
					if ( suppressKeyPress ) {
						suppressKeyPress = false;
						if ( !this.isMultiLine || this.menu.element.is( ":visible" ) ) {
							event.preventDefault();
						}
						return;
					}
					if ( suppressKeyPressRepeat ) {
						return;
					}
	
					// replicate some key handlers to allow them to repeat in Firefox and Opera
					var keyCode = $.ui.keyCode;
					switch ( event.keyCode ) {
					case keyCode.PAGE_UP:
						this._move( "previousPage", event );
						break;
					case keyCode.PAGE_DOWN:
						this._move( "nextPage", event );
						break;
					case keyCode.UP:
						this._keyEvent( "previous", event );
						break;
					case keyCode.DOWN:
						this._keyEvent( "next", event );
						break;
					}
				},
				input: function( event ) {
					if ( suppressInput ) {
						suppressInput = false;
						event.preventDefault();
						return;
					}
					this._searchTimeout( event );
				},
				focus: function() {
					this.selectedItem = null;
					this.previous = this._value();
				},
				blur: function( event ) {
					if ( this.cancelBlur ) {
						delete this.cancelBlur;
						return;
					}
	
					clearTimeout( this.searching );
					this.close( event );
					this._change( event );
				}
			});
	
			this._initSource();
			this.menu = $( "<ul>" )
				.addClass( "ui-autocomplete ui-front" )
				.appendTo( this._appendTo() )
				.menu({
					// disable ARIA support, the live region takes care of that
					role: null
				})
				.hide()
				.menu( "instance" );
	
			this._on( this.menu.element, {
				mousedown: function( event ) {
					// prevent moving focus out of the text field
					event.preventDefault();
	
					// IE doesn't prevent moving focus even with event.preventDefault()
					// so we set a flag to know when we should ignore the blur event
					this.cancelBlur = true;
					this._delay(function() {
						delete this.cancelBlur;
					});
	
					// clicking on the scrollbar causes focus to shift to the body
					// but we can't detect a mouseup or a click immediately afterward
					// so we have to track the next mousedown and close the menu if
					// the user clicks somewhere outside of the autocomplete
					var menuElement = this.menu.element[ 0 ];
					if ( !$( event.target ).closest( ".ui-menu-item" ).length ) {
						this._delay(function() {
							var that = this;
							this.document.one( "mousedown", function( event ) {
								if ( event.target !== that.element[ 0 ] &&
										event.target !== menuElement &&
										!$.contains( menuElement, event.target ) ) {
									that.close();
								}
							});
						});
					}
				},
				menufocus: function( event, ui ) {
					var label, item;
					// support: Firefox
					// Prevent accidental activation of menu items in Firefox (#7024 #9118)
					if ( this.isNewMenu ) {
						this.isNewMenu = false;
						if ( event.originalEvent && /^mouse/.test( event.originalEvent.type ) ) {
							this.menu.blur();
	
							this.document.one( "mousemove", function() {
								$( event.target ).trigger( event.originalEvent );
							});
	
							return;
						}
					}
	
					item = ui.item.data( "ui-autocomplete-item" );
					if ( false !== this._trigger( "focus", event, { item: item } ) ) {
						// use value to match what will end up in the input, if it was a key event
						if ( event.originalEvent && /^key/.test( event.originalEvent.type ) ) {
							this._value( item.value );
						}
					}
	
					// Announce the value in the liveRegion
					label = ui.item.attr( "aria-label" ) || item.value;
					if ( label && $.trim( label ).length ) {
						this.liveRegion.children().hide();
						$( "<div>" ).text( label ).appendTo( this.liveRegion );
					}
				},
				menuselect: function( event, ui ) {
					var item = ui.item.data( "ui-autocomplete-item" ),
						previous = this.previous;
	
					// only trigger when focus was lost (click on menu)
					if ( this.element[ 0 ] !== this.document[ 0 ].activeElement ) {
						this.element.focus();
						this.previous = previous;
						// #6109 - IE triggers two focus events and the second
						// is asynchronous, so we need to reset the previous
						// term synchronously and asynchronously :-(
						this._delay(function() {
							this.previous = previous;
							this.selectedItem = item;
						});
					}
	
					if ( false !== this._trigger( "select", event, { item: item } ) ) {
						this._value( item.value );
					}
					// reset the term after the select event
					// this allows custom select handling to work properly
					this.term = this._value();
	
					this.close( event );
					this.selectedItem = item;
				}
			});
	
			this.liveRegion = $( "<span>", {
					role: "status",
					"aria-live": "assertive",
					"aria-relevant": "additions"
				})
				.addClass( "ui-helper-hidden-accessible" )
				.appendTo( this.document[ 0 ].body );
	
			// turning off autocomplete prevents the browser from remembering the
			// value when navigating through history, so we re-enable autocomplete
			// if the page is unloaded before the widget is destroyed. #7790
			this._on( this.window, {
				beforeunload: function() {
					this.element.removeAttr( "autocomplete" );
				}
			});
		},
	
		_destroy: function() {
			clearTimeout( this.searching );
			this.element
				.removeClass( "ui-autocomplete-input" )
				.removeAttr( "autocomplete" );
			this.menu.element.remove();
			this.liveRegion.remove();
		},
	
		_setOption: function( key, value ) {
			this._super( key, value );
			if ( key === "source" ) {
				this._initSource();
			}
			if ( key === "appendTo" ) {
				this.menu.element.appendTo( this._appendTo() );
			}
			if ( key === "disabled" && value && this.xhr ) {
				this.xhr.abort();
			}
		},
	
		_appendTo: function() {
			var element = this.options.appendTo;
	
			if ( element ) {
				element = element.jquery || element.nodeType ?
					$( element ) :
					this.document.find( element ).eq( 0 );
			}
	
			if ( !element || !element[ 0 ] ) {
				element = this.element.closest( ".ui-front" );
			}
	
			if ( !element.length ) {
				element = this.document[ 0 ].body;
			}
	
			return element;
		},
	
		_initSource: function() {
			var array, url,
				that = this;
			if ( $.isArray( this.options.source ) ) {
				array = this.options.source;
				this.source = function( request, response ) {
					response( $.ui.autocomplete.filter( array, request.term ) );
				};
			} else if ( typeof this.options.source === "string" ) {
				url = this.options.source;
				this.source = function( request, response ) {
					if ( that.xhr ) {
						that.xhr.abort();
					}
					that.xhr = $.ajax({
						url: url,
						data: request,
						dataType: "json",
						success: function( data ) {
							response( data );
						},
						error: function() {
							response([]);
						}
					});
				};
			} else {
				this.source = this.options.source;
			}
		},
	
		_searchTimeout: function( event ) {
			clearTimeout( this.searching );
			this.searching = this._delay(function() {
	
				// Search if the value has changed, or if the user retypes the same value (see #7434)
				var equalValues = this.term === this._value(),
					menuVisible = this.menu.element.is( ":visible" ),
					modifierKey = event.altKey || event.ctrlKey || event.metaKey || event.shiftKey;
	
				if ( !equalValues || ( equalValues && !menuVisible && !modifierKey ) ) {
					this.selectedItem = null;
					this.search( null, event );
				}
			}, this.options.delay );
		},
	
		search: function( value, event ) {
			value = value != null ? value : this._value();
	
			// always save the actual value, not the one passed as an argument
			this.term = this._value();
	
			if ( value.length < this.options.minLength ) {
				return this.close( event );
			}
	
			if ( this._trigger( "search", event ) === false ) {
				return;
			}
	
			return this._search( value );
		},
	
		_search: function( value ) {
			this.pending++;
			this.element.addClass( "ui-autocomplete-loading" );
			this.cancelSearch = false;
	
			this.source( { term: value }, this._response() );
		},
	
		_response: function() {
			var index = ++this.requestIndex;
	
			return $.proxy(function( content ) {
				if ( index === this.requestIndex ) {
					this.__response( content );
				}
	
				this.pending--;
				if ( !this.pending ) {
					this.element.removeClass( "ui-autocomplete-loading" );
				}
			}, this );
		},
	
		__response: function( content ) {
			if ( content ) {
				content = this._normalize( content );
			}
			this._trigger( "response", null, { content: content } );
			if ( !this.options.disabled && content && content.length && !this.cancelSearch ) {
				this._suggest( content );
				this._trigger( "open" );
			} else {
				// use ._close() instead of .close() so we don't cancel future searches
				this._close();
			}
		},
	
		close: function( event ) {
			this.cancelSearch = true;
			this._close( event );
		},
	
		_close: function( event ) {
			if ( this.menu.element.is( ":visible" ) ) {
				this.menu.element.hide();
				this.menu.blur();
				this.isNewMenu = true;
				this._trigger( "close", event );
			}
		},
	
		_change: function( event ) {
			if ( this.previous !== this._value() ) {
				this._trigger( "change", event, { item: this.selectedItem } );
			}
		},
	
		_normalize: function( items ) {
			// assume all items have the right format when the first item is complete
			if ( items.length && items[ 0 ].label && items[ 0 ].value ) {
				return items;
			}
			return $.map( items, function( item ) {
				if ( typeof item === "string" ) {
					return {
						label: item,
						value: item
					};
				}
				return $.extend( {}, item, {
					label: item.label || item.value,
					value: item.value || item.label
				});
			});
		},
	
		_suggest: function( items ) {
			var ul = this.menu.element.empty();
			this._renderMenu( ul, items );
			this.isNewMenu = true;
			this.menu.refresh();
	
			// size and position menu
			ul.show();
			this._resizeMenu();
			ul.position( $.extend({
				of: this.element
			}, this.options.position ) );
	
			if ( this.options.autoFocus ) {
				this.menu.next();
			}
		},
	
		_resizeMenu: function() {
			var ul = this.menu.element;
			ul.outerWidth( Math.max(
				// Firefox wraps long text (possibly a rounding bug)
				// so we add 1px to avoid the wrapping (#7513)
				ul.width( "" ).outerWidth() + 1,
				this.element.outerWidth()
			) );
		},
	
		_renderMenu: function( ul, items ) {
			var that = this;
			$.each( items, function( index, item ) {
				that._renderItemData( ul, item );
			});
		},
	
		_renderItemData: function( ul, item ) {
			return this._renderItem( ul, item ).data( "ui-autocomplete-item", item );
		},
	
		_renderItem: function( ul, item ) {
			return $( "<li>" ).text( item.label ).appendTo( ul );
		},
	
		_move: function( direction, event ) {
			if ( !this.menu.element.is( ":visible" ) ) {
				this.search( null, event );
				return;
			}
			if ( this.menu.isFirstItem() && /^previous/.test( direction ) ||
					this.menu.isLastItem() && /^next/.test( direction ) ) {
	
				if ( !this.isMultiLine ) {
					this._value( this.term );
				}
	
				this.menu.blur();
				return;
			}
			this.menu[ direction ]( event );
		},
	
		widget: function() {
			return this.menu.element;
		},
	
		_value: function() {
			return this.valueMethod.apply( this.element, arguments );
		},
	
		_keyEvent: function( keyEvent, event ) {
			if ( !this.isMultiLine || this.menu.element.is( ":visible" ) ) {
				this._move( keyEvent, event );
	
				// prevents moving cursor to beginning/end of the text field in some browsers
				event.preventDefault();
			}
		}
	});
	
	$.extend( $.ui.autocomplete, {
		escapeRegex: function( value ) {
			return value.replace( /[\-\[\]{}()*+?.,\\\^$|#\s]/g, "\\$&" );
		},
		filter: function( array, term ) {
			var matcher = new RegExp( $.ui.autocomplete.escapeRegex( term ), "i" );
			return $.grep( array, function( value ) {
				return matcher.test( value.label || value.value || value );
			});
		}
	});
	
	// live region extension, adding a `messages` option
	// NOTE: This is an experimental API. We are still investigating
	// a full solution for string manipulation and internationalization.
	$.widget( "ui.autocomplete", $.ui.autocomplete, {
		options: {
			messages: {
				noResults: "No search results.",
				results: function( amount ) {
					return amount + ( amount > 1 ? " results are" : " result is" ) +
						" available, use up and down arrow keys to navigate.";
				}
			}
		},
	
		__response: function( content ) {
			var message;
			this._superApply( arguments );
			if ( this.options.disabled || this.cancelSearch ) {
				return;
			}
			if ( content && content.length ) {
				message = this.options.messages.results( content.length );
			} else {
				message = this.options.messages.noResults;
			}
			this.liveRegion.children().hide();
			$( "<div>" ).text( message ).appendTo( this.liveRegion );
		}
	});
	
	var autocomplete = $.ui.autocomplete;
	
	
	/*!
	 * jQuery UI Button 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/button/
	 */
	
	
	var lastActive,
		baseClasses = "ui-button ui-widget ui-state-default ui-corner-all",
		typeClasses = "ui-button-icons-only ui-button-icon-only ui-button-text-icons ui-button-text-icon-primary ui-button-text-icon-secondary ui-button-text-only",
		formResetHandler = function() {
			var form = $( this );
			setTimeout(function() {
				form.find( ":ui-button" ).button( "refresh" );
			}, 1 );
		},
		radioGroup = function( radio ) {
			var name = radio.name,
				form = radio.form,
				radios = $( [] );
			if ( name ) {
				name = name.replace( /'/g, "\\'" );
				if ( form ) {
					radios = $( form ).find( "[name='" + name + "'][type=radio]" );
				} else {
					radios = $( "[name='" + name + "'][type=radio]", radio.ownerDocument )
						.filter(function() {
							return !this.form;
						});
				}
			}
			return radios;
		};
	
	$.widget( "ui.button", {
		version: "1.11.4",
		defaultElement: "<button>",
		options: {
			disabled: null,
			text: true,
			label: null,
			icons: {
				primary: null,
				secondary: null
			}
		},
		_create: function() {
			this.element.closest( "form" )
				.unbind( "reset" + this.eventNamespace )
				.bind( "reset" + this.eventNamespace, formResetHandler );
	
			if ( typeof this.options.disabled !== "boolean" ) {
				this.options.disabled = !!this.element.prop( "disabled" );
			} else {
				this.element.prop( "disabled", this.options.disabled );
			}
	
			this._determineButtonType();
			this.hasTitle = !!this.buttonElement.attr( "title" );
	
			var that = this,
				options = this.options,
				toggleButton = this.type === "checkbox" || this.type === "radio",
				activeClass = !toggleButton ? "ui-state-active" : "";
	
			if ( options.label === null ) {
				options.label = (this.type === "input" ? this.buttonElement.val() : this.buttonElement.html());
			}
	
			this._hoverable( this.buttonElement );
	
			this.buttonElement
				.addClass( baseClasses )
				.attr( "role", "button" )
				.bind( "mouseenter" + this.eventNamespace, function() {
					if ( options.disabled ) {
						return;
					}
					if ( this === lastActive ) {
						$( this ).addClass( "ui-state-active" );
					}
				})
				.bind( "mouseleave" + this.eventNamespace, function() {
					if ( options.disabled ) {
						return;
					}
					$( this ).removeClass( activeClass );
				})
				.bind( "click" + this.eventNamespace, function( event ) {
					if ( options.disabled ) {
						event.preventDefault();
						event.stopImmediatePropagation();
					}
				});
	
			// Can't use _focusable() because the element that receives focus
			// and the element that gets the ui-state-focus class are different
			this._on({
				focus: function() {
					this.buttonElement.addClass( "ui-state-focus" );
				},
				blur: function() {
					this.buttonElement.removeClass( "ui-state-focus" );
				}
			});
	
			if ( toggleButton ) {
				this.element.bind( "change" + this.eventNamespace, function() {
					that.refresh();
				});
			}
	
			if ( this.type === "checkbox" ) {
				this.buttonElement.bind( "click" + this.eventNamespace, function() {
					if ( options.disabled ) {
						return false;
					}
				});
			} else if ( this.type === "radio" ) {
				this.buttonElement.bind( "click" + this.eventNamespace, function() {
					if ( options.disabled ) {
						return false;
					}
					$( this ).addClass( "ui-state-active" );
					that.buttonElement.attr( "aria-pressed", "true" );
	
					var radio = that.element[ 0 ];
					radioGroup( radio )
						.not( radio )
						.map(function() {
							return $( this ).button( "widget" )[ 0 ];
						})
						.removeClass( "ui-state-active" )
						.attr( "aria-pressed", "false" );
				});
			} else {
				this.buttonElement
					.bind( "mousedown" + this.eventNamespace, function() {
						if ( options.disabled ) {
							return false;
						}
						$( this ).addClass( "ui-state-active" );
						lastActive = this;
						that.document.one( "mouseup", function() {
							lastActive = null;
						});
					})
					.bind( "mouseup" + this.eventNamespace, function() {
						if ( options.disabled ) {
							return false;
						}
						$( this ).removeClass( "ui-state-active" );
					})
					.bind( "keydown" + this.eventNamespace, function(event) {
						if ( options.disabled ) {
							return false;
						}
						if ( event.keyCode === $.ui.keyCode.SPACE || event.keyCode === $.ui.keyCode.ENTER ) {
							$( this ).addClass( "ui-state-active" );
						}
					})
					// see #8559, we bind to blur here in case the button element loses
					// focus between keydown and keyup, it would be left in an "active" state
					.bind( "keyup" + this.eventNamespace + " blur" + this.eventNamespace, function() {
						$( this ).removeClass( "ui-state-active" );
					});
	
				if ( this.buttonElement.is("a") ) {
					this.buttonElement.keyup(function(event) {
						if ( event.keyCode === $.ui.keyCode.SPACE ) {
							// TODO pass through original event correctly (just as 2nd argument doesn't work)
							$( this ).click();
						}
					});
				}
			}
	
			this._setOption( "disabled", options.disabled );
			this._resetButton();
		},
	
		_determineButtonType: function() {
			var ancestor, labelSelector, checked;
	
			if ( this.element.is("[type=checkbox]") ) {
				this.type = "checkbox";
			} else if ( this.element.is("[type=radio]") ) {
				this.type = "radio";
			} else if ( this.element.is("input") ) {
				this.type = "input";
			} else {
				this.type = "button";
			}
	
			if ( this.type === "checkbox" || this.type === "radio" ) {
				// we don't search against the document in case the element
				// is disconnected from the DOM
				ancestor = this.element.parents().last();
				labelSelector = "label[for='" + this.element.attr("id") + "']";
				this.buttonElement = ancestor.find( labelSelector );
				if ( !this.buttonElement.length ) {
					ancestor = ancestor.length ? ancestor.siblings() : this.element.siblings();
					this.buttonElement = ancestor.filter( labelSelector );
					if ( !this.buttonElement.length ) {
						this.buttonElement = ancestor.find( labelSelector );
					}
				}
				this.element.addClass( "ui-helper-hidden-accessible" );
	
				checked = this.element.is( ":checked" );
				if ( checked ) {
					this.buttonElement.addClass( "ui-state-active" );
				}
				this.buttonElement.prop( "aria-pressed", checked );
			} else {
				this.buttonElement = this.element;
			}
		},
	
		widget: function() {
			return this.buttonElement;
		},
	
		_destroy: function() {
			this.element
				.removeClass( "ui-helper-hidden-accessible" );
			this.buttonElement
				.removeClass( baseClasses + " ui-state-active " + typeClasses )
				.removeAttr( "role" )
				.removeAttr( "aria-pressed" )
				.html( this.buttonElement.find(".ui-button-text").html() );
	
			if ( !this.hasTitle ) {
				this.buttonElement.removeAttr( "title" );
			}
		},
	
		_setOption: function( key, value ) {
			this._super( key, value );
			if ( key === "disabled" ) {
				this.widget().toggleClass( "ui-state-disabled", !!value );
				this.element.prop( "disabled", !!value );
				if ( value ) {
					if ( this.type === "checkbox" || this.type === "radio" ) {
						this.buttonElement.removeClass( "ui-state-focus" );
					} else {
						this.buttonElement.removeClass( "ui-state-focus ui-state-active" );
					}
				}
				return;
			}
			this._resetButton();
		},
	
		refresh: function() {
			//See #8237 & #8828
			var isDisabled = this.element.is( "input, button" ) ? this.element.is( ":disabled" ) : this.element.hasClass( "ui-button-disabled" );
	
			if ( isDisabled !== this.options.disabled ) {
				this._setOption( "disabled", isDisabled );
			}
			if ( this.type === "radio" ) {
				radioGroup( this.element[0] ).each(function() {
					if ( $( this ).is( ":checked" ) ) {
						$( this ).button( "widget" )
							.addClass( "ui-state-active" )
							.attr( "aria-pressed", "true" );
					} else {
						$( this ).button( "widget" )
							.removeClass( "ui-state-active" )
							.attr( "aria-pressed", "false" );
					}
				});
			} else if ( this.type === "checkbox" ) {
				if ( this.element.is( ":checked" ) ) {
					this.buttonElement
						.addClass( "ui-state-active" )
						.attr( "aria-pressed", "true" );
				} else {
					this.buttonElement
						.removeClass( "ui-state-active" )
						.attr( "aria-pressed", "false" );
				}
			}
		},
	
		_resetButton: function() {
			if ( this.type === "input" ) {
				if ( this.options.label ) {
					this.element.val( this.options.label );
				}
				return;
			}
			var buttonElement = this.buttonElement.removeClass( typeClasses ),
				buttonText = $( "<span></span>", this.document[0] )
					.addClass( "ui-button-text" )
					.html( this.options.label )
					.appendTo( buttonElement.empty() )
					.text(),
				icons = this.options.icons,
				multipleIcons = icons.primary && icons.secondary,
				buttonClasses = [];
	
			if ( icons.primary || icons.secondary ) {
				if ( this.options.text ) {
					buttonClasses.push( "ui-button-text-icon" + ( multipleIcons ? "s" : ( icons.primary ? "-primary" : "-secondary" ) ) );
				}
	
				if ( icons.primary ) {
					buttonElement.prepend( "<span class='ui-button-icon-primary ui-icon " + icons.primary + "'></span>" );
				}
	
				if ( icons.secondary ) {
					buttonElement.append( "<span class='ui-button-icon-secondary ui-icon " + icons.secondary + "'></span>" );
				}
	
				if ( !this.options.text ) {
					buttonClasses.push( multipleIcons ? "ui-button-icons-only" : "ui-button-icon-only" );
	
					if ( !this.hasTitle ) {
						buttonElement.attr( "title", $.trim( buttonText ) );
					}
				}
			} else {
				buttonClasses.push( "ui-button-text-only" );
			}
			buttonElement.addClass( buttonClasses.join( " " ) );
		}
	});
	
	$.widget( "ui.buttonset", {
		version: "1.11.4",
		options: {
			items: "button, input[type=button], input[type=submit], input[type=reset], input[type=checkbox], input[type=radio], a, :data(ui-button)"
		},
	
		_create: function() {
			this.element.addClass( "ui-buttonset" );
		},
	
		_init: function() {
			this.refresh();
		},
	
		_setOption: function( key, value ) {
			if ( key === "disabled" ) {
				this.buttons.button( "option", key, value );
			}
	
			this._super( key, value );
		},
	
		refresh: function() {
			var rtl = this.element.css( "direction" ) === "rtl",
				allButtons = this.element.find( this.options.items ),
				existingButtons = allButtons.filter( ":ui-button" );
	
			// Initialize new buttons
			allButtons.not( ":ui-button" ).button();
	
			// Refresh existing buttons
			existingButtons.button( "refresh" );
	
			this.buttons = allButtons
				.map(function() {
					return $( this ).button( "widget" )[ 0 ];
				})
					.removeClass( "ui-corner-all ui-corner-left ui-corner-right" )
					.filter( ":first" )
						.addClass( rtl ? "ui-corner-right" : "ui-corner-left" )
					.end()
					.filter( ":last" )
						.addClass( rtl ? "ui-corner-left" : "ui-corner-right" )
					.end()
				.end();
		},
	
		_destroy: function() {
			this.element.removeClass( "ui-buttonset" );
			this.buttons
				.map(function() {
					return $( this ).button( "widget" )[ 0 ];
				})
					.removeClass( "ui-corner-left ui-corner-right" )
				.end()
				.button( "destroy" );
		}
	});
	
	var button = $.ui.button;
	
	
	/*!
	 * jQuery UI Datepicker 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/datepicker/
	 */
	
	
	$.extend($.ui, { datepicker: { version: "1.11.4" } });
	
	var datepicker_instActive;
	
	function datepicker_getZindex( elem ) {
		var position, value;
		while ( elem.length && elem[ 0 ] !== document ) {
			// Ignore z-index if position is set to a value where z-index is ignored by the browser
			// This makes behavior of this function consistent across browsers
			// WebKit always returns auto if the element is positioned
			position = elem.css( "position" );
			if ( position === "absolute" || position === "relative" || position === "fixed" ) {
				// IE returns 0 when zIndex is not specified
				// other browsers return a string
				// we ignore the case of nested elements with an explicit value of 0
				// <div style="z-index: -10;"><div style="z-index: 0;"></div></div>
				value = parseInt( elem.css( "zIndex" ), 10 );
				if ( !isNaN( value ) && value !== 0 ) {
					return value;
				}
			}
			elem = elem.parent();
		}
	
		return 0;
	}
	/* Date picker manager.
	   Use the singleton instance of this class, $.datepicker, to interact with the date picker.
	   Settings for (groups of) date pickers are maintained in an instance object,
	   allowing multiple different settings on the same page. */
	
	function Datepicker() {
		this._curInst = null; // The current instance in use
		this._keyEvent = false; // If the last event was a key event
		this._disabledInputs = []; // List of date picker inputs that have been disabled
		this._datepickerShowing = false; // True if the popup picker is showing , false if not
		this._inDialog = false; // True if showing within a "dialog", false if not
		this._mainDivId = "ui-datepicker-div"; // The ID of the main datepicker division
		this._inlineClass = "ui-datepicker-inline"; // The name of the inline marker class
		this._appendClass = "ui-datepicker-append"; // The name of the append marker class
		this._triggerClass = "ui-datepicker-trigger"; // The name of the trigger marker class
		this._dialogClass = "ui-datepicker-dialog"; // The name of the dialog marker class
		this._disableClass = "ui-datepicker-disabled"; // The name of the disabled covering marker class
		this._unselectableClass = "ui-datepicker-unselectable"; // The name of the unselectable cell marker class
		this._currentClass = "ui-datepicker-current-day"; // The name of the current day marker class
		this._dayOverClass = "ui-datepicker-days-cell-over"; // The name of the day hover marker class
		this.regional = []; // Available regional settings, indexed by language code
		this.regional[""] = { // Default regional settings
			closeText: "Done", // Display text for close link
			prevText: "Prev", // Display text for previous month link
			nextText: "Next", // Display text for next month link
			currentText: "Today", // Display text for current month link
			monthNames: ["January","February","March","April","May","June",
				"July","August","September","October","November","December"], // Names of months for drop-down and formatting
			monthNamesShort: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"], // For formatting
			dayNames: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"], // For formatting
			dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"], // For formatting
			dayNamesMin: ["Su","Mo","Tu","We","Th","Fr","Sa"], // Column headings for days starting at Sunday
			weekHeader: "Wk", // Column header for week of the year
			dateFormat: "mm/dd/yy", // See format options on parseDate
			firstDay: 0, // The first day of the week, Sun = 0, Mon = 1, ...
			isRTL: false, // True if right-to-left language, false if left-to-right
			showMonthAfterYear: false, // True if the year select precedes month, false for month then year
			yearSuffix: "" // Additional text to append to the year in the month headers
		};
		this._defaults = { // Global defaults for all the date picker instances
			showOn: "focus", // "focus" for popup on focus,
				// "button" for trigger button, or "both" for either
			showAnim: "fadeIn", // Name of jQuery animation for popup
			showOptions: {}, // Options for enhanced animations
			defaultDate: null, // Used when field is blank: actual date,
				// +/-number for offset from today, null for today
			appendText: "", // Display text following the input box, e.g. showing the format
			buttonText: "...", // Text for trigger button
			buttonImage: "", // URL for trigger button image
			buttonImageOnly: false, // True if the image appears alone, false if it appears on a button
			hideIfNoPrevNext: false, // True to hide next/previous month links
				// if not applicable, false to just disable them
			navigationAsDateFormat: false, // True if date formatting applied to prev/today/next links
			gotoCurrent: false, // True if today link goes back to current selection instead
			changeMonth: false, // True if month can be selected directly, false if only prev/next
			changeYear: false, // True if year can be selected directly, false if only prev/next
			yearRange: "c-10:c+10", // Range of years to display in drop-down,
				// either relative to today's year (-nn:+nn), relative to currently displayed year
				// (c-nn:c+nn), absolute (nnnn:nnnn), or a combination of the above (nnnn:-n)
			showOtherMonths: false, // True to show dates in other months, false to leave blank
			selectOtherMonths: false, // True to allow selection of dates in other months, false for unselectable
			showWeek: false, // True to show week of the year, false to not show it
			calculateWeek: this.iso8601Week, // How to calculate the week of the year,
				// takes a Date and returns the number of the week for it
			shortYearCutoff: "+10", // Short year values < this are in the current century,
				// > this are in the previous century,
				// string value starting with "+" for current year + value
			minDate: null, // The earliest selectable date, or null for no limit
			maxDate: null, // The latest selectable date, or null for no limit
			duration: "fast", // Duration of display/closure
			beforeShowDay: null, // Function that takes a date and returns an array with
				// [0] = true if selectable, false if not, [1] = custom CSS class name(s) or "",
				// [2] = cell title (optional), e.g. $.datepicker.noWeekends
			beforeShow: null, // Function that takes an input field and
				// returns a set of custom settings for the date picker
			onSelect: null, // Define a callback function when a date is selected
			onChangeMonthYear: null, // Define a callback function when the month or year is changed
			onClose: null, // Define a callback function when the datepicker is closed
			numberOfMonths: 1, // Number of months to show at a time
			showCurrentAtPos: 0, // The position in multipe months at which to show the current month (starting at 0)
			stepMonths: 1, // Number of months to step back/forward
			stepBigMonths: 12, // Number of months to step back/forward for the big links
			altField: "", // Selector for an alternate field to store selected dates into
			altFormat: "", // The date format to use for the alternate field
			constrainInput: true, // The input is constrained by the current date format
			showButtonPanel: false, // True to show button panel, false to not show it
			autoSize: false, // True to size the input for the date format, false to leave as is
			disabled: false // The initial disabled state
		};
		$.extend(this._defaults, this.regional[""]);
		this.regional.en = $.extend( true, {}, this.regional[ "" ]);
		this.regional[ "en-US" ] = $.extend( true, {}, this.regional.en );
		this.dpDiv = datepicker_bindHover($("<div id='" + this._mainDivId + "' class='ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>"));
	}
	
	$.extend(Datepicker.prototype, {
		/* Class name added to elements to indicate already configured with a date picker. */
		markerClassName: "hasDatepicker",
	
		//Keep track of the maximum number of rows displayed (see #7043)
		maxRows: 4,
	
		// TODO rename to "widget" when switching to widget factory
		_widgetDatepicker: function() {
			return this.dpDiv;
		},
	
		/* Override the default settings for all instances of the date picker.
		 * @param  settings  object - the new settings to use as defaults (anonymous object)
		 * @return the manager object
		 */
		setDefaults: function(settings) {
			datepicker_extendRemove(this._defaults, settings || {});
			return this;
		},
	
		/* Attach the date picker to a jQuery selection.
		 * @param  target	element - the target input field or division or span
		 * @param  settings  object - the new settings to use for this date picker instance (anonymous)
		 */
		_attachDatepicker: function(target, settings) {
			var nodeName, inline, inst;
			nodeName = target.nodeName.toLowerCase();
			inline = (nodeName === "div" || nodeName === "span");
			if (!target.id) {
				this.uuid += 1;
				target.id = "dp" + this.uuid;
			}
			inst = this._newInst($(target), inline);
			inst.settings = $.extend({}, settings || {});
			if (nodeName === "input") {
				this._connectDatepicker(target, inst);
			} else if (inline) {
				this._inlineDatepicker(target, inst);
			}
		},
	
		/* Create a new instance object. */
		_newInst: function(target, inline) {
			var id = target[0].id.replace(/([^A-Za-z0-9_\-])/g, "\\\\$1"); // escape jQuery meta chars
			return {id: id, input: target, // associated target
				selectedDay: 0, selectedMonth: 0, selectedYear: 0, // current selection
				drawMonth: 0, drawYear: 0, // month being drawn
				inline: inline, // is datepicker inline or not
				dpDiv: (!inline ? this.dpDiv : // presentation div
				datepicker_bindHover($("<div class='" + this._inlineClass + " ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>")))};
		},
	
		/* Attach the date picker to an input field. */
		_connectDatepicker: function(target, inst) {
			var input = $(target);
			inst.append = $([]);
			inst.trigger = $([]);
			if (input.hasClass(this.markerClassName)) {
				return;
			}
			this._attachments(input, inst);
			input.addClass(this.markerClassName).keydown(this._doKeyDown).
				keypress(this._doKeyPress).keyup(this._doKeyUp);
			this._autoSize(inst);
			$.data(target, "datepicker", inst);
			//If disabled option is true, disable the datepicker once it has been attached to the input (see ticket #5665)
			if( inst.settings.disabled ) {
				this._disableDatepicker( target );
			}
		},
	
		/* Make attachments based on settings. */
		_attachments: function(input, inst) {
			var showOn, buttonText, buttonImage,
				appendText = this._get(inst, "appendText"),
				isRTL = this._get(inst, "isRTL");
	
			if (inst.append) {
				inst.append.remove();
			}
			if (appendText) {
				inst.append = $("<span class='" + this._appendClass + "'>" + appendText + "</span>");
				input[isRTL ? "before" : "after"](inst.append);
			}
	
			input.unbind("focus", this._showDatepicker);
	
			if (inst.trigger) {
				inst.trigger.remove();
			}
	
			showOn = this._get(inst, "showOn");
			if (showOn === "focus" || showOn === "both") { // pop-up date picker when in the marked field
				input.focus(this._showDatepicker);
			}
			if (showOn === "button" || showOn === "both") { // pop-up date picker when button clicked
				buttonText = this._get(inst, "buttonText");
				buttonImage = this._get(inst, "buttonImage");
				inst.trigger = $(this._get(inst, "buttonImageOnly") ?
					$("<img/>").addClass(this._triggerClass).
						attr({ src: buttonImage, alt: buttonText, title: buttonText }) :
					$("<button type='button'></button>").addClass(this._triggerClass).
						html(!buttonImage ? buttonText : $("<img/>").attr(
						{ src:buttonImage, alt:buttonText, title:buttonText })));
				input[isRTL ? "before" : "after"](inst.trigger);
				inst.trigger.click(function() {
					if ($.datepicker._datepickerShowing && $.datepicker._lastInput === input[0]) {
						$.datepicker._hideDatepicker();
					} else if ($.datepicker._datepickerShowing && $.datepicker._lastInput !== input[0]) {
						$.datepicker._hideDatepicker();
						$.datepicker._showDatepicker(input[0]);
					} else {
						$.datepicker._showDatepicker(input[0]);
					}
					return false;
				});
			}
		},
	
		/* Apply the maximum length for the date format. */
		_autoSize: function(inst) {
			if (this._get(inst, "autoSize") && !inst.inline) {
				var findMax, max, maxI, i,
					date = new Date(2009, 12 - 1, 20), // Ensure double digits
					dateFormat = this._get(inst, "dateFormat");
	
				if (dateFormat.match(/[DM]/)) {
					findMax = function(names) {
						max = 0;
						maxI = 0;
						for (i = 0; i < names.length; i++) {
							if (names[i].length > max) {
								max = names[i].length;
								maxI = i;
							}
						}
						return maxI;
					};
					date.setMonth(findMax(this._get(inst, (dateFormat.match(/MM/) ?
						"monthNames" : "monthNamesShort"))));
					date.setDate(findMax(this._get(inst, (dateFormat.match(/DD/) ?
						"dayNames" : "dayNamesShort"))) + 20 - date.getDay());
				}
				inst.input.attr("size", this._formatDate(inst, date).length);
			}
		},
	
		/* Attach an inline date picker to a div. */
		_inlineDatepicker: function(target, inst) {
			var divSpan = $(target);
			if (divSpan.hasClass(this.markerClassName)) {
				return;
			}
			divSpan.addClass(this.markerClassName).append(inst.dpDiv);
			$.data(target, "datepicker", inst);
			this._setDate(inst, this._getDefaultDate(inst), true);
			this._updateDatepicker(inst);
			this._updateAlternate(inst);
			//If disabled option is true, disable the datepicker before showing it (see ticket #5665)
			if( inst.settings.disabled ) {
				this._disableDatepicker( target );
			}
			// Set display:block in place of inst.dpDiv.show() which won't work on disconnected elements
			// http://bugs.jqueryui.com/ticket/7552 - A Datepicker created on a detached div has zero height
			inst.dpDiv.css( "display", "block" );
		},
	
		/* Pop-up the date picker in a "dialog" box.
		 * @param  input element - ignored
		 * @param  date	string or Date - the initial date to display
		 * @param  onSelect  function - the function to call when a date is selected
		 * @param  settings  object - update the dialog date picker instance's settings (anonymous object)
		 * @param  pos int[2] - coordinates for the dialog's position within the screen or
		 *					event - with x/y coordinates or
		 *					leave empty for default (screen centre)
		 * @return the manager object
		 */
		_dialogDatepicker: function(input, date, onSelect, settings, pos) {
			var id, browserWidth, browserHeight, scrollX, scrollY,
				inst = this._dialogInst; // internal instance
	
			if (!inst) {
				this.uuid += 1;
				id = "dp" + this.uuid;
				this._dialogInput = $("<input type='text' id='" + id +
					"' style='position: absolute; top: -100px; width: 0px;'/>");
				this._dialogInput.keydown(this._doKeyDown);
				$("body").append(this._dialogInput);
				inst = this._dialogInst = this._newInst(this._dialogInput, false);
				inst.settings = {};
				$.data(this._dialogInput[0], "datepicker", inst);
			}
			datepicker_extendRemove(inst.settings, settings || {});
			date = (date && date.constructor === Date ? this._formatDate(inst, date) : date);
			this._dialogInput.val(date);
	
			this._pos = (pos ? (pos.length ? pos : [pos.pageX, pos.pageY]) : null);
			if (!this._pos) {
				browserWidth = document.documentElement.clientWidth;
				browserHeight = document.documentElement.clientHeight;
				scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
				scrollY = document.documentElement.scrollTop || document.body.scrollTop;
				this._pos = // should use actual width/height below
					[(browserWidth / 2) - 100 + scrollX, (browserHeight / 2) - 150 + scrollY];
			}
	
			// move input on screen for focus, but hidden behind dialog
			this._dialogInput.css("left", (this._pos[0] + 20) + "px").css("top", this._pos[1] + "px");
			inst.settings.onSelect = onSelect;
			this._inDialog = true;
			this.dpDiv.addClass(this._dialogClass);
			this._showDatepicker(this._dialogInput[0]);
			if ($.blockUI) {
				$.blockUI(this.dpDiv);
			}
			$.data(this._dialogInput[0], "datepicker", inst);
			return this;
		},
	
		/* Detach a datepicker from its control.
		 * @param  target	element - the target input field or division or span
		 */
		_destroyDatepicker: function(target) {
			var nodeName,
				$target = $(target),
				inst = $.data(target, "datepicker");
	
			if (!$target.hasClass(this.markerClassName)) {
				return;
			}
	
			nodeName = target.nodeName.toLowerCase();
			$.removeData(target, "datepicker");
			if (nodeName === "input") {
				inst.append.remove();
				inst.trigger.remove();
				$target.removeClass(this.markerClassName).
					unbind("focus", this._showDatepicker).
					unbind("keydown", this._doKeyDown).
					unbind("keypress", this._doKeyPress).
					unbind("keyup", this._doKeyUp);
			} else if (nodeName === "div" || nodeName === "span") {
				$target.removeClass(this.markerClassName).empty();
			}
	
			if ( datepicker_instActive === inst ) {
				datepicker_instActive = null;
			}
		},
	
		/* Enable the date picker to a jQuery selection.
		 * @param  target	element - the target input field or division or span
		 */
		_enableDatepicker: function(target) {
			var nodeName, inline,
				$target = $(target),
				inst = $.data(target, "datepicker");
	
			if (!$target.hasClass(this.markerClassName)) {
				return;
			}
	
			nodeName = target.nodeName.toLowerCase();
			if (nodeName === "input") {
				target.disabled = false;
				inst.trigger.filter("button").
					each(function() { this.disabled = false; }).end().
					filter("img").css({opacity: "1.0", cursor: ""});
			} else if (nodeName === "div" || nodeName === "span") {
				inline = $target.children("." + this._inlineClass);
				inline.children().removeClass("ui-state-disabled");
				inline.find("select.ui-datepicker-month, select.ui-datepicker-year").
					prop("disabled", false);
			}
			this._disabledInputs = $.map(this._disabledInputs,
				function(value) { return (value === target ? null : value); }); // delete entry
		},
	
		/* Disable the date picker to a jQuery selection.
		 * @param  target	element - the target input field or division or span
		 */
		_disableDatepicker: function(target) {
			var nodeName, inline,
				$target = $(target),
				inst = $.data(target, "datepicker");
	
			if (!$target.hasClass(this.markerClassName)) {
				return;
			}
	
			nodeName = target.nodeName.toLowerCase();
			if (nodeName === "input") {
				target.disabled = true;
				inst.trigger.filter("button").
					each(function() { this.disabled = true; }).end().
					filter("img").css({opacity: "0.5", cursor: "default"});
			} else if (nodeName === "div" || nodeName === "span") {
				inline = $target.children("." + this._inlineClass);
				inline.children().addClass("ui-state-disabled");
				inline.find("select.ui-datepicker-month, select.ui-datepicker-year").
					prop("disabled", true);
			}
			this._disabledInputs = $.map(this._disabledInputs,
				function(value) { return (value === target ? null : value); }); // delete entry
			this._disabledInputs[this._disabledInputs.length] = target;
		},
	
		/* Is the first field in a jQuery collection disabled as a datepicker?
		 * @param  target	element - the target input field or division or span
		 * @return boolean - true if disabled, false if enabled
		 */
		_isDisabledDatepicker: function(target) {
			if (!target) {
				return false;
			}
			for (var i = 0; i < this._disabledInputs.length; i++) {
				if (this._disabledInputs[i] === target) {
					return true;
				}
			}
			return false;
		},
	
		/* Retrieve the instance data for the target control.
		 * @param  target  element - the target input field or division or span
		 * @return  object - the associated instance data
		 * @throws  error if a jQuery problem getting data
		 */
		_getInst: function(target) {
			try {
				return $.data(target, "datepicker");
			}
			catch (err) {
				throw "Missing instance data for this datepicker";
			}
		},
	
		/* Update or retrieve the settings for a date picker attached to an input field or division.
		 * @param  target  element - the target input field or division or span
		 * @param  name	object - the new settings to update or
		 *				string - the name of the setting to change or retrieve,
		 *				when retrieving also "all" for all instance settings or
		 *				"defaults" for all global defaults
		 * @param  value   any - the new value for the setting
		 *				(omit if above is an object or to retrieve a value)
		 */
		_optionDatepicker: function(target, name, value) {
			var settings, date, minDate, maxDate,
				inst = this._getInst(target);
	
			if (arguments.length === 2 && typeof name === "string") {
				return (name === "defaults" ? $.extend({}, $.datepicker._defaults) :
					(inst ? (name === "all" ? $.extend({}, inst.settings) :
					this._get(inst, name)) : null));
			}
	
			settings = name || {};
			if (typeof name === "string") {
				settings = {};
				settings[name] = value;
			}
	
			if (inst) {
				if (this._curInst === inst) {
					this._hideDatepicker();
				}
	
				date = this._getDateDatepicker(target, true);
				minDate = this._getMinMaxDate(inst, "min");
				maxDate = this._getMinMaxDate(inst, "max");
				datepicker_extendRemove(inst.settings, settings);
				// reformat the old minDate/maxDate values if dateFormat changes and a new minDate/maxDate isn't provided
				if (minDate !== null && settings.dateFormat !== undefined && settings.minDate === undefined) {
					inst.settings.minDate = this._formatDate(inst, minDate);
				}
				if (maxDate !== null && settings.dateFormat !== undefined && settings.maxDate === undefined) {
					inst.settings.maxDate = this._formatDate(inst, maxDate);
				}
				if ( "disabled" in settings ) {
					if ( settings.disabled ) {
						this._disableDatepicker(target);
					} else {
						this._enableDatepicker(target);
					}
				}
				this._attachments($(target), inst);
				this._autoSize(inst);
				this._setDate(inst, date);
				this._updateAlternate(inst);
				this._updateDatepicker(inst);
			}
		},
	
		// change method deprecated
		_changeDatepicker: function(target, name, value) {
			this._optionDatepicker(target, name, value);
		},
	
		/* Redraw the date picker attached to an input field or division.
		 * @param  target  element - the target input field or division or span
		 */
		_refreshDatepicker: function(target) {
			var inst = this._getInst(target);
			if (inst) {
				this._updateDatepicker(inst);
			}
		},
	
		/* Set the dates for a jQuery selection.
		 * @param  target element - the target input field or division or span
		 * @param  date	Date - the new date
		 */
		_setDateDatepicker: function(target, date) {
			var inst = this._getInst(target);
			if (inst) {
				this._setDate(inst, date);
				this._updateDatepicker(inst);
				this._updateAlternate(inst);
			}
		},
	
		/* Get the date(s) for the first entry in a jQuery selection.
		 * @param  target element - the target input field or division or span
		 * @param  noDefault boolean - true if no default date is to be used
		 * @return Date - the current date
		 */
		_getDateDatepicker: function(target, noDefault) {
			var inst = this._getInst(target);
			if (inst && !inst.inline) {
				this._setDateFromField(inst, noDefault);
			}
			return (inst ? this._getDate(inst) : null);
		},
	
		/* Handle keystrokes. */
		_doKeyDown: function(event) {
			var onSelect, dateStr, sel,
				inst = $.datepicker._getInst(event.target),
				handled = true,
				isRTL = inst.dpDiv.is(".ui-datepicker-rtl");
	
			inst._keyEvent = true;
			if ($.datepicker._datepickerShowing) {
				switch (event.keyCode) {
					case 9: $.datepicker._hideDatepicker();
							handled = false;
							break; // hide on tab out
					case 13: sel = $("td." + $.datepicker._dayOverClass + ":not(." +
										$.datepicker._currentClass + ")", inst.dpDiv);
							if (sel[0]) {
								$.datepicker._selectDay(event.target, inst.selectedMonth, inst.selectedYear, sel[0]);
							}
	
							onSelect = $.datepicker._get(inst, "onSelect");
							if (onSelect) {
								dateStr = $.datepicker._formatDate(inst);
	
								// trigger custom callback
								onSelect.apply((inst.input ? inst.input[0] : null), [dateStr, inst]);
							} else {
								$.datepicker._hideDatepicker();
							}
	
							return false; // don't submit the form
					case 27: $.datepicker._hideDatepicker();
							break; // hide on escape
					case 33: $.datepicker._adjustDate(event.target, (event.ctrlKey ?
								-$.datepicker._get(inst, "stepBigMonths") :
								-$.datepicker._get(inst, "stepMonths")), "M");
							break; // previous month/year on page up/+ ctrl
					case 34: $.datepicker._adjustDate(event.target, (event.ctrlKey ?
								+$.datepicker._get(inst, "stepBigMonths") :
								+$.datepicker._get(inst, "stepMonths")), "M");
							break; // next month/year on page down/+ ctrl
					case 35: if (event.ctrlKey || event.metaKey) {
								$.datepicker._clearDate(event.target);
							}
							handled = event.ctrlKey || event.metaKey;
							break; // clear on ctrl or command +end
					case 36: if (event.ctrlKey || event.metaKey) {
								$.datepicker._gotoToday(event.target);
							}
							handled = event.ctrlKey || event.metaKey;
							break; // current on ctrl or command +home
					case 37: if (event.ctrlKey || event.metaKey) {
								$.datepicker._adjustDate(event.target, (isRTL ? +1 : -1), "D");
							}
							handled = event.ctrlKey || event.metaKey;
							// -1 day on ctrl or command +left
							if (event.originalEvent.altKey) {
								$.datepicker._adjustDate(event.target, (event.ctrlKey ?
									-$.datepicker._get(inst, "stepBigMonths") :
									-$.datepicker._get(inst, "stepMonths")), "M");
							}
							// next month/year on alt +left on Mac
							break;
					case 38: if (event.ctrlKey || event.metaKey) {
								$.datepicker._adjustDate(event.target, -7, "D");
							}
							handled = event.ctrlKey || event.metaKey;
							break; // -1 week on ctrl or command +up
					case 39: if (event.ctrlKey || event.metaKey) {
								$.datepicker._adjustDate(event.target, (isRTL ? -1 : +1), "D");
							}
							handled = event.ctrlKey || event.metaKey;
							// +1 day on ctrl or command +right
							if (event.originalEvent.altKey) {
								$.datepicker._adjustDate(event.target, (event.ctrlKey ?
									+$.datepicker._get(inst, "stepBigMonths") :
									+$.datepicker._get(inst, "stepMonths")), "M");
							}
							// next month/year on alt +right
							break;
					case 40: if (event.ctrlKey || event.metaKey) {
								$.datepicker._adjustDate(event.target, +7, "D");
							}
							handled = event.ctrlKey || event.metaKey;
							break; // +1 week on ctrl or command +down
					default: handled = false;
				}
			} else if (event.keyCode === 36 && event.ctrlKey) { // display the date picker on ctrl+home
				$.datepicker._showDatepicker(this);
			} else {
				handled = false;
			}
	
			if (handled) {
				event.preventDefault();
				event.stopPropagation();
			}
		},
	
		/* Filter entered characters - based on date format. */
		_doKeyPress: function(event) {
			var chars, chr,
				inst = $.datepicker._getInst(event.target);
	
			if ($.datepicker._get(inst, "constrainInput")) {
				chars = $.datepicker._possibleChars($.datepicker._get(inst, "dateFormat"));
				chr = String.fromCharCode(event.charCode == null ? event.keyCode : event.charCode);
				return event.ctrlKey || event.metaKey || (chr < " " || !chars || chars.indexOf(chr) > -1);
			}
		},
	
		/* Synchronise manual entry and field/alternate field. */
		_doKeyUp: function(event) {
			var date,
				inst = $.datepicker._getInst(event.target);
	
			if (inst.input.val() !== inst.lastVal) {
				try {
					date = $.datepicker.parseDate($.datepicker._get(inst, "dateFormat"),
						(inst.input ? inst.input.val() : null),
						$.datepicker._getFormatConfig(inst));
	
					if (date) { // only if valid
						$.datepicker._setDateFromField(inst);
						$.datepicker._updateAlternate(inst);
						$.datepicker._updateDatepicker(inst);
					}
				}
				catch (err) {
				}
			}
			return true;
		},
	
		/* Pop-up the date picker for a given input field.
		 * If false returned from beforeShow event handler do not show.
		 * @param  input  element - the input field attached to the date picker or
		 *					event - if triggered by focus
		 */
		_showDatepicker: function(input) {
			input = input.target || input;
			if (input.nodeName.toLowerCase() !== "input") { // find from button/image trigger
				input = $("input", input.parentNode)[0];
			}
	
			if ($.datepicker._isDisabledDatepicker(input) || $.datepicker._lastInput === input) { // already here
				return;
			}
	
			var inst, beforeShow, beforeShowSettings, isFixed,
				offset, showAnim, duration;
	
			inst = $.datepicker._getInst(input);
			if ($.datepicker._curInst && $.datepicker._curInst !== inst) {
				$.datepicker._curInst.dpDiv.stop(true, true);
				if ( inst && $.datepicker._datepickerShowing ) {
					$.datepicker._hideDatepicker( $.datepicker._curInst.input[0] );
				}
			}
	
			beforeShow = $.datepicker._get(inst, "beforeShow");
			beforeShowSettings = beforeShow ? beforeShow.apply(input, [input, inst]) : {};
			if(beforeShowSettings === false){
				return;
			}
			datepicker_extendRemove(inst.settings, beforeShowSettings);
	
			inst.lastVal = null;
			$.datepicker._lastInput = input;
			$.datepicker._setDateFromField(inst);
	
			if ($.datepicker._inDialog) { // hide cursor
				input.value = "";
			}
			if (!$.datepicker._pos) { // position below input
				$.datepicker._pos = $.datepicker._findPos(input);
				$.datepicker._pos[1] += input.offsetHeight; // add the height
			}
	
			isFixed = false;
			$(input).parents().each(function() {
				isFixed |= $(this).css("position") === "fixed";
				return !isFixed;
			});
	
			offset = {left: $.datepicker._pos[0], top: $.datepicker._pos[1]};
			$.datepicker._pos = null;
			//to avoid flashes on Firefox
			inst.dpDiv.empty();
			// determine sizing offscreen
			inst.dpDiv.css({position: "absolute", display: "block", top: "-1000px"});
			$.datepicker._updateDatepicker(inst);
			// fix width for dynamic number of date pickers
			// and adjust position before showing
			offset = $.datepicker._checkOffset(inst, offset, isFixed);
			inst.dpDiv.css({position: ($.datepicker._inDialog && $.blockUI ?
				"static" : (isFixed ? "fixed" : "absolute")), display: "none",
				left: offset.left + "px", top: offset.top + "px"});
	
			if (!inst.inline) {
				showAnim = $.datepicker._get(inst, "showAnim");
				duration = $.datepicker._get(inst, "duration");
				inst.dpDiv.css( "z-index", datepicker_getZindex( $( input ) ) + 1 );
				$.datepicker._datepickerShowing = true;
	
				if ( $.effects && $.effects.effect[ showAnim ] ) {
					inst.dpDiv.show(showAnim, $.datepicker._get(inst, "showOptions"), duration);
				} else {
					inst.dpDiv[showAnim || "show"](showAnim ? duration : null);
				}
	
				if ( $.datepicker._shouldFocusInput( inst ) ) {
					inst.input.focus();
				}
	
				$.datepicker._curInst = inst;
			}
		},
	
		/* Generate the date picker content. */
		_updateDatepicker: function(inst) {
			this.maxRows = 4; //Reset the max number of rows being displayed (see #7043)
			datepicker_instActive = inst; // for delegate hover events
			inst.dpDiv.empty().append(this._generateHTML(inst));
			this._attachHandlers(inst);
	
			var origyearshtml,
				numMonths = this._getNumberOfMonths(inst),
				cols = numMonths[1],
				width = 17,
				activeCell = inst.dpDiv.find( "." + this._dayOverClass + " a" );
	
			if ( activeCell.length > 0 ) {
				datepicker_handleMouseover.apply( activeCell.get( 0 ) );
			}
	
			inst.dpDiv.removeClass("ui-datepicker-multi-2 ui-datepicker-multi-3 ui-datepicker-multi-4").width("");
			if (cols > 1) {
				inst.dpDiv.addClass("ui-datepicker-multi-" + cols).css("width", (width * cols) + "em");
			}
			inst.dpDiv[(numMonths[0] !== 1 || numMonths[1] !== 1 ? "add" : "remove") +
				"Class"]("ui-datepicker-multi");
			inst.dpDiv[(this._get(inst, "isRTL") ? "add" : "remove") +
				"Class"]("ui-datepicker-rtl");
	
			if (inst === $.datepicker._curInst && $.datepicker._datepickerShowing && $.datepicker._shouldFocusInput( inst ) ) {
				inst.input.focus();
			}
	
			// deffered render of the years select (to avoid flashes on Firefox)
			if( inst.yearshtml ){
				origyearshtml = inst.yearshtml;
				setTimeout(function(){
					//assure that inst.yearshtml didn't change.
					if( origyearshtml === inst.yearshtml && inst.yearshtml ){
						inst.dpDiv.find("select.ui-datepicker-year:first").replaceWith(inst.yearshtml);
					}
					origyearshtml = inst.yearshtml = null;
				}, 0);
			}
		},
	
		// #6694 - don't focus the input if it's already focused
		// this breaks the change event in IE
		// Support: IE and jQuery <1.9
		_shouldFocusInput: function( inst ) {
			return inst.input && inst.input.is( ":visible" ) && !inst.input.is( ":disabled" ) && !inst.input.is( ":focus" );
		},
	
		/* Check positioning to remain on screen. */
		_checkOffset: function(inst, offset, isFixed) {
			var dpWidth = inst.dpDiv.outerWidth(),
				dpHeight = inst.dpDiv.outerHeight(),
				inputWidth = inst.input ? inst.input.outerWidth() : 0,
				inputHeight = inst.input ? inst.input.outerHeight() : 0,
				viewWidth = document.documentElement.clientWidth + (isFixed ? 0 : $(document).scrollLeft()),
				viewHeight = document.documentElement.clientHeight + (isFixed ? 0 : $(document).scrollTop());
	
			offset.left -= (this._get(inst, "isRTL") ? (dpWidth - inputWidth) : 0);
			offset.left -= (isFixed && offset.left === inst.input.offset().left) ? $(document).scrollLeft() : 0;
			offset.top -= (isFixed && offset.top === (inst.input.offset().top + inputHeight)) ? $(document).scrollTop() : 0;
	
			// now check if datepicker is showing outside window viewport - move to a better place if so.
			offset.left -= Math.min(offset.left, (offset.left + dpWidth > viewWidth && viewWidth > dpWidth) ?
				Math.abs(offset.left + dpWidth - viewWidth) : 0);
			offset.top -= Math.min(offset.top, (offset.top + dpHeight > viewHeight && viewHeight > dpHeight) ?
				Math.abs(dpHeight + inputHeight) : 0);
	
			return offset;
		},
	
		/* Find an object's position on the screen. */
		_findPos: function(obj) {
			var position,
				inst = this._getInst(obj),
				isRTL = this._get(inst, "isRTL");
	
			while (obj && (obj.type === "hidden" || obj.nodeType !== 1 || $.expr.filters.hidden(obj))) {
				obj = obj[isRTL ? "previousSibling" : "nextSibling"];
			}
	
			position = $(obj).offset();
			return [position.left, position.top];
		},
	
		/* Hide the date picker from view.
		 * @param  input  element - the input field attached to the date picker
		 */
		_hideDatepicker: function(input) {
			var showAnim, duration, postProcess, onClose,
				inst = this._curInst;
	
			if (!inst || (input && inst !== $.data(input, "datepicker"))) {
				return;
			}
	
			if (this._datepickerShowing) {
				showAnim = this._get(inst, "showAnim");
				duration = this._get(inst, "duration");
				postProcess = function() {
					$.datepicker._tidyDialog(inst);
				};
	
				// DEPRECATED: after BC for 1.8.x $.effects[ showAnim ] is not needed
				if ( $.effects && ( $.effects.effect[ showAnim ] || $.effects[ showAnim ] ) ) {
					inst.dpDiv.hide(showAnim, $.datepicker._get(inst, "showOptions"), duration, postProcess);
				} else {
					inst.dpDiv[(showAnim === "slideDown" ? "slideUp" :
						(showAnim === "fadeIn" ? "fadeOut" : "hide"))]((showAnim ? duration : null), postProcess);
				}
	
				if (!showAnim) {
					postProcess();
				}
				this._datepickerShowing = false;
	
				onClose = this._get(inst, "onClose");
				if (onClose) {
					onClose.apply((inst.input ? inst.input[0] : null), [(inst.input ? inst.input.val() : ""), inst]);
				}
	
				this._lastInput = null;
				if (this._inDialog) {
					this._dialogInput.css({ position: "absolute", left: "0", top: "-100px" });
					if ($.blockUI) {
						$.unblockUI();
						$("body").append(this.dpDiv);
					}
				}
				this._inDialog = false;
			}
		},
	
		/* Tidy up after a dialog display. */
		_tidyDialog: function(inst) {
			inst.dpDiv.removeClass(this._dialogClass).unbind(".ui-datepicker-calendar");
		},
	
		/* Close date picker if clicked elsewhere. */
		_checkExternalClick: function(event) {
			if (!$.datepicker._curInst) {
				return;
			}
	
			var $target = $(event.target),
				inst = $.datepicker._getInst($target[0]);
	
			if ( ( ( $target[0].id !== $.datepicker._mainDivId &&
					$target.parents("#" + $.datepicker._mainDivId).length === 0 &&
					!$target.hasClass($.datepicker.markerClassName) &&
					!$target.closest("." + $.datepicker._triggerClass).length &&
					$.datepicker._datepickerShowing && !($.datepicker._inDialog && $.blockUI) ) ) ||
				( $target.hasClass($.datepicker.markerClassName) && $.datepicker._curInst !== inst ) ) {
					$.datepicker._hideDatepicker();
			}
		},
	
		/* Adjust one of the date sub-fields. */
		_adjustDate: function(id, offset, period) {
			var target = $(id),
				inst = this._getInst(target[0]);
	
			if (this._isDisabledDatepicker(target[0])) {
				return;
			}
			this._adjustInstDate(inst, offset +
				(period === "M" ? this._get(inst, "showCurrentAtPos") : 0), // undo positioning
				period);
			this._updateDatepicker(inst);
		},
	
		/* Action for current link. */
		_gotoToday: function(id) {
			var date,
				target = $(id),
				inst = this._getInst(target[0]);
	
			if (this._get(inst, "gotoCurrent") && inst.currentDay) {
				inst.selectedDay = inst.currentDay;
				inst.drawMonth = inst.selectedMonth = inst.currentMonth;
				inst.drawYear = inst.selectedYear = inst.currentYear;
			} else {
				date = new Date();
				inst.selectedDay = date.getDate();
				inst.drawMonth = inst.selectedMonth = date.getMonth();
				inst.drawYear = inst.selectedYear = date.getFullYear();
			}
			this._notifyChange(inst);
			this._adjustDate(target);
		},
	
		/* Action for selecting a new month/year. */
		_selectMonthYear: function(id, select, period) {
			var target = $(id),
				inst = this._getInst(target[0]);
	
			inst["selected" + (period === "M" ? "Month" : "Year")] =
			inst["draw" + (period === "M" ? "Month" : "Year")] =
				parseInt(select.options[select.selectedIndex].value,10);
	
			this._notifyChange(inst);
			this._adjustDate(target);
		},
	
		/* Action for selecting a day. */
		_selectDay: function(id, month, year, td) {
			var inst,
				target = $(id);
	
			if ($(td).hasClass(this._unselectableClass) || this._isDisabledDatepicker(target[0])) {
				return;
			}
	
			inst = this._getInst(target[0]);
			inst.selectedDay = inst.currentDay = $("a", td).html();
			inst.selectedMonth = inst.currentMonth = month;
			inst.selectedYear = inst.currentYear = year;
			this._selectDate(id, this._formatDate(inst,
				inst.currentDay, inst.currentMonth, inst.currentYear));
		},
	
		/* Erase the input field and hide the date picker. */
		_clearDate: function(id) {
			var target = $(id);
			this._selectDate(target, "");
		},
	
		/* Update the input field with the selected date. */
		_selectDate: function(id, dateStr) {
			var onSelect,
				target = $(id),
				inst = this._getInst(target[0]);
	
			dateStr = (dateStr != null ? dateStr : this._formatDate(inst));
			if (inst.input) {
				inst.input.val(dateStr);
			}
			this._updateAlternate(inst);
	
			onSelect = this._get(inst, "onSelect");
			if (onSelect) {
				onSelect.apply((inst.input ? inst.input[0] : null), [dateStr, inst]);  // trigger custom callback
			} else if (inst.input) {
				inst.input.trigger("change"); // fire the change event
			}
	
			if (inst.inline){
				this._updateDatepicker(inst);
			} else {
				this._hideDatepicker();
				this._lastInput = inst.input[0];
				if (typeof(inst.input[0]) !== "object") {
					inst.input.focus(); // restore focus
				}
				this._lastInput = null;
			}
		},
	
		/* Update any alternate field to synchronise with the main field. */
		_updateAlternate: function(inst) {
			var altFormat, date, dateStr,
				altField = this._get(inst, "altField");
	
			if (altField) { // update alternate field too
				altFormat = this._get(inst, "altFormat") || this._get(inst, "dateFormat");
				date = this._getDate(inst);
				dateStr = this.formatDate(altFormat, date, this._getFormatConfig(inst));
				$(altField).each(function() { $(this).val(dateStr); });
			}
		},
	
		/* Set as beforeShowDay function to prevent selection of weekends.
		 * @param  date  Date - the date to customise
		 * @return [boolean, string] - is this date selectable?, what is its CSS class?
		 */
		noWeekends: function(date) {
			var day = date.getDay();
			return [(day > 0 && day < 6), ""];
		},
	
		/* Set as calculateWeek to determine the week of the year based on the ISO 8601 definition.
		 * @param  date  Date - the date to get the week for
		 * @return  number - the number of the week within the year that contains this date
		 */
		iso8601Week: function(date) {
			var time,
				checkDate = new Date(date.getTime());
	
			// Find Thursday of this week starting on Monday
			checkDate.setDate(checkDate.getDate() + 4 - (checkDate.getDay() || 7));
	
			time = checkDate.getTime();
			checkDate.setMonth(0); // Compare with Jan 1
			checkDate.setDate(1);
			return Math.floor(Math.round((time - checkDate) / 86400000) / 7) + 1;
		},
	
		/* Parse a string value into a date object.
		 * See formatDate below for the possible formats.
		 *
		 * @param  format string - the expected format of the date
		 * @param  value string - the date in the above format
		 * @param  settings Object - attributes include:
		 *					shortYearCutoff  number - the cutoff year for determining the century (optional)
		 *					dayNamesShort	string[7] - abbreviated names of the days from Sunday (optional)
		 *					dayNames		string[7] - names of the days from Sunday (optional)
		 *					monthNamesShort string[12] - abbreviated names of the months (optional)
		 *					monthNames		string[12] - names of the months (optional)
		 * @return  Date - the extracted date value or null if value is blank
		 */
		parseDate: function (format, value, settings) {
			if (format == null || value == null) {
				throw "Invalid arguments";
			}
	
			value = (typeof value === "object" ? value.toString() : value + "");
			if (value === "") {
				return null;
			}
	
			var iFormat, dim, extra,
				iValue = 0,
				shortYearCutoffTemp = (settings ? settings.shortYearCutoff : null) || this._defaults.shortYearCutoff,
				shortYearCutoff = (typeof shortYearCutoffTemp !== "string" ? shortYearCutoffTemp :
					new Date().getFullYear() % 100 + parseInt(shortYearCutoffTemp, 10)),
				dayNamesShort = (settings ? settings.dayNamesShort : null) || this._defaults.dayNamesShort,
				dayNames = (settings ? settings.dayNames : null) || this._defaults.dayNames,
				monthNamesShort = (settings ? settings.monthNamesShort : null) || this._defaults.monthNamesShort,
				monthNames = (settings ? settings.monthNames : null) || this._defaults.monthNames,
				year = -1,
				month = -1,
				day = -1,
				doy = -1,
				literal = false,
				date,
				// Check whether a format character is doubled
				lookAhead = function(match) {
					var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) === match);
					if (matches) {
						iFormat++;
					}
					return matches;
				},
				// Extract a number from the string value
				getNumber = function(match) {
					var isDoubled = lookAhead(match),
						size = (match === "@" ? 14 : (match === "!" ? 20 :
						(match === "y" && isDoubled ? 4 : (match === "o" ? 3 : 2)))),
						minSize = (match === "y" ? size : 1),
						digits = new RegExp("^\\d{" + minSize + "," + size + "}"),
						num = value.substring(iValue).match(digits);
					if (!num) {
						throw "Missing number at position " + iValue;
					}
					iValue += num[0].length;
					return parseInt(num[0], 10);
				},
				// Extract a name from the string value and convert to an index
				getName = function(match, shortNames, longNames) {
					var index = -1,
						names = $.map(lookAhead(match) ? longNames : shortNames, function (v, k) {
							return [ [k, v] ];
						}).sort(function (a, b) {
							return -(a[1].length - b[1].length);
						});
	
					$.each(names, function (i, pair) {
						var name = pair[1];
						if (value.substr(iValue, name.length).toLowerCase() === name.toLowerCase()) {
							index = pair[0];
							iValue += name.length;
							return false;
						}
					});
					if (index !== -1) {
						return index + 1;
					} else {
						throw "Unknown name at position " + iValue;
					}
				},
				// Confirm that a literal character matches the string value
				checkLiteral = function() {
					if (value.charAt(iValue) !== format.charAt(iFormat)) {
						throw "Unexpected literal at position " + iValue;
					}
					iValue++;
				};
	
			for (iFormat = 0; iFormat < format.length; iFormat++) {
				if (literal) {
					if (format.charAt(iFormat) === "'" && !lookAhead("'")) {
						literal = false;
					} else {
						checkLiteral();
					}
				} else {
					switch (format.charAt(iFormat)) {
						case "d":
							day = getNumber("d");
							break;
						case "D":
							getName("D", dayNamesShort, dayNames);
							break;
						case "o":
							doy = getNumber("o");
							break;
						case "m":
							month = getNumber("m");
							break;
						case "M":
							month = getName("M", monthNamesShort, monthNames);
							break;
						case "y":
							year = getNumber("y");
							break;
						case "@":
							date = new Date(getNumber("@"));
							year = date.getFullYear();
							month = date.getMonth() + 1;
							day = date.getDate();
							break;
						case "!":
							date = new Date((getNumber("!") - this._ticksTo1970) / 10000);
							year = date.getFullYear();
							month = date.getMonth() + 1;
							day = date.getDate();
							break;
						case "'":
							if (lookAhead("'")){
								checkLiteral();
							} else {
								literal = true;
							}
							break;
						default:
							checkLiteral();
					}
				}
			}
	
			if (iValue < value.length){
				extra = value.substr(iValue);
				if (!/^\s+/.test(extra)) {
					throw "Extra/unparsed characters found in date: " + extra;
				}
			}
	
			if (year === -1) {
				year = new Date().getFullYear();
			} else if (year < 100) {
				year += new Date().getFullYear() - new Date().getFullYear() % 100 +
					(year <= shortYearCutoff ? 0 : -100);
			}
	
			if (doy > -1) {
				month = 1;
				day = doy;
				do {
					dim = this._getDaysInMonth(year, month - 1);
					if (day <= dim) {
						break;
					}
					month++;
					day -= dim;
				} while (true);
			}
	
			date = this._daylightSavingAdjust(new Date(year, month - 1, day));
			if (date.getFullYear() !== year || date.getMonth() + 1 !== month || date.getDate() !== day) {
				throw "Invalid date"; // E.g. 31/02/00
			}
			return date;
		},
	
		/* Standard date formats. */
		ATOM: "yy-mm-dd", // RFC 3339 (ISO 8601)
		COOKIE: "D, dd M yy",
		ISO_8601: "yy-mm-dd",
		RFC_822: "D, d M y",
		RFC_850: "DD, dd-M-y",
		RFC_1036: "D, d M y",
		RFC_1123: "D, d M yy",
		RFC_2822: "D, d M yy",
		RSS: "D, d M y", // RFC 822
		TICKS: "!",
		TIMESTAMP: "@",
		W3C: "yy-mm-dd", // ISO 8601
	
		_ticksTo1970: (((1970 - 1) * 365 + Math.floor(1970 / 4) - Math.floor(1970 / 100) +
			Math.floor(1970 / 400)) * 24 * 60 * 60 * 10000000),
	
		/* Format a date object into a string value.
		 * The format can be combinations of the following:
		 * d  - day of month (no leading zero)
		 * dd - day of month (two digit)
		 * o  - day of year (no leading zeros)
		 * oo - day of year (three digit)
		 * D  - day name short
		 * DD - day name long
		 * m  - month of year (no leading zero)
		 * mm - month of year (two digit)
		 * M  - month name short
		 * MM - month name long
		 * y  - year (two digit)
		 * yy - year (four digit)
		 * @ - Unix timestamp (ms since 01/01/1970)
		 * ! - Windows ticks (100ns since 01/01/0001)
		 * "..." - literal text
		 * '' - single quote
		 *
		 * @param  format string - the desired format of the date
		 * @param  date Date - the date value to format
		 * @param  settings Object - attributes include:
		 *					dayNamesShort	string[7] - abbreviated names of the days from Sunday (optional)
		 *					dayNames		string[7] - names of the days from Sunday (optional)
		 *					monthNamesShort string[12] - abbreviated names of the months (optional)
		 *					monthNames		string[12] - names of the months (optional)
		 * @return  string - the date in the above format
		 */
		formatDate: function (format, date, settings) {
			if (!date) {
				return "";
			}
	
			var iFormat,
				dayNamesShort = (settings ? settings.dayNamesShort : null) || this._defaults.dayNamesShort,
				dayNames = (settings ? settings.dayNames : null) || this._defaults.dayNames,
				monthNamesShort = (settings ? settings.monthNamesShort : null) || this._defaults.monthNamesShort,
				monthNames = (settings ? settings.monthNames : null) || this._defaults.monthNames,
				// Check whether a format character is doubled
				lookAhead = function(match) {
					var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) === match);
					if (matches) {
						iFormat++;
					}
					return matches;
				},
				// Format a number, with leading zero if necessary
				formatNumber = function(match, value, len) {
					var num = "" + value;
					if (lookAhead(match)) {
						while (num.length < len) {
							num = "0" + num;
						}
					}
					return num;
				},
				// Format a name, short or long as requested
				formatName = function(match, value, shortNames, longNames) {
					return (lookAhead(match) ? longNames[value] : shortNames[value]);
				},
				output = "",
				literal = false;
	
			if (date) {
				for (iFormat = 0; iFormat < format.length; iFormat++) {
					if (literal) {
						if (format.charAt(iFormat) === "'" && !lookAhead("'")) {
							literal = false;
						} else {
							output += format.charAt(iFormat);
						}
					} else {
						switch (format.charAt(iFormat)) {
							case "d":
								output += formatNumber("d", date.getDate(), 2);
								break;
							case "D":
								output += formatName("D", date.getDay(), dayNamesShort, dayNames);
								break;
							case "o":
								output += formatNumber("o",
									Math.round((new Date(date.getFullYear(), date.getMonth(), date.getDate()).getTime() - new Date(date.getFullYear(), 0, 0).getTime()) / 86400000), 3);
								break;
							case "m":
								output += formatNumber("m", date.getMonth() + 1, 2);
								break;
							case "M":
								output += formatName("M", date.getMonth(), monthNamesShort, monthNames);
								break;
							case "y":
								output += (lookAhead("y") ? date.getFullYear() :
									(date.getYear() % 100 < 10 ? "0" : "") + date.getYear() % 100);
								break;
							case "@":
								output += date.getTime();
								break;
							case "!":
								output += date.getTime() * 10000 + this._ticksTo1970;
								break;
							case "'":
								if (lookAhead("'")) {
									output += "'";
								} else {
									literal = true;
								}
								break;
							default:
								output += format.charAt(iFormat);
						}
					}
				}
			}
			return output;
		},
	
		/* Extract all possible characters from the date format. */
		_possibleChars: function (format) {
			var iFormat,
				chars = "",
				literal = false,
				// Check whether a format character is doubled
				lookAhead = function(match) {
					var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) === match);
					if (matches) {
						iFormat++;
					}
					return matches;
				};
	
			for (iFormat = 0; iFormat < format.length; iFormat++) {
				if (literal) {
					if (format.charAt(iFormat) === "'" && !lookAhead("'")) {
						literal = false;
					} else {
						chars += format.charAt(iFormat);
					}
				} else {
					switch (format.charAt(iFormat)) {
						case "d": case "m": case "y": case "@":
							chars += "0123456789";
							break;
						case "D": case "M":
							return null; // Accept anything
						case "'":
							if (lookAhead("'")) {
								chars += "'";
							} else {
								literal = true;
							}
							break;
						default:
							chars += format.charAt(iFormat);
					}
				}
			}
			return chars;
		},
	
		/* Get a setting value, defaulting if necessary. */
		_get: function(inst, name) {
			return inst.settings[name] !== undefined ?
				inst.settings[name] : this._defaults[name];
		},
	
		/* Parse existing date and initialise date picker. */
		_setDateFromField: function(inst, noDefault) {
			if (inst.input.val() === inst.lastVal) {
				return;
			}
	
			var dateFormat = this._get(inst, "dateFormat"),
				dates = inst.lastVal = inst.input ? inst.input.val() : null,
				defaultDate = this._getDefaultDate(inst),
				date = defaultDate,
				settings = this._getFormatConfig(inst);
	
			try {
				date = this.parseDate(dateFormat, dates, settings) || defaultDate;
			} catch (event) {
				dates = (noDefault ? "" : dates);
			}
			inst.selectedDay = date.getDate();
			inst.drawMonth = inst.selectedMonth = date.getMonth();
			inst.drawYear = inst.selectedYear = date.getFullYear();
			inst.currentDay = (dates ? date.getDate() : 0);
			inst.currentMonth = (dates ? date.getMonth() : 0);
			inst.currentYear = (dates ? date.getFullYear() : 0);
			this._adjustInstDate(inst);
		},
	
		/* Retrieve the default date shown on opening. */
		_getDefaultDate: function(inst) {
			return this._restrictMinMax(inst,
				this._determineDate(inst, this._get(inst, "defaultDate"), new Date()));
		},
	
		/* A date may be specified as an exact value or a relative one. */
		_determineDate: function(inst, date, defaultDate) {
			var offsetNumeric = function(offset) {
					var date = new Date();
					date.setDate(date.getDate() + offset);
					return date;
				},
				offsetString = function(offset) {
					try {
						return $.datepicker.parseDate($.datepicker._get(inst, "dateFormat"),
							offset, $.datepicker._getFormatConfig(inst));
					}
					catch (e) {
						// Ignore
					}
	
					var date = (offset.toLowerCase().match(/^c/) ?
						$.datepicker._getDate(inst) : null) || new Date(),
						year = date.getFullYear(),
						month = date.getMonth(),
						day = date.getDate(),
						pattern = /([+\-]?[0-9]+)\s*(d|D|w|W|m|M|y|Y)?/g,
						matches = pattern.exec(offset);
	
					while (matches) {
						switch (matches[2] || "d") {
							case "d" : case "D" :
								day += parseInt(matches[1],10); break;
							case "w" : case "W" :
								day += parseInt(matches[1],10) * 7; break;
							case "m" : case "M" :
								month += parseInt(matches[1],10);
								day = Math.min(day, $.datepicker._getDaysInMonth(year, month));
								break;
							case "y": case "Y" :
								year += parseInt(matches[1],10);
								day = Math.min(day, $.datepicker._getDaysInMonth(year, month));
								break;
						}
						matches = pattern.exec(offset);
					}
					return new Date(year, month, day);
				},
				newDate = (date == null || date === "" ? defaultDate : (typeof date === "string" ? offsetString(date) :
					(typeof date === "number" ? (isNaN(date) ? defaultDate : offsetNumeric(date)) : new Date(date.getTime()))));
	
			newDate = (newDate && newDate.toString() === "Invalid Date" ? defaultDate : newDate);
			if (newDate) {
				newDate.setHours(0);
				newDate.setMinutes(0);
				newDate.setSeconds(0);
				newDate.setMilliseconds(0);
			}
			return this._daylightSavingAdjust(newDate);
		},
	
		/* Handle switch to/from daylight saving.
		 * Hours may be non-zero on daylight saving cut-over:
		 * > 12 when midnight changeover, but then cannot generate
		 * midnight datetime, so jump to 1AM, otherwise reset.
		 * @param  date  (Date) the date to check
		 * @return  (Date) the corrected date
		 */
		_daylightSavingAdjust: function(date) {
			if (!date) {
				return null;
			}
			date.setHours(date.getHours() > 12 ? date.getHours() + 2 : 0);
			return date;
		},
	
		/* Set the date(s) directly. */
		_setDate: function(inst, date, noChange) {
			var clear = !date,
				origMonth = inst.selectedMonth,
				origYear = inst.selectedYear,
				newDate = this._restrictMinMax(inst, this._determineDate(inst, date, new Date()));
	
			inst.selectedDay = inst.currentDay = newDate.getDate();
			inst.drawMonth = inst.selectedMonth = inst.currentMonth = newDate.getMonth();
			inst.drawYear = inst.selectedYear = inst.currentYear = newDate.getFullYear();
			if ((origMonth !== inst.selectedMonth || origYear !== inst.selectedYear) && !noChange) {
				this._notifyChange(inst);
			}
			this._adjustInstDate(inst);
			if (inst.input) {
				inst.input.val(clear ? "" : this._formatDate(inst));
			}
		},
	
		/* Retrieve the date(s) directly. */
		_getDate: function(inst) {
			var startDate = (!inst.currentYear || (inst.input && inst.input.val() === "") ? null :
				this._daylightSavingAdjust(new Date(
				inst.currentYear, inst.currentMonth, inst.currentDay)));
				return startDate;
		},
	
		/* Attach the onxxx handlers.  These are declared statically so
		 * they work with static code transformers like Caja.
		 */
		_attachHandlers: function(inst) {
			var stepMonths = this._get(inst, "stepMonths"),
				id = "#" + inst.id.replace( /\\\\/g, "\\" );
			inst.dpDiv.find("[data-handler]").map(function () {
				var handler = {
					prev: function () {
						$.datepicker._adjustDate(id, -stepMonths, "M");
					},
					next: function () {
						$.datepicker._adjustDate(id, +stepMonths, "M");
					},
					hide: function () {
						$.datepicker._hideDatepicker();
					},
					today: function () {
						$.datepicker._gotoToday(id);
					},
					selectDay: function () {
						$.datepicker._selectDay(id, +this.getAttribute("data-month"), +this.getAttribute("data-year"), this);
						return false;
					},
					selectMonth: function () {
						$.datepicker._selectMonthYear(id, this, "M");
						return false;
					},
					selectYear: function () {
						$.datepicker._selectMonthYear(id, this, "Y");
						return false;
					}
				};
				$(this).bind(this.getAttribute("data-event"), handler[this.getAttribute("data-handler")]);
			});
		},
	
		/* Generate the HTML for the current state of the date picker. */
		_generateHTML: function(inst) {
			var maxDraw, prevText, prev, nextText, next, currentText, gotoDate,
				controls, buttonPanel, firstDay, showWeek, dayNames, dayNamesMin,
				monthNames, monthNamesShort, beforeShowDay, showOtherMonths,
				selectOtherMonths, defaultDate, html, dow, row, group, col, selectedDate,
				cornerClass, calender, thead, day, daysInMonth, leadDays, curRows, numRows,
				printDate, dRow, tbody, daySettings, otherMonth, unselectable,
				tempDate = new Date(),
				today = this._daylightSavingAdjust(
					new Date(tempDate.getFullYear(), tempDate.getMonth(), tempDate.getDate())), // clear time
				isRTL = this._get(inst, "isRTL"),
				showButtonPanel = this._get(inst, "showButtonPanel"),
				hideIfNoPrevNext = this._get(inst, "hideIfNoPrevNext"),
				navigationAsDateFormat = this._get(inst, "navigationAsDateFormat"),
				numMonths = this._getNumberOfMonths(inst),
				showCurrentAtPos = this._get(inst, "showCurrentAtPos"),
				stepMonths = this._get(inst, "stepMonths"),
				isMultiMonth = (numMonths[0] !== 1 || numMonths[1] !== 1),
				currentDate = this._daylightSavingAdjust((!inst.currentDay ? new Date(9999, 9, 9) :
					new Date(inst.currentYear, inst.currentMonth, inst.currentDay))),
				minDate = this._getMinMaxDate(inst, "min"),
				maxDate = this._getMinMaxDate(inst, "max"),
				drawMonth = inst.drawMonth - showCurrentAtPos,
				drawYear = inst.drawYear;
	
			if (drawMonth < 0) {
				drawMonth += 12;
				drawYear--;
			}
			if (maxDate) {
				maxDraw = this._daylightSavingAdjust(new Date(maxDate.getFullYear(),
					maxDate.getMonth() - (numMonths[0] * numMonths[1]) + 1, maxDate.getDate()));
				maxDraw = (minDate && maxDraw < minDate ? minDate : maxDraw);
				while (this._daylightSavingAdjust(new Date(drawYear, drawMonth, 1)) > maxDraw) {
					drawMonth--;
					if (drawMonth < 0) {
						drawMonth = 11;
						drawYear--;
					}
				}
			}
			inst.drawMonth = drawMonth;
			inst.drawYear = drawYear;
	
			prevText = this._get(inst, "prevText");
			prevText = (!navigationAsDateFormat ? prevText : this.formatDate(prevText,
				this._daylightSavingAdjust(new Date(drawYear, drawMonth - stepMonths, 1)),
				this._getFormatConfig(inst)));
	
			prev = (this._canAdjustMonth(inst, -1, drawYear, drawMonth) ?
				"<a class='ui-datepicker-prev ui-corner-all' data-handler='prev' data-event='click'" +
				" title='" + prevText + "'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "e" : "w") + "'>" + prevText + "</span></a>" :
				(hideIfNoPrevNext ? "" : "<a class='ui-datepicker-prev ui-corner-all ui-state-disabled' title='"+ prevText +"'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "e" : "w") + "'>" + prevText + "</span></a>"));
	
			nextText = this._get(inst, "nextText");
			nextText = (!navigationAsDateFormat ? nextText : this.formatDate(nextText,
				this._daylightSavingAdjust(new Date(drawYear, drawMonth + stepMonths, 1)),
				this._getFormatConfig(inst)));
	
			next = (this._canAdjustMonth(inst, +1, drawYear, drawMonth) ?
				"<a class='ui-datepicker-next ui-corner-all' data-handler='next' data-event='click'" +
				" title='" + nextText + "'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "w" : "e") + "'>" + nextText + "</span></a>" :
				(hideIfNoPrevNext ? "" : "<a class='ui-datepicker-next ui-corner-all ui-state-disabled' title='"+ nextText + "'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "w" : "e") + "'>" + nextText + "</span></a>"));
	
			currentText = this._get(inst, "currentText");
			gotoDate = (this._get(inst, "gotoCurrent") && inst.currentDay ? currentDate : today);
			currentText = (!navigationAsDateFormat ? currentText :
				this.formatDate(currentText, gotoDate, this._getFormatConfig(inst)));
	
			controls = (!inst.inline ? "<button type='button' class='ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all' data-handler='hide' data-event='click'>" +
				this._get(inst, "closeText") + "</button>" : "");
	
			buttonPanel = (showButtonPanel) ? "<div class='ui-datepicker-buttonpane ui-widget-content'>" + (isRTL ? controls : "") +
				(this._isInRange(inst, gotoDate) ? "<button type='button' class='ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all' data-handler='today' data-event='click'" +
				">" + currentText + "</button>" : "") + (isRTL ? "" : controls) + "</div>" : "";
	
			firstDay = parseInt(this._get(inst, "firstDay"),10);
			firstDay = (isNaN(firstDay) ? 0 : firstDay);
	
			showWeek = this._get(inst, "showWeek");
			dayNames = this._get(inst, "dayNames");
			dayNamesMin = this._get(inst, "dayNamesMin");
			monthNames = this._get(inst, "monthNames");
			monthNamesShort = this._get(inst, "monthNamesShort");
			beforeShowDay = this._get(inst, "beforeShowDay");
			showOtherMonths = this._get(inst, "showOtherMonths");
			selectOtherMonths = this._get(inst, "selectOtherMonths");
			defaultDate = this._getDefaultDate(inst);
			html = "";
			dow;
			for (row = 0; row < numMonths[0]; row++) {
				group = "";
				this.maxRows = 4;
				for (col = 0; col < numMonths[1]; col++) {
					selectedDate = this._daylightSavingAdjust(new Date(drawYear, drawMonth, inst.selectedDay));
					cornerClass = " ui-corner-all";
					calender = "";
					if (isMultiMonth) {
						calender += "<div class='ui-datepicker-group";
						if (numMonths[1] > 1) {
							switch (col) {
								case 0: calender += " ui-datepicker-group-first";
									cornerClass = " ui-corner-" + (isRTL ? "right" : "left"); break;
								case numMonths[1]-1: calender += " ui-datepicker-group-last";
									cornerClass = " ui-corner-" + (isRTL ? "left" : "right"); break;
								default: calender += " ui-datepicker-group-middle"; cornerClass = ""; break;
							}
						}
						calender += "'>";
					}
					calender += "<div class='ui-datepicker-header ui-widget-header ui-helper-clearfix" + cornerClass + "'>" +
						(/all|left/.test(cornerClass) && row === 0 ? (isRTL ? next : prev) : "") +
						(/all|right/.test(cornerClass) && row === 0 ? (isRTL ? prev : next) : "") +
						this._generateMonthYearHeader(inst, drawMonth, drawYear, minDate, maxDate,
						row > 0 || col > 0, monthNames, monthNamesShort) + // draw month headers
						"</div><table class='ui-datepicker-calendar'><thead>" +
						"<tr>";
					thead = (showWeek ? "<th class='ui-datepicker-week-col'>" + this._get(inst, "weekHeader") + "</th>" : "");
					for (dow = 0; dow < 7; dow++) { // days of the week
						day = (dow + firstDay) % 7;
						thead += "<th scope='col'" + ((dow + firstDay + 6) % 7 >= 5 ? " class='ui-datepicker-week-end'" : "") + ">" +
							"<span title='" + dayNames[day] + "'>" + dayNamesMin[day] + "</span></th>";
					}
					calender += thead + "</tr></thead><tbody>";
					daysInMonth = this._getDaysInMonth(drawYear, drawMonth);
					if (drawYear === inst.selectedYear && drawMonth === inst.selectedMonth) {
						inst.selectedDay = Math.min(inst.selectedDay, daysInMonth);
					}
					leadDays = (this._getFirstDayOfMonth(drawYear, drawMonth) - firstDay + 7) % 7;
					curRows = Math.ceil((leadDays + daysInMonth) / 7); // calculate the number of rows to generate
					numRows = (isMultiMonth ? this.maxRows > curRows ? this.maxRows : curRows : curRows); //If multiple months, use the higher number of rows (see #7043)
					this.maxRows = numRows;
					printDate = this._daylightSavingAdjust(new Date(drawYear, drawMonth, 1 - leadDays));
					for (dRow = 0; dRow < numRows; dRow++) { // create date picker rows
						calender += "<tr>";
						tbody = (!showWeek ? "" : "<td class='ui-datepicker-week-col'>" +
							this._get(inst, "calculateWeek")(printDate) + "</td>");
						for (dow = 0; dow < 7; dow++) { // create date picker days
							daySettings = (beforeShowDay ?
								beforeShowDay.apply((inst.input ? inst.input[0] : null), [printDate]) : [true, ""]);
							otherMonth = (printDate.getMonth() !== drawMonth);
							unselectable = (otherMonth && !selectOtherMonths) || !daySettings[0] ||
								(minDate && printDate < minDate) || (maxDate && printDate > maxDate);
							tbody += "<td class='" +
								((dow + firstDay + 6) % 7 >= 5 ? " ui-datepicker-week-end" : "") + // highlight weekends
								(otherMonth ? " ui-datepicker-other-month" : "") + // highlight days from other months
								((printDate.getTime() === selectedDate.getTime() && drawMonth === inst.selectedMonth && inst._keyEvent) || // user pressed key
								(defaultDate.getTime() === printDate.getTime() && defaultDate.getTime() === selectedDate.getTime()) ?
								// or defaultDate is current printedDate and defaultDate is selectedDate
								" " + this._dayOverClass : "") + // highlight selected day
								(unselectable ? " " + this._unselectableClass + " ui-state-disabled": "") +  // highlight unselectable days
								(otherMonth && !showOtherMonths ? "" : " " + daySettings[1] + // highlight custom dates
								(printDate.getTime() === currentDate.getTime() ? " " + this._currentClass : "") + // highlight selected day
								(printDate.getTime() === today.getTime() ? " ui-datepicker-today" : "")) + "'" + // highlight today (if different)
								((!otherMonth || showOtherMonths) && daySettings[2] ? " title='" + daySettings[2].replace(/'/g, "&#39;") + "'" : "") + // cell title
								(unselectable ? "" : " data-handler='selectDay' data-event='click' data-month='" + printDate.getMonth() + "' data-year='" + printDate.getFullYear() + "'") + ">" + // actions
								(otherMonth && !showOtherMonths ? "&#xa0;" : // display for other months
								(unselectable ? "<span class='ui-state-default'>" + printDate.getDate() + "</span>" : "<a class='ui-state-default" +
								(printDate.getTime() === today.getTime() ? " ui-state-highlight" : "") +
								(printDate.getTime() === currentDate.getTime() ? " ui-state-active" : "") + // highlight selected day
								(otherMonth ? " ui-priority-secondary" : "") + // distinguish dates from other months
								"' href='#'>" + printDate.getDate() + "</a>")) + "</td>"; // display selectable date
							printDate.setDate(printDate.getDate() + 1);
							printDate = this._daylightSavingAdjust(printDate);
						}
						calender += tbody + "</tr>";
					}
					drawMonth++;
					if (drawMonth > 11) {
						drawMonth = 0;
						drawYear++;
					}
					calender += "</tbody></table>" + (isMultiMonth ? "</div>" +
								((numMonths[0] > 0 && col === numMonths[1]-1) ? "<div class='ui-datepicker-row-break'></div>" : "") : "");
					group += calender;
				}
				html += group;
			}
			html += buttonPanel;
			inst._keyEvent = false;
			return html;
		},
	
		/* Generate the month and year header. */
		_generateMonthYearHeader: function(inst, drawMonth, drawYear, minDate, maxDate,
				secondary, monthNames, monthNamesShort) {
	
			var inMinYear, inMaxYear, month, years, thisYear, determineYear, year, endYear,
				changeMonth = this._get(inst, "changeMonth"),
				changeYear = this._get(inst, "changeYear"),
				showMonthAfterYear = this._get(inst, "showMonthAfterYear"),
				html = "<div class='ui-datepicker-title'>",
				monthHtml = "";
	
			// month selection
			if (secondary || !changeMonth) {
				monthHtml += "<span class='ui-datepicker-month'>" + monthNames[drawMonth] + "</span>";
			} else {
				inMinYear = (minDate && minDate.getFullYear() === drawYear);
				inMaxYear = (maxDate && maxDate.getFullYear() === drawYear);
				monthHtml += "<select class='ui-datepicker-month' data-handler='selectMonth' data-event='change'>";
				for ( month = 0; month < 12; month++) {
					if ((!inMinYear || month >= minDate.getMonth()) && (!inMaxYear || month <= maxDate.getMonth())) {
						monthHtml += "<option value='" + month + "'" +
							(month === drawMonth ? " selected='selected'" : "") +
							">" + monthNamesShort[month] + "</option>";
					}
				}
				monthHtml += "</select>";
			}
	
			if (!showMonthAfterYear) {
				html += monthHtml + (secondary || !(changeMonth && changeYear) ? "&#xa0;" : "");
			}
	
			// year selection
			if ( !inst.yearshtml ) {
				inst.yearshtml = "";
				if (secondary || !changeYear) {
					html += "<span class='ui-datepicker-year'>" + drawYear + "</span>";
				} else {
					// determine range of years to display
					years = this._get(inst, "yearRange").split(":");
					thisYear = new Date().getFullYear();
					determineYear = function(value) {
						var year = (value.match(/c[+\-].*/) ? drawYear + parseInt(value.substring(1), 10) :
							(value.match(/[+\-].*/) ? thisYear + parseInt(value, 10) :
							parseInt(value, 10)));
						return (isNaN(year) ? thisYear : year);
					};
					year = determineYear(years[0]);
					endYear = Math.max(year, determineYear(years[1] || ""));
					year = (minDate ? Math.max(year, minDate.getFullYear()) : year);
					endYear = (maxDate ? Math.min(endYear, maxDate.getFullYear()) : endYear);
					inst.yearshtml += "<select class='ui-datepicker-year' data-handler='selectYear' data-event='change'>";
					for (; year <= endYear; year++) {
						inst.yearshtml += "<option value='" + year + "'" +
							(year === drawYear ? " selected='selected'" : "") +
							">" + year + "</option>";
					}
					inst.yearshtml += "</select>";
	
					html += inst.yearshtml;
					inst.yearshtml = null;
				}
			}
	
			html += this._get(inst, "yearSuffix");
			if (showMonthAfterYear) {
				html += (secondary || !(changeMonth && changeYear) ? "&#xa0;" : "") + monthHtml;
			}
			html += "</div>"; // Close datepicker_header
			return html;
		},
	
		/* Adjust one of the date sub-fields. */
		_adjustInstDate: function(inst, offset, period) {
			var year = inst.drawYear + (period === "Y" ? offset : 0),
				month = inst.drawMonth + (period === "M" ? offset : 0),
				day = Math.min(inst.selectedDay, this._getDaysInMonth(year, month)) + (period === "D" ? offset : 0),
				date = this._restrictMinMax(inst, this._daylightSavingAdjust(new Date(year, month, day)));
	
			inst.selectedDay = date.getDate();
			inst.drawMonth = inst.selectedMonth = date.getMonth();
			inst.drawYear = inst.selectedYear = date.getFullYear();
			if (period === "M" || period === "Y") {
				this._notifyChange(inst);
			}
		},
	
		/* Ensure a date is within any min/max bounds. */
		_restrictMinMax: function(inst, date) {
			var minDate = this._getMinMaxDate(inst, "min"),
				maxDate = this._getMinMaxDate(inst, "max"),
				newDate = (minDate && date < minDate ? minDate : date);
			return (maxDate && newDate > maxDate ? maxDate : newDate);
		},
	
		/* Notify change of month/year. */
		_notifyChange: function(inst) {
			var onChange = this._get(inst, "onChangeMonthYear");
			if (onChange) {
				onChange.apply((inst.input ? inst.input[0] : null),
					[inst.selectedYear, inst.selectedMonth + 1, inst]);
			}
		},
	
		/* Determine the number of months to show. */
		_getNumberOfMonths: function(inst) {
			var numMonths = this._get(inst, "numberOfMonths");
			return (numMonths == null ? [1, 1] : (typeof numMonths === "number" ? [1, numMonths] : numMonths));
		},
	
		/* Determine the current maximum date - ensure no time components are set. */
		_getMinMaxDate: function(inst, minMax) {
			return this._determineDate(inst, this._get(inst, minMax + "Date"), null);
		},
	
		/* Find the number of days in a given month. */
		_getDaysInMonth: function(year, month) {
			return 32 - this._daylightSavingAdjust(new Date(year, month, 32)).getDate();
		},
	
		/* Find the day of the week of the first of a month. */
		_getFirstDayOfMonth: function(year, month) {
			return new Date(year, month, 1).getDay();
		},
	
		/* Determines if we should allow a "next/prev" month display change. */
		_canAdjustMonth: function(inst, offset, curYear, curMonth) {
			var numMonths = this._getNumberOfMonths(inst),
				date = this._daylightSavingAdjust(new Date(curYear,
				curMonth + (offset < 0 ? offset : numMonths[0] * numMonths[1]), 1));
	
			if (offset < 0) {
				date.setDate(this._getDaysInMonth(date.getFullYear(), date.getMonth()));
			}
			return this._isInRange(inst, date);
		},
	
		/* Is the given date in the accepted range? */
		_isInRange: function(inst, date) {
			var yearSplit, currentYear,
				minDate = this._getMinMaxDate(inst, "min"),
				maxDate = this._getMinMaxDate(inst, "max"),
				minYear = null,
				maxYear = null,
				years = this._get(inst, "yearRange");
				if (years){
					yearSplit = years.split(":");
					currentYear = new Date().getFullYear();
					minYear = parseInt(yearSplit[0], 10);
					maxYear = parseInt(yearSplit[1], 10);
					if ( yearSplit[0].match(/[+\-].*/) ) {
						minYear += currentYear;
					}
					if ( yearSplit[1].match(/[+\-].*/) ) {
						maxYear += currentYear;
					}
				}
	
			return ((!minDate || date.getTime() >= minDate.getTime()) &&
				(!maxDate || date.getTime() <= maxDate.getTime()) &&
				(!minYear || date.getFullYear() >= minYear) &&
				(!maxYear || date.getFullYear() <= maxYear));
		},
	
		/* Provide the configuration settings for formatting/parsing. */
		_getFormatConfig: function(inst) {
			var shortYearCutoff = this._get(inst, "shortYearCutoff");
			shortYearCutoff = (typeof shortYearCutoff !== "string" ? shortYearCutoff :
				new Date().getFullYear() % 100 + parseInt(shortYearCutoff, 10));
			return {shortYearCutoff: shortYearCutoff,
				dayNamesShort: this._get(inst, "dayNamesShort"), dayNames: this._get(inst, "dayNames"),
				monthNamesShort: this._get(inst, "monthNamesShort"), monthNames: this._get(inst, "monthNames")};
		},
	
		/* Format the given date for display. */
		_formatDate: function(inst, day, month, year) {
			if (!day) {
				inst.currentDay = inst.selectedDay;
				inst.currentMonth = inst.selectedMonth;
				inst.currentYear = inst.selectedYear;
			}
			var date = (day ? (typeof day === "object" ? day :
				this._daylightSavingAdjust(new Date(year, month, day))) :
				this._daylightSavingAdjust(new Date(inst.currentYear, inst.currentMonth, inst.currentDay)));
			return this.formatDate(this._get(inst, "dateFormat"), date, this._getFormatConfig(inst));
		}
	});
	
	/*
	 * Bind hover events for datepicker elements.
	 * Done via delegate so the binding only occurs once in the lifetime of the parent div.
	 * Global datepicker_instActive, set by _updateDatepicker allows the handlers to find their way back to the active picker.
	 */
	function datepicker_bindHover(dpDiv) {
		var selector = "button, .ui-datepicker-prev, .ui-datepicker-next, .ui-datepicker-calendar td a";
		return dpDiv.delegate(selector, "mouseout", function() {
				$(this).removeClass("ui-state-hover");
				if (this.className.indexOf("ui-datepicker-prev") !== -1) {
					$(this).removeClass("ui-datepicker-prev-hover");
				}
				if (this.className.indexOf("ui-datepicker-next") !== -1) {
					$(this).removeClass("ui-datepicker-next-hover");
				}
			})
			.delegate( selector, "mouseover", datepicker_handleMouseover );
	}
	
	function datepicker_handleMouseover() {
		if (!$.datepicker._isDisabledDatepicker( datepicker_instActive.inline? datepicker_instActive.dpDiv.parent()[0] : datepicker_instActive.input[0])) {
			$(this).parents(".ui-datepicker-calendar").find("a").removeClass("ui-state-hover");
			$(this).addClass("ui-state-hover");
			if (this.className.indexOf("ui-datepicker-prev") !== -1) {
				$(this).addClass("ui-datepicker-prev-hover");
			}
			if (this.className.indexOf("ui-datepicker-next") !== -1) {
				$(this).addClass("ui-datepicker-next-hover");
			}
		}
	}
	
	/* jQuery extend now ignores nulls! */
	function datepicker_extendRemove(target, props) {
		$.extend(target, props);
		for (var name in props) {
			if (props[name] == null) {
				target[name] = props[name];
			}
		}
		return target;
	}
	
	/* Invoke the datepicker functionality.
	   @param  options  string - a command, optionally followed by additional parameters or
						Object - settings for attaching new datepicker functionality
	   @return  jQuery object */
	$.fn.datepicker = function(options){
	
		/* Verify an empty collection wasn't passed - Fixes #6976 */
		if ( !this.length ) {
			return this;
		}
	
		/* Initialise the date picker. */
		if (!$.datepicker.initialized) {
			$(document).mousedown($.datepicker._checkExternalClick);
			$.datepicker.initialized = true;
		}
	
		/* Append datepicker main container to body if not exist. */
		if ($("#"+$.datepicker._mainDivId).length === 0) {
			$("body").append($.datepicker.dpDiv);
		}
	
		var otherArgs = Array.prototype.slice.call(arguments, 1);
		if (typeof options === "string" && (options === "isDisabled" || options === "getDate" || options === "widget")) {
			return $.datepicker["_" + options + "Datepicker"].
				apply($.datepicker, [this[0]].concat(otherArgs));
		}
		if (options === "option" && arguments.length === 2 && typeof arguments[1] === "string") {
			return $.datepicker["_" + options + "Datepicker"].
				apply($.datepicker, [this[0]].concat(otherArgs));
		}
		return this.each(function() {
			typeof options === "string" ?
				$.datepicker["_" + options + "Datepicker"].
					apply($.datepicker, [this].concat(otherArgs)) :
				$.datepicker._attachDatepicker(this, options);
		});
	};
	
	$.datepicker = new Datepicker(); // singleton instance
	$.datepicker.initialized = false;
	$.datepicker.uuid = new Date().getTime();
	$.datepicker.version = "1.11.4";
	
	var datepicker = $.datepicker;
	
	
	/*!
	 * jQuery UI Draggable 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/draggable/
	 */
	
	
	$.widget("ui.draggable", $.ui.mouse, {
		version: "1.11.4",
		widgetEventPrefix: "drag",
		options: {
			addClasses: true,
			appendTo: "parent",
			axis: false,
			connectToSortable: false,
			containment: false,
			cursor: "auto",
			cursorAt: false,
			grid: false,
			handle: false,
			helper: "original",
			iframeFix: false,
			opacity: false,
			refreshPositions: false,
			revert: false,
			revertDuration: 500,
			scope: "default",
			scroll: true,
			scrollSensitivity: 20,
			scrollSpeed: 20,
			snap: false,
			snapMode: "both",
			snapTolerance: 20,
			stack: false,
			zIndex: false,
	
			// callbacks
			drag: null,
			start: null,
			stop: null
		},
		_create: function() {
	
			if ( this.options.helper === "original" ) {
				this._setPositionRelative();
			}
			if (this.options.addClasses){
				this.element.addClass("ui-draggable");
			}
			if (this.options.disabled){
				this.element.addClass("ui-draggable-disabled");
			}
			this._setHandleClassName();
	
			this._mouseInit();
		},
	
		_setOption: function( key, value ) {
			this._super( key, value );
			if ( key === "handle" ) {
				this._removeHandleClassName();
				this._setHandleClassName();
			}
		},
	
		_destroy: function() {
			if ( ( this.helper || this.element ).is( ".ui-draggable-dragging" ) ) {
				this.destroyOnClear = true;
				return;
			}
			this.element.removeClass( "ui-draggable ui-draggable-dragging ui-draggable-disabled" );
			this._removeHandleClassName();
			this._mouseDestroy();
		},
	
		_mouseCapture: function(event) {
			var o = this.options;
	
			this._blurActiveElement( event );
	
			// among others, prevent a drag on a resizable-handle
			if (this.helper || o.disabled || $(event.target).closest(".ui-resizable-handle").length > 0) {
				return false;
			}
	
			//Quit if we're not on a valid handle
			this.handle = this._getHandle(event);
			if (!this.handle) {
				return false;
			}
	
			this._blockFrames( o.iframeFix === true ? "iframe" : o.iframeFix );
	
			return true;
	
		},
	
		_blockFrames: function( selector ) {
			this.iframeBlocks = this.document.find( selector ).map(function() {
				var iframe = $( this );
	
				return $( "<div>" )
					.css( "position", "absolute" )
					.appendTo( iframe.parent() )
					.outerWidth( iframe.outerWidth() )
					.outerHeight( iframe.outerHeight() )
					.offset( iframe.offset() )[ 0 ];
			});
		},
	
		_unblockFrames: function() {
			if ( this.iframeBlocks ) {
				this.iframeBlocks.remove();
				delete this.iframeBlocks;
			}
		},
	
		_blurActiveElement: function( event ) {
			var document = this.document[ 0 ];
	
			// Only need to blur if the event occurred on the draggable itself, see #10527
			if ( !this.handleElement.is( event.target ) ) {
				return;
			}
	
			// support: IE9
			// IE9 throws an "Unspecified error" accessing document.activeElement from an <iframe>
			try {
	
				// Support: IE9, IE10
				// If the <body> is blurred, IE will switch windows, see #9520
				if ( document.activeElement && document.activeElement.nodeName.toLowerCase() !== "body" ) {
	
					// Blur any element that currently has focus, see #4261
					$( document.activeElement ).blur();
				}
			} catch ( error ) {}
		},
	
		_mouseStart: function(event) {
	
			var o = this.options;
	
			//Create and append the visible helper
			this.helper = this._createHelper(event);
	
			this.helper.addClass("ui-draggable-dragging");
	
			//Cache the helper size
			this._cacheHelperProportions();
	
			//If ddmanager is used for droppables, set the global draggable
			if ($.ui.ddmanager) {
				$.ui.ddmanager.current = this;
			}
	
			/*
			 * - Position generation -
			 * This block generates everything position related - it's the core of draggables.
			 */
	
			//Cache the margins of the original element
			this._cacheMargins();
	
			//Store the helper's css position
			this.cssPosition = this.helper.css( "position" );
			this.scrollParent = this.helper.scrollParent( true );
			this.offsetParent = this.helper.offsetParent();
			this.hasFixedAncestor = this.helper.parents().filter(function() {
					return $( this ).css( "position" ) === "fixed";
				}).length > 0;
	
			//The element's absolute position on the page minus margins
			this.positionAbs = this.element.offset();
			this._refreshOffsets( event );
	
			//Generate the original position
			this.originalPosition = this.position = this._generatePosition( event, false );
			this.originalPageX = event.pageX;
			this.originalPageY = event.pageY;
	
			//Adjust the mouse offset relative to the helper if "cursorAt" is supplied
			(o.cursorAt && this._adjustOffsetFromHelper(o.cursorAt));
	
			//Set a containment if given in the options
			this._setContainment();
	
			//Trigger event + callbacks
			if (this._trigger("start", event) === false) {
				this._clear();
				return false;
			}
	
			//Recache the helper size
			this._cacheHelperProportions();
	
			//Prepare the droppable offsets
			if ($.ui.ddmanager && !o.dropBehaviour) {
				$.ui.ddmanager.prepareOffsets(this, event);
			}
	
			// Reset helper's right/bottom css if they're set and set explicit width/height instead
			// as this prevents resizing of elements with right/bottom set (see #7772)
			this._normalizeRightBottom();
	
			this._mouseDrag(event, true); //Execute the drag once - this causes the helper not to be visible before getting its correct position
	
			//If the ddmanager is used for droppables, inform the manager that dragging has started (see #5003)
			if ( $.ui.ddmanager ) {
				$.ui.ddmanager.dragStart(this, event);
			}
	
			return true;
		},
	
		_refreshOffsets: function( event ) {
			this.offset = {
				top: this.positionAbs.top - this.margins.top,
				left: this.positionAbs.left - this.margins.left,
				scroll: false,
				parent: this._getParentOffset(),
				relative: this._getRelativeOffset()
			};
	
			this.offset.click = {
				left: event.pageX - this.offset.left,
				top: event.pageY - this.offset.top
			};
		},
	
		_mouseDrag: function(event, noPropagation) {
			// reset any necessary cached properties (see #5009)
			if ( this.hasFixedAncestor ) {
				this.offset.parent = this._getParentOffset();
			}
	
			//Compute the helpers position
			this.position = this._generatePosition( event, true );
			this.positionAbs = this._convertPositionTo("absolute");
	
			//Call plugins and callbacks and use the resulting position if something is returned
			if (!noPropagation) {
				var ui = this._uiHash();
				if (this._trigger("drag", event, ui) === false) {
					this._mouseUp({});
					return false;
				}
				this.position = ui.position;
			}
	
			this.helper[ 0 ].style.left = this.position.left + "px";
			this.helper[ 0 ].style.top = this.position.top + "px";
	
			if ($.ui.ddmanager) {
				$.ui.ddmanager.drag(this, event);
			}
	
			return false;
		},
	
		_mouseStop: function(event) {
	
			//If we are using droppables, inform the manager about the drop
			var that = this,
				dropped = false;
			if ($.ui.ddmanager && !this.options.dropBehaviour) {
				dropped = $.ui.ddmanager.drop(this, event);
			}
	
			//if a drop comes from outside (a sortable)
			if (this.dropped) {
				dropped = this.dropped;
				this.dropped = false;
			}
	
			if ((this.options.revert === "invalid" && !dropped) || (this.options.revert === "valid" && dropped) || this.options.revert === true || ($.isFunction(this.options.revert) && this.options.revert.call(this.element, dropped))) {
				$(this.helper).animate(this.originalPosition, parseInt(this.options.revertDuration, 10), function() {
					if (that._trigger("stop", event) !== false) {
						that._clear();
					}
				});
			} else {
				if (this._trigger("stop", event) !== false) {
					this._clear();
				}
			}
	
			return false;
		},
	
		_mouseUp: function( event ) {
			this._unblockFrames();
	
			//If the ddmanager is used for droppables, inform the manager that dragging has stopped (see #5003)
			if ( $.ui.ddmanager ) {
				$.ui.ddmanager.dragStop(this, event);
			}
	
			// Only need to focus if the event occurred on the draggable itself, see #10527
			if ( this.handleElement.is( event.target ) ) {
				// The interaction is over; whether or not the click resulted in a drag, focus the element
				this.element.focus();
			}
	
			return $.ui.mouse.prototype._mouseUp.call(this, event);
		},
	
		cancel: function() {
	
			if (this.helper.is(".ui-draggable-dragging")) {
				this._mouseUp({});
			} else {
				this._clear();
			}
	
			return this;
	
		},
	
		_getHandle: function(event) {
			return this.options.handle ?
				!!$( event.target ).closest( this.element.find( this.options.handle ) ).length :
				true;
		},
	
		_setHandleClassName: function() {
			this.handleElement = this.options.handle ?
				this.element.find( this.options.handle ) : this.element;
			this.handleElement.addClass( "ui-draggable-handle" );
		},
	
		_removeHandleClassName: function() {
			this.handleElement.removeClass( "ui-draggable-handle" );
		},
	
		_createHelper: function(event) {
	
			var o = this.options,
				helperIsFunction = $.isFunction( o.helper ),
				helper = helperIsFunction ?
					$( o.helper.apply( this.element[ 0 ], [ event ] ) ) :
					( o.helper === "clone" ?
						this.element.clone().removeAttr( "id" ) :
						this.element );
	
			if (!helper.parents("body").length) {
				helper.appendTo((o.appendTo === "parent" ? this.element[0].parentNode : o.appendTo));
			}
	
			// http://bugs.jqueryui.com/ticket/9446
			// a helper function can return the original element
			// which wouldn't have been set to relative in _create
			if ( helperIsFunction && helper[ 0 ] === this.element[ 0 ] ) {
				this._setPositionRelative();
			}
	
			if (helper[0] !== this.element[0] && !(/(fixed|absolute)/).test(helper.css("position"))) {
				helper.css("position", "absolute");
			}
	
			return helper;
	
		},
	
		_setPositionRelative: function() {
			if ( !( /^(?:r|a|f)/ ).test( this.element.css( "position" ) ) ) {
				this.element[ 0 ].style.position = "relative";
			}
		},
	
		_adjustOffsetFromHelper: function(obj) {
			if (typeof obj === "string") {
				obj = obj.split(" ");
			}
			if ($.isArray(obj)) {
				obj = { left: +obj[0], top: +obj[1] || 0 };
			}
			if ("left" in obj) {
				this.offset.click.left = obj.left + this.margins.left;
			}
			if ("right" in obj) {
				this.offset.click.left = this.helperProportions.width - obj.right + this.margins.left;
			}
			if ("top" in obj) {
				this.offset.click.top = obj.top + this.margins.top;
			}
			if ("bottom" in obj) {
				this.offset.click.top = this.helperProportions.height - obj.bottom + this.margins.top;
			}
		},
	
		_isRootNode: function( element ) {
			return ( /(html|body)/i ).test( element.tagName ) || element === this.document[ 0 ];
		},
	
		_getParentOffset: function() {
	
			//Get the offsetParent and cache its position
			var po = this.offsetParent.offset(),
				document = this.document[ 0 ];
	
			// This is a special case where we need to modify a offset calculated on start, since the following happened:
			// 1. The position of the helper is absolute, so it's position is calculated based on the next positioned parent
			// 2. The actual offset parent is a child of the scroll parent, and the scroll parent isn't the document, which means that
			//    the scroll is included in the initial calculation of the offset of the parent, and never recalculated upon drag
			if (this.cssPosition === "absolute" && this.scrollParent[0] !== document && $.contains(this.scrollParent[0], this.offsetParent[0])) {
				po.left += this.scrollParent.scrollLeft();
				po.top += this.scrollParent.scrollTop();
			}
	
			if ( this._isRootNode( this.offsetParent[ 0 ] ) ) {
				po = { top: 0, left: 0 };
			}
	
			return {
				top: po.top + (parseInt(this.offsetParent.css("borderTopWidth"), 10) || 0),
				left: po.left + (parseInt(this.offsetParent.css("borderLeftWidth"), 10) || 0)
			};
	
		},
	
		_getRelativeOffset: function() {
			if ( this.cssPosition !== "relative" ) {
				return { top: 0, left: 0 };
			}
	
			var p = this.element.position(),
				scrollIsRootNode = this._isRootNode( this.scrollParent[ 0 ] );
	
			return {
				top: p.top - ( parseInt(this.helper.css( "top" ), 10) || 0 ) + ( !scrollIsRootNode ? this.scrollParent.scrollTop() : 0 ),
				left: p.left - ( parseInt(this.helper.css( "left" ), 10) || 0 ) + ( !scrollIsRootNode ? this.scrollParent.scrollLeft() : 0 )
			};
	
		},
	
		_cacheMargins: function() {
			this.margins = {
				left: (parseInt(this.element.css("marginLeft"), 10) || 0),
				top: (parseInt(this.element.css("marginTop"), 10) || 0),
				right: (parseInt(this.element.css("marginRight"), 10) || 0),
				bottom: (parseInt(this.element.css("marginBottom"), 10) || 0)
			};
		},
	
		_cacheHelperProportions: function() {
			this.helperProportions = {
				width: this.helper.outerWidth(),
				height: this.helper.outerHeight()
			};
		},
	
		_setContainment: function() {
	
			var isUserScrollable, c, ce,
				o = this.options,
				document = this.document[ 0 ];
	
			this.relativeContainer = null;
	
			if ( !o.containment ) {
				this.containment = null;
				return;
			}
	
			if ( o.containment === "window" ) {
				this.containment = [
					$( window ).scrollLeft() - this.offset.relative.left - this.offset.parent.left,
					$( window ).scrollTop() - this.offset.relative.top - this.offset.parent.top,
					$( window ).scrollLeft() + $( window ).width() - this.helperProportions.width - this.margins.left,
					$( window ).scrollTop() + ( $( window ).height() || document.body.parentNode.scrollHeight ) - this.helperProportions.height - this.margins.top
				];
				return;
			}
	
			if ( o.containment === "document") {
				this.containment = [
					0,
					0,
					$( document ).width() - this.helperProportions.width - this.margins.left,
					( $( document ).height() || document.body.parentNode.scrollHeight ) - this.helperProportions.height - this.margins.top
				];
				return;
			}
	
			if ( o.containment.constructor === Array ) {
				this.containment = o.containment;
				return;
			}
	
			if ( o.containment === "parent" ) {
				o.containment = this.helper[ 0 ].parentNode;
			}
	
			c = $( o.containment );
			ce = c[ 0 ];
	
			if ( !ce ) {
				return;
			}
	
			isUserScrollable = /(scroll|auto)/.test( c.css( "overflow" ) );
	
			this.containment = [
				( parseInt( c.css( "borderLeftWidth" ), 10 ) || 0 ) + ( parseInt( c.css( "paddingLeft" ), 10 ) || 0 ),
				( parseInt( c.css( "borderTopWidth" ), 10 ) || 0 ) + ( parseInt( c.css( "paddingTop" ), 10 ) || 0 ),
				( isUserScrollable ? Math.max( ce.scrollWidth, ce.offsetWidth ) : ce.offsetWidth ) -
					( parseInt( c.css( "borderRightWidth" ), 10 ) || 0 ) -
					( parseInt( c.css( "paddingRight" ), 10 ) || 0 ) -
					this.helperProportions.width -
					this.margins.left -
					this.margins.right,
				( isUserScrollable ? Math.max( ce.scrollHeight, ce.offsetHeight ) : ce.offsetHeight ) -
					( parseInt( c.css( "borderBottomWidth" ), 10 ) || 0 ) -
					( parseInt( c.css( "paddingBottom" ), 10 ) || 0 ) -
					this.helperProportions.height -
					this.margins.top -
					this.margins.bottom
			];
			this.relativeContainer = c;
		},
	
		_convertPositionTo: function(d, pos) {
	
			if (!pos) {
				pos = this.position;
			}
	
			var mod = d === "absolute" ? 1 : -1,
				scrollIsRootNode = this._isRootNode( this.scrollParent[ 0 ] );
	
			return {
				top: (
					pos.top	+																// The absolute mouse position
					this.offset.relative.top * mod +										// Only for relative positioned nodes: Relative offset from element to offset parent
					this.offset.parent.top * mod -										// The offsetParent's offset without borders (offset + border)
					( ( this.cssPosition === "fixed" ? -this.offset.scroll.top : ( scrollIsRootNode ? 0 : this.offset.scroll.top ) ) * mod)
				),
				left: (
					pos.left +																// The absolute mouse position
					this.offset.relative.left * mod +										// Only for relative positioned nodes: Relative offset from element to offset parent
					this.offset.parent.left * mod	-										// The offsetParent's offset without borders (offset + border)
					( ( this.cssPosition === "fixed" ? -this.offset.scroll.left : ( scrollIsRootNode ? 0 : this.offset.scroll.left ) ) * mod)
				)
			};
	
		},
	
		_generatePosition: function( event, constrainPosition ) {
	
			var containment, co, top, left,
				o = this.options,
				scrollIsRootNode = this._isRootNode( this.scrollParent[ 0 ] ),
				pageX = event.pageX,
				pageY = event.pageY;
	
			// Cache the scroll
			if ( !scrollIsRootNode || !this.offset.scroll ) {
				this.offset.scroll = {
					top: this.scrollParent.scrollTop(),
					left: this.scrollParent.scrollLeft()
				};
			}
	
			/*
			 * - Position constraining -
			 * Constrain the position to a mix of grid, containment.
			 */
	
			// If we are not dragging yet, we won't check for options
			if ( constrainPosition ) {
				if ( this.containment ) {
					if ( this.relativeContainer ){
						co = this.relativeContainer.offset();
						containment = [
							this.containment[ 0 ] + co.left,
							this.containment[ 1 ] + co.top,
							this.containment[ 2 ] + co.left,
							this.containment[ 3 ] + co.top
						];
					} else {
						containment = this.containment;
					}
	
					if (event.pageX - this.offset.click.left < containment[0]) {
						pageX = containment[0] + this.offset.click.left;
					}
					if (event.pageY - this.offset.click.top < containment[1]) {
						pageY = containment[1] + this.offset.click.top;
					}
					if (event.pageX - this.offset.click.left > containment[2]) {
						pageX = containment[2] + this.offset.click.left;
					}
					if (event.pageY - this.offset.click.top > containment[3]) {
						pageY = containment[3] + this.offset.click.top;
					}
				}
	
				if (o.grid) {
					//Check for grid elements set to 0 to prevent divide by 0 error causing invalid argument errors in IE (see ticket #6950)
					top = o.grid[1] ? this.originalPageY + Math.round((pageY - this.originalPageY) / o.grid[1]) * o.grid[1] : this.originalPageY;
					pageY = containment ? ((top - this.offset.click.top >= containment[1] || top - this.offset.click.top > containment[3]) ? top : ((top - this.offset.click.top >= containment[1]) ? top - o.grid[1] : top + o.grid[1])) : top;
	
					left = o.grid[0] ? this.originalPageX + Math.round((pageX - this.originalPageX) / o.grid[0]) * o.grid[0] : this.originalPageX;
					pageX = containment ? ((left - this.offset.click.left >= containment[0] || left - this.offset.click.left > containment[2]) ? left : ((left - this.offset.click.left >= containment[0]) ? left - o.grid[0] : left + o.grid[0])) : left;
				}
	
				if ( o.axis === "y" ) {
					pageX = this.originalPageX;
				}
	
				if ( o.axis === "x" ) {
					pageY = this.originalPageY;
				}
			}
	
			return {
				top: (
					pageY -																	// The absolute mouse position
					this.offset.click.top	-												// Click offset (relative to the element)
					this.offset.relative.top -												// Only for relative positioned nodes: Relative offset from element to offset parent
					this.offset.parent.top +												// The offsetParent's offset without borders (offset + border)
					( this.cssPosition === "fixed" ? -this.offset.scroll.top : ( scrollIsRootNode ? 0 : this.offset.scroll.top ) )
				),
				left: (
					pageX -																	// The absolute mouse position
					this.offset.click.left -												// Click offset (relative to the element)
					this.offset.relative.left -												// Only for relative positioned nodes: Relative offset from element to offset parent
					this.offset.parent.left +												// The offsetParent's offset without borders (offset + border)
					( this.cssPosition === "fixed" ? -this.offset.scroll.left : ( scrollIsRootNode ? 0 : this.offset.scroll.left ) )
				)
			};
	
		},
	
		_clear: function() {
			this.helper.removeClass("ui-draggable-dragging");
			if (this.helper[0] !== this.element[0] && !this.cancelHelperRemoval) {
				this.helper.remove();
			}
			this.helper = null;
			this.cancelHelperRemoval = false;
			if ( this.destroyOnClear ) {
				this.destroy();
			}
		},
	
		_normalizeRightBottom: function() {
			if ( this.options.axis !== "y" && this.helper.css( "right" ) !== "auto" ) {
				this.helper.width( this.helper.width() );
				this.helper.css( "right", "auto" );
			}
			if ( this.options.axis !== "x" && this.helper.css( "bottom" ) !== "auto" ) {
				this.helper.height( this.helper.height() );
				this.helper.css( "bottom", "auto" );
			}
		},
	
		// From now on bulk stuff - mainly helpers
	
		_trigger: function( type, event, ui ) {
			ui = ui || this._uiHash();
			$.ui.plugin.call( this, type, [ event, ui, this ], true );
	
			// Absolute position and offset (see #6884 ) have to be recalculated after plugins
			if ( /^(drag|start|stop)/.test( type ) ) {
				this.positionAbs = this._convertPositionTo( "absolute" );
				ui.offset = this.positionAbs;
			}
			return $.Widget.prototype._trigger.call( this, type, event, ui );
		},
	
		plugins: {},
	
		_uiHash: function() {
			return {
				helper: this.helper,
				position: this.position,
				originalPosition: this.originalPosition,
				offset: this.positionAbs
			};
		}
	
	});
	
	$.ui.plugin.add( "draggable", "connectToSortable", {
		start: function( event, ui, draggable ) {
			var uiSortable = $.extend( {}, ui, {
				item: draggable.element
			});
	
			draggable.sortables = [];
			$( draggable.options.connectToSortable ).each(function() {
				var sortable = $( this ).sortable( "instance" );
	
				if ( sortable && !sortable.options.disabled ) {
					draggable.sortables.push( sortable );
	
					// refreshPositions is called at drag start to refresh the containerCache
					// which is used in drag. This ensures it's initialized and synchronized
					// with any changes that might have happened on the page since initialization.
					sortable.refreshPositions();
					sortable._trigger("activate", event, uiSortable);
				}
			});
		},
		stop: function( event, ui, draggable ) {
			var uiSortable = $.extend( {}, ui, {
				item: draggable.element
			});
	
			draggable.cancelHelperRemoval = false;
	
			$.each( draggable.sortables, function() {
				var sortable = this;
	
				if ( sortable.isOver ) {
					sortable.isOver = 0;
	
					// Allow this sortable to handle removing the helper
					draggable.cancelHelperRemoval = true;
					sortable.cancelHelperRemoval = false;
	
					// Use _storedCSS To restore properties in the sortable,
					// as this also handles revert (#9675) since the draggable
					// may have modified them in unexpected ways (#8809)
					sortable._storedCSS = {
						position: sortable.placeholder.css( "position" ),
						top: sortable.placeholder.css( "top" ),
						left: sortable.placeholder.css( "left" )
					};
	
					sortable._mouseStop(event);
	
					// Once drag has ended, the sortable should return to using
					// its original helper, not the shared helper from draggable
					sortable.options.helper = sortable.options._helper;
				} else {
					// Prevent this Sortable from removing the helper.
					// However, don't set the draggable to remove the helper
					// either as another connected Sortable may yet handle the removal.
					sortable.cancelHelperRemoval = true;
	
					sortable._trigger( "deactivate", event, uiSortable );
				}
			});
		},
		drag: function( event, ui, draggable ) {
			$.each( draggable.sortables, function() {
				var innermostIntersecting = false,
					sortable = this;
	
				// Copy over variables that sortable's _intersectsWith uses
				sortable.positionAbs = draggable.positionAbs;
				sortable.helperProportions = draggable.helperProportions;
				sortable.offset.click = draggable.offset.click;
	
				if ( sortable._intersectsWith( sortable.containerCache ) ) {
					innermostIntersecting = true;
	
					$.each( draggable.sortables, function() {
						// Copy over variables that sortable's _intersectsWith uses
						this.positionAbs = draggable.positionAbs;
						this.helperProportions = draggable.helperProportions;
						this.offset.click = draggable.offset.click;
	
						if ( this !== sortable &&
								this._intersectsWith( this.containerCache ) &&
								$.contains( sortable.element[ 0 ], this.element[ 0 ] ) ) {
							innermostIntersecting = false;
						}
	
						return innermostIntersecting;
					});
				}
	
				if ( innermostIntersecting ) {
					// If it intersects, we use a little isOver variable and set it once,
					// so that the move-in stuff gets fired only once.
					if ( !sortable.isOver ) {
						sortable.isOver = 1;
	
						// Store draggable's parent in case we need to reappend to it later.
						draggable._parent = ui.helper.parent();
	
						sortable.currentItem = ui.helper
							.appendTo( sortable.element )
							.data( "ui-sortable-item", true );
	
						// Store helper option to later restore it
						sortable.options._helper = sortable.options.helper;
	
						sortable.options.helper = function() {
							return ui.helper[ 0 ];
						};
	
						// Fire the start events of the sortable with our passed browser event,
						// and our own helper (so it doesn't create a new one)
						event.target = sortable.currentItem[ 0 ];
						sortable._mouseCapture( event, true );
						sortable._mouseStart( event, true, true );
	
						// Because the browser event is way off the new appended portlet,
						// modify necessary variables to reflect the changes
						sortable.offset.click.top = draggable.offset.click.top;
						sortable.offset.click.left = draggable.offset.click.left;
						sortable.offset.parent.left -= draggable.offset.parent.left -
							sortable.offset.parent.left;
						sortable.offset.parent.top -= draggable.offset.parent.top -
							sortable.offset.parent.top;
	
						draggable._trigger( "toSortable", event );
	
						// Inform draggable that the helper is in a valid drop zone,
						// used solely in the revert option to handle "valid/invalid".
						draggable.dropped = sortable.element;
	
						// Need to refreshPositions of all sortables in the case that
						// adding to one sortable changes the location of the other sortables (#9675)
						$.each( draggable.sortables, function() {
							this.refreshPositions();
						});
	
						// hack so receive/update callbacks work (mostly)
						draggable.currentItem = draggable.element;
						sortable.fromOutside = draggable;
					}
	
					if ( sortable.currentItem ) {
						sortable._mouseDrag( event );
						// Copy the sortable's position because the draggable's can potentially reflect
						// a relative position, while sortable is always absolute, which the dragged
						// element has now become. (#8809)
						ui.position = sortable.position;
					}
				} else {
					// If it doesn't intersect with the sortable, and it intersected before,
					// we fake the drag stop of the sortable, but make sure it doesn't remove
					// the helper by using cancelHelperRemoval.
					if ( sortable.isOver ) {
	
						sortable.isOver = 0;
						sortable.cancelHelperRemoval = true;
	
						// Calling sortable's mouseStop would trigger a revert,
						// so revert must be temporarily false until after mouseStop is called.
						sortable.options._revert = sortable.options.revert;
						sortable.options.revert = false;
	
						sortable._trigger( "out", event, sortable._uiHash( sortable ) );
						sortable._mouseStop( event, true );
	
						// restore sortable behaviors that were modfied
						// when the draggable entered the sortable area (#9481)
						sortable.options.revert = sortable.options._revert;
						sortable.options.helper = sortable.options._helper;
	
						if ( sortable.placeholder ) {
							sortable.placeholder.remove();
						}
	
						// Restore and recalculate the draggable's offset considering the sortable
						// may have modified them in unexpected ways. (#8809, #10669)
						ui.helper.appendTo( draggable._parent );
						draggable._refreshOffsets( event );
						ui.position = draggable._generatePosition( event, true );
	
						draggable._trigger( "fromSortable", event );
	
						// Inform draggable that the helper is no longer in a valid drop zone
						draggable.dropped = false;
	
						// Need to refreshPositions of all sortables just in case removing
						// from one sortable changes the location of other sortables (#9675)
						$.each( draggable.sortables, function() {
							this.refreshPositions();
						});
					}
				}
			});
		}
	});
	
	$.ui.plugin.add("draggable", "cursor", {
		start: function( event, ui, instance ) {
			var t = $( "body" ),
				o = instance.options;
	
			if (t.css("cursor")) {
				o._cursor = t.css("cursor");
			}
			t.css("cursor", o.cursor);
		},
		stop: function( event, ui, instance ) {
			var o = instance.options;
			if (o._cursor) {
				$("body").css("cursor", o._cursor);
			}
		}
	});
	
	$.ui.plugin.add("draggable", "opacity", {
		start: function( event, ui, instance ) {
			var t = $( ui.helper ),
				o = instance.options;
			if (t.css("opacity")) {
				o._opacity = t.css("opacity");
			}
			t.css("opacity", o.opacity);
		},
		stop: function( event, ui, instance ) {
			var o = instance.options;
			if (o._opacity) {
				$(ui.helper).css("opacity", o._opacity);
			}
		}
	});
	
	$.ui.plugin.add("draggable", "scroll", {
		start: function( event, ui, i ) {
			if ( !i.scrollParentNotHidden ) {
				i.scrollParentNotHidden = i.helper.scrollParent( false );
			}
	
			if ( i.scrollParentNotHidden[ 0 ] !== i.document[ 0 ] && i.scrollParentNotHidden[ 0 ].tagName !== "HTML" ) {
				i.overflowOffset = i.scrollParentNotHidden.offset();
			}
		},
		drag: function( event, ui, i  ) {
	
			var o = i.options,
				scrolled = false,
				scrollParent = i.scrollParentNotHidden[ 0 ],
				document = i.document[ 0 ];
	
			if ( scrollParent !== document && scrollParent.tagName !== "HTML" ) {
				if ( !o.axis || o.axis !== "x" ) {
					if ( ( i.overflowOffset.top + scrollParent.offsetHeight ) - event.pageY < o.scrollSensitivity ) {
						scrollParent.scrollTop = scrolled = scrollParent.scrollTop + o.scrollSpeed;
					} else if ( event.pageY - i.overflowOffset.top < o.scrollSensitivity ) {
						scrollParent.scrollTop = scrolled = scrollParent.scrollTop - o.scrollSpeed;
					}
				}
	
				if ( !o.axis || o.axis !== "y" ) {
					if ( ( i.overflowOffset.left + scrollParent.offsetWidth ) - event.pageX < o.scrollSensitivity ) {
						scrollParent.scrollLeft = scrolled = scrollParent.scrollLeft + o.scrollSpeed;
					} else if ( event.pageX - i.overflowOffset.left < o.scrollSensitivity ) {
						scrollParent.scrollLeft = scrolled = scrollParent.scrollLeft - o.scrollSpeed;
					}
				}
	
			} else {
	
				if (!o.axis || o.axis !== "x") {
					if (event.pageY - $(document).scrollTop() < o.scrollSensitivity) {
						scrolled = $(document).scrollTop($(document).scrollTop() - o.scrollSpeed);
					} else if ($(window).height() - (event.pageY - $(document).scrollTop()) < o.scrollSensitivity) {
						scrolled = $(document).scrollTop($(document).scrollTop() + o.scrollSpeed);
					}
				}
	
				if (!o.axis || o.axis !== "y") {
					if (event.pageX - $(document).scrollLeft() < o.scrollSensitivity) {
						scrolled = $(document).scrollLeft($(document).scrollLeft() - o.scrollSpeed);
					} else if ($(window).width() - (event.pageX - $(document).scrollLeft()) < o.scrollSensitivity) {
						scrolled = $(document).scrollLeft($(document).scrollLeft() + o.scrollSpeed);
					}
				}
	
			}
	
			if (scrolled !== false && $.ui.ddmanager && !o.dropBehaviour) {
				$.ui.ddmanager.prepareOffsets(i, event);
			}
	
		}
	});
	
	$.ui.plugin.add("draggable", "snap", {
		start: function( event, ui, i ) {
	
			var o = i.options;
	
			i.snapElements = [];
	
			$(o.snap.constructor !== String ? ( o.snap.items || ":data(ui-draggable)" ) : o.snap).each(function() {
				var $t = $(this),
					$o = $t.offset();
				if (this !== i.element[0]) {
					i.snapElements.push({
						item: this,
						width: $t.outerWidth(), height: $t.outerHeight(),
						top: $o.top, left: $o.left
					});
				}
			});
	
		},
		drag: function( event, ui, inst ) {
	
			var ts, bs, ls, rs, l, r, t, b, i, first,
				o = inst.options,
				d = o.snapTolerance,
				x1 = ui.offset.left, x2 = x1 + inst.helperProportions.width,
				y1 = ui.offset.top, y2 = y1 + inst.helperProportions.height;
	
			for (i = inst.snapElements.length - 1; i >= 0; i--){
	
				l = inst.snapElements[i].left - inst.margins.left;
				r = l + inst.snapElements[i].width;
				t = inst.snapElements[i].top - inst.margins.top;
				b = t + inst.snapElements[i].height;
	
				if ( x2 < l - d || x1 > r + d || y2 < t - d || y1 > b + d || !$.contains( inst.snapElements[ i ].item.ownerDocument, inst.snapElements[ i ].item ) ) {
					if (inst.snapElements[i].snapping) {
						(inst.options.snap.release && inst.options.snap.release.call(inst.element, event, $.extend(inst._uiHash(), { snapItem: inst.snapElements[i].item })));
					}
					inst.snapElements[i].snapping = false;
					continue;
				}
	
				if (o.snapMode !== "inner") {
					ts = Math.abs(t - y2) <= d;
					bs = Math.abs(b - y1) <= d;
					ls = Math.abs(l - x2) <= d;
					rs = Math.abs(r - x1) <= d;
					if (ts) {
						ui.position.top = inst._convertPositionTo("relative", { top: t - inst.helperProportions.height, left: 0 }).top;
					}
					if (bs) {
						ui.position.top = inst._convertPositionTo("relative", { top: b, left: 0 }).top;
					}
					if (ls) {
						ui.position.left = inst._convertPositionTo("relative", { top: 0, left: l - inst.helperProportions.width }).left;
					}
					if (rs) {
						ui.position.left = inst._convertPositionTo("relative", { top: 0, left: r }).left;
					}
				}
	
				first = (ts || bs || ls || rs);
	
				if (o.snapMode !== "outer") {
					ts = Math.abs(t - y1) <= d;
					bs = Math.abs(b - y2) <= d;
					ls = Math.abs(l - x1) <= d;
					rs = Math.abs(r - x2) <= d;
					if (ts) {
						ui.position.top = inst._convertPositionTo("relative", { top: t, left: 0 }).top;
					}
					if (bs) {
						ui.position.top = inst._convertPositionTo("relative", { top: b - inst.helperProportions.height, left: 0 }).top;
					}
					if (ls) {
						ui.position.left = inst._convertPositionTo("relative", { top: 0, left: l }).left;
					}
					if (rs) {
						ui.position.left = inst._convertPositionTo("relative", { top: 0, left: r - inst.helperProportions.width }).left;
					}
				}
	
				if (!inst.snapElements[i].snapping && (ts || bs || ls || rs || first)) {
					(inst.options.snap.snap && inst.options.snap.snap.call(inst.element, event, $.extend(inst._uiHash(), { snapItem: inst.snapElements[i].item })));
				}
				inst.snapElements[i].snapping = (ts || bs || ls || rs || first);
	
			}
	
		}
	});
	
	$.ui.plugin.add("draggable", "stack", {
		start: function( event, ui, instance ) {
			var min,
				o = instance.options,
				group = $.makeArray($(o.stack)).sort(function(a, b) {
					return (parseInt($(a).css("zIndex"), 10) || 0) - (parseInt($(b).css("zIndex"), 10) || 0);
				});
	
			if (!group.length) { return; }
	
			min = parseInt($(group[0]).css("zIndex"), 10) || 0;
			$(group).each(function(i) {
				$(this).css("zIndex", min + i);
			});
			this.css("zIndex", (min + group.length));
		}
	});
	
	$.ui.plugin.add("draggable", "zIndex", {
		start: function( event, ui, instance ) {
			var t = $( ui.helper ),
				o = instance.options;
	
			if (t.css("zIndex")) {
				o._zIndex = t.css("zIndex");
			}
			t.css("zIndex", o.zIndex);
		},
		stop: function( event, ui, instance ) {
			var o = instance.options;
	
			if (o._zIndex) {
				$(ui.helper).css("zIndex", o._zIndex);
			}
		}
	});
	
	var draggable = $.ui.draggable;
	
	
	/*!
	 * jQuery UI Resizable 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/resizable/
	 */
	
	
	$.widget("ui.resizable", $.ui.mouse, {
		version: "1.11.4",
		widgetEventPrefix: "resize",
		options: {
			alsoResize: false,
			animate: false,
			animateDuration: "slow",
			animateEasing: "swing",
			aspectRatio: false,
			autoHide: false,
			containment: false,
			ghost: false,
			grid: false,
			handles: "e,s,se",
			helper: false,
			maxHeight: null,
			maxWidth: null,
			minHeight: 10,
			minWidth: 10,
			// See #7960
			zIndex: 90,
	
			// callbacks
			resize: null,
			start: null,
			stop: null
		},
	
		_num: function( value ) {
			return parseInt( value, 10 ) || 0;
		},
	
		_isNumber: function( value ) {
			return !isNaN( parseInt( value, 10 ) );
		},
	
		_hasScroll: function( el, a ) {
	
			if ( $( el ).css( "overflow" ) === "hidden") {
				return false;
			}
	
			var scroll = ( a && a === "left" ) ? "scrollLeft" : "scrollTop",
				has = false;
	
			if ( el[ scroll ] > 0 ) {
				return true;
			}
	
			// TODO: determine which cases actually cause this to happen
			// if the element doesn't have the scroll set, see if it's possible to
			// set the scroll
			el[ scroll ] = 1;
			has = ( el[ scroll ] > 0 );
			el[ scroll ] = 0;
			return has;
		},
	
		_create: function() {
	
			var n, i, handle, axis, hname,
				that = this,
				o = this.options;
			this.element.addClass("ui-resizable");
	
			$.extend(this, {
				_aspectRatio: !!(o.aspectRatio),
				aspectRatio: o.aspectRatio,
				originalElement: this.element,
				_proportionallyResizeElements: [],
				_helper: o.helper || o.ghost || o.animate ? o.helper || "ui-resizable-helper" : null
			});
	
			// Wrap the element if it cannot hold child nodes
			if (this.element[0].nodeName.match(/^(canvas|textarea|input|select|button|img)$/i)) {
	
				this.element.wrap(
					$("<div class='ui-wrapper' style='overflow: hidden;'></div>").css({
						position: this.element.css("position"),
						width: this.element.outerWidth(),
						height: this.element.outerHeight(),
						top: this.element.css("top"),
						left: this.element.css("left")
					})
				);
	
				this.element = this.element.parent().data(
					"ui-resizable", this.element.resizable( "instance" )
				);
	
				this.elementIsWrapper = true;
	
				this.element.css({
					marginLeft: this.originalElement.css("marginLeft"),
					marginTop: this.originalElement.css("marginTop"),
					marginRight: this.originalElement.css("marginRight"),
					marginBottom: this.originalElement.css("marginBottom")
				});
				this.originalElement.css({
					marginLeft: 0,
					marginTop: 0,
					marginRight: 0,
					marginBottom: 0
				});
				// support: Safari
				// Prevent Safari textarea resize
				this.originalResizeStyle = this.originalElement.css("resize");
				this.originalElement.css("resize", "none");
	
				this._proportionallyResizeElements.push( this.originalElement.css({
					position: "static",
					zoom: 1,
					display: "block"
				}) );
	
				// support: IE9
				// avoid IE jump (hard set the margin)
				this.originalElement.css({ margin: this.originalElement.css("margin") });
	
				this._proportionallyResize();
			}
	
			this.handles = o.handles ||
				( !$(".ui-resizable-handle", this.element).length ?
					"e,s,se" : {
						n: ".ui-resizable-n",
						e: ".ui-resizable-e",
						s: ".ui-resizable-s",
						w: ".ui-resizable-w",
						se: ".ui-resizable-se",
						sw: ".ui-resizable-sw",
						ne: ".ui-resizable-ne",
						nw: ".ui-resizable-nw"
					} );
	
			this._handles = $();
			if ( this.handles.constructor === String ) {
	
				if ( this.handles === "all") {
					this.handles = "n,e,s,w,se,sw,ne,nw";
				}
	
				n = this.handles.split(",");
				this.handles = {};
	
				for (i = 0; i < n.length; i++) {
	
					handle = $.trim(n[i]);
					hname = "ui-resizable-" + handle;
					axis = $("<div class='ui-resizable-handle " + hname + "'></div>");
	
					axis.css({ zIndex: o.zIndex });
	
					// TODO : What's going on here?
					if ("se" === handle) {
						axis.addClass("ui-icon ui-icon-gripsmall-diagonal-se");
					}
	
					this.handles[handle] = ".ui-resizable-" + handle;
					this.element.append(axis);
				}
	
			}
	
			this._renderAxis = function(target) {
	
				var i, axis, padPos, padWrapper;
	
				target = target || this.element;
	
				for (i in this.handles) {
	
					if (this.handles[i].constructor === String) {
						this.handles[i] = this.element.children( this.handles[ i ] ).first().show();
					} else if ( this.handles[ i ].jquery || this.handles[ i ].nodeType ) {
						this.handles[ i ] = $( this.handles[ i ] );
						this._on( this.handles[ i ], { "mousedown": that._mouseDown });
					}
	
					if (this.elementIsWrapper && this.originalElement[0].nodeName.match(/^(textarea|input|select|button)$/i)) {
	
						axis = $(this.handles[i], this.element);
	
						padWrapper = /sw|ne|nw|se|n|s/.test(i) ? axis.outerHeight() : axis.outerWidth();
	
						padPos = [ "padding",
							/ne|nw|n/.test(i) ? "Top" :
							/se|sw|s/.test(i) ? "Bottom" :
							/^e$/.test(i) ? "Right" : "Left" ].join("");
	
						target.css(padPos, padWrapper);
	
						this._proportionallyResize();
					}
	
					this._handles = this._handles.add( this.handles[ i ] );
				}
			};
	
			// TODO: make renderAxis a prototype function
			this._renderAxis(this.element);
	
			this._handles = this._handles.add( this.element.find( ".ui-resizable-handle" ) );
			this._handles.disableSelection();
	
			this._handles.mouseover(function() {
				if (!that.resizing) {
					if (this.className) {
						axis = this.className.match(/ui-resizable-(se|sw|ne|nw|n|e|s|w)/i);
					}
					that.axis = axis && axis[1] ? axis[1] : "se";
				}
			});
	
			if (o.autoHide) {
				this._handles.hide();
				$(this.element)
					.addClass("ui-resizable-autohide")
					.mouseenter(function() {
						if (o.disabled) {
							return;
						}
						$(this).removeClass("ui-resizable-autohide");
						that._handles.show();
					})
					.mouseleave(function() {
						if (o.disabled) {
							return;
						}
						if (!that.resizing) {
							$(this).addClass("ui-resizable-autohide");
							that._handles.hide();
						}
					});
			}
	
			this._mouseInit();
		},
	
		_destroy: function() {
	
			this._mouseDestroy();
	
			var wrapper,
				_destroy = function(exp) {
					$(exp)
						.removeClass("ui-resizable ui-resizable-disabled ui-resizable-resizing")
						.removeData("resizable")
						.removeData("ui-resizable")
						.unbind(".resizable")
						.find(".ui-resizable-handle")
							.remove();
				};
	
			// TODO: Unwrap at same DOM position
			if (this.elementIsWrapper) {
				_destroy(this.element);
				wrapper = this.element;
				this.originalElement.css({
					position: wrapper.css("position"),
					width: wrapper.outerWidth(),
					height: wrapper.outerHeight(),
					top: wrapper.css("top"),
					left: wrapper.css("left")
				}).insertAfter( wrapper );
				wrapper.remove();
			}
	
			this.originalElement.css("resize", this.originalResizeStyle);
			_destroy(this.originalElement);
	
			return this;
		},
	
		_mouseCapture: function(event) {
			var i, handle,
				capture = false;
	
			for (i in this.handles) {
				handle = $(this.handles[i])[0];
				if (handle === event.target || $.contains(handle, event.target)) {
					capture = true;
				}
			}
	
			return !this.options.disabled && capture;
		},
	
		_mouseStart: function(event) {
	
			var curleft, curtop, cursor,
				o = this.options,
				el = this.element;
	
			this.resizing = true;
	
			this._renderProxy();
	
			curleft = this._num(this.helper.css("left"));
			curtop = this._num(this.helper.css("top"));
	
			if (o.containment) {
				curleft += $(o.containment).scrollLeft() || 0;
				curtop += $(o.containment).scrollTop() || 0;
			}
	
			this.offset = this.helper.offset();
			this.position = { left: curleft, top: curtop };
	
			this.size = this._helper ? {
					width: this.helper.width(),
					height: this.helper.height()
				} : {
					width: el.width(),
					height: el.height()
				};
	
			this.originalSize = this._helper ? {
					width: el.outerWidth(),
					height: el.outerHeight()
				} : {
					width: el.width(),
					height: el.height()
				};
	
			this.sizeDiff = {
				width: el.outerWidth() - el.width(),
				height: el.outerHeight() - el.height()
			};
	
			this.originalPosition = { left: curleft, top: curtop };
			this.originalMousePosition = { left: event.pageX, top: event.pageY };
	
			this.aspectRatio = (typeof o.aspectRatio === "number") ?
				o.aspectRatio :
				((this.originalSize.width / this.originalSize.height) || 1);
	
			cursor = $(".ui-resizable-" + this.axis).css("cursor");
			$("body").css("cursor", cursor === "auto" ? this.axis + "-resize" : cursor);
	
			el.addClass("ui-resizable-resizing");
			this._propagate("start", event);
			return true;
		},
	
		_mouseDrag: function(event) {
	
			var data, props,
				smp = this.originalMousePosition,
				a = this.axis,
				dx = (event.pageX - smp.left) || 0,
				dy = (event.pageY - smp.top) || 0,
				trigger = this._change[a];
	
			this._updatePrevProperties();
	
			if (!trigger) {
				return false;
			}
	
			data = trigger.apply(this, [ event, dx, dy ]);
	
			this._updateVirtualBoundaries(event.shiftKey);
			if (this._aspectRatio || event.shiftKey) {
				data = this._updateRatio(data, event);
			}
	
			data = this._respectSize(data, event);
	
			this._updateCache(data);
	
			this._propagate("resize", event);
	
			props = this._applyChanges();
	
			if ( !this._helper && this._proportionallyResizeElements.length ) {
				this._proportionallyResize();
			}
	
			if ( !$.isEmptyObject( props ) ) {
				this._updatePrevProperties();
				this._trigger( "resize", event, this.ui() );
				this._applyChanges();
			}
	
			return false;
		},
	
		_mouseStop: function(event) {
	
			this.resizing = false;
			var pr, ista, soffseth, soffsetw, s, left, top,
				o = this.options, that = this;
	
			if (this._helper) {
	
				pr = this._proportionallyResizeElements;
				ista = pr.length && (/textarea/i).test(pr[0].nodeName);
				soffseth = ista && this._hasScroll(pr[0], "left") ? 0 : that.sizeDiff.height;
				soffsetw = ista ? 0 : that.sizeDiff.width;
	
				s = {
					width: (that.helper.width()  - soffsetw),
					height: (that.helper.height() - soffseth)
				};
				left = (parseInt(that.element.css("left"), 10) +
					(that.position.left - that.originalPosition.left)) || null;
				top = (parseInt(that.element.css("top"), 10) +
					(that.position.top - that.originalPosition.top)) || null;
	
				if (!o.animate) {
					this.element.css($.extend(s, { top: top, left: left }));
				}
	
				that.helper.height(that.size.height);
				that.helper.width(that.size.width);
	
				if (this._helper && !o.animate) {
					this._proportionallyResize();
				}
			}
	
			$("body").css("cursor", "auto");
	
			this.element.removeClass("ui-resizable-resizing");
	
			this._propagate("stop", event);
	
			if (this._helper) {
				this.helper.remove();
			}
	
			return false;
	
		},
	
		_updatePrevProperties: function() {
			this.prevPosition = {
				top: this.position.top,
				left: this.position.left
			};
			this.prevSize = {
				width: this.size.width,
				height: this.size.height
			};
		},
	
		_applyChanges: function() {
			var props = {};
	
			if ( this.position.top !== this.prevPosition.top ) {
				props.top = this.position.top + "px";
			}
			if ( this.position.left !== this.prevPosition.left ) {
				props.left = this.position.left + "px";
			}
			if ( this.size.width !== this.prevSize.width ) {
				props.width = this.size.width + "px";
			}
			if ( this.size.height !== this.prevSize.height ) {
				props.height = this.size.height + "px";
			}
	
			this.helper.css( props );
	
			return props;
		},
	
		_updateVirtualBoundaries: function(forceAspectRatio) {
			var pMinWidth, pMaxWidth, pMinHeight, pMaxHeight, b,
				o = this.options;
	
			b = {
				minWidth: this._isNumber(o.minWidth) ? o.minWidth : 0,
				maxWidth: this._isNumber(o.maxWidth) ? o.maxWidth : Infinity,
				minHeight: this._isNumber(o.minHeight) ? o.minHeight : 0,
				maxHeight: this._isNumber(o.maxHeight) ? o.maxHeight : Infinity
			};
	
			if (this._aspectRatio || forceAspectRatio) {
				pMinWidth = b.minHeight * this.aspectRatio;
				pMinHeight = b.minWidth / this.aspectRatio;
				pMaxWidth = b.maxHeight * this.aspectRatio;
				pMaxHeight = b.maxWidth / this.aspectRatio;
	
				if (pMinWidth > b.minWidth) {
					b.minWidth = pMinWidth;
				}
				if (pMinHeight > b.minHeight) {
					b.minHeight = pMinHeight;
				}
				if (pMaxWidth < b.maxWidth) {
					b.maxWidth = pMaxWidth;
				}
				if (pMaxHeight < b.maxHeight) {
					b.maxHeight = pMaxHeight;
				}
			}
			this._vBoundaries = b;
		},
	
		_updateCache: function(data) {
			this.offset = this.helper.offset();
			if (this._isNumber(data.left)) {
				this.position.left = data.left;
			}
			if (this._isNumber(data.top)) {
				this.position.top = data.top;
			}
			if (this._isNumber(data.height)) {
				this.size.height = data.height;
			}
			if (this._isNumber(data.width)) {
				this.size.width = data.width;
			}
		},
	
		_updateRatio: function( data ) {
	
			var cpos = this.position,
				csize = this.size,
				a = this.axis;
	
			if (this._isNumber(data.height)) {
				data.width = (data.height * this.aspectRatio);
			} else if (this._isNumber(data.width)) {
				data.height = (data.width / this.aspectRatio);
			}
	
			if (a === "sw") {
				data.left = cpos.left + (csize.width - data.width);
				data.top = null;
			}
			if (a === "nw") {
				data.top = cpos.top + (csize.height - data.height);
				data.left = cpos.left + (csize.width - data.width);
			}
	
			return data;
		},
	
		_respectSize: function( data ) {
	
			var o = this._vBoundaries,
				a = this.axis,
				ismaxw = this._isNumber(data.width) && o.maxWidth && (o.maxWidth < data.width),
				ismaxh = this._isNumber(data.height) && o.maxHeight && (o.maxHeight < data.height),
				isminw = this._isNumber(data.width) && o.minWidth && (o.minWidth > data.width),
				isminh = this._isNumber(data.height) && o.minHeight && (o.minHeight > data.height),
				dw = this.originalPosition.left + this.originalSize.width,
				dh = this.position.top + this.size.height,
				cw = /sw|nw|w/.test(a), ch = /nw|ne|n/.test(a);
			if (isminw) {
				data.width = o.minWidth;
			}
			if (isminh) {
				data.height = o.minHeight;
			}
			if (ismaxw) {
				data.width = o.maxWidth;
			}
			if (ismaxh) {
				data.height = o.maxHeight;
			}
	
			if (isminw && cw) {
				data.left = dw - o.minWidth;
			}
			if (ismaxw && cw) {
				data.left = dw - o.maxWidth;
			}
			if (isminh && ch) {
				data.top = dh - o.minHeight;
			}
			if (ismaxh && ch) {
				data.top = dh - o.maxHeight;
			}
	
			// Fixing jump error on top/left - bug #2330
			if (!data.width && !data.height && !data.left && data.top) {
				data.top = null;
			} else if (!data.width && !data.height && !data.top && data.left) {
				data.left = null;
			}
	
			return data;
		},
	
		_getPaddingPlusBorderDimensions: function( element ) {
			var i = 0,
				widths = [],
				borders = [
					element.css( "borderTopWidth" ),
					element.css( "borderRightWidth" ),
					element.css( "borderBottomWidth" ),
					element.css( "borderLeftWidth" )
				],
				paddings = [
					element.css( "paddingTop" ),
					element.css( "paddingRight" ),
					element.css( "paddingBottom" ),
					element.css( "paddingLeft" )
				];
	
			for ( ; i < 4; i++ ) {
				widths[ i ] = ( parseInt( borders[ i ], 10 ) || 0 );
				widths[ i ] += ( parseInt( paddings[ i ], 10 ) || 0 );
			}
	
			return {
				height: widths[ 0 ] + widths[ 2 ],
				width: widths[ 1 ] + widths[ 3 ]
			};
		},
	
		_proportionallyResize: function() {
	
			if (!this._proportionallyResizeElements.length) {
				return;
			}
	
			var prel,
				i = 0,
				element = this.helper || this.element;
	
			for ( ; i < this._proportionallyResizeElements.length; i++) {
	
				prel = this._proportionallyResizeElements[i];
	
				// TODO: Seems like a bug to cache this.outerDimensions
				// considering that we are in a loop.
				if (!this.outerDimensions) {
					this.outerDimensions = this._getPaddingPlusBorderDimensions( prel );
				}
	
				prel.css({
					height: (element.height() - this.outerDimensions.height) || 0,
					width: (element.width() - this.outerDimensions.width) || 0
				});
	
			}
	
		},
	
		_renderProxy: function() {
	
			var el = this.element, o = this.options;
			this.elementOffset = el.offset();
	
			if (this._helper) {
	
				this.helper = this.helper || $("<div style='overflow:hidden;'></div>");
	
				this.helper.addClass(this._helper).css({
					width: this.element.outerWidth() - 1,
					height: this.element.outerHeight() - 1,
					position: "absolute",
					left: this.elementOffset.left + "px",
					top: this.elementOffset.top + "px",
					zIndex: ++o.zIndex //TODO: Don't modify option
				});
	
				this.helper
					.appendTo("body")
					.disableSelection();
	
			} else {
				this.helper = this.element;
			}
	
		},
	
		_change: {
			e: function(event, dx) {
				return { width: this.originalSize.width + dx };
			},
			w: function(event, dx) {
				var cs = this.originalSize, sp = this.originalPosition;
				return { left: sp.left + dx, width: cs.width - dx };
			},
			n: function(event, dx, dy) {
				var cs = this.originalSize, sp = this.originalPosition;
				return { top: sp.top + dy, height: cs.height - dy };
			},
			s: function(event, dx, dy) {
				return { height: this.originalSize.height + dy };
			},
			se: function(event, dx, dy) {
				return $.extend(this._change.s.apply(this, arguments),
					this._change.e.apply(this, [ event, dx, dy ]));
			},
			sw: function(event, dx, dy) {
				return $.extend(this._change.s.apply(this, arguments),
					this._change.w.apply(this, [ event, dx, dy ]));
			},
			ne: function(event, dx, dy) {
				return $.extend(this._change.n.apply(this, arguments),
					this._change.e.apply(this, [ event, dx, dy ]));
			},
			nw: function(event, dx, dy) {
				return $.extend(this._change.n.apply(this, arguments),
					this._change.w.apply(this, [ event, dx, dy ]));
			}
		},
	
		_propagate: function(n, event) {
			$.ui.plugin.call(this, n, [ event, this.ui() ]);
			(n !== "resize" && this._trigger(n, event, this.ui()));
		},
	
		plugins: {},
	
		ui: function() {
			return {
				originalElement: this.originalElement,
				element: this.element,
				helper: this.helper,
				position: this.position,
				size: this.size,
				originalSize: this.originalSize,
				originalPosition: this.originalPosition
			};
		}
	
	});
	
	/*
	 * Resizable Extensions
	 */
	
	$.ui.plugin.add("resizable", "animate", {
	
		stop: function( event ) {
			var that = $(this).resizable( "instance" ),
				o = that.options,
				pr = that._proportionallyResizeElements,
				ista = pr.length && (/textarea/i).test(pr[0].nodeName),
				soffseth = ista && that._hasScroll(pr[0], "left") ? 0 : that.sizeDiff.height,
				soffsetw = ista ? 0 : that.sizeDiff.width,
				style = { width: (that.size.width - soffsetw), height: (that.size.height - soffseth) },
				left = (parseInt(that.element.css("left"), 10) +
					(that.position.left - that.originalPosition.left)) || null,
				top = (parseInt(that.element.css("top"), 10) +
					(that.position.top - that.originalPosition.top)) || null;
	
			that.element.animate(
				$.extend(style, top && left ? { top: top, left: left } : {}), {
					duration: o.animateDuration,
					easing: o.animateEasing,
					step: function() {
	
						var data = {
							width: parseInt(that.element.css("width"), 10),
							height: parseInt(that.element.css("height"), 10),
							top: parseInt(that.element.css("top"), 10),
							left: parseInt(that.element.css("left"), 10)
						};
	
						if (pr && pr.length) {
							$(pr[0]).css({ width: data.width, height: data.height });
						}
	
						// propagating resize, and updating values for each animation step
						that._updateCache(data);
						that._propagate("resize", event);
	
					}
				}
			);
		}
	
	});
	
	$.ui.plugin.add( "resizable", "containment", {
	
		start: function() {
			var element, p, co, ch, cw, width, height,
				that = $( this ).resizable( "instance" ),
				o = that.options,
				el = that.element,
				oc = o.containment,
				ce = ( oc instanceof $ ) ? oc.get( 0 ) : ( /parent/.test( oc ) ) ? el.parent().get( 0 ) : oc;
	
			if ( !ce ) {
				return;
			}
	
			that.containerElement = $( ce );
	
			if ( /document/.test( oc ) || oc === document ) {
				that.containerOffset = {
					left: 0,
					top: 0
				};
				that.containerPosition = {
					left: 0,
					top: 0
				};
	
				that.parentData = {
					element: $( document ),
					left: 0,
					top: 0,
					width: $( document ).width(),
					height: $( document ).height() || document.body.parentNode.scrollHeight
				};
			} else {
				element = $( ce );
				p = [];
				$([ "Top", "Right", "Left", "Bottom" ]).each(function( i, name ) {
					p[ i ] = that._num( element.css( "padding" + name ) );
				});
	
				that.containerOffset = element.offset();
				that.containerPosition = element.position();
				that.containerSize = {
					height: ( element.innerHeight() - p[ 3 ] ),
					width: ( element.innerWidth() - p[ 1 ] )
				};
	
				co = that.containerOffset;
				ch = that.containerSize.height;
				cw = that.containerSize.width;
				width = ( that._hasScroll ( ce, "left" ) ? ce.scrollWidth : cw );
				height = ( that._hasScroll ( ce ) ? ce.scrollHeight : ch ) ;
	
				that.parentData = {
					element: ce,
					left: co.left,
					top: co.top,
					width: width,
					height: height
				};
			}
		},
	
		resize: function( event ) {
			var woset, hoset, isParent, isOffsetRelative,
				that = $( this ).resizable( "instance" ),
				o = that.options,
				co = that.containerOffset,
				cp = that.position,
				pRatio = that._aspectRatio || event.shiftKey,
				cop = {
					top: 0,
					left: 0
				},
				ce = that.containerElement,
				continueResize = true;
	
			if ( ce[ 0 ] !== document && ( /static/ ).test( ce.css( "position" ) ) ) {
				cop = co;
			}
	
			if ( cp.left < ( that._helper ? co.left : 0 ) ) {
				that.size.width = that.size.width +
					( that._helper ?
						( that.position.left - co.left ) :
						( that.position.left - cop.left ) );
	
				if ( pRatio ) {
					that.size.height = that.size.width / that.aspectRatio;
					continueResize = false;
				}
				that.position.left = o.helper ? co.left : 0;
			}
	
			if ( cp.top < ( that._helper ? co.top : 0 ) ) {
				that.size.height = that.size.height +
					( that._helper ?
						( that.position.top - co.top ) :
						that.position.top );
	
				if ( pRatio ) {
					that.size.width = that.size.height * that.aspectRatio;
					continueResize = false;
				}
				that.position.top = that._helper ? co.top : 0;
			}
	
			isParent = that.containerElement.get( 0 ) === that.element.parent().get( 0 );
			isOffsetRelative = /relative|absolute/.test( that.containerElement.css( "position" ) );
	
			if ( isParent && isOffsetRelative ) {
				that.offset.left = that.parentData.left + that.position.left;
				that.offset.top = that.parentData.top + that.position.top;
			} else {
				that.offset.left = that.element.offset().left;
				that.offset.top = that.element.offset().top;
			}
	
			woset = Math.abs( that.sizeDiff.width +
				(that._helper ?
					that.offset.left - cop.left :
					(that.offset.left - co.left)) );
	
			hoset = Math.abs( that.sizeDiff.height +
				(that._helper ?
					that.offset.top - cop.top :
					(that.offset.top - co.top)) );
	
			if ( woset + that.size.width >= that.parentData.width ) {
				that.size.width = that.parentData.width - woset;
				if ( pRatio ) {
					that.size.height = that.size.width / that.aspectRatio;
					continueResize = false;
				}
			}
	
			if ( hoset + that.size.height >= that.parentData.height ) {
				that.size.height = that.parentData.height - hoset;
				if ( pRatio ) {
					that.size.width = that.size.height * that.aspectRatio;
					continueResize = false;
				}
			}
	
			if ( !continueResize ) {
				that.position.left = that.prevPosition.left;
				that.position.top = that.prevPosition.top;
				that.size.width = that.prevSize.width;
				that.size.height = that.prevSize.height;
			}
		},
	
		stop: function() {
			var that = $( this ).resizable( "instance" ),
				o = that.options,
				co = that.containerOffset,
				cop = that.containerPosition,
				ce = that.containerElement,
				helper = $( that.helper ),
				ho = helper.offset(),
				w = helper.outerWidth() - that.sizeDiff.width,
				h = helper.outerHeight() - that.sizeDiff.height;
	
			if ( that._helper && !o.animate && ( /relative/ ).test( ce.css( "position" ) ) ) {
				$( this ).css({
					left: ho.left - cop.left - co.left,
					width: w,
					height: h
				});
			}
	
			if ( that._helper && !o.animate && ( /static/ ).test( ce.css( "position" ) ) ) {
				$( this ).css({
					left: ho.left - cop.left - co.left,
					width: w,
					height: h
				});
			}
		}
	});
	
	$.ui.plugin.add("resizable", "alsoResize", {
	
		start: function() {
			var that = $(this).resizable( "instance" ),
				o = that.options;
	
			$(o.alsoResize).each(function() {
				var el = $(this);
				el.data("ui-resizable-alsoresize", {
					width: parseInt(el.width(), 10), height: parseInt(el.height(), 10),
					left: parseInt(el.css("left"), 10), top: parseInt(el.css("top"), 10)
				});
			});
		},
	
		resize: function(event, ui) {
			var that = $(this).resizable( "instance" ),
				o = that.options,
				os = that.originalSize,
				op = that.originalPosition,
				delta = {
					height: (that.size.height - os.height) || 0,
					width: (that.size.width - os.width) || 0,
					top: (that.position.top - op.top) || 0,
					left: (that.position.left - op.left) || 0
				};
	
				$(o.alsoResize).each(function() {
					var el = $(this), start = $(this).data("ui-resizable-alsoresize"), style = {},
						css = el.parents(ui.originalElement[0]).length ?
								[ "width", "height" ] :
								[ "width", "height", "top", "left" ];
	
					$.each(css, function(i, prop) {
						var sum = (start[prop] || 0) + (delta[prop] || 0);
						if (sum && sum >= 0) {
							style[prop] = sum || null;
						}
					});
	
					el.css(style);
				});
		},
	
		stop: function() {
			$(this).removeData("resizable-alsoresize");
		}
	});
	
	$.ui.plugin.add("resizable", "ghost", {
	
		start: function() {
	
			var that = $(this).resizable( "instance" ), o = that.options, cs = that.size;
	
			that.ghost = that.originalElement.clone();
			that.ghost
				.css({
					opacity: 0.25,
					display: "block",
					position: "relative",
					height: cs.height,
					width: cs.width,
					margin: 0,
					left: 0,
					top: 0
				})
				.addClass("ui-resizable-ghost")
				.addClass(typeof o.ghost === "string" ? o.ghost : "");
	
			that.ghost.appendTo(that.helper);
	
		},
	
		resize: function() {
			var that = $(this).resizable( "instance" );
			if (that.ghost) {
				that.ghost.css({
					position: "relative",
					height: that.size.height,
					width: that.size.width
				});
			}
		},
	
		stop: function() {
			var that = $(this).resizable( "instance" );
			if (that.ghost && that.helper) {
				that.helper.get(0).removeChild(that.ghost.get(0));
			}
		}
	
	});
	
	$.ui.plugin.add("resizable", "grid", {
	
		resize: function() {
			var outerDimensions,
				that = $(this).resizable( "instance" ),
				o = that.options,
				cs = that.size,
				os = that.originalSize,
				op = that.originalPosition,
				a = that.axis,
				grid = typeof o.grid === "number" ? [ o.grid, o.grid ] : o.grid,
				gridX = (grid[0] || 1),
				gridY = (grid[1] || 1),
				ox = Math.round((cs.width - os.width) / gridX) * gridX,
				oy = Math.round((cs.height - os.height) / gridY) * gridY,
				newWidth = os.width + ox,
				newHeight = os.height + oy,
				isMaxWidth = o.maxWidth && (o.maxWidth < newWidth),
				isMaxHeight = o.maxHeight && (o.maxHeight < newHeight),
				isMinWidth = o.minWidth && (o.minWidth > newWidth),
				isMinHeight = o.minHeight && (o.minHeight > newHeight);
	
			o.grid = grid;
	
			if (isMinWidth) {
				newWidth += gridX;
			}
			if (isMinHeight) {
				newHeight += gridY;
			}
			if (isMaxWidth) {
				newWidth -= gridX;
			}
			if (isMaxHeight) {
				newHeight -= gridY;
			}
	
			if (/^(se|s|e)$/.test(a)) {
				that.size.width = newWidth;
				that.size.height = newHeight;
			} else if (/^(ne)$/.test(a)) {
				that.size.width = newWidth;
				that.size.height = newHeight;
				that.position.top = op.top - oy;
			} else if (/^(sw)$/.test(a)) {
				that.size.width = newWidth;
				that.size.height = newHeight;
				that.position.left = op.left - ox;
			} else {
				if ( newHeight - gridY <= 0 || newWidth - gridX <= 0) {
					outerDimensions = that._getPaddingPlusBorderDimensions( this );
				}
	
				if ( newHeight - gridY > 0 ) {
					that.size.height = newHeight;
					that.position.top = op.top - oy;
				} else {
					newHeight = gridY - outerDimensions.height;
					that.size.height = newHeight;
					that.position.top = op.top + os.height - newHeight;
				}
				if ( newWidth - gridX > 0 ) {
					that.size.width = newWidth;
					that.position.left = op.left - ox;
				} else {
					newWidth = gridX - outerDimensions.width;
					that.size.width = newWidth;
					that.position.left = op.left + os.width - newWidth;
				}
			}
		}
	
	});
	
	var resizable = $.ui.resizable;
	
	
	/*!
	 * jQuery UI Dialog 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/dialog/
	 */
	
	
	var dialog = $.widget( "ui.dialog", {
		version: "1.11.4",
		options: {
			appendTo: "body",
			autoOpen: true,
			buttons: [],
			closeOnEscape: true,
			closeText: "Close",
			dialogClass: "",
			draggable: true,
			hide: null,
			height: "auto",
			maxHeight: null,
			maxWidth: null,
			minHeight: 150,
			minWidth: 150,
			modal: false,
			position: {
				my: "center",
				at: "center",
				of: window,
				collision: "fit",
				// Ensure the titlebar is always visible
				using: function( pos ) {
					var topOffset = $( this ).css( pos ).offset().top;
					if ( topOffset < 0 ) {
						$( this ).css( "top", pos.top - topOffset );
					}
				}
			},
			resizable: true,
			show: null,
			title: null,
			width: 300,
	
			// callbacks
			beforeClose: null,
			close: null,
			drag: null,
			dragStart: null,
			dragStop: null,
			focus: null,
			open: null,
			resize: null,
			resizeStart: null,
			resizeStop: null
		},
	
		sizeRelatedOptions: {
			buttons: true,
			height: true,
			maxHeight: true,
			maxWidth: true,
			minHeight: true,
			minWidth: true,
			width: true
		},
	
		resizableRelatedOptions: {
			maxHeight: true,
			maxWidth: true,
			minHeight: true,
			minWidth: true
		},
	
		_create: function() {
			this.originalCss = {
				display: this.element[ 0 ].style.display,
				width: this.element[ 0 ].style.width,
				minHeight: this.element[ 0 ].style.minHeight,
				maxHeight: this.element[ 0 ].style.maxHeight,
				height: this.element[ 0 ].style.height
			};
			this.originalPosition = {
				parent: this.element.parent(),
				index: this.element.parent().children().index( this.element )
			};
			this.originalTitle = this.element.attr( "title" );
			this.options.title = this.options.title || this.originalTitle;
	
			this._createWrapper();
	
			this.element
				.show()
				.removeAttr( "title" )
				.addClass( "ui-dialog-content ui-widget-content" )
				.appendTo( this.uiDialog );
	
			this._createTitlebar();
			this._createButtonPane();
	
			if ( this.options.draggable && $.fn.draggable ) {
				this._makeDraggable();
			}
			if ( this.options.resizable && $.fn.resizable ) {
				this._makeResizable();
			}
	
			this._isOpen = false;
	
			this._trackFocus();
		},
	
		_init: function() {
			if ( this.options.autoOpen ) {
				this.open();
			}
		},
	
		_appendTo: function() {
			var element = this.options.appendTo;
			if ( element && (element.jquery || element.nodeType) ) {
				return $( element );
			}
			return this.document.find( element || "body" ).eq( 0 );
		},
	
		_destroy: function() {
			var next,
				originalPosition = this.originalPosition;
	
			this._untrackInstance();
			this._destroyOverlay();
	
			this.element
				.removeUniqueId()
				.removeClass( "ui-dialog-content ui-widget-content" )
				.css( this.originalCss )
				// Without detaching first, the following becomes really slow
				.detach();
	
			this.uiDialog.stop( true, true ).remove();
	
			if ( this.originalTitle ) {
				this.element.attr( "title", this.originalTitle );
			}
	
			next = originalPosition.parent.children().eq( originalPosition.index );
			// Don't try to place the dialog next to itself (#8613)
			if ( next.length && next[ 0 ] !== this.element[ 0 ] ) {
				next.before( this.element );
			} else {
				originalPosition.parent.append( this.element );
			}
		},
	
		widget: function() {
			return this.uiDialog;
		},
	
		disable: $.noop,
		enable: $.noop,
	
		close: function( event ) {
			var activeElement,
				that = this;
	
			if ( !this._isOpen || this._trigger( "beforeClose", event ) === false ) {
				return;
			}
	
			this._isOpen = false;
			this._focusedElement = null;
			this._destroyOverlay();
			this._untrackInstance();
	
			if ( !this.opener.filter( ":focusable" ).focus().length ) {
	
				// support: IE9
				// IE9 throws an "Unspecified error" accessing document.activeElement from an <iframe>
				try {
					activeElement = this.document[ 0 ].activeElement;
	
					// Support: IE9, IE10
					// If the <body> is blurred, IE will switch windows, see #4520
					if ( activeElement && activeElement.nodeName.toLowerCase() !== "body" ) {
	
						// Hiding a focused element doesn't trigger blur in WebKit
						// so in case we have nothing to focus on, explicitly blur the active element
						// https://bugs.webkit.org/show_bug.cgi?id=47182
						$( activeElement ).blur();
					}
				} catch ( error ) {}
			}
	
			this._hide( this.uiDialog, this.options.hide, function() {
				that._trigger( "close", event );
			});
		},
	
		isOpen: function() {
			return this._isOpen;
		},
	
		moveToTop: function() {
			this._moveToTop();
		},
	
		_moveToTop: function( event, silent ) {
			var moved = false,
				zIndices = this.uiDialog.siblings( ".ui-front:visible" ).map(function() {
					return +$( this ).css( "z-index" );
				}).get(),
				zIndexMax = Math.max.apply( null, zIndices );
	
			if ( zIndexMax >= +this.uiDialog.css( "z-index" ) ) {
				this.uiDialog.css( "z-index", zIndexMax + 1 );
				moved = true;
			}
	
			if ( moved && !silent ) {
				this._trigger( "focus", event );
			}
			return moved;
		},
	
		open: function() {
			var that = this;
			if ( this._isOpen ) {
				if ( this._moveToTop() ) {
					this._focusTabbable();
				}
				return;
			}
	
			this._isOpen = true;
			this.opener = $( this.document[ 0 ].activeElement );
	
			this._size();
			this._position();
			this._createOverlay();
			this._moveToTop( null, true );
	
			// Ensure the overlay is moved to the top with the dialog, but only when
			// opening. The overlay shouldn't move after the dialog is open so that
			// modeless dialogs opened after the modal dialog stack properly.
			if ( this.overlay ) {
				this.overlay.css( "z-index", this.uiDialog.css( "z-index" ) - 1 );
			}
	
			this._show( this.uiDialog, this.options.show, function() {
				that._focusTabbable();
				that._trigger( "focus" );
			});
	
			// Track the dialog immediately upon openening in case a focus event
			// somehow occurs outside of the dialog before an element inside the
			// dialog is focused (#10152)
			this._makeFocusTarget();
	
			this._trigger( "open" );
		},
	
		_focusTabbable: function() {
			// Set focus to the first match:
			// 1. An element that was focused previously
			// 2. First element inside the dialog matching [autofocus]
			// 3. Tabbable element inside the content element
			// 4. Tabbable element inside the buttonpane
			// 5. The close button
			// 6. The dialog itself
			var hasFocus = this._focusedElement;
			if ( !hasFocus ) {
				hasFocus = this.element.find( "[autofocus]" );
			}
			if ( !hasFocus.length ) {
				hasFocus = this.element.find( ":tabbable" );
			}
			if ( !hasFocus.length ) {
				hasFocus = this.uiDialogButtonPane.find( ":tabbable" );
			}
			if ( !hasFocus.length ) {
				hasFocus = this.uiDialogTitlebarClose.filter( ":tabbable" );
			}
			if ( !hasFocus.length ) {
				hasFocus = this.uiDialog;
			}
			hasFocus.eq( 0 ).focus();
		},
	
		_keepFocus: function( event ) {
			function checkFocus() {
				var activeElement = this.document[0].activeElement,
					isActive = this.uiDialog[0] === activeElement ||
						$.contains( this.uiDialog[0], activeElement );
				if ( !isActive ) {
					this._focusTabbable();
				}
			}
			event.preventDefault();
			checkFocus.call( this );
			// support: IE
			// IE <= 8 doesn't prevent moving focus even with event.preventDefault()
			// so we check again later
			this._delay( checkFocus );
		},
	
		_createWrapper: function() {
			this.uiDialog = $("<div>")
				.addClass( "ui-dialog ui-widget ui-widget-content ui-corner-all ui-front " +
					this.options.dialogClass )
				.hide()
				.attr({
					// Setting tabIndex makes the div focusable
					tabIndex: -1,
					role: "dialog"
				})
				.appendTo( this._appendTo() );
	
			this._on( this.uiDialog, {
				keydown: function( event ) {
					if ( this.options.closeOnEscape && !event.isDefaultPrevented() && event.keyCode &&
							event.keyCode === $.ui.keyCode.ESCAPE ) {
						event.preventDefault();
						this.close( event );
						return;
					}
	
					// prevent tabbing out of dialogs
					if ( event.keyCode !== $.ui.keyCode.TAB || event.isDefaultPrevented() ) {
						return;
					}
					var tabbables = this.uiDialog.find( ":tabbable" ),
						first = tabbables.filter( ":first" ),
						last = tabbables.filter( ":last" );
	
					if ( ( event.target === last[0] || event.target === this.uiDialog[0] ) && !event.shiftKey ) {
						this._delay(function() {
							first.focus();
						});
						event.preventDefault();
					} else if ( ( event.target === first[0] || event.target === this.uiDialog[0] ) && event.shiftKey ) {
						this._delay(function() {
							last.focus();
						});
						event.preventDefault();
					}
				},
				mousedown: function( event ) {
					if ( this._moveToTop( event ) ) {
						this._focusTabbable();
					}
				}
			});
	
			// We assume that any existing aria-describedby attribute means
			// that the dialog content is marked up properly
			// otherwise we brute force the content as the description
			if ( !this.element.find( "[aria-describedby]" ).length ) {
				this.uiDialog.attr({
					"aria-describedby": this.element.uniqueId().attr( "id" )
				});
			}
		},
	
		_createTitlebar: function() {
			var uiDialogTitle;
	
			this.uiDialogTitlebar = $( "<div>" )
				.addClass( "ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" )
				.prependTo( this.uiDialog );
			this._on( this.uiDialogTitlebar, {
				mousedown: function( event ) {
					// Don't prevent click on close button (#8838)
					// Focusing a dialog that is partially scrolled out of view
					// causes the browser to scroll it into view, preventing the click event
					if ( !$( event.target ).closest( ".ui-dialog-titlebar-close" ) ) {
						// Dialog isn't getting focus when dragging (#8063)
						this.uiDialog.focus();
					}
				}
			});
	
			// support: IE
			// Use type="button" to prevent enter keypresses in textboxes from closing the
			// dialog in IE (#9312)
			this.uiDialogTitlebarClose = $( "<button type='button'></button>" )
				.button({
					label: this.options.closeText,
					icons: {
						primary: "ui-icon-closethick"
					},
					text: false
				})
				.addClass( "ui-dialog-titlebar-close" )
				.appendTo( this.uiDialogTitlebar );
			this._on( this.uiDialogTitlebarClose, {
				click: function( event ) {
					event.preventDefault();
					this.close( event );
				}
			});
	
			uiDialogTitle = $( "<span>" )
				.uniqueId()
				.addClass( "ui-dialog-title" )
				.prependTo( this.uiDialogTitlebar );
			this._title( uiDialogTitle );
	
			this.uiDialog.attr({
				"aria-labelledby": uiDialogTitle.attr( "id" )
			});
		},
	
		_title: function( title ) {
			if ( !this.options.title ) {
				title.html( "&#160;" );
			}
			title.text( this.options.title );
		},
	
		_createButtonPane: function() {
			this.uiDialogButtonPane = $( "<div>" )
				.addClass( "ui-dialog-buttonpane ui-widget-content ui-helper-clearfix" );
	
			this.uiButtonSet = $( "<div>" )
				.addClass( "ui-dialog-buttonset" )
				.appendTo( this.uiDialogButtonPane );
	
			this._createButtons();
		},
	
		_createButtons: function() {
			var that = this,
				buttons = this.options.buttons;
	
			// if we already have a button pane, remove it
			this.uiDialogButtonPane.remove();
			this.uiButtonSet.empty();
	
			if ( $.isEmptyObject( buttons ) || ($.isArray( buttons ) && !buttons.length) ) {
				this.uiDialog.removeClass( "ui-dialog-buttons" );
				return;
			}
	
			$.each( buttons, function( name, props ) {
				var click, buttonOptions;
				props = $.isFunction( props ) ?
					{ click: props, text: name } :
					props;
				// Default to a non-submitting button
				props = $.extend( { type: "button" }, props );
				// Change the context for the click callback to be the main element
				click = props.click;
				props.click = function() {
					click.apply( that.element[ 0 ], arguments );
				};
				buttonOptions = {
					icons: props.icons,
					text: props.showText
				};
				delete props.icons;
				delete props.showText;
				$( "<button></button>", props )
					.button( buttonOptions )
					.appendTo( that.uiButtonSet );
			});
			this.uiDialog.addClass( "ui-dialog-buttons" );
			this.uiDialogButtonPane.appendTo( this.uiDialog );
		},
	
		_makeDraggable: function() {
			var that = this,
				options = this.options;
	
			function filteredUi( ui ) {
				return {
					position: ui.position,
					offset: ui.offset
				};
			}
	
			this.uiDialog.draggable({
				cancel: ".ui-dialog-content, .ui-dialog-titlebar-close",
				handle: ".ui-dialog-titlebar",
				containment: "document",
				start: function( event, ui ) {
					$( this ).addClass( "ui-dialog-dragging" );
					that._blockFrames();
					that._trigger( "dragStart", event, filteredUi( ui ) );
				},
				drag: function( event, ui ) {
					that._trigger( "drag", event, filteredUi( ui ) );
				},
				stop: function( event, ui ) {
					var left = ui.offset.left - that.document.scrollLeft(),
						top = ui.offset.top - that.document.scrollTop();
	
					options.position = {
						my: "left top",
						at: "left" + (left >= 0 ? "+" : "") + left + " " +
							"top" + (top >= 0 ? "+" : "") + top,
						of: that.window
					};
					$( this ).removeClass( "ui-dialog-dragging" );
					that._unblockFrames();
					that._trigger( "dragStop", event, filteredUi( ui ) );
				}
			});
		},
	
		_makeResizable: function() {
			var that = this,
				options = this.options,
				handles = options.resizable,
				// .ui-resizable has position: relative defined in the stylesheet
				// but dialogs have to use absolute or fixed positioning
				position = this.uiDialog.css("position"),
				resizeHandles = typeof handles === "string" ?
					handles	:
					"n,e,s,w,se,sw,ne,nw";
	
			function filteredUi( ui ) {
				return {
					originalPosition: ui.originalPosition,
					originalSize: ui.originalSize,
					position: ui.position,
					size: ui.size
				};
			}
	
			this.uiDialog.resizable({
				cancel: ".ui-dialog-content",
				containment: "document",
				alsoResize: this.element,
				maxWidth: options.maxWidth,
				maxHeight: options.maxHeight,
				minWidth: options.minWidth,
				minHeight: this._minHeight(),
				handles: resizeHandles,
				start: function( event, ui ) {
					$( this ).addClass( "ui-dialog-resizing" );
					that._blockFrames();
					that._trigger( "resizeStart", event, filteredUi( ui ) );
				},
				resize: function( event, ui ) {
					that._trigger( "resize", event, filteredUi( ui ) );
				},
				stop: function( event, ui ) {
					var offset = that.uiDialog.offset(),
						left = offset.left - that.document.scrollLeft(),
						top = offset.top - that.document.scrollTop();
	
					options.height = that.uiDialog.height();
					options.width = that.uiDialog.width();
					options.position = {
						my: "left top",
						at: "left" + (left >= 0 ? "+" : "") + left + " " +
							"top" + (top >= 0 ? "+" : "") + top,
						of: that.window
					};
					$( this ).removeClass( "ui-dialog-resizing" );
					that._unblockFrames();
					that._trigger( "resizeStop", event, filteredUi( ui ) );
				}
			})
			.css( "position", position );
		},
	
		_trackFocus: function() {
			this._on( this.widget(), {
				focusin: function( event ) {
					this._makeFocusTarget();
					this._focusedElement = $( event.target );
				}
			});
		},
	
		_makeFocusTarget: function() {
			this._untrackInstance();
			this._trackingInstances().unshift( this );
		},
	
		_untrackInstance: function() {
			var instances = this._trackingInstances(),
				exists = $.inArray( this, instances );
			if ( exists !== -1 ) {
				instances.splice( exists, 1 );
			}
		},
	
		_trackingInstances: function() {
			var instances = this.document.data( "ui-dialog-instances" );
			if ( !instances ) {
				instances = [];
				this.document.data( "ui-dialog-instances", instances );
			}
			return instances;
		},
	
		_minHeight: function() {
			var options = this.options;
	
			return options.height === "auto" ?
				options.minHeight :
				Math.min( options.minHeight, options.height );
		},
	
		_position: function() {
			// Need to show the dialog to get the actual offset in the position plugin
			var isVisible = this.uiDialog.is( ":visible" );
			if ( !isVisible ) {
				this.uiDialog.show();
			}
			this.uiDialog.position( this.options.position );
			if ( !isVisible ) {
				this.uiDialog.hide();
			}
		},
	
		_setOptions: function( options ) {
			var that = this,
				resize = false,
				resizableOptions = {};
	
			$.each( options, function( key, value ) {
				that._setOption( key, value );
	
				if ( key in that.sizeRelatedOptions ) {
					resize = true;
				}
				if ( key in that.resizableRelatedOptions ) {
					resizableOptions[ key ] = value;
				}
			});
	
			if ( resize ) {
				this._size();
				this._position();
			}
			if ( this.uiDialog.is( ":data(ui-resizable)" ) ) {
				this.uiDialog.resizable( "option", resizableOptions );
			}
		},
	
		_setOption: function( key, value ) {
			var isDraggable, isResizable,
				uiDialog = this.uiDialog;
	
			if ( key === "dialogClass" ) {
				uiDialog
					.removeClass( this.options.dialogClass )
					.addClass( value );
			}
	
			if ( key === "disabled" ) {
				return;
			}
	
			this._super( key, value );
	
			if ( key === "appendTo" ) {
				this.uiDialog.appendTo( this._appendTo() );
			}
	
			if ( key === "buttons" ) {
				this._createButtons();
			}
	
			if ( key === "closeText" ) {
				this.uiDialogTitlebarClose.button({
					// Ensure that we always pass a string
					label: "" + value
				});
			}
	
			if ( key === "draggable" ) {
				isDraggable = uiDialog.is( ":data(ui-draggable)" );
				if ( isDraggable && !value ) {
					uiDialog.draggable( "destroy" );
				}
	
				if ( !isDraggable && value ) {
					this._makeDraggable();
				}
			}
	
			if ( key === "position" ) {
				this._position();
			}
	
			if ( key === "resizable" ) {
				// currently resizable, becoming non-resizable
				isResizable = uiDialog.is( ":data(ui-resizable)" );
				if ( isResizable && !value ) {
					uiDialog.resizable( "destroy" );
				}
	
				// currently resizable, changing handles
				if ( isResizable && typeof value === "string" ) {
					uiDialog.resizable( "option", "handles", value );
				}
	
				// currently non-resizable, becoming resizable
				if ( !isResizable && value !== false ) {
					this._makeResizable();
				}
			}
	
			if ( key === "title" ) {
				this._title( this.uiDialogTitlebar.find( ".ui-dialog-title" ) );
			}
		},
	
		_size: function() {
			// If the user has resized the dialog, the .ui-dialog and .ui-dialog-content
			// divs will both have width and height set, so we need to reset them
			var nonContentHeight, minContentHeight, maxContentHeight,
				options = this.options;
	
			// Reset content sizing
			this.element.show().css({
				width: "auto",
				minHeight: 0,
				maxHeight: "none",
				height: 0
			});
	
			if ( options.minWidth > options.width ) {
				options.width = options.minWidth;
			}
	
			// reset wrapper sizing
			// determine the height of all the non-content elements
			nonContentHeight = this.uiDialog.css({
					height: "auto",
					width: options.width
				})
				.outerHeight();
			minContentHeight = Math.max( 0, options.minHeight - nonContentHeight );
			maxContentHeight = typeof options.maxHeight === "number" ?
				Math.max( 0, options.maxHeight - nonContentHeight ) :
				"none";
	
			if ( options.height === "auto" ) {
				this.element.css({
					minHeight: minContentHeight,
					maxHeight: maxContentHeight,
					height: "auto"
				});
			} else {
				this.element.height( Math.max( 0, options.height - nonContentHeight ) );
			}
	
			if ( this.uiDialog.is( ":data(ui-resizable)" ) ) {
				this.uiDialog.resizable( "option", "minHeight", this._minHeight() );
			}
		},
	
		_blockFrames: function() {
			this.iframeBlocks = this.document.find( "iframe" ).map(function() {
				var iframe = $( this );
	
				return $( "<div>" )
					.css({
						position: "absolute",
						width: iframe.outerWidth(),
						height: iframe.outerHeight()
					})
					.appendTo( iframe.parent() )
					.offset( iframe.offset() )[0];
			});
		},
	
		_unblockFrames: function() {
			if ( this.iframeBlocks ) {
				this.iframeBlocks.remove();
				delete this.iframeBlocks;
			}
		},
	
		_allowInteraction: function( event ) {
			if ( $( event.target ).closest( ".ui-dialog" ).length ) {
				return true;
			}
	
			// TODO: Remove hack when datepicker implements
			// the .ui-front logic (#8989)
			return !!$( event.target ).closest( ".ui-datepicker" ).length;
		},
	
		_createOverlay: function() {
			if ( !this.options.modal ) {
				return;
			}
	
			// We use a delay in case the overlay is created from an
			// event that we're going to be cancelling (#2804)
			var isOpening = true;
			this._delay(function() {
				isOpening = false;
			});
	
			if ( !this.document.data( "ui-dialog-overlays" ) ) {
	
				// Prevent use of anchors and inputs
				// Using _on() for an event handler shared across many instances is
				// safe because the dialogs stack and must be closed in reverse order
				this._on( this.document, {
					focusin: function( event ) {
						if ( isOpening ) {
							return;
						}
	
						if ( !this._allowInteraction( event ) ) {
							event.preventDefault();
							this._trackingInstances()[ 0 ]._focusTabbable();
						}
					}
				});
			}
	
			this.overlay = $( "<div>" )
				.addClass( "ui-widget-overlay ui-front" )
				.appendTo( this._appendTo() );
			this._on( this.overlay, {
				mousedown: "_keepFocus"
			});
			this.document.data( "ui-dialog-overlays",
				(this.document.data( "ui-dialog-overlays" ) || 0) + 1 );
		},
	
		_destroyOverlay: function() {
			if ( !this.options.modal ) {
				return;
			}
	
			if ( this.overlay ) {
				var overlays = this.document.data( "ui-dialog-overlays" ) - 1;
	
				if ( !overlays ) {
					this.document
						.unbind( "focusin" )
						.removeData( "ui-dialog-overlays" );
				} else {
					this.document.data( "ui-dialog-overlays", overlays );
				}
	
				this.overlay.remove();
				this.overlay = null;
			}
		}
	});
	
	
	/*!
	 * jQuery UI Droppable 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/droppable/
	 */
	
	
	$.widget( "ui.droppable", {
		version: "1.11.4",
		widgetEventPrefix: "drop",
		options: {
			accept: "*",
			activeClass: false,
			addClasses: true,
			greedy: false,
			hoverClass: false,
			scope: "default",
			tolerance: "intersect",
	
			// callbacks
			activate: null,
			deactivate: null,
			drop: null,
			out: null,
			over: null
		},
		_create: function() {
	
			var proportions,
				o = this.options,
				accept = o.accept;
	
			this.isover = false;
			this.isout = true;
	
			this.accept = $.isFunction( accept ) ? accept : function( d ) {
				return d.is( accept );
			};
	
			this.proportions = function( /* valueToWrite */ ) {
				if ( arguments.length ) {
					// Store the droppable's proportions
					proportions = arguments[ 0 ];
				} else {
					// Retrieve or derive the droppable's proportions
					return proportions ?
						proportions :
						proportions = {
							width: this.element[ 0 ].offsetWidth,
							height: this.element[ 0 ].offsetHeight
						};
				}
			};
	
			this._addToManager( o.scope );
	
			o.addClasses && this.element.addClass( "ui-droppable" );
	
		},
	
		_addToManager: function( scope ) {
			// Add the reference and positions to the manager
			$.ui.ddmanager.droppables[ scope ] = $.ui.ddmanager.droppables[ scope ] || [];
			$.ui.ddmanager.droppables[ scope ].push( this );
		},
	
		_splice: function( drop ) {
			var i = 0;
			for ( ; i < drop.length; i++ ) {
				if ( drop[ i ] === this ) {
					drop.splice( i, 1 );
				}
			}
		},
	
		_destroy: function() {
			var drop = $.ui.ddmanager.droppables[ this.options.scope ];
	
			this._splice( drop );
	
			this.element.removeClass( "ui-droppable ui-droppable-disabled" );
		},
	
		_setOption: function( key, value ) {
	
			if ( key === "accept" ) {
				this.accept = $.isFunction( value ) ? value : function( d ) {
					return d.is( value );
				};
			} else if ( key === "scope" ) {
				var drop = $.ui.ddmanager.droppables[ this.options.scope ];
	
				this._splice( drop );
				this._addToManager( value );
			}
	
			this._super( key, value );
		},
	
		_activate: function( event ) {
			var draggable = $.ui.ddmanager.current;
			if ( this.options.activeClass ) {
				this.element.addClass( this.options.activeClass );
			}
			if ( draggable ){
				this._trigger( "activate", event, this.ui( draggable ) );
			}
		},
	
		_deactivate: function( event ) {
			var draggable = $.ui.ddmanager.current;
			if ( this.options.activeClass ) {
				this.element.removeClass( this.options.activeClass );
			}
			if ( draggable ){
				this._trigger( "deactivate", event, this.ui( draggable ) );
			}
		},
	
		_over: function( event ) {
	
			var draggable = $.ui.ddmanager.current;
	
			// Bail if draggable and droppable are same element
			if ( !draggable || ( draggable.currentItem || draggable.element )[ 0 ] === this.element[ 0 ] ) {
				return;
			}
	
			if ( this.accept.call( this.element[ 0 ], ( draggable.currentItem || draggable.element ) ) ) {
				if ( this.options.hoverClass ) {
					this.element.addClass( this.options.hoverClass );
				}
				this._trigger( "over", event, this.ui( draggable ) );
			}
	
		},
	
		_out: function( event ) {
	
			var draggable = $.ui.ddmanager.current;
	
			// Bail if draggable and droppable are same element
			if ( !draggable || ( draggable.currentItem || draggable.element )[ 0 ] === this.element[ 0 ] ) {
				return;
			}
	
			if ( this.accept.call( this.element[ 0 ], ( draggable.currentItem || draggable.element ) ) ) {
				if ( this.options.hoverClass ) {
					this.element.removeClass( this.options.hoverClass );
				}
				this._trigger( "out", event, this.ui( draggable ) );
			}
	
		},
	
		_drop: function( event, custom ) {
	
			var draggable = custom || $.ui.ddmanager.current,
				childrenIntersection = false;
	
			// Bail if draggable and droppable are same element
			if ( !draggable || ( draggable.currentItem || draggable.element )[ 0 ] === this.element[ 0 ] ) {
				return false;
			}
	
			this.element.find( ":data(ui-droppable)" ).not( ".ui-draggable-dragging" ).each(function() {
				var inst = $( this ).droppable( "instance" );
				if (
					inst.options.greedy &&
					!inst.options.disabled &&
					inst.options.scope === draggable.options.scope &&
					inst.accept.call( inst.element[ 0 ], ( draggable.currentItem || draggable.element ) ) &&
					$.ui.intersect( draggable, $.extend( inst, { offset: inst.element.offset() } ), inst.options.tolerance, event )
				) { childrenIntersection = true; return false; }
			});
			if ( childrenIntersection ) {
				return false;
			}
	
			if ( this.accept.call( this.element[ 0 ], ( draggable.currentItem || draggable.element ) ) ) {
				if ( this.options.activeClass ) {
					this.element.removeClass( this.options.activeClass );
				}
				if ( this.options.hoverClass ) {
					this.element.removeClass( this.options.hoverClass );
				}
				this._trigger( "drop", event, this.ui( draggable ) );
				return this.element;
			}
	
			return false;
	
		},
	
		ui: function( c ) {
			return {
				draggable: ( c.currentItem || c.element ),
				helper: c.helper,
				position: c.position,
				offset: c.positionAbs
			};
		}
	
	});
	
	$.ui.intersect = (function() {
		function isOverAxis( x, reference, size ) {
			return ( x >= reference ) && ( x < ( reference + size ) );
		}
	
		return function( draggable, droppable, toleranceMode, event ) {
	
			if ( !droppable.offset ) {
				return false;
			}
	
			var x1 = ( draggable.positionAbs || draggable.position.absolute ).left + draggable.margins.left,
				y1 = ( draggable.positionAbs || draggable.position.absolute ).top + draggable.margins.top,
				x2 = x1 + draggable.helperProportions.width,
				y2 = y1 + draggable.helperProportions.height,
				l = droppable.offset.left,
				t = droppable.offset.top,
				r = l + droppable.proportions().width,
				b = t + droppable.proportions().height;
	
			switch ( toleranceMode ) {
			case "fit":
				return ( l <= x1 && x2 <= r && t <= y1 && y2 <= b );
			case "intersect":
				return ( l < x1 + ( draggable.helperProportions.width / 2 ) && // Right Half
					x2 - ( draggable.helperProportions.width / 2 ) < r && // Left Half
					t < y1 + ( draggable.helperProportions.height / 2 ) && // Bottom Half
					y2 - ( draggable.helperProportions.height / 2 ) < b ); // Top Half
			case "pointer":
				return isOverAxis( event.pageY, t, droppable.proportions().height ) && isOverAxis( event.pageX, l, droppable.proportions().width );
			case "touch":
				return (
					( y1 >= t && y1 <= b ) || // Top edge touching
					( y2 >= t && y2 <= b ) || // Bottom edge touching
					( y1 < t && y2 > b ) // Surrounded vertically
				) && (
					( x1 >= l && x1 <= r ) || // Left edge touching
					( x2 >= l && x2 <= r ) || // Right edge touching
					( x1 < l && x2 > r ) // Surrounded horizontally
				);
			default:
				return false;
			}
		};
	})();
	
	/*
		This manager tracks offsets of draggables and droppables
	*/
	$.ui.ddmanager = {
		current: null,
		droppables: { "default": [] },
		prepareOffsets: function( t, event ) {
	
			var i, j,
				m = $.ui.ddmanager.droppables[ t.options.scope ] || [],
				type = event ? event.type : null, // workaround for #2317
				list = ( t.currentItem || t.element ).find( ":data(ui-droppable)" ).addBack();
	
			droppablesLoop: for ( i = 0; i < m.length; i++ ) {
	
				// No disabled and non-accepted
				if ( m[ i ].options.disabled || ( t && !m[ i ].accept.call( m[ i ].element[ 0 ], ( t.currentItem || t.element ) ) ) ) {
					continue;
				}
	
				// Filter out elements in the current dragged item
				for ( j = 0; j < list.length; j++ ) {
					if ( list[ j ] === m[ i ].element[ 0 ] ) {
						m[ i ].proportions().height = 0;
						continue droppablesLoop;
					}
				}
	
				m[ i ].visible = m[ i ].element.css( "display" ) !== "none";
				if ( !m[ i ].visible ) {
					continue;
				}
	
				// Activate the droppable if used directly from draggables
				if ( type === "mousedown" ) {
					m[ i ]._activate.call( m[ i ], event );
				}
	
				m[ i ].offset = m[ i ].element.offset();
				m[ i ].proportions({ width: m[ i ].element[ 0 ].offsetWidth, height: m[ i ].element[ 0 ].offsetHeight });
	
			}
	
		},
		drop: function( draggable, event ) {
	
			var dropped = false;
			// Create a copy of the droppables in case the list changes during the drop (#9116)
			$.each( ( $.ui.ddmanager.droppables[ draggable.options.scope ] || [] ).slice(), function() {
	
				if ( !this.options ) {
					return;
				}
				if ( !this.options.disabled && this.visible && $.ui.intersect( draggable, this, this.options.tolerance, event ) ) {
					dropped = this._drop.call( this, event ) || dropped;
				}
	
				if ( !this.options.disabled && this.visible && this.accept.call( this.element[ 0 ], ( draggable.currentItem || draggable.element ) ) ) {
					this.isout = true;
					this.isover = false;
					this._deactivate.call( this, event );
				}
	
			});
			return dropped;
	
		},
		dragStart: function( draggable, event ) {
			// Listen for scrolling so that if the dragging causes scrolling the position of the droppables can be recalculated (see #5003)
			draggable.element.parentsUntil( "body" ).bind( "scroll.droppable", function() {
				if ( !draggable.options.refreshPositions ) {
					$.ui.ddmanager.prepareOffsets( draggable, event );
				}
			});
		},
		drag: function( draggable, event ) {
	
			// If you have a highly dynamic page, you might try this option. It renders positions every time you move the mouse.
			if ( draggable.options.refreshPositions ) {
				$.ui.ddmanager.prepareOffsets( draggable, event );
			}
	
			// Run through all droppables and check their positions based on specific tolerance options
			$.each( $.ui.ddmanager.droppables[ draggable.options.scope ] || [], function() {
	
				if ( this.options.disabled || this.greedyChild || !this.visible ) {
					return;
				}
	
				var parentInstance, scope, parent,
					intersects = $.ui.intersect( draggable, this, this.options.tolerance, event ),
					c = !intersects && this.isover ? "isout" : ( intersects && !this.isover ? "isover" : null );
				if ( !c ) {
					return;
				}
	
				if ( this.options.greedy ) {
					// find droppable parents with same scope
					scope = this.options.scope;
					parent = this.element.parents( ":data(ui-droppable)" ).filter(function() {
						return $( this ).droppable( "instance" ).options.scope === scope;
					});
	
					if ( parent.length ) {
						parentInstance = $( parent[ 0 ] ).droppable( "instance" );
						parentInstance.greedyChild = ( c === "isover" );
					}
				}
	
				// we just moved into a greedy child
				if ( parentInstance && c === "isover" ) {
					parentInstance.isover = false;
					parentInstance.isout = true;
					parentInstance._out.call( parentInstance, event );
				}
	
				this[ c ] = true;
				this[c === "isout" ? "isover" : "isout"] = false;
				this[c === "isover" ? "_over" : "_out"].call( this, event );
	
				// we just moved out of a greedy child
				if ( parentInstance && c === "isout" ) {
					parentInstance.isout = false;
					parentInstance.isover = true;
					parentInstance._over.call( parentInstance, event );
				}
			});
	
		},
		dragStop: function( draggable, event ) {
			draggable.element.parentsUntil( "body" ).unbind( "scroll.droppable" );
			// Call prepareOffsets one final time since IE does not fire return scroll events when overflow was caused by drag (see #5003)
			if ( !draggable.options.refreshPositions ) {
				$.ui.ddmanager.prepareOffsets( draggable, event );
			}
		}
	};
	
	var droppable = $.ui.droppable;
	
	
	/*!
	 * jQuery UI Effects 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/category/effects-core/
	 */
	
	
	var dataSpace = "ui-effects-",
	
		// Create a local jQuery because jQuery Color relies on it and the
		// global may not exist with AMD and a custom build (#10199)
		jQuery = $;
	
	$.effects = {
		effect: {}
	};
	
	/*!
	 * jQuery Color Animations v2.1.2
	 * https://github.com/jquery/jquery-color
	 *
	 * Copyright 2014 jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * Date: Wed Jan 16 08:47:09 2013 -0600
	 */
	(function( jQuery, undefined ) {
	
		var stepHooks = "backgroundColor borderBottomColor borderLeftColor borderRightColor borderTopColor color columnRuleColor outlineColor textDecorationColor textEmphasisColor",
	
		// plusequals test for += 100 -= 100
		rplusequals = /^([\-+])=\s*(\d+\.?\d*)/,
		// a set of RE's that can match strings and generate color tuples.
		stringParsers = [ {
				re: /rgba?\(\s*(\d{1,3})\s*,\s*(\d{1,3})\s*,\s*(\d{1,3})\s*(?:,\s*(\d?(?:\.\d+)?)\s*)?\)/,
				parse: function( execResult ) {
					return [
						execResult[ 1 ],
						execResult[ 2 ],
						execResult[ 3 ],
						execResult[ 4 ]
					];
				}
			}, {
				re: /rgba?\(\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*(?:,\s*(\d?(?:\.\d+)?)\s*)?\)/,
				parse: function( execResult ) {
					return [
						execResult[ 1 ] * 2.55,
						execResult[ 2 ] * 2.55,
						execResult[ 3 ] * 2.55,
						execResult[ 4 ]
					];
				}
			}, {
				// this regex ignores A-F because it's compared against an already lowercased string
				re: /#([a-f0-9]{2})([a-f0-9]{2})([a-f0-9]{2})/,
				parse: function( execResult ) {
					return [
						parseInt( execResult[ 1 ], 16 ),
						parseInt( execResult[ 2 ], 16 ),
						parseInt( execResult[ 3 ], 16 )
					];
				}
			}, {
				// this regex ignores A-F because it's compared against an already lowercased string
				re: /#([a-f0-9])([a-f0-9])([a-f0-9])/,
				parse: function( execResult ) {
					return [
						parseInt( execResult[ 1 ] + execResult[ 1 ], 16 ),
						parseInt( execResult[ 2 ] + execResult[ 2 ], 16 ),
						parseInt( execResult[ 3 ] + execResult[ 3 ], 16 )
					];
				}
			}, {
				re: /hsla?\(\s*(\d+(?:\.\d+)?)\s*,\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*(?:,\s*(\d?(?:\.\d+)?)\s*)?\)/,
				space: "hsla",
				parse: function( execResult ) {
					return [
						execResult[ 1 ],
						execResult[ 2 ] / 100,
						execResult[ 3 ] / 100,
						execResult[ 4 ]
					];
				}
			} ],
	
		// jQuery.Color( )
		color = jQuery.Color = function( color, green, blue, alpha ) {
			return new jQuery.Color.fn.parse( color, green, blue, alpha );
		},
		spaces = {
			rgba: {
				props: {
					red: {
						idx: 0,
						type: "byte"
					},
					green: {
						idx: 1,
						type: "byte"
					},
					blue: {
						idx: 2,
						type: "byte"
					}
				}
			},
	
			hsla: {
				props: {
					hue: {
						idx: 0,
						type: "degrees"
					},
					saturation: {
						idx: 1,
						type: "percent"
					},
					lightness: {
						idx: 2,
						type: "percent"
					}
				}
			}
		},
		propTypes = {
			"byte": {
				floor: true,
				max: 255
			},
			"percent": {
				max: 1
			},
			"degrees": {
				mod: 360,
				floor: true
			}
		},
		support = color.support = {},
	
		// element for support tests
		supportElem = jQuery( "<p>" )[ 0 ],
	
		// colors = jQuery.Color.names
		colors,
	
		// local aliases of functions called often
		each = jQuery.each;
	
	// determine rgba support immediately
	supportElem.style.cssText = "background-color:rgba(1,1,1,.5)";
	support.rgba = supportElem.style.backgroundColor.indexOf( "rgba" ) > -1;
	
	// define cache name and alpha properties
	// for rgba and hsla spaces
	each( spaces, function( spaceName, space ) {
		space.cache = "_" + spaceName;
		space.props.alpha = {
			idx: 3,
			type: "percent",
			def: 1
		};
	});
	
	function clamp( value, prop, allowEmpty ) {
		var type = propTypes[ prop.type ] || {};
	
		if ( value == null ) {
			return (allowEmpty || !prop.def) ? null : prop.def;
		}
	
		// ~~ is an short way of doing floor for positive numbers
		value = type.floor ? ~~value : parseFloat( value );
	
		// IE will pass in empty strings as value for alpha,
		// which will hit this case
		if ( isNaN( value ) ) {
			return prop.def;
		}
	
		if ( type.mod ) {
			// we add mod before modding to make sure that negatives values
			// get converted properly: -10 -> 350
			return (value + type.mod) % type.mod;
		}
	
		// for now all property types without mod have min and max
		return 0 > value ? 0 : type.max < value ? type.max : value;
	}
	
	function stringParse( string ) {
		var inst = color(),
			rgba = inst._rgba = [];
	
		string = string.toLowerCase();
	
		each( stringParsers, function( i, parser ) {
			var parsed,
				match = parser.re.exec( string ),
				values = match && parser.parse( match ),
				spaceName = parser.space || "rgba";
	
			if ( values ) {
				parsed = inst[ spaceName ]( values );
	
				// if this was an rgba parse the assignment might happen twice
				// oh well....
				inst[ spaces[ spaceName ].cache ] = parsed[ spaces[ spaceName ].cache ];
				rgba = inst._rgba = parsed._rgba;
	
				// exit each( stringParsers ) here because we matched
				return false;
			}
		});
	
		// Found a stringParser that handled it
		if ( rgba.length ) {
	
			// if this came from a parsed string, force "transparent" when alpha is 0
			// chrome, (and maybe others) return "transparent" as rgba(0,0,0,0)
			if ( rgba.join() === "0,0,0,0" ) {
				jQuery.extend( rgba, colors.transparent );
			}
			return inst;
		}
	
		// named colors
		return colors[ string ];
	}
	
	color.fn = jQuery.extend( color.prototype, {
		parse: function( red, green, blue, alpha ) {
			if ( red === undefined ) {
				this._rgba = [ null, null, null, null ];
				return this;
			}
			if ( red.jquery || red.nodeType ) {
				red = jQuery( red ).css( green );
				green = undefined;
			}
	
			var inst = this,
				type = jQuery.type( red ),
				rgba = this._rgba = [];
	
			// more than 1 argument specified - assume ( red, green, blue, alpha )
			if ( green !== undefined ) {
				red = [ red, green, blue, alpha ];
				type = "array";
			}
	
			if ( type === "string" ) {
				return this.parse( stringParse( red ) || colors._default );
			}
	
			if ( type === "array" ) {
				each( spaces.rgba.props, function( key, prop ) {
					rgba[ prop.idx ] = clamp( red[ prop.idx ], prop );
				});
				return this;
			}
	
			if ( type === "object" ) {
				if ( red instanceof color ) {
					each( spaces, function( spaceName, space ) {
						if ( red[ space.cache ] ) {
							inst[ space.cache ] = red[ space.cache ].slice();
						}
					});
				} else {
					each( spaces, function( spaceName, space ) {
						var cache = space.cache;
						each( space.props, function( key, prop ) {
	
							// if the cache doesn't exist, and we know how to convert
							if ( !inst[ cache ] && space.to ) {
	
								// if the value was null, we don't need to copy it
								// if the key was alpha, we don't need to copy it either
								if ( key === "alpha" || red[ key ] == null ) {
									return;
								}
								inst[ cache ] = space.to( inst._rgba );
							}
	
							// this is the only case where we allow nulls for ALL properties.
							// call clamp with alwaysAllowEmpty
							inst[ cache ][ prop.idx ] = clamp( red[ key ], prop, true );
						});
	
						// everything defined but alpha?
						if ( inst[ cache ] && jQuery.inArray( null, inst[ cache ].slice( 0, 3 ) ) < 0 ) {
							// use the default of 1
							inst[ cache ][ 3 ] = 1;
							if ( space.from ) {
								inst._rgba = space.from( inst[ cache ] );
							}
						}
					});
				}
				return this;
			}
		},
		is: function( compare ) {
			var is = color( compare ),
				same = true,
				inst = this;
	
			each( spaces, function( _, space ) {
				var localCache,
					isCache = is[ space.cache ];
				if (isCache) {
					localCache = inst[ space.cache ] || space.to && space.to( inst._rgba ) || [];
					each( space.props, function( _, prop ) {
						if ( isCache[ prop.idx ] != null ) {
							same = ( isCache[ prop.idx ] === localCache[ prop.idx ] );
							return same;
						}
					});
				}
				return same;
			});
			return same;
		},
		_space: function() {
			var used = [],
				inst = this;
			each( spaces, function( spaceName, space ) {
				if ( inst[ space.cache ] ) {
					used.push( spaceName );
				}
			});
			return used.pop();
		},
		transition: function( other, distance ) {
			var end = color( other ),
				spaceName = end._space(),
				space = spaces[ spaceName ],
				startColor = this.alpha() === 0 ? color( "transparent" ) : this,
				start = startColor[ space.cache ] || space.to( startColor._rgba ),
				result = start.slice();
	
			end = end[ space.cache ];
			each( space.props, function( key, prop ) {
				var index = prop.idx,
					startValue = start[ index ],
					endValue = end[ index ],
					type = propTypes[ prop.type ] || {};
	
				// if null, don't override start value
				if ( endValue === null ) {
					return;
				}
				// if null - use end
				if ( startValue === null ) {
					result[ index ] = endValue;
				} else {
					if ( type.mod ) {
						if ( endValue - startValue > type.mod / 2 ) {
							startValue += type.mod;
						} else if ( startValue - endValue > type.mod / 2 ) {
							startValue -= type.mod;
						}
					}
					result[ index ] = clamp( ( endValue - startValue ) * distance + startValue, prop );
				}
			});
			return this[ spaceName ]( result );
		},
		blend: function( opaque ) {
			// if we are already opaque - return ourself
			if ( this._rgba[ 3 ] === 1 ) {
				return this;
			}
	
			var rgb = this._rgba.slice(),
				a = rgb.pop(),
				blend = color( opaque )._rgba;
	
			return color( jQuery.map( rgb, function( v, i ) {
				return ( 1 - a ) * blend[ i ] + a * v;
			}));
		},
		toRgbaString: function() {
			var prefix = "rgba(",
				rgba = jQuery.map( this._rgba, function( v, i ) {
					return v == null ? ( i > 2 ? 1 : 0 ) : v;
				});
	
			if ( rgba[ 3 ] === 1 ) {
				rgba.pop();
				prefix = "rgb(";
			}
	
			return prefix + rgba.join() + ")";
		},
		toHslaString: function() {
			var prefix = "hsla(",
				hsla = jQuery.map( this.hsla(), function( v, i ) {
					if ( v == null ) {
						v = i > 2 ? 1 : 0;
					}
	
					// catch 1 and 2
					if ( i && i < 3 ) {
						v = Math.round( v * 100 ) + "%";
					}
					return v;
				});
	
			if ( hsla[ 3 ] === 1 ) {
				hsla.pop();
				prefix = "hsl(";
			}
			return prefix + hsla.join() + ")";
		},
		toHexString: function( includeAlpha ) {
			var rgba = this._rgba.slice(),
				alpha = rgba.pop();
	
			if ( includeAlpha ) {
				rgba.push( ~~( alpha * 255 ) );
			}
	
			return "#" + jQuery.map( rgba, function( v ) {
	
				// default to 0 when nulls exist
				v = ( v || 0 ).toString( 16 );
				return v.length === 1 ? "0" + v : v;
			}).join("");
		},
		toString: function() {
			return this._rgba[ 3 ] === 0 ? "transparent" : this.toRgbaString();
		}
	});
	color.fn.parse.prototype = color.fn;
	
	// hsla conversions adapted from:
	// https://code.google.com/p/maashaack/source/browse/packages/graphics/trunk/src/graphics/colors/HUE2RGB.as?r=5021
	
	function hue2rgb( p, q, h ) {
		h = ( h + 1 ) % 1;
		if ( h * 6 < 1 ) {
			return p + ( q - p ) * h * 6;
		}
		if ( h * 2 < 1) {
			return q;
		}
		if ( h * 3 < 2 ) {
			return p + ( q - p ) * ( ( 2 / 3 ) - h ) * 6;
		}
		return p;
	}
	
	spaces.hsla.to = function( rgba ) {
		if ( rgba[ 0 ] == null || rgba[ 1 ] == null || rgba[ 2 ] == null ) {
			return [ null, null, null, rgba[ 3 ] ];
		}
		var r = rgba[ 0 ] / 255,
			g = rgba[ 1 ] / 255,
			b = rgba[ 2 ] / 255,
			a = rgba[ 3 ],
			max = Math.max( r, g, b ),
			min = Math.min( r, g, b ),
			diff = max - min,
			add = max + min,
			l = add * 0.5,
			h, s;
	
		if ( min === max ) {
			h = 0;
		} else if ( r === max ) {
			h = ( 60 * ( g - b ) / diff ) + 360;
		} else if ( g === max ) {
			h = ( 60 * ( b - r ) / diff ) + 120;
		} else {
			h = ( 60 * ( r - g ) / diff ) + 240;
		}
	
		// chroma (diff) == 0 means greyscale which, by definition, saturation = 0%
		// otherwise, saturation is based on the ratio of chroma (diff) to lightness (add)
		if ( diff === 0 ) {
			s = 0;
		} else if ( l <= 0.5 ) {
			s = diff / add;
		} else {
			s = diff / ( 2 - add );
		}
		return [ Math.round(h) % 360, s, l, a == null ? 1 : a ];
	};
	
	spaces.hsla.from = function( hsla ) {
		if ( hsla[ 0 ] == null || hsla[ 1 ] == null || hsla[ 2 ] == null ) {
			return [ null, null, null, hsla[ 3 ] ];
		}
		var h = hsla[ 0 ] / 360,
			s = hsla[ 1 ],
			l = hsla[ 2 ],
			a = hsla[ 3 ],
			q = l <= 0.5 ? l * ( 1 + s ) : l + s - l * s,
			p = 2 * l - q;
	
		return [
			Math.round( hue2rgb( p, q, h + ( 1 / 3 ) ) * 255 ),
			Math.round( hue2rgb( p, q, h ) * 255 ),
			Math.round( hue2rgb( p, q, h - ( 1 / 3 ) ) * 255 ),
			a
		];
	};
	
	each( spaces, function( spaceName, space ) {
		var props = space.props,
			cache = space.cache,
			to = space.to,
			from = space.from;
	
		// makes rgba() and hsla()
		color.fn[ spaceName ] = function( value ) {
	
			// generate a cache for this space if it doesn't exist
			if ( to && !this[ cache ] ) {
				this[ cache ] = to( this._rgba );
			}
			if ( value === undefined ) {
				return this[ cache ].slice();
			}
	
			var ret,
				type = jQuery.type( value ),
				arr = ( type === "array" || type === "object" ) ? value : arguments,
				local = this[ cache ].slice();
	
			each( props, function( key, prop ) {
				var val = arr[ type === "object" ? key : prop.idx ];
				if ( val == null ) {
					val = local[ prop.idx ];
				}
				local[ prop.idx ] = clamp( val, prop );
			});
	
			if ( from ) {
				ret = color( from( local ) );
				ret[ cache ] = local;
				return ret;
			} else {
				return color( local );
			}
		};
	
		// makes red() green() blue() alpha() hue() saturation() lightness()
		each( props, function( key, prop ) {
			// alpha is included in more than one space
			if ( color.fn[ key ] ) {
				return;
			}
			color.fn[ key ] = function( value ) {
				var vtype = jQuery.type( value ),
					fn = ( key === "alpha" ? ( this._hsla ? "hsla" : "rgba" ) : spaceName ),
					local = this[ fn ](),
					cur = local[ prop.idx ],
					match;
	
				if ( vtype === "undefined" ) {
					return cur;
				}
	
				if ( vtype === "function" ) {
					value = value.call( this, cur );
					vtype = jQuery.type( value );
				}
				if ( value == null && prop.empty ) {
					return this;
				}
				if ( vtype === "string" ) {
					match = rplusequals.exec( value );
					if ( match ) {
						value = cur + parseFloat( match[ 2 ] ) * ( match[ 1 ] === "+" ? 1 : -1 );
					}
				}
				local[ prop.idx ] = value;
				return this[ fn ]( local );
			};
		});
	});
	
	// add cssHook and .fx.step function for each named hook.
	// accept a space separated string of properties
	color.hook = function( hook ) {
		var hooks = hook.split( " " );
		each( hooks, function( i, hook ) {
			jQuery.cssHooks[ hook ] = {
				set: function( elem, value ) {
					var parsed, curElem,
						backgroundColor = "";
	
					if ( value !== "transparent" && ( jQuery.type( value ) !== "string" || ( parsed = stringParse( value ) ) ) ) {
						value = color( parsed || value );
						if ( !support.rgba && value._rgba[ 3 ] !== 1 ) {
							curElem = hook === "backgroundColor" ? elem.parentNode : elem;
							while (
								(backgroundColor === "" || backgroundColor === "transparent") &&
								curElem && curElem.style
							) {
								try {
									backgroundColor = jQuery.css( curElem, "backgroundColor" );
									curElem = curElem.parentNode;
								} catch ( e ) {
								}
							}
	
							value = value.blend( backgroundColor && backgroundColor !== "transparent" ?
								backgroundColor :
								"_default" );
						}
	
						value = value.toRgbaString();
					}
					try {
						elem.style[ hook ] = value;
					} catch ( e ) {
						// wrapped to prevent IE from throwing errors on "invalid" values like 'auto' or 'inherit'
					}
				}
			};
			jQuery.fx.step[ hook ] = function( fx ) {
				if ( !fx.colorInit ) {
					fx.start = color( fx.elem, hook );
					fx.end = color( fx.end );
					fx.colorInit = true;
				}
				jQuery.cssHooks[ hook ].set( fx.elem, fx.start.transition( fx.end, fx.pos ) );
			};
		});
	
	};
	
	color.hook( stepHooks );
	
	jQuery.cssHooks.borderColor = {
		expand: function( value ) {
			var expanded = {};
	
			each( [ "Top", "Right", "Bottom", "Left" ], function( i, part ) {
				expanded[ "border" + part + "Color" ] = value;
			});
			return expanded;
		}
	};
	
	// Basic color names only.
	// Usage of any of the other color names requires adding yourself or including
	// jquery.color.svg-names.js.
	colors = jQuery.Color.names = {
		// 4.1. Basic color keywords
		aqua: "#00ffff",
		black: "#000000",
		blue: "#0000ff",
		fuchsia: "#ff00ff",
		gray: "#808080",
		green: "#008000",
		lime: "#00ff00",
		maroon: "#800000",
		navy: "#000080",
		olive: "#808000",
		purple: "#800080",
		red: "#ff0000",
		silver: "#c0c0c0",
		teal: "#008080",
		white: "#ffffff",
		yellow: "#ffff00",
	
		// 4.2.3. "transparent" color keyword
		transparent: [ null, null, null, 0 ],
	
		_default: "#ffffff"
	};
	
	})( jQuery );
	
	/******************************************************************************/
	/****************************** CLASS ANIMATIONS ******************************/
	/******************************************************************************/
	(function() {
	
	var classAnimationActions = [ "add", "remove", "toggle" ],
		shorthandStyles = {
			border: 1,
			borderBottom: 1,
			borderColor: 1,
			borderLeft: 1,
			borderRight: 1,
			borderTop: 1,
			borderWidth: 1,
			margin: 1,
			padding: 1
		};
	
	$.each([ "borderLeftStyle", "borderRightStyle", "borderBottomStyle", "borderTopStyle" ], function( _, prop ) {
		$.fx.step[ prop ] = function( fx ) {
			if ( fx.end !== "none" && !fx.setAttr || fx.pos === 1 && !fx.setAttr ) {
				jQuery.style( fx.elem, prop, fx.end );
				fx.setAttr = true;
			}
		};
	});
	
	function getElementStyles( elem ) {
		var key, len,
			style = elem.ownerDocument.defaultView ?
				elem.ownerDocument.defaultView.getComputedStyle( elem, null ) :
				elem.currentStyle,
			styles = {};
	
		if ( style && style.length && style[ 0 ] && style[ style[ 0 ] ] ) {
			len = style.length;
			while ( len-- ) {
				key = style[ len ];
				if ( typeof style[ key ] === "string" ) {
					styles[ $.camelCase( key ) ] = style[ key ];
				}
			}
		// support: Opera, IE <9
		} else {
			for ( key in style ) {
				if ( typeof style[ key ] === "string" ) {
					styles[ key ] = style[ key ];
				}
			}
		}
	
		return styles;
	}
	
	function styleDifference( oldStyle, newStyle ) {
		var diff = {},
			name, value;
	
		for ( name in newStyle ) {
			value = newStyle[ name ];
			if ( oldStyle[ name ] !== value ) {
				if ( !shorthandStyles[ name ] ) {
					if ( $.fx.step[ name ] || !isNaN( parseFloat( value ) ) ) {
						diff[ name ] = value;
					}
				}
			}
		}
	
		return diff;
	}
	
	// support: jQuery <1.8
	if ( !$.fn.addBack ) {
		$.fn.addBack = function( selector ) {
			return this.add( selector == null ?
				this.prevObject : this.prevObject.filter( selector )
			);
		};
	}
	
	$.effects.animateClass = function( value, duration, easing, callback ) {
		var o = $.speed( duration, easing, callback );
	
		return this.queue( function() {
			var animated = $( this ),
				baseClass = animated.attr( "class" ) || "",
				applyClassChange,
				allAnimations = o.children ? animated.find( "*" ).addBack() : animated;
	
			// map the animated objects to store the original styles.
			allAnimations = allAnimations.map(function() {
				var el = $( this );
				return {
					el: el,
					start: getElementStyles( this )
				};
			});
	
			// apply class change
			applyClassChange = function() {
				$.each( classAnimationActions, function(i, action) {
					if ( value[ action ] ) {
						animated[ action + "Class" ]( value[ action ] );
					}
				});
			};
			applyClassChange();
	
			// map all animated objects again - calculate new styles and diff
			allAnimations = allAnimations.map(function() {
				this.end = getElementStyles( this.el[ 0 ] );
				this.diff = styleDifference( this.start, this.end );
				return this;
			});
	
			// apply original class
			animated.attr( "class", baseClass );
	
			// map all animated objects again - this time collecting a promise
			allAnimations = allAnimations.map(function() {
				var styleInfo = this,
					dfd = $.Deferred(),
					opts = $.extend({}, o, {
						queue: false,
						complete: function() {
							dfd.resolve( styleInfo );
						}
					});
	
				this.el.animate( this.diff, opts );
				return dfd.promise();
			});
	
			// once all animations have completed:
			$.when.apply( $, allAnimations.get() ).done(function() {
	
				// set the final class
				applyClassChange();
	
				// for each animated element,
				// clear all css properties that were animated
				$.each( arguments, function() {
					var el = this.el;
					$.each( this.diff, function(key) {
						el.css( key, "" );
					});
				});
	
				// this is guarnteed to be there if you use jQuery.speed()
				// it also handles dequeuing the next anim...
				o.complete.call( animated[ 0 ] );
			});
		});
	};
	
	$.fn.extend({
		addClass: (function( orig ) {
			return function( classNames, speed, easing, callback ) {
				return speed ?
					$.effects.animateClass.call( this,
						{ add: classNames }, speed, easing, callback ) :
					orig.apply( this, arguments );
			};
		})( $.fn.addClass ),
	
		removeClass: (function( orig ) {
			return function( classNames, speed, easing, callback ) {
				return arguments.length > 1 ?
					$.effects.animateClass.call( this,
						{ remove: classNames }, speed, easing, callback ) :
					orig.apply( this, arguments );
			};
		})( $.fn.removeClass ),
	
		toggleClass: (function( orig ) {
			return function( classNames, force, speed, easing, callback ) {
				if ( typeof force === "boolean" || force === undefined ) {
					if ( !speed ) {
						// without speed parameter
						return orig.apply( this, arguments );
					} else {
						return $.effects.animateClass.call( this,
							(force ? { add: classNames } : { remove: classNames }),
							speed, easing, callback );
					}
				} else {
					// without force parameter
					return $.effects.animateClass.call( this,
						{ toggle: classNames }, force, speed, easing );
				}
			};
		})( $.fn.toggleClass ),
	
		switchClass: function( remove, add, speed, easing, callback) {
			return $.effects.animateClass.call( this, {
				add: add,
				remove: remove
			}, speed, easing, callback );
		}
	});
	
	})();
	
	/******************************************************************************/
	/*********************************** EFFECTS **********************************/
	/******************************************************************************/
	
	(function() {
	
	$.extend( $.effects, {
		version: "1.11.4",
	
		// Saves a set of properties in a data storage
		save: function( element, set ) {
			for ( var i = 0; i < set.length; i++ ) {
				if ( set[ i ] !== null ) {
					element.data( dataSpace + set[ i ], element[ 0 ].style[ set[ i ] ] );
				}
			}
		},
	
		// Restores a set of previously saved properties from a data storage
		restore: function( element, set ) {
			var val, i;
			for ( i = 0; i < set.length; i++ ) {
				if ( set[ i ] !== null ) {
					val = element.data( dataSpace + set[ i ] );
					// support: jQuery 1.6.2
					// http://bugs.jquery.com/ticket/9917
					// jQuery 1.6.2 incorrectly returns undefined for any falsy value.
					// We can't differentiate between "" and 0 here, so we just assume
					// empty string since it's likely to be a more common value...
					if ( val === undefined ) {
						val = "";
					}
					element.css( set[ i ], val );
				}
			}
		},
	
		setMode: function( el, mode ) {
			if (mode === "toggle") {
				mode = el.is( ":hidden" ) ? "show" : "hide";
			}
			return mode;
		},
	
		// Translates a [top,left] array into a baseline value
		// this should be a little more flexible in the future to handle a string & hash
		getBaseline: function( origin, original ) {
			var y, x;
			switch ( origin[ 0 ] ) {
				case "top": y = 0; break;
				case "middle": y = 0.5; break;
				case "bottom": y = 1; break;
				default: y = origin[ 0 ] / original.height;
			}
			switch ( origin[ 1 ] ) {
				case "left": x = 0; break;
				case "center": x = 0.5; break;
				case "right": x = 1; break;
				default: x = origin[ 1 ] / original.width;
			}
			return {
				x: x,
				y: y
			};
		},
	
		// Wraps the element around a wrapper that copies position properties
		createWrapper: function( element ) {
	
			// if the element is already wrapped, return it
			if ( element.parent().is( ".ui-effects-wrapper" )) {
				return element.parent();
			}
	
			// wrap the element
			var props = {
					width: element.outerWidth(true),
					height: element.outerHeight(true),
					"float": element.css( "float" )
				},
				wrapper = $( "<div></div>" )
					.addClass( "ui-effects-wrapper" )
					.css({
						fontSize: "100%",
						background: "transparent",
						border: "none",
						margin: 0,
						padding: 0
					}),
				// Store the size in case width/height are defined in % - Fixes #5245
				size = {
					width: element.width(),
					height: element.height()
				},
				active = document.activeElement;
	
			// support: Firefox
			// Firefox incorrectly exposes anonymous content
			// https://bugzilla.mozilla.org/show_bug.cgi?id=561664
			try {
				active.id;
			} catch ( e ) {
				active = document.body;
			}
	
			element.wrap( wrapper );
	
			// Fixes #7595 - Elements lose focus when wrapped.
			if ( element[ 0 ] === active || $.contains( element[ 0 ], active ) ) {
				$( active ).focus();
			}
	
			wrapper = element.parent(); //Hotfix for jQuery 1.4 since some change in wrap() seems to actually lose the reference to the wrapped element
	
			// transfer positioning properties to the wrapper
			if ( element.css( "position" ) === "static" ) {
				wrapper.css({ position: "relative" });
				element.css({ position: "relative" });
			} else {
				$.extend( props, {
					position: element.css( "position" ),
					zIndex: element.css( "z-index" )
				});
				$.each([ "top", "left", "bottom", "right" ], function(i, pos) {
					props[ pos ] = element.css( pos );
					if ( isNaN( parseInt( props[ pos ], 10 ) ) ) {
						props[ pos ] = "auto";
					}
				});
				element.css({
					position: "relative",
					top: 0,
					left: 0,
					right: "auto",
					bottom: "auto"
				});
			}
			element.css(size);
	
			return wrapper.css( props ).show();
		},
	
		removeWrapper: function( element ) {
			var active = document.activeElement;
	
			if ( element.parent().is( ".ui-effects-wrapper" ) ) {
				element.parent().replaceWith( element );
	
				// Fixes #7595 - Elements lose focus when wrapped.
				if ( element[ 0 ] === active || $.contains( element[ 0 ], active ) ) {
					$( active ).focus();
				}
			}
	
			return element;
		},
	
		setTransition: function( element, list, factor, value ) {
			value = value || {};
			$.each( list, function( i, x ) {
				var unit = element.cssUnit( x );
				if ( unit[ 0 ] > 0 ) {
					value[ x ] = unit[ 0 ] * factor + unit[ 1 ];
				}
			});
			return value;
		}
	});
	
	// return an effect options object for the given parameters:
	function _normalizeArguments( effect, options, speed, callback ) {
	
		// allow passing all options as the first parameter
		if ( $.isPlainObject( effect ) ) {
			options = effect;
			effect = effect.effect;
		}
	
		// convert to an object
		effect = { effect: effect };
	
		// catch (effect, null, ...)
		if ( options == null ) {
			options = {};
		}
	
		// catch (effect, callback)
		if ( $.isFunction( options ) ) {
			callback = options;
			speed = null;
			options = {};
		}
	
		// catch (effect, speed, ?)
		if ( typeof options === "number" || $.fx.speeds[ options ] ) {
			callback = speed;
			speed = options;
			options = {};
		}
	
		// catch (effect, options, callback)
		if ( $.isFunction( speed ) ) {
			callback = speed;
			speed = null;
		}
	
		// add options to effect
		if ( options ) {
			$.extend( effect, options );
		}
	
		speed = speed || options.duration;
		effect.duration = $.fx.off ? 0 :
			typeof speed === "number" ? speed :
			speed in $.fx.speeds ? $.fx.speeds[ speed ] :
			$.fx.speeds._default;
	
		effect.complete = callback || options.complete;
	
		return effect;
	}
	
	function standardAnimationOption( option ) {
		// Valid standard speeds (nothing, number, named speed)
		if ( !option || typeof option === "number" || $.fx.speeds[ option ] ) {
			return true;
		}
	
		// Invalid strings - treat as "normal" speed
		if ( typeof option === "string" && !$.effects.effect[ option ] ) {
			return true;
		}
	
		// Complete callback
		if ( $.isFunction( option ) ) {
			return true;
		}
	
		// Options hash (but not naming an effect)
		if ( typeof option === "object" && !option.effect ) {
			return true;
		}
	
		// Didn't match any standard API
		return false;
	}
	
	$.fn.extend({
		effect: function( /* effect, options, speed, callback */ ) {
			var args = _normalizeArguments.apply( this, arguments ),
				mode = args.mode,
				queue = args.queue,
				effectMethod = $.effects.effect[ args.effect ];
	
			if ( $.fx.off || !effectMethod ) {
				// delegate to the original method (e.g., .show()) if possible
				if ( mode ) {
					return this[ mode ]( args.duration, args.complete );
				} else {
					return this.each( function() {
						if ( args.complete ) {
							args.complete.call( this );
						}
					});
				}
			}
	
			function run( next ) {
				var elem = $( this ),
					complete = args.complete,
					mode = args.mode;
	
				function done() {
					if ( $.isFunction( complete ) ) {
						complete.call( elem[0] );
					}
					if ( $.isFunction( next ) ) {
						next();
					}
				}
	
				// If the element already has the correct final state, delegate to
				// the core methods so the internal tracking of "olddisplay" works.
				if ( elem.is( ":hidden" ) ? mode === "hide" : mode === "show" ) {
					elem[ mode ]();
					done();
				} else {
					effectMethod.call( elem[0], args, done );
				}
			}
	
			return queue === false ? this.each( run ) : this.queue( queue || "fx", run );
		},
	
		show: (function( orig ) {
			return function( option ) {
				if ( standardAnimationOption( option ) ) {
					return orig.apply( this, arguments );
				} else {
					var args = _normalizeArguments.apply( this, arguments );
					args.mode = "show";
					return this.effect.call( this, args );
				}
			};
		})( $.fn.show ),
	
		hide: (function( orig ) {
			return function( option ) {
				if ( standardAnimationOption( option ) ) {
					return orig.apply( this, arguments );
				} else {
					var args = _normalizeArguments.apply( this, arguments );
					args.mode = "hide";
					return this.effect.call( this, args );
				}
			};
		})( $.fn.hide ),
	
		toggle: (function( orig ) {
			return function( option ) {
				if ( standardAnimationOption( option ) || typeof option === "boolean" ) {
					return orig.apply( this, arguments );
				} else {
					var args = _normalizeArguments.apply( this, arguments );
					args.mode = "toggle";
					return this.effect.call( this, args );
				}
			};
		})( $.fn.toggle ),
	
		// helper functions
		cssUnit: function(key) {
			var style = this.css( key ),
				val = [];
	
			$.each( [ "em", "px", "%", "pt" ], function( i, unit ) {
				if ( style.indexOf( unit ) > 0 ) {
					val = [ parseFloat( style ), unit ];
				}
			});
			return val;
		}
	});
	
	})();
	
	/******************************************************************************/
	/*********************************** EASING ***********************************/
	/******************************************************************************/
	
	(function() {
	
	// based on easing equations from Robert Penner (http://www.robertpenner.com/easing)
	
	var baseEasings = {};
	
	$.each( [ "Quad", "Cubic", "Quart", "Quint", "Expo" ], function( i, name ) {
		baseEasings[ name ] = function( p ) {
			return Math.pow( p, i + 2 );
		};
	});
	
	$.extend( baseEasings, {
		Sine: function( p ) {
			return 1 - Math.cos( p * Math.PI / 2 );
		},
		Circ: function( p ) {
			return 1 - Math.sqrt( 1 - p * p );
		},
		Elastic: function( p ) {
			return p === 0 || p === 1 ? p :
				-Math.pow( 2, 8 * (p - 1) ) * Math.sin( ( (p - 1) * 80 - 7.5 ) * Math.PI / 15 );
		},
		Back: function( p ) {
			return p * p * ( 3 * p - 2 );
		},
		Bounce: function( p ) {
			var pow2,
				bounce = 4;
	
			while ( p < ( ( pow2 = Math.pow( 2, --bounce ) ) - 1 ) / 11 ) {}
			return 1 / Math.pow( 4, 3 - bounce ) - 7.5625 * Math.pow( ( pow2 * 3 - 2 ) / 22 - p, 2 );
		}
	});
	
	$.each( baseEasings, function( name, easeIn ) {
		$.easing[ "easeIn" + name ] = easeIn;
		$.easing[ "easeOut" + name ] = function( p ) {
			return 1 - easeIn( 1 - p );
		};
		$.easing[ "easeInOut" + name ] = function( p ) {
			return p < 0.5 ?
				easeIn( p * 2 ) / 2 :
				1 - easeIn( p * -2 + 2 ) / 2;
		};
	});
	
	})();
	
	var effect = $.effects;
	
	
	/*!
	 * jQuery UI Effects Blind 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/blind-effect/
	 */
	
	
	var effectBlind = $.effects.effect.blind = function( o, done ) {
		// Create element
		var el = $( this ),
			rvertical = /up|down|vertical/,
			rpositivemotion = /up|left|vertical|horizontal/,
			props = [ "position", "top", "bottom", "left", "right", "height", "width" ],
			mode = $.effects.setMode( el, o.mode || "hide" ),
			direction = o.direction || "up",
			vertical = rvertical.test( direction ),
			ref = vertical ? "height" : "width",
			ref2 = vertical ? "top" : "left",
			motion = rpositivemotion.test( direction ),
			animation = {},
			show = mode === "show",
			wrapper, distance, margin;
	
		// if already wrapped, the wrapper's properties are my property. #6245
		if ( el.parent().is( ".ui-effects-wrapper" ) ) {
			$.effects.save( el.parent(), props );
		} else {
			$.effects.save( el, props );
		}
		el.show();
		wrapper = $.effects.createWrapper( el ).css({
			overflow: "hidden"
		});
	
		distance = wrapper[ ref ]();
		margin = parseFloat( wrapper.css( ref2 ) ) || 0;
	
		animation[ ref ] = show ? distance : 0;
		if ( !motion ) {
			el
				.css( vertical ? "bottom" : "right", 0 )
				.css( vertical ? "top" : "left", "auto" )
				.css({ position: "absolute" });
	
			animation[ ref2 ] = show ? margin : distance + margin;
		}
	
		// start at 0 if we are showing
		if ( show ) {
			wrapper.css( ref, 0 );
			if ( !motion ) {
				wrapper.css( ref2, margin + distance );
			}
		}
	
		// Animate
		wrapper.animate( animation, {
			duration: o.duration,
			easing: o.easing,
			queue: false,
			complete: function() {
				if ( mode === "hide" ) {
					el.hide();
				}
				$.effects.restore( el, props );
				$.effects.removeWrapper( el );
				done();
			}
		});
	};
	
	
	/*!
	 * jQuery UI Effects Bounce 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/bounce-effect/
	 */
	
	
	var effectBounce = $.effects.effect.bounce = function( o, done ) {
		var el = $( this ),
			props = [ "position", "top", "bottom", "left", "right", "height", "width" ],
	
			// defaults:
			mode = $.effects.setMode( el, o.mode || "effect" ),
			hide = mode === "hide",
			show = mode === "show",
			direction = o.direction || "up",
			distance = o.distance,
			times = o.times || 5,
	
			// number of internal animations
			anims = times * 2 + ( show || hide ? 1 : 0 ),
			speed = o.duration / anims,
			easing = o.easing,
	
			// utility:
			ref = ( direction === "up" || direction === "down" ) ? "top" : "left",
			motion = ( direction === "up" || direction === "left" ),
			i,
			upAnim,
			downAnim,
	
			// we will need to re-assemble the queue to stack our animations in place
			queue = el.queue(),
			queuelen = queue.length;
	
		// Avoid touching opacity to prevent clearType and PNG issues in IE
		if ( show || hide ) {
			props.push( "opacity" );
		}
	
		$.effects.save( el, props );
		el.show();
		$.effects.createWrapper( el ); // Create Wrapper
	
		// default distance for the BIGGEST bounce is the outer Distance / 3
		if ( !distance ) {
			distance = el[ ref === "top" ? "outerHeight" : "outerWidth" ]() / 3;
		}
	
		if ( show ) {
			downAnim = { opacity: 1 };
			downAnim[ ref ] = 0;
	
			// if we are showing, force opacity 0 and set the initial position
			// then do the "first" animation
			el.css( "opacity", 0 )
				.css( ref, motion ? -distance * 2 : distance * 2 )
				.animate( downAnim, speed, easing );
		}
	
		// start at the smallest distance if we are hiding
		if ( hide ) {
			distance = distance / Math.pow( 2, times - 1 );
		}
	
		downAnim = {};
		downAnim[ ref ] = 0;
		// Bounces up/down/left/right then back to 0 -- times * 2 animations happen here
		for ( i = 0; i < times; i++ ) {
			upAnim = {};
			upAnim[ ref ] = ( motion ? "-=" : "+=" ) + distance;
	
			el.animate( upAnim, speed, easing )
				.animate( downAnim, speed, easing );
	
			distance = hide ? distance * 2 : distance / 2;
		}
	
		// Last Bounce when Hiding
		if ( hide ) {
			upAnim = { opacity: 0 };
			upAnim[ ref ] = ( motion ? "-=" : "+=" ) + distance;
	
			el.animate( upAnim, speed, easing );
		}
	
		el.queue(function() {
			if ( hide ) {
				el.hide();
			}
			$.effects.restore( el, props );
			$.effects.removeWrapper( el );
			done();
		});
	
		// inject all the animations we just queued to be first in line (after "inprogress")
		if ( queuelen > 1) {
			queue.splice.apply( queue,
				[ 1, 0 ].concat( queue.splice( queuelen, anims + 1 ) ) );
		}
		el.dequeue();
	
	};
	
	
	/*!
	 * jQuery UI Effects Clip 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/clip-effect/
	 */
	
	
	var effectClip = $.effects.effect.clip = function( o, done ) {
		// Create element
		var el = $( this ),
			props = [ "position", "top", "bottom", "left", "right", "height", "width" ],
			mode = $.effects.setMode( el, o.mode || "hide" ),
			show = mode === "show",
			direction = o.direction || "vertical",
			vert = direction === "vertical",
			size = vert ? "height" : "width",
			position = vert ? "top" : "left",
			animation = {},
			wrapper, animate, distance;
	
		// Save & Show
		$.effects.save( el, props );
		el.show();
	
		// Create Wrapper
		wrapper = $.effects.createWrapper( el ).css({
			overflow: "hidden"
		});
		animate = ( el[0].tagName === "IMG" ) ? wrapper : el;
		distance = animate[ size ]();
	
		// Shift
		if ( show ) {
			animate.css( size, 0 );
			animate.css( position, distance / 2 );
		}
	
		// Create Animation Object:
		animation[ size ] = show ? distance : 0;
		animation[ position ] = show ? 0 : distance / 2;
	
		// Animate
		animate.animate( animation, {
			queue: false,
			duration: o.duration,
			easing: o.easing,
			complete: function() {
				if ( !show ) {
					el.hide();
				}
				$.effects.restore( el, props );
				$.effects.removeWrapper( el );
				done();
			}
		});
	
	};
	
	
	/*!
	 * jQuery UI Effects Drop 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/drop-effect/
	 */
	
	
	var effectDrop = $.effects.effect.drop = function( o, done ) {
	
		var el = $( this ),
			props = [ "position", "top", "bottom", "left", "right", "opacity", "height", "width" ],
			mode = $.effects.setMode( el, o.mode || "hide" ),
			show = mode === "show",
			direction = o.direction || "left",
			ref = ( direction === "up" || direction === "down" ) ? "top" : "left",
			motion = ( direction === "up" || direction === "left" ) ? "pos" : "neg",
			animation = {
				opacity: show ? 1 : 0
			},
			distance;
	
		// Adjust
		$.effects.save( el, props );
		el.show();
		$.effects.createWrapper( el );
	
		distance = o.distance || el[ ref === "top" ? "outerHeight" : "outerWidth" ]( true ) / 2;
	
		if ( show ) {
			el
				.css( "opacity", 0 )
				.css( ref, motion === "pos" ? -distance : distance );
		}
	
		// Animation
		animation[ ref ] = ( show ?
			( motion === "pos" ? "+=" : "-=" ) :
			( motion === "pos" ? "-=" : "+=" ) ) +
			distance;
	
		// Animate
		el.animate( animation, {
			queue: false,
			duration: o.duration,
			easing: o.easing,
			complete: function() {
				if ( mode === "hide" ) {
					el.hide();
				}
				$.effects.restore( el, props );
				$.effects.removeWrapper( el );
				done();
			}
		});
	};
	
	
	/*!
	 * jQuery UI Effects Explode 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/explode-effect/
	 */
	
	
	var effectExplode = $.effects.effect.explode = function( o, done ) {
	
		var rows = o.pieces ? Math.round( Math.sqrt( o.pieces ) ) : 3,
			cells = rows,
			el = $( this ),
			mode = $.effects.setMode( el, o.mode || "hide" ),
			show = mode === "show",
	
			// show and then visibility:hidden the element before calculating offset
			offset = el.show().css( "visibility", "hidden" ).offset(),
	
			// width and height of a piece
			width = Math.ceil( el.outerWidth() / cells ),
			height = Math.ceil( el.outerHeight() / rows ),
			pieces = [],
	
			// loop
			i, j, left, top, mx, my;
	
		// children animate complete:
		function childComplete() {
			pieces.push( this );
			if ( pieces.length === rows * cells ) {
				animComplete();
			}
		}
	
		// clone the element for each row and cell.
		for ( i = 0; i < rows ; i++ ) { // ===>
			top = offset.top + i * height;
			my = i - ( rows - 1 ) / 2 ;
	
			for ( j = 0; j < cells ; j++ ) { // |||
				left = offset.left + j * width;
				mx = j - ( cells - 1 ) / 2 ;
	
				// Create a clone of the now hidden main element that will be absolute positioned
				// within a wrapper div off the -left and -top equal to size of our pieces
				el
					.clone()
					.appendTo( "body" )
					.wrap( "<div></div>" )
					.css({
						position: "absolute",
						visibility: "visible",
						left: -j * width,
						top: -i * height
					})
	
				// select the wrapper - make it overflow: hidden and absolute positioned based on
				// where the original was located +left and +top equal to the size of pieces
					.parent()
					.addClass( "ui-effects-explode" )
					.css({
						position: "absolute",
						overflow: "hidden",
						width: width,
						height: height,
						left: left + ( show ? mx * width : 0 ),
						top: top + ( show ? my * height : 0 ),
						opacity: show ? 0 : 1
					}).animate({
						left: left + ( show ? 0 : mx * width ),
						top: top + ( show ? 0 : my * height ),
						opacity: show ? 1 : 0
					}, o.duration || 500, o.easing, childComplete );
			}
		}
	
		function animComplete() {
			el.css({
				visibility: "visible"
			});
			$( pieces ).remove();
			if ( !show ) {
				el.hide();
			}
			done();
		}
	};
	
	
	/*!
	 * jQuery UI Effects Fade 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/fade-effect/
	 */
	
	
	var effectFade = $.effects.effect.fade = function( o, done ) {
		var el = $( this ),
			mode = $.effects.setMode( el, o.mode || "toggle" );
	
		el.animate({
			opacity: mode
		}, {
			queue: false,
			duration: o.duration,
			easing: o.easing,
			complete: done
		});
	};
	
	
	/*!
	 * jQuery UI Effects Fold 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/fold-effect/
	 */
	
	
	var effectFold = $.effects.effect.fold = function( o, done ) {
	
		// Create element
		var el = $( this ),
			props = [ "position", "top", "bottom", "left", "right", "height", "width" ],
			mode = $.effects.setMode( el, o.mode || "hide" ),
			show = mode === "show",
			hide = mode === "hide",
			size = o.size || 15,
			percent = /([0-9]+)%/.exec( size ),
			horizFirst = !!o.horizFirst,
			widthFirst = show !== horizFirst,
			ref = widthFirst ? [ "width", "height" ] : [ "height", "width" ],
			duration = o.duration / 2,
			wrapper, distance,
			animation1 = {},
			animation2 = {};
	
		$.effects.save( el, props );
		el.show();
	
		// Create Wrapper
		wrapper = $.effects.createWrapper( el ).css({
			overflow: "hidden"
		});
		distance = widthFirst ?
			[ wrapper.width(), wrapper.height() ] :
			[ wrapper.height(), wrapper.width() ];
	
		if ( percent ) {
			size = parseInt( percent[ 1 ], 10 ) / 100 * distance[ hide ? 0 : 1 ];
		}
		if ( show ) {
			wrapper.css( horizFirst ? {
				height: 0,
				width: size
			} : {
				height: size,
				width: 0
			});
		}
	
		// Animation
		animation1[ ref[ 0 ] ] = show ? distance[ 0 ] : size;
		animation2[ ref[ 1 ] ] = show ? distance[ 1 ] : 0;
	
		// Animate
		wrapper
			.animate( animation1, duration, o.easing )
			.animate( animation2, duration, o.easing, function() {
				if ( hide ) {
					el.hide();
				}
				$.effects.restore( el, props );
				$.effects.removeWrapper( el );
				done();
			});
	
	};
	
	
	/*!
	 * jQuery UI Effects Highlight 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/highlight-effect/
	 */
	
	
	var effectHighlight = $.effects.effect.highlight = function( o, done ) {
		var elem = $( this ),
			props = [ "backgroundImage", "backgroundColor", "opacity" ],
			mode = $.effects.setMode( elem, o.mode || "show" ),
			animation = {
				backgroundColor: elem.css( "backgroundColor" )
			};
	
		if (mode === "hide") {
			animation.opacity = 0;
		}
	
		$.effects.save( elem, props );
	
		elem
			.show()
			.css({
				backgroundImage: "none",
				backgroundColor: o.color || "#ffff99"
			})
			.animate( animation, {
				queue: false,
				duration: o.duration,
				easing: o.easing,
				complete: function() {
					if ( mode === "hide" ) {
						elem.hide();
					}
					$.effects.restore( elem, props );
					done();
				}
			});
	};
	
	
	/*!
	 * jQuery UI Effects Size 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/size-effect/
	 */
	
	
	var effectSize = $.effects.effect.size = function( o, done ) {
	
		// Create element
		var original, baseline, factor,
			el = $( this ),
			props0 = [ "position", "top", "bottom", "left", "right", "width", "height", "overflow", "opacity" ],
	
			// Always restore
			props1 = [ "position", "top", "bottom", "left", "right", "overflow", "opacity" ],
	
			// Copy for children
			props2 = [ "width", "height", "overflow" ],
			cProps = [ "fontSize" ],
			vProps = [ "borderTopWidth", "borderBottomWidth", "paddingTop", "paddingBottom" ],
			hProps = [ "borderLeftWidth", "borderRightWidth", "paddingLeft", "paddingRight" ],
	
			// Set options
			mode = $.effects.setMode( el, o.mode || "effect" ),
			restore = o.restore || mode !== "effect",
			scale = o.scale || "both",
			origin = o.origin || [ "middle", "center" ],
			position = el.css( "position" ),
			props = restore ? props0 : props1,
			zero = {
				height: 0,
				width: 0,
				outerHeight: 0,
				outerWidth: 0
			};
	
		if ( mode === "show" ) {
			el.show();
		}
		original = {
			height: el.height(),
			width: el.width(),
			outerHeight: el.outerHeight(),
			outerWidth: el.outerWidth()
		};
	
		if ( o.mode === "toggle" && mode === "show" ) {
			el.from = o.to || zero;
			el.to = o.from || original;
		} else {
			el.from = o.from || ( mode === "show" ? zero : original );
			el.to = o.to || ( mode === "hide" ? zero : original );
		}
	
		// Set scaling factor
		factor = {
			from: {
				y: el.from.height / original.height,
				x: el.from.width / original.width
			},
			to: {
				y: el.to.height / original.height,
				x: el.to.width / original.width
			}
		};
	
		// Scale the css box
		if ( scale === "box" || scale === "both" ) {
	
			// Vertical props scaling
			if ( factor.from.y !== factor.to.y ) {
				props = props.concat( vProps );
				el.from = $.effects.setTransition( el, vProps, factor.from.y, el.from );
				el.to = $.effects.setTransition( el, vProps, factor.to.y, el.to );
			}
	
			// Horizontal props scaling
			if ( factor.from.x !== factor.to.x ) {
				props = props.concat( hProps );
				el.from = $.effects.setTransition( el, hProps, factor.from.x, el.from );
				el.to = $.effects.setTransition( el, hProps, factor.to.x, el.to );
			}
		}
	
		// Scale the content
		if ( scale === "content" || scale === "both" ) {
	
			// Vertical props scaling
			if ( factor.from.y !== factor.to.y ) {
				props = props.concat( cProps ).concat( props2 );
				el.from = $.effects.setTransition( el, cProps, factor.from.y, el.from );
				el.to = $.effects.setTransition( el, cProps, factor.to.y, el.to );
			}
		}
	
		$.effects.save( el, props );
		el.show();
		$.effects.createWrapper( el );
		el.css( "overflow", "hidden" ).css( el.from );
	
		// Adjust
		if (origin) { // Calculate baseline shifts
			baseline = $.effects.getBaseline( origin, original );
			el.from.top = ( original.outerHeight - el.outerHeight() ) * baseline.y;
			el.from.left = ( original.outerWidth - el.outerWidth() ) * baseline.x;
			el.to.top = ( original.outerHeight - el.to.outerHeight ) * baseline.y;
			el.to.left = ( original.outerWidth - el.to.outerWidth ) * baseline.x;
		}
		el.css( el.from ); // set top & left
	
		// Animate
		if ( scale === "content" || scale === "both" ) { // Scale the children
	
			// Add margins/font-size
			vProps = vProps.concat([ "marginTop", "marginBottom" ]).concat(cProps);
			hProps = hProps.concat([ "marginLeft", "marginRight" ]);
			props2 = props0.concat(vProps).concat(hProps);
	
			el.find( "*[width]" ).each( function() {
				var child = $( this ),
					c_original = {
						height: child.height(),
						width: child.width(),
						outerHeight: child.outerHeight(),
						outerWidth: child.outerWidth()
					};
				if (restore) {
					$.effects.save(child, props2);
				}
	
				child.from = {
					height: c_original.height * factor.from.y,
					width: c_original.width * factor.from.x,
					outerHeight: c_original.outerHeight * factor.from.y,
					outerWidth: c_original.outerWidth * factor.from.x
				};
				child.to = {
					height: c_original.height * factor.to.y,
					width: c_original.width * factor.to.x,
					outerHeight: c_original.height * factor.to.y,
					outerWidth: c_original.width * factor.to.x
				};
	
				// Vertical props scaling
				if ( factor.from.y !== factor.to.y ) {
					child.from = $.effects.setTransition( child, vProps, factor.from.y, child.from );
					child.to = $.effects.setTransition( child, vProps, factor.to.y, child.to );
				}
	
				// Horizontal props scaling
				if ( factor.from.x !== factor.to.x ) {
					child.from = $.effects.setTransition( child, hProps, factor.from.x, child.from );
					child.to = $.effects.setTransition( child, hProps, factor.to.x, child.to );
				}
	
				// Animate children
				child.css( child.from );
				child.animate( child.to, o.duration, o.easing, function() {
	
					// Restore children
					if ( restore ) {
						$.effects.restore( child, props2 );
					}
				});
			});
		}
	
		// Animate
		el.animate( el.to, {
			queue: false,
			duration: o.duration,
			easing: o.easing,
			complete: function() {
				if ( el.to.opacity === 0 ) {
					el.css( "opacity", el.from.opacity );
				}
				if ( mode === "hide" ) {
					el.hide();
				}
				$.effects.restore( el, props );
				if ( !restore ) {
	
					// we need to calculate our new positioning based on the scaling
					if ( position === "static" ) {
						el.css({
							position: "relative",
							top: el.to.top,
							left: el.to.left
						});
					} else {
						$.each([ "top", "left" ], function( idx, pos ) {
							el.css( pos, function( _, str ) {
								var val = parseInt( str, 10 ),
									toRef = idx ? el.to.left : el.to.top;
	
								// if original was "auto", recalculate the new value from wrapper
								if ( str === "auto" ) {
									return toRef + "px";
								}
	
								return val + toRef + "px";
							});
						});
					}
				}
	
				$.effects.removeWrapper( el );
				done();
			}
		});
	
	};
	
	
	/*!
	 * jQuery UI Effects Scale 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/scale-effect/
	 */
	
	
	var effectScale = $.effects.effect.scale = function( o, done ) {
	
		// Create element
		var el = $( this ),
			options = $.extend( true, {}, o ),
			mode = $.effects.setMode( el, o.mode || "effect" ),
			percent = parseInt( o.percent, 10 ) ||
				( parseInt( o.percent, 10 ) === 0 ? 0 : ( mode === "hide" ? 0 : 100 ) ),
			direction = o.direction || "both",
			origin = o.origin,
			original = {
				height: el.height(),
				width: el.width(),
				outerHeight: el.outerHeight(),
				outerWidth: el.outerWidth()
			},
			factor = {
				y: direction !== "horizontal" ? (percent / 100) : 1,
				x: direction !== "vertical" ? (percent / 100) : 1
			};
	
		// We are going to pass this effect to the size effect:
		options.effect = "size";
		options.queue = false;
		options.complete = done;
	
		// Set default origin and restore for show/hide
		if ( mode !== "effect" ) {
			options.origin = origin || [ "middle", "center" ];
			options.restore = true;
		}
	
		options.from = o.from || ( mode === "show" ? {
			height: 0,
			width: 0,
			outerHeight: 0,
			outerWidth: 0
		} : original );
		options.to = {
			height: original.height * factor.y,
			width: original.width * factor.x,
			outerHeight: original.outerHeight * factor.y,
			outerWidth: original.outerWidth * factor.x
		};
	
		// Fade option to support puff
		if ( options.fade ) {
			if ( mode === "show" ) {
				options.from.opacity = 0;
				options.to.opacity = 1;
			}
			if ( mode === "hide" ) {
				options.from.opacity = 1;
				options.to.opacity = 0;
			}
		}
	
		// Animate
		el.effect( options );
	
	};
	
	
	/*!
	 * jQuery UI Effects Puff 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/puff-effect/
	 */
	
	
	var effectPuff = $.effects.effect.puff = function( o, done ) {
		var elem = $( this ),
			mode = $.effects.setMode( elem, o.mode || "hide" ),
			hide = mode === "hide",
			percent = parseInt( o.percent, 10 ) || 150,
			factor = percent / 100,
			original = {
				height: elem.height(),
				width: elem.width(),
				outerHeight: elem.outerHeight(),
				outerWidth: elem.outerWidth()
			};
	
		$.extend( o, {
			effect: "scale",
			queue: false,
			fade: true,
			mode: mode,
			complete: done,
			percent: hide ? percent : 100,
			from: hide ?
				original :
				{
					height: original.height * factor,
					width: original.width * factor,
					outerHeight: original.outerHeight * factor,
					outerWidth: original.outerWidth * factor
				}
		});
	
		elem.effect( o );
	};
	
	
	/*!
	 * jQuery UI Effects Pulsate 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/pulsate-effect/
	 */
	
	
	var effectPulsate = $.effects.effect.pulsate = function( o, done ) {
		var elem = $( this ),
			mode = $.effects.setMode( elem, o.mode || "show" ),
			show = mode === "show",
			hide = mode === "hide",
			showhide = ( show || mode === "hide" ),
	
			// showing or hiding leaves of the "last" animation
			anims = ( ( o.times || 5 ) * 2 ) + ( showhide ? 1 : 0 ),
			duration = o.duration / anims,
			animateTo = 0,
			queue = elem.queue(),
			queuelen = queue.length,
			i;
	
		if ( show || !elem.is(":visible")) {
			elem.css( "opacity", 0 ).show();
			animateTo = 1;
		}
	
		// anims - 1 opacity "toggles"
		for ( i = 1; i < anims; i++ ) {
			elem.animate({
				opacity: animateTo
			}, duration, o.easing );
			animateTo = 1 - animateTo;
		}
	
		elem.animate({
			opacity: animateTo
		}, duration, o.easing);
	
		elem.queue(function() {
			if ( hide ) {
				elem.hide();
			}
			done();
		});
	
		// We just queued up "anims" animations, we need to put them next in the queue
		if ( queuelen > 1 ) {
			queue.splice.apply( queue,
				[ 1, 0 ].concat( queue.splice( queuelen, anims + 1 ) ) );
		}
		elem.dequeue();
	};
	
	
	/*!
	 * jQuery UI Effects Shake 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/shake-effect/
	 */
	
	
	var effectShake = $.effects.effect.shake = function( o, done ) {
	
		var el = $( this ),
			props = [ "position", "top", "bottom", "left", "right", "height", "width" ],
			mode = $.effects.setMode( el, o.mode || "effect" ),
			direction = o.direction || "left",
			distance = o.distance || 20,
			times = o.times || 3,
			anims = times * 2 + 1,
			speed = Math.round( o.duration / anims ),
			ref = (direction === "up" || direction === "down") ? "top" : "left",
			positiveMotion = (direction === "up" || direction === "left"),
			animation = {},
			animation1 = {},
			animation2 = {},
			i,
	
			// we will need to re-assemble the queue to stack our animations in place
			queue = el.queue(),
			queuelen = queue.length;
	
		$.effects.save( el, props );
		el.show();
		$.effects.createWrapper( el );
	
		// Animation
		animation[ ref ] = ( positiveMotion ? "-=" : "+=" ) + distance;
		animation1[ ref ] = ( positiveMotion ? "+=" : "-=" ) + distance * 2;
		animation2[ ref ] = ( positiveMotion ? "-=" : "+=" ) + distance * 2;
	
		// Animate
		el.animate( animation, speed, o.easing );
	
		// Shakes
		for ( i = 1; i < times; i++ ) {
			el.animate( animation1, speed, o.easing ).animate( animation2, speed, o.easing );
		}
		el
			.animate( animation1, speed, o.easing )
			.animate( animation, speed / 2, o.easing )
			.queue(function() {
				if ( mode === "hide" ) {
					el.hide();
				}
				$.effects.restore( el, props );
				$.effects.removeWrapper( el );
				done();
			});
	
		// inject all the animations we just queued to be first in line (after "inprogress")
		if ( queuelen > 1) {
			queue.splice.apply( queue,
				[ 1, 0 ].concat( queue.splice( queuelen, anims + 1 ) ) );
		}
		el.dequeue();
	
	};
	
	
	/*!
	 * jQuery UI Effects Slide 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/slide-effect/
	 */
	
	
	var effectSlide = $.effects.effect.slide = function( o, done ) {
	
		// Create element
		var el = $( this ),
			props = [ "position", "top", "bottom", "left", "right", "width", "height" ],
			mode = $.effects.setMode( el, o.mode || "show" ),
			show = mode === "show",
			direction = o.direction || "left",
			ref = (direction === "up" || direction === "down") ? "top" : "left",
			positiveMotion = (direction === "up" || direction === "left"),
			distance,
			animation = {};
	
		// Adjust
		$.effects.save( el, props );
		el.show();
		distance = o.distance || el[ ref === "top" ? "outerHeight" : "outerWidth" ]( true );
	
		$.effects.createWrapper( el ).css({
			overflow: "hidden"
		});
	
		if ( show ) {
			el.css( ref, positiveMotion ? (isNaN(distance) ? "-" + distance : -distance) : distance );
		}
	
		// Animation
		animation[ ref ] = ( show ?
			( positiveMotion ? "+=" : "-=") :
			( positiveMotion ? "-=" : "+=")) +
			distance;
	
		// Animate
		el.animate( animation, {
			queue: false,
			duration: o.duration,
			easing: o.easing,
			complete: function() {
				if ( mode === "hide" ) {
					el.hide();
				}
				$.effects.restore( el, props );
				$.effects.removeWrapper( el );
				done();
			}
		});
	};
	
	
	/*!
	 * jQuery UI Effects Transfer 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/transfer-effect/
	 */
	
	
	var effectTransfer = $.effects.effect.transfer = function( o, done ) {
		var elem = $( this ),
			target = $( o.to ),
			targetFixed = target.css( "position" ) === "fixed",
			body = $("body"),
			fixTop = targetFixed ? body.scrollTop() : 0,
			fixLeft = targetFixed ? body.scrollLeft() : 0,
			endPosition = target.offset(),
			animation = {
				top: endPosition.top - fixTop,
				left: endPosition.left - fixLeft,
				height: target.innerHeight(),
				width: target.innerWidth()
			},
			startPosition = elem.offset(),
			transfer = $( "<div class='ui-effects-transfer'></div>" )
				.appendTo( document.body )
				.addClass( o.className )
				.css({
					top: startPosition.top - fixTop,
					left: startPosition.left - fixLeft,
					height: elem.innerHeight(),
					width: elem.innerWidth(),
					position: targetFixed ? "fixed" : "absolute"
				})
				.animate( animation, o.duration, o.easing, function() {
					transfer.remove();
					done();
				});
	};
	
	
	/*!
	 * jQuery UI Progressbar 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/progressbar/
	 */
	
	
	var progressbar = $.widget( "ui.progressbar", {
		version: "1.11.4",
		options: {
			max: 100,
			value: 0,
	
			change: null,
			complete: null
		},
	
		min: 0,
	
		_create: function() {
			// Constrain initial value
			this.oldValue = this.options.value = this._constrainedValue();
	
			this.element
				.addClass( "ui-progressbar ui-widget ui-widget-content ui-corner-all" )
				.attr({
					// Only set static values, aria-valuenow and aria-valuemax are
					// set inside _refreshValue()
					role: "progressbar",
					"aria-valuemin": this.min
				});
	
			this.valueDiv = $( "<div class='ui-progressbar-value ui-widget-header ui-corner-left'></div>" )
				.appendTo( this.element );
	
			this._refreshValue();
		},
	
		_destroy: function() {
			this.element
				.removeClass( "ui-progressbar ui-widget ui-widget-content ui-corner-all" )
				.removeAttr( "role" )
				.removeAttr( "aria-valuemin" )
				.removeAttr( "aria-valuemax" )
				.removeAttr( "aria-valuenow" );
	
			this.valueDiv.remove();
		},
	
		value: function( newValue ) {
			if ( newValue === undefined ) {
				return this.options.value;
			}
	
			this.options.value = this._constrainedValue( newValue );
			this._refreshValue();
		},
	
		_constrainedValue: function( newValue ) {
			if ( newValue === undefined ) {
				newValue = this.options.value;
			}
	
			this.indeterminate = newValue === false;
	
			// sanitize value
			if ( typeof newValue !== "number" ) {
				newValue = 0;
			}
	
			return this.indeterminate ? false :
				Math.min( this.options.max, Math.max( this.min, newValue ) );
		},
	
		_setOptions: function( options ) {
			// Ensure "value" option is set after other values (like max)
			var value = options.value;
			delete options.value;
	
			this._super( options );
	
			this.options.value = this._constrainedValue( value );
			this._refreshValue();
		},
	
		_setOption: function( key, value ) {
			if ( key === "max" ) {
				// Don't allow a max less than min
				value = Math.max( this.min, value );
			}
			if ( key === "disabled" ) {
				this.element
					.toggleClass( "ui-state-disabled", !!value )
					.attr( "aria-disabled", value );
			}
			this._super( key, value );
		},
	
		_percentage: function() {
			return this.indeterminate ? 100 : 100 * ( this.options.value - this.min ) / ( this.options.max - this.min );
		},
	
		_refreshValue: function() {
			var value = this.options.value,
				percentage = this._percentage();
	
			this.valueDiv
				.toggle( this.indeterminate || value > this.min )
				.toggleClass( "ui-corner-right", value === this.options.max )
				.width( percentage.toFixed(0) + "%" );
	
			this.element.toggleClass( "ui-progressbar-indeterminate", this.indeterminate );
	
			if ( this.indeterminate ) {
				this.element.removeAttr( "aria-valuenow" );
				if ( !this.overlayDiv ) {
					this.overlayDiv = $( "<div class='ui-progressbar-overlay'></div>" ).appendTo( this.valueDiv );
				}
			} else {
				this.element.attr({
					"aria-valuemax": this.options.max,
					"aria-valuenow": value
				});
				if ( this.overlayDiv ) {
					this.overlayDiv.remove();
					this.overlayDiv = null;
				}
			}
	
			if ( this.oldValue !== value ) {
				this.oldValue = value;
				this._trigger( "change" );
			}
			if ( value === this.options.max ) {
				this._trigger( "complete" );
			}
		}
	});
	
	
	/*!
	 * jQuery UI Selectable 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/selectable/
	 */
	
	
	var selectable = $.widget("ui.selectable", $.ui.mouse, {
		version: "1.11.4",
		options: {
			appendTo: "body",
			autoRefresh: true,
			distance: 0,
			filter: "*",
			tolerance: "touch",
	
			// callbacks
			selected: null,
			selecting: null,
			start: null,
			stop: null,
			unselected: null,
			unselecting: null
		},
		_create: function() {
			var selectees,
				that = this;
	
			this.element.addClass("ui-selectable");
	
			this.dragged = false;
	
			// cache selectee children based on filter
			this.refresh = function() {
				selectees = $(that.options.filter, that.element[0]);
				selectees.addClass("ui-selectee");
				selectees.each(function() {
					var $this = $(this),
						pos = $this.offset();
					$.data(this, "selectable-item", {
						element: this,
						$element: $this,
						left: pos.left,
						top: pos.top,
						right: pos.left + $this.outerWidth(),
						bottom: pos.top + $this.outerHeight(),
						startselected: false,
						selected: $this.hasClass("ui-selected"),
						selecting: $this.hasClass("ui-selecting"),
						unselecting: $this.hasClass("ui-unselecting")
					});
				});
			};
			this.refresh();
	
			this.selectees = selectees.addClass("ui-selectee");
	
			this._mouseInit();
	
			this.helper = $("<div class='ui-selectable-helper'></div>");
		},
	
		_destroy: function() {
			this.selectees
				.removeClass("ui-selectee")
				.removeData("selectable-item");
			this.element
				.removeClass("ui-selectable ui-selectable-disabled");
			this._mouseDestroy();
		},
	
		_mouseStart: function(event) {
			var that = this,
				options = this.options;
	
			this.opos = [ event.pageX, event.pageY ];
	
			if (this.options.disabled) {
				return;
			}
	
			this.selectees = $(options.filter, this.element[0]);
	
			this._trigger("start", event);
	
			$(options.appendTo).append(this.helper);
			// position helper (lasso)
			this.helper.css({
				"left": event.pageX,
				"top": event.pageY,
				"width": 0,
				"height": 0
			});
	
			if (options.autoRefresh) {
				this.refresh();
			}
	
			this.selectees.filter(".ui-selected").each(function() {
				var selectee = $.data(this, "selectable-item");
				selectee.startselected = true;
				if (!event.metaKey && !event.ctrlKey) {
					selectee.$element.removeClass("ui-selected");
					selectee.selected = false;
					selectee.$element.addClass("ui-unselecting");
					selectee.unselecting = true;
					// selectable UNSELECTING callback
					that._trigger("unselecting", event, {
						unselecting: selectee.element
					});
				}
			});
	
			$(event.target).parents().addBack().each(function() {
				var doSelect,
					selectee = $.data(this, "selectable-item");
				if (selectee) {
					doSelect = (!event.metaKey && !event.ctrlKey) || !selectee.$element.hasClass("ui-selected");
					selectee.$element
						.removeClass(doSelect ? "ui-unselecting" : "ui-selected")
						.addClass(doSelect ? "ui-selecting" : "ui-unselecting");
					selectee.unselecting = !doSelect;
					selectee.selecting = doSelect;
					selectee.selected = doSelect;
					// selectable (UN)SELECTING callback
					if (doSelect) {
						that._trigger("selecting", event, {
							selecting: selectee.element
						});
					} else {
						that._trigger("unselecting", event, {
							unselecting: selectee.element
						});
					}
					return false;
				}
			});
	
		},
	
		_mouseDrag: function(event) {
	
			this.dragged = true;
	
			if (this.options.disabled) {
				return;
			}
	
			var tmp,
				that = this,
				options = this.options,
				x1 = this.opos[0],
				y1 = this.opos[1],
				x2 = event.pageX,
				y2 = event.pageY;
	
			if (x1 > x2) { tmp = x2; x2 = x1; x1 = tmp; }
			if (y1 > y2) { tmp = y2; y2 = y1; y1 = tmp; }
			this.helper.css({ left: x1, top: y1, width: x2 - x1, height: y2 - y1 });
	
			this.selectees.each(function() {
				var selectee = $.data(this, "selectable-item"),
					hit = false;
	
				//prevent helper from being selected if appendTo: selectable
				if (!selectee || selectee.element === that.element[0]) {
					return;
				}
	
				if (options.tolerance === "touch") {
					hit = ( !(selectee.left > x2 || selectee.right < x1 || selectee.top > y2 || selectee.bottom < y1) );
				} else if (options.tolerance === "fit") {
					hit = (selectee.left > x1 && selectee.right < x2 && selectee.top > y1 && selectee.bottom < y2);
				}
	
				if (hit) {
					// SELECT
					if (selectee.selected) {
						selectee.$element.removeClass("ui-selected");
						selectee.selected = false;
					}
					if (selectee.unselecting) {
						selectee.$element.removeClass("ui-unselecting");
						selectee.unselecting = false;
					}
					if (!selectee.selecting) {
						selectee.$element.addClass("ui-selecting");
						selectee.selecting = true;
						// selectable SELECTING callback
						that._trigger("selecting", event, {
							selecting: selectee.element
						});
					}
				} else {
					// UNSELECT
					if (selectee.selecting) {
						if ((event.metaKey || event.ctrlKey) && selectee.startselected) {
							selectee.$element.removeClass("ui-selecting");
							selectee.selecting = false;
							selectee.$element.addClass("ui-selected");
							selectee.selected = true;
						} else {
							selectee.$element.removeClass("ui-selecting");
							selectee.selecting = false;
							if (selectee.startselected) {
								selectee.$element.addClass("ui-unselecting");
								selectee.unselecting = true;
							}
							// selectable UNSELECTING callback
							that._trigger("unselecting", event, {
								unselecting: selectee.element
							});
						}
					}
					if (selectee.selected) {
						if (!event.metaKey && !event.ctrlKey && !selectee.startselected) {
							selectee.$element.removeClass("ui-selected");
							selectee.selected = false;
	
							selectee.$element.addClass("ui-unselecting");
							selectee.unselecting = true;
							// selectable UNSELECTING callback
							that._trigger("unselecting", event, {
								unselecting: selectee.element
							});
						}
					}
				}
			});
	
			return false;
		},
	
		_mouseStop: function(event) {
			var that = this;
	
			this.dragged = false;
	
			$(".ui-unselecting", this.element[0]).each(function() {
				var selectee = $.data(this, "selectable-item");
				selectee.$element.removeClass("ui-unselecting");
				selectee.unselecting = false;
				selectee.startselected = false;
				that._trigger("unselected", event, {
					unselected: selectee.element
				});
			});
			$(".ui-selecting", this.element[0]).each(function() {
				var selectee = $.data(this, "selectable-item");
				selectee.$element.removeClass("ui-selecting").addClass("ui-selected");
				selectee.selecting = false;
				selectee.selected = true;
				selectee.startselected = true;
				that._trigger("selected", event, {
					selected: selectee.element
				});
			});
			this._trigger("stop", event);
	
			this.helper.remove();
	
			return false;
		}
	
	});
	
	
	/*!
	 * jQuery UI Selectmenu 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/selectmenu
	 */
	
	
	var selectmenu = $.widget( "ui.selectmenu", {
		version: "1.11.4",
		defaultElement: "<select>",
		options: {
			appendTo: null,
			disabled: null,
			icons: {
				button: "ui-icon-triangle-1-s"
			},
			position: {
				my: "left top",
				at: "left bottom",
				collision: "none"
			},
			width: null,
	
			// callbacks
			change: null,
			close: null,
			focus: null,
			open: null,
			select: null
		},
	
		_create: function() {
			var selectmenuId = this.element.uniqueId().attr( "id" );
			this.ids = {
				element: selectmenuId,
				button: selectmenuId + "-button",
				menu: selectmenuId + "-menu"
			};
	
			this._drawButton();
			this._drawMenu();
	
			if ( this.options.disabled ) {
				this.disable();
			}
		},
	
		_drawButton: function() {
			var that = this;
	
			// Associate existing label with the new button
			this.label = $( "label[for='" + this.ids.element + "']" ).attr( "for", this.ids.button );
			this._on( this.label, {
				click: function( event ) {
					this.button.focus();
					event.preventDefault();
				}
			});
	
			// Hide original select element
			this.element.hide();
	
			// Create button
			this.button = $( "<span>", {
				"class": "ui-selectmenu-button ui-widget ui-state-default ui-corner-all",
				tabindex: this.options.disabled ? -1 : 0,
				id: this.ids.button,
				role: "combobox",
				"aria-expanded": "false",
				"aria-autocomplete": "list",
				"aria-owns": this.ids.menu,
				"aria-haspopup": "true"
			})
				.insertAfter( this.element );
	
			$( "<span>", {
				"class": "ui-icon " + this.options.icons.button
			})
				.prependTo( this.button );
	
			this.buttonText = $( "<span>", {
				"class": "ui-selectmenu-text"
			})
				.appendTo( this.button );
	
			this._setText( this.buttonText, this.element.find( "option:selected" ).text() );
			this._resizeButton();
	
			this._on( this.button, this._buttonEvents );
			this.button.one( "focusin", function() {
	
				// Delay rendering the menu items until the button receives focus.
				// The menu may have already been rendered via a programmatic open.
				if ( !that.menuItems ) {
					that._refreshMenu();
				}
			});
			this._hoverable( this.button );
			this._focusable( this.button );
		},
	
		_drawMenu: function() {
			var that = this;
	
			// Create menu
			this.menu = $( "<ul>", {
				"aria-hidden": "true",
				"aria-labelledby": this.ids.button,
				id: this.ids.menu
			});
	
			// Wrap menu
			this.menuWrap = $( "<div>", {
				"class": "ui-selectmenu-menu ui-front"
			})
				.append( this.menu )
				.appendTo( this._appendTo() );
	
			// Initialize menu widget
			this.menuInstance = this.menu
				.menu({
					role: "listbox",
					select: function( event, ui ) {
						event.preventDefault();
	
						// support: IE8
						// If the item was selected via a click, the text selection
						// will be destroyed in IE
						that._setSelection();
	
						that._select( ui.item.data( "ui-selectmenu-item" ), event );
					},
					focus: function( event, ui ) {
						var item = ui.item.data( "ui-selectmenu-item" );
	
						// Prevent inital focus from firing and check if its a newly focused item
						if ( that.focusIndex != null && item.index !== that.focusIndex ) {
							that._trigger( "focus", event, { item: item } );
							if ( !that.isOpen ) {
								that._select( item, event );
							}
						}
						that.focusIndex = item.index;
	
						that.button.attr( "aria-activedescendant",
							that.menuItems.eq( item.index ).attr( "id" ) );
					}
				})
				.menu( "instance" );
	
			// Adjust menu styles to dropdown
			this.menu
				.addClass( "ui-corner-bottom" )
				.removeClass( "ui-corner-all" );
	
			// Don't close the menu on mouseleave
			this.menuInstance._off( this.menu, "mouseleave" );
	
			// Cancel the menu's collapseAll on document click
			this.menuInstance._closeOnDocumentClick = function() {
				return false;
			};
	
			// Selects often contain empty items, but never contain dividers
			this.menuInstance._isDivider = function() {
				return false;
			};
		},
	
		refresh: function() {
			this._refreshMenu();
			this._setText( this.buttonText, this._getSelectedItem().text() );
			if ( !this.options.width ) {
				this._resizeButton();
			}
		},
	
		_refreshMenu: function() {
			this.menu.empty();
	
			var item,
				options = this.element.find( "option" );
	
			if ( !options.length ) {
				return;
			}
	
			this._parseOptions( options );
			this._renderMenu( this.menu, this.items );
	
			this.menuInstance.refresh();
			this.menuItems = this.menu.find( "li" ).not( ".ui-selectmenu-optgroup" );
	
			item = this._getSelectedItem();
	
			// Update the menu to have the correct item focused
			this.menuInstance.focus( null, item );
			this._setAria( item.data( "ui-selectmenu-item" ) );
	
			// Set disabled state
			this._setOption( "disabled", this.element.prop( "disabled" ) );
		},
	
		open: function( event ) {
			if ( this.options.disabled ) {
				return;
			}
	
			// If this is the first time the menu is being opened, render the items
			if ( !this.menuItems ) {
				this._refreshMenu();
			} else {
	
				// Menu clears focus on close, reset focus to selected item
				this.menu.find( ".ui-state-focus" ).removeClass( "ui-state-focus" );
				this.menuInstance.focus( null, this._getSelectedItem() );
			}
	
			this.isOpen = true;
			this._toggleAttr();
			this._resizeMenu();
			this._position();
	
			this._on( this.document, this._documentClick );
	
			this._trigger( "open", event );
		},
	
		_position: function() {
			this.menuWrap.position( $.extend( { of: this.button }, this.options.position ) );
		},
	
		close: function( event ) {
			if ( !this.isOpen ) {
				return;
			}
	
			this.isOpen = false;
			this._toggleAttr();
	
			this.range = null;
			this._off( this.document );
	
			this._trigger( "close", event );
		},
	
		widget: function() {
			return this.button;
		},
	
		menuWidget: function() {
			return this.menu;
		},
	
		_renderMenu: function( ul, items ) {
			var that = this,
				currentOptgroup = "";
	
			$.each( items, function( index, item ) {
				if ( item.optgroup !== currentOptgroup ) {
					$( "<li>", {
						"class": "ui-selectmenu-optgroup ui-menu-divider" +
							( item.element.parent( "optgroup" ).prop( "disabled" ) ?
								" ui-state-disabled" :
								"" ),
						text: item.optgroup
					})
						.appendTo( ul );
	
					currentOptgroup = item.optgroup;
				}
	
				that._renderItemData( ul, item );
			});
		},
	
		_renderItemData: function( ul, item ) {
			return this._renderItem( ul, item ).data( "ui-selectmenu-item", item );
		},
	
		_renderItem: function( ul, item ) {
			var li = $( "<li>" );
	
			if ( item.disabled ) {
				li.addClass( "ui-state-disabled" );
			}
			this._setText( li, item.label );
	
			return li.appendTo( ul );
		},
	
		_setText: function( element, value ) {
			if ( value ) {
				element.text( value );
			} else {
				element.html( "&#160;" );
			}
		},
	
		_move: function( direction, event ) {
			var item, next,
				filter = ".ui-menu-item";
	
			if ( this.isOpen ) {
				item = this.menuItems.eq( this.focusIndex );
			} else {
				item = this.menuItems.eq( this.element[ 0 ].selectedIndex );
				filter += ":not(.ui-state-disabled)";
			}
	
			if ( direction === "first" || direction === "last" ) {
				next = item[ direction === "first" ? "prevAll" : "nextAll" ]( filter ).eq( -1 );
			} else {
				next = item[ direction + "All" ]( filter ).eq( 0 );
			}
	
			if ( next.length ) {
				this.menuInstance.focus( event, next );
			}
		},
	
		_getSelectedItem: function() {
			return this.menuItems.eq( this.element[ 0 ].selectedIndex );
		},
	
		_toggle: function( event ) {
			this[ this.isOpen ? "close" : "open" ]( event );
		},
	
		_setSelection: function() {
			var selection;
	
			if ( !this.range ) {
				return;
			}
	
			if ( window.getSelection ) {
				selection = window.getSelection();
				selection.removeAllRanges();
				selection.addRange( this.range );
	
			// support: IE8
			} else {
				this.range.select();
			}
	
			// support: IE
			// Setting the text selection kills the button focus in IE, but
			// restoring the focus doesn't kill the selection.
			this.button.focus();
		},
	
		_documentClick: {
			mousedown: function( event ) {
				if ( !this.isOpen ) {
					return;
				}
	
				if ( !$( event.target ).closest( ".ui-selectmenu-menu, #" + this.ids.button ).length ) {
					this.close( event );
				}
			}
		},
	
		_buttonEvents: {
	
			// Prevent text selection from being reset when interacting with the selectmenu (#10144)
			mousedown: function() {
				var selection;
	
				if ( window.getSelection ) {
					selection = window.getSelection();
					if ( selection.rangeCount ) {
						this.range = selection.getRangeAt( 0 );
					}
	
				// support: IE8
				} else {
					this.range = document.selection.createRange();
				}
			},
	
			click: function( event ) {
				this._setSelection();
				this._toggle( event );
			},
	
			keydown: function( event ) {
				var preventDefault = true;
				switch ( event.keyCode ) {
					case $.ui.keyCode.TAB:
					case $.ui.keyCode.ESCAPE:
						this.close( event );
						preventDefault = false;
						break;
					case $.ui.keyCode.ENTER:
						if ( this.isOpen ) {
							this._selectFocusedItem( event );
						}
						break;
					case $.ui.keyCode.UP:
						if ( event.altKey ) {
							this._toggle( event );
						} else {
							this._move( "prev", event );
						}
						break;
					case $.ui.keyCode.DOWN:
						if ( event.altKey ) {
							this._toggle( event );
						} else {
							this._move( "next", event );
						}
						break;
					case $.ui.keyCode.SPACE:
						if ( this.isOpen ) {
							this._selectFocusedItem( event );
						} else {
							this._toggle( event );
						}
						break;
					case $.ui.keyCode.LEFT:
						this._move( "prev", event );
						break;
					case $.ui.keyCode.RIGHT:
						this._move( "next", event );
						break;
					case $.ui.keyCode.HOME:
					case $.ui.keyCode.PAGE_UP:
						this._move( "first", event );
						break;
					case $.ui.keyCode.END:
					case $.ui.keyCode.PAGE_DOWN:
						this._move( "last", event );
						break;
					default:
						this.menu.trigger( event );
						preventDefault = false;
				}
	
				if ( preventDefault ) {
					event.preventDefault();
				}
			}
		},
	
		_selectFocusedItem: function( event ) {
			var item = this.menuItems.eq( this.focusIndex );
			if ( !item.hasClass( "ui-state-disabled" ) ) {
				this._select( item.data( "ui-selectmenu-item" ), event );
			}
		},
	
		_select: function( item, event ) {
			var oldIndex = this.element[ 0 ].selectedIndex;
	
			// Change native select element
			this.element[ 0 ].selectedIndex = item.index;
			this._setText( this.buttonText, item.label );
			this._setAria( item );
			this._trigger( "select", event, { item: item } );
	
			if ( item.index !== oldIndex ) {
				this._trigger( "change", event, { item: item } );
			}
	
			this.close( event );
		},
	
		_setAria: function( item ) {
			var id = this.menuItems.eq( item.index ).attr( "id" );
	
			this.button.attr({
				"aria-labelledby": id,
				"aria-activedescendant": id
			});
			this.menu.attr( "aria-activedescendant", id );
		},
	
		_setOption: function( key, value ) {
			if ( key === "icons" ) {
				this.button.find( "span.ui-icon" )
					.removeClass( this.options.icons.button )
					.addClass( value.button );
			}
	
			this._super( key, value );
	
			if ( key === "appendTo" ) {
				this.menuWrap.appendTo( this._appendTo() );
			}
	
			if ( key === "disabled" ) {
				this.menuInstance.option( "disabled", value );
				this.button
					.toggleClass( "ui-state-disabled", value )
					.attr( "aria-disabled", value );
	
				this.element.prop( "disabled", value );
				if ( value ) {
					this.button.attr( "tabindex", -1 );
					this.close();
				} else {
					this.button.attr( "tabindex", 0 );
				}
			}
	
			if ( key === "width" ) {
				this._resizeButton();
			}
		},
	
		_appendTo: function() {
			var element = this.options.appendTo;
	
			if ( element ) {
				element = element.jquery || element.nodeType ?
					$( element ) :
					this.document.find( element ).eq( 0 );
			}
	
			if ( !element || !element[ 0 ] ) {
				element = this.element.closest( ".ui-front" );
			}
	
			if ( !element.length ) {
				element = this.document[ 0 ].body;
			}
	
			return element;
		},
	
		_toggleAttr: function() {
			this.button
				.toggleClass( "ui-corner-top", this.isOpen )
				.toggleClass( "ui-corner-all", !this.isOpen )
				.attr( "aria-expanded", this.isOpen );
			this.menuWrap.toggleClass( "ui-selectmenu-open", this.isOpen );
			this.menu.attr( "aria-hidden", !this.isOpen );
		},
	
		_resizeButton: function() {
			var width = this.options.width;
	
			if ( !width ) {
				width = this.element.show().outerWidth();
				this.element.hide();
			}
	
			this.button.outerWidth( width );
		},
	
		_resizeMenu: function() {
			this.menu.outerWidth( Math.max(
				this.button.outerWidth(),
	
				// support: IE10
				// IE10 wraps long text (possibly a rounding bug)
				// so we add 1px to avoid the wrapping
				this.menu.width( "" ).outerWidth() + 1
			) );
		},
	
		_getCreateOptions: function() {
			return { disabled: this.element.prop( "disabled" ) };
		},
	
		_parseOptions: function( options ) {
			var data = [];
			options.each(function( index, item ) {
				var option = $( item ),
					optgroup = option.parent( "optgroup" );
				data.push({
					element: option,
					index: index,
					value: option.val(),
					label: option.text(),
					optgroup: optgroup.attr( "label" ) || "",
					disabled: optgroup.prop( "disabled" ) || option.prop( "disabled" )
				});
			});
			this.items = data;
		},
	
		_destroy: function() {
			this.menuWrap.remove();
			this.button.remove();
			this.element.show();
			this.element.removeUniqueId();
			this.label.attr( "for", this.ids.element );
		}
	});
	
	
	/*!
	 * jQuery UI Slider 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/slider/
	 */
	
	
	var slider = $.widget( "ui.slider", $.ui.mouse, {
		version: "1.11.4",
		widgetEventPrefix: "slide",
	
		options: {
			animate: false,
			distance: 0,
			max: 100,
			min: 0,
			orientation: "horizontal",
			range: false,
			step: 1,
			value: 0,
			values: null,
	
			// callbacks
			change: null,
			slide: null,
			start: null,
			stop: null
		},
	
		// number of pages in a slider
		// (how many times can you page up/down to go through the whole range)
		numPages: 5,
	
		_create: function() {
			this._keySliding = false;
			this._mouseSliding = false;
			this._animateOff = true;
			this._handleIndex = null;
			this._detectOrientation();
			this._mouseInit();
			this._calculateNewMax();
	
			this.element
				.addClass( "ui-slider" +
					" ui-slider-" + this.orientation +
					" ui-widget" +
					" ui-widget-content" +
					" ui-corner-all");
	
			this._refresh();
			this._setOption( "disabled", this.options.disabled );
	
			this._animateOff = false;
		},
	
		_refresh: function() {
			this._createRange();
			this._createHandles();
			this._setupEvents();
			this._refreshValue();
		},
	
		_createHandles: function() {
			var i, handleCount,
				options = this.options,
				existingHandles = this.element.find( ".ui-slider-handle" ).addClass( "ui-state-default ui-corner-all" ),
				handle = "<span class='ui-slider-handle ui-state-default ui-corner-all' tabindex='0'></span>",
				handles = [];
	
			handleCount = ( options.values && options.values.length ) || 1;
	
			if ( existingHandles.length > handleCount ) {
				existingHandles.slice( handleCount ).remove();
				existingHandles = existingHandles.slice( 0, handleCount );
			}
	
			for ( i = existingHandles.length; i < handleCount; i++ ) {
				handles.push( handle );
			}
	
			this.handles = existingHandles.add( $( handles.join( "" ) ).appendTo( this.element ) );
	
			this.handle = this.handles.eq( 0 );
	
			this.handles.each(function( i ) {
				$( this ).data( "ui-slider-handle-index", i );
			});
		},
	
		_createRange: function() {
			var options = this.options,
				classes = "";
	
			if ( options.range ) {
				if ( options.range === true ) {
					if ( !options.values ) {
						options.values = [ this._valueMin(), this._valueMin() ];
					} else if ( options.values.length && options.values.length !== 2 ) {
						options.values = [ options.values[0], options.values[0] ];
					} else if ( $.isArray( options.values ) ) {
						options.values = options.values.slice(0);
					}
				}
	
				if ( !this.range || !this.range.length ) {
					this.range = $( "<div></div>" )
						.appendTo( this.element );
	
					classes = "ui-slider-range" +
					// note: this isn't the most fittingly semantic framework class for this element,
					// but worked best visually with a variety of themes
					" ui-widget-header ui-corner-all";
				} else {
					this.range.removeClass( "ui-slider-range-min ui-slider-range-max" )
						// Handle range switching from true to min/max
						.css({
							"left": "",
							"bottom": ""
						});
				}
	
				this.range.addClass( classes +
					( ( options.range === "min" || options.range === "max" ) ? " ui-slider-range-" + options.range : "" ) );
			} else {
				if ( this.range ) {
					this.range.remove();
				}
				this.range = null;
			}
		},
	
		_setupEvents: function() {
			this._off( this.handles );
			this._on( this.handles, this._handleEvents );
			this._hoverable( this.handles );
			this._focusable( this.handles );
		},
	
		_destroy: function() {
			this.handles.remove();
			if ( this.range ) {
				this.range.remove();
			}
	
			this.element
				.removeClass( "ui-slider" +
					" ui-slider-horizontal" +
					" ui-slider-vertical" +
					" ui-widget" +
					" ui-widget-content" +
					" ui-corner-all" );
	
			this._mouseDestroy();
		},
	
		_mouseCapture: function( event ) {
			var position, normValue, distance, closestHandle, index, allowed, offset, mouseOverHandle,
				that = this,
				o = this.options;
	
			if ( o.disabled ) {
				return false;
			}
	
			this.elementSize = {
				width: this.element.outerWidth(),
				height: this.element.outerHeight()
			};
			this.elementOffset = this.element.offset();
	
			position = { x: event.pageX, y: event.pageY };
			normValue = this._normValueFromMouse( position );
			distance = this._valueMax() - this._valueMin() + 1;
			this.handles.each(function( i ) {
				var thisDistance = Math.abs( normValue - that.values(i) );
				if (( distance > thisDistance ) ||
					( distance === thisDistance &&
						(i === that._lastChangedValue || that.values(i) === o.min ))) {
					distance = thisDistance;
					closestHandle = $( this );
					index = i;
				}
			});
	
			allowed = this._start( event, index );
			if ( allowed === false ) {
				return false;
			}
			this._mouseSliding = true;
	
			this._handleIndex = index;
	
			closestHandle
				.addClass( "ui-state-active" )
				.focus();
	
			offset = closestHandle.offset();
			mouseOverHandle = !$( event.target ).parents().addBack().is( ".ui-slider-handle" );
			this._clickOffset = mouseOverHandle ? { left: 0, top: 0 } : {
				left: event.pageX - offset.left - ( closestHandle.width() / 2 ),
				top: event.pageY - offset.top -
					( closestHandle.height() / 2 ) -
					( parseInt( closestHandle.css("borderTopWidth"), 10 ) || 0 ) -
					( parseInt( closestHandle.css("borderBottomWidth"), 10 ) || 0) +
					( parseInt( closestHandle.css("marginTop"), 10 ) || 0)
			};
	
			if ( !this.handles.hasClass( "ui-state-hover" ) ) {
				this._slide( event, index, normValue );
			}
			this._animateOff = true;
			return true;
		},
	
		_mouseStart: function() {
			return true;
		},
	
		_mouseDrag: function( event ) {
			var position = { x: event.pageX, y: event.pageY },
				normValue = this._normValueFromMouse( position );
	
			this._slide( event, this._handleIndex, normValue );
	
			return false;
		},
	
		_mouseStop: function( event ) {
			this.handles.removeClass( "ui-state-active" );
			this._mouseSliding = false;
	
			this._stop( event, this._handleIndex );
			this._change( event, this._handleIndex );
	
			this._handleIndex = null;
			this._clickOffset = null;
			this._animateOff = false;
	
			return false;
		},
	
		_detectOrientation: function() {
			this.orientation = ( this.options.orientation === "vertical" ) ? "vertical" : "horizontal";
		},
	
		_normValueFromMouse: function( position ) {
			var pixelTotal,
				pixelMouse,
				percentMouse,
				valueTotal,
				valueMouse;
	
			if ( this.orientation === "horizontal" ) {
				pixelTotal = this.elementSize.width;
				pixelMouse = position.x - this.elementOffset.left - ( this._clickOffset ? this._clickOffset.left : 0 );
			} else {
				pixelTotal = this.elementSize.height;
				pixelMouse = position.y - this.elementOffset.top - ( this._clickOffset ? this._clickOffset.top : 0 );
			}
	
			percentMouse = ( pixelMouse / pixelTotal );
			if ( percentMouse > 1 ) {
				percentMouse = 1;
			}
			if ( percentMouse < 0 ) {
				percentMouse = 0;
			}
			if ( this.orientation === "vertical" ) {
				percentMouse = 1 - percentMouse;
			}
	
			valueTotal = this._valueMax() - this._valueMin();
			valueMouse = this._valueMin() + percentMouse * valueTotal;
	
			return this._trimAlignValue( valueMouse );
		},
	
		_start: function( event, index ) {
			var uiHash = {
				handle: this.handles[ index ],
				value: this.value()
			};
			if ( this.options.values && this.options.values.length ) {
				uiHash.value = this.values( index );
				uiHash.values = this.values();
			}
			return this._trigger( "start", event, uiHash );
		},
	
		_slide: function( event, index, newVal ) {
			var otherVal,
				newValues,
				allowed;
	
			if ( this.options.values && this.options.values.length ) {
				otherVal = this.values( index ? 0 : 1 );
	
				if ( ( this.options.values.length === 2 && this.options.range === true ) &&
						( ( index === 0 && newVal > otherVal) || ( index === 1 && newVal < otherVal ) )
					) {
					newVal = otherVal;
				}
	
				if ( newVal !== this.values( index ) ) {
					newValues = this.values();
					newValues[ index ] = newVal;
					// A slide can be canceled by returning false from the slide callback
					allowed = this._trigger( "slide", event, {
						handle: this.handles[ index ],
						value: newVal,
						values: newValues
					} );
					otherVal = this.values( index ? 0 : 1 );
					if ( allowed !== false ) {
						this.values( index, newVal );
					}
				}
			} else {
				if ( newVal !== this.value() ) {
					// A slide can be canceled by returning false from the slide callback
					allowed = this._trigger( "slide", event, {
						handle: this.handles[ index ],
						value: newVal
					} );
					if ( allowed !== false ) {
						this.value( newVal );
					}
				}
			}
		},
	
		_stop: function( event, index ) {
			var uiHash = {
				handle: this.handles[ index ],
				value: this.value()
			};
			if ( this.options.values && this.options.values.length ) {
				uiHash.value = this.values( index );
				uiHash.values = this.values();
			}
	
			this._trigger( "stop", event, uiHash );
		},
	
		_change: function( event, index ) {
			if ( !this._keySliding && !this._mouseSliding ) {
				var uiHash = {
					handle: this.handles[ index ],
					value: this.value()
				};
				if ( this.options.values && this.options.values.length ) {
					uiHash.value = this.values( index );
					uiHash.values = this.values();
				}
	
				//store the last changed value index for reference when handles overlap
				this._lastChangedValue = index;
	
				this._trigger( "change", event, uiHash );
			}
		},
	
		value: function( newValue ) {
			if ( arguments.length ) {
				this.options.value = this._trimAlignValue( newValue );
				this._refreshValue();
				this._change( null, 0 );
				return;
			}
	
			return this._value();
		},
	
		values: function( index, newValue ) {
			var vals,
				newValues,
				i;
	
			if ( arguments.length > 1 ) {
				this.options.values[ index ] = this._trimAlignValue( newValue );
				this._refreshValue();
				this._change( null, index );
				return;
			}
	
			if ( arguments.length ) {
				if ( $.isArray( arguments[ 0 ] ) ) {
					vals = this.options.values;
					newValues = arguments[ 0 ];
					for ( i = 0; i < vals.length; i += 1 ) {
						vals[ i ] = this._trimAlignValue( newValues[ i ] );
						this._change( null, i );
					}
					this._refreshValue();
				} else {
					if ( this.options.values && this.options.values.length ) {
						return this._values( index );
					} else {
						return this.value();
					}
				}
			} else {
				return this._values();
			}
		},
	
		_setOption: function( key, value ) {
			var i,
				valsLength = 0;
	
			if ( key === "range" && this.options.range === true ) {
				if ( value === "min" ) {
					this.options.value = this._values( 0 );
					this.options.values = null;
				} else if ( value === "max" ) {
					this.options.value = this._values( this.options.values.length - 1 );
					this.options.values = null;
				}
			}
	
			if ( $.isArray( this.options.values ) ) {
				valsLength = this.options.values.length;
			}
	
			if ( key === "disabled" ) {
				this.element.toggleClass( "ui-state-disabled", !!value );
			}
	
			this._super( key, value );
	
			switch ( key ) {
				case "orientation":
					this._detectOrientation();
					this.element
						.removeClass( "ui-slider-horizontal ui-slider-vertical" )
						.addClass( "ui-slider-" + this.orientation );
					this._refreshValue();
	
					// Reset positioning from previous orientation
					this.handles.css( value === "horizontal" ? "bottom" : "left", "" );
					break;
				case "value":
					this._animateOff = true;
					this._refreshValue();
					this._change( null, 0 );
					this._animateOff = false;
					break;
				case "values":
					this._animateOff = true;
					this._refreshValue();
					for ( i = 0; i < valsLength; i += 1 ) {
						this._change( null, i );
					}
					this._animateOff = false;
					break;
				case "step":
				case "min":
				case "max":
					this._animateOff = true;
					this._calculateNewMax();
					this._refreshValue();
					this._animateOff = false;
					break;
				case "range":
					this._animateOff = true;
					this._refresh();
					this._animateOff = false;
					break;
			}
		},
	
		//internal value getter
		// _value() returns value trimmed by min and max, aligned by step
		_value: function() {
			var val = this.options.value;
			val = this._trimAlignValue( val );
	
			return val;
		},
	
		//internal values getter
		// _values() returns array of values trimmed by min and max, aligned by step
		// _values( index ) returns single value trimmed by min and max, aligned by step
		_values: function( index ) {
			var val,
				vals,
				i;
	
			if ( arguments.length ) {
				val = this.options.values[ index ];
				val = this._trimAlignValue( val );
	
				return val;
			} else if ( this.options.values && this.options.values.length ) {
				// .slice() creates a copy of the array
				// this copy gets trimmed by min and max and then returned
				vals = this.options.values.slice();
				for ( i = 0; i < vals.length; i += 1) {
					vals[ i ] = this._trimAlignValue( vals[ i ] );
				}
	
				return vals;
			} else {
				return [];
			}
		},
	
		// returns the step-aligned value that val is closest to, between (inclusive) min and max
		_trimAlignValue: function( val ) {
			if ( val <= this._valueMin() ) {
				return this._valueMin();
			}
			if ( val >= this._valueMax() ) {
				return this._valueMax();
			}
			var step = ( this.options.step > 0 ) ? this.options.step : 1,
				valModStep = (val - this._valueMin()) % step,
				alignValue = val - valModStep;
	
			if ( Math.abs(valModStep) * 2 >= step ) {
				alignValue += ( valModStep > 0 ) ? step : ( -step );
			}
	
			// Since JavaScript has problems with large floats, round
			// the final value to 5 digits after the decimal point (see #4124)
			return parseFloat( alignValue.toFixed(5) );
		},
	
		_calculateNewMax: function() {
			var max = this.options.max,
				min = this._valueMin(),
				step = this.options.step,
				aboveMin = Math.floor( ( +( max - min ).toFixed( this._precision() ) ) / step ) * step;
			max = aboveMin + min;
			this.max = parseFloat( max.toFixed( this._precision() ) );
		},
	
		_precision: function() {
			var precision = this._precisionOf( this.options.step );
			if ( this.options.min !== null ) {
				precision = Math.max( precision, this._precisionOf( this.options.min ) );
			}
			return precision;
		},
	
		_precisionOf: function( num ) {
			var str = num.toString(),
				decimal = str.indexOf( "." );
			return decimal === -1 ? 0 : str.length - decimal - 1;
		},
	
		_valueMin: function() {
			return this.options.min;
		},
	
		_valueMax: function() {
			return this.max;
		},
	
		_refreshValue: function() {
			var lastValPercent, valPercent, value, valueMin, valueMax,
				oRange = this.options.range,
				o = this.options,
				that = this,
				animate = ( !this._animateOff ) ? o.animate : false,
				_set = {};
	
			if ( this.options.values && this.options.values.length ) {
				this.handles.each(function( i ) {
					valPercent = ( that.values(i) - that._valueMin() ) / ( that._valueMax() - that._valueMin() ) * 100;
					_set[ that.orientation === "horizontal" ? "left" : "bottom" ] = valPercent + "%";
					$( this ).stop( 1, 1 )[ animate ? "animate" : "css" ]( _set, o.animate );
					if ( that.options.range === true ) {
						if ( that.orientation === "horizontal" ) {
							if ( i === 0 ) {
								that.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( { left: valPercent + "%" }, o.animate );
							}
							if ( i === 1 ) {
								that.range[ animate ? "animate" : "css" ]( { width: ( valPercent - lastValPercent ) + "%" }, { queue: false, duration: o.animate } );
							}
						} else {
							if ( i === 0 ) {
								that.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( { bottom: ( valPercent ) + "%" }, o.animate );
							}
							if ( i === 1 ) {
								that.range[ animate ? "animate" : "css" ]( { height: ( valPercent - lastValPercent ) + "%" }, { queue: false, duration: o.animate } );
							}
						}
					}
					lastValPercent = valPercent;
				});
			} else {
				value = this.value();
				valueMin = this._valueMin();
				valueMax = this._valueMax();
				valPercent = ( valueMax !== valueMin ) ?
						( value - valueMin ) / ( valueMax - valueMin ) * 100 :
						0;
				_set[ this.orientation === "horizontal" ? "left" : "bottom" ] = valPercent + "%";
				this.handle.stop( 1, 1 )[ animate ? "animate" : "css" ]( _set, o.animate );
	
				if ( oRange === "min" && this.orientation === "horizontal" ) {
					this.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( { width: valPercent + "%" }, o.animate );
				}
				if ( oRange === "max" && this.orientation === "horizontal" ) {
					this.range[ animate ? "animate" : "css" ]( { width: ( 100 - valPercent ) + "%" }, { queue: false, duration: o.animate } );
				}
				if ( oRange === "min" && this.orientation === "vertical" ) {
					this.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( { height: valPercent + "%" }, o.animate );
				}
				if ( oRange === "max" && this.orientation === "vertical" ) {
					this.range[ animate ? "animate" : "css" ]( { height: ( 100 - valPercent ) + "%" }, { queue: false, duration: o.animate } );
				}
			}
		},
	
		_handleEvents: {
			keydown: function( event ) {
				var allowed, curVal, newVal, step,
					index = $( event.target ).data( "ui-slider-handle-index" );
	
				switch ( event.keyCode ) {
					case $.ui.keyCode.HOME:
					case $.ui.keyCode.END:
					case $.ui.keyCode.PAGE_UP:
					case $.ui.keyCode.PAGE_DOWN:
					case $.ui.keyCode.UP:
					case $.ui.keyCode.RIGHT:
					case $.ui.keyCode.DOWN:
					case $.ui.keyCode.LEFT:
						event.preventDefault();
						if ( !this._keySliding ) {
							this._keySliding = true;
							$( event.target ).addClass( "ui-state-active" );
							allowed = this._start( event, index );
							if ( allowed === false ) {
								return;
							}
						}
						break;
				}
	
				step = this.options.step;
				if ( this.options.values && this.options.values.length ) {
					curVal = newVal = this.values( index );
				} else {
					curVal = newVal = this.value();
				}
	
				switch ( event.keyCode ) {
					case $.ui.keyCode.HOME:
						newVal = this._valueMin();
						break;
					case $.ui.keyCode.END:
						newVal = this._valueMax();
						break;
					case $.ui.keyCode.PAGE_UP:
						newVal = this._trimAlignValue(
							curVal + ( ( this._valueMax() - this._valueMin() ) / this.numPages )
						);
						break;
					case $.ui.keyCode.PAGE_DOWN:
						newVal = this._trimAlignValue(
							curVal - ( (this._valueMax() - this._valueMin()) / this.numPages ) );
						break;
					case $.ui.keyCode.UP:
					case $.ui.keyCode.RIGHT:
						if ( curVal === this._valueMax() ) {
							return;
						}
						newVal = this._trimAlignValue( curVal + step );
						break;
					case $.ui.keyCode.DOWN:
					case $.ui.keyCode.LEFT:
						if ( curVal === this._valueMin() ) {
							return;
						}
						newVal = this._trimAlignValue( curVal - step );
						break;
				}
	
				this._slide( event, index, newVal );
			},
			keyup: function( event ) {
				var index = $( event.target ).data( "ui-slider-handle-index" );
	
				if ( this._keySliding ) {
					this._keySliding = false;
					this._stop( event, index );
					this._change( event, index );
					$( event.target ).removeClass( "ui-state-active" );
				}
			}
		}
	});
	
	
	/*!
	 * jQuery UI Sortable 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/sortable/
	 */
	
	
	var sortable = $.widget("ui.sortable", $.ui.mouse, {
		version: "1.11.4",
		widgetEventPrefix: "sort",
		ready: false,
		options: {
			appendTo: "parent",
			axis: false,
			connectWith: false,
			containment: false,
			cursor: "auto",
			cursorAt: false,
			dropOnEmpty: true,
			forcePlaceholderSize: false,
			forceHelperSize: false,
			grid: false,
			handle: false,
			helper: "original",
			items: "> *",
			opacity: false,
			placeholder: false,
			revert: false,
			scroll: true,
			scrollSensitivity: 20,
			scrollSpeed: 20,
			scope: "default",
			tolerance: "intersect",
			zIndex: 1000,
	
			// callbacks
			activate: null,
			beforeStop: null,
			change: null,
			deactivate: null,
			out: null,
			over: null,
			receive: null,
			remove: null,
			sort: null,
			start: null,
			stop: null,
			update: null
		},
	
		_isOverAxis: function( x, reference, size ) {
			return ( x >= reference ) && ( x < ( reference + size ) );
		},
	
		_isFloating: function( item ) {
			return (/left|right/).test(item.css("float")) || (/inline|table-cell/).test(item.css("display"));
		},
	
		_create: function() {
			this.containerCache = {};
			this.element.addClass("ui-sortable");
	
			//Get the items
			this.refresh();
	
			//Let's determine the parent's offset
			this.offset = this.element.offset();
	
			//Initialize mouse events for interaction
			this._mouseInit();
	
			this._setHandleClassName();
	
			//We're ready to go
			this.ready = true;
	
		},
	
		_setOption: function( key, value ) {
			this._super( key, value );
	
			if ( key === "handle" ) {
				this._setHandleClassName();
			}
		},
	
		_setHandleClassName: function() {
			this.element.find( ".ui-sortable-handle" ).removeClass( "ui-sortable-handle" );
			$.each( this.items, function() {
				( this.instance.options.handle ?
					this.item.find( this.instance.options.handle ) : this.item )
					.addClass( "ui-sortable-handle" );
			});
		},
	
		_destroy: function() {
			this.element
				.removeClass( "ui-sortable ui-sortable-disabled" )
				.find( ".ui-sortable-handle" )
					.removeClass( "ui-sortable-handle" );
			this._mouseDestroy();
	
			for ( var i = this.items.length - 1; i >= 0; i-- ) {
				this.items[i].item.removeData(this.widgetName + "-item");
			}
	
			return this;
		},
	
		_mouseCapture: function(event, overrideHandle) {
			var currentItem = null,
				validHandle = false,
				that = this;
	
			if (this.reverting) {
				return false;
			}
	
			if(this.options.disabled || this.options.type === "static") {
				return false;
			}
	
			//We have to refresh the items data once first
			this._refreshItems(event);
	
			//Find out if the clicked node (or one of its parents) is a actual item in this.items
			$(event.target).parents().each(function() {
				if($.data(this, that.widgetName + "-item") === that) {
					currentItem = $(this);
					return false;
				}
			});
			if($.data(event.target, that.widgetName + "-item") === that) {
				currentItem = $(event.target);
			}
	
			if(!currentItem) {
				return false;
			}
			if(this.options.handle && !overrideHandle) {
				$(this.options.handle, currentItem).find("*").addBack().each(function() {
					if(this === event.target) {
						validHandle = true;
					}
				});
				if(!validHandle) {
					return false;
				}
			}
	
			this.currentItem = currentItem;
			this._removeCurrentsFromItems();
			return true;
	
		},
	
		_mouseStart: function(event, overrideHandle, noActivation) {
	
			var i, body,
				o = this.options;
	
			this.currentContainer = this;
	
			//We only need to call refreshPositions, because the refreshItems call has been moved to mouseCapture
			this.refreshPositions();
	
			//Create and append the visible helper
			this.helper = this._createHelper(event);
	
			//Cache the helper size
			this._cacheHelperProportions();
	
			/*
			 * - Position generation -
			 * This block generates everything position related - it's the core of draggables.
			 */
	
			//Cache the margins of the original element
			this._cacheMargins();
	
			//Get the next scrolling parent
			this.scrollParent = this.helper.scrollParent();
	
			//The element's absolute position on the page minus margins
			this.offset = this.currentItem.offset();
			this.offset = {
				top: this.offset.top - this.margins.top,
				left: this.offset.left - this.margins.left
			};
	
			$.extend(this.offset, {
				click: { //Where the click happened, relative to the element
					left: event.pageX - this.offset.left,
					top: event.pageY - this.offset.top
				},
				parent: this._getParentOffset(),
				relative: this._getRelativeOffset() //This is a relative to absolute position minus the actual position calculation - only used for relative positioned helper
			});
	
			// Only after we got the offset, we can change the helper's position to absolute
			// TODO: Still need to figure out a way to make relative sorting possible
			this.helper.css("position", "absolute");
			this.cssPosition = this.helper.css("position");
	
			//Generate the original position
			this.originalPosition = this._generatePosition(event);
			this.originalPageX = event.pageX;
			this.originalPageY = event.pageY;
	
			//Adjust the mouse offset relative to the helper if "cursorAt" is supplied
			(o.cursorAt && this._adjustOffsetFromHelper(o.cursorAt));
	
			//Cache the former DOM position
			this.domPosition = { prev: this.currentItem.prev()[0], parent: this.currentItem.parent()[0] };
	
			//If the helper is not the original, hide the original so it's not playing any role during the drag, won't cause anything bad this way
			if(this.helper[0] !== this.currentItem[0]) {
				this.currentItem.hide();
			}
	
			//Create the placeholder
			this._createPlaceholder();
	
			//Set a containment if given in the options
			if(o.containment) {
				this._setContainment();
			}
	
			if( o.cursor && o.cursor !== "auto" ) { // cursor option
				body = this.document.find( "body" );
	
				// support: IE
				this.storedCursor = body.css( "cursor" );
				body.css( "cursor", o.cursor );
	
				this.storedStylesheet = $( "<style>*{ cursor: "+o.cursor+" !important; }</style>" ).appendTo( body );
			}
	
			if(o.opacity) { // opacity option
				if (this.helper.css("opacity")) {
					this._storedOpacity = this.helper.css("opacity");
				}
				this.helper.css("opacity", o.opacity);
			}
	
			if(o.zIndex) { // zIndex option
				if (this.helper.css("zIndex")) {
					this._storedZIndex = this.helper.css("zIndex");
				}
				this.helper.css("zIndex", o.zIndex);
			}
	
			//Prepare scrolling
			if(this.scrollParent[0] !== this.document[0] && this.scrollParent[0].tagName !== "HTML") {
				this.overflowOffset = this.scrollParent.offset();
			}
	
			//Call callbacks
			this._trigger("start", event, this._uiHash());
	
			//Recache the helper size
			if(!this._preserveHelperProportions) {
				this._cacheHelperProportions();
			}
	
	
			//Post "activate" events to possible containers
			if( !noActivation ) {
				for ( i = this.containers.length - 1; i >= 0; i-- ) {
					this.containers[ i ]._trigger( "activate", event, this._uiHash( this ) );
				}
			}
	
			//Prepare possible droppables
			if($.ui.ddmanager) {
				$.ui.ddmanager.current = this;
			}
	
			if ($.ui.ddmanager && !o.dropBehaviour) {
				$.ui.ddmanager.prepareOffsets(this, event);
			}
	
			this.dragging = true;
	
			this.helper.addClass("ui-sortable-helper");
			this._mouseDrag(event); //Execute the drag once - this causes the helper not to be visible before getting its correct position
			return true;
	
		},
	
		_mouseDrag: function(event) {
			var i, item, itemElement, intersection,
				o = this.options,
				scrolled = false;
	
			//Compute the helpers position
			this.position = this._generatePosition(event);
			this.positionAbs = this._convertPositionTo("absolute");
	
			if (!this.lastPositionAbs) {
				this.lastPositionAbs = this.positionAbs;
			}
	
			//Do scrolling
			if(this.options.scroll) {
				if(this.scrollParent[0] !== this.document[0] && this.scrollParent[0].tagName !== "HTML") {
	
					if((this.overflowOffset.top + this.scrollParent[0].offsetHeight) - event.pageY < o.scrollSensitivity) {
						this.scrollParent[0].scrollTop = scrolled = this.scrollParent[0].scrollTop + o.scrollSpeed;
					} else if(event.pageY - this.overflowOffset.top < o.scrollSensitivity) {
						this.scrollParent[0].scrollTop = scrolled = this.scrollParent[0].scrollTop - o.scrollSpeed;
					}
	
					if((this.overflowOffset.left + this.scrollParent[0].offsetWidth) - event.pageX < o.scrollSensitivity) {
						this.scrollParent[0].scrollLeft = scrolled = this.scrollParent[0].scrollLeft + o.scrollSpeed;
					} else if(event.pageX - this.overflowOffset.left < o.scrollSensitivity) {
						this.scrollParent[0].scrollLeft = scrolled = this.scrollParent[0].scrollLeft - o.scrollSpeed;
					}
	
				} else {
	
					if(event.pageY - this.document.scrollTop() < o.scrollSensitivity) {
						scrolled = this.document.scrollTop(this.document.scrollTop() - o.scrollSpeed);
					} else if(this.window.height() - (event.pageY - this.document.scrollTop()) < o.scrollSensitivity) {
						scrolled = this.document.scrollTop(this.document.scrollTop() + o.scrollSpeed);
					}
	
					if(event.pageX - this.document.scrollLeft() < o.scrollSensitivity) {
						scrolled = this.document.scrollLeft(this.document.scrollLeft() - o.scrollSpeed);
					} else if(this.window.width() - (event.pageX - this.document.scrollLeft()) < o.scrollSensitivity) {
						scrolled = this.document.scrollLeft(this.document.scrollLeft() + o.scrollSpeed);
					}
	
				}
	
				if(scrolled !== false && $.ui.ddmanager && !o.dropBehaviour) {
					$.ui.ddmanager.prepareOffsets(this, event);
				}
			}
	
			//Regenerate the absolute position used for position checks
			this.positionAbs = this._convertPositionTo("absolute");
	
			//Set the helper position
			if(!this.options.axis || this.options.axis !== "y") {
				this.helper[0].style.left = this.position.left+"px";
			}
			if(!this.options.axis || this.options.axis !== "x") {
				this.helper[0].style.top = this.position.top+"px";
			}
	
			//Rearrange
			for (i = this.items.length - 1; i >= 0; i--) {
	
				//Cache variables and intersection, continue if no intersection
				item = this.items[i];
				itemElement = item.item[0];
				intersection = this._intersectsWithPointer(item);
				if (!intersection) {
					continue;
				}
	
				// Only put the placeholder inside the current Container, skip all
				// items from other containers. This works because when moving
				// an item from one container to another the
				// currentContainer is switched before the placeholder is moved.
				//
				// Without this, moving items in "sub-sortables" can cause
				// the placeholder to jitter between the outer and inner container.
				if (item.instance !== this.currentContainer) {
					continue;
				}
	
				// cannot intersect with itself
				// no useless actions that have been done before
				// no action if the item moved is the parent of the item checked
				if (itemElement !== this.currentItem[0] &&
					this.placeholder[intersection === 1 ? "next" : "prev"]()[0] !== itemElement &&
					!$.contains(this.placeholder[0], itemElement) &&
					(this.options.type === "semi-dynamic" ? !$.contains(this.element[0], itemElement) : true)
				) {
	
					this.direction = intersection === 1 ? "down" : "up";
	
					if (this.options.tolerance === "pointer" || this._intersectsWithSides(item)) {
						this._rearrange(event, item);
					} else {
						break;
					}
	
					this._trigger("change", event, this._uiHash());
					break;
				}
			}
	
			//Post events to containers
			this._contactContainers(event);
	
			//Interconnect with droppables
			if($.ui.ddmanager) {
				$.ui.ddmanager.drag(this, event);
			}
	
			//Call callbacks
			this._trigger("sort", event, this._uiHash());
	
			this.lastPositionAbs = this.positionAbs;
			return false;
	
		},
	
		_mouseStop: function(event, noPropagation) {
	
			if(!event) {
				return;
			}
	
			//If we are using droppables, inform the manager about the drop
			if ($.ui.ddmanager && !this.options.dropBehaviour) {
				$.ui.ddmanager.drop(this, event);
			}
	
			if(this.options.revert) {
				var that = this,
					cur = this.placeholder.offset(),
					axis = this.options.axis,
					animation = {};
	
				if ( !axis || axis === "x" ) {
					animation.left = cur.left - this.offset.parent.left - this.margins.left + (this.offsetParent[0] === this.document[0].body ? 0 : this.offsetParent[0].scrollLeft);
				}
				if ( !axis || axis === "y" ) {
					animation.top = cur.top - this.offset.parent.top - this.margins.top + (this.offsetParent[0] === this.document[0].body ? 0 : this.offsetParent[0].scrollTop);
				}
				this.reverting = true;
				$(this.helper).animate( animation, parseInt(this.options.revert, 10) || 500, function() {
					that._clear(event);
				});
			} else {
				this._clear(event, noPropagation);
			}
	
			return false;
	
		},
	
		cancel: function() {
	
			if(this.dragging) {
	
				this._mouseUp({ target: null });
	
				if(this.options.helper === "original") {
					this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper");
				} else {
					this.currentItem.show();
				}
	
				//Post deactivating events to containers
				for (var i = this.containers.length - 1; i >= 0; i--){
					this.containers[i]._trigger("deactivate", null, this._uiHash(this));
					if(this.containers[i].containerCache.over) {
						this.containers[i]._trigger("out", null, this._uiHash(this));
						this.containers[i].containerCache.over = 0;
					}
				}
	
			}
	
			if (this.placeholder) {
				//$(this.placeholder[0]).remove(); would have been the jQuery way - unfortunately, it unbinds ALL events from the original node!
				if(this.placeholder[0].parentNode) {
					this.placeholder[0].parentNode.removeChild(this.placeholder[0]);
				}
				if(this.options.helper !== "original" && this.helper && this.helper[0].parentNode) {
					this.helper.remove();
				}
	
				$.extend(this, {
					helper: null,
					dragging: false,
					reverting: false,
					_noFinalSort: null
				});
	
				if(this.domPosition.prev) {
					$(this.domPosition.prev).after(this.currentItem);
				} else {
					$(this.domPosition.parent).prepend(this.currentItem);
				}
			}
	
			return this;
	
		},
	
		serialize: function(o) {
	
			var items = this._getItemsAsjQuery(o && o.connected),
				str = [];
			o = o || {};
	
			$(items).each(function() {
				var res = ($(o.item || this).attr(o.attribute || "id") || "").match(o.expression || (/(.+)[\-=_](.+)/));
				if (res) {
					str.push((o.key || res[1]+"[]")+"="+(o.key && o.expression ? res[1] : res[2]));
				}
			});
	
			if(!str.length && o.key) {
				str.push(o.key + "=");
			}
	
			return str.join("&");
	
		},
	
		toArray: function(o) {
	
			var items = this._getItemsAsjQuery(o && o.connected),
				ret = [];
	
			o = o || {};
	
			items.each(function() { ret.push($(o.item || this).attr(o.attribute || "id") || ""); });
			return ret;
	
		},
	
		/* Be careful with the following core functions */
		_intersectsWith: function(item) {
	
			var x1 = this.positionAbs.left,
				x2 = x1 + this.helperProportions.width,
				y1 = this.positionAbs.top,
				y2 = y1 + this.helperProportions.height,
				l = item.left,
				r = l + item.width,
				t = item.top,
				b = t + item.height,
				dyClick = this.offset.click.top,
				dxClick = this.offset.click.left,
				isOverElementHeight = ( this.options.axis === "x" ) || ( ( y1 + dyClick ) > t && ( y1 + dyClick ) < b ),
				isOverElementWidth = ( this.options.axis === "y" ) || ( ( x1 + dxClick ) > l && ( x1 + dxClick ) < r ),
				isOverElement = isOverElementHeight && isOverElementWidth;
	
			if ( this.options.tolerance === "pointer" ||
				this.options.forcePointerForContainers ||
				(this.options.tolerance !== "pointer" && this.helperProportions[this.floating ? "width" : "height"] > item[this.floating ? "width" : "height"])
			) {
				return isOverElement;
			} else {
	
				return (l < x1 + (this.helperProportions.width / 2) && // Right Half
					x2 - (this.helperProportions.width / 2) < r && // Left Half
					t < y1 + (this.helperProportions.height / 2) && // Bottom Half
					y2 - (this.helperProportions.height / 2) < b ); // Top Half
	
			}
		},
	
		_intersectsWithPointer: function(item) {
	
			var isOverElementHeight = (this.options.axis === "x") || this._isOverAxis(this.positionAbs.top + this.offset.click.top, item.top, item.height),
				isOverElementWidth = (this.options.axis === "y") || this._isOverAxis(this.positionAbs.left + this.offset.click.left, item.left, item.width),
				isOverElement = isOverElementHeight && isOverElementWidth,
				verticalDirection = this._getDragVerticalDirection(),
				horizontalDirection = this._getDragHorizontalDirection();
	
			if (!isOverElement) {
				return false;
			}
	
			return this.floating ?
				( ((horizontalDirection && horizontalDirection === "right") || verticalDirection === "down") ? 2 : 1 )
				: ( verticalDirection && (verticalDirection === "down" ? 2 : 1) );
	
		},
	
		_intersectsWithSides: function(item) {
	
			var isOverBottomHalf = this._isOverAxis(this.positionAbs.top + this.offset.click.top, item.top + (item.height/2), item.height),
				isOverRightHalf = this._isOverAxis(this.positionAbs.left + this.offset.click.left, item.left + (item.width/2), item.width),
				verticalDirection = this._getDragVerticalDirection(),
				horizontalDirection = this._getDragHorizontalDirection();
	
			if (this.floating && horizontalDirection) {
				return ((horizontalDirection === "right" && isOverRightHalf) || (horizontalDirection === "left" && !isOverRightHalf));
			} else {
				return verticalDirection && ((verticalDirection === "down" && isOverBottomHalf) || (verticalDirection === "up" && !isOverBottomHalf));
			}
	
		},
	
		_getDragVerticalDirection: function() {
			var delta = this.positionAbs.top - this.lastPositionAbs.top;
			return delta !== 0 && (delta > 0 ? "down" : "up");
		},
	
		_getDragHorizontalDirection: function() {
			var delta = this.positionAbs.left - this.lastPositionAbs.left;
			return delta !== 0 && (delta > 0 ? "right" : "left");
		},
	
		refresh: function(event) {
			this._refreshItems(event);
			this._setHandleClassName();
			this.refreshPositions();
			return this;
		},
	
		_connectWith: function() {
			var options = this.options;
			return options.connectWith.constructor === String ? [options.connectWith] : options.connectWith;
		},
	
		_getItemsAsjQuery: function(connected) {
	
			var i, j, cur, inst,
				items = [],
				queries = [],
				connectWith = this._connectWith();
	
			if(connectWith && connected) {
				for (i = connectWith.length - 1; i >= 0; i--){
					cur = $(connectWith[i], this.document[0]);
					for ( j = cur.length - 1; j >= 0; j--){
						inst = $.data(cur[j], this.widgetFullName);
						if(inst && inst !== this && !inst.options.disabled) {
							queries.push([$.isFunction(inst.options.items) ? inst.options.items.call(inst.element) : $(inst.options.items, inst.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"), inst]);
						}
					}
				}
			}
	
			queries.push([$.isFunction(this.options.items) ? this.options.items.call(this.element, null, { options: this.options, item: this.currentItem }) : $(this.options.items, this.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"), this]);
	
			function addItems() {
				items.push( this );
			}
			for (i = queries.length - 1; i >= 0; i--){
				queries[i][0].each( addItems );
			}
	
			return $(items);
	
		},
	
		_removeCurrentsFromItems: function() {
	
			var list = this.currentItem.find(":data(" + this.widgetName + "-item)");
	
			this.items = $.grep(this.items, function (item) {
				for (var j=0; j < list.length; j++) {
					if(list[j] === item.item[0]) {
						return false;
					}
				}
				return true;
			});
	
		},
	
		_refreshItems: function(event) {
	
			this.items = [];
			this.containers = [this];
	
			var i, j, cur, inst, targetData, _queries, item, queriesLength,
				items = this.items,
				queries = [[$.isFunction(this.options.items) ? this.options.items.call(this.element[0], event, { item: this.currentItem }) : $(this.options.items, this.element), this]],
				connectWith = this._connectWith();
	
			if(connectWith && this.ready) { //Shouldn't be run the first time through due to massive slow-down
				for (i = connectWith.length - 1; i >= 0; i--){
					cur = $(connectWith[i], this.document[0]);
					for (j = cur.length - 1; j >= 0; j--){
						inst = $.data(cur[j], this.widgetFullName);
						if(inst && inst !== this && !inst.options.disabled) {
							queries.push([$.isFunction(inst.options.items) ? inst.options.items.call(inst.element[0], event, { item: this.currentItem }) : $(inst.options.items, inst.element), inst]);
							this.containers.push(inst);
						}
					}
				}
			}
	
			for (i = queries.length - 1; i >= 0; i--) {
				targetData = queries[i][1];
				_queries = queries[i][0];
	
				for (j=0, queriesLength = _queries.length; j < queriesLength; j++) {
					item = $(_queries[j]);
	
					item.data(this.widgetName + "-item", targetData); // Data for target checking (mouse manager)
	
					items.push({
						item: item,
						instance: targetData,
						width: 0, height: 0,
						left: 0, top: 0
					});
				}
			}
	
		},
	
		refreshPositions: function(fast) {
	
			// Determine whether items are being displayed horizontally
			this.floating = this.items.length ?
				this.options.axis === "x" || this._isFloating( this.items[ 0 ].item ) :
				false;
	
			//This has to be redone because due to the item being moved out/into the offsetParent, the offsetParent's position will change
			if(this.offsetParent && this.helper) {
				this.offset.parent = this._getParentOffset();
			}
	
			var i, item, t, p;
	
			for (i = this.items.length - 1; i >= 0; i--){
				item = this.items[i];
	
				//We ignore calculating positions of all connected containers when we're not over them
				if(item.instance !== this.currentContainer && this.currentContainer && item.item[0] !== this.currentItem[0]) {
					continue;
				}
	
				t = this.options.toleranceElement ? $(this.options.toleranceElement, item.item) : item.item;
	
				if (!fast) {
					item.width = t.outerWidth();
					item.height = t.outerHeight();
				}
	
				p = t.offset();
				item.left = p.left;
				item.top = p.top;
			}
	
			if(this.options.custom && this.options.custom.refreshContainers) {
				this.options.custom.refreshContainers.call(this);
			} else {
				for (i = this.containers.length - 1; i >= 0; i--){
					p = this.containers[i].element.offset();
					this.containers[i].containerCache.left = p.left;
					this.containers[i].containerCache.top = p.top;
					this.containers[i].containerCache.width = this.containers[i].element.outerWidth();
					this.containers[i].containerCache.height = this.containers[i].element.outerHeight();
				}
			}
	
			return this;
		},
	
		_createPlaceholder: function(that) {
			that = that || this;
			var className,
				o = that.options;
	
			if(!o.placeholder || o.placeholder.constructor === String) {
				className = o.placeholder;
				o.placeholder = {
					element: function() {
	
						var nodeName = that.currentItem[0].nodeName.toLowerCase(),
							element = $( "<" + nodeName + ">", that.document[0] )
								.addClass(className || that.currentItem[0].className+" ui-sortable-placeholder")
								.removeClass("ui-sortable-helper");
	
						if ( nodeName === "tbody" ) {
							that._createTrPlaceholder(
								that.currentItem.find( "tr" ).eq( 0 ),
								$( "<tr>", that.document[ 0 ] ).appendTo( element )
							);
						} else if ( nodeName === "tr" ) {
							that._createTrPlaceholder( that.currentItem, element );
						} else if ( nodeName === "img" ) {
							element.attr( "src", that.currentItem.attr( "src" ) );
						}
	
						if ( !className ) {
							element.css( "visibility", "hidden" );
						}
	
						return element;
					},
					update: function(container, p) {
	
						// 1. If a className is set as 'placeholder option, we don't force sizes - the class is responsible for that
						// 2. The option 'forcePlaceholderSize can be enabled to force it even if a class name is specified
						if(className && !o.forcePlaceholderSize) {
							return;
						}
	
						//If the element doesn't have a actual height by itself (without styles coming from a stylesheet), it receives the inline height from the dragged item
						if(!p.height()) { p.height(that.currentItem.innerHeight() - parseInt(that.currentItem.css("paddingTop")||0, 10) - parseInt(that.currentItem.css("paddingBottom")||0, 10)); }
						if(!p.width()) { p.width(that.currentItem.innerWidth() - parseInt(that.currentItem.css("paddingLeft")||0, 10) - parseInt(that.currentItem.css("paddingRight")||0, 10)); }
					}
				};
			}
	
			//Create the placeholder
			that.placeholder = $(o.placeholder.element.call(that.element, that.currentItem));
	
			//Append it after the actual current item
			that.currentItem.after(that.placeholder);
	
			//Update the size of the placeholder (TODO: Logic to fuzzy, see line 316/317)
			o.placeholder.update(that, that.placeholder);
	
		},
	
		_createTrPlaceholder: function( sourceTr, targetTr ) {
			var that = this;
	
			sourceTr.children().each(function() {
				$( "<td>&#160;</td>", that.document[ 0 ] )
					.attr( "colspan", $( this ).attr( "colspan" ) || 1 )
					.appendTo( targetTr );
			});
		},
	
		_contactContainers: function(event) {
			var i, j, dist, itemWithLeastDistance, posProperty, sizeProperty, cur, nearBottom, floating, axis,
				innermostContainer = null,
				innermostIndex = null;
	
			// get innermost container that intersects with item
			for (i = this.containers.length - 1; i >= 0; i--) {
	
				// never consider a container that's located within the item itself
				if($.contains(this.currentItem[0], this.containers[i].element[0])) {
					continue;
				}
	
				if(this._intersectsWith(this.containers[i].containerCache)) {
	
					// if we've already found a container and it's more "inner" than this, then continue
					if(innermostContainer && $.contains(this.containers[i].element[0], innermostContainer.element[0])) {
						continue;
					}
	
					innermostContainer = this.containers[i];
					innermostIndex = i;
	
				} else {
					// container doesn't intersect. trigger "out" event if necessary
					if(this.containers[i].containerCache.over) {
						this.containers[i]._trigger("out", event, this._uiHash(this));
						this.containers[i].containerCache.over = 0;
					}
				}
	
			}
	
			// if no intersecting containers found, return
			if(!innermostContainer) {
				return;
			}
	
			// move the item into the container if it's not there already
			if(this.containers.length === 1) {
				if (!this.containers[innermostIndex].containerCache.over) {
					this.containers[innermostIndex]._trigger("over", event, this._uiHash(this));
					this.containers[innermostIndex].containerCache.over = 1;
				}
			} else {
	
				//When entering a new container, we will find the item with the least distance and append our item near it
				dist = 10000;
				itemWithLeastDistance = null;
				floating = innermostContainer.floating || this._isFloating(this.currentItem);
				posProperty = floating ? "left" : "top";
				sizeProperty = floating ? "width" : "height";
				axis = floating ? "clientX" : "clientY";
	
				for (j = this.items.length - 1; j >= 0; j--) {
					if(!$.contains(this.containers[innermostIndex].element[0], this.items[j].item[0])) {
						continue;
					}
					if(this.items[j].item[0] === this.currentItem[0]) {
						continue;
					}
	
					cur = this.items[j].item.offset()[posProperty];
					nearBottom = false;
					if ( event[ axis ] - cur > this.items[ j ][ sizeProperty ] / 2 ) {
						nearBottom = true;
					}
	
					if ( Math.abs( event[ axis ] - cur ) < dist ) {
						dist = Math.abs( event[ axis ] - cur );
						itemWithLeastDistance = this.items[ j ];
						this.direction = nearBottom ? "up": "down";
					}
				}
	
				//Check if dropOnEmpty is enabled
				if(!itemWithLeastDistance && !this.options.dropOnEmpty) {
					return;
				}
	
				if(this.currentContainer === this.containers[innermostIndex]) {
					if ( !this.currentContainer.containerCache.over ) {
						this.containers[ innermostIndex ]._trigger( "over", event, this._uiHash() );
						this.currentContainer.containerCache.over = 1;
					}
					return;
				}
	
				itemWithLeastDistance ? this._rearrange(event, itemWithLeastDistance, null, true) : this._rearrange(event, null, this.containers[innermostIndex].element, true);
				this._trigger("change", event, this._uiHash());
				this.containers[innermostIndex]._trigger("change", event, this._uiHash(this));
				this.currentContainer = this.containers[innermostIndex];
	
				//Update the placeholder
				this.options.placeholder.update(this.currentContainer, this.placeholder);
	
				this.containers[innermostIndex]._trigger("over", event, this._uiHash(this));
				this.containers[innermostIndex].containerCache.over = 1;
			}
	
	
		},
	
		_createHelper: function(event) {
	
			var o = this.options,
				helper = $.isFunction(o.helper) ? $(o.helper.apply(this.element[0], [event, this.currentItem])) : (o.helper === "clone" ? this.currentItem.clone() : this.currentItem);
	
			//Add the helper to the DOM if that didn't happen already
			if(!helper.parents("body").length) {
				$(o.appendTo !== "parent" ? o.appendTo : this.currentItem[0].parentNode)[0].appendChild(helper[0]);
			}
	
			if(helper[0] === this.currentItem[0]) {
				this._storedCSS = { width: this.currentItem[0].style.width, height: this.currentItem[0].style.height, position: this.currentItem.css("position"), top: this.currentItem.css("top"), left: this.currentItem.css("left") };
			}
	
			if(!helper[0].style.width || o.forceHelperSize) {
				helper.width(this.currentItem.width());
			}
			if(!helper[0].style.height || o.forceHelperSize) {
				helper.height(this.currentItem.height());
			}
	
			return helper;
	
		},
	
		_adjustOffsetFromHelper: function(obj) {
			if (typeof obj === "string") {
				obj = obj.split(" ");
			}
			if ($.isArray(obj)) {
				obj = {left: +obj[0], top: +obj[1] || 0};
			}
			if ("left" in obj) {
				this.offset.click.left = obj.left + this.margins.left;
			}
			if ("right" in obj) {
				this.offset.click.left = this.helperProportions.width - obj.right + this.margins.left;
			}
			if ("top" in obj) {
				this.offset.click.top = obj.top + this.margins.top;
			}
			if ("bottom" in obj) {
				this.offset.click.top = this.helperProportions.height - obj.bottom + this.margins.top;
			}
		},
	
		_getParentOffset: function() {
	
	
			//Get the offsetParent and cache its position
			this.offsetParent = this.helper.offsetParent();
			var po = this.offsetParent.offset();
	
			// This is a special case where we need to modify a offset calculated on start, since the following happened:
			// 1. The position of the helper is absolute, so it's position is calculated based on the next positioned parent
			// 2. The actual offset parent is a child of the scroll parent, and the scroll parent isn't the document, which means that
			//    the scroll is included in the initial calculation of the offset of the parent, and never recalculated upon drag
			if(this.cssPosition === "absolute" && this.scrollParent[0] !== this.document[0] && $.contains(this.scrollParent[0], this.offsetParent[0])) {
				po.left += this.scrollParent.scrollLeft();
				po.top += this.scrollParent.scrollTop();
			}
	
			// This needs to be actually done for all browsers, since pageX/pageY includes this information
			// with an ugly IE fix
			if( this.offsetParent[0] === this.document[0].body || (this.offsetParent[0].tagName && this.offsetParent[0].tagName.toLowerCase() === "html" && $.ui.ie)) {
				po = { top: 0, left: 0 };
			}
	
			return {
				top: po.top + (parseInt(this.offsetParent.css("borderTopWidth"),10) || 0),
				left: po.left + (parseInt(this.offsetParent.css("borderLeftWidth"),10) || 0)
			};
	
		},
	
		_getRelativeOffset: function() {
	
			if(this.cssPosition === "relative") {
				var p = this.currentItem.position();
				return {
					top: p.top - (parseInt(this.helper.css("top"),10) || 0) + this.scrollParent.scrollTop(),
					left: p.left - (parseInt(this.helper.css("left"),10) || 0) + this.scrollParent.scrollLeft()
				};
			} else {
				return { top: 0, left: 0 };
			}
	
		},
	
		_cacheMargins: function() {
			this.margins = {
				left: (parseInt(this.currentItem.css("marginLeft"),10) || 0),
				top: (parseInt(this.currentItem.css("marginTop"),10) || 0)
			};
		},
	
		_cacheHelperProportions: function() {
			this.helperProportions = {
				width: this.helper.outerWidth(),
				height: this.helper.outerHeight()
			};
		},
	
		_setContainment: function() {
	
			var ce, co, over,
				o = this.options;
			if(o.containment === "parent") {
				o.containment = this.helper[0].parentNode;
			}
			if(o.containment === "document" || o.containment === "window") {
				this.containment = [
					0 - this.offset.relative.left - this.offset.parent.left,
					0 - this.offset.relative.top - this.offset.parent.top,
					o.containment === "document" ? this.document.width() : this.window.width() - this.helperProportions.width - this.margins.left,
					(o.containment === "document" ? this.document.width() : this.window.height() || this.document[0].body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top
				];
			}
	
			if(!(/^(document|window|parent)$/).test(o.containment)) {
				ce = $(o.containment)[0];
				co = $(o.containment).offset();
				over = ($(ce).css("overflow") !== "hidden");
	
				this.containment = [
					co.left + (parseInt($(ce).css("borderLeftWidth"),10) || 0) + (parseInt($(ce).css("paddingLeft"),10) || 0) - this.margins.left,
					co.top + (parseInt($(ce).css("borderTopWidth"),10) || 0) + (parseInt($(ce).css("paddingTop"),10) || 0) - this.margins.top,
					co.left+(over ? Math.max(ce.scrollWidth,ce.offsetWidth) : ce.offsetWidth) - (parseInt($(ce).css("borderLeftWidth"),10) || 0) - (parseInt($(ce).css("paddingRight"),10) || 0) - this.helperProportions.width - this.margins.left,
					co.top+(over ? Math.max(ce.scrollHeight,ce.offsetHeight) : ce.offsetHeight) - (parseInt($(ce).css("borderTopWidth"),10) || 0) - (parseInt($(ce).css("paddingBottom"),10) || 0) - this.helperProportions.height - this.margins.top
				];
			}
	
		},
	
		_convertPositionTo: function(d, pos) {
	
			if(!pos) {
				pos = this.position;
			}
			var mod = d === "absolute" ? 1 : -1,
				scroll = this.cssPosition === "absolute" && !(this.scrollParent[0] !== this.document[0] && $.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent : this.scrollParent,
				scrollIsRootNode = (/(html|body)/i).test(scroll[0].tagName);
	
			return {
				top: (
					pos.top	+																// The absolute mouse position
					this.offset.relative.top * mod +										// Only for relative positioned nodes: Relative offset from element to offset parent
					this.offset.parent.top * mod -											// The offsetParent's offset without borders (offset + border)
					( ( this.cssPosition === "fixed" ? -this.scrollParent.scrollTop() : ( scrollIsRootNode ? 0 : scroll.scrollTop() ) ) * mod)
				),
				left: (
					pos.left +																// The absolute mouse position
					this.offset.relative.left * mod +										// Only for relative positioned nodes: Relative offset from element to offset parent
					this.offset.parent.left * mod	-										// The offsetParent's offset without borders (offset + border)
					( ( this.cssPosition === "fixed" ? -this.scrollParent.scrollLeft() : scrollIsRootNode ? 0 : scroll.scrollLeft() ) * mod)
				)
			};
	
		},
	
		_generatePosition: function(event) {
	
			var top, left,
				o = this.options,
				pageX = event.pageX,
				pageY = event.pageY,
				scroll = this.cssPosition === "absolute" && !(this.scrollParent[0] !== this.document[0] && $.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent : this.scrollParent, scrollIsRootNode = (/(html|body)/i).test(scroll[0].tagName);
	
			// This is another very weird special case that only happens for relative elements:
			// 1. If the css position is relative
			// 2. and the scroll parent is the document or similar to the offset parent
			// we have to refresh the relative offset during the scroll so there are no jumps
			if(this.cssPosition === "relative" && !(this.scrollParent[0] !== this.document[0] && this.scrollParent[0] !== this.offsetParent[0])) {
				this.offset.relative = this._getRelativeOffset();
			}
	
			/*
			 * - Position constraining -
			 * Constrain the position to a mix of grid, containment.
			 */
	
			if(this.originalPosition) { //If we are not dragging yet, we won't check for options
	
				if(this.containment) {
					if(event.pageX - this.offset.click.left < this.containment[0]) {
						pageX = this.containment[0] + this.offset.click.left;
					}
					if(event.pageY - this.offset.click.top < this.containment[1]) {
						pageY = this.containment[1] + this.offset.click.top;
					}
					if(event.pageX - this.offset.click.left > this.containment[2]) {
						pageX = this.containment[2] + this.offset.click.left;
					}
					if(event.pageY - this.offset.click.top > this.containment[3]) {
						pageY = this.containment[3] + this.offset.click.top;
					}
				}
	
				if(o.grid) {
					top = this.originalPageY + Math.round((pageY - this.originalPageY) / o.grid[1]) * o.grid[1];
					pageY = this.containment ? ( (top - this.offset.click.top >= this.containment[1] && top - this.offset.click.top <= this.containment[3]) ? top : ((top - this.offset.click.top >= this.containment[1]) ? top - o.grid[1] : top + o.grid[1])) : top;
	
					left = this.originalPageX + Math.round((pageX - this.originalPageX) / o.grid[0]) * o.grid[0];
					pageX = this.containment ? ( (left - this.offset.click.left >= this.containment[0] && left - this.offset.click.left <= this.containment[2]) ? left : ((left - this.offset.click.left >= this.containment[0]) ? left - o.grid[0] : left + o.grid[0])) : left;
				}
	
			}
	
			return {
				top: (
					pageY -																// The absolute mouse position
					this.offset.click.top -													// Click offset (relative to the element)
					this.offset.relative.top	-											// Only for relative positioned nodes: Relative offset from element to offset parent
					this.offset.parent.top +												// The offsetParent's offset without borders (offset + border)
					( ( this.cssPosition === "fixed" ? -this.scrollParent.scrollTop() : ( scrollIsRootNode ? 0 : scroll.scrollTop() ) ))
				),
				left: (
					pageX -																// The absolute mouse position
					this.offset.click.left -												// Click offset (relative to the element)
					this.offset.relative.left	-											// Only for relative positioned nodes: Relative offset from element to offset parent
					this.offset.parent.left +												// The offsetParent's offset without borders (offset + border)
					( ( this.cssPosition === "fixed" ? -this.scrollParent.scrollLeft() : scrollIsRootNode ? 0 : scroll.scrollLeft() ))
				)
			};
	
		},
	
		_rearrange: function(event, i, a, hardRefresh) {
	
			a ? a[0].appendChild(this.placeholder[0]) : i.item[0].parentNode.insertBefore(this.placeholder[0], (this.direction === "down" ? i.item[0] : i.item[0].nextSibling));
	
			//Various things done here to improve the performance:
			// 1. we create a setTimeout, that calls refreshPositions
			// 2. on the instance, we have a counter variable, that get's higher after every append
			// 3. on the local scope, we copy the counter variable, and check in the timeout, if it's still the same
			// 4. this lets only the last addition to the timeout stack through
			this.counter = this.counter ? ++this.counter : 1;
			var counter = this.counter;
	
			this._delay(function() {
				if(counter === this.counter) {
					this.refreshPositions(!hardRefresh); //Precompute after each DOM insertion, NOT on mousemove
				}
			});
	
		},
	
		_clear: function(event, noPropagation) {
	
			this.reverting = false;
			// We delay all events that have to be triggered to after the point where the placeholder has been removed and
			// everything else normalized again
			var i,
				delayedTriggers = [];
	
			// We first have to update the dom position of the actual currentItem
			// Note: don't do it if the current item is already removed (by a user), or it gets reappended (see #4088)
			if(!this._noFinalSort && this.currentItem.parent().length) {
				this.placeholder.before(this.currentItem);
			}
			this._noFinalSort = null;
	
			if(this.helper[0] === this.currentItem[0]) {
				for(i in this._storedCSS) {
					if(this._storedCSS[i] === "auto" || this._storedCSS[i] === "static") {
						this._storedCSS[i] = "";
					}
				}
				this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper");
			} else {
				this.currentItem.show();
			}
	
			if(this.fromOutside && !noPropagation) {
				delayedTriggers.push(function(event) { this._trigger("receive", event, this._uiHash(this.fromOutside)); });
			}
			if((this.fromOutside || this.domPosition.prev !== this.currentItem.prev().not(".ui-sortable-helper")[0] || this.domPosition.parent !== this.currentItem.parent()[0]) && !noPropagation) {
				delayedTriggers.push(function(event) { this._trigger("update", event, this._uiHash()); }); //Trigger update callback if the DOM position has changed
			}
	
			// Check if the items Container has Changed and trigger appropriate
			// events.
			if (this !== this.currentContainer) {
				if(!noPropagation) {
					delayedTriggers.push(function(event) { this._trigger("remove", event, this._uiHash()); });
					delayedTriggers.push((function(c) { return function(event) { c._trigger("receive", event, this._uiHash(this)); };  }).call(this, this.currentContainer));
					delayedTriggers.push((function(c) { return function(event) { c._trigger("update", event, this._uiHash(this));  }; }).call(this, this.currentContainer));
				}
			}
	
	
			//Post events to containers
			function delayEvent( type, instance, container ) {
				return function( event ) {
					container._trigger( type, event, instance._uiHash( instance ) );
				};
			}
			for (i = this.containers.length - 1; i >= 0; i--){
				if (!noPropagation) {
					delayedTriggers.push( delayEvent( "deactivate", this, this.containers[ i ] ) );
				}
				if(this.containers[i].containerCache.over) {
					delayedTriggers.push( delayEvent( "out", this, this.containers[ i ] ) );
					this.containers[i].containerCache.over = 0;
				}
			}
	
			//Do what was originally in plugins
			if ( this.storedCursor ) {
				this.document.find( "body" ).css( "cursor", this.storedCursor );
				this.storedStylesheet.remove();
			}
			if(this._storedOpacity) {
				this.helper.css("opacity", this._storedOpacity);
			}
			if(this._storedZIndex) {
				this.helper.css("zIndex", this._storedZIndex === "auto" ? "" : this._storedZIndex);
			}
	
			this.dragging = false;
	
			if(!noPropagation) {
				this._trigger("beforeStop", event, this._uiHash());
			}
	
			//$(this.placeholder[0]).remove(); would have been the jQuery way - unfortunately, it unbinds ALL events from the original node!
			this.placeholder[0].parentNode.removeChild(this.placeholder[0]);
	
			if ( !this.cancelHelperRemoval ) {
				if ( this.helper[ 0 ] !== this.currentItem[ 0 ] ) {
					this.helper.remove();
				}
				this.helper = null;
			}
	
			if(!noPropagation) {
				for (i=0; i < delayedTriggers.length; i++) {
					delayedTriggers[i].call(this, event);
				} //Trigger all delayed events
				this._trigger("stop", event, this._uiHash());
			}
	
			this.fromOutside = false;
			return !this.cancelHelperRemoval;
	
		},
	
		_trigger: function() {
			if ($.Widget.prototype._trigger.apply(this, arguments) === false) {
				this.cancel();
			}
		},
	
		_uiHash: function(_inst) {
			var inst = _inst || this;
			return {
				helper: inst.helper,
				placeholder: inst.placeholder || $([]),
				position: inst.position,
				originalPosition: inst.originalPosition,
				offset: inst.positionAbs,
				item: inst.currentItem,
				sender: _inst ? _inst.element : null
			};
		}
	
	});
	
	
	/*!
	 * jQuery UI Spinner 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/spinner/
	 */
	
	
	function spinner_modifier( fn ) {
		return function() {
			var previous = this.element.val();
			fn.apply( this, arguments );
			this._refresh();
			if ( previous !== this.element.val() ) {
				this._trigger( "change" );
			}
		};
	}
	
	var spinner = $.widget( "ui.spinner", {
		version: "1.11.4",
		defaultElement: "<input>",
		widgetEventPrefix: "spin",
		options: {
			culture: null,
			icons: {
				down: "ui-icon-triangle-1-s",
				up: "ui-icon-triangle-1-n"
			},
			incremental: true,
			max: null,
			min: null,
			numberFormat: null,
			page: 10,
			step: 1,
	
			change: null,
			spin: null,
			start: null,
			stop: null
		},
	
		_create: function() {
			// handle string values that need to be parsed
			this._setOption( "max", this.options.max );
			this._setOption( "min", this.options.min );
			this._setOption( "step", this.options.step );
	
			// Only format if there is a value, prevents the field from being marked
			// as invalid in Firefox, see #9573.
			if ( this.value() !== "" ) {
				// Format the value, but don't constrain.
				this._value( this.element.val(), true );
			}
	
			this._draw();
			this._on( this._events );
			this._refresh();
	
			// turning off autocomplete prevents the browser from remembering the
			// value when navigating through history, so we re-enable autocomplete
			// if the page is unloaded before the widget is destroyed. #7790
			this._on( this.window, {
				beforeunload: function() {
					this.element.removeAttr( "autocomplete" );
				}
			});
		},
	
		_getCreateOptions: function() {
			var options = {},
				element = this.element;
	
			$.each( [ "min", "max", "step" ], function( i, option ) {
				var value = element.attr( option );
				if ( value !== undefined && value.length ) {
					options[ option ] = value;
				}
			});
	
			return options;
		},
	
		_events: {
			keydown: function( event ) {
				if ( this._start( event ) && this._keydown( event ) ) {
					event.preventDefault();
				}
			},
			keyup: "_stop",
			focus: function() {
				this.previous = this.element.val();
			},
			blur: function( event ) {
				if ( this.cancelBlur ) {
					delete this.cancelBlur;
					return;
				}
	
				this._stop();
				this._refresh();
				if ( this.previous !== this.element.val() ) {
					this._trigger( "change", event );
				}
			},
			mousewheel: function( event, delta ) {
				if ( !delta ) {
					return;
				}
				if ( !this.spinning && !this._start( event ) ) {
					return false;
				}
	
				this._spin( (delta > 0 ? 1 : -1) * this.options.step, event );
				clearTimeout( this.mousewheelTimer );
				this.mousewheelTimer = this._delay(function() {
					if ( this.spinning ) {
						this._stop( event );
					}
				}, 100 );
				event.preventDefault();
			},
			"mousedown .ui-spinner-button": function( event ) {
				var previous;
	
				// We never want the buttons to have focus; whenever the user is
				// interacting with the spinner, the focus should be on the input.
				// If the input is focused then this.previous is properly set from
				// when the input first received focus. If the input is not focused
				// then we need to set this.previous based on the value before spinning.
				previous = this.element[0] === this.document[0].activeElement ?
					this.previous : this.element.val();
				function checkFocus() {
					var isActive = this.element[0] === this.document[0].activeElement;
					if ( !isActive ) {
						this.element.focus();
						this.previous = previous;
						// support: IE
						// IE sets focus asynchronously, so we need to check if focus
						// moved off of the input because the user clicked on the button.
						this._delay(function() {
							this.previous = previous;
						});
					}
				}
	
				// ensure focus is on (or stays on) the text field
				event.preventDefault();
				checkFocus.call( this );
	
				// support: IE
				// IE doesn't prevent moving focus even with event.preventDefault()
				// so we set a flag to know when we should ignore the blur event
				// and check (again) if focus moved off of the input.
				this.cancelBlur = true;
				this._delay(function() {
					delete this.cancelBlur;
					checkFocus.call( this );
				});
	
				if ( this._start( event ) === false ) {
					return;
				}
	
				this._repeat( null, $( event.currentTarget ).hasClass( "ui-spinner-up" ) ? 1 : -1, event );
			},
			"mouseup .ui-spinner-button": "_stop",
			"mouseenter .ui-spinner-button": function( event ) {
				// button will add ui-state-active if mouse was down while mouseleave and kept down
				if ( !$( event.currentTarget ).hasClass( "ui-state-active" ) ) {
					return;
				}
	
				if ( this._start( event ) === false ) {
					return false;
				}
				this._repeat( null, $( event.currentTarget ).hasClass( "ui-spinner-up" ) ? 1 : -1, event );
			},
			// TODO: do we really want to consider this a stop?
			// shouldn't we just stop the repeater and wait until mouseup before
			// we trigger the stop event?
			"mouseleave .ui-spinner-button": "_stop"
		},
	
		_draw: function() {
			var uiSpinner = this.uiSpinner = this.element
				.addClass( "ui-spinner-input" )
				.attr( "autocomplete", "off" )
				.wrap( this._uiSpinnerHtml() )
				.parent()
					// add buttons
					.append( this._buttonHtml() );
	
			this.element.attr( "role", "spinbutton" );
	
			// button bindings
			this.buttons = uiSpinner.find( ".ui-spinner-button" )
				.attr( "tabIndex", -1 )
				.button()
				.removeClass( "ui-corner-all" );
	
			// IE 6 doesn't understand height: 50% for the buttons
			// unless the wrapper has an explicit height
			if ( this.buttons.height() > Math.ceil( uiSpinner.height() * 0.5 ) &&
					uiSpinner.height() > 0 ) {
				uiSpinner.height( uiSpinner.height() );
			}
	
			// disable spinner if element was already disabled
			if ( this.options.disabled ) {
				this.disable();
			}
		},
	
		_keydown: function( event ) {
			var options = this.options,
				keyCode = $.ui.keyCode;
	
			switch ( event.keyCode ) {
			case keyCode.UP:
				this._repeat( null, 1, event );
				return true;
			case keyCode.DOWN:
				this._repeat( null, -1, event );
				return true;
			case keyCode.PAGE_UP:
				this._repeat( null, options.page, event );
				return true;
			case keyCode.PAGE_DOWN:
				this._repeat( null, -options.page, event );
				return true;
			}
	
			return false;
		},
	
		_uiSpinnerHtml: function() {
			return "<span class='ui-spinner ui-widget ui-widget-content ui-corner-all'></span>";
		},
	
		_buttonHtml: function() {
			return "" +
				"<a class='ui-spinner-button ui-spinner-up ui-corner-tr'>" +
					"<span class='ui-icon " + this.options.icons.up + "'>&#9650;</span>" +
				"</a>" +
				"<a class='ui-spinner-button ui-spinner-down ui-corner-br'>" +
					"<span class='ui-icon " + this.options.icons.down + "'>&#9660;</span>" +
				"</a>";
		},
	
		_start: function( event ) {
			if ( !this.spinning && this._trigger( "start", event ) === false ) {
				return false;
			}
	
			if ( !this.counter ) {
				this.counter = 1;
			}
			this.spinning = true;
			return true;
		},
	
		_repeat: function( i, steps, event ) {
			i = i || 500;
	
			clearTimeout( this.timer );
			this.timer = this._delay(function() {
				this._repeat( 40, steps, event );
			}, i );
	
			this._spin( steps * this.options.step, event );
		},
	
		_spin: function( step, event ) {
			var value = this.value() || 0;
	
			if ( !this.counter ) {
				this.counter = 1;
			}
	
			value = this._adjustValue( value + step * this._increment( this.counter ) );
	
			if ( !this.spinning || this._trigger( "spin", event, { value: value } ) !== false) {
				this._value( value );
				this.counter++;
			}
		},
	
		_increment: function( i ) {
			var incremental = this.options.incremental;
	
			if ( incremental ) {
				return $.isFunction( incremental ) ?
					incremental( i ) :
					Math.floor( i * i * i / 50000 - i * i / 500 + 17 * i / 200 + 1 );
			}
	
			return 1;
		},
	
		_precision: function() {
			var precision = this._precisionOf( this.options.step );
			if ( this.options.min !== null ) {
				precision = Math.max( precision, this._precisionOf( this.options.min ) );
			}
			return precision;
		},
	
		_precisionOf: function( num ) {
			var str = num.toString(),
				decimal = str.indexOf( "." );
			return decimal === -1 ? 0 : str.length - decimal - 1;
		},
	
		_adjustValue: function( value ) {
			var base, aboveMin,
				options = this.options;
	
			// make sure we're at a valid step
			// - find out where we are relative to the base (min or 0)
			base = options.min !== null ? options.min : 0;
			aboveMin = value - base;
			// - round to the nearest step
			aboveMin = Math.round(aboveMin / options.step) * options.step;
			// - rounding is based on 0, so adjust back to our base
			value = base + aboveMin;
	
			// fix precision from bad JS floating point math
			value = parseFloat( value.toFixed( this._precision() ) );
	
			// clamp the value
			if ( options.max !== null && value > options.max) {
				return options.max;
			}
			if ( options.min !== null && value < options.min ) {
				return options.min;
			}
	
			return value;
		},
	
		_stop: function( event ) {
			if ( !this.spinning ) {
				return;
			}
	
			clearTimeout( this.timer );
			clearTimeout( this.mousewheelTimer );
			this.counter = 0;
			this.spinning = false;
			this._trigger( "stop", event );
		},
	
		_setOption: function( key, value ) {
			if ( key === "culture" || key === "numberFormat" ) {
				var prevValue = this._parse( this.element.val() );
				this.options[ key ] = value;
				this.element.val( this._format( prevValue ) );
				return;
			}
	
			if ( key === "max" || key === "min" || key === "step" ) {
				if ( typeof value === "string" ) {
					value = this._parse( value );
				}
			}
			if ( key === "icons" ) {
				this.buttons.first().find( ".ui-icon" )
					.removeClass( this.options.icons.up )
					.addClass( value.up );
				this.buttons.last().find( ".ui-icon" )
					.removeClass( this.options.icons.down )
					.addClass( value.down );
			}
	
			this._super( key, value );
	
			if ( key === "disabled" ) {
				this.widget().toggleClass( "ui-state-disabled", !!value );
				this.element.prop( "disabled", !!value );
				this.buttons.button( value ? "disable" : "enable" );
			}
		},
	
		_setOptions: spinner_modifier(function( options ) {
			this._super( options );
		}),
	
		_parse: function( val ) {
			if ( typeof val === "string" && val !== "" ) {
				val = window.Globalize && this.options.numberFormat ?
					Globalize.parseFloat( val, 10, this.options.culture ) : +val;
			}
			return val === "" || isNaN( val ) ? null : val;
		},
	
		_format: function( value ) {
			if ( value === "" ) {
				return "";
			}
			return window.Globalize && this.options.numberFormat ?
				Globalize.format( value, this.options.numberFormat, this.options.culture ) :
				value;
		},
	
		_refresh: function() {
			this.element.attr({
				"aria-valuemin": this.options.min,
				"aria-valuemax": this.options.max,
				// TODO: what should we do with values that can't be parsed?
				"aria-valuenow": this._parse( this.element.val() )
			});
		},
	
		isValid: function() {
			var value = this.value();
	
			// null is invalid
			if ( value === null ) {
				return false;
			}
	
			// if value gets adjusted, it's invalid
			return value === this._adjustValue( value );
		},
	
		// update the value without triggering change
		_value: function( value, allowAny ) {
			var parsed;
			if ( value !== "" ) {
				parsed = this._parse( value );
				if ( parsed !== null ) {
					if ( !allowAny ) {
						parsed = this._adjustValue( parsed );
					}
					value = this._format( parsed );
				}
			}
			this.element.val( value );
			this._refresh();
		},
	
		_destroy: function() {
			this.element
				.removeClass( "ui-spinner-input" )
				.prop( "disabled", false )
				.removeAttr( "autocomplete" )
				.removeAttr( "role" )
				.removeAttr( "aria-valuemin" )
				.removeAttr( "aria-valuemax" )
				.removeAttr( "aria-valuenow" );
			this.uiSpinner.replaceWith( this.element );
		},
	
		stepUp: spinner_modifier(function( steps ) {
			this._stepUp( steps );
		}),
		_stepUp: function( steps ) {
			if ( this._start() ) {
				this._spin( (steps || 1) * this.options.step );
				this._stop();
			}
		},
	
		stepDown: spinner_modifier(function( steps ) {
			this._stepDown( steps );
		}),
		_stepDown: function( steps ) {
			if ( this._start() ) {
				this._spin( (steps || 1) * -this.options.step );
				this._stop();
			}
		},
	
		pageUp: spinner_modifier(function( pages ) {
			this._stepUp( (pages || 1) * this.options.page );
		}),
	
		pageDown: spinner_modifier(function( pages ) {
			this._stepDown( (pages || 1) * this.options.page );
		}),
	
		value: function( newVal ) {
			if ( !arguments.length ) {
				return this._parse( this.element.val() );
			}
			spinner_modifier( this._value ).call( this, newVal );
		},
	
		widget: function() {
			return this.uiSpinner;
		}
	});
	
	
	/*!
	 * jQuery UI Tabs 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/tabs/
	 */
	
	
	var tabs = $.widget( "ui.tabs", {
		version: "1.11.4",
		delay: 300,
		options: {
			active: null,
			collapsible: false,
			event: "click",
			heightStyle: "content",
			hide: null,
			show: null,
	
			// callbacks
			activate: null,
			beforeActivate: null,
			beforeLoad: null,
			load: null
		},
	
		_isLocal: (function() {
			var rhash = /#.*$/;
	
			return function( anchor ) {
				var anchorUrl, locationUrl;
	
				// support: IE7
				// IE7 doesn't normalize the href property when set via script (#9317)
				anchor = anchor.cloneNode( false );
	
				anchorUrl = anchor.href.replace( rhash, "" );
				locationUrl = location.href.replace( rhash, "" );
	
				// decoding may throw an error if the URL isn't UTF-8 (#9518)
				try {
					anchorUrl = decodeURIComponent( anchorUrl );
				} catch ( error ) {}
				try {
					locationUrl = decodeURIComponent( locationUrl );
				} catch ( error ) {}
	
				return anchor.hash.length > 1 && anchorUrl === locationUrl;
			};
		})(),
	
		_create: function() {
			var that = this,
				options = this.options;
	
			this.running = false;
	
			this.element
				.addClass( "ui-tabs ui-widget ui-widget-content ui-corner-all" )
				.toggleClass( "ui-tabs-collapsible", options.collapsible );
	
			this._processTabs();
			options.active = this._initialActive();
	
			// Take disabling tabs via class attribute from HTML
			// into account and update option properly.
			if ( $.isArray( options.disabled ) ) {
				options.disabled = $.unique( options.disabled.concat(
					$.map( this.tabs.filter( ".ui-state-disabled" ), function( li ) {
						return that.tabs.index( li );
					})
				) ).sort();
			}
	
			// check for length avoids error when initializing empty list
			if ( this.options.active !== false && this.anchors.length ) {
				this.active = this._findActive( options.active );
			} else {
				this.active = $();
			}
	
			this._refresh();
	
			if ( this.active.length ) {
				this.load( options.active );
			}
		},
	
		_initialActive: function() {
			var active = this.options.active,
				collapsible = this.options.collapsible,
				locationHash = location.hash.substring( 1 );
	
			if ( active === null ) {
				// check the fragment identifier in the URL
				if ( locationHash ) {
					this.tabs.each(function( i, tab ) {
						if ( $( tab ).attr( "aria-controls" ) === locationHash ) {
							active = i;
							return false;
						}
					});
				}
	
				// check for a tab marked active via a class
				if ( active === null ) {
					active = this.tabs.index( this.tabs.filter( ".ui-tabs-active" ) );
				}
	
				// no active tab, set to false
				if ( active === null || active === -1 ) {
					active = this.tabs.length ? 0 : false;
				}
			}
	
			// handle numbers: negative, out of range
			if ( active !== false ) {
				active = this.tabs.index( this.tabs.eq( active ) );
				if ( active === -1 ) {
					active = collapsible ? false : 0;
				}
			}
	
			// don't allow collapsible: false and active: false
			if ( !collapsible && active === false && this.anchors.length ) {
				active = 0;
			}
	
			return active;
		},
	
		_getCreateEventData: function() {
			return {
				tab: this.active,
				panel: !this.active.length ? $() : this._getPanelForTab( this.active )
			};
		},
	
		_tabKeydown: function( event ) {
			var focusedTab = $( this.document[0].activeElement ).closest( "li" ),
				selectedIndex = this.tabs.index( focusedTab ),
				goingForward = true;
	
			if ( this._handlePageNav( event ) ) {
				return;
			}
	
			switch ( event.keyCode ) {
				case $.ui.keyCode.RIGHT:
				case $.ui.keyCode.DOWN:
					selectedIndex++;
					break;
				case $.ui.keyCode.UP:
				case $.ui.keyCode.LEFT:
					goingForward = false;
					selectedIndex--;
					break;
				case $.ui.keyCode.END:
					selectedIndex = this.anchors.length - 1;
					break;
				case $.ui.keyCode.HOME:
					selectedIndex = 0;
					break;
				case $.ui.keyCode.SPACE:
					// Activate only, no collapsing
					event.preventDefault();
					clearTimeout( this.activating );
					this._activate( selectedIndex );
					return;
				case $.ui.keyCode.ENTER:
					// Toggle (cancel delayed activation, allow collapsing)
					event.preventDefault();
					clearTimeout( this.activating );
					// Determine if we should collapse or activate
					this._activate( selectedIndex === this.options.active ? false : selectedIndex );
					return;
				default:
					return;
			}
	
			// Focus the appropriate tab, based on which key was pressed
			event.preventDefault();
			clearTimeout( this.activating );
			selectedIndex = this._focusNextTab( selectedIndex, goingForward );
	
			// Navigating with control/command key will prevent automatic activation
			if ( !event.ctrlKey && !event.metaKey ) {
	
				// Update aria-selected immediately so that AT think the tab is already selected.
				// Otherwise AT may confuse the user by stating that they need to activate the tab,
				// but the tab will already be activated by the time the announcement finishes.
				focusedTab.attr( "aria-selected", "false" );
				this.tabs.eq( selectedIndex ).attr( "aria-selected", "true" );
	
				this.activating = this._delay(function() {
					this.option( "active", selectedIndex );
				}, this.delay );
			}
		},
	
		_panelKeydown: function( event ) {
			if ( this._handlePageNav( event ) ) {
				return;
			}
	
			// Ctrl+up moves focus to the current tab
			if ( event.ctrlKey && event.keyCode === $.ui.keyCode.UP ) {
				event.preventDefault();
				this.active.focus();
			}
		},
	
		// Alt+page up/down moves focus to the previous/next tab (and activates)
		_handlePageNav: function( event ) {
			if ( event.altKey && event.keyCode === $.ui.keyCode.PAGE_UP ) {
				this._activate( this._focusNextTab( this.options.active - 1, false ) );
				return true;
			}
			if ( event.altKey && event.keyCode === $.ui.keyCode.PAGE_DOWN ) {
				this._activate( this._focusNextTab( this.options.active + 1, true ) );
				return true;
			}
		},
	
		_findNextTab: function( index, goingForward ) {
			var lastTabIndex = this.tabs.length - 1;
	
			function constrain() {
				if ( index > lastTabIndex ) {
					index = 0;
				}
				if ( index < 0 ) {
					index = lastTabIndex;
				}
				return index;
			}
	
			while ( $.inArray( constrain(), this.options.disabled ) !== -1 ) {
				index = goingForward ? index + 1 : index - 1;
			}
	
			return index;
		},
	
		_focusNextTab: function( index, goingForward ) {
			index = this._findNextTab( index, goingForward );
			this.tabs.eq( index ).focus();
			return index;
		},
	
		_setOption: function( key, value ) {
			if ( key === "active" ) {
				// _activate() will handle invalid values and update this.options
				this._activate( value );
				return;
			}
	
			if ( key === "disabled" ) {
				// don't use the widget factory's disabled handling
				this._setupDisabled( value );
				return;
			}
	
			this._super( key, value);
	
			if ( key === "collapsible" ) {
				this.element.toggleClass( "ui-tabs-collapsible", value );
				// Setting collapsible: false while collapsed; open first panel
				if ( !value && this.options.active === false ) {
					this._activate( 0 );
				}
			}
	
			if ( key === "event" ) {
				this._setupEvents( value );
			}
	
			if ( key === "heightStyle" ) {
				this._setupHeightStyle( value );
			}
		},
	
		_sanitizeSelector: function( hash ) {
			return hash ? hash.replace( /[!"$%&'()*+,.\/:;<=>?@\[\]\^`{|}~]/g, "\\$&" ) : "";
		},
	
		refresh: function() {
			var options = this.options,
				lis = this.tablist.children( ":has(a[href])" );
	
			// get disabled tabs from class attribute from HTML
			// this will get converted to a boolean if needed in _refresh()
			options.disabled = $.map( lis.filter( ".ui-state-disabled" ), function( tab ) {
				return lis.index( tab );
			});
	
			this._processTabs();
	
			// was collapsed or no tabs
			if ( options.active === false || !this.anchors.length ) {
				options.active = false;
				this.active = $();
			// was active, but active tab is gone
			} else if ( this.active.length && !$.contains( this.tablist[ 0 ], this.active[ 0 ] ) ) {
				// all remaining tabs are disabled
				if ( this.tabs.length === options.disabled.length ) {
					options.active = false;
					this.active = $();
				// activate previous tab
				} else {
					this._activate( this._findNextTab( Math.max( 0, options.active - 1 ), false ) );
				}
			// was active, active tab still exists
			} else {
				// make sure active index is correct
				options.active = this.tabs.index( this.active );
			}
	
			this._refresh();
		},
	
		_refresh: function() {
			this._setupDisabled( this.options.disabled );
			this._setupEvents( this.options.event );
			this._setupHeightStyle( this.options.heightStyle );
	
			this.tabs.not( this.active ).attr({
				"aria-selected": "false",
				"aria-expanded": "false",
				tabIndex: -1
			});
			this.panels.not( this._getPanelForTab( this.active ) )
				.hide()
				.attr({
					"aria-hidden": "true"
				});
	
			// Make sure one tab is in the tab order
			if ( !this.active.length ) {
				this.tabs.eq( 0 ).attr( "tabIndex", 0 );
			} else {
				this.active
					.addClass( "ui-tabs-active ui-state-active" )
					.attr({
						"aria-selected": "true",
						"aria-expanded": "true",
						tabIndex: 0
					});
				this._getPanelForTab( this.active )
					.show()
					.attr({
						"aria-hidden": "false"
					});
			}
		},
	
		_processTabs: function() {
			var that = this,
				prevTabs = this.tabs,
				prevAnchors = this.anchors,
				prevPanels = this.panels;
	
			this.tablist = this._getList()
				.addClass( "ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" )
				.attr( "role", "tablist" )
	
				// Prevent users from focusing disabled tabs via click
				.delegate( "> li", "mousedown" + this.eventNamespace, function( event ) {
					if ( $( this ).is( ".ui-state-disabled" ) ) {
						event.preventDefault();
					}
				})
	
				// support: IE <9
				// Preventing the default action in mousedown doesn't prevent IE
				// from focusing the element, so if the anchor gets focused, blur.
				// We don't have to worry about focusing the previously focused
				// element since clicking on a non-focusable element should focus
				// the body anyway.
				.delegate( ".ui-tabs-anchor", "focus" + this.eventNamespace, function() {
					if ( $( this ).closest( "li" ).is( ".ui-state-disabled" ) ) {
						this.blur();
					}
				});
	
			this.tabs = this.tablist.find( "> li:has(a[href])" )
				.addClass( "ui-state-default ui-corner-top" )
				.attr({
					role: "tab",
					tabIndex: -1
				});
	
			this.anchors = this.tabs.map(function() {
					return $( "a", this )[ 0 ];
				})
				.addClass( "ui-tabs-anchor" )
				.attr({
					role: "presentation",
					tabIndex: -1
				});
	
			this.panels = $();
	
			this.anchors.each(function( i, anchor ) {
				var selector, panel, panelId,
					anchorId = $( anchor ).uniqueId().attr( "id" ),
					tab = $( anchor ).closest( "li" ),
					originalAriaControls = tab.attr( "aria-controls" );
	
				// inline tab
				if ( that._isLocal( anchor ) ) {
					selector = anchor.hash;
					panelId = selector.substring( 1 );
					panel = that.element.find( that._sanitizeSelector( selector ) );
				// remote tab
				} else {
					// If the tab doesn't already have aria-controls,
					// generate an id by using a throw-away element
					panelId = tab.attr( "aria-controls" ) || $( {} ).uniqueId()[ 0 ].id;
					selector = "#" + panelId;
					panel = that.element.find( selector );
					if ( !panel.length ) {
						panel = that._createPanel( panelId );
						panel.insertAfter( that.panels[ i - 1 ] || that.tablist );
					}
					panel.attr( "aria-live", "polite" );
				}
	
				if ( panel.length) {
					that.panels = that.panels.add( panel );
				}
				if ( originalAriaControls ) {
					tab.data( "ui-tabs-aria-controls", originalAriaControls );
				}
				tab.attr({
					"aria-controls": panelId,
					"aria-labelledby": anchorId
				});
				panel.attr( "aria-labelledby", anchorId );
			});
	
			this.panels
				.addClass( "ui-tabs-panel ui-widget-content ui-corner-bottom" )
				.attr( "role", "tabpanel" );
	
			// Avoid memory leaks (#10056)
			if ( prevTabs ) {
				this._off( prevTabs.not( this.tabs ) );
				this._off( prevAnchors.not( this.anchors ) );
				this._off( prevPanels.not( this.panels ) );
			}
		},
	
		// allow overriding how to find the list for rare usage scenarios (#7715)
		_getList: function() {
			return this.tablist || this.element.find( "ol,ul" ).eq( 0 );
		},
	
		_createPanel: function( id ) {
			return $( "<div>" )
				.attr( "id", id )
				.addClass( "ui-tabs-panel ui-widget-content ui-corner-bottom" )
				.data( "ui-tabs-destroy", true );
		},
	
		_setupDisabled: function( disabled ) {
			if ( $.isArray( disabled ) ) {
				if ( !disabled.length ) {
					disabled = false;
				} else if ( disabled.length === this.anchors.length ) {
					disabled = true;
				}
			}
	
			// disable tabs
			for ( var i = 0, li; ( li = this.tabs[ i ] ); i++ ) {
				if ( disabled === true || $.inArray( i, disabled ) !== -1 ) {
					$( li )
						.addClass( "ui-state-disabled" )
						.attr( "aria-disabled", "true" );
				} else {
					$( li )
						.removeClass( "ui-state-disabled" )
						.removeAttr( "aria-disabled" );
				}
			}
	
			this.options.disabled = disabled;
		},
	
		_setupEvents: function( event ) {
			var events = {};
			if ( event ) {
				$.each( event.split(" "), function( index, eventName ) {
					events[ eventName ] = "_eventHandler";
				});
			}
	
			this._off( this.anchors.add( this.tabs ).add( this.panels ) );
			// Always prevent the default action, even when disabled
			this._on( true, this.anchors, {
				click: function( event ) {
					event.preventDefault();
				}
			});
			this._on( this.anchors, events );
			this._on( this.tabs, { keydown: "_tabKeydown" } );
			this._on( this.panels, { keydown: "_panelKeydown" } );
	
			this._focusable( this.tabs );
			this._hoverable( this.tabs );
		},
	
		_setupHeightStyle: function( heightStyle ) {
			var maxHeight,
				parent = this.element.parent();
	
			if ( heightStyle === "fill" ) {
				maxHeight = parent.height();
				maxHeight -= this.element.outerHeight() - this.element.height();
	
				this.element.siblings( ":visible" ).each(function() {
					var elem = $( this ),
						position = elem.css( "position" );
	
					if ( position === "absolute" || position === "fixed" ) {
						return;
					}
					maxHeight -= elem.outerHeight( true );
				});
	
				this.element.children().not( this.panels ).each(function() {
					maxHeight -= $( this ).outerHeight( true );
				});
	
				this.panels.each(function() {
					$( this ).height( Math.max( 0, maxHeight -
						$( this ).innerHeight() + $( this ).height() ) );
				})
				.css( "overflow", "auto" );
			} else if ( heightStyle === "auto" ) {
				maxHeight = 0;
				this.panels.each(function() {
					maxHeight = Math.max( maxHeight, $( this ).height( "" ).height() );
				}).height( maxHeight );
			}
		},
	
		_eventHandler: function( event ) {
			var options = this.options,
				active = this.active,
				anchor = $( event.currentTarget ),
				tab = anchor.closest( "li" ),
				clickedIsActive = tab[ 0 ] === active[ 0 ],
				collapsing = clickedIsActive && options.collapsible,
				toShow = collapsing ? $() : this._getPanelForTab( tab ),
				toHide = !active.length ? $() : this._getPanelForTab( active ),
				eventData = {
					oldTab: active,
					oldPanel: toHide,
					newTab: collapsing ? $() : tab,
					newPanel: toShow
				};
	
			event.preventDefault();
	
			if ( tab.hasClass( "ui-state-disabled" ) ||
					// tab is already loading
					tab.hasClass( "ui-tabs-loading" ) ||
					// can't switch durning an animation
					this.running ||
					// click on active header, but not collapsible
					( clickedIsActive && !options.collapsible ) ||
					// allow canceling activation
					( this._trigger( "beforeActivate", event, eventData ) === false ) ) {
				return;
			}
	
			options.active = collapsing ? false : this.tabs.index( tab );
	
			this.active = clickedIsActive ? $() : tab;
			if ( this.xhr ) {
				this.xhr.abort();
			}
	
			if ( !toHide.length && !toShow.length ) {
				$.error( "jQuery UI Tabs: Mismatching fragment identifier." );
			}
	
			if ( toShow.length ) {
				this.load( this.tabs.index( tab ), event );
			}
			this._toggle( event, eventData );
		},
	
		// handles show/hide for selecting tabs
		_toggle: function( event, eventData ) {
			var that = this,
				toShow = eventData.newPanel,
				toHide = eventData.oldPanel;
	
			this.running = true;
	
			function complete() {
				that.running = false;
				that._trigger( "activate", event, eventData );
			}
	
			function show() {
				eventData.newTab.closest( "li" ).addClass( "ui-tabs-active ui-state-active" );
	
				if ( toShow.length && that.options.show ) {
					that._show( toShow, that.options.show, complete );
				} else {
					toShow.show();
					complete();
				}
			}
	
			// start out by hiding, then showing, then completing
			if ( toHide.length && this.options.hide ) {
				this._hide( toHide, this.options.hide, function() {
					eventData.oldTab.closest( "li" ).removeClass( "ui-tabs-active ui-state-active" );
					show();
				});
			} else {
				eventData.oldTab.closest( "li" ).removeClass( "ui-tabs-active ui-state-active" );
				toHide.hide();
				show();
			}
	
			toHide.attr( "aria-hidden", "true" );
			eventData.oldTab.attr({
				"aria-selected": "false",
				"aria-expanded": "false"
			});
			// If we're switching tabs, remove the old tab from the tab order.
			// If we're opening from collapsed state, remove the previous tab from the tab order.
			// If we're collapsing, then keep the collapsing tab in the tab order.
			if ( toShow.length && toHide.length ) {
				eventData.oldTab.attr( "tabIndex", -1 );
			} else if ( toShow.length ) {
				this.tabs.filter(function() {
					return $( this ).attr( "tabIndex" ) === 0;
				})
				.attr( "tabIndex", -1 );
			}
	
			toShow.attr( "aria-hidden", "false" );
			eventData.newTab.attr({
				"aria-selected": "true",
				"aria-expanded": "true",
				tabIndex: 0
			});
		},
	
		_activate: function( index ) {
			var anchor,
				active = this._findActive( index );
	
			// trying to activate the already active panel
			if ( active[ 0 ] === this.active[ 0 ] ) {
				return;
			}
	
			// trying to collapse, simulate a click on the current active header
			if ( !active.length ) {
				active = this.active;
			}
	
			anchor = active.find( ".ui-tabs-anchor" )[ 0 ];
			this._eventHandler({
				target: anchor,
				currentTarget: anchor,
				preventDefault: $.noop
			});
		},
	
		_findActive: function( index ) {
			return index === false ? $() : this.tabs.eq( index );
		},
	
		_getIndex: function( index ) {
			// meta-function to give users option to provide a href string instead of a numerical index.
			if ( typeof index === "string" ) {
				index = this.anchors.index( this.anchors.filter( "[href$='" + index + "']" ) );
			}
	
			return index;
		},
	
		_destroy: function() {
			if ( this.xhr ) {
				this.xhr.abort();
			}
	
			this.element.removeClass( "ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible" );
	
			this.tablist
				.removeClass( "ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" )
				.removeAttr( "role" );
	
			this.anchors
				.removeClass( "ui-tabs-anchor" )
				.removeAttr( "role" )
				.removeAttr( "tabIndex" )
				.removeUniqueId();
	
			this.tablist.unbind( this.eventNamespace );
	
			this.tabs.add( this.panels ).each(function() {
				if ( $.data( this, "ui-tabs-destroy" ) ) {
					$( this ).remove();
				} else {
					$( this )
						.removeClass( "ui-state-default ui-state-active ui-state-disabled " +
							"ui-corner-top ui-corner-bottom ui-widget-content ui-tabs-active ui-tabs-panel" )
						.removeAttr( "tabIndex" )
						.removeAttr( "aria-live" )
						.removeAttr( "aria-busy" )
						.removeAttr( "aria-selected" )
						.removeAttr( "aria-labelledby" )
						.removeAttr( "aria-hidden" )
						.removeAttr( "aria-expanded" )
						.removeAttr( "role" );
				}
			});
	
			this.tabs.each(function() {
				var li = $( this ),
					prev = li.data( "ui-tabs-aria-controls" );
				if ( prev ) {
					li
						.attr( "aria-controls", prev )
						.removeData( "ui-tabs-aria-controls" );
				} else {
					li.removeAttr( "aria-controls" );
				}
			});
	
			this.panels.show();
	
			if ( this.options.heightStyle !== "content" ) {
				this.panels.css( "height", "" );
			}
		},
	
		enable: function( index ) {
			var disabled = this.options.disabled;
			if ( disabled === false ) {
				return;
			}
	
			if ( index === undefined ) {
				disabled = false;
			} else {
				index = this._getIndex( index );
				if ( $.isArray( disabled ) ) {
					disabled = $.map( disabled, function( num ) {
						return num !== index ? num : null;
					});
				} else {
					disabled = $.map( this.tabs, function( li, num ) {
						return num !== index ? num : null;
					});
				}
			}
			this._setupDisabled( disabled );
		},
	
		disable: function( index ) {
			var disabled = this.options.disabled;
			if ( disabled === true ) {
				return;
			}
	
			if ( index === undefined ) {
				disabled = true;
			} else {
				index = this._getIndex( index );
				if ( $.inArray( index, disabled ) !== -1 ) {
					return;
				}
				if ( $.isArray( disabled ) ) {
					disabled = $.merge( [ index ], disabled ).sort();
				} else {
					disabled = [ index ];
				}
			}
			this._setupDisabled( disabled );
		},
	
		load: function( index, event ) {
			index = this._getIndex( index );
			var that = this,
				tab = this.tabs.eq( index ),
				anchor = tab.find( ".ui-tabs-anchor" ),
				panel = this._getPanelForTab( tab ),
				eventData = {
					tab: tab,
					panel: panel
				},
				complete = function( jqXHR, status ) {
					if ( status === "abort" ) {
						that.panels.stop( false, true );
					}
	
					tab.removeClass( "ui-tabs-loading" );
					panel.removeAttr( "aria-busy" );
	
					if ( jqXHR === that.xhr ) {
						delete that.xhr;
					}
				};
	
			// not remote
			if ( this._isLocal( anchor[ 0 ] ) ) {
				return;
			}
	
			this.xhr = $.ajax( this._ajaxSettings( anchor, event, eventData ) );
	
			// support: jQuery <1.8
			// jQuery <1.8 returns false if the request is canceled in beforeSend,
			// but as of 1.8, $.ajax() always returns a jqXHR object.
			if ( this.xhr && this.xhr.statusText !== "canceled" ) {
				tab.addClass( "ui-tabs-loading" );
				panel.attr( "aria-busy", "true" );
	
				this.xhr
					.done(function( response, status, jqXHR ) {
						// support: jQuery <1.8
						// http://bugs.jquery.com/ticket/11778
						setTimeout(function() {
							panel.html( response );
							that._trigger( "load", event, eventData );
	
							complete( jqXHR, status );
						}, 1 );
					})
					.fail(function( jqXHR, status ) {
						// support: jQuery <1.8
						// http://bugs.jquery.com/ticket/11778
						setTimeout(function() {
							complete( jqXHR, status );
						}, 1 );
					});
			}
		},
	
		_ajaxSettings: function( anchor, event, eventData ) {
			var that = this;
			return {
				url: anchor.attr( "href" ),
				beforeSend: function( jqXHR, settings ) {
					return that._trigger( "beforeLoad", event,
						$.extend( { jqXHR: jqXHR, ajaxSettings: settings }, eventData ) );
				}
			};
		},
	
		_getPanelForTab: function( tab ) {
			var id = $( tab ).attr( "aria-controls" );
			return this.element.find( this._sanitizeSelector( "#" + id ) );
		}
	});
	
	
	/*!
	 * jQuery UI Tooltip 1.11.4
	 * http://jqueryui.com
	 *
	 * Copyright jQuery Foundation and other contributors
	 * Released under the MIT license.
	 * http://jquery.org/license
	 *
	 * http://api.jqueryui.com/tooltip/
	 */
	
	
	var tooltip = $.widget( "ui.tooltip", {
		version: "1.11.4",
		options: {
			content: function() {
				// support: IE<9, Opera in jQuery <1.7
				// .text() can't accept undefined, so coerce to a string
				var title = $( this ).attr( "title" ) || "";
				// Escape title, since we're going from an attribute to raw HTML
				return $( "<a>" ).text( title ).html();
			},
			hide: true,
			// Disabled elements have inconsistent behavior across browsers (#8661)
			items: "[title]:not([disabled])",
			position: {
				my: "left top+15",
				at: "left bottom",
				collision: "flipfit flip"
			},
			show: true,
			tooltipClass: null,
			track: false,
	
			// callbacks
			close: null,
			open: null
		},
	
		_addDescribedBy: function( elem, id ) {
			var describedby = (elem.attr( "aria-describedby" ) || "").split( /\s+/ );
			describedby.push( id );
			elem
				.data( "ui-tooltip-id", id )
				.attr( "aria-describedby", $.trim( describedby.join( " " ) ) );
		},
	
		_removeDescribedBy: function( elem ) {
			var id = elem.data( "ui-tooltip-id" ),
				describedby = (elem.attr( "aria-describedby" ) || "").split( /\s+/ ),
				index = $.inArray( id, describedby );
	
			if ( index !== -1 ) {
				describedby.splice( index, 1 );
			}
	
			elem.removeData( "ui-tooltip-id" );
			describedby = $.trim( describedby.join( " " ) );
			if ( describedby ) {
				elem.attr( "aria-describedby", describedby );
			} else {
				elem.removeAttr( "aria-describedby" );
			}
		},
	
		_create: function() {
			this._on({
				mouseover: "open",
				focusin: "open"
			});
	
			// IDs of generated tooltips, needed for destroy
			this.tooltips = {};
	
			// IDs of parent tooltips where we removed the title attribute
			this.parents = {};
	
			if ( this.options.disabled ) {
				this._disable();
			}
	
			// Append the aria-live region so tooltips announce correctly
			this.liveRegion = $( "<div>" )
				.attr({
					role: "log",
					"aria-live": "assertive",
					"aria-relevant": "additions"
				})
				.addClass( "ui-helper-hidden-accessible" )
				.appendTo( this.document[ 0 ].body );
		},
	
		_setOption: function( key, value ) {
			var that = this;
	
			if ( key === "disabled" ) {
				this[ value ? "_disable" : "_enable" ]();
				this.options[ key ] = value;
				// disable element style changes
				return;
			}
	
			this._super( key, value );
	
			if ( key === "content" ) {
				$.each( this.tooltips, function( id, tooltipData ) {
					that._updateContent( tooltipData.element );
				});
			}
		},
	
		_disable: function() {
			var that = this;
	
			// close open tooltips
			$.each( this.tooltips, function( id, tooltipData ) {
				var event = $.Event( "blur" );
				event.target = event.currentTarget = tooltipData.element[ 0 ];
				that.close( event, true );
			});
	
			// remove title attributes to prevent native tooltips
			this.element.find( this.options.items ).addBack().each(function() {
				var element = $( this );
				if ( element.is( "[title]" ) ) {
					element
						.data( "ui-tooltip-title", element.attr( "title" ) )
						.removeAttr( "title" );
				}
			});
		},
	
		_enable: function() {
			// restore title attributes
			this.element.find( this.options.items ).addBack().each(function() {
				var element = $( this );
				if ( element.data( "ui-tooltip-title" ) ) {
					element.attr( "title", element.data( "ui-tooltip-title" ) );
				}
			});
		},
	
		open: function( event ) {
			var that = this,
				target = $( event ? event.target : this.element )
					// we need closest here due to mouseover bubbling,
					// but always pointing at the same event target
					.closest( this.options.items );
	
			// No element to show a tooltip for or the tooltip is already open
			if ( !target.length || target.data( "ui-tooltip-id" ) ) {
				return;
			}
	
			if ( target.attr( "title" ) ) {
				target.data( "ui-tooltip-title", target.attr( "title" ) );
			}
	
			target.data( "ui-tooltip-open", true );
	
			// kill parent tooltips, custom or native, for hover
			if ( event && event.type === "mouseover" ) {
				target.parents().each(function() {
					var parent = $( this ),
						blurEvent;
					if ( parent.data( "ui-tooltip-open" ) ) {
						blurEvent = $.Event( "blur" );
						blurEvent.target = blurEvent.currentTarget = this;
						that.close( blurEvent, true );
					}
					if ( parent.attr( "title" ) ) {
						parent.uniqueId();
						that.parents[ this.id ] = {
							element: this,
							title: parent.attr( "title" )
						};
						parent.attr( "title", "" );
					}
				});
			}
	
			this._registerCloseHandlers( event, target );
			this._updateContent( target, event );
		},
	
		_updateContent: function( target, event ) {
			var content,
				contentOption = this.options.content,
				that = this,
				eventType = event ? event.type : null;
	
			if ( typeof contentOption === "string" ) {
				return this._open( event, target, contentOption );
			}
	
			content = contentOption.call( target[0], function( response ) {
	
				// IE may instantly serve a cached response for ajax requests
				// delay this call to _open so the other call to _open runs first
				that._delay(function() {
	
					// Ignore async response if tooltip was closed already
					if ( !target.data( "ui-tooltip-open" ) ) {
						return;
					}
	
					// jQuery creates a special event for focusin when it doesn't
					// exist natively. To improve performance, the native event
					// object is reused and the type is changed. Therefore, we can't
					// rely on the type being correct after the event finished
					// bubbling, so we set it back to the previous value. (#8740)
					if ( event ) {
						event.type = eventType;
					}
					this._open( event, target, response );
				});
			});
			if ( content ) {
				this._open( event, target, content );
			}
		},
	
		_open: function( event, target, content ) {
			var tooltipData, tooltip, delayedShow, a11yContent,
				positionOption = $.extend( {}, this.options.position );
	
			if ( !content ) {
				return;
			}
	
			// Content can be updated multiple times. If the tooltip already
			// exists, then just update the content and bail.
			tooltipData = this._find( target );
			if ( tooltipData ) {
				tooltipData.tooltip.find( ".ui-tooltip-content" ).html( content );
				return;
			}
	
			// if we have a title, clear it to prevent the native tooltip
			// we have to check first to avoid defining a title if none exists
			// (we don't want to cause an element to start matching [title])
			//
			// We use removeAttr only for key events, to allow IE to export the correct
			// accessible attributes. For mouse events, set to empty string to avoid
			// native tooltip showing up (happens only when removing inside mouseover).
			if ( target.is( "[title]" ) ) {
				if ( event && event.type === "mouseover" ) {
					target.attr( "title", "" );
				} else {
					target.removeAttr( "title" );
				}
			}
	
			tooltipData = this._tooltip( target );
			tooltip = tooltipData.tooltip;
			this._addDescribedBy( target, tooltip.attr( "id" ) );
			tooltip.find( ".ui-tooltip-content" ).html( content );
	
			// Support: Voiceover on OS X, JAWS on IE <= 9
			// JAWS announces deletions even when aria-relevant="additions"
			// Voiceover will sometimes re-read the entire log region's contents from the beginning
			this.liveRegion.children().hide();
			if ( content.clone ) {
				a11yContent = content.clone();
				a11yContent.removeAttr( "id" ).find( "[id]" ).removeAttr( "id" );
			} else {
				a11yContent = content;
			}
			$( "<div>" ).html( a11yContent ).appendTo( this.liveRegion );
	
			function position( event ) {
				positionOption.of = event;
				if ( tooltip.is( ":hidden" ) ) {
					return;
				}
				tooltip.position( positionOption );
			}
			if ( this.options.track && event && /^mouse/.test( event.type ) ) {
				this._on( this.document, {
					mousemove: position
				});
				// trigger once to override element-relative positioning
				position( event );
			} else {
				tooltip.position( $.extend({
					of: target
				}, this.options.position ) );
			}
	
			tooltip.hide();
	
			this._show( tooltip, this.options.show );
			// Handle tracking tooltips that are shown with a delay (#8644). As soon
			// as the tooltip is visible, position the tooltip using the most recent
			// event.
			if ( this.options.show && this.options.show.delay ) {
				delayedShow = this.delayedShow = setInterval(function() {
					if ( tooltip.is( ":visible" ) ) {
						position( positionOption.of );
						clearInterval( delayedShow );
					}
				}, $.fx.interval );
			}
	
			this._trigger( "open", event, { tooltip: tooltip } );
		},
	
		_registerCloseHandlers: function( event, target ) {
			var events = {
				keyup: function( event ) {
					if ( event.keyCode === $.ui.keyCode.ESCAPE ) {
						var fakeEvent = $.Event(event);
						fakeEvent.currentTarget = target[0];
						this.close( fakeEvent, true );
					}
				}
			};
	
			// Only bind remove handler for delegated targets. Non-delegated
			// tooltips will handle this in destroy.
			if ( target[ 0 ] !== this.element[ 0 ] ) {
				events.remove = function() {
					this._removeTooltip( this._find( target ).tooltip );
				};
			}
	
			if ( !event || event.type === "mouseover" ) {
				events.mouseleave = "close";
			}
			if ( !event || event.type === "focusin" ) {
				events.focusout = "close";
			}
			this._on( true, target, events );
		},
	
		close: function( event ) {
			var tooltip,
				that = this,
				target = $( event ? event.currentTarget : this.element ),
				tooltipData = this._find( target );
	
			// The tooltip may already be closed
			if ( !tooltipData ) {
	
				// We set ui-tooltip-open immediately upon open (in open()), but only set the
				// additional data once there's actually content to show (in _open()). So even if the
				// tooltip doesn't have full data, we always remove ui-tooltip-open in case we're in
				// the period between open() and _open().
				target.removeData( "ui-tooltip-open" );
				return;
			}
	
			tooltip = tooltipData.tooltip;
	
			// disabling closes the tooltip, so we need to track when we're closing
			// to avoid an infinite loop in case the tooltip becomes disabled on close
			if ( tooltipData.closing ) {
				return;
			}
	
			// Clear the interval for delayed tracking tooltips
			clearInterval( this.delayedShow );
	
			// only set title if we had one before (see comment in _open())
			// If the title attribute has changed since open(), don't restore
			if ( target.data( "ui-tooltip-title" ) && !target.attr( "title" ) ) {
				target.attr( "title", target.data( "ui-tooltip-title" ) );
			}
	
			this._removeDescribedBy( target );
	
			tooltipData.hiding = true;
			tooltip.stop( true );
			this._hide( tooltip, this.options.hide, function() {
				that._removeTooltip( $( this ) );
			});
	
			target.removeData( "ui-tooltip-open" );
			this._off( target, "mouseleave focusout keyup" );
	
			// Remove 'remove' binding only on delegated targets
			if ( target[ 0 ] !== this.element[ 0 ] ) {
				this._off( target, "remove" );
			}
			this._off( this.document, "mousemove" );
	
			if ( event && event.type === "mouseleave" ) {
				$.each( this.parents, function( id, parent ) {
					$( parent.element ).attr( "title", parent.title );
					delete that.parents[ id ];
				});
			}
	
			tooltipData.closing = true;
			this._trigger( "close", event, { tooltip: tooltip } );
			if ( !tooltipData.hiding ) {
				tooltipData.closing = false;
			}
		},
	
		_tooltip: function( element ) {
			var tooltip = $( "<div>" )
					.attr( "role", "tooltip" )
					.addClass( "ui-tooltip ui-widget ui-corner-all ui-widget-content " +
						( this.options.tooltipClass || "" ) ),
				id = tooltip.uniqueId().attr( "id" );
	
			$( "<div>" )
				.addClass( "ui-tooltip-content" )
				.appendTo( tooltip );
	
			tooltip.appendTo( this.document[0].body );
	
			return this.tooltips[ id ] = {
				element: element,
				tooltip: tooltip
			};
		},
	
		_find: function( target ) {
			var id = target.data( "ui-tooltip-id" );
			return id ? this.tooltips[ id ] : null;
		},
	
		_removeTooltip: function( tooltip ) {
			tooltip.remove();
			delete this.tooltips[ tooltip.attr( "id" ) ];
		},
	
		_destroy: function() {
			var that = this;
	
			// close open tooltips
			$.each( this.tooltips, function( id, tooltipData ) {
				// Delegate to close method to handle common cleanup
				var event = $.Event( "blur" ),
					element = tooltipData.element;
				event.target = event.currentTarget = element[ 0 ];
				that.close( event, true );
	
				// Remove immediately; destroying an open tooltip doesn't use the
				// hide animation
				$( "#" + id ).remove();
	
				// Restore the title
				if ( element.data( "ui-tooltip-title" ) ) {
					// If the title attribute has changed since open(), don't restore
					if ( !element.attr( "title" ) ) {
						element.attr( "title", element.data( "ui-tooltip-title" ) );
					}
					element.removeData( "ui-tooltip-title" );
				}
			});
			this.liveRegion.remove();
		}
	});
	
	
	
	}));

/***/ },

/***/ 2785:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/index.js ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	exports.LegendDifferential = __webpack_require__(/*! ./src/LegendDifferential.jsx */ 2786);
	exports.LegendBaseline = __webpack_require__(/*! ./src/LegendBaseline.jsx */ 2798);

/***/ },

/***/ 2786:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendDifferential.jsx ***!
  \****************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 2);
	var ReactDOM = __webpack_require__(/*! react-dom */ 35);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 2787);
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 2792);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 2796);
	
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

/***/ 2787:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendRow.jsx ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 2);
	
	__webpack_require__(/*! ./gxaGradient.css */ 2788);
	
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

/***/ 2788:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../css-loader!./gxaGradient.css */ 2789);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../style-loader/addStyles.js */ 2791)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../css-loader/index.js!./gxaGradient.css", function() {
				var newContent = require("!!../../css-loader/index.js!./gxaGradient.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2789:
/*!**************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaGradient.css ***!
  \**************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../css-loader/lib/css-base.js */ 2790)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaGradientColour {\n    overflow: auto;\n    vertical-align: middle;\n    width: 200px;\n    height: 15px;\n    margin: 2px 6px 2px 6px;\n    display: inline-block;\n}\n\n.gxaGradientLevel {\n    white-space: nowrap;\n    font-size: 10px;\n    vertical-align: middle;\n    display: table-cell;\n}\n\n.gxaGradientLevelMin {\n    text-align: right;\n}\n\n.gxaGradientLevelMax {\n    text-align: left;\n}", ""]);
	
	// exports


/***/ },

/***/ 2790:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader/lib/css-base.js ***!
  \****************************************************************************/
467,

/***/ 2791:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader/addStyles.js ***!
  \***************************************************************************/
468,

/***/ 2792:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-help-tooltips/index.js ***!
  \*****************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/helpTooltipsModule.js */ 2793);

/***/ },

/***/ 2793:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-help-tooltips/src/helpTooltipsModule.js ***!
  \**********************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 2773);
	__webpack_require__(/*! jquery-ui-bundle */ 2784);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaHelpTooltip.css */ 2794);
	
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

/***/ 2794:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \*******************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../css-loader!./gxaHelpTooltip.css */ 2795);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../style-loader/addStyles.js */ 2791)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../css-loader/index.js!./gxaHelpTooltip.css", function() {
				var newContent = require("!!../../css-loader/index.js!./gxaHelpTooltip.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2795:
/*!************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-help-tooltips/src/gxaHelpTooltip.css ***!
  \************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../css-loader/lib/css-base.js */ 2790)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaHelpTooltip {\n    width: 33% !important;\n    left: 700px;\n    font-size: 14px !important;\n    background: white;\n    border-width: 1px !important;\n    border: solid cornflowerblue;\n    padding: 4px;\n    color: cornflowerblue;\n    /*font: 10px Verdana, Helvetica, Arial, sans-serif;*/\n}\n\na.help-icon {\n    color: darkorange;\n    vertical-align: top;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    font-weight: bold;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 2796:
/*!*******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \*******************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../css-loader!./gxaLegend.css */ 2797);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../style-loader/addStyles.js */ 2791)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../css-loader/index.js!./gxaLegend.css", function() {
				var newContent = require("!!../../css-loader/index.js!./gxaLegend.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2797:
/*!************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-legend/src/gxaLegend.css ***!
  \************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../css-loader/lib/css-base.js */ 2790)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaLegendHelp {\n    display: inline-block;\n    vertical-align: top;\n    padding-left: 2px;\n}\n\n.gxaLegend {\n    display: inline-block;\n    padding-left: 20px;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 2798:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/src/LegendBaseline.jsx ***!
  \************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 2);
	var ReactDOM = __webpack_require__(/*! react-dom */ 35);
	
	//*------------------------------------------------------------------*
	
	var LegendRow = __webpack_require__(/*! ./LegendRow.jsx */ 2787);
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 2799).default;
	var HelpTooltips = __webpack_require__(/*! expression-atlas-help-tooltips */ 2792);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaLegend.css */ 2796);
	
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

/***/ 2799:
/*!*******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/index.js ***!
  \*******************************************************************************************************************/
[3900, 2800],

/***/ 2800:
/*!*******************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-legend/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \*******************************************************************************************************************************/
764,

/***/ 2801:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/CellDifferential.jsx */ 2802);

/***/ },

/***/ 2802:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/CellDifferential.jsx ***!
  \*************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 2);
	var ReactDOM = __webpack_require__(/*! react-dom */ 35);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 758);
	var $ = __webpack_require__(/*! jquery */ 2773);
	__webpack_require__(/*! jquery-ui-bundle */ 2784);
	
	var NumberFormat = __webpack_require__(/*! expression-atlas-number-format */ 2803).default;
	
	__webpack_require__(/*! ./gxaShowHideCell.css */ 2805);
	__webpack_require__(/*! ./gxaDifferentialCellTooltip.css */ 2807);
	
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

/***/ 2803:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-number-format/index.js ***!
  \*****************************************************************************************/
[3900, 2804],

/***/ 2804:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-number-format/src/NumberFormat.jsx ***!
  \*****************************************************************************************************/
764,

/***/ 2805:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../css-loader!./gxaShowHideCell.css */ 2806);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../style-loader/addStyles.js */ 2791)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../css-loader/index.js!./gxaShowHideCell.css", function() {
				var newContent = require("!!../../css-loader/index.js!./gxaShowHideCell.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2806:
/*!*****************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaShowHideCell.css ***!
  \*****************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../css-loader/lib/css-base.js */ 2790)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaShowCell {\n    background-color: white;\n    white-space: nowrap;\n    text-align: center;\n    margin: 4px;\n    padding: 2px;\n}\n\n.gxaHideCell {\n    display: none;\n    visibility: hidden;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 2807:
/*!***********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \***********************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../css-loader!./gxaDifferentialCellTooltip.css */ 2808);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../style-loader/addStyles.js */ 2791)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../css-loader/index.js!./gxaDifferentialCellTooltip.css", function() {
				var newContent = require("!!../../css-loader/index.js!./gxaDifferentialCellTooltip.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2808:
/*!****************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-cell-differential/src/gxaDifferentialCellTooltip.css ***!
  \****************************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../css-loader/lib/css-base.js */ 2790)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaDifferentialCellTooltip {\n    width: 26%;\n    left: 300px;\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\n.gxaDifferentialCellTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaDifferentialCellTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaDifferentialCellTooltip td, .gxaDifferentialCellTooltip th {\n    text-align: center;\n    white-space: nowrap;\n    vertical-align: middle;\n    padding: 8px;\n    width: 25px;\n}\n.gxaDifferentialCellTooltip thead {\n    font-size: 0.9em;\n}\n\n.gxaDifferentialCellTooltip tbody {\n    font-size: smaller;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 2809:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.jsx ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };
	
	var $ = __webpack_require__(/*! jquery */ 2773);
	__webpack_require__(/*! jquery-ui-bundle */ 2784);
	//TODO: make this button consistently styled, using Bootstrap or Foundation
	//remove this dependency on jquery
	
	var React = __webpack_require__(/*! react */ 2);
	var ReactDOM = __webpack_require__(/*! react-dom */ 35);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialDownloadButton.css */ 2810);
	
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
	
	        // let downloadImgSrcURL = this.props.hostUrl + '/gxa/resources/images/download_blue_small.png';
	
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
	                    }, className: 'button large gxaDownloadButton',
	                    href: uri, download: fileName, target: '_blank',
	                    onClick: this._downloadDifferentialProfiles },
	                React.createElement('span', { className: 'icon icon-functional', 'data-icon': '=' })
	            )
	        );
	    },
	    componentDidMount: function componentDidMount() {
	        var $downloadProfilesLink = $(ReactDOM.findDOMNode(this._downloadProfilesLinkRef));
	        $downloadProfilesLink.tooltip();
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = DownloadDifferentialButton;

/***/ },

/***/ 2810:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../~/css-loader!./DifferentialDownloadButton.css */ 2811);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../~/style-loader/addStyles.js */ 2791)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../node_modules/css-loader/index.js!./DifferentialDownloadButton.css", function() {
				var newContent = require("!!../node_modules/css-loader/index.js!./DifferentialDownloadButton.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2811:
/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialDownloadButton.css ***!
  \***************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../~/css-loader/lib/css-base.js */ 2790)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaNoTextButton {\n    border: 1px solid #ccc !important; /* overrides ebi-visual.css */\n}\n\n.gxaNoTextButton .ui-button-text {\n    padding: 2px;\n}\n\n.gxaDownloadButton {\n    background-color: white;\n}\n\n.button:hover, .button:focus, .button:active {\n    background-color: transparent;\n}\n\n.button {\n    padding-left: 1em;\n    padding-top:0;\n    padding-bottom:0;\n    margin:0;\n}\n\nhtml.fontface a.gxaDownloadButton.button .icon-functional:before {\n    color: #454545;\n}\n\na.gxaDownloadButton.button:hover, a.gxaDownloadButton.button:active, a.gxaDownloadButton.button:focus{\n    border-bottom: transparent 1px solid;\n}", ""]);
	
	// exports


/***/ },

/***/ 2812:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/index.js ***!
  \*********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/contrastTooltipModule.js */ 2813);

/***/ },

/***/ 2813:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/contrastTooltipModule.js ***!
  \*****************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 2);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 758);
	
	var $ = __webpack_require__(/*! jquery */ 2773);
	__webpack_require__(/*! jquery-ui-bundle */ 2784);
	
	//*------------------------------------------------------------------*
	
	var ContrastTooltip = __webpack_require__(/*! ./ContrastTooltip.jsx */ 2814);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./gxaContrastTooltip.css */ 2815);
	
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

/***/ 2814:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/ContrastTooltip.jsx ***!
  \************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	var React = __webpack_require__(/*! react */ 2);
	
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

/***/ 2815:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \***************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../../css-loader!./gxaContrastTooltip.css */ 2816);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../../style-loader/addStyles.js */ 2791)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../../css-loader/index.js!./gxaContrastTooltip.css", function() {
				var newContent = require("!!../../css-loader/index.js!./gxaContrastTooltip.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2816:
/*!********************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-contrast-tooltips/src/gxaContrastTooltip.css ***!
  \********************************************************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../../css-loader/lib/css-base.js */ 2790)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaContrastTooltip {\n    position: relative;\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    max-width: 500px;\n}\n\n.gxaContrastTooltip table {\n    margin: 0; /* overrides ebi-visual.css:134 */\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n    font-size: 0.8em;\n}\n\n.gxaContrastTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaContrastTooltip td {\n    border: 1px solid lightgrey;\n}\n\n.gxaContrastTooltip td, .gxaContrastTooltip th {\n    vertical-align: middle;\n    padding: 8px;\n}\n\n#contrastExperimentDescription {\n    font-size: small;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 2817:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/index.js ***!
  \************************************************************************************/
[3963, 2818],

/***/ 2818:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/Feedback.jsx ***!
  \********************************************************************************************/
[3964, 2819, 2821, 2822, 2823, 2922, 2924, 2929, 2930, 2939],

/***/ 2819:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-localstorage/react-localstorage.js ***!
  \******************************************************************************************/
[3965, 2820],

/***/ 2820:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-localstorage/lib/warning.js ***!
  \***********************************************************************************/
859,

/***/ 2821:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-timer-mixin/TimerMixin.js ***!
  \*********************************************************************************/
860,

/***/ 2822:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-addons-css-transition-group/index.js ***!
  \********************************************************************************************/
861,

/***/ 2823:
/*!*******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/Button.js ***!
  \*******************************************************************************/
[3813, 2824, 2859, 2860, 2867, 2868, 2904, 2912, 2913, 2915, 2920, 2921],

/***/ 2824:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/core-js/object/values.js ***!
  \****************************************************************************************/
[3814, 2825],

/***/ 2825:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/fn/object/values.js ***!
  \*****************************************************************************************************/
[3815, 2826, 2829],

/***/ 2826:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/es7.object.values.js ***!
  \**************************************************************************************************************/
[3816, 2827, 2842],

/***/ 2827:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_export.js ***!
  \****************************************************************************************************/
[3749, 2828, 2829, 2830, 2832],

/***/ 2828:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_global.js ***!
  \****************************************************************************************************/
486,

/***/ 2829:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_core.js ***!
  \**************************************************************************************************/
487,

/***/ 2830:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_ctx.js ***!
  \*************************************************************************************************/
[3750, 2831],

/***/ 2831:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_a-function.js ***!
  \********************************************************************************************************/
489,

/***/ 2832:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_hide.js ***!
  \**************************************************************************************************/
[3751, 2833, 2841, 2837],

/***/ 2833:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-dp.js ***!
  \*******************************************************************************************************/
[3752, 2834, 2836, 2840, 2837],

/***/ 2834:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_an-object.js ***!
  \*******************************************************************************************************/
[3753, 2835],

/***/ 2835:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_is-object.js ***!
  \*******************************************************************************************************/
493,

/***/ 2836:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_ie8-dom-define.js ***!
  \************************************************************************************************************/
[3754, 2837, 2838, 2839],

/***/ 2837:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_descriptors.js ***!
  \*********************************************************************************************************/
[3755, 2838],

/***/ 2838:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_fails.js ***!
  \***************************************************************************************************/
496,

/***/ 2839:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_dom-create.js ***!
  \********************************************************************************************************/
[3756, 2835, 2828],

/***/ 2840:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_to-primitive.js ***!
  \**********************************************************************************************************/
[3757, 2835],

/***/ 2841:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_property-desc.js ***!
  \***********************************************************************************************************/
499,

/***/ 2842:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-to-array.js ***!
  \*************************************************************************************************************/
[3817, 2843, 2846, 2858],

/***/ 2843:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-keys.js ***!
  \*********************************************************************************************************/
[3759, 2844, 2857],

/***/ 2844:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-keys-internal.js ***!
  \******************************************************************************************************************/
[3760, 2845, 2846, 2850, 2854],

/***/ 2845:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_has.js ***!
  \*************************************************************************************************/
503,

/***/ 2846:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_to-iobject.js ***!
  \********************************************************************************************************/
[3761, 2847, 2849],

/***/ 2847:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_iobject.js ***!
  \*****************************************************************************************************/
[3762, 2848],

/***/ 2848:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_cof.js ***!
  \*************************************************************************************************/
506,

/***/ 2849:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_defined.js ***!
  \*****************************************************************************************************/
507,

/***/ 2850:
/*!************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_array-includes.js ***!
  \************************************************************************************************************/
[3763, 2846, 2851, 2853],

/***/ 2851:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_to-length.js ***!
  \*******************************************************************************************************/
[3764, 2852],

/***/ 2852:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_to-integer.js ***!
  \********************************************************************************************************/
510,

/***/ 2853:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_to-index.js ***!
  \******************************************************************************************************/
[3765, 2852],

/***/ 2854:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_shared-key.js ***!
  \********************************************************************************************************/
[3766, 2855, 2856],

/***/ 2855:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_shared.js ***!
  \****************************************************************************************************/
[3767, 2828],

/***/ 2856:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_uid.js ***!
  \*************************************************************************************************/
514,

/***/ 2857:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_enum-bug-keys.js ***!
  \***********************************************************************************************************/
515,

/***/ 2858:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-pie.js ***!
  \********************************************************************************************************/
517,

/***/ 2859:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/helpers/objectWithoutProperties.js ***!
  \**************************************************************************************************/
480,

/***/ 2860:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/helpers/extends.js ***!
  \**********************************************************************************/
[3745, 2861],

/***/ 2861:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/core-js/object/assign.js ***!
  \****************************************************************************************/
[3746, 2862],

/***/ 2862:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/fn/object/assign.js ***!
  \*****************************************************************************************************/
[3747, 2863, 2829],

/***/ 2863:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/es6.object.assign.js ***!
  \**************************************************************************************************************/
[3748, 2827, 2864],

/***/ 2864:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-assign.js ***!
  \***********************************************************************************************************/
[3758, 2843, 2865, 2858, 2866, 2847, 2838],

/***/ 2865:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-gops.js ***!
  \*********************************************************************************************************/
516,

/***/ 2866:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_to-object.js ***!
  \*******************************************************************************************************/
[3768, 2849],

/***/ 2867:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/helpers/classCallCheck.js ***!
  \*****************************************************************************************/
519,

/***/ 2868:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/helpers/possibleConstructorReturn.js ***!
  \****************************************************************************************************/
[3769, 2869],

/***/ 2869:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/helpers/typeof.js ***!
  \*********************************************************************************/
[3770, 2870, 2890],

/***/ 2870:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/core-js/symbol/iterator.js ***!
  \******************************************************************************************/
[3771, 2871],

/***/ 2871:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/fn/symbol/iterator.js ***!
  \*******************************************************************************************************/
[3772, 2872, 2885, 2889],

/***/ 2872:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/es6.string.iterator.js ***!
  \****************************************************************************************************************/
[3773, 2873, 2874],

/***/ 2873:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_string-at.js ***!
  \*******************************************************************************************************/
[3774, 2852, 2849],

/***/ 2874:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_iter-define.js ***!
  \*********************************************************************************************************/
[3775, 2875, 2827, 2876, 2832, 2845, 2877, 2878, 2882, 2884, 2883],

/***/ 2875:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_library.js ***!
  \*****************************************************************************************************/
527,

/***/ 2876:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_redefine.js ***!
  \******************************************************************************************************/
[3776, 2832],

/***/ 2877:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_iterators.js ***!
  \*******************************************************************************************************/
529,

/***/ 2878:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_iter-create.js ***!
  \*********************************************************************************************************/
[3777, 2879, 2841, 2882, 2832, 2883],

/***/ 2879:
/*!***********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-create.js ***!
  \***********************************************************************************************************/
[3778, 2834, 2880, 2857, 2854, 2839, 2881],

/***/ 2880:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-dps.js ***!
  \********************************************************************************************************/
[3779, 2833, 2834, 2843, 2837],

/***/ 2881:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_html.js ***!
  \**************************************************************************************************/
[3780, 2828],

/***/ 2882:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_set-to-string-tag.js ***!
  \***************************************************************************************************************/
[3781, 2833, 2845, 2883],

/***/ 2883:
/*!*************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_wks.js ***!
  \*************************************************************************************************/
[3782, 2855, 2856, 2828],

/***/ 2884:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-gpo.js ***!
  \********************************************************************************************************/
[3783, 2845, 2866, 2854],

/***/ 2885:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/web.dom.iterable.js ***!
  \*************************************************************************************************************/
[3784, 2886, 2828, 2832, 2877, 2883],

/***/ 2886:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/es6.array.iterator.js ***!
  \***************************************************************************************************************/
[3785, 2887, 2888, 2877, 2846, 2874],

/***/ 2887:
/*!****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_add-to-unscopables.js ***!
  \****************************************************************************************************************/
539,

/***/ 2888:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_iter-step.js ***!
  \*******************************************************************************************************/
540,

/***/ 2889:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_wks-ext.js ***!
  \*****************************************************************************************************/
[3786, 2883],

/***/ 2890:
/*!*********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/core-js/symbol.js ***!
  \*********************************************************************************/
[3787, 2891],

/***/ 2891:
/*!****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/fn/symbol/index.js ***!
  \****************************************************************************************************/
[3788, 2892, 2901, 2902, 2903, 2829],

/***/ 2892:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/es6.symbol.js ***!
  \*******************************************************************************************************/
[3789, 2828, 2845, 2837, 2827, 2876, 2893, 2838, 2855, 2882, 2856, 2883, 2889, 2894, 2895, 2896, 2897, 2834, 2846, 2840, 2841, 2879, 2898, 2900, 2833, 2843, 2899, 2858, 2865, 2875, 2832],

/***/ 2893:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_meta.js ***!
  \**************************************************************************************************/
[3790, 2856, 2835, 2845, 2833, 2838],

/***/ 2894:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_wks-define.js ***!
  \********************************************************************************************************/
[3791, 2828, 2829, 2875, 2889, 2833],

/***/ 2895:
/*!***************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_keyof.js ***!
  \***************************************************************************************************/
[3792, 2843, 2846],

/***/ 2896:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_enum-keys.js ***!
  \*******************************************************************************************************/
[3793, 2843, 2865, 2858],

/***/ 2897:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_is-array.js ***!
  \******************************************************************************************************/
[3794, 2848],

/***/ 2898:
/*!*************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-gopn-ext.js ***!
  \*************************************************************************************************************/
[3795, 2846, 2899],

/***/ 2899:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-gopn.js ***!
  \*********************************************************************************************************/
[3796, 2844, 2857],

/***/ 2900:
/*!*********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_object-gopd.js ***!
  \*********************************************************************************************************/
[3797, 2858, 2841, 2846, 2840, 2845, 2836, 2837],

/***/ 2901:
/*!*****************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/es6.object.to-string.js ***!
  \*****************************************************************************************************************/
553,

/***/ 2902:
/*!**********************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/es7.symbol.async-iterator.js ***!
  \**********************************************************************************************************************/
[3798, 2894],

/***/ 2903:
/*!******************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/es7.symbol.observable.js ***!
  \******************************************************************************************************************/
[3799, 2894],

/***/ 2904:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/helpers/inherits.js ***!
  \***********************************************************************************/
[3800, 2905, 2909, 2869],

/***/ 2905:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/core-js/object/set-prototype-of.js ***!
  \**************************************************************************************************/
[3801, 2906],

/***/ 2906:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/fn/object/set-prototype-of.js ***!
  \***************************************************************************************************************/
[3802, 2907, 2829],

/***/ 2907:
/*!************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/es6.object.set-prototype-of.js ***!
  \************************************************************************************************************************/
[3803, 2827, 2908],

/***/ 2908:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/_set-proto.js ***!
  \*******************************************************************************************************/
[3804, 2835, 2834, 2830, 2900],

/***/ 2909:
/*!****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/core-js/object/create.js ***!
  \****************************************************************************************/
[3805, 2910],

/***/ 2910:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/fn/object/create.js ***!
  \*****************************************************************************************************/
[3806, 2911, 2829],

/***/ 2911:
/*!**************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/es6.object.create.js ***!
  \**************************************************************************************************************/
[3807, 2827, 2879],

/***/ 2912:
/*!*********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/classnames/index.js ***!
  \*********************************************************************/
564,

/***/ 2913:
/*!*************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-prop-types/lib/elementType.js ***!
  \*************************************************************************************/
[3811, 2914],

/***/ 2914:
/*!**********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-prop-types/lib/utils/createChainableTypeChecker.js ***!
  \**********************************************************************************************************/
572,

/***/ 2915:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/utils/bootstrapUtils.js ***!
  \*********************************************************************************************/
[3818, 2916, 2860, 2919, 2920],

/***/ 2916:
/*!*****************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/core-js/object/entries.js ***!
  \*****************************************************************************************/
[3819, 2917],

/***/ 2917:
/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/fn/object/entries.js ***!
  \******************************************************************************************************/
[3820, 2918, 2829],

/***/ 2918:
/*!***************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/babel-runtime/~/core-js/library/modules/es7.object.entries.js ***!
  \***************************************************************************************************************/
[3821, 2827, 2842],

/***/ 2919:
/*!**********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/invariant/browser.js ***!
  \**********************************************************************/
187,

/***/ 2920:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/utils/StyleConfig.js ***!
  \******************************************************************************************/
586,

/***/ 2921:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/SafeAnchor.js ***!
  \***********************************************************************************/
[3822, 2860, 2859, 2867, 2868, 2904, 2913],

/***/ 2922:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/FormGroup.js ***!
  \**********************************************************************************/
[3966, 2860, 2859, 2867, 2868, 2904, 2912, 2915, 2920, 2923],

/***/ 2923:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/utils/ValidComponentChildren.js ***!
  \*****************************************************************************************************/
607,

/***/ 2924:
/*!************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/FormControl.js ***!
  \************************************************************************************/
[3967, 2860, 2859, 2867, 2868, 2904, 2912, 2913, 2925, 2926, 2928, 2915],

/***/ 2925:
/*!********************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/warning/browser.js ***!
  \********************************************************************/
190,

/***/ 2926:
/*!********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/FormControlFeedback.js ***!
  \********************************************************************************************/
[3968, 2859, 2860, 2867, 2868, 2904, 2912, 2927, 2915],

/***/ 2927:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/Glyphicon.js ***!
  \**********************************************************************************/
[3842, 2860, 2859, 2867, 2868, 2904, 2912, 2915],

/***/ 2928:
/*!******************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-bootstrap/lib/FormControlStatic.js ***!
  \******************************************************************************************/
[3969, 2860, 2859, 2867, 2868, 2904, 2912, 2913, 2915],

/***/ 2929:
/*!*******************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/assets/emojione.sprites.png ***!
  \*******************************************************************************************************/
872,

/***/ 2930:
/*!**************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/react-emojione.js ***!
  \**************************************************************************************/
[3970, 2931, 2932, 2936],

/***/ 2931:
/*!*********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/data/ascii-to-unicode.js ***!
  \*********************************************************************************************/
874,

/***/ 2932:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/renderers/renderer-factory.js ***!
  \**************************************************************************************************/
[3971, 2933, 2938],

/***/ 2933:
/*!************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/renderers/emoji-renderer.js ***!
  \************************************************************************************************/
[3972, 2934, 2936],

/***/ 2934:
/*!**********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/styles/emojione-sprite.js ***!
  \**********************************************************************************************/
[3973, 2935],

/***/ 2935:
/*!********************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/styles/emojione-sprite-positions.js ***!
  \********************************************************************************************************/
878,

/***/ 2936:
/*!*****************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/utils/emoji-format-conversion.js ***!
  \*****************************************************************************************************/
[3974, 2937],

/***/ 2937:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/data/emoji-data.js ***!
  \***************************************************************************************/
880,

/***/ 2938:
/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-emojione/lib/renderers/unicode-renderer.js ***!
  \**************************************************************************************************/
[3975, 2936],

/***/ 2939:
/*!***********************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \***********************************************************************************************/
[3976, 2940, 2791],

/***/ 2940:
/*!****************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/expression-atlas-feedback/src/gxaFeedback.css ***!
  \****************************************************************************************************************************************************/
[3977, 2790],

/***/ 2941:
/*!****************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/index.js ***!
  \****************************************************************************/
[3978, 2942, 2946],

/***/ 2942:
/*!***************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \***************************************************************************************/
[3979, 2943, 2945],

/***/ 2943:
/*!**********************************************************************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/style-loader!./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \**********************************************************************************************************************************************************************************************************/
[3980, 2944, 2791],

/***/ 2944:
/*!***************************************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************************/
[3981, 2790],

/***/ 2945:
/*!**********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/mapping.js ***!
  \**********************************************************************************/
888,

/***/ 2946:
/*!***********************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/react-ebi-species/src/renderer.js ***!
  \***********************************************************************************/
[3982, 2942],

/***/ 2947:
/*!***************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../~/css-loader!./DifferentialResults.css */ 2948);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../~/style-loader/addStyles.js */ 2791)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../node_modules/css-loader/index.js!./DifferentialResults.css", function() {
				var newContent = require("!!../node_modules/css-loader/index.js!./DifferentialResults.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2948:
/*!********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialResults.css ***!
  \********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../~/css-loader/lib/css-base.js */ 2790)();
	// imports
	
	
	// module
	exports.push([module.id, "table.table-striped tr:nth-child(even) {\n    background-color: #f9f9f9;\n}\n\ntable.table-striped tr:nth-child(odd) {\n    background: #FFF;\n}\n\ntable.gxaDifferentialFacetedSearchResults, table.gxaDifferentialFacetedSearchResults td {\n    border: none;\n    width: inherit;\n}\n\ntable.gxaDifferentialFacetedSearchResults th, table.gxaDifferentialFacetedSearchResults th span {\n    font-weight: bold;\n}\n\ntable.gxaDifferentialFacetedSearchResults th {\n    background: transparent;\n    text-align: center;\n    font-size: 90%;\n    border: 0 solid #ddd;\n    border-bottom-width: 2px;\n    vertical-align: bottom;\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td {\n    padding: 8px;\n    line-height: 1.42857143;\n    vertical-align: middle;\n    border-top: 1px solid #ddd;\n    text-align: center;\n    font-size: 90%;\n}\n\ntable.gxaDifferentialFacetedSearchResults tr td.col_species .react-ebi-species-icon {\n    font-size: 300%;\n    margin-left: 4px;\n}\n\ntd.gxaExperimentalVariable {\n    text-align: center;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 2949:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.jsx ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 2);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./DifferentialFacetsTree.css */ 2950);
	
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

/***/ 2950:
/*!******************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !../~/css-loader!./DifferentialFacetsTree.css */ 2951);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ../~/style-loader/addStyles.js */ 2791)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!../node_modules/css-loader/index.js!./DifferentialFacetsTree.css", function() {
				var newContent = require("!!../node_modules/css-loader/index.js!./DifferentialFacetsTree.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2951:
/*!***********************************************************************************************************************************!*\
  !*** ./atlas_bundles/differential-expression/~/css-loader!./atlas_bundles/differential-expression/src/DifferentialFacetsTree.css ***!
  \***********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ../~/css-loader/lib/css-base.js */ 2790)();
	// imports
	
	
	// module
	exports.push([module.id, "/* Facets-tree container */\n.gxaFacetsContainer ul, .gxaFacetsContainer li {\n    list-style-type: none;\n    padding: 0px;\n}\n\n.gxaFacetsContainer .gxaFacetItem {\n    padding-bottom: 0px;\n}\n\n.gxaFacetsContainer h3 {\n    padding-left: 0;\n    margin: 0 0 20px 0;\n    font-size: 1.6rem;\n}\n\n.filterTitle {\n    color: #195454\n}\n\n.gxaFacetsContainer .gxaFacetItem h4:first-letter, .gxaFacetsContainer .gxaFacetItem ul li span:first-letter {\n    text-transform: capitalize;\n}\n\n.gxaFacetsContainer h5 {\n    font-size: 1.2rem;\n    color: rgba(34,34,34,0.84);\n}\n\n.gxaFacetsContainer input {\n    margin: 0 0.5em 0.5em 0;\n    /*vertical-align: middle;*/\n}\n\n.gxaFacetsContainer .gxaFacetItem .gxaDisabledFacet span {\n    color: gray;\n}\n\n.gxaFacetsContainer .gxaDisabledCheckbox {\n    color: lightgray;\n}\n\n.gxaSpeciesFacet li span {\n    font-style: italic;\n}\n\n.gxaFacetItem li {\n    font-size: 80%;\n}\n\n", ""]);
	
	// exports


/***/ },

/***/ 2952:
/*!*****************************************************************!*\
  !*** ./atlas_bundles/differential-expression/src/urlManager.js ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var Url = __webpack_require__(/*! url */ 2774);
	var QueryString = __webpack_require__(/*! querystring */ 2777);
	
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