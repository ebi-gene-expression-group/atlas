var expressionAtlasHeatmapHighcharts =
webpackJsonp_name_([5],{

/***/ 0:
/*!******************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/index.js ***!
  \******************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/highchartsHeatmapRenderer.js */ 2107);

/***/ },

/***/ 1646:
/*!*******************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/buffer/index.js ***!
  \*******************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(Buffer, global) {/*!
	 * The buffer module from node.js, for the browser.
	 *
	 * @author   Feross Aboukhadijeh <feross@feross.org> <http://feross.org>
	 * @license  MIT
	 */
	/* eslint-disable no-proto */
	
	'use strict'
	
	var base64 = __webpack_require__(/*! base64-js */ 1647)
	var ieee754 = __webpack_require__(/*! ieee754 */ 1648)
	var isArray = __webpack_require__(/*! isarray */ 1649)
	
	exports.Buffer = Buffer
	exports.SlowBuffer = SlowBuffer
	exports.INSPECT_MAX_BYTES = 50
	Buffer.poolSize = 8192 // not used by this implementation
	
	var rootParent = {}
	
	/**
	 * If `Buffer.TYPED_ARRAY_SUPPORT`:
	 *   === true    Use Uint8Array implementation (fastest)
	 *   === false   Use Object implementation (most compatible, even IE6)
	 *
	 * Browsers that support typed arrays are IE 10+, Firefox 4+, Chrome 7+, Safari 5.1+,
	 * Opera 11.6+, iOS 4.2+.
	 *
	 * Due to various browser bugs, sometimes the Object implementation will be used even
	 * when the browser supports typed arrays.
	 *
	 * Note:
	 *
	 *   - Firefox 4-29 lacks support for adding new properties to `Uint8Array` instances,
	 *     See: https://bugzilla.mozilla.org/show_bug.cgi?id=695438.
	 *
	 *   - Safari 5-7 lacks support for changing the `Object.prototype.constructor` property
	 *     on objects.
	 *
	 *   - Chrome 9-10 is missing the `TypedArray.prototype.subarray` function.
	 *
	 *   - IE10 has a broken `TypedArray.prototype.subarray` function which returns arrays of
	 *     incorrect length in some situations.
	
	 * We detect these buggy browsers and set `Buffer.TYPED_ARRAY_SUPPORT` to `false` so they
	 * get the Object implementation, which is slower but behaves correctly.
	 */
	Buffer.TYPED_ARRAY_SUPPORT = global.TYPED_ARRAY_SUPPORT !== undefined
	  ? global.TYPED_ARRAY_SUPPORT
	  : typedArraySupport()
	
	function typedArraySupport () {
	  function Bar () {}
	  try {
	    var arr = new Uint8Array(1)
	    arr.foo = function () { return 42 }
	    arr.constructor = Bar
	    return arr.foo() === 42 && // typed array instances can be augmented
	        arr.constructor === Bar && // constructor can be set
	        typeof arr.subarray === 'function' && // chrome 9-10 lack `subarray`
	        arr.subarray(1, 1).byteLength === 0 // ie10 has broken `subarray`
	  } catch (e) {
	    return false
	  }
	}
	
	function kMaxLength () {
	  return Buffer.TYPED_ARRAY_SUPPORT
	    ? 0x7fffffff
	    : 0x3fffffff
	}
	
	/**
	 * Class: Buffer
	 * =============
	 *
	 * The Buffer constructor returns instances of `Uint8Array` that are augmented
	 * with function properties for all the node `Buffer` API functions. We use
	 * `Uint8Array` so that square bracket notation works as expected -- it returns
	 * a single octet.
	 *
	 * By augmenting the instances, we can avoid modifying the `Uint8Array`
	 * prototype.
	 */
	function Buffer (arg) {
	  if (!(this instanceof Buffer)) {
	    // Avoid going through an ArgumentsAdaptorTrampoline in the common case.
	    if (arguments.length > 1) return new Buffer(arg, arguments[1])
	    return new Buffer(arg)
	  }
	
	  if (!Buffer.TYPED_ARRAY_SUPPORT) {
	    this.length = 0
	    this.parent = undefined
	  }
	
	  // Common case.
	  if (typeof arg === 'number') {
	    return fromNumber(this, arg)
	  }
	
	  // Slightly less common case.
	  if (typeof arg === 'string') {
	    return fromString(this, arg, arguments.length > 1 ? arguments[1] : 'utf8')
	  }
	
	  // Unusual.
	  return fromObject(this, arg)
	}
	
	function fromNumber (that, length) {
	  that = allocate(that, length < 0 ? 0 : checked(length) | 0)
	  if (!Buffer.TYPED_ARRAY_SUPPORT) {
	    for (var i = 0; i < length; i++) {
	      that[i] = 0
	    }
	  }
	  return that
	}
	
	function fromString (that, string, encoding) {
	  if (typeof encoding !== 'string' || encoding === '') encoding = 'utf8'
	
	  // Assumption: byteLength() return value is always < kMaxLength.
	  var length = byteLength(string, encoding) | 0
	  that = allocate(that, length)
	
	  that.write(string, encoding)
	  return that
	}
	
	function fromObject (that, object) {
	  if (Buffer.isBuffer(object)) return fromBuffer(that, object)
	
	  if (isArray(object)) return fromArray(that, object)
	
	  if (object == null) {
	    throw new TypeError('must start with number, buffer, array or string')
	  }
	
	  if (typeof ArrayBuffer !== 'undefined') {
	    if (object.buffer instanceof ArrayBuffer) {
	      return fromTypedArray(that, object)
	    }
	    if (object instanceof ArrayBuffer) {
	      return fromArrayBuffer(that, object)
	    }
	  }
	
	  if (object.length) return fromArrayLike(that, object)
	
	  return fromJsonObject(that, object)
	}
	
	function fromBuffer (that, buffer) {
	  var length = checked(buffer.length) | 0
	  that = allocate(that, length)
	  buffer.copy(that, 0, 0, length)
	  return that
	}
	
	function fromArray (that, array) {
	  var length = checked(array.length) | 0
	  that = allocate(that, length)
	  for (var i = 0; i < length; i += 1) {
	    that[i] = array[i] & 255
	  }
	  return that
	}
	
	// Duplicate of fromArray() to keep fromArray() monomorphic.
	function fromTypedArray (that, array) {
	  var length = checked(array.length) | 0
	  that = allocate(that, length)
	  // Truncating the elements is probably not what people expect from typed
	  // arrays with BYTES_PER_ELEMENT > 1 but it's compatible with the behavior
	  // of the old Buffer constructor.
	  for (var i = 0; i < length; i += 1) {
	    that[i] = array[i] & 255
	  }
	  return that
	}
	
	function fromArrayBuffer (that, array) {
	  if (Buffer.TYPED_ARRAY_SUPPORT) {
	    // Return an augmented `Uint8Array` instance, for best performance
	    array.byteLength
	    that = Buffer._augment(new Uint8Array(array))
	  } else {
	    // Fallback: Return an object instance of the Buffer class
	    that = fromTypedArray(that, new Uint8Array(array))
	  }
	  return that
	}
	
	function fromArrayLike (that, array) {
	  var length = checked(array.length) | 0
	  that = allocate(that, length)
	  for (var i = 0; i < length; i += 1) {
	    that[i] = array[i] & 255
	  }
	  return that
	}
	
	// Deserialize { type: 'Buffer', data: [1,2,3,...] } into a Buffer object.
	// Returns a zero-length buffer for inputs that don't conform to the spec.
	function fromJsonObject (that, object) {
	  var array
	  var length = 0
	
	  if (object.type === 'Buffer' && isArray(object.data)) {
	    array = object.data
	    length = checked(array.length) | 0
	  }
	  that = allocate(that, length)
	
	  for (var i = 0; i < length; i += 1) {
	    that[i] = array[i] & 255
	  }
	  return that
	}
	
	if (Buffer.TYPED_ARRAY_SUPPORT) {
	  Buffer.prototype.__proto__ = Uint8Array.prototype
	  Buffer.__proto__ = Uint8Array
	} else {
	  // pre-set for values that may exist in the future
	  Buffer.prototype.length = undefined
	  Buffer.prototype.parent = undefined
	}
	
	function allocate (that, length) {
	  if (Buffer.TYPED_ARRAY_SUPPORT) {
	    // Return an augmented `Uint8Array` instance, for best performance
	    that = Buffer._augment(new Uint8Array(length))
	    that.__proto__ = Buffer.prototype
	  } else {
	    // Fallback: Return an object instance of the Buffer class
	    that.length = length
	    that._isBuffer = true
	  }
	
	  var fromPool = length !== 0 && length <= Buffer.poolSize >>> 1
	  if (fromPool) that.parent = rootParent
	
	  return that
	}
	
	function checked (length) {
	  // Note: cannot use `length < kMaxLength` here because that fails when
	  // length is NaN (which is otherwise coerced to zero.)
	  if (length >= kMaxLength()) {
	    throw new RangeError('Attempt to allocate Buffer larger than maximum ' +
	                         'size: 0x' + kMaxLength().toString(16) + ' bytes')
	  }
	  return length | 0
	}
	
	function SlowBuffer (subject, encoding) {
	  if (!(this instanceof SlowBuffer)) return new SlowBuffer(subject, encoding)
	
	  var buf = new Buffer(subject, encoding)
	  delete buf.parent
	  return buf
	}
	
	Buffer.isBuffer = function isBuffer (b) {
	  return !!(b != null && b._isBuffer)
	}
	
	Buffer.compare = function compare (a, b) {
	  if (!Buffer.isBuffer(a) || !Buffer.isBuffer(b)) {
	    throw new TypeError('Arguments must be Buffers')
	  }
	
	  if (a === b) return 0
	
	  var x = a.length
	  var y = b.length
	
	  var i = 0
	  var len = Math.min(x, y)
	  while (i < len) {
	    if (a[i] !== b[i]) break
	
	    ++i
	  }
	
	  if (i !== len) {
	    x = a[i]
	    y = b[i]
	  }
	
	  if (x < y) return -1
	  if (y < x) return 1
	  return 0
	}
	
	Buffer.isEncoding = function isEncoding (encoding) {
	  switch (String(encoding).toLowerCase()) {
	    case 'hex':
	    case 'utf8':
	    case 'utf-8':
	    case 'ascii':
	    case 'binary':
	    case 'base64':
	    case 'raw':
	    case 'ucs2':
	    case 'ucs-2':
	    case 'utf16le':
	    case 'utf-16le':
	      return true
	    default:
	      return false
	  }
	}
	
	Buffer.concat = function concat (list, length) {
	  if (!isArray(list)) throw new TypeError('list argument must be an Array of Buffers.')
	
	  if (list.length === 0) {
	    return new Buffer(0)
	  }
	
	  var i
	  if (length === undefined) {
	    length = 0
	    for (i = 0; i < list.length; i++) {
	      length += list[i].length
	    }
	  }
	
	  var buf = new Buffer(length)
	  var pos = 0
	  for (i = 0; i < list.length; i++) {
	    var item = list[i]
	    item.copy(buf, pos)
	    pos += item.length
	  }
	  return buf
	}
	
	function byteLength (string, encoding) {
	  if (typeof string !== 'string') string = '' + string
	
	  var len = string.length
	  if (len === 0) return 0
	
	  // Use a for loop to avoid recursion
	  var loweredCase = false
	  for (;;) {
	    switch (encoding) {
	      case 'ascii':
	      case 'binary':
	      // Deprecated
	      case 'raw':
	      case 'raws':
	        return len
	      case 'utf8':
	      case 'utf-8':
	        return utf8ToBytes(string).length
	      case 'ucs2':
	      case 'ucs-2':
	      case 'utf16le':
	      case 'utf-16le':
	        return len * 2
	      case 'hex':
	        return len >>> 1
	      case 'base64':
	        return base64ToBytes(string).length
	      default:
	        if (loweredCase) return utf8ToBytes(string).length // assume utf8
	        encoding = ('' + encoding).toLowerCase()
	        loweredCase = true
	    }
	  }
	}
	Buffer.byteLength = byteLength
	
	function slowToString (encoding, start, end) {
	  var loweredCase = false
	
	  start = start | 0
	  end = end === undefined || end === Infinity ? this.length : end | 0
	
	  if (!encoding) encoding = 'utf8'
	  if (start < 0) start = 0
	  if (end > this.length) end = this.length
	  if (end <= start) return ''
	
	  while (true) {
	    switch (encoding) {
	      case 'hex':
	        return hexSlice(this, start, end)
	
	      case 'utf8':
	      case 'utf-8':
	        return utf8Slice(this, start, end)
	
	      case 'ascii':
	        return asciiSlice(this, start, end)
	
	      case 'binary':
	        return binarySlice(this, start, end)
	
	      case 'base64':
	        return base64Slice(this, start, end)
	
	      case 'ucs2':
	      case 'ucs-2':
	      case 'utf16le':
	      case 'utf-16le':
	        return utf16leSlice(this, start, end)
	
	      default:
	        if (loweredCase) throw new TypeError('Unknown encoding: ' + encoding)
	        encoding = (encoding + '').toLowerCase()
	        loweredCase = true
	    }
	  }
	}
	
	Buffer.prototype.toString = function toString () {
	  var length = this.length | 0
	  if (length === 0) return ''
	  if (arguments.length === 0) return utf8Slice(this, 0, length)
	  return slowToString.apply(this, arguments)
	}
	
	Buffer.prototype.equals = function equals (b) {
	  if (!Buffer.isBuffer(b)) throw new TypeError('Argument must be a Buffer')
	  if (this === b) return true
	  return Buffer.compare(this, b) === 0
	}
	
	Buffer.prototype.inspect = function inspect () {
	  var str = ''
	  var max = exports.INSPECT_MAX_BYTES
	  if (this.length > 0) {
	    str = this.toString('hex', 0, max).match(/.{2}/g).join(' ')
	    if (this.length > max) str += ' ... '
	  }
	  return '<Buffer ' + str + '>'
	}
	
	Buffer.prototype.compare = function compare (b) {
	  if (!Buffer.isBuffer(b)) throw new TypeError('Argument must be a Buffer')
	  if (this === b) return 0
	  return Buffer.compare(this, b)
	}
	
	Buffer.prototype.indexOf = function indexOf (val, byteOffset) {
	  if (byteOffset > 0x7fffffff) byteOffset = 0x7fffffff
	  else if (byteOffset < -0x80000000) byteOffset = -0x80000000
	  byteOffset >>= 0
	
	  if (this.length === 0) return -1
	  if (byteOffset >= this.length) return -1
	
	  // Negative offsets start from the end of the buffer
	  if (byteOffset < 0) byteOffset = Math.max(this.length + byteOffset, 0)
	
	  if (typeof val === 'string') {
	    if (val.length === 0) return -1 // special case: looking for empty string always fails
	    return String.prototype.indexOf.call(this, val, byteOffset)
	  }
	  if (Buffer.isBuffer(val)) {
	    return arrayIndexOf(this, val, byteOffset)
	  }
	  if (typeof val === 'number') {
	    if (Buffer.TYPED_ARRAY_SUPPORT && Uint8Array.prototype.indexOf === 'function') {
	      return Uint8Array.prototype.indexOf.call(this, val, byteOffset)
	    }
	    return arrayIndexOf(this, [ val ], byteOffset)
	  }
	
	  function arrayIndexOf (arr, val, byteOffset) {
	    var foundIndex = -1
	    for (var i = 0; byteOffset + i < arr.length; i++) {
	      if (arr[byteOffset + i] === val[foundIndex === -1 ? 0 : i - foundIndex]) {
	        if (foundIndex === -1) foundIndex = i
	        if (i - foundIndex + 1 === val.length) return byteOffset + foundIndex
	      } else {
	        foundIndex = -1
	      }
	    }
	    return -1
	  }
	
	  throw new TypeError('val must be string, number or Buffer')
	}
	
	// `get` is deprecated
	Buffer.prototype.get = function get (offset) {
	  console.log('.get() is deprecated. Access using array indexes instead.')
	  return this.readUInt8(offset)
	}
	
	// `set` is deprecated
	Buffer.prototype.set = function set (v, offset) {
	  console.log('.set() is deprecated. Access using array indexes instead.')
	  return this.writeUInt8(v, offset)
	}
	
	function hexWrite (buf, string, offset, length) {
	  offset = Number(offset) || 0
	  var remaining = buf.length - offset
	  if (!length) {
	    length = remaining
	  } else {
	    length = Number(length)
	    if (length > remaining) {
	      length = remaining
	    }
	  }
	
	  // must be an even number of digits
	  var strLen = string.length
	  if (strLen % 2 !== 0) throw new Error('Invalid hex string')
	
	  if (length > strLen / 2) {
	    length = strLen / 2
	  }
	  for (var i = 0; i < length; i++) {
	    var parsed = parseInt(string.substr(i * 2, 2), 16)
	    if (isNaN(parsed)) throw new Error('Invalid hex string')
	    buf[offset + i] = parsed
	  }
	  return i
	}
	
	function utf8Write (buf, string, offset, length) {
	  return blitBuffer(utf8ToBytes(string, buf.length - offset), buf, offset, length)
	}
	
	function asciiWrite (buf, string, offset, length) {
	  return blitBuffer(asciiToBytes(string), buf, offset, length)
	}
	
	function binaryWrite (buf, string, offset, length) {
	  return asciiWrite(buf, string, offset, length)
	}
	
	function base64Write (buf, string, offset, length) {
	  return blitBuffer(base64ToBytes(string), buf, offset, length)
	}
	
	function ucs2Write (buf, string, offset, length) {
	  return blitBuffer(utf16leToBytes(string, buf.length - offset), buf, offset, length)
	}
	
	Buffer.prototype.write = function write (string, offset, length, encoding) {
	  // Buffer#write(string)
	  if (offset === undefined) {
	    encoding = 'utf8'
	    length = this.length
	    offset = 0
	  // Buffer#write(string, encoding)
	  } else if (length === undefined && typeof offset === 'string') {
	    encoding = offset
	    length = this.length
	    offset = 0
	  // Buffer#write(string, offset[, length][, encoding])
	  } else if (isFinite(offset)) {
	    offset = offset | 0
	    if (isFinite(length)) {
	      length = length | 0
	      if (encoding === undefined) encoding = 'utf8'
	    } else {
	      encoding = length
	      length = undefined
	    }
	  // legacy write(string, encoding, offset, length) - remove in v0.13
	  } else {
	    var swap = encoding
	    encoding = offset
	    offset = length | 0
	    length = swap
	  }
	
	  var remaining = this.length - offset
	  if (length === undefined || length > remaining) length = remaining
	
	  if ((string.length > 0 && (length < 0 || offset < 0)) || offset > this.length) {
	    throw new RangeError('attempt to write outside buffer bounds')
	  }
	
	  if (!encoding) encoding = 'utf8'
	
	  var loweredCase = false
	  for (;;) {
	    switch (encoding) {
	      case 'hex':
	        return hexWrite(this, string, offset, length)
	
	      case 'utf8':
	      case 'utf-8':
	        return utf8Write(this, string, offset, length)
	
	      case 'ascii':
	        return asciiWrite(this, string, offset, length)
	
	      case 'binary':
	        return binaryWrite(this, string, offset, length)
	
	      case 'base64':
	        // Warning: maxLength not taken into account in base64Write
	        return base64Write(this, string, offset, length)
	
	      case 'ucs2':
	      case 'ucs-2':
	      case 'utf16le':
	      case 'utf-16le':
	        return ucs2Write(this, string, offset, length)
	
	      default:
	        if (loweredCase) throw new TypeError('Unknown encoding: ' + encoding)
	        encoding = ('' + encoding).toLowerCase()
	        loweredCase = true
	    }
	  }
	}
	
	Buffer.prototype.toJSON = function toJSON () {
	  return {
	    type: 'Buffer',
	    data: Array.prototype.slice.call(this._arr || this, 0)
	  }
	}
	
	function base64Slice (buf, start, end) {
	  if (start === 0 && end === buf.length) {
	    return base64.fromByteArray(buf)
	  } else {
	    return base64.fromByteArray(buf.slice(start, end))
	  }
	}
	
	function utf8Slice (buf, start, end) {
	  end = Math.min(buf.length, end)
	  var res = []
	
	  var i = start
	  while (i < end) {
	    var firstByte = buf[i]
	    var codePoint = null
	    var bytesPerSequence = (firstByte > 0xEF) ? 4
	      : (firstByte > 0xDF) ? 3
	      : (firstByte > 0xBF) ? 2
	      : 1
	
	    if (i + bytesPerSequence <= end) {
	      var secondByte, thirdByte, fourthByte, tempCodePoint
	
	      switch (bytesPerSequence) {
	        case 1:
	          if (firstByte < 0x80) {
	            codePoint = firstByte
	          }
	          break
	        case 2:
	          secondByte = buf[i + 1]
	          if ((secondByte & 0xC0) === 0x80) {
	            tempCodePoint = (firstByte & 0x1F) << 0x6 | (secondByte & 0x3F)
	            if (tempCodePoint > 0x7F) {
	              codePoint = tempCodePoint
	            }
	          }
	          break
	        case 3:
	          secondByte = buf[i + 1]
	          thirdByte = buf[i + 2]
	          if ((secondByte & 0xC0) === 0x80 && (thirdByte & 0xC0) === 0x80) {
	            tempCodePoint = (firstByte & 0xF) << 0xC | (secondByte & 0x3F) << 0x6 | (thirdByte & 0x3F)
	            if (tempCodePoint > 0x7FF && (tempCodePoint < 0xD800 || tempCodePoint > 0xDFFF)) {
	              codePoint = tempCodePoint
	            }
	          }
	          break
	        case 4:
	          secondByte = buf[i + 1]
	          thirdByte = buf[i + 2]
	          fourthByte = buf[i + 3]
	          if ((secondByte & 0xC0) === 0x80 && (thirdByte & 0xC0) === 0x80 && (fourthByte & 0xC0) === 0x80) {
	            tempCodePoint = (firstByte & 0xF) << 0x12 | (secondByte & 0x3F) << 0xC | (thirdByte & 0x3F) << 0x6 | (fourthByte & 0x3F)
	            if (tempCodePoint > 0xFFFF && tempCodePoint < 0x110000) {
	              codePoint = tempCodePoint
	            }
	          }
	      }
	    }
	
	    if (codePoint === null) {
	      // we did not generate a valid codePoint so insert a
	      // replacement char (U+FFFD) and advance only 1 byte
	      codePoint = 0xFFFD
	      bytesPerSequence = 1
	    } else if (codePoint > 0xFFFF) {
	      // encode to utf16 (surrogate pair dance)
	      codePoint -= 0x10000
	      res.push(codePoint >>> 10 & 0x3FF | 0xD800)
	      codePoint = 0xDC00 | codePoint & 0x3FF
	    }
	
	    res.push(codePoint)
	    i += bytesPerSequence
	  }
	
	  return decodeCodePointsArray(res)
	}
	
	// Based on http://stackoverflow.com/a/22747272/680742, the browser with
	// the lowest limit is Chrome, with 0x10000 args.
	// We go 1 magnitude less, for safety
	var MAX_ARGUMENTS_LENGTH = 0x1000
	
	function decodeCodePointsArray (codePoints) {
	  var len = codePoints.length
	  if (len <= MAX_ARGUMENTS_LENGTH) {
	    return String.fromCharCode.apply(String, codePoints) // avoid extra slice()
	  }
	
	  // Decode in chunks to avoid "call stack size exceeded".
	  var res = ''
	  var i = 0
	  while (i < len) {
	    res += String.fromCharCode.apply(
	      String,
	      codePoints.slice(i, i += MAX_ARGUMENTS_LENGTH)
	    )
	  }
	  return res
	}
	
	function asciiSlice (buf, start, end) {
	  var ret = ''
	  end = Math.min(buf.length, end)
	
	  for (var i = start; i < end; i++) {
	    ret += String.fromCharCode(buf[i] & 0x7F)
	  }
	  return ret
	}
	
	function binarySlice (buf, start, end) {
	  var ret = ''
	  end = Math.min(buf.length, end)
	
	  for (var i = start; i < end; i++) {
	    ret += String.fromCharCode(buf[i])
	  }
	  return ret
	}
	
	function hexSlice (buf, start, end) {
	  var len = buf.length
	
	  if (!start || start < 0) start = 0
	  if (!end || end < 0 || end > len) end = len
	
	  var out = ''
	  for (var i = start; i < end; i++) {
	    out += toHex(buf[i])
	  }
	  return out
	}
	
	function utf16leSlice (buf, start, end) {
	  var bytes = buf.slice(start, end)
	  var res = ''
	  for (var i = 0; i < bytes.length; i += 2) {
	    res += String.fromCharCode(bytes[i] + bytes[i + 1] * 256)
	  }
	  return res
	}
	
	Buffer.prototype.slice = function slice (start, end) {
	  var len = this.length
	  start = ~~start
	  end = end === undefined ? len : ~~end
	
	  if (start < 0) {
	    start += len
	    if (start < 0) start = 0
	  } else if (start > len) {
	    start = len
	  }
	
	  if (end < 0) {
	    end += len
	    if (end < 0) end = 0
	  } else if (end > len) {
	    end = len
	  }
	
	  if (end < start) end = start
	
	  var newBuf
	  if (Buffer.TYPED_ARRAY_SUPPORT) {
	    newBuf = Buffer._augment(this.subarray(start, end))
	  } else {
	    var sliceLen = end - start
	    newBuf = new Buffer(sliceLen, undefined)
	    for (var i = 0; i < sliceLen; i++) {
	      newBuf[i] = this[i + start]
	    }
	  }
	
	  if (newBuf.length) newBuf.parent = this.parent || this
	
	  return newBuf
	}
	
	/*
	 * Need to make sure that buffer isn't trying to write out of bounds.
	 */
	function checkOffset (offset, ext, length) {
	  if ((offset % 1) !== 0 || offset < 0) throw new RangeError('offset is not uint')
	  if (offset + ext > length) throw new RangeError('Trying to access beyond buffer length')
	}
	
	Buffer.prototype.readUIntLE = function readUIntLE (offset, byteLength, noAssert) {
	  offset = offset | 0
	  byteLength = byteLength | 0
	  if (!noAssert) checkOffset(offset, byteLength, this.length)
	
	  var val = this[offset]
	  var mul = 1
	  var i = 0
	  while (++i < byteLength && (mul *= 0x100)) {
	    val += this[offset + i] * mul
	  }
	
	  return val
	}
	
	Buffer.prototype.readUIntBE = function readUIntBE (offset, byteLength, noAssert) {
	  offset = offset | 0
	  byteLength = byteLength | 0
	  if (!noAssert) {
	    checkOffset(offset, byteLength, this.length)
	  }
	
	  var val = this[offset + --byteLength]
	  var mul = 1
	  while (byteLength > 0 && (mul *= 0x100)) {
	    val += this[offset + --byteLength] * mul
	  }
	
	  return val
	}
	
	Buffer.prototype.readUInt8 = function readUInt8 (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 1, this.length)
	  return this[offset]
	}
	
	Buffer.prototype.readUInt16LE = function readUInt16LE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 2, this.length)
	  return this[offset] | (this[offset + 1] << 8)
	}
	
	Buffer.prototype.readUInt16BE = function readUInt16BE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 2, this.length)
	  return (this[offset] << 8) | this[offset + 1]
	}
	
	Buffer.prototype.readUInt32LE = function readUInt32LE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 4, this.length)
	
	  return ((this[offset]) |
	      (this[offset + 1] << 8) |
	      (this[offset + 2] << 16)) +
	      (this[offset + 3] * 0x1000000)
	}
	
	Buffer.prototype.readUInt32BE = function readUInt32BE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 4, this.length)
	
	  return (this[offset] * 0x1000000) +
	    ((this[offset + 1] << 16) |
	    (this[offset + 2] << 8) |
	    this[offset + 3])
	}
	
	Buffer.prototype.readIntLE = function readIntLE (offset, byteLength, noAssert) {
	  offset = offset | 0
	  byteLength = byteLength | 0
	  if (!noAssert) checkOffset(offset, byteLength, this.length)
	
	  var val = this[offset]
	  var mul = 1
	  var i = 0
	  while (++i < byteLength && (mul *= 0x100)) {
	    val += this[offset + i] * mul
	  }
	  mul *= 0x80
	
	  if (val >= mul) val -= Math.pow(2, 8 * byteLength)
	
	  return val
	}
	
	Buffer.prototype.readIntBE = function readIntBE (offset, byteLength, noAssert) {
	  offset = offset | 0
	  byteLength = byteLength | 0
	  if (!noAssert) checkOffset(offset, byteLength, this.length)
	
	  var i = byteLength
	  var mul = 1
	  var val = this[offset + --i]
	  while (i > 0 && (mul *= 0x100)) {
	    val += this[offset + --i] * mul
	  }
	  mul *= 0x80
	
	  if (val >= mul) val -= Math.pow(2, 8 * byteLength)
	
	  return val
	}
	
	Buffer.prototype.readInt8 = function readInt8 (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 1, this.length)
	  if (!(this[offset] & 0x80)) return (this[offset])
	  return ((0xff - this[offset] + 1) * -1)
	}
	
	Buffer.prototype.readInt16LE = function readInt16LE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 2, this.length)
	  var val = this[offset] | (this[offset + 1] << 8)
	  return (val & 0x8000) ? val | 0xFFFF0000 : val
	}
	
	Buffer.prototype.readInt16BE = function readInt16BE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 2, this.length)
	  var val = this[offset + 1] | (this[offset] << 8)
	  return (val & 0x8000) ? val | 0xFFFF0000 : val
	}
	
	Buffer.prototype.readInt32LE = function readInt32LE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 4, this.length)
	
	  return (this[offset]) |
	    (this[offset + 1] << 8) |
	    (this[offset + 2] << 16) |
	    (this[offset + 3] << 24)
	}
	
	Buffer.prototype.readInt32BE = function readInt32BE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 4, this.length)
	
	  return (this[offset] << 24) |
	    (this[offset + 1] << 16) |
	    (this[offset + 2] << 8) |
	    (this[offset + 3])
	}
	
	Buffer.prototype.readFloatLE = function readFloatLE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 4, this.length)
	  return ieee754.read(this, offset, true, 23, 4)
	}
	
	Buffer.prototype.readFloatBE = function readFloatBE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 4, this.length)
	  return ieee754.read(this, offset, false, 23, 4)
	}
	
	Buffer.prototype.readDoubleLE = function readDoubleLE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 8, this.length)
	  return ieee754.read(this, offset, true, 52, 8)
	}
	
	Buffer.prototype.readDoubleBE = function readDoubleBE (offset, noAssert) {
	  if (!noAssert) checkOffset(offset, 8, this.length)
	  return ieee754.read(this, offset, false, 52, 8)
	}
	
	function checkInt (buf, value, offset, ext, max, min) {
	  if (!Buffer.isBuffer(buf)) throw new TypeError('buffer must be a Buffer instance')
	  if (value > max || value < min) throw new RangeError('value is out of bounds')
	  if (offset + ext > buf.length) throw new RangeError('index out of range')
	}
	
	Buffer.prototype.writeUIntLE = function writeUIntLE (value, offset, byteLength, noAssert) {
	  value = +value
	  offset = offset | 0
	  byteLength = byteLength | 0
	  if (!noAssert) checkInt(this, value, offset, byteLength, Math.pow(2, 8 * byteLength), 0)
	
	  var mul = 1
	  var i = 0
	  this[offset] = value & 0xFF
	  while (++i < byteLength && (mul *= 0x100)) {
	    this[offset + i] = (value / mul) & 0xFF
	  }
	
	  return offset + byteLength
	}
	
	Buffer.prototype.writeUIntBE = function writeUIntBE (value, offset, byteLength, noAssert) {
	  value = +value
	  offset = offset | 0
	  byteLength = byteLength | 0
	  if (!noAssert) checkInt(this, value, offset, byteLength, Math.pow(2, 8 * byteLength), 0)
	
	  var i = byteLength - 1
	  var mul = 1
	  this[offset + i] = value & 0xFF
	  while (--i >= 0 && (mul *= 0x100)) {
	    this[offset + i] = (value / mul) & 0xFF
	  }
	
	  return offset + byteLength
	}
	
	Buffer.prototype.writeUInt8 = function writeUInt8 (value, offset, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) checkInt(this, value, offset, 1, 0xff, 0)
	  if (!Buffer.TYPED_ARRAY_SUPPORT) value = Math.floor(value)
	  this[offset] = (value & 0xff)
	  return offset + 1
	}
	
	function objectWriteUInt16 (buf, value, offset, littleEndian) {
	  if (value < 0) value = 0xffff + value + 1
	  for (var i = 0, j = Math.min(buf.length - offset, 2); i < j; i++) {
	    buf[offset + i] = (value & (0xff << (8 * (littleEndian ? i : 1 - i)))) >>>
	      (littleEndian ? i : 1 - i) * 8
	  }
	}
	
	Buffer.prototype.writeUInt16LE = function writeUInt16LE (value, offset, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) checkInt(this, value, offset, 2, 0xffff, 0)
	  if (Buffer.TYPED_ARRAY_SUPPORT) {
	    this[offset] = (value & 0xff)
	    this[offset + 1] = (value >>> 8)
	  } else {
	    objectWriteUInt16(this, value, offset, true)
	  }
	  return offset + 2
	}
	
	Buffer.prototype.writeUInt16BE = function writeUInt16BE (value, offset, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) checkInt(this, value, offset, 2, 0xffff, 0)
	  if (Buffer.TYPED_ARRAY_SUPPORT) {
	    this[offset] = (value >>> 8)
	    this[offset + 1] = (value & 0xff)
	  } else {
	    objectWriteUInt16(this, value, offset, false)
	  }
	  return offset + 2
	}
	
	function objectWriteUInt32 (buf, value, offset, littleEndian) {
	  if (value < 0) value = 0xffffffff + value + 1
	  for (var i = 0, j = Math.min(buf.length - offset, 4); i < j; i++) {
	    buf[offset + i] = (value >>> (littleEndian ? i : 3 - i) * 8) & 0xff
	  }
	}
	
	Buffer.prototype.writeUInt32LE = function writeUInt32LE (value, offset, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) checkInt(this, value, offset, 4, 0xffffffff, 0)
	  if (Buffer.TYPED_ARRAY_SUPPORT) {
	    this[offset + 3] = (value >>> 24)
	    this[offset + 2] = (value >>> 16)
	    this[offset + 1] = (value >>> 8)
	    this[offset] = (value & 0xff)
	  } else {
	    objectWriteUInt32(this, value, offset, true)
	  }
	  return offset + 4
	}
	
	Buffer.prototype.writeUInt32BE = function writeUInt32BE (value, offset, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) checkInt(this, value, offset, 4, 0xffffffff, 0)
	  if (Buffer.TYPED_ARRAY_SUPPORT) {
	    this[offset] = (value >>> 24)
	    this[offset + 1] = (value >>> 16)
	    this[offset + 2] = (value >>> 8)
	    this[offset + 3] = (value & 0xff)
	  } else {
	    objectWriteUInt32(this, value, offset, false)
	  }
	  return offset + 4
	}
	
	Buffer.prototype.writeIntLE = function writeIntLE (value, offset, byteLength, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) {
	    var limit = Math.pow(2, 8 * byteLength - 1)
	
	    checkInt(this, value, offset, byteLength, limit - 1, -limit)
	  }
	
	  var i = 0
	  var mul = 1
	  var sub = value < 0 ? 1 : 0
	  this[offset] = value & 0xFF
	  while (++i < byteLength && (mul *= 0x100)) {
	    this[offset + i] = ((value / mul) >> 0) - sub & 0xFF
	  }
	
	  return offset + byteLength
	}
	
	Buffer.prototype.writeIntBE = function writeIntBE (value, offset, byteLength, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) {
	    var limit = Math.pow(2, 8 * byteLength - 1)
	
	    checkInt(this, value, offset, byteLength, limit - 1, -limit)
	  }
	
	  var i = byteLength - 1
	  var mul = 1
	  var sub = value < 0 ? 1 : 0
	  this[offset + i] = value & 0xFF
	  while (--i >= 0 && (mul *= 0x100)) {
	    this[offset + i] = ((value / mul) >> 0) - sub & 0xFF
	  }
	
	  return offset + byteLength
	}
	
	Buffer.prototype.writeInt8 = function writeInt8 (value, offset, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) checkInt(this, value, offset, 1, 0x7f, -0x80)
	  if (!Buffer.TYPED_ARRAY_SUPPORT) value = Math.floor(value)
	  if (value < 0) value = 0xff + value + 1
	  this[offset] = (value & 0xff)
	  return offset + 1
	}
	
	Buffer.prototype.writeInt16LE = function writeInt16LE (value, offset, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) checkInt(this, value, offset, 2, 0x7fff, -0x8000)
	  if (Buffer.TYPED_ARRAY_SUPPORT) {
	    this[offset] = (value & 0xff)
	    this[offset + 1] = (value >>> 8)
	  } else {
	    objectWriteUInt16(this, value, offset, true)
	  }
	  return offset + 2
	}
	
	Buffer.prototype.writeInt16BE = function writeInt16BE (value, offset, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) checkInt(this, value, offset, 2, 0x7fff, -0x8000)
	  if (Buffer.TYPED_ARRAY_SUPPORT) {
	    this[offset] = (value >>> 8)
	    this[offset + 1] = (value & 0xff)
	  } else {
	    objectWriteUInt16(this, value, offset, false)
	  }
	  return offset + 2
	}
	
	Buffer.prototype.writeInt32LE = function writeInt32LE (value, offset, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) checkInt(this, value, offset, 4, 0x7fffffff, -0x80000000)
	  if (Buffer.TYPED_ARRAY_SUPPORT) {
	    this[offset] = (value & 0xff)
	    this[offset + 1] = (value >>> 8)
	    this[offset + 2] = (value >>> 16)
	    this[offset + 3] = (value >>> 24)
	  } else {
	    objectWriteUInt32(this, value, offset, true)
	  }
	  return offset + 4
	}
	
	Buffer.prototype.writeInt32BE = function writeInt32BE (value, offset, noAssert) {
	  value = +value
	  offset = offset | 0
	  if (!noAssert) checkInt(this, value, offset, 4, 0x7fffffff, -0x80000000)
	  if (value < 0) value = 0xffffffff + value + 1
	  if (Buffer.TYPED_ARRAY_SUPPORT) {
	    this[offset] = (value >>> 24)
	    this[offset + 1] = (value >>> 16)
	    this[offset + 2] = (value >>> 8)
	    this[offset + 3] = (value & 0xff)
	  } else {
	    objectWriteUInt32(this, value, offset, false)
	  }
	  return offset + 4
	}
	
	function checkIEEE754 (buf, value, offset, ext, max, min) {
	  if (value > max || value < min) throw new RangeError('value is out of bounds')
	  if (offset + ext > buf.length) throw new RangeError('index out of range')
	  if (offset < 0) throw new RangeError('index out of range')
	}
	
	function writeFloat (buf, value, offset, littleEndian, noAssert) {
	  if (!noAssert) {
	    checkIEEE754(buf, value, offset, 4, 3.4028234663852886e+38, -3.4028234663852886e+38)
	  }
	  ieee754.write(buf, value, offset, littleEndian, 23, 4)
	  return offset + 4
	}
	
	Buffer.prototype.writeFloatLE = function writeFloatLE (value, offset, noAssert) {
	  return writeFloat(this, value, offset, true, noAssert)
	}
	
	Buffer.prototype.writeFloatBE = function writeFloatBE (value, offset, noAssert) {
	  return writeFloat(this, value, offset, false, noAssert)
	}
	
	function writeDouble (buf, value, offset, littleEndian, noAssert) {
	  if (!noAssert) {
	    checkIEEE754(buf, value, offset, 8, 1.7976931348623157E+308, -1.7976931348623157E+308)
	  }
	  ieee754.write(buf, value, offset, littleEndian, 52, 8)
	  return offset + 8
	}
	
	Buffer.prototype.writeDoubleLE = function writeDoubleLE (value, offset, noAssert) {
	  return writeDouble(this, value, offset, true, noAssert)
	}
	
	Buffer.prototype.writeDoubleBE = function writeDoubleBE (value, offset, noAssert) {
	  return writeDouble(this, value, offset, false, noAssert)
	}
	
	// copy(targetBuffer, targetStart=0, sourceStart=0, sourceEnd=buffer.length)
	Buffer.prototype.copy = function copy (target, targetStart, start, end) {
	  if (!start) start = 0
	  if (!end && end !== 0) end = this.length
	  if (targetStart >= target.length) targetStart = target.length
	  if (!targetStart) targetStart = 0
	  if (end > 0 && end < start) end = start
	
	  // Copy 0 bytes; we're done
	  if (end === start) return 0
	  if (target.length === 0 || this.length === 0) return 0
	
	  // Fatal error conditions
	  if (targetStart < 0) {
	    throw new RangeError('targetStart out of bounds')
	  }
	  if (start < 0 || start >= this.length) throw new RangeError('sourceStart out of bounds')
	  if (end < 0) throw new RangeError('sourceEnd out of bounds')
	
	  // Are we oob?
	  if (end > this.length) end = this.length
	  if (target.length - targetStart < end - start) {
	    end = target.length - targetStart + start
	  }
	
	  var len = end - start
	  var i
	
	  if (this === target && start < targetStart && targetStart < end) {
	    // descending copy from end
	    for (i = len - 1; i >= 0; i--) {
	      target[i + targetStart] = this[i + start]
	    }
	  } else if (len < 1000 || !Buffer.TYPED_ARRAY_SUPPORT) {
	    // ascending copy from start
	    for (i = 0; i < len; i++) {
	      target[i + targetStart] = this[i + start]
	    }
	  } else {
	    target._set(this.subarray(start, start + len), targetStart)
	  }
	
	  return len
	}
	
	// fill(value, start=0, end=buffer.length)
	Buffer.prototype.fill = function fill (value, start, end) {
	  if (!value) value = 0
	  if (!start) start = 0
	  if (!end) end = this.length
	
	  if (end < start) throw new RangeError('end < start')
	
	  // Fill 0 bytes; we're done
	  if (end === start) return
	  if (this.length === 0) return
	
	  if (start < 0 || start >= this.length) throw new RangeError('start out of bounds')
	  if (end < 0 || end > this.length) throw new RangeError('end out of bounds')
	
	  var i
	  if (typeof value === 'number') {
	    for (i = start; i < end; i++) {
	      this[i] = value
	    }
	  } else {
	    var bytes = utf8ToBytes(value.toString())
	    var len = bytes.length
	    for (i = start; i < end; i++) {
	      this[i] = bytes[i % len]
	    }
	  }
	
	  return this
	}
	
	/**
	 * Creates a new `ArrayBuffer` with the *copied* memory of the buffer instance.
	 * Added in Node 0.12. Only available in browsers that support ArrayBuffer.
	 */
	Buffer.prototype.toArrayBuffer = function toArrayBuffer () {
	  if (typeof Uint8Array !== 'undefined') {
	    if (Buffer.TYPED_ARRAY_SUPPORT) {
	      return (new Buffer(this)).buffer
	    } else {
	      var buf = new Uint8Array(this.length)
	      for (var i = 0, len = buf.length; i < len; i += 1) {
	        buf[i] = this[i]
	      }
	      return buf.buffer
	    }
	  } else {
	    throw new TypeError('Buffer.toArrayBuffer not supported in this browser')
	  }
	}
	
	// HELPER FUNCTIONS
	// ================
	
	var BP = Buffer.prototype
	
	/**
	 * Augment a Uint8Array *instance* (not the Uint8Array class!) with Buffer methods
	 */
	Buffer._augment = function _augment (arr) {
	  arr.constructor = Buffer
	  arr._isBuffer = true
	
	  // save reference to original Uint8Array set method before overwriting
	  arr._set = arr.set
	
	  // deprecated
	  arr.get = BP.get
	  arr.set = BP.set
	
	  arr.write = BP.write
	  arr.toString = BP.toString
	  arr.toLocaleString = BP.toString
	  arr.toJSON = BP.toJSON
	  arr.equals = BP.equals
	  arr.compare = BP.compare
	  arr.indexOf = BP.indexOf
	  arr.copy = BP.copy
	  arr.slice = BP.slice
	  arr.readUIntLE = BP.readUIntLE
	  arr.readUIntBE = BP.readUIntBE
	  arr.readUInt8 = BP.readUInt8
	  arr.readUInt16LE = BP.readUInt16LE
	  arr.readUInt16BE = BP.readUInt16BE
	  arr.readUInt32LE = BP.readUInt32LE
	  arr.readUInt32BE = BP.readUInt32BE
	  arr.readIntLE = BP.readIntLE
	  arr.readIntBE = BP.readIntBE
	  arr.readInt8 = BP.readInt8
	  arr.readInt16LE = BP.readInt16LE
	  arr.readInt16BE = BP.readInt16BE
	  arr.readInt32LE = BP.readInt32LE
	  arr.readInt32BE = BP.readInt32BE
	  arr.readFloatLE = BP.readFloatLE
	  arr.readFloatBE = BP.readFloatBE
	  arr.readDoubleLE = BP.readDoubleLE
	  arr.readDoubleBE = BP.readDoubleBE
	  arr.writeUInt8 = BP.writeUInt8
	  arr.writeUIntLE = BP.writeUIntLE
	  arr.writeUIntBE = BP.writeUIntBE
	  arr.writeUInt16LE = BP.writeUInt16LE
	  arr.writeUInt16BE = BP.writeUInt16BE
	  arr.writeUInt32LE = BP.writeUInt32LE
	  arr.writeUInt32BE = BP.writeUInt32BE
	  arr.writeIntLE = BP.writeIntLE
	  arr.writeIntBE = BP.writeIntBE
	  arr.writeInt8 = BP.writeInt8
	  arr.writeInt16LE = BP.writeInt16LE
	  arr.writeInt16BE = BP.writeInt16BE
	  arr.writeInt32LE = BP.writeInt32LE
	  arr.writeInt32BE = BP.writeInt32BE
	  arr.writeFloatLE = BP.writeFloatLE
	  arr.writeFloatBE = BP.writeFloatBE
	  arr.writeDoubleLE = BP.writeDoubleLE
	  arr.writeDoubleBE = BP.writeDoubleBE
	  arr.fill = BP.fill
	  arr.inspect = BP.inspect
	  arr.toArrayBuffer = BP.toArrayBuffer
	
	  return arr
	}
	
	var INVALID_BASE64_RE = /[^+\/0-9A-Za-z-_]/g
	
	function base64clean (str) {
	  // Node strips out invalid characters like \n and \t from the string, base64-js does not
	  str = stringtrim(str).replace(INVALID_BASE64_RE, '')
	  // Node converts strings with length < 2 to ''
	  if (str.length < 2) return ''
	  // Node allows for non-padded base64 strings (missing trailing ===), base64-js does not
	  while (str.length % 4 !== 0) {
	    str = str + '='
	  }
	  return str
	}
	
	function stringtrim (str) {
	  if (str.trim) return str.trim()
	  return str.replace(/^\s+|\s+$/g, '')
	}
	
	function toHex (n) {
	  if (n < 16) return '0' + n.toString(16)
	  return n.toString(16)
	}
	
	function utf8ToBytes (string, units) {
	  units = units || Infinity
	  var codePoint
	  var length = string.length
	  var leadSurrogate = null
	  var bytes = []
	
	  for (var i = 0; i < length; i++) {
	    codePoint = string.charCodeAt(i)
	
	    // is surrogate component
	    if (codePoint > 0xD7FF && codePoint < 0xE000) {
	      // last char was a lead
	      if (!leadSurrogate) {
	        // no lead yet
	        if (codePoint > 0xDBFF) {
	          // unexpected trail
	          if ((units -= 3) > -1) bytes.push(0xEF, 0xBF, 0xBD)
	          continue
	        } else if (i + 1 === length) {
	          // unpaired lead
	          if ((units -= 3) > -1) bytes.push(0xEF, 0xBF, 0xBD)
	          continue
	        }
	
	        // valid lead
	        leadSurrogate = codePoint
	
	        continue
	      }
	
	      // 2 leads in a row
	      if (codePoint < 0xDC00) {
	        if ((units -= 3) > -1) bytes.push(0xEF, 0xBF, 0xBD)
	        leadSurrogate = codePoint
	        continue
	      }
	
	      // valid surrogate pair
	      codePoint = (leadSurrogate - 0xD800 << 10 | codePoint - 0xDC00) + 0x10000
	    } else if (leadSurrogate) {
	      // valid bmp char, but last char was a lead
	      if ((units -= 3) > -1) bytes.push(0xEF, 0xBF, 0xBD)
	    }
	
	    leadSurrogate = null
	
	    // encode utf8
	    if (codePoint < 0x80) {
	      if ((units -= 1) < 0) break
	      bytes.push(codePoint)
	    } else if (codePoint < 0x800) {
	      if ((units -= 2) < 0) break
	      bytes.push(
	        codePoint >> 0x6 | 0xC0,
	        codePoint & 0x3F | 0x80
	      )
	    } else if (codePoint < 0x10000) {
	      if ((units -= 3) < 0) break
	      bytes.push(
	        codePoint >> 0xC | 0xE0,
	        codePoint >> 0x6 & 0x3F | 0x80,
	        codePoint & 0x3F | 0x80
	      )
	    } else if (codePoint < 0x110000) {
	      if ((units -= 4) < 0) break
	      bytes.push(
	        codePoint >> 0x12 | 0xF0,
	        codePoint >> 0xC & 0x3F | 0x80,
	        codePoint >> 0x6 & 0x3F | 0x80,
	        codePoint & 0x3F | 0x80
	      )
	    } else {
	      throw new Error('Invalid code point')
	    }
	  }
	
	  return bytes
	}
	
	function asciiToBytes (str) {
	  var byteArray = []
	  for (var i = 0; i < str.length; i++) {
	    // Node's code seems to be doing this and not & 0x7F..
	    byteArray.push(str.charCodeAt(i) & 0xFF)
	  }
	  return byteArray
	}
	
	function utf16leToBytes (str, units) {
	  var c, hi, lo
	  var byteArray = []
	  for (var i = 0; i < str.length; i++) {
	    if ((units -= 2) < 0) break
	
	    c = str.charCodeAt(i)
	    hi = c >> 8
	    lo = c % 256
	    byteArray.push(lo)
	    byteArray.push(hi)
	  }
	
	  return byteArray
	}
	
	function base64ToBytes (str) {
	  return base64.toByteArray(base64clean(str))
	}
	
	function blitBuffer (src, dst, offset, length) {
	  for (var i = 0; i < length; i++) {
	    if ((i + offset >= dst.length) || (i >= src.length)) break
	    dst[i + offset] = src[i]
	  }
	  return i
	}
	
	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! (webpack)/~/node-libs-browser/~/buffer/index.js */ 1646).Buffer, (function() { return this; }())))

/***/ },

/***/ 1647:
/*!*********************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/buffer/~/base64-js/lib/b64.js ***!
  \*********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var lookup = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';
	
	;(function (exports) {
		'use strict';
	
	  var Arr = (typeof Uint8Array !== 'undefined')
	    ? Uint8Array
	    : Array
	
		var PLUS   = '+'.charCodeAt(0)
		var SLASH  = '/'.charCodeAt(0)
		var NUMBER = '0'.charCodeAt(0)
		var LOWER  = 'a'.charCodeAt(0)
		var UPPER  = 'A'.charCodeAt(0)
		var PLUS_URL_SAFE = '-'.charCodeAt(0)
		var SLASH_URL_SAFE = '_'.charCodeAt(0)
	
		function decode (elt) {
			var code = elt.charCodeAt(0)
			if (code === PLUS ||
			    code === PLUS_URL_SAFE)
				return 62 // '+'
			if (code === SLASH ||
			    code === SLASH_URL_SAFE)
				return 63 // '/'
			if (code < NUMBER)
				return -1 //no match
			if (code < NUMBER + 10)
				return code - NUMBER + 26 + 26
			if (code < UPPER + 26)
				return code - UPPER
			if (code < LOWER + 26)
				return code - LOWER + 26
		}
	
		function b64ToByteArray (b64) {
			var i, j, l, tmp, placeHolders, arr
	
			if (b64.length % 4 > 0) {
				throw new Error('Invalid string. Length must be a multiple of 4')
			}
	
			// the number of equal signs (place holders)
			// if there are two placeholders, than the two characters before it
			// represent one byte
			// if there is only one, then the three characters before it represent 2 bytes
			// this is just a cheap hack to not do indexOf twice
			var len = b64.length
			placeHolders = '=' === b64.charAt(len - 2) ? 2 : '=' === b64.charAt(len - 1) ? 1 : 0
	
			// base64 is 4/3 + up to two characters of the original data
			arr = new Arr(b64.length * 3 / 4 - placeHolders)
	
			// if there are placeholders, only get up to the last complete 4 chars
			l = placeHolders > 0 ? b64.length - 4 : b64.length
	
			var L = 0
	
			function push (v) {
				arr[L++] = v
			}
	
			for (i = 0, j = 0; i < l; i += 4, j += 3) {
				tmp = (decode(b64.charAt(i)) << 18) | (decode(b64.charAt(i + 1)) << 12) | (decode(b64.charAt(i + 2)) << 6) | decode(b64.charAt(i + 3))
				push((tmp & 0xFF0000) >> 16)
				push((tmp & 0xFF00) >> 8)
				push(tmp & 0xFF)
			}
	
			if (placeHolders === 2) {
				tmp = (decode(b64.charAt(i)) << 2) | (decode(b64.charAt(i + 1)) >> 4)
				push(tmp & 0xFF)
			} else if (placeHolders === 1) {
				tmp = (decode(b64.charAt(i)) << 10) | (decode(b64.charAt(i + 1)) << 4) | (decode(b64.charAt(i + 2)) >> 2)
				push((tmp >> 8) & 0xFF)
				push(tmp & 0xFF)
			}
	
			return arr
		}
	
		function uint8ToBase64 (uint8) {
			var i,
				extraBytes = uint8.length % 3, // if we have 1 byte left, pad 2 bytes
				output = "",
				temp, length
	
			function encode (num) {
				return lookup.charAt(num)
			}
	
			function tripletToBase64 (num) {
				return encode(num >> 18 & 0x3F) + encode(num >> 12 & 0x3F) + encode(num >> 6 & 0x3F) + encode(num & 0x3F)
			}
	
			// go through the array every three bytes, we'll deal with trailing stuff later
			for (i = 0, length = uint8.length - extraBytes; i < length; i += 3) {
				temp = (uint8[i] << 16) + (uint8[i + 1] << 8) + (uint8[i + 2])
				output += tripletToBase64(temp)
			}
	
			// pad the end with zeros, but make sure to not forget the extra bytes
			switch (extraBytes) {
				case 1:
					temp = uint8[uint8.length - 1]
					output += encode(temp >> 2)
					output += encode((temp << 4) & 0x3F)
					output += '=='
					break
				case 2:
					temp = (uint8[uint8.length - 2] << 8) + (uint8[uint8.length - 1])
					output += encode(temp >> 10)
					output += encode((temp >> 4) & 0x3F)
					output += encode((temp << 2) & 0x3F)
					output += '='
					break
			}
	
			return output
		}
	
		exports.toByteArray = b64ToByteArray
		exports.fromByteArray = uint8ToBase64
	}( false ? (this.base64js = {}) : exports))


/***/ },

/***/ 1648:
/*!*****************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/buffer/~/ieee754/index.js ***!
  \*****************************************************************/
/***/ function(module, exports) {

	exports.read = function (buffer, offset, isLE, mLen, nBytes) {
	  var e, m
	  var eLen = nBytes * 8 - mLen - 1
	  var eMax = (1 << eLen) - 1
	  var eBias = eMax >> 1
	  var nBits = -7
	  var i = isLE ? (nBytes - 1) : 0
	  var d = isLE ? -1 : 1
	  var s = buffer[offset + i]
	
	  i += d
	
	  e = s & ((1 << (-nBits)) - 1)
	  s >>= (-nBits)
	  nBits += eLen
	  for (; nBits > 0; e = e * 256 + buffer[offset + i], i += d, nBits -= 8) {}
	
	  m = e & ((1 << (-nBits)) - 1)
	  e >>= (-nBits)
	  nBits += mLen
	  for (; nBits > 0; m = m * 256 + buffer[offset + i], i += d, nBits -= 8) {}
	
	  if (e === 0) {
	    e = 1 - eBias
	  } else if (e === eMax) {
	    return m ? NaN : ((s ? -1 : 1) * Infinity)
	  } else {
	    m = m + Math.pow(2, mLen)
	    e = e - eBias
	  }
	  return (s ? -1 : 1) * m * Math.pow(2, e - mLen)
	}
	
	exports.write = function (buffer, value, offset, isLE, mLen, nBytes) {
	  var e, m, c
	  var eLen = nBytes * 8 - mLen - 1
	  var eMax = (1 << eLen) - 1
	  var eBias = eMax >> 1
	  var rt = (mLen === 23 ? Math.pow(2, -24) - Math.pow(2, -77) : 0)
	  var i = isLE ? 0 : (nBytes - 1)
	  var d = isLE ? 1 : -1
	  var s = value < 0 || (value === 0 && 1 / value < 0) ? 1 : 0
	
	  value = Math.abs(value)
	
	  if (isNaN(value) || value === Infinity) {
	    m = isNaN(value) ? 1 : 0
	    e = eMax
	  } else {
	    e = Math.floor(Math.log(value) / Math.LN2)
	    if (value * (c = Math.pow(2, -e)) < 1) {
	      e--
	      c *= 2
	    }
	    if (e + eBias >= 1) {
	      value += rt / c
	    } else {
	      value += rt * Math.pow(2, 1 - eBias)
	    }
	    if (value * c >= 2) {
	      e++
	      c /= 2
	    }
	
	    if (e + eBias >= eMax) {
	      m = 0
	      e = eMax
	    } else if (e + eBias >= 1) {
	      m = (value * c - 1) * Math.pow(2, mLen)
	      e = e + eBias
	    } else {
	      m = value * Math.pow(2, eBias - 1) * Math.pow(2, mLen)
	      e = 0
	    }
	  }
	
	  for (; mLen >= 8; buffer[offset + i] = m & 0xff, i += d, m /= 256, mLen -= 8) {}
	
	  e = (e << mLen) | m
	  eLen += mLen
	  for (; eLen > 0; buffer[offset + i] = e & 0xff, i += d, e /= 256, eLen -= 8) {}
	
	  buffer[offset + i - d] |= s * 128
	}


/***/ },

/***/ 1649:
/*!*****************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/buffer/~/isarray/index.js ***!
  \*****************************************************************/
/***/ function(module, exports) {

	var toString = {}.toString;
	
	module.exports = Array.isArray || function (arr) {
	  return toString.call(arr) == '[object Array]';
	};


/***/ },

/***/ 1657:
/*!******************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/index.js ***!
  \******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(Buffer) {var rng = __webpack_require__(/*! ./rng */ 1658)
	
	function error () {
	  var m = [].slice.call(arguments).join(' ')
	  throw new Error([
	    m,
	    'we accept pull requests',
	    'http://github.com/dominictarr/crypto-browserify'
	    ].join('\n'))
	}
	
	exports.createHash = __webpack_require__(/*! ./create-hash */ 1660)
	
	exports.createHmac = __webpack_require__(/*! ./create-hmac */ 1672)
	
	exports.randomBytes = function(size, callback) {
	  if (callback && callback.call) {
	    try {
	      callback.call(this, undefined, new Buffer(rng(size)))
	    } catch (err) { callback(err) }
	  } else {
	    return new Buffer(rng(size))
	  }
	}
	
	function each(a, f) {
	  for(var i in a)
	    f(a[i], i)
	}
	
	exports.getHashes = function () {
	  return ['sha1', 'sha256', 'sha512', 'md5', 'rmd160']
	}
	
	var p = __webpack_require__(/*! ./pbkdf2 */ 1673)(exports)
	exports.pbkdf2 = p.pbkdf2
	exports.pbkdf2Sync = p.pbkdf2Sync
	
	
	// the least I can do is make error messages for the rest of the node.js/crypto api.
	each(['createCredentials'
	, 'createCipher'
	, 'createCipheriv'
	, 'createDecipher'
	, 'createDecipheriv'
	, 'createSign'
	, 'createVerify'
	, 'createDiffieHellman'
	], function (name) {
	  exports[name] = function () {
	    error('sorry,', name, 'is not implemented yet')
	  }
	})
	
	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! (webpack)/~/node-libs-browser/~/buffer/index.js */ 1646).Buffer))

/***/ },

/***/ 1658:
/*!****************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/rng.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global, Buffer) {(function() {
	  var g = ('undefined' === typeof window ? global : window) || {}
	  _crypto = (
	    g.crypto || g.msCrypto || __webpack_require__(/*! crypto */ 1659)
	  )
	  module.exports = function(size) {
	    // Modern Browsers
	    if(_crypto.getRandomValues) {
	      var bytes = new Buffer(size); //in browserify, this is an extended Uint8Array
	      /* This will not work in older browsers.
	       * See https://developer.mozilla.org/en-US/docs/Web/API/window.crypto.getRandomValues
	       */
	    
	      _crypto.getRandomValues(bytes);
	      return bytes;
	    }
	    else if (_crypto.randomBytes) {
	      return _crypto.randomBytes(size)
	    }
	    else
	      throw new Error(
	        'secure random number generation not supported by this browser\n'+
	        'use chrome, FireFox or Internet Explorer 11'
	      )
	  }
	}())
	
	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }()), __webpack_require__(/*! (webpack)/~/node-libs-browser/~/buffer/index.js */ 1646).Buffer))

/***/ },

/***/ 1659:
/*!************************!*\
  !*** crypto (ignored) ***!
  \************************/
/***/ function(module, exports) {

	/* (ignored) */

/***/ },

/***/ 1660:
/*!************************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/create-hash.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(Buffer) {var createHash = __webpack_require__(/*! sha.js */ 1661)
	
	var md5 = toConstructor(__webpack_require__(/*! ./md5 */ 1669))
	var rmd160 = toConstructor(__webpack_require__(/*! ripemd160 */ 1671))
	
	function toConstructor (fn) {
	  return function () {
	    var buffers = []
	    var m= {
	      update: function (data, enc) {
	        if(!Buffer.isBuffer(data)) data = new Buffer(data, enc)
	        buffers.push(data)
	        return this
	      },
	      digest: function (enc) {
	        var buf = Buffer.concat(buffers)
	        var r = fn(buf)
	        buffers = null
	        return enc ? r.toString(enc) : r
	      }
	    }
	    return m
	  }
	}
	
	module.exports = function (alg) {
	  if('md5' === alg) return new md5()
	  if('rmd160' === alg) return new rmd160()
	  return createHash(alg)
	}
	
	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! (webpack)/~/node-libs-browser/~/buffer/index.js */ 1646).Buffer))

/***/ },

/***/ 1661:
/*!***************************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/~/sha.js/index.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var exports = module.exports = function (alg) {
	  var Alg = exports[alg]
	  if(!Alg) throw new Error(alg + ' is not supported (we accept pull requests)')
	  return new Alg()
	}
	
	var Buffer = __webpack_require__(/*! buffer */ 1646).Buffer
	var Hash   = __webpack_require__(/*! ./hash */ 1662)(Buffer)
	
	exports.sha1 = __webpack_require__(/*! ./sha1 */ 1663)(Buffer, Hash)
	exports.sha256 = __webpack_require__(/*! ./sha256 */ 1667)(Buffer, Hash)
	exports.sha512 = __webpack_require__(/*! ./sha512 */ 1668)(Buffer, Hash)


/***/ },

/***/ 1662:
/*!**************************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/~/sha.js/hash.js ***!
  \**************************************************************************/
/***/ function(module, exports) {

	module.exports = function (Buffer) {
	
	  //prototype class for hash functions
	  function Hash (blockSize, finalSize) {
	    this._block = new Buffer(blockSize) //new Uint32Array(blockSize/4)
	    this._finalSize = finalSize
	    this._blockSize = blockSize
	    this._len = 0
	    this._s = 0
	  }
	
	  Hash.prototype.init = function () {
	    this._s = 0
	    this._len = 0
	  }
	
	  Hash.prototype.update = function (data, enc) {
	    if ("string" === typeof data) {
	      enc = enc || "utf8"
	      data = new Buffer(data, enc)
	    }
	
	    var l = this._len += data.length
	    var s = this._s = (this._s || 0)
	    var f = 0
	    var buffer = this._block
	
	    while (s < l) {
	      var t = Math.min(data.length, f + this._blockSize - (s % this._blockSize))
	      var ch = (t - f)
	
	      for (var i = 0; i < ch; i++) {
	        buffer[(s % this._blockSize) + i] = data[i + f]
	      }
	
	      s += ch
	      f += ch
	
	      if ((s % this._blockSize) === 0) {
	        this._update(buffer)
	      }
	    }
	    this._s = s
	
	    return this
	  }
	
	  Hash.prototype.digest = function (enc) {
	    // Suppose the length of the message M, in bits, is l
	    var l = this._len * 8
	
	    // Append the bit 1 to the end of the message
	    this._block[this._len % this._blockSize] = 0x80
	
	    // and then k zero bits, where k is the smallest non-negative solution to the equation (l + 1 + k) === finalSize mod blockSize
	    this._block.fill(0, this._len % this._blockSize + 1)
	
	    if (l % (this._blockSize * 8) >= this._finalSize * 8) {
	      this._update(this._block)
	      this._block.fill(0)
	    }
	
	    // to this append the block which is equal to the number l written in binary
	    // TODO: handle case where l is > Math.pow(2, 29)
	    this._block.writeInt32BE(l, this._blockSize - 4)
	
	    var hash = this._update(this._block) || this._hash()
	
	    return enc ? hash.toString(enc) : hash
	  }
	
	  Hash.prototype._update = function () {
	    throw new Error('_update must be implemented by subclass')
	  }
	
	  return Hash
	}


/***/ },

/***/ 1663:
/*!**************************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/~/sha.js/sha1.js ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/*
	 * A JavaScript implementation of the Secure Hash Algorithm, SHA-1, as defined
	 * in FIPS PUB 180-1
	 * Version 2.1a Copyright Paul Johnston 2000 - 2002.
	 * Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
	 * Distributed under the BSD License
	 * See http://pajhome.org.uk/crypt/md5 for details.
	 */
	
	var inherits = __webpack_require__(/*! util */ 1664).inherits
	
	module.exports = function (Buffer, Hash) {
	
	  var A = 0|0
	  var B = 4|0
	  var C = 8|0
	  var D = 12|0
	  var E = 16|0
	
	  var W = new (typeof Int32Array === 'undefined' ? Array : Int32Array)(80)
	
	  var POOL = []
	
	  function Sha1 () {
	    if(POOL.length)
	      return POOL.pop().init()
	
	    if(!(this instanceof Sha1)) return new Sha1()
	    this._w = W
	    Hash.call(this, 16*4, 14*4)
	
	    this._h = null
	    this.init()
	  }
	
	  inherits(Sha1, Hash)
	
	  Sha1.prototype.init = function () {
	    this._a = 0x67452301
	    this._b = 0xefcdab89
	    this._c = 0x98badcfe
	    this._d = 0x10325476
	    this._e = 0xc3d2e1f0
	
	    Hash.prototype.init.call(this)
	    return this
	  }
	
	  Sha1.prototype._POOL = POOL
	  Sha1.prototype._update = function (X) {
	
	    var a, b, c, d, e, _a, _b, _c, _d, _e
	
	    a = _a = this._a
	    b = _b = this._b
	    c = _c = this._c
	    d = _d = this._d
	    e = _e = this._e
	
	    var w = this._w
	
	    for(var j = 0; j < 80; j++) {
	      var W = w[j] = j < 16 ? X.readInt32BE(j*4)
	        : rol(w[j - 3] ^ w[j -  8] ^ w[j - 14] ^ w[j - 16], 1)
	
	      var t = add(
	        add(rol(a, 5), sha1_ft(j, b, c, d)),
	        add(add(e, W), sha1_kt(j))
	      )
	
	      e = d
	      d = c
	      c = rol(b, 30)
	      b = a
	      a = t
	    }
	
	    this._a = add(a, _a)
	    this._b = add(b, _b)
	    this._c = add(c, _c)
	    this._d = add(d, _d)
	    this._e = add(e, _e)
	  }
	
	  Sha1.prototype._hash = function () {
	    if(POOL.length < 100) POOL.push(this)
	    var H = new Buffer(20)
	    //console.log(this._a|0, this._b|0, this._c|0, this._d|0, this._e|0)
	    H.writeInt32BE(this._a|0, A)
	    H.writeInt32BE(this._b|0, B)
	    H.writeInt32BE(this._c|0, C)
	    H.writeInt32BE(this._d|0, D)
	    H.writeInt32BE(this._e|0, E)
	    return H
	  }
	
	  /*
	   * Perform the appropriate triplet combination function for the current
	   * iteration
	   */
	  function sha1_ft(t, b, c, d) {
	    if(t < 20) return (b & c) | ((~b) & d);
	    if(t < 40) return b ^ c ^ d;
	    if(t < 60) return (b & c) | (b & d) | (c & d);
	    return b ^ c ^ d;
	  }
	
	  /*
	   * Determine the appropriate additive constant for the current iteration
	   */
	  function sha1_kt(t) {
	    return (t < 20) ?  1518500249 : (t < 40) ?  1859775393 :
	           (t < 60) ? -1894007588 : -899497514;
	  }
	
	  /*
	   * Add integers, wrapping at 2^32. This uses 16-bit operations internally
	   * to work around bugs in some JS interpreters.
	   * //dominictarr: this is 10 years old, so maybe this can be dropped?)
	   *
	   */
	  function add(x, y) {
	    return (x + y ) | 0
	  //lets see how this goes on testling.
	  //  var lsw = (x & 0xFFFF) + (y & 0xFFFF);
	  //  var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
	  //  return (msw << 16) | (lsw & 0xFFFF);
	  }
	
	  /*
	   * Bitwise rotate a 32-bit number to the left.
	   */
	  function rol(num, cnt) {
	    return (num << cnt) | (num >>> (32 - cnt));
	  }
	
	  return Sha1
	}


/***/ },

/***/ 1664:
/*!****************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/util/util.js ***!
  \****************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global, process) {// Copyright Joyent, Inc. and other Node contributors.
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
	
	var formatRegExp = /%[sdj%]/g;
	exports.format = function(f) {
	  if (!isString(f)) {
	    var objects = [];
	    for (var i = 0; i < arguments.length; i++) {
	      objects.push(inspect(arguments[i]));
	    }
	    return objects.join(' ');
	  }
	
	  var i = 1;
	  var args = arguments;
	  var len = args.length;
	  var str = String(f).replace(formatRegExp, function(x) {
	    if (x === '%%') return '%';
	    if (i >= len) return x;
	    switch (x) {
	      case '%s': return String(args[i++]);
	      case '%d': return Number(args[i++]);
	      case '%j':
	        try {
	          return JSON.stringify(args[i++]);
	        } catch (_) {
	          return '[Circular]';
	        }
	      default:
	        return x;
	    }
	  });
	  for (var x = args[i]; i < len; x = args[++i]) {
	    if (isNull(x) || !isObject(x)) {
	      str += ' ' + x;
	    } else {
	      str += ' ' + inspect(x);
	    }
	  }
	  return str;
	};
	
	
	// Mark that a method should not be used.
	// Returns a modified function which warns once by default.
	// If --no-deprecation is set, then it is a no-op.
	exports.deprecate = function(fn, msg) {
	  // Allow for deprecating things in the process of starting up.
	  if (isUndefined(global.process)) {
	    return function() {
	      return exports.deprecate(fn, msg).apply(this, arguments);
	    };
	  }
	
	  if (process.noDeprecation === true) {
	    return fn;
	  }
	
	  var warned = false;
	  function deprecated() {
	    if (!warned) {
	      if (process.throwDeprecation) {
	        throw new Error(msg);
	      } else if (process.traceDeprecation) {
	        console.trace(msg);
	      } else {
	        console.error(msg);
	      }
	      warned = true;
	    }
	    return fn.apply(this, arguments);
	  }
	
	  return deprecated;
	};
	
	
	var debugs = {};
	var debugEnviron;
	exports.debuglog = function(set) {
	  if (isUndefined(debugEnviron))
	    debugEnviron = process.env.NODE_DEBUG || '';
	  set = set.toUpperCase();
	  if (!debugs[set]) {
	    if (new RegExp('\\b' + set + '\\b', 'i').test(debugEnviron)) {
	      var pid = process.pid;
	      debugs[set] = function() {
	        var msg = exports.format.apply(exports, arguments);
	        console.error('%s %d: %s', set, pid, msg);
	      };
	    } else {
	      debugs[set] = function() {};
	    }
	  }
	  return debugs[set];
	};
	
	
	/**
	 * Echos the value of a value. Trys to print the value out
	 * in the best way possible given the different types.
	 *
	 * @param {Object} obj The object to print out.
	 * @param {Object} opts Optional options object that alters the output.
	 */
	/* legacy: obj, showHidden, depth, colors*/
	function inspect(obj, opts) {
	  // default options
	  var ctx = {
	    seen: [],
	    stylize: stylizeNoColor
	  };
	  // legacy...
	  if (arguments.length >= 3) ctx.depth = arguments[2];
	  if (arguments.length >= 4) ctx.colors = arguments[3];
	  if (isBoolean(opts)) {
	    // legacy...
	    ctx.showHidden = opts;
	  } else if (opts) {
	    // got an "options" object
	    exports._extend(ctx, opts);
	  }
	  // set default options
	  if (isUndefined(ctx.showHidden)) ctx.showHidden = false;
	  if (isUndefined(ctx.depth)) ctx.depth = 2;
	  if (isUndefined(ctx.colors)) ctx.colors = false;
	  if (isUndefined(ctx.customInspect)) ctx.customInspect = true;
	  if (ctx.colors) ctx.stylize = stylizeWithColor;
	  return formatValue(ctx, obj, ctx.depth);
	}
	exports.inspect = inspect;
	
	
	// http://en.wikipedia.org/wiki/ANSI_escape_code#graphics
	inspect.colors = {
	  'bold' : [1, 22],
	  'italic' : [3, 23],
	  'underline' : [4, 24],
	  'inverse' : [7, 27],
	  'white' : [37, 39],
	  'grey' : [90, 39],
	  'black' : [30, 39],
	  'blue' : [34, 39],
	  'cyan' : [36, 39],
	  'green' : [32, 39],
	  'magenta' : [35, 39],
	  'red' : [31, 39],
	  'yellow' : [33, 39]
	};
	
	// Don't use 'blue' not visible on cmd.exe
	inspect.styles = {
	  'special': 'cyan',
	  'number': 'yellow',
	  'boolean': 'yellow',
	  'undefined': 'grey',
	  'null': 'bold',
	  'string': 'green',
	  'date': 'magenta',
	  // "name": intentionally not styling
	  'regexp': 'red'
	};
	
	
	function stylizeWithColor(str, styleType) {
	  var style = inspect.styles[styleType];
	
	  if (style) {
	    return '\u001b[' + inspect.colors[style][0] + 'm' + str +
	           '\u001b[' + inspect.colors[style][1] + 'm';
	  } else {
	    return str;
	  }
	}
	
	
	function stylizeNoColor(str, styleType) {
	  return str;
	}
	
	
	function arrayToHash(array) {
	  var hash = {};
	
	  array.forEach(function(val, idx) {
	    hash[val] = true;
	  });
	
	  return hash;
	}
	
	
	function formatValue(ctx, value, recurseTimes) {
	  // Provide a hook for user-specified inspect functions.
	  // Check that value is an object with an inspect function on it
	  if (ctx.customInspect &&
	      value &&
	      isFunction(value.inspect) &&
	      // Filter out the util module, it's inspect function is special
	      value.inspect !== exports.inspect &&
	      // Also filter out any prototype objects using the circular check.
	      !(value.constructor && value.constructor.prototype === value)) {
	    var ret = value.inspect(recurseTimes, ctx);
	    if (!isString(ret)) {
	      ret = formatValue(ctx, ret, recurseTimes);
	    }
	    return ret;
	  }
	
	  // Primitive types cannot have properties
	  var primitive = formatPrimitive(ctx, value);
	  if (primitive) {
	    return primitive;
	  }
	
	  // Look up the keys of the object.
	  var keys = Object.keys(value);
	  var visibleKeys = arrayToHash(keys);
	
	  if (ctx.showHidden) {
	    keys = Object.getOwnPropertyNames(value);
	  }
	
	  // IE doesn't make error fields non-enumerable
	  // http://msdn.microsoft.com/en-us/library/ie/dww52sbt(v=vs.94).aspx
	  if (isError(value)
	      && (keys.indexOf('message') >= 0 || keys.indexOf('description') >= 0)) {
	    return formatError(value);
	  }
	
	  // Some type of object without properties can be shortcutted.
	  if (keys.length === 0) {
	    if (isFunction(value)) {
	      var name = value.name ? ': ' + value.name : '';
	      return ctx.stylize('[Function' + name + ']', 'special');
	    }
	    if (isRegExp(value)) {
	      return ctx.stylize(RegExp.prototype.toString.call(value), 'regexp');
	    }
	    if (isDate(value)) {
	      return ctx.stylize(Date.prototype.toString.call(value), 'date');
	    }
	    if (isError(value)) {
	      return formatError(value);
	    }
	  }
	
	  var base = '', array = false, braces = ['{', '}'];
	
	  // Make Array say that they are Array
	  if (isArray(value)) {
	    array = true;
	    braces = ['[', ']'];
	  }
	
	  // Make functions say that they are functions
	  if (isFunction(value)) {
	    var n = value.name ? ': ' + value.name : '';
	    base = ' [Function' + n + ']';
	  }
	
	  // Make RegExps say that they are RegExps
	  if (isRegExp(value)) {
	    base = ' ' + RegExp.prototype.toString.call(value);
	  }
	
	  // Make dates with properties first say the date
	  if (isDate(value)) {
	    base = ' ' + Date.prototype.toUTCString.call(value);
	  }
	
	  // Make error with message first say the error
	  if (isError(value)) {
	    base = ' ' + formatError(value);
	  }
	
	  if (keys.length === 0 && (!array || value.length == 0)) {
	    return braces[0] + base + braces[1];
	  }
	
	  if (recurseTimes < 0) {
	    if (isRegExp(value)) {
	      return ctx.stylize(RegExp.prototype.toString.call(value), 'regexp');
	    } else {
	      return ctx.stylize('[Object]', 'special');
	    }
	  }
	
	  ctx.seen.push(value);
	
	  var output;
	  if (array) {
	    output = formatArray(ctx, value, recurseTimes, visibleKeys, keys);
	  } else {
	    output = keys.map(function(key) {
	      return formatProperty(ctx, value, recurseTimes, visibleKeys, key, array);
	    });
	  }
	
	  ctx.seen.pop();
	
	  return reduceToSingleString(output, base, braces);
	}
	
	
	function formatPrimitive(ctx, value) {
	  if (isUndefined(value))
	    return ctx.stylize('undefined', 'undefined');
	  if (isString(value)) {
	    var simple = '\'' + JSON.stringify(value).replace(/^"|"$/g, '')
	                                             .replace(/'/g, "\\'")
	                                             .replace(/\\"/g, '"') + '\'';
	    return ctx.stylize(simple, 'string');
	  }
	  if (isNumber(value))
	    return ctx.stylize('' + value, 'number');
	  if (isBoolean(value))
	    return ctx.stylize('' + value, 'boolean');
	  // For some reason typeof null is "object", so special case here.
	  if (isNull(value))
	    return ctx.stylize('null', 'null');
	}
	
	
	function formatError(value) {
	  return '[' + Error.prototype.toString.call(value) + ']';
	}
	
	
	function formatArray(ctx, value, recurseTimes, visibleKeys, keys) {
	  var output = [];
	  for (var i = 0, l = value.length; i < l; ++i) {
	    if (hasOwnProperty(value, String(i))) {
	      output.push(formatProperty(ctx, value, recurseTimes, visibleKeys,
	          String(i), true));
	    } else {
	      output.push('');
	    }
	  }
	  keys.forEach(function(key) {
	    if (!key.match(/^\d+$/)) {
	      output.push(formatProperty(ctx, value, recurseTimes, visibleKeys,
	          key, true));
	    }
	  });
	  return output;
	}
	
	
	function formatProperty(ctx, value, recurseTimes, visibleKeys, key, array) {
	  var name, str, desc;
	  desc = Object.getOwnPropertyDescriptor(value, key) || { value: value[key] };
	  if (desc.get) {
	    if (desc.set) {
	      str = ctx.stylize('[Getter/Setter]', 'special');
	    } else {
	      str = ctx.stylize('[Getter]', 'special');
	    }
	  } else {
	    if (desc.set) {
	      str = ctx.stylize('[Setter]', 'special');
	    }
	  }
	  if (!hasOwnProperty(visibleKeys, key)) {
	    name = '[' + key + ']';
	  }
	  if (!str) {
	    if (ctx.seen.indexOf(desc.value) < 0) {
	      if (isNull(recurseTimes)) {
	        str = formatValue(ctx, desc.value, null);
	      } else {
	        str = formatValue(ctx, desc.value, recurseTimes - 1);
	      }
	      if (str.indexOf('\n') > -1) {
	        if (array) {
	          str = str.split('\n').map(function(line) {
	            return '  ' + line;
	          }).join('\n').substr(2);
	        } else {
	          str = '\n' + str.split('\n').map(function(line) {
	            return '   ' + line;
	          }).join('\n');
	        }
	      }
	    } else {
	      str = ctx.stylize('[Circular]', 'special');
	    }
	  }
	  if (isUndefined(name)) {
	    if (array && key.match(/^\d+$/)) {
	      return str;
	    }
	    name = JSON.stringify('' + key);
	    if (name.match(/^"([a-zA-Z_][a-zA-Z_0-9]*)"$/)) {
	      name = name.substr(1, name.length - 2);
	      name = ctx.stylize(name, 'name');
	    } else {
	      name = name.replace(/'/g, "\\'")
	                 .replace(/\\"/g, '"')
	                 .replace(/(^"|"$)/g, "'");
	      name = ctx.stylize(name, 'string');
	    }
	  }
	
	  return name + ': ' + str;
	}
	
	
	function reduceToSingleString(output, base, braces) {
	  var numLinesEst = 0;
	  var length = output.reduce(function(prev, cur) {
	    numLinesEst++;
	    if (cur.indexOf('\n') >= 0) numLinesEst++;
	    return prev + cur.replace(/\u001b\[\d\d?m/g, '').length + 1;
	  }, 0);
	
	  if (length > 60) {
	    return braces[0] +
	           (base === '' ? '' : base + '\n ') +
	           ' ' +
	           output.join(',\n  ') +
	           ' ' +
	           braces[1];
	  }
	
	  return braces[0] + base + ' ' + output.join(', ') + ' ' + braces[1];
	}
	
	
	// NOTE: These type checking functions intentionally don't use `instanceof`
	// because it is fragile and can be easily faked with `Object.create()`.
	function isArray(ar) {
	  return Array.isArray(ar);
	}
	exports.isArray = isArray;
	
	function isBoolean(arg) {
	  return typeof arg === 'boolean';
	}
	exports.isBoolean = isBoolean;
	
	function isNull(arg) {
	  return arg === null;
	}
	exports.isNull = isNull;
	
	function isNullOrUndefined(arg) {
	  return arg == null;
	}
	exports.isNullOrUndefined = isNullOrUndefined;
	
	function isNumber(arg) {
	  return typeof arg === 'number';
	}
	exports.isNumber = isNumber;
	
	function isString(arg) {
	  return typeof arg === 'string';
	}
	exports.isString = isString;
	
	function isSymbol(arg) {
	  return typeof arg === 'symbol';
	}
	exports.isSymbol = isSymbol;
	
	function isUndefined(arg) {
	  return arg === void 0;
	}
	exports.isUndefined = isUndefined;
	
	function isRegExp(re) {
	  return isObject(re) && objectToString(re) === '[object RegExp]';
	}
	exports.isRegExp = isRegExp;
	
	function isObject(arg) {
	  return typeof arg === 'object' && arg !== null;
	}
	exports.isObject = isObject;
	
	function isDate(d) {
	  return isObject(d) && objectToString(d) === '[object Date]';
	}
	exports.isDate = isDate;
	
	function isError(e) {
	  return isObject(e) &&
	      (objectToString(e) === '[object Error]' || e instanceof Error);
	}
	exports.isError = isError;
	
	function isFunction(arg) {
	  return typeof arg === 'function';
	}
	exports.isFunction = isFunction;
	
	function isPrimitive(arg) {
	  return arg === null ||
	         typeof arg === 'boolean' ||
	         typeof arg === 'number' ||
	         typeof arg === 'string' ||
	         typeof arg === 'symbol' ||  // ES6 symbol
	         typeof arg === 'undefined';
	}
	exports.isPrimitive = isPrimitive;
	
	exports.isBuffer = __webpack_require__(/*! ./support/isBuffer */ 1665);
	
	function objectToString(o) {
	  return Object.prototype.toString.call(o);
	}
	
	
	function pad(n) {
	  return n < 10 ? '0' + n.toString(10) : n.toString(10);
	}
	
	
	var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep',
	              'Oct', 'Nov', 'Dec'];
	
	// 26 Feb 16:19:34
	function timestamp() {
	  var d = new Date();
	  var time = [pad(d.getHours()),
	              pad(d.getMinutes()),
	              pad(d.getSeconds())].join(':');
	  return [d.getDate(), months[d.getMonth()], time].join(' ');
	}
	
	
	// log is just a thin wrapper to console.log that prepends a timestamp
	exports.log = function() {
	  console.log('%s - %s', timestamp(), exports.format.apply(exports, arguments));
	};
	
	
	/**
	 * Inherit the prototype methods from one constructor into another.
	 *
	 * The Function.prototype.inherits from lang.js rewritten as a standalone
	 * function (not on Function.prototype). NOTE: If this file is to be loaded
	 * during bootstrapping this function needs to be rewritten using some native
	 * functions as prototype setup using normal JavaScript does not work as
	 * expected during bootstrapping (see mirror.js in r114903).
	 *
	 * @param {function} ctor Constructor function which needs to inherit the
	 *     prototype.
	 * @param {function} superCtor Constructor function to inherit prototype from.
	 */
	exports.inherits = __webpack_require__(/*! inherits */ 1666);
	
	exports._extend = function(origin, add) {
	  // Don't do anything if add isn't an object
	  if (!add || !isObject(add)) return origin;
	
	  var keys = Object.keys(add);
	  var i = keys.length;
	  while (i--) {
	    origin[keys[i]] = add[keys[i]];
	  }
	  return origin;
	};
	
	function hasOwnProperty(obj, prop) {
	  return Object.prototype.hasOwnProperty.call(obj, prop);
	}
	
	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }()), __webpack_require__(/*! (webpack)/~/node-libs-browser/~/process/browser.js */ 4)))

/***/ },

/***/ 1665:
/*!***********************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/util/support/isBufferBrowser.js ***!
  \***********************************************************************/
/***/ function(module, exports) {

	module.exports = function isBuffer(arg) {
	  return arg && typeof arg === 'object'
	    && typeof arg.copy === 'function'
	    && typeof arg.fill === 'function'
	    && typeof arg.readUInt8 === 'function';
	}

/***/ },

/***/ 1666:
/*!***************************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/util/~/inherits/inherits_browser.js ***!
  \***************************************************************************/
/***/ function(module, exports) {

	if (typeof Object.create === 'function') {
	  // implementation from standard node.js 'util' module
	  module.exports = function inherits(ctor, superCtor) {
	    ctor.super_ = superCtor
	    ctor.prototype = Object.create(superCtor.prototype, {
	      constructor: {
	        value: ctor,
	        enumerable: false,
	        writable: true,
	        configurable: true
	      }
	    });
	  };
	} else {
	  // old school shim for old browsers
	  module.exports = function inherits(ctor, superCtor) {
	    ctor.super_ = superCtor
	    var TempCtor = function () {}
	    TempCtor.prototype = superCtor.prototype
	    ctor.prototype = new TempCtor()
	    ctor.prototype.constructor = ctor
	  }
	}


/***/ },

/***/ 1667:
/*!****************************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/~/sha.js/sha256.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	
	/**
	 * A JavaScript implementation of the Secure Hash Algorithm, SHA-256, as defined
	 * in FIPS 180-2
	 * Version 2.2-beta Copyright Angel Marin, Paul Johnston 2000 - 2009.
	 * Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
	 *
	 */
	
	var inherits = __webpack_require__(/*! util */ 1664).inherits
	
	module.exports = function (Buffer, Hash) {
	
	  var K = [
	      0x428A2F98, 0x71374491, 0xB5C0FBCF, 0xE9B5DBA5,
	      0x3956C25B, 0x59F111F1, 0x923F82A4, 0xAB1C5ED5,
	      0xD807AA98, 0x12835B01, 0x243185BE, 0x550C7DC3,
	      0x72BE5D74, 0x80DEB1FE, 0x9BDC06A7, 0xC19BF174,
	      0xE49B69C1, 0xEFBE4786, 0x0FC19DC6, 0x240CA1CC,
	      0x2DE92C6F, 0x4A7484AA, 0x5CB0A9DC, 0x76F988DA,
	      0x983E5152, 0xA831C66D, 0xB00327C8, 0xBF597FC7,
	      0xC6E00BF3, 0xD5A79147, 0x06CA6351, 0x14292967,
	      0x27B70A85, 0x2E1B2138, 0x4D2C6DFC, 0x53380D13,
	      0x650A7354, 0x766A0ABB, 0x81C2C92E, 0x92722C85,
	      0xA2BFE8A1, 0xA81A664B, 0xC24B8B70, 0xC76C51A3,
	      0xD192E819, 0xD6990624, 0xF40E3585, 0x106AA070,
	      0x19A4C116, 0x1E376C08, 0x2748774C, 0x34B0BCB5,
	      0x391C0CB3, 0x4ED8AA4A, 0x5B9CCA4F, 0x682E6FF3,
	      0x748F82EE, 0x78A5636F, 0x84C87814, 0x8CC70208,
	      0x90BEFFFA, 0xA4506CEB, 0xBEF9A3F7, 0xC67178F2
	    ]
	
	  var W = new Array(64)
	
	  function Sha256() {
	    this.init()
	
	    this._w = W //new Array(64)
	
	    Hash.call(this, 16*4, 14*4)
	  }
	
	  inherits(Sha256, Hash)
	
	  Sha256.prototype.init = function () {
	
	    this._a = 0x6a09e667|0
	    this._b = 0xbb67ae85|0
	    this._c = 0x3c6ef372|0
	    this._d = 0xa54ff53a|0
	    this._e = 0x510e527f|0
	    this._f = 0x9b05688c|0
	    this._g = 0x1f83d9ab|0
	    this._h = 0x5be0cd19|0
	
	    this._len = this._s = 0
	
	    return this
	  }
	
	  function S (X, n) {
	    return (X >>> n) | (X << (32 - n));
	  }
	
	  function R (X, n) {
	    return (X >>> n);
	  }
	
	  function Ch (x, y, z) {
	    return ((x & y) ^ ((~x) & z));
	  }
	
	  function Maj (x, y, z) {
	    return ((x & y) ^ (x & z) ^ (y & z));
	  }
	
	  function Sigma0256 (x) {
	    return (S(x, 2) ^ S(x, 13) ^ S(x, 22));
	  }
	
	  function Sigma1256 (x) {
	    return (S(x, 6) ^ S(x, 11) ^ S(x, 25));
	  }
	
	  function Gamma0256 (x) {
	    return (S(x, 7) ^ S(x, 18) ^ R(x, 3));
	  }
	
	  function Gamma1256 (x) {
	    return (S(x, 17) ^ S(x, 19) ^ R(x, 10));
	  }
	
	  Sha256.prototype._update = function(M) {
	
	    var W = this._w
	    var a, b, c, d, e, f, g, h
	    var T1, T2
	
	    a = this._a | 0
	    b = this._b | 0
	    c = this._c | 0
	    d = this._d | 0
	    e = this._e | 0
	    f = this._f | 0
	    g = this._g | 0
	    h = this._h | 0
	
	    for (var j = 0; j < 64; j++) {
	      var w = W[j] = j < 16
	        ? M.readInt32BE(j * 4)
	        : Gamma1256(W[j - 2]) + W[j - 7] + Gamma0256(W[j - 15]) + W[j - 16]
	
	      T1 = h + Sigma1256(e) + Ch(e, f, g) + K[j] + w
	
	      T2 = Sigma0256(a) + Maj(a, b, c);
	      h = g; g = f; f = e; e = d + T1; d = c; c = b; b = a; a = T1 + T2;
	    }
	
	    this._a = (a + this._a) | 0
	    this._b = (b + this._b) | 0
	    this._c = (c + this._c) | 0
	    this._d = (d + this._d) | 0
	    this._e = (e + this._e) | 0
	    this._f = (f + this._f) | 0
	    this._g = (g + this._g) | 0
	    this._h = (h + this._h) | 0
	
	  };
	
	  Sha256.prototype._hash = function () {
	    var H = new Buffer(32)
	
	    H.writeInt32BE(this._a,  0)
	    H.writeInt32BE(this._b,  4)
	    H.writeInt32BE(this._c,  8)
	    H.writeInt32BE(this._d, 12)
	    H.writeInt32BE(this._e, 16)
	    H.writeInt32BE(this._f, 20)
	    H.writeInt32BE(this._g, 24)
	    H.writeInt32BE(this._h, 28)
	
	    return H
	  }
	
	  return Sha256
	
	}


/***/ },

/***/ 1668:
/*!****************************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/~/sha.js/sha512.js ***!
  \****************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var inherits = __webpack_require__(/*! util */ 1664).inherits
	
	module.exports = function (Buffer, Hash) {
	  var K = [
	    0x428a2f98, 0xd728ae22, 0x71374491, 0x23ef65cd,
	    0xb5c0fbcf, 0xec4d3b2f, 0xe9b5dba5, 0x8189dbbc,
	    0x3956c25b, 0xf348b538, 0x59f111f1, 0xb605d019,
	    0x923f82a4, 0xaf194f9b, 0xab1c5ed5, 0xda6d8118,
	    0xd807aa98, 0xa3030242, 0x12835b01, 0x45706fbe,
	    0x243185be, 0x4ee4b28c, 0x550c7dc3, 0xd5ffb4e2,
	    0x72be5d74, 0xf27b896f, 0x80deb1fe, 0x3b1696b1,
	    0x9bdc06a7, 0x25c71235, 0xc19bf174, 0xcf692694,
	    0xe49b69c1, 0x9ef14ad2, 0xefbe4786, 0x384f25e3,
	    0x0fc19dc6, 0x8b8cd5b5, 0x240ca1cc, 0x77ac9c65,
	    0x2de92c6f, 0x592b0275, 0x4a7484aa, 0x6ea6e483,
	    0x5cb0a9dc, 0xbd41fbd4, 0x76f988da, 0x831153b5,
	    0x983e5152, 0xee66dfab, 0xa831c66d, 0x2db43210,
	    0xb00327c8, 0x98fb213f, 0xbf597fc7, 0xbeef0ee4,
	    0xc6e00bf3, 0x3da88fc2, 0xd5a79147, 0x930aa725,
	    0x06ca6351, 0xe003826f, 0x14292967, 0x0a0e6e70,
	    0x27b70a85, 0x46d22ffc, 0x2e1b2138, 0x5c26c926,
	    0x4d2c6dfc, 0x5ac42aed, 0x53380d13, 0x9d95b3df,
	    0x650a7354, 0x8baf63de, 0x766a0abb, 0x3c77b2a8,
	    0x81c2c92e, 0x47edaee6, 0x92722c85, 0x1482353b,
	    0xa2bfe8a1, 0x4cf10364, 0xa81a664b, 0xbc423001,
	    0xc24b8b70, 0xd0f89791, 0xc76c51a3, 0x0654be30,
	    0xd192e819, 0xd6ef5218, 0xd6990624, 0x5565a910,
	    0xf40e3585, 0x5771202a, 0x106aa070, 0x32bbd1b8,
	    0x19a4c116, 0xb8d2d0c8, 0x1e376c08, 0x5141ab53,
	    0x2748774c, 0xdf8eeb99, 0x34b0bcb5, 0xe19b48a8,
	    0x391c0cb3, 0xc5c95a63, 0x4ed8aa4a, 0xe3418acb,
	    0x5b9cca4f, 0x7763e373, 0x682e6ff3, 0xd6b2b8a3,
	    0x748f82ee, 0x5defb2fc, 0x78a5636f, 0x43172f60,
	    0x84c87814, 0xa1f0ab72, 0x8cc70208, 0x1a6439ec,
	    0x90befffa, 0x23631e28, 0xa4506ceb, 0xde82bde9,
	    0xbef9a3f7, 0xb2c67915, 0xc67178f2, 0xe372532b,
	    0xca273ece, 0xea26619c, 0xd186b8c7, 0x21c0c207,
	    0xeada7dd6, 0xcde0eb1e, 0xf57d4f7f, 0xee6ed178,
	    0x06f067aa, 0x72176fba, 0x0a637dc5, 0xa2c898a6,
	    0x113f9804, 0xbef90dae, 0x1b710b35, 0x131c471b,
	    0x28db77f5, 0x23047d84, 0x32caab7b, 0x40c72493,
	    0x3c9ebe0a, 0x15c9bebc, 0x431d67c4, 0x9c100d4c,
	    0x4cc5d4be, 0xcb3e42b6, 0x597f299c, 0xfc657e2a,
	    0x5fcb6fab, 0x3ad6faec, 0x6c44198c, 0x4a475817
	  ]
	
	  var W = new Array(160)
	
	  function Sha512() {
	    this.init()
	    this._w = W
	
	    Hash.call(this, 128, 112)
	  }
	
	  inherits(Sha512, Hash)
	
	  Sha512.prototype.init = function () {
	
	    this._a = 0x6a09e667|0
	    this._b = 0xbb67ae85|0
	    this._c = 0x3c6ef372|0
	    this._d = 0xa54ff53a|0
	    this._e = 0x510e527f|0
	    this._f = 0x9b05688c|0
	    this._g = 0x1f83d9ab|0
	    this._h = 0x5be0cd19|0
	
	    this._al = 0xf3bcc908|0
	    this._bl = 0x84caa73b|0
	    this._cl = 0xfe94f82b|0
	    this._dl = 0x5f1d36f1|0
	    this._el = 0xade682d1|0
	    this._fl = 0x2b3e6c1f|0
	    this._gl = 0xfb41bd6b|0
	    this._hl = 0x137e2179|0
	
	    this._len = this._s = 0
	
	    return this
	  }
	
	  function S (X, Xl, n) {
	    return (X >>> n) | (Xl << (32 - n))
	  }
	
	  function Ch (x, y, z) {
	    return ((x & y) ^ ((~x) & z));
	  }
	
	  function Maj (x, y, z) {
	    return ((x & y) ^ (x & z) ^ (y & z));
	  }
	
	  Sha512.prototype._update = function(M) {
	
	    var W = this._w
	    var a, b, c, d, e, f, g, h
	    var al, bl, cl, dl, el, fl, gl, hl
	
	    a = this._a | 0
	    b = this._b | 0
	    c = this._c | 0
	    d = this._d | 0
	    e = this._e | 0
	    f = this._f | 0
	    g = this._g | 0
	    h = this._h | 0
	
	    al = this._al | 0
	    bl = this._bl | 0
	    cl = this._cl | 0
	    dl = this._dl | 0
	    el = this._el | 0
	    fl = this._fl | 0
	    gl = this._gl | 0
	    hl = this._hl | 0
	
	    for (var i = 0; i < 80; i++) {
	      var j = i * 2
	
	      var Wi, Wil
	
	      if (i < 16) {
	        Wi = W[j] = M.readInt32BE(j * 4)
	        Wil = W[j + 1] = M.readInt32BE(j * 4 + 4)
	
	      } else {
	        var x  = W[j - 15*2]
	        var xl = W[j - 15*2 + 1]
	        var gamma0  = S(x, xl, 1) ^ S(x, xl, 8) ^ (x >>> 7)
	        var gamma0l = S(xl, x, 1) ^ S(xl, x, 8) ^ S(xl, x, 7)
	
	        x  = W[j - 2*2]
	        xl = W[j - 2*2 + 1]
	        var gamma1  = S(x, xl, 19) ^ S(xl, x, 29) ^ (x >>> 6)
	        var gamma1l = S(xl, x, 19) ^ S(x, xl, 29) ^ S(xl, x, 6)
	
	        // W[i] = gamma0 + W[i - 7] + gamma1 + W[i - 16]
	        var Wi7  = W[j - 7*2]
	        var Wi7l = W[j - 7*2 + 1]
	
	        var Wi16  = W[j - 16*2]
	        var Wi16l = W[j - 16*2 + 1]
	
	        Wil = gamma0l + Wi7l
	        Wi  = gamma0  + Wi7 + ((Wil >>> 0) < (gamma0l >>> 0) ? 1 : 0)
	        Wil = Wil + gamma1l
	        Wi  = Wi  + gamma1  + ((Wil >>> 0) < (gamma1l >>> 0) ? 1 : 0)
	        Wil = Wil + Wi16l
	        Wi  = Wi  + Wi16 + ((Wil >>> 0) < (Wi16l >>> 0) ? 1 : 0)
	
	        W[j] = Wi
	        W[j + 1] = Wil
	      }
	
	      var maj = Maj(a, b, c)
	      var majl = Maj(al, bl, cl)
	
	      var sigma0h = S(a, al, 28) ^ S(al, a, 2) ^ S(al, a, 7)
	      var sigma0l = S(al, a, 28) ^ S(a, al, 2) ^ S(a, al, 7)
	      var sigma1h = S(e, el, 14) ^ S(e, el, 18) ^ S(el, e, 9)
	      var sigma1l = S(el, e, 14) ^ S(el, e, 18) ^ S(e, el, 9)
	
	      // t1 = h + sigma1 + ch + K[i] + W[i]
	      var Ki = K[j]
	      var Kil = K[j + 1]
	
	      var ch = Ch(e, f, g)
	      var chl = Ch(el, fl, gl)
	
	      var t1l = hl + sigma1l
	      var t1 = h + sigma1h + ((t1l >>> 0) < (hl >>> 0) ? 1 : 0)
	      t1l = t1l + chl
	      t1 = t1 + ch + ((t1l >>> 0) < (chl >>> 0) ? 1 : 0)
	      t1l = t1l + Kil
	      t1 = t1 + Ki + ((t1l >>> 0) < (Kil >>> 0) ? 1 : 0)
	      t1l = t1l + Wil
	      t1 = t1 + Wi + ((t1l >>> 0) < (Wil >>> 0) ? 1 : 0)
	
	      // t2 = sigma0 + maj
	      var t2l = sigma0l + majl
	      var t2 = sigma0h + maj + ((t2l >>> 0) < (sigma0l >>> 0) ? 1 : 0)
	
	      h  = g
	      hl = gl
	      g  = f
	      gl = fl
	      f  = e
	      fl = el
	      el = (dl + t1l) | 0
	      e  = (d + t1 + ((el >>> 0) < (dl >>> 0) ? 1 : 0)) | 0
	      d  = c
	      dl = cl
	      c  = b
	      cl = bl
	      b  = a
	      bl = al
	      al = (t1l + t2l) | 0
	      a  = (t1 + t2 + ((al >>> 0) < (t1l >>> 0) ? 1 : 0)) | 0
	    }
	
	    this._al = (this._al + al) | 0
	    this._bl = (this._bl + bl) | 0
	    this._cl = (this._cl + cl) | 0
	    this._dl = (this._dl + dl) | 0
	    this._el = (this._el + el) | 0
	    this._fl = (this._fl + fl) | 0
	    this._gl = (this._gl + gl) | 0
	    this._hl = (this._hl + hl) | 0
	
	    this._a = (this._a + a + ((this._al >>> 0) < (al >>> 0) ? 1 : 0)) | 0
	    this._b = (this._b + b + ((this._bl >>> 0) < (bl >>> 0) ? 1 : 0)) | 0
	    this._c = (this._c + c + ((this._cl >>> 0) < (cl >>> 0) ? 1 : 0)) | 0
	    this._d = (this._d + d + ((this._dl >>> 0) < (dl >>> 0) ? 1 : 0)) | 0
	    this._e = (this._e + e + ((this._el >>> 0) < (el >>> 0) ? 1 : 0)) | 0
	    this._f = (this._f + f + ((this._fl >>> 0) < (fl >>> 0) ? 1 : 0)) | 0
	    this._g = (this._g + g + ((this._gl >>> 0) < (gl >>> 0) ? 1 : 0)) | 0
	    this._h = (this._h + h + ((this._hl >>> 0) < (hl >>> 0) ? 1 : 0)) | 0
	  }
	
	  Sha512.prototype._hash = function () {
	    var H = new Buffer(64)
	
	    function writeInt64BE(h, l, offset) {
	      H.writeInt32BE(h, offset)
	      H.writeInt32BE(l, offset + 4)
	    }
	
	    writeInt64BE(this._a, this._al, 0)
	    writeInt64BE(this._b, this._bl, 8)
	    writeInt64BE(this._c, this._cl, 16)
	    writeInt64BE(this._d, this._dl, 24)
	    writeInt64BE(this._e, this._el, 32)
	    writeInt64BE(this._f, this._fl, 40)
	    writeInt64BE(this._g, this._gl, 48)
	    writeInt64BE(this._h, this._hl, 56)
	
	    return H
	  }
	
	  return Sha512
	
	}


/***/ },

/***/ 1669:
/*!****************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/md5.js ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/*
	 * A JavaScript implementation of the RSA Data Security, Inc. MD5 Message
	 * Digest Algorithm, as defined in RFC 1321.
	 * Version 2.1 Copyright (C) Paul Johnston 1999 - 2002.
	 * Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
	 * Distributed under the BSD License
	 * See http://pajhome.org.uk/crypt/md5 for more info.
	 */
	
	var helpers = __webpack_require__(/*! ./helpers */ 1670);
	
	/*
	 * Calculate the MD5 of an array of little-endian words, and a bit length
	 */
	function core_md5(x, len)
	{
	  /* append padding */
	  x[len >> 5] |= 0x80 << ((len) % 32);
	  x[(((len + 64) >>> 9) << 4) + 14] = len;
	
	  var a =  1732584193;
	  var b = -271733879;
	  var c = -1732584194;
	  var d =  271733878;
	
	  for(var i = 0; i < x.length; i += 16)
	  {
	    var olda = a;
	    var oldb = b;
	    var oldc = c;
	    var oldd = d;
	
	    a = md5_ff(a, b, c, d, x[i+ 0], 7 , -680876936);
	    d = md5_ff(d, a, b, c, x[i+ 1], 12, -389564586);
	    c = md5_ff(c, d, a, b, x[i+ 2], 17,  606105819);
	    b = md5_ff(b, c, d, a, x[i+ 3], 22, -1044525330);
	    a = md5_ff(a, b, c, d, x[i+ 4], 7 , -176418897);
	    d = md5_ff(d, a, b, c, x[i+ 5], 12,  1200080426);
	    c = md5_ff(c, d, a, b, x[i+ 6], 17, -1473231341);
	    b = md5_ff(b, c, d, a, x[i+ 7], 22, -45705983);
	    a = md5_ff(a, b, c, d, x[i+ 8], 7 ,  1770035416);
	    d = md5_ff(d, a, b, c, x[i+ 9], 12, -1958414417);
	    c = md5_ff(c, d, a, b, x[i+10], 17, -42063);
	    b = md5_ff(b, c, d, a, x[i+11], 22, -1990404162);
	    a = md5_ff(a, b, c, d, x[i+12], 7 ,  1804603682);
	    d = md5_ff(d, a, b, c, x[i+13], 12, -40341101);
	    c = md5_ff(c, d, a, b, x[i+14], 17, -1502002290);
	    b = md5_ff(b, c, d, a, x[i+15], 22,  1236535329);
	
	    a = md5_gg(a, b, c, d, x[i+ 1], 5 , -165796510);
	    d = md5_gg(d, a, b, c, x[i+ 6], 9 , -1069501632);
	    c = md5_gg(c, d, a, b, x[i+11], 14,  643717713);
	    b = md5_gg(b, c, d, a, x[i+ 0], 20, -373897302);
	    a = md5_gg(a, b, c, d, x[i+ 5], 5 , -701558691);
	    d = md5_gg(d, a, b, c, x[i+10], 9 ,  38016083);
	    c = md5_gg(c, d, a, b, x[i+15], 14, -660478335);
	    b = md5_gg(b, c, d, a, x[i+ 4], 20, -405537848);
	    a = md5_gg(a, b, c, d, x[i+ 9], 5 ,  568446438);
	    d = md5_gg(d, a, b, c, x[i+14], 9 , -1019803690);
	    c = md5_gg(c, d, a, b, x[i+ 3], 14, -187363961);
	    b = md5_gg(b, c, d, a, x[i+ 8], 20,  1163531501);
	    a = md5_gg(a, b, c, d, x[i+13], 5 , -1444681467);
	    d = md5_gg(d, a, b, c, x[i+ 2], 9 , -51403784);
	    c = md5_gg(c, d, a, b, x[i+ 7], 14,  1735328473);
	    b = md5_gg(b, c, d, a, x[i+12], 20, -1926607734);
	
	    a = md5_hh(a, b, c, d, x[i+ 5], 4 , -378558);
	    d = md5_hh(d, a, b, c, x[i+ 8], 11, -2022574463);
	    c = md5_hh(c, d, a, b, x[i+11], 16,  1839030562);
	    b = md5_hh(b, c, d, a, x[i+14], 23, -35309556);
	    a = md5_hh(a, b, c, d, x[i+ 1], 4 , -1530992060);
	    d = md5_hh(d, a, b, c, x[i+ 4], 11,  1272893353);
	    c = md5_hh(c, d, a, b, x[i+ 7], 16, -155497632);
	    b = md5_hh(b, c, d, a, x[i+10], 23, -1094730640);
	    a = md5_hh(a, b, c, d, x[i+13], 4 ,  681279174);
	    d = md5_hh(d, a, b, c, x[i+ 0], 11, -358537222);
	    c = md5_hh(c, d, a, b, x[i+ 3], 16, -722521979);
	    b = md5_hh(b, c, d, a, x[i+ 6], 23,  76029189);
	    a = md5_hh(a, b, c, d, x[i+ 9], 4 , -640364487);
	    d = md5_hh(d, a, b, c, x[i+12], 11, -421815835);
	    c = md5_hh(c, d, a, b, x[i+15], 16,  530742520);
	    b = md5_hh(b, c, d, a, x[i+ 2], 23, -995338651);
	
	    a = md5_ii(a, b, c, d, x[i+ 0], 6 , -198630844);
	    d = md5_ii(d, a, b, c, x[i+ 7], 10,  1126891415);
	    c = md5_ii(c, d, a, b, x[i+14], 15, -1416354905);
	    b = md5_ii(b, c, d, a, x[i+ 5], 21, -57434055);
	    a = md5_ii(a, b, c, d, x[i+12], 6 ,  1700485571);
	    d = md5_ii(d, a, b, c, x[i+ 3], 10, -1894986606);
	    c = md5_ii(c, d, a, b, x[i+10], 15, -1051523);
	    b = md5_ii(b, c, d, a, x[i+ 1], 21, -2054922799);
	    a = md5_ii(a, b, c, d, x[i+ 8], 6 ,  1873313359);
	    d = md5_ii(d, a, b, c, x[i+15], 10, -30611744);
	    c = md5_ii(c, d, a, b, x[i+ 6], 15, -1560198380);
	    b = md5_ii(b, c, d, a, x[i+13], 21,  1309151649);
	    a = md5_ii(a, b, c, d, x[i+ 4], 6 , -145523070);
	    d = md5_ii(d, a, b, c, x[i+11], 10, -1120210379);
	    c = md5_ii(c, d, a, b, x[i+ 2], 15,  718787259);
	    b = md5_ii(b, c, d, a, x[i+ 9], 21, -343485551);
	
	    a = safe_add(a, olda);
	    b = safe_add(b, oldb);
	    c = safe_add(c, oldc);
	    d = safe_add(d, oldd);
	  }
	  return Array(a, b, c, d);
	
	}
	
	/*
	 * These functions implement the four basic operations the algorithm uses.
	 */
	function md5_cmn(q, a, b, x, s, t)
	{
	  return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s),b);
	}
	function md5_ff(a, b, c, d, x, s, t)
	{
	  return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
	}
	function md5_gg(a, b, c, d, x, s, t)
	{
	  return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
	}
	function md5_hh(a, b, c, d, x, s, t)
	{
	  return md5_cmn(b ^ c ^ d, a, b, x, s, t);
	}
	function md5_ii(a, b, c, d, x, s, t)
	{
	  return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
	}
	
	/*
	 * Add integers, wrapping at 2^32. This uses 16-bit operations internally
	 * to work around bugs in some JS interpreters.
	 */
	function safe_add(x, y)
	{
	  var lsw = (x & 0xFFFF) + (y & 0xFFFF);
	  var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
	  return (msw << 16) | (lsw & 0xFFFF);
	}
	
	/*
	 * Bitwise rotate a 32-bit number to the left.
	 */
	function bit_rol(num, cnt)
	{
	  return (num << cnt) | (num >>> (32 - cnt));
	}
	
	module.exports = function md5(buf) {
	  return helpers.hash(buf, core_md5, 16);
	};


/***/ },

/***/ 1670:
/*!********************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/helpers.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(Buffer) {var intSize = 4;
	var zeroBuffer = new Buffer(intSize); zeroBuffer.fill(0);
	var chrsz = 8;
	
	function toArray(buf, bigEndian) {
	  if ((buf.length % intSize) !== 0) {
	    var len = buf.length + (intSize - (buf.length % intSize));
	    buf = Buffer.concat([buf, zeroBuffer], len);
	  }
	
	  var arr = [];
	  var fn = bigEndian ? buf.readInt32BE : buf.readInt32LE;
	  for (var i = 0; i < buf.length; i += intSize) {
	    arr.push(fn.call(buf, i));
	  }
	  return arr;
	}
	
	function toBuffer(arr, size, bigEndian) {
	  var buf = new Buffer(size);
	  var fn = bigEndian ? buf.writeInt32BE : buf.writeInt32LE;
	  for (var i = 0; i < arr.length; i++) {
	    fn.call(buf, arr[i], i * 4, true);
	  }
	  return buf;
	}
	
	function hash(buf, fn, hashSize, bigEndian) {
	  if (!Buffer.isBuffer(buf)) buf = new Buffer(buf);
	  var arr = fn(toArray(buf, bigEndian), buf.length * chrsz);
	  return toBuffer(arr, hashSize, bigEndian);
	}
	
	module.exports = { hash: hash };
	
	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! (webpack)/~/node-libs-browser/~/buffer/index.js */ 1646).Buffer))

/***/ },

/***/ 1671:
/*!**************************************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/~/ripemd160/lib/ripemd160.js ***!
  \**************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(Buffer) {
	module.exports = ripemd160
	
	
	
	/*
	CryptoJS v3.1.2
	code.google.com/p/crypto-js
	(c) 2009-2013 by Jeff Mott. All rights reserved.
	code.google.com/p/crypto-js/wiki/License
	*/
	/** @preserve
	(c) 2012 by Cédric Mesnil. All rights reserved.
	
	Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
	
	    - Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
	    - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
	
	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
	*/
	
	// Constants table
	var zl = [
	    0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15,
	    7,  4, 13,  1, 10,  6, 15,  3, 12,  0,  9,  5,  2, 14, 11,  8,
	    3, 10, 14,  4,  9, 15,  8,  1,  2,  7,  0,  6, 13, 11,  5, 12,
	    1,  9, 11, 10,  0,  8, 12,  4, 13,  3,  7, 15, 14,  5,  6,  2,
	    4,  0,  5,  9,  7, 12,  2, 10, 14,  1,  3,  8, 11,  6, 15, 13];
	var zr = [
	    5, 14,  7,  0,  9,  2, 11,  4, 13,  6, 15,  8,  1, 10,  3, 12,
	    6, 11,  3,  7,  0, 13,  5, 10, 14, 15,  8, 12,  4,  9,  1,  2,
	    15,  5,  1,  3,  7, 14,  6,  9, 11,  8, 12,  2, 10,  0,  4, 13,
	    8,  6,  4,  1,  3, 11, 15,  0,  5, 12,  2, 13,  9,  7, 10, 14,
	    12, 15, 10,  4,  1,  5,  8,  7,  6,  2, 13, 14,  0,  3,  9, 11];
	var sl = [
	     11, 14, 15, 12,  5,  8,  7,  9, 11, 13, 14, 15,  6,  7,  9,  8,
	    7, 6,   8, 13, 11,  9,  7, 15,  7, 12, 15,  9, 11,  7, 13, 12,
	    11, 13,  6,  7, 14,  9, 13, 15, 14,  8, 13,  6,  5, 12,  7,  5,
	      11, 12, 14, 15, 14, 15,  9,  8,  9, 14,  5,  6,  8,  6,  5, 12,
	    9, 15,  5, 11,  6,  8, 13, 12,  5, 12, 13, 14, 11,  8,  5,  6 ];
	var sr = [
	    8,  9,  9, 11, 13, 15, 15,  5,  7,  7,  8, 11, 14, 14, 12,  6,
	    9, 13, 15,  7, 12,  8,  9, 11,  7,  7, 12,  7,  6, 15, 13, 11,
	    9,  7, 15, 11,  8,  6,  6, 14, 12, 13,  5, 14, 13, 13,  7,  5,
	    15,  5,  8, 11, 14, 14,  6, 14,  6,  9, 12,  9, 12,  5, 15,  8,
	    8,  5, 12,  9, 12,  5, 14,  6,  8, 13,  6,  5, 15, 13, 11, 11 ];
	
	var hl =  [ 0x00000000, 0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xA953FD4E];
	var hr =  [ 0x50A28BE6, 0x5C4DD124, 0x6D703EF3, 0x7A6D76E9, 0x00000000];
	
	var bytesToWords = function (bytes) {
	  var words = [];
	  for (var i = 0, b = 0; i < bytes.length; i++, b += 8) {
	    words[b >>> 5] |= bytes[i] << (24 - b % 32);
	  }
	  return words;
	};
	
	var wordsToBytes = function (words) {
	  var bytes = [];
	  for (var b = 0; b < words.length * 32; b += 8) {
	    bytes.push((words[b >>> 5] >>> (24 - b % 32)) & 0xFF);
	  }
	  return bytes;
	};
	
	var processBlock = function (H, M, offset) {
	
	  // Swap endian
	  for (var i = 0; i < 16; i++) {
	    var offset_i = offset + i;
	    var M_offset_i = M[offset_i];
	
	    // Swap
	    M[offset_i] = (
	        (((M_offset_i << 8)  | (M_offset_i >>> 24)) & 0x00ff00ff) |
	        (((M_offset_i << 24) | (M_offset_i >>> 8))  & 0xff00ff00)
	    );
	  }
	
	  // Working variables
	  var al, bl, cl, dl, el;
	  var ar, br, cr, dr, er;
	
	  ar = al = H[0];
	  br = bl = H[1];
	  cr = cl = H[2];
	  dr = dl = H[3];
	  er = el = H[4];
	  // Computation
	  var t;
	  for (var i = 0; i < 80; i += 1) {
	    t = (al +  M[offset+zl[i]])|0;
	    if (i<16){
	        t +=  f1(bl,cl,dl) + hl[0];
	    } else if (i<32) {
	        t +=  f2(bl,cl,dl) + hl[1];
	    } else if (i<48) {
	        t +=  f3(bl,cl,dl) + hl[2];
	    } else if (i<64) {
	        t +=  f4(bl,cl,dl) + hl[3];
	    } else {// if (i<80) {
	        t +=  f5(bl,cl,dl) + hl[4];
	    }
	    t = t|0;
	    t =  rotl(t,sl[i]);
	    t = (t+el)|0;
	    al = el;
	    el = dl;
	    dl = rotl(cl, 10);
	    cl = bl;
	    bl = t;
	
	    t = (ar + M[offset+zr[i]])|0;
	    if (i<16){
	        t +=  f5(br,cr,dr) + hr[0];
	    } else if (i<32) {
	        t +=  f4(br,cr,dr) + hr[1];
	    } else if (i<48) {
	        t +=  f3(br,cr,dr) + hr[2];
	    } else if (i<64) {
	        t +=  f2(br,cr,dr) + hr[3];
	    } else {// if (i<80) {
	        t +=  f1(br,cr,dr) + hr[4];
	    }
	    t = t|0;
	    t =  rotl(t,sr[i]) ;
	    t = (t+er)|0;
	    ar = er;
	    er = dr;
	    dr = rotl(cr, 10);
	    cr = br;
	    br = t;
	  }
	  // Intermediate hash value
	  t    = (H[1] + cl + dr)|0;
	  H[1] = (H[2] + dl + er)|0;
	  H[2] = (H[3] + el + ar)|0;
	  H[3] = (H[4] + al + br)|0;
	  H[4] = (H[0] + bl + cr)|0;
	  H[0] =  t;
	};
	
	function f1(x, y, z) {
	  return ((x) ^ (y) ^ (z));
	}
	
	function f2(x, y, z) {
	  return (((x)&(y)) | ((~x)&(z)));
	}
	
	function f3(x, y, z) {
	  return (((x) | (~(y))) ^ (z));
	}
	
	function f4(x, y, z) {
	  return (((x) & (z)) | ((y)&(~(z))));
	}
	
	function f5(x, y, z) {
	  return ((x) ^ ((y) |(~(z))));
	}
	
	function rotl(x,n) {
	  return (x<<n) | (x>>>(32-n));
	}
	
	function ripemd160(message) {
	  var H = [0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0];
	
	  if (typeof message == 'string')
	    message = new Buffer(message, 'utf8');
	
	  var m = bytesToWords(message);
	
	  var nBitsLeft = message.length * 8;
	  var nBitsTotal = message.length * 8;
	
	  // Add padding
	  m[nBitsLeft >>> 5] |= 0x80 << (24 - nBitsLeft % 32);
	  m[(((nBitsLeft + 64) >>> 9) << 4) + 14] = (
	      (((nBitsTotal << 8)  | (nBitsTotal >>> 24)) & 0x00ff00ff) |
	      (((nBitsTotal << 24) | (nBitsTotal >>> 8))  & 0xff00ff00)
	  );
	
	  for (var i=0 ; i<m.length; i += 16) {
	    processBlock(H, m, i);
	  }
	
	  // Swap endian
	  for (var i = 0; i < 5; i++) {
	      // Shortcut
	    var H_i = H[i];
	
	    // Swap
	    H[i] = (((H_i << 8)  | (H_i >>> 24)) & 0x00ff00ff) |
	          (((H_i << 24) | (H_i >>> 8))  & 0xff00ff00);
	  }
	
	  var digestbytes = wordsToBytes(H);
	  return new Buffer(digestbytes);
	}
	
	
	
	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! (webpack)/~/node-libs-browser/~/buffer/index.js */ 1646).Buffer))

/***/ },

/***/ 1672:
/*!************************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/create-hmac.js ***!
  \************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(Buffer) {var createHash = __webpack_require__(/*! ./create-hash */ 1660)
	
	var zeroBuffer = new Buffer(128)
	zeroBuffer.fill(0)
	
	module.exports = Hmac
	
	function Hmac (alg, key) {
	  if(!(this instanceof Hmac)) return new Hmac(alg, key)
	  this._opad = opad
	  this._alg = alg
	
	  var blocksize = (alg === 'sha512') ? 128 : 64
	
	  key = this._key = !Buffer.isBuffer(key) ? new Buffer(key) : key
	
	  if(key.length > blocksize) {
	    key = createHash(alg).update(key).digest()
	  } else if(key.length < blocksize) {
	    key = Buffer.concat([key, zeroBuffer], blocksize)
	  }
	
	  var ipad = this._ipad = new Buffer(blocksize)
	  var opad = this._opad = new Buffer(blocksize)
	
	  for(var i = 0; i < blocksize; i++) {
	    ipad[i] = key[i] ^ 0x36
	    opad[i] = key[i] ^ 0x5C
	  }
	
	  this._hash = createHash(alg).update(ipad)
	}
	
	Hmac.prototype.update = function (data, enc) {
	  this._hash.update(data, enc)
	  return this
	}
	
	Hmac.prototype.digest = function (enc) {
	  var h = this._hash.digest()
	  return createHash(this._alg).update(this._opad).update(h).digest(enc)
	}
	
	
	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! (webpack)/~/node-libs-browser/~/buffer/index.js */ 1646).Buffer))

/***/ },

/***/ 1673:
/*!*******************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/pbkdf2.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var pbkdf2Export = __webpack_require__(/*! pbkdf2-compat/pbkdf2 */ 1674)
	
	module.exports = function (crypto, exports) {
	  exports = exports || {}
	
	  var exported = pbkdf2Export(crypto)
	
	  exports.pbkdf2 = exported.pbkdf2
	  exports.pbkdf2Sync = exported.pbkdf2Sync
	
	  return exports
	}


/***/ },

/***/ 1674:
/*!***********************************************************************************!*\
  !*** (webpack)/~/node-libs-browser/~/crypto-browserify/~/pbkdf2-compat/pbkdf2.js ***!
  \***********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(Buffer) {module.exports = function(crypto) {
	  function pbkdf2(password, salt, iterations, keylen, digest, callback) {
	    if ('function' === typeof digest) {
	      callback = digest
	      digest = undefined
	    }
	
	    if ('function' !== typeof callback)
	      throw new Error('No callback provided to pbkdf2')
	
	    setTimeout(function() {
	      var result
	
	      try {
	        result = pbkdf2Sync(password, salt, iterations, keylen, digest)
	      } catch (e) {
	        return callback(e)
	      }
	
	      callback(undefined, result)
	    })
	  }
	
	  function pbkdf2Sync(password, salt, iterations, keylen, digest) {
	    if ('number' !== typeof iterations)
	      throw new TypeError('Iterations not a number')
	
	    if (iterations < 0)
	      throw new TypeError('Bad iterations')
	
	    if ('number' !== typeof keylen)
	      throw new TypeError('Key length not a number')
	
	    if (keylen < 0)
	      throw new TypeError('Bad key length')
	
	    digest = digest || 'sha1'
	
	    if (!Buffer.isBuffer(password)) password = new Buffer(password)
	    if (!Buffer.isBuffer(salt)) salt = new Buffer(salt)
	
	    var hLen, l = 1, r, T
	    var DK = new Buffer(keylen)
	    var block1 = new Buffer(salt.length + 4)
	    salt.copy(block1, 0, 0, salt.length)
	
	    for (var i = 1; i <= l; i++) {
	      block1.writeUInt32BE(i, salt.length)
	
	      var U = crypto.createHmac(digest, password).update(block1).digest()
	
	      if (!hLen) {
	        hLen = U.length
	        T = new Buffer(hLen)
	        l = Math.ceil(keylen / hLen)
	        r = keylen - (l - 1) * hLen
	
	        if (keylen > (Math.pow(2, 32) - 1) * hLen)
	          throw new TypeError('keylen exceeds maximum length')
	      }
	
	      U.copy(T, 0, 0, hLen)
	
	      for (var j = 1; j < iterations; j++) {
	        U = crypto.createHmac(digest, password).update(U).digest()
	
	        for (var k = 0; k < hLen; k++) {
	          T[k] ^= U[k]
	        }
	      }
	
	      var destPos = (i - 1) * hLen
	      var len = (i == l ? r : hLen)
	      T.copy(DK, destPos, 0, len)
	    }
	
	    return DK
	  }
	
	  return {
	    pbkdf2: pbkdf2,
	    pbkdf2Sync: pbkdf2Sync
	  }
	}
	
	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! (webpack)/~/node-libs-browser/~/buffer/index.js */ 1646).Buffer))

/***/ },

/***/ 2107:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/highchartsHeatmapRenderer.js ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	var React = __webpack_require__(/*! react */ 2108);
	var ReactDOM = __webpack_require__(/*! react-dom */ 2264);
	
	//*------------------------------------------------------------------*
	
	var EventEmitter = __webpack_require__(/*! events */ 641);
	
	var HighchartsHeatmapContainer = __webpack_require__(/*! ./HighchartsHeatmapContainer.jsx */ 2265);
	
	//*------------------------------------------------------------------*
	
	/**
	 * @param {Object}      options
	 * @param {string}          options.proxyPrefix - Proxy URL with protocol: required by CTTV
	 * @param {boolean=}        options.disableGoogleAnalytics - Disable Google Analytics: required by CTTV
	 * @param {string=}         options.atlasHost - Atlas host with protocol and port
	 * @param {string}          options.sourceURL - Where to source the data from
	 * *                        e.g. /json/experiments/E-PROT-1, /json/genes/ENSG00000005801, /json/genesets/GO:0000001 or a widget endpoint
	 * @param {string}          options.params - Alternate way of sourcing data if you do not provide the sourceURL
	 * @param {string | Object} options.target - a <div> id or a DOM element, as returned by ReactDOM.findDOMNode()
	 * @param {function}        options.fail - Callback to run if the AJAX request to the server fails. (jqXHR, textStatus)
	 * @param {Object}          options.anatomogramDataEventEmitter - emits events to the facets tree to signal the existence of anatomogram
	 * @param {boolean=}        options.showAnatomogram - optionally hide the anatomogram
	 * @param {boolean}         options.isDifferential
	 * @param {boolean}         options.isMultiExperiment
	 * @param {boolean=}        options.isWidget
	 */
	
	exports.render = function (options) {
	
	    var protocol = window.location.protocol + "//",
	        atlasHost = options.atlasHost === undefined ? "https://www.ebi.ac.uk" : options.atlasHost,
	        atlasPath = "/gxa";
	
	    var linksAtlasBaseURL = atlasHost.indexOf("http://") === 0 || atlasHost.indexOf("https://") === 0 ? atlasHost + atlasPath : protocol + atlasHost + atlasPath;
	
	    var atlasBaseURL = options.proxyPrefix ? options.proxyPrefix + "/" + atlasHost + atlasPath : linksAtlasBaseURL;
	
	    //If using this renderer for a standalone widget, see uk.ac.ebi.atlas.widget.HeatmapWidgetController.java for the source URL/params required
	    var sourceURL = options.sourceURL || atlasBaseURL + "/widgets/heatmap" + (options.isMultiExperiment ? "/baselineAnalytics" : "/referenceExperiment") + "?" + options.params;
	
	    var anatomogramEventEmitter = new EventEmitter();
	    anatomogramEventEmitter.setMaxListeners(0);
	
	    ReactDOM.render(React.createElement(HighchartsHeatmapContainer, {
	        sourceURL: sourceURL,
	        atlasBaseURL: atlasBaseURL,
	        linksAtlasBaseURL: linksAtlasBaseURL,
	        pathToFolderWithBundledResources: linksAtlasBaseURL + "/resources/js-bundles",
	        showAnatomogram: options.showAnatomogram === undefined || options.showAnatomogram,
	        isDifferential: !!options.isDifferential,
	        isMultiExperiment: sourceURL.indexOf("/baselineAnalytics") > -1,
	        isWidget: options.isWidget === undefined || options.isWidget,
	        disableGoogleAnalytics: !!options.disableGoogleAnalytics,
	        fail: options.fail,
	        anatomogramEventEmitter: anatomogramEventEmitter,
	        anatomogramDataEventEmitter: options.anatomogramDataEventEmitter
	    }), typeof options.target === "string" ? document.getElementById(options.target) : options.target);
	};

/***/ },

/***/ 2108:
/*!**************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/react.js ***!
  \**************************************************************/
[2493, 2109],

/***/ 2109:
/*!******************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/React.js ***!
  \******************************************************************/
[2494, 2110, 2254, 2258, 2145, 2263],

/***/ 2110:
/*!*********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOM.js ***!
  \*********************************************************************/
[2495, 2111, 2112, 2177, 2151, 2134, 2124, 2156, 2160, 2252, 2197, 2253, 2131, 2115],

/***/ 2111:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactCurrentOwner.js ***!
  \******************************************************************************/
5,

/***/ 2112:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMTextComponent.js ***!
  \**********************************************************************************/
[2496, 2113, 2128, 2132, 2134, 2145, 2127, 2126, 2176],

/***/ 2113:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DOMChildrenOperations.js ***!
  \**********************************************************************************/
[2497, 2114, 2122, 2124, 2125, 2126, 2119],

/***/ 2114:
/*!*******************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/Danger.js ***!
  \*******************************************************************/
[2498, 2115, 2116, 2121, 2120, 2119],

/***/ 2115:
/*!****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/ExecutionEnvironment.js ***!
  \****************************************************************************************/
9,

/***/ 2116:
/*!*****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \*****************************************************************************************/
[2499, 2115, 2117, 2120, 2119],

/***/ 2117:
/*!****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \****************************************************************************************/
[2500, 2118],

/***/ 2118:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/toArray.js ***!
  \***************************************************************************/
[2501, 2119],

/***/ 2119:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/invariant.js ***!
  \*****************************************************************************/
13,

/***/ 2120:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \*********************************************************************************/
[2502, 2115, 2119],

/***/ 2121:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/emptyFunction.js ***!
  \*********************************************************************************/
15,

/***/ 2122:
/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \***************************************************************************************/
[2503, 2123],

/***/ 2123:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/keyMirror.js ***!
  \*****************************************************************************/
[2504, 2119],

/***/ 2124:
/*!**********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactPerf.js ***!
  \**********************************************************************/
18,

/***/ 2125:
/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/setInnerHTML.js ***!
  \*************************************************************************/
[2505, 2115],

/***/ 2126:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/setTextContent.js ***!
  \***************************************************************************/
[2506, 2115, 2127, 2125],

/***/ 2127:
/*!****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/escapeTextContentForBrowser.js ***!
  \****************************************************************************************/
21,

/***/ 2128:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DOMPropertyOperations.js ***!
  \**********************************************************************************/
[2507, 2129, 2124, 2130, 2131],

/***/ 2129:
/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DOMProperty.js ***!
  \************************************************************************/
[2508, 2119],

/***/ 2130:
/*!******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \******************************************************************************************/
[2509, 2127],

/***/ 2131:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/warning.js ***!
  \***************************************************************************/
[2510, 2121],

/***/ 2132:
/*!*********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \*********************************************************************************************/
[2511, 2133, 2134],

/***/ 2133:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMIDOperations.js ***!
  \*********************************************************************************/
[2512, 2113, 2128, 2134, 2124, 2119],

/***/ 2134:
/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMount.js ***!
  \***********************************************************************/
[2513, 2129, 2135, 2111, 2147, 2148, 2150, 2151, 2153, 2154, 2124, 2156, 2159, 2160, 2145, 2164, 2165, 2168, 2119, 2125, 2173, 2176, 2131],

/***/ 2135:
/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactBrowserEventEmitter.js ***!
  \*************************************************************************************/
[2514, 2136, 2137, 2138, 2143, 2124, 2144, 2145, 2146],

/***/ 2136:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventConstants.js ***!
  \***************************************************************************/
[2515, 2123],

/***/ 2137:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPluginHub.js ***!
  \***************************************************************************/
[2516, 2138, 2139, 2140, 2141, 2142, 2119, 2131],

/***/ 2138:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPluginRegistry.js ***!
  \********************************************************************************/
[2517, 2119],

/***/ 2139:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPluginUtils.js ***!
  \*****************************************************************************/
[2518, 2136, 2140, 2119, 2131],

/***/ 2140:
/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactErrorUtils.js ***!
  \****************************************************************************/
34,

/***/ 2141:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/accumulateInto.js ***!
  \***************************************************************************/
[2519, 2119],

/***/ 2142:
/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/forEachAccumulated.js ***!
  \*******************************************************************************/
36,

/***/ 2143:
/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactEventEmitterMixin.js ***!
  \***********************************************************************************/
[2520, 2137],

/***/ 2144:
/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ViewportMetrics.js ***!
  \****************************************************************************/
38,

/***/ 2145:
/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/Object.assign.js ***!
  \**************************************************************************/
39,

/***/ 2146:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/isEventSupported.js ***!
  \*****************************************************************************/
[2521, 2115],

/***/ 2147:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMFeatureFlags.js ***!
  \*********************************************************************************/
41,

/***/ 2148:
/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactElement.js ***!
  \*************************************************************************/
[2522, 2111, 2145, 2149],

/***/ 2149:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/canDefineProperty.js ***!
  \******************************************************************************/
43,

/***/ 2150:
/*!****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactEmptyComponentRegistry.js ***!
  \****************************************************************************************/
44,

/***/ 2151:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactInstanceHandles.js ***!
  \*********************************************************************************/
[2523, 2152, 2119],

/***/ 2152:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactRootIndex.js ***!
  \***************************************************************************/
46,

/***/ 2153:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactInstanceMap.js ***!
  \*****************************************************************************/
47,

/***/ 2154:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMarkupChecksum.js ***!
  \********************************************************************************/
[2524, 2155],

/***/ 2155:
/*!********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/adler32.js ***!
  \********************************************************************/
49,

/***/ 2156:
/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactReconciler.js ***!
  \****************************************************************************/
[2525, 2157],

/***/ 2157:
/*!*********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactRef.js ***!
  \*********************************************************************/
[2526, 2158],

/***/ 2158:
/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactOwner.js ***!
  \***********************************************************************/
[2527, 2119],

/***/ 2159:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactUpdateQueue.js ***!
  \*****************************************************************************/
[2528, 2111, 2148, 2153, 2160, 2145, 2119, 2131],

/***/ 2160:
/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactUpdates.js ***!
  \*************************************************************************/
[2529, 2161, 2162, 2124, 2156, 2163, 2145, 2119],

/***/ 2161:
/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/CallbackQueue.js ***!
  \**************************************************************************/
[2530, 2162, 2145, 2119],

/***/ 2162:
/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/PooledClass.js ***!
  \************************************************************************/
[2531, 2119],

/***/ 2163:
/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/Transaction.js ***!
  \************************************************************************/
[2532, 2119],

/***/ 2164:
/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/emptyObject.js ***!
  \*******************************************************************************/
58,

/***/ 2165:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/containsNode.js ***!
  \********************************************************************************/
[2533, 2166],

/***/ 2166:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/isTextNode.js ***!
  \******************************************************************************/
[2534, 2167],

/***/ 2167:
/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/isNode.js ***!
  \**************************************************************************/
61,

/***/ 2168:
/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/instantiateReactComponent.js ***!
  \**************************************************************************************/
[2535, 2169, 2174, 2175, 2145, 2119, 2131],

/***/ 2169:
/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactCompositeComponent.js ***!
  \************************************************************************************/
[2536, 2170, 2111, 2148, 2153, 2124, 2171, 2172, 2156, 2159, 2145, 2164, 2119, 2173, 2131],

/***/ 2170:
/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactComponentEnvironment.js ***!
  \**************************************************************************************/
[2537, 2119],

/***/ 2171:
/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactPropTypeLocations.js ***!
  \***********************************************************************************/
[2538, 2123],

/***/ 2172:
/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactPropTypeLocationNames.js ***!
  \***************************************************************************************/
66,

/***/ 2173:
/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/shouldUpdateReactComponent.js ***!
  \***************************************************************************************/
67,

/***/ 2174:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactEmptyComponent.js ***!
  \********************************************************************************/
[2539, 2148, 2150, 2156, 2145],

/***/ 2175:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactNativeComponent.js ***!
  \*********************************************************************************/
[2540, 2145, 2119],

/***/ 2176:
/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/validateDOMNesting.js ***!
  \*******************************************************************************/
[2541, 2145, 2121, 2131],

/***/ 2177:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultInjection.js ***!
  \**********************************************************************************/
[2542, 2178, 2186, 2189, 2190, 2191, 2115, 2195, 2196, 2132, 2198, 2199, 2112, 2224, 2227, 2151, 2134, 2231, 2236, 2237, 2238, 2247, 2248],

/***/ 2178:
/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/BeforeInputEventPlugin.js ***!
  \***********************************************************************************/
[2543, 2136, 2179, 2115, 2180, 2182, 2184, 2185],

/***/ 2179:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPropagators.js ***!
  \*****************************************************************************/
[2544, 2136, 2137, 2131, 2141, 2142],

/***/ 2180:
/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/FallbackCompositionState.js ***!
  \*************************************************************************************/
[2545, 2162, 2145, 2181],

/***/ 2181:
/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/getTextContentAccessor.js ***!
  \***********************************************************************************/
[2546, 2115],

/***/ 2182:
/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticCompositionEvent.js ***!
  \**************************************************************************************/
[2547, 2183],

/***/ 2183:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticEvent.js ***!
  \***************************************************************************/
[2548, 2162, 2145, 2121, 2131],

/***/ 2184:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticInputEvent.js ***!
  \********************************************************************************/
[2549, 2183],

/***/ 2185:
/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/keyOf.js ***!
  \*************************************************************************/
79,

/***/ 2186:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ChangeEventPlugin.js ***!
  \******************************************************************************/
[2550, 2136, 2137, 2179, 2115, 2160, 2183, 2187, 2146, 2188, 2185],

/***/ 2187:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/getEventTarget.js ***!
  \***************************************************************************/
81,

/***/ 2188:
/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/isTextInputElement.js ***!
  \*******************************************************************************/
82,

/***/ 2189:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ClientReactRootIndex.js ***!
  \*********************************************************************************/
83,

/***/ 2190:
/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DefaultEventPluginOrder.js ***!
  \************************************************************************************/
[2551, 2185],

/***/ 2191:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EnterLeaveEventPlugin.js ***!
  \**********************************************************************************/
[2552, 2136, 2179, 2192, 2134, 2185],

/***/ 2192:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticMouseEvent.js ***!
  \********************************************************************************/
[2553, 2193, 2144, 2194],

/***/ 2193:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticUIEvent.js ***!
  \*****************************************************************************/
[2554, 2183, 2187],

/***/ 2194:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/getEventModifierState.js ***!
  \**********************************************************************************/
88,

/***/ 2195:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \**********************************************************************************/
[2555, 2129, 2115],

/***/ 2196:
/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactBrowserComponentMixin.js ***!
  \***************************************************************************************/
[2556, 2153, 2197, 2131],

/***/ 2197:
/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/findDOMNode.js ***!
  \************************************************************************/
[2557, 2111, 2153, 2134, 2119, 2131],

/***/ 2198:
/*!*****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \*****************************************************************************************/
[2558, 2160, 2163, 2145, 2121],

/***/ 2199:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMComponent.js ***!
  \******************************************************************************/
[2559, 2200, 2202, 2129, 2128, 2136, 2135, 2132, 2210, 2211, 2215, 2218, 2219, 2134, 2220, 2124, 2159, 2145, 2149, 2127, 2119, 2146, 2185, 2125, 2126, 2223, 2176, 2131],

/***/ 2200:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/AutoFocusUtils.js ***!
  \***************************************************************************/
[2560, 2134, 2197, 2201],

/***/ 2201:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/focusNode.js ***!
  \*****************************************************************************/
95,

/***/ 2202:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/CSSPropertyOperations.js ***!
  \**********************************************************************************/
[2561, 2203, 2115, 2124, 2204, 2206, 2207, 2209, 2131],

/***/ 2203:
/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/CSSProperty.js ***!
  \************************************************************************/
97,

/***/ 2204:
/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \*************************************************************************************/
[2562, 2205],

/***/ 2205:
/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/camelize.js ***!
  \****************************************************************************/
99,

/***/ 2206:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/dangerousStyleValue.js ***!
  \********************************************************************************/
[2563, 2203],

/***/ 2207:
/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \**************************************************************************************/
[2564, 2208],

/***/ 2208:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/hyphenate.js ***!
  \*****************************************************************************/
102,

/***/ 2209:
/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/memoizeStringOnly.js ***!
  \*************************************************************************************/
103,

/***/ 2210:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMButton.js ***!
  \***************************************************************************/
104,

/***/ 2211:
/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMInput.js ***!
  \**************************************************************************/
[2565, 2133, 2212, 2134, 2160, 2145, 2119],

/***/ 2212:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/LinkedValueUtils.js ***!
  \*****************************************************************************/
[2566, 2213, 2171, 2119, 2131],

/***/ 2213:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactPropTypes.js ***!
  \***************************************************************************/
[2567, 2148, 2172, 2121, 2214],

/***/ 2214:
/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/getIteratorFn.js ***!
  \**************************************************************************/
108,

/***/ 2215:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMOption.js ***!
  \***************************************************************************/
[2568, 2216, 2218, 2145, 2131],

/***/ 2216:
/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactChildren.js ***!
  \**************************************************************************/
[2569, 2162, 2148, 2121, 2217],

/***/ 2217:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/traverseAllChildren.js ***!
  \********************************************************************************/
[2570, 2111, 2148, 2151, 2214, 2119, 2131],

/***/ 2218:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMSelect.js ***!
  \***************************************************************************/
[2571, 2212, 2134, 2160, 2145, 2131],

/***/ 2219:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMTextarea.js ***!
  \*****************************************************************************/
[2572, 2212, 2133, 2160, 2145, 2119, 2131],

/***/ 2220:
/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMultiChild.js ***!
  \****************************************************************************/
[2573, 2170, 2122, 2111, 2156, 2221, 2222],

/***/ 2221:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactChildReconciler.js ***!
  \*********************************************************************************/
[2574, 2156, 2168, 2173, 2217, 2131],

/***/ 2222:
/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/flattenChildren.js ***!
  \****************************************************************************/
[2575, 2217, 2131],

/***/ 2223:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/shallowEqual.js ***!
  \********************************************************************************/
117,

/***/ 2224:
/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactEventListener.js ***!
  \*******************************************************************************/
[2576, 2225, 2115, 2162, 2151, 2134, 2160, 2145, 2187, 2226],

/***/ 2225:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/EventListener.js ***!
  \*********************************************************************************/
[2577, 2121],

/***/ 2226:
/*!**********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/getUnboundedScrollPosition.js ***!
  \**********************************************************************************************/
120,

/***/ 2227:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactInjection.js ***!
  \***************************************************************************/
[2578, 2129, 2137, 2170, 2228, 2174, 2135, 2175, 2124, 2152, 2160],

/***/ 2228:
/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactClass.js ***!
  \***********************************************************************/
[2579, 2229, 2148, 2171, 2172, 2230, 2145, 2164, 2119, 2123, 2185, 2131],

/***/ 2229:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactComponent.js ***!
  \***************************************************************************/
[2580, 2230, 2149, 2164, 2119, 2131],

/***/ 2230:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactNoopUpdateQueue.js ***!
  \*********************************************************************************/
[2581, 2131],

/***/ 2231:
/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactReconcileTransaction.js ***!
  \**************************************************************************************/
[2582, 2161, 2162, 2135, 2147, 2232, 2163, 2145],

/***/ 2232:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactInputSelection.js ***!
  \********************************************************************************/
[2583, 2233, 2165, 2201, 2235],

/***/ 2233:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMSelection.js ***!
  \******************************************************************************/
[2584, 2115, 2234, 2181],

/***/ 2234:
/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/getNodeForCharacterOffset.js ***!
  \**************************************************************************************/
128,

/***/ 2235:
/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/getActiveElement.js ***!
  \************************************************************************************/
129,

/***/ 2236:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SelectEventPlugin.js ***!
  \******************************************************************************/
[2585, 2136, 2179, 2115, 2232, 2183, 2235, 2188, 2185, 2223],

/***/ 2237:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ServerReactRootIndex.js ***!
  \*********************************************************************************/
131,

/***/ 2238:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SimpleEventPlugin.js ***!
  \******************************************************************************/
[2586, 2136, 2225, 2179, 2134, 2239, 2183, 2240, 2241, 2192, 2244, 2245, 2193, 2246, 2121, 2242, 2119, 2185],

/***/ 2239:
/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticClipboardEvent.js ***!
  \************************************************************************************/
[2587, 2183],

/***/ 2240:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticFocusEvent.js ***!
  \********************************************************************************/
[2588, 2193],

/***/ 2241:
/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticKeyboardEvent.js ***!
  \***********************************************************************************/
[2589, 2193, 2242, 2243, 2194],

/***/ 2242:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/getEventCharCode.js ***!
  \*****************************************************************************/
136,

/***/ 2243:
/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/getEventKey.js ***!
  \************************************************************************/
[2590, 2242],

/***/ 2244:
/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticDragEvent.js ***!
  \*******************************************************************************/
[2591, 2192],

/***/ 2245:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticTouchEvent.js ***!
  \********************************************************************************/
[2592, 2193, 2194],

/***/ 2246:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticWheelEvent.js ***!
  \********************************************************************************/
[2593, 2192],

/***/ 2247:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SVGDOMPropertyConfig.js ***!
  \*********************************************************************************/
[2594, 2129],

/***/ 2248:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultPerf.js ***!
  \*****************************************************************************/
[2595, 2129, 2249, 2134, 2124, 2250],

/***/ 2249:
/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \*************************************************************************************/
[2596, 2145],

/***/ 2250:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/performanceNow.js ***!
  \**********************************************************************************/
[2597, 2251],

/***/ 2251:
/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/performance.js ***!
  \*******************************************************************************/
[2598, 2115],

/***/ 2252:
/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactVersion.js ***!
  \*************************************************************************/
146,

/***/ 2253:
/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/renderSubtreeIntoContainer.js ***!
  \***************************************************************************************/
[2599, 2134],

/***/ 2254:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMServer.js ***!
  \***************************************************************************/
[2600, 2177, 2255, 2252],

/***/ 2255:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactServerRendering.js ***!
  \*********************************************************************************/
[2601, 2198, 2148, 2151, 2154, 2256, 2257, 2160, 2164, 2168, 2119],

/***/ 2256:
/*!****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactServerBatchingStrategy.js ***!
  \****************************************************************************************/
150,

/***/ 2257:
/*!********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactServerRenderingTransaction.js ***!
  \********************************************************************************************/
[2602, 2162, 2161, 2163, 2145, 2121],

/***/ 2258:
/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactIsomorphic.js ***!
  \****************************************************************************/
[2603, 2216, 2229, 2228, 2259, 2148, 2260, 2213, 2252, 2145, 2262],

/***/ 2259:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMFactories.js ***!
  \******************************************************************************/
[2604, 2148, 2260, 2261],

/***/ 2260:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactElementValidator.js ***!
  \**********************************************************************************/
[2605, 2148, 2171, 2172, 2111, 2149, 2214, 2119, 2131],

/***/ 2261:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/mapObject.js ***!
  \*****************************************************************************/
155,

/***/ 2262:
/*!**********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/onlyChild.js ***!
  \**********************************************************************/
[2606, 2148, 2119],

/***/ 2263:
/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/deprecated.js ***!
  \***********************************************************************/
[2607, 2145, 2131],

/***/ 2264:
/*!******************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-dom/index.js ***!
  \******************************************************************/
[2608, 2110],

/***/ 2265:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/HighchartsHeatmapContainer.jsx ***!
  \********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var React = __webpack_require__(/*! react */ 2108);
	
	var Snap = __webpack_require__(/*! imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js */ 2266);
	
	var $ = __webpack_require__(/*! jquery */ 2267);
	
	var HeatmapData = __webpack_require__(/*! ./DataForHighcharts.js */ 2269);
	
	//*------------------------------------------------------------------*
	
	var HighchartsHeatmap = __webpack_require__(/*! ./HighchartsHeatmap.jsx */ 2272);
	__webpack_require__(/*! ./HighchartsHeatmapContainer.css */ 2432);
	
	var Anatomogram = __webpack_require__(/*! anatomogram */ 2434);
	
	var genomeBrowserTemplate = __webpack_require__(/*! ./genomeBrowserLink.js */ 2489);
	
	//*------------------------------------------------------------------*
	
	var ExperimentDescription = React.createClass({
	  displayName: 'ExperimentDescription',
	
	  propTypes: {
	    linksAtlasBaseURL: React.PropTypes.string.isRequired,
	    experiment: React.PropTypes.shape({
	      URL: React.PropTypes.string.isRequired,
	      description: React.PropTypes.string.isRequired,
	      species: React.PropTypes.string.isRequired
	    }).isRequired
	  },
	
	  render: function render() {
	
	    var experimentURL = this.props.linksAtlasBaseURL + this.props.experiment.URL;
	
	    return React.createElement(
	      'div',
	      { style: { width: "100%", paddingBottom: "20px" } },
	      React.createElement(
	        'div',
	        { id: 'experimentDescription' },
	        React.createElement(
	          'a',
	          { id: 'goto-experiment', className: 'gxaThickLink', title: 'Experiment Page', href: experimentURL },
	          this.props.experiment.description
	        )
	      ),
	      React.createElement(
	        'div',
	        { id: 'experimentOrganisms' },
	        'Organism: ',
	        React.createElement(
	          'span',
	          { style: { "fontStyle": "italic" } },
	          this.props.experiment.species
	        )
	      )
	    );
	  }
	
	});
	
	var Container = React.createClass({
	  displayName: 'Container',
	
	
	  getDefaultProps: function getDefaultProps() {
	    return {
	      referenceToAnatomogramContainer: "anatomogramContainer"
	    };
	  },
	  render: function render() {
	    var heatmapProps = {
	      heatmapConfig: this.props.heatmapConfig,
	      googleAnalyticsCallback: this.props.googleAnalyticsCallback,
	      heatmapData: this.props.heatmapData,
	      onHeatmapRedrawn: this._attachListenersToLabels
	    }; //overriden: ontologyIdsToHighlight, onOntologyIdIsUnderFocus
	    var anatomogramConfig = {
	      pathToFolderWithBundledResources: this.props.pathToFolderWithBundledResources,
	      anatomogramData: this.props.anatomogramData,
	      expressedTissueColour: this.props.heatmapConfig.isExperimentPage ? "gray" : "red",
	      hoveredTissueColour: this.props.heatmapConfig.isExperimentPage ? "red" : "purple",
	      atlasBaseURL: this.props.atlasBaseURL,
	      idsExpressedInExperiment: this._ontologyIdsForTissuesExpressedInAllRows()
	    };
	    var Wrapped = Anatomogram.wrapComponent(anatomogramConfig, HighchartsHeatmap, heatmapProps);
	    return this._showAnatomogram() ? React.createElement(Wrapped, { ref: this.props.referenceToAnatomogramContainer }) : React.createElement(HighchartsHeatmap, _extends({}, heatmapProps, { ontologyIdsToHighlight: [], onOntologyIdIsUnderFocus: function onOntologyIdIsUnderFocus() {} }));
	  },
	  _showAnatomogram: function _showAnatomogram() {
	    return this.props.showAnatomogram && this.props.anatomogramData && Object.keys(this.props.anatomogramData).length;
	  },
	
	  componentDidMount: function componentDidMount() {
	    this._attachListenersToLabels();
	  },
	
	  _ontologyIdsForTissuesExpressedInAllRows: function _ontologyIdsForTissuesExpressedInAllRows() {
	    //TODO be less copypastey
	    var _expressedFactors = function _expressedFactors(expressedFactorsPerRow) {
	      var o = expressedFactorsPerRow;
	      var vs = Object.keys(o).map(function (e) {
	        return o[e];
	      });
	      return [].concat.apply([], vs).filter(function uniq(e, ix, self) {
	        return self.indexOf(e) === ix;
	      });
	    };
	    var _expressedFactorsPerRow = function _expressedFactorsPerRow(profileRows) {
	      return profileRows.reduce(function (result, row) {
	        result[row.name] = row.expressions.filter(function (expression) {
	          return expression.value;
	        }).map(function (expression) {
	          return expression.svgPathId;
	        });
	        return result;
	      }, {});
	    };
	    return _expressedFactors(_expressedFactorsPerRow(this.props.profiles.rows));
	  },
	
	  _ontologyIdsForTissuesExpressedInRow: function _ontologyIdsForTissuesExpressedInRow(rowTitle) {
	    //TODO be more sane
	    var _expressedFactorsPerRow = function _expressedFactorsPerRow(profileRows) {
	      return profileRows.reduce(function (result, row) {
	        result[row.name] = row.expressions.filter(function (expression) {
	          return expression.value;
	        }).map(function (expression) {
	          return expression.svgPathId;
	        });
	        return result;
	      }, {});
	    };
	    return _expressedFactorsPerRow(this.props.profiles.rows)[rowTitle];
	  },
	
	  _attachListenersToLabels: function _attachListenersToLabels() {
	    if (!this._showAnatomogram()) {
	      return;
	    }
	    /*
	    I am a hack and I attach event listeners to the labels.
	    There seems to be no way to do it in the HighchartsHeatmap component -
	    the labels that are selected when HighchartsHeatmap.componentDidUpdate is called are redrawn when both components appear on the screen
	    */
	    Snap.selectAll('.highcharts-yaxis-labels > *').forEach(function (v) {
	      //careful - if the label doesn't fit, the element will have two children: displayed and full title
	      //here we assume the longest text is the correct title of the experiment
	      var title = v.selectAll('*').items.map(function (c) {
	        return c.node.textContent;
	      }).reduce(function (l, r) {
	        return l.length > r.length ? l : r;
	      }, "");
	      if (title) {
	        v.hover(function onMouseEnterSendTitle() {
	          this.refs[this.props.referenceToAnatomogramContainer].setState({ ontologyIdsForComponentContentUnderFocus: this._ontologyIdsForTissuesExpressedInRow(title) });
	        }, function onMouseLeaveSendNull() {
	          this.refs[this.props.referenceToAnatomogramContainer].setState({ ontologyIdsForComponentContentUnderFocus: [] });
	        }, this, this);
	      }
	    }, this);
	  }
	
	});
	
	var ContainerLoader = React.createClass({
	  displayName: 'ContainerLoader',
	
	  propTypes: {
	    pathToFolderWithBundledResources: React.PropTypes.string.isRequired,
	    sourceURL: React.PropTypes.string.isRequired,
	    atlasBaseURL: React.PropTypes.string.isRequired,
	    linksAtlasBaseURL: React.PropTypes.string.isRequired,
	    showAnatomogram: React.PropTypes.bool.isRequired,
	    isDifferential: React.PropTypes.bool.isRequired,
	    isMultiExperiment: React.PropTypes.bool.isRequired,
	    isWidget: React.PropTypes.bool.isRequired,
	    disableGoogleAnalytics: React.PropTypes.bool.isRequired,
	    fail: React.PropTypes.func,
	    googleAnalyticsCallback: React.PropTypes.func,
	    anatomogramDataEventEmitter: React.PropTypes.object
	  },
	
	  render: function render() {
	
	    var geneURL = this.props.linksAtlasBaseURL + "/query" + "?geneQuery=" + this.state.heatmapConfig.geneQuery + "&conditionQuery=" + this.state.heatmapConfig.conditionQuery + "&organism=" + this.state.heatmapConfig.species;
	
	    return React.createElement(
	      'div',
	      { ref: 'this' },
	      this._isReferenceExperiment() && this.state.experimentData ? React.createElement(ExperimentDescription, { experiment: this.state.experimentData, linksAtlasBaseURL: this.props.linksAtlasBaseURL }) : null,
	      this.state.ajaxCompleted ? this.state.error ? React.createElement(
	        'div',
	        { ref: 'gxaError' },
	        this.state.error
	      ) : React.createElement(Container, _extends({}, this.props, this.state)) : React.createElement(
	        'div',
	        { ref: 'loadingImagePlaceholder' },
	        React.createElement('img', { src: this.props.atlasBaseURL + "/resources/images/loading.gif" })
	      ),
	      this.props.isWidget ? React.createElement(
	        'div',
	        null,
	        React.createElement(
	          'p',
	          null,
	          React.createElement(
	            'a',
	            { href: geneURL },
	            'See more expression data at Expression Atlas.'
	          ),
	          React.createElement('br', null),
	          'This expression view is provided by ',
	          React.createElement(
	            'a',
	            { href: this.props.linksAtlasBaseURL },
	            'Expression Atlas'
	          ),
	          '.',
	          React.createElement('br', null),
	          'Please direct any queries or feedback to ',
	          React.createElement(
	            'a',
	            { href: 'mailto:arrayexpress-atlas@ebi.ac.uk' },
	            'arrayexpress-atlas@ebi.ac.uk'
	          )
	        )
	      ) : null
	    );
	  },
	
	  componentDidUpdate: function componentDidUpdate() {
	    if (this.props.anatomogramDataEventEmitter) {
	      if (this.state.anatomogramData && Object.keys(this.state.anatomogramData).length !== 0) {
	        this.props.anatomogramDataEventEmitter.emit('existAnatomogramData', true);
	      } else {
	        this.props.anatomogramDataEventEmitter.emit('existAnatomogramData', false);
	      }
	    }
	  },
	
	  getInitialState: function getInitialState() {
	    return {
	      ajaxCompleted: false,
	      error: false,
	      heatmapConfig: {},
	      profiles: {
	        rows: [],
	        minExpressionLevel: 0,
	        maxExpressionLevel: 0
	      },
	      jsonCoexpressions: [],
	      geneSetProfiles: {},
	      anatomogramData: {},
	      googleAnalyticsCallback: function googleAnalyticsCallback() {},
	      heatmapData: HeatmapData.EMPTY
	    };
	  },
	
	  _handleAjaxFailure: function _handleAjaxFailure(jqXHR, textStatus, errorThrown) {
	    if (this.props.fail) {
	      this.props.fail(jqXHR, textStatus, errorThrown);
	    } else {
	      this.setState({
	        ajaxCompleted: true,
	        error: textStatus === "parsererror" ? "Could not parse JSON response" : errorThrown
	      });
	    }
	  },
	
	  _onAjaxDone: function _onAjaxDone(data, textStatus, jqXHR) {
	    if (!this.isMounted()) {
	      this._handleAjaxFailure(jqXHR, textStatus, "DOM element not mounted!");
	    } else if (data.hasOwnProperty('error')) {
	      this._handleAjaxFailure(jqXHR, textStatus, data.error);
	    } else {
	      this.onAjaxSuccessful(data);
	    }
	  },
	
	  _isExperimentPage: function _isExperimentPage() {
	    return this.props.sourceURL.indexOf("/json/experiments/") > -1;
	  },
	
	  _isReferenceExperiment: function _isReferenceExperiment() {
	    return !this.props.isMultiExperiment && !this._isExperimentPage();
	  },
	
	  _introductoryMessage: function _introductoryMessage(profiles) {
	    var shownRows = profiles.rows.length,
	        totalRows = profiles.searchResultTotal;
	
	    var what = (this.props.isMultiExperiment ? 'experiment' : 'gene') + (totalRows > 1 ? 's' : '');
	
	    return 'Showing ' + shownRows + ' ' + (totalRows === shownRows ? what + ':' : 'of ' + totalRows + ' ' + what + ' found:');
	  },
	
	  onAjaxSuccessful: function onAjaxSuccessful(data) {
	    var capitalizeFirstLetter = function capitalizeFirstLetter(str) {
	      return !str ? str : str.charAt(0).toUpperCase() + str.substr(1);
	    };
	    var config = {
	      geneQuery: data.config.geneQuery,
	      atlasBaseURL: this.props.atlasBaseURL,
	      isExperimentPage: this._isExperimentPage(),
	      isMultiExperiment: this.props.isMultiExperiment,
	      isReferenceExperiment: this._isReferenceExperiment(),
	      isDifferential: this.props.isDifferential,
	      introductoryMessage: this._introductoryMessage(data.profiles),
	      xAxisLegendName: capitalizeFirstLetter(data.config.columnType) || "Experimental condition",
	      yAxisLegendName: this._isExperimentPage() ? "Gene name" : "Experiment"
	    };
	    //See in heatmap-data.jsp which thirteen properties this config is populated with.
	    Object.assign(config, data.config);
	    Object.assign(config, { genomeBrowserTemplate: this._isExperimentPage() ? genomeBrowserTemplate(config) : "" });
	
	    this.setState({
	      ajaxCompleted: true,
	      heatmapConfig: Object.freeze(config),
	      columnHeaders: data.columnHeaders,
	      profiles: data.profiles,
	      jsonCoexpressions: data.jsonCoexpressions,
	      anatomogramData: data.anatomogram,
	      experimentData: data.experiment,
	      heatmapData: HeatmapData.get(data.profiles.rows, data.columnHeaders, config)
	    });
	  },
	
	  componentDidMount: function componentDidMount() {
	    var httpRequest = {
	      url: this.props.sourceURL,
	      dataType: "json",
	      method: "GET"
	    };
	
	    $.ajax(httpRequest).done(this._onAjaxDone).fail(this._handleAjaxFailure);
	
	    if (!this.props.disableGoogleAnalytics) {
	      (function (i, s, o, g, r, a, m) {
	        i['GoogleAnalyticsObject'] = r;i[r] = i[r] || function () {
	          (i[r].q = i[r].q || []).push(arguments);
	        }, i[r].l = 1 * new Date();a = s.createElement(o), m = s.getElementsByTagName(o)[0];a.async = 1;a.src = g;m.parentNode.insertBefore(a, m);
	      })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');
	
	      ga('create', 'UA-37676851-1', 'auto');
	      ga('send', 'pageview');
	      this.setState({ googleAnalyticsCallback: ga });
	    }
	  }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = ContainerLoader;

/***/ },

/***/ 2266:
/*!******************************************************************************************!*\
  !*** ./~/imports-loader?this=>window,fix=>module.exports=0!./~/snapsvg/dist/snap.svg.js ***!
  \******************************************************************************************/
574,

/***/ 2267:
/*!*********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/jquery/dist/jquery.js ***!
  \*********************************************************************/
[2821, 2268],

/***/ 2268:
/*!***********************************!*\
  !*** (webpack)/buildin/module.js ***!
  \***********************************/
628,

/***/ 2269:
/*!**********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/DataForHighcharts.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var _ = __webpack_require__(/*! lodash */ 2270);
	
	//*------------------------------------------------------------------*
	var Orderings = __webpack_require__(/*! ./OrderingsForHighcharts.js */ 2271);
	
	//*------------------------------------------------------------------*
	
	var EMPTY = {
	  xAxisCategories: {},
	  columnOrderings: {},
	  yAxisCategories: {},
	  rowOrderings: {},
	  dataSeries: [[], [], [], []]
	};
	
	var getXAxisCategories = function getXAxisCategories(columnHeaders, config) {
	  return columnHeaders.map(config.isDifferential ? function (columnHeader) {
	    return { "label": columnHeader.displayName,
	      "id": columnHeader.id,
	      "info": {
	        trackId: columnHeader.id
	      } };
	  } : function (columnHeader) {
	    return { "label": columnHeader.factorValue,
	      "id": columnHeader.factorValueOntologyTermId,
	      "info": {
	        trackId: config.isExperimentPage ? columnHeader.assayGroupId : ""
	      } };
	  });
	};
	
	var getYAxisCategories = function getYAxisCategories(rows, config) {
	  return rows.map(config.isDifferential ? function (profile) {
	    return { "label": profile.name,
	      "id": profile.id,
	      "info": {
	        trackId: profile.id
	      } };
	  } : function (profile) {
	    return { "label": profile.name,
	      "id": profile.id + "?geneQuery=" + config.geneQuery + (profile.serializedFilterFactors ? "&serializedFilterFactors=" + encodeURIComponent(profile.serializedFilterFactors) : ""),
	      "info": {
	        trackId: config.isExperimentPage ? profile.id : ""
	      } };
	  });
	};
	
	var __dataPointFromExpression = function __dataPointFromExpression(infoCommonForTheRow, columnNumber, expression, rowNumber) {
	  //TODO make this function more complicated and determine the info to pass about each point here.
	  return expression.hasOwnProperty("value") ? { x: rowNumber, y: columnNumber, value: expression.value, info: infoCommonForTheRow } : expression.hasOwnProperty("foldChange") ? {
	    x: rowNumber,
	    y: columnNumber,
	    value: +expression.foldChange,
	    info: {
	      pValue: expression.pValue,
	      foldChange: expression.foldChange,
	      tStat: expression.tStat } } : null;
	};
	
	var _dataPointsFromRow = _.curry(function (config, row, columnNumber) {
	  return row.expressions.map(_.curry(__dataPointFromExpression, 4)({ unit: unitForThisRowOfData(row, config) }, columnNumber)).filter(function (el) {
	    return el;
	  });
	}, 3);
	
	var unitForThisRowOfData = function unitForThisRowOfData(row, config) {
	  return config.isDifferential ? "Log2Fold change" //this is what we use for point.value, but we don't actually use this unit for display. See Formatters.jsx.
	  : config.isMultiExperiment ? row.experimentType === "RNASEQ_MRNA_BASELINE" ? row.name.indexOf("FANTOM") > -1 ? "TPM" : "FPKM" : "" : "" //TODO determine the units on the experiment page as well
	  ;
	};
	
	var _groupByExperimentType = function _groupByExperimentType(chain, config) {
	  return chain.map(function (row, columnNumber) {
	    return [row.experimentType, _dataPointsFromRow(config, row, columnNumber)];
	  }).groupBy(function (experimentTypeAndRow) {
	    return experimentTypeAndRow[0];
	  }).mapValues(function (rows) {
	    return rows.map(function (experimentTypeAndRow) {
	      return experimentTypeAndRow[1];
	    });
	  }).mapValues(_.flatten).toPairs();
	};
	
	var _experimentsIntoDataSeriesByThresholds = function _experimentsIntoDataSeriesByThresholds(thresholds) {
	  return function (experimentType, dataPoints) {
	    return dataPoints.map(function (dataPoint) {
	      return [_.sortedIndex(thresholds[experimentType] || thresholds.DEFAULT, dataPoint.value), dataPoint];
	    }.bind(this));
	  };
	};
	
	var getDataSeries = function getDataSeries(profilesRows, config) {
	  var _fns = [_.lt, _.eq, _.gt].map(function (f) {
	    return function (point) {
	      return f(point.value, 0);
	    };
	  });
	  var _belowCutoff = _fns[1];
	  return config.isMultiExperiment ? _dataSplitByThresholds({
	    RNASEQ_MRNA_BASELINE: [0, 10, 1000],
	    PROTEOMICS_BASELINE: [0, 0.001, 8],
	    DEFAULT: [0, 10, 1000]
	  }, ["Below cutoff", "Low", "Medium", "High"], ["#eaeaea", "#45affd", "#1E74CA", "#024990"], profilesRows, config) : config.isDifferential ? _dataProportionallyInEachSeries(profilesRows, config, _fns, [["High down", "Down"], ["Below cutoff"], ["Up", "High up"]], [["#0000ff", "#8cc6ff"], ["#808080"], ["#e9967a", "#b22222"]]) : _dataProportionallyInEachSeries(profilesRows, config, [_belowCutoff, _.negate(_belowCutoff)], [["Below cutoff"], ["Low", "Medium", "High"]], [["#808080"], ["#8cc6ff", "#0000ff", "#0000b3"]]);
	};
	var _splitDataSetByProportion = function _splitDataSetByProportion(data, names, colours) {
	  var sortedValues = data.map(function (point) {
	    return point.value;
	  }).sort(function (l, r) {
	    return l - r;
	  });
	  var howManyPointsInTotal = data.length;
	  var howManyDataSetsToSplitIn = names.length;
	  return _bucketsIntoSeries(names, colours)(_.chain(data).map(function (point) {
	    return [Math.floor(_.sortedIndex(sortedValues, point.value) / howManyPointsInTotal * howManyDataSetsToSplitIn), point];
	  })).value();
	};
	
	var _dataProportionallyInEachSeries = function _dataProportionallyInEachSeries(profilesRows, config, filters, names, colors) {
	  var points = _.flatten(profilesRows.map(_dataPointsFromRow(config)));
	  return _.flatten(_.range(filters.length).map(function (i) {
	    return _splitDataSetByProportion(points.filter(filters[i]), names[i], colors[i]);
	  }));
	};
	
	var _bucketsIntoSeries = _.curry(function (names, colours, chain) {
	  return chain.groupBy(_.spread(function (dataSeriesAssigned, point) {
	    return dataSeriesAssigned;
	  })).mapValues(function (bucket) {
	    return bucket.map(_.spread(function (dataSeriesAssigned, point) {
	      return point;
	    }));
	  }).transform(function (result, bucket, bucketNumber) {
	    result[bucketNumber].data = bucket;
	  }, _.range(names.length).map(function (i) {
	    return {
	      name: names[i],
	      colour: colours[i],
	      data: []
	    };
	  }));
	}, 3);
	
	var _dataSplitByThresholds = function _dataSplitByThresholds(thresholds, names, colours, profilesRows, config) {
	  return _bucketsIntoSeries(names, colours)(_groupByExperimentType(_.chain(profilesRows), config).map(_.spread(_experimentsIntoDataSeriesByThresholds(thresholds))).flatten()).value();
	};
	
	var extractExpressionValues = function extractExpressionValues(rows, isDifferential) {
	  var _valueFieldExtractor = function _valueFieldExtractor(valueField) {
	    return function (expression) {
	      return expression.hasOwnProperty(valueField) ? { value: +expression[valueField] } : {};
	    };
	  };
	  return rows.map(function (row) {
	    return row.expressions.map(_valueFieldExtractor(isDifferential ? "foldChange" : "value"));
	  });
	};
	
	var getTheWholeDataObject = function getTheWholeDataObject(rows, columnHeaders, config) {
	  var expressions = extractExpressionValues(rows, config.isDifferential);
	
	  return {
	    xAxisCategories: getXAxisCategories(columnHeaders, config),
	    yAxisCategories: getYAxisCategories(rows, config),
	    orderings: Orderings.create(expressions, columnHeaders, rows, config),
	    dataSeries: getDataSeries(rows, config)
	  };
	};
	
	//*------------------------------------------------------------------*
	exports.EMPTY = EMPTY;
	exports.get = getTheWholeDataObject;

/***/ },

/***/ 2270:
/*!****************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/lodash/lodash.js ***!
  \****************************************************************/
[2845, 2268],

/***/ 2271:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/OrderingsForHighcharts.js ***!
  \***************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var _ = __webpack_require__(/*! lodash */ 2270);
	
	//*------------------------------------------------------------------*
	
	
	//*------------------------------------------------------------------*
	
	//apply rank first,use comparator to resolve ties
	var createOrdering = function createOrdering(rank, comparator, arr) {
	  return arr.map(function (e, ix) {
	    return [e, ix];
	  }).sort(function (e_ixLeft, e_ixRight) {
	    return (//lower ranks go to the beginning of series
	      rank[e_ixLeft[1]] - rank[e_ixRight[1]] || comparator(e_ixLeft[0], e_ixRight[0])
	    );
	  }).map(function (e_ix) {
	    return e_ix[1];
	  });
	};
	
	var createAlphabeticalOrdering = function createAlphabeticalOrdering(property, arr) {
	  return createOrdering(arr.map(_.constant(0)), comparatorByProperty(property), arr);
	};
	
	var comparatorByProperty = _.curry(function (property, e1, e2) {
	  return e1[property].localeCompare(e2[property]);
	});
	
	var rankColumnsByWhereTheyAppearFirst = function rankColumnsByWhereTheyAppearFirst(expressions) {
	  return _.chain(expressions).map(function (row) {
	    return row.map(function (e) {
	      return +e.hasOwnProperty("value");
	    });
	  }).thru(_.spread(_.zip)).map(function (column) {
	    return column.map(function (e, ix) {
	      return e * (ix + 1);
	    }).filter(_.identity);
	  }).map(_.min).value();
	};
	
	var highestColumnRankPossible = function highestColumnRankPossible(expressions) {
	  return expressions.length ? expressions[0].length : Number.MAX_VALUE;
	};
	
	var thresholdColumnsByExpressionAboveCutoff = function thresholdColumnsByExpressionAboveCutoff(expressions) {
	  return rankColumnsByExpression(expressions, 0).map(function (e) {
	    //check if the function assigned the rank value corresponding to everything filtered off
	    return e === highestColumnRankPossible(expressions) ? 1 : 0;
	  });
	};
	
	var rankColumnsByExpression = function rankColumnsByExpression(expressions, minimalExpression) {
	  var includeInRanking = typeof minimalExpression === 'number' ? function (e) {
	    return e.hasOwnProperty("value") && !isNaN(e.value) && Math.abs(e.value) > minimalExpression;
	  } : function (e) {
	    return e.hasOwnProperty("value") && !isNaN(e.value);
	  };
	  return _.chain(expressions).map(function (row) {
	    var valuesInRow = row.filter(includeInRanking).map(function (e) {
	      return e.value;
	    }).sort(function (l, r) {
	      return r - l;
	    }).filter(function (e, ix, self) {
	      return self.indexOf(e) === ix;
	    });
	    return row.map(function (e) {
	      return includeInRanking(e) ? valuesInRow.indexOf(e.value) : "missing";
	    });
	  }).thru(_.spread(_.zip)).map(function (ranks) {
	    return ranks.filter(_.negate(isNaN));
	  }).map(function (ranks) {
	    return ranks.length ? _.sum(ranks) / ranks.length : highestColumnRankPossible(expressions);
	  }).value();
	};
	
	var rankColumnsByThreshold = function rankColumnsByThreshold(threshold, expressions) {
	  return expressions.map(function (row) {
	    return row.map(function (point) {
	      return +(point.hasOwnProperty("value") && point.value !== 0);
	    });
	  }).reduce(function (r1, r2) {
	    return r1.map(function (el, ix) {
	      return el + r2[ix];
	    }, _.fill(Array(expressions.length ? expressions[0].length : 0), 0));
	  }).map(function (countOfExperimentsWhereTissueExpressedAboveCutoff) {
	    return countOfExperimentsWhereTissueExpressedAboveCutoff > expressions.length * threshold ? 0 : 1;
	  });
	};
	
	var noOrdering = function noOrdering(arr) {
	  return arr.map(function (el, ix) {
	    return ix;
	  });
	};
	
	var combineRanks = function combineRanks(ranksAndTheirWeighings) {
	  return _.chain(ranksAndTheirWeighings).map(_.spread(function (ranks, weighing) {
	    return ranks.map(_.curry(_.multiply, 2)(weighing));
	  })).thru(_.spread(_.zip)).map(_.sum).value();
	};
	
	var createOrderings = function createOrderings(expressions, columnHeaders, rows, config) {
	  var transposed = _.zip.apply(_, expressions);
	  return config.isMultiExperiment || config.isReferenceExperiment ? {
	    "Default": {
	      columns: createAlphabeticalOrdering("factorValue", columnHeaders),
	      rows: noOrdering(rows)
	    },
	    "Alphabetical order": {
	      columns: createAlphabeticalOrdering("factorValue", columnHeaders),
	      rows: createAlphabeticalOrdering("name", rows)
	    },
	    "Gene expression rank": {
	      columns: createOrdering(combineRanks([[rankColumnsByWhereTheyAppearFirst(expressions), 1], [rankColumnsByExpression(expressions), 1e3], [rankColumnsByThreshold(0.05 + 0.4 / Math.pow(1 + transposed.length / 8, 0.4), expressions), 1e6], [thresholdColumnsByExpressionAboveCutoff(expressions), 1e7]]), comparatorByProperty("factorValue"), columnHeaders),
	      rows: createOrdering(combineRanks([[rankColumnsByExpression(transposed), 1e3], [rankColumnsByThreshold(0.05 + 0.4 / (1 + expressions.length / 5), transposed), 1e6]]), comparatorByProperty("name"), rows)
	    }
	  } : {
	    "Default": {
	      columns: noOrdering(columnHeaders),
	      rows: noOrdering(rows)
	    }
	  };
	};
	
	//*------------------------------------------------------------------*
	
	exports.create = createOrderings;

/***/ },

/***/ 2272:
/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/HighchartsHeatmap.jsx ***!
  \***********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var React = __webpack_require__(/*! react */ 2108);
	
	var ReactHighcharts = __webpack_require__(/*! react-highcharts */ 2273);
	var Highcharts = ReactHighcharts.Highcharts;
	__webpack_require__(/*! highcharts-heatmap */ 2275)(Highcharts);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ./HighchartsHeatmap.css */ 2276);
	var Button = __webpack_require__(/*! react-bootstrap/lib/Button */ 2280);
	var DownloadProfilesButton = __webpack_require__(/*! download-profiles-button */ 2320);
	
	var FormattersFactory = __webpack_require__(/*! ./Formatters.jsx */ 2415);
	var PropTypes = __webpack_require__(/*! ./PropTypes.js */ 2420);
	var createColorAxis = __webpack_require__(/*! ./ColoursForHighcharts.js */ 2422);
	var hash = __webpack_require__(/*! object-hash */ 2431);
	
	//*------------------------------------------------------------------*
	
	var HeatmapContainer = React.createClass({
	    displayName: 'HeatmapContainer',
	
	    propTypes: {
	        heatmapConfig: React.PropTypes.object.isRequired,
	        googleAnalyticsCallback: React.PropTypes.func.isRequired,
	        heatmapData: PropTypes.HeatmapData,
	        onHeatmapRedrawn: React.PropTypes.func.isRequired,
	        ontologyIdsToHighlight: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
	        onOntologyIdIsUnderFocus: React.PropTypes.func.isRequired
	    },
	
	    getInitialState: function getInitialState() {
	        return {
	            ordering: "Default"
	        };
	    },
	
	    _data: function _data() {
	        var permuteX = function (x) {
	            return this.props.heatmapData.orderings[this.state.ordering].columns.indexOf(x);
	        }.bind(this);
	
	        var permuteY = function (y) {
	            return this.props.heatmapData.orderings[this.state.ordering].rows.indexOf(y);
	        }.bind(this);
	
	        var permutePoint = function permutePoint(point) {
	            return {
	                x: permuteX(point.x),
	                y: permuteY(point.y),
	                value: point.value,
	                info: point.info
	            };
	        };
	
	        var permuteArray = function permuteArray(arr, permute) {
	            return arr.map(function (el, ix) {
	                return [el, permute(ix)];
	            }).sort(function (l, r) {
	                return l[1] - r[1];
	            }).map(function (el) {
	                return el[0];
	            });
	        };
	
	        return {
	            dataSeries: this.props.heatmapData.dataSeries.map(function (series) {
	                return series.data.map(permutePoint);
	            }),
	            xAxisCategories: permuteArray(this.props.heatmapData.xAxisCategories, permuteX),
	            yAxisCategories: permuteArray(this.props.heatmapData.yAxisCategories, permuteY)
	        };
	    },
	
	    _labels: function _labels() {
	        return this.props.heatmapData.dataSeries.map(function (e) {
	            return {
	                colour: e.colour,
	                name: e.name
	            };
	        });
	    },
	
	    render: function render() {
	        var marginRight = 60;
	        return React.createElement(
	            'div',
	            null,
	            React.createElement(HeatmapOptions, {
	                marginRight: marginRight,
	                introductoryMessage: this.props.heatmapConfig.introductoryMessage,
	                downloadOptions: {
	                    downloadProfilesURL: this.props.heatmapConfig.downloadProfilesURL,
	                    atlasBaseURL: this.props.heatmapConfig.atlasBaseURL,
	                    disclaimer: this.props.heatmapConfig.disclaimer
	                },
	                orderings: {
	                    available: Object.keys(this.props.heatmapData.orderings),
	                    current: this.state.ordering,
	                    onSelect: function (orderingChosen) {
	                        this.setState({ ordering: orderingChosen });
	                    }.bind(this)
	                },
	                googleAnalyticsCallback: this.props.googleAnalyticsCallback,
	                showUsageMessage: this.props.heatmapData.xAxisCategories.length > 100 }),
	            React.createElement(HeatmapCanvas, {
	                marginRight: marginRight,
	                ontologyIdsToHighlight: this.props.ontologyIdsToHighlight,
	                data: this._data(),
	                labels: this._labels(),
	                colorAxis: this.props.heatmapConfig.isExperimentPage ? createColorAxis(this.props.heatmapData.dataSeries) : undefined,
	                onHeatmapRedrawn: this.props.onHeatmapRedrawn,
	                formatters: FormattersFactory(this.props.heatmapConfig),
	                genomeBrowserTemplate: this.props.heatmapConfig.genomeBrowserTemplate,
	                onUserSelectsColumn: this.props.onOntologyIdIsUnderFocus })
	        );
	    }
	
	});
	
	var HeatmapCanvas = React.createClass({
	    displayName: 'HeatmapCanvas',
	
	    propTypes: {
	        marginRight: React.PropTypes.number.isRequired,
	        ontologyIdsToHighlight: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
	        data: React.PropTypes.shape({
	            dataSeries: PropTypes.PointsInDataSeries,
	            xAxisCategories: PropTypes.AxisCategories,
	            yAxisCategories: PropTypes.AxisCategories
	        }),
	        labels: React.PropTypes.arrayOf(React.PropTypes.shape({
	            name: React.PropTypes.string,
	            colour: React.PropTypes.string
	        })).isRequired,
	        colorAxis: React.PropTypes.object,
	        onHeatmapRedrawn: React.PropTypes.func.isRequired,
	        formatters: React.PropTypes.shape({
	            xAxis: PropTypes.Formatter,
	            yAxis: PropTypes.Formatter,
	            tooltip: PropTypes.Formatter
	        }).isRequired,
	        genomeBrowserTemplate: React.PropTypes.string.isRequired,
	        onUserSelectsColumn: React.PropTypes.func.isRequired
	    },
	
	    getInitialState: function getInitialState() {
	        return {
	            dataSeriesToShow: this.props.labels.map(function (e) {
	                return true;
	            })
	        };
	    },
	
	    _dataToShow: function _dataToShow() {
	        var all_s = function (propertyToPickFromEachPoint) {
	            return this.props.data.dataSeries.filter(function (e, ix) {
	                return this.state.dataSeriesToShow[ix];
	            }.bind(this)).reduce(function (l, r) {
	                return l.concat(r);
	            }, []).map(function (e) {
	                return e[propertyToPickFromEachPoint];
	            }).filter(function (e, ix, self) {
	                return self.indexOf(e) === ix;
	            }).sort(function (l, r) {
	                return l - r;
	            });
	        }.bind(this);
	
	        var allXs = all_s("x");
	        var allYs = all_s("y");
	
	        var ds = this.props.data.dataSeries.map(function (e, ix) {
	            return this.state.dataSeriesToShow[ix] ? e : [];
	        }.bind(this)).map(function (series) {
	            return series.map(function (point) {
	                return {
	                    x: allXs.indexOf(point.x),
	                    y: allYs.indexOf(point.y),
	                    value: point.value,
	                    info: point.info
	                };
	            }).filter(function (point) {
	                return point.x > -1 && point.y > -1;
	            });
	        });
	
	        return {
	            dataSeries: this.props.labels.map(function (e, ix) {
	                return {
	                    name: e.name,
	                    color: e.colour,
	                    /*For smaller experiments, separate the cells so that they look easier to distinguish
	                      For large experiments this would make it show up as one big white block so don't do it.
	                      Change the magic number 200 if you feel like it.
	                      */
	                    borderWidth: this.props.data.xAxisCategories.length > 200 ? 0 : 1,
	                    borderColor: "white",
	                    data: ds[ix]
	                };
	            }.bind(this)),
	            xAxisCategories: this.props.data.xAxisCategories.filter(function (e, ix) {
	                return allXs.indexOf(ix) > -1;
	            }),
	            yAxisCategories: this.props.data.yAxisCategories.filter(function (e, ix) {
	                return allYs.indexOf(ix) > -1;
	            })
	
	        };
	    },
	
	    componentDidUpdate: function componentDidUpdate() {
	        this.props.onHeatmapRedrawn();
	    },
	
	    _makeLabelToggle: function _makeLabelToggle(ix) {
	        return function () {
	            this.setState(function (previousState) {
	                return {
	                    dataSeriesToShow: previousState.dataSeriesToShow.map(function (e, jx) {
	                        return ix === jx ? !e : e;
	                    })
	                };
	            });
	        }.bind(this);
	    },
	
	    renderLegend: function renderLegend() {
	        return React.createElement(
	            'div',
	            { id: 'barcharts_legend_list_items', ref: 'barcharts_legend_items' },
	            this.props.labels.map(function (e, ix) {
	                return React.createElement(HeatmapLegendBox, { key: e.name,
	                    name: e.name,
	                    colour: e.colour,
	                    on: this.state.dataSeriesToShow[ix],
	                    onClickCallback: this._makeLabelToggle(ix) });
	            }.bind(this)),
	            React.createElement(
	                'div',
	                { className: 'legend-item special' },
	                React.createElement('span', { className: 'icon icon-generic', 'data-icon': 'i', 'data-toggle': 'tooltip', 'data-placement': 'bottom',
	                    title: 'Baseline expression levels in RNA-seq experiments are in FPKM or TPM. Low: 0-10, Medium: 11-1000,  High: >1000. Proteomics expression levels are mapped to low, medium, high on per experiment basis.' })
	            ),
	            React.createElement(HeatmapLegendBox, { key: "No data available",
	                name: "No data available",
	                colour: "white",
	                on: true,
	                onClickCallback: function onClickCallback() {} })
	        );
	    },
	
	    render: function render() {
	
	        var data = this._dataToShow();
	        return React.createElement(
	            'div',
	            { id: 'highcharts_container' },
	            data.dataSeries.map(function (e) {
	                return e.data;
	            }).reduce(function (l, r) {
	                return l.concat(r);
	            }, []).length ? React.createElement(HeatmapDrawing, _extends({ dataForTheChart: data }, this.props)) : React.createElement(
	                'p',
	                null,
	                ' No data in the series currently selected. '
	            ),
	            this.renderLegend()
	        );
	    }
	
	});
	
	var HeatmapDrawing = React.createClass({
	    displayName: 'HeatmapDrawing',
	
	    propTypes: {
	        dataForTheChart: React.PropTypes.object.isRequired,
	        ontologyIdsToHighlight: React.PropTypes.arrayOf(React.PropTypes.string).isRequired
	        //And also more props needed for the config
	    },
	
	    shouldComponentUpdate: function shouldComponentUpdate(nextProps) {
	        return hash.MD5(nextProps.dataForTheChart) !== hash.MD5(this.props.dataForTheChart);
	    },
	
	    componentWillReceiveProps: function componentWillReceiveProps(nextProps) {
	        var chart = this.refs.chart.getChart();
	        var forEachXNotInYsEmit = function forEachXNotInYsEmit(xs, ys, eventName) {
	            xs.filter(function (id) {
	                return ys.indexOf(id) == -1;
	            }).filter(function uniq(id, ix, self) {
	                return ix == self.indexOf(id);
	            }).forEach(function (id) {
	                Highcharts.fireEvent(chart, eventName, { svgPathId: id });
	            }.bind(this));
	        };
	        forEachXNotInYsEmit(nextProps.ontologyIdsToHighlight, this.props.ontologyIdsToHighlight, 'handleGxaAnatomogramTissueMouseEnter');
	        forEachXNotInYsEmit(this.props.ontologyIdsToHighlight, nextProps.ontologyIdsToHighlight, 'handleGxaAnatomogramTissueMouseLeave');
	    },
	
	    render: function render() {
	        //TODO calculating this based on dataForTheChart would be more correct.
	        var xAxisLongestHeaderLength = Math.max.apply(null, this.props.data.xAxisCategories.map(function (category) {
	            return category.label.length;
	        }));
	
	        var marginTop = this.props.data.xAxisCategories.length < 10 ? 30 : // labels aren’t tilted
	        this.props.data.xAxisCategories.length < 50 ? Math.min(150, Math.round(xAxisLongestHeaderLength * 3.75)) : // labels at -45°
	        Math.min(250, Math.round(xAxisLongestHeaderLength * 5.5)); // labels at -90°
	
	        var maxWidthFraction = 1 - 1 / Math.pow(0.2 * this._countColumnsToShow() + 1, 4);
	        //TODO the marginRight value of props used to be the same here and in top legend.
	        //Probably it's time to get rid of this prop.
	        var marginRight = this.props.marginRight * (1 + 10 / Math.pow(1 + this._countColumnsToShow(), 2));
	
	        return React.createElement(
	            'div',
	            { style: { maxWidth: maxWidthFraction * 100 + "%" } },
	            React.createElement(ReactHighcharts, { config: this._highchartsOptions(marginTop, marginRight, this.props.dataForTheChart), ref: 'chart' })
	        );
	    },
	
	    _countColumnsToShow: function _countColumnsToShow() {
	        return this.props.data.xAxisCategories.length;
	    },
	
	    _highchartsOptions: function _highchartsOptions(marginTop, marginRight, data) {
	        return {
	            plotOptions: {
	                heatmap: {
	                    turboThreshold: 0
	                },
	                series: {
	                    cursor: !!this.props.genomeBrowserTemplate ? "pointer" : undefined,
	                    point: {
	                        events: {
	                            mouseOver: function mouseOver() {
	                                this.series.chart.userOptions.onUserSelectsColumn(this.series.xAxis.categories[this.x].id);
	                            },
	                            click: !this.props.genomeBrowserTemplate ? function () {} : function () {
	                                var x = this.series.xAxis.categories[this.x].info.trackId;
	                                var y = this.series.yAxis.categories[this.y].info.trackId;
	                                window.open(this.series.chart.userOptions.genomeBrowserTemplate.replace(/__x__/g, x).replace(/__y__/g, y), "_blank");
	                            }
	                        }
	                    },
	                    events: {
	                        mouseOut: function mouseOut() {
	                            this.chart.userOptions.onUserSelectsColumn("");
	                        }
	                    },
	
	                    states: {
	                        hover: {
	                            color: '#eeec38' //#edab12 color cell on mouse over
	                        },
	                        select: {
	                            color: '#eeec38'
	                        }
	                    }
	                }
	            },
	            credits: {
	                enabled: false //remove Highcharts text in the bottom right corner
	            },
	            chart: {
	                type: 'heatmap',
	                marginTop: marginTop,
	                marginRight: marginRight, //leave space for tilted long headers
	                spacingTop: 0,
	                plotBorderWidth: 1,
	                height: Math.max(70, data.yAxisCategories.length * 30 + marginTop),
	                zoomType: 'x',
	                events: {
	                    handleGxaAnatomogramTissueMouseEnter: function handleGxaAnatomogramTissueMouseEnter(e) {
	                        Highcharts.each(this.series, function (series) {
	                            Highcharts.each(series.points, function (point) {
	                                if (point.series.xAxis.categories[point.x].id === e.svgPathId) {
	                                    point.select(true, true);
	                                }
	                            });
	                        });
	                    },
	                    handleGxaAnatomogramTissueMouseLeave: function handleGxaAnatomogramTissueMouseLeave(e) {
	                        var points = this.getSelectedPoints();
	                        if (points.length > 0) {
	                            Highcharts.each(points, function (point) {
	                                point.select(false);
	                            });
	                        }
	                    }
	                }
	            },
	            legend: {
	                enabled: false
	            },
	            title: null,
	            colorAxis: this.props.colorAxis || undefined,
	            xAxis: { //assays
	                tickLength: 5,
	                tickColor: 'rgb(192, 192, 192)',
	                lineColor: 'rgb(192, 192, 192)',
	                labels: {
	                    style: {
	                        fontSize: '9px',
	                        textOverflow: 'ellipsis'
	                    },
	                    autoRotation: [-45, -90],
	                    formatter: function () {
	                        var f = this.props.formatters.xAxis;return function () {
	                            return f(this.value);
	                        };
	                    }.bind(this)()
	                },
	                opposite: 'true',
	                categories: data.xAxisCategories
	            },
	            yAxis: { //experiments or bioentities
	                useHTML: true,
	                reversed: true,
	                labels: {
	                    style: {
	                        fontSize: '10px',
	                        color: '#148ff3'
	                    },
	                    formatter: function () {
	                        var f = this.props.formatters.yAxis;return function () {
	                            return f(this.value);
	                        };
	                    }.bind(this)()
	                },
	                categories: data.yAxisCategories,
	                title: null,
	                gridLineWidth: 0,
	                minorGridLineWidth: 0,
	                endOnTick: false
	            },
	            tooltip: {
	                useHTML: true,
	                formatter: function () {
	                    var f = this.props.formatters.tooltip;return function () {
	                        return f(this.series, this.point);
	                    };
	                }.bind(this)()
	            },
	            onUserSelectsColumn: this.props.onUserSelectsColumn,
	            genomeBrowserTemplate: this.props.genomeBrowserTemplate,
	            series: data.dataSeries
	        };
	    }
	});
	
	var HeatmapLegendBox = React.createClass({
	    displayName: 'HeatmapLegendBox',
	
	    propTypes: {
	        name: React.PropTypes.string.isRequired,
	        colour: React.PropTypes.string.isRequired,
	        on: React.PropTypes.bool.isRequired,
	        onClickCallback: React.PropTypes.func.isRequired
	    },
	
	    render: function render() {
	        return React.createElement(
	            'div',
	            { className: "legend-item " + (this.props.on ? "special" : "legend-item-off"), onClick: this.props.onClickCallback },
	            React.createElement('div', { style: { background: this.props.colour }, className: 'legend-rectangle' }),
	            React.createElement(
	                'span',
	                { style: { verticalAlign: "middle" } },
	                this.props.name
	            )
	        );
	    }
	});
	
	var propsForOrderingDropdown = {
	    available: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
	    current: React.PropTypes.string.isRequired,
	    onSelect: React.PropTypes.func.isRequired
	};
	
	var OrderingDropdown = React.createClass({
	    displayName: 'OrderingDropdown',
	
	    propTypes: propsForOrderingDropdown,
	
	    getInitialState: function getInitialState() {
	        return { selected: this.props.current };
	    },
	
	    handleChange: function handleChange(e) {
	        this.state.selected = e.target.value;
	        this.props.onSelect(this.state.selected);
	        this.forceUpdate();
	    },
	
	    render: function render() {
	
	        var createOption = function createOption(option, key) {
	            return React.createElement(
	                'option',
	                { key: key, value: option },
	                option
	            );
	        };
	
	        return React.createElement(
	            'div',
	            { style: { float: "left", marginRight: "10px", marginTop: "1px" } },
	            React.createElement(
	                'span',
	                null,
	                'Sort by: '
	            ),
	            React.createElement(
	                'select',
	                { onChange: this.handleChange, value: this.state.selected },
	                this.props.available.map(createOption)
	            )
	        );
	    }
	});
	
	var HeatmapOptions = React.createClass({
	    displayName: 'HeatmapOptions',
	
	    propTypes: {
	        marginRight: React.PropTypes.number.isRequired,
	        downloadOptions: React.PropTypes.object.isRequired,
	        googleAnalyticsCallback: React.PropTypes.func.isRequired,
	        showUsageMessage: React.PropTypes.bool.isRequired,
	        orderings: React.PropTypes.shape(propsForOrderingDropdown)
	    },
	
	    render: function render() {
	        return React.createElement(
	            'div',
	            { ref: 'countAndLegend', className: 'gxaHeatmapCountAndLegend', style: { paddingBottom: '15px', position: 'sticky' } },
	            React.createElement(
	                'div',
	                { style: { display: 'inline-block', verticalAlign: 'top' } },
	                this.props.introductoryMessage
	            ),
	            React.createElement(
	                'div',
	                { style: { display: "inline-block", verticalAlign: "top", float: "right", marginRight: this.props.marginRight } },
	                this.props.orderings.available.length > 1 ? React.createElement(OrderingDropdown, {
	                    available: this.props.orderings.available,
	                    current: this.props.orderings.current,
	                    onSelect: this.props.orderings.onSelect }) : null,
	                React.createElement(DownloadProfilesButton, _extends({ ref: 'downloadProfilesButton'
	                }, this.props.downloadOptions, {
	                    onDownloadCallbackForAnalytics: function () {
	                        this.props.googleAnalyticsCallback('send', 'event', 'HeatmapHighcharts', 'downloadData');
	                    }.bind(this) }))
	            ),
	            this.props.showUsageMessage ? React.createElement(
	                'div',
	                { style: { fontSize: 'small', color: 'grey' } },
	                'Select a section of the heatmap to zoom in'
	            ) : null
	        );
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = HeatmapContainer;

/***/ },

/***/ 2273:
/*!****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-highcharts/dist/ReactHighcharts.js ***!
  \****************************************************************************************/
[2829, 2268, 2108, 2274, 2108, 2274],

/***/ 2274:
/*!*******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-highcharts/~/highcharts/highcharts.js ***!
  \*******************************************************************************************/
[2830, 2268],

/***/ 2275:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/highcharts-heatmap/heatmap.js ***!
  \*****************************************************************************/
[2846, 2268],

/***/ 2276:
/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/HighchartsHeatmap.css ***!
  \***********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./HighchartsHeatmap.css */ 2277);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 2279)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./HighchartsHeatmap.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./HighchartsHeatmap.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2277:
/*!**************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/css-loader!./expression-atlas-heatmap-highcharts/src/HighchartsHeatmap.css ***!
  \**************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 2278)();
	// imports
	
	
	// module
	exports.push([module.id, ".gene_title {\n    text-align: center;\n    font-size: 70%;\n    border: 0 red solid;\n}\n\n#container {\n    min-width: 410px;\n    min-height: 210px;\n}\n\n#barcharts_legend_list_items {\n    color: #606060;\n    margin-left: 180px;\n    border: 0 solid olive;\n}\n\n#barcharts_legend_list_items .legend-text {\n    display: inline-block;\n    padding: 4px;\n    vertical-align: middle;\n}\n\n#barcharts_legend_list_items .legend-item {\n    cursor: pointer;\n    display: inline-block;\n    padding: 4px;\n    vertical-align: middle;\n}\n\n#barcharts_legend_list_items .legend-item:hover {\n    color: black;\n}\n\n#barcharts_legend_list_items .legend-item.legend-item-off {\n    color: #ccc;\n}\n\n#barcharts_legend_list_items .legend-item.legend-item-off div {\n    background-color: #f7f7f7;\n}\n\n#barcharts_legend_list_items .legend-item.special {\n    cursor: default;\n    margin-left: 8px;\n    color: black;\n}\n\n#barcharts_legend_list_items .legend-item .legend-rectangle {\n    width: 12px;\n    height: 12px;\n    border: 1px rgba(0, 0, 0, 0.2) solid;\n    display: inline-block;\n    margin-right: 4px;\n    vertical-align: middle;\n}\n\n#barcharts_legend_list_items .legend-item .icon-generic:before {\n    font-size: 180%;\n    color: #7e7e7e;\n}\n\n#barcharts_legend_list_items .legend-item:hover .icon-generic:before {\n    color: #353535;\n}\n\n/*****************************************EBI font*******************************************************/\n@font-face {\n    font-family: 'EBI-Conceptual';\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.eot');\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.eot?#iefix') format('embedded-opentype'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.woff') format('woff'),\n    local('\\263A'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.svg#EBI-Conceptual') format('svg'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal\n}\n\n@font-face {\n    font-family: 'EBI-Functional';\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.eot');\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.eot?#iefix') format('embedded-opentype'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.woff') format('woff'),\n    local('\\263A'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.svg#EBI-Functional') format('svg'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal\n}\n\n@font-face {\n    font-family: 'EBI-Generic';\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot');\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot?#iefix') format('embedded-opentype'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.woff') format('woff'),\n    local('\\263A'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.svg#EBI-Generic') format('svg'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal\n}\n\n@font-face {\n    font-family: 'EBI-Species';\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot');\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.woff') format('woff'),\n    local('\\263A'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal\n}\n\n@font-face {\n    font-family: 'EBI-SocialMedia';\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.eot');\n    src: url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.eot?#iefix') format('embedded-opentype'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.woff') format('woff'),\n    local('\\263A'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.svg#EBI-Species') format('svg'),\n    url('https://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.ttf') format('truetype');\n    font-weight: normal;\n    font-style: normal\n}\n\n.icon-socialmedia:before {\n    font-family: 'EBI-SocialMedia';\n    margin: 0 0.3em 0 0;\n    content: attr(data-icon);\n    color: white;\n    font-size: 100%;\n}\n\n.icon-conceptual:before {\n    font-family: 'EBI-Conceptual';\n    margin: 0 0.3em 0 0;\n    content: attr(data-icon);\n    color: #7e7e7e;\n    font-size: 180%;\n}\n\n.icon-species:before {\n    font-family: 'EBI-Species';\n    font-size: 100%;\n    color: white;\n    content: attr(data-icon);\n    margin: 0 0 0 0;\n}\n\n.icon {\n    text-decoration: none;\n    font-style: normal;\n}\n\n.icon-static:before, .icon-generic:before {\n    font-family: 'EBI-Generic';\n    font-size: 100%;\n    color: #BBB;\n    content: attr(data-icon);\n    margin: 0 0 0 0;\n}\n\n.icon-functional:before {\n    font-family: 'EBI-Functional';\n    font-size: 100%;\n    color: white;\n    content: attr(data-icon);\n    margin: 0 0.3em 0 0;\n}\n\n/*****************************************END EBI font*******************************************************/\n", ""]);
	
	// exports


/***/ },

/***/ 2278:
/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/css-loader/lib/css-base.js ***!
  \**************************************************************************/
578,

/***/ 2279:
/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/style-loader/addStyles.js ***!
  \*************************************************************************/
579,

/***/ 2280:
/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/Button.js ***!
  \*****************************************************************************/
[2655, 2281, 2296, 2297, 2307, 2308, 2108, 2309, 2311, 2316, 2318],

/***/ 2281:
/*!***************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/helpers/inherits.js ***!
  \***************************************************************************************************/
[2610, 2282, 2285],

/***/ 2282:
/*!********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/object/create.js ***!
  \********************************************************************************************************/
[2611, 2283],

/***/ 2283:
/*!*********************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/create.js ***!
  \*********************************************************************************************************************/
[2612, 2284],

/***/ 2284:
/*!**************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.js ***!
  \**************************************************************************************************************/
165,

/***/ 2285:
/*!******************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/object/set-prototype-of.js ***!
  \******************************************************************************************************************/
[2613, 2286],

/***/ 2286:
/*!*******************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/set-prototype-of.js ***!
  \*******************************************************************************************************************************/
[2614, 2287, 2290],

/***/ 2287:
/*!****************************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.set-prototype-of.js ***!
  \****************************************************************************************************************************************/
[2615, 2288, 2293],

/***/ 2288:
/*!*********************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.export.js ***!
  \*********************************************************************************************************************/
[2616, 2289, 2290, 2291],

/***/ 2289:
/*!*********************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.global.js ***!
  \*********************************************************************************************************************/
170,

/***/ 2290:
/*!*******************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.core.js ***!
  \*******************************************************************************************************************/
171,

/***/ 2291:
/*!******************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.ctx.js ***!
  \******************************************************************************************************************/
[2617, 2292],

/***/ 2292:
/*!*************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.a-function.js ***!
  \*************************************************************************************************************************/
173,

/***/ 2293:
/*!************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.set-proto.js ***!
  \************************************************************************************************************************/
[2618, 2284, 2294, 2295, 2291],

/***/ 2294:
/*!************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.is-object.js ***!
  \************************************************************************************************************************/
175,

/***/ 2295:
/*!************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.an-object.js ***!
  \************************************************************************************************************************/
[2619, 2294],

/***/ 2296:
/*!***********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/helpers/class-call-check.js ***!
  \***********************************************************************************************************/
177,

/***/ 2297:
/*!**************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/helpers/extends.js ***!
  \**************************************************************************************************/
[2620, 2298],

/***/ 2298:
/*!********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/object/assign.js ***!
  \********************************************************************************************************/
[2621, 2299],

/***/ 2299:
/*!*********************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/assign.js ***!
  \*********************************************************************************************************************/
[2622, 2300, 2290],

/***/ 2300:
/*!******************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.assign.js ***!
  \******************************************************************************************************************************/
[2623, 2288, 2301],

/***/ 2301:
/*!****************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.object-assign.js ***!
  \****************************************************************************************************************************/
[2624, 2284, 2302, 2304, 2306],

/***/ 2302:
/*!************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.to-object.js ***!
  \************************************************************************************************************************/
[2625, 2303],

/***/ 2303:
/*!**********************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.defined.js ***!
  \**********************************************************************************************************************/
184,

/***/ 2304:
/*!**********************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.iobject.js ***!
  \**********************************************************************************************************************/
[2626, 2305],

/***/ 2305:
/*!******************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.cof.js ***!
  \******************************************************************************************************************/
186,

/***/ 2306:
/*!********************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.fails.js ***!
  \********************************************************************************************************************/
187,

/***/ 2307:
/*!******************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/helpers/interop-require-default.js ***!
  \******************************************************************************************************************/
193,

/***/ 2308:
/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/classnames/index.js ***!
  \*************************************************************************************/
195,

/***/ 2309:
/*!*****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-prop-types/lib/elementType.js ***!
  \*****************************************************************************************************/
[2651, 2108, 2310],

/***/ 2310:
/*!************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-prop-types/lib/common.js ***!
  \************************************************************************************************/
272,

/***/ 2311:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/styleMaps.js ***!
  \********************************************************************************/
[2653, 2298, 2282, 2312],

/***/ 2312:
/*!******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/core-js/object/keys.js ***!
  \******************************************************************************************************/
[2627, 2313],

/***/ 2313:
/*!*******************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/fn/object/keys.js ***!
  \*******************************************************************************************************************/
[2628, 2314, 2290],

/***/ 2314:
/*!****************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/es6.object.keys.js ***!
  \****************************************************************************************************************************/
[2629, 2302, 2315],

/***/ 2315:
/*!*************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/~/core-js/library/modules/$.object-sap.js ***!
  \*************************************************************************************************************************/
[2630, 2288, 2290, 2306],

/***/ 2316:
/*!*******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/utils/bootstrapUtils.js ***!
  \*******************************************************************************************/
[2652, 2297, 2307, 2108, 2311, 2317],

/***/ 2317:
/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/invariant/browser.js ***!
  \**************************************************************************************/
277,

/***/ 2318:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/SafeAnchor.js ***!
  \*********************************************************************************/
[2656, 2281, 2296, 2297, 2319, 2307, 2108, 2309],

/***/ 2319:
/*!********************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/babel-runtime/helpers/object-without-properties.js ***!
  \********************************************************************************************************************/
188,

/***/ 2320:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/download-profiles-button/index.js ***!
  \*********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/DownloadProfilesButton.jsx */ 2321);

/***/ },

/***/ 2321:
/*!*******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/download-profiles-button/src/DownloadProfilesButton.jsx ***!
  \*******************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 2108);
	
	var Modal = __webpack_require__(/*! react-bootstrap/lib/Modal */ 2322);
	var Button = __webpack_require__(/*! react-bootstrap/lib/Button */ 2280);
	var Glyphicon = __webpack_require__(/*! react-bootstrap/lib/Glyphicon */ 2399);
	var Tooltip = __webpack_require__(/*! react-bootstrap/lib/Tooltip */ 2400);
	var OverlayTrigger = __webpack_require__(/*! react-bootstrap/lib/OverlayTrigger */ 2402);
	
	var Disclaimers = __webpack_require__(/*! ./Disclaimers.jsx */ 2414);
	
	//*------------------------------------------------------------------*
	
	
	//*------------------------------------------------------------------*
	var DownloadProfilesButton = React.createClass({
	    displayName: 'DownloadProfilesButton',
	
	    propTypes: {
	        atlasBaseURL: React.PropTypes.string.isRequired,
	        downloadProfilesURL: React.PropTypes.string.isRequired,
	        disclaimer: React.PropTypes.string.isRequired,
	        onDownloadCallbackForAnalytics: React.PropTypes.func.isRequired
	    },
	    getInitialState: function getInitialState() {
	        return { showModal: false };
	    },
	
	    _closeModal: function _closeModal() {
	        this.setState({ showModal: false });
	    },
	
	    _disclaimer: function _disclaimer() {
	        return this.props.disclaimer && Disclaimers[this.props.disclaimer] || null;
	    },
	
	    _afterDownloadButtonClicked: function _afterDownloadButtonClicked() {
	        if (!this._disclaimer()) {
	            this._commenceDownload();
	        } else {
	            this.setState({ showModal: true });
	        }
	    },
	
	    _commenceDownload: function _commenceDownload() {
	        this.props.onDownloadCallbackForAnalytics();
	        window.location.href = this.props.atlasBaseURL + this.props.downloadProfilesURL;
	    },
	
	    _commenceDownloadAndCloseModal: function _commenceDownloadAndCloseModal() {
	        this._commenceDownload();
	        this._closeModal();
	    },
	
	    render: function render() {
	
	        return React.createElement(
	            'a',
	            { ref: 'downloadProfilesLink', onClick: this._afterDownloadButtonClicked },
	            React.createElement(
	                Button,
	                { bsSize: 'xsmall' },
	                React.createElement(Glyphicon, { style: { verticalAlign: 'middle', paddingBottom: '2px' }, glyph: 'download-alt' }),
	                React.createElement(
	                    'span',
	                    { style: { verticalAlign: 'middle', paddingTop: '2px' } },
	                    ' Download all results'
	                )
	            ),
	            React.createElement(
	                Modal,
	                { show: this.state.showModal, onHide: this._closeModal, bsSize: 'large' },
	                React.createElement(Modal.Header, { closeButton: true }),
	                React.createElement(
	                    Modal.Body,
	                    { style: { maxHeight: '360px' } },
	                    this._disclaimer()
	                ),
	                React.createElement(
	                    Modal.Footer,
	                    null,
	                    React.createElement(
	                        Button,
	                        { onClick: this._closeModal },
	                        'Close'
	                    ),
	                    React.createElement(
	                        Button,
	                        { bsStyle: 'primary', onClick: this._commenceDownloadAndCloseModal },
	                        'Continue downloading'
	                    )
	                )
	            )
	        );
	    }
	});
	
	module.exports = DownloadProfilesButton;

/***/ },

/***/ 2322:
/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/Modal.js ***!
  \****************************************************************************/
[2676, 2297, 2319, 2312, 2307, 2308, 2323, 2330, 2325, 2331, 2332, 2108, 2264, 2361, 2382, 2388, 2309, 2311, 2316, 2390, 2393, 2394, 2395, 2397, 2398],

/***/ 2323:
/*!*********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/events/index.js ***!
  \*********************************************************************************************/
[2677, 2324, 2326, 2327],

/***/ 2324:
/*!******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/events/on.js ***!
  \******************************************************************************************/
[2659, 2325],

/***/ 2325:
/*!*******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/util/inDOM.js ***!
  \*******************************************************************************************/
201,

/***/ 2326:
/*!*******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/events/off.js ***!
  \*******************************************************************************************/
[2660, 2325],

/***/ 2327:
/*!**********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/events/filter.js ***!
  \**********************************************************************************************/
[2678, 2328, 2329],

/***/ 2328:
/*!***********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/query/contains.js ***!
  \***********************************************************************************************/
[2632, 2325],

/***/ 2329:
/*!*******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/query/querySelectorAll.js ***!
  \*******************************************************************************************************/
363,

/***/ 2330:
/*!**********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/ownerDocument.js ***!
  \**********************************************************************************************/
199,

/***/ 2331:
/*!***************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/util/scrollbarSize.js ***!
  \***************************************************************************************************/
[2679, 2325],

/***/ 2332:
/*!**********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/object/pick.js ***!
  \**********************************************************************************************/
[2662, 2333, 2350, 2352, 2353, 2360],

/***/ 2333:
/*!*******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/baseFlatten.js ***!
  \*******************************************************************************************************/
[2647, 2334, 2335, 2345, 2336, 2342],

/***/ 2334:
/*!*****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/arrayPush.js ***!
  \*****************************************************************************************************/
266,

/***/ 2335:
/*!***************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/lang/isArguments.js ***!
  \***************************************************************************************************/
[2643, 2336, 2342],

/***/ 2336:
/*!*******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/isArrayLike.js ***!
  \*******************************************************************************************************/
[2640, 2337, 2344],

/***/ 2337:
/*!*****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/getLength.js ***!
  \*****************************************************************************************************/
[2641, 2338],

/***/ 2338:
/*!********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/baseProperty.js ***!
  \********************************************************************************************************/
[2642, 2339],

/***/ 2339:
/*!****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/toObject.js ***!
  \****************************************************************************************************/
[2635, 2340, 2341, 2343],

/***/ 2340:
/*!************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/lang/isObject.js ***!
  \************************************************************************************************/
209,

/***/ 2341:
/*!************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/lang/isString.js ***!
  \************************************************************************************************/
[2636, 2342],

/***/ 2342:
/*!********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/isObjectLike.js ***!
  \********************************************************************************************************/
211,

/***/ 2343:
/*!******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/support.js ***!
  \******************************************************************************************/
212,

/***/ 2344:
/*!****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/isLength.js ***!
  \****************************************************************************************************/
221,

/***/ 2345:
/*!***********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/lang/isArray.js ***!
  \***********************************************************************************************/
[2644, 2346, 2344, 2342],

/***/ 2346:
/*!*****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/getNative.js ***!
  \*****************************************************************************************************/
[2637, 2347],

/***/ 2347:
/*!************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/lang/isNative.js ***!
  \************************************************************************************************/
[2638, 2348, 2349, 2342],

/***/ 2348:
/*!**************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/lang/isFunction.js ***!
  \**************************************************************************************************/
[2639, 2340],

/***/ 2349:
/*!********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/isHostObject.js ***!
  \********************************************************************************************************/
217,

/***/ 2350:
/*!********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/bindCallback.js ***!
  \********************************************************************************************************/
[2646, 2351],

/***/ 2351:
/*!***************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/utility/identity.js ***!
  \***************************************************************************************************/
251,

/***/ 2352:
/*!*******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/pickByArray.js ***!
  \*******************************************************************************************************/
[2648, 2339],

/***/ 2353:
/*!**********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/pickByCallback.js ***!
  \**********************************************************************************************************/
[2649, 2354],

/***/ 2354:
/*!*****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/baseForIn.js ***!
  \*****************************************************************************************************/
[2650, 2355, 2357],

/***/ 2355:
/*!***************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/baseFor.js ***!
  \***************************************************************************************************/
[2633, 2356],

/***/ 2356:
/*!*********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/createBaseFor.js ***!
  \*********************************************************************************************************/
[2634, 2339],

/***/ 2357:
/*!************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/object/keysIn.js ***!
  \************************************************************************************************/
[2645, 2358, 2335, 2345, 2348, 2359, 2344, 2340, 2341, 2343],

/***/ 2358:
/*!*****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/arrayEach.js ***!
  \*****************************************************************************************************/
227,

/***/ 2359:
/*!***************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/internal/isIndex.js ***!
  \***************************************************************************************************/
225,

/***/ 2360:
/*!*****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/lodash-compat/function/restParam.js ***!
  \*****************************************************************************************************/
270,

/***/ 2361:
/*!*********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/Modal.js ***!
  \*********************************************************************************************/
[2680, 2108, 2362, 2363, 2365, 2366, 2369, 2367, 2385, 2386, 2325, 2387, 2328, 2368],

/***/ 2362:
/*!*****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/warning/browser.js ***!
  \*****************************************************************************************************/
279,

/***/ 2363:
/*!*****************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/react-prop-types/lib/componentOrElement.js ***!
  \*****************************************************************************************************************************/
[2681, 2108, 2364],

/***/ 2364:
/*!*******************************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/react-prop-types/lib/utils/createChainableTypeChecker.js ***!
  \*******************************************************************************************************************************************/
368,

/***/ 2365:
/*!**********************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/~/react-prop-types/lib/elementType.js ***!
  \**********************************************************************************************************************/
[2682, 2108, 2364],

/***/ 2366:
/*!**********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/Portal.js ***!
  \**********************************************************************************************/
[2683, 2108, 2264, 2363, 2367, 2368],

/***/ 2367:
/*!***********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/ownerDocument.js ***!
  \***********************************************************************************************************/
[2661, 2264, 2330],

/***/ 2368:
/*!**********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/getContainer.js ***!
  \**********************************************************************************************************/
[2684, 2264],

/***/ 2369:
/*!****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/ModalManager.js ***!
  \****************************************************************************************************/
[2685, 2370, 2378, 2331, 2382, 2384],

/***/ 2370:
/*!********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/style/index.js ***!
  \********************************************************************************************/
[2669, 2371, 2373, 2375, 2377],

/***/ 2371:
/*!***************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/util/camelizeStyle.js ***!
  \***************************************************************************************************/
[2670, 2372],

/***/ 2372:
/*!**********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/util/camelize.js ***!
  \**********************************************************************************************/
330,

/***/ 2373:
/*!****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/util/hyphenateStyle.js ***!
  \****************************************************************************************************/
[2671, 2374],

/***/ 2374:
/*!***********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/util/hyphenate.js ***!
  \***********************************************************************************************/
332,

/***/ 2375:
/*!*******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/style/getComputedStyle.js ***!
  \*******************************************************************************************************/
[2672, 2376, 2371],

/***/ 2376:
/*!**************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/util/babelHelpers.js ***!
  \**************************************************************************************************/
198,

/***/ 2377:
/*!**************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/style/removeStyle.js ***!
  \**************************************************************************************************/
334,

/***/ 2378:
/*!********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/class/index.js ***!
  \********************************************************************************************/
[2686, 2379, 2381, 2380],

/***/ 2379:
/*!***********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/class/addClass.js ***!
  \***********************************************************************************************/
[2687, 2380],

/***/ 2380:
/*!***********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/class/hasClass.js ***!
  \***********************************************************************************************/
375,

/***/ 2381:
/*!**************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/class/removeClass.js ***!
  \**************************************************************************************************/
376,

/***/ 2382:
/*!***********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/isOverflowing.js ***!
  \***********************************************************************************************************/
[2688, 2383, 2330],

/***/ 2383:
/*!***********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/query/isWindow.js ***!
  \***********************************************************************************************/
378,

/***/ 2384:
/*!**************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/manageAriaHidden.js ***!
  \**************************************************************************************************************/
379,

/***/ 2385:
/*!**************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/addEventListener.js ***!
  \**************************************************************************************************************/
[2658, 2324, 2326],

/***/ 2386:
/*!**************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/addFocusListener.js ***!
  \**************************************************************************************************************/
380,

/***/ 2387:
/*!**********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/activeElement.js ***!
  \**********************************************************************************************/
[2631, 2376, 2330],

/***/ 2388:
/*!****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-prop-types/lib/deprecated.js ***!
  \****************************************************************************************************/
[2664, 2389],

/***/ 2389:
/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/warning/browser.js ***!
  \************************************************************************************/
279,

/***/ 2390:
/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/Fade.js ***!
  \***************************************************************************/
[2675, 2281, 2296, 2297, 2307, 2108, 2308, 2391],

/***/ 2391:
/*!**************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/Transition.js ***!
  \**************************************************************************************************/
[2673, 2108, 2264, 2392, 2324, 2308],

/***/ 2392:
/*!******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/transition/properties.js ***!
  \******************************************************************************************************/
[2674, 2325],

/***/ 2393:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/ModalDialog.js ***!
  \**********************************************************************************/
[2689, 2297, 2307, 2308, 2108, 2311, 2316],

/***/ 2394:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/ModalBody.js ***!
  \********************************************************************************/
[2690, 2281, 2296, 2297, 2307, 2308, 2108, 2316],

/***/ 2395:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/ModalHeader.js ***!
  \**********************************************************************************/
[2691, 2281, 2296, 2319, 2297, 2307, 2308, 2108, 2316, 2396],

/***/ 2396:
/*!**************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/utils/createChainedFunction.js ***!
  \**************************************************************************************************/
282,

/***/ 2397:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/ModalTitle.js ***!
  \*********************************************************************************/
[2692, 2281, 2296, 2297, 2307, 2308, 2108, 2316],

/***/ 2398:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/ModalFooter.js ***!
  \**********************************************************************************/
[2693, 2281, 2296, 2297, 2307, 2308, 2108, 2316],

/***/ 2399:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/Glyphicon.js ***!
  \********************************************************************************/
[2667, 2297, 2307, 2308, 2108, 2388],

/***/ 2400:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/Tooltip.js ***!
  \******************************************************************************/
[2704, 2297, 2307, 2308, 2108, 2401, 2316],

/***/ 2401:
/*!***********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-prop-types/lib/isRequiredForA11y.js ***!
  \***********************************************************************************************************/
274,

/***/ 2402:
/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/OverlayTrigger.js ***!
  \*************************************************************************************/
[2703, 2297, 2312, 2307, 2328, 2332, 2108, 2264, 2389, 2403, 2396],

/***/ 2403:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/lib/Overlay.js ***!
  \******************************************************************************/
[2694, 2281, 2296, 2297, 2319, 2307, 2108, 2404, 2309, 2390, 2308],

/***/ 2404:
/*!***********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/Overlay.js ***!
  \***********************************************************************************************/
[2695, 2108, 2366, 2405, 2412, 2365],

/***/ 2405:
/*!************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/Position.js ***!
  \************************************************************************************************/
[2696, 2308, 2108, 2264, 2363, 2406, 2368, 2367],

/***/ 2406:
/*!***************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/calculatePosition.js ***!
  \***************************************************************************************************************/
[2697, 2407, 2408, 2410, 2367],

/***/ 2407:
/*!*********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/query/offset.js ***!
  \*********************************************************************************************/
[2698, 2328, 2383, 2330],

/***/ 2408:
/*!***********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/query/position.js ***!
  \***********************************************************************************************/
[2699, 2376, 2407, 2409, 2410, 2411, 2370],

/***/ 2409:
/*!***************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/query/offsetParent.js ***!
  \***************************************************************************************************/
[2700, 2376, 2330, 2370],

/***/ 2410:
/*!************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/query/scrollTop.js ***!
  \************************************************************************************************/
[2701, 2383],

/***/ 2411:
/*!*************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/dom-helpers/query/scrollLeft.js ***!
  \*************************************************************************************************/
[2702, 2383],

/***/ 2412:
/*!********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/RootCloseWrapper.js ***!
  \********************************************************************************************************/
[2657, 2108, 2264, 2385, 2413, 2367],

/***/ 2413:
/*!*******************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-bootstrap/~/react-overlays/lib/utils/createChainedFunction.js ***!
  \*******************************************************************************************************************/
294,

/***/ 2414:
/*!********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/download-profiles-button/src/Disclaimers.jsx ***!
  \********************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 2108);
	
	var BlueprintText = React.createElement(
	    "div",
	    null,
	    React.createElement("meta", { charSet: "UTF-8" }),
	    React.createElement(
	        "title",
	        null,
	        "The Blueprint project Data Reuse statement"
	    ),
	    React.createElement(
	        "h3",
	        null,
	        "The Blueprint project Data Reuse statement"
	    ),
	    React.createElement("br", null),
	    React.createElement(
	        "p",
	        null,
	        "This document refers to the reuse of data generated by the EC funded FP7 High Impact Project, Blueprint."
	    ),
	    React.createElement(
	        "p",
	        null,
	        "Blueprint regularly released analysis results via its ftp site and makes the raw sequence data available through the sequence archives at the EMBL-EBI. Much Blueprint data is generated from samples whose data must be released through a managed access process. For these data sets external users must apply for permission to access the data from the European Genome-phenome Archive (EGA) through the Blueprint Data Access Committee.",
	        React.createElement("br", null),
	        "The Blueprint consortium expects this data to be valuable to other researchers and in keeping with Fort Lauderdale principles data users may use the data for many studies, but are expected to allow the data producers to make the first presentations and to publish the first paper with global analyses of the data."
	    ),
	    React.createElement(
	        "h5",
	        null,
	        "Global analyses of Project data"
	    ),
	    React.createElement(
	        "p",
	        null,
	        "Blueprint plans to publish global analyses of the sequencing data, epigenetic marks, expression levels and variation both in the context of normal hematopoietic cells and of those neoplastic and non-neoplastic diseases studied within th econsortium. Talks, posters, and papers on all such analyses are to be published first by the Blueprint project, by approved presenters on behalf of the Project, with the Project as author. When the first major Project paper on these analyses is published, then researchers inside and outside the Project are free to present and publish using the Project data for these and other analyses."
	    ),
	    React.createElement(
	        "h5",
	        null,
	        "Large-scale analyses of Project data"
	    ),
	    React.createElement(
	        "p",
	        null,
	        "Groups within the Project may make presentations and publish papers on more extensive analyses of topics to be included in the main analysis presentations and papers, coincident with the main project analysis presentations and papers. The major points would be included in the main Project presentations and papers, but these additional presentations and papers allow more focused discussion of methods and results. The author list would include the Consortium."
	    ),
	    React.createElement(
	        "h5",
	        null,
	        "Methods development using Project data"
	    ),
	    React.createElement(
	        "p",
	        null,
	        "Researchers who have used small amounts of Project data (<= one chromosome) may present methods development posters, talks, and papers that include these data prior to the first major Project paper, without needing Project approval or authorship, although the Project should be acknowledged. Methods presentations or papers on global analyses or analyses using large amounts of Project data, on topics that the Consortium plans to examine, would be similar to large-scale analyses of Project data: researchers within the Project may make presentations or submit papers at the same time as the main Project presentations and papers, and others could do so after the Project publishes the first major analysis paper."
	    ),
	    React.createElement(
	        "h5",
	        null,
	        "Disease studies using Project data"
	    ),
	    React.createElement(
	        "p",
	        null,
	        "Researchers may present and publish on use of Project data in specific chromosome regions (that are not of general interest) or as summaries (such as number of differentially expressed genes in cell types assayed by Blueprint) for studies on diseases not studied by BLUEPRINT without Project approval, prior to the first major Project paper being published. The Project should not be listed as an author."
	    ),
	    React.createElement(
	        "h5",
	        null,
	        "Authors who use data from the project must acknowledge Blueprint using the following wording"
	    ),
	    React.createElement(
	        "p",
	        null,
	        "This study makes use of data generated by the Blueprint Consortium. A full list of the investigators who contributed to the generation of the data is available from www.blueprint-epigenome.eu. Funding for the project was provided by the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement no 282510 BLUEPRINT."
	    )
	);
	
	var ZebrafishText = React.createElement(
	    "div",
	    null,
	    React.createElement("meta", { charSet: "UTF-8" }),
	    React.createElement(
	        "title",
	        null,
	        "Data Reuse Statement"
	    ),
	    React.createElement(
	        "h3",
	        null,
	        "Data Reuse Statement"
	    ),
	    React.createElement("br", null),
	    React.createElement(
	        "p",
	        null,
	        "This is a pre-publication release in accordance with ",
	        React.createElement(
	            "a",
	            { href: "http://www.sanger.ac.uk/datasharing/" },
	            " the Fort Lauderdale Agreement "
	        ),
	        ". Feel free to search and download data on your genes of interest."
	    ),
	    React.createElement(
	        "p",
	        null,
	        "Equally, you can use the dataset to show developmental expression profiles for specific genes in your publications."
	    ),
	    React.createElement(
	        "p",
	        null,
	        "However, we ask that you refrain from publishing larger scale or genome-wide analyses of this dataset for 12 months from the time of deposition in Expression Atlas or until we have published our transcriptional time-course paper, whichever comes first."
	    ),
	    React.createElement(
	        "p",
	        null,
	        "For citations in publications before the paper is out please use this link to the Expression Atlas site (",
	        React.createElement(
	            "a",
	            { href: "http://www.ebi.ac.uk/gxa/experiments/E-ERAD-475" },
	            "http://www.ebi.ac.uk/gxa/experiments/E-ERAD-475"
	        ),
	        ") and acknowledge us: “We would like to thank the Busch-Nentwich lab for providing RNA-seq data.”"
	    )
	);
	
	module.exports = { "fortLauderdale": BlueprintText, "zebrafish": ZebrafishText };

/***/ },

/***/ 2415:
/*!****************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/Formatters.jsx ***!
  \****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	//*------------------------------------------------------------------*
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var React = __webpack_require__(/*! react */ 2108);
	var ReactDOMServer = __webpack_require__(/*! react-dom/server */ 2416);
	var scientificNotation = function scientificNotation(value) {
	  return React.createElement(
	    'b',
	    null,
	    __webpack_require__(/*! number-format */ 2417).scientificNotation(value)
	  );
	};
	var escapedHtmlDecoder = __webpack_require__(/*! he */ 2419);
	
	//*------------------------------------------------------------------*
	
	var Tooltip = React.createClass({
	  displayName: 'Tooltip',
	
	  propTypes: {
	    config: React.PropTypes.shape({
	      isDifferential: React.PropTypes.bool.isRequired,
	      xAxisLegendName: React.PropTypes.string.isRequired,
	      yAxisLegendName: React.PropTypes.string.isRequired,
	      genomeBrowserTemplate: React.PropTypes.string.isRequired
	    }).isRequired,
	    colour: React.PropTypes.string.isRequired,
	    xLabel: React.PropTypes.string.isRequired,
	    yLabel: React.PropTypes.string.isRequired,
	    value: React.PropTypes.number.isRequired,
	    unit: React.PropTypes.string,
	    foldChange: React.PropTypes.number,
	    pValue: React.PropTypes.string,
	    tStat: React.PropTypes.string
	  }, //TODO extend this prop checker.Props for this component are created dynamically so it's important. If differential, expect p-values and fold changes, etc.
	
	  render: function render() {
	    return React.createElement(
	      'div',
	      { style: { whiteSpace: "pre" } },
	      this._div(this.props.config.yAxisLegendName, this.props.yLabel),
	      this._div(this.props.config.xAxisLegendName, this.props.xLabel),
	      this.props.config.isDifferential ? [React.createElement(
	        'div',
	        { key: "" },
	        this._tinySquare(),
	        this._span("Fold change", this.props.foldChange)
	      ), this._div("P-value", this.props.pValue, scientificNotation), this._div("T-statistic", this.props.tStat)] : React.createElement(
	        'div',
	        null,
	        this._tinySquare(),
	        this._span("Expression level", this.props.value ? this.props.value + " " + (this.props.unit || "") : "Below cutoff")
	      ),
	      !!this.props.config.genomeBrowserTemplate ? this._info("Click on the cell to show expression in the Genome Browser") : null
	    );
	  },
	  _tinySquare: function _tinySquare() {
	    return React.createElement('span', { key: "Tiny " + this.props.colour + " square",
	      style: {
	        border: "1px rgb(192, 192, 192) solid",
	        marginRight: "2px",
	        width: "6px",
	        height: "6px",
	        display: "inline-block",
	        backgroundColor: this.props.colour
	      } });
	  },
	  _info: function _info(text) {
	    return React.createElement(
	      'div',
	      null,
	      React.createElement(
	        'i',
	        null,
	        text
	      )
	    );
	  },
	  _div: function _div(name, value, format) {
	    return name && value ? React.createElement(
	      'div',
	      { key: name + " " + value },
	      name + ": ",
	      value.length > 50 ? React.createElement('br', null) : null,
	      (format || this._bold)(value)
	    ) : null;
	  },
	  _span: function _span(name, value) {
	    return React.createElement(
	      'span',
	      { key: name + " " + value },
	      name + ": ",
	      value.length > 50 ? React.createElement('br', null) : null,
	      this._bold(value)
	    );
	  },
	  _bold: function _bold(value) {
	    return React.createElement(
	      'b',
	      null,
	      value
	    );
	  }
	});
	
	var YAxisLabel = React.createClass({
	  displayName: 'YAxisLabel',
	
	  propTypes: {
	    config: React.PropTypes.shape({
	      atlasBaseURL: React.PropTypes.string.isRequired,
	      isMultiExperiment: React.PropTypes.bool.isRequired
	    }).isRequired,
	    labelText: React.PropTypes.string.isRequired,
	    resourceId: React.PropTypes.string.isRequired
	  },
	  render: function render() {
	    return React.createElement(
	      'a',
	      { href: this.props.config.atlasBaseURL + (this.props.config.isMultiExperiment ? "/experiments/" : "/genes/") + this.props.resourceId },
	      this.props.labelText
	    );
	  }
	});
	
	var reactToHtml = function reactToHtml(component) {
	  return escapedHtmlDecoder.decode(ReactDOMServer.renderToStaticMarkup(component));
	};
	
	var makeFormatter = function makeFormatter(config) {
	  return {
	    xAxis: function Formatter(value) {
	      return value.label;
	    },
	    yAxis: function Formatter(value) {
	      return reactToHtml(React.createElement(YAxisLabel, {
	        config: config,
	        labelText: value.label,
	        resourceId: value.id }));
	    },
	    tooltip: function Formatter(series, point) {
	      var o = {
	        colour: point.color,
	        xLabel: series.xAxis.categories[point.x].label,
	        yLabel: series.yAxis.categories[point.y].label,
	        value: point.value
	      };
	      for (var key in point.options.info) {
	        if (point.options.info.hasOwnProperty(key)) {
	          o[key] = point.options.info[key];
	        }
	      }
	      return reactToHtml(React.createElement(Tooltip, _extends({}, o, { config: config })));
	    }
	  };
	};
	//*------------------------------------------------------------------*
	
	module.exports = makeFormatter;

/***/ },

/***/ 2416:
/*!*******************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-dom/server.js ***!
  \*******************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! react/lib/ReactDOMServer */ 2254);

/***/ },

/***/ 2417:
/*!**********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/number-format/index.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/NumberFormat.jsx */ 2418);

/***/ },

/***/ 2418:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/number-format/src/NumberFormat.jsx ***!
  \**********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	var React = __webpack_require__(/*! react */ 2108); // React is called in the transpiled JS files in the return statements
	
	//*------------------------------------------------------------------*
	
	function formatBaselineExpression(expressionLevel) {
	    var numberExpressionLevel = +expressionLevel;
	    return numberExpressionLevel >= 100000 || numberExpressionLevel < 0.1 ? formatScientificNotation(numberExpressionLevel.toExponential(1).replace('+', '')) : '' + numberExpressionLevel;
	}
	
	// expects number in the format #E# and displays exponent in superscript
	function formatScientificNotation(scientificNotationString) {
	
	    var formatParts = scientificNotationString.split(/[Ee]/);
	
	    if (formatParts.length == 1) {
	        return React.createElement(
	            'span',
	            null,
	            scientificNotationString
	        );
	    }
	
	    var mantissa = formatParts[0];
	    var exponent = formatParts[1];
	
	    return React.createElement(
	        'span',
	        null,
	        mantissa !== "1" ? mantissa + ' × ' : '',
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

/***/ 2419:
/*!********************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/he/he.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	var __WEBPACK_AMD_DEFINE_RESULT__;/* WEBPACK VAR INJECTION */(function(module, global) {'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };
	
	/*! https://mths.be/he v1.1.0 by @mathias | MIT license */
	;(function (root) {
	
		// Detect free variables `exports`.
		var freeExports = ( false ? 'undefined' : _typeof(exports)) == 'object' && exports;
	
		// Detect free variable `module`.
		var freeModule = ( false ? 'undefined' : _typeof(module)) == 'object' && module && module.exports == freeExports && module;
	
		// Detect free variable `global`, from Node.js or Browserified code,
		// and use it as `root`.
		var freeGlobal = (typeof global === 'undefined' ? 'undefined' : _typeof(global)) == 'object' && global;
		if (freeGlobal.global === freeGlobal || freeGlobal.window === freeGlobal) {
			root = freeGlobal;
		}
	
		/*--------------------------------------------------------------------------*/
	
		// All astral symbols.
		var regexAstralSymbols = /[\uD800-\uDBFF][\uDC00-\uDFFF]/g;
		// All ASCII symbols (not just printable ASCII) except those listed in the
		// first column of the overrides table.
		// https://html.spec.whatwg.org/multipage/syntax.html#table-charref-overrides
		var regexAsciiWhitelist = /[\x01-\x7F]/g;
		// All BMP symbols that are not ASCII newlines, printable ASCII symbols, or
		// code points listed in the first column of the overrides table on
		// https://html.spec.whatwg.org/multipage/syntax.html#table-charref-overrides.
		var regexBmpWhitelist = /[\x01-\t\x0B\f\x0E-\x1F\x7F\x81\x8D\x8F\x90\x9D\xA0-\uFFFF]/g;
	
		var regexEncodeNonAscii = /<\u20D2|=\u20E5|>\u20D2|\u205F\u200A|\u219D\u0338|\u2202\u0338|\u2220\u20D2|\u2229\uFE00|\u222A\uFE00|\u223C\u20D2|\u223D\u0331|\u223E\u0333|\u2242\u0338|\u224B\u0338|\u224D\u20D2|\u224E\u0338|\u224F\u0338|\u2250\u0338|\u2261\u20E5|\u2264\u20D2|\u2265\u20D2|\u2266\u0338|\u2267\u0338|\u2268\uFE00|\u2269\uFE00|\u226A\u0338|\u226A\u20D2|\u226B\u0338|\u226B\u20D2|\u227F\u0338|\u2282\u20D2|\u2283\u20D2|\u228A\uFE00|\u228B\uFE00|\u228F\u0338|\u2290\u0338|\u2293\uFE00|\u2294\uFE00|\u22B4\u20D2|\u22B5\u20D2|\u22D8\u0338|\u22D9\u0338|\u22DA\uFE00|\u22DB\uFE00|\u22F5\u0338|\u22F9\u0338|\u2933\u0338|\u29CF\u0338|\u29D0\u0338|\u2A6D\u0338|\u2A70\u0338|\u2A7D\u0338|\u2A7E\u0338|\u2AA1\u0338|\u2AA2\u0338|\u2AAC\uFE00|\u2AAD\uFE00|\u2AAF\u0338|\u2AB0\u0338|\u2AC5\u0338|\u2AC6\u0338|\u2ACB\uFE00|\u2ACC\uFE00|\u2AFD\u20E5|[\xA0-\u0113\u0116-\u0122\u0124-\u012B\u012E-\u014D\u0150-\u017E\u0192\u01B5\u01F5\u0237\u02C6\u02C7\u02D8-\u02DD\u0311\u0391-\u03A1\u03A3-\u03A9\u03B1-\u03C9\u03D1\u03D2\u03D5\u03D6\u03DC\u03DD\u03F0\u03F1\u03F5\u03F6\u0401-\u040C\u040E-\u044F\u0451-\u045C\u045E\u045F\u2002-\u2005\u2007-\u2010\u2013-\u2016\u2018-\u201A\u201C-\u201E\u2020-\u2022\u2025\u2026\u2030-\u2035\u2039\u203A\u203E\u2041\u2043\u2044\u204F\u2057\u205F-\u2063\u20AC\u20DB\u20DC\u2102\u2105\u210A-\u2113\u2115-\u211E\u2122\u2124\u2127-\u2129\u212C\u212D\u212F-\u2131\u2133-\u2138\u2145-\u2148\u2153-\u215E\u2190-\u219B\u219D-\u21A7\u21A9-\u21AE\u21B0-\u21B3\u21B5-\u21B7\u21BA-\u21DB\u21DD\u21E4\u21E5\u21F5\u21FD-\u2205\u2207-\u2209\u220B\u220C\u220F-\u2214\u2216-\u2218\u221A\u221D-\u2238\u223A-\u2257\u2259\u225A\u225C\u225F-\u2262\u2264-\u228B\u228D-\u229B\u229D-\u22A5\u22A7-\u22B0\u22B2-\u22BB\u22BD-\u22DB\u22DE-\u22E3\u22E6-\u22F7\u22F9-\u22FE\u2305\u2306\u2308-\u2310\u2312\u2313\u2315\u2316\u231C-\u231F\u2322\u2323\u232D\u232E\u2336\u233D\u233F\u237C\u23B0\u23B1\u23B4-\u23B6\u23DC-\u23DF\u23E2\u23E7\u2423\u24C8\u2500\u2502\u250C\u2510\u2514\u2518\u251C\u2524\u252C\u2534\u253C\u2550-\u256C\u2580\u2584\u2588\u2591-\u2593\u25A1\u25AA\u25AB\u25AD\u25AE\u25B1\u25B3-\u25B5\u25B8\u25B9\u25BD-\u25BF\u25C2\u25C3\u25CA\u25CB\u25EC\u25EF\u25F8-\u25FC\u2605\u2606\u260E\u2640\u2642\u2660\u2663\u2665\u2666\u266A\u266D-\u266F\u2713\u2717\u2720\u2736\u2758\u2772\u2773\u27C8\u27C9\u27E6-\u27ED\u27F5-\u27FA\u27FC\u27FF\u2902-\u2905\u290C-\u2913\u2916\u2919-\u2920\u2923-\u292A\u2933\u2935-\u2939\u293C\u293D\u2945\u2948-\u294B\u294E-\u2976\u2978\u2979\u297B-\u297F\u2985\u2986\u298B-\u2996\u299A\u299C\u299D\u29A4-\u29B7\u29B9\u29BB\u29BC\u29BE-\u29C5\u29C9\u29CD-\u29D0\u29DC-\u29DE\u29E3-\u29E5\u29EB\u29F4\u29F6\u2A00-\u2A02\u2A04\u2A06\u2A0C\u2A0D\u2A10-\u2A17\u2A22-\u2A27\u2A29\u2A2A\u2A2D-\u2A31\u2A33-\u2A3C\u2A3F\u2A40\u2A42-\u2A4D\u2A50\u2A53-\u2A58\u2A5A-\u2A5D\u2A5F\u2A66\u2A6A\u2A6D-\u2A75\u2A77-\u2A9A\u2A9D-\u2AA2\u2AA4-\u2AB0\u2AB3-\u2AC8\u2ACB\u2ACC\u2ACF-\u2ADB\u2AE4\u2AE6-\u2AE9\u2AEB-\u2AF3\u2AFD\uFB00-\uFB04]|\uD835[\uDC9C\uDC9E\uDC9F\uDCA2\uDCA5\uDCA6\uDCA9-\uDCAC\uDCAE-\uDCB9\uDCBB\uDCBD-\uDCC3\uDCC5-\uDCCF\uDD04\uDD05\uDD07-\uDD0A\uDD0D-\uDD14\uDD16-\uDD1C\uDD1E-\uDD39\uDD3B-\uDD3E\uDD40-\uDD44\uDD46\uDD4A-\uDD50\uDD52-\uDD6B]/g;
		var encodeMap = { '\xAD': 'shy', '‌': 'zwnj', '‍': 'zwj', '‎': 'lrm', '⁣': 'ic', '⁢': 'it', '⁡': 'af', '‏': 'rlm', '​': 'ZeroWidthSpace', '⁠': 'NoBreak', '̑': 'DownBreve', '⃛': 'tdot', '⃜': 'DotDot', '\t': 'Tab', '\n': 'NewLine', ' ': 'puncsp', ' ': 'MediumSpace', ' ': 'thinsp', ' ': 'hairsp', ' ': 'emsp13', ' ': 'ensp', ' ': 'emsp14', ' ': 'emsp', ' ': 'numsp', '\xA0': 'nbsp', '  ': 'ThickSpace', '‾': 'oline', '_': 'lowbar', '‐': 'dash', '–': 'ndash', '—': 'mdash', '―': 'horbar', ',': 'comma', ';': 'semi', '⁏': 'bsemi', ':': 'colon', '⩴': 'Colone', '!': 'excl', '\xA1': 'iexcl', '?': 'quest', '\xBF': 'iquest', '.': 'period', '‥': 'nldr', '…': 'mldr', '\xB7': 'middot', '\'': 'apos', '‘': 'lsquo', '’': 'rsquo', '‚': 'sbquo', '‹': 'lsaquo', '›': 'rsaquo', '"': 'quot', '“': 'ldquo', '”': 'rdquo', '„': 'bdquo', '\xAB': 'laquo', '\xBB': 'raquo', '(': 'lpar', ')': 'rpar', '[': 'lsqb', ']': 'rsqb', '{': 'lcub', '}': 'rcub', '⌈': 'lceil', '⌉': 'rceil', '⌊': 'lfloor', '⌋': 'rfloor', '⦅': 'lopar', '⦆': 'ropar', '⦋': 'lbrke', '⦌': 'rbrke', '⦍': 'lbrkslu', '⦎': 'rbrksld', '⦏': 'lbrksld', '⦐': 'rbrkslu', '⦑': 'langd', '⦒': 'rangd', '⦓': 'lparlt', '⦔': 'rpargt', '⦕': 'gtlPar', '⦖': 'ltrPar', '⟦': 'lobrk', '⟧': 'robrk', '⟨': 'lang', '⟩': 'rang', '⟪': 'Lang', '⟫': 'Rang', '⟬': 'loang', '⟭': 'roang', '❲': 'lbbrk', '❳': 'rbbrk', '‖': 'Vert', '\xA7': 'sect', '\xB6': 'para', '@': 'commat', '*': 'ast', '/': 'sol', 'undefined': null, '&': 'amp', '#': 'num', '%': 'percnt', '‰': 'permil', '‱': 'pertenk', '†': 'dagger', '‡': 'Dagger', '•': 'bull', '⁃': 'hybull', '′': 'prime', '″': 'Prime', '‴': 'tprime', '⁗': 'qprime', '‵': 'bprime', '⁁': 'caret', '`': 'grave', '\xB4': 'acute', '˜': 'tilde', '^': 'Hat', '\xAF': 'macr', '˘': 'breve', '˙': 'dot', '\xA8': 'die', '˚': 'ring', '˝': 'dblac', '\xB8': 'cedil', '˛': 'ogon', 'ˆ': 'circ', 'ˇ': 'caron', '\xB0': 'deg', '\xA9': 'copy', '\xAE': 'reg', '℗': 'copysr', '℘': 'wp', '℞': 'rx', '℧': 'mho', '℩': 'iiota', '←': 'larr', '↚': 'nlarr', '→': 'rarr', '↛': 'nrarr', '↑': 'uarr', '↓': 'darr', '↔': 'harr', '↮': 'nharr', '↕': 'varr', '↖': 'nwarr', '↗': 'nearr', '↘': 'searr', '↙': 'swarr', '↝': 'rarrw', '↝̸': 'nrarrw', '↞': 'Larr', '↟': 'Uarr', '↠': 'Rarr', '↡': 'Darr', '↢': 'larrtl', '↣': 'rarrtl', '↤': 'mapstoleft', '↥': 'mapstoup', '↦': 'map', '↧': 'mapstodown', '↩': 'larrhk', '↪': 'rarrhk', '↫': 'larrlp', '↬': 'rarrlp', '↭': 'harrw', '↰': 'lsh', '↱': 'rsh', '↲': 'ldsh', '↳': 'rdsh', '↵': 'crarr', '↶': 'cularr', '↷': 'curarr', '↺': 'olarr', '↻': 'orarr', '↼': 'lharu', '↽': 'lhard', '↾': 'uharr', '↿': 'uharl', '⇀': 'rharu', '⇁': 'rhard', '⇂': 'dharr', '⇃': 'dharl', '⇄': 'rlarr', '⇅': 'udarr', '⇆': 'lrarr', '⇇': 'llarr', '⇈': 'uuarr', '⇉': 'rrarr', '⇊': 'ddarr', '⇋': 'lrhar', '⇌': 'rlhar', '⇐': 'lArr', '⇍': 'nlArr', '⇑': 'uArr', '⇒': 'rArr', '⇏': 'nrArr', '⇓': 'dArr', '⇔': 'iff', '⇎': 'nhArr', '⇕': 'vArr', '⇖': 'nwArr', '⇗': 'neArr', '⇘': 'seArr', '⇙': 'swArr', '⇚': 'lAarr', '⇛': 'rAarr', '⇝': 'zigrarr', '⇤': 'larrb', '⇥': 'rarrb', '⇵': 'duarr', '⇽': 'loarr', '⇾': 'roarr', '⇿': 'hoarr', '∀': 'forall', '∁': 'comp', '∂': 'part', '∂̸': 'npart', '∃': 'exist', '∄': 'nexist', '∅': 'empty', '∇': 'Del', '∈': 'in', '∉': 'notin', '∋': 'ni', '∌': 'notni', '϶': 'bepsi', '∏': 'prod', '∐': 'coprod', '∑': 'sum', '+': 'plus', '\xB1': 'pm', '\xF7': 'div', '\xD7': 'times', '<': 'lt', '≮': 'nlt', '<⃒': 'nvlt', '=': 'equals', '≠': 'ne', '=⃥': 'bne', '⩵': 'Equal', '>': 'gt', '≯': 'ngt', '>⃒': 'nvgt', '\xAC': 'not', '|': 'vert', '\xA6': 'brvbar', '−': 'minus', '∓': 'mp', '∔': 'plusdo', '⁄': 'frasl', '∖': 'setmn', '∗': 'lowast', '∘': 'compfn', '√': 'Sqrt', '∝': 'prop', '∞': 'infin', '∟': 'angrt', '∠': 'ang', '∠⃒': 'nang', '∡': 'angmsd', '∢': 'angsph', '∣': 'mid', '∤': 'nmid', '∥': 'par', '∦': 'npar', '∧': 'and', '∨': 'or', '∩': 'cap', '∩︀': 'caps', '∪': 'cup', '∪︀': 'cups', '∫': 'int', '∬': 'Int', '∭': 'tint', '⨌': 'qint', '∮': 'oint', '∯': 'Conint', '∰': 'Cconint', '∱': 'cwint', '∲': 'cwconint', '∳': 'awconint', '∴': 'there4', '∵': 'becaus', '∶': 'ratio', '∷': 'Colon', '∸': 'minusd', '∺': 'mDDot', '∻': 'homtht', '∼': 'sim', '≁': 'nsim', '∼⃒': 'nvsim', '∽': 'bsim', '∽̱': 'race', '∾': 'ac', '∾̳': 'acE', '∿': 'acd', '≀': 'wr', '≂': 'esim', '≂̸': 'nesim', '≃': 'sime', '≄': 'nsime', '≅': 'cong', '≇': 'ncong', '≆': 'simne', '≈': 'ap', '≉': 'nap', '≊': 'ape', '≋': 'apid', '≋̸': 'napid', '≌': 'bcong', '≍': 'CupCap', '≭': 'NotCupCap', '≍⃒': 'nvap', '≎': 'bump', '≎̸': 'nbump', '≏': 'bumpe', '≏̸': 'nbumpe', '≐': 'doteq', '≐̸': 'nedot', '≑': 'eDot', '≒': 'efDot', '≓': 'erDot', '≔': 'colone', '≕': 'ecolon', '≖': 'ecir', '≗': 'cire', '≙': 'wedgeq', '≚': 'veeeq', '≜': 'trie', '≟': 'equest', '≡': 'equiv', '≢': 'nequiv', '≡⃥': 'bnequiv', '≤': 'le', '≰': 'nle', '≤⃒': 'nvle', '≥': 'ge', '≱': 'nge', '≥⃒': 'nvge', '≦': 'lE', '≦̸': 'nlE', '≧': 'gE', '≧̸': 'ngE', '≨︀': 'lvnE', '≨': 'lnE', '≩': 'gnE', '≩︀': 'gvnE', '≪': 'll', '≪̸': 'nLtv', '≪⃒': 'nLt', '≫': 'gg', '≫̸': 'nGtv', '≫⃒': 'nGt', '≬': 'twixt', '≲': 'lsim', '≴': 'nlsim', '≳': 'gsim', '≵': 'ngsim', '≶': 'lg', '≸': 'ntlg', '≷': 'gl', '≹': 'ntgl', '≺': 'pr', '⊀': 'npr', '≻': 'sc', '⊁': 'nsc', '≼': 'prcue', '⋠': 'nprcue', '≽': 'sccue', '⋡': 'nsccue', '≾': 'prsim', '≿': 'scsim', '≿̸': 'NotSucceedsTilde', '⊂': 'sub', '⊄': 'nsub', '⊂⃒': 'vnsub', '⊃': 'sup', '⊅': 'nsup', '⊃⃒': 'vnsup', '⊆': 'sube', '⊈': 'nsube', '⊇': 'supe', '⊉': 'nsupe', '⊊︀': 'vsubne', '⊊': 'subne', '⊋︀': 'vsupne', '⊋': 'supne', '⊍': 'cupdot', '⊎': 'uplus', '⊏': 'sqsub', '⊏̸': 'NotSquareSubset', '⊐': 'sqsup', '⊐̸': 'NotSquareSuperset', '⊑': 'sqsube', '⋢': 'nsqsube', '⊒': 'sqsupe', '⋣': 'nsqsupe', '⊓': 'sqcap', '⊓︀': 'sqcaps', '⊔': 'sqcup', '⊔︀': 'sqcups', '⊕': 'oplus', '⊖': 'ominus', '⊗': 'otimes', '⊘': 'osol', '⊙': 'odot', '⊚': 'ocir', '⊛': 'oast', '⊝': 'odash', '⊞': 'plusb', '⊟': 'minusb', '⊠': 'timesb', '⊡': 'sdotb', '⊢': 'vdash', '⊬': 'nvdash', '⊣': 'dashv', '⊤': 'top', '⊥': 'bot', '⊧': 'models', '⊨': 'vDash', '⊭': 'nvDash', '⊩': 'Vdash', '⊮': 'nVdash', '⊪': 'Vvdash', '⊫': 'VDash', '⊯': 'nVDash', '⊰': 'prurel', '⊲': 'vltri', '⋪': 'nltri', '⊳': 'vrtri', '⋫': 'nrtri', '⊴': 'ltrie', '⋬': 'nltrie', '⊴⃒': 'nvltrie', '⊵': 'rtrie', '⋭': 'nrtrie', '⊵⃒': 'nvrtrie', '⊶': 'origof', '⊷': 'imof', '⊸': 'mumap', '⊹': 'hercon', '⊺': 'intcal', '⊻': 'veebar', '⊽': 'barvee', '⊾': 'angrtvb', '⊿': 'lrtri', '⋀': 'Wedge', '⋁': 'Vee', '⋂': 'xcap', '⋃': 'xcup', '⋄': 'diam', '⋅': 'sdot', '⋆': 'Star', '⋇': 'divonx', '⋈': 'bowtie', '⋉': 'ltimes', '⋊': 'rtimes', '⋋': 'lthree', '⋌': 'rthree', '⋍': 'bsime', '⋎': 'cuvee', '⋏': 'cuwed', '⋐': 'Sub', '⋑': 'Sup', '⋒': 'Cap', '⋓': 'Cup', '⋔': 'fork', '⋕': 'epar', '⋖': 'ltdot', '⋗': 'gtdot', '⋘': 'Ll', '⋘̸': 'nLl', '⋙': 'Gg', '⋙̸': 'nGg', '⋚︀': 'lesg', '⋚': 'leg', '⋛': 'gel', '⋛︀': 'gesl', '⋞': 'cuepr', '⋟': 'cuesc', '⋦': 'lnsim', '⋧': 'gnsim', '⋨': 'prnsim', '⋩': 'scnsim', '⋮': 'vellip', '⋯': 'ctdot', '⋰': 'utdot', '⋱': 'dtdot', '⋲': 'disin', '⋳': 'isinsv', '⋴': 'isins', '⋵': 'isindot', '⋵̸': 'notindot', '⋶': 'notinvc', '⋷': 'notinvb', '⋹': 'isinE', '⋹̸': 'notinE', '⋺': 'nisd', '⋻': 'xnis', '⋼': 'nis', '⋽': 'notnivc', '⋾': 'notnivb', '⌅': 'barwed', '⌆': 'Barwed', '⌌': 'drcrop', '⌍': 'dlcrop', '⌎': 'urcrop', '⌏': 'ulcrop', '⌐': 'bnot', '⌒': 'profline', '⌓': 'profsurf', '⌕': 'telrec', '⌖': 'target', '⌜': 'ulcorn', '⌝': 'urcorn', '⌞': 'dlcorn', '⌟': 'drcorn', '⌢': 'frown', '⌣': 'smile', '⌭': 'cylcty', '⌮': 'profalar', '⌶': 'topbot', '⌽': 'ovbar', '⌿': 'solbar', '⍼': 'angzarr', '⎰': 'lmoust', '⎱': 'rmoust', '⎴': 'tbrk', '⎵': 'bbrk', '⎶': 'bbrktbrk', '⏜': 'OverParenthesis', '⏝': 'UnderParenthesis', '⏞': 'OverBrace', '⏟': 'UnderBrace', '⏢': 'trpezium', '⏧': 'elinters', '␣': 'blank', '─': 'boxh', '│': 'boxv', '┌': 'boxdr', '┐': 'boxdl', '└': 'boxur', '┘': 'boxul', '├': 'boxvr', '┤': 'boxvl', '┬': 'boxhd', '┴': 'boxhu', '┼': 'boxvh', '═': 'boxH', '║': 'boxV', '╒': 'boxdR', '╓': 'boxDr', '╔': 'boxDR', '╕': 'boxdL', '╖': 'boxDl', '╗': 'boxDL', '╘': 'boxuR', '╙': 'boxUr', '╚': 'boxUR', '╛': 'boxuL', '╜': 'boxUl', '╝': 'boxUL', '╞': 'boxvR', '╟': 'boxVr', '╠': 'boxVR', '╡': 'boxvL', '╢': 'boxVl', '╣': 'boxVL', '╤': 'boxHd', '╥': 'boxhD', '╦': 'boxHD', '╧': 'boxHu', '╨': 'boxhU', '╩': 'boxHU', '╪': 'boxvH', '╫': 'boxVh', '╬': 'boxVH', '▀': 'uhblk', '▄': 'lhblk', '█': 'block', '░': 'blk14', '▒': 'blk12', '▓': 'blk34', '□': 'squ', '▪': 'squf', '▫': 'EmptyVerySmallSquare', '▭': 'rect', '▮': 'marker', '▱': 'fltns', '△': 'xutri', '▴': 'utrif', '▵': 'utri', '▸': 'rtrif', '▹': 'rtri', '▽': 'xdtri', '▾': 'dtrif', '▿': 'dtri', '◂': 'ltrif', '◃': 'ltri', '◊': 'loz', '○': 'cir', '◬': 'tridot', '◯': 'xcirc', '◸': 'ultri', '◹': 'urtri', '◺': 'lltri', '◻': 'EmptySmallSquare', '◼': 'FilledSmallSquare', '★': 'starf', '☆': 'star', '☎': 'phone', '♀': 'female', '♂': 'male', '♠': 'spades', '♣': 'clubs', '♥': 'hearts', '♦': 'diams', '♪': 'sung', '✓': 'check', '✗': 'cross', '✠': 'malt', '✶': 'sext', '❘': 'VerticalSeparator', '⟈': 'bsolhsub', '⟉': 'suphsol', '⟵': 'xlarr', '⟶': 'xrarr', '⟷': 'xharr', '⟸': 'xlArr', '⟹': 'xrArr', '⟺': 'xhArr', '⟼': 'xmap', '⟿': 'dzigrarr', '⤂': 'nvlArr', '⤃': 'nvrArr', '⤄': 'nvHarr', '⤅': 'Map', '⤌': 'lbarr', '⤍': 'rbarr', '⤎': 'lBarr', '⤏': 'rBarr', '⤐': 'RBarr', '⤑': 'DDotrahd', '⤒': 'UpArrowBar', '⤓': 'DownArrowBar', '⤖': 'Rarrtl', '⤙': 'latail', '⤚': 'ratail', '⤛': 'lAtail', '⤜': 'rAtail', '⤝': 'larrfs', '⤞': 'rarrfs', '⤟': 'larrbfs', '⤠': 'rarrbfs', '⤣': 'nwarhk', '⤤': 'nearhk', '⤥': 'searhk', '⤦': 'swarhk', '⤧': 'nwnear', '⤨': 'toea', '⤩': 'tosa', '⤪': 'swnwar', '⤳': 'rarrc', '⤳̸': 'nrarrc', '⤵': 'cudarrr', '⤶': 'ldca', '⤷': 'rdca', '⤸': 'cudarrl', '⤹': 'larrpl', '⤼': 'curarrm', '⤽': 'cularrp', '⥅': 'rarrpl', '⥈': 'harrcir', '⥉': 'Uarrocir', '⥊': 'lurdshar', '⥋': 'ldrushar', '⥎': 'LeftRightVector', '⥏': 'RightUpDownVector', '⥐': 'DownLeftRightVector', '⥑': 'LeftUpDownVector', '⥒': 'LeftVectorBar', '⥓': 'RightVectorBar', '⥔': 'RightUpVectorBar', '⥕': 'RightDownVectorBar', '⥖': 'DownLeftVectorBar', '⥗': 'DownRightVectorBar', '⥘': 'LeftUpVectorBar', '⥙': 'LeftDownVectorBar', '⥚': 'LeftTeeVector', '⥛': 'RightTeeVector', '⥜': 'RightUpTeeVector', '⥝': 'RightDownTeeVector', '⥞': 'DownLeftTeeVector', '⥟': 'DownRightTeeVector', '⥠': 'LeftUpTeeVector', '⥡': 'LeftDownTeeVector', '⥢': 'lHar', '⥣': 'uHar', '⥤': 'rHar', '⥥': 'dHar', '⥦': 'luruhar', '⥧': 'ldrdhar', '⥨': 'ruluhar', '⥩': 'rdldhar', '⥪': 'lharul', '⥫': 'llhard', '⥬': 'rharul', '⥭': 'lrhard', '⥮': 'udhar', '⥯': 'duhar', '⥰': 'RoundImplies', '⥱': 'erarr', '⥲': 'simrarr', '⥳': 'larrsim', '⥴': 'rarrsim', '⥵': 'rarrap', '⥶': 'ltlarr', '⥸': 'gtrarr', '⥹': 'subrarr', '⥻': 'suplarr', '⥼': 'lfisht', '⥽': 'rfisht', '⥾': 'ufisht', '⥿': 'dfisht', '⦚': 'vzigzag', '⦜': 'vangrt', '⦝': 'angrtvbd', '⦤': 'ange', '⦥': 'range', '⦦': 'dwangle', '⦧': 'uwangle', '⦨': 'angmsdaa', '⦩': 'angmsdab', '⦪': 'angmsdac', '⦫': 'angmsdad', '⦬': 'angmsdae', '⦭': 'angmsdaf', '⦮': 'angmsdag', '⦯': 'angmsdah', '⦰': 'bemptyv', '⦱': 'demptyv', '⦲': 'cemptyv', '⦳': 'raemptyv', '⦴': 'laemptyv', '⦵': 'ohbar', '⦶': 'omid', '⦷': 'opar', '⦹': 'operp', '⦻': 'olcross', '⦼': 'odsold', '⦾': 'olcir', '⦿': 'ofcir', '⧀': 'olt', '⧁': 'ogt', '⧂': 'cirscir', '⧃': 'cirE', '⧄': 'solb', '⧅': 'bsolb', '⧉': 'boxbox', '⧍': 'trisb', '⧎': 'rtriltri', '⧏': 'LeftTriangleBar', '⧏̸': 'NotLeftTriangleBar', '⧐': 'RightTriangleBar', '⧐̸': 'NotRightTriangleBar', '⧜': 'iinfin', '⧝': 'infintie', '⧞': 'nvinfin', '⧣': 'eparsl', '⧤': 'smeparsl', '⧥': 'eqvparsl', '⧫': 'lozf', '⧴': 'RuleDelayed', '⧶': 'dsol', '⨀': 'xodot', '⨁': 'xoplus', '⨂': 'xotime', '⨄': 'xuplus', '⨆': 'xsqcup', '⨍': 'fpartint', '⨐': 'cirfnint', '⨑': 'awint', '⨒': 'rppolint', '⨓': 'scpolint', '⨔': 'npolint', '⨕': 'pointint', '⨖': 'quatint', '⨗': 'intlarhk', '⨢': 'pluscir', '⨣': 'plusacir', '⨤': 'simplus', '⨥': 'plusdu', '⨦': 'plussim', '⨧': 'plustwo', '⨩': 'mcomma', '⨪': 'minusdu', '⨭': 'loplus', '⨮': 'roplus', '⨯': 'Cross', '⨰': 'timesd', '⨱': 'timesbar', '⨳': 'smashp', '⨴': 'lotimes', '⨵': 'rotimes', '⨶': 'otimesas', '⨷': 'Otimes', '⨸': 'odiv', '⨹': 'triplus', '⨺': 'triminus', '⨻': 'tritime', '⨼': 'iprod', '⨿': 'amalg', '⩀': 'capdot', '⩂': 'ncup', '⩃': 'ncap', '⩄': 'capand', '⩅': 'cupor', '⩆': 'cupcap', '⩇': 'capcup', '⩈': 'cupbrcap', '⩉': 'capbrcup', '⩊': 'cupcup', '⩋': 'capcap', '⩌': 'ccups', '⩍': 'ccaps', '⩐': 'ccupssm', '⩓': 'And', '⩔': 'Or', '⩕': 'andand', '⩖': 'oror', '⩗': 'orslope', '⩘': 'andslope', '⩚': 'andv', '⩛': 'orv', '⩜': 'andd', '⩝': 'ord', '⩟': 'wedbar', '⩦': 'sdote', '⩪': 'simdot', '⩭': 'congdot', '⩭̸': 'ncongdot', '⩮': 'easter', '⩯': 'apacir', '⩰': 'apE', '⩰̸': 'napE', '⩱': 'eplus', '⩲': 'pluse', '⩳': 'Esim', '⩷': 'eDDot', '⩸': 'equivDD', '⩹': 'ltcir', '⩺': 'gtcir', '⩻': 'ltquest', '⩼': 'gtquest', '⩽': 'les', '⩽̸': 'nles', '⩾': 'ges', '⩾̸': 'nges', '⩿': 'lesdot', '⪀': 'gesdot', '⪁': 'lesdoto', '⪂': 'gesdoto', '⪃': 'lesdotor', '⪄': 'gesdotol', '⪅': 'lap', '⪆': 'gap', '⪇': 'lne', '⪈': 'gne', '⪉': 'lnap', '⪊': 'gnap', '⪋': 'lEg', '⪌': 'gEl', '⪍': 'lsime', '⪎': 'gsime', '⪏': 'lsimg', '⪐': 'gsiml', '⪑': 'lgE', '⪒': 'glE', '⪓': 'lesges', '⪔': 'gesles', '⪕': 'els', '⪖': 'egs', '⪗': 'elsdot', '⪘': 'egsdot', '⪙': 'el', '⪚': 'eg', '⪝': 'siml', '⪞': 'simg', '⪟': 'simlE', '⪠': 'simgE', '⪡': 'LessLess', '⪡̸': 'NotNestedLessLess', '⪢': 'GreaterGreater', '⪢̸': 'NotNestedGreaterGreater', '⪤': 'glj', '⪥': 'gla', '⪦': 'ltcc', '⪧': 'gtcc', '⪨': 'lescc', '⪩': 'gescc', '⪪': 'smt', '⪫': 'lat', '⪬': 'smte', '⪬︀': 'smtes', '⪭': 'late', '⪭︀': 'lates', '⪮': 'bumpE', '⪯': 'pre', '⪯̸': 'npre', '⪰': 'sce', '⪰̸': 'nsce', '⪳': 'prE', '⪴': 'scE', '⪵': 'prnE', '⪶': 'scnE', '⪷': 'prap', '⪸': 'scap', '⪹': 'prnap', '⪺': 'scnap', '⪻': 'Pr', '⪼': 'Sc', '⪽': 'subdot', '⪾': 'supdot', '⪿': 'subplus', '⫀': 'supplus', '⫁': 'submult', '⫂': 'supmult', '⫃': 'subedot', '⫄': 'supedot', '⫅': 'subE', '⫅̸': 'nsubE', '⫆': 'supE', '⫆̸': 'nsupE', '⫇': 'subsim', '⫈': 'supsim', '⫋︀': 'vsubnE', '⫋': 'subnE', '⫌︀': 'vsupnE', '⫌': 'supnE', '⫏': 'csub', '⫐': 'csup', '⫑': 'csube', '⫒': 'csupe', '⫓': 'subsup', '⫔': 'supsub', '⫕': 'subsub', '⫖': 'supsup', '⫗': 'suphsub', '⫘': 'supdsub', '⫙': 'forkv', '⫚': 'topfork', '⫛': 'mlcp', '⫤': 'Dashv', '⫦': 'Vdashl', '⫧': 'Barv', '⫨': 'vBar', '⫩': 'vBarv', '⫫': 'Vbar', '⫬': 'Not', '⫭': 'bNot', '⫮': 'rnmid', '⫯': 'cirmid', '⫰': 'midcir', '⫱': 'topcir', '⫲': 'nhpar', '⫳': 'parsim', '⫽': 'parsl', '⫽⃥': 'nparsl', '♭': 'flat', '♮': 'natur', '♯': 'sharp', '\xA4': 'curren', '\xA2': 'cent', '$': 'dollar', '\xA3': 'pound', '\xA5': 'yen', '€': 'euro', '\xB9': 'sup1', '\xBD': 'half', '⅓': 'frac13', '\xBC': 'frac14', '⅕': 'frac15', '⅙': 'frac16', '⅛': 'frac18', '\xB2': 'sup2', '⅔': 'frac23', '⅖': 'frac25', '\xB3': 'sup3', '\xBE': 'frac34', '⅗': 'frac35', '⅜': 'frac38', '⅘': 'frac45', '⅚': 'frac56', '⅝': 'frac58', '⅞': 'frac78', '𝒶': 'ascr', '𝕒': 'aopf', '𝔞': 'afr', '𝔸': 'Aopf', '𝔄': 'Afr', '𝒜': 'Ascr', '\xAA': 'ordf', '\xE1': 'aacute', '\xC1': 'Aacute', '\xE0': 'agrave', '\xC0': 'Agrave', 'ă': 'abreve', 'Ă': 'Abreve', '\xE2': 'acirc', '\xC2': 'Acirc', '\xE5': 'aring', '\xC5': 'angst', '\xE4': 'auml', '\xC4': 'Auml', '\xE3': 'atilde', '\xC3': 'Atilde', 'ą': 'aogon', 'Ą': 'Aogon', 'ā': 'amacr', 'Ā': 'Amacr', '\xE6': 'aelig', '\xC6': 'AElig', '𝒷': 'bscr', '𝕓': 'bopf', '𝔟': 'bfr', '𝔹': 'Bopf', 'ℬ': 'Bscr', '𝔅': 'Bfr', '𝔠': 'cfr', '𝒸': 'cscr', '𝕔': 'copf', 'ℭ': 'Cfr', '𝒞': 'Cscr', 'ℂ': 'Copf', 'ć': 'cacute', 'Ć': 'Cacute', 'ĉ': 'ccirc', 'Ĉ': 'Ccirc', 'č': 'ccaron', 'Č': 'Ccaron', 'ċ': 'cdot', 'Ċ': 'Cdot', '\xE7': 'ccedil', '\xC7': 'Ccedil', '℅': 'incare', '𝔡': 'dfr', 'ⅆ': 'dd', '𝕕': 'dopf', '𝒹': 'dscr', '𝒟': 'Dscr', '𝔇': 'Dfr', 'ⅅ': 'DD', '𝔻': 'Dopf', 'ď': 'dcaron', 'Ď': 'Dcaron', 'đ': 'dstrok', 'Đ': 'Dstrok', '\xF0': 'eth', '\xD0': 'ETH', 'ⅇ': 'ee', 'ℯ': 'escr', '𝔢': 'efr', '𝕖': 'eopf', 'ℰ': 'Escr', '𝔈': 'Efr', '𝔼': 'Eopf', '\xE9': 'eacute', '\xC9': 'Eacute', '\xE8': 'egrave', '\xC8': 'Egrave', '\xEA': 'ecirc', '\xCA': 'Ecirc', 'ě': 'ecaron', 'Ě': 'Ecaron', '\xEB': 'euml', '\xCB': 'Euml', 'ė': 'edot', 'Ė': 'Edot', 'ę': 'eogon', 'Ę': 'Eogon', 'ē': 'emacr', 'Ē': 'Emacr', '𝔣': 'ffr', '𝕗': 'fopf', '𝒻': 'fscr', '𝔉': 'Ffr', '𝔽': 'Fopf', 'ℱ': 'Fscr', 'ﬀ': 'fflig', 'ﬃ': 'ffilig', 'ﬄ': 'ffllig', 'ﬁ': 'filig', 'fj': 'fjlig', 'ﬂ': 'fllig', 'ƒ': 'fnof', 'ℊ': 'gscr', '𝕘': 'gopf', '𝔤': 'gfr', '𝒢': 'Gscr', '𝔾': 'Gopf', '𝔊': 'Gfr', 'ǵ': 'gacute', 'ğ': 'gbreve', 'Ğ': 'Gbreve', 'ĝ': 'gcirc', 'Ĝ': 'Gcirc', 'ġ': 'gdot', 'Ġ': 'Gdot', 'Ģ': 'Gcedil', '𝔥': 'hfr', 'ℎ': 'planckh', '𝒽': 'hscr', '𝕙': 'hopf', 'ℋ': 'Hscr', 'ℌ': 'Hfr', 'ℍ': 'Hopf', 'ĥ': 'hcirc', 'Ĥ': 'Hcirc', 'ℏ': 'hbar', 'ħ': 'hstrok', 'Ħ': 'Hstrok', '𝕚': 'iopf', '𝔦': 'ifr', '𝒾': 'iscr', 'ⅈ': 'ii', '𝕀': 'Iopf', 'ℐ': 'Iscr', 'ℑ': 'Im', '\xED': 'iacute', '\xCD': 'Iacute', '\xEC': 'igrave', '\xCC': 'Igrave', '\xEE': 'icirc', '\xCE': 'Icirc', '\xEF': 'iuml', '\xCF': 'Iuml', 'ĩ': 'itilde', 'Ĩ': 'Itilde', 'İ': 'Idot', 'į': 'iogon', 'Į': 'Iogon', 'ī': 'imacr', 'Ī': 'Imacr', 'ĳ': 'ijlig', 'Ĳ': 'IJlig', 'ı': 'imath', '𝒿': 'jscr', '𝕛': 'jopf', '𝔧': 'jfr', '𝒥': 'Jscr', '𝔍': 'Jfr', '𝕁': 'Jopf', 'ĵ': 'jcirc', 'Ĵ': 'Jcirc', 'ȷ': 'jmath', '𝕜': 'kopf', '𝓀': 'kscr', '𝔨': 'kfr', '𝒦': 'Kscr', '𝕂': 'Kopf', '𝔎': 'Kfr', 'ķ': 'kcedil', 'Ķ': 'Kcedil', '𝔩': 'lfr', '𝓁': 'lscr', 'ℓ': 'ell', '𝕝': 'lopf', 'ℒ': 'Lscr', '𝔏': 'Lfr', '𝕃': 'Lopf', 'ĺ': 'lacute', 'Ĺ': 'Lacute', 'ľ': 'lcaron', 'Ľ': 'Lcaron', 'ļ': 'lcedil', 'Ļ': 'Lcedil', 'ł': 'lstrok', 'Ł': 'Lstrok', 'ŀ': 'lmidot', 'Ŀ': 'Lmidot', '𝔪': 'mfr', '𝕞': 'mopf', '𝓂': 'mscr', '𝔐': 'Mfr', '𝕄': 'Mopf', 'ℳ': 'Mscr', '𝔫': 'nfr', '𝕟': 'nopf', '𝓃': 'nscr', 'ℕ': 'Nopf', '𝒩': 'Nscr', '𝔑': 'Nfr', 'ń': 'nacute', 'Ń': 'Nacute', 'ň': 'ncaron', 'Ň': 'Ncaron', '\xF1': 'ntilde', '\xD1': 'Ntilde', 'ņ': 'ncedil', 'Ņ': 'Ncedil', '№': 'numero', 'ŋ': 'eng', 'Ŋ': 'ENG', '𝕠': 'oopf', '𝔬': 'ofr', 'ℴ': 'oscr', '𝒪': 'Oscr', '𝔒': 'Ofr', '𝕆': 'Oopf', '\xBA': 'ordm', '\xF3': 'oacute', '\xD3': 'Oacute', '\xF2': 'ograve', '\xD2': 'Ograve', '\xF4': 'ocirc', '\xD4': 'Ocirc', '\xF6': 'ouml', '\xD6': 'Ouml', 'ő': 'odblac', 'Ő': 'Odblac', '\xF5': 'otilde', '\xD5': 'Otilde', '\xF8': 'oslash', '\xD8': 'Oslash', 'ō': 'omacr', 'Ō': 'Omacr', 'œ': 'oelig', 'Œ': 'OElig', '𝔭': 'pfr', '𝓅': 'pscr', '𝕡': 'popf', 'ℙ': 'Popf', '𝔓': 'Pfr', '𝒫': 'Pscr', '𝕢': 'qopf', '𝔮': 'qfr', '𝓆': 'qscr', '𝒬': 'Qscr', '𝔔': 'Qfr', 'ℚ': 'Qopf', 'ĸ': 'kgreen', '𝔯': 'rfr', '𝕣': 'ropf', '𝓇': 'rscr', 'ℛ': 'Rscr', 'ℜ': 'Re', 'ℝ': 'Ropf', 'ŕ': 'racute', 'Ŕ': 'Racute', 'ř': 'rcaron', 'Ř': 'Rcaron', 'ŗ': 'rcedil', 'Ŗ': 'Rcedil', '𝕤': 'sopf', '𝓈': 'sscr', '𝔰': 'sfr', '𝕊': 'Sopf', '𝔖': 'Sfr', '𝒮': 'Sscr', 'Ⓢ': 'oS', 'ś': 'sacute', 'Ś': 'Sacute', 'ŝ': 'scirc', 'Ŝ': 'Scirc', 'š': 'scaron', 'Š': 'Scaron', 'ş': 'scedil', 'Ş': 'Scedil', '\xDF': 'szlig', '𝔱': 'tfr', '𝓉': 'tscr', '𝕥': 'topf', '𝒯': 'Tscr', '𝔗': 'Tfr', '𝕋': 'Topf', 'ť': 'tcaron', 'Ť': 'Tcaron', 'ţ': 'tcedil', 'Ţ': 'Tcedil', '™': 'trade', 'ŧ': 'tstrok', 'Ŧ': 'Tstrok', '𝓊': 'uscr', '𝕦': 'uopf', '𝔲': 'ufr', '𝕌': 'Uopf', '𝔘': 'Ufr', '𝒰': 'Uscr', '\xFA': 'uacute', '\xDA': 'Uacute', '\xF9': 'ugrave', '\xD9': 'Ugrave', 'ŭ': 'ubreve', 'Ŭ': 'Ubreve', '\xFB': 'ucirc', '\xDB': 'Ucirc', 'ů': 'uring', 'Ů': 'Uring', '\xFC': 'uuml', '\xDC': 'Uuml', 'ű': 'udblac', 'Ű': 'Udblac', 'ũ': 'utilde', 'Ũ': 'Utilde', 'ų': 'uogon', 'Ų': 'Uogon', 'ū': 'umacr', 'Ū': 'Umacr', '𝔳': 'vfr', '𝕧': 'vopf', '𝓋': 'vscr', '𝔙': 'Vfr', '𝕍': 'Vopf', '𝒱': 'Vscr', '𝕨': 'wopf', '𝓌': 'wscr', '𝔴': 'wfr', '𝒲': 'Wscr', '𝕎': 'Wopf', '𝔚': 'Wfr', 'ŵ': 'wcirc', 'Ŵ': 'Wcirc', '𝔵': 'xfr', '𝓍': 'xscr', '𝕩': 'xopf', '𝕏': 'Xopf', '𝔛': 'Xfr', '𝒳': 'Xscr', '𝔶': 'yfr', '𝓎': 'yscr', '𝕪': 'yopf', '𝒴': 'Yscr', '𝔜': 'Yfr', '𝕐': 'Yopf', '\xFD': 'yacute', '\xDD': 'Yacute', 'ŷ': 'ycirc', 'Ŷ': 'Ycirc', '\xFF': 'yuml', 'Ÿ': 'Yuml', '𝓏': 'zscr', '𝔷': 'zfr', '𝕫': 'zopf', 'ℨ': 'Zfr', 'ℤ': 'Zopf', '𝒵': 'Zscr', 'ź': 'zacute', 'Ź': 'Zacute', 'ž': 'zcaron', 'Ž': 'Zcaron', 'ż': 'zdot', 'Ż': 'Zdot', 'Ƶ': 'imped', '\xFE': 'thorn', '\xDE': 'THORN', 'ŉ': 'napos', 'α': 'alpha', 'Α': 'Alpha', 'β': 'beta', 'Β': 'Beta', 'γ': 'gamma', 'Γ': 'Gamma', 'δ': 'delta', 'Δ': 'Delta', 'ε': 'epsi', 'ϵ': 'epsiv', 'Ε': 'Epsilon', 'ϝ': 'gammad', 'Ϝ': 'Gammad', 'ζ': 'zeta', 'Ζ': 'Zeta', 'η': 'eta', 'Η': 'Eta', 'θ': 'theta', 'ϑ': 'thetav', 'Θ': 'Theta', 'ι': 'iota', 'Ι': 'Iota', 'κ': 'kappa', 'ϰ': 'kappav', 'Κ': 'Kappa', 'λ': 'lambda', 'Λ': 'Lambda', 'μ': 'mu', '\xB5': 'micro', 'Μ': 'Mu', 'ν': 'nu', 'Ν': 'Nu', 'ξ': 'xi', 'Ξ': 'Xi', 'ο': 'omicron', 'Ο': 'Omicron', 'π': 'pi', 'ϖ': 'piv', 'Π': 'Pi', 'ρ': 'rho', 'ϱ': 'rhov', 'Ρ': 'Rho', 'σ': 'sigma', 'Σ': 'Sigma', 'ς': 'sigmaf', 'τ': 'tau', 'Τ': 'Tau', 'υ': 'upsi', 'Υ': 'Upsilon', 'ϒ': 'Upsi', 'φ': 'phi', 'ϕ': 'phiv', 'Φ': 'Phi', 'χ': 'chi', 'Χ': 'Chi', 'ψ': 'psi', 'Ψ': 'Psi', 'ω': 'omega', 'Ω': 'ohm', 'а': 'acy', 'А': 'Acy', 'б': 'bcy', 'Б': 'Bcy', 'в': 'vcy', 'В': 'Vcy', 'г': 'gcy', 'Г': 'Gcy', 'ѓ': 'gjcy', 'Ѓ': 'GJcy', 'д': 'dcy', 'Д': 'Dcy', 'ђ': 'djcy', 'Ђ': 'DJcy', 'е': 'iecy', 'Е': 'IEcy', 'ё': 'iocy', 'Ё': 'IOcy', 'є': 'jukcy', 'Є': 'Jukcy', 'ж': 'zhcy', 'Ж': 'ZHcy', 'з': 'zcy', 'З': 'Zcy', 'ѕ': 'dscy', 'Ѕ': 'DScy', 'и': 'icy', 'И': 'Icy', 'і': 'iukcy', 'І': 'Iukcy', 'ї': 'yicy', 'Ї': 'YIcy', 'й': 'jcy', 'Й': 'Jcy', 'ј': 'jsercy', 'Ј': 'Jsercy', 'к': 'kcy', 'К': 'Kcy', 'ќ': 'kjcy', 'Ќ': 'KJcy', 'л': 'lcy', 'Л': 'Lcy', 'љ': 'ljcy', 'Љ': 'LJcy', 'м': 'mcy', 'М': 'Mcy', 'н': 'ncy', 'Н': 'Ncy', 'њ': 'njcy', 'Њ': 'NJcy', 'о': 'ocy', 'О': 'Ocy', 'п': 'pcy', 'П': 'Pcy', 'р': 'rcy', 'Р': 'Rcy', 'с': 'scy', 'С': 'Scy', 'т': 'tcy', 'Т': 'Tcy', 'ћ': 'tshcy', 'Ћ': 'TSHcy', 'у': 'ucy', 'У': 'Ucy', 'ў': 'ubrcy', 'Ў': 'Ubrcy', 'ф': 'fcy', 'Ф': 'Fcy', 'х': 'khcy', 'Х': 'KHcy', 'ц': 'tscy', 'Ц': 'TScy', 'ч': 'chcy', 'Ч': 'CHcy', 'џ': 'dzcy', 'Џ': 'DZcy', 'ш': 'shcy', 'Ш': 'SHcy', 'щ': 'shchcy', 'Щ': 'SHCHcy', 'ъ': 'hardcy', 'Ъ': 'HARDcy', 'ы': 'ycy', 'Ы': 'Ycy', 'ь': 'softcy', 'Ь': 'SOFTcy', 'э': 'ecy', 'Э': 'Ecy', 'ю': 'yucy', 'Ю': 'YUcy', 'я': 'yacy', 'Я': 'YAcy', 'ℵ': 'aleph', 'ℶ': 'beth', 'ℷ': 'gimel', 'ℸ': 'daleth' };
	
		var regexEscape = /["&'<>`]/g;
		var escapeMap = {
			'"': '&quot;',
			'&': '&amp;',
			'\'': '&#x27;',
			'<': '&lt;',
			// See https://mathiasbynens.be/notes/ambiguous-ampersands: in HTML, the
			// following is not strictly necessary unless it’s part of a tag or an
			// unquoted attribute value. We’re only escaping it to support those
			// situations, and for XML support.
			'>': '&gt;',
			// In Internet Explorer ≤ 8, the backtick character can be used
			// to break out of (un)quoted attribute values or HTML comments.
			// See http://html5sec.org/#102, http://html5sec.org/#108, and
			// http://html5sec.org/#133.
			'`': '&#x60;'
		};
	
		var regexInvalidEntity = /&#(?:[xX][^a-fA-F0-9]|[^0-9xX])/;
		var regexInvalidRawCodePoint = /[\0-\x08\x0B\x0E-\x1F\x7F-\x9F\uFDD0-\uFDEF\uFFFE\uFFFF]|[\uD83F\uD87F\uD8BF\uD8FF\uD93F\uD97F\uD9BF\uD9FF\uDA3F\uDA7F\uDABF\uDAFF\uDB3F\uDB7F\uDBBF\uDBFF][\uDFFE\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF]/;
		var regexDecode = /&#([0-9]+)(;?)|&#[xX]([a-fA-F0-9]+)(;?)|&([0-9a-zA-Z]+);|&(Aacute|Agrave|Atilde|Ccedil|Eacute|Egrave|Iacute|Igrave|Ntilde|Oacute|Ograve|Oslash|Otilde|Uacute|Ugrave|Yacute|aacute|agrave|atilde|brvbar|ccedil|curren|divide|eacute|egrave|frac12|frac14|frac34|iacute|igrave|iquest|middot|ntilde|oacute|ograve|oslash|otilde|plusmn|uacute|ugrave|yacute|AElig|Acirc|Aring|Ecirc|Icirc|Ocirc|THORN|Ucirc|acirc|acute|aelig|aring|cedil|ecirc|icirc|iexcl|laquo|micro|ocirc|pound|raquo|szlig|thorn|times|ucirc|Auml|COPY|Euml|Iuml|Ouml|QUOT|Uuml|auml|cent|copy|euml|iuml|macr|nbsp|ordf|ordm|ouml|para|quot|sect|sup1|sup2|sup3|uuml|yuml|AMP|ETH|REG|amp|deg|eth|not|reg|shy|uml|yen|GT|LT|gt|lt)([=a-zA-Z0-9])?/g;
		var decodeMap = { 'aacute': '\xE1', 'Aacute': '\xC1', 'abreve': 'ă', 'Abreve': 'Ă', 'ac': '∾', 'acd': '∿', 'acE': '∾̳', 'acirc': '\xE2', 'Acirc': '\xC2', 'acute': '\xB4', 'acy': 'а', 'Acy': 'А', 'aelig': '\xE6', 'AElig': '\xC6', 'af': '⁡', 'afr': '𝔞', 'Afr': '𝔄', 'agrave': '\xE0', 'Agrave': '\xC0', 'alefsym': 'ℵ', 'aleph': 'ℵ', 'alpha': 'α', 'Alpha': 'Α', 'amacr': 'ā', 'Amacr': 'Ā', 'amalg': '⨿', 'amp': '&', 'AMP': '&', 'and': '∧', 'And': '⩓', 'andand': '⩕', 'andd': '⩜', 'andslope': '⩘', 'andv': '⩚', 'ang': '∠', 'ange': '⦤', 'angle': '∠', 'angmsd': '∡', 'angmsdaa': '⦨', 'angmsdab': '⦩', 'angmsdac': '⦪', 'angmsdad': '⦫', 'angmsdae': '⦬', 'angmsdaf': '⦭', 'angmsdag': '⦮', 'angmsdah': '⦯', 'angrt': '∟', 'angrtvb': '⊾', 'angrtvbd': '⦝', 'angsph': '∢', 'angst': '\xC5', 'angzarr': '⍼', 'aogon': 'ą', 'Aogon': 'Ą', 'aopf': '𝕒', 'Aopf': '𝔸', 'ap': '≈', 'apacir': '⩯', 'ape': '≊', 'apE': '⩰', 'apid': '≋', 'apos': '\'', 'ApplyFunction': '⁡', 'approx': '≈', 'approxeq': '≊', 'aring': '\xE5', 'Aring': '\xC5', 'ascr': '𝒶', 'Ascr': '𝒜', 'Assign': '≔', 'ast': '*', 'asymp': '≈', 'asympeq': '≍', 'atilde': '\xE3', 'Atilde': '\xC3', 'auml': '\xE4', 'Auml': '\xC4', 'awconint': '∳', 'awint': '⨑', 'backcong': '≌', 'backepsilon': '϶', 'backprime': '‵', 'backsim': '∽', 'backsimeq': '⋍', 'Backslash': '∖', 'Barv': '⫧', 'barvee': '⊽', 'barwed': '⌅', 'Barwed': '⌆', 'barwedge': '⌅', 'bbrk': '⎵', 'bbrktbrk': '⎶', 'bcong': '≌', 'bcy': 'б', 'Bcy': 'Б', 'bdquo': '„', 'becaus': '∵', 'because': '∵', 'Because': '∵', 'bemptyv': '⦰', 'bepsi': '϶', 'bernou': 'ℬ', 'Bernoullis': 'ℬ', 'beta': 'β', 'Beta': 'Β', 'beth': 'ℶ', 'between': '≬', 'bfr': '𝔟', 'Bfr': '𝔅', 'bigcap': '⋂', 'bigcirc': '◯', 'bigcup': '⋃', 'bigodot': '⨀', 'bigoplus': '⨁', 'bigotimes': '⨂', 'bigsqcup': '⨆', 'bigstar': '★', 'bigtriangledown': '▽', 'bigtriangleup': '△', 'biguplus': '⨄', 'bigvee': '⋁', 'bigwedge': '⋀', 'bkarow': '⤍', 'blacklozenge': '⧫', 'blacksquare': '▪', 'blacktriangle': '▴', 'blacktriangledown': '▾', 'blacktriangleleft': '◂', 'blacktriangleright': '▸', 'blank': '␣', 'blk12': '▒', 'blk14': '░', 'blk34': '▓', 'block': '█', 'bne': '=⃥', 'bnequiv': '≡⃥', 'bnot': '⌐', 'bNot': '⫭', 'bopf': '𝕓', 'Bopf': '𝔹', 'bot': '⊥', 'bottom': '⊥', 'bowtie': '⋈', 'boxbox': '⧉', 'boxdl': '┐', 'boxdL': '╕', 'boxDl': '╖', 'boxDL': '╗', 'boxdr': '┌', 'boxdR': '╒', 'boxDr': '╓', 'boxDR': '╔', 'boxh': '─', 'boxH': '═', 'boxhd': '┬', 'boxhD': '╥', 'boxHd': '╤', 'boxHD': '╦', 'boxhu': '┴', 'boxhU': '╨', 'boxHu': '╧', 'boxHU': '╩', 'boxminus': '⊟', 'boxplus': '⊞', 'boxtimes': '⊠', 'boxul': '┘', 'boxuL': '╛', 'boxUl': '╜', 'boxUL': '╝', 'boxur': '└', 'boxuR': '╘', 'boxUr': '╙', 'boxUR': '╚', 'boxv': '│', 'boxV': '║', 'boxvh': '┼', 'boxvH': '╪', 'boxVh': '╫', 'boxVH': '╬', 'boxvl': '┤', 'boxvL': '╡', 'boxVl': '╢', 'boxVL': '╣', 'boxvr': '├', 'boxvR': '╞', 'boxVr': '╟', 'boxVR': '╠', 'bprime': '‵', 'breve': '˘', 'Breve': '˘', 'brvbar': '\xA6', 'bscr': '𝒷', 'Bscr': 'ℬ', 'bsemi': '⁏', 'bsim': '∽', 'bsime': '⋍', 'bsol': '\\', 'bsolb': '⧅', 'bsolhsub': '⟈', 'bull': '•', 'bullet': '•', 'bump': '≎', 'bumpe': '≏', 'bumpE': '⪮', 'bumpeq': '≏', 'Bumpeq': '≎', 'cacute': 'ć', 'Cacute': 'Ć', 'cap': '∩', 'Cap': '⋒', 'capand': '⩄', 'capbrcup': '⩉', 'capcap': '⩋', 'capcup': '⩇', 'capdot': '⩀', 'CapitalDifferentialD': 'ⅅ', 'caps': '∩︀', 'caret': '⁁', 'caron': 'ˇ', 'Cayleys': 'ℭ', 'ccaps': '⩍', 'ccaron': 'č', 'Ccaron': 'Č', 'ccedil': '\xE7', 'Ccedil': '\xC7', 'ccirc': 'ĉ', 'Ccirc': 'Ĉ', 'Cconint': '∰', 'ccups': '⩌', 'ccupssm': '⩐', 'cdot': 'ċ', 'Cdot': 'Ċ', 'cedil': '\xB8', 'Cedilla': '\xB8', 'cemptyv': '⦲', 'cent': '\xA2', 'centerdot': '\xB7', 'CenterDot': '\xB7', 'cfr': '𝔠', 'Cfr': 'ℭ', 'chcy': 'ч', 'CHcy': 'Ч', 'check': '✓', 'checkmark': '✓', 'chi': 'χ', 'Chi': 'Χ', 'cir': '○', 'circ': 'ˆ', 'circeq': '≗', 'circlearrowleft': '↺', 'circlearrowright': '↻', 'circledast': '⊛', 'circledcirc': '⊚', 'circleddash': '⊝', 'CircleDot': '⊙', 'circledR': '\xAE', 'circledS': 'Ⓢ', 'CircleMinus': '⊖', 'CirclePlus': '⊕', 'CircleTimes': '⊗', 'cire': '≗', 'cirE': '⧃', 'cirfnint': '⨐', 'cirmid': '⫯', 'cirscir': '⧂', 'ClockwiseContourIntegral': '∲', 'CloseCurlyDoubleQuote': '”', 'CloseCurlyQuote': '’', 'clubs': '♣', 'clubsuit': '♣', 'colon': ':', 'Colon': '∷', 'colone': '≔', 'Colone': '⩴', 'coloneq': '≔', 'comma': ',', 'commat': '@', 'comp': '∁', 'compfn': '∘', 'complement': '∁', 'complexes': 'ℂ', 'cong': '≅', 'congdot': '⩭', 'Congruent': '≡', 'conint': '∮', 'Conint': '∯', 'ContourIntegral': '∮', 'copf': '𝕔', 'Copf': 'ℂ', 'coprod': '∐', 'Coproduct': '∐', 'copy': '\xA9', 'COPY': '\xA9', 'copysr': '℗', 'CounterClockwiseContourIntegral': '∳', 'crarr': '↵', 'cross': '✗', 'Cross': '⨯', 'cscr': '𝒸', 'Cscr': '𝒞', 'csub': '⫏', 'csube': '⫑', 'csup': '⫐', 'csupe': '⫒', 'ctdot': '⋯', 'cudarrl': '⤸', 'cudarrr': '⤵', 'cuepr': '⋞', 'cuesc': '⋟', 'cularr': '↶', 'cularrp': '⤽', 'cup': '∪', 'Cup': '⋓', 'cupbrcap': '⩈', 'cupcap': '⩆', 'CupCap': '≍', 'cupcup': '⩊', 'cupdot': '⊍', 'cupor': '⩅', 'cups': '∪︀', 'curarr': '↷', 'curarrm': '⤼', 'curlyeqprec': '⋞', 'curlyeqsucc': '⋟', 'curlyvee': '⋎', 'curlywedge': '⋏', 'curren': '\xA4', 'curvearrowleft': '↶', 'curvearrowright': '↷', 'cuvee': '⋎', 'cuwed': '⋏', 'cwconint': '∲', 'cwint': '∱', 'cylcty': '⌭', 'dagger': '†', 'Dagger': '‡', 'daleth': 'ℸ', 'darr': '↓', 'dArr': '⇓', 'Darr': '↡', 'dash': '‐', 'dashv': '⊣', 'Dashv': '⫤', 'dbkarow': '⤏', 'dblac': '˝', 'dcaron': 'ď', 'Dcaron': 'Ď', 'dcy': 'д', 'Dcy': 'Д', 'dd': 'ⅆ', 'DD': 'ⅅ', 'ddagger': '‡', 'ddarr': '⇊', 'DDotrahd': '⤑', 'ddotseq': '⩷', 'deg': '\xB0', 'Del': '∇', 'delta': 'δ', 'Delta': 'Δ', 'demptyv': '⦱', 'dfisht': '⥿', 'dfr': '𝔡', 'Dfr': '𝔇', 'dHar': '⥥', 'dharl': '⇃', 'dharr': '⇂', 'DiacriticalAcute': '\xB4', 'DiacriticalDot': '˙', 'DiacriticalDoubleAcute': '˝', 'DiacriticalGrave': '`', 'DiacriticalTilde': '˜', 'diam': '⋄', 'diamond': '⋄', 'Diamond': '⋄', 'diamondsuit': '♦', 'diams': '♦', 'die': '\xA8', 'DifferentialD': 'ⅆ', 'digamma': 'ϝ', 'disin': '⋲', 'div': '\xF7', 'divide': '\xF7', 'divideontimes': '⋇', 'divonx': '⋇', 'djcy': 'ђ', 'DJcy': 'Ђ', 'dlcorn': '⌞', 'dlcrop': '⌍', 'dollar': '$', 'dopf': '𝕕', 'Dopf': '𝔻', 'dot': '˙', 'Dot': '\xA8', 'DotDot': '⃜', 'doteq': '≐', 'doteqdot': '≑', 'DotEqual': '≐', 'dotminus': '∸', 'dotplus': '∔', 'dotsquare': '⊡', 'doublebarwedge': '⌆', 'DoubleContourIntegral': '∯', 'DoubleDot': '\xA8', 'DoubleDownArrow': '⇓', 'DoubleLeftArrow': '⇐', 'DoubleLeftRightArrow': '⇔', 'DoubleLeftTee': '⫤', 'DoubleLongLeftArrow': '⟸', 'DoubleLongLeftRightArrow': '⟺', 'DoubleLongRightArrow': '⟹', 'DoubleRightArrow': '⇒', 'DoubleRightTee': '⊨', 'DoubleUpArrow': '⇑', 'DoubleUpDownArrow': '⇕', 'DoubleVerticalBar': '∥', 'downarrow': '↓', 'Downarrow': '⇓', 'DownArrow': '↓', 'DownArrowBar': '⤓', 'DownArrowUpArrow': '⇵', 'DownBreve': '̑', 'downdownarrows': '⇊', 'downharpoonleft': '⇃', 'downharpoonright': '⇂', 'DownLeftRightVector': '⥐', 'DownLeftTeeVector': '⥞', 'DownLeftVector': '↽', 'DownLeftVectorBar': '⥖', 'DownRightTeeVector': '⥟', 'DownRightVector': '⇁', 'DownRightVectorBar': '⥗', 'DownTee': '⊤', 'DownTeeArrow': '↧', 'drbkarow': '⤐', 'drcorn': '⌟', 'drcrop': '⌌', 'dscr': '𝒹', 'Dscr': '𝒟', 'dscy': 'ѕ', 'DScy': 'Ѕ', 'dsol': '⧶', 'dstrok': 'đ', 'Dstrok': 'Đ', 'dtdot': '⋱', 'dtri': '▿', 'dtrif': '▾', 'duarr': '⇵', 'duhar': '⥯', 'dwangle': '⦦', 'dzcy': 'џ', 'DZcy': 'Џ', 'dzigrarr': '⟿', 'eacute': '\xE9', 'Eacute': '\xC9', 'easter': '⩮', 'ecaron': 'ě', 'Ecaron': 'Ě', 'ecir': '≖', 'ecirc': '\xEA', 'Ecirc': '\xCA', 'ecolon': '≕', 'ecy': 'э', 'Ecy': 'Э', 'eDDot': '⩷', 'edot': 'ė', 'eDot': '≑', 'Edot': 'Ė', 'ee': 'ⅇ', 'efDot': '≒', 'efr': '𝔢', 'Efr': '𝔈', 'eg': '⪚', 'egrave': '\xE8', 'Egrave': '\xC8', 'egs': '⪖', 'egsdot': '⪘', 'el': '⪙', 'Element': '∈', 'elinters': '⏧', 'ell': 'ℓ', 'els': '⪕', 'elsdot': '⪗', 'emacr': 'ē', 'Emacr': 'Ē', 'empty': '∅', 'emptyset': '∅', 'EmptySmallSquare': '◻', 'emptyv': '∅', 'EmptyVerySmallSquare': '▫', 'emsp': ' ', 'emsp13': ' ', 'emsp14': ' ', 'eng': 'ŋ', 'ENG': 'Ŋ', 'ensp': ' ', 'eogon': 'ę', 'Eogon': 'Ę', 'eopf': '𝕖', 'Eopf': '𝔼', 'epar': '⋕', 'eparsl': '⧣', 'eplus': '⩱', 'epsi': 'ε', 'epsilon': 'ε', 'Epsilon': 'Ε', 'epsiv': 'ϵ', 'eqcirc': '≖', 'eqcolon': '≕', 'eqsim': '≂', 'eqslantgtr': '⪖', 'eqslantless': '⪕', 'Equal': '⩵', 'equals': '=', 'EqualTilde': '≂', 'equest': '≟', 'Equilibrium': '⇌', 'equiv': '≡', 'equivDD': '⩸', 'eqvparsl': '⧥', 'erarr': '⥱', 'erDot': '≓', 'escr': 'ℯ', 'Escr': 'ℰ', 'esdot': '≐', 'esim': '≂', 'Esim': '⩳', 'eta': 'η', 'Eta': 'Η', 'eth': '\xF0', 'ETH': '\xD0', 'euml': '\xEB', 'Euml': '\xCB', 'euro': '€', 'excl': '!', 'exist': '∃', 'Exists': '∃', 'expectation': 'ℰ', 'exponentiale': 'ⅇ', 'ExponentialE': 'ⅇ', 'fallingdotseq': '≒', 'fcy': 'ф', 'Fcy': 'Ф', 'female': '♀', 'ffilig': 'ﬃ', 'fflig': 'ﬀ', 'ffllig': 'ﬄ', 'ffr': '𝔣', 'Ffr': '𝔉', 'filig': 'ﬁ', 'FilledSmallSquare': '◼', 'FilledVerySmallSquare': '▪', 'fjlig': 'fj', 'flat': '♭', 'fllig': 'ﬂ', 'fltns': '▱', 'fnof': 'ƒ', 'fopf': '𝕗', 'Fopf': '𝔽', 'forall': '∀', 'ForAll': '∀', 'fork': '⋔', 'forkv': '⫙', 'Fouriertrf': 'ℱ', 'fpartint': '⨍', 'frac12': '\xBD', 'frac13': '⅓', 'frac14': '\xBC', 'frac15': '⅕', 'frac16': '⅙', 'frac18': '⅛', 'frac23': '⅔', 'frac25': '⅖', 'frac34': '\xBE', 'frac35': '⅗', 'frac38': '⅜', 'frac45': '⅘', 'frac56': '⅚', 'frac58': '⅝', 'frac78': '⅞', 'frasl': '⁄', 'frown': '⌢', 'fscr': '𝒻', 'Fscr': 'ℱ', 'gacute': 'ǵ', 'gamma': 'γ', 'Gamma': 'Γ', 'gammad': 'ϝ', 'Gammad': 'Ϝ', 'gap': '⪆', 'gbreve': 'ğ', 'Gbreve': 'Ğ', 'Gcedil': 'Ģ', 'gcirc': 'ĝ', 'Gcirc': 'Ĝ', 'gcy': 'г', 'Gcy': 'Г', 'gdot': 'ġ', 'Gdot': 'Ġ', 'ge': '≥', 'gE': '≧', 'gel': '⋛', 'gEl': '⪌', 'geq': '≥', 'geqq': '≧', 'geqslant': '⩾', 'ges': '⩾', 'gescc': '⪩', 'gesdot': '⪀', 'gesdoto': '⪂', 'gesdotol': '⪄', 'gesl': '⋛︀', 'gesles': '⪔', 'gfr': '𝔤', 'Gfr': '𝔊', 'gg': '≫', 'Gg': '⋙', 'ggg': '⋙', 'gimel': 'ℷ', 'gjcy': 'ѓ', 'GJcy': 'Ѓ', 'gl': '≷', 'gla': '⪥', 'glE': '⪒', 'glj': '⪤', 'gnap': '⪊', 'gnapprox': '⪊', 'gne': '⪈', 'gnE': '≩', 'gneq': '⪈', 'gneqq': '≩', 'gnsim': '⋧', 'gopf': '𝕘', 'Gopf': '𝔾', 'grave': '`', 'GreaterEqual': '≥', 'GreaterEqualLess': '⋛', 'GreaterFullEqual': '≧', 'GreaterGreater': '⪢', 'GreaterLess': '≷', 'GreaterSlantEqual': '⩾', 'GreaterTilde': '≳', 'gscr': 'ℊ', 'Gscr': '𝒢', 'gsim': '≳', 'gsime': '⪎', 'gsiml': '⪐', 'gt': '>', 'Gt': '≫', 'GT': '>', 'gtcc': '⪧', 'gtcir': '⩺', 'gtdot': '⋗', 'gtlPar': '⦕', 'gtquest': '⩼', 'gtrapprox': '⪆', 'gtrarr': '⥸', 'gtrdot': '⋗', 'gtreqless': '⋛', 'gtreqqless': '⪌', 'gtrless': '≷', 'gtrsim': '≳', 'gvertneqq': '≩︀', 'gvnE': '≩︀', 'Hacek': 'ˇ', 'hairsp': ' ', 'half': '\xBD', 'hamilt': 'ℋ', 'hardcy': 'ъ', 'HARDcy': 'Ъ', 'harr': '↔', 'hArr': '⇔', 'harrcir': '⥈', 'harrw': '↭', 'Hat': '^', 'hbar': 'ℏ', 'hcirc': 'ĥ', 'Hcirc': 'Ĥ', 'hearts': '♥', 'heartsuit': '♥', 'hellip': '…', 'hercon': '⊹', 'hfr': '𝔥', 'Hfr': 'ℌ', 'HilbertSpace': 'ℋ', 'hksearow': '⤥', 'hkswarow': '⤦', 'hoarr': '⇿', 'homtht': '∻', 'hookleftarrow': '↩', 'hookrightarrow': '↪', 'hopf': '𝕙', 'Hopf': 'ℍ', 'horbar': '―', 'HorizontalLine': '─', 'hscr': '𝒽', 'Hscr': 'ℋ', 'hslash': 'ℏ', 'hstrok': 'ħ', 'Hstrok': 'Ħ', 'HumpDownHump': '≎', 'HumpEqual': '≏', 'hybull': '⁃', 'hyphen': '‐', 'iacute': '\xED', 'Iacute': '\xCD', 'ic': '⁣', 'icirc': '\xEE', 'Icirc': '\xCE', 'icy': 'и', 'Icy': 'И', 'Idot': 'İ', 'iecy': 'е', 'IEcy': 'Е', 'iexcl': '\xA1', 'iff': '⇔', 'ifr': '𝔦', 'Ifr': 'ℑ', 'igrave': '\xEC', 'Igrave': '\xCC', 'ii': 'ⅈ', 'iiiint': '⨌', 'iiint': '∭', 'iinfin': '⧜', 'iiota': '℩', 'ijlig': 'ĳ', 'IJlig': 'Ĳ', 'Im': 'ℑ', 'imacr': 'ī', 'Imacr': 'Ī', 'image': 'ℑ', 'ImaginaryI': 'ⅈ', 'imagline': 'ℐ', 'imagpart': 'ℑ', 'imath': 'ı', 'imof': '⊷', 'imped': 'Ƶ', 'Implies': '⇒', 'in': '∈', 'incare': '℅', 'infin': '∞', 'infintie': '⧝', 'inodot': 'ı', 'int': '∫', 'Int': '∬', 'intcal': '⊺', 'integers': 'ℤ', 'Integral': '∫', 'intercal': '⊺', 'Intersection': '⋂', 'intlarhk': '⨗', 'intprod': '⨼', 'InvisibleComma': '⁣', 'InvisibleTimes': '⁢', 'iocy': 'ё', 'IOcy': 'Ё', 'iogon': 'į', 'Iogon': 'Į', 'iopf': '𝕚', 'Iopf': '𝕀', 'iota': 'ι', 'Iota': 'Ι', 'iprod': '⨼', 'iquest': '\xBF', 'iscr': '𝒾', 'Iscr': 'ℐ', 'isin': '∈', 'isindot': '⋵', 'isinE': '⋹', 'isins': '⋴', 'isinsv': '⋳', 'isinv': '∈', 'it': '⁢', 'itilde': 'ĩ', 'Itilde': 'Ĩ', 'iukcy': 'і', 'Iukcy': 'І', 'iuml': '\xEF', 'Iuml': '\xCF', 'jcirc': 'ĵ', 'Jcirc': 'Ĵ', 'jcy': 'й', 'Jcy': 'Й', 'jfr': '𝔧', 'Jfr': '𝔍', 'jmath': 'ȷ', 'jopf': '𝕛', 'Jopf': '𝕁', 'jscr': '𝒿', 'Jscr': '𝒥', 'jsercy': 'ј', 'Jsercy': 'Ј', 'jukcy': 'є', 'Jukcy': 'Є', 'kappa': 'κ', 'Kappa': 'Κ', 'kappav': 'ϰ', 'kcedil': 'ķ', 'Kcedil': 'Ķ', 'kcy': 'к', 'Kcy': 'К', 'kfr': '𝔨', 'Kfr': '𝔎', 'kgreen': 'ĸ', 'khcy': 'х', 'KHcy': 'Х', 'kjcy': 'ќ', 'KJcy': 'Ќ', 'kopf': '𝕜', 'Kopf': '𝕂', 'kscr': '𝓀', 'Kscr': '𝒦', 'lAarr': '⇚', 'lacute': 'ĺ', 'Lacute': 'Ĺ', 'laemptyv': '⦴', 'lagran': 'ℒ', 'lambda': 'λ', 'Lambda': 'Λ', 'lang': '⟨', 'Lang': '⟪', 'langd': '⦑', 'langle': '⟨', 'lap': '⪅', 'Laplacetrf': 'ℒ', 'laquo': '\xAB', 'larr': '←', 'lArr': '⇐', 'Larr': '↞', 'larrb': '⇤', 'larrbfs': '⤟', 'larrfs': '⤝', 'larrhk': '↩', 'larrlp': '↫', 'larrpl': '⤹', 'larrsim': '⥳', 'larrtl': '↢', 'lat': '⪫', 'latail': '⤙', 'lAtail': '⤛', 'late': '⪭', 'lates': '⪭︀', 'lbarr': '⤌', 'lBarr': '⤎', 'lbbrk': '❲', 'lbrace': '{', 'lbrack': '[', 'lbrke': '⦋', 'lbrksld': '⦏', 'lbrkslu': '⦍', 'lcaron': 'ľ', 'Lcaron': 'Ľ', 'lcedil': 'ļ', 'Lcedil': 'Ļ', 'lceil': '⌈', 'lcub': '{', 'lcy': 'л', 'Lcy': 'Л', 'ldca': '⤶', 'ldquo': '“', 'ldquor': '„', 'ldrdhar': '⥧', 'ldrushar': '⥋', 'ldsh': '↲', 'le': '≤', 'lE': '≦', 'LeftAngleBracket': '⟨', 'leftarrow': '←', 'Leftarrow': '⇐', 'LeftArrow': '←', 'LeftArrowBar': '⇤', 'LeftArrowRightArrow': '⇆', 'leftarrowtail': '↢', 'LeftCeiling': '⌈', 'LeftDoubleBracket': '⟦', 'LeftDownTeeVector': '⥡', 'LeftDownVector': '⇃', 'LeftDownVectorBar': '⥙', 'LeftFloor': '⌊', 'leftharpoondown': '↽', 'leftharpoonup': '↼', 'leftleftarrows': '⇇', 'leftrightarrow': '↔', 'Leftrightarrow': '⇔', 'LeftRightArrow': '↔', 'leftrightarrows': '⇆', 'leftrightharpoons': '⇋', 'leftrightsquigarrow': '↭', 'LeftRightVector': '⥎', 'LeftTee': '⊣', 'LeftTeeArrow': '↤', 'LeftTeeVector': '⥚', 'leftthreetimes': '⋋', 'LeftTriangle': '⊲', 'LeftTriangleBar': '⧏', 'LeftTriangleEqual': '⊴', 'LeftUpDownVector': '⥑', 'LeftUpTeeVector': '⥠', 'LeftUpVector': '↿', 'LeftUpVectorBar': '⥘', 'LeftVector': '↼', 'LeftVectorBar': '⥒', 'leg': '⋚', 'lEg': '⪋', 'leq': '≤', 'leqq': '≦', 'leqslant': '⩽', 'les': '⩽', 'lescc': '⪨', 'lesdot': '⩿', 'lesdoto': '⪁', 'lesdotor': '⪃', 'lesg': '⋚︀', 'lesges': '⪓', 'lessapprox': '⪅', 'lessdot': '⋖', 'lesseqgtr': '⋚', 'lesseqqgtr': '⪋', 'LessEqualGreater': '⋚', 'LessFullEqual': '≦', 'LessGreater': '≶', 'lessgtr': '≶', 'LessLess': '⪡', 'lesssim': '≲', 'LessSlantEqual': '⩽', 'LessTilde': '≲', 'lfisht': '⥼', 'lfloor': '⌊', 'lfr': '𝔩', 'Lfr': '𝔏', 'lg': '≶', 'lgE': '⪑', 'lHar': '⥢', 'lhard': '↽', 'lharu': '↼', 'lharul': '⥪', 'lhblk': '▄', 'ljcy': 'љ', 'LJcy': 'Љ', 'll': '≪', 'Ll': '⋘', 'llarr': '⇇', 'llcorner': '⌞', 'Lleftarrow': '⇚', 'llhard': '⥫', 'lltri': '◺', 'lmidot': 'ŀ', 'Lmidot': 'Ŀ', 'lmoust': '⎰', 'lmoustache': '⎰', 'lnap': '⪉', 'lnapprox': '⪉', 'lne': '⪇', 'lnE': '≨', 'lneq': '⪇', 'lneqq': '≨', 'lnsim': '⋦', 'loang': '⟬', 'loarr': '⇽', 'lobrk': '⟦', 'longleftarrow': '⟵', 'Longleftarrow': '⟸', 'LongLeftArrow': '⟵', 'longleftrightarrow': '⟷', 'Longleftrightarrow': '⟺', 'LongLeftRightArrow': '⟷', 'longmapsto': '⟼', 'longrightarrow': '⟶', 'Longrightarrow': '⟹', 'LongRightArrow': '⟶', 'looparrowleft': '↫', 'looparrowright': '↬', 'lopar': '⦅', 'lopf': '𝕝', 'Lopf': '𝕃', 'loplus': '⨭', 'lotimes': '⨴', 'lowast': '∗', 'lowbar': '_', 'LowerLeftArrow': '↙', 'LowerRightArrow': '↘', 'loz': '◊', 'lozenge': '◊', 'lozf': '⧫', 'lpar': '(', 'lparlt': '⦓', 'lrarr': '⇆', 'lrcorner': '⌟', 'lrhar': '⇋', 'lrhard': '⥭', 'lrm': '‎', 'lrtri': '⊿', 'lsaquo': '‹', 'lscr': '𝓁', 'Lscr': 'ℒ', 'lsh': '↰', 'Lsh': '↰', 'lsim': '≲', 'lsime': '⪍', 'lsimg': '⪏', 'lsqb': '[', 'lsquo': '‘', 'lsquor': '‚', 'lstrok': 'ł', 'Lstrok': 'Ł', 'lt': '<', 'Lt': '≪', 'LT': '<', 'ltcc': '⪦', 'ltcir': '⩹', 'ltdot': '⋖', 'lthree': '⋋', 'ltimes': '⋉', 'ltlarr': '⥶', 'ltquest': '⩻', 'ltri': '◃', 'ltrie': '⊴', 'ltrif': '◂', 'ltrPar': '⦖', 'lurdshar': '⥊', 'luruhar': '⥦', 'lvertneqq': '≨︀', 'lvnE': '≨︀', 'macr': '\xAF', 'male': '♂', 'malt': '✠', 'maltese': '✠', 'map': '↦', 'Map': '⤅', 'mapsto': '↦', 'mapstodown': '↧', 'mapstoleft': '↤', 'mapstoup': '↥', 'marker': '▮', 'mcomma': '⨩', 'mcy': 'м', 'Mcy': 'М', 'mdash': '—', 'mDDot': '∺', 'measuredangle': '∡', 'MediumSpace': ' ', 'Mellintrf': 'ℳ', 'mfr': '𝔪', 'Mfr': '𝔐', 'mho': '℧', 'micro': '\xB5', 'mid': '∣', 'midast': '*', 'midcir': '⫰', 'middot': '\xB7', 'minus': '−', 'minusb': '⊟', 'minusd': '∸', 'minusdu': '⨪', 'MinusPlus': '∓', 'mlcp': '⫛', 'mldr': '…', 'mnplus': '∓', 'models': '⊧', 'mopf': '𝕞', 'Mopf': '𝕄', 'mp': '∓', 'mscr': '𝓂', 'Mscr': 'ℳ', 'mstpos': '∾', 'mu': 'μ', 'Mu': 'Μ', 'multimap': '⊸', 'mumap': '⊸', 'nabla': '∇', 'nacute': 'ń', 'Nacute': 'Ń', 'nang': '∠⃒', 'nap': '≉', 'napE': '⩰̸', 'napid': '≋̸', 'napos': 'ŉ', 'napprox': '≉', 'natur': '♮', 'natural': '♮', 'naturals': 'ℕ', 'nbsp': '\xA0', 'nbump': '≎̸', 'nbumpe': '≏̸', 'ncap': '⩃', 'ncaron': 'ň', 'Ncaron': 'Ň', 'ncedil': 'ņ', 'Ncedil': 'Ņ', 'ncong': '≇', 'ncongdot': '⩭̸', 'ncup': '⩂', 'ncy': 'н', 'Ncy': 'Н', 'ndash': '–', 'ne': '≠', 'nearhk': '⤤', 'nearr': '↗', 'neArr': '⇗', 'nearrow': '↗', 'nedot': '≐̸', 'NegativeMediumSpace': '​', 'NegativeThickSpace': '​', 'NegativeThinSpace': '​', 'NegativeVeryThinSpace': '​', 'nequiv': '≢', 'nesear': '⤨', 'nesim': '≂̸', 'NestedGreaterGreater': '≫', 'NestedLessLess': '≪', 'NewLine': '\n', 'nexist': '∄', 'nexists': '∄', 'nfr': '𝔫', 'Nfr': '𝔑', 'nge': '≱', 'ngE': '≧̸', 'ngeq': '≱', 'ngeqq': '≧̸', 'ngeqslant': '⩾̸', 'nges': '⩾̸', 'nGg': '⋙̸', 'ngsim': '≵', 'ngt': '≯', 'nGt': '≫⃒', 'ngtr': '≯', 'nGtv': '≫̸', 'nharr': '↮', 'nhArr': '⇎', 'nhpar': '⫲', 'ni': '∋', 'nis': '⋼', 'nisd': '⋺', 'niv': '∋', 'njcy': 'њ', 'NJcy': 'Њ', 'nlarr': '↚', 'nlArr': '⇍', 'nldr': '‥', 'nle': '≰', 'nlE': '≦̸', 'nleftarrow': '↚', 'nLeftarrow': '⇍', 'nleftrightarrow': '↮', 'nLeftrightarrow': '⇎', 'nleq': '≰', 'nleqq': '≦̸', 'nleqslant': '⩽̸', 'nles': '⩽̸', 'nless': '≮', 'nLl': '⋘̸', 'nlsim': '≴', 'nlt': '≮', 'nLt': '≪⃒', 'nltri': '⋪', 'nltrie': '⋬', 'nLtv': '≪̸', 'nmid': '∤', 'NoBreak': '⁠', 'NonBreakingSpace': '\xA0', 'nopf': '𝕟', 'Nopf': 'ℕ', 'not': '\xAC', 'Not': '⫬', 'NotCongruent': '≢', 'NotCupCap': '≭', 'NotDoubleVerticalBar': '∦', 'NotElement': '∉', 'NotEqual': '≠', 'NotEqualTilde': '≂̸', 'NotExists': '∄', 'NotGreater': '≯', 'NotGreaterEqual': '≱', 'NotGreaterFullEqual': '≧̸', 'NotGreaterGreater': '≫̸', 'NotGreaterLess': '≹', 'NotGreaterSlantEqual': '⩾̸', 'NotGreaterTilde': '≵', 'NotHumpDownHump': '≎̸', 'NotHumpEqual': '≏̸', 'notin': '∉', 'notindot': '⋵̸', 'notinE': '⋹̸', 'notinva': '∉', 'notinvb': '⋷', 'notinvc': '⋶', 'NotLeftTriangle': '⋪', 'NotLeftTriangleBar': '⧏̸', 'NotLeftTriangleEqual': '⋬', 'NotLess': '≮', 'NotLessEqual': '≰', 'NotLessGreater': '≸', 'NotLessLess': '≪̸', 'NotLessSlantEqual': '⩽̸', 'NotLessTilde': '≴', 'NotNestedGreaterGreater': '⪢̸', 'NotNestedLessLess': '⪡̸', 'notni': '∌', 'notniva': '∌', 'notnivb': '⋾', 'notnivc': '⋽', 'NotPrecedes': '⊀', 'NotPrecedesEqual': '⪯̸', 'NotPrecedesSlantEqual': '⋠', 'NotReverseElement': '∌', 'NotRightTriangle': '⋫', 'NotRightTriangleBar': '⧐̸', 'NotRightTriangleEqual': '⋭', 'NotSquareSubset': '⊏̸', 'NotSquareSubsetEqual': '⋢', 'NotSquareSuperset': '⊐̸', 'NotSquareSupersetEqual': '⋣', 'NotSubset': '⊂⃒', 'NotSubsetEqual': '⊈', 'NotSucceeds': '⊁', 'NotSucceedsEqual': '⪰̸', 'NotSucceedsSlantEqual': '⋡', 'NotSucceedsTilde': '≿̸', 'NotSuperset': '⊃⃒', 'NotSupersetEqual': '⊉', 'NotTilde': '≁', 'NotTildeEqual': '≄', 'NotTildeFullEqual': '≇', 'NotTildeTilde': '≉', 'NotVerticalBar': '∤', 'npar': '∦', 'nparallel': '∦', 'nparsl': '⫽⃥', 'npart': '∂̸', 'npolint': '⨔', 'npr': '⊀', 'nprcue': '⋠', 'npre': '⪯̸', 'nprec': '⊀', 'npreceq': '⪯̸', 'nrarr': '↛', 'nrArr': '⇏', 'nrarrc': '⤳̸', 'nrarrw': '↝̸', 'nrightarrow': '↛', 'nRightarrow': '⇏', 'nrtri': '⋫', 'nrtrie': '⋭', 'nsc': '⊁', 'nsccue': '⋡', 'nsce': '⪰̸', 'nscr': '𝓃', 'Nscr': '𝒩', 'nshortmid': '∤', 'nshortparallel': '∦', 'nsim': '≁', 'nsime': '≄', 'nsimeq': '≄', 'nsmid': '∤', 'nspar': '∦', 'nsqsube': '⋢', 'nsqsupe': '⋣', 'nsub': '⊄', 'nsube': '⊈', 'nsubE': '⫅̸', 'nsubset': '⊂⃒', 'nsubseteq': '⊈', 'nsubseteqq': '⫅̸', 'nsucc': '⊁', 'nsucceq': '⪰̸', 'nsup': '⊅', 'nsupe': '⊉', 'nsupE': '⫆̸', 'nsupset': '⊃⃒', 'nsupseteq': '⊉', 'nsupseteqq': '⫆̸', 'ntgl': '≹', 'ntilde': '\xF1', 'Ntilde': '\xD1', 'ntlg': '≸', 'ntriangleleft': '⋪', 'ntrianglelefteq': '⋬', 'ntriangleright': '⋫', 'ntrianglerighteq': '⋭', 'nu': 'ν', 'Nu': 'Ν', 'num': '#', 'numero': '№', 'numsp': ' ', 'nvap': '≍⃒', 'nvdash': '⊬', 'nvDash': '⊭', 'nVdash': '⊮', 'nVDash': '⊯', 'nvge': '≥⃒', 'nvgt': '>⃒', 'nvHarr': '⤄', 'nvinfin': '⧞', 'nvlArr': '⤂', 'nvle': '≤⃒', 'nvlt': '<⃒', 'nvltrie': '⊴⃒', 'nvrArr': '⤃', 'nvrtrie': '⊵⃒', 'nvsim': '∼⃒', 'nwarhk': '⤣', 'nwarr': '↖', 'nwArr': '⇖', 'nwarrow': '↖', 'nwnear': '⤧', 'oacute': '\xF3', 'Oacute': '\xD3', 'oast': '⊛', 'ocir': '⊚', 'ocirc': '\xF4', 'Ocirc': '\xD4', 'ocy': 'о', 'Ocy': 'О', 'odash': '⊝', 'odblac': 'ő', 'Odblac': 'Ő', 'odiv': '⨸', 'odot': '⊙', 'odsold': '⦼', 'oelig': 'œ', 'OElig': 'Œ', 'ofcir': '⦿', 'ofr': '𝔬', 'Ofr': '𝔒', 'ogon': '˛', 'ograve': '\xF2', 'Ograve': '\xD2', 'ogt': '⧁', 'ohbar': '⦵', 'ohm': 'Ω', 'oint': '∮', 'olarr': '↺', 'olcir': '⦾', 'olcross': '⦻', 'oline': '‾', 'olt': '⧀', 'omacr': 'ō', 'Omacr': 'Ō', 'omega': 'ω', 'Omega': 'Ω', 'omicron': 'ο', 'Omicron': 'Ο', 'omid': '⦶', 'ominus': '⊖', 'oopf': '𝕠', 'Oopf': '𝕆', 'opar': '⦷', 'OpenCurlyDoubleQuote': '“', 'OpenCurlyQuote': '‘', 'operp': '⦹', 'oplus': '⊕', 'or': '∨', 'Or': '⩔', 'orarr': '↻', 'ord': '⩝', 'order': 'ℴ', 'orderof': 'ℴ', 'ordf': '\xAA', 'ordm': '\xBA', 'origof': '⊶', 'oror': '⩖', 'orslope': '⩗', 'orv': '⩛', 'oS': 'Ⓢ', 'oscr': 'ℴ', 'Oscr': '𝒪', 'oslash': '\xF8', 'Oslash': '\xD8', 'osol': '⊘', 'otilde': '\xF5', 'Otilde': '\xD5', 'otimes': '⊗', 'Otimes': '⨷', 'otimesas': '⨶', 'ouml': '\xF6', 'Ouml': '\xD6', 'ovbar': '⌽', 'OverBar': '‾', 'OverBrace': '⏞', 'OverBracket': '⎴', 'OverParenthesis': '⏜', 'par': '∥', 'para': '\xB6', 'parallel': '∥', 'parsim': '⫳', 'parsl': '⫽', 'part': '∂', 'PartialD': '∂', 'pcy': 'п', 'Pcy': 'П', 'percnt': '%', 'period': '.', 'permil': '‰', 'perp': '⊥', 'pertenk': '‱', 'pfr': '𝔭', 'Pfr': '𝔓', 'phi': 'φ', 'Phi': 'Φ', 'phiv': 'ϕ', 'phmmat': 'ℳ', 'phone': '☎', 'pi': 'π', 'Pi': 'Π', 'pitchfork': '⋔', 'piv': 'ϖ', 'planck': 'ℏ', 'planckh': 'ℎ', 'plankv': 'ℏ', 'plus': '+', 'plusacir': '⨣', 'plusb': '⊞', 'pluscir': '⨢', 'plusdo': '∔', 'plusdu': '⨥', 'pluse': '⩲', 'PlusMinus': '\xB1', 'plusmn': '\xB1', 'plussim': '⨦', 'plustwo': '⨧', 'pm': '\xB1', 'Poincareplane': 'ℌ', 'pointint': '⨕', 'popf': '𝕡', 'Popf': 'ℙ', 'pound': '\xA3', 'pr': '≺', 'Pr': '⪻', 'prap': '⪷', 'prcue': '≼', 'pre': '⪯', 'prE': '⪳', 'prec': '≺', 'precapprox': '⪷', 'preccurlyeq': '≼', 'Precedes': '≺', 'PrecedesEqual': '⪯', 'PrecedesSlantEqual': '≼', 'PrecedesTilde': '≾', 'preceq': '⪯', 'precnapprox': '⪹', 'precneqq': '⪵', 'precnsim': '⋨', 'precsim': '≾', 'prime': '′', 'Prime': '″', 'primes': 'ℙ', 'prnap': '⪹', 'prnE': '⪵', 'prnsim': '⋨', 'prod': '∏', 'Product': '∏', 'profalar': '⌮', 'profline': '⌒', 'profsurf': '⌓', 'prop': '∝', 'Proportion': '∷', 'Proportional': '∝', 'propto': '∝', 'prsim': '≾', 'prurel': '⊰', 'pscr': '𝓅', 'Pscr': '𝒫', 'psi': 'ψ', 'Psi': 'Ψ', 'puncsp': ' ', 'qfr': '𝔮', 'Qfr': '𝔔', 'qint': '⨌', 'qopf': '𝕢', 'Qopf': 'ℚ', 'qprime': '⁗', 'qscr': '𝓆', 'Qscr': '𝒬', 'quaternions': 'ℍ', 'quatint': '⨖', 'quest': '?', 'questeq': '≟', 'quot': '"', 'QUOT': '"', 'rAarr': '⇛', 'race': '∽̱', 'racute': 'ŕ', 'Racute': 'Ŕ', 'radic': '√', 'raemptyv': '⦳', 'rang': '⟩', 'Rang': '⟫', 'rangd': '⦒', 'range': '⦥', 'rangle': '⟩', 'raquo': '\xBB', 'rarr': '→', 'rArr': '⇒', 'Rarr': '↠', 'rarrap': '⥵', 'rarrb': '⇥', 'rarrbfs': '⤠', 'rarrc': '⤳', 'rarrfs': '⤞', 'rarrhk': '↪', 'rarrlp': '↬', 'rarrpl': '⥅', 'rarrsim': '⥴', 'rarrtl': '↣', 'Rarrtl': '⤖', 'rarrw': '↝', 'ratail': '⤚', 'rAtail': '⤜', 'ratio': '∶', 'rationals': 'ℚ', 'rbarr': '⤍', 'rBarr': '⤏', 'RBarr': '⤐', 'rbbrk': '❳', 'rbrace': '}', 'rbrack': ']', 'rbrke': '⦌', 'rbrksld': '⦎', 'rbrkslu': '⦐', 'rcaron': 'ř', 'Rcaron': 'Ř', 'rcedil': 'ŗ', 'Rcedil': 'Ŗ', 'rceil': '⌉', 'rcub': '}', 'rcy': 'р', 'Rcy': 'Р', 'rdca': '⤷', 'rdldhar': '⥩', 'rdquo': '”', 'rdquor': '”', 'rdsh': '↳', 'Re': 'ℜ', 'real': 'ℜ', 'realine': 'ℛ', 'realpart': 'ℜ', 'reals': 'ℝ', 'rect': '▭', 'reg': '\xAE', 'REG': '\xAE', 'ReverseElement': '∋', 'ReverseEquilibrium': '⇋', 'ReverseUpEquilibrium': '⥯', 'rfisht': '⥽', 'rfloor': '⌋', 'rfr': '𝔯', 'Rfr': 'ℜ', 'rHar': '⥤', 'rhard': '⇁', 'rharu': '⇀', 'rharul': '⥬', 'rho': 'ρ', 'Rho': 'Ρ', 'rhov': 'ϱ', 'RightAngleBracket': '⟩', 'rightarrow': '→', 'Rightarrow': '⇒', 'RightArrow': '→', 'RightArrowBar': '⇥', 'RightArrowLeftArrow': '⇄', 'rightarrowtail': '↣', 'RightCeiling': '⌉', 'RightDoubleBracket': '⟧', 'RightDownTeeVector': '⥝', 'RightDownVector': '⇂', 'RightDownVectorBar': '⥕', 'RightFloor': '⌋', 'rightharpoondown': '⇁', 'rightharpoonup': '⇀', 'rightleftarrows': '⇄', 'rightleftharpoons': '⇌', 'rightrightarrows': '⇉', 'rightsquigarrow': '↝', 'RightTee': '⊢', 'RightTeeArrow': '↦', 'RightTeeVector': '⥛', 'rightthreetimes': '⋌', 'RightTriangle': '⊳', 'RightTriangleBar': '⧐', 'RightTriangleEqual': '⊵', 'RightUpDownVector': '⥏', 'RightUpTeeVector': '⥜', 'RightUpVector': '↾', 'RightUpVectorBar': '⥔', 'RightVector': '⇀', 'RightVectorBar': '⥓', 'ring': '˚', 'risingdotseq': '≓', 'rlarr': '⇄', 'rlhar': '⇌', 'rlm': '‏', 'rmoust': '⎱', 'rmoustache': '⎱', 'rnmid': '⫮', 'roang': '⟭', 'roarr': '⇾', 'robrk': '⟧', 'ropar': '⦆', 'ropf': '𝕣', 'Ropf': 'ℝ', 'roplus': '⨮', 'rotimes': '⨵', 'RoundImplies': '⥰', 'rpar': ')', 'rpargt': '⦔', 'rppolint': '⨒', 'rrarr': '⇉', 'Rrightarrow': '⇛', 'rsaquo': '›', 'rscr': '𝓇', 'Rscr': 'ℛ', 'rsh': '↱', 'Rsh': '↱', 'rsqb': ']', 'rsquo': '’', 'rsquor': '’', 'rthree': '⋌', 'rtimes': '⋊', 'rtri': '▹', 'rtrie': '⊵', 'rtrif': '▸', 'rtriltri': '⧎', 'RuleDelayed': '⧴', 'ruluhar': '⥨', 'rx': '℞', 'sacute': 'ś', 'Sacute': 'Ś', 'sbquo': '‚', 'sc': '≻', 'Sc': '⪼', 'scap': '⪸', 'scaron': 'š', 'Scaron': 'Š', 'sccue': '≽', 'sce': '⪰', 'scE': '⪴', 'scedil': 'ş', 'Scedil': 'Ş', 'scirc': 'ŝ', 'Scirc': 'Ŝ', 'scnap': '⪺', 'scnE': '⪶', 'scnsim': '⋩', 'scpolint': '⨓', 'scsim': '≿', 'scy': 'с', 'Scy': 'С', 'sdot': '⋅', 'sdotb': '⊡', 'sdote': '⩦', 'searhk': '⤥', 'searr': '↘', 'seArr': '⇘', 'searrow': '↘', 'sect': '\xA7', 'semi': ';', 'seswar': '⤩', 'setminus': '∖', 'setmn': '∖', 'sext': '✶', 'sfr': '𝔰', 'Sfr': '𝔖', 'sfrown': '⌢', 'sharp': '♯', 'shchcy': 'щ', 'SHCHcy': 'Щ', 'shcy': 'ш', 'SHcy': 'Ш', 'ShortDownArrow': '↓', 'ShortLeftArrow': '←', 'shortmid': '∣', 'shortparallel': '∥', 'ShortRightArrow': '→', 'ShortUpArrow': '↑', 'shy': '\xAD', 'sigma': 'σ', 'Sigma': 'Σ', 'sigmaf': 'ς', 'sigmav': 'ς', 'sim': '∼', 'simdot': '⩪', 'sime': '≃', 'simeq': '≃', 'simg': '⪞', 'simgE': '⪠', 'siml': '⪝', 'simlE': '⪟', 'simne': '≆', 'simplus': '⨤', 'simrarr': '⥲', 'slarr': '←', 'SmallCircle': '∘', 'smallsetminus': '∖', 'smashp': '⨳', 'smeparsl': '⧤', 'smid': '∣', 'smile': '⌣', 'smt': '⪪', 'smte': '⪬', 'smtes': '⪬︀', 'softcy': 'ь', 'SOFTcy': 'Ь', 'sol': '/', 'solb': '⧄', 'solbar': '⌿', 'sopf': '𝕤', 'Sopf': '𝕊', 'spades': '♠', 'spadesuit': '♠', 'spar': '∥', 'sqcap': '⊓', 'sqcaps': '⊓︀', 'sqcup': '⊔', 'sqcups': '⊔︀', 'Sqrt': '√', 'sqsub': '⊏', 'sqsube': '⊑', 'sqsubset': '⊏', 'sqsubseteq': '⊑', 'sqsup': '⊐', 'sqsupe': '⊒', 'sqsupset': '⊐', 'sqsupseteq': '⊒', 'squ': '□', 'square': '□', 'Square': '□', 'SquareIntersection': '⊓', 'SquareSubset': '⊏', 'SquareSubsetEqual': '⊑', 'SquareSuperset': '⊐', 'SquareSupersetEqual': '⊒', 'SquareUnion': '⊔', 'squarf': '▪', 'squf': '▪', 'srarr': '→', 'sscr': '𝓈', 'Sscr': '𝒮', 'ssetmn': '∖', 'ssmile': '⌣', 'sstarf': '⋆', 'star': '☆', 'Star': '⋆', 'starf': '★', 'straightepsilon': 'ϵ', 'straightphi': 'ϕ', 'strns': '\xAF', 'sub': '⊂', 'Sub': '⋐', 'subdot': '⪽', 'sube': '⊆', 'subE': '⫅', 'subedot': '⫃', 'submult': '⫁', 'subne': '⊊', 'subnE': '⫋', 'subplus': '⪿', 'subrarr': '⥹', 'subset': '⊂', 'Subset': '⋐', 'subseteq': '⊆', 'subseteqq': '⫅', 'SubsetEqual': '⊆', 'subsetneq': '⊊', 'subsetneqq': '⫋', 'subsim': '⫇', 'subsub': '⫕', 'subsup': '⫓', 'succ': '≻', 'succapprox': '⪸', 'succcurlyeq': '≽', 'Succeeds': '≻', 'SucceedsEqual': '⪰', 'SucceedsSlantEqual': '≽', 'SucceedsTilde': '≿', 'succeq': '⪰', 'succnapprox': '⪺', 'succneqq': '⪶', 'succnsim': '⋩', 'succsim': '≿', 'SuchThat': '∋', 'sum': '∑', 'Sum': '∑', 'sung': '♪', 'sup': '⊃', 'Sup': '⋑', 'sup1': '\xB9', 'sup2': '\xB2', 'sup3': '\xB3', 'supdot': '⪾', 'supdsub': '⫘', 'supe': '⊇', 'supE': '⫆', 'supedot': '⫄', 'Superset': '⊃', 'SupersetEqual': '⊇', 'suphsol': '⟉', 'suphsub': '⫗', 'suplarr': '⥻', 'supmult': '⫂', 'supne': '⊋', 'supnE': '⫌', 'supplus': '⫀', 'supset': '⊃', 'Supset': '⋑', 'supseteq': '⊇', 'supseteqq': '⫆', 'supsetneq': '⊋', 'supsetneqq': '⫌', 'supsim': '⫈', 'supsub': '⫔', 'supsup': '⫖', 'swarhk': '⤦', 'swarr': '↙', 'swArr': '⇙', 'swarrow': '↙', 'swnwar': '⤪', 'szlig': '\xDF', 'Tab': '\t', 'target': '⌖', 'tau': 'τ', 'Tau': 'Τ', 'tbrk': '⎴', 'tcaron': 'ť', 'Tcaron': 'Ť', 'tcedil': 'ţ', 'Tcedil': 'Ţ', 'tcy': 'т', 'Tcy': 'Т', 'tdot': '⃛', 'telrec': '⌕', 'tfr': '𝔱', 'Tfr': '𝔗', 'there4': '∴', 'therefore': '∴', 'Therefore': '∴', 'theta': 'θ', 'Theta': 'Θ', 'thetasym': 'ϑ', 'thetav': 'ϑ', 'thickapprox': '≈', 'thicksim': '∼', 'ThickSpace': '  ', 'thinsp': ' ', 'ThinSpace': ' ', 'thkap': '≈', 'thksim': '∼', 'thorn': '\xFE', 'THORN': '\xDE', 'tilde': '˜', 'Tilde': '∼', 'TildeEqual': '≃', 'TildeFullEqual': '≅', 'TildeTilde': '≈', 'times': '\xD7', 'timesb': '⊠', 'timesbar': '⨱', 'timesd': '⨰', 'tint': '∭', 'toea': '⤨', 'top': '⊤', 'topbot': '⌶', 'topcir': '⫱', 'topf': '𝕥', 'Topf': '𝕋', 'topfork': '⫚', 'tosa': '⤩', 'tprime': '‴', 'trade': '™', 'TRADE': '™', 'triangle': '▵', 'triangledown': '▿', 'triangleleft': '◃', 'trianglelefteq': '⊴', 'triangleq': '≜', 'triangleright': '▹', 'trianglerighteq': '⊵', 'tridot': '◬', 'trie': '≜', 'triminus': '⨺', 'TripleDot': '⃛', 'triplus': '⨹', 'trisb': '⧍', 'tritime': '⨻', 'trpezium': '⏢', 'tscr': '𝓉', 'Tscr': '𝒯', 'tscy': 'ц', 'TScy': 'Ц', 'tshcy': 'ћ', 'TSHcy': 'Ћ', 'tstrok': 'ŧ', 'Tstrok': 'Ŧ', 'twixt': '≬', 'twoheadleftarrow': '↞', 'twoheadrightarrow': '↠', 'uacute': '\xFA', 'Uacute': '\xDA', 'uarr': '↑', 'uArr': '⇑', 'Uarr': '↟', 'Uarrocir': '⥉', 'ubrcy': 'ў', 'Ubrcy': 'Ў', 'ubreve': 'ŭ', 'Ubreve': 'Ŭ', 'ucirc': '\xFB', 'Ucirc': '\xDB', 'ucy': 'у', 'Ucy': 'У', 'udarr': '⇅', 'udblac': 'ű', 'Udblac': 'Ű', 'udhar': '⥮', 'ufisht': '⥾', 'ufr': '𝔲', 'Ufr': '𝔘', 'ugrave': '\xF9', 'Ugrave': '\xD9', 'uHar': '⥣', 'uharl': '↿', 'uharr': '↾', 'uhblk': '▀', 'ulcorn': '⌜', 'ulcorner': '⌜', 'ulcrop': '⌏', 'ultri': '◸', 'umacr': 'ū', 'Umacr': 'Ū', 'uml': '\xA8', 'UnderBar': '_', 'UnderBrace': '⏟', 'UnderBracket': '⎵', 'UnderParenthesis': '⏝', 'Union': '⋃', 'UnionPlus': '⊎', 'uogon': 'ų', 'Uogon': 'Ų', 'uopf': '𝕦', 'Uopf': '𝕌', 'uparrow': '↑', 'Uparrow': '⇑', 'UpArrow': '↑', 'UpArrowBar': '⤒', 'UpArrowDownArrow': '⇅', 'updownarrow': '↕', 'Updownarrow': '⇕', 'UpDownArrow': '↕', 'UpEquilibrium': '⥮', 'upharpoonleft': '↿', 'upharpoonright': '↾', 'uplus': '⊎', 'UpperLeftArrow': '↖', 'UpperRightArrow': '↗', 'upsi': 'υ', 'Upsi': 'ϒ', 'upsih': 'ϒ', 'upsilon': 'υ', 'Upsilon': 'Υ', 'UpTee': '⊥', 'UpTeeArrow': '↥', 'upuparrows': '⇈', 'urcorn': '⌝', 'urcorner': '⌝', 'urcrop': '⌎', 'uring': 'ů', 'Uring': 'Ů', 'urtri': '◹', 'uscr': '𝓊', 'Uscr': '𝒰', 'utdot': '⋰', 'utilde': 'ũ', 'Utilde': 'Ũ', 'utri': '▵', 'utrif': '▴', 'uuarr': '⇈', 'uuml': '\xFC', 'Uuml': '\xDC', 'uwangle': '⦧', 'vangrt': '⦜', 'varepsilon': 'ϵ', 'varkappa': 'ϰ', 'varnothing': '∅', 'varphi': 'ϕ', 'varpi': 'ϖ', 'varpropto': '∝', 'varr': '↕', 'vArr': '⇕', 'varrho': 'ϱ', 'varsigma': 'ς', 'varsubsetneq': '⊊︀', 'varsubsetneqq': '⫋︀', 'varsupsetneq': '⊋︀', 'varsupsetneqq': '⫌︀', 'vartheta': 'ϑ', 'vartriangleleft': '⊲', 'vartriangleright': '⊳', 'vBar': '⫨', 'Vbar': '⫫', 'vBarv': '⫩', 'vcy': 'в', 'Vcy': 'В', 'vdash': '⊢', 'vDash': '⊨', 'Vdash': '⊩', 'VDash': '⊫', 'Vdashl': '⫦', 'vee': '∨', 'Vee': '⋁', 'veebar': '⊻', 'veeeq': '≚', 'vellip': '⋮', 'verbar': '|', 'Verbar': '‖', 'vert': '|', 'Vert': '‖', 'VerticalBar': '∣', 'VerticalLine': '|', 'VerticalSeparator': '❘', 'VerticalTilde': '≀', 'VeryThinSpace': ' ', 'vfr': '𝔳', 'Vfr': '𝔙', 'vltri': '⊲', 'vnsub': '⊂⃒', 'vnsup': '⊃⃒', 'vopf': '𝕧', 'Vopf': '𝕍', 'vprop': '∝', 'vrtri': '⊳', 'vscr': '𝓋', 'Vscr': '𝒱', 'vsubne': '⊊︀', 'vsubnE': '⫋︀', 'vsupne': '⊋︀', 'vsupnE': '⫌︀', 'Vvdash': '⊪', 'vzigzag': '⦚', 'wcirc': 'ŵ', 'Wcirc': 'Ŵ', 'wedbar': '⩟', 'wedge': '∧', 'Wedge': '⋀', 'wedgeq': '≙', 'weierp': '℘', 'wfr': '𝔴', 'Wfr': '𝔚', 'wopf': '𝕨', 'Wopf': '𝕎', 'wp': '℘', 'wr': '≀', 'wreath': '≀', 'wscr': '𝓌', 'Wscr': '𝒲', 'xcap': '⋂', 'xcirc': '◯', 'xcup': '⋃', 'xdtri': '▽', 'xfr': '𝔵', 'Xfr': '𝔛', 'xharr': '⟷', 'xhArr': '⟺', 'xi': 'ξ', 'Xi': 'Ξ', 'xlarr': '⟵', 'xlArr': '⟸', 'xmap': '⟼', 'xnis': '⋻', 'xodot': '⨀', 'xopf': '𝕩', 'Xopf': '𝕏', 'xoplus': '⨁', 'xotime': '⨂', 'xrarr': '⟶', 'xrArr': '⟹', 'xscr': '𝓍', 'Xscr': '𝒳', 'xsqcup': '⨆', 'xuplus': '⨄', 'xutri': '△', 'xvee': '⋁', 'xwedge': '⋀', 'yacute': '\xFD', 'Yacute': '\xDD', 'yacy': 'я', 'YAcy': 'Я', 'ycirc': 'ŷ', 'Ycirc': 'Ŷ', 'ycy': 'ы', 'Ycy': 'Ы', 'yen': '\xA5', 'yfr': '𝔶', 'Yfr': '𝔜', 'yicy': 'ї', 'YIcy': 'Ї', 'yopf': '𝕪', 'Yopf': '𝕐', 'yscr': '𝓎', 'Yscr': '𝒴', 'yucy': 'ю', 'YUcy': 'Ю', 'yuml': '\xFF', 'Yuml': 'Ÿ', 'zacute': 'ź', 'Zacute': 'Ź', 'zcaron': 'ž', 'Zcaron': 'Ž', 'zcy': 'з', 'Zcy': 'З', 'zdot': 'ż', 'Zdot': 'Ż', 'zeetrf': 'ℨ', 'ZeroWidthSpace': '​', 'zeta': 'ζ', 'Zeta': 'Ζ', 'zfr': '𝔷', 'Zfr': 'ℨ', 'zhcy': 'ж', 'ZHcy': 'Ж', 'zigrarr': '⇝', 'zopf': '𝕫', 'Zopf': 'ℤ', 'zscr': '𝓏', 'Zscr': '𝒵', 'zwj': '‍', 'zwnj': '‌' };
		var decodeMapLegacy = { 'aacute': '\xE1', 'Aacute': '\xC1', 'acirc': '\xE2', 'Acirc': '\xC2', 'acute': '\xB4', 'aelig': '\xE6', 'AElig': '\xC6', 'agrave': '\xE0', 'Agrave': '\xC0', 'amp': '&', 'AMP': '&', 'aring': '\xE5', 'Aring': '\xC5', 'atilde': '\xE3', 'Atilde': '\xC3', 'auml': '\xE4', 'Auml': '\xC4', 'brvbar': '\xA6', 'ccedil': '\xE7', 'Ccedil': '\xC7', 'cedil': '\xB8', 'cent': '\xA2', 'copy': '\xA9', 'COPY': '\xA9', 'curren': '\xA4', 'deg': '\xB0', 'divide': '\xF7', 'eacute': '\xE9', 'Eacute': '\xC9', 'ecirc': '\xEA', 'Ecirc': '\xCA', 'egrave': '\xE8', 'Egrave': '\xC8', 'eth': '\xF0', 'ETH': '\xD0', 'euml': '\xEB', 'Euml': '\xCB', 'frac12': '\xBD', 'frac14': '\xBC', 'frac34': '\xBE', 'gt': '>', 'GT': '>', 'iacute': '\xED', 'Iacute': '\xCD', 'icirc': '\xEE', 'Icirc': '\xCE', 'iexcl': '\xA1', 'igrave': '\xEC', 'Igrave': '\xCC', 'iquest': '\xBF', 'iuml': '\xEF', 'Iuml': '\xCF', 'laquo': '\xAB', 'lt': '<', 'LT': '<', 'macr': '\xAF', 'micro': '\xB5', 'middot': '\xB7', 'nbsp': '\xA0', 'not': '\xAC', 'ntilde': '\xF1', 'Ntilde': '\xD1', 'oacute': '\xF3', 'Oacute': '\xD3', 'ocirc': '\xF4', 'Ocirc': '\xD4', 'ograve': '\xF2', 'Ograve': '\xD2', 'ordf': '\xAA', 'ordm': '\xBA', 'oslash': '\xF8', 'Oslash': '\xD8', 'otilde': '\xF5', 'Otilde': '\xD5', 'ouml': '\xF6', 'Ouml': '\xD6', 'para': '\xB6', 'plusmn': '\xB1', 'pound': '\xA3', 'quot': '"', 'QUOT': '"', 'raquo': '\xBB', 'reg': '\xAE', 'REG': '\xAE', 'sect': '\xA7', 'shy': '\xAD', 'sup1': '\xB9', 'sup2': '\xB2', 'sup3': '\xB3', 'szlig': '\xDF', 'thorn': '\xFE', 'THORN': '\xDE', 'times': '\xD7', 'uacute': '\xFA', 'Uacute': '\xDA', 'ucirc': '\xFB', 'Ucirc': '\xDB', 'ugrave': '\xF9', 'Ugrave': '\xD9', 'uml': '\xA8', 'uuml': '\xFC', 'Uuml': '\xDC', 'yacute': '\xFD', 'Yacute': '\xDD', 'yen': '\xA5', 'yuml': '\xFF' };
		var decodeMapNumeric = { '0': '�', '128': '€', '130': '‚', '131': 'ƒ', '132': '„', '133': '…', '134': '†', '135': '‡', '136': 'ˆ', '137': '‰', '138': 'Š', '139': '‹', '140': 'Œ', '142': 'Ž', '145': '‘', '146': '’', '147': '“', '148': '”', '149': '•', '150': '–', '151': '—', '152': '˜', '153': '™', '154': 'š', '155': '›', '156': 'œ', '158': 'ž', '159': 'Ÿ' };
		var invalidReferenceCodePoints = [1, 2, 3, 4, 5, 6, 7, 8, 11, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 64976, 64977, 64978, 64979, 64980, 64981, 64982, 64983, 64984, 64985, 64986, 64987, 64988, 64989, 64990, 64991, 64992, 64993, 64994, 64995, 64996, 64997, 64998, 64999, 65000, 65001, 65002, 65003, 65004, 65005, 65006, 65007, 65534, 65535, 131070, 131071, 196606, 196607, 262142, 262143, 327678, 327679, 393214, 393215, 458750, 458751, 524286, 524287, 589822, 589823, 655358, 655359, 720894, 720895, 786430, 786431, 851966, 851967, 917502, 917503, 983038, 983039, 1048574, 1048575, 1114110, 1114111];
	
		/*--------------------------------------------------------------------------*/
	
		var stringFromCharCode = String.fromCharCode;
	
		var object = {};
		var hasOwnProperty = object.hasOwnProperty;
		var has = function has(object, propertyName) {
			return hasOwnProperty.call(object, propertyName);
		};
	
		var contains = function contains(array, value) {
			var index = -1;
			var length = array.length;
			while (++index < length) {
				if (array[index] == value) {
					return true;
				}
			}
			return false;
		};
	
		var merge = function merge(options, defaults) {
			if (!options) {
				return defaults;
			}
			var result = {};
			var key;
			for (key in defaults) {
				// A `hasOwnProperty` check is not needed here, since only recognized
				// option names are used anyway. Any others are ignored.
				result[key] = has(options, key) ? options[key] : defaults[key];
			}
			return result;
		};
	
		// Modified version of `ucs2encode`; see https://mths.be/punycode.
		var codePointToSymbol = function codePointToSymbol(codePoint, strict) {
			var output = '';
			if (codePoint >= 0xD800 && codePoint <= 0xDFFF || codePoint > 0x10FFFF) {
				// See issue #4:
				// “Otherwise, if the number is in the range 0xD800 to 0xDFFF or is
				// greater than 0x10FFFF, then this is a parse error. Return a U+FFFD
				// REPLACEMENT CHARACTER.”
				if (strict) {
					parseError('character reference outside the permissible Unicode range');
				}
				return '�';
			}
			if (has(decodeMapNumeric, codePoint)) {
				if (strict) {
					parseError('disallowed character reference');
				}
				return decodeMapNumeric[codePoint];
			}
			if (strict && contains(invalidReferenceCodePoints, codePoint)) {
				parseError('disallowed character reference');
			}
			if (codePoint > 0xFFFF) {
				codePoint -= 0x10000;
				output += stringFromCharCode(codePoint >>> 10 & 0x3FF | 0xD800);
				codePoint = 0xDC00 | codePoint & 0x3FF;
			}
			output += stringFromCharCode(codePoint);
			return output;
		};
	
		var hexEscape = function hexEscape(codePoint) {
			return '&#x' + codePoint.toString(16).toUpperCase() + ';';
		};
	
		var decEscape = function decEscape(codePoint) {
			return '&#' + codePoint + ';';
		};
	
		var parseError = function parseError(message) {
			throw Error('Parse error: ' + message);
		};
	
		/*--------------------------------------------------------------------------*/
	
		var encode = function encode(string, options) {
			options = merge(options, encode.options);
			var strict = options.strict;
			if (strict && regexInvalidRawCodePoint.test(string)) {
				parseError('forbidden code point');
			}
			var encodeEverything = options.encodeEverything;
			var useNamedReferences = options.useNamedReferences;
			var allowUnsafeSymbols = options.allowUnsafeSymbols;
			var escapeCodePoint = options.decimal ? decEscape : hexEscape;
	
			var escapeBmpSymbol = function escapeBmpSymbol(symbol) {
				return escapeCodePoint(symbol.charCodeAt(0));
			};
	
			if (encodeEverything) {
				// Encode ASCII symbols.
				string = string.replace(regexAsciiWhitelist, function (symbol) {
					// Use named references if requested & possible.
					if (useNamedReferences && has(encodeMap, symbol)) {
						return '&' + encodeMap[symbol] + ';';
					}
					return escapeBmpSymbol(symbol);
				});
				// Shorten a few escapes that represent two symbols, of which at least one
				// is within the ASCII range.
				if (useNamedReferences) {
					string = string.replace(/&gt;\u20D2/g, '&nvgt;').replace(/&lt;\u20D2/g, '&nvlt;').replace(/&#x66;&#x6A;/g, '&fjlig;');
				}
				// Encode non-ASCII symbols.
				if (useNamedReferences) {
					// Encode non-ASCII symbols that can be replaced with a named reference.
					string = string.replace(regexEncodeNonAscii, function (string) {
						// Note: there is no need to check `has(encodeMap, string)` here.
						return '&' + encodeMap[string] + ';';
					});
				}
				// Note: any remaining non-ASCII symbols are handled outside of the `if`.
			} else if (useNamedReferences) {
				// Apply named character references.
				// Encode `<>"'&` using named character references.
				if (!allowUnsafeSymbols) {
					string = string.replace(regexEscape, function (string) {
						return '&' + encodeMap[string] + ';'; // no need to check `has()` here
					});
				}
				// Shorten escapes that represent two symbols, of which at least one is
				// `<>"'&`.
				string = string.replace(/&gt;\u20D2/g, '&nvgt;').replace(/&lt;\u20D2/g, '&nvlt;');
				// Encode non-ASCII symbols that can be replaced with a named reference.
				string = string.replace(regexEncodeNonAscii, function (string) {
					// Note: there is no need to check `has(encodeMap, string)` here.
					return '&' + encodeMap[string] + ';';
				});
			} else if (!allowUnsafeSymbols) {
				// Encode `<>"'&` using hexadecimal escapes, now that they’re not handled
				// using named character references.
				string = string.replace(regexEscape, escapeBmpSymbol);
			}
			return string
			// Encode astral symbols.
			.replace(regexAstralSymbols, function ($0) {
				// https://mathiasbynens.be/notes/javascript-encoding#surrogate-formulae
				var high = $0.charCodeAt(0);
				var low = $0.charCodeAt(1);
				var codePoint = (high - 0xD800) * 0x400 + low - 0xDC00 + 0x10000;
				return escapeCodePoint(codePoint);
			})
			// Encode any remaining BMP symbols that are not printable ASCII symbols
			// using a hexadecimal escape.
			.replace(regexBmpWhitelist, escapeBmpSymbol);
		};
		// Expose default options (so they can be overridden globally).
		encode.options = {
			'allowUnsafeSymbols': false,
			'encodeEverything': false,
			'strict': false,
			'useNamedReferences': false,
			'decimal': false
		};
	
		var decode = function decode(html, options) {
			options = merge(options, decode.options);
			var strict = options.strict;
			if (strict && regexInvalidEntity.test(html)) {
				parseError('malformed character reference');
			}
			return html.replace(regexDecode, function ($0, $1, $2, $3, $4, $5, $6, $7) {
				var codePoint;
				var semicolon;
				var decDigits;
				var hexDigits;
				var reference;
				var next;
				if ($1) {
					// Decode decimal escapes, e.g. `&#119558;`.
					decDigits = $1;
					semicolon = $2;
					if (strict && !semicolon) {
						parseError('character reference was not terminated by a semicolon');
					}
					codePoint = parseInt(decDigits, 10);
					return codePointToSymbol(codePoint, strict);
				}
				if ($3) {
					// Decode hexadecimal escapes, e.g. `&#x1D306;`.
					hexDigits = $3;
					semicolon = $4;
					if (strict && !semicolon) {
						parseError('character reference was not terminated by a semicolon');
					}
					codePoint = parseInt(hexDigits, 16);
					return codePointToSymbol(codePoint, strict);
				}
				if ($5) {
					// Decode named character references with trailing `;`, e.g. `&copy;`.
					reference = $5;
					if (has(decodeMap, reference)) {
						return decodeMap[reference];
					} else {
						// Ambiguous ampersand. https://mths.be/notes/ambiguous-ampersands
						if (strict) {
							parseError('named character reference was not terminated by a semicolon');
						}
						return $0;
					}
				}
				// If we’re still here, it’s a legacy reference for sure. No need for an
				// extra `if` check.
				// Decode named character references without trailing `;`, e.g. `&amp`
				// This is only a parse error if it gets converted to `&`, or if it is
				// followed by `=` in an attribute context.
				reference = $6;
				next = $7;
				if (next && options.isAttributeValue) {
					if (strict && next == '=') {
						parseError('`&` did not start a character reference');
					}
					return $0;
				} else {
					if (strict) {
						parseError('named character reference was not terminated by a semicolon');
					}
					// Note: there is no need to check `has(decodeMapLegacy, reference)`.
					return decodeMapLegacy[reference] + (next || '');
				}
			});
		};
		// Expose default options (so they can be overridden globally).
		decode.options = {
			'isAttributeValue': false,
			'strict': false
		};
	
		var escape = function escape(string) {
			return string.replace(regexEscape, function ($0) {
				// Note: there is no need to check `has(escapeMap, $0)` here.
				return escapeMap[$0];
			});
		};
	
		/*--------------------------------------------------------------------------*/
	
		var he = {
			'version': '1.1.0',
			'encode': encode,
			'decode': decode,
			'escape': escape,
			'unescape': decode
		};
	
		// Some AMD build optimizers, like r.js, check for specific condition patterns
		// like the following:
		if ("function" == 'function' && _typeof(__webpack_require__(/*! !webpack amd options */ 196)) == 'object' && __webpack_require__(/*! !webpack amd options */ 196)) {
			!(__WEBPACK_AMD_DEFINE_RESULT__ = function () {
				return he;
			}.call(exports, __webpack_require__, exports, module), __WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
		} else if (freeExports && !freeExports.nodeType) {
			if (freeModule) {
				// in Node.js, io.js, or RingoJS v0.8.0+
				freeModule.exports = he;
			} else {
				// in Narwhal or RingoJS v0.7.0-
				for (var key in he) {
					has(he, key) && (freeExports[key] = he[key]);
				}
			}
		} else {
			// in Rhino or a web browser
			root.he = he;
		}
	})(undefined);
	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../~/webpack/buildin/module.js */ 2268)(module), (function() { return this; }())))

/***/ },

/***/ 2420:
/*!**************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/PropTypes.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 2108);
	var validate = __webpack_require__(/*! react-prop-types-check */ 2421);
	
	//*------------------------------------------------------------------*
	
	//*------------------------------------------------------------------*
	
	
	var PointPropType = React.PropTypes.shape({
	    x: React.PropTypes.number.isRequired,
	    y: React.PropTypes.number.isRequired,
	    value: React.PropTypes.number.isRequired,
	    info: React.PropTypes.object.isRequired
	});
	
	var PointsInDataSeriesPropType = React.PropTypes.arrayOf(React.PropTypes.arrayOf(PointPropType));
	
	var DataSeriesProps = {
	    colour: React.PropTypes.string.isRequired,
	    name: React.PropTypes.string.isRequired,
	    data: React.PropTypes.arrayOf(PointPropType).isRequired
	};
	
	var validateDataSeries = function validateDataSeries(dataSeries) {
	    dataSeries.forEach(function (series) {
	        validate(series, DataSeriesProps);
	    });
	};
	
	var AxisCategoriesPropType = React.PropTypes.arrayOf(React.PropTypes.shape({
	    id: React.PropTypes.string, // ontology ID can be missing for x axis
	    label: React.PropTypes.string.isRequired
	})).isRequired;
	
	var HeatmapDataPropType = React.PropTypes.objectOf(function (heatmapData) {
	    validateDataSeries(heatmapData.dataSeries);
	
	    var width = heatmapData.xAxisCategories.length;
	    var height = heatmapData.yAxisCategories.length;
	
	    for (var i = 0; i < heatmapData.dataSeries.length; i++) {
	        for (var j = 0; j < heatmapData.dataSeries[i].data.length; j++) {
	            var point = heatmapData.dataSeries[i].data[j];
	            var x = point.x;
	            var y = point.y;
	            if (x < 0 || y < 0 || x >= width || y >= height) {
	                return new Error("Point with coordinates outside range:" + x + "," + y);
	            }
	        }
	    }
	
	    var isPermutation = function isPermutation(arr) {
	        return [].concat(arr).sort(function (a, b) {
	            return a - b;
	        }).map(function (el, ix) {
	            return el === ix;
	        }).reduce(function (l, r) {
	            return l && r;
	        }, true);
	    };
	
	    if (!heatmapData.orderings.hasOwnProperty("Default")) {
	        return new Error("Default ordering missing!");
	    }
	
	    for (var orderingName in heatmapData.orderings) {
	        if (heatmapData.orderings.hasOwnProperty(orderingName)) {
	            var ordering = heatmapData.orderings[orderingName];
	
	            if (ordering.columns.length !== width || !isPermutation(ordering.columns)) {
	                return new Error("Column ordering invalid in " + orderingName);
	            }
	            if (ordering.rows.length !== height || !isPermutation(ordering.rows)) {
	                return new Error("Row ordering invalid in " + orderingName);
	            }
	        }
	    }
	});
	
	var FormatterPropType = function FormatterPropType(props, propName) {
	    var f = props[propName];
	    if (typeof f === 'undefined') {
	        return new Error(propName + " formatter missing");
	    } else if (typeof f !== 'function' || f.name !== 'Formatter') {
	        return new Error(propName + " formatter not correctly created. See the main method of TooltipFormatter.jsx .");
	    }
	};
	
	exports.validateDataSeries = validateDataSeries;
	exports.PointsInDataSeries = PointsInDataSeriesPropType;
	exports.HeatmapData = HeatmapDataPropType;
	exports.AxisCategories = AxisCategoriesPropType;
	exports.Formatter = FormatterPropType;

/***/ },

/***/ 2421:
/*!********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-prop-types-check/package/react_prop_types_check.js ***!
  \********************************************************************************************************/
160,

/***/ 2422:
/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/ColoursForHighcharts.js ***!
  \*************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 2108);
	var validateDataSeries = __webpack_require__(/*! ./PropTypes.js */ 2420).validateDataSeries;
	var Colour = __webpack_require__(/*! color */ 2423);
	
	//*------------------------------------------------------------------*
	
	//*------------------------------------------------------------------*
	
	var highlightColour = function highlightColour(c) {
	  return c.light() ? c.lighten(0.5) : c.saturate(0.3).darken(0.5);
	};
	
	var dataClassesFromSeries = function dataClassesFromSeries(dataSeries) {
	  validateDataSeries(dataSeries);
	  var xs = dataSeries.map(function (series) {
	    return series.data.length === 0 && series.name === "Below cutoff" ? {
	      data: [{ value: 0.0 }],
	      colour: series.colour
	    } : series;
	  }).filter(function (series) {
	    return series.data.length > 0;
	  }).map(function (series, ix, self) {
	    var theseSeriesValuesSorted = series.data.map(function (point) {
	      return point.value;
	    });
	    theseSeriesValuesSorted.sort(function (l, r) {
	      return l - r;
	    });
	    return {
	      min: theseSeriesValuesSorted[0],
	      minColour: ix == 0 ? highlightColour(Colour(self[ix].colour)) : Colour(self[ix - 1].colour),
	      max: theseSeriesValuesSorted[theseSeriesValuesSorted.length - 1],
	      maxColour: ix == self.length - 1 ? highlightColour(Colour(self[ix].colour)) : Colour(self[ix + 1].colour),
	      median: theseSeriesValuesSorted[Math.floor(series.data.length / 2)],
	      medianColour: Colour(self[ix].colour),
	      sortedValues: theseSeriesValuesSorted
	    };
	  });
	  var needToSplit = function needToSplit(x) {
	    return x.sortedValues.length > 3 && x.sortedValues[0] != x.sortedValues[x.sortedValues.length - 1] && x.minColour.rgb() !== x.maxColour.rgb();
	  };
	
	  var splitInHalf = function splitInHalf(x) {
	    return [{
	      min: x.min,
	      minColour: x.minColour,
	      max: x.median,
	      maxColour: x.medianColour,
	      median: x.sortedValues[Math.floor(x.sortedValues.length / 4)],
	      medianColour: x.minColour.mix(x.medianColour),
	      sortedValues: x.sortedValues.slice(0, Math.floor(x.sortedValues.length / 2))
	    }, {
	      min: x.median,
	      minColour: x.medianColour,
	      max: x.max,
	      maxColour: x.maxColour,
	      median: x.sortedValues[Math.floor(3 * x.sortedValues.length / 4)],
	      medianColour: x.medianColour.mix(x.maxColour),
	      sortedValues: x.sortedValues.slice(Math.floor(x.sortedValues.length / 2))
	    }];
	  };
	  var l = Number.MIN_VALUE;
	  var L = xs.length;
	  while (l < L) {
	    xs = [].concat.apply([], xs.map(function (x) {
	      if (needToSplit(x)) {
	        return splitInHalf(x);
	      } else {
	        return [x];
	      }
	    }));
	    l = L;
	    L = xs.length;
	  }
	
	  return xs.map(function (x) {
	    return {
	      from: x.min,
	      to: x.max,
	      color: x.medianColour.hexString()
	    };
	  });
	};
	
	var getColorAxisFromDataSeries = function getColorAxisFromDataSeries(dataSeries) {
	  return {
	    dataClasses: dataClassesFromSeries(dataSeries)
	  };
	};
	
	module.exports = getColorAxisFromDataSeries;

/***/ },

/***/ 2423:
/*!**************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/color/index.js ***!
  \**************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };
	
	/* MIT license */
	var _clone = __webpack_require__(/*! clone */ 2424);
	var convert = __webpack_require__(/*! color-convert */ 2425);
	var string = __webpack_require__(/*! color-string */ 2429);
	
	var Color = function Color(obj) {
		if (obj instanceof Color) {
			return obj;
		}
		if (!(this instanceof Color)) {
			return new Color(obj);
		}
	
		this.values = {
			rgb: [0, 0, 0],
			hsl: [0, 0, 0],
			hsv: [0, 0, 0],
			hwb: [0, 0, 0],
			cmyk: [0, 0, 0, 0],
			alpha: 1
		};
	
		// parse Color() argument
		var vals;
		if (typeof obj === 'string') {
			vals = string.getRgba(obj);
			if (vals) {
				this.setValues('rgb', vals);
			} else if (vals = string.getHsla(obj)) {
				this.setValues('hsl', vals);
			} else if (vals = string.getHwb(obj)) {
				this.setValues('hwb', vals);
			} else {
				throw new Error('Unable to parse color from string "' + obj + '"');
			}
		} else if ((typeof obj === 'undefined' ? 'undefined' : _typeof(obj)) === 'object') {
			vals = obj;
			if (vals.r !== undefined || vals.red !== undefined) {
				this.setValues('rgb', vals);
			} else if (vals.l !== undefined || vals.lightness !== undefined) {
				this.setValues('hsl', vals);
			} else if (vals.v !== undefined || vals.value !== undefined) {
				this.setValues('hsv', vals);
			} else if (vals.w !== undefined || vals.whiteness !== undefined) {
				this.setValues('hwb', vals);
			} else if (vals.c !== undefined || vals.cyan !== undefined) {
				this.setValues('cmyk', vals);
			} else {
				throw new Error('Unable to parse color from object ' + JSON.stringify(obj));
			}
		}
	};
	
	Color.prototype = {
		rgb: function rgb() {
			return this.setSpace('rgb', arguments);
		},
		hsl: function hsl() {
			return this.setSpace('hsl', arguments);
		},
		hsv: function hsv() {
			return this.setSpace('hsv', arguments);
		},
		hwb: function hwb() {
			return this.setSpace('hwb', arguments);
		},
		cmyk: function cmyk() {
			return this.setSpace('cmyk', arguments);
		},
	
		rgbArray: function rgbArray() {
			return this.values.rgb;
		},
		hslArray: function hslArray() {
			return this.values.hsl;
		},
		hsvArray: function hsvArray() {
			return this.values.hsv;
		},
		hwbArray: function hwbArray() {
			if (this.values.alpha !== 1) {
				return this.values.hwb.concat([this.values.alpha]);
			}
			return this.values.hwb;
		},
		cmykArray: function cmykArray() {
			return this.values.cmyk;
		},
		rgbaArray: function rgbaArray() {
			var rgb = this.values.rgb;
			return rgb.concat([this.values.alpha]);
		},
		hslaArray: function hslaArray() {
			var hsl = this.values.hsl;
			return hsl.concat([this.values.alpha]);
		},
		alpha: function alpha(val) {
			if (val === undefined) {
				return this.values.alpha;
			}
			this.setValues('alpha', val);
			return this;
		},
	
		red: function red(val) {
			return this.setChannel('rgb', 0, val);
		},
		green: function green(val) {
			return this.setChannel('rgb', 1, val);
		},
		blue: function blue(val) {
			return this.setChannel('rgb', 2, val);
		},
		hue: function hue(val) {
			if (val) {
				val %= 360;
				val = val < 0 ? 360 + val : val;
			}
			return this.setChannel('hsl', 0, val);
		},
		saturation: function saturation(val) {
			return this.setChannel('hsl', 1, val);
		},
		lightness: function lightness(val) {
			return this.setChannel('hsl', 2, val);
		},
		saturationv: function saturationv(val) {
			return this.setChannel('hsv', 1, val);
		},
		whiteness: function whiteness(val) {
			return this.setChannel('hwb', 1, val);
		},
		blackness: function blackness(val) {
			return this.setChannel('hwb', 2, val);
		},
		value: function value(val) {
			return this.setChannel('hsv', 2, val);
		},
		cyan: function cyan(val) {
			return this.setChannel('cmyk', 0, val);
		},
		magenta: function magenta(val) {
			return this.setChannel('cmyk', 1, val);
		},
		yellow: function yellow(val) {
			return this.setChannel('cmyk', 2, val);
		},
		black: function black(val) {
			return this.setChannel('cmyk', 3, val);
		},
	
		hexString: function hexString() {
			return string.hexString(this.values.rgb);
		},
		rgbString: function rgbString() {
			return string.rgbString(this.values.rgb, this.values.alpha);
		},
		rgbaString: function rgbaString() {
			return string.rgbaString(this.values.rgb, this.values.alpha);
		},
		percentString: function percentString() {
			return string.percentString(this.values.rgb, this.values.alpha);
		},
		hslString: function hslString() {
			return string.hslString(this.values.hsl, this.values.alpha);
		},
		hslaString: function hslaString() {
			return string.hslaString(this.values.hsl, this.values.alpha);
		},
		hwbString: function hwbString() {
			return string.hwbString(this.values.hwb, this.values.alpha);
		},
		keyword: function keyword() {
			return string.keyword(this.values.rgb, this.values.alpha);
		},
	
		rgbNumber: function rgbNumber() {
			return this.values.rgb[0] << 16 | this.values.rgb[1] << 8 | this.values.rgb[2];
		},
	
		luminosity: function luminosity() {
			// http://www.w3.org/TR/WCAG20/#relativeluminancedef
			var rgb = this.values.rgb;
			var lum = [];
			for (var i = 0; i < rgb.length; i++) {
				var chan = rgb[i] / 255;
				lum[i] = chan <= 0.03928 ? chan / 12.92 : Math.pow((chan + 0.055) / 1.055, 2.4);
			}
			return 0.2126 * lum[0] + 0.7152 * lum[1] + 0.0722 * lum[2];
		},
	
		contrast: function contrast(color2) {
			// http://www.w3.org/TR/WCAG20/#contrast-ratiodef
			var lum1 = this.luminosity();
			var lum2 = color2.luminosity();
			if (lum1 > lum2) {
				return (lum1 + 0.05) / (lum2 + 0.05);
			}
			return (lum2 + 0.05) / (lum1 + 0.05);
		},
	
		level: function level(color2) {
			var contrastRatio = this.contrast(color2);
			if (contrastRatio >= 7.1) {
				return 'AAA';
			}
	
			return contrastRatio >= 4.5 ? 'AA' : '';
		},
	
		dark: function dark() {
			// YIQ equation from http://24ways.org/2010/calculating-color-contrast
			var rgb = this.values.rgb;
			var yiq = (rgb[0] * 299 + rgb[1] * 587 + rgb[2] * 114) / 1000;
			return yiq < 128;
		},
	
		light: function light() {
			return !this.dark();
		},
	
		negate: function negate() {
			var rgb = [];
			for (var i = 0; i < 3; i++) {
				rgb[i] = 255 - this.values.rgb[i];
			}
			this.setValues('rgb', rgb);
			return this;
		},
	
		lighten: function lighten(ratio) {
			this.values.hsl[2] += this.values.hsl[2] * ratio;
			this.setValues('hsl', this.values.hsl);
			return this;
		},
	
		darken: function darken(ratio) {
			this.values.hsl[2] -= this.values.hsl[2] * ratio;
			this.setValues('hsl', this.values.hsl);
			return this;
		},
	
		saturate: function saturate(ratio) {
			this.values.hsl[1] += this.values.hsl[1] * ratio;
			this.setValues('hsl', this.values.hsl);
			return this;
		},
	
		desaturate: function desaturate(ratio) {
			this.values.hsl[1] -= this.values.hsl[1] * ratio;
			this.setValues('hsl', this.values.hsl);
			return this;
		},
	
		whiten: function whiten(ratio) {
			this.values.hwb[1] += this.values.hwb[1] * ratio;
			this.setValues('hwb', this.values.hwb);
			return this;
		},
	
		blacken: function blacken(ratio) {
			this.values.hwb[2] += this.values.hwb[2] * ratio;
			this.setValues('hwb', this.values.hwb);
			return this;
		},
	
		greyscale: function greyscale() {
			var rgb = this.values.rgb;
			// http://en.wikipedia.org/wiki/Grayscale#Converting_color_to_grayscale
			var val = rgb[0] * 0.3 + rgb[1] * 0.59 + rgb[2] * 0.11;
			this.setValues('rgb', [val, val, val]);
			return this;
		},
	
		clearer: function clearer(ratio) {
			this.setValues('alpha', this.values.alpha - this.values.alpha * ratio);
			return this;
		},
	
		opaquer: function opaquer(ratio) {
			this.setValues('alpha', this.values.alpha + this.values.alpha * ratio);
			return this;
		},
	
		rotate: function rotate(degrees) {
			var hue = this.values.hsl[0];
			hue = (hue + degrees) % 360;
			hue = hue < 0 ? 360 + hue : hue;
			this.values.hsl[0] = hue;
			this.setValues('hsl', this.values.hsl);
			return this;
		},
	
		/**
	  * Ported from sass implementation in C
	  * https://github.com/sass/libsass/blob/0e6b4a2850092356aa3ece07c6b249f0221caced/functions.cpp#L209
	  */
		mix: function mix(mixinColor, weight) {
			var color1 = this;
			var color2 = mixinColor;
			var p = weight === undefined ? 0.5 : weight;
	
			var w = 2 * p - 1;
			var a = color1.alpha() - color2.alpha();
	
			var w1 = ((w * a === -1 ? w : (w + a) / (1 + w * a)) + 1) / 2.0;
			var w2 = 1 - w1;
	
			return this.rgb(w1 * color1.red() + w2 * color2.red(), w1 * color1.green() + w2 * color2.green(), w1 * color1.blue() + w2 * color2.blue()).alpha(color1.alpha() * p + color2.alpha() * (1 - p));
		},
	
		toJSON: function toJSON() {
			return this.rgb();
		},
	
		clone: function clone() {
			var col = new Color();
			col.values = _clone(this.values);
			return col;
		}
	};
	
	Color.prototype.getValues = function (space) {
		var vals = {};
	
		for (var i = 0; i < space.length; i++) {
			vals[space.charAt(i)] = this.values[space][i];
		}
	
		if (this.values.alpha !== 1) {
			vals.a = this.values.alpha;
		}
	
		// {r: 255, g: 255, b: 255, a: 0.4}
		return vals;
	};
	
	Color.prototype.setValues = function (space, vals) {
		var spaces = {
			rgb: ['red', 'green', 'blue'],
			hsl: ['hue', 'saturation', 'lightness'],
			hsv: ['hue', 'saturation', 'value'],
			hwb: ['hue', 'whiteness', 'blackness'],
			cmyk: ['cyan', 'magenta', 'yellow', 'black']
		};
	
		var maxes = {
			rgb: [255, 255, 255],
			hsl: [360, 100, 100],
			hsv: [360, 100, 100],
			hwb: [360, 100, 100],
			cmyk: [100, 100, 100, 100]
		};
	
		var i;
		var alpha = 1;
		if (space === 'alpha') {
			alpha = vals;
		} else if (vals.length) {
			// [10, 10, 10]
			this.values[space] = vals.slice(0, space.length);
			alpha = vals[space.length];
		} else if (vals[space.charAt(0)] !== undefined) {
			// {r: 10, g: 10, b: 10}
			for (i = 0; i < space.length; i++) {
				this.values[space][i] = vals[space.charAt(i)];
			}
	
			alpha = vals.a;
		} else if (vals[spaces[space][0]] !== undefined) {
			// {red: 10, green: 10, blue: 10}
			var chans = spaces[space];
	
			for (i = 0; i < space.length; i++) {
				this.values[space][i] = vals[chans[i]];
			}
	
			alpha = vals.alpha;
		}
	
		this.values.alpha = Math.max(0, Math.min(1, alpha === undefined ? this.values.alpha : alpha));
	
		if (space === 'alpha') {
			return false;
		}
	
		var capped;
	
		// cap values of the space prior converting all values
		for (i = 0; i < space.length; i++) {
			capped = Math.max(0, Math.min(maxes[space][i], this.values[space][i]));
			this.values[space][i] = Math.round(capped);
		}
	
		// convert to all the other color spaces
		for (var sname in spaces) {
			if (sname !== space) {
				this.values[sname] = convert[space][sname](this.values[space]);
			}
	
			// cap values
			for (i = 0; i < sname.length; i++) {
				capped = Math.max(0, Math.min(maxes[sname][i], this.values[sname][i]));
				this.values[sname][i] = Math.round(capped);
			}
		}
	
		return true;
	};
	
	Color.prototype.setSpace = function (space, args) {
		var vals = args[0];
	
		if (vals === undefined) {
			// color.rgb()
			return this.getValues(space);
		}
	
		// color.rgb(10, 10, 10)
		if (typeof vals === 'number') {
			vals = Array.prototype.slice.call(args);
		}
	
		this.setValues(space, vals);
		return this;
	};
	
	Color.prototype.setChannel = function (space, index, val) {
		if (val === undefined) {
			// color.red()
			return this.values[space][index];
		} else if (val === this.values[space][index]) {
			// color.red(color.red())
			return this;
		}
	
		// color.red(100)
		this.values[space][index] = val;
		this.setValues(space, this.values[space]);
	
		return this;
	};
	
	module.exports = Color;

/***/ },

/***/ 2424:
/*!**********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/color/~/clone/clone.js ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(Buffer, module) {'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };
	
	var clone = function () {
	  'use strict';
	
	  /**
	   * Clones (copies) an Object using deep copying.
	   *
	   * This function supports circular references by default, but if you are certain
	   * there are no circular references in your object, you can save some CPU time
	   * by calling clone(obj, false).
	   *
	   * Caution: if `circular` is false and `parent` contains circular references,
	   * your program may enter an infinite loop and crash.
	   *
	   * @param `parent` - the object to be cloned
	   * @param `circular` - set to true if the object to be cloned may contain
	   *    circular references. (optional - true by default)
	   * @param `depth` - set to a number if the object is only to be cloned to
	   *    a particular depth. (optional - defaults to Infinity)
	   * @param `prototype` - sets the prototype to be used when cloning an object.
	   *    (optional - defaults to parent prototype).
	  */
	
	  function clone(parent, circular, depth, prototype) {
	    var filter;
	    if ((typeof circular === 'undefined' ? 'undefined' : _typeof(circular)) === 'object') {
	      depth = circular.depth;
	      prototype = circular.prototype;
	      filter = circular.filter;
	      circular = circular.circular;
	    }
	    // maintain two arrays for circular references, where corresponding parents
	    // and children have the same index
	    var allParents = [];
	    var allChildren = [];
	
	    var useBuffer = typeof Buffer != 'undefined';
	
	    if (typeof circular == 'undefined') circular = true;
	
	    if (typeof depth == 'undefined') depth = Infinity;
	
	    // recurse this function so we don't reset allParents and allChildren
	    function _clone(parent, depth) {
	      // cloning null always returns null
	      if (parent === null) return null;
	
	      if (depth == 0) return parent;
	
	      var child;
	      var proto;
	      if ((typeof parent === 'undefined' ? 'undefined' : _typeof(parent)) != 'object') {
	        return parent;
	      }
	
	      if (clone.__isArray(parent)) {
	        child = [];
	      } else if (clone.__isRegExp(parent)) {
	        child = new RegExp(parent.source, __getRegExpFlags(parent));
	        if (parent.lastIndex) child.lastIndex = parent.lastIndex;
	      } else if (clone.__isDate(parent)) {
	        child = new Date(parent.getTime());
	      } else if (useBuffer && Buffer.isBuffer(parent)) {
	        child = new Buffer(parent.length);
	        parent.copy(child);
	        return child;
	      } else {
	        if (typeof prototype == 'undefined') {
	          proto = Object.getPrototypeOf(parent);
	          child = Object.create(proto);
	        } else {
	          child = Object.create(prototype);
	          proto = prototype;
	        }
	      }
	
	      if (circular) {
	        var index = allParents.indexOf(parent);
	
	        if (index != -1) {
	          return allChildren[index];
	        }
	        allParents.push(parent);
	        allChildren.push(child);
	      }
	
	      for (var i in parent) {
	        var attrs;
	        if (proto) {
	          attrs = Object.getOwnPropertyDescriptor(proto, i);
	        }
	
	        if (attrs && attrs.set == null) {
	          continue;
	        }
	        child[i] = _clone(parent[i], depth - 1);
	      }
	
	      return child;
	    }
	
	    return _clone(parent, depth);
	  }
	
	  /**
	   * Simple flat clone using prototype, accepts only objects, usefull for property
	   * override on FLAT configuration object (no nested props).
	   *
	   * USE WITH CAUTION! This may not behave as you wish if you do not know how this
	   * works.
	   */
	  clone.clonePrototype = function clonePrototype(parent) {
	    if (parent === null) return null;
	
	    var c = function c() {};
	    c.prototype = parent;
	    return new c();
	  };
	
	  // private utility functions
	
	  function __objToStr(o) {
	    return Object.prototype.toString.call(o);
	  };
	  clone.__objToStr = __objToStr;
	
	  function __isDate(o) {
	    return (typeof o === 'undefined' ? 'undefined' : _typeof(o)) === 'object' && __objToStr(o) === '[object Date]';
	  };
	  clone.__isDate = __isDate;
	
	  function __isArray(o) {
	    return (typeof o === 'undefined' ? 'undefined' : _typeof(o)) === 'object' && __objToStr(o) === '[object Array]';
	  };
	  clone.__isArray = __isArray;
	
	  function __isRegExp(o) {
	    return (typeof o === 'undefined' ? 'undefined' : _typeof(o)) === 'object' && __objToStr(o) === '[object RegExp]';
	  };
	  clone.__isRegExp = __isRegExp;
	
	  function __getRegExpFlags(re) {
	    var flags = '';
	    if (re.global) flags += 'g';
	    if (re.ignoreCase) flags += 'i';
	    if (re.multiline) flags += 'm';
	    return flags;
	  };
	  clone.__getRegExpFlags = __getRegExpFlags;
	
	  return clone;
	}();
	
	if (( false ? 'undefined' : _typeof(module)) === 'object' && module.exports) {
	  module.exports = clone;
	}
	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! (webpack)/~/node-libs-browser/~/buffer/index.js */ 1646).Buffer, __webpack_require__(/*! ./../../../../../~/webpack/buildin/module.js */ 2268)(module)))

/***/ },

/***/ 2425:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/color/~/color-convert/index.js ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };
	
	var conversions = __webpack_require__(/*! ./conversions */ 2426);
	var route = __webpack_require__(/*! ./route */ 2428);
	
	var convert = {};
	
	var models = Object.keys(conversions);
	
	function wrapRaw(fn) {
		var wrappedFn = function wrappedFn(args) {
			if (args === undefined || args === null) {
				return args;
			}
	
			if (arguments.length > 1) {
				args = Array.prototype.slice.call(arguments);
			}
	
			return fn(args);
		};
	
		// preserve .conversion property if there is one
		if ('conversion' in fn) {
			wrappedFn.conversion = fn.conversion;
		}
	
		return wrappedFn;
	}
	
	function wrapRounded(fn) {
		var wrappedFn = function wrappedFn(args) {
			if (args === undefined || args === null) {
				return args;
			}
	
			if (arguments.length > 1) {
				args = Array.prototype.slice.call(arguments);
			}
	
			var result = fn(args);
	
			// we're assuming the result is an array here.
			// see notice in conversions.js; don't use box types
			// in conversion functions.
			if ((typeof result === 'undefined' ? 'undefined' : _typeof(result)) === 'object') {
				for (var len = result.length, i = 0; i < len; i++) {
					result[i] = Math.round(result[i]);
				}
			}
	
			return result;
		};
	
		// preserve .conversion property if there is one
		if ('conversion' in fn) {
			wrappedFn.conversion = fn.conversion;
		}
	
		return wrappedFn;
	}
	
	models.forEach(function (fromModel) {
		convert[fromModel] = {};
	
		Object.defineProperty(convert[fromModel], 'channels', { value: conversions[fromModel].channels });
	
		var routes = route(fromModel);
		var routeModels = Object.keys(routes);
	
		routeModels.forEach(function (toModel) {
			var fn = routes[toModel];
	
			convert[fromModel][toModel] = wrapRounded(fn);
			convert[fromModel][toModel].raw = wrapRaw(fn);
		});
	});
	
	module.exports = convert;

/***/ },

/***/ 2426:
/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/color/~/color-convert/conversions.js ***!
  \************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	/* MIT license */
	var cssKeywords = __webpack_require__(/*! ./css-keywords */ 2427);
	
	// NOTE: conversions should only return primitive values (i.e. arrays, or
	//       values that give correct `typeof` results).
	//       do not use box values types (i.e. Number(), String(), etc.)
	
	var reverseKeywords = {};
	for (var key in cssKeywords) {
		if (cssKeywords.hasOwnProperty(key)) {
			reverseKeywords[cssKeywords[key].join()] = key;
		}
	}
	
	var convert = module.exports = {
		rgb: { channels: 3 },
		hsl: { channels: 3 },
		hsv: { channels: 3 },
		hwb: { channels: 3 },
		cmyk: { channels: 4 },
		xyz: { channels: 3 },
		lab: { channels: 3 },
		lch: { channels: 3 },
		hex: { channels: 1 },
		keyword: { channels: 1 },
		ansi16: { channels: 1 },
		ansi256: { channels: 1 },
		hcg: { channels: 3 },
		apple: { channels: 3 }
	};
	
	// hide .channels property
	for (var model in convert) {
		if (convert.hasOwnProperty(model)) {
			if (!('channels' in convert[model])) {
				throw new Error('missing channels property: ' + model);
			}
	
			var channels = convert[model].channels;
			delete convert[model].channels;
			Object.defineProperty(convert[model], 'channels', { value: channels });
		}
	}
	
	convert.rgb.hsl = function (rgb) {
		var r = rgb[0] / 255;
		var g = rgb[1] / 255;
		var b = rgb[2] / 255;
		var min = Math.min(r, g, b);
		var max = Math.max(r, g, b);
		var delta = max - min;
		var h;
		var s;
		var l;
	
		if (max === min) {
			h = 0;
		} else if (r === max) {
			h = (g - b) / delta;
		} else if (g === max) {
			h = 2 + (b - r) / delta;
		} else if (b === max) {
			h = 4 + (r - g) / delta;
		}
	
		h = Math.min(h * 60, 360);
	
		if (h < 0) {
			h += 360;
		}
	
		l = (min + max) / 2;
	
		if (max === min) {
			s = 0;
		} else if (l <= 0.5) {
			s = delta / (max + min);
		} else {
			s = delta / (2 - max - min);
		}
	
		return [h, s * 100, l * 100];
	};
	
	convert.rgb.hsv = function (rgb) {
		var r = rgb[0];
		var g = rgb[1];
		var b = rgb[2];
		var min = Math.min(r, g, b);
		var max = Math.max(r, g, b);
		var delta = max - min;
		var h;
		var s;
		var v;
	
		if (max === 0) {
			s = 0;
		} else {
			s = delta / max * 1000 / 10;
		}
	
		if (max === min) {
			h = 0;
		} else if (r === max) {
			h = (g - b) / delta;
		} else if (g === max) {
			h = 2 + (b - r) / delta;
		} else if (b === max) {
			h = 4 + (r - g) / delta;
		}
	
		h = Math.min(h * 60, 360);
	
		if (h < 0) {
			h += 360;
		}
	
		v = max / 255 * 1000 / 10;
	
		return [h, s, v];
	};
	
	convert.rgb.hwb = function (rgb) {
		var r = rgb[0];
		var g = rgb[1];
		var b = rgb[2];
		var h = convert.rgb.hsl(rgb)[0];
		var w = 1 / 255 * Math.min(r, Math.min(g, b));
	
		b = 1 - 1 / 255 * Math.max(r, Math.max(g, b));
	
		return [h, w * 100, b * 100];
	};
	
	convert.rgb.cmyk = function (rgb) {
		var r = rgb[0] / 255;
		var g = rgb[1] / 255;
		var b = rgb[2] / 255;
		var c;
		var m;
		var y;
		var k;
	
		k = Math.min(1 - r, 1 - g, 1 - b);
		c = (1 - r - k) / (1 - k) || 0;
		m = (1 - g - k) / (1 - k) || 0;
		y = (1 - b - k) / (1 - k) || 0;
	
		return [c * 100, m * 100, y * 100, k * 100];
	};
	
	convert.rgb.keyword = function (rgb) {
		return reverseKeywords[rgb.join()];
	};
	
	convert.keyword.rgb = function (keyword) {
		return cssKeywords[keyword];
	};
	
	convert.rgb.xyz = function (rgb) {
		var r = rgb[0] / 255;
		var g = rgb[1] / 255;
		var b = rgb[2] / 255;
	
		// assume sRGB
		r = r > 0.04045 ? Math.pow((r + 0.055) / 1.055, 2.4) : r / 12.92;
		g = g > 0.04045 ? Math.pow((g + 0.055) / 1.055, 2.4) : g / 12.92;
		b = b > 0.04045 ? Math.pow((b + 0.055) / 1.055, 2.4) : b / 12.92;
	
		var x = r * 0.4124 + g * 0.3576 + b * 0.1805;
		var y = r * 0.2126 + g * 0.7152 + b * 0.0722;
		var z = r * 0.0193 + g * 0.1192 + b * 0.9505;
	
		return [x * 100, y * 100, z * 100];
	};
	
	convert.rgb.lab = function (rgb) {
		var xyz = convert.rgb.xyz(rgb);
		var x = xyz[0];
		var y = xyz[1];
		var z = xyz[2];
		var l;
		var a;
		var b;
	
		x /= 95.047;
		y /= 100;
		z /= 108.883;
	
		x = x > 0.008856 ? Math.pow(x, 1 / 3) : 7.787 * x + 16 / 116;
		y = y > 0.008856 ? Math.pow(y, 1 / 3) : 7.787 * y + 16 / 116;
		z = z > 0.008856 ? Math.pow(z, 1 / 3) : 7.787 * z + 16 / 116;
	
		l = 116 * y - 16;
		a = 500 * (x - y);
		b = 200 * (y - z);
	
		return [l, a, b];
	};
	
	convert.hsl.rgb = function (hsl) {
		var h = hsl[0] / 360;
		var s = hsl[1] / 100;
		var l = hsl[2] / 100;
		var t1;
		var t2;
		var t3;
		var rgb;
		var val;
	
		if (s === 0) {
			val = l * 255;
			return [val, val, val];
		}
	
		if (l < 0.5) {
			t2 = l * (1 + s);
		} else {
			t2 = l + s - l * s;
		}
	
		t1 = 2 * l - t2;
	
		rgb = [0, 0, 0];
		for (var i = 0; i < 3; i++) {
			t3 = h + 1 / 3 * -(i - 1);
			if (t3 < 0) {
				t3++;
			}
			if (t3 > 1) {
				t3--;
			}
	
			if (6 * t3 < 1) {
				val = t1 + (t2 - t1) * 6 * t3;
			} else if (2 * t3 < 1) {
				val = t2;
			} else if (3 * t3 < 2) {
				val = t1 + (t2 - t1) * (2 / 3 - t3) * 6;
			} else {
				val = t1;
			}
	
			rgb[i] = val * 255;
		}
	
		return rgb;
	};
	
	convert.hsl.hsv = function (hsl) {
		var h = hsl[0];
		var s = hsl[1] / 100;
		var l = hsl[2] / 100;
		var smin = s;
		var lmin = Math.max(l, 0.01);
		var sv;
		var v;
	
		l *= 2;
		s *= l <= 1 ? l : 2 - l;
		smin *= lmin <= 1 ? lmin : 2 - lmin;
		v = (l + s) / 2;
		sv = l === 0 ? 2 * smin / (lmin + smin) : 2 * s / (l + s);
	
		return [h, sv * 100, v * 100];
	};
	
	convert.hsv.rgb = function (hsv) {
		var h = hsv[0] / 60;
		var s = hsv[1] / 100;
		var v = hsv[2] / 100;
		var hi = Math.floor(h) % 6;
	
		var f = h - Math.floor(h);
		var p = 255 * v * (1 - s);
		var q = 255 * v * (1 - s * f);
		var t = 255 * v * (1 - s * (1 - f));
		v *= 255;
	
		switch (hi) {
			case 0:
				return [v, t, p];
			case 1:
				return [q, v, p];
			case 2:
				return [p, v, t];
			case 3:
				return [p, q, v];
			case 4:
				return [t, p, v];
			case 5:
				return [v, p, q];
		}
	};
	
	convert.hsv.hsl = function (hsv) {
		var h = hsv[0];
		var s = hsv[1] / 100;
		var v = hsv[2] / 100;
		var vmin = Math.max(v, 0.01);
		var lmin;
		var sl;
		var l;
	
		l = (2 - s) * v;
		lmin = (2 - s) * vmin;
		sl = s * vmin;
		sl /= lmin <= 1 ? lmin : 2 - lmin;
		sl = sl || 0;
		l /= 2;
	
		return [h, sl * 100, l * 100];
	};
	
	// http://dev.w3.org/csswg/css-color/#hwb-to-rgb
	convert.hwb.rgb = function (hwb) {
		var h = hwb[0] / 360;
		var wh = hwb[1] / 100;
		var bl = hwb[2] / 100;
		var ratio = wh + bl;
		var i;
		var v;
		var f;
		var n;
	
		// wh + bl cant be > 1
		if (ratio > 1) {
			wh /= ratio;
			bl /= ratio;
		}
	
		i = Math.floor(6 * h);
		v = 1 - bl;
		f = 6 * h - i;
	
		if ((i & 0x01) !== 0) {
			f = 1 - f;
		}
	
		n = wh + f * (v - wh); // linear interpolation
	
		var r;
		var g;
		var b;
		switch (i) {
			default:
			case 6:
			case 0:
				r = v;g = n;b = wh;break;
			case 1:
				r = n;g = v;b = wh;break;
			case 2:
				r = wh;g = v;b = n;break;
			case 3:
				r = wh;g = n;b = v;break;
			case 4:
				r = n;g = wh;b = v;break;
			case 5:
				r = v;g = wh;b = n;break;
		}
	
		return [r * 255, g * 255, b * 255];
	};
	
	convert.cmyk.rgb = function (cmyk) {
		var c = cmyk[0] / 100;
		var m = cmyk[1] / 100;
		var y = cmyk[2] / 100;
		var k = cmyk[3] / 100;
		var r;
		var g;
		var b;
	
		r = 1 - Math.min(1, c * (1 - k) + k);
		g = 1 - Math.min(1, m * (1 - k) + k);
		b = 1 - Math.min(1, y * (1 - k) + k);
	
		return [r * 255, g * 255, b * 255];
	};
	
	convert.xyz.rgb = function (xyz) {
		var x = xyz[0] / 100;
		var y = xyz[1] / 100;
		var z = xyz[2] / 100;
		var r;
		var g;
		var b;
	
		r = x * 3.2406 + y * -1.5372 + z * -0.4986;
		g = x * -0.9689 + y * 1.8758 + z * 0.0415;
		b = x * 0.0557 + y * -0.2040 + z * 1.0570;
	
		// assume sRGB
		r = r > 0.0031308 ? 1.055 * Math.pow(r, 1.0 / 2.4) - 0.055 : r * 12.92;
	
		g = g > 0.0031308 ? 1.055 * Math.pow(g, 1.0 / 2.4) - 0.055 : g * 12.92;
	
		b = b > 0.0031308 ? 1.055 * Math.pow(b, 1.0 / 2.4) - 0.055 : b * 12.92;
	
		r = Math.min(Math.max(0, r), 1);
		g = Math.min(Math.max(0, g), 1);
		b = Math.min(Math.max(0, b), 1);
	
		return [r * 255, g * 255, b * 255];
	};
	
	convert.xyz.lab = function (xyz) {
		var x = xyz[0];
		var y = xyz[1];
		var z = xyz[2];
		var l;
		var a;
		var b;
	
		x /= 95.047;
		y /= 100;
		z /= 108.883;
	
		x = x > 0.008856 ? Math.pow(x, 1 / 3) : 7.787 * x + 16 / 116;
		y = y > 0.008856 ? Math.pow(y, 1 / 3) : 7.787 * y + 16 / 116;
		z = z > 0.008856 ? Math.pow(z, 1 / 3) : 7.787 * z + 16 / 116;
	
		l = 116 * y - 16;
		a = 500 * (x - y);
		b = 200 * (y - z);
	
		return [l, a, b];
	};
	
	convert.lab.xyz = function (lab) {
		var l = lab[0];
		var a = lab[1];
		var b = lab[2];
		var x;
		var y;
		var z;
	
		y = (l + 16) / 116;
		x = a / 500 + y;
		z = y - b / 200;
	
		var y2 = Math.pow(y, 3);
		var x2 = Math.pow(x, 3);
		var z2 = Math.pow(z, 3);
		y = y2 > 0.008856 ? y2 : (y - 16 / 116) / 7.787;
		x = x2 > 0.008856 ? x2 : (x - 16 / 116) / 7.787;
		z = z2 > 0.008856 ? z2 : (z - 16 / 116) / 7.787;
	
		x *= 95.047;
		y *= 100;
		z *= 108.883;
	
		return [x, y, z];
	};
	
	convert.lab.lch = function (lab) {
		var l = lab[0];
		var a = lab[1];
		var b = lab[2];
		var hr;
		var h;
		var c;
	
		hr = Math.atan2(b, a);
		h = hr * 360 / 2 / Math.PI;
	
		if (h < 0) {
			h += 360;
		}
	
		c = Math.sqrt(a * a + b * b);
	
		return [l, c, h];
	};
	
	convert.lch.lab = function (lch) {
		var l = lch[0];
		var c = lch[1];
		var h = lch[2];
		var a;
		var b;
		var hr;
	
		hr = h / 360 * 2 * Math.PI;
		a = c * Math.cos(hr);
		b = c * Math.sin(hr);
	
		return [l, a, b];
	};
	
	convert.rgb.ansi16 = function (args) {
		var r = args[0];
		var g = args[1];
		var b = args[2];
		var value = 1 in arguments ? arguments[1] : convert.rgb.hsv(args)[2]; // hsv -> ansi16 optimization
	
		value = Math.round(value / 50);
	
		if (value === 0) {
			return 30;
		}
	
		var ansi = 30 + (Math.round(b / 255) << 2 | Math.round(g / 255) << 1 | Math.round(r / 255));
	
		if (value === 2) {
			ansi += 60;
		}
	
		return ansi;
	};
	
	convert.hsv.ansi16 = function (args) {
		// optimization here; we already know the value and don't need to get
		// it converted for us.
		return convert.rgb.ansi16(convert.hsv.rgb(args), args[2]);
	};
	
	convert.rgb.ansi256 = function (args) {
		var r = args[0];
		var g = args[1];
		var b = args[2];
	
		// we use the extended greyscale palette here, with the exception of
		// black and white. normal palette only has 4 greyscale shades.
		if (r === g && g === b) {
			if (r < 8) {
				return 16;
			}
	
			if (r > 248) {
				return 231;
			}
	
			return Math.round((r - 8) / 247 * 24) + 232;
		}
	
		var ansi = 16 + 36 * Math.round(r / 255 * 5) + 6 * Math.round(g / 255 * 5) + Math.round(b / 255 * 5);
	
		return ansi;
	};
	
	convert.ansi16.rgb = function (args) {
		var color = args % 10;
	
		// handle greyscale
		if (color === 0 || color === 7) {
			if (args > 50) {
				color += 3.5;
			}
	
			color = color / 10.5 * 255;
	
			return [color, color, color];
		}
	
		var mult = (~~(args > 50) + 1) * 0.5;
		var r = (color & 1) * mult * 255;
		var g = (color >> 1 & 1) * mult * 255;
		var b = (color >> 2 & 1) * mult * 255;
	
		return [r, g, b];
	};
	
	convert.ansi256.rgb = function (args) {
		// handle greyscale
		if (args >= 232) {
			var c = (args - 232) * 10 + 8;
			return [c, c, c];
		}
	
		args -= 16;
	
		var rem;
		var r = Math.floor(args / 36) / 5 * 255;
		var g = Math.floor((rem = args % 36) / 6) / 5 * 255;
		var b = rem % 6 / 5 * 255;
	
		return [r, g, b];
	};
	
	convert.rgb.hex = function (args) {
		var integer = ((Math.round(args[0]) & 0xFF) << 16) + ((Math.round(args[1]) & 0xFF) << 8) + (Math.round(args[2]) & 0xFF);
	
		var string = integer.toString(16).toUpperCase();
		return '000000'.substring(string.length) + string;
	};
	
	convert.hex.rgb = function (args) {
		var match = args.toString(16).match(/[a-f0-9]{6}/i);
		if (!match) {
			return [0, 0, 0];
		}
	
		var integer = parseInt(match[0], 16);
		var r = integer >> 16 & 0xFF;
		var g = integer >> 8 & 0xFF;
		var b = integer & 0xFF;
	
		return [r, g, b];
	};
	
	convert.rgb.hcg = function (rgb) {
		var r = rgb[0] / 255;
		var g = rgb[1] / 255;
		var b = rgb[2] / 255;
		var max = Math.max(Math.max(r, g), b);
		var min = Math.min(Math.min(r, g), b);
		var chroma = max - min;
		var grayscale;
		var hue;
	
		if (chroma < 1) {
			grayscale = min / (1 - chroma);
		} else {
			grayscale = 0;
		}
	
		if (chroma <= 0) {
			hue = 0;
		} else if (max === r) {
			hue = (g - b) / chroma % 6;
		} else if (max === g) {
			hue = 2 + (b - r) / chroma;
		} else {
			hue = 4 + (r - g) / chroma + 4;
		}
	
		hue /= 6;
		hue %= 1;
	
		return [hue * 360, chroma * 100, grayscale * 100];
	};
	
	convert.hsl.hcg = function (hsl) {
		var s = hsl[1] / 100;
		var l = hsl[2] / 100;
		var c = 1;
		var f = 0;
	
		if (l < 0.5) {
			c = 2.0 * s * l;
		} else {
			c = 2.0 * s * (1.0 - l);
		}
	
		if (c < 1.0) {
			f = (l - 0.5 * c) / (1.0 - c);
		}
	
		return [hsl[0], c * 100, f * 100];
	};
	
	convert.hsv.hcg = function (hsv) {
		var s = hsv[1] / 100;
		var v = hsv[2] / 100;
	
		var c = s * v;
		var f = 0;
	
		if (c < 1.0) {
			f = (v - c) / (1 - c);
		}
	
		return [hsv[0], c * 100, f * 100];
	};
	
	convert.hcg.rgb = function (hcg) {
		var h = hcg[0] / 360;
		var c = hcg[1] / 100;
		var g = hcg[2] / 100;
	
		if (c === 0.0) {
			return [g * 255, g * 255, g * 255];
		}
	
		var pure = [0, 0, 0];
		var hi = h % 1 * 6;
		var v = hi % 1;
		var w = 1 - v;
		var mg = 0;
	
		switch (Math.floor(hi)) {
			case 0:
				pure[0] = 1;pure[1] = v;pure[2] = 0;break;
			case 1:
				pure[0] = w;pure[1] = 1;pure[2] = 0;break;
			case 2:
				pure[0] = 0;pure[1] = 1;pure[2] = v;break;
			case 3:
				pure[0] = 0;pure[1] = w;pure[2] = 1;break;
			case 4:
				pure[0] = v;pure[1] = 0;pure[2] = 1;break;
			default:
				pure[0] = 1;pure[1] = 0;pure[2] = w;
		}
	
		mg = (1.0 - c) * g;
	
		return [(c * pure[0] + mg) * 255, (c * pure[1] + mg) * 255, (c * pure[2] + mg) * 255];
	};
	
	convert.hcg.hsv = function (hcg) {
		var c = hcg[1] / 100;
		var g = hcg[2] / 100;
	
		var v = c + g * (1.0 - c);
		var f = 0;
	
		if (v > 0.0) {
			f = c / v;
		}
	
		return [hcg[0], f * 100, v * 100];
	};
	
	convert.hcg.hsl = function (hcg) {
		var c = hcg[1] / 100;
		var g = hcg[2] / 100;
	
		var l = g * (1.0 - c) + 0.5 * c;
		var s = 0;
	
		if (l > 0.0 && l < 0.5) {
			s = c / (2 * l);
		} else if (l >= 0.5 && l < 1.0) {
			s = c / (2 * (1 - l));
		}
	
		return [hcg[0], s * 100, l * 100];
	};
	
	convert.hcg.hwb = function (hcg) {
		var c = hcg[1] / 100;
		var g = hcg[2] / 100;
		var v = c + g * (1.0 - c);
		return [hcg[0], (v - c) * 100, (1 - v) * 100];
	};
	
	convert.hwb.hcg = function (hwb) {
		var w = hwb[1] / 100;
		var b = hwb[2] / 100;
		var v = 1 - b;
		var c = v - w;
		var g = 0;
	
		if (c < 1) {
			g = (v - c) / (1 - c);
		}
	
		return [hwb[0], c * 100, g * 100];
	};
	
	convert.apple.rgb = function (apple) {
		return [apple[0] / 65535 * 255, apple[1] / 65535 * 255, apple[2] / 65535 * 255];
	};
	
	convert.rgb.apple = function (rgb) {
		return [rgb[0] / 255 * 65535, rgb[1] / 255 * 65535, rgb[2] / 255 * 65535];
	};

/***/ },

/***/ 2427:
/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/color/~/color-convert/css-keywords.js ***!
  \*************************************************************************************/
/***/ function(module, exports) {

	"use strict";
	
	module.exports = {
		aliceblue: [240, 248, 255],
		antiquewhite: [250, 235, 215],
		aqua: [0, 255, 255],
		aquamarine: [127, 255, 212],
		azure: [240, 255, 255],
		beige: [245, 245, 220],
		bisque: [255, 228, 196],
		black: [0, 0, 0],
		blanchedalmond: [255, 235, 205],
		blue: [0, 0, 255],
		blueviolet: [138, 43, 226],
		brown: [165, 42, 42],
		burlywood: [222, 184, 135],
		cadetblue: [95, 158, 160],
		chartreuse: [127, 255, 0],
		chocolate: [210, 105, 30],
		coral: [255, 127, 80],
		cornflowerblue: [100, 149, 237],
		cornsilk: [255, 248, 220],
		crimson: [220, 20, 60],
		cyan: [0, 255, 255],
		darkblue: [0, 0, 139],
		darkcyan: [0, 139, 139],
		darkgoldenrod: [184, 134, 11],
		darkgray: [169, 169, 169],
		darkgreen: [0, 100, 0],
		darkgrey: [169, 169, 169],
		darkkhaki: [189, 183, 107],
		darkmagenta: [139, 0, 139],
		darkolivegreen: [85, 107, 47],
		darkorange: [255, 140, 0],
		darkorchid: [153, 50, 204],
		darkred: [139, 0, 0],
		darksalmon: [233, 150, 122],
		darkseagreen: [143, 188, 143],
		darkslateblue: [72, 61, 139],
		darkslategray: [47, 79, 79],
		darkslategrey: [47, 79, 79],
		darkturquoise: [0, 206, 209],
		darkviolet: [148, 0, 211],
		deeppink: [255, 20, 147],
		deepskyblue: [0, 191, 255],
		dimgray: [105, 105, 105],
		dimgrey: [105, 105, 105],
		dodgerblue: [30, 144, 255],
		firebrick: [178, 34, 34],
		floralwhite: [255, 250, 240],
		forestgreen: [34, 139, 34],
		fuchsia: [255, 0, 255],
		gainsboro: [220, 220, 220],
		ghostwhite: [248, 248, 255],
		gold: [255, 215, 0],
		goldenrod: [218, 165, 32],
		gray: [128, 128, 128],
		green: [0, 128, 0],
		greenyellow: [173, 255, 47],
		grey: [128, 128, 128],
		honeydew: [240, 255, 240],
		hotpink: [255, 105, 180],
		indianred: [205, 92, 92],
		indigo: [75, 0, 130],
		ivory: [255, 255, 240],
		khaki: [240, 230, 140],
		lavender: [230, 230, 250],
		lavenderblush: [255, 240, 245],
		lawngreen: [124, 252, 0],
		lemonchiffon: [255, 250, 205],
		lightblue: [173, 216, 230],
		lightcoral: [240, 128, 128],
		lightcyan: [224, 255, 255],
		lightgoldenrodyellow: [250, 250, 210],
		lightgray: [211, 211, 211],
		lightgreen: [144, 238, 144],
		lightgrey: [211, 211, 211],
		lightpink: [255, 182, 193],
		lightsalmon: [255, 160, 122],
		lightseagreen: [32, 178, 170],
		lightskyblue: [135, 206, 250],
		lightslategray: [119, 136, 153],
		lightslategrey: [119, 136, 153],
		lightsteelblue: [176, 196, 222],
		lightyellow: [255, 255, 224],
		lime: [0, 255, 0],
		limegreen: [50, 205, 50],
		linen: [250, 240, 230],
		magenta: [255, 0, 255],
		maroon: [128, 0, 0],
		mediumaquamarine: [102, 205, 170],
		mediumblue: [0, 0, 205],
		mediumorchid: [186, 85, 211],
		mediumpurple: [147, 112, 219],
		mediumseagreen: [60, 179, 113],
		mediumslateblue: [123, 104, 238],
		mediumspringgreen: [0, 250, 154],
		mediumturquoise: [72, 209, 204],
		mediumvioletred: [199, 21, 133],
		midnightblue: [25, 25, 112],
		mintcream: [245, 255, 250],
		mistyrose: [255, 228, 225],
		moccasin: [255, 228, 181],
		navajowhite: [255, 222, 173],
		navy: [0, 0, 128],
		oldlace: [253, 245, 230],
		olive: [128, 128, 0],
		olivedrab: [107, 142, 35],
		orange: [255, 165, 0],
		orangered: [255, 69, 0],
		orchid: [218, 112, 214],
		palegoldenrod: [238, 232, 170],
		palegreen: [152, 251, 152],
		paleturquoise: [175, 238, 238],
		palevioletred: [219, 112, 147],
		papayawhip: [255, 239, 213],
		peachpuff: [255, 218, 185],
		peru: [205, 133, 63],
		pink: [255, 192, 203],
		plum: [221, 160, 221],
		powderblue: [176, 224, 230],
		purple: [128, 0, 128],
		rebeccapurple: [102, 51, 153],
		red: [255, 0, 0],
		rosybrown: [188, 143, 143],
		royalblue: [65, 105, 225],
		saddlebrown: [139, 69, 19],
		salmon: [250, 128, 114],
		sandybrown: [244, 164, 96],
		seagreen: [46, 139, 87],
		seashell: [255, 245, 238],
		sienna: [160, 82, 45],
		silver: [192, 192, 192],
		skyblue: [135, 206, 235],
		slateblue: [106, 90, 205],
		slategray: [112, 128, 144],
		slategrey: [112, 128, 144],
		snow: [255, 250, 250],
		springgreen: [0, 255, 127],
		steelblue: [70, 130, 180],
		tan: [210, 180, 140],
		teal: [0, 128, 128],
		thistle: [216, 191, 216],
		tomato: [255, 99, 71],
		turquoise: [64, 224, 208],
		violet: [238, 130, 238],
		wheat: [245, 222, 179],
		white: [255, 255, 255],
		whitesmoke: [245, 245, 245],
		yellow: [255, 255, 0],
		yellowgreen: [154, 205, 50]
	};

/***/ },

/***/ 2428:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/color/~/color-convert/route.js ***!
  \******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var conversions = __webpack_require__(/*! ./conversions */ 2426);
	
	/*
		this function routes a model to all other models.
	
		all functions that are routed have a property `.conversion` attached
		to the returned synthetic function. This property is an array
		of strings, each with the steps in between the 'from' and 'to'
		color models (inclusive).
	
		conversions that are not possible simply are not included.
	*/
	
	// https://jsperf.com/object-keys-vs-for-in-with-closure/3
	var models = Object.keys(conversions);
	
	function buildGraph() {
		var graph = {};
	
		for (var len = models.length, i = 0; i < len; i++) {
			graph[models[i]] = {
				// http://jsperf.com/1-vs-infinity
				// micro-opt, but this is simple.
				distance: -1,
				parent: null
			};
		}
	
		return graph;
	}
	
	// https://en.wikipedia.org/wiki/Breadth-first_search
	function deriveBFS(fromModel) {
		var graph = buildGraph();
		var queue = [fromModel]; // unshift -> queue -> pop
	
		graph[fromModel].distance = 0;
	
		while (queue.length) {
			var current = queue.pop();
			var adjacents = Object.keys(conversions[current]);
	
			for (var len = adjacents.length, i = 0; i < len; i++) {
				var adjacent = adjacents[i];
				var node = graph[adjacent];
	
				if (node.distance === -1) {
					node.distance = graph[current].distance + 1;
					node.parent = current;
					queue.unshift(adjacent);
				}
			}
		}
	
		return graph;
	}
	
	function link(from, to) {
		return function (args) {
			return to(from(args));
		};
	}
	
	function wrapConversion(toModel, graph) {
		var path = [graph[toModel].parent, toModel];
		var fn = conversions[graph[toModel].parent][toModel];
	
		var cur = graph[toModel].parent;
		while (graph[cur].parent) {
			path.unshift(graph[cur].parent);
			fn = link(conversions[graph[cur].parent][cur], fn);
			cur = graph[cur].parent;
		}
	
		fn.conversion = path;
		return fn;
	}
	
	module.exports = function (fromModel) {
		var graph = deriveBFS(fromModel);
		var conversion = {};
	
		var models = Object.keys(graph);
		for (var len = models.length, i = 0; i < len; i++) {
			var toModel = models[i];
			var node = graph[toModel];
	
			if (node.parent === null) {
				// no possible conversion, or this node is the source model.
				continue;
			}
	
			conversion[toModel] = wrapConversion(toModel, graph);
		}
	
		return conversion;
	};

/***/ },

/***/ 2429:
/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/color/~/color-string/color-string.js ***!
  \************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	/* MIT license */
	var colorNames = __webpack_require__(/*! color-name */ 2430);
	
	module.exports = {
	   getRgba: getRgba,
	   getHsla: getHsla,
	   getRgb: getRgb,
	   getHsl: getHsl,
	   getHwb: getHwb,
	   getAlpha: getAlpha,
	
	   hexString: hexString,
	   rgbString: rgbString,
	   rgbaString: rgbaString,
	   percentString: percentString,
	   percentaString: percentaString,
	   hslString: hslString,
	   hslaString: hslaString,
	   hwbString: hwbString,
	   keyword: keyword
	};
	
	function getRgba(string) {
	   if (!string) {
	      return;
	   }
	   var abbr = /^#([a-fA-F0-9]{3})$/,
	       hex = /^#([a-fA-F0-9]{6})$/,
	       rgba = /^rgba?\(\s*([+-]?\d+)\s*,\s*([+-]?\d+)\s*,\s*([+-]?\d+)\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)$/,
	       per = /^rgba?\(\s*([+-]?[\d\.]+)\%\s*,\s*([+-]?[\d\.]+)\%\s*,\s*([+-]?[\d\.]+)\%\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)$/,
	       keyword = /(\D+)/;
	
	   var rgb = [0, 0, 0],
	       a = 1,
	       match = string.match(abbr);
	   if (match) {
	      match = match[1];
	      for (var i = 0; i < rgb.length; i++) {
	         rgb[i] = parseInt(match[i] + match[i], 16);
	      }
	   } else if (match = string.match(hex)) {
	      match = match[1];
	      for (var i = 0; i < rgb.length; i++) {
	         rgb[i] = parseInt(match.slice(i * 2, i * 2 + 2), 16);
	      }
	   } else if (match = string.match(rgba)) {
	      for (var i = 0; i < rgb.length; i++) {
	         rgb[i] = parseInt(match[i + 1]);
	      }
	      a = parseFloat(match[4]);
	   } else if (match = string.match(per)) {
	      for (var i = 0; i < rgb.length; i++) {
	         rgb[i] = Math.round(parseFloat(match[i + 1]) * 2.55);
	      }
	      a = parseFloat(match[4]);
	   } else if (match = string.match(keyword)) {
	      if (match[1] == "transparent") {
	         return [0, 0, 0, 0];
	      }
	      rgb = colorNames[match[1]];
	      if (!rgb) {
	         return;
	      }
	   }
	
	   for (var i = 0; i < rgb.length; i++) {
	      rgb[i] = scale(rgb[i], 0, 255);
	   }
	   if (!a && a != 0) {
	      a = 1;
	   } else {
	      a = scale(a, 0, 1);
	   }
	   rgb[3] = a;
	   return rgb;
	}
	
	function getHsla(string) {
	   if (!string) {
	      return;
	   }
	   var hsl = /^hsla?\(\s*([+-]?\d+)(?:deg)?\s*,\s*([+-]?[\d\.]+)%\s*,\s*([+-]?[\d\.]+)%\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)/;
	   var match = string.match(hsl);
	   if (match) {
	      var alpha = parseFloat(match[4]);
	      var h = scale(parseInt(match[1]), 0, 360),
	          s = scale(parseFloat(match[2]), 0, 100),
	          l = scale(parseFloat(match[3]), 0, 100),
	          a = scale(isNaN(alpha) ? 1 : alpha, 0, 1);
	      return [h, s, l, a];
	   }
	}
	
	function getHwb(string) {
	   if (!string) {
	      return;
	   }
	   var hwb = /^hwb\(\s*([+-]?\d+)(?:deg)?\s*,\s*([+-]?[\d\.]+)%\s*,\s*([+-]?[\d\.]+)%\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)/;
	   var match = string.match(hwb);
	   if (match) {
	      var alpha = parseFloat(match[4]);
	      var h = scale(parseInt(match[1]), 0, 360),
	          w = scale(parseFloat(match[2]), 0, 100),
	          b = scale(parseFloat(match[3]), 0, 100),
	          a = scale(isNaN(alpha) ? 1 : alpha, 0, 1);
	      return [h, w, b, a];
	   }
	}
	
	function getRgb(string) {
	   var rgba = getRgba(string);
	   return rgba && rgba.slice(0, 3);
	}
	
	function getHsl(string) {
	   var hsla = getHsla(string);
	   return hsla && hsla.slice(0, 3);
	}
	
	function getAlpha(string) {
	   var vals = getRgba(string);
	   if (vals) {
	      return vals[3];
	   } else if (vals = getHsla(string)) {
	      return vals[3];
	   } else if (vals = getHwb(string)) {
	      return vals[3];
	   }
	}
	
	// generators
	function hexString(rgb) {
	   return "#" + hexDouble(rgb[0]) + hexDouble(rgb[1]) + hexDouble(rgb[2]);
	}
	
	function rgbString(rgba, alpha) {
	   if (alpha < 1 || rgba[3] && rgba[3] < 1) {
	      return rgbaString(rgba, alpha);
	   }
	   return "rgb(" + rgba[0] + ", " + rgba[1] + ", " + rgba[2] + ")";
	}
	
	function rgbaString(rgba, alpha) {
	   if (alpha === undefined) {
	      alpha = rgba[3] !== undefined ? rgba[3] : 1;
	   }
	   return "rgba(" + rgba[0] + ", " + rgba[1] + ", " + rgba[2] + ", " + alpha + ")";
	}
	
	function percentString(rgba, alpha) {
	   if (alpha < 1 || rgba[3] && rgba[3] < 1) {
	      return percentaString(rgba, alpha);
	   }
	   var r = Math.round(rgba[0] / 255 * 100),
	       g = Math.round(rgba[1] / 255 * 100),
	       b = Math.round(rgba[2] / 255 * 100);
	
	   return "rgb(" + r + "%, " + g + "%, " + b + "%)";
	}
	
	function percentaString(rgba, alpha) {
	   var r = Math.round(rgba[0] / 255 * 100),
	       g = Math.round(rgba[1] / 255 * 100),
	       b = Math.round(rgba[2] / 255 * 100);
	   return "rgba(" + r + "%, " + g + "%, " + b + "%, " + (alpha || rgba[3] || 1) + ")";
	}
	
	function hslString(hsla, alpha) {
	   if (alpha < 1 || hsla[3] && hsla[3] < 1) {
	      return hslaString(hsla, alpha);
	   }
	   return "hsl(" + hsla[0] + ", " + hsla[1] + "%, " + hsla[2] + "%)";
	}
	
	function hslaString(hsla, alpha) {
	   if (alpha === undefined) {
	      alpha = hsla[3] !== undefined ? hsla[3] : 1;
	   }
	   return "hsla(" + hsla[0] + ", " + hsla[1] + "%, " + hsla[2] + "%, " + alpha + ")";
	}
	
	// hwb is a bit different than rgb(a) & hsl(a) since there is no alpha specific syntax
	// (hwb have alpha optional & 1 is default value)
	function hwbString(hwb, alpha) {
	   if (alpha === undefined) {
	      alpha = hwb[3] !== undefined ? hwb[3] : 1;
	   }
	   return "hwb(" + hwb[0] + ", " + hwb[1] + "%, " + hwb[2] + "%" + (alpha !== undefined && alpha !== 1 ? ", " + alpha : "") + ")";
	}
	
	function keyword(rgb) {
	   return reverseNames[rgb.slice(0, 3)];
	}
	
	// helpers
	function scale(num, min, max) {
	   return Math.min(Math.max(min, num), max);
	}
	
	function hexDouble(num) {
	   var str = num.toString(16).toUpperCase();
	   return str.length < 2 ? "0" + str : str;
	}
	
	//create a list of reverse color names
	var reverseNames = {};
	for (var name in colorNames) {
	   reverseNames[colorNames[name]] = name;
	}

/***/ },

/***/ 2430:
/*!******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/color/~/color-string/~/color-name/index.js ***!
  \******************************************************************************************/
/***/ function(module, exports) {

	"use strict";
	
	module.exports = {
		"aliceblue": [240, 248, 255],
		"antiquewhite": [250, 235, 215],
		"aqua": [0, 255, 255],
		"aquamarine": [127, 255, 212],
		"azure": [240, 255, 255],
		"beige": [245, 245, 220],
		"bisque": [255, 228, 196],
		"black": [0, 0, 0],
		"blanchedalmond": [255, 235, 205],
		"blue": [0, 0, 255],
		"blueviolet": [138, 43, 226],
		"brown": [165, 42, 42],
		"burlywood": [222, 184, 135],
		"cadetblue": [95, 158, 160],
		"chartreuse": [127, 255, 0],
		"chocolate": [210, 105, 30],
		"coral": [255, 127, 80],
		"cornflowerblue": [100, 149, 237],
		"cornsilk": [255, 248, 220],
		"crimson": [220, 20, 60],
		"cyan": [0, 255, 255],
		"darkblue": [0, 0, 139],
		"darkcyan": [0, 139, 139],
		"darkgoldenrod": [184, 134, 11],
		"darkgray": [169, 169, 169],
		"darkgreen": [0, 100, 0],
		"darkgrey": [169, 169, 169],
		"darkkhaki": [189, 183, 107],
		"darkmagenta": [139, 0, 139],
		"darkolivegreen": [85, 107, 47],
		"darkorange": [255, 140, 0],
		"darkorchid": [153, 50, 204],
		"darkred": [139, 0, 0],
		"darksalmon": [233, 150, 122],
		"darkseagreen": [143, 188, 143],
		"darkslateblue": [72, 61, 139],
		"darkslategray": [47, 79, 79],
		"darkslategrey": [47, 79, 79],
		"darkturquoise": [0, 206, 209],
		"darkviolet": [148, 0, 211],
		"deeppink": [255, 20, 147],
		"deepskyblue": [0, 191, 255],
		"dimgray": [105, 105, 105],
		"dimgrey": [105, 105, 105],
		"dodgerblue": [30, 144, 255],
		"firebrick": [178, 34, 34],
		"floralwhite": [255, 250, 240],
		"forestgreen": [34, 139, 34],
		"fuchsia": [255, 0, 255],
		"gainsboro": [220, 220, 220],
		"ghostwhite": [248, 248, 255],
		"gold": [255, 215, 0],
		"goldenrod": [218, 165, 32],
		"gray": [128, 128, 128],
		"green": [0, 128, 0],
		"greenyellow": [173, 255, 47],
		"grey": [128, 128, 128],
		"honeydew": [240, 255, 240],
		"hotpink": [255, 105, 180],
		"indianred": [205, 92, 92],
		"indigo": [75, 0, 130],
		"ivory": [255, 255, 240],
		"khaki": [240, 230, 140],
		"lavender": [230, 230, 250],
		"lavenderblush": [255, 240, 245],
		"lawngreen": [124, 252, 0],
		"lemonchiffon": [255, 250, 205],
		"lightblue": [173, 216, 230],
		"lightcoral": [240, 128, 128],
		"lightcyan": [224, 255, 255],
		"lightgoldenrodyellow": [250, 250, 210],
		"lightgray": [211, 211, 211],
		"lightgreen": [144, 238, 144],
		"lightgrey": [211, 211, 211],
		"lightpink": [255, 182, 193],
		"lightsalmon": [255, 160, 122],
		"lightseagreen": [32, 178, 170],
		"lightskyblue": [135, 206, 250],
		"lightslategray": [119, 136, 153],
		"lightslategrey": [119, 136, 153],
		"lightsteelblue": [176, 196, 222],
		"lightyellow": [255, 255, 224],
		"lime": [0, 255, 0],
		"limegreen": [50, 205, 50],
		"linen": [250, 240, 230],
		"magenta": [255, 0, 255],
		"maroon": [128, 0, 0],
		"mediumaquamarine": [102, 205, 170],
		"mediumblue": [0, 0, 205],
		"mediumorchid": [186, 85, 211],
		"mediumpurple": [147, 112, 219],
		"mediumseagreen": [60, 179, 113],
		"mediumslateblue": [123, 104, 238],
		"mediumspringgreen": [0, 250, 154],
		"mediumturquoise": [72, 209, 204],
		"mediumvioletred": [199, 21, 133],
		"midnightblue": [25, 25, 112],
		"mintcream": [245, 255, 250],
		"mistyrose": [255, 228, 225],
		"moccasin": [255, 228, 181],
		"navajowhite": [255, 222, 173],
		"navy": [0, 0, 128],
		"oldlace": [253, 245, 230],
		"olive": [128, 128, 0],
		"olivedrab": [107, 142, 35],
		"orange": [255, 165, 0],
		"orangered": [255, 69, 0],
		"orchid": [218, 112, 214],
		"palegoldenrod": [238, 232, 170],
		"palegreen": [152, 251, 152],
		"paleturquoise": [175, 238, 238],
		"palevioletred": [219, 112, 147],
		"papayawhip": [255, 239, 213],
		"peachpuff": [255, 218, 185],
		"peru": [205, 133, 63],
		"pink": [255, 192, 203],
		"plum": [221, 160, 221],
		"powderblue": [176, 224, 230],
		"purple": [128, 0, 128],
		"rebeccapurple": [102, 51, 153],
		"red": [255, 0, 0],
		"rosybrown": [188, 143, 143],
		"royalblue": [65, 105, 225],
		"saddlebrown": [139, 69, 19],
		"salmon": [250, 128, 114],
		"sandybrown": [244, 164, 96],
		"seagreen": [46, 139, 87],
		"seashell": [255, 245, 238],
		"sienna": [160, 82, 45],
		"silver": [192, 192, 192],
		"skyblue": [135, 206, 235],
		"slateblue": [106, 90, 205],
		"slategray": [112, 128, 144],
		"slategrey": [112, 128, 144],
		"snow": [255, 250, 250],
		"springgreen": [0, 255, 127],
		"steelblue": [70, 130, 180],
		"tan": [210, 180, 140],
		"teal": [0, 128, 128],
		"thistle": [216, 191, 216],
		"tomato": [255, 99, 71],
		"turquoise": [64, 224, 208],
		"violet": [238, 130, 238],
		"wheat": [245, 222, 179],
		"white": [255, 255, 255],
		"whitesmoke": [245, 245, 245],
		"yellow": [255, 255, 0],
		"yellowgreen": [154, 205, 50]
	};

/***/ },

/***/ 2431:
/*!********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/object-hash/index.js ***!
  \********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(Buffer) {'use strict';
	
	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };
	
	var crypto = __webpack_require__(/*! crypto */ 1657);
	
	/**
	 * Exported function
	 *
	 * Options:
	 *
	 *  - `algorithm` hash algo to be used by this instance: *'sha1', 'md5' 
	 *  - `excludeValues` {true|*false} hash object keys, values ignored 
	 *  - `encoding` hash encoding, supports 'buffer', '*hex', 'binary', 'base64' 
	 *  - `ignoreUnknown` {true|*false} ignore unknown object types
	 *  - `replacer` optional function that replaces values before hashing
	 *  - `respectFunctionProperties` {*true|false} consider function properties when hashing
	 *  - `respectFunctionNames` {*true|false} consider 'name' property of functions for hashing
	 *  - `respectType` {*true|false} Respect special properties (prototype, constructor)
	 *    when hashing to distinguish between types
	 *  - `unorderedArrays` {true|*false} Sort all arrays before hashing
	 *  - `unorderedSets` {*true|false} Sort `Set` and `Map` instances before hashing
	 *  * = default
	 *
	 * @param {object} object value to hash
	 * @param {object} options hashing options
	 * @return {string} hash value
	 * @api public
	 */
	exports = module.exports = objectHash;
	
	function objectHash(object, options) {
	  options = applyDefaults(object, options);
	
	  return hash(object, options);
	}
	
	/**
	 * Exported sugar methods
	 *
	 * @param {object} object value to hash
	 * @return {string} hash value
	 * @api public
	 */
	exports.sha1 = function (object) {
	  return objectHash(object);
	};
	exports.keys = function (object) {
	  return objectHash(object, { excludeValues: true, algorithm: 'sha1', encoding: 'hex' });
	};
	exports.MD5 = function (object) {
	  return objectHash(object, { algorithm: 'md5', encoding: 'hex' });
	};
	exports.keysMD5 = function (object) {
	  return objectHash(object, { algorithm: 'md5', encoding: 'hex', excludeValues: true });
	};
	
	// Internals
	function applyDefaults(object, options) {
	  var hashes = crypto.getHashes ? crypto.getHashes() : ['sha1', 'md5'];
	  var encodings = ['buffer', 'hex', 'binary', 'base64'];
	
	  options = options || {};
	  options.algorithm = options.algorithm || 'sha1';
	  options.encoding = options.encoding || 'hex';
	  options.excludeValues = options.excludeValues ? true : false;
	  options.algorithm = options.algorithm.toLowerCase();
	  options.encoding = options.encoding.toLowerCase();
	  options.ignoreUnknown = options.ignoreUnknown !== true ? false : true; // default to false
	  options.respectType = options.respectType === false ? false : true; // default to true
	  options.respectFunctionNames = options.respectFunctionNames === false ? false : true;
	  options.respectFunctionProperties = options.respectFunctionProperties === false ? false : true;
	  options.unorderedArrays = options.unorderedArrays !== true ? false : true; // default to false
	  options.unorderedSets = options.unorderedSets === false ? false : true; // default to false
	  options.replacer = options.replacer || undefined;
	
	  if (typeof object === 'undefined') {
	    throw new Error('Object argument required.');
	  }
	
	  hashes.push('passthrough');
	  // if there is a case-insensitive match in the hashes list, accept it
	  // (i.e. SHA256 for sha256)
	  for (var i = 0; i < hashes.length; ++i) {
	    if (hashes[i].toLowerCase() === options.algorithm.toLowerCase()) {
	      options.algorithm = hashes[i];
	    }
	  }
	
	  if (hashes.indexOf(options.algorithm) === -1) {
	    throw new Error('Algorithm "' + options.algorithm + '"  not supported. ' + 'supported values: ' + hashes.join(', '));
	  }
	
	  if (encodings.indexOf(options.encoding) === -1 && options.algorithm !== 'passthrough') {
	    throw new Error('Encoding "' + options.encoding + '"  not supported. ' + 'supported values: ' + encodings.join(', '));
	  }
	
	  return options;
	}
	
	/** Check if the given function is a native function */
	function isNativeFunction(f) {
	  if (typeof f !== 'function') {
	    return false;
	  }
	  var exp = /^function\s+\w*\s*\(\s*\)\s*{\s+\[native code\]\s+}$/i;
	  return exp.exec(Function.prototype.toString.call(f)) != null;
	}
	
	function hash(object, options) {
	  var hashingStream;
	
	  if (options.algorithm !== 'passthrough') {
	    hashingStream = crypto.createHash(options.algorithm);
	  } else {
	    hashingStream = new PassThrough();
	  }
	
	  if (typeof hashingStream.write === 'undefined') {
	    hashingStream.write = hashingStream.update;
	    hashingStream.end = hashingStream.update;
	  }
	
	  var hasher = typeHasher(options, hashingStream);
	  hasher.dispatch(object);
	  if (!hashingStream.update) hashingStream.end('');
	
	  if (hashingStream.digest) {
	    return hashingStream.digest(options.encoding === 'buffer' ? undefined : options.encoding);
	  }
	
	  var buf = hashingStream.read();
	  if (options.encoding === 'buffer') {
	    return buf;
	  }
	
	  return buf.toString(options.encoding);
	}
	
	/**
	 * Expose streaming API
	 *
	 * @param {object} object  Value to serialize
	 * @param {object} options  Options, as for hash()
	 * @param {object} stream  A stream to write the serializiation to
	 * @api public
	 */
	exports.writeToStream = function (object, options, stream) {
	  if (typeof stream === 'undefined') {
	    stream = options;
	    options = {};
	  }
	
	  options = applyDefaults(object, options);
	
	  return typeHasher(options, stream).dispatch(object);
	};
	
	function typeHasher(options, writeTo, context) {
	  context = context || [];
	  var write = function write(str) {
	    if (writeTo.update) return writeTo.update(str, 'utf8');else return writeTo.write(str, 'utf8');
	  };
	
	  return {
	    dispatch: function dispatch(value) {
	      if (options.replacer) {
	        value = options.replacer(value);
	      }
	
	      var type = typeof value === 'undefined' ? 'undefined' : _typeof(value);
	      if (value === null) {
	        type = 'null';
	      }
	
	      //console.log("[DEBUG] Dispatch: ", value, "->", type, " -> ", "_" + type);
	
	      return this['_' + type](value);
	    },
	    _object: function _object(object) {
	      var pattern = /\[object (.*)\]/i;
	      var objString = Object.prototype.toString.call(object);
	      var objType = pattern.exec(objString);
	      if (!objType) {
	        // object type did not match [object ...]
	        objType = 'unknown:[' + objString + ']';
	      } else {
	        objType = objType[1]; // take only the class name
	      }
	
	      objType = objType.toLowerCase();
	
	      var objectNumber = null;
	
	      if ((objectNumber = context.indexOf(object)) >= 0) {
	        return this.dispatch('[CIRCULAR:' + objectNumber + ']');
	      } else {
	        context.push(object);
	      }
	
	      if (typeof Buffer !== 'undefined' && Buffer.isBuffer && Buffer.isBuffer(object)) {
	        write('buffer:');
	        return write(object);
	      }
	
	      if (objType !== 'object' && objType !== 'function') {
	        if (this['_' + objType]) {
	          this['_' + objType](object);
	        } else if (options.ignoreUnknown) {
	          return write('[' + objType + ']');
	        } else {
	          throw new Error('Unknown object type "' + objType + '"');
	        }
	      } else {
	        var keys = Object.keys(object).sort();
	        // Make sure to incorporate special properties, so
	        // Types with different prototypes will produce
	        // a different hash and objects derived from
	        // different functions (`new Foo`, `new Bar`) will
	        // produce different hashes.
	        // We never do this for native functions since some
	        // seem to break because of that.
	        if (options.respectType !== false && !isNativeFunction(object)) {
	          keys.splice(0, 0, 'prototype', '__proto__', 'constructor');
	        }
	
	        write('object:' + keys.length + ':');
	        var self = this;
	        return keys.forEach(function (key) {
	          self.dispatch(key);
	          write(':');
	          if (!options.excludeValues) {
	            self.dispatch(object[key]);
	          }
	          write(',');
	        });
	      }
	    },
	    _array: function _array(arr, unordered) {
	      unordered = typeof unordered !== 'undefined' ? unordered : options.unorderedArrays !== false; // default to options.unorderedArrays
	
	      var self = this;
	      write('array:' + arr.length + ':');
	      if (!unordered || arr.length <= 1) {
	        return arr.forEach(function (entry) {
	          return self.dispatch(entry);
	        });
	      }
	
	      // the unordered case is a little more complicated:
	      // since there is no canonical ordering on objects,
	      // i.e. {a:1} < {a:2} and {a:1} > {a:2} are both false,
	      // we first serialize each entry using a PassThrough stream
	      // before sorting.
	      // also: we can’t use the same context array for all entries
	      // since the order of hashing should *not* matter. instead,
	      // we keep track of the additions to a copy of the context array
	      // and add all of them to the global context array when we’re done
	      var contextAdditions = [];
	      var entries = arr.map(function (entry) {
	        var strm = new PassThrough();
	        var localContext = context.slice(); // make copy
	        var hasher = typeHasher(options, strm, localContext);
	        hasher.dispatch(entry);
	        // take only what was added to localContext and append it to contextAdditions
	        contextAdditions = contextAdditions.concat(localContext.slice(context.length));
	        return strm.read().toString();
	      });
	      context = context.concat(contextAdditions);
	      entries.sort();
	      return this._array(entries, false);
	    },
	    _date: function _date(date) {
	      return write('date:' + date.toJSON());
	    },
	    _symbol: function _symbol(sym) {
	      return write('symbol:' + sym.toString());
	    },
	    _error: function _error(err) {
	      return write('error:' + err.toString());
	    },
	    _boolean: function _boolean(bool) {
	      return write('bool:' + bool.toString());
	    },
	    _string: function _string(string) {
	      write('string:' + string.length + ':');
	      write(string);
	    },
	    _function: function _function(fn) {
	      write('fn:');
	      if (isNativeFunction(fn)) {
	        this.dispatch('[native]');
	      } else {
	        this.dispatch(fn.toString());
	      }
	
	      if (options.respectFunctionNames !== false) {
	        // Make sure we can still distinguish native functions
	        // by their name, otherwise String and Function will
	        // have the same hash
	        this.dispatch("function-name:" + String(fn.name));
	      }
	
	      if (options.respectFunctionProperties) {
	        this._object(fn);
	      }
	    },
	    _number: function _number(number) {
	      return write('number:' + number.toString());
	    },
	    _xml: function _xml(xml) {
	      return write('xml:' + xml.toString());
	    },
	    _null: function _null() {
	      return write('Null');
	    },
	    _undefined: function _undefined() {
	      return write('Undefined');
	    },
	    _regexp: function _regexp(regex) {
	      return write('regex:' + regex.toString());
	    },
	    _uint8array: function _uint8array(arr) {
	      write('uint8array:');
	      return this.dispatch(Array.prototype.slice.call(arr));
	    },
	    _uint8clampedarray: function _uint8clampedarray(arr) {
	      write('uint8clampedarray:');
	      return this.dispatch(Array.prototype.slice.call(arr));
	    },
	    _int8array: function _int8array(arr) {
	      write('uint8array:');
	      return this.dispatch(Array.prototype.slice.call(arr));
	    },
	    _uint16array: function _uint16array(arr) {
	      write('uint16array:');
	      return this.dispatch(Array.prototype.slice.call(arr));
	    },
	    _int16array: function _int16array(arr) {
	      write('uint16array:');
	      return this.dispatch(Array.prototype.slice.call(arr));
	    },
	    _uint32array: function _uint32array(arr) {
	      write('uint32array:');
	      return this.dispatch(Array.prototype.slice.call(arr));
	    },
	    _int32array: function _int32array(arr) {
	      write('uint32array:');
	      return this.dispatch(Array.prototype.slice.call(arr));
	    },
	    _float32array: function _float32array(arr) {
	      write('float32array:');
	      return this.dispatch(Array.prototype.slice.call(arr));
	    },
	    _float64array: function _float64array(arr) {
	      write('float64array:');
	      return this.dispatch(Array.prototype.slice.call(arr));
	    },
	    _arraybuffer: function _arraybuffer(arr) {
	      write('arraybuffer:');
	      return this.dispatch(new Uint8Array(arr));
	    },
	    _url: function _url(url) {
	      return write('url:' + url.toString(), 'utf8');
	    },
	    _map: function _map(map) {
	      write('map:');
	      var arr = Array.from(map);
	      return this._array(arr, options.unorderedSets !== false);
	    },
	    _set: function _set(set) {
	      write('set:');
	      var arr = Array.from(set);
	      return this._array(arr, options.unorderedSets !== false);
	    },
	    _blob: function _blob() {
	      if (options.ignoreUnknown) {
	        return write('[blob]');
	      }
	
	      throw Error('Hashing Blob objects is currently not supported\n' + '(see https://github.com/puleos/object-hash/issues/26)\n' + 'Use "options.replacer" or "options.ignoreUnknown"\n');
	    },
	    _domwindow: function _domwindow() {
	      return write('domwindow');
	    },
	    /* Node.js standard native objects */
	    _process: function _process() {
	      return write('process');
	    },
	    _timer: function _timer() {
	      return write('timer');
	    },
	    _pipe: function _pipe() {
	      return write('pipe');
	    },
	    _tcp: function _tcp() {
	      return write('tcp');
	    },
	    _udp: function _udp() {
	      return write('udp');
	    },
	    _tty: function _tty() {
	      return write('tty');
	    },
	    _statwatcher: function _statwatcher() {
	      return write('statwatcher');
	    },
	    _securecontext: function _securecontext() {
	      return write('securecontext');
	    },
	    _connection: function _connection() {
	      return write('connection');
	    },
	    _zlib: function _zlib() {
	      return write('zlib');
	    },
	    _context: function _context() {
	      return write('context');
	    },
	    _nodescript: function _nodescript() {
	      return write('nodescript');
	    },
	    _httpparser: function _httpparser() {
	      return write('httpparser');
	    },
	    _dataview: function _dataview() {
	      return write('dataview');
	    },
	    _signal: function _signal() {
	      return write('signal');
	    },
	    _fsevent: function _fsevent() {
	      return write('fsevent');
	    },
	    _tlswrap: function _tlswrap() {
	      return write('tlswrap');
	    }
	  };
	}
	
	// Mini-implementation of stream.PassThrough
	// We are far from having need for the full implementation, and we can
	// make assumtions like "many writes, then only one final read"
	// and we can ignore encoding specifics
	function PassThrough() {
	  return {
	    buf: '',
	
	    write: function write(b) {
	      this.buf += b;
	    },
	
	    end: function end(b) {
	      this.buf += b;
	    },
	
	    read: function read() {
	      return this.buf;
	    }
	  };
	}
	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! (webpack)/~/node-libs-browser/~/buffer/index.js */ 1646).Buffer))

/***/ },

/***/ 2432:
/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/HighchartsHeatmapContainer.css ***!
  \********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./HighchartsHeatmapContainer.css */ 2433);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 2279)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./HighchartsHeatmapContainer.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./HighchartsHeatmapContainer.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 2433:
/*!***********************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/css-loader!./expression-atlas-heatmap-highcharts/src/HighchartsHeatmapContainer.css ***!
  \***********************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 2278)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaInnerHeatmap {\n    position: relative;\n    overflow: hidden;\n}\n\n.gxaAside {\n    float: left;\n}\n\n.gxaInnerHeatmap:after {\n    clear: both;\n    content: \".\";\n    display: block;\n    visibility: hidden;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 2434:
/*!********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/index.js ***!
  \********************************************************************/
[2811, 2435],

/***/ 2435:
/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/src/AnatomogramFactory.jsx ***!
  \**************************************************************************************/
[2812, 2108, 2421, 2436, 2454, 2487],

/***/ 2436:
/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/src/Anatomogram.jsx ***!
  \*******************************************************************************/
[2813, 2108, 2437, 2439, 2453],

/***/ 2437:
/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/src/AnatomogramImage.jsx ***!
  \************************************************************************************/
[2814, 2108, 2264, 2421, 2438],

/***/ 2438:
/*!********************************************************************************************************************************************!*\
  !*** ./~/imports-loader?this=>window,fix=>module.exports=0!./expression-atlas-heatmap-highcharts/~/anatomogram/~/snapsvg/dist/snap.svg.js ***!
  \********************************************************************************************************************************************/
574,

/***/ 2439:
/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/src/SelectionIcon.jsx ***!
  \*********************************************************************************/
[2815, 2108, 2440, 2442],

/***/ 2440:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/src/SelectionIcon.less ***!
  \**********************************************************************************/
[2816, 2441, 2279],

/***/ 2441:
/*!*****************************************************************************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/css-loader!./expression-atlas-heatmap-highcharts/~/less-loader!./expression-atlas-heatmap-highcharts/~/anatomogram/src/SelectionIcon.less ***!
  \*****************************************************************************************************************************************************************************************/
[2817, 2278],

/***/ 2442:
/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/icons ^\.\/.*\.png$ ***!
  \**************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var map = {
		"./brain_selected.png": 2443,
		"./brain_unselected.png": 2444,
		"./female_selected.png": 2445,
		"./female_unselected.png": 2446,
		"./flower_parts_selected.png": 2447,
		"./flower_parts_unselected.png": 2448,
		"./male_selected.png": 2449,
		"./male_unselected.png": 2450,
		"./whole_plant_selected.png": 2451,
		"./whole_plant_unselected.png": 2452
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
	webpackContext.id = 2442;


/***/ },

/***/ 2443:
/*!*******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/icons/brain_selected.png ***!
  \*******************************************************************************************/
581,

/***/ 2444:
/*!*********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/icons/brain_unselected.png ***!
  \*********************************************************************************************/
582,

/***/ 2445:
/*!********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/icons/female_selected.png ***!
  \********************************************************************************************/
583,

/***/ 2446:
/*!**********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/icons/female_unselected.png ***!
  \**********************************************************************************************/
584,

/***/ 2447:
/*!**************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/icons/flower_parts_selected.png ***!
  \**************************************************************************************************/
585,

/***/ 2448:
/*!****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/icons/flower_parts_unselected.png ***!
  \****************************************************************************************************/
586,

/***/ 2449:
/*!******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/icons/male_selected.png ***!
  \******************************************************************************************/
587,

/***/ 2450:
/*!********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/icons/male_unselected.png ***!
  \********************************************************************************************/
588,

/***/ 2451:
/*!*************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/icons/whole_plant_selected.png ***!
  \*************************************************************************************************/
589,

/***/ 2452:
/*!***************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/icons/whole_plant_unselected.png ***!
  \***************************************************************************************************/
590,

/***/ 2453:
/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/json/idsForSvgs.json ***!
  \***************************************************************************************/
591,

/***/ 2454:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/src/imagesAvailable.js ***!
  \**********************************************************************************/
[2818, 2455, 2453, 2456],

/***/ 2455:
/*!*******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/json/svgsForSpecies.json ***!
  \*******************************************************************************************/
593,

/***/ 2456:
/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg ^\.\/.*$ ***!
  \*******************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	var map = {
		"./anolis_carolinensis.svg": 2457,
		"./arabidopsis_thaliana_whole_plant.svg": 2458,
		"./brachypodium_distachyon_flower_parts.svg": 2459,
		"./brachypodium_distachyon_whole_plant.svg": 2460,
		"./chicken.svg": 2461,
		"./cow.svg": 2462,
		"./hordeum_vulgare_flower_parts.svg": 2463,
		"./hordeum_vulgare_whole_plant.svg": 2464,
		"./human_brain.svg": 2465,
		"./human_female.svg": 2466,
		"./human_male.svg": 2467,
		"./macaca_mulatta.svg": 2468,
		"./monodelphis_domestica.svg": 2469,
		"./mouse_brain.svg": 2470,
		"./mouse_female.svg": 2471,
		"./mouse_male.svg": 2472,
		"./oryza_sativa_flower_parts.svg": 2473,
		"./oryza_sativa_whole_plant.svg": 2474,
		"./papio_anubis.svg": 2475,
		"./rat.svg": 2476,
		"./solanum_lycopersicum_flower_parts.svg": 2477,
		"./solanum_lycopersicum_whole_plant.svg": 2478,
		"./sorghum_bicolor_flower_parts.svg": 2479,
		"./sorghum_bicolor_whole_plant.svg": 2480,
		"./tetraodon_nigroviridis.svg": 2481,
		"./triticum_aestivum_flower_parts.svg": 2482,
		"./triticum_aestivum_whole_plant.svg": 2483,
		"./xenopus_tropicalis.svg": 2484,
		"./zea_mays_flower_parts.svg": 2485,
		"./zea_mays_whole_plant.svg": 2486
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
	webpackContext.id = 2456;


/***/ },

/***/ 2457:
/*!**********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/anolis_carolinensis.svg ***!
  \**********************************************************************************************/
595,

/***/ 2458:
/*!***********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/arabidopsis_thaliana_whole_plant.svg ***!
  \***********************************************************************************************************/
596,

/***/ 2459:
/*!***************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/brachypodium_distachyon_flower_parts.svg ***!
  \***************************************************************************************************************/
597,

/***/ 2460:
/*!**************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/brachypodium_distachyon_whole_plant.svg ***!
  \**************************************************************************************************************/
598,

/***/ 2461:
/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/chicken.svg ***!
  \**********************************************************************************/
599,

/***/ 2462:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/cow.svg ***!
  \******************************************************************************/
600,

/***/ 2463:
/*!*******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/hordeum_vulgare_flower_parts.svg ***!
  \*******************************************************************************************************/
601,

/***/ 2464:
/*!******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/hordeum_vulgare_whole_plant.svg ***!
  \******************************************************************************************************/
602,

/***/ 2465:
/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/human_brain.svg ***!
  \**************************************************************************************/
603,

/***/ 2466:
/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/human_female.svg ***!
  \***************************************************************************************/
604,

/***/ 2467:
/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/human_male.svg ***!
  \*************************************************************************************/
605,

/***/ 2468:
/*!*****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/macaca_mulatta.svg ***!
  \*****************************************************************************************/
606,

/***/ 2469:
/*!************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/monodelphis_domestica.svg ***!
  \************************************************************************************************/
607,

/***/ 2470:
/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/mouse_brain.svg ***!
  \**************************************************************************************/
608,

/***/ 2471:
/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/mouse_female.svg ***!
  \***************************************************************************************/
609,

/***/ 2472:
/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/mouse_male.svg ***!
  \*************************************************************************************/
610,

/***/ 2473:
/*!****************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/oryza_sativa_flower_parts.svg ***!
  \****************************************************************************************************/
611,

/***/ 2474:
/*!***************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/oryza_sativa_whole_plant.svg ***!
  \***************************************************************************************************/
612,

/***/ 2475:
/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/papio_anubis.svg ***!
  \***************************************************************************************/
613,

/***/ 2476:
/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/rat.svg ***!
  \******************************************************************************/
614,

/***/ 2477:
/*!************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/solanum_lycopersicum_flower_parts.svg ***!
  \************************************************************************************************************/
615,

/***/ 2478:
/*!***********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/solanum_lycopersicum_whole_plant.svg ***!
  \***********************************************************************************************************/
616,

/***/ 2479:
/*!*******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/sorghum_bicolor_flower_parts.svg ***!
  \*******************************************************************************************************/
617,

/***/ 2480:
/*!******************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/sorghum_bicolor_whole_plant.svg ***!
  \******************************************************************************************************/
618,

/***/ 2481:
/*!*************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/tetraodon_nigroviridis.svg ***!
  \*************************************************************************************************/
619,

/***/ 2482:
/*!*********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/triticum_aestivum_flower_parts.svg ***!
  \*********************************************************************************************************/
620,

/***/ 2483:
/*!********************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/triticum_aestivum_whole_plant.svg ***!
  \********************************************************************************************************/
621,

/***/ 2484:
/*!*********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/xenopus_tropicalis.svg ***!
  \*********************************************************************************************/
622,

/***/ 2485:
/*!************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/zea_mays_flower_parts.svg ***!
  \************************************************************************************************/
623,

/***/ 2486:
/*!***********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/assets/svg/zea_mays_whole_plant.svg ***!
  \***********************************************************************************************/
624,

/***/ 2487:
/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/anatomogram/src/ContainerLayout.less ***!
  \************************************************************************************/
[2819, 2488, 2279],

/***/ 2488:
/*!*******************************************************************************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/css-loader!./expression-atlas-heatmap-highcharts/~/less-loader!./expression-atlas-heatmap-highcharts/~/anatomogram/src/ContainerLayout.less ***!
  \*******************************************************************************************************************************************************************************************/
[2820, 2278],

/***/ 2489:
/*!**********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/genomeBrowserLink.js ***!
  \**********************************************************************/
/***/ function(module, exports) {

	"use strict";
	//Taken from ensemblUtils.js and EnsemblLauncher.jsx in the old heatmap
	//*------------------------------------------------------------------*
	
	// ensemblSpecies is the first two words only, with underscores instead of spaces, and all lower case except for the first character
	// used to launch the ensembl genome browser for tracks
	/**
	 * @param {string} species
	 */
	
	var toEnsemblSpecies = function toEnsemblSpecies(species) {
	    /**
	     * @param {string} str
	     */
	    function capitaliseFirstLetter(str) {
	        return str.charAt(0).toUpperCase() + str.slice(1);
	    }
	
	    /**
	     * @param {string} str
	     */
	    function firstTwoWords(str) {
	        var words = str.split(" ");
	        return words.length <= 2 ? str : words[0] + " " + words[1];
	    }
	
	    return capitaliseFirstLetter(firstTwoWords(species).replace(" ", "_").toLowerCase());
	};
	
	var _ensemblTrackURL = function _ensemblTrackURL(isBaseline, experimentAccession, atlasBaseUrl, species, baseURL, selectedColumnId, selectedGeneId) {
	    var ensemblSpecies = toEnsemblSpecies(species);
	    var atlasTrackBaseURLWithTrackFileHeader = atlasBaseUrl + "/experiments/" + experimentAccession + "/tracks/" + experimentAccession + "." + selectedColumnId;
	    var contigViewBottom = "contigviewbottom=url:" + atlasTrackBaseURLWithTrackFileHeader + (isBaseline ? ".genes.expressions.bedGraph" : ".genes.log2foldchange.bedGraph");
	    var tiling = isBaseline ? "" : "=tiling,url:" + atlasTrackBaseURLWithTrackFileHeader + ".genes.pval.bedGraph=pvalue;";
	    return baseURL + ensemblSpecies + "/Location/View?g=" + selectedGeneId + ";db=core;" + contigViewBottom + tiling + ";format=BEDGRAPH";
	};
	
	var getHost = function getHost(ensemblDB) {
	    var ensemblHost = "";
	    if (ensemblDB === "plants") {
	        ensemblHost = "https://ensembl.gramene.org/";
	    } else if (ensemblDB === "fungi") {
	        ensemblHost = "https://fungi.ensembl.org/";
	    } else if (ensemblDB === "metazoa") {
	        ensemblHost = "https://metazoa.ensembl.org/";
	    } else if (ensemblDB === "ensembl") {
	        ensemblHost = "https://www.ensembl.org/";
	    }
	    return ensemblHost;
	};
	
	module.exports = function (config) {
	    return _ensemblTrackURL(!config.isDifferential, config.experimentAccession, config.atlasBaseURL, config.species, getHost(config.ensemblDB), "__x__", "__y__");
	};

/***/ }

});
//# sourceMappingURL=expressionAtlasHeatmapHighcharts.bundle.js.map