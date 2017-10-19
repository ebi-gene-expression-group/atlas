var experimentPage =
webpackJsonp_name_([1],{

/***/ 15:
/*!***************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/urijs/src/URI.js ***!
  \***************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {var __WEBPACK_AMD_DEFINE_FACTORY__, __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/*!
 * URI.js - Mutating URLs
 *
 * Version: 1.19.0
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
    module.exports = factory(__webpack_require__(/*! ./punycode */ 193), __webpack_require__(/*! ./IPv6 */ 194), __webpack_require__(/*! ./SecondLevelDomains */ 195));
  } else if (true) {
    // AMD. Register as an anonymous module.
    !(__WEBPACK_AMD_DEFINE_ARRAY__ = [__webpack_require__(/*! ./punycode */ 193), __webpack_require__(/*! ./IPv6 */ 194), __webpack_require__(/*! ./SecondLevelDomains */ 195)], __WEBPACK_AMD_DEFINE_FACTORY__ = (factory),
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

  function isInteger(value) {
    return (/^[0-9]+$/.test(value)
    );
  }

  URI.version = '1.19.0';

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
      preventInvalidHostname: URI.preventInvalidHostname,
      duplicateQueryParameters: URI.duplicateQueryParameters,
      escapeQuerySpace: URI.escapeQuerySpace
    };
  };
  // state: throw on invalid hostname
  // see https://github.com/medialize/URI.js/pull/345
  // and https://github.com/medialize/URI.js/issues/354
  URI.preventInvalidHostname = false;
  // state: allow duplicate query parameters (a=1&a=1)
  URI.duplicateQueryParameters = false;
  // state: replaces + with %20 (space in query strings)
  URI.escapeQuerySpace = true;
  // static properties
  URI.protocol_expression = /^[a-z][a-z0-9.+-]*$/i;
  URI.idn_expression = /[^a-z0-9\._-]/i;
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
  // list of protocols which always require a hostname
  URI.hostProtocols = ['http', 'https'];

  // allowed hostname characters according to RFC 3986
  // ALPHA DIGIT "-" "." "_" "~" "!" "$" "&" "'" "(" ")" "*" "+" "," ";" "=" %encoded
  // I've never seen a (non-IDN) hostname other than: ALPHA DIGIT . - _
  URI.invalid_hostname_characters = /[^a-zA-Z0-9\.\-:_]/;
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
      parts = {
        preventInvalidHostname: URI.preventInvalidHostname
      };
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
    if (!string) {
      string = '';
    }

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

    if (parts.preventInvalidHostname) {
      URI.ensureValidHostname(parts.hostname, parts.protocol);
    }

    if (parts.port) {
      URI.ensureValidPort(parts.port);
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

  URI.setQuery = function (data, name, value) {
    if ((typeof name === 'undefined' ? 'undefined' : _typeof(name)) === 'object') {
      for (var key in name) {
        if (hasOwn.call(name, key)) {
          URI.setQuery(data, key, name[key]);
        }
      }
    } else if (typeof name === 'string') {
      data[name] = value === undefined ? null : value;
    } else {
      throw new TypeError('URI.setQuery() accepts an object, string as the name parameter');
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

  URI.ensureValidHostname = function (v, protocol) {
    // Theoretically URIs allow percent-encoding in Hostnames (according to RFC 3986)
    // they are not part of DNS and therefore ignored by URI.js

    var hasHostname = !!v; // not null and not an empty string
    var hasProtocol = !!protocol;
    var rejectEmptyHostname = false;

    if (hasProtocol) {
      rejectEmptyHostname = arrayContains(URI.hostProtocols, protocol);
    }

    if (rejectEmptyHostname && !hasHostname) {
      throw new TypeError('Hostname cannot be empty, if protocol is ' + protocol);
    } else if (v && v.match(URI.invalid_hostname_characters)) {
      // test punycode
      if (!punycode) {
        throw new TypeError('Hostname "' + v + '" contains characters other than [A-Z0-9.-:_] and Punycode.js is not available');
      }
      if (punycode.toASCII(v).match(URI.invalid_hostname_characters)) {
        throw new TypeError('Hostname "' + v + '" contains characters other than [A-Z0-9.-:_]');
      }
    }
  };

  URI.ensureValidPort = function (v) {
    if (!v) {
      return;
    }

    var port = Number(v);
    if (isInteger(port) && port > 0 && port < 65536) {
      return;
    }

    throw new TypeError('Port "' + v + '" is not a valid port');
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
    if (v) {
      // accept trailing ://
      v = v.replace(/:(\/\/)?$/, '');

      if (!v.match(URI.protocol_expression)) {
        throw new TypeError('Protocol "' + v + '" contains characters other than [A-Z0-9.+-] or doesn\'t start with [A-Z]');
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

        URI.ensureValidPort(v);
      }
    }
    return _port.call(this, v, build);
  };
  p.hostname = function (v, build) {
    if (this._parts.urn) {
      return v === undefined ? '' : this;
    }

    if (v !== undefined) {
      var x = { preventInvalidHostname: this._parts.preventInvalidHostname };
      var res = URI.parseHost(v, x);
      if (res !== '/') {
        throw new TypeError('Hostname "' + v + '" contains characters other than [A-Z0-9.-]');
      }

      v = x.hostname;
      if (this._parts.preventInvalidHostname) {
        URI.ensureValidHostname(v, this._parts.protocol);
      }
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

      if (v.indexOf(':') !== -1) {
        throw new TypeError('Domains cannot contain colons');
      }

      if (v) {
        URI.ensureValidHostname(v, this._parts.protocol);
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

      if (v.indexOf(':') !== -1) {
        throw new TypeError('Domains cannot contain colons');
      }

      URI.ensureValidHostname(v, this._parts.protocol);

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
  p.preventInvalidHostname = function (v) {
    this._parts.preventInvalidHostname = !!v;
    return this;
  };

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
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/module.js */ 1)(module)))

/***/ }),

/***/ 181:
/*!************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/fbjs/lib/emptyFunction.js ***!
  \************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Copyright (c) 2013-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
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

/***/ 182:
/*!********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/fbjs/lib/invariant.js ***!
  \********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright (c) 2013-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
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

/***/ 183:
/*!******************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/fbjs/lib/warning.js ***!
  \******************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright (c) 2014-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 */



var emptyFunction = __webpack_require__(/*! ./emptyFunction */ 181);

/**
 * Similar to invariant but only logs a warning if the condition is not met.
 * This can be used to log issues in development environments in critical
 * paths. Removing the logging code for production environments will keep the
 * same logic and follow the same code paths.
 */

var warning = emptyFunction;

if (true) {
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
}

module.exports = warning;

/***/ }),

/***/ 184:
/*!*********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/object-assign/index.js ***!
  \*********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/*
object-assign
(c) Sindre Sorhus
@license MIT
*/


/* eslint-disable no-unused-vars */

var getOwnPropertySymbols = Object.getOwnPropertySymbols;
var hasOwnProperty = Object.prototype.hasOwnProperty;
var propIsEnumerable = Object.prototype.propertyIsEnumerable;

function toObject(val) {
	if (val === null || val === undefined) {
		throw new TypeError('Object.assign cannot be called with null or undefined');
	}

	return Object(val);
}

function shouldUseNative() {
	try {
		if (!Object.assign) {
			return false;
		}

		// Detect buggy property enumeration order in older V8 versions.

		// https://bugs.chromium.org/p/v8/issues/detail?id=4118
		var test1 = new String('abc'); // eslint-disable-line no-new-wrappers
		test1[5] = 'de';
		if (Object.getOwnPropertyNames(test1)[0] === '5') {
			return false;
		}

		// https://bugs.chromium.org/p/v8/issues/detail?id=3056
		var test2 = {};
		for (var i = 0; i < 10; i++) {
			test2['_' + String.fromCharCode(i)] = i;
		}
		var order2 = Object.getOwnPropertyNames(test2).map(function (n) {
			return test2[n];
		});
		if (order2.join('') !== '0123456789') {
			return false;
		}

		// https://bugs.chromium.org/p/v8/issues/detail?id=3056
		var test3 = {};
		'abcdefghijklmnopqrst'.split('').forEach(function (letter) {
			test3[letter] = letter;
		});
		if (Object.keys(Object.assign({}, test3)).join('') !== 'abcdefghijklmnopqrst') {
			return false;
		}

		return true;
	} catch (err) {
		// We don't expect any of the above to throw, but better to be safe.
		return false;
	}
}

module.exports = shouldUseNative() ? Object.assign : function (target, source) {
	var from;
	var to = toObject(target);
	var symbols;

	for (var s = 1; s < arguments.length; s++) {
		from = Object(arguments[s]);

		for (var key in from) {
			if (hasOwnProperty.call(from, key)) {
				to[key] = from[key];
			}
		}

		if (getOwnPropertySymbols) {
			symbols = getOwnPropertySymbols(from);
			for (var i = 0; i < symbols.length; i++) {
				if (propIsEnumerable.call(from, symbols[i])) {
					to[symbols[i]] = from[symbols[i]];
				}
			}
		}
	}

	return to;
};

/***/ }),

/***/ 185:
/*!*************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/prop-types/lib/ReactPropTypesSecret.js ***!
  \*************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright (c) 2013-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */



var ReactPropTypesSecret = 'SECRET_DO_NOT_PASS_THIS_OR_YOU_WILL_BE_FIRED';

module.exports = ReactPropTypesSecret;

/***/ }),

/***/ 186:
/*!************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/resolve-pathname/index.js ***!
  \************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
function isAbsolute(pathname) {
  return pathname.charAt(0) === '/';
}

// About 1.5x faster than the two-arg version of Array#splice()
function spliceOne(list, index) {
  for (var i = index, k = i + 1, n = list.length; k < n; i += 1, k += 1) {
    list[i] = list[k];
  }

  list.pop();
}

// This implementation is based heavily on node's url.parse
function resolvePathname(to) {
  var from = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : '';

  var toParts = to && to.split('/') || [];
  var fromParts = from && from.split('/') || [];

  var isToAbs = to && isAbsolute(to);
  var isFromAbs = from && isAbsolute(from);
  var mustEndAbs = isToAbs || isFromAbs;

  if (to && isAbsolute(to)) {
    // to is absolute
    fromParts = toParts;
  } else if (toParts.length) {
    // to is relative, drop the filename
    fromParts.pop();
    fromParts = fromParts.concat(toParts);
  }

  if (!fromParts.length) return '/';

  var hasTrailingSlash = void 0;
  if (fromParts.length) {
    var last = fromParts[fromParts.length - 1];
    hasTrailingSlash = last === '.' || last === '..' || last === '';
  } else {
    hasTrailingSlash = false;
  }

  var up = 0;
  for (var i = fromParts.length; i >= 0; i--) {
    var part = fromParts[i];

    if (part === '.') {
      spliceOne(fromParts, i);
    } else if (part === '..') {
      spliceOne(fromParts, i);
      up++;
    } else if (up) {
      spliceOne(fromParts, i);
      up--;
    }
  }

  if (!mustEndAbs) for (; up--; up) {
    fromParts.unshift('..');
  }if (mustEndAbs && fromParts[0] !== '' && (!fromParts[0] || !isAbsolute(fromParts[0]))) fromParts.unshift('');

  var result = fromParts.join('/');

  if (hasTrailingSlash && result.substr(-1) !== '/') result += '/';

  return result;
}

exports.default = resolvePathname;

/***/ }),

/***/ 187:
/*!*******************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/value-equal/index.js ***!
  \*******************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _typeof = typeof Symbol === "function" && _typeof2(Symbol.iterator) === "symbol" ? function (obj) {
  return typeof obj === "undefined" ? "undefined" : _typeof2(obj);
} : function (obj) {
  return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof2(obj);
};

function valueEqual(a, b) {
  if (a === b) return true;

  if (a == null || b == null) return false;

  if (Array.isArray(a)) {
    return Array.isArray(b) && a.length === b.length && a.every(function (item, index) {
      return valueEqual(item, b[index]);
    });
  }

  var aType = typeof a === 'undefined' ? 'undefined' : _typeof(a);
  var bType = typeof b === 'undefined' ? 'undefined' : _typeof(b);

  if (aType !== bType) return false;

  if (aType === 'object') {
    var aValue = a.valueOf();
    var bValue = b.valueOf();

    if (aValue !== a || bValue !== b) return valueEqual(aValue, bValue);

    var aKeys = Object.keys(a);
    var bKeys = Object.keys(b);

    if (aKeys.length !== bKeys.length) return false;

    return aKeys.every(function (key) {
      return valueEqual(a[key], b[key]);
    });
  }

  return false;
}

exports.default = valueEqual;

/***/ }),

/***/ 188:
/*!******************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/DOMUtils.js ***!
  \******************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;
var canUseDOM = exports.canUseDOM = !!(typeof window !== 'undefined' && window.document && window.document.createElement);

var addEventListener = exports.addEventListener = function addEventListener(node, event, listener) {
  return node.addEventListener ? node.addEventListener(event, listener, false) : node.attachEvent('on' + event, listener);
};

var removeEventListener = exports.removeEventListener = function removeEventListener(node, event, listener) {
  return node.removeEventListener ? node.removeEventListener(event, listener, false) : node.detachEvent('on' + event, listener);
};

var getConfirmation = exports.getConfirmation = function getConfirmation(message, callback) {
  return callback(window.confirm(message));
}; // eslint-disable-line no-alert

/**
 * Returns true if the HTML5 history API is supported. Taken from Modernizr.
 *
 * https://github.com/Modernizr/Modernizr/blob/master/LICENSE
 * https://github.com/Modernizr/Modernizr/blob/master/feature-detects/history.js
 * changed to avoid false negatives for Windows Phones: https://github.com/reactjs/react-router/issues/586
 */
var supportsHistory = exports.supportsHistory = function supportsHistory() {
  var ua = window.navigator.userAgent;

  if ((ua.indexOf('Android 2.') !== -1 || ua.indexOf('Android 4.0') !== -1) && ua.indexOf('Mobile Safari') !== -1 && ua.indexOf('Chrome') === -1 && ua.indexOf('Windows Phone') === -1) return false;

  return window.history && 'pushState' in window.history;
};

/**
 * Returns true if browser fires popstate on hash change.
 * IE10 and IE11 do not.
 */
var supportsPopStateOnHashChange = exports.supportsPopStateOnHashChange = function supportsPopStateOnHashChange() {
  return window.navigator.userAgent.indexOf('Trident') === -1;
};

/**
 * Returns false if using go(n) with hash history causes a full page reload.
 */
var supportsGoWithoutReloadUsingHash = exports.supportsGoWithoutReloadUsingHash = function supportsGoWithoutReloadUsingHash() {
  return window.navigator.userAgent.indexOf('Firefox') === -1;
};

/**
 * Returns true if a given popstate event is an extraneous WebKit event.
 * Accounts for the fact that Chrome on iOS fires real popstate events
 * containing undefined state when pressing the back button.
 */
var isExtraneousPopstateEvent = exports.isExtraneousPopstateEvent = function isExtraneousPopstateEvent(event) {
  return event.state === undefined && navigator.userAgent.indexOf('CriOS') === -1;
};

/***/ }),

/***/ 189:
/*!**************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/Link.js ***!
  \**************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _invariant = __webpack_require__(/*! invariant */ 8);

var _invariant2 = _interopRequireDefault(_invariant);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

function _objectWithoutProperties(obj, keys) {
  var target = {};for (var i in obj) {
    if (keys.indexOf(i) >= 0) continue;if (!Object.prototype.hasOwnProperty.call(obj, i)) continue;target[i] = obj[i];
  }return target;
}

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

var isModifiedEvent = function isModifiedEvent(event) {
  return !!(event.metaKey || event.altKey || event.ctrlKey || event.shiftKey);
};

/**
 * The public API for rendering a history-aware <a>.
 */

var Link = function (_React$Component) {
  _inherits(Link, _React$Component);

  function Link() {
    var _temp, _this, _ret;

    _classCallCheck(this, Link);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, _React$Component.call.apply(_React$Component, [this].concat(args))), _this), _this.handleClick = function (event) {
      if (_this.props.onClick) _this.props.onClick(event);

      if (!event.defaultPrevented && // onClick prevented default
      event.button === 0 && // ignore right clicks
      !_this.props.target && // let browser handle "target=_blank" etc.
      !isModifiedEvent(event) // ignore clicks with modifier keys
      ) {
          event.preventDefault();

          var history = _this.context.router.history;
          var _this$props = _this.props,
              replace = _this$props.replace,
              to = _this$props.to;

          if (replace) {
            history.replace(to);
          } else {
            history.push(to);
          }
        }
    }, _temp), _possibleConstructorReturn(_this, _ret);
  }

  Link.prototype.render = function render() {
    var _props = this.props,
        replace = _props.replace,
        to = _props.to,
        innerRef = _props.innerRef,
        props = _objectWithoutProperties(_props, ['replace', 'to', 'innerRef']); // eslint-disable-line no-unused-vars

    (0, _invariant2.default)(this.context.router, 'You should not use <Link> outside a <Router>');

    var href = this.context.router.history.createHref(typeof to === 'string' ? { pathname: to } : to);

    return _react2.default.createElement('a', _extends({}, props, { onClick: this.handleClick, href: href, ref: innerRef }));
  };

  return Link;
}(_react2.default.Component);

Link.propTypes = {
  onClick: _propTypes2.default.func,
  target: _propTypes2.default.string,
  replace: _propTypes2.default.bool,
  to: _propTypes2.default.oneOfType([_propTypes2.default.string, _propTypes2.default.object]).isRequired,
  innerRef: _propTypes2.default.oneOfType([_propTypes2.default.string, _propTypes2.default.func])
};
Link.defaultProps = {
  replace: false
};
Link.contextTypes = {
  router: _propTypes2.default.shape({
    history: _propTypes2.default.shape({
      push: _propTypes2.default.func.isRequired,
      replace: _propTypes2.default.func.isRequired,
      createHref: _propTypes2.default.func.isRequired
    }).isRequired
  }).isRequired
};

exports.default = Link;

/***/ }),

/***/ 190:
/*!***************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/Route.js ***!
  \***************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _Route = __webpack_require__(/*! react-router/es/Route */ 191);

var _Route2 = _interopRequireDefault(_Route);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = _Route2.default; // Written in this round about way for babel-transform-imports

/***/ }),

/***/ 191:
/*!***********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router/es/Route.js ***!
  \***********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _invariant = __webpack_require__(/*! invariant */ 8);

var _invariant2 = _interopRequireDefault(_invariant);

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _matchPath = __webpack_require__(/*! ./matchPath */ 85);

var _matchPath2 = _interopRequireDefault(_matchPath);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

var isEmptyChildren = function isEmptyChildren(children) {
  return _react2.default.Children.count(children) === 0;
};

/**
 * The public API for matching a single path and rendering.
 */

var Route = function (_React$Component) {
  _inherits(Route, _React$Component);

  function Route() {
    var _temp, _this, _ret;

    _classCallCheck(this, Route);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, _React$Component.call.apply(_React$Component, [this].concat(args))), _this), _this.state = {
      match: _this.computeMatch(_this.props, _this.context.router)
    }, _temp), _possibleConstructorReturn(_this, _ret);
  }

  Route.prototype.getChildContext = function getChildContext() {
    return {
      router: _extends({}, this.context.router, {
        route: {
          location: this.props.location || this.context.router.route.location,
          match: this.state.match
        }
      })
    };
  };

  Route.prototype.computeMatch = function computeMatch(_ref, router) {
    var computedMatch = _ref.computedMatch,
        location = _ref.location,
        path = _ref.path,
        strict = _ref.strict,
        exact = _ref.exact,
        sensitive = _ref.sensitive;

    if (computedMatch) return computedMatch; // <Switch> already computed the match for us

    (0, _invariant2.default)(router, 'You should not use <Route> or withRouter() outside a <Router>');

    var route = router.route;

    var pathname = (location || route.location).pathname;

    return path ? (0, _matchPath2.default)(pathname, { path: path, strict: strict, exact: exact, sensitive: sensitive }) : route.match;
  };

  Route.prototype.componentWillMount = function componentWillMount() {
    (0, _warning2.default)(!(this.props.component && this.props.render), 'You should not use <Route component> and <Route render> in the same route; <Route render> will be ignored');

    (0, _warning2.default)(!(this.props.component && this.props.children && !isEmptyChildren(this.props.children)), 'You should not use <Route component> and <Route children> in the same route; <Route children> will be ignored');

    (0, _warning2.default)(!(this.props.render && this.props.children && !isEmptyChildren(this.props.children)), 'You should not use <Route render> and <Route children> in the same route; <Route children> will be ignored');
  };

  Route.prototype.componentWillReceiveProps = function componentWillReceiveProps(nextProps, nextContext) {
    (0, _warning2.default)(!(nextProps.location && !this.props.location), '<Route> elements should not change from uncontrolled to controlled (or vice versa). You initially used no "location" prop and then provided one on a subsequent render.');

    (0, _warning2.default)(!(!nextProps.location && this.props.location), '<Route> elements should not change from controlled to uncontrolled (or vice versa). You provided a "location" prop initially but omitted it on a subsequent render.');

    this.setState({
      match: this.computeMatch(nextProps, nextContext.router)
    });
  };

  Route.prototype.render = function render() {
    var match = this.state.match;
    var _props = this.props,
        children = _props.children,
        component = _props.component,
        render = _props.render;
    var _context$router = this.context.router,
        history = _context$router.history,
        route = _context$router.route,
        staticContext = _context$router.staticContext;

    var location = this.props.location || route.location;
    var props = { match: match, location: location, history: history, staticContext: staticContext };

    return component ? // component prop gets first priority, only called if there's a match
    match ? _react2.default.createElement(component, props) : null : render ? // render prop is next, only called if there's a match
    match ? render(props) : null : children ? // children come last, always called
    typeof children === 'function' ? children(props) : !isEmptyChildren(children) ? _react2.default.Children.only(children) : null : null;
  };

  return Route;
}(_react2.default.Component);

Route.propTypes = {
  computedMatch: _propTypes2.default.object, // private, from <Switch>
  path: _propTypes2.default.string,
  exact: _propTypes2.default.bool,
  strict: _propTypes2.default.bool,
  sensitive: _propTypes2.default.bool,
  component: _propTypes2.default.func,
  render: _propTypes2.default.func,
  children: _propTypes2.default.oneOfType([_propTypes2.default.func, _propTypes2.default.node]),
  location: _propTypes2.default.object
};
Route.contextTypes = {
  router: _propTypes2.default.shape({
    history: _propTypes2.default.object.isRequired,
    route: _propTypes2.default.object.isRequired,
    staticContext: _propTypes2.default.object
  })
};
Route.childContextTypes = {
  router: _propTypes2.default.object.isRequired
};

exports.default = Route;

/***/ }),

/***/ 192:
/*!*********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/es/DOMUtils.js ***!
  \*********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
var canUseDOM = exports.canUseDOM = !!(typeof window !== 'undefined' && window.document && window.document.createElement);

var addEventListener = exports.addEventListener = function addEventListener(node, event, listener) {
  return node.addEventListener ? node.addEventListener(event, listener, false) : node.attachEvent('on' + event, listener);
};

var removeEventListener = exports.removeEventListener = function removeEventListener(node, event, listener) {
  return node.removeEventListener ? node.removeEventListener(event, listener, false) : node.detachEvent('on' + event, listener);
};

var getConfirmation = exports.getConfirmation = function getConfirmation(message, callback) {
  return callback(window.confirm(message));
}; // eslint-disable-line no-alert

/**
 * Returns true if the HTML5 history API is supported. Taken from Modernizr.
 *
 * https://github.com/Modernizr/Modernizr/blob/master/LICENSE
 * https://github.com/Modernizr/Modernizr/blob/master/feature-detects/history.js
 * changed to avoid false negatives for Windows Phones: https://github.com/reactjs/react-router/issues/586
 */
var supportsHistory = exports.supportsHistory = function supportsHistory() {
  var ua = window.navigator.userAgent;

  if ((ua.indexOf('Android 2.') !== -1 || ua.indexOf('Android 4.0') !== -1) && ua.indexOf('Mobile Safari') !== -1 && ua.indexOf('Chrome') === -1 && ua.indexOf('Windows Phone') === -1) return false;

  return window.history && 'pushState' in window.history;
};

/**
 * Returns true if browser fires popstate on hash change.
 * IE10 and IE11 do not.
 */
var supportsPopStateOnHashChange = exports.supportsPopStateOnHashChange = function supportsPopStateOnHashChange() {
  return window.navigator.userAgent.indexOf('Trident') === -1;
};

/**
 * Returns false if using go(n) with hash history causes a full page reload.
 */
var supportsGoWithoutReloadUsingHash = exports.supportsGoWithoutReloadUsingHash = function supportsGoWithoutReloadUsingHash() {
  return window.navigator.userAgent.indexOf('Firefox') === -1;
};

/**
 * Returns true if a given popstate event is an extraneous WebKit event.
 * Accounts for the fact that Chrome on iOS fires real popstate events
 * containing undefined state when pressing the back button.
 */
var isExtraneousPopstateEvent = exports.isExtraneousPopstateEvent = function isExtraneousPopstateEvent(event) {
  return event.state === undefined && navigator.userAgent.indexOf('CriOS') === -1;
};

/***/ }),

/***/ 193:
/*!********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/urijs/src/punycode.js ***!
  \********************************************************************/
/*! dynamic exports provided */
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
	if ("function" == 'function' && _typeof(__webpack_require__(/*! !webpack amd options */ 10)) == 'object' && __webpack_require__(/*! !webpack amd options */ 10)) {
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
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/module.js */ 1)(module), __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/global.js */ 9)))

/***/ }),

/***/ 194:
/*!****************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/urijs/src/IPv6.js ***!
  \****************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {var __WEBPACK_AMD_DEFINE_FACTORY__, __WEBPACK_AMD_DEFINE_RESULT__;

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/*!
 * URI.js - Mutating URLs
 * IPv6 Support
 *
 * Version: 1.19.0
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
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/module.js */ 1)(module)))

/***/ }),

/***/ 195:
/*!******************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/urijs/src/SecondLevelDomains.js ***!
  \******************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {var __WEBPACK_AMD_DEFINE_FACTORY__, __WEBPACK_AMD_DEFINE_RESULT__;

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/*!
 * URI.js - Mutating URLs
 * Second Level Domain (SLD) Support
 *
 * Version: 1.19.0
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
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/module.js */ 1)(module)))

/***/ }),

/***/ 196:
/*!*************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/color/index.js ***!
  \*************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var colorString = __webpack_require__(/*! color-string */ 463);
var convert = __webpack_require__(/*! color-convert */ 466);

var _slice = [].slice;

var skippedModels = [
// to be honest, I don't really feel like keyword belongs in color convert, but eh.
'keyword',

// gray conflicts with some method names, and has its own method defined.
'gray',

// shouldn't really be in color-convert either...
'hex'];

var hashedModelKeys = {};
Object.keys(convert).forEach(function (model) {
	hashedModelKeys[_slice.call(convert[model].labels).sort().join('')] = model;
});

var limiters = {};

function Color(obj, model) {
	if (!(this instanceof Color)) {
		return new Color(obj, model);
	}

	if (model && model in skippedModels) {
		model = null;
	}

	if (model && !(model in convert)) {
		throw new Error('Unknown model: ' + model);
	}

	var i;
	var channels;

	if (!obj) {
		this.model = 'rgb';
		this.color = [0, 0, 0];
		this.valpha = 1;
	} else if (obj instanceof Color) {
		this.model = obj.model;
		this.color = obj.color.slice();
		this.valpha = obj.valpha;
	} else if (typeof obj === 'string') {
		var result = colorString.get(obj);
		if (result === null) {
			throw new Error('Unable to parse color from string: ' + obj);
		}

		this.model = result.model;
		channels = convert[this.model].channels;
		this.color = result.value.slice(0, channels);
		this.valpha = typeof result.value[channels] === 'number' ? result.value[channels] : 1;
	} else if (obj.length) {
		this.model = model || 'rgb';
		channels = convert[this.model].channels;
		var newArr = _slice.call(obj, 0, channels);
		this.color = zeroArray(newArr, channels);
		this.valpha = typeof obj[channels] === 'number' ? obj[channels] : 1;
	} else if (typeof obj === 'number') {
		// this is always RGB - can be converted later on.
		obj &= 0xFFFFFF;
		this.model = 'rgb';
		this.color = [obj >> 16 & 0xFF, obj >> 8 & 0xFF, obj & 0xFF];
		this.valpha = 1;
	} else {
		this.valpha = 1;

		var keys = Object.keys(obj);
		if ('alpha' in obj) {
			keys.splice(keys.indexOf('alpha'), 1);
			this.valpha = typeof obj.alpha === 'number' ? obj.alpha : 0;
		}

		var hashedKeys = keys.sort().join('');
		if (!(hashedKeys in hashedModelKeys)) {
			throw new Error('Unable to parse color from object: ' + JSON.stringify(obj));
		}

		this.model = hashedModelKeys[hashedKeys];

		var labels = convert[this.model].labels;
		var color = [];
		for (i = 0; i < labels.length; i++) {
			color.push(obj[labels[i]]);
		}

		this.color = zeroArray(color);
	}

	// perform limitations (clamping, etc.)
	if (limiters[this.model]) {
		channels = convert[this.model].channels;
		for (i = 0; i < channels; i++) {
			var limit = limiters[this.model][i];
			if (limit) {
				this.color[i] = limit(this.color[i]);
			}
		}
	}

	this.valpha = Math.max(0, Math.min(1, this.valpha));

	if (Object.freeze) {
		Object.freeze(this);
	}
}

Color.prototype = {
	toString: function toString() {
		return this.string();
	},

	toJSON: function toJSON() {
		return this[this.model]();
	},

	string: function string(places) {
		var self = this.model in colorString.to ? this : this.rgb();
		self = self.round(typeof places === 'number' ? places : 1);
		var args = self.valpha === 1 ? self.color : self.color.concat(this.valpha);
		return colorString.to[self.model](args);
	},

	percentString: function percentString(places) {
		var self = this.rgb().round(typeof places === 'number' ? places : 1);
		var args = self.valpha === 1 ? self.color : self.color.concat(this.valpha);
		return colorString.to.rgb.percent(args);
	},

	array: function array() {
		return this.valpha === 1 ? this.color.slice() : this.color.concat(this.valpha);
	},

	object: function object() {
		var result = {};
		var channels = convert[this.model].channels;
		var labels = convert[this.model].labels;

		for (var i = 0; i < channels; i++) {
			result[labels[i]] = this.color[i];
		}

		if (this.valpha !== 1) {
			result.alpha = this.valpha;
		}

		return result;
	},

	unitArray: function unitArray() {
		var rgb = this.rgb().color;
		rgb[0] /= 255;
		rgb[1] /= 255;
		rgb[2] /= 255;

		if (this.valpha !== 1) {
			rgb.push(this.valpha);
		}

		return rgb;
	},

	unitObject: function unitObject() {
		var rgb = this.rgb().object();
		rgb.r /= 255;
		rgb.g /= 255;
		rgb.b /= 255;

		if (this.valpha !== 1) {
			rgb.alpha = this.valpha;
		}

		return rgb;
	},

	round: function round(places) {
		places = Math.max(places || 0, 0);
		return new Color(this.color.map(roundToPlace(places)).concat(this.valpha), this.model);
	},

	alpha: function alpha(val) {
		if (arguments.length) {
			return new Color(this.color.concat(Math.max(0, Math.min(1, val))), this.model);
		}

		return this.valpha;
	},

	// rgb
	red: getset('rgb', 0, maxfn(255)),
	green: getset('rgb', 1, maxfn(255)),
	blue: getset('rgb', 2, maxfn(255)),

	hue: getset(['hsl', 'hsv', 'hsl', 'hwb', 'hcg'], 0, function (val) {
		return (val % 360 + 360) % 360;
	}), // eslint-disable-line brace-style

	saturationl: getset('hsl', 1, maxfn(100)),
	lightness: getset('hsl', 2, maxfn(100)),

	saturationv: getset('hsv', 1, maxfn(100)),
	value: getset('hsv', 2, maxfn(100)),

	chroma: getset('hcg', 1, maxfn(100)),
	gray: getset('hcg', 2, maxfn(100)),

	white: getset('hwb', 1, maxfn(100)),
	wblack: getset('hwb', 2, maxfn(100)),

	cyan: getset('cmyk', 0, maxfn(100)),
	magenta: getset('cmyk', 1, maxfn(100)),
	yellow: getset('cmyk', 2, maxfn(100)),
	black: getset('cmyk', 3, maxfn(100)),

	x: getset('xyz', 0, maxfn(100)),
	y: getset('xyz', 1, maxfn(100)),
	z: getset('xyz', 2, maxfn(100)),

	l: getset('lab', 0, maxfn(100)),
	a: getset('lab', 1),
	b: getset('lab', 2),

	keyword: function keyword(val) {
		if (arguments.length) {
			return new Color(val);
		}

		return convert[this.model].keyword(this.color);
	},

	hex: function hex(val) {
		if (arguments.length) {
			return new Color(val);
		}

		return colorString.to.hex(this.rgb().round().color);
	},

	rgbNumber: function rgbNumber() {
		var rgb = this.rgb().color;
		return (rgb[0] & 0xFF) << 16 | (rgb[1] & 0xFF) << 8 | rgb[2] & 0xFF;
	},

	luminosity: function luminosity() {
		// http://www.w3.org/TR/WCAG20/#relativeluminancedef
		var rgb = this.rgb().color;

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
		var rgb = this.rgb().color;
		var yiq = (rgb[0] * 299 + rgb[1] * 587 + rgb[2] * 114) / 1000;
		return yiq < 128;
	},

	light: function light() {
		return !this.dark();
	},

	negate: function negate() {
		var rgb = this.rgb();
		for (var i = 0; i < 3; i++) {
			rgb.color[i] = 255 - rgb.color[i];
		}
		return rgb;
	},

	lighten: function lighten(ratio) {
		var hsl = this.hsl();
		hsl.color[2] += hsl.color[2] * ratio;
		return hsl;
	},

	darken: function darken(ratio) {
		var hsl = this.hsl();
		hsl.color[2] -= hsl.color[2] * ratio;
		return hsl;
	},

	saturate: function saturate(ratio) {
		var hsl = this.hsl();
		hsl.color[1] += hsl.color[1] * ratio;
		return hsl;
	},

	desaturate: function desaturate(ratio) {
		var hsl = this.hsl();
		hsl.color[1] -= hsl.color[1] * ratio;
		return hsl;
	},

	whiten: function whiten(ratio) {
		var hwb = this.hwb();
		hwb.color[1] += hwb.color[1] * ratio;
		return hwb;
	},

	blacken: function blacken(ratio) {
		var hwb = this.hwb();
		hwb.color[2] += hwb.color[2] * ratio;
		return hwb;
	},

	grayscale: function grayscale() {
		// http://en.wikipedia.org/wiki/Grayscale#Converting_color_to_grayscale
		var rgb = this.rgb().color;
		var val = rgb[0] * 0.3 + rgb[1] * 0.59 + rgb[2] * 0.11;
		return Color.rgb(val, val, val);
	},

	fade: function fade(ratio) {
		return this.alpha(this.valpha - this.valpha * ratio);
	},

	opaquer: function opaquer(ratio) {
		return this.alpha(this.valpha + this.valpha * ratio);
	},

	rotate: function rotate(degrees) {
		var hsl = this.hsl();
		var hue = hsl.color[0];
		hue = (hue + degrees) % 360;
		hue = hue < 0 ? 360 + hue : hue;
		hsl.color[0] = hue;
		return hsl;
	},

	mix: function mix(mixinColor, weight) {
		// ported from sass implementation in C
		// https://github.com/sass/libsass/blob/0e6b4a2850092356aa3ece07c6b249f0221caced/functions.cpp#L209
		var color1 = mixinColor.rgb();
		var color2 = this.rgb();
		var p = weight === undefined ? 0.5 : weight;

		var w = 2 * p - 1;
		var a = color1.alpha() - color2.alpha();

		var w1 = ((w * a === -1 ? w : (w + a) / (1 + w * a)) + 1) / 2.0;
		var w2 = 1 - w1;

		return Color.rgb(w1 * color1.red() + w2 * color2.red(), w1 * color1.green() + w2 * color2.green(), w1 * color1.blue() + w2 * color2.blue(), color1.alpha() * p + color2.alpha() * (1 - p));
	}
};

// model conversion methods and static constructors
Object.keys(convert).forEach(function (model) {
	if (skippedModels.indexOf(model) !== -1) {
		return;
	}

	var channels = convert[model].channels;

	// conversion methods
	Color.prototype[model] = function () {
		if (this.model === model) {
			return new Color(this);
		}

		if (arguments.length) {
			return new Color(arguments, model);
		}

		var newAlpha = typeof arguments[channels] === 'number' ? channels : this.valpha;
		return new Color(assertArray(convert[this.model][model].raw(this.color)).concat(newAlpha), model);
	};

	// 'static' construction methods
	Color[model] = function (color) {
		if (typeof color === 'number') {
			color = zeroArray(_slice.call(arguments), channels);
		}
		return new Color(color, model);
	};
});

function roundTo(num, places) {
	return Number(num.toFixed(places));
}

function roundToPlace(places) {
	return function (num) {
		return roundTo(num, places);
	};
}

function getset(model, channel, modifier) {
	model = Array.isArray(model) ? model : [model];

	model.forEach(function (m) {
		(limiters[m] || (limiters[m] = []))[channel] = modifier;
	});

	model = model[0];

	return function (val) {
		var result;

		if (arguments.length) {
			if (modifier) {
				val = modifier(val);
			}

			result = this[model]();
			result.color[channel] = val;
			return result;
		}

		result = this[model]().color[channel];
		if (modifier) {
			result = modifier(result);
		}

		return result;
	};
}

function maxfn(max) {
	return function (v) {
		return Math.max(0, Math.min(max, v));
	};
}

function assertArray(val) {
	return Array.isArray(val) ? val : [val];
}

function zeroArray(arr, length) {
	for (var i = 0; i < length; i++) {
		if (typeof arr[i] !== 'number') {
			arr[i] = 0;
		}
	}

	return arr;
}

module.exports = Color;

/***/ }),

/***/ 197:
/*!******************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/color-name/index.js ***!
  \******************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

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

/***/ }),

/***/ 198:
/*!***************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/color-convert/conversions.js ***!
  \***************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/* MIT license */
var cssKeywords = __webpack_require__(/*! color-name */ 197);

// NOTE: conversions should only return primitive values (i.e. arrays, or
//       values that give correct `typeof` results).
//       do not use box values types (i.e. Number(), String(), etc.)

var reverseKeywords = {};
for (var key in cssKeywords) {
	if (cssKeywords.hasOwnProperty(key)) {
		reverseKeywords[cssKeywords[key]] = key;
	}
}

var convert = module.exports = {
	rgb: { channels: 3, labels: 'rgb' },
	hsl: { channels: 3, labels: 'hsl' },
	hsv: { channels: 3, labels: 'hsv' },
	hwb: { channels: 3, labels: 'hwb' },
	cmyk: { channels: 4, labels: 'cmyk' },
	xyz: { channels: 3, labels: 'xyz' },
	lab: { channels: 3, labels: 'lab' },
	lch: { channels: 3, labels: 'lch' },
	hex: { channels: 1, labels: ['hex'] },
	keyword: { channels: 1, labels: ['keyword'] },
	ansi16: { channels: 1, labels: ['ansi16'] },
	ansi256: { channels: 1, labels: ['ansi256'] },
	hcg: { channels: 3, labels: ['h', 'c', 'g'] },
	apple: { channels: 3, labels: ['r16', 'g16', 'b16'] },
	gray: { channels: 1, labels: ['gray'] }
};

// hide .channels and .labels properties
for (var model in convert) {
	if (convert.hasOwnProperty(model)) {
		if (!('channels' in convert[model])) {
			throw new Error('missing channels property: ' + model);
		}

		if (!('labels' in convert[model])) {
			throw new Error('missing channel labels property: ' + model);
		}

		if (convert[model].labels.length !== convert[model].channels) {
			throw new Error('channel and label counts mismatch: ' + model);
		}

		var channels = convert[model].channels;
		var labels = convert[model].labels;
		delete convert[model].channels;
		delete convert[model].labels;
		Object.defineProperty(convert[model], 'channels', { value: channels });
		Object.defineProperty(convert[model], 'labels', { value: labels });
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

/**
 * See https://en.m.wikipedia.org/wiki/Euclidean_distance#Squared_Euclidean_distance
 * */
function comparativeDistance(x, y) {
	return Math.pow(x[0] - y[0], 2) + Math.pow(x[1] - y[1], 2) + Math.pow(x[2] - y[2], 2);
}

convert.rgb.keyword = function (rgb) {
	var reversed = reverseKeywords[rgb];
	if (reversed) {
		return reversed;
	}

	var currentClosestDistance = Infinity;
	var currentClosestKeyword;

	for (var keyword in cssKeywords) {
		if (cssKeywords.hasOwnProperty(keyword)) {
			var value = cssKeywords[keyword];

			// Compute comparative distance
			var distance = comparativeDistance(rgb, value);

			// Check if its less, if so set as closest
			if (distance < currentClosestDistance) {
				currentClosestDistance = distance;
				currentClosestKeyword = keyword;
			}
		}
	}

	return currentClosestKeyword;
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
	var match = args.toString(16).match(/[a-f0-9]{6}|[a-f0-9]{3}/i);
	if (!match) {
		return [0, 0, 0];
	}

	var colorString = match[0];

	if (match[0].length === 3) {
		colorString = colorString.split('').map(function (char) {
			return char + char;
		}).join('');
	}

	var integer = parseInt(colorString, 16);
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

convert.gray.rgb = function (args) {
	return [args[0] / 100 * 255, args[0] / 100 * 255, args[0] / 100 * 255];
};

convert.gray.hsl = convert.gray.hsv = function (args) {
	return [0, 0, args[0]];
};

convert.gray.hwb = function (gray) {
	return [0, 100, gray[0]];
};

convert.gray.cmyk = function (gray) {
	return [0, 0, 0, gray[0]];
};

convert.gray.lab = function (gray) {
	return [gray[0], 0, 0];
};

convert.gray.hex = function (gray) {
	var val = Math.round(gray[0] / 100 * 255) & 0xFF;
	var integer = (val << 16) + (val << 8) + val;

	var string = integer.toString(16).toUpperCase();
	return '000000'.substring(string.length) + string;
};

convert.rgb.gray = function (rgb) {
	var val = (rgb[0] + rgb[1] + rgb[2]) / 3;
	return [val / 255 * 100];
};

/***/ }),

/***/ 199:
/*!**********************************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-experiment-page-tsne-plot/lib/plotloader/PlotLoader.js ***!
  \**********************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _urijs = __webpack_require__(/*! urijs */ 15);

var _urijs2 = _interopRequireDefault(_urijs);

var _LoadingOverlay = __webpack_require__(/*! ./LoadingOverlay */ 468);

var _LoadingOverlay2 = _interopRequireDefault(_LoadingOverlay);

var _ScatterPlot = __webpack_require__(/*! ./ScatterPlot */ 470);

var _ScatterPlot2 = _interopRequireDefault(_ScatterPlot);

var _SeriesPropTypes = __webpack_require__(/*! ./SeriesPropTypes */ 201);

var _SeriesPropTypes2 = _interopRequireDefault(_SeriesPropTypes);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var PlotLoader = function PlotLoader(_ref) {
  var loading = _ref.loading,
      series = _ref.series,
      errorMessage = _ref.errorMessage,
      highchartsConfig = _ref.highchartsConfig,
      resourcesUrl = _ref.resourcesUrl,
      children = _ref.children,
      wrapperClassName = _ref.wrapperClassName,
      chartClassName = _ref.chartClassName;
  return errorMessage ? _react2.default.createElement('div', { className: wrapperClassName + ' text-center scxa-error' }, _react2.default.createElement('p', null, errorMessage)) : _react2.default.createElement('div', { style: { position: 'relative' }, className: wrapperClassName }, _react2.default.createElement(_ScatterPlot2.default, { wrapperClassName: wrapperClassName,
    chartClassName: chartClassName,
    series: series,
    highchartsConfig: highchartsConfig,
    children: children
  }), _react2.default.createElement(_LoadingOverlay2.default, { show: loading,
    resourcesUrl: resourcesUrl
  }));
};

PlotLoader.propTypes = {
  loading: _propTypes2.default.bool.isRequired,
  series: _SeriesPropTypes2.default,
  errorMessage: _propTypes2.default.string,
  highchartsConfig: _propTypes2.default.object,
  chartClassName: _propTypes2.default.string,
  resourcesUrl: _propTypes2.default.string,
  children: _propTypes2.default.object
};

PlotLoader.defaultProps = {
  wrapperClassName: '',
  chartClassName: '',
  highchartsConfig: {},
  resourcesUrl: ''
};

exports.default = PlotLoader;

/***/ }),

/***/ 2:
/*!******************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/prop-types/index.js ***!
  \******************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/**
 * Copyright (c) 2013-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

if (true) {
  var REACT_ELEMENT_TYPE = typeof Symbol === 'function' && Symbol.for && Symbol.for('react.element') || 0xeac7;

  var isValidElement = function isValidElement(object) {
    return (typeof object === 'undefined' ? 'undefined' : _typeof(object)) === 'object' && object !== null && object.$$typeof === REACT_ELEMENT_TYPE;
  };

  // By explicitly using `prop-types` you are opting into new development behavior.
  // http://fb.me/prop-types-in-prod
  var throwOnDirectAccess = true;
  module.exports = __webpack_require__(/*! ./factoryWithTypeCheckers */ 428)(isValidElement, throwOnDirectAccess);
} else {
  // By explicitly using `prop-types` you are opting into new production behavior.
  // http://fb.me/prop-types-in-prod
  module.exports = require('./factoryWithThrowingShims')();
}

/***/ }),

/***/ 200:
/*!***********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/highcharts/highcharts.js ***!
  \***********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/*
 Highcharts JS v5.0.14 (2017-07-28)

 (c) 2009-2016 Torstein Honsi

 License: www.highcharts.com/license
*/
(function (M, S) {
  "object" === ( false ? "undefined" : _typeof(module)) && module.exports ? module.exports = M.document ? S(M) : S : M.Highcharts = S(M);
})("undefined" !== typeof window ? window : undefined, function (M) {
  M = function () {
    var a = window,
        C = a.document,
        A = a.navigator && a.navigator.userAgent || "",
        F = C && C.createElementNS && !!C.createElementNS("http://www.w3.org/2000/svg", "svg").createSVGRect,
        E = /(edge|msie|trident)/i.test(A) && !window.opera,
        m = !F,
        f = /Firefox/.test(A),
        l = f && 4 > parseInt(A.split("Firefox/")[1], 10);return a.Highcharts ? a.Highcharts.error(16, !0) : { product: "Highcharts",
      version: "5.0.14", deg2rad: 2 * Math.PI / 360, doc: C, hasBidiBug: l, hasTouch: C && void 0 !== C.documentElement.ontouchstart, isMS: E, isWebKit: /AppleWebKit/.test(A), isFirefox: f, isTouchDevice: /(Mobile|Android|Windows Phone)/.test(A), SVG_NS: "http://www.w3.org/2000/svg", chartCount: 0, seriesTypes: {}, symbolSizes: {}, svg: F, vml: m, win: a, marginNames: ["plotTop", "marginRight", "marginBottom", "plotLeft"], noop: function noop() {}, charts: [] };
  }();(function (a) {
    var C = [],
        A = a.charts,
        F = a.doc,
        E = a.win;a.error = function (m, f) {
      m = a.isNumber(m) ? "Highcharts error #" + m + ": www.highcharts.com/errors/" + m : m;if (f) throw Error(m);E.console && console.log(m);
    };a.Fx = function (a, f, l) {
      this.options = f;this.elem = a;this.prop = l;
    };a.Fx.prototype = { dSetter: function dSetter() {
        var a = this.paths[0],
            f = this.paths[1],
            l = [],
            r = this.now,
            u = a.length,
            t;if (1 === r) l = this.toD;else if (u === f.length && 1 > r) for (; u--;) {
          t = parseFloat(a[u]), l[u] = isNaN(t) ? a[u] : r * parseFloat(f[u] - t) + t;
        } else l = f;this.elem.attr("d", l, null, !0);
      }, update: function update() {
        var a = this.elem,
            f = this.prop,
            l = this.now,
            r = this.options.step;if (this[f + "Setter"]) this[f + "Setter"]();else a.attr ? a.element && a.attr(f, l, null, !0) : a.style[f] = l + this.unit;r && r.call(a, l, this);
      }, run: function run(a, f, l) {
        var r = this,
            m = function m(a) {
          return m.stopped ? !1 : r.step(a);
        },
            t;this.startTime = +new Date();this.start = a;this.end = f;this.unit = l;this.now = this.start;this.pos = 0;m.elem = this.elem;m.prop = this.prop;m() && 1 === C.push(m) && (m.timerId = setInterval(function () {
          for (t = 0; t < C.length; t++) {
            C[t]() || C.splice(t--, 1);
          }C.length || clearInterval(m.timerId);
        }, 13));
      }, step: function step(m) {
        var f = +new Date(),
            l,
            r = this.options,
            u = this.elem,
            t = r.complete,
            g = r.duration,
            d = r.curAnim;u.attr && !u.element ? m = !1 : m || f >= g + this.startTime ? (this.now = this.end, this.pos = 1, this.update(), l = d[this.prop] = !0, a.objectEach(d, function (a) {
          !0 !== a && (l = !1);
        }), l && t && t.call(u), m = !1) : (this.pos = r.easing((f - this.startTime) / g), this.now = this.start + (this.end - this.start) * this.pos, this.update(), m = !0);return m;
      }, initPath: function initPath(m, f, l) {
        function r(a) {
          var c, e;for (n = a.length; n--;) {
            c = "M" === a[n] || "L" === a[n], e = /[a-zA-Z]/.test(a[n + 3]), c && e && a.splice(n + 1, 0, a[n + 1], a[n + 2], a[n + 1], a[n + 2]);
          }
        }
        function u(a, c) {
          for (; a.length < v;) {
            a[0] = c[v - a.length];var b = a.slice(0, e);[].splice.apply(a, [0, 0].concat(b));D && (b = a.slice(a.length - e), [].splice.apply(a, [a.length, 0].concat(b)), n--);
          }a[0] = "M";
        }function t(a, c) {
          for (var q = (v - a.length) / e; 0 < q && q--;) {
            y = a.slice().splice(a.length / J - e, e * J), y[0] = c[v - e - q * e], b && (y[e - 6] = y[e - 2], y[e - 5] = y[e - 1]), [].splice.apply(a, [a.length / J, 0].concat(y)), D && q--;
          }
        }f = f || "";var g,
            d = m.startX,
            k = m.endX,
            b = -1 < f.indexOf("C"),
            e = b ? 7 : 3,
            v,
            y,
            n;f = f.split(" ");l = l.slice();var D = m.isArea,
            J = D ? 2 : 1,
            c;b && (r(f), r(l));if (d && k) {
          for (n = 0; n < d.length; n++) {
            if (d[n] === k[0]) {
              g = n;break;
            } else if (d[0] === k[k.length - d.length + n]) {
              g = n;c = !0;break;
            }
          }void 0 === g && (f = []);
        }f.length && a.isNumber(g) && (v = l.length + g * J * e, c ? (u(f, l), t(l, f)) : (u(l, f), t(f, l)));return [f, l];
      } };a.Fx.prototype.fillSetter = a.Fx.prototype.strokeSetter = function () {
      this.elem.attr(this.prop, a.color(this.start).tweenTo(a.color(this.end), this.pos), null, !0);
    };a.extend = function (a, f) {
      var m;a || (a = {});for (m in f) {
        a[m] = f[m];
      }return a;
    };a.merge = function () {
      var m,
          f = arguments,
          l,
          r = {},
          u = function u(f, g) {
        "object" !== (typeof f === "undefined" ? "undefined" : _typeof(f)) && (f = {});a.objectEach(g, function (d, k) {
          !a.isObject(d, !0) || a.isClass(d) || a.isDOMElement(d) ? f[k] = g[k] : f[k] = u(f[k] || {}, d);
        });return f;
      };!0 === f[0] && (r = f[1], f = Array.prototype.slice.call(f, 2));l = f.length;for (m = 0; m < l; m++) {
        r = u(r, f[m]);
      }return r;
    };a.pInt = function (a, f) {
      return parseInt(a, f || 10);
    };a.isString = function (a) {
      return "string" === typeof a;
    };a.isArray = function (a) {
      a = Object.prototype.toString.call(a);return "[object Array]" === a || "[object Array Iterator]" === a;
    };a.isObject = function (m, f) {
      return !!m && "object" === (typeof m === "undefined" ? "undefined" : _typeof(m)) && (!f || !a.isArray(m));
    };a.isDOMElement = function (m) {
      return a.isObject(m) && "number" === typeof m.nodeType;
    };a.isClass = function (m) {
      var f = m && m.constructor;return !(!a.isObject(m, !0) || a.isDOMElement(m) || !f || !f.name || "Object" === f.name);
    };a.isNumber = function (a) {
      return "number" === typeof a && !isNaN(a);
    };a.erase = function (a, f) {
      for (var m = a.length; m--;) {
        if (a[m] === f) {
          a.splice(m, 1);break;
        }
      }
    };a.defined = function (a) {
      return void 0 !== a && null !== a;
    };a.attr = function (m, f, l) {
      var r;a.isString(f) ? a.defined(l) ? m.setAttribute(f, l) : m && m.getAttribute && (r = m.getAttribute(f)) : a.defined(f) && a.isObject(f) && a.objectEach(f, function (a, f) {
        m.setAttribute(f, a);
      });return r;
    };a.splat = function (m) {
      return a.isArray(m) ? m : [m];
    };a.syncTimeout = function (a, f, l) {
      if (f) return setTimeout(a, f, l);a.call(0, l);
    };a.pick = function () {
      var a = arguments,
          f,
          l,
          r = a.length;for (f = 0; f < r; f++) {
        if (l = a[f], void 0 !== l && null !== l) return l;
      }
    };a.css = function (m, f) {
      a.isMS && !a.svg && f && void 0 !== f.opacity && (f.filter = "alpha(opacity\x3d" + 100 * f.opacity + ")");a.extend(m.style, f);
    };a.createElement = function (m, f, l, r, u) {
      m = F.createElement(m);var t = a.css;f && a.extend(m, f);u && t(m, { padding: 0, border: "none", margin: 0 });l && t(m, l);r && r.appendChild(m);return m;
    };a.extendClass = function (m, f) {
      var l = function l() {};l.prototype = new m();a.extend(l.prototype, f);return l;
    };a.pad = function (a, f, l) {
      return Array((f || 2) + 1 - String(a).length).join(l || 0) + a;
    };a.relativeLength = function (a, f, l) {
      return (/%$/.test(a) ? f * parseFloat(a) / 100 + (l || 0) : parseFloat(a)
      );
    };a.wrap = function (a, f, l) {
      var r = a[f];a[f] = function () {
        var a = Array.prototype.slice.call(arguments),
            f = arguments,
            g = this;g.proceed = function () {
          r.apply(g, arguments.length ? arguments : f);
        };a.unshift(r);a = l.apply(this, a);g.proceed = null;return a;
      };
    };a.getTZOffset = function (m) {
      var f = a.Date;return 6E4 * (f.hcGetTimezoneOffset && f.hcGetTimezoneOffset(m) || f.hcTimezoneOffset || 0);
    };a.dateFormat = function (m, f, l) {
      if (!a.defined(f) || isNaN(f)) return a.defaultOptions.lang.invalidDate || "";m = a.pick(m, "%Y-%m-%d %H:%M:%S");var r = a.Date,
          u = new r(f - a.getTZOffset(f)),
          t = u[r.hcGetHours](),
          g = u[r.hcGetDay](),
          d = u[r.hcGetDate](),
          k = u[r.hcGetMonth](),
          b = u[r.hcGetFullYear](),
          e = a.defaultOptions.lang,
          v = e.weekdays,
          y = e.shortWeekdays,
          n = a.pad,
          r = a.extend({ a: y ? y[g] : v[g].substr(0, 3), A: v[g], d: n(d), e: n(d, 2, " "), w: g, b: e.shortMonths[k], B: e.months[k], m: n(k + 1), y: b.toString().substr(2, 2), Y: b, H: n(t), k: t, I: n(t % 12 || 12), l: t % 12 || 12, M: n(u[r.hcGetMinutes]()), p: 12 > t ? "AM" : "PM", P: 12 > t ? "am" : "pm", S: n(u.getSeconds()), L: n(Math.round(f % 1E3), 3) }, a.dateFormats);a.objectEach(r, function (a, e) {
        for (; -1 !== m.indexOf("%" + e);) {
          m = m.replace("%" + e, "function" === typeof a ? a(f) : a);
        }
      });return l ? m.substr(0, 1).toUpperCase() + m.substr(1) : m;
    };a.formatSingle = function (m, f) {
      var l = /\.([0-9])/,
          r = a.defaultOptions.lang;/f$/.test(m) ? (l = (l = m.match(l)) ? l[1] : -1, null !== f && (f = a.numberFormat(f, l, r.decimalPoint, -1 < m.indexOf(",") ? r.thousandsSep : ""))) : f = a.dateFormat(m, f);return f;
    };a.format = function (m, f) {
      for (var l = "{", r = !1, u, t, g, d, k = [], b; m;) {
        l = m.indexOf(l);if (-1 === l) break;u = m.slice(0, l);if (r) {
          u = u.split(":");t = u.shift().split(".");d = t.length;b = f;for (g = 0; g < d; g++) {
            b = b[t[g]];
          }u.length && (b = a.formatSingle(u.join(":"), b));k.push(b);
        } else k.push(u);
        m = m.slice(l + 1);l = (r = !r) ? "}" : "{";
      }k.push(m);return k.join("");
    };a.getMagnitude = function (a) {
      return Math.pow(10, Math.floor(Math.log(a) / Math.LN10));
    };a.normalizeTickInterval = function (m, f, l, r, u) {
      var t,
          g = m;l = a.pick(l, 1);t = m / l;f || (f = u ? [1, 1.2, 1.5, 2, 2.5, 3, 4, 5, 6, 8, 10] : [1, 2, 2.5, 5, 10], !1 === r && (1 === l ? f = a.grep(f, function (a) {
        return 0 === a % 1;
      }) : .1 >= l && (f = [1 / l])));for (r = 0; r < f.length && !(g = f[r], u && g * l >= m || !u && t <= (f[r] + (f[r + 1] || f[r])) / 2); r++) {}return g = a.correctFloat(g * l, -Math.round(Math.log(.001) / Math.LN10));
    };a.stableSort = function (a, f) {
      var l = a.length,
          r,
          m;for (m = 0; m < l; m++) {
        a[m].safeI = m;
      }a.sort(function (a, g) {
        r = f(a, g);return 0 === r ? a.safeI - g.safeI : r;
      });for (m = 0; m < l; m++) {
        delete a[m].safeI;
      }
    };a.arrayMin = function (a) {
      for (var f = a.length, l = a[0]; f--;) {
        a[f] < l && (l = a[f]);
      }return l;
    };a.arrayMax = function (a) {
      for (var f = a.length, l = a[0]; f--;) {
        a[f] > l && (l = a[f]);
      }return l;
    };a.destroyObjectProperties = function (m, f) {
      a.objectEach(m, function (a, r) {
        a && a !== f && a.destroy && a.destroy();delete m[r];
      });
    };a.discardElement = function (m) {
      var f = a.garbageBin;f || (f = a.createElement("div"));
      m && f.appendChild(m);f.innerHTML = "";
    };a.correctFloat = function (a, f) {
      return parseFloat(a.toPrecision(f || 14));
    };a.setAnimation = function (m, f) {
      f.renderer.globalAnimation = a.pick(m, f.options.chart.animation, !0);
    };a.animObject = function (m) {
      return a.isObject(m) ? a.merge(m) : { duration: m ? 500 : 0 };
    };a.timeUnits = { millisecond: 1, second: 1E3, minute: 6E4, hour: 36E5, day: 864E5, week: 6048E5, month: 24192E5, year: 314496E5 };a.numberFormat = function (m, f, l, r) {
      m = +m || 0;f = +f;var u = a.defaultOptions.lang,
          t = (m.toString().split(".")[1] || "").split("e")[0].length,
          g,
          d,
          k = m.toString().split("e");-1 === f ? f = Math.min(t, 20) : a.isNumber(f) || (f = 2);d = (Math.abs(k[1] ? k[0] : m) + Math.pow(10, -Math.max(f, t) - 1)).toFixed(f);t = String(a.pInt(d));g = 3 < t.length ? t.length % 3 : 0;l = a.pick(l, u.decimalPoint);r = a.pick(r, u.thousandsSep);m = (0 > m ? "-" : "") + (g ? t.substr(0, g) + r : "");m += t.substr(g).replace(/(\d{3})(?=\d)/g, "$1" + r);f && (m += l + d.slice(-f));k[1] && (m += "e" + k[1]);return m;
    };Math.easeInOutSine = function (a) {
      return -.5 * (Math.cos(Math.PI * a) - 1);
    };a.getStyle = function (m, f, l) {
      if ("width" === f) return Math.min(m.offsetWidth, m.scrollWidth) - a.getStyle(m, "padding-left") - a.getStyle(m, "padding-right");if ("height" === f) return Math.min(m.offsetHeight, m.scrollHeight) - a.getStyle(m, "padding-top") - a.getStyle(m, "padding-bottom");if (m = E.getComputedStyle(m, void 0)) m = m.getPropertyValue(f), a.pick(l, !0) && (m = a.pInt(m));return m;
    };a.inArray = function (a, f) {
      return f.indexOf ? f.indexOf(a) : [].indexOf.call(f, a);
    };a.grep = function (a, f) {
      return [].filter.call(a, f);
    };a.find = function (a, f) {
      return [].find.call(a, f);
    };a.map = function (a, f) {
      for (var l = [], r = 0, m = a.length; r < m; r++) {
        l[r] = f.call(a[r], a[r], r, a);
      }return l;
    };a.offset = function (a) {
      var f = F.documentElement;a = a.getBoundingClientRect();return { top: a.top + (E.pageYOffset || f.scrollTop) - (f.clientTop || 0), left: a.left + (E.pageXOffset || f.scrollLeft) - (f.clientLeft || 0) };
    };a.stop = function (a, f) {
      for (var l = C.length; l--;) {
        C[l].elem !== a || f && f !== C[l].prop || (C[l].stopped = !0);
      }
    };a.each = function (a, f, l) {
      return Array.prototype.forEach.call(a, f, l);
    };a.objectEach = function (a, f, l) {
      for (var r in a) {
        a.hasOwnProperty(r) && f.call(l, a[r], r, a);
      }
    };
    a.addEvent = function (m, f, l) {
      function r(a) {
        a.target = a.srcElement || E;l.call(m, a);
      }var u = m.hcEvents = m.hcEvents || {};m.addEventListener ? m.addEventListener(f, l, !1) : m.attachEvent && (m.hcEventsIE || (m.hcEventsIE = {}), l.hcGetKey || (l.hcGetKey = a.uniqueKey()), m.hcEventsIE[l.hcGetKey] = r, m.attachEvent("on" + f, r));u[f] || (u[f] = []);u[f].push(l);return function () {
        a.removeEvent(m, f, l);
      };
    };a.removeEvent = function (m, f, l) {
      function r(a, b) {
        m.removeEventListener ? m.removeEventListener(a, b, !1) : m.attachEvent && (b = m.hcEventsIE[b.hcGetKey], m.detachEvent("on" + a, b));
      }function u() {
        var d, b;m.nodeName && (f ? (d = {}, d[f] = !0) : d = g, a.objectEach(d, function (a, d) {
          if (g[d]) for (b = g[d].length; b--;) {
            r(d, g[d][b]);
          }
        }));
      }var t,
          g = m.hcEvents,
          d;g && (f ? (t = g[f] || [], l ? (d = a.inArray(l, t), -1 < d && (t.splice(d, 1), g[f] = t), r(f, l)) : (u(), g[f] = [])) : (u(), m.hcEvents = {}));
    };a.fireEvent = function (m, f, l, r) {
      var u;u = m.hcEvents;var t, g;l = l || {};if (F.createEvent && (m.dispatchEvent || m.fireEvent)) u = F.createEvent("Events"), u.initEvent(f, !0, !0), a.extend(u, l), m.dispatchEvent ? m.dispatchEvent(u) : m.fireEvent(f, u);else if (u) for (u = u[f] || [], t = u.length, l.target || a.extend(l, { preventDefault: function preventDefault() {
          l.defaultPrevented = !0;
        }, target: m, type: f }), f = 0; f < t; f++) {
        (g = u[f]) && !1 === g.call(m, l) && l.preventDefault();
      }r && !l.defaultPrevented && r(l);
    };a.animate = function (m, f, l) {
      var r,
          u = "",
          t,
          g,
          d;a.isObject(l) || (d = arguments, l = { duration: d[2], easing: d[3], complete: d[4] });a.isNumber(l.duration) || (l.duration = 400);l.easing = "function" === typeof l.easing ? l.easing : Math[l.easing] || Math.easeInOutSine;l.curAnim = a.merge(f);a.objectEach(f, function (d, b) {
        a.stop(m, b);g = new a.Fx(m, l, b);t = null;"d" === b ? (g.paths = g.initPath(m, m.d, f.d), g.toD = f.d, r = 0, t = 1) : m.attr ? r = m.attr(b) : (r = parseFloat(a.getStyle(m, b)) || 0, "opacity" !== b && (u = "px"));t || (t = d);t && t.match && t.match("px") && (t = t.replace(/px/g, ""));g.run(r, t, u);
      });
    };a.seriesType = function (m, f, l, r, u) {
      var t = a.getOptions(),
          g = a.seriesTypes;t.plotOptions[m] = a.merge(t.plotOptions[f], l);g[m] = a.extendClass(g[f] || function () {}, r);g[m].prototype.type = m;u && (g[m].prototype.pointClass = a.extendClass(a.Point, u));return g[m];
    };a.uniqueKey = function () {
      var a = Math.random().toString(36).substring(2, 9),
          f = 0;return function () {
        return "highcharts-" + a + "-" + f++;
      };
    }();E.jQuery && (E.jQuery.fn.highcharts = function () {
      var m = [].slice.call(arguments);if (this[0]) return m[0] ? (new a[a.isString(m[0]) ? m.shift() : "Chart"](this[0], m[0], m[1]), this) : A[a.attr(this[0], "data-highcharts-chart")];
    });F && !F.defaultView && (a.getStyle = function (m, f) {
      var l = { width: "clientWidth", height: "clientHeight" }[f];if (m.style[f]) return a.pInt(m.style[f]);"opacity" === f && (f = "filter");if (l) return m.style.zoom = 1, Math.max(m[l] - 2 * a.getStyle(m, "padding"), 0);m = m.currentStyle[f.replace(/\-(\w)/g, function (a, f) {
        return f.toUpperCase();
      })];"filter" === f && (m = m.replace(/alpha\(opacity=([0-9]+)\)/, function (a, f) {
        return f / 100;
      }));return "" === m ? 1 : a.pInt(m);
    });Array.prototype.forEach || (a.each = function (a, f, l) {
      for (var r = 0, m = a.length; r < m; r++) {
        if (!1 === f.call(l, a[r], r, a)) return r;
      }
    });Array.prototype.indexOf || (a.inArray = function (a, f) {
      var l,
          r = 0;if (f) for (l = f.length; r < l; r++) {
        if (f[r] === a) return r;
      }return -1;
    });Array.prototype.filter || (a.grep = function (a, f) {
      for (var l = [], r = 0, m = a.length; r < m; r++) {
        f(a[r], r) && l.push(a[r]);
      }return l;
    });Array.prototype.find || (a.find = function (a, f) {
      var l,
          r = a.length;for (l = 0; l < r; l++) {
        if (f(a[l], l)) return a[l];
      }
    });
  })(M);(function (a) {
    var C = a.each,
        A = a.isNumber,
        F = a.map,
        E = a.merge,
        m = a.pInt;a.Color = function (f) {
      if (!(this instanceof a.Color)) return new a.Color(f);this.init(f);
    };a.Color.prototype = { parsers: [{ regex: /rgba\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]?(?:\.[0-9]+)?)\s*\)/, parse: function parse(a) {
          return [m(a[1]), m(a[2]), m(a[3]), parseFloat(a[4], 10)];
        } }, { regex: /rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/, parse: function parse(a) {
          return [m(a[1]), m(a[2]), m(a[3]), 1];
        } }], names: { none: "rgba(255,255,255,0)", white: "#ffffff", black: "#000000" }, init: function init(f) {
        var l, r, m, t;if ((this.input = f = this.names[f && f.toLowerCase ? f.toLowerCase() : ""] || f) && f.stops) this.stops = F(f.stops, function (g) {
          return new a.Color(g[1]);
        });else if (f && "#" === f.charAt() && (l = f.length, f = parseInt(f.substr(1), 16), 7 === l ? r = [(f & 16711680) >> 16, (f & 65280) >> 8, f & 255, 1] : 4 === l && (r = [(f & 3840) >> 4 | (f & 3840) >> 8, (f & 240) >> 4 | f & 240, (f & 15) << 4 | f & 15, 1])), !r) for (m = this.parsers.length; m-- && !r;) {
          t = this.parsers[m], (l = t.regex.exec(f)) && (r = t.parse(l));
        }this.rgba = r || [];
      }, get: function get(a) {
        var f = this.input,
            r = this.rgba,
            m;this.stops ? (m = E(f), m.stops = [].concat(m.stops), C(this.stops, function (f, g) {
          m.stops[g] = [m.stops[g][0], f.get(a)];
        })) : m = r && A(r[0]) ? "rgb" === a || !a && 1 === r[3] ? "rgb(" + r[0] + "," + r[1] + "," + r[2] + ")" : "a" === a ? r[3] : "rgba(" + r.join(",") + ")" : f;return m;
      }, brighten: function brighten(a) {
        var f,
            r = this.rgba;
        if (this.stops) C(this.stops, function (f) {
          f.brighten(a);
        });else if (A(a) && 0 !== a) for (f = 0; 3 > f; f++) {
          r[f] += m(255 * a), 0 > r[f] && (r[f] = 0), 255 < r[f] && (r[f] = 255);
        }return this;
      }, setOpacity: function setOpacity(a) {
        this.rgba[3] = a;return this;
      }, tweenTo: function tweenTo(a, l) {
        var f, m;a.rgba.length ? (f = this.rgba, a = a.rgba, m = 1 !== a[3] || 1 !== f[3], a = (m ? "rgba(" : "rgb(") + Math.round(a[0] + (f[0] - a[0]) * (1 - l)) + "," + Math.round(a[1] + (f[1] - a[1]) * (1 - l)) + "," + Math.round(a[2] + (f[2] - a[2]) * (1 - l)) + (m ? "," + (a[3] + (f[3] - a[3]) * (1 - l)) : "") + ")") : a = a.input || "none";return a;
      } };a.color = function (f) {
      return new a.Color(f);
    };
  })(M);(function (a) {
    var C,
        A,
        F = a.addEvent,
        E = a.animate,
        m = a.attr,
        f = a.charts,
        l = a.color,
        r = a.css,
        u = a.createElement,
        t = a.defined,
        g = a.deg2rad,
        d = a.destroyObjectProperties,
        k = a.doc,
        b = a.each,
        e = a.extend,
        v = a.erase,
        y = a.grep,
        n = a.hasTouch,
        D = a.inArray,
        J = a.isArray,
        c = a.isFirefox,
        G = a.isMS,
        q = a.isObject,
        B = a.isString,
        K = a.isWebKit,
        p = a.merge,
        z = a.noop,
        I = a.objectEach,
        L = a.pick,
        h = a.pInt,
        w = a.removeEvent,
        P = a.stop,
        H = a.svg,
        O = a.SVG_NS,
        Q = a.symbolSizes,
        R = a.win;C = a.SVGElement = function () {
      return this;
    };e(C.prototype, { opacity: 1, SVG_NS: O, textProps: "direction fontSize fontWeight fontFamily fontStyle color lineHeight width textAlign textDecoration textOverflow textOutline".split(" "), init: function init(a, h) {
        this.element = "span" === h ? u(h) : k.createElementNS(this.SVG_NS, h);this.renderer = a;
      }, animate: function animate(x, h, c) {
        h = a.animObject(L(h, this.renderer.globalAnimation, !0));0 !== h.duration ? (c && (h.complete = c), E(this, x, h)) : (this.attr(x, null, c), h.step && h.step.call(this));return this;
      }, colorGradient: function colorGradient(x, h, c) {
        var w = this.renderer,
            e,
            q,
            N,
            d,
            n,
            g,
            k,
            H,
            G,
            v,
            z = [],
            f;x.radialGradient ? q = "radialGradient" : x.linearGradient && (q = "linearGradient");q && (N = x[q], n = w.gradients, k = x.stops, v = c.radialReference, J(N) && (x[q] = N = { x1: N[0], y1: N[1], x2: N[2], y2: N[3], gradientUnits: "userSpaceOnUse" }), "radialGradient" === q && v && !t(N.gradientUnits) && (d = N, N = p(N, w.getRadialAttr(v, d), { gradientUnits: "userSpaceOnUse" })), I(N, function (a, x) {
          "id" !== x && z.push(x, a);
        }), I(k, function (a) {
          z.push(a);
        }), z = z.join(","), n[z] ? v = n[z].attr("id") : (N.id = v = a.uniqueKey(), n[z] = g = w.createElement(q).attr(N).add(w.defs), g.radAttr = d, g.stops = [], b(k, function (x) {
          0 === x[1].indexOf("rgba") ? (e = a.color(x[1]), H = e.get("rgb"), G = e.get("a")) : (H = x[1], G = 1);x = w.createElement("stop").attr({ offset: x[0], "stop-color": H, "stop-opacity": G }).add(g);g.stops.push(x);
        })), f = "url(" + w.url + "#" + v + ")", c.setAttribute(h, f), c.gradient = z, x.toString = function () {
          return f;
        });
      }, applyTextOutline: function applyTextOutline(x) {
        var h = this.element,
            c,
            w,
            p,
            e,
            q;-1 !== x.indexOf("contrast") && (x = x.replace(/contrast/g, this.renderer.getContrast(h.style.fill)));x = x.split(" ");w = x[x.length - 1];
        if ((p = x[0]) && "none" !== p && a.svg) {
          this.fakeTS = !0;x = [].slice.call(h.getElementsByTagName("tspan"));this.ySetter = this.xSetter;p = p.replace(/(^[\d\.]+)(.*?)$/g, function (a, x, h) {
            return 2 * x + h;
          });for (q = x.length; q--;) {
            c = x[q], "highcharts-text-outline" === c.getAttribute("class") && v(x, h.removeChild(c));
          }e = h.firstChild;b(x, function (a, x) {
            0 === x && (a.setAttribute("x", h.getAttribute("x")), x = h.getAttribute("y"), a.setAttribute("y", x || 0), null === x && h.setAttribute("y", 0));a = a.cloneNode(1);m(a, { "class": "highcharts-text-outline",
              fill: w, stroke: w, "stroke-width": p, "stroke-linejoin": "round" });h.insertBefore(a, e);
          });
        }
      }, attr: function attr(a, h, c, w) {
        var x,
            p = this.element,
            e,
            q = this,
            b,
            N;"string" === typeof a && void 0 !== h && (x = a, a = {}, a[x] = h);"string" === typeof a ? q = (this[a + "Getter"] || this._defaultGetter).call(this, a, p) : (I(a, function (x, h) {
          b = !1;w || P(this, h);this.symbolName && /^(x|y|width|height|r|start|end|innerR|anchorX|anchorY)$/.test(h) && (e || (this.symbolAttr(a), e = !0), b = !0);!this.rotation || "x" !== h && "y" !== h || (this.doTransform = !0);b || (N = this[h + "Setter"] || this._defaultSetter, N.call(this, x, h, p), this.shadows && /^(width|height|visibility|x|y|d|transform|cx|cy|r)$/.test(h) && this.updateShadows(h, x, N));
        }, this), this.afterSetters());c && c();return q;
      }, afterSetters: function afterSetters() {
        this.doTransform && (this.updateTransform(), this.doTransform = !1);
      }, updateShadows: function updateShadows(a, h, c) {
        for (var x = this.shadows, w = x.length; w--;) {
          c.call(x[w], "height" === a ? Math.max(h - (x[w].cutHeight || 0), 0) : "d" === a ? this.d : h, a, x[w]);
        }
      }, addClass: function addClass(a, h) {
        var x = this.attr("class") || "";-1 === x.indexOf(a) && (h || (a = (x + (x ? " " : "") + a).replace("  ", " ")), this.attr("class", a));return this;
      }, hasClass: function hasClass(a) {
        return -1 !== D(a, (this.attr("class") || "").split(" "));
      }, removeClass: function removeClass(a) {
        return this.attr("class", (this.attr("class") || "").replace(a, ""));
      }, symbolAttr: function symbolAttr(a) {
        var x = this;b("x y r start end width height innerR anchorX anchorY".split(" "), function (h) {
          x[h] = L(a[h], x[h]);
        });x.attr({ d: x.renderer.symbols[x.symbolName](x.x, x.y, x.width, x.height, x) });
      }, clip: function clip(a) {
        return this.attr("clip-path", a ? "url(" + this.renderer.url + "#" + a.id + ")" : "none");
      }, crisp: function crisp(a, h) {
        var x = this,
            c = {},
            w;h = h || a.strokeWidth || 0;w = Math.round(h) % 2 / 2;a.x = Math.floor(a.x || x.x || 0) + w;a.y = Math.floor(a.y || x.y || 0) + w;a.width = Math.floor((a.width || x.width || 0) - 2 * w);a.height = Math.floor((a.height || x.height || 0) - 2 * w);t(a.strokeWidth) && (a.strokeWidth = h);I(a, function (a, h) {
          x[h] !== a && (x[h] = c[h] = a);
        });return c;
      }, css: function css(a) {
        var x = this.styles,
            c = {},
            w = this.element,
            p,
            q = "",
            b,
            d = !x,
            n = ["textOutline", "textOverflow", "width"];a && a.color && (a.fill = a.color);
        x && I(a, function (a, h) {
          a !== x[h] && (c[h] = a, d = !0);
        });d && (x && (a = e(x, c)), p = this.textWidth = a && a.width && "auto" !== a.width && "text" === w.nodeName.toLowerCase() && h(a.width), this.styles = a, p && !H && this.renderer.forExport && delete a.width, G && !H ? r(this.element, a) : (b = function b(a, x) {
          return "-" + x.toLowerCase();
        }, I(a, function (a, x) {
          -1 === D(x, n) && (q += x.replace(/([A-Z])/g, b) + ":" + a + ";");
        }), q && m(w, "style", q)), this.added && ("text" === this.element.nodeName && this.renderer.buildText(this), a && a.textOutline && this.applyTextOutline(a.textOutline)));
        return this;
      }, strokeWidth: function strokeWidth() {
        return this["stroke-width"] || 0;
      }, on: function on(a, h) {
        var x = this,
            c = x.element;n && "click" === a ? (c.ontouchstart = function (a) {
          x.touchEventFired = Date.now();a.preventDefault();h.call(c, a);
        }, c.onclick = function (a) {
          (-1 === R.navigator.userAgent.indexOf("Android") || 1100 < Date.now() - (x.touchEventFired || 0)) && h.call(c, a);
        }) : c["on" + a] = h;return this;
      }, setRadialReference: function setRadialReference(a) {
        var x = this.renderer.gradients[this.element.gradient];this.element.radialReference = a;x && x.radAttr && x.animate(this.renderer.getRadialAttr(a, x.radAttr));return this;
      }, translate: function translate(a, h) {
        return this.attr({ translateX: a, translateY: h });
      }, invert: function invert(a) {
        this.inverted = a;this.updateTransform();return this;
      }, updateTransform: function updateTransform() {
        var a = this.translateX || 0,
            h = this.translateY || 0,
            c = this.scaleX,
            w = this.scaleY,
            p = this.inverted,
            e = this.rotation,
            q = this.element;p && (a += this.width, h += this.height);a = ["translate(" + a + "," + h + ")"];p ? a.push("rotate(90) scale(-1,1)") : e && a.push("rotate(" + e + " " + (q.getAttribute("x") || 0) + " " + (q.getAttribute("y") || 0) + ")");(t(c) || t(w)) && a.push("scale(" + L(c, 1) + " " + L(w, 1) + ")");a.length && q.setAttribute("transform", a.join(" "));
      }, toFront: function toFront() {
        var a = this.element;a.parentNode.appendChild(a);return this;
      }, align: function align(a, h, c) {
        var x,
            w,
            p,
            e,
            q = {};w = this.renderer;p = w.alignedObjects;var b, d;if (a) {
          if (this.alignOptions = a, this.alignByTranslate = h, !c || B(c)) this.alignTo = x = c || "renderer", v(p, this), p.push(this), c = null;
        } else a = this.alignOptions, h = this.alignByTranslate, x = this.alignTo;c = L(c, w[x], w);x = a.align;w = a.verticalAlign;p = (c.x || 0) + (a.x || 0);e = (c.y || 0) + (a.y || 0);"right" === x ? b = 1 : "center" === x && (b = 2);b && (p += (c.width - (a.width || 0)) / b);q[h ? "translateX" : "x"] = Math.round(p);"bottom" === w ? d = 1 : "middle" === w && (d = 2);d && (e += (c.height - (a.height || 0)) / d);q[h ? "translateY" : "y"] = Math.round(e);this[this.placed ? "animate" : "attr"](q);this.placed = !0;this.alignAttr = q;return this;
      }, getBBox: function getBBox(a, h) {
        var x,
            c = this.renderer,
            w,
            p = this.element,
            q = this.styles,
            d,
            n = this.textStr,
            k,
            N = c.cache,
            H = c.cacheKeys,
            G;h = L(h, this.rotation);w = h * g;d = q && q.fontSize;void 0 !== n && (G = n.toString(), -1 === G.indexOf("\x3c") && (G = G.replace(/[0-9]/g, "0")), G += ["", h || 0, d, q && q.width, q && q.textOverflow].join());G && !a && (x = N[G]);if (!x) {
          if (p.namespaceURI === this.SVG_NS || c.forExport) {
            try {
              (k = this.fakeTS && function (a) {
                b(p.querySelectorAll(".highcharts-text-outline"), function (x) {
                  x.style.display = a;
                });
              }) && k("none"), x = p.getBBox ? e({}, p.getBBox()) : { width: p.offsetWidth, height: p.offsetHeight }, k && k("");
            } catch (W) {}if (!x || 0 > x.width) x = { width: 0, height: 0 };
          } else x = this.htmlGetBBox();c.isSVG && (a = x.width, c = x.height, q && "11px" === q.fontSize && 17 === Math.round(c) && (x.height = c = 14), h && (x.width = Math.abs(c * Math.sin(w)) + Math.abs(a * Math.cos(w)), x.height = Math.abs(c * Math.cos(w)) + Math.abs(a * Math.sin(w))));if (G && 0 < x.height) {
            for (; 250 < H.length;) {
              delete N[H.shift()];
            }N[G] || H.push(G);N[G] = x;
          }
        }return x;
      }, show: function show(a) {
        return this.attr({ visibility: a ? "inherit" : "visible" });
      }, hide: function hide() {
        return this.attr({ visibility: "hidden" });
      }, fadeOut: function fadeOut(a) {
        var x = this;x.animate({ opacity: 0 }, { duration: a || 150, complete: function complete() {
            x.attr({ y: -9999 });
          } });
      }, add: function add(a) {
        var x = this.renderer,
            h = this.element,
            c;a && (this.parentGroup = a);this.parentInverted = a && a.inverted;void 0 !== this.textStr && x.buildText(this);this.added = !0;if (!a || a.handleZ || this.zIndex) c = this.zIndexSetter();c || (a ? a.element : x.box).appendChild(h);if (this.onAdd) this.onAdd();return this;
      }, safeRemoveChild: function safeRemoveChild(a) {
        var x = a.parentNode;x && x.removeChild(a);
      }, destroy: function destroy() {
        var a = this,
            h = a.element || {},
            c = a.renderer.isSVG && "SPAN" === h.nodeName && a.parentGroup,
            w = h.ownerSVGElement;h.onclick = h.onmouseout = h.onmouseover = h.onmousemove = h.point = null;P(a);a.clipPath && w && (b(w.querySelectorAll("[clip-path]"), function (x) {
          -1 < x.getAttribute("clip-path").indexOf(a.clipPath.element.id + ")") && x.removeAttribute("clip-path");
        }), a.clipPath = a.clipPath.destroy());if (a.stops) {
          for (w = 0; w < a.stops.length; w++) {
            a.stops[w] = a.stops[w].destroy();
          }a.stops = null;
        }a.safeRemoveChild(h);for (a.destroyShadows(); c && c.div && 0 === c.div.childNodes.length;) {
          h = c.parentGroup, a.safeRemoveChild(c.div), delete c.div, c = h;
        }a.alignTo && v(a.renderer.alignedObjects, a);I(a, function (x, h) {
          delete a[h];
        });
        return null;
      }, shadow: function shadow(a, h, c) {
        var x = [],
            w,
            p,
            q = this.element,
            e,
            b,
            d,
            n;if (!a) this.destroyShadows();else if (!this.shadows) {
          b = L(a.width, 3);d = (a.opacity || .15) / b;n = this.parentInverted ? "(-1,-1)" : "(" + L(a.offsetX, 1) + ", " + L(a.offsetY, 1) + ")";for (w = 1; w <= b; w++) {
            p = q.cloneNode(0), e = 2 * b + 1 - 2 * w, m(p, { isShadow: "true", stroke: a.color || "#000000", "stroke-opacity": d * w, "stroke-width": e, transform: "translate" + n, fill: "none" }), c && (m(p, "height", Math.max(m(p, "height") - e, 0)), p.cutHeight = e), h ? h.element.appendChild(p) : q.parentNode.insertBefore(p, q), x.push(p);
          }this.shadows = x;
        }return this;
      }, destroyShadows: function destroyShadows() {
        b(this.shadows || [], function (a) {
          this.safeRemoveChild(a);
        }, this);this.shadows = void 0;
      }, xGetter: function xGetter(a) {
        "circle" === this.element.nodeName && ("x" === a ? a = "cx" : "y" === a && (a = "cy"));return this._defaultGetter(a);
      }, _defaultGetter: function _defaultGetter(a) {
        a = L(this[a], this.element ? this.element.getAttribute(a) : null, 0);/^[\-0-9\.]+$/.test(a) && (a = parseFloat(a));return a;
      }, dSetter: function dSetter(a, h, c) {
        a && a.join && (a = a.join(" "));/(NaN| {2}|^$)/.test(a) && (a = "M 0 0");this[h] !== a && (c.setAttribute(h, a), this[h] = a);
      }, dashstyleSetter: function dashstyleSetter(a) {
        var x,
            c = this["stroke-width"];"inherit" === c && (c = 1);if (a = a && a.toLowerCase()) {
          a = a.replace("shortdashdotdot", "3,1,1,1,1,1,").replace("shortdashdot", "3,1,1,1").replace("shortdot", "1,1,").replace("shortdash", "3,1,").replace("longdash", "8,3,").replace(/dot/g, "1,3,").replace("dash", "4,3,").replace(/,$/, "").split(",");for (x = a.length; x--;) {
            a[x] = h(a[x]) * c;
          }a = a.join(",").replace(/NaN/g, "none");this.element.setAttribute("stroke-dasharray", a);
        }
      }, alignSetter: function alignSetter(a) {
        this.element.setAttribute("text-anchor", { left: "start", center: "middle", right: "end" }[a]);
      }, opacitySetter: function opacitySetter(a, h, c) {
        this[h] = a;c.setAttribute(h, a);
      }, titleSetter: function titleSetter(a) {
        var h = this.element.getElementsByTagName("title")[0];h || (h = k.createElementNS(this.SVG_NS, "title"), this.element.appendChild(h));h.firstChild && h.removeChild(h.firstChild);h.appendChild(k.createTextNode(String(L(a), "").replace(/<[^>]*>/g, "")));
      }, textSetter: function textSetter(a) {
        a !== this.textStr && (delete this.bBox, this.textStr = a, this.added && this.renderer.buildText(this));
      }, fillSetter: function fillSetter(a, h, c) {
        "string" === typeof a ? c.setAttribute(h, a) : a && this.colorGradient(a, h, c);
      }, visibilitySetter: function visibilitySetter(a, h, c) {
        "inherit" === a ? c.removeAttribute(h) : this[h] !== a && c.setAttribute(h, a);this[h] = a;
      }, zIndexSetter: function zIndexSetter(a, c) {
        var x = this.renderer,
            w = this.parentGroup,
            p = (w || x).element || x.box,
            q,
            e = this.element,
            b;q = this.added;var d;t(a) && (e.zIndex = a, a = +a, this[c] === a && (q = !1), this[c] = a);if (q) {
          (a = this.zIndex) && w && (w.handleZ = !0);c = p.childNodes;for (d = 0; d < c.length && !b; d++) {
            w = c[d], q = w.zIndex, w !== e && (h(q) > a || !t(a) && t(q) || 0 > a && !t(q) && p !== x.box) && (p.insertBefore(e, w), b = !0);
          }b || p.appendChild(e);
        }return b;
      }, _defaultSetter: function _defaultSetter(a, h, c) {
        c.setAttribute(h, a);
      } });C.prototype.yGetter = C.prototype.xGetter;C.prototype.translateXSetter = C.prototype.translateYSetter = C.prototype.rotationSetter = C.prototype.verticalAlignSetter = C.prototype.scaleXSetter = C.prototype.scaleYSetter = function (a, h) {
      this[h] = a;this.doTransform = !0;
    };C.prototype["stroke-widthSetter"] = C.prototype.strokeSetter = function (a, h, c) {
      this[h] = a;this.stroke && this["stroke-width"] ? (C.prototype.fillSetter.call(this, this.stroke, "stroke", c), c.setAttribute("stroke-width", this["stroke-width"]), this.hasStroke = !0) : "stroke-width" === h && 0 === a && this.hasStroke && (c.removeAttribute("stroke"), this.hasStroke = !1);
    };A = a.SVGRenderer = function () {
      this.init.apply(this, arguments);
    };e(A.prototype, { Element: C, SVG_NS: O, init: function init(a, h, w, p, q, e) {
        var x;p = this.createElement("svg").attr({ version: "1.1", "class": "highcharts-root" }).css(this.getStyle(p));x = p.element;a.appendChild(x);-1 === a.innerHTML.indexOf("xmlns") && m(x, "xmlns", this.SVG_NS);this.isSVG = !0;this.box = x;this.boxWrapper = p;this.alignedObjects = [];this.url = (c || K) && k.getElementsByTagName("base").length ? R.location.href.replace(/#.*?$/, "").replace(/<[^>]*>/g, "").replace(/([\('\)])/g, "\\$1").replace(/ /g, "%20") : "";this.createElement("desc").add().element.appendChild(k.createTextNode("Created with Highcharts 5.0.14"));this.defs = this.createElement("defs").add();this.allowHTML = e;this.forExport = q;this.gradients = {};this.cache = {};this.cacheKeys = [];this.imgCount = 0;this.setSize(h, w, !1);var b;c && a.getBoundingClientRect && (h = function h() {
          r(a, { left: 0, top: 0 });b = a.getBoundingClientRect();r(a, { left: Math.ceil(b.left) - b.left + "px", top: Math.ceil(b.top) - b.top + "px" });
        }, h(), this.unSubPixelFix = F(R, "resize", h));
      }, getStyle: function getStyle(a) {
        return this.style = e({ fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif', fontSize: "12px" }, a);
      }, setStyle: function setStyle(a) {
        this.boxWrapper.css(this.getStyle(a));
      }, isHidden: function isHidden() {
        return !this.boxWrapper.getBBox().width;
      }, destroy: function destroy() {
        var a = this.defs;this.box = null;this.boxWrapper = this.boxWrapper.destroy();d(this.gradients || {});this.gradients = null;a && (this.defs = a.destroy());this.unSubPixelFix && this.unSubPixelFix();return this.alignedObjects = null;
      }, createElement: function createElement(a) {
        var h = new this.Element();h.init(this, a);return h;
      }, draw: z, getRadialAttr: function getRadialAttr(a, h) {
        return { cx: a[0] - a[2] / 2 + h.cx * a[2], cy: a[1] - a[2] / 2 + h.cy * a[2], r: h.r * a[2] };
      }, getSpanWidth: function getSpanWidth(a, h) {
        var c = a.getBBox(!0).width;!H && this.forExport && (c = this.measureSpanWidth(h.firstChild.data, a.styles));return c;
      }, applyEllipsis: function applyEllipsis(a, h, c, w) {
        var x = a.rotation,
            p = c,
            q,
            e = 0,
            b = c.length,
            d = function d(a) {
          h.removeChild(h.firstChild);a && h.appendChild(k.createTextNode(a));
        },
            n;a.rotation = 0;p = this.getSpanWidth(a, h);if (n = p > w) {
          for (; e <= b;) {
            q = Math.ceil((e + b) / 2), p = c.substring(0, q) + "\u2026", d(p), p = this.getSpanWidth(a, h), e === b ? e = b + 1 : p > w ? b = q - 1 : e = q;
          }0 === b && d("");
        }a.rotation = x;return n;
      }, buildText: function buildText(a) {
        var c = a.element,
            w = this,
            x = w.forExport,
            p = L(a.textStr, "").toString(),
            q = -1 !== p.indexOf("\x3c"),
            e = c.childNodes,
            d,
            n,
            g,
            G,
            v = m(c, "x"),
            z = a.styles,
            f = a.textWidth,
            I = z && z.lineHeight,
            B = z && z.textOutline,
            D = z && "ellipsis" === z.textOverflow,
            l = z && "nowrap" === z.whiteSpace,
            P = z && z.fontSize,
            t,
            J,
            u = e.length,
            z = f && !a.added && this.box,
            K = function K(a) {
          var x;x = /(px|em)$/.test(a && a.style.fontSize) ? a.style.fontSize : P || w.style.fontSize || 12;return I ? h(I) : w.fontMetrics(x, a.getAttribute("style") ? a : c).h;
        };t = [p, D, l, I, B, P, f].join();if (t !== a.textCache) {
          for (a.textCache = t; u--;) {
            c.removeChild(e[u]);
          }q || B || D || f || -1 !== p.indexOf(" ") ? (d = /<.*class="([^"]+)".*>/, n = /<.*style="([^"]+)".*>/, g = /<.*href="([^"]+)".*>/, z && z.appendChild(c), p = q ? p.replace(/<(b|strong)>/g, '\x3cspan style\x3d"font-weight:bold"\x3e').replace(/<(i|em)>/g, '\x3cspan style\x3d"font-style:italic"\x3e').replace(/<a/g, "\x3cspan").replace(/<\/(b|strong|i|em|a)>/g, "\x3c/span\x3e").split(/<br.*?>/g) : [p], p = y(p, function (a) {
            return "" !== a;
          }), b(p, function (h, p) {
            var q,
                e = 0;h = h.replace(/^\s+|\s+$/g, "").replace(/<span/g, "|||\x3cspan").replace(/<\/span>/g, "\x3c/span\x3e|||");q = h.split("|||");b(q, function (h) {
              if ("" !== h || 1 === q.length) {
                var b = {},
                    z = k.createElementNS(w.SVG_NS, "tspan"),
                    y,
                    I;d.test(h) && (y = h.match(d)[1], m(z, "class", y));n.test(h) && (I = h.match(n)[1].replace(/(;| |^)color([ :])/, "$1fill$2"), m(z, "style", I));g.test(h) && !x && (m(z, "onclick", 'location.href\x3d"' + h.match(g)[1] + '"'), r(z, { cursor: "pointer" }));h = (h.replace(/<(.|\n)*?>/g, "") || " ").replace(/&lt;/g, "\x3c").replace(/&gt;/g, "\x3e");if (" " !== h) {
                  z.appendChild(k.createTextNode(h));e ? b.dx = 0 : p && null !== v && (b.x = v);m(z, b);c.appendChild(z);!e && J && (!H && x && r(z, { display: "block" }), m(z, "dy", K(z)));if (f) {
                    b = h.replace(/([^\^])-/g, "$1- ").split(" ");y = 1 < q.length || p || 1 < b.length && !l;var B = [],
                        N,
                        P = K(z),
                        t = a.rotation;for (D && (G = w.applyEllipsis(a, z, h, f)); !D && y && (b.length || B.length);) {
                      a.rotation = 0, N = w.getSpanWidth(a, z), h = N > f, void 0 === G && (G = h), h && 1 !== b.length ? (z.removeChild(z.firstChild), B.unshift(b.pop())) : (b = B, B = [], b.length && !l && (z = k.createElementNS(O, "tspan"), m(z, { dy: P, x: v }), I && m(z, "style", I), c.appendChild(z)), N > f && (f = N)), b.length && z.appendChild(k.createTextNode(b.join(" ").replace(/- /g, "-")));
                    }a.rotation = t;
                  }e++;
                }
              }
            });J = J || c.childNodes.length;
          }), G && a.attr("title", a.textStr), z && z.removeChild(c), B && a.applyTextOutline && a.applyTextOutline(B)) : c.appendChild(k.createTextNode(p.replace(/&lt;/g, "\x3c").replace(/&gt;/g, "\x3e")));
        }
      }, getContrast: function getContrast(a) {
        a = l(a).rgba;return 510 < a[0] + a[1] + a[2] ? "#000000" : "#FFFFFF";
      }, button: function button(a, h, c, w, q, b, d, n, g) {
        var x = this.label(a, h, c, g, null, null, null, null, "button"),
            k = 0;x.attr(p({ padding: 8, r: 2 }, q));var z, H, v, f;q = p({ fill: "#f7f7f7", stroke: "#cccccc", "stroke-width": 1,
          style: { color: "#333333", cursor: "pointer", fontWeight: "normal" } }, q);z = q.style;delete q.style;b = p(q, { fill: "#e6e6e6" }, b);H = b.style;delete b.style;d = p(q, { fill: "#e6ebf5", style: { color: "#000000", fontWeight: "bold" } }, d);v = d.style;delete d.style;n = p(q, { style: { color: "#cccccc" } }, n);f = n.style;delete n.style;F(x.element, G ? "mouseover" : "mouseenter", function () {
          3 !== k && x.setState(1);
        });F(x.element, G ? "mouseout" : "mouseleave", function () {
          3 !== k && x.setState(k);
        });x.setState = function (a) {
          1 !== a && (x.state = k = a);x.removeClass(/highcharts-button-(normal|hover|pressed|disabled)/).addClass("highcharts-button-" + ["normal", "hover", "pressed", "disabled"][a || 0]);x.attr([q, b, d, n][a || 0]).css([z, H, v, f][a || 0]);
        };x.attr(q).css(e({ cursor: "default" }, z));return x.on("click", function (a) {
          3 !== k && w.call(x, a);
        });
      }, crispLine: function crispLine(a, h) {
        a[1] === a[4] && (a[1] = a[4] = Math.round(a[1]) - h % 2 / 2);a[2] === a[5] && (a[2] = a[5] = Math.round(a[2]) + h % 2 / 2);return a;
      }, path: function path(a) {
        var h = { fill: "none" };J(a) ? h.d = a : q(a) && e(h, a);return this.createElement("path").attr(h);
      }, circle: function circle(a, h, c) {
        a = q(a) ? a : { x: a, y: h, r: c };h = this.createElement("circle");h.xSetter = h.ySetter = function (a, h, c) {
          c.setAttribute("c" + h, a);
        };return h.attr(a);
      }, arc: function arc(a, h, c, w, p, b) {
        q(a) ? (w = a, h = w.y, c = w.r, a = w.x) : w = { innerR: w, start: p, end: b };a = this.symbol("arc", a, h, c, c, w);a.r = c;return a;
      }, rect: function rect(a, h, c, w, p, b) {
        p = q(a) ? a.r : p;var x = this.createElement("rect");a = q(a) ? a : void 0 === a ? {} : { x: a, y: h, width: Math.max(c, 0), height: Math.max(w, 0) };void 0 !== b && (a.strokeWidth = b, a = x.crisp(a));a.fill = "none";p && (a.r = p);x.rSetter = function (a, h, c) {
          m(c, { rx: a, ry: a });
        };return x.attr(a);
      }, setSize: function setSize(a, h, c) {
        var w = this.alignedObjects,
            p = w.length;this.width = a;this.height = h;for (this.boxWrapper.animate({ width: a, height: h }, { step: function step() {
            this.attr({ viewBox: "0 0 " + this.attr("width") + " " + this.attr("height") });
          }, duration: L(c, !0) ? void 0 : 0 }); p--;) {
          w[p].align();
        }
      }, g: function g(a) {
        var h = this.createElement("g");return a ? h.attr({ "class": "highcharts-" + a }) : h;
      }, image: function image(a, h, c, w, p) {
        var x = { preserveAspectRatio: "none" };1 < arguments.length && e(x, { x: h, y: c, width: w, height: p });x = this.createElement("image").attr(x);x.element.setAttributeNS ? x.element.setAttributeNS("http://www.w3.org/1999/xlink", "href", a) : x.element.setAttribute("hc-svg-href", a);return x;
      }, symbol: function symbol(a, h, c, w, p, q) {
        var x = this,
            d,
            n = /^url\((.*?)\)$/,
            g = n.test(a),
            z = !g && (this.symbols[a] ? a : "circle"),
            G = z && this.symbols[z],
            H = t(h) && G && G.call(this.symbols, Math.round(h), Math.round(c), w, p, q),
            v,
            y;G ? (d = this.path(H), d.attr("fill", "none"), e(d, { symbolName: z, x: h, y: c, width: w, height: p }), q && e(d, q)) : g && (v = a.match(n)[1], d = this.image(v), d.imgwidth = L(Q[v] && Q[v].width, q && q.width), d.imgheight = L(Q[v] && Q[v].height, q && q.height), y = function y() {
          d.attr({ width: d.width, height: d.height });
        }, b(["width", "height"], function (a) {
          d[a + "Setter"] = function (a, h) {
            var c = {},
                w = this["img" + h],
                p = "width" === h ? "translateX" : "translateY";this[h] = a;t(w) && (this.element && this.element.setAttribute(h, w), this.alignByTranslate || (c[p] = ((this[h] || 0) - w) / 2, this.attr(c)));
          };
        }), t(h) && d.attr({ x: h, y: c }), d.isImg = !0, t(d.imgwidth) && t(d.imgheight) ? y() : (d.attr({ width: 0, height: 0 }), u("img", { onload: function onload() {
            var a = f[x.chartIndex];0 === this.width && (r(this, { position: "absolute", top: "-999em" }), k.body.appendChild(this));Q[v] = { width: this.width, height: this.height };d.imgwidth = this.width;d.imgheight = this.height;d.element && y();this.parentNode && this.parentNode.removeChild(this);x.imgCount--;if (!x.imgCount && a && a.onload) a.onload();
          }, src: v }), this.imgCount++));return d;
      }, symbols: { circle: function circle(a, h, c, w) {
          return this.arc(a + c / 2, h + w / 2, c / 2, w / 2, { start: 0, end: 2 * Math.PI, open: !1 });
        }, square: function square(a, h, c, w) {
          return ["M", a, h, "L", a + c, h, a + c, h + w, a, h + w, "Z"];
        }, triangle: function triangle(a, h, c, w) {
          return ["M", a + c / 2, h, "L", a + c, h + w, a, h + w, "Z"];
        }, "triangle-down": function triangleDown(a, h, c, w) {
          return ["M", a, h, "L", a + c, h, a + c / 2, h + w, "Z"];
        }, diamond: function diamond(a, h, c, w) {
          return ["M", a + c / 2, h, "L", a + c, h + w / 2, a + c / 2, h + w, a, h + w / 2, "Z"];
        }, arc: function arc(a, h, c, w, p) {
          var q = p.start,
              b = p.r || c,
              x = p.r || w || c,
              e = p.end - .001;c = p.innerR;w = L(p.open, .001 > Math.abs(p.end - p.start - 2 * Math.PI));var d = Math.cos(q),
              n = Math.sin(q),
              g = Math.cos(e),
              e = Math.sin(e);p = .001 > p.end - q - Math.PI ? 0 : 1;b = ["M", a + b * d, h + x * n, "A", b, x, 0, p, 1, a + b * g, h + x * e];t(c) && b.push(w ? "M" : "L", a + c * g, h + c * e, "A", c, c, 0, p, 0, a + c * d, h + c * n);b.push(w ? "" : "Z");return b;
        }, callout: function callout(a, h, c, w, p) {
          var q = Math.min(p && p.r || 0, c, w),
              b = q + 6,
              e = p && p.anchorX;p = p && p.anchorY;var d;d = ["M", a + q, h, "L", a + c - q, h, "C", a + c, h, a + c, h, a + c, h + q, "L", a + c, h + w - q, "C", a + c, h + w, a + c, h + w, a + c - q, h + w, "L", a + q, h + w, "C", a, h + w, a, h + w, a, h + w - q, "L", a, h + q, "C", a, h, a, h, a + q, h];e && e > c ? p > h + b && p < h + w - b ? d.splice(13, 3, "L", a + c, p - 6, a + c + 6, p, a + c, p + 6, a + c, h + w - q) : d.splice(13, 3, "L", a + c, w / 2, e, p, a + c, w / 2, a + c, h + w - q) : e && 0 > e ? p > h + b && p < h + w - b ? d.splice(33, 3, "L", a, p + 6, a - 6, p, a, p - 6, a, h + q) : d.splice(33, 3, "L", a, w / 2, e, p, a, w / 2, a, h + q) : p && p > w && e > a + b && e < a + c - b ? d.splice(23, 3, "L", e + 6, h + w, e, h + w + 6, e - 6, h + w, a + q, h + w) : p && 0 > p && e > a + b && e < a + c - b && d.splice(3, 3, "L", e - 6, h, e, h - 6, e + 6, h, c - q, h);return d;
        } }, clipRect: function clipRect(h, c, w, p) {
        var q = a.uniqueKey(),
            b = this.createElement("clipPath").attr({ id: q }).add(this.defs);h = this.rect(h, c, w, p, 0).add(b);h.id = q;h.clipPath = b;h.count = 0;return h;
      }, text: function text(a, h, c, w) {
        var p = !H && this.forExport,
            q = {};if (w && (this.allowHTML || !this.forExport)) return this.html(a, h, c);q.x = Math.round(h || 0);c && (q.y = Math.round(c));if (a || 0 === a) q.text = a;a = this.createElement("text").attr(q);p && a.css({ position: "absolute" });w || (a.xSetter = function (a, h, c) {
          var w = c.getElementsByTagName("tspan"),
              p,
              q = c.getAttribute(h),
              b;for (b = 0; b < w.length; b++) {
            p = w[b], p.getAttribute(h) === q && p.setAttribute(h, a);
          }c.setAttribute(h, a);
        });return a;
      }, fontMetrics: function fontMetrics(a, c) {
        a = a || c && c.style && c.style.fontSize || this.style && this.style.fontSize;a = /px/.test(a) ? h(a) : /em/.test(a) ? parseFloat(a) * (c ? this.fontMetrics(null, c.parentNode).f : 16) : 12;
        c = 24 > a ? a + 3 : Math.round(1.2 * a);return { h: c, b: Math.round(.8 * c), f: a };
      }, rotCorr: function rotCorr(a, h, c) {
        var w = a;h && c && (w = Math.max(w * Math.cos(h * g), 4));return { x: -a / 3 * Math.sin(h * g), y: w };
      }, label: function label(h, c, q, d, n, g, k, z, G) {
        var x = this,
            H = x.g("button" !== G && "label"),
            v = H.text = x.text("", 0, 0, k).attr({ zIndex: 1 }),
            f,
            y,
            I = 0,
            B = 3,
            D = 0,
            r,
            l,
            P,
            m,
            J,
            O = {},
            L,
            u,
            N = /^url\((.*?)\)$/.test(d),
            K = N,
            U,
            T,
            Q,
            R;G && H.addClass("highcharts-" + G);K = N;U = function U() {
          return (L || 0) % 2 / 2;
        };T = function T() {
          var a = v.element.style,
              h = {};y = (void 0 === r || void 0 === l || J) && t(v.textStr) && v.getBBox();H.width = (r || y.width || 0) + 2 * B + D;H.height = (l || y.height || 0) + 2 * B;u = B + x.fontMetrics(a && a.fontSize, v).b;K && (f || (H.box = f = x.symbols[d] || N ? x.symbol(d) : x.rect(), f.addClass(("button" === G ? "" : "highcharts-label-box") + (G ? " highcharts-" + G + "-box" : "")), f.add(H), a = U(), h.x = a, h.y = (z ? -u : 0) + a), h.width = Math.round(H.width), h.height = Math.round(H.height), f.attr(e(h, O)), O = {});
        };Q = function Q() {
          var a = D + B,
              h;h = z ? 0 : u;t(r) && y && ("center" === J || "right" === J) && (a += { center: .5, right: 1 }[J] * (r - y.width));if (a !== v.x || h !== v.y) v.attr("x", a), void 0 !== h && v.attr("y", h);v.x = a;v.y = h;
        };R = function R(a, h) {
          f ? f.attr(a, h) : O[a] = h;
        };H.onAdd = function () {
          v.add(H);H.attr({ text: h || 0 === h ? h : "", x: c, y: q });f && t(n) && H.attr({ anchorX: n, anchorY: g });
        };H.widthSetter = function (h) {
          r = a.isNumber(h) ? h : null;
        };H.heightSetter = function (a) {
          l = a;
        };H["text-alignSetter"] = function (a) {
          J = a;
        };H.paddingSetter = function (a) {
          t(a) && a !== B && (B = H.padding = a, Q());
        };H.paddingLeftSetter = function (a) {
          t(a) && a !== D && (D = a, Q());
        };H.alignSetter = function (a) {
          a = { left: 0, center: .5, right: 1 }[a];a !== I && (I = a, y && H.attr({ x: P }));
        };
        H.textSetter = function (a) {
          void 0 !== a && v.textSetter(a);T();Q();
        };H["stroke-widthSetter"] = function (a, h) {
          a && (K = !0);L = this["stroke-width"] = a;R(h, a);
        };H.strokeSetter = H.fillSetter = H.rSetter = function (a, h) {
          "r" !== h && ("fill" === h && a && (K = !0), H[h] = a);R(h, a);
        };H.anchorXSetter = function (a, h) {
          n = H.anchorX = a;R(h, Math.round(a) - U() - P);
        };H.anchorYSetter = function (a, h) {
          g = H.anchorY = a;R(h, a - m);
        };H.xSetter = function (a) {
          H.x = a;I && (a -= I * ((r || y.width) + 2 * B));P = Math.round(a);H.attr("translateX", P);
        };H.ySetter = function (a) {
          m = H.y = Math.round(a);
          H.attr("translateY", m);
        };var V = H.css;return e(H, { css: function css(a) {
            if (a) {
              var h = {};a = p(a);b(H.textProps, function (c) {
                void 0 !== a[c] && (h[c] = a[c], delete a[c]);
              });v.css(h);
            }return V.call(H, a);
          }, getBBox: function getBBox() {
            return { width: y.width + 2 * B, height: y.height + 2 * B, x: y.x - B, y: y.y - B };
          }, shadow: function shadow(a) {
            a && (T(), f && f.shadow(a));return H;
          }, destroy: function destroy() {
            w(H.element, "mouseenter");w(H.element, "mouseleave");v && (v = v.destroy());f && (f = f.destroy());C.prototype.destroy.call(H);H = x = T = Q = R = null;
          } });
      } });a.Renderer = A;
  })(M);(function (a) {
    var C = a.attr,
        A = a.createElement,
        F = a.css,
        E = a.defined,
        m = a.each,
        f = a.extend,
        l = a.isFirefox,
        r = a.isMS,
        u = a.isWebKit,
        t = a.pInt,
        g = a.SVGRenderer,
        d = a.win,
        k = a.wrap;f(a.SVGElement.prototype, { htmlCss: function htmlCss(a) {
        var b = this.element;if (b = a && "SPAN" === b.tagName && a.width) delete a.width, this.textWidth = b, this.updateTransform();a && "ellipsis" === a.textOverflow && (a.whiteSpace = "nowrap", a.overflow = "hidden");this.styles = f(this.styles, a);F(this.element, a);return this;
      }, htmlGetBBox: function htmlGetBBox() {
        var a = this.element;"text" === a.nodeName && (a.style.position = "absolute");return { x: a.offsetLeft, y: a.offsetTop, width: a.offsetWidth, height: a.offsetHeight };
      }, htmlUpdateTransform: function htmlUpdateTransform() {
        if (this.added) {
          var a = this.renderer,
              e = this.element,
              d = this.translateX || 0,
              g = this.translateY || 0,
              n = this.x || 0,
              k = this.y || 0,
              f = this.textAlign || "left",
              c = { left: 0, center: .5, right: 1 }[f],
              G = this.styles;F(e, { marginLeft: d, marginTop: g });this.shadows && m(this.shadows, function (a) {
            F(a, { marginLeft: d + 1, marginTop: g + 1 });
          });this.inverted && m(e.childNodes, function (c) {
            a.invertChild(c, e);
          });if ("SPAN" === e.tagName) {
            var q = this.rotation,
                B = t(this.textWidth),
                r = G && G.whiteSpace,
                p = [q, f, e.innerHTML, this.textWidth, this.textAlign].join();p !== this.cTT && (G = a.fontMetrics(e.style.fontSize).b, E(q) && this.setSpanRotation(q, c, G), F(e, { width: "", whiteSpace: r || "nowrap" }), e.offsetWidth > B && /[ \-]/.test(e.textContent || e.innerText) && F(e, { width: B + "px", display: "block", whiteSpace: r || "normal" }), this.getSpanCorrection(e.offsetWidth, G, c, q, f));F(e, { left: n + (this.xCorr || 0) + "px", top: k + (this.yCorr || 0) + "px" });u && (G = e.offsetHeight);this.cTT = p;
          }
        } else this.alignOnAdd = !0;
      }, setSpanRotation: function setSpanRotation(a, e, g) {
        var b = {},
            n = r ? "-ms-transform" : u ? "-webkit-transform" : l ? "MozTransform" : d.opera ? "-o-transform" : "";b[n] = b.transform = "rotate(" + a + "deg)";b[n + (l ? "Origin" : "-origin")] = b.transformOrigin = 100 * e + "% " + g + "px";F(this.element, b);
      }, getSpanCorrection: function getSpanCorrection(a, e, d) {
        this.xCorr = -a * d;this.yCorr = -e;
      } });f(g.prototype, { html: function html(a, e, d) {
        var b = this.createElement("span"),
            n = b.element,
            g = b.renderer,
            v = g.isSVG,
            c = function c(a, _c) {
          m(["opacity", "visibility"], function (q) {
            k(a, q + "Setter", function (a, p, q, b) {
              a.call(this, p, q, b);_c[q] = p;
            });
          });
        };b.textSetter = function (a) {
          a !== n.innerHTML && delete this.bBox;n.innerHTML = this.textStr = a;b.htmlUpdateTransform();
        };v && c(b, b.element.style);b.xSetter = b.ySetter = b.alignSetter = b.rotationSetter = function (a, c) {
          "align" === c && (c = "textAlign");b[c] = a;b.htmlUpdateTransform();
        };b.attr({ text: a, x: Math.round(e), y: Math.round(d) }).css({ fontFamily: this.style.fontFamily, fontSize: this.style.fontSize, position: "absolute" });n.style.whiteSpace = "nowrap";b.css = b.htmlCss;v && (b.add = function (a) {
          var q,
              e = g.box.parentNode,
              d = [];if (this.parentGroup = a) {
            if (q = a.div, !q) {
              for (; a;) {
                d.push(a), a = a.parentGroup;
              }m(d.reverse(), function (a) {
                var p,
                    n = C(a.element, "class");n && (n = { className: n });q = a.div = a.div || A("div", n, { position: "absolute", left: (a.translateX || 0) + "px", top: (a.translateY || 0) + "px", display: a.display, opacity: a.opacity, pointerEvents: a.styles && a.styles.pointerEvents }, q || e);p = q.style;f(a, { classSetter: function classSetter(a) {
                    this.element.setAttribute("class", a);q.className = a;
                  }, on: function on() {
                    d[0].div && b.on.apply({ element: d[0].div }, arguments);return a;
                  }, translateXSetter: function translateXSetter(c, h) {
                    p.left = c + "px";a[h] = c;a.doTransform = !0;
                  }, translateYSetter: function translateYSetter(c, h) {
                    p.top = c + "px";a[h] = c;a.doTransform = !0;
                  } });c(a, p);
              });
            }
          } else q = e;q.appendChild(n);b.added = !0;b.alignOnAdd && b.htmlUpdateTransform();return b;
        });return b;
      } });
  })(M);(function (a) {
    var C,
        A,
        F = a.createElement,
        E = a.css,
        m = a.defined,
        f = a.deg2rad,
        l = a.discardElement,
        r = a.doc,
        u = a.each,
        t = a.erase,
        g = a.extend;C = a.extendClass;var d = a.isArray,
        k = a.isNumber,
        b = a.isObject,
        e = a.merge;A = a.noop;var v = a.pick,
        y = a.pInt,
        n = a.SVGElement,
        D = a.SVGRenderer,
        J = a.win;a.svg || (A = { docMode8: r && 8 === r.documentMode, init: function init(a, b) {
        var c = ["\x3c", b, ' filled\x3d"f" stroked\x3d"f"'],
            e = ["position: ", "absolute", ";"],
            d = "div" === b;("shape" === b || d) && e.push("left:0;top:0;width:1px;height:1px;");e.push("visibility: ", d ? "hidden" : "visible");c.push(' style\x3d"', e.join(""), '"/\x3e');b && (c = d || "span" === b || "img" === b ? c.join("") : a.prepVML(c), this.element = F(c));this.renderer = a;
      }, add: function add(a) {
        var c = this.renderer,
            b = this.element,
            e = c.box,
            d = a && a.inverted,
            e = a ? a.element || a : e;a && (this.parentGroup = a);d && c.invertChild(b, e);e.appendChild(b);this.added = !0;this.alignOnAdd && !this.deferUpdateTransform && this.updateTransform();if (this.onAdd) this.onAdd();this.className && this.attr("class", this.className);return this;
      }, updateTransform: n.prototype.htmlUpdateTransform, setSpanRotation: function setSpanRotation() {
        var a = this.rotation,
            b = Math.cos(a * f),
            q = Math.sin(a * f);E(this.element, { filter: a ? ["progid:DXImageTransform.Microsoft.Matrix(M11\x3d", b, ", M12\x3d", -q, ", M21\x3d", q, ", M22\x3d", b, ", sizingMethod\x3d'auto expand')"].join("") : "none" });
      }, getSpanCorrection: function getSpanCorrection(a, b, q, e, d) {
        var c = e ? Math.cos(e * f) : 1,
            n = e ? Math.sin(e * f) : 0,
            g = v(this.elemHeight, this.element.offsetHeight),
            k;this.xCorr = 0 > c && -a;this.yCorr = 0 > n && -g;k = 0 > c * n;this.xCorr += n * b * (k ? 1 - q : q);this.yCorr -= c * b * (e ? k ? q : 1 - q : 1);d && "left" !== d && (this.xCorr -= a * q * (0 > c ? -1 : 1), e && (this.yCorr -= g * q * (0 > n ? -1 : 1)), E(this.element, { textAlign: d }));
      }, pathToVML: function pathToVML(a) {
        for (var c = a.length, b = []; c--;) {
          k(a[c]) ? b[c] = Math.round(10 * a[c]) - 5 : "Z" === a[c] ? b[c] = "x" : (b[c] = a[c], !a.isArc || "wa" !== a[c] && "at" !== a[c] || (b[c + 5] === b[c + 7] && (b[c + 7] += a[c + 7] > a[c + 5] ? 1 : -1), b[c + 6] === b[c + 8] && (b[c + 8] += a[c + 8] > a[c + 6] ? 1 : -1)));
        }return b.join(" ") || "x";
      }, clip: function clip(a) {
        var c = this,
            b;a ? (b = a.members, t(b, c), b.push(c), c.destroyClip = function () {
          t(b, c);
        }, a = a.getCSS(c)) : (c.destroyClip && c.destroyClip(), a = { clip: c.docMode8 ? "inherit" : "rect(auto)" });return c.css(a);
      }, css: n.prototype.htmlCss, safeRemoveChild: function safeRemoveChild(a) {
        a.parentNode && l(a);
      }, destroy: function destroy() {
        this.destroyClip && this.destroyClip();return n.prototype.destroy.apply(this);
      },
      on: function on(a, b) {
        this.element["on" + a] = function () {
          var a = J.event;a.target = a.srcElement;b(a);
        };return this;
      }, cutOffPath: function cutOffPath(a, b) {
        var c;a = a.split(/[ ,]/);c = a.length;if (9 === c || 11 === c) a[c - 4] = a[c - 2] = y(a[c - 2]) - 10 * b;return a.join(" ");
      }, shadow: function shadow(a, b, e) {
        var c = [],
            q,
            p = this.element,
            d = this.renderer,
            n,
            g = p.style,
            h,
            w = p.path,
            k,
            H,
            f,
            D;w && "string" !== typeof w.value && (w = "x");H = w;if (a) {
          f = v(a.width, 3);D = (a.opacity || .15) / f;for (q = 1; 3 >= q; q++) {
            k = 2 * f + 1 - 2 * q, e && (H = this.cutOffPath(w.value, k + .5)), h = ['\x3cshape isShadow\x3d"true" strokeweight\x3d"', k, '" filled\x3d"false" path\x3d"', H, '" coordsize\x3d"10 10" style\x3d"', p.style.cssText, '" /\x3e'], n = F(d.prepVML(h), null, { left: y(g.left) + v(a.offsetX, 1), top: y(g.top) + v(a.offsetY, 1) }), e && (n.cutOff = k + 1), h = ['\x3cstroke color\x3d"', a.color || "#000000", '" opacity\x3d"', D * q, '"/\x3e'], F(d.prepVML(h), null, null, n), b ? b.element.appendChild(n) : p.parentNode.insertBefore(n, p), c.push(n);
          }this.shadows = c;
        }return this;
      }, updateShadows: A, setAttr: function setAttr(a, b) {
        this.docMode8 ? this.element[a] = b : this.element.setAttribute(a, b);
      },
      classSetter: function classSetter(a) {
        (this.added ? this.element : this).className = a;
      }, dashstyleSetter: function dashstyleSetter(a, b, e) {
        (e.getElementsByTagName("stroke")[0] || F(this.renderer.prepVML(["\x3cstroke/\x3e"]), null, null, e))[b] = a || "solid";this[b] = a;
      }, dSetter: function dSetter(a, b, e) {
        var c = this.shadows;a = a || [];this.d = a.join && a.join(" ");e.path = a = this.pathToVML(a);if (c) for (e = c.length; e--;) {
          c[e].path = c[e].cutOff ? this.cutOffPath(a, c[e].cutOff) : a;
        }this.setAttr(b, a);
      }, fillSetter: function fillSetter(a, b, e) {
        var c = e.nodeName;"SPAN" === c ? e.style.color = a : "IMG" !== c && (e.filled = "none" !== a, this.setAttr("fillcolor", this.renderer.color(a, e, b, this)));
      }, "fill-opacitySetter": function fillOpacitySetter(a, b, e) {
        F(this.renderer.prepVML(["\x3c", b.split("-")[0], ' opacity\x3d"', a, '"/\x3e']), null, null, e);
      }, opacitySetter: A, rotationSetter: function rotationSetter(a, b, e) {
        e = e.style;this[b] = e[b] = a;e.left = -Math.round(Math.sin(a * f) + 1) + "px";e.top = Math.round(Math.cos(a * f)) + "px";
      }, strokeSetter: function strokeSetter(a, b, e) {
        this.setAttr("strokecolor", this.renderer.color(a, e, b, this));
      }, "stroke-widthSetter": function strokeWidthSetter(a, b, e) {
        e.stroked = !!a;
        this[b] = a;k(a) && (a += "px");this.setAttr("strokeweight", a);
      }, titleSetter: function titleSetter(a, b) {
        this.setAttr(b, a);
      }, visibilitySetter: function visibilitySetter(a, b, e) {
        "inherit" === a && (a = "visible");this.shadows && u(this.shadows, function (c) {
          c.style[b] = a;
        });"DIV" === e.nodeName && (a = "hidden" === a ? "-999em" : 0, this.docMode8 || (e.style[b] = a ? "visible" : "hidden"), b = "top");e.style[b] = a;
      }, xSetter: function xSetter(a, b, e) {
        this[b] = a;"x" === b ? b = "left" : "y" === b && (b = "top");this.updateClipping ? (this[b] = a, this.updateClipping()) : e.style[b] = a;
      }, zIndexSetter: function zIndexSetter(a, b, e) {
        e.style[b] = a;
      } }, A["stroke-opacitySetter"] = A["fill-opacitySetter"], a.VMLElement = A = C(n, A), A.prototype.ySetter = A.prototype.widthSetter = A.prototype.heightSetter = A.prototype.xSetter, A = { Element: A, isIE8: -1 < J.navigator.userAgent.indexOf("MSIE 8.0"), init: function init(a, b, e) {
        var c, d;this.alignedObjects = [];c = this.createElement("div").css({ position: "relative" });d = c.element;a.appendChild(c.element);this.isVML = !0;this.box = d;this.boxWrapper = c;this.gradients = {};this.cache = {};this.cacheKeys = [];this.imgCount = 0;this.setSize(b, e, !1);if (!r.namespaces.hcv) {
          r.namespaces.add("hcv", "urn:schemas-microsoft-com:vml");try {
            r.createStyleSheet().cssText = "hcv\\:fill, hcv\\:path, hcv\\:shape, hcv\\:stroke{ behavior:url(#default#VML); display: inline-block; } ";
          } catch (p) {
            r.styleSheets[0].cssText += "hcv\\:fill, hcv\\:path, hcv\\:shape, hcv\\:stroke{ behavior:url(#default#VML); display: inline-block; } ";
          }
        }
      }, isHidden: function isHidden() {
        return !this.box.offsetWidth;
      }, clipRect: function clipRect(a, e, d, n) {
        var c = this.createElement(),
            p = b(a);return g(c, { members: [],
          count: 0, left: (p ? a.x : a) + 1, top: (p ? a.y : e) + 1, width: (p ? a.width : d) - 1, height: (p ? a.height : n) - 1, getCSS: function getCSS(a) {
            var c = a.element,
                b = c.nodeName,
                h = a.inverted,
                w = this.top - ("shape" === b ? c.offsetTop : 0),
                p = this.left,
                c = p + this.width,
                e = w + this.height,
                w = { clip: "rect(" + Math.round(h ? p : w) + "px," + Math.round(h ? e : c) + "px," + Math.round(h ? c : e) + "px," + Math.round(h ? w : p) + "px)" };!h && a.docMode8 && "DIV" === b && g(w, { width: c + "px", height: e + "px" });return w;
          }, updateClipping: function updateClipping() {
            u(c.members, function (a) {
              a.element && a.css(c.getCSS(a));
            });
          } });
      }, color: function color(c, b, e, d) {
        var q = this,
            p,
            n = /^rgba/,
            g,
            k,
            h = "none";c && c.linearGradient ? k = "gradient" : c && c.radialGradient && (k = "pattern");if (k) {
          var w,
              v,
              H = c.linearGradient || c.radialGradient,
              f,
              D,
              y,
              x,
              r,
              B = "";c = c.stops;var l,
              G = [],
              m = function m() {
            g = ['\x3cfill colors\x3d"' + G.join(",") + '" opacity\x3d"', y, '" o:opacity2\x3d"', D, '" type\x3d"', k, '" ', B, 'focus\x3d"100%" method\x3d"any" /\x3e'];F(q.prepVML(g), null, null, b);
          };f = c[0];l = c[c.length - 1];0 < f[0] && c.unshift([0, f[1]]);1 > l[0] && c.push([1, l[1]]);u(c, function (h, c) {
            n.test(h[1]) ? (p = a.color(h[1]), w = p.get("rgb"), v = p.get("a")) : (w = h[1], v = 1);G.push(100 * h[0] + "% " + w);c ? (y = v, x = w) : (D = v, r = w);
          });if ("fill" === e) {
            if ("gradient" === k) e = H.x1 || H[0] || 0, c = H.y1 || H[1] || 0, f = H.x2 || H[2] || 0, H = H.y2 || H[3] || 0, B = 'angle\x3d"' + (90 - 180 * Math.atan((H - c) / (f - e)) / Math.PI) + '"', m();else {
              var h = H.r,
                  t = 2 * h,
                  J = 2 * h,
                  A = H.cx,
                  C = H.cy,
                  E = b.radialReference,
                  M,
                  h = function h() {
                E && (M = d.getBBox(), A += (E[0] - M.x) / M.width - .5, C += (E[1] - M.y) / M.height - .5, t *= E[2] / M.width, J *= E[2] / M.height);B = 'src\x3d"' + a.getOptions().global.VMLRadialGradientURL + '" size\x3d"' + t + "," + J + '" origin\x3d"0.5,0.5" position\x3d"' + A + "," + C + '" color2\x3d"' + r + '" ';m();
              };d.added ? h() : d.onAdd = h;h = x;
            }
          } else h = w;
        } else n.test(c) && "IMG" !== b.tagName ? (p = a.color(c), d[e + "-opacitySetter"](p.get("a"), e, b), h = p.get("rgb")) : (h = b.getElementsByTagName(e), h.length && (h[0].opacity = 1, h[0].type = "solid"), h = c);return h;
      }, prepVML: function prepVML(a) {
        var c = this.isIE8;a = a.join("");c ? (a = a.replace("/\x3e", ' xmlns\x3d"urn:schemas-microsoft-com:vml" /\x3e'), a = -1 === a.indexOf('style\x3d"') ? a.replace("/\x3e", ' style\x3d"display:inline-block;behavior:url(#default#VML);" /\x3e') : a.replace('style\x3d"', 'style\x3d"display:inline-block;behavior:url(#default#VML);')) : a = a.replace("\x3c", "\x3chcv:");return a;
      }, text: D.prototype.html, path: function path(a) {
        var c = { coordsize: "10 10" };d(a) ? c.d = a : b(a) && g(c, a);return this.createElement("shape").attr(c);
      }, circle: function circle(a, e, d) {
        var c = this.symbol("circle");b(a) && (d = a.r, e = a.y, a = a.x);c.isCircle = !0;c.r = d;return c.attr({ x: a, y: e });
      }, g: function g(a) {
        var c;a && (c = { className: "highcharts-" + a, "class": "highcharts-" + a });return this.createElement("div").attr(c);
      },
      image: function image(a, b, e, d, n) {
        var c = this.createElement("img").attr({ src: a });1 < arguments.length && c.attr({ x: b, y: e, width: d, height: n });return c;
      }, createElement: function createElement(a) {
        return "rect" === a ? this.symbol(a) : D.prototype.createElement.call(this, a);
      }, invertChild: function invertChild(a, b) {
        var c = this;b = b.style;var e = "IMG" === a.tagName && a.style;E(a, { flip: "x", left: y(b.width) - (e ? y(e.top) : 1), top: y(b.height) - (e ? y(e.left) : 1), rotation: -90 });u(a.childNodes, function (b) {
          c.invertChild(b, a);
        });
      }, symbols: { arc: function arc(a, b, e, d, n) {
          var c = n.start,
              q = n.end,
              g = n.r || e || d;e = n.innerR;d = Math.cos(c);var k = Math.sin(c),
              h = Math.cos(q),
              w = Math.sin(q);if (0 === q - c) return ["x"];c = ["wa", a - g, b - g, a + g, b + g, a + g * d, b + g * k, a + g * h, b + g * w];n.open && !e && c.push("e", "M", a, b);c.push("at", a - e, b - e, a + e, b + e, a + e * h, b + e * w, a + e * d, b + e * k, "x", "e");c.isArc = !0;return c;
        }, circle: function circle(a, b, e, d, n) {
          n && m(n.r) && (e = d = 2 * n.r);n && n.isCircle && (a -= e / 2, b -= d / 2);return ["wa", a, b, a + e, b + d, a + e, b + d / 2, a + e, b + d / 2, "e"];
        }, rect: function rect(a, b, e, d, n) {
          return D.prototype.symbols[m(n) && n.r ? "callout" : "square"].call(0, a, b, e, d, n);
        } } }, a.VMLRenderer = C = function C() {
      this.init.apply(this, arguments);
    }, C.prototype = e(D.prototype, A), a.Renderer = C);D.prototype.measureSpanWidth = function (a, b) {
      var c = r.createElement("span");a = r.createTextNode(a);c.appendChild(a);E(c, b);this.box.appendChild(c);b = c.offsetWidth;l(c);return b;
    };
  })(M);(function (a) {
    function C() {
      var f = a.defaultOptions.global,
          l = r.moment;if (f.timezone) {
        if (l) return function (a) {
          return -l.tz(a, f.timezone).utcOffset();
        };a.error(25);
      }return f.useUTC && f.getTimezoneOffset;
    }function A() {
      var f = a.defaultOptions.global,
          t,
          g = f.useUTC,
          d = g ? "getUTC" : "get",
          k = g ? "setUTC" : "set";a.Date = t = f.Date || r.Date;t.hcTimezoneOffset = g && f.timezoneOffset;t.hcGetTimezoneOffset = C();t.hcMakeTime = function (a, e, d, k, n, f) {
        var b;g ? (b = t.UTC.apply(0, arguments), b += m(b)) : b = new t(a, e, l(d, 1), l(k, 0), l(n, 0), l(f, 0)).getTime();return b;
      };E("Minutes Hours Day Date Month FullYear".split(" "), function (a) {
        t["hcGet" + a] = d + a;
      });E("Milliseconds Seconds Minutes Hours Date Month FullYear".split(" "), function (a) {
        t["hcSet" + a] = k + a;
      });
    }var F = a.color,
        E = a.each,
        m = a.getTZOffset,
        f = a.merge,
        l = a.pick,
        r = a.win;a.defaultOptions = { colors: "#7cb5ec #434348 #90ed7d #f7a35c #8085e9 #f15c80 #e4d354 #2b908f #f45b5b #91e8e1".split(" "), symbols: ["circle", "diamond", "square", "triangle", "triangle-down"], lang: { loading: "Loading...", months: "January February March April May June July August September October November December".split(" "), shortMonths: "Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec".split(" "), weekdays: "Sunday Monday Tuesday Wednesday Thursday Friday Saturday".split(" "),
        decimalPoint: ".", numericSymbols: "kMGTPE".split(""), resetZoom: "Reset zoom", resetZoomTitle: "Reset zoom level 1:1", thousandsSep: " " }, global: { useUTC: !0, VMLRadialGradientURL: "http://code.highcharts.com/5.0.14/gfx/vml-radial-gradient.png" }, chart: { borderRadius: 0, defaultSeriesType: "line", ignoreHiddenSeries: !0, spacing: [10, 10, 15, 10], resetZoomButton: { theme: { zIndex: 20 }, position: { align: "right", x: -10, y: 10 } }, width: null, height: null, borderColor: "#335cad", backgroundColor: "#ffffff", plotBorderColor: "#cccccc" }, title: { text: "Chart title",
        align: "center", margin: 15, widthAdjust: -44 }, subtitle: { text: "", align: "center", widthAdjust: -44 }, plotOptions: {}, labels: { style: { position: "absolute", color: "#333333" } }, legend: { enabled: !0, align: "center", layout: "horizontal", labelFormatter: function labelFormatter() {
          return this.name;
        }, borderColor: "#999999", borderRadius: 0, navigation: { activeColor: "#003399", inactiveColor: "#cccccc" }, itemStyle: { color: "#333333", fontSize: "12px", fontWeight: "bold", textOverflow: "ellipsis" }, itemHoverStyle: { color: "#000000" }, itemHiddenStyle: { color: "#cccccc" },
        shadow: !1, itemCheckboxStyle: { position: "absolute", width: "13px", height: "13px" }, squareSymbol: !0, symbolPadding: 5, verticalAlign: "bottom", x: 0, y: 0, title: { style: { fontWeight: "bold" } } }, loading: { labelStyle: { fontWeight: "bold", position: "relative", top: "45%" }, style: { position: "absolute", backgroundColor: "#ffffff", opacity: .5, textAlign: "center" } }, tooltip: { enabled: !0, animation: a.svg, borderRadius: 3, dateTimeLabelFormats: { millisecond: "%A, %b %e, %H:%M:%S.%L", second: "%A, %b %e, %H:%M:%S", minute: "%A, %b %e, %H:%M", hour: "%A, %b %e, %H:%M",
          day: "%A, %b %e, %Y", week: "Week from %A, %b %e, %Y", month: "%B %Y", year: "%Y" }, footerFormat: "", padding: 8, snap: a.isTouchDevice ? 25 : 10, backgroundColor: F("#f7f7f7").setOpacity(.85).get(), borderWidth: 1, headerFormat: '\x3cspan style\x3d"font-size: 10px"\x3e{point.key}\x3c/span\x3e\x3cbr/\x3e', pointFormat: "<span style=\"color:{point.color}\">\u25CF</span> {series.name}: <b>{point.y}</b><br/>", shadow: !0, style: { color: "#333333", cursor: "default", fontSize: "12px", pointerEvents: "none",
          whiteSpace: "nowrap" } }, credits: { enabled: !0, href: "http://www.highcharts.com", position: { align: "right", x: -10, verticalAlign: "bottom", y: -5 }, style: { cursor: "pointer", color: "#999999", fontSize: "9px" }, text: "Highcharts.com" } };a.setOptions = function (r) {
      a.defaultOptions = f(!0, a.defaultOptions, r);A();return a.defaultOptions;
    };a.getOptions = function () {
      return a.defaultOptions;
    };a.defaultPlotOptions = a.defaultOptions.plotOptions;A();
  })(M);(function (a) {
    var C = a.correctFloat,
        A = a.defined,
        F = a.destroyObjectProperties,
        E = a.isNumber,
        m = a.merge,
        f = a.pick,
        l = a.deg2rad;a.Tick = function (a, f, l, g) {
      this.axis = a;this.pos = f;this.type = l || "";this.isNewLabel = this.isNew = !0;l || g || this.addLabel();
    };a.Tick.prototype = { addLabel: function addLabel() {
        var a = this.axis,
            l = a.options,
            t = a.chart,
            g = a.categories,
            d = a.names,
            k = this.pos,
            b = l.labels,
            e = a.tickPositions,
            v = k === e[0],
            y = k === e[e.length - 1],
            d = g ? f(g[k], d[k], k) : k,
            g = this.label,
            e = e.info,
            n;a.isDatetimeAxis && e && (n = l.dateTimeLabelFormats[e.higherRanks[k] || e.unitName]);this.isFirst = v;this.isLast = y;l = a.labelFormatter.call({ axis: a,
          chart: t, isFirst: v, isLast: y, dateTimeLabelFormat: n, value: a.isLog ? C(a.lin2log(d)) : d, pos: k });A(g) ? g && g.attr({ text: l }) : (this.labelLength = (this.label = g = A(l) && b.enabled ? t.renderer.text(l, 0, 0, b.useHTML).css(m(b.style)).add(a.labelGroup) : null) && g.getBBox().width, this.rotation = 0);
      }, getLabelSize: function getLabelSize() {
        return this.label ? this.label.getBBox()[this.axis.horiz ? "height" : "width"] : 0;
      }, handleOverflow: function handleOverflow(a) {
        var r = this.axis,
            m = a.x,
            g = r.chart.chartWidth,
            d = r.chart.spacing,
            k = f(r.labelLeft, Math.min(r.pos, d[3])),
            d = f(r.labelRight, Math.max(r.pos + r.len, g - d[1])),
            b = this.label,
            e = this.rotation,
            v = { left: 0, center: .5, right: 1 }[r.labelAlign],
            y = b.getBBox().width,
            n = r.getSlotWidth(),
            D = n,
            J = 1,
            c,
            G = {};if (e) 0 > e && m - v * y < k ? c = Math.round(m / Math.cos(e * l) - k) : 0 < e && m + v * y > d && (c = Math.round((g - m) / Math.cos(e * l)));else if (g = m + (1 - v) * y, m - v * y < k ? D = a.x + D * (1 - v) - k : g > d && (D = d - a.x + D * v, J = -1), D = Math.min(n, D), D < n && "center" === r.labelAlign && (a.x += J * (n - D - v * (n - Math.min(y, D)))), y > D || r.autoRotation && (b.styles || {}).width) c = D;c && (G.width = c, (r.options.labels.style || {}).textOverflow || (G.textOverflow = "ellipsis"), b.css(G));
      }, getPosition: function getPosition(a, f, l, g) {
        var d = this.axis,
            k = d.chart,
            b = g && k.oldChartHeight || k.chartHeight;return { x: a ? d.translate(f + l, null, null, g) + d.transB : d.left + d.offset + (d.opposite ? (g && k.oldChartWidth || k.chartWidth) - d.right - d.left : 0), y: a ? b - d.bottom + d.offset - (d.opposite ? d.height : 0) : b - d.translate(f + l, null, null, g) - d.transB };
      }, getLabelPosition: function getLabelPosition(a, f, m, g, d, k, b, e) {
        var v = this.axis,
            y = v.transA,
            n = v.reversed,
            D = v.staggerLines,
            r = v.tickRotCorr || { x: 0, y: 0 },
            c = d.y;A(c) || (c = 0 === v.side ? m.rotation ? -8 : -m.getBBox().height : 2 === v.side ? r.y + 8 : Math.cos(m.rotation * l) * (r.y - m.getBBox(!1, 0).height / 2));a = a + d.x + r.x - (k && g ? k * y * (n ? -1 : 1) : 0);f = f + c - (k && !g ? k * y * (n ? 1 : -1) : 0);D && (m = b / (e || 1) % D, v.opposite && (m = D - m - 1), f += v.labelOffset / D * m);return { x: a, y: Math.round(f) };
      }, getMarkPath: function getMarkPath(a, f, l, g, d, k) {
        return k.crispLine(["M", a, f, "L", a + (d ? 0 : -l), f + (d ? l : 0)], g);
      }, renderGridLine: function renderGridLine(a, f, l) {
        var g = this.axis,
            d = g.options,
            k = this.gridLine,
            b = {},
            e = this.pos,
            v = this.type,
            y = g.tickmarkOffset,
            n = g.chart.renderer,
            D = v ? v + "Grid" : "grid",
            r = d[D + "LineWidth"],
            c = d[D + "LineColor"],
            d = d[D + "LineDashStyle"];k || (b.stroke = c, b["stroke-width"] = r, d && (b.dashstyle = d), v || (b.zIndex = 1), a && (b.opacity = 0), this.gridLine = k = n.path().attr(b).addClass("highcharts-" + (v ? v + "-" : "") + "grid-line").add(g.gridGroup));if (!a && k && (a = g.getPlotLinePath(e + y, k.strokeWidth() * l, a, !0))) k[this.isNew ? "attr" : "animate"]({ d: a, opacity: f });
      }, renderMark: function renderMark(a, l, m) {
        var g = this.axis,
            d = g.options,
            k = g.chart.renderer,
            b = this.type,
            e = b ? b + "Tick" : "tick",
            v = g.tickSize(e),
            y = this.mark,
            n = !y,
            D = a.x;a = a.y;var r = f(d[e + "Width"], !b && g.isXAxis ? 1 : 0),
            d = d[e + "Color"];v && (g.opposite && (v[0] = -v[0]), n && (this.mark = y = k.path().addClass("highcharts-" + (b ? b + "-" : "") + "tick").add(g.axisGroup), y.attr({ stroke: d, "stroke-width": r })), y[n ? "attr" : "animate"]({ d: this.getMarkPath(D, a, v[0], y.strokeWidth() * m, g.horiz, k), opacity: l }));
      }, renderLabel: function renderLabel(a, l, m, g) {
        var d = this.axis,
            k = d.horiz,
            b = d.options,
            e = this.label,
            v = b.labels,
            y = v.step,
            n = d.tickmarkOffset,
            D = !0,
            r = a.x;a = a.y;e && E(r) && (e.xy = a = this.getLabelPosition(r, a, e, k, v, n, g, y), this.isFirst && !this.isLast && !f(b.showFirstLabel, 1) || this.isLast && !this.isFirst && !f(b.showLastLabel, 1) ? D = !1 : !k || d.isRadial || v.step || v.rotation || l || 0 === m || this.handleOverflow(a), y && g % y && (D = !1), D && E(a.y) ? (a.opacity = m, e[this.isNewLabel ? "attr" : "animate"](a), this.isNewLabel = !1) : (e.attr("y", -9999), this.isNewLabel = !0), this.isNew = !1);
      }, render: function render(a, l, m) {
        var g = this.axis,
            d = g.horiz,
            k = this.getPosition(d, this.pos, g.tickmarkOffset, l),
            b = k.x,
            e = k.y,
            g = d && b === g.pos + g.len || !d && e === g.pos ? -1 : 1;m = f(m, 1);this.isActive = !0;this.renderGridLine(l, m, g);this.renderMark(k, m, g);this.renderLabel(k, l, m, a);
      }, destroy: function destroy() {
        F(this, this.axis);
      } };
  })(M);var S = function (a) {
    var C = a.addEvent,
        A = a.animObject,
        F = a.arrayMax,
        E = a.arrayMin,
        m = a.color,
        f = a.correctFloat,
        l = a.defaultOptions,
        r = a.defined,
        u = a.deg2rad,
        t = a.destroyObjectProperties,
        g = a.each,
        d = a.extend,
        k = a.fireEvent,
        b = a.format,
        e = a.getMagnitude,
        v = a.grep,
        y = a.inArray,
        n = a.isArray,
        D = a.isNumber,
        J = a.isString,
        c = a.merge,
        G = a.normalizeTickInterval,
        q = a.objectEach,
        B = a.pick,
        K = a.removeEvent,
        p = a.splat,
        z = a.syncTimeout,
        I = a.Tick,
        L = function L() {
      this.init.apply(this, arguments);
    };a.extend(L.prototype, { defaultOptions: { dateTimeLabelFormats: { millisecond: "%H:%M:%S.%L", second: "%H:%M:%S", minute: "%H:%M", hour: "%H:%M", day: "%e. %b", week: "%e. %b", month: "%b '%y", year: "%Y" }, endOnTick: !1, labels: { enabled: !0, style: { color: "#666666", cursor: "default", fontSize: "11px" }, x: 0 }, minPadding: .01, maxPadding: .01, minorTickLength: 2, minorTickPosition: "outside", startOfWeek: 1, startOnTick: !1, tickLength: 10, tickmarkPlacement: "between", tickPixelInterval: 100,
        tickPosition: "outside", title: { align: "middle", style: { color: "#666666" } }, type: "linear", minorGridLineColor: "#f2f2f2", minorGridLineWidth: 1, minorTickColor: "#999999", lineColor: "#ccd6eb", lineWidth: 1, gridLineColor: "#e6e6e6", tickColor: "#ccd6eb" }, defaultYAxisOptions: { endOnTick: !0, tickPixelInterval: 72, showLastLabel: !0, labels: { x: -8 }, maxPadding: .05, minPadding: .05, startOnTick: !0, title: { rotation: 270, text: "Values" }, stackLabels: { allowOverlap: !1, enabled: !1, formatter: function formatter() {
            return a.numberFormat(this.total, -1);
          },
          style: { fontSize: "11px", fontWeight: "bold", color: "#000000", textOutline: "1px contrast" } }, gridLineWidth: 1, lineWidth: 0 }, defaultLeftAxisOptions: { labels: { x: -15 }, title: { rotation: 270 } }, defaultRightAxisOptions: { labels: { x: 15 }, title: { rotation: 90 } }, defaultBottomAxisOptions: { labels: { autoRotation: [-45], x: 0 }, title: { rotation: 0 } }, defaultTopAxisOptions: { labels: { autoRotation: [-45], x: 0 }, title: { rotation: 0 } }, init: function init(a, c) {
        var h = c.isX,
            b = this;b.chart = a;b.horiz = a.inverted && !b.isZAxis ? !h : h;b.isXAxis = h;b.coll = b.coll || (h ? "xAxis" : "yAxis");b.opposite = c.opposite;b.side = c.side || (b.horiz ? b.opposite ? 0 : 2 : b.opposite ? 1 : 3);b.setOptions(c);var w = this.options,
            e = w.type;b.labelFormatter = w.labels.formatter || b.defaultLabelFormatter;b.userOptions = c;b.minPixelPadding = 0;b.reversed = w.reversed;b.visible = !1 !== w.visible;b.zoomEnabled = !1 !== w.zoomEnabled;b.hasNames = "category" === e || !0 === w.categories;b.categories = w.categories || b.hasNames;b.names = b.names || [];b.plotLinesAndBandsGroups = {};b.isLog = "logarithmic" === e;b.isDatetimeAxis = "datetime" === e;b.positiveValuesOnly = b.isLog && !b.allowNegativeLog;b.isLinked = r(w.linkedTo);b.ticks = {};b.labelEdge = [];b.minorTicks = {};b.plotLinesAndBands = [];b.alternateBands = {};b.len = 0;b.minRange = b.userMinRange = w.minRange || w.maxZoom;b.range = w.range;b.offset = w.offset || 0;b.stacks = {};b.oldStacks = {};b.stacksTouched = 0;b.max = null;b.min = null;b.crosshair = B(w.crosshair, p(a.options.tooltip.crosshairs)[h ? 0 : 1], !1);c = b.options.events;-1 === y(b, a.axes) && (h ? a.axes.splice(a.xAxis.length, 0, b) : a.axes.push(b), a[b.coll].push(b));b.series = b.series || [];a.inverted && !b.isZAxis && h && void 0 === b.reversed && (b.reversed = !0);q(c, function (a, h) {
          C(b, h, a);
        });b.lin2log = w.linearToLogConverter || b.lin2log;b.isLog && (b.val2lin = b.log2lin, b.lin2val = b.lin2log);
      }, setOptions: function setOptions(a) {
        this.options = c(this.defaultOptions, "yAxis" === this.coll && this.defaultYAxisOptions, [this.defaultTopAxisOptions, this.defaultRightAxisOptions, this.defaultBottomAxisOptions, this.defaultLeftAxisOptions][this.side], c(l[this.coll], a));
      }, defaultLabelFormatter: function defaultLabelFormatter() {
        var h = this.axis,
            c = this.value,
            e = h.categories,
            p = this.dateTimeLabelFormat,
            d = l.lang,
            n = d.numericSymbols,
            d = d.numericSymbolMagnitude || 1E3,
            q = n && n.length,
            x,
            g = h.options.labels.format,
            h = h.isLog ? Math.abs(c) : h.tickInterval;if (g) x = b(g, this);else if (e) x = c;else if (p) x = a.dateFormat(p, c);else if (q && 1E3 <= h) for (; q-- && void 0 === x;) {
          e = Math.pow(d, q + 1), h >= e && 0 === 10 * c % e && null !== n[q] && 0 !== c && (x = a.numberFormat(c / e, -1) + n[q]);
        }void 0 === x && (x = 1E4 <= Math.abs(c) ? a.numberFormat(c, -1) : a.numberFormat(c, -1, void 0, ""));return x;
      }, getSeriesExtremes: function getSeriesExtremes() {
        var a = this,
            b = a.chart;a.hasVisibleSeries = !1;a.dataMin = a.dataMax = a.threshold = null;a.softThreshold = !a.isXAxis;a.buildStacks && a.buildStacks();g(a.series, function (h) {
          if (h.visible || !b.options.chart.ignoreHiddenSeries) {
            var c = h.options,
                w = c.threshold,
                e;a.hasVisibleSeries = !0;a.positiveValuesOnly && 0 >= w && (w = null);if (a.isXAxis) c = h.xData, c.length && (h = E(c), D(h) || h instanceof Date || (c = v(c, function (a) {
              return D(a);
            }), h = E(c)), a.dataMin = Math.min(B(a.dataMin, c[0]), h), a.dataMax = Math.max(B(a.dataMax, c[0]), F(c)));else if (h.getExtremes(), e = h.dataMax, h = h.dataMin, r(h) && r(e) && (a.dataMin = Math.min(B(a.dataMin, h), h), a.dataMax = Math.max(B(a.dataMax, e), e)), r(w) && (a.threshold = w), !c.softThreshold || a.positiveValuesOnly) a.softThreshold = !1;
          }
        });
      }, translate: function translate(a, b, c, e, p, d) {
        var h = this.linkedParent || this,
            w = 1,
            n = 0,
            q = e ? h.oldTransA : h.transA;e = e ? h.oldMin : h.min;var g = h.minPixelPadding;p = (h.isOrdinal || h.isBroken || h.isLog && p) && h.lin2val;q || (q = h.transA);c && (w *= -1, n = h.len);h.reversed && (w *= -1, n -= w * (h.sector || h.len));b ? (a = (a * w + n - g) / q + e, p && (a = h.lin2val(a))) : (p && (a = h.val2lin(a)), a = w * (a - e) * q + n + w * g + (D(d) ? q * d : 0));return a;
      }, toPixels: function toPixels(a, b) {
        return this.translate(a, !1, !this.horiz, null, !0) + (b ? 0 : this.pos);
      }, toValue: function toValue(a, b) {
        return this.translate(a - (b ? 0 : this.pos), !0, !this.horiz, null, !0);
      }, getPlotLinePath: function getPlotLinePath(a, b, c, e, p) {
        var h = this.chart,
            w = this.left,
            d = this.top,
            n,
            q,
            g = c && h.oldChartHeight || h.chartHeight,
            k = c && h.oldChartWidth || h.chartWidth,
            f;n = this.transB;var v = function v(a, h, b) {
          if (a < h || a > b) e ? a = Math.min(Math.max(h, a), b) : f = !0;return a;
        };p = B(p, this.translate(a, null, null, c));a = c = Math.round(p + n);n = q = Math.round(g - p - n);D(p) ? this.horiz ? (n = d, q = g - this.bottom, a = c = v(a, w, w + this.width)) : (a = w, c = k - this.right, n = q = v(n, d, d + this.height)) : f = !0;return f && !e ? null : h.renderer.crispLine(["M", a, n, "L", c, q], b || 1);
      }, getLinearTickPositions: function getLinearTickPositions(a, b, c) {
        var h,
            w = f(Math.floor(b / a) * a);c = f(Math.ceil(c / a) * a);var e = [];if (this.single) return [b];for (b = w; b <= c;) {
          e.push(b);b = f(b + a);if (b === h) break;h = b;
        }return e;
      }, getMinorTickPositions: function getMinorTickPositions() {
        var a = this,
            b = a.options,
            c = a.tickPositions,
            e = a.minorTickInterval,
            p = [],
            d = a.pointRangePadding || 0,
            n = a.min - d,
            d = a.max + d,
            q = d - n;if (q && q / e < a.len / 3) if (a.isLog) g(this.paddedTicks, function (h, b, c) {
          b && p.push.apply(p, a.getLogTickPositions(e, c[b - 1], c[b], !0));
        });else if (a.isDatetimeAxis && "auto" === b.minorTickInterval) p = p.concat(a.getTimeTicks(a.normalizeTimeTickInterval(e), n, d, b.startOfWeek));else for (b = n + (c[0] - n) % e; b <= d && b !== p[0]; b += e) {
          p.push(b);
        }0 !== p.length && a.trimTicks(p);return p;
      }, adjustForMinRange: function adjustForMinRange() {
        var a = this.options,
            b = this.min,
            c = this.max,
            e,
            p,
            d,
            n,
            q,
            k,
            f,
            v;this.isXAxis && void 0 === this.minRange && !this.isLog && (r(a.min) || r(a.max) ? this.minRange = null : (g(this.series, function (a) {
          k = a.xData;for (n = f = a.xIncrement ? 1 : k.length - 1; 0 < n; n--) {
            if (q = k[n] - k[n - 1], void 0 === d || q < d) d = q;
          }
        }), this.minRange = Math.min(5 * d, this.dataMax - this.dataMin)));c - b < this.minRange && (p = this.dataMax - this.dataMin >= this.minRange, v = this.minRange, e = (v - c + b) / 2, e = [b - e, B(a.min, b - e)], p && (e[2] = this.isLog ? this.log2lin(this.dataMin) : this.dataMin), b = F(e), c = [b + v, B(a.max, b + v)], p && (c[2] = this.isLog ? this.log2lin(this.dataMax) : this.dataMax), c = E(c), c - b < v && (e[0] = c - v, e[1] = B(a.min, c - v), b = F(e)));this.min = b;this.max = c;
      }, getClosest: function getClosest() {
        var a;this.categories ? a = 1 : g(this.series, function (h) {
          var b = h.closestPointRange,
              c = h.visible || !h.chart.options.chart.ignoreHiddenSeries;!h.noSharedTooltip && r(b) && c && (a = r(a) ? Math.min(a, b) : b);
        });return a;
      }, nameToX: function nameToX(a) {
        var h = n(this.categories),
            b = h ? this.categories : this.names,
            c = a.options.x,
            e;a.series.requireSorting = !1;r(c) || (c = !1 === this.options.uniqueNames ? a.series.autoIncrement() : y(a.name, b));-1 === c ? h || (e = b.length) : e = c;void 0 !== e && (this.names[e] = a.name);return e;
      }, updateNames: function updateNames() {
        var a = this;0 < this.names.length && (this.names.length = 0, this.minRange = this.userMinRange, g(this.series || [], function (h) {
          h.xIncrement = null;if (!h.points || h.isDirtyData) h.processData(), h.generatePoints();g(h.points, function (b, c) {
            var e;b.options && (e = a.nameToX(b), void 0 !== e && e !== b.x && (b.x = e, h.xData[c] = e));
          });
        }));
      }, setAxisTranslation: function setAxisTranslation(a) {
        var h = this,
            b = h.max - h.min,
            c = h.axisPointRange || 0,
            e,
            p = 0,
            d = 0,
            n = h.linkedParent,
            q = !!h.categories,
            k = h.transA,
            f = h.isXAxis;if (f || q || c) e = h.getClosest(), n ? (p = n.minPointOffset, d = n.pointRangePadding) : g(h.series, function (a) {
          var b = q ? 1 : f ? B(a.options.pointRange, e, 0) : h.axisPointRange || 0;a = a.options.pointPlacement;c = Math.max(c, b);h.single || (p = Math.max(p, J(a) ? 0 : b / 2), d = Math.max(d, "on" === a ? 0 : b));
        }), n = h.ordinalSlope && e ? h.ordinalSlope / e : 1, h.minPointOffset = p *= n, h.pointRangePadding = d *= n, h.pointRange = Math.min(c, b), f && (h.closestPointRange = e);a && (h.oldTransA = k);h.translationSlope = h.transA = k = h.options.staticScale || h.len / (b + d || 1);h.transB = h.horiz ? h.left : h.bottom;h.minPixelPadding = k * p;
      }, minFromRange: function minFromRange() {
        return this.max - this.range;
      }, setTickInterval: function setTickInterval(h) {
        var b = this,
            c = b.chart,
            p = b.options,
            d = b.isLog,
            n = b.log2lin,
            q = b.isDatetimeAxis,
            x = b.isXAxis,
            v = b.isLinked,
            z = p.maxPadding,
            y = p.minPadding,
            l = p.tickInterval,
            I = p.tickPixelInterval,
            m = b.categories,
            J = b.threshold,
            t = b.softThreshold,
            L,
            u,
            K,
            A;q || m || v || this.getTickAmount();K = B(b.userMin, p.min);A = B(b.userMax, p.max);v ? (b.linkedParent = c[b.coll][p.linkedTo], c = b.linkedParent.getExtremes(), b.min = B(c.min, c.dataMin), b.max = B(c.max, c.dataMax), p.type !== b.linkedParent.options.type && a.error(11, 1)) : (!t && r(J) && (b.dataMin >= J ? (L = J, y = 0) : b.dataMax <= J && (u = J, z = 0)), b.min = B(K, L, b.dataMin), b.max = B(A, u, b.dataMax));d && (b.positiveValuesOnly && !h && 0 >= Math.min(b.min, B(b.dataMin, b.min)) && a.error(10, 1), b.min = f(n(b.min), 15), b.max = f(n(b.max), 15));b.range && r(b.max) && (b.userMin = b.min = K = Math.max(b.dataMin, b.minFromRange()), b.userMax = A = b.max, b.range = null);k(b, "foundExtremes");b.beforePadding && b.beforePadding();b.adjustForMinRange();
        !(m || b.axisPointRange || b.usePercentage || v) && r(b.min) && r(b.max) && (n = b.max - b.min) && (!r(K) && y && (b.min -= n * y), !r(A) && z && (b.max += n * z));D(p.softMin) && (b.min = Math.min(b.min, p.softMin));D(p.softMax) && (b.max = Math.max(b.max, p.softMax));D(p.floor) && (b.min = Math.max(b.min, p.floor));D(p.ceiling) && (b.max = Math.min(b.max, p.ceiling));t && r(b.dataMin) && (J = J || 0, !r(K) && b.min < J && b.dataMin >= J ? b.min = J : !r(A) && b.max > J && b.dataMax <= J && (b.max = J));b.tickInterval = b.min === b.max || void 0 === b.min || void 0 === b.max ? 1 : v && !l && I === b.linkedParent.options.tickPixelInterval ? l = b.linkedParent.tickInterval : B(l, this.tickAmount ? (b.max - b.min) / Math.max(this.tickAmount - 1, 1) : void 0, m ? 1 : (b.max - b.min) * I / Math.max(b.len, I));x && !h && g(b.series, function (a) {
          a.processData(b.min !== b.oldMin || b.max !== b.oldMax);
        });b.setAxisTranslation(!0);b.beforeSetTickPositions && b.beforeSetTickPositions();b.postProcessTickInterval && (b.tickInterval = b.postProcessTickInterval(b.tickInterval));b.pointRange && !l && (b.tickInterval = Math.max(b.pointRange, b.tickInterval));h = B(p.minTickInterval, b.isDatetimeAxis && b.closestPointRange);
        !l && b.tickInterval < h && (b.tickInterval = h);q || d || l || (b.tickInterval = G(b.tickInterval, null, e(b.tickInterval), B(p.allowDecimals, !(.5 < b.tickInterval && 5 > b.tickInterval && 1E3 < b.max && 9999 > b.max)), !!this.tickAmount));this.tickAmount || (b.tickInterval = b.unsquish());this.setTickPositions();
      }, setTickPositions: function setTickPositions() {
        var a = this.options,
            b,
            c = a.tickPositions,
            e = a.tickPositioner,
            p = a.startOnTick,
            d = a.endOnTick;this.tickmarkOffset = this.categories && "between" === a.tickmarkPlacement && 1 === this.tickInterval ? .5 : 0;this.minorTickInterval = "auto" === a.minorTickInterval && this.tickInterval ? this.tickInterval / 5 : a.minorTickInterval;this.single = this.min === this.max && r(this.min) && !this.tickAmount && (parseInt(this.min, 10) === this.min || !1 !== a.allowDecimals);this.tickPositions = b = c && c.slice();!b && (b = this.isDatetimeAxis ? this.getTimeTicks(this.normalizeTimeTickInterval(this.tickInterval, a.units), this.min, this.max, a.startOfWeek, this.ordinalPositions, this.closestPointRange, !0) : this.isLog ? this.getLogTickPositions(this.tickInterval, this.min, this.max) : this.getLinearTickPositions(this.tickInterval, this.min, this.max), b.length > this.len && (b = [b[0], b.pop()]), this.tickPositions = b, e && (e = e.apply(this, [this.min, this.max]))) && (this.tickPositions = b = e);this.paddedTicks = b.slice(0);this.trimTicks(b, p, d);this.isLinked || (this.single && 2 > b.length && (this.min -= .5, this.max += .5), c || e || this.adjustTickAmount());
      }, trimTicks: function trimTicks(a, b, c) {
        var h = a[0],
            e = a[a.length - 1],
            p = this.minPointOffset || 0;if (!this.isLinked) {
          if (b && -Infinity !== h) this.min = h;else for (; this.min - p > a[0];) {
            a.shift();
          }if (c) this.max = e;else for (; this.max + p < a[a.length - 1];) {
            a.pop();
          }0 === a.length && r(h) && a.push((e + h) / 2);
        }
      }, alignToOthers: function alignToOthers() {
        var a = {},
            b,
            c = this.options;!1 === this.chart.options.chart.alignTicks || !1 === c.alignTicks || this.isLog || g(this.chart[this.coll], function (h) {
          var c = h.options,
              c = [h.horiz ? c.left : c.top, c.width, c.height, c.pane].join();h.series.length && (a[c] ? b = !0 : a[c] = 1);
        });return b;
      }, getTickAmount: function getTickAmount() {
        var a = this.options,
            b = a.tickAmount,
            c = a.tickPixelInterval;!r(a.tickInterval) && this.len < c && !this.isRadial && !this.isLog && a.startOnTick && a.endOnTick && (b = 2);!b && this.alignToOthers() && (b = Math.ceil(this.len / c) + 1);4 > b && (this.finalTickAmt = b, b = 5);this.tickAmount = b;
      }, adjustTickAmount: function adjustTickAmount() {
        var a = this.tickInterval,
            b = this.tickPositions,
            c = this.tickAmount,
            e = this.finalTickAmt,
            p = b && b.length;if (p < c) {
          for (; b.length < c;) {
            b.push(f(b[b.length - 1] + a));
          }this.transA *= (p - 1) / (c - 1);this.max = b[b.length - 1];
        } else p > c && (this.tickInterval *= 2, this.setTickPositions());if (r(e)) {
          for (a = c = b.length; a--;) {
            (3 === e && 1 === a % 2 || 2 >= e && 0 < a && a < c - 1) && b.splice(a, 1);
          }this.finalTickAmt = void 0;
        }
      }, setScale: function setScale() {
        var a, b;this.oldMin = this.min;this.oldMax = this.max;this.oldAxisLength = this.len;this.setAxisSize();b = this.len !== this.oldAxisLength;g(this.series, function (b) {
          if (b.isDirtyData || b.isDirty || b.xAxis.isDirty) a = !0;
        });b || a || this.isLinked || this.forceRedraw || this.userMin !== this.oldUserMin || this.userMax !== this.oldUserMax || this.alignToOthers() ? (this.resetStacks && this.resetStacks(), this.forceRedraw = !1, this.getSeriesExtremes(), this.setTickInterval(), this.oldUserMin = this.userMin, this.oldUserMax = this.userMax, this.isDirty || (this.isDirty = b || this.min !== this.oldMin || this.max !== this.oldMax)) : this.cleanStacks && this.cleanStacks();
      }, setExtremes: function setExtremes(a, b, c, e, p) {
        var h = this,
            n = h.chart;c = B(c, !0);g(h.series, function (a) {
          delete a.kdTree;
        });p = d(p, { min: a, max: b });k(h, "setExtremes", p, function () {
          h.userMin = a;h.userMax = b;h.eventArgs = p;c && n.redraw(e);
        });
      }, zoom: function zoom(a, b) {
        var h = this.dataMin,
            c = this.dataMax,
            e = this.options,
            p = Math.min(h, B(e.min, h)),
            e = Math.max(c, B(e.max, c));if (a !== this.min || b !== this.max) this.allowZoomOutside || (r(h) && (a < p && (a = p), a > e && (a = e)), r(c) && (b < p && (b = p), b > e && (b = e))), this.displayBtn = void 0 !== a || void 0 !== b, this.setExtremes(a, b, !1, void 0, { trigger: "zoom" });return !0;
      }, setAxisSize: function setAxisSize() {
        var b = this.chart,
            c = this.options,
            e = c.offsets || [0, 0, 0, 0],
            p = this.horiz,
            d = this.width = Math.round(a.relativeLength(B(c.width, b.plotWidth - e[3] + e[1]), b.plotWidth)),
            n = this.height = Math.round(a.relativeLength(B(c.height, b.plotHeight - e[0] + e[2]), b.plotHeight)),
            q = this.top = Math.round(a.relativeLength(B(c.top, b.plotTop + e[0]), b.plotHeight, b.plotTop)),
            c = this.left = Math.round(a.relativeLength(B(c.left, b.plotLeft + e[3]), b.plotWidth, b.plotLeft));this.bottom = b.chartHeight - n - q;this.right = b.chartWidth - d - c;this.len = Math.max(p ? d : n, 0);this.pos = p ? c : q;
      }, getExtremes: function getExtremes() {
        var a = this.isLog,
            b = this.lin2log;return { min: a ? f(b(this.min)) : this.min, max: a ? f(b(this.max)) : this.max, dataMin: this.dataMin, dataMax: this.dataMax, userMin: this.userMin, userMax: this.userMax };
      }, getThreshold: function getThreshold(a) {
        var b = this.isLog,
            h = this.lin2log,
            c = b ? h(this.min) : this.min,
            b = b ? h(this.max) : this.max;
        null === a ? a = c : c > a ? a = c : b < a && (a = b);return this.translate(a, 0, 1, 0, 1);
      }, autoLabelAlign: function autoLabelAlign(a) {
        a = (B(a, 0) - 90 * this.side + 720) % 360;return 15 < a && 165 > a ? "right" : 195 < a && 345 > a ? "left" : "center";
      }, tickSize: function tickSize(a) {
        var b = this.options,
            h = b[a + "Length"],
            c = B(b[a + "Width"], "tick" === a && this.isXAxis ? 1 : 0);if (c && h) return "inside" === b[a + "Position"] && (h = -h), [h, c];
      }, labelMetrics: function labelMetrics() {
        var a = this.tickPositions && this.tickPositions[0] || 0;return this.chart.renderer.fontMetrics(this.options.labels.style && this.options.labels.style.fontSize, this.ticks[a] && this.ticks[a].label);
      }, unsquish: function unsquish() {
        var a = this.options.labels,
            b = this.horiz,
            c = this.tickInterval,
            e = c,
            p = this.len / (((this.categories ? 1 : 0) + this.max - this.min) / c),
            d,
            n = a.rotation,
            q = this.labelMetrics(),
            k,
            f = Number.MAX_VALUE,
            v,
            z = function z(a) {
          a /= p || 1;a = 1 < a ? Math.ceil(a) : 1;return a * c;
        };b ? (v = !a.staggerLines && !a.step && (r(n) ? [n] : p < B(a.autoRotationLimit, 80) && a.autoRotation)) && g(v, function (a) {
          var b;if (a === n || a && -90 <= a && 90 >= a) k = z(Math.abs(q.h / Math.sin(u * a))), b = k + Math.abs(a / 360), b < f && (f = b, d = a, e = k);
        }) : a.step || (e = z(q.h));this.autoRotation = v;this.labelRotation = B(d, n);return e;
      }, getSlotWidth: function getSlotWidth() {
        var a = this.chart,
            b = this.horiz,
            c = this.options.labels,
            e = Math.max(this.tickPositions.length - (this.categories ? 0 : 1), 1),
            p = a.margin[3];return b && 2 > (c.step || 0) && !c.rotation && (this.staggerLines || 1) * this.len / e || !b && (p && p - a.spacing[3] || .33 * a.chartWidth);
      }, renderUnsquish: function renderUnsquish() {
        var a = this.chart,
            b = a.renderer,
            e = this.tickPositions,
            p = this.ticks,
            d = this.options.labels,
            n = this.horiz,
            q = this.getSlotWidth(),
            k = Math.max(1, Math.round(q - 2 * (d.padding || 5))),
            f = {},
            v = this.labelMetrics(),
            z = d.style && d.style.textOverflow,
            D,
            y = 0,
            l,
            I;J(d.rotation) || (f.rotation = d.rotation || 0);g(e, function (a) {
          (a = p[a]) && a.labelLength > y && (y = a.labelLength);
        });this.maxLabelLength = y;if (this.autoRotation) y > k && y > v.h ? f.rotation = this.labelRotation : this.labelRotation = 0;else if (q && (D = { width: k + "px" }, !z)) for (D.textOverflow = "clip", l = e.length; !n && l--;) {
          if (I = e[l], k = p[I].label) k.styles && "ellipsis" === k.styles.textOverflow ? k.css({ textOverflow: "clip" }) : p[I].labelLength > q && k.css({ width: q + "px" }), k.getBBox().height > this.len / e.length - (v.h - v.f) && (k.specCss = { textOverflow: "ellipsis" });
        }f.rotation && (D = { width: (y > .5 * a.chartHeight ? .33 * a.chartHeight : a.chartHeight) + "px" }, z || (D.textOverflow = "ellipsis"));if (this.labelAlign = d.align || this.autoLabelAlign(this.labelRotation)) f.align = this.labelAlign;g(e, function (a) {
          var b = (a = p[a]) && a.label;b && (b.attr(f), D && b.css(c(D, b.specCss)), delete b.specCss, a.rotation = f.rotation);
        });this.tickRotCorr = b.rotCorr(v.b, this.labelRotation || 0, 0 !== this.side);
      },
      hasData: function hasData() {
        return this.hasVisibleSeries || r(this.min) && r(this.max) && !!this.tickPositions;
      }, addTitle: function addTitle(a) {
        var b = this.chart.renderer,
            c = this.horiz,
            h = this.opposite,
            e = this.options.title,
            p;this.axisTitle || ((p = e.textAlign) || (p = (c ? { low: "left", middle: "center", high: "right" } : { low: h ? "right" : "left", middle: "center", high: h ? "left" : "right" })[e.align]), this.axisTitle = b.text(e.text, 0, 0, e.useHTML).attr({ zIndex: 7, rotation: e.rotation || 0, align: p }).addClass("highcharts-axis-title").css(e.style).add(this.axisGroup), this.axisTitle.isNew = !0);e.style.width || this.isRadial || this.axisTitle.css({ width: this.len });this.axisTitle[a ? "show" : "hide"](!0);
      }, generateTick: function generateTick(a) {
        var b = this.ticks;b[a] ? b[a].addLabel() : b[a] = new I(this, a);
      }, getOffset: function getOffset() {
        var a = this,
            b = a.chart,
            c = b.renderer,
            e = a.options,
            p = a.tickPositions,
            d = a.ticks,
            n = a.horiz,
            k = a.side,
            f = b.inverted && !a.isZAxis ? [1, 0, 3, 2][k] : k,
            v,
            z,
            D = 0,
            y,
            l = 0,
            I = e.title,
            m = e.labels,
            G = 0,
            J = b.axisOffset,
            b = b.clipOffset,
            t = [-1, 1, 1, -1][k],
            L = e.className,
            u = a.axisParent,
            K = this.tickSize("tick");
        v = a.hasData();a.showAxis = z = v || B(e.showEmpty, !0);a.staggerLines = a.horiz && m.staggerLines;a.axisGroup || (a.gridGroup = c.g("grid").attr({ zIndex: e.gridZIndex || 1 }).addClass("highcharts-" + this.coll.toLowerCase() + "-grid " + (L || "")).add(u), a.axisGroup = c.g("axis").attr({ zIndex: e.zIndex || 2 }).addClass("highcharts-" + this.coll.toLowerCase() + " " + (L || "")).add(u), a.labelGroup = c.g("axis-labels").attr({ zIndex: m.zIndex || 7 }).addClass("highcharts-" + a.coll.toLowerCase() + "-labels " + (L || "")).add(u));v || a.isLinked ? (g(p, function (b, c) {
          a.generateTick(b, c);
        }), a.renderUnsquish(), !1 === m.reserveSpace || 0 !== k && 2 !== k && { 1: "left", 3: "right" }[k] !== a.labelAlign && "center" !== a.labelAlign || g(p, function (a) {
          G = Math.max(d[a].getLabelSize(), G);
        }), a.staggerLines && (G *= a.staggerLines, a.labelOffset = G * (a.opposite ? -1 : 1))) : q(d, function (a, b) {
          a.destroy();delete d[b];
        });I && I.text && !1 !== I.enabled && (a.addTitle(z), z && !1 !== I.reserveSpace && (a.titleOffset = D = a.axisTitle.getBBox()[n ? "height" : "width"], y = I.offset, l = r(y) ? 0 : B(I.margin, n ? 5 : 10)));a.renderLine();a.offset = t * B(e.offset, J[k]);a.tickRotCorr = a.tickRotCorr || { x: 0, y: 0 };c = 0 === k ? -a.labelMetrics().h : 2 === k ? a.tickRotCorr.y : 0;l = Math.abs(G) + l;G && (l = l - c + t * (n ? B(m.y, a.tickRotCorr.y + 8 * t) : m.x));a.axisTitleMargin = B(y, l);J[k] = Math.max(J[k], a.axisTitleMargin + D + t * a.offset, l, v && p.length && K ? K[0] + t * a.offset : 0);p = 2 * Math.floor(a.axisLine.strokeWidth() / 2);0 < e.offset && (p -= 2 * e.offset);b[f] = Math.max(b[f] || p, p);
      }, getLinePath: function getLinePath(a) {
        var b = this.chart,
            c = this.opposite,
            h = this.offset,
            e = this.horiz,
            p = this.left + (c ? this.width : 0) + h,
            h = b.chartHeight - this.bottom - (c ? this.height : 0) + h;c && (a *= -1);return b.renderer.crispLine(["M", e ? this.left : p, e ? h : this.top, "L", e ? b.chartWidth - this.right : p, e ? h : b.chartHeight - this.bottom], a);
      }, renderLine: function renderLine() {
        this.axisLine || (this.axisLine = this.chart.renderer.path().addClass("highcharts-axis-line").add(this.axisGroup), this.axisLine.attr({ stroke: this.options.lineColor, "stroke-width": this.options.lineWidth, zIndex: 7 }));
      }, getTitlePosition: function getTitlePosition() {
        var a = this.horiz,
            b = this.left,
            c = this.top,
            e = this.len,
            p = this.options.title,
            d = a ? b : c,
            n = this.opposite,
            q = this.offset,
            k = p.x || 0,
            g = p.y || 0,
            f = this.axisTitle,
            v = this.chart.renderer.fontMetrics(p.style && p.style.fontSize, f),
            f = Math.max(f.getBBox(null, 0).height - v.h - 1, 0),
            e = { low: d + (a ? 0 : e), middle: d + e / 2, high: d + (a ? e : 0) }[p.align],
            b = (a ? c + this.height : b) + (a ? 1 : -1) * (n ? -1 : 1) * this.axisTitleMargin + [-f, f, v.f, -f][this.side];return { x: a ? e + k : b + (n ? this.width : 0) + q + k, y: a ? b + g - (n ? this.height : 0) + q : e + g };
      }, renderMinorTick: function renderMinorTick(a) {
        var b = this.chart.hasRendered && D(this.oldMin),
            c = this.minorTicks;c[a] || (c[a] = new I(this, a, "minor"));b && c[a].isNew && c[a].render(null, !0);c[a].render(null, !1, 1);
      }, renderTick: function renderTick(a, b) {
        var c = this.isLinked,
            e = this.ticks,
            h = this.chart.hasRendered && D(this.oldMin);if (!c || a >= this.min && a <= this.max) e[a] || (e[a] = new I(this, a)), h && e[a].isNew && e[a].render(b, !0, .1), e[a].render(b);
      }, render: function render() {
        var b = this,
            c = b.chart,
            e = b.options,
            p = b.isLog,
            d = b.lin2log,
            n = b.isLinked,
            k = b.tickPositions,
            f = b.axisTitle,
            v = b.ticks,
            y = b.minorTicks,
            l = b.alternateBands,
            m = e.stackLabels,
            r = e.alternateGridColor,
            B = b.tickmarkOffset,
            G = b.axisLine,
            J = b.showAxis,
            t = A(c.renderer.globalAnimation),
            L,
            u;b.labelEdge.length = 0;b.overlap = !1;g([v, y, l], function (a) {
          q(a, function (a) {
            a.isActive = !1;
          });
        });if (b.hasData() || n) b.minorTickInterval && !b.categories && g(b.getMinorTickPositions(), function (a) {
          b.renderMinorTick(a);
        }), k.length && (g(k, function (a, c) {
          b.renderTick(a, c);
        }), B && (0 === b.min || b.single) && (v[-1] || (v[-1] = new I(b, -1, null, !0)), v[-1].render(-1))), r && g(k, function (e, h) {
          u = void 0 !== k[h + 1] ? k[h + 1] + B : b.max - B;0 === h % 2 && e < b.max && u <= b.max + (c.polar ? -B : B) && (l[e] || (l[e] = new a.PlotLineOrBand(b)), L = e + B, l[e].options = { from: p ? d(L) : L, to: p ? d(u) : u, color: r }, l[e].render(), l[e].isActive = !0);
        }), b._addedPlotLB || (g((e.plotLines || []).concat(e.plotBands || []), function (a) {
          b.addPlotBandOrLine(a);
        }), b._addedPlotLB = !0);g([v, y, l], function (a) {
          var b,
              e = [],
              h = t.duration;q(a, function (a, b) {
            a.isActive || (a.render(b, !1, 0), a.isActive = !1, e.push(b));
          });z(function () {
            for (b = e.length; b--;) {
              a[e[b]] && !a[e[b]].isActive && (a[e[b]].destroy(), delete a[e[b]]);
            }
          }, a !== l && c.hasRendered && h ? h : 0);
        });G && (G[G.isPlaced ? "animate" : "attr"]({ d: this.getLinePath(G.strokeWidth()) }), G.isPlaced = !0, G[J ? "show" : "hide"](!0));f && J && (e = b.getTitlePosition(), D(e.y) ? (f[f.isNew ? "attr" : "animate"](e), f.isNew = !1) : (f.attr("y", -9999), f.isNew = !0));m && m.enabled && b.renderStackTotals();b.isDirty = !1;
      }, redraw: function redraw() {
        this.visible && (this.render(), g(this.plotLinesAndBands, function (a) {
          a.render();
        }));g(this.series, function (a) {
          a.isDirty = !0;
        });
      }, keepProps: "extKey hcEvents names series userMax userMin".split(" "), destroy: function destroy(a) {
        var b = this,
            c = b.stacks,
            e = b.plotLinesAndBands,
            h;a || K(b);q(c, function (a, b) {
          t(a);c[b] = null;
        });g([b.ticks, b.minorTicks, b.alternateBands], function (a) {
          t(a);
        });if (e) for (a = e.length; a--;) {
          e[a].destroy();
        }g("stackTotalGroup axisLine axisTitle axisGroup gridGroup labelGroup cross".split(" "), function (a) {
          b[a] && (b[a] = b[a].destroy());
        });for (h in b.plotLinesAndBandsGroups) {
          b.plotLinesAndBandsGroups[h] = b.plotLinesAndBandsGroups[h].destroy();
        }q(b, function (a, c) {
          -1 === y(c, b.keepProps) && delete b[c];
        });
      }, drawCrosshair: function drawCrosshair(a, b) {
        var c,
            e = this.crosshair,
            h = B(e.snap, !0),
            p,
            d = this.cross;a || (a = this.cross && this.cross.e);this.crosshair && !1 !== (r(b) || !h) ? (h ? r(b) && (p = this.isXAxis ? b.plotX : this.len - b.plotY) : p = a && (this.horiz ? a.chartX - this.pos : this.len - a.chartY + this.pos), r(p) && (c = this.getPlotLinePath(b && (this.isXAxis ? b.x : B(b.stackY, b.y)), null, null, null, p) || null), r(c) ? (b = this.categories && !this.isRadial, d || (this.cross = d = this.chart.renderer.path().addClass("highcharts-crosshair highcharts-crosshair-" + (b ? "category " : "thin ") + e.className).attr({ zIndex: B(e.zIndex, 2) }).add(), d.attr({ stroke: e.color || (b ? m("#ccd6eb").setOpacity(.25).get() : "#cccccc"), "stroke-width": B(e.width, 1) }), e.dashStyle && d.attr({ dashstyle: e.dashStyle })), d.show().attr({ d: c }), b && !e.width && d.attr({ "stroke-width": this.transA }), this.cross.e = a) : this.hideCrosshair()) : this.hideCrosshair();
      }, hideCrosshair: function hideCrosshair() {
        this.cross && this.cross.hide();
      } });return a.Axis = L;
  }(M);(function (a) {
    var C = a.Axis,
        A = a.Date,
        F = a.dateFormat,
        E = a.defaultOptions,
        m = a.defined,
        f = a.each,
        l = a.extend,
        r = a.getMagnitude,
        u = a.getTZOffset,
        t = a.normalizeTickInterval,
        g = a.pick,
        d = a.timeUnits;C.prototype.getTimeTicks = function (a, b, e, v) {
      var k = [],
          n = {},
          D = E.global.useUTC,
          r,
          c = new A(b - Math.max(u(b), u(e))),
          G = A.hcMakeTime,
          q = a.unitRange,
          B = a.count,
          t,
          p;if (m(b)) {
        c[A.hcSetMilliseconds](q >= d.second ? 0 : B * Math.floor(c.getMilliseconds() / B));if (q >= d.second) c[A.hcSetSeconds](q >= d.minute ? 0 : B * Math.floor(c.getSeconds() / B));if (q >= d.minute) c[A.hcSetMinutes](q >= d.hour ? 0 : B * Math.floor(c[A.hcGetMinutes]() / B));if (q >= d.hour) c[A.hcSetHours](q >= d.day ? 0 : B * Math.floor(c[A.hcGetHours]() / B));if (q >= d.day) c[A.hcSetDate](q >= d.month ? 1 : B * Math.floor(c[A.hcGetDate]() / B));q >= d.month && (c[A.hcSetMonth](q >= d.year ? 0 : B * Math.floor(c[A.hcGetMonth]() / B)), r = c[A.hcGetFullYear]());if (q >= d.year) c[A.hcSetFullYear](r - r % B);if (q === d.week) c[A.hcSetDate](c[A.hcGetDate]() - c[A.hcGetDay]() + g(v, 1));r = c[A.hcGetFullYear]();v = c[A.hcGetMonth]();var z = c[A.hcGetDate](),
            I = c[A.hcGetHours]();if (A.hcTimezoneOffset || A.hcGetTimezoneOffset) p = (!D || !!A.hcGetTimezoneOffset) && (e - b > 4 * d.month || u(b) !== u(e)), c = c.getTime(), t = u(c), c = new A(c + t);D = c.getTime();for (b = 1; D < e;) {
          k.push(D), D = q === d.year ? G(r + b * B, 0) : q === d.month ? G(r, v + b * B) : !p || q !== d.day && q !== d.week ? p && q === d.hour ? G(r, v, z, I + b * B, 0, 0, t) - t : D + q * B : G(r, v, z + b * B * (q === d.day ? 1 : 7)), b++;
        }k.push(D);q <= d.hour && 1E4 > k.length && f(k, function (a) {
          0 === a % 18E5 && "000000000" === F("%H%M%S%L", a) && (n[a] = "day");
        });
      }k.info = l(a, { higherRanks: n, totalRange: q * B });return k;
    };C.prototype.normalizeTimeTickInterval = function (a, b) {
      var e = b || [["millisecond", [1, 2, 5, 10, 20, 25, 50, 100, 200, 500]], ["second", [1, 2, 5, 10, 15, 30]], ["minute", [1, 2, 5, 10, 15, 30]], ["hour", [1, 2, 3, 4, 6, 8, 12]], ["day", [1, 2]], ["week", [1, 2]], ["month", [1, 2, 3, 4, 6]], ["year", null]];b = e[e.length - 1];var k = d[b[0]],
          g = b[1],
          n;for (n = 0; n < e.length && !(b = e[n], k = d[b[0]], g = b[1], e[n + 1] && a <= (k * g[g.length - 1] + d[e[n + 1][0]]) / 2); n++) {}k === d.year && a < 5 * k && (g = [1, 2, 5]);a = t(a / k, g, "year" === b[0] ? Math.max(r(a / k), 1) : 1);return { unitRange: k, count: a, unitName: b[0] };
    };
  })(M);(function (a) {
    var C = a.Axis,
        A = a.getMagnitude,
        F = a.map,
        E = a.normalizeTickInterval,
        m = a.pick;C.prototype.getLogTickPositions = function (a, l, r, u) {
      var f = this.options,
          g = this.len,
          d = this.lin2log,
          k = this.log2lin,
          b = [];u || (this._minorAutoInterval = null);if (.5 <= a) a = Math.round(a), b = this.getLinearTickPositions(a, l, r);else if (.08 <= a) for (var g = Math.floor(l), e, v, y, n, D, f = .3 < a ? [1, 2, 4] : .15 < a ? [1, 2, 4, 6, 8] : [1, 2, 3, 4, 5, 6, 7, 8, 9]; g < r + 1 && !D; g++) {
        for (v = f.length, e = 0; e < v && !D; e++) {
          y = k(d(g) * f[e]), y > l && (!u || n <= r) && void 0 !== n && b.push(n), n > r && (D = !0), n = y;
        }
      } else l = d(l), r = d(r), a = f[u ? "minorTickInterval" : "tickInterval"], a = m("auto" === a ? null : a, this._minorAutoInterval, f.tickPixelInterval / (u ? 5 : 1) * (r - l) / ((u ? g / this.tickPositions.length : g) || 1)), a = E(a, null, A(a)), b = F(this.getLinearTickPositions(a, l, r), k), u || (this._minorAutoInterval = a / 5);u || (this.tickInterval = a);return b;
    };C.prototype.log2lin = function (a) {
      return Math.log(a) / Math.LN10;
    };C.prototype.lin2log = function (a) {
      return Math.pow(10, a);
    };
  })(M);(function (a, C) {
    var A = a.arrayMax,
        F = a.arrayMin,
        E = a.defined,
        m = a.destroyObjectProperties,
        f = a.each,
        l = a.erase,
        r = a.merge,
        u = a.pick;a.PlotLineOrBand = function (a, g) {
      this.axis = a;g && (this.options = g, this.id = g.id);
    };a.PlotLineOrBand.prototype = { render: function render() {
        var f = this,
            g = f.axis,
            d = g.horiz,
            k = f.options,
            b = k.label,
            e = f.label,
            v = k.to,
            l = k.from,
            n = k.value,
            D = E(l) && E(v),
            m = E(n),
            c = f.svgElem,
            G = !c,
            q = [],
            B = k.color,
            K = u(k.zIndex, 0),
            p = k.events,
            q = { "class": "highcharts-plot-" + (D ? "band " : "line ") + (k.className || "") },
            z = {},
            I = g.chart.renderer,
            L = D ? "bands" : "lines",
            h = g.log2lin;g.isLog && (l = h(l), v = h(v), n = h(n));m ? (q = { stroke: B, "stroke-width": k.width }, k.dashStyle && (q.dashstyle = k.dashStyle)) : D && (B && (q.fill = B), k.borderWidth && (q.stroke = k.borderColor, q["stroke-width"] = k.borderWidth));z.zIndex = K;L += "-" + K;(B = g.plotLinesAndBandsGroups[L]) || (g.plotLinesAndBandsGroups[L] = B = I.g("plot-" + L).attr(z).add());G && (f.svgElem = c = I.path().attr(q).add(B));if (m) q = g.getPlotLinePath(n, c.strokeWidth());else if (D) q = g.getPlotBandPath(l, v, k);else return;G && q && q.length ? (c.attr({ d: q }), p && a.objectEach(p, function (a, b) {
          c.on(b, function (a) {
            p[b].apply(f, [a]);
          });
        })) : c && (q ? (c.show(), c.animate({ d: q })) : (c.hide(), e && (f.label = e = e.destroy())));b && E(b.text) && q && q.length && 0 < g.width && 0 < g.height && !q.flat ? (b = r({ align: d && D && "center", x: d ? !D && 4 : 10, verticalAlign: !d && D && "middle", y: d ? D ? 16 : 10 : D ? 6 : -4, rotation: d && !D && 90 }, b), this.renderLabel(b, q, D, K)) : e && e.hide();return f;
      }, renderLabel: function renderLabel(a, g, d, k) {
        var b = this.label,
            e = this.axis.chart.renderer;b || (b = { align: a.textAlign || a.align, rotation: a.rotation, "class": "highcharts-plot-" + (d ? "band" : "line") + "-label " + (a.className || "") }, b.zIndex = k, this.label = b = e.text(a.text, 0, 0, a.useHTML).attr(b).add(), b.css(a.style));k = [g[1], g[4], d ? g[6] : g[1]];g = [g[2], g[5], d ? g[7] : g[2]];d = F(k);e = F(g);b.align(a, !1, { x: d, y: e,
          width: A(k) - d, height: A(g) - e });b.show();
      }, destroy: function destroy() {
        l(this.axis.plotLinesAndBands, this);delete this.axis;m(this);
      } };a.extend(C.prototype, { getPlotBandPath: function getPlotBandPath(a, g) {
        var d = this.getPlotLinePath(g, null, null, !0),
            k = this.getPlotLinePath(a, null, null, !0),
            b = this.horiz,
            e = 1;a = a < this.min && g < this.min || a > this.max && g > this.max;k && d ? (a && (k.flat = k.toString() === d.toString(), e = 0), k.push(b && d[4] === k[4] ? d[4] + e : d[4], b || d[5] !== k[5] ? d[5] : d[5] + e, b && d[1] === k[1] ? d[1] + e : d[1], b || d[2] !== k[2] ? d[2] : d[2] + e)) : k = null;return k;
      },
      addPlotBand: function addPlotBand(a) {
        return this.addPlotBandOrLine(a, "plotBands");
      }, addPlotLine: function addPlotLine(a) {
        return this.addPlotBandOrLine(a, "plotLines");
      }, addPlotBandOrLine: function addPlotBandOrLine(f, g) {
        var d = new a.PlotLineOrBand(this, f).render(),
            k = this.userOptions;d && (g && (k[g] = k[g] || [], k[g].push(f)), this.plotLinesAndBands.push(d));return d;
      }, removePlotBandOrLine: function removePlotBandOrLine(a) {
        for (var g = this.plotLinesAndBands, d = this.options, k = this.userOptions, b = g.length; b--;) {
          g[b].id === a && g[b].destroy();
        }f([d.plotLines || [], k.plotLines || [], d.plotBands || [], k.plotBands || []], function (e) {
          for (b = e.length; b--;) {
            e[b].id === a && l(e, e[b]);
          }
        });
      }, removePlotBand: function removePlotBand(a) {
        this.removePlotBandOrLine(a);
      }, removePlotLine: function removePlotLine(a) {
        this.removePlotBandOrLine(a);
      } });
  })(M, S);(function (a) {
    var C = a.dateFormat,
        A = a.each,
        F = a.extend,
        E = a.format,
        m = a.isNumber,
        f = a.map,
        l = a.merge,
        r = a.pick,
        u = a.splat,
        t = a.syncTimeout,
        g = a.timeUnits;a.Tooltip = function () {
      this.init.apply(this, arguments);
    };a.Tooltip.prototype = { init: function init(a, k) {
        this.chart = a;this.options = k;this.crosshairs = [];this.now = { x: 0, y: 0 };
        this.isHidden = !0;this.split = k.split && !a.inverted;this.shared = k.shared || this.split;
      }, cleanSplit: function cleanSplit(a) {
        A(this.chart.series, function (d) {
          var b = d && d.tt;b && (!b.isActive || a ? d.tt = b.destroy() : b.isActive = !1);
        });
      }, getLabel: function getLabel() {
        var a = this.chart.renderer,
            k = this.options;this.label || (this.split ? this.label = a.g("tooltip") : (this.label = a.label("", 0, 0, k.shape || "callout", null, null, k.useHTML, null, "tooltip").attr({ padding: k.padding, r: k.borderRadius }), this.label.attr({ fill: k.backgroundColor, "stroke-width": k.borderWidth }).css(k.style).shadow(k.shadow)), this.label.attr({ zIndex: 8 }).add());return this.label;
      }, update: function update(a) {
        this.destroy();l(!0, this.chart.options.tooltip.userOptions, a);this.init(this.chart, l(!0, this.options, a));
      }, destroy: function destroy() {
        this.label && (this.label = this.label.destroy());this.split && this.tt && (this.cleanSplit(this.chart, !0), this.tt = this.tt.destroy());clearTimeout(this.hideTimer);clearTimeout(this.tooltipTimeout);
      }, move: function move(a, k, b, e) {
        var d = this,
            g = d.now,
            n = !1 !== d.options.animation && !d.isHidden && (1 < Math.abs(a - g.x) || 1 < Math.abs(k - g.y)),
            f = d.followPointer || 1 < d.len;F(g, { x: n ? (2 * g.x + a) / 3 : a, y: n ? (g.y + k) / 2 : k, anchorX: f ? void 0 : n ? (2 * g.anchorX + b) / 3 : b, anchorY: f ? void 0 : n ? (g.anchorY + e) / 2 : e });d.getLabel().attr(g);n && (clearTimeout(this.tooltipTimeout), this.tooltipTimeout = setTimeout(function () {
          d && d.move(a, k, b, e);
        }, 32));
      }, hide: function hide(a) {
        var d = this;clearTimeout(this.hideTimer);a = r(a, this.options.hideDelay, 500);this.isHidden || (this.hideTimer = t(function () {
          d.getLabel()[a ? "fadeOut" : "hide"]();d.isHidden = !0;
        }, a));
      }, getAnchor: function getAnchor(a, k) {
        var b,
            e = this.chart,
            d = e.inverted,
            g = e.plotTop,
            n = e.plotLeft,
            l = 0,
            m = 0,
            c,
            r;a = u(a);b = a[0].tooltipPos;this.followPointer && k && (void 0 === k.chartX && (k = e.pointer.normalize(k)), b = [k.chartX - e.plotLeft, k.chartY - g]);b || (A(a, function (a) {
          c = a.series.yAxis;r = a.series.xAxis;l += a.plotX + (!d && r ? r.left - n : 0);m += (a.plotLow ? (a.plotLow + a.plotHigh) / 2 : a.plotY) + (!d && c ? c.top - g : 0);
        }), l /= a.length, m /= a.length, b = [d ? e.plotWidth - m : l, this.shared && !d && 1 < a.length && k ? k.chartY - g : d ? e.plotHeight - l : m]);return f(b, Math.round);
      }, getPosition: function getPosition(a, g, b) {
        var e = this.chart,
            d = this.distance,
            k = {},
            n = b.h || 0,
            f,
            l = ["y", e.chartHeight, g, b.plotY + e.plotTop, e.plotTop, e.plotTop + e.plotHeight],
            c = ["x", e.chartWidth, a, b.plotX + e.plotLeft, e.plotLeft, e.plotLeft + e.plotWidth],
            m = !this.followPointer && r(b.ttBelow, !e.inverted === !!b.negative),
            q = function q(a, b, c, e, p, _q) {
          var h = c < e - d,
              g = e + d + c < b,
              f = e - d - c;e += d;if (m && g) k[a] = e;else if (!m && h) k[a] = f;else if (h) k[a] = Math.min(_q - c, 0 > f - n ? f : f - n);else if (g) k[a] = Math.max(p, e + n + c > b ? e : e + n);else return !1;
        },
            B = function B(a, b, c, e) {
          var h;e < d || e > b - d ? h = !1 : k[a] = e < c / 2 ? 1 : e > b - c / 2 ? b - c - 2 : e - c / 2;return h;
        },
            t = function t(a) {
          var b = l;l = c;c = b;f = a;
        },
            p = function p() {
          !1 !== q.apply(0, l) ? !1 !== B.apply(0, c) || f || (t(!0), p()) : f ? k.x = k.y = 0 : (t(!0), p());
        };(e.inverted || 1 < this.len) && t();p();return k;
      }, defaultFormatter: function defaultFormatter(a) {
        var d = this.points || u(this),
            b;b = [a.tooltipFooterHeaderFormatter(d[0])];b = b.concat(a.bodyFormatter(d));b.push(a.tooltipFooterHeaderFormatter(d[0], !0));return b;
      }, refresh: function refresh(a, g) {
        var b,
            e = this.options,
            d,
            k = a,
            n,
            f = {},
            l = [];b = e.formatter || this.defaultFormatter;var f = this.shared,
            c;e.enabled && (clearTimeout(this.hideTimer), this.followPointer = u(k)[0].series.tooltipOptions.followPointer, n = this.getAnchor(k, g), g = n[0], d = n[1], !f || k.series && k.series.noSharedTooltip ? f = k.getLabelConfig() : (A(k, function (a) {
          a.setState("hover");l.push(a.getLabelConfig());
        }), f = { x: k[0].category, y: k[0].y }, f.points = l, k = k[0]), this.len = l.length, f = b.call(f, this), c = k.series, this.distance = r(c.tooltipOptions.distance, 16), !1 === f ? this.hide() : (b = this.getLabel(), this.isHidden && b.attr({ opacity: 1 }).show(), this.split ? this.renderSplit(f, a) : (e.style.width || b.css({ width: this.chart.spacingBox.width }), b.attr({ text: f && f.join ? f.join("") : f }), b.removeClass(/highcharts-color-[\d]+/g).addClass("highcharts-color-" + r(k.colorIndex, c.colorIndex)), b.attr({ stroke: e.borderColor || k.color || c.color || "#666666" }), this.updatePosition({ plotX: g, plotY: d, negative: k.negative, ttBelow: k.ttBelow, h: n[2] || 0 })), this.isHidden = !1));
      }, renderSplit: function renderSplit(d, k) {
        var b = this,
            e = [],
            g = this.chart,
            f = g.renderer,
            n = !0,
            l = this.options,
            m = 0,
            c = this.getLabel();A(d.slice(0, k.length + 1), function (a, d) {
          if (!1 !== a) {
            d = k[d - 1] || { isHeader: !0, plotX: k[0].plotX };var q = d.series || b,
                v = q.tt,
                p = d.series || {},
                z = "highcharts-color-" + r(d.colorIndex, p.colorIndex, "none");v || (q.tt = v = f.label(null, null, null, "callout").addClass("highcharts-tooltip-box " + z).attr({ padding: l.padding, r: l.borderRadius, fill: l.backgroundColor, stroke: l.borderColor || d.color || p.color || "#333333", "stroke-width": l.borderWidth }).add(c));v.isActive = !0;v.attr({ text: a });v.css(l.style).shadow(l.shadow);a = v.getBBox();p = a.width + v.strokeWidth();
            d.isHeader ? (m = a.height, p = Math.max(0, Math.min(d.plotX + g.plotLeft - p / 2, g.chartWidth - p))) : p = d.plotX + g.plotLeft - r(l.distance, 16) - p;0 > p && (n = !1);a = (d.series && d.series.yAxis && d.series.yAxis.pos) + (d.plotY || 0);a -= g.plotTop;e.push({ target: d.isHeader ? g.plotHeight + m : a, rank: d.isHeader ? 1 : 0, size: q.tt.getBBox().height + 1, point: d, x: p, tt: v });
          }
        });this.cleanSplit();a.distribute(e, g.plotHeight + m);A(e, function (a) {
          var b = a.point,
              c = b.series;a.tt.attr({ visibility: void 0 === a.pos ? "hidden" : "inherit", x: n || b.isHeader ? a.x : b.plotX + g.plotLeft + r(l.distance, 16), y: a.pos + g.plotTop, anchorX: b.isHeader ? b.plotX + g.plotLeft : b.plotX + c.xAxis.pos, anchorY: b.isHeader ? a.pos + g.plotTop - 15 : b.plotY + c.yAxis.pos });
        });
      }, updatePosition: function updatePosition(a) {
        var d = this.chart,
            b = this.getLabel(),
            b = (this.options.positioner || this.getPosition).call(this, b.width, b.height, a);this.move(Math.round(b.x), Math.round(b.y || 0), a.plotX + d.plotLeft, a.plotY + d.plotTop);
      }, getDateFormat: function getDateFormat(a, k, b, e) {
        var d = C("%m-%d %H:%M:%S.%L", k),
            f,
            n,
            l = { millisecond: 15, second: 12, minute: 9, hour: 6, day: 3 },
            m = "millisecond";for (n in g) {
          if (a === g.week && +C("%w", k) === b && "00:00:00.000" === d.substr(6)) {
            n = "week";break;
          }if (g[n] > a) {
            n = m;break;
          }if (l[n] && d.substr(l[n]) !== "01-01 00:00:00.000".substr(l[n])) break;"week" !== n && (m = n);
        }n && (f = e[n]);return f;
      }, getXDateFormat: function getXDateFormat(a, g, b) {
        g = g.dateTimeLabelFormats;var e = b && b.closestPointRange;return (e ? this.getDateFormat(e, a.x, b.options.startOfWeek, g) : g.day) || g.year;
      }, tooltipFooterHeaderFormatter: function tooltipFooterHeaderFormatter(a, g) {
        var b = g ? "footer" : "header";g = a.series;var e = g.tooltipOptions,
            d = e.xDateFormat,
            k = g.xAxis,
            n = k && "datetime" === k.options.type && m(a.key),
            b = e[b + "Format"];n && !d && (d = this.getXDateFormat(a, e, k));n && d && (b = b.replace("{point.key}", "{point.key:" + d + "}"));return E(b, { point: a, series: g });
      }, bodyFormatter: function bodyFormatter(a) {
        return f(a, function (a) {
          var b = a.series.tooltipOptions;return (b.pointFormatter || a.point.tooltipFormatter).call(a.point, b.pointFormat);
        });
      } };
  })(M);(function (a) {
    var C = a.addEvent,
        A = a.attr,
        F = a.charts,
        E = a.color,
        m = a.css,
        f = a.defined,
        l = a.each,
        r = a.extend,
        u = a.find,
        t = a.fireEvent,
        g = a.isObject,
        d = a.offset,
        k = a.pick,
        b = a.removeEvent,
        e = a.splat,
        v = a.Tooltip,
        y = a.win;a.Pointer = function (a, b) {
      this.init(a, b);
    };a.Pointer.prototype = { init: function init(a, b) {
        this.options = b;this.chart = a;this.runChartClick = b.chart.events && !!b.chart.events.click;this.pinchDown = [];this.lastValidTouch = {};v && (a.tooltip = new v(a, b.tooltip), this.followTouchMove = k(b.tooltip.followTouchMove, !0));this.setDOMEvents();
      }, zoomOption: function zoomOption(a) {
        var b = this.chart,
            e = b.options.chart,
            c = e.zoomType || "",
            b = b.inverted;/touch/.test(a.type) && (c = k(e.pinchType, c));
        this.zoomX = a = /x/.test(c);this.zoomY = c = /y/.test(c);this.zoomHor = a && !b || c && b;this.zoomVert = c && !b || a && b;this.hasZoom = a || c;
      }, normalize: function normalize(a, b) {
        var e, c;a = a || y.event;a.target || (a.target = a.srcElement);c = a.touches ? a.touches.length ? a.touches.item(0) : a.changedTouches[0] : a;b || (this.chartPosition = b = d(this.chart.container));void 0 === c.pageX ? (e = Math.max(a.x, a.clientX - b.left), b = a.y) : (e = c.pageX - b.left, b = c.pageY - b.top);return r(a, { chartX: Math.round(e), chartY: Math.round(b) });
      }, getCoordinates: function getCoordinates(a) {
        var b = { xAxis: [], yAxis: [] };l(this.chart.axes, function (e) {
          b[e.isXAxis ? "xAxis" : "yAxis"].push({ axis: e, value: e.toValue(a[e.horiz ? "chartX" : "chartY"]) });
        });return b;
      }, findNearestKDPoint: function findNearestKDPoint(a, b, e) {
        var c;l(a, function (a) {
          var d = !(a.noSharedTooltip && b) && 0 > a.options.findNearestPointBy.indexOf("y");a = a.searchPoint(e, d);if ((d = g(a, !0)) && !(d = !g(c, !0))) var d = c.distX - a.distX,
              n = c.dist - a.dist,
              k = (a.series.group && a.series.group.zIndex) - (c.series.group && c.series.group.zIndex),
              d = 0 < (0 !== d && b ? d : 0 !== n ? n : 0 !== k ? k : c.series.index > a.series.index ? -1 : 1);d && (c = a);
        });return c;
      }, getPointFromEvent: function getPointFromEvent(a) {
        a = a.target;for (var b; a && !b;) {
          b = a.point, a = a.parentNode;
        }return b;
      }, getChartCoordinatesFromPoint: function getChartCoordinatesFromPoint(a, b) {
        var e = a.series,
            c = e.xAxis,
            e = e.yAxis;if (c && e) return b ? { chartX: c.len + c.pos - a.clientX, chartY: e.len + e.pos - a.plotY } : { chartX: a.clientX + c.pos, chartY: a.plotY + e.pos };
      }, getHoverData: function getHoverData(b, e, d, c, f, q) {
        var n,
            v = [];c = !(!c || !b);var p = e && !e.stickyTracking ? [e] : a.grep(d, function (a) {
          return a.visible && !(!f && a.directTouch) && k(a.options.enableMouseTracking, !0) && a.stickyTracking;
        });e = (n = c ? b : this.findNearestKDPoint(p, f, q)) && n.series;n && (f && !e.noSharedTooltip ? (p = a.grep(d, function (a) {
          return a.visible && !(!f && a.directTouch) && k(a.options.enableMouseTracking, !0) && !a.noSharedTooltip;
        }), l(p, function (a) {
          a = u(a.points, function (a) {
            return a.x === n.x;
          });g(a) && !a.isNull && v.push(a);
        })) : v.push(n));return { hoverPoint: n, hoverSeries: e, hoverPoints: v };
      }, runPointActions: function runPointActions(b, e) {
        var d = this.chart,
            c = d.tooltip,
            g = c ? c.shared : !1,
            n = e || d.hoverPoint,
            f = n && n.series || d.hoverSeries,
            f = this.getHoverData(n, f, d.series, !!e || f && f.directTouch && this.isDirectTouch, g, b),
            v,
            n = f.hoverPoint;v = f.hoverPoints;e = (f = f.hoverSeries) && f.tooltipOptions.followPointer;g = g && f && !f.noSharedTooltip;if (n && (n !== d.hoverPoint || c && c.isHidden)) {
          l(d.hoverPoints || [], function (b) {
            -1 === a.inArray(b, v) && b.setState();
          });l(v || [], function (a) {
            a.setState("hover");
          });if (d.hoverSeries !== f) f.onMouseOver();d.hoverPoint && d.hoverPoint.firePointEvent("mouseOut");n.firePointEvent("mouseOver");d.hoverPoints = v;d.hoverPoint = n;c && c.refresh(g ? v : n, b);
        } else e && c && !c.isHidden && (n = c.getAnchor([{}], b), c.updatePosition({ plotX: n[0], plotY: n[1] }));this.unDocMouseMove || (this.unDocMouseMove = C(d.container.ownerDocument, "mousemove", function (b) {
          var c = F[a.hoverChartIndex];if (c) c.pointer.onDocumentMouseMove(b);
        }));l(d.axes, function (c) {
          var e = k(c.crosshair.snap, !0),
              p = e ? a.find(v, function (a) {
            return a.series[c.coll] === c;
          }) : void 0;p || !e ? c.drawCrosshair(b, p) : c.hideCrosshair();
        });
      }, reset: function reset(a, b) {
        var d = this.chart,
            c = d.hoverSeries,
            g = d.hoverPoint,
            n = d.hoverPoints,
            f = d.tooltip,
            k = f && f.shared ? n : g;a && k && l(e(k), function (b) {
          b.series.isCartesian && void 0 === b.plotX && (a = !1);
        });if (a) f && k && (f.refresh(k), g && (g.setState(g.state, !0), l(d.axes, function (a) {
          a.crosshair && a.drawCrosshair(null, g);
        })));else {
          if (g) g.onMouseOut();n && l(n, function (a) {
            a.setState();
          });if (c) c.onMouseOut();f && f.hide(b);this.unDocMouseMove && (this.unDocMouseMove = this.unDocMouseMove());l(d.axes, function (a) {
            a.hideCrosshair();
          });this.hoverX = d.hoverPoints = d.hoverPoint = null;
        }
      }, scaleGroups: function scaleGroups(a, b) {
        var e = this.chart,
            c;l(e.series, function (d) {
          c = a || d.getPlotBox();d.xAxis && d.xAxis.zoomEnabled && d.group && (d.group.attr(c), d.markerGroup && (d.markerGroup.attr(c), d.markerGroup.clip(b ? e.clipRect : null)), d.dataLabelsGroup && d.dataLabelsGroup.attr(c));
        });e.clipRect.attr(b || e.clipBox);
      }, dragStart: function dragStart(a) {
        var b = this.chart;b.mouseIsDown = a.type;b.cancelClick = !1;b.mouseDownX = this.mouseDownX = a.chartX;b.mouseDownY = this.mouseDownY = a.chartY;
      }, drag: function drag(a) {
        var b = this.chart,
            e = b.options.chart,
            c = a.chartX,
            d = a.chartY,
            g = this.zoomHor,
            n = this.zoomVert,
            f = b.plotLeft,
            p = b.plotTop,
            k = b.plotWidth,
            v = b.plotHeight,
            l,
            h = this.selectionMarker,
            w = this.mouseDownX,
            m = this.mouseDownY,
            r = e.panKey && a[e.panKey + "Key"];h && h.touch || (c < f ? c = f : c > f + k && (c = f + k), d < p ? d = p : d > p + v && (d = p + v), this.hasDragged = Math.sqrt(Math.pow(w - c, 2) + Math.pow(m - d, 2)), 10 < this.hasDragged && (l = b.isInsidePlot(w - f, m - p), b.hasCartesianSeries && (this.zoomX || this.zoomY) && l && !r && !h && (this.selectionMarker = h = b.renderer.rect(f, p, g ? 1 : k, n ? 1 : v, 0).attr({ fill: e.selectionMarkerFill || E("#335cad").setOpacity(.25).get(), "class": "highcharts-selection-marker",
          zIndex: 7 }).add()), h && g && (c -= w, h.attr({ width: Math.abs(c), x: (0 < c ? 0 : c) + w })), h && n && (c = d - m, h.attr({ height: Math.abs(c), y: (0 < c ? 0 : c) + m })), l && !h && e.panning && b.pan(a, e.panning)));
      }, drop: function drop(a) {
        var b = this,
            e = this.chart,
            c = this.hasPinched;if (this.selectionMarker) {
          var d = { originalEvent: a, xAxis: [], yAxis: [] },
              g = this.selectionMarker,
              n = g.attr ? g.attr("x") : g.x,
              k = g.attr ? g.attr("y") : g.y,
              p = g.attr ? g.attr("width") : g.width,
              v = g.attr ? g.attr("height") : g.height,
              I;if (this.hasDragged || c) l(e.axes, function (e) {
            if (e.zoomEnabled && f(e.min) && (c || b[{ xAxis: "zoomX", yAxis: "zoomY" }[e.coll]])) {
              var h = e.horiz,
                  g = "touchend" === a.type ? e.minPixelPadding : 0,
                  q = e.toValue((h ? n : k) + g),
                  h = e.toValue((h ? n + p : k + v) - g);d[e.coll].push({ axis: e, min: Math.min(q, h), max: Math.max(q, h) });I = !0;
            }
          }), I && t(e, "selection", d, function (a) {
            e.zoom(r(a, c ? { animation: !1 } : null));
          });this.selectionMarker = this.selectionMarker.destroy();c && this.scaleGroups();
        }e && (m(e.container, { cursor: e._cursor }), e.cancelClick = 10 < this.hasDragged, e.mouseIsDown = this.hasDragged = this.hasPinched = !1, this.pinchDown = []);
      }, onContainerMouseDown: function onContainerMouseDown(a) {
        a = this.normalize(a);this.zoomOption(a);a.preventDefault && a.preventDefault();this.dragStart(a);
      }, onDocumentMouseUp: function onDocumentMouseUp(b) {
        F[a.hoverChartIndex] && F[a.hoverChartIndex].pointer.drop(b);
      }, onDocumentMouseMove: function onDocumentMouseMove(a) {
        var b = this.chart,
            e = this.chartPosition;a = this.normalize(a, e);!e || this.inClass(a.target, "highcharts-tracker") || b.isInsidePlot(a.chartX - b.plotLeft, a.chartY - b.plotTop) || this.reset();
      }, onContainerMouseLeave: function onContainerMouseLeave(b) {
        var e = F[a.hoverChartIndex];e && (b.relatedTarget || b.toElement) && (e.pointer.reset(), e.pointer.chartPosition = null);
      }, onContainerMouseMove: function onContainerMouseMove(b) {
        var e = this.chart;f(a.hoverChartIndex) && F[a.hoverChartIndex] && F[a.hoverChartIndex].mouseIsDown || (a.hoverChartIndex = e.index);b = this.normalize(b);b.returnValue = !1;"mousedown" === e.mouseIsDown && this.drag(b);!this.inClass(b.target, "highcharts-tracker") && !e.isInsidePlot(b.chartX - e.plotLeft, b.chartY - e.plotTop) || e.openMenu || this.runPointActions(b);
      }, inClass: function inClass(a, b) {
        for (var e; a;) {
          if (e = A(a, "class")) {
            if (-1 !== e.indexOf(b)) return !0;if (-1 !== e.indexOf("highcharts-container")) return !1;
          }a = a.parentNode;
        }
      }, onTrackerMouseOut: function onTrackerMouseOut(a) {
        var b = this.chart.hoverSeries;a = a.relatedTarget || a.toElement;this.isDirectTouch = !1;if (!(!b || !a || b.stickyTracking || this.inClass(a, "highcharts-tooltip") || this.inClass(a, "highcharts-series-" + b.index) && this.inClass(a, "highcharts-tracker"))) b.onMouseOut();
      }, onContainerClick: function onContainerClick(a) {
        var b = this.chart,
            e = b.hoverPoint,
            c = b.plotLeft,
            d = b.plotTop;a = this.normalize(a);b.cancelClick || (e && this.inClass(a.target, "highcharts-tracker") ? (t(e.series, "click", r(a, { point: e })), b.hoverPoint && e.firePointEvent("click", a)) : (r(a, this.getCoordinates(a)), b.isInsidePlot(a.chartX - c, a.chartY - d) && t(b, "click", a)));
      }, setDOMEvents: function setDOMEvents() {
        var b = this,
            e = b.chart.container,
            d = e.ownerDocument;e.onmousedown = function (a) {
          b.onContainerMouseDown(a);
        };e.onmousemove = function (a) {
          b.onContainerMouseMove(a);
        };e.onclick = function (a) {
          b.onContainerClick(a);
        };C(e, "mouseleave", b.onContainerMouseLeave);1 === a.chartCount && C(d, "mouseup", b.onDocumentMouseUp);
        a.hasTouch && (e.ontouchstart = function (a) {
          b.onContainerTouchStart(a);
        }, e.ontouchmove = function (a) {
          b.onContainerTouchMove(a);
        }, 1 === a.chartCount && C(d, "touchend", b.onDocumentTouchEnd));
      }, destroy: function destroy() {
        var e = this,
            d = this.chart.container.ownerDocument;e.unDocMouseMove && e.unDocMouseMove();b(e.chart.container, "mouseleave", e.onContainerMouseLeave);a.chartCount || (b(d, "mouseup", e.onDocumentMouseUp), a.hasTouch && b(d, "touchend", e.onDocumentTouchEnd));clearInterval(e.tooltipTimeout);a.objectEach(e, function (a, b) {
          e[b] = null;
        });
      } };
  })(M);(function (a) {
    var C = a.charts,
        A = a.each,
        F = a.extend,
        E = a.map,
        m = a.noop,
        f = a.pick;F(a.Pointer.prototype, { pinchTranslate: function pinchTranslate(a, f, m, t, g, d) {
        this.zoomHor && this.pinchTranslateDirection(!0, a, f, m, t, g, d);this.zoomVert && this.pinchTranslateDirection(!1, a, f, m, t, g, d);
      }, pinchTranslateDirection: function pinchTranslateDirection(a, f, m, t, g, d, k, b) {
        var e = this.chart,
            v = a ? "x" : "y",
            l = a ? "X" : "Y",
            n = "chart" + l,
            r = a ? "width" : "height",
            u = e["plot" + (a ? "Left" : "Top")],
            c,
            G,
            q = b || 1,
            B = e.inverted,
            K = e.bounds[a ? "h" : "v"],
            p = 1 === f.length,
            z = f[0][n],
            I = m[0][n],
            L = !p && f[1][n],
            h = !p && m[1][n],
            w;m = function m() {
          !p && 20 < Math.abs(z - L) && (q = b || Math.abs(I - h) / Math.abs(z - L));G = (u - I) / q + z;c = e["plot" + (a ? "Width" : "Height")] / q;
        };m();f = G;f < K.min ? (f = K.min, w = !0) : f + c > K.max && (f = K.max - c, w = !0);w ? (I -= .8 * (I - k[v][0]), p || (h -= .8 * (h - k[v][1])), m()) : k[v] = [I, h];B || (d[v] = G - u, d[r] = c);d = B ? 1 / q : q;g[r] = c;g[v] = f;t[B ? a ? "scaleY" : "scaleX" : "scale" + l] = q;t["translate" + l] = d * u + (I - d * z);
      }, pinch: function pinch(a) {
        var l = this,
            u = l.chart,
            t = l.pinchDown,
            g = a.touches,
            d = g.length,
            k = l.lastValidTouch,
            b = l.hasZoom,
            e = l.selectionMarker,
            v = {},
            y = 1 === d && (l.inClass(a.target, "highcharts-tracker") && u.runTrackerClick || l.runChartClick),
            n = {};1 < d && (l.initiated = !0);b && l.initiated && !y && a.preventDefault();E(g, function (a) {
          return l.normalize(a);
        });"touchstart" === a.type ? (A(g, function (a, b) {
          t[b] = { chartX: a.chartX, chartY: a.chartY };
        }), k.x = [t[0].chartX, t[1] && t[1].chartX], k.y = [t[0].chartY, t[1] && t[1].chartY], A(u.axes, function (a) {
          if (a.zoomEnabled) {
            var b = u.bounds[a.horiz ? "h" : "v"],
                e = a.minPixelPadding,
                d = a.toPixels(f(a.options.min, a.dataMin)),
                g = a.toPixels(f(a.options.max, a.dataMax)),
                k = Math.max(d, g);b.min = Math.min(a.pos, Math.min(d, g) - e);b.max = Math.max(a.pos + a.len, k + e);
          }
        }), l.res = !0) : l.followTouchMove && 1 === d ? this.runPointActions(l.normalize(a)) : t.length && (e || (l.selectionMarker = e = F({ destroy: m, touch: !0 }, u.plotBox)), l.pinchTranslate(t, g, v, e, n, k), l.hasPinched = b, l.scaleGroups(v, n), l.res && (l.res = !1, this.reset(!1, 0)));
      }, touch: function touch(l, m) {
        var r = this.chart,
            t,
            g;if (r.index !== a.hoverChartIndex) this.onContainerMouseLeave({ relatedTarget: !0 });a.hoverChartIndex = r.index;1 === l.touches.length ? (l = this.normalize(l), (g = r.isInsidePlot(l.chartX - r.plotLeft, l.chartY - r.plotTop)) && !r.openMenu ? (m && this.runPointActions(l), "touchmove" === l.type && (m = this.pinchDown, t = m[0] ? 4 <= Math.sqrt(Math.pow(m[0].chartX - l.chartX, 2) + Math.pow(m[0].chartY - l.chartY, 2)) : !1), f(t, !0) && this.pinch(l)) : m && this.reset()) : 2 === l.touches.length && this.pinch(l);
      }, onContainerTouchStart: function onContainerTouchStart(a) {
        this.zoomOption(a);this.touch(a, !0);
      }, onContainerTouchMove: function onContainerTouchMove(a) {
        this.touch(a);
      }, onDocumentTouchEnd: function onDocumentTouchEnd(f) {
        C[a.hoverChartIndex] && C[a.hoverChartIndex].pointer.drop(f);
      } });
  })(M);(function (a) {
    var C = a.addEvent,
        A = a.charts,
        F = a.css,
        E = a.doc,
        m = a.extend,
        f = a.noop,
        l = a.Pointer,
        r = a.removeEvent,
        u = a.win,
        t = a.wrap;if (!a.hasTouch && (u.PointerEvent || u.MSPointerEvent)) {
      var g = {},
          d = !!u.PointerEvent,
          k = function k() {
        var b = [];b.item = function (a) {
          return this[a];
        };a.objectEach(g, function (a) {
          b.push({ pageX: a.pageX, pageY: a.pageY, target: a.target });
        });return b;
      },
          b = function b(_b, d, g, n) {
        "touch" !== _b.pointerType && _b.pointerType !== _b.MSPOINTER_TYPE_TOUCH || !A[a.hoverChartIndex] || (n(_b), n = A[a.hoverChartIndex].pointer, n[d]({ type: g, target: _b.currentTarget, preventDefault: f, touches: k() }));
      };m(l.prototype, { onContainerPointerDown: function onContainerPointerDown(a) {
          b(a, "onContainerTouchStart", "touchstart", function (a) {
            g[a.pointerId] = { pageX: a.pageX, pageY: a.pageY, target: a.currentTarget };
          });
        }, onContainerPointerMove: function onContainerPointerMove(a) {
          b(a, "onContainerTouchMove", "touchmove", function (a) {
            g[a.pointerId] = { pageX: a.pageX, pageY: a.pageY };g[a.pointerId].target || (g[a.pointerId].target = a.currentTarget);
          });
        }, onDocumentPointerUp: function onDocumentPointerUp(a) {
          b(a, "onDocumentTouchEnd", "touchend", function (a) {
            delete g[a.pointerId];
          });
        }, batchMSEvents: function batchMSEvents(a) {
          a(this.chart.container, d ? "pointerdown" : "MSPointerDown", this.onContainerPointerDown);a(this.chart.container, d ? "pointermove" : "MSPointerMove", this.onContainerPointerMove);a(E, d ? "pointerup" : "MSPointerUp", this.onDocumentPointerUp);
        } });t(l.prototype, "init", function (a, b, d) {
        a.call(this, b, d);this.hasZoom && F(b.container, { "-ms-touch-action": "none", "touch-action": "none" });
      });t(l.prototype, "setDOMEvents", function (a) {
        a.apply(this);
        (this.hasZoom || this.followTouchMove) && this.batchMSEvents(C);
      });t(l.prototype, "destroy", function (a) {
        this.batchMSEvents(r);a.call(this);
      });
    }
  })(M);(function (a) {
    var C = a.addEvent,
        A = a.css,
        F = a.discardElement,
        E = a.defined,
        m = a.each,
        f = a.isFirefox,
        l = a.marginNames,
        r = a.merge,
        u = a.pick,
        t = a.setAnimation,
        g = a.stableSort,
        d = a.win,
        k = a.wrap;a.Legend = function (a, e) {
      this.init(a, e);
    };a.Legend.prototype = { init: function init(a, e) {
        this.chart = a;this.setOptions(e);e.enabled && (this.render(), C(this.chart, "endResize", function () {
          this.legend.positionCheckboxes();
        }));
      },
      setOptions: function setOptions(a) {
        var b = u(a.padding, 8);this.options = a;this.itemStyle = a.itemStyle;this.itemHiddenStyle = r(this.itemStyle, a.itemHiddenStyle);this.itemMarginTop = a.itemMarginTop || 0;this.padding = b;this.initialItemY = b - 5;this.itemHeight = this.maxItemWidth = 0;this.symbolWidth = u(a.symbolWidth, 16);this.pages = [];
      }, update: function update(a, e) {
        var b = this.chart;this.setOptions(r(!0, this.options, a));this.destroy();b.isDirtyLegend = b.isDirtyBox = !0;u(e, !0) && b.redraw();
      }, colorizeItem: function colorizeItem(a, e) {
        a.legendGroup[e ? "removeClass" : "addClass"]("highcharts-legend-item-hidden");var b = this.options,
            d = a.legendItem,
            g = a.legendLine,
            f = a.legendSymbol,
            k = this.itemHiddenStyle.color,
            b = e ? b.itemStyle.color : k,
            c = e ? a.color || k : k,
            l = a.options && a.options.marker,
            q = { fill: c };d && d.css({ fill: b, color: b });g && g.attr({ stroke: c });f && (l && f.isMarker && (q = a.pointAttribs(), e || (q.stroke = q.fill = k)), f.attr(q));
      }, positionItem: function positionItem(a) {
        var b = this.options,
            d = b.symbolPadding,
            b = !b.rtl,
            g = a._legendItemPos,
            f = g[0],
            g = g[1],
            k = a.checkbox;(a = a.legendGroup) && a.element && a.translate(b ? f : this.legendWidth - f - 2 * d - 4, g);k && (k.x = f, k.y = g);
      }, destroyItem: function destroyItem(a) {
        var b = a.checkbox;m(["legendItem", "legendLine", "legendSymbol", "legendGroup"], function (b) {
          a[b] && (a[b] = a[b].destroy());
        });b && F(a.checkbox);
      }, destroy: function destroy() {
        function a(a) {
          this[a] && (this[a] = this[a].destroy());
        }m(this.getAllItems(), function (b) {
          m(["legendItem", "legendGroup"], a, b);
        });m("clipRect up down pager nav box title group".split(" "), a, this);this.display = null;
      }, positionCheckboxes: function positionCheckboxes(a) {
        var b = this.group && this.group.alignAttr,
            d,
            g = this.clipHeight || this.legendHeight,
            f = this.titleHeight;b && (d = b.translateY, m(this.allItems, function (e) {
          var k = e.checkbox,
              c;k && (c = d + f + k.y + (a || 0) + 3, A(k, { left: b.translateX + e.checkboxOffset + k.x - 20 + "px", top: c + "px", display: c > d - 6 && c < d + g - 6 ? "" : "none" }));
        }));
      }, renderTitle: function renderTitle() {
        var a = this.options,
            e = this.padding,
            d = a.title,
            g = 0;d.text && (this.title || (this.title = this.chart.renderer.label(d.text, e - 3, e - 4, null, null, null, a.useHTML, null, "legend-title").attr({ zIndex: 1 }).css(d.style).add(this.group)), a = this.title.getBBox(), g = a.height, this.offsetWidth = a.width, this.contentGroup.attr({ translateY: g }));this.titleHeight = g;
      }, setText: function setText(b) {
        var e = this.options;b.legendItem.attr({ text: e.labelFormat ? a.format(e.labelFormat, b) : e.labelFormatter.call(b) });
      }, renderItem: function renderItem(a) {
        var b = this.chart,
            d = b.renderer,
            g = this.options,
            f = "horizontal" === g.layout,
            k = this.symbolWidth,
            l = g.symbolPadding,
            c = this.itemStyle,
            m = this.itemHiddenStyle,
            q = this.padding,
            B = f ? u(g.itemDistance, 20) : 0,
            t = !g.rtl,
            p = g.width,
            z = g.itemMarginBottom || 0,
            I = this.itemMarginTop,
            L = a.legendItem,
            h = !a.series,
            w = !h && a.series.drawLegendSymbol ? a.series : a,
            P = w.options,
            H = this.createCheckboxForItem && P && P.showCheckbox,
            P = k + l + B + (H ? 20 : 0),
            O = g.useHTML,
            A = a.options.className;L || (a.legendGroup = d.g("legend-item").addClass("highcharts-" + w.type + "-series highcharts-color-" + a.colorIndex + (A ? " " + A : "") + (h ? " highcharts-series-" + a.index : "")).attr({ zIndex: 1 }).add(this.scrollGroup), a.legendItem = L = d.text("", t ? k + l : -l, this.baseline || 0, O).css(r(a.visible ? c : m)).attr({ align: t ? "left" : "right", zIndex: 2 }).add(a.legendGroup), this.baseline || (k = c.fontSize, this.fontMetrics = d.fontMetrics(k, L), this.baseline = this.fontMetrics.f + 3 + I, L.attr("y", this.baseline)), this.symbolHeight = g.symbolHeight || this.fontMetrics.f, w.drawLegendSymbol(this, a), this.setItemEvents && this.setItemEvents(a, L, O), H && this.createCheckboxForItem(a));this.colorizeItem(a, a.visible);c.width || L.css({ width: (g.itemWidth || g.width || b.spacingBox.width) - P });this.setText(a);d = L.getBBox();c = a.checkboxOffset = g.itemWidth || a.legendItemWidth || d.width + P;this.itemHeight = d = Math.round(a.legendItemHeight || d.height || this.symbolHeight);f && this.itemX - q + c > (p || b.spacingBox.width - 2 * q - g.x) && (this.itemX = q, this.itemY += I + this.lastLineHeight + z, this.lastLineHeight = 0);this.maxItemWidth = Math.max(this.maxItemWidth, c);this.lastItemY = I + this.itemY + z;this.lastLineHeight = Math.max(d, this.lastLineHeight);a._legendItemPos = [this.itemX, this.itemY];f ? this.itemX += c : (this.itemY += I + d + z, this.lastLineHeight = d);this.offsetWidth = p || Math.max((f ? this.itemX - q - (a.checkbox ? 0 : B) : c) + q, this.offsetWidth);
      }, getAllItems: function getAllItems() {
        var a = [];m(this.chart.series, function (b) {
          var e = b && b.options;b && u(e.showInLegend, E(e.linkedTo) ? !1 : void 0, !0) && (a = a.concat(b.legendItems || ("point" === e.legendType ? b.data : b)));
        });return a;
      }, adjustMargins: function adjustMargins(a, e) {
        var b = this.chart,
            d = this.options,
            g = d.align.charAt(0) + d.verticalAlign.charAt(0) + d.layout.charAt(0);d.floating || m([/(lth|ct|rth)/, /(rtv|rm|rbv)/, /(rbh|cb|lbh)/, /(lbv|lm|ltv)/], function (f, k) {
          f.test(g) && !E(a[k]) && (b[l[k]] = Math.max(b[l[k]], b.legend[(k + 1) % 2 ? "legendHeight" : "legendWidth"] + [1, -1, -1, 1][k] * d[k % 2 ? "x" : "y"] + u(d.margin, 12) + e[k]));
        });
      }, render: function render() {
        var a = this,
            e = a.chart,
            d = e.renderer,
            f = a.group,
            k,
            l,
            t,
            c,
            u = a.box,
            q = a.options,
            B = a.padding;a.itemX = B;a.itemY = a.initialItemY;a.offsetWidth = 0;a.lastItemY = 0;f || (a.group = f = d.g("legend").attr({ zIndex: 7 }).add(), a.contentGroup = d.g().attr({ zIndex: 1 }).add(f), a.scrollGroup = d.g().add(a.contentGroup));a.renderTitle();k = a.getAllItems();g(k, function (a, b) {
          return (a.options && a.options.legendIndex || 0) - (b.options && b.options.legendIndex || 0);
        });q.reversed && k.reverse();a.allItems = k;a.display = l = !!k.length;a.lastLineHeight = 0;m(k, function (b) {
          a.renderItem(b);
        });t = (q.width || a.offsetWidth) + B;c = a.lastItemY + a.lastLineHeight + a.titleHeight;c = a.handleOverflow(c);c += B;u || (a.box = u = d.rect().addClass("highcharts-legend-box").attr({ r: q.borderRadius }).add(f), u.isNew = !0);u.attr({ stroke: q.borderColor, "stroke-width": q.borderWidth || 0, fill: q.backgroundColor || "none" }).shadow(q.shadow);0 < t && 0 < c && (u[u.isNew ? "attr" : "animate"](u.crisp({ x: 0, y: 0, width: t, height: c }, u.strokeWidth())), u.isNew = !1);u[l ? "show" : "hide"]();a.legendWidth = t;a.legendHeight = c;m(k, function (b) {
          a.positionItem(b);
        });l && f.align(r(q, { width: t, height: c }), !0, "spacingBox");e.isResizing || this.positionCheckboxes();
      }, handleOverflow: function handleOverflow(a) {
        var b = this,
            d = this.chart,
            g = d.renderer,
            f = this.options,
            k = f.y,
            l = this.padding,
            d = d.spacingBox.height + ("top" === f.verticalAlign ? -k : k) - l,
            k = f.maxHeight,
            c,
            r = this.clipRect,
            q = f.navigation,
            B = u(q.animation, !0),
            t = q.arrowSize || 12,
            p = this.nav,
            z = this.pages,
            I,
            L = this.allItems,
            h = function h(a) {
          "number" === typeof a ? r.attr({ height: a }) : r && (b.clipRect = r.destroy(), b.contentGroup.clip());b.contentGroup.div && (b.contentGroup.div.style.clip = a ? "rect(" + l + "px,9999px," + (l + a) + "px,0)" : "auto");
        };"horizontal" !== f.layout || "middle" === f.verticalAlign || f.floating || (d /= 2);k && (d = Math.min(d, k));z.length = 0;a > d && !1 !== q.enabled ? (this.clipHeight = c = Math.max(d - 20 - this.titleHeight - l, 0), this.currentPage = u(this.currentPage, 1), this.fullHeight = a, m(L, function (a, b) {
          var e = a._legendItemPos[1];a = Math.round(a.legendItem.getBBox().height);var d = z.length;if (!d || e - z[d - 1] > c && (I || e) !== z[d - 1]) z.push(I || e), d++;b === L.length - 1 && e + a - z[d - 1] > c && z.push(e);e !== I && (I = e);
        }), r || (r = b.clipRect = g.clipRect(0, l, 9999, 0), b.contentGroup.clip(r)), h(c), p || (this.nav = p = g.g().attr({ zIndex: 1 }).add(this.group), this.up = g.symbol("triangle", 0, 0, t, t).on("click", function () {
          b.scroll(-1, B);
        }).add(p), this.pager = g.text("", 15, 10).addClass("highcharts-legend-navigation").css(q.style).add(p), this.down = g.symbol("triangle-down", 0, 0, t, t).on("click", function () {
          b.scroll(1, B);
        }).add(p)), b.scroll(0), a = d) : p && (h(), this.nav = p.destroy(), this.scrollGroup.attr({ translateY: 1 }), this.clipHeight = 0);return a;
      }, scroll: function scroll(a, e) {
        var b = this.pages,
            d = b.length;a = this.currentPage + a;var g = this.clipHeight,
            f = this.options.navigation,
            k = this.pager,
            c = this.padding;a > d && (a = d);0 < a && (void 0 !== e && t(e, this.chart), this.nav.attr({ translateX: c, translateY: g + this.padding + 7 + this.titleHeight, visibility: "visible" }), this.up.attr({ "class": 1 === a ? "highcharts-legend-nav-inactive" : "highcharts-legend-nav-active" }), k.attr({ text: a + "/" + d }), this.down.attr({ x: 18 + this.pager.getBBox().width, "class": a === d ? "highcharts-legend-nav-inactive" : "highcharts-legend-nav-active" }), this.up.attr({ fill: 1 === a ? f.inactiveColor : f.activeColor }).css({ cursor: 1 === a ? "default" : "pointer" }), this.down.attr({ fill: a === d ? f.inactiveColor : f.activeColor }).css({ cursor: a === d ? "default" : "pointer" }), e = -b[a - 1] + this.initialItemY, this.scrollGroup.animate({ translateY: e }), this.currentPage = a, this.positionCheckboxes(e));
      } };a.LegendSymbolMixin = { drawRectangle: function drawRectangle(a, e) {
        var b = a.symbolHeight,
            d = a.options.squareSymbol;e.legendSymbol = this.chart.renderer.rect(d ? (a.symbolWidth - b) / 2 : 0, a.baseline - b + 1, d ? b : a.symbolWidth, b, u(a.options.symbolRadius, b / 2)).addClass("highcharts-point").attr({ zIndex: 3 }).add(e.legendGroup);
      }, drawLineMarker: function drawLineMarker(a) {
        var b = this.options,
            d = b.marker,
            g = a.symbolWidth,
            f = a.symbolHeight,
            k = f / 2,
            l = this.chart.renderer,
            c = this.legendGroup;a = a.baseline - Math.round(.3 * a.fontMetrics.b);var m;m = { "stroke-width": b.lineWidth || 0 };b.dashStyle && (m.dashstyle = b.dashStyle);this.legendLine = l.path(["M", 0, a, "L", g, a]).addClass("highcharts-graph").attr(m).add(c);d && !1 !== d.enabled && (b = Math.min(u(d.radius, k), k), 0 === this.symbol.indexOf("url") && (d = r(d, { width: f, height: f }), b = 0), this.legendSymbol = d = l.symbol(this.symbol, g / 2 - b, a - b, 2 * b, 2 * b, d).addClass("highcharts-point").add(c), d.isMarker = !0);
      } };(/Trident\/7\.0/.test(d.navigator.userAgent) || f) && k(a.Legend.prototype, "positionItem", function (a, e) {
      var b = this,
          d = function d() {
        e._legendItemPos && a.call(b, e);
      };d();setTimeout(d);
    });
  })(M);(function (a) {
    var C = a.addEvent,
        A = a.animate,
        F = a.animObject,
        E = a.attr,
        m = a.doc,
        f = a.Axis,
        l = a.createElement,
        r = a.defaultOptions,
        u = a.discardElement,
        t = a.charts,
        g = a.css,
        d = a.defined,
        k = a.each,
        b = a.extend,
        e = a.find,
        v = a.fireEvent,
        y = a.getStyle,
        n = a.grep,
        D = a.isNumber,
        J = a.isObject,
        c = a.isString,
        G = a.Legend,
        q = a.marginNames,
        B = a.merge,
        K = a.objectEach,
        p = a.Pointer,
        z = a.pick,
        I = a.pInt,
        L = a.removeEvent,
        h = a.seriesTypes,
        w = a.splat,
        P = a.svg,
        H = a.syncTimeout,
        O = a.win,
        Q = a.Renderer,
        R = a.Chart = function () {
      this.getArgs.apply(this, arguments);
    };a.chart = function (a, b, c) {
      return new R(a, b, c);
    };b(R.prototype, { callbacks: [], getArgs: function getArgs() {
        var a = [].slice.call(arguments);
        if (c(a[0]) || a[0].nodeName) this.renderTo = a.shift();this.init(a[0], a[1]);
      }, init: function init(b, c) {
        var e,
            d,
            h = b.series,
            p = b.plotOptions || {};b.series = null;e = B(r, b);for (d in e.plotOptions) {
          e.plotOptions[d].tooltip = p[d] && B(p[d].tooltip) || void 0;
        }e.tooltip.userOptions = b.chart && b.chart.forExport && b.tooltip.userOptions || b.tooltip;e.series = b.series = h;this.userOptions = b;b = e.chart;d = b.events;this.margin = [];this.spacing = [];this.bounds = { h: {}, v: {} };this.callback = c;this.isResizing = 0;this.options = e;this.axes = [];this.series = [];this.hasCartesianSeries = b.showAxes;var g = this;g.index = t.length;t.push(g);a.chartCount++;d && K(d, function (a, b) {
          C(g, b, a);
        });g.xAxis = [];g.yAxis = [];g.pointCount = g.colorCounter = g.symbolCounter = 0;g.firstRender();
      }, initSeries: function initSeries(b) {
        var c = this.options.chart;(c = h[b.type || c.type || c.defaultSeriesType]) || a.error(17, !0);c = new c();c.init(this, b);return c;
      }, orderSeries: function orderSeries(a) {
        var b = this.series;for (a = a || 0; a < b.length; a++) {
          b[a] && (b[a].index = a, b[a].name = b[a].name || "Series " + (b[a].index + 1));
        }
      }, isInsidePlot: function isInsidePlot(a, b, c) {
        var e = c ? b : a;a = c ? a : b;return 0 <= e && e <= this.plotWidth && 0 <= a && a <= this.plotHeight;
      }, redraw: function redraw(c) {
        var e = this.axes,
            d = this.series,
            h = this.pointer,
            p = this.legend,
            g = this.isDirtyLegend,
            f,
            q,
            l = this.hasCartesianSeries,
            n = this.isDirtyBox,
            z,
            m = this.renderer,
            x = m.isHidden(),
            w = [];this.setResponsive && this.setResponsive(!1);a.setAnimation(c, this);x && this.temporaryDisplay();this.layOutTitles();for (c = d.length; c--;) {
          if (z = d[c], z.options.stacking && (f = !0, z.isDirty)) {
            q = !0;break;
          }
        }if (q) for (c = d.length; c--;) {
          z = d[c], z.options.stacking && (z.isDirty = !0);
        }k(d, function (a) {
          a.isDirty && "point" === a.options.legendType && (a.updateTotals && a.updateTotals(), g = !0);a.isDirtyData && v(a, "updatedData");
        });g && p.options.enabled && (p.render(), this.isDirtyLegend = !1);f && this.getStacks();l && k(e, function (a) {
          a.updateNames();a.setScale();
        });this.getMargins();l && (k(e, function (a) {
          a.isDirty && (n = !0);
        }), k(e, function (a) {
          var c = a.min + "," + a.max;a.extKey !== c && (a.extKey = c, w.push(function () {
            v(a, "afterSetExtremes", b(a.eventArgs, a.getExtremes()));delete a.eventArgs;
          }));(n || f) && a.redraw();
        }));n && this.drawChartBox();v(this, "predraw");k(d, function (a) {
          (n || a.isDirty) && a.visible && a.redraw();a.isDirtyData = !1;
        });h && h.reset(!0);m.draw();v(this, "redraw");v(this, "render");x && this.temporaryDisplay(!0);k(w, function (a) {
          a.call();
        });
      }, get: function get(a) {
        function b(b) {
          return b.id === a || b.options && b.options.id === a;
        }var c,
            d = this.series,
            h;c = e(this.axes, b) || e(this.series, b);for (h = 0; !c && h < d.length; h++) {
          c = e(d[h].points || [], b);
        }return c;
      }, getAxes: function getAxes() {
        var a = this,
            b = this.options,
            c = b.xAxis = w(b.xAxis || {}),
            b = b.yAxis = w(b.yAxis || {});k(c, function (a, b) {
          a.index = b;a.isX = !0;
        });k(b, function (a, b) {
          a.index = b;
        });c = c.concat(b);k(c, function (b) {
          new f(a, b);
        });
      }, getSelectedPoints: function getSelectedPoints() {
        var a = [];k(this.series, function (b) {
          a = a.concat(n(b.data || [], function (a) {
            return a.selected;
          }));
        });return a;
      }, getSelectedSeries: function getSelectedSeries() {
        return n(this.series, function (a) {
          return a.selected;
        });
      }, setTitle: function setTitle(a, b, c) {
        var e = this,
            d = e.options,
            h;h = d.title = B({ style: { color: "#333333", fontSize: d.isStock ? "16px" : "18px" } }, d.title, a);d = d.subtitle = B({ style: { color: "#666666" } }, d.subtitle, b);k([["title", a, h], ["subtitle", b, d]], function (a, b) {
          var c = a[0],
              d = e[c],
              h = a[1];a = a[2];d && h && (e[c] = d = d.destroy());a && a.text && !d && (e[c] = e.renderer.text(a.text, 0, 0, a.useHTML).attr({ align: a.align, "class": "highcharts-" + c, zIndex: a.zIndex || 4 }).add(), e[c].update = function (a) {
            e.setTitle(!b && a, b && a);
          }, e[c].css(a.style));
        });e.layOutTitles(c);
      }, layOutTitles: function layOutTitles(a) {
        var c = 0,
            e,
            d = this.renderer,
            h = this.spacingBox;k(["title", "subtitle"], function (a) {
          var e = this[a],
              p = this.options[a];
          a = "title" === a ? -3 : p.verticalAlign ? 0 : c + 2;var g;e && (g = p.style.fontSize, g = d.fontMetrics(g, e).b, e.css({ width: (p.width || h.width + p.widthAdjust) + "px" }).align(b({ y: a + g }, p), !1, "spacingBox"), p.floating || p.verticalAlign || (c = Math.ceil(c + e.getBBox(p.useHTML).height)));
        }, this);e = this.titleOffset !== c;this.titleOffset = c;!this.isDirtyBox && e && (this.isDirtyBox = e, this.hasRendered && z(a, !0) && this.isDirtyBox && this.redraw());
      }, getChartSize: function getChartSize() {
        var b = this.options.chart,
            c = b.width,
            b = b.height,
            e = this.renderTo;d(c) || (this.containerWidth = y(e, "width"));d(b) || (this.containerHeight = y(e, "height"));this.chartWidth = Math.max(0, c || this.containerWidth || 600);this.chartHeight = Math.max(0, a.relativeLength(b, this.chartWidth) || this.containerHeight || 400);
      }, temporaryDisplay: function temporaryDisplay(b) {
        var c = this.renderTo;if (b) for (; c && c.style;) {
          c.hcOrigStyle && (a.css(c, c.hcOrigStyle), delete c.hcOrigStyle), c.hcOrigDetached && (m.body.removeChild(c), c.hcOrigDetached = !1), c = c.parentNode;
        } else for (; c && c.style;) {
          m.body.contains(c) || (c.hcOrigDetached = !0, m.body.appendChild(c));
          if ("none" === y(c, "display", !1) || c.hcOricDetached) c.hcOrigStyle = { display: c.style.display, height: c.style.height, overflow: c.style.overflow }, b = { display: "block", overflow: "hidden" }, c !== this.renderTo && (b.height = 0), a.css(c, b), c.offsetWidth || c.style.setProperty("display", "block", "important");c = c.parentNode;if (c === m.body) break;
        }
      }, setClassName: function setClassName(a) {
        this.container.className = "highcharts-container " + (a || "");
      }, getContainer: function getContainer() {
        var e,
            d = this.options,
            h = d.chart,
            p,
            g;e = this.renderTo;var f = a.uniqueKey(),
            k;e || (this.renderTo = e = h.renderTo);c(e) && (this.renderTo = e = m.getElementById(e));e || a.error(13, !0);p = I(E(e, "data-highcharts-chart"));D(p) && t[p] && t[p].hasRendered && t[p].destroy();E(e, "data-highcharts-chart", this.index);e.innerHTML = "";h.skipClone || e.offsetWidth || this.temporaryDisplay();this.getChartSize();p = this.chartWidth;g = this.chartHeight;k = b({ position: "relative", overflow: "hidden", width: p + "px", height: g + "px", textAlign: "left", lineHeight: "normal", zIndex: 0, "-webkit-tap-highlight-color": "rgba(0,0,0,0)" }, h.style);
        this.container = e = l("div", { id: f }, k, e);this._cursor = e.style.cursor;this.renderer = new (a[h.renderer] || Q)(e, p, g, null, h.forExport, d.exporting && d.exporting.allowHTML);this.setClassName(h.className);this.renderer.setStyle(h.style);this.renderer.chartIndex = this.index;
      }, getMargins: function getMargins(a) {
        var b = this.spacing,
            c = this.margin,
            e = this.titleOffset;this.resetMargins();e && !d(c[0]) && (this.plotTop = Math.max(this.plotTop, e + this.options.title.margin + b[0]));this.legend.display && this.legend.adjustMargins(c, b);this.extraMargin && (this[this.extraMargin.type] = (this[this.extraMargin.type] || 0) + this.extraMargin.value);this.extraTopMargin && (this.plotTop += this.extraTopMargin);a || this.getAxisMargins();
      }, getAxisMargins: function getAxisMargins() {
        var a = this,
            b = a.axisOffset = [0, 0, 0, 0],
            c = a.margin;a.hasCartesianSeries && k(a.axes, function (a) {
          a.visible && a.getOffset();
        });k(q, function (e, h) {
          d(c[h]) || (a[e] += b[h]);
        });a.setChartSize();
      }, reflow: function reflow(a) {
        var b = this,
            c = b.options.chart,
            e = b.renderTo,
            h = d(c.width) && d(c.height),
            p = c.width || y(e, "width"),
            c = c.height || y(e, "height"),
            e = a ? a.target : O;if (!h && !b.isPrinting && p && c && (e === O || e === m)) {
          if (p !== b.containerWidth || c !== b.containerHeight) clearTimeout(b.reflowTimeout), b.reflowTimeout = H(function () {
            b.container && b.setSize(void 0, void 0, !1);
          }, a ? 100 : 0);b.containerWidth = p;b.containerHeight = c;
        }
      }, initReflow: function initReflow() {
        var a = this,
            b;b = C(O, "resize", function (b) {
          a.reflow(b);
        });C(a, "destroy", b);
      }, setSize: function setSize(b, c, e) {
        var d = this,
            h = d.renderer;d.isResizing += 1;a.setAnimation(e, d);d.oldChartHeight = d.chartHeight;d.oldChartWidth = d.chartWidth;void 0 !== b && (d.options.chart.width = b);void 0 !== c && (d.options.chart.height = c);d.getChartSize();b = h.globalAnimation;(b ? A : g)(d.container, { width: d.chartWidth + "px", height: d.chartHeight + "px" }, b);d.setChartSize(!0);h.setSize(d.chartWidth, d.chartHeight, e);k(d.axes, function (a) {
          a.isDirty = !0;a.setScale();
        });d.isDirtyLegend = !0;d.isDirtyBox = !0;d.layOutTitles();d.getMargins();d.redraw(e);d.oldChartHeight = null;v(d, "resize");H(function () {
          d && v(d, "endResize", null, function () {
            --d.isResizing;
          });
        }, F(b).duration);
      }, setChartSize: function setChartSize(a) {
        function b(a) {
          a = f[a] || 0;return Math.max(m || a, a) / 2;
        }var c = this.inverted,
            e = this.renderer,
            d = this.chartWidth,
            h = this.chartHeight,
            p = this.options.chart,
            g = this.spacing,
            f = this.clipOffset,
            q,
            n,
            l,
            z,
            m;this.plotLeft = q = Math.round(this.plotLeft);this.plotTop = n = Math.round(this.plotTop);this.plotWidth = l = Math.max(0, Math.round(d - q - this.marginRight));this.plotHeight = z = Math.max(0, Math.round(h - n - this.marginBottom));this.plotSizeX = c ? z : l;this.plotSizeY = c ? l : z;this.plotBorderWidth = p.plotBorderWidth || 0;this.spacingBox = e.spacingBox = { x: g[3], y: g[0],
          width: d - g[3] - g[1], height: h - g[0] - g[2] };this.plotBox = e.plotBox = { x: q, y: n, width: l, height: z };m = 2 * Math.floor(this.plotBorderWidth / 2);c = Math.ceil(b(3));e = Math.ceil(b(0));this.clipBox = { x: c, y: e, width: Math.floor(this.plotSizeX - b(1) - c), height: Math.max(0, Math.floor(this.plotSizeY - b(2) - e)) };a || k(this.axes, function (a) {
          a.setAxisSize();a.setAxisTranslation();
        });
      }, resetMargins: function resetMargins() {
        var a = this,
            b = a.options.chart;k(["margin", "spacing"], function (c) {
          var e = b[c],
              d = J(e) ? e : [e, e, e, e];k(["Top", "Right", "Bottom", "Left"], function (e, h) {
            a[c][h] = z(b[c + e], d[h]);
          });
        });k(q, function (b, c) {
          a[b] = z(a.margin[c], a.spacing[c]);
        });a.axisOffset = [0, 0, 0, 0];a.clipOffset = [];
      }, drawChartBox: function drawChartBox() {
        var a = this.options.chart,
            b = this.renderer,
            c = this.chartWidth,
            e = this.chartHeight,
            d = this.chartBackground,
            h = this.plotBackground,
            p = this.plotBorder,
            g,
            f = this.plotBGImage,
            k = a.backgroundColor,
            q = a.plotBackgroundColor,
            l = a.plotBackgroundImage,
            n,
            z = this.plotLeft,
            m = this.plotTop,
            w = this.plotWidth,
            I = this.plotHeight,
            v = this.plotBox,
            r = this.clipRect,
            B = this.clipBox,
            y = "animate";
        d || (this.chartBackground = d = b.rect().addClass("highcharts-background").add(), y = "attr");g = a.borderWidth || 0;n = g + (a.shadow ? 8 : 0);k = { fill: k || "none" };if (g || d["stroke-width"]) k.stroke = a.borderColor, k["stroke-width"] = g;d.attr(k).shadow(a.shadow);d[y]({ x: n / 2, y: n / 2, width: c - n - g % 2, height: e - n - g % 2, r: a.borderRadius });y = "animate";h || (y = "attr", this.plotBackground = h = b.rect().addClass("highcharts-plot-background").add());h[y](v);h.attr({ fill: q || "none" }).shadow(a.plotShadow);l && (f ? f.animate(v) : this.plotBGImage = b.image(l, z, m, w, I).add());r ? r.animate({ width: B.width, height: B.height }) : this.clipRect = b.clipRect(B);y = "animate";p || (y = "attr", this.plotBorder = p = b.rect().addClass("highcharts-plot-border").attr({ zIndex: 1 }).add());p.attr({ stroke: a.plotBorderColor, "stroke-width": a.plotBorderWidth || 0, fill: "none" });p[y](p.crisp({ x: z, y: m, width: w, height: I }, -p.strokeWidth()));this.isDirtyBox = !1;
      }, propFromSeries: function propFromSeries() {
        var a = this,
            b = a.options.chart,
            c,
            e = a.options.series,
            d,
            p;k(["inverted", "angular", "polar"], function (g) {
          c = h[b.type || b.defaultSeriesType];
          p = b[g] || c && c.prototype[g];for (d = e && e.length; !p && d--;) {
            (c = h[e[d].type]) && c.prototype[g] && (p = !0);
          }a[g] = p;
        });
      }, linkSeries: function linkSeries() {
        var a = this,
            b = a.series;k(b, function (a) {
          a.linkedSeries.length = 0;
        });k(b, function (b) {
          var e = b.options.linkedTo;c(e) && (e = ":previous" === e ? a.series[b.index - 1] : a.get(e)) && e.linkedParent !== b && (e.linkedSeries.push(b), b.linkedParent = e, b.visible = z(b.options.visible, e.options.visible, b.visible));
        });
      }, renderSeries: function renderSeries() {
        k(this.series, function (a) {
          a.translate();a.render();
        });
      }, renderLabels: function renderLabels() {
        var a = this,
            c = a.options.labels;c.items && k(c.items, function (e) {
          var d = b(c.style, e.style),
              h = I(d.left) + a.plotLeft,
              p = I(d.top) + a.plotTop + 12;delete d.left;delete d.top;a.renderer.text(e.html, h, p).attr({ zIndex: 2 }).css(d).add();
        });
      }, render: function render() {
        var a = this.axes,
            b = this.renderer,
            c = this.options,
            e,
            d,
            h;this.setTitle();this.legend = new G(this, c.legend);this.getStacks && this.getStacks();this.getMargins(!0);this.setChartSize();c = this.plotWidth;e = this.plotHeight -= 21;k(a, function (a) {
          a.setScale();
        });this.getAxisMargins();d = 1.1 < c / this.plotWidth;h = 1.05 < e / this.plotHeight;if (d || h) k(a, function (a) {
          (a.horiz && d || !a.horiz && h) && a.setTickInterval(!0);
        }), this.getMargins();this.drawChartBox();this.hasCartesianSeries && k(a, function (a) {
          a.visible && a.render();
        });this.seriesGroup || (this.seriesGroup = b.g("series-group").attr({ zIndex: 3 }).add());this.renderSeries();this.renderLabels();this.addCredits();this.setResponsive && this.setResponsive();this.hasRendered = !0;
      }, addCredits: function addCredits(a) {
        var b = this;a = B(!0, this.options.credits, a);a.enabled && !this.credits && (this.credits = this.renderer.text(a.text + (this.mapCredits || ""), 0, 0).addClass("highcharts-credits").on("click", function () {
          a.href && (O.location.href = a.href);
        }).attr({ align: a.position.align, zIndex: 8 }).css(a.style).add().align(a.position), this.credits.update = function (a) {
          b.credits = b.credits.destroy();b.addCredits(a);
        });
      }, destroy: function destroy() {
        var b = this,
            c = b.axes,
            e = b.series,
            d = b.container,
            h,
            p = d && d.parentNode;v(b, "destroy");b.renderer.forExport ? a.erase(t, b) : t[b.index] = void 0;a.chartCount--;b.renderTo.removeAttribute("data-highcharts-chart");
        L(b);for (h = c.length; h--;) {
          c[h] = c[h].destroy();
        }this.scroller && this.scroller.destroy && this.scroller.destroy();for (h = e.length; h--;) {
          e[h] = e[h].destroy();
        }k("title subtitle chartBackground plotBackground plotBGImage plotBorder seriesGroup clipRect credits pointer rangeSelector legend resetZoomButton tooltip renderer".split(" "), function (a) {
          var c = b[a];c && c.destroy && (b[a] = c.destroy());
        });d && (d.innerHTML = "", L(d), p && u(d));K(b, function (a, c) {
          delete b[c];
        });
      }, isReadyToRender: function isReadyToRender() {
        var a = this;return P || O != O.top || "complete" === m.readyState ? !0 : (m.attachEvent("onreadystatechange", function () {
          m.detachEvent("onreadystatechange", a.firstRender);"complete" === m.readyState && a.firstRender();
        }), !1);
      }, firstRender: function firstRender() {
        var a = this,
            b = a.options;if (a.isReadyToRender()) {
          a.getContainer();v(a, "init");a.resetMargins();a.setChartSize();a.propFromSeries();a.getAxes();k(b.series || [], function (b) {
            a.initSeries(b);
          });a.linkSeries();v(a, "beforeRender");p && (a.pointer = new p(a, b));a.render();if (!a.renderer.imgCount && a.onload) a.onload();
          a.temporaryDisplay(!0);
        }
      }, onload: function onload() {
        k([this.callback].concat(this.callbacks), function (a) {
          a && void 0 !== this.index && a.apply(this, [this]);
        }, this);v(this, "load");v(this, "render");d(this.index) && !1 !== this.options.chart.reflow && this.initReflow();this.onload = null;
      } });
  })(M);(function (a) {
    var C,
        A = a.each,
        F = a.extend,
        E = a.erase,
        m = a.fireEvent,
        f = a.format,
        l = a.isArray,
        r = a.isNumber,
        u = a.pick,
        t = a.removeEvent;a.Point = C = function C() {};a.Point.prototype = { init: function init(a, d, f) {
        this.series = a;this.color = a.color;this.applyOptions(d, f);a.options.colorByPoint ? (d = a.options.colors || a.chart.options.colors, this.color = this.color || d[a.colorCounter], d = d.length, f = a.colorCounter, a.colorCounter++, a.colorCounter === d && (a.colorCounter = 0)) : f = a.colorIndex;this.colorIndex = u(this.colorIndex, f);a.chart.pointCount++;return this;
      }, applyOptions: function applyOptions(a, d) {
        var g = this.series,
            b = g.options.pointValKey || g.pointValKey;a = C.prototype.optionsToObject.call(this, a);F(this, a);this.options = this.options ? F(this.options, a) : a;a.group && delete this.group;b && (this.y = this[b]);this.isNull = u(this.isValid && !this.isValid(), null === this.x || !r(this.y, !0));this.selected && (this.state = "select");"name" in this && void 0 === d && g.xAxis && g.xAxis.hasNames && (this.x = g.xAxis.nameToX(this));void 0 === this.x && g && (this.x = void 0 === d ? g.autoIncrement(this) : d);return this;
      }, optionsToObject: function optionsToObject(a) {
        var d = {},
            g = this.series,
            b = g.options.keys,
            e = b || g.pointArrayMap || ["y"],
            f = e.length,
            m = 0,
            n = 0;if (r(a) || null === a) d[e[0]] = a;else if (l(a)) for (!b && a.length > f && (g = _typeof(a[0]), "string" === g ? d.name = a[0] : "number" === g && (d.x = a[0]), m++); n < f;) {
          b && void 0 === a[m] || (d[e[n]] = a[m]), m++, n++;
        } else "object" === (typeof a === "undefined" ? "undefined" : _typeof(a)) && (d = a, a.dataLabels && (g._hasPointLabels = !0), a.marker && (g._hasPointMarkers = !0));return d;
      }, getClassName: function getClassName() {
        return "highcharts-point" + (this.selected ? " highcharts-point-select" : "") + (this.negative ? " highcharts-negative" : "") + (this.isNull ? " highcharts-null-point" : "") + (void 0 !== this.colorIndex ? " highcharts-color-" + this.colorIndex : "") + (this.options.className ? " " + this.options.className : "") + (this.zone && this.zone.className ? " " + this.zone.className.replace("highcharts-negative", "") : "");
      }, getZone: function getZone() {
        var a = this.series,
            d = a.zones,
            a = a.zoneAxis || "y",
            f = 0,
            b;for (b = d[f]; this[a] >= b.value;) {
          b = d[++f];
        }b && b.color && !this.options.color && (this.color = b.color);return b;
      }, destroy: function destroy() {
        var a = this.series.chart,
            d = a.hoverPoints,
            f;a.pointCount--;d && (this.setState(), E(d, this), d.length || (a.hoverPoints = null));if (this === a.hoverPoint) this.onMouseOut();if (this.graphic || this.dataLabel) t(this), this.destroyElements();this.legendItem && a.legend.destroyItem(this);
        for (f in this) {
          this[f] = null;
        }
      }, destroyElements: function destroyElements() {
        for (var a = ["graphic", "dataLabel", "dataLabelUpper", "connector", "shadowGroup"], d, f = 6; f--;) {
          d = a[f], this[d] && (this[d] = this[d].destroy());
        }
      }, getLabelConfig: function getLabelConfig() {
        return { x: this.category, y: this.y, color: this.color, colorIndex: this.colorIndex, key: this.name || this.category, series: this.series, point: this, percentage: this.percentage, total: this.total || this.stackTotal };
      }, tooltipFormatter: function tooltipFormatter(a) {
        var d = this.series,
            g = d.tooltipOptions,
            b = u(g.valueDecimals, ""),
            e = g.valuePrefix || "",
            l = g.valueSuffix || "";A(d.pointArrayMap || ["y"], function (d) {
          d = "{point." + d;if (e || l) a = a.replace(d + "}", e + d + "}" + l);a = a.replace(d + "}", d + ":,." + b + "f}");
        });return f(a, { point: this, series: this.series });
      }, firePointEvent: function firePointEvent(a, d, f) {
        var b = this,
            e = this.series.options;(e.point.events[a] || b.options && b.options.events && b.options.events[a]) && this.importEvents();"click" === a && e.allowPointSelect && (f = function f(a) {
          b.select && b.select(null, a.ctrlKey || a.metaKey || a.shiftKey);
        });m(this, a, d, f);
      }, visible: !0 };
  })(M);
  (function (a) {
    var C = a.addEvent,
        A = a.animObject,
        F = a.arrayMax,
        E = a.arrayMin,
        m = a.correctFloat,
        f = a.Date,
        l = a.defaultOptions,
        r = a.defaultPlotOptions,
        u = a.defined,
        t = a.each,
        g = a.erase,
        d = a.extend,
        k = a.fireEvent,
        b = a.grep,
        e = a.isArray,
        v = a.isNumber,
        y = a.isString,
        n = a.merge,
        D = a.objectEach,
        J = a.pick,
        c = a.removeEvent,
        G = a.splat,
        q = a.SVGElement,
        B = a.syncTimeout,
        K = a.win;a.Series = a.seriesType("line", null, { lineWidth: 2, allowPointSelect: !1, showCheckbox: !1, animation: { duration: 1E3 }, events: {}, marker: { lineWidth: 0, lineColor: "#ffffff", radius: 4,
        states: { hover: { animation: { duration: 50 }, enabled: !0, radiusPlus: 2, lineWidthPlus: 1 }, select: { fillColor: "#cccccc", lineColor: "#000000", lineWidth: 2 } } }, point: { events: {} }, dataLabels: { align: "center", formatter: function formatter() {
          return null === this.y ? "" : a.numberFormat(this.y, -1);
        }, style: { fontSize: "11px", fontWeight: "bold", color: "contrast", textOutline: "1px contrast" }, verticalAlign: "bottom", x: 0, y: 0, padding: 5 }, cropThreshold: 300, pointRange: 0, softThreshold: !0, states: { hover: { animation: { duration: 50 }, lineWidthPlus: 1, marker: {},
          halo: { size: 10, opacity: .25 } }, select: { marker: {} } }, stickyTracking: !0, turboThreshold: 1E3, findNearestPointBy: "x" }, { isCartesian: !0, pointClass: a.Point, sorted: !0, requireSorting: !0, directTouch: !1, axisTypes: ["xAxis", "yAxis"], colorCounter: 0, parallelArrays: ["x", "y"], coll: "series", init: function init(a, b) {
        var c = this,
            e,
            h = a.series,
            p;c.chart = a;c.options = b = c.setOptions(b);c.linkedSeries = [];c.bindAxes();d(c, { name: b.name, state: "", visible: !1 !== b.visible, selected: !0 === b.selected });e = b.events;D(e, function (a, b) {
          C(c, b, a);
        });if (e && e.click || b.point && b.point.events && b.point.events.click || b.allowPointSelect) a.runTrackerClick = !0;c.getColor();c.getSymbol();t(c.parallelArrays, function (a) {
          c[a + "Data"] = [];
        });c.setData(b.data, !1);c.isCartesian && (a.hasCartesianSeries = !0);h.length && (p = h[h.length - 1]);c._i = J(p && p._i, -1) + 1;a.orderSeries(this.insert(h));
      }, insert: function insert(a) {
        var b = this.options.index,
            c;if (v(b)) {
          for (c = a.length; c--;) {
            if (b >= J(a[c].options.index, a[c]._i)) {
              a.splice(c + 1, 0, this);break;
            }
          }-1 === c && a.unshift(this);c += 1;
        } else a.push(this);return J(c, a.length - 1);
      }, bindAxes: function bindAxes() {
        var b = this,
            c = b.options,
            e = b.chart,
            d;t(b.axisTypes || [], function (h) {
          t(e[h], function (a) {
            d = a.options;if (c[h] === d.index || void 0 !== c[h] && c[h] === d.id || void 0 === c[h] && 0 === d.index) b.insert(a.series), b[h] = a, a.isDirty = !0;
          });b[h] || b.optionalAxis === h || a.error(18, !0);
        });
      }, updateParallelArrays: function updateParallelArrays(a, b) {
        var c = a.series,
            e = arguments,
            d = v(b) ? function (e) {
          var d = "y" === e && c.toYData ? c.toYData(a) : a[e];c[e + "Data"][b] = d;
        } : function (a) {
          Array.prototype[b].apply(c[a + "Data"], Array.prototype.slice.call(e, 2));
        };t(c.parallelArrays, d);
      }, autoIncrement: function autoIncrement() {
        var a = this.options,
            b = this.xIncrement,
            c,
            e = a.pointIntervalUnit,
            b = J(b, a.pointStart, 0);this.pointInterval = c = J(this.pointInterval, a.pointInterval, 1);e && (a = new f(b), "day" === e ? a = +a[f.hcSetDate](a[f.hcGetDate]() + c) : "month" === e ? a = +a[f.hcSetMonth](a[f.hcGetMonth]() + c) : "year" === e && (a = +a[f.hcSetFullYear](a[f.hcGetFullYear]() + c)), c = a - b);this.xIncrement = b + c;return b;
      }, setOptions: function setOptions(a) {
        var b = this.chart,
            c = b.options,
            e = c.plotOptions,
            d = (b.userOptions || {}).plotOptions || {},
            p = e[this.type];this.userOptions = a;b = n(p, e.series, a);this.tooltipOptions = n(l.tooltip, l.plotOptions.series && l.plotOptions.series.tooltip, l.plotOptions[this.type].tooltip, c.tooltip.userOptions, e.series && e.series.tooltip, e[this.type].tooltip, a.tooltip);this.stickyTracking = J(a.stickyTracking, d[this.type] && d[this.type].stickyTracking, d.series && d.series.stickyTracking, this.tooltipOptions.shared && !this.noSharedTooltip ? !0 : b.stickyTracking);null === p.marker && delete b.marker;this.zoneAxis = b.zoneAxis;a = this.zones = (b.zones || []).slice();!b.negativeColor && !b.negativeFillColor || b.zones || a.push({ value: b[this.zoneAxis + "Threshold"] || b.threshold || 0, className: "highcharts-negative", color: b.negativeColor, fillColor: b.negativeFillColor });a.length && u(a[a.length - 1].value) && a.push({ color: this.color, fillColor: this.fillColor });return b;
      }, getCyclic: function getCyclic(a, b, c) {
        var e,
            d = this.chart,
            p = this.userOptions,
            f = a + "Index",
            g = a + "Counter",
            k = c ? c.length : J(d.options.chart[a + "Count"], d[a + "Count"]);b || (e = J(p[f], p["_" + f]), u(e) || (d.series.length || (d[g] = 0), p["_" + f] = e = d[g] % k, d[g] += 1), c && (b = c[e]));void 0 !== e && (this[f] = e);this[a] = b;
      }, getColor: function getColor() {
        this.options.colorByPoint ? this.options.color = null : this.getCyclic("color", this.options.color || r[this.type].color, this.chart.options.colors);
      }, getSymbol: function getSymbol() {
        this.getCyclic("symbol", this.options.marker.symbol, this.chart.options.symbols);
      }, drawLegendSymbol: a.LegendSymbolMixin.drawLineMarker, setData: function setData(b, c, d, f) {
        var h = this,
            p = h.points,
            g = p && p.length || 0,
            k,
            q = h.options,
            l = h.chart,
            n = null,
            m = h.xAxis,
            z = q.turboThreshold,
            r = this.xData,
            B = this.yData,
            I = (k = h.pointArrayMap) && k.length;b = b || [];k = b.length;c = J(c, !0);if (!1 !== f && k && g === k && !h.cropped && !h.hasGroupedData && h.visible) t(b, function (a, b) {
          p[b].update && a !== q.data[b] && p[b].update(a, !1, null, !1);
        });else {
          h.xIncrement = null;h.colorCounter = 0;t(this.parallelArrays, function (a) {
            h[a + "Data"].length = 0;
          });if (z && k > z) {
            for (d = 0; null === n && d < k;) {
              n = b[d], d++;
            }if (v(n)) for (d = 0; d < k; d++) {
              r[d] = this.autoIncrement(), B[d] = b[d];
            } else if (e(n)) {
              if (I) for (d = 0; d < k; d++) {
                n = b[d], r[d] = n[0], B[d] = n.slice(1, I + 1);
              } else for (d = 0; d < k; d++) {
                n = b[d], r[d] = n[0], B[d] = n[1];
              }
            } else a.error(12);
          } else for (d = 0; d < k; d++) {
            void 0 !== b[d] && (n = { series: h }, h.pointClass.prototype.applyOptions.apply(n, [b[d]]), h.updateParallelArrays(n, d));
          }y(B[0]) && a.error(14, !0);h.data = [];h.options.data = h.userOptions.data = b;for (d = g; d--;) {
            p[d] && p[d].destroy && p[d].destroy();
          }m && (m.minRange = m.userMinRange);h.isDirty = l.isDirtyBox = !0;h.isDirtyData = !!p;d = !1;
        }"point" === q.legendType && (this.processData(), this.generatePoints());c && l.redraw(d);
      }, processData: function processData(b) {
        var c = this.xData,
            e = this.yData,
            d = c.length,
            h;h = 0;var p,
            f,
            g = this.xAxis,
            k,
            q = this.options;k = q.cropThreshold;var n = this.getExtremesFromAll || q.getExtremesFromAll,
            l = this.isCartesian,
            q = g && g.val2lin,
            m = g && g.isLog,
            v,
            r;if (l && !this.isDirty && !g.isDirty && !this.yAxis.isDirty && !b) return !1;g && (b = g.getExtremes(), v = b.min, r = b.max);if (l && this.sorted && !n && (!k || d > k || this.forceCrop)) if (c[d - 1] < v || c[0] > r) c = [], e = [];else if (c[0] < v || c[d - 1] > r) h = this.cropData(this.xData, this.yData, v, r), c = h.xData, e = h.yData, h = h.start, p = !0;for (k = c.length || 1; --k;) {
          d = m ? q(c[k]) - q(c[k - 1]) : c[k] - c[k - 1], 0 < d && (void 0 === f || d < f) ? f = d : 0 > d && this.requireSorting && a.error(15);
        }this.cropped = p;this.cropStart = h;this.processedXData = c;this.processedYData = e;this.closestPointRange = f;
      }, cropData: function cropData(a, b, c, e) {
        var d = a.length,
            p = 0,
            g = d,
            f = J(this.cropShoulder, 1),
            k;for (k = 0; k < d; k++) {
          if (a[k] >= c) {
            p = Math.max(0, k - f);break;
          }
        }for (c = k; c < d; c++) {
          if (a[c] > e) {
            g = c + f;break;
          }
        }return { xData: a.slice(p, g), yData: b.slice(p, g), start: p, end: g };
      }, generatePoints: function generatePoints() {
        var a = this.options,
            b = a.data,
            c = this.data,
            e,
            d = this.processedXData,
            g = this.processedYData,
            f = this.pointClass,
            k = d.length,
            q = this.cropStart || 0,
            n,
            l = this.hasGroupedData,
            a = a.keys,
            m,
            v = [],
            r;c || l || (c = [], c.length = b.length, c = this.data = c);a && l && (this.options.keys = !1);for (r = 0; r < k; r++) {
          n = q + r, l ? (m = new f().init(this, [d[r]].concat(G(g[r]))), m.dataGroup = this.groupMap[r]) : (m = c[n]) || void 0 === b[n] || (c[n] = m = new f().init(this, b[n], d[r])), m && (m.index = n, v[r] = m);
        }this.options.keys = a;if (c && (k !== (e = c.length) || l)) for (r = 0; r < e; r++) {
          r !== q || l || (r += k), c[r] && (c[r].destroyElements(), c[r].plotX = void 0);
        }this.data = c;this.points = v;
      }, getExtremes: function getExtremes(a) {
        var b = this.yAxis,
            c = this.processedXData,
            d,
            h = [],
            p = 0;d = this.xAxis.getExtremes();var g = d.min,
            f = d.max,
            k,
            q,
            n,
            l;a = a || this.stackedYData || this.processedYData || [];d = a.length;for (l = 0; l < d; l++) {
          if (q = c[l], n = a[l], k = (v(n, !0) || e(n)) && (!b.positiveValuesOnly || n.length || 0 < n), q = this.getExtremesFromAll || this.options.getExtremesFromAll || this.cropped || (c[l] || q) >= g && (c[l] || q) <= f, k && q) if (k = n.length) for (; k--;) {
            null !== n[k] && (h[p++] = n[k]);
          } else h[p++] = n;
        }this.dataMin = E(h);this.dataMax = F(h);
      }, translate: function translate() {
        this.processedXData || this.processData();this.generatePoints();var a = this.options,
            b = a.stacking,
            c = this.xAxis,
            e = c.categories,
            d = this.yAxis,
            g = this.points,
            f = g.length,
            k = !!this.modifyValue,
            q = a.pointPlacement,
            n = "between" === q || v(q),
            l = a.threshold,
            r = a.startFromThreshold ? l : 0,
            B,
            y,
            t,
            G,
            D = Number.MAX_VALUE;"between" === q && (q = .5);v(q) && (q *= J(a.pointRange || c.pointRange));for (a = 0; a < f; a++) {
          var K = g[a],
              A = K.x,
              C = K.y;y = K.low;var E = b && d.stacks[(this.negStacks && C < (r ? 0 : l) ? "-" : "") + this.stackKey],
              F;d.positiveValuesOnly && null !== C && 0 >= C && (K.isNull = !0);K.plotX = B = m(Math.min(Math.max(-1E5, c.translate(A, 0, 0, 0, 1, q, "flags" === this.type)), 1E5));b && this.visible && !K.isNull && E && E[A] && (G = this.getStackIndicator(G, A, this.index), F = E[A], C = F.points[G.key], y = C[0], C = C[1], y === r && G.key === E[A].base && (y = J(l, d.min)), d.positiveValuesOnly && 0 >= y && (y = null), K.total = K.stackTotal = F.total, K.percentage = F.total && K.y / F.total * 100, K.stackY = C, F.setOffset(this.pointXOffset || 0, this.barW || 0));K.yBottom = u(y) ? d.translate(y, 0, 1, 0, 1) : null;k && (C = this.modifyValue(C, K));K.plotY = y = "number" === typeof C && Infinity !== C ? Math.min(Math.max(-1E5, d.translate(C, 0, 1, 0, 1)), 1E5) : void 0;K.isInside = void 0 !== y && 0 <= y && y <= d.len && 0 <= B && B <= c.len;K.clientX = n ? m(c.translate(A, 0, 0, 0, 1, q)) : B;K.negative = K.y < (l || 0);K.category = e && void 0 !== e[K.x] ? e[K.x] : K.x;K.isNull || (void 0 !== t && (D = Math.min(D, Math.abs(B - t))), t = B);K.zone = this.zones.length && K.getZone();
        }this.closestPointRangePx = D;
      }, getValidPoints: function getValidPoints(a, c) {
        var e = this.chart;return b(a || this.points || [], function (a) {
          return c && !e.isInsidePlot(a.plotX, a.plotY, e.inverted) ? !1 : !a.isNull;
        });
      }, setClip: function setClip(a) {
        var b = this.chart,
            c = this.options,
            e = b.renderer,
            d = b.inverted,
            p = this.clipBox,
            g = p || b.clipBox,
            f = this.sharedClipKey || ["_sharedClip", a && a.duration, a && a.easing, g.height, c.xAxis, c.yAxis].join(),
            k = b[f],
            q = b[f + "m"];k || (a && (g.width = 0, b[f + "m"] = q = e.clipRect(-99, d ? -b.plotLeft : -b.plotTop, 99, d ? b.chartWidth : b.chartHeight)), b[f] = k = e.clipRect(g), k.count = { length: 0 });a && !k.count[this.index] && (k.count[this.index] = !0, k.count.length += 1);!1 !== c.clip && (this.group.clip(a || p ? k : b.clipRect), this.markerGroup.clip(q), this.sharedClipKey = f);a || (k.count[this.index] && (delete k.count[this.index], --k.count.length), 0 === k.count.length && f && b[f] && (p || (b[f] = b[f].destroy()), b[f + "m"] && (b[f + "m"] = b[f + "m"].destroy())));
      }, animate: function animate(a) {
        var b = this.chart,
            c = A(this.options.animation),
            e;a ? this.setClip(c) : (e = this.sharedClipKey, (a = b[e]) && a.animate({ width: b.plotSizeX }, c), b[e + "m"] && b[e + "m"].animate({ width: b.plotSizeX + 99 }, c), this.animate = null);
      }, afterAnimate: function afterAnimate() {
        this.setClip();
        k(this, "afterAnimate");this.finishedAnimating = !0;
      }, drawPoints: function drawPoints() {
        var a = this.points,
            b = this.chart,
            c,
            e,
            d,
            f,
            g = this.options.marker,
            k,
            q,
            n,
            l,
            m = this[this.specialGroup] || this.markerGroup,
            r = J(g.enabled, this.xAxis.isRadial ? !0 : null, this.closestPointRangePx >= 2 * g.radius);if (!1 !== g.enabled || this._hasPointMarkers) for (e = 0; e < a.length; e++) {
          d = a[e], c = d.plotY, f = d.graphic, k = d.marker || {}, q = !!d.marker, n = r && void 0 === k.enabled || k.enabled, l = d.isInside, n && v(c) && null !== d.y ? (c = J(k.symbol, this.symbol), d.hasImage = 0 === c.indexOf("url"), n = this.markerAttribs(d, d.selected && "select"), f ? f[l ? "show" : "hide"](!0).animate(n) : l && (0 < n.width || d.hasImage) && (d.graphic = f = b.renderer.symbol(c, n.x, n.y, n.width, n.height, q ? k : g).add(m)), f && f.attr(this.pointAttribs(d, d.selected && "select")), f && f.addClass(d.getClassName(), !0)) : f && (d.graphic = f.destroy());
        }
      }, markerAttribs: function markerAttribs(a, b) {
        var c = this.options.marker,
            e = a.marker || {},
            d = J(e.radius, c.radius);b && (c = c.states[b], b = e.states && e.states[b], d = J(b && b.radius, c && c.radius, d + (c && c.radiusPlus || 0)));a.hasImage && (d = 0);a = { x: Math.floor(a.plotX) - d, y: a.plotY - d };d && (a.width = a.height = 2 * d);return a;
      }, pointAttribs: function pointAttribs(a, b) {
        var c = this.options.marker,
            e = a && a.options,
            d = e && e.marker || {},
            f = this.color,
            g = e && e.color,
            p = a && a.color,
            e = J(d.lineWidth, c.lineWidth);a = a && a.zone && a.zone.color;f = g || a || p || f;a = d.fillColor || c.fillColor || f;f = d.lineColor || c.lineColor || f;b && (c = c.states[b], b = d.states && d.states[b] || {}, e = J(b.lineWidth, c.lineWidth, e + J(b.lineWidthPlus, c.lineWidthPlus, 0)), a = b.fillColor || c.fillColor || a, f = b.lineColor || c.lineColor || f);return { stroke: f, "stroke-width": e, fill: a };
      }, destroy: function destroy() {
        var a = this,
            b = a.chart,
            e = /AppleWebKit\/533/.test(K.navigator.userAgent),
            d,
            h,
            f = a.data || [],
            n,
            l;k(a, "destroy");c(a);t(a.axisTypes || [], function (b) {
          (l = a[b]) && l.series && (g(l.series, a), l.isDirty = l.forceRedraw = !0);
        });a.legendItem && a.chart.legend.destroyItem(a);for (h = f.length; h--;) {
          (n = f[h]) && n.destroy && n.destroy();
        }a.points = null;clearTimeout(a.animationTimeout);D(a, function (a, b) {
          a instanceof q && !a.survive && (d = e && "group" === b ? "hide" : "destroy", a[d]());
        });
        b.hoverSeries === a && (b.hoverSeries = null);g(b.series, a);b.orderSeries();D(a, function (b, c) {
          delete a[c];
        });
      }, getGraphPath: function getGraphPath(a, b, c) {
        var e = this,
            d = e.options,
            f = d.step,
            g,
            p = [],
            k = [],
            q;a = a || e.points;(g = a.reversed) && a.reverse();(f = { right: 1, center: 2 }[f] || f && 3) && g && (f = 4 - f);!d.connectNulls || b || c || (a = this.getValidPoints(a));t(a, function (h, g) {
          var n = h.plotX,
              l = h.plotY,
              m = a[g - 1];(h.leftCliff || m && m.rightCliff) && !c && (q = !0);h.isNull && !u(b) && 0 < g ? q = !d.connectNulls : h.isNull && !b ? q = !0 : (0 === g || q ? g = ["M", h.plotX, h.plotY] : e.getPointSpline ? g = e.getPointSpline(a, h, g) : f ? (g = 1 === f ? ["L", m.plotX, l] : 2 === f ? ["L", (m.plotX + n) / 2, m.plotY, "L", (m.plotX + n) / 2, l] : ["L", n, m.plotY], g.push("L", n, l)) : g = ["L", n, l], k.push(h.x), f && k.push(h.x), p.push.apply(p, g), q = !1);
        });p.xMap = k;return e.graphPath = p;
      }, drawGraph: function drawGraph() {
        var a = this,
            b = this.options,
            c = (this.gappedPath || this.getGraphPath).call(this),
            e = [["graph", "highcharts-graph", b.lineColor || this.color, b.dashStyle]];t(this.zones, function (c, d) {
          e.push(["zone-graph-" + d, "highcharts-graph highcharts-zone-graph-" + d + " " + (c.className || ""), c.color || a.color, c.dashStyle || b.dashStyle]);
        });t(e, function (e, d) {
          var h = e[0],
              f = a[h];f ? (f.endX = c.xMap, f.animate({ d: c })) : c.length && (a[h] = a.chart.renderer.path(c).addClass(e[1]).attr({ zIndex: 1 }).add(a.group), f = { stroke: e[2], "stroke-width": b.lineWidth, fill: a.fillGraph && a.color || "none" }, e[3] ? f.dashstyle = e[3] : "square" !== b.linecap && (f["stroke-linecap"] = f["stroke-linejoin"] = "round"), f = a[h].attr(f).shadow(2 > d && b.shadow));f && (f.startX = c.xMap, f.isArea = c.isArea);
        });
      }, applyZones: function applyZones() {
        var a = this,
            b = this.chart,
            c = b.renderer,
            e = this.zones,
            d,
            f,
            g = this.clips || [],
            k,
            q = this.graph,
            n = this.area,
            l = Math.max(b.chartWidth, b.chartHeight),
            m = this[(this.zoneAxis || "y") + "Axis"],
            r,
            v,
            B = b.inverted,
            y,
            u,
            G,
            D,
            K = !1;e.length && (q || n) && m && void 0 !== m.min && (v = m.reversed, y = m.horiz, q && q.hide(), n && n.hide(), r = m.getExtremes(), t(e, function (e, h) {
          d = v ? y ? b.plotWidth : 0 : y ? 0 : m.toPixels(r.min);d = Math.min(Math.max(J(f, d), 0), l);f = Math.min(Math.max(Math.round(m.toPixels(J(e.value, r.max), !0)), 0), l);K && (d = f = m.toPixels(r.max));u = Math.abs(d - f);G = Math.min(d, f);D = Math.max(d, f);m.isXAxis ? (k = { x: B ? D : G, y: 0, width: u, height: l }, y || (k.x = b.plotHeight - k.x)) : (k = { x: 0, y: B ? D : G, width: l, height: u }, y && (k.y = b.plotWidth - k.y));B && c.isVML && (k = m.isXAxis ? { x: 0, y: v ? G : D, height: k.width, width: b.chartWidth } : { x: k.y - b.plotLeft - b.spacingBox.x, y: 0, width: k.height, height: b.chartHeight });g[h] ? g[h].animate(k) : (g[h] = c.clipRect(k), q && a["zone-graph-" + h].clip(g[h]), n && a["zone-area-" + h].clip(g[h]));K = e.value > r.max;
        }), this.clips = g);
      }, invertGroups: function invertGroups(a) {
        function b() {
          t(["group", "markerGroup"], function (b) {
            c[b] && (e.renderer.isVML && c[b].attr({ width: c.yAxis.len, height: c.xAxis.len }), c[b].width = c.yAxis.len, c[b].height = c.xAxis.len, c[b].invert(a));
          });
        }var c = this,
            e = c.chart,
            d;c.xAxis && (d = C(e, "resize", b), C(c, "destroy", d), b(a), c.invertGroups = b);
      }, plotGroup: function plotGroup(a, b, c, e, d) {
        var h = this[a],
            f = !h;f && (this[a] = h = this.chart.renderer.g().attr({ zIndex: e || .1 }).add(d));h.addClass("highcharts-" + b + " highcharts-series-" + this.index + " highcharts-" + this.type + "-series highcharts-color-" + this.colorIndex + " " + (this.options.className || ""), !0);h.attr({ visibility: c })[f ? "attr" : "animate"](this.getPlotBox());return h;
      }, getPlotBox: function getPlotBox() {
        var a = this.chart,
            b = this.xAxis,
            c = this.yAxis;a.inverted && (b = c, c = this.xAxis);return { translateX: b ? b.left : a.plotLeft, translateY: c ? c.top : a.plotTop, scaleX: 1, scaleY: 1 };
      }, render: function render() {
        var a = this,
            b = a.chart,
            c,
            e = a.options,
            d = !!a.animate && b.renderer.isSVG && A(e.animation).duration,
            f = a.visible ? "inherit" : "hidden",
            g = e.zIndex,
            k = a.hasRendered,
            q = b.seriesGroup,
            n = b.inverted;c = a.plotGroup("group", "series", f, g, q);a.markerGroup = a.plotGroup("markerGroup", "markers", f, g, q);d && a.animate(!0);c.inverted = a.isCartesian ? n : !1;a.drawGraph && (a.drawGraph(), a.applyZones());a.drawDataLabels && a.drawDataLabels();a.visible && a.drawPoints();a.drawTracker && !1 !== a.options.enableMouseTracking && a.drawTracker();a.invertGroups(n);!1 === e.clip || a.sharedClipKey || k || c.clip(b.clipRect);d && a.animate();k || (a.animationTimeout = B(function () {
          a.afterAnimate();
        }, d));a.isDirty = !1;a.hasRendered = !0;
      }, redraw: function redraw() {
        var a = this.chart,
            b = this.isDirty || this.isDirtyData,
            c = this.group,
            e = this.xAxis,
            d = this.yAxis;c && (a.inverted && c.attr({ width: a.plotWidth, height: a.plotHeight }), c.animate({ translateX: J(e && e.left, a.plotLeft), translateY: J(d && d.top, a.plotTop) }));this.translate();this.render();b && delete this.kdTree;
      }, kdAxisArray: ["clientX", "plotY"], searchPoint: function searchPoint(a, b) {
        var c = this.xAxis,
            e = this.yAxis,
            d = this.chart.inverted;return this.searchKDTree({ clientX: d ? c.len - a.chartY + c.pos : a.chartX - c.pos, plotY: d ? e.len - a.chartX + e.pos : a.chartY - e.pos }, b);
      },
      buildKDTree: function buildKDTree() {
        function a(c, e, d) {
          var h, f;if (f = c && c.length) return h = b.kdAxisArray[e % d], c.sort(function (a, b) {
            return a[h] - b[h];
          }), f = Math.floor(f / 2), { point: c[f], left: a(c.slice(0, f), e + 1, d), right: a(c.slice(f + 1), e + 1, d) };
        }this.buildingKdTree = !0;var b = this,
            c = -1 < b.options.findNearestPointBy.indexOf("y") ? 2 : 1;delete b.kdTree;B(function () {
          b.kdTree = a(b.getValidPoints(null, !b.directTouch), c, c);b.buildingKdTree = !1;
        }, b.options.kdNow ? 0 : 1);
      }, searchKDTree: function searchKDTree(a, b) {
        function c(a, b, h, k) {
          var p = b.point,
              q = e.kdAxisArray[h % k],
              n,
              l,
              m = p;l = u(a[d]) && u(p[d]) ? Math.pow(a[d] - p[d], 2) : null;n = u(a[f]) && u(p[f]) ? Math.pow(a[f] - p[f], 2) : null;n = (l || 0) + (n || 0);p.dist = u(n) ? Math.sqrt(n) : Number.MAX_VALUE;p.distX = u(l) ? Math.sqrt(l) : Number.MAX_VALUE;q = a[q] - p[q];n = 0 > q ? "left" : "right";l = 0 > q ? "right" : "left";b[n] && (n = c(a, b[n], h + 1, k), m = n[g] < m[g] ? n : p);b[l] && Math.sqrt(q * q) < m[g] && (a = c(a, b[l], h + 1, k), m = a[g] < m[g] ? a : m);return m;
        }var e = this,
            d = this.kdAxisArray[0],
            f = this.kdAxisArray[1],
            g = b ? "distX" : "dist";b = -1 < e.options.findNearestPointBy.indexOf("y") ? 2 : 1;this.kdTree || this.buildingKdTree || this.buildKDTree();if (this.kdTree) return c(a, this.kdTree, b, b);
      } });
  })(M);(function (a) {
    var C = a.Axis,
        A = a.Chart,
        F = a.correctFloat,
        E = a.defined,
        m = a.destroyObjectProperties,
        f = a.each,
        l = a.format,
        r = a.objectEach,
        u = a.pick,
        t = a.Series;a.StackItem = function (a, d, f, b, e) {
      var g = a.chart.inverted;this.axis = a;this.isNegative = f;this.options = d;this.x = b;this.total = null;this.points = {};this.stack = e;this.rightCliff = this.leftCliff = 0;this.alignOptions = { align: d.align || (g ? f ? "left" : "right" : "center"), verticalAlign: d.verticalAlign || (g ? "middle" : f ? "bottom" : "top"), y: u(d.y, g ? 4 : f ? 14 : -6), x: u(d.x, g ? f ? -6 : 6 : 0) };this.textAlign = d.textAlign || (g ? f ? "right" : "left" : "center");
    };a.StackItem.prototype = { destroy: function destroy() {
        m(this, this.axis);
      }, render: function render(a) {
        var d = this.options,
            f = d.format,
            f = f ? l(f, this) : d.formatter.call(this);this.label ? this.label.attr({ text: f, visibility: "hidden" }) : this.label = this.axis.chart.renderer.text(f, null, null, d.useHTML).css(d.style).attr({ align: this.textAlign, rotation: d.rotation, visibility: "hidden" }).add(a);
      }, setOffset: function setOffset(a, d) {
        var f = this.axis,
            b = f.chart,
            e = f.translate(f.usePercentage ? 100 : this.total, 0, 0, 0, 1),
            f = f.translate(0),
            f = Math.abs(e - f);a = b.xAxis[0].translate(this.x) + a;e = this.getStackBox(b, this, a, e, d, f);if (d = this.label) d.align(this.alignOptions, null, e), e = d.alignAttr, d[!1 === this.options.crop || b.isInsidePlot(e.x, e.y) ? "show" : "hide"](!0);
      }, getStackBox: function getStackBox(a, d, f, b, e, l) {
        var g = d.axis.reversed,
            k = a.inverted;a = a.plotHeight;d = d.isNegative && !g || !d.isNegative && g;return { x: k ? d ? b : b - l : f, y: k ? a - f - e : d ? a - b - l : a - b, width: k ? l : e, height: k ? e : l };
      } };A.prototype.getStacks = function () {
      var a = this;f(a.yAxis, function (a) {
        a.stacks && a.hasVisibleSeries && (a.oldStacks = a.stacks);
      });f(a.series, function (d) {
        !d.options.stacking || !0 !== d.visible && !1 !== a.options.chart.ignoreHiddenSeries || (d.stackKey = d.type + u(d.options.stack, ""));
      });
    };C.prototype.buildStacks = function () {
      var a = this.series,
          d = u(this.options.reversedStacks, !0),
          f = a.length,
          b;if (!this.isXAxis) {
        this.usePercentage = !1;for (b = f; b--;) {
          a[d ? b : f - b - 1].setStackedPoints();
        }if (this.usePercentage) for (b = 0; b < f; b++) {
          a[b].setPercentStacks();
        }
      }
    };
    C.prototype.renderStackTotals = function () {
      var a = this.chart,
          d = a.renderer,
          f = this.stacks,
          b = this.stackTotalGroup;b || (this.stackTotalGroup = b = d.g("stack-labels").attr({ visibility: "visible", zIndex: 6 }).add());b.translate(a.plotLeft, a.plotTop);r(f, function (a) {
        r(a, function (a) {
          a.render(b);
        });
      });
    };C.prototype.resetStacks = function () {
      var a = this,
          d = a.stacks;a.isXAxis || r(d, function (d) {
        r(d, function (b, e) {
          b.touched < a.stacksTouched ? (b.destroy(), delete d[e]) : (b.total = null, b.cum = null);
        });
      });
    };C.prototype.cleanStacks = function () {
      var a;
      this.isXAxis || (this.oldStacks && (a = this.stacks = this.oldStacks), r(a, function (a) {
        r(a, function (a) {
          a.cum = a.total;
        });
      }));
    };t.prototype.setStackedPoints = function () {
      if (this.options.stacking && (!0 === this.visible || !1 === this.chart.options.chart.ignoreHiddenSeries)) {
        var f = this.processedXData,
            d = this.processedYData,
            k = [],
            b = d.length,
            e = this.options,
            l = e.threshold,
            m = e.startFromThreshold ? l : 0,
            n = e.stack,
            e = e.stacking,
            r = this.stackKey,
            t = "-" + r,
            c = this.negStacks,
            G = this.yAxis,
            q = G.stacks,
            B = G.oldStacks,
            K,
            p,
            z,
            I,
            A,
            h,
            w;G.stacksTouched += 1;for (A = 0; A < b; A++) {
          h = f[A], w = d[A], K = this.getStackIndicator(K, h, this.index), I = K.key, z = (p = c && w < (m ? 0 : l)) ? t : r, q[z] || (q[z] = {}), q[z][h] || (B[z] && B[z][h] ? (q[z][h] = B[z][h], q[z][h].total = null) : q[z][h] = new a.StackItem(G, G.options.stackLabels, p, h, n)), z = q[z][h], null !== w && (z.points[I] = z.points[this.index] = [u(z.cum, m)], E(z.cum) || (z.base = I), z.touched = G.stacksTouched, 0 < K.index && !1 === this.singleStacks && (z.points[I][0] = z.points[this.index + "," + h + ",0"][0])), "percent" === e ? (p = p ? r : t, c && q[p] && q[p][h] ? (p = q[p][h], z.total = p.total = Math.max(p.total, z.total) + Math.abs(w) || 0) : z.total = F(z.total + (Math.abs(w) || 0))) : z.total = F(z.total + (w || 0)), z.cum = u(z.cum, m) + (w || 0), null !== w && (z.points[I].push(z.cum), k[A] = z.cum);
        }"percent" === e && (G.usePercentage = !0);this.stackedYData = k;G.oldStacks = {};
      }
    };t.prototype.setPercentStacks = function () {
      var a = this,
          d = a.stackKey,
          k = a.yAxis.stacks,
          b = a.processedXData,
          e;f([d, "-" + d], function (d) {
        for (var f = b.length, g, l; f--;) {
          if (g = b[f], e = a.getStackIndicator(e, g, a.index, d), g = (l = k[d] && k[d][g]) && l.points[e.key]) l = l.total ? 100 / l.total : 0, g[0] = F(g[0] * l), g[1] = F(g[1] * l), a.stackedYData[f] = g[1];
        }
      });
    };t.prototype.getStackIndicator = function (a, d, f, b) {
      !E(a) || a.x !== d || b && a.key !== b ? a = { x: d, index: 0, key: b } : a.index++;a.key = [f, d, a.index].join();return a;
    };
  })(M);(function (a) {
    var C = a.addEvent,
        A = a.animate,
        F = a.Axis,
        E = a.createElement,
        m = a.css,
        f = a.defined,
        l = a.each,
        r = a.erase,
        u = a.extend,
        t = a.fireEvent,
        g = a.inArray,
        d = a.isNumber,
        k = a.isObject,
        b = a.isArray,
        e = a.merge,
        v = a.objectEach,
        y = a.pick,
        n = a.Point,
        D = a.Series,
        J = a.seriesTypes,
        c = a.setAnimation,
        G = a.splat;u(a.Chart.prototype, { addSeries: function addSeries(a, b, c) {
        var e,
            d = this;a && (b = y(b, !0), t(d, "addSeries", { options: a }, function () {
          e = d.initSeries(a);d.isDirtyLegend = !0;d.linkSeries();b && d.redraw(c);
        }));return e;
      }, addAxis: function addAxis(a, b, c, d) {
        var f = b ? "xAxis" : "yAxis",
            g = this.options;a = e(a, { index: this[f].length, isX: b });b = new F(this, a);g[f] = G(g[f] || {});g[f].push(a);y(c, !0) && this.redraw(d);return b;
      }, showLoading: function showLoading(a) {
        var b = this,
            c = b.options,
            e = b.loadingDiv,
            d = c.loading,
            f = function f() {
          e && m(e, { left: b.plotLeft + "px", top: b.plotTop + "px", width: b.plotWidth + "px", height: b.plotHeight + "px" });
        };e || (b.loadingDiv = e = E("div", { className: "highcharts-loading highcharts-loading-hidden" }, null, b.container), b.loadingSpan = E("span", { className: "highcharts-loading-inner" }, null, e), C(b, "redraw", f));e.className = "highcharts-loading";b.loadingSpan.innerHTML = a || c.lang.loading;m(e, u(d.style, { zIndex: 10 }));m(b.loadingSpan, d.labelStyle);b.loadingShown || (m(e, { opacity: 0, display: "" }), A(e, { opacity: d.style.opacity || .5 }, { duration: d.showDuration || 0 }));b.loadingShown = !0;f();
      }, hideLoading: function hideLoading() {
        var a = this.options,
            b = this.loadingDiv;b && (b.className = "highcharts-loading highcharts-loading-hidden", A(b, { opacity: 0 }, { duration: a.loading.hideDuration || 100, complete: function complete() {
            m(b, { display: "none" });
          } }));this.loadingShown = !1;
      }, propsRequireDirtyBox: "backgroundColor borderColor borderWidth margin marginTop marginRight marginBottom marginLeft spacing spacingTop spacingRight spacingBottom spacingLeft borderRadius plotBackgroundColor plotBackgroundImage plotBorderColor plotBorderWidth plotShadow shadow".split(" "),
      propsRequireUpdateSeries: "chart.inverted chart.polar chart.ignoreHiddenSeries chart.type colors plotOptions tooltip".split(" "), update: function update(a, b, c) {
        var k = this,
            n = { credits: "addCredits", title: "setTitle", subtitle: "setSubtitle" },
            q = a.chart,
            m,
            h,
            r = [];if (q) {
          e(!0, k.options.chart, q);"className" in q && k.setClassName(q.className);if ("inverted" in q || "polar" in q) k.propFromSeries(), m = !0;"alignTicks" in q && (m = !0);v(q, function (a, b) {
            -1 !== g("chart." + b, k.propsRequireUpdateSeries) && (h = !0);-1 !== g(b, k.propsRequireDirtyBox) && (k.isDirtyBox = !0);
          });"style" in q && k.renderer.setStyle(q.style);
        }a.colors && (this.options.colors = a.colors);a.plotOptions && e(!0, this.options.plotOptions, a.plotOptions);v(a, function (a, b) {
          if (k[b] && "function" === typeof k[b].update) k[b].update(a, !1);else if ("function" === typeof k[n[b]]) k[n[b]](a);"chart" !== b && -1 !== g(b, k.propsRequireUpdateSeries) && (h = !0);
        });l("xAxis yAxis zAxis series colorAxis pane".split(" "), function (b) {
          a[b] && (l(G(a[b]), function (a, e) {
            (e = f(a.id) && k.get(a.id) || k[b][e]) && e.coll === b && (e.update(a, !1), c && (e.touched = !0));if (!e && c) if ("series" === b) k.addSeries(a, !1).touched = !0;else if ("xAxis" === b || "yAxis" === b) k.addAxis(a, "xAxis" === b, !1).touched = !0;
          }), c && l(k[b], function (a) {
            a.touched ? delete a.touched : r.push(a);
          }));
        });l(r, function (a) {
          a.remove(!1);
        });m && l(k.axes, function (a) {
          a.update({}, !1);
        });h && l(k.series, function (a) {
          a.update({}, !1);
        });a.loading && e(!0, k.options.loading, a.loading);m = q && q.width;q = q && q.height;d(m) && m !== k.chartWidth || d(q) && q !== k.chartHeight ? k.setSize(m, q) : y(b, !0) && k.redraw();
      }, setSubtitle: function setSubtitle(a) {
        this.setTitle(void 0, a);
      } });u(n.prototype, { update: function update(a, b, c, e) {
        function d() {
          f.applyOptions(a);null === f.y && h && (f.graphic = h.destroy());k(a, !0) && (h && h.element && a && a.marker && void 0 !== a.marker.symbol && (f.graphic = h.destroy()), a && a.dataLabels && f.dataLabel && (f.dataLabel = f.dataLabel.destroy()));p = f.index;g.updateParallelArrays(f, p);q.data[p] = k(q.data[p], !0) || k(a, !0) ? f.options : a;g.isDirty = g.isDirtyData = !0;!g.fixedBox && g.hasCartesianSeries && (l.isDirtyBox = !0);"point" === q.legendType && (l.isDirtyLegend = !0);b && l.redraw(c);
        }var f = this,
            g = f.series,
            h = f.graphic,
            p,
            l = g.chart,
            q = g.options;b = y(b, !0);!1 === e ? d() : f.firePointEvent("update", { options: a }, d);
      }, remove: function remove(a, b) {
        this.series.removePoint(g(this, this.series.data), a, b);
      } });u(D.prototype, { addPoint: function addPoint(a, b, c, e) {
        var d = this.options,
            f = this.data,
            g = this.chart,
            h = this.xAxis,
            h = h && h.hasNames && h.names,
            k = d.data,
            p,
            l,
            q = this.xData,
            n,
            m;b = y(b, !0);p = { series: this };this.pointClass.prototype.applyOptions.apply(p, [a]);m = p.x;n = q.length;if (this.requireSorting && m < q[n - 1]) for (l = !0; n && q[n - 1] > m;) {
          n--;
        }this.updateParallelArrays(p, "splice", n, 0, 0);this.updateParallelArrays(p, n);h && p.name && (h[m] = p.name);k.splice(n, 0, a);l && (this.data.splice(n, 0, null), this.processData());"point" === d.legendType && this.generatePoints();c && (f[0] && f[0].remove ? f[0].remove(!1) : (f.shift(), this.updateParallelArrays(p, "shift"), k.shift()));this.isDirtyData = this.isDirty = !0;b && g.redraw(e);
      }, removePoint: function removePoint(a, b, e) {
        var d = this,
            f = d.data,
            g = f[a],
            k = d.points,
            h = d.chart,
            l = function l() {
          k && k.length === f.length && k.splice(a, 1);f.splice(a, 1);
          d.options.data.splice(a, 1);d.updateParallelArrays(g || { series: d }, "splice", a, 1);g && g.destroy();d.isDirty = !0;d.isDirtyData = !0;b && h.redraw();
        };c(e, h);b = y(b, !0);g ? g.firePointEvent("remove", null, l) : l();
      }, remove: function remove(a, b, c) {
        function e() {
          d.destroy();f.isDirtyLegend = f.isDirtyBox = !0;f.linkSeries();y(a, !0) && f.redraw(b);
        }var d = this,
            f = d.chart;!1 !== c ? t(d, "remove", null, e) : e();
      }, update: function update(a, b) {
        var c = this,
            d = c.chart,
            f = c.userOptions,
            g = c.oldType || c.type,
            k = a.type || f.type || d.options.chart.type,
            h = J[g].prototype,
            n,
            q = ["group", "markerGroup", "dataLabelsGroup", "navigatorSeries", "baseSeries"],
            m = c.finishedAnimating && { animation: !1 };if (Object.keys && "data" === Object.keys(a).toString()) return this.setData(a.data, b);if (k && k !== g || void 0 !== a.zIndex) q.length = 0;l(q, function (a) {
          q[a] = c[a];delete c[a];
        });a = e(f, m, { index: c.index, pointStart: c.xData[0] }, { data: c.options.data }, a);c.remove(!1, null, !1);for (n in h) {
          c[n] = void 0;
        }u(c, J[k || g].prototype);l(q, function (a) {
          c[a] = q[a];
        });c.init(d, a);c.oldType = g;d.linkSeries();y(b, !0) && d.redraw(!1);
      } });
    u(F.prototype, { update: function update(a, b) {
        var c = this.chart;a = c.options[this.coll][this.options.index] = e(this.userOptions, a);this.destroy(!0);this.init(c, u(a, { events: void 0 }));c.isDirtyBox = !0;y(b, !0) && c.redraw();
      }, remove: function remove(a) {
        for (var c = this.chart, e = this.coll, d = this.series, f = d.length; f--;) {
          d[f] && d[f].remove(!1);
        }r(c.axes, this);r(c[e], this);b(c.options[e]) ? c.options[e].splice(this.options.index, 1) : delete c.options[e];l(c[e], function (a, b) {
          a.options.index = b;
        });this.destroy();c.isDirtyBox = !0;y(a, !0) && c.redraw();
      },
      setTitle: function setTitle(a, b) {
        this.update({ title: a }, b);
      }, setCategories: function setCategories(a, b) {
        this.update({ categories: a }, b);
      } });
  })(M);(function (a) {
    var C = a.color,
        A = a.each,
        F = a.map,
        E = a.pick,
        m = a.Series,
        f = a.seriesType;f("area", "line", { softThreshold: !1, threshold: 0 }, { singleStacks: !1, getStackPoints: function getStackPoints(f) {
        var l = [],
            m = [],
            t = this.xAxis,
            g = this.yAxis,
            d = g.stacks[this.stackKey],
            k = {},
            b = this.index,
            e = g.series,
            v = e.length,
            y,
            n = E(g.options.reversedStacks, !0) ? 1 : -1,
            D;f = f || this.points;if (this.options.stacking) {
          for (D = 0; D < f.length; D++) {
            k[f[D].x] = f[D];
          }a.objectEach(d, function (a, b) {
            null !== a.total && m.push(b);
          });m.sort(function (a, b) {
            return a - b;
          });y = F(e, function () {
            return this.visible;
          });A(m, function (a, c) {
            var e = 0,
                f,
                r;if (k[a] && !k[a].isNull) l.push(k[a]), A([-1, 1], function (e) {
              var g = 1 === e ? "rightNull" : "leftNull",
                  l = 0,
                  q = d[m[c + e]];if (q) for (D = b; 0 <= D && D < v;) {
                f = q.points[D], f || (D === b ? k[a][g] = !0 : y[D] && (r = d[a].points[D]) && (l -= r[1] - r[0])), D += n;
              }k[a][1 === e ? "rightCliff" : "leftCliff"] = l;
            });else {
              for (D = b; 0 <= D && D < v;) {
                if (f = d[a].points[D]) {
                  e = f[1];break;
                }D += n;
              }e = g.translate(e, 0, 1, 0, 1);l.push({ isNull: !0, plotX: t.translate(a, 0, 0, 0, 1), x: a, plotY: e, yBottom: e });
            }
          });
        }return l;
      }, getGraphPath: function getGraphPath(a) {
        var f = m.prototype.getGraphPath,
            l = this.options,
            t = l.stacking,
            g = this.yAxis,
            d,
            k,
            b = [],
            e = [],
            v = this.index,
            y,
            n = g.stacks[this.stackKey],
            D = l.threshold,
            A = g.getThreshold(l.threshold),
            c,
            l = l.connectNulls || "percent" === t,
            G = function G(c, d, f) {
          var k = a[c];c = t && n[k.x].points[v];var l = k[f + "Null"] || 0;f = k[f + "Cliff"] || 0;var q,
              m,
              k = !0;f || l ? (q = (l ? c[0] : c[1]) + f, m = c[0] + f, k = !!l) : !t && a[d] && a[d].isNull && (q = m = D);void 0 !== q && (e.push({ plotX: y, plotY: null === q ? A : g.getThreshold(q), isNull: k, isCliff: !0 }), b.push({ plotX: y, plotY: null === m ? A : g.getThreshold(m), doCurve: !1 }));
        };a = a || this.points;t && (a = this.getStackPoints(a));for (d = 0; d < a.length; d++) {
          if (k = a[d].isNull, y = E(a[d].rectPlotX, a[d].plotX), c = E(a[d].yBottom, A), !k || l) l || G(d, d - 1, "left"), k && !t && l || (e.push(a[d]), b.push({ x: d, plotX: y, plotY: c })), l || G(d, d + 1, "right");
        }d = f.call(this, e, !0, !0);b.reversed = !0;k = f.call(this, b, !0, !0);k.length && (k[0] = "L");k = d.concat(k);f = f.call(this, e, !1, l);k.xMap = d.xMap;this.areaPath = k;return f;
      }, drawGraph: function drawGraph() {
        this.areaPath = [];m.prototype.drawGraph.apply(this);var a = this,
            f = this.areaPath,
            u = this.options,
            t = [["area", "highcharts-area", this.color, u.fillColor]];A(this.zones, function (f, d) {
          t.push(["zone-area-" + d, "highcharts-area highcharts-zone-area-" + d + " " + f.className, f.color || a.color, f.fillColor || u.fillColor]);
        });A(t, function (g) {
          var d = g[0],
              k = a[d];k ? (k.endX = f.xMap, k.animate({ d: f })) : (k = a[d] = a.chart.renderer.path(f).addClass(g[1]).attr({ fill: E(g[3], C(g[2]).setOpacity(E(u.fillOpacity, .75)).get()), zIndex: 0 }).add(a.group), k.isArea = !0);k.startX = f.xMap;k.shiftUnit = u.step ? 2 : 1;
        });
      }, drawLegendSymbol: a.LegendSymbolMixin.drawRectangle });
  })(M);(function (a) {
    var C = a.pick;a = a.seriesType;a("spline", "line", {}, { getPointSpline: function getPointSpline(a, F, E) {
        var m = F.plotX,
            f = F.plotY,
            l = a[E - 1];E = a[E + 1];var r, u, t, g;if (l && !l.isNull && !1 !== l.doCurve && !F.isCliff && E && !E.isNull && !1 !== E.doCurve && !F.isCliff) {
          a = l.plotY;t = E.plotX;E = E.plotY;var d = 0;r = (1.5 * m + l.plotX) / 2.5;u = (1.5 * f + a) / 2.5;t = (1.5 * m + t) / 2.5;g = (1.5 * f + E) / 2.5;t !== r && (d = (g - u) * (t - m) / (t - r) + f - g);u += d;g += d;u > a && u > f ? (u = Math.max(a, f), g = 2 * f - u) : u < a && u < f && (u = Math.min(a, f), g = 2 * f - u);g > E && g > f ? (g = Math.max(E, f), u = 2 * f - g) : g < E && g < f && (g = Math.min(E, f), u = 2 * f - g);F.rightContX = t;F.rightContY = g;
        }F = ["C", C(l.rightContX, l.plotX), C(l.rightContY, l.plotY), C(r, m), C(u, f), m, f];l.rightContX = l.rightContY = null;return F;
      } });
  })(M);(function (a) {
    var C = a.seriesTypes.area.prototype,
        A = a.seriesType;A("areaspline", "spline", a.defaultPlotOptions.area, { getStackPoints: C.getStackPoints, getGraphPath: C.getGraphPath,
      drawGraph: C.drawGraph, drawLegendSymbol: a.LegendSymbolMixin.drawRectangle });
  })(M);(function (a) {
    var C = a.animObject,
        A = a.color,
        F = a.each,
        E = a.extend,
        m = a.isNumber,
        f = a.merge,
        l = a.pick,
        r = a.Series,
        u = a.seriesType,
        t = a.svg;u("column", "line", { borderRadius: 0, crisp: !0, groupPadding: .2, marker: null, pointPadding: .1, minPointLength: 0, cropThreshold: 50, pointRange: null, states: { hover: { halo: !1, brightness: .1, shadow: !1 }, select: { color: "#cccccc", borderColor: "#000000", shadow: !1 } }, dataLabels: { align: null, verticalAlign: null, y: null },
      softThreshold: !1, startFromThreshold: !0, stickyTracking: !1, tooltip: { distance: 6 }, threshold: 0, borderColor: "#ffffff" }, { cropShoulder: 0, directTouch: !0, trackerGroups: ["group", "dataLabelsGroup"], negStacks: !0, init: function init() {
        r.prototype.init.apply(this, arguments);var a = this,
            d = a.chart;d.hasRendered && F(d.series, function (d) {
          d.type === a.type && (d.isDirty = !0);
        });
      }, getColumnMetrics: function getColumnMetrics() {
        var a = this,
            d = a.options,
            f = a.xAxis,
            b = a.yAxis,
            e = f.reversed,
            m,
            r = {},
            n = 0;!1 === d.grouping ? n = 1 : F(a.chart.series, function (c) {
          var e = c.options,
              d = c.yAxis,
              f;c.type !== a.type || !c.visible && a.chart.options.chart.ignoreHiddenSeries || b.len !== d.len || b.pos !== d.pos || (e.stacking ? (m = c.stackKey, void 0 === r[m] && (r[m] = n++), f = r[m]) : !1 !== e.grouping && (f = n++), c.columnIndex = f);
        });var t = Math.min(Math.abs(f.transA) * (f.ordinalSlope || d.pointRange || f.closestPointRange || f.tickInterval || 1), f.len),
            u = t * d.groupPadding,
            c = (t - 2 * u) / (n || 1),
            d = Math.min(d.maxPointWidth || f.len, l(d.pointWidth, c * (1 - 2 * d.pointPadding)));a.columnMetrics = { width: d, offset: (c - d) / 2 + (u + ((a.columnIndex || 0) + (e ? 1 : 0)) * c - t / 2) * (e ? -1 : 1) };return a.columnMetrics;
      }, crispCol: function crispCol(a, d, f, b) {
        var e = this.chart,
            g = this.borderWidth,
            k = -(g % 2 ? .5 : 0),
            g = g % 2 ? .5 : 1;e.inverted && e.renderer.isVML && (g += 1);this.options.crisp && (f = Math.round(a + f) + k, a = Math.round(a) + k, f -= a);b = Math.round(d + b) + g;k = .5 >= Math.abs(d) && .5 < b;d = Math.round(d) + g;b -= d;k && b && (--d, b += 1);return { x: a, y: d, width: f, height: b };
      }, translate: function translate() {
        var a = this,
            d = a.chart,
            f = a.options,
            b = a.dense = 2 > a.closestPointRange * a.xAxis.transA,
            b = a.borderWidth = l(f.borderWidth, b ? 0 : 1),
            e = a.yAxis,
            m = a.translatedThreshold = e.getThreshold(f.threshold),
            t = l(f.minPointLength, 5),
            n = a.getColumnMetrics(),
            u = n.width,
            A = a.barW = Math.max(u, 1 + 2 * b),
            c = a.pointXOffset = n.offset;d.inverted && (m -= .5);f.pointPadding && (A = Math.ceil(A));r.prototype.translate.apply(a);F(a.points, function (b) {
          var f = l(b.yBottom, m),
              g = 999 + Math.abs(f),
              g = Math.min(Math.max(-g, b.plotY), e.len + g),
              k = b.plotX + c,
              n = A,
              r = Math.min(g, f),
              v,
              y = Math.max(g, f) - r;Math.abs(y) < t && t && (y = t, v = !e.reversed && !b.negative || e.reversed && b.negative, r = Math.abs(r - m) > t ? f - t : m - (v ? t : 0));b.barX = k;b.pointWidth = u;b.tooltipPos = d.inverted ? [e.len + e.pos - d.plotLeft - g, a.xAxis.len - k - n / 2, y] : [k + n / 2, g + e.pos - d.plotTop, y];b.shapeType = "rect";b.shapeArgs = a.crispCol.apply(a, b.isNull ? [k, m, n, 0] : [k, r, n, y]);
        });
      }, getSymbol: a.noop, drawLegendSymbol: a.LegendSymbolMixin.drawRectangle, drawGraph: function drawGraph() {
        this.group[this.dense ? "addClass" : "removeClass"]("highcharts-dense-data");
      }, pointAttribs: function pointAttribs(a, d) {
        var g = this.options,
            b,
            e = this.pointAttrToOptions || {};b = e.stroke || "borderColor";var l = e["stroke-width"] || "borderWidth",
            m = a && a.color || this.color,
            n = a[b] || g[b] || this.color || m,
            r = a[l] || g[l] || this[l] || 0,
            e = g.dashStyle;a && this.zones.length && (m = a.getZone(), m = a.options.color || m && m.color || this.color);d && (a = f(g.states[d], a.options.states && a.options.states[d] || {}), d = a.brightness, m = a.color || void 0 !== d && A(m).brighten(a.brightness).get() || m, n = a[b] || n, r = a[l] || r, e = a.dashStyle || e);b = { fill: m, stroke: n, "stroke-width": r };e && (b.dashstyle = e);return b;
      }, drawPoints: function drawPoints() {
        var a = this,
            d = this.chart,
            k = a.options,
            b = d.renderer,
            e = k.animationLimit || 250,
            l;F(a.points, function (g) {
          var n = g.graphic;if (m(g.plotY) && null !== g.y) {
            l = g.shapeArgs;if (n) n[d.pointCount < e ? "animate" : "attr"](f(l));else g.graphic = n = b[g.shapeType](l).add(g.group || a.group);k.borderRadius && n.attr({ r: k.borderRadius });n.attr(a.pointAttribs(g, g.selected && "select")).shadow(k.shadow, null, k.stacking && !k.borderRadius);n.addClass(g.getClassName(), !0);
          } else n && (g.graphic = n.destroy());
        });
      }, animate: function animate(a) {
        var d = this,
            f = this.yAxis,
            b = d.options,
            e = this.chart.inverted,
            g = {};t && (a ? (g.scaleY = .001, a = Math.min(f.pos + f.len, Math.max(f.pos, f.toPixels(b.threshold))), e ? g.translateX = a - f.len : g.translateY = a, d.group.attr(g)) : (g[e ? "translateX" : "translateY"] = f.pos, d.group.animate(g, E(C(d.options.animation), { step: function step(a, b) {
            d.group.attr({ scaleY: Math.max(.001, b.pos) });
          } })), d.animate = null));
      }, remove: function remove() {
        var a = this,
            d = a.chart;d.hasRendered && F(d.series, function (d) {
          d.type === a.type && (d.isDirty = !0);
        });r.prototype.remove.apply(a, arguments);
      } });
  })(M);(function (a) {
    a = a.seriesType;a("bar", "column", null, { inverted: !0 });
  })(M);(function (a) {
    var C = a.Series;a = a.seriesType;a("scatter", "line", { lineWidth: 0, findNearestPointBy: "xy", marker: { enabled: !0 }, tooltip: { headerFormat: "<span style=\"color:{point.color}\">\u25CF</span> <span style=\"font-size: 0.85em\"> {series.name}</span><br/>", pointFormat: "x: \x3cb\x3e{point.x}\x3c/b\x3e\x3cbr/\x3ey: \x3cb\x3e{point.y}\x3c/b\x3e\x3cbr/\x3e" } }, { sorted: !1, requireSorting: !1, noSharedTooltip: !0, trackerGroups: ["group", "markerGroup", "dataLabelsGroup"],
      takeOrdinalPosition: !1, drawGraph: function drawGraph() {
        this.options.lineWidth && C.prototype.drawGraph.call(this);
      } });
  })(M);(function (a) {
    var C = a.pick,
        A = a.relativeLength;a.CenteredSeriesMixin = { getCenter: function getCenter() {
        var a = this.options,
            E = this.chart,
            m = 2 * (a.slicedOffset || 0),
            f = E.plotWidth - 2 * m,
            E = E.plotHeight - 2 * m,
            l = a.center,
            l = [C(l[0], "50%"), C(l[1], "50%"), a.size || "100%", a.innerSize || 0],
            r = Math.min(f, E),
            u,
            t;for (u = 0; 4 > u; ++u) {
          t = l[u], a = 2 > u || 2 === u && /%$/.test(t), l[u] = A(t, [f, E, r, l[2]][u]) + (a ? m : 0);
        }l[3] > l[2] && (l[3] = l[2]);return l;
      } };
  })(M);
  (function (a) {
    var C = a.addEvent,
        A = a.defined,
        F = a.each,
        E = a.extend,
        m = a.inArray,
        f = a.noop,
        l = a.pick,
        r = a.Point,
        u = a.Series,
        t = a.seriesType,
        g = a.setAnimation;t("pie", "line", { center: [null, null], clip: !1, colorByPoint: !0, dataLabels: { distance: 30, enabled: !0, formatter: function formatter() {
          return this.point.isNull ? void 0 : this.point.name;
        }, x: 0 }, ignoreHiddenPoint: !0, legendType: "point", marker: null, size: null, showInLegend: !1, slicedOffset: 10, stickyTracking: !1, tooltip: { followPointer: !0 }, borderColor: "#ffffff", borderWidth: 1, states: { hover: { brightness: .1,
          shadow: !1 } } }, { isCartesian: !1, requireSorting: !1, directTouch: !0, noSharedTooltip: !0, trackerGroups: ["group", "dataLabelsGroup"], axisTypes: [], pointAttribs: a.seriesTypes.column.prototype.pointAttribs, animate: function animate(a) {
        var d = this,
            b = d.points,
            e = d.startAngleRad;a || (F(b, function (a) {
          var b = a.graphic,
              f = a.shapeArgs;b && (b.attr({ r: a.startR || d.center[3] / 2, start: e, end: e }), b.animate({ r: f.r, start: f.start, end: f.end }, d.options.animation));
        }), d.animate = null);
      }, updateTotals: function updateTotals() {
        var a,
            f = 0,
            b = this.points,
            e = b.length,
            g,
            l = this.options.ignoreHiddenPoint;for (a = 0; a < e; a++) {
          g = b[a], f += l && !g.visible ? 0 : g.isNull ? 0 : g.y;
        }this.total = f;for (a = 0; a < e; a++) {
          g = b[a], g.percentage = 0 < f && (g.visible || !l) ? g.y / f * 100 : 0, g.total = f;
        }
      }, generatePoints: function generatePoints() {
        u.prototype.generatePoints.call(this);this.updateTotals();
      }, translate: function translate(a) {
        this.generatePoints();var d = 0,
            b = this.options,
            e = b.slicedOffset,
            f = e + (b.borderWidth || 0),
            g,
            n,
            m,
            r = b.startAngle || 0,
            c = this.startAngleRad = Math.PI / 180 * (r - 90),
            r = (this.endAngleRad = Math.PI / 180 * (l(b.endAngle, r + 360) - 90)) - c,
            t = this.points,
            q,
            B = b.dataLabels.distance,
            b = b.ignoreHiddenPoint,
            u,
            p = t.length,
            z;a || (this.center = a = this.getCenter());this.getX = function (b, c, e) {
          m = Math.asin(Math.min((b - a[1]) / (a[2] / 2 + e.labelDistance), 1));return a[0] + (c ? -1 : 1) * Math.cos(m) * (a[2] / 2 + e.labelDistance);
        };for (u = 0; u < p; u++) {
          z = t[u];z.labelDistance = l(z.options.dataLabels && z.options.dataLabels.distance, B);this.maxLabelDistance = Math.max(this.maxLabelDistance || 0, z.labelDistance);g = c + d * r;if (!b || z.visible) d += z.percentage / 100;n = c + d * r;z.shapeType = "arc";z.shapeArgs = { x: a[0], y: a[1], r: a[2] / 2, innerR: a[3] / 2, start: Math.round(1E3 * g) / 1E3, end: Math.round(1E3 * n) / 1E3 };m = (n + g) / 2;m > 1.5 * Math.PI ? m -= 2 * Math.PI : m < -Math.PI / 2 && (m += 2 * Math.PI);z.slicedTranslation = { translateX: Math.round(Math.cos(m) * e), translateY: Math.round(Math.sin(m) * e) };n = Math.cos(m) * a[2] / 2;q = Math.sin(m) * a[2] / 2;z.tooltipPos = [a[0] + .7 * n, a[1] + .7 * q];z.half = m < -Math.PI / 2 || m > Math.PI / 2 ? 1 : 0;z.angle = m;g = Math.min(f, z.labelDistance / 5);z.labelPos = [a[0] + n + Math.cos(m) * z.labelDistance, a[1] + q + Math.sin(m) * z.labelDistance, a[0] + n + Math.cos(m) * g, a[1] + q + Math.sin(m) * g, a[0] + n, a[1] + q, 0 > z.labelDistance ? "center" : z.half ? "right" : "left", m];
        }
      }, drawGraph: null, drawPoints: function drawPoints() {
        var a = this,
            f = a.chart.renderer,
            b,
            e,
            g,
            l,
            n = a.options.shadow;n && !a.shadowGroup && (a.shadowGroup = f.g("shadow").add(a.group));F(a.points, function (d) {
          if (!d.isNull) {
            e = d.graphic;l = d.shapeArgs;b = d.getTranslate();var k = d.shadowGroup;n && !k && (k = d.shadowGroup = f.g("shadow").add(a.shadowGroup));k && k.attr(b);g = a.pointAttribs(d, d.selected && "select");e ? e.setRadialReference(a.center).attr(g).animate(E(l, b)) : (d.graphic = e = f[d.shapeType](l).setRadialReference(a.center).attr(b).add(a.group), d.visible || e.attr({ visibility: "hidden" }), e.attr(g).attr({ "stroke-linejoin": "round" }).shadow(n, k));e.addClass(d.getClassName());
          }
        });
      }, searchPoint: f, sortByAngle: function sortByAngle(a, f) {
        a.sort(function (a, e) {
          return void 0 !== a.angle && (e.angle - a.angle) * f;
        });
      }, drawLegendSymbol: a.LegendSymbolMixin.drawRectangle, getCenter: a.CenteredSeriesMixin.getCenter, getSymbol: f }, { init: function init() {
        r.prototype.init.apply(this, arguments);var a = this,
            f;a.name = l(a.name, "Slice");f = function f(b) {
          a.slice("select" === b.type);
        };C(a, "select", f);C(a, "unselect", f);return a;
      }, isValid: function isValid() {
        return a.isNumber(this.y, !0) && 0 <= this.y;
      }, setVisible: function setVisible(a, f) {
        var b = this,
            e = b.series,
            d = e.chart,
            g = e.options.ignoreHiddenPoint;f = l(f, g);a !== b.visible && (b.visible = b.options.visible = a = void 0 === a ? !b.visible : a, e.options.data[m(b, e.data)] = b.options, F(["graphic", "dataLabel", "connector", "shadowGroup"], function (e) {
          if (b[e]) b[e][a ? "show" : "hide"](!0);
        }), b.legendItem && d.legend.colorizeItem(b, a), a || "hover" !== b.state || b.setState(""), g && (e.isDirty = !0), f && d.redraw());
      }, slice: function slice(a, f, b) {
        var e = this.series;g(b, e.chart);l(f, !0);this.sliced = this.options.sliced = A(a) ? a : !this.sliced;e.options.data[m(this, e.data)] = this.options;this.graphic.animate(this.getTranslate());this.shadowGroup && this.shadowGroup.animate(this.getTranslate());
      }, getTranslate: function getTranslate() {
        return this.sliced ? this.slicedTranslation : { translateX: 0, translateY: 0 };
      }, haloPath: function haloPath(a) {
        var d = this.shapeArgs;return this.sliced || !this.visible ? [] : this.series.chart.renderer.symbols.arc(d.x, d.y, d.r + a, d.r + a, { innerR: this.shapeArgs.r, start: d.start, end: d.end });
      } });
  })(M);(function (a) {
    var C = a.addEvent,
        A = a.arrayMax,
        F = a.defined,
        E = a.each,
        m = a.extend,
        f = a.format,
        l = a.map,
        r = a.merge,
        u = a.noop,
        t = a.pick,
        g = a.relativeLength,
        d = a.Series,
        k = a.seriesTypes,
        b = a.stableSort;a.distribute = function (a, d) {
      function e(a, b) {
        return a.target - b.target;
      }var f,
          g = !0,
          k = a,
          c = [],
          m;m = 0;for (f = a.length; f--;) {
        m += a[f].size;
      }if (m > d) {
        b(a, function (a, b) {
          return (b.rank || 0) - (a.rank || 0);
        });for (m = f = 0; m <= d;) {
          m += a[f].size, f++;
        }c = a.splice(f - 1, a.length);
      }b(a, e);for (a = l(a, function (a) {
        return { size: a.size, targets: [a.target] };
      }); g;) {
        for (f = a.length; f--;) {
          g = a[f], m = (Math.min.apply(0, g.targets) + Math.max.apply(0, g.targets)) / 2, g.pos = Math.min(Math.max(0, m - g.size / 2), d - g.size);
        }f = a.length;for (g = !1; f--;) {
          0 < f && a[f - 1].pos + a[f - 1].size > a[f].pos && (a[f - 1].size += a[f].size, a[f - 1].targets = a[f - 1].targets.concat(a[f].targets), a[f - 1].pos + a[f - 1].size > d && (a[f - 1].pos = d - a[f - 1].size), a.splice(f, 1), g = !0);
        }
      }f = 0;E(a, function (a) {
        var b = 0;E(a.targets, function () {
          k[f].pos = a.pos + b;b += k[f].size;f++;
        });
      });k.push.apply(k, c);b(k, e);
    };d.prototype.drawDataLabels = function () {
      var b = this,
          d = b.options,
          g = d.dataLabels,
          k = b.points,
          l,
          m,
          c = b.hasRendered || 0,
          u,
          q,
          B = t(g.defer, !!d.animation),
          A = b.chart.renderer;if (g.enabled || b._hasPointLabels) b.dlProcessOptions && b.dlProcessOptions(g), q = b.plotGroup("dataLabelsGroup", "data-labels", B && !c ? "hidden" : "visible", g.zIndex || 6), B && (q.attr({ opacity: +c }), c || C(b, "afterAnimate", function () {
        b.visible && q.show(!0);q[d.animation ? "animate" : "attr"]({ opacity: 1 }, { duration: 200 });
      })), m = g, E(k, function (c) {
        var e,
            k = c.dataLabel,
            n,
            h,
            p = c.connector,
            v = !k,
            B;l = c.dlOptions || c.options && c.options.dataLabels;if (e = t(l && l.enabled, m.enabled) && null !== c.y) g = r(m, l), n = c.getLabelConfig(), u = g.format ? f(g.format, n) : g.formatter.call(n, g), B = g.style, n = g.rotation, B.color = t(g.color, B.color, b.color, "#000000"), "contrast" === B.color && (c.contrastColor = A.getContrast(c.color || b.color), B.color = g.inside || 0 > t(c.labelDistance, g.distance) || d.stacking ? c.contrastColor : "#000000"), d.cursor && (B.cursor = d.cursor), h = { fill: g.backgroundColor, stroke: g.borderColor, "stroke-width": g.borderWidth, r: g.borderRadius || 0, rotation: n, padding: g.padding, zIndex: 1 }, a.objectEach(h, function (a, b) {
          void 0 === a && delete h[b];
        });!k || e && F(u) ? e && F(u) && (k ? h.text = u : (k = c.dataLabel = A[n ? "text" : "label"](u, 0, -9999, g.shape, null, null, g.useHTML, null, "data-label"), k.addClass("highcharts-data-label-color-" + c.colorIndex + " " + (g.className || "") + (g.useHTML ? "highcharts-tracker" : ""))), k.attr(h), k.css(B).shadow(g.shadow), k.added || k.add(q), b.alignDataLabel(c, k, g, null, v)) : (c.dataLabel = k = k.destroy(), p && (c.connector = p.destroy()));
      });
    };d.prototype.alignDataLabel = function (a, b, d, f, g) {
      var e = this.chart,
          c = e.inverted,
          k = t(a.plotX, -9999),
          l = t(a.plotY, -9999),
          n = b.getBBox(),
          r,
          p = d.rotation,
          v = d.align,
          u = this.visible && (a.series.forceDL || e.isInsidePlot(k, Math.round(l), c) || f && e.isInsidePlot(k, c ? f.x + 1 : f.y + f.height - 1, c)),
          y = "justify" === t(d.overflow, "justify");if (u && (r = d.style.fontSize, r = e.renderer.fontMetrics(r, b).b, f = m({ x: c ? this.yAxis.len - l : k, y: Math.round(c ? this.xAxis.len - k : l),
        width: 0, height: 0 }, f), m(d, { width: n.width, height: n.height }), p ? (y = !1, k = e.renderer.rotCorr(r, p), k = { x: f.x + d.x + f.width / 2 + k.x, y: f.y + d.y + { top: 0, middle: .5, bottom: 1 }[d.verticalAlign] * f.height }, b[g ? "attr" : "animate"](k).attr({ align: v }), l = (p + 720) % 360, l = 180 < l && 360 > l, "left" === v ? k.y -= l ? n.height : 0 : "center" === v ? (k.x -= n.width / 2, k.y -= n.height / 2) : "right" === v && (k.x -= n.width, k.y -= l ? 0 : n.height)) : (b.align(d, null, f), k = b.alignAttr), y ? a.isLabelJustified = this.justifyDataLabel(b, d, k, n, f, g) : t(d.crop, !0) && (u = e.isInsidePlot(k.x, k.y) && e.isInsidePlot(k.x + n.width, k.y + n.height)), d.shape && !p)) b[g ? "attr" : "animate"]({ anchorX: c ? e.plotWidth - a.plotY : a.plotX, anchorY: c ? e.plotHeight - a.plotX : a.plotY });u || (b.attr({ y: -9999 }), b.placed = !1);
    };d.prototype.justifyDataLabel = function (a, b, d, f, g, k) {
      var c = this.chart,
          e = b.align,
          l = b.verticalAlign,
          m,
          n,
          p = a.box ? 0 : a.padding || 0;m = d.x + p;0 > m && ("right" === e ? b.align = "left" : b.x = -m, n = !0);m = d.x + f.width - p;m > c.plotWidth && ("left" === e ? b.align = "right" : b.x = c.plotWidth - m, n = !0);m = d.y + p;0 > m && ("bottom" === l ? b.verticalAlign = "top" : b.y = -m, n = !0);m = d.y + f.height - p;m > c.plotHeight && ("top" === l ? b.verticalAlign = "bottom" : b.y = c.plotHeight - m, n = !0);n && (a.placed = !k, a.align(b, null, g));return n;
    };k.pie && (k.pie.prototype.drawDataLabels = function () {
      var b = this,
          f = b.data,
          g,
          k = b.chart,
          l = b.options.dataLabels,
          m = t(l.connectorPadding, 10),
          c = t(l.connectorWidth, 1),
          r = k.plotWidth,
          q = k.plotHeight,
          u,
          C = b.center,
          p = C[2] / 2,
          z = C[1],
          I,
          L,
          h,
          w,
          M = [[], []],
          H,
          O,
          Q,
          R,
          x = [0, 0, 0, 0];b.visible && (l.enabled || b._hasPointLabels) && (E(f, function (a) {
        a.dataLabel && a.visible && a.dataLabel.shortened && (a.dataLabel.attr({ width: "auto" }).css({ width: "auto", textOverflow: "clip" }), a.dataLabel.shortened = !1);
      }), d.prototype.drawDataLabels.apply(b), E(f, function (a) {
        a.dataLabel && a.visible && (M[a.half].push(a), a.dataLabel._pos = null);
      }), E(M, function (c, d) {
        var e,
            f,
            n = c.length,
            v = [],
            u;if (n) for (b.sortByAngle(c, d - .5), 0 < b.maxLabelDistance && (e = Math.max(0, z - p - b.maxLabelDistance), f = Math.min(z + p + b.maxLabelDistance, k.plotHeight), E(c, function (a) {
          0 < a.labelDistance && a.dataLabel && (a.top = Math.max(0, z - p - a.labelDistance), a.bottom = Math.min(z + p + a.labelDistance, k.plotHeight), u = a.dataLabel.getBBox().height || 21, a.positionsIndex = v.push({ target: a.labelPos[1] - a.top + u / 2, size: u, rank: a.y }) - 1);
        }), a.distribute(v, f + u - e)), R = 0; R < n; R++) {
          g = c[R], f = g.positionsIndex, h = g.labelPos, I = g.dataLabel, Q = !1 === g.visible ? "hidden" : "inherit", e = h[1], v && F(v[f]) ? void 0 === v[f].pos ? Q = "hidden" : (w = v[f].size, O = g.top + v[f].pos) : O = e, delete g.positionIndex, H = l.justify ? C[0] + (d ? -1 : 1) * (p + g.labelDistance) : b.getX(O < g.top + 2 || O > g.bottom - 2 ? e : O, d, g), I._attr = { visibility: Q, align: h[6] }, I._pos = { x: H + l.x + ({ left: m, right: -m }[h[6]] || 0), y: O + l.y - 10 }, h.x = H, h.y = O, t(l.crop, !0) && (L = I.getBBox().width, e = null, H - L < m ? (e = Math.round(L - H + m), x[3] = Math.max(e, x[3])) : H + L > r - m && (e = Math.round(H + L - r + m), x[1] = Math.max(e, x[1])), 0 > O - w / 2 ? x[0] = Math.max(Math.round(-O + w / 2), x[0]) : O + w / 2 > q && (x[2] = Math.max(Math.round(O + w / 2 - q), x[2])), I.sideOverflow = e);
        }
      }), 0 === A(x) || this.verifyDataLabelOverflow(x)) && (this.placeDataLabels(), c && E(this.points, function (a) {
        var e;u = a.connector;if ((I = a.dataLabel) && I._pos && a.visible && 0 < a.labelDistance) {
          Q = I._attr.visibility;if (e = !u) a.connector = u = k.renderer.path().addClass("highcharts-data-label-connector highcharts-color-" + a.colorIndex).add(b.dataLabelsGroup), u.attr({ "stroke-width": c, stroke: l.connectorColor || a.color || "#666666" });u[e ? "attr" : "animate"]({ d: b.connectorPath(a.labelPos) });u.attr("visibility", Q);
        } else u && (a.connector = u.destroy());
      }));
    }, k.pie.prototype.connectorPath = function (a) {
      var b = a.x,
          d = a.y;return t(this.options.dataLabels.softConnector, !0) ? ["M", b + ("left" === a[6] ? 5 : -5), d, "C", b, d, 2 * a[2] - a[4], 2 * a[3] - a[5], a[2], a[3], "L", a[4], a[5]] : ["M", b + ("left" === a[6] ? 5 : -5), d, "L", a[2], a[3], "L", a[4], a[5]];
    }, k.pie.prototype.placeDataLabels = function () {
      E(this.points, function (a) {
        var b = a.dataLabel;b && a.visible && ((a = b._pos) ? (b.sideOverflow && (b._attr.width = b.getBBox().width - b.sideOverflow, b.css({ width: b._attr.width + "px", textOverflow: "ellipsis" }), b.shortened = !0), b.attr(b._attr), b[b.moved ? "animate" : "attr"](a), b.moved = !0) : b && b.attr({ y: -9999 }));
      }, this);
    }, k.pie.prototype.alignDataLabel = u, k.pie.prototype.verifyDataLabelOverflow = function (a) {
      var b = this.center,
          d = this.options,
          e = d.center,
          f = d.minSize || 80,
          k,
          c = null !== d.size;c || (null !== e[0] ? k = Math.max(b[2] - Math.max(a[1], a[3]), f) : (k = Math.max(b[2] - a[1] - a[3], f), b[0] += (a[3] - a[1]) / 2), null !== e[1] ? k = Math.max(Math.min(k, b[2] - Math.max(a[0], a[2])), f) : (k = Math.max(Math.min(k, b[2] - a[0] - a[2]), f), b[1] += (a[0] - a[2]) / 2), k < b[2] ? (b[2] = k, b[3] = Math.min(g(d.innerSize || 0, k), k), this.translate(b), this.drawDataLabels && this.drawDataLabels()) : c = !0);return c;
    });k.column && (k.column.prototype.alignDataLabel = function (a, b, f, g, k) {
      var e = this.chart.inverted,
          c = a.series,
          l = a.dlBox || a.shapeArgs,
          m = t(a.below, a.plotY > t(this.translatedThreshold, c.yAxis.len)),
          n = t(f.inside, !!this.options.stacking);l && (g = r(l), 0 > g.y && (g.height += g.y, g.y = 0), l = g.y + g.height - c.yAxis.len, 0 < l && (g.height -= l), e && (g = { x: c.yAxis.len - g.y - g.height, y: c.xAxis.len - g.x - g.width, width: g.height, height: g.width }), n || (e ? (g.x += m ? 0 : g.width, g.width = 0) : (g.y += m ? g.height : 0, g.height = 0)));f.align = t(f.align, !e || n ? "center" : m ? "right" : "left");f.verticalAlign = t(f.verticalAlign, e || n ? "middle" : m ? "top" : "bottom");d.prototype.alignDataLabel.call(this, a, b, f, g, k);a.isLabelJustified && a.contrastColor && a.dataLabel.css({ color: a.contrastColor });
    });
  })(M);(function (a) {
    var C = a.Chart,
        A = a.each,
        F = a.objectEach,
        E = a.pick,
        m = a.addEvent;C.prototype.callbacks.push(function (a) {
      function f() {
        var f = [];A(a.yAxis || [], function (a) {
          a.options.stackLabels && !a.options.stackLabels.allowOverlap && F(a.stacks, function (a) {
            F(a, function (a) {
              f.push(a.label);
            });
          });
        });A(a.series || [], function (a) {
          var l = a.options.dataLabels,
              g = a.dataLabelCollections || ["dataLabel"];(l.enabled || a._hasPointLabels) && !l.allowOverlap && a.visible && A(g, function (d) {
            A(a.points, function (a) {
              a[d] && (a[d].labelrank = E(a.labelrank, a.shapeArgs && a.shapeArgs.height), f.push(a[d]));
            });
          });
        });a.hideOverlappingLabels(f);
      }f();m(a, "redraw", f);
    });C.prototype.hideOverlappingLabels = function (a) {
      var f = a.length,
          m,
          u,
          t,
          g,
          d,
          k,
          b,
          e,
          v,
          y = function y(a, b, d, c, e, f, g, k) {
        return !(e > a + d || e + g < a || f > b + c || f + k < b);
      };for (u = 0; u < f; u++) {
        if (m = a[u]) m.oldOpacity = m.opacity, m.newOpacity = 1, m.width || (t = m.getBBox(), m.width = t.width, m.height = t.height);
      }a.sort(function (a, b) {
        return (b.labelrank || 0) - (a.labelrank || 0);
      });for (u = 0; u < f; u++) {
        for (t = a[u], m = u + 1; m < f; ++m) {
          if (g = a[m], t && g && t !== g && t.placed && g.placed && 0 !== t.newOpacity && 0 !== g.newOpacity && (d = t.alignAttr, k = g.alignAttr, b = t.parentGroup, e = g.parentGroup, v = 2 * (t.box ? 0 : t.padding || 0), d = y(d.x + b.translateX, d.y + b.translateY, t.width - v, t.height - v, k.x + e.translateX, k.y + e.translateY, g.width - v, g.height - v))) (t.labelrank < g.labelrank ? t : g).newOpacity = 0;
        }
      }A(a, function (a) {
        var b, d;a && (d = a.newOpacity, a.oldOpacity !== d && a.placed && (d ? a.show(!0) : b = function b() {
          a.hide();
        }, a.alignAttr.opacity = d, a[a.isOld ? "animate" : "attr"](a.alignAttr, null, b)), a.isOld = !0);
      });
    };
  })(M);(function (a) {
    var C = a.addEvent,
        A = a.Chart,
        F = a.createElement,
        E = a.css,
        m = a.defaultOptions,
        f = a.defaultPlotOptions,
        l = a.each,
        r = a.extend,
        u = a.fireEvent,
        t = a.hasTouch,
        g = a.inArray,
        d = a.isObject,
        k = a.Legend,
        b = a.merge,
        e = a.pick,
        v = a.Point,
        y = a.Series,
        n = a.seriesTypes,
        D = a.svg,
        J;J = a.TrackerMixin = { drawTrackerPoint: function drawTrackerPoint() {
        var a = this,
            b = a.chart.pointer,
            d = function d(a) {
          var c = b.getPointFromEvent(a);
          void 0 !== c && (b.isDirectTouch = !0, c.onMouseOver(a));
        };l(a.points, function (a) {
          a.graphic && (a.graphic.element.point = a);a.dataLabel && (a.dataLabel.div ? a.dataLabel.div.point = a : a.dataLabel.element.point = a);
        });a._hasTracking || (l(a.trackerGroups, function (c) {
          if (a[c]) {
            a[c].addClass("highcharts-tracker").on("mouseover", d).on("mouseout", function (a) {
              b.onTrackerMouseOut(a);
            });if (t) a[c].on("touchstart", d);a.options.cursor && a[c].css(E).css({ cursor: a.options.cursor });
          }
        }), a._hasTracking = !0);
      }, drawTrackerGraph: function drawTrackerGraph() {
        var a = this,
            b = a.options,
            d = b.trackByArea,
            e = [].concat(d ? a.areaPath : a.graphPath),
            f = e.length,
            g = a.chart,
            k = g.pointer,
            m = g.renderer,
            n = g.options.tooltip.snap,
            h = a.tracker,
            r,
            u = function u() {
          if (g.hoverSeries !== a) a.onMouseOver();
        },
            v = "rgba(192,192,192," + (D ? .0001 : .002) + ")";if (f && !d) for (r = f + 1; r--;) {
          "M" === e[r] && e.splice(r + 1, 0, e[r + 1] - n, e[r + 2], "L"), (r && "M" === e[r] || r === f) && e.splice(r, 0, "L", e[r - 2] + n, e[r - 1]);
        }h ? h.attr({ d: e }) : a.graph && (a.tracker = m.path(e).attr({ "stroke-linejoin": "round", visibility: a.visible ? "visible" : "hidden", stroke: v,
          fill: d ? v : "none", "stroke-width": a.graph.strokeWidth() + (d ? 0 : 2 * n), zIndex: 2 }).add(a.group), l([a.tracker, a.markerGroup], function (a) {
          a.addClass("highcharts-tracker").on("mouseover", u).on("mouseout", function (a) {
            k.onTrackerMouseOut(a);
          });b.cursor && a.css({ cursor: b.cursor });if (t) a.on("touchstart", u);
        }));
      } };n.column && (n.column.prototype.drawTracker = J.drawTrackerPoint);n.pie && (n.pie.prototype.drawTracker = J.drawTrackerPoint);n.scatter && (n.scatter.prototype.drawTracker = J.drawTrackerPoint);r(k.prototype, { setItemEvents: function setItemEvents(a, d, e) {
        var c = this,
            f = c.chart.renderer.boxWrapper,
            g = "highcharts-legend-" + (a.series ? "point" : "series") + "-active";(e ? d : a.legendGroup).on("mouseover", function () {
          a.setState("hover");f.addClass(g);d.css(c.options.itemHoverStyle);
        }).on("mouseout", function () {
          d.css(b(a.visible ? c.itemStyle : c.itemHiddenStyle));f.removeClass(g);a.setState();
        }).on("click", function (b) {
          var c = function c() {
            a.setVisible && a.setVisible();
          };b = { browserEvent: b };a.firePointEvent ? a.firePointEvent("legendItemClick", b, c) : u(a, "legendItemClick", b, c);
        });
      },
      createCheckboxForItem: function createCheckboxForItem(a) {
        a.checkbox = F("input", { type: "checkbox", checked: a.selected, defaultChecked: a.selected }, this.options.itemCheckboxStyle, this.chart.container);C(a.checkbox, "click", function (b) {
          u(a.series || a, "checkboxClick", { checked: b.target.checked, item: a }, function () {
            a.select();
          });
        });
      } });m.legend.itemStyle.cursor = "pointer";r(A.prototype, { showResetZoom: function showResetZoom() {
        var a = this,
            b = m.lang,
            d = a.options.chart.resetZoomButton,
            e = d.theme,
            f = e.states,
            g = "chart" === d.relativeTo ? null : "plotBox";this.resetZoomButton = a.renderer.button(b.resetZoom, null, null, function () {
          a.zoomOut();
        }, e, f && f.hover).attr({ align: d.position.align, title: b.resetZoomTitle }).addClass("highcharts-reset-zoom").add().align(d.position, !1, g);
      }, zoomOut: function zoomOut() {
        var a = this;u(a, "selection", { resetSelection: !0 }, function () {
          a.zoom();
        });
      }, zoom: function zoom(a) {
        var b,
            c = this.pointer,
            f = !1,
            g;!a || a.resetSelection ? (l(this.axes, function (a) {
          b = a.zoom();
        }), c.initiated = !1) : l(a.xAxis.concat(a.yAxis), function (a) {
          var d = a.axis;c[d.isXAxis ? "zoomX" : "zoomY"] && (b = d.zoom(a.min, a.max), d.displayBtn && (f = !0));
        });g = this.resetZoomButton;f && !g ? this.showResetZoom() : !f && d(g) && (this.resetZoomButton = g.destroy());b && this.redraw(e(this.options.chart.animation, a && a.animation, 100 > this.pointCount));
      }, pan: function pan(a, b) {
        var c = this,
            d = c.hoverPoints,
            e;d && l(d, function (a) {
          a.setState();
        });l("xy" === b ? [1, 0] : [1], function (b) {
          b = c[b ? "xAxis" : "yAxis"][0];var d = b.horiz,
              f = a[d ? "chartX" : "chartY"],
              d = d ? "mouseDownX" : "mouseDownY",
              g = c[d],
              h = (b.pointRange || 0) / 2,
              k = b.getExtremes(),
              l = b.toValue(g - f, !0) + h,
              h = b.toValue(g + b.len - f, !0) - h,
              m = h < l,
              g = m ? h : l,
              l = m ? l : h,
              h = Math.min(k.dataMin, b.toValue(b.toPixels(k.min) - b.minPixelPadding)),
              m = Math.max(k.dataMax, b.toValue(b.toPixels(k.max) + b.minPixelPadding)),
              n;n = h - g;0 < n && (l += n, g = h);n = l - m;0 < n && (l = m, g -= n);b.series.length && g !== k.min && l !== k.max && (b.setExtremes(g, l, !1, !1, { trigger: "pan" }), e = !0);c[d] = f;
        });e && c.redraw(!1);E(c.container, { cursor: "move" });
      } });r(v.prototype, { select: function select(a, b) {
        var c = this,
            d = c.series,
            f = d.chart;a = e(a, !c.selected);c.firePointEvent(a ? "select" : "unselect", { accumulate: b }, function () {
          c.selected = c.options.selected = a;d.options.data[g(c, d.data)] = c.options;c.setState(a && "select");b || l(f.getSelectedPoints(), function (a) {
            a.selected && a !== c && (a.selected = a.options.selected = !1, d.options.data[g(a, d.data)] = a.options, a.setState(""), a.firePointEvent("unselect"));
          });
        });
      }, onMouseOver: function onMouseOver(a) {
        var b = this.series.chart,
            c = b.pointer;a = a ? c.normalize(a) : c.getChartCoordinatesFromPoint(this, b.inverted);c.runPointActions(a, this);
      }, onMouseOut: function onMouseOut() {
        var a = this.series.chart;this.firePointEvent("mouseOut");
        l(a.hoverPoints || [], function (a) {
          a.setState();
        });a.hoverPoints = a.hoverPoint = null;
      }, importEvents: function importEvents() {
        if (!this.hasImportedEvents) {
          var c = this,
              d = b(c.series.options.point, c.options).events;c.events = d;a.objectEach(d, function (a, b) {
            C(c, b, a);
          });this.hasImportedEvents = !0;
        }
      }, setState: function setState(a, b) {
        var c = Math.floor(this.plotX),
            d = this.plotY,
            g = this.series,
            k = g.options.states[a] || {},
            l = f[g.type].marker && g.options.marker,
            m = l && !1 === l.enabled,
            n = l && l.states && l.states[a] || {},
            h = !1 === n.enabled,
            t = g.stateMarkerGraphic,
            u = this.marker || {},
            v = g.chart,
            y = g.halo,
            A,
            C = l && g.markerAttribs;a = a || "";if (!(a === this.state && !b || this.selected && "select" !== a || !1 === k.enabled || a && (h || m && !1 === n.enabled) || a && u.states && u.states[a] && !1 === u.states[a].enabled)) {
          C && (A = g.markerAttribs(this, a));if (this.graphic) this.state && this.graphic.removeClass("highcharts-point-" + this.state), a && this.graphic.addClass("highcharts-point-" + a), this.graphic.animate(g.pointAttribs(this, a), e(v.options.chart.animation, k.animation)), A && this.graphic.animate(A, e(v.options.chart.animation, n.animation, l.animation)), t && t.hide();else {
            if (a && n) {
              l = u.symbol || g.symbol;t && t.currentSymbol !== l && (t = t.destroy());if (t) t[b ? "animate" : "attr"]({ x: A.x, y: A.y });else l && (g.stateMarkerGraphic = t = v.renderer.symbol(l, A.x, A.y, A.width, A.height).add(g.markerGroup), t.currentSymbol = l);t && t.attr(g.pointAttribs(this, a));
            }t && (t[a && v.isInsidePlot(c, d, v.inverted) ? "show" : "hide"](), t.element.point = this);
          }(c = k.halo) && c.size ? (y || (g.halo = y = v.renderer.path().add((this.graphic || t).parentGroup)), y[b ? "animate" : "attr"]({ d: this.haloPath(c.size) }), y.attr({ "class": "highcharts-halo highcharts-color-" + e(this.colorIndex, g.colorIndex) }), y.point = this, y.attr(r({ fill: this.color || g.color, "fill-opacity": c.opacity, zIndex: -1 }, c.attributes))) : y && y.point && y.point.haloPath && y.animate({ d: y.point.haloPath(0) });this.state = a;
        }
      }, haloPath: function haloPath(a) {
        return this.series.chart.renderer.symbols.circle(Math.floor(this.plotX) - a, this.plotY - a, 2 * a, 2 * a);
      } });r(y.prototype, { onMouseOver: function onMouseOver() {
        var a = this.chart,
            b = a.hoverSeries;if (b && b !== this) b.onMouseOut();this.options.events.mouseOver && u(this, "mouseOver");this.setState("hover");a.hoverSeries = this;
      }, onMouseOut: function onMouseOut() {
        var a = this.options,
            b = this.chart,
            d = b.tooltip,
            e = b.hoverPoint;b.hoverSeries = null;if (e) e.onMouseOut();this && a.events.mouseOut && u(this, "mouseOut");!d || this.stickyTracking || d.shared && !this.noSharedTooltip || d.hide();this.setState();
      }, setState: function setState(a) {
        var b = this,
            c = b.options,
            d = b.graph,
            f = c.states,
            g = c.lineWidth,
            c = 0;a = a || "";if (b.state !== a && (l([b.group, b.markerGroup, b.dataLabelsGroup], function (c) {
          c && (b.state && c.removeClass("highcharts-series-" + b.state), a && c.addClass("highcharts-series-" + a));
        }), b.state = a, !f[a] || !1 !== f[a].enabled) && (a && (g = f[a].lineWidth || g + (f[a].lineWidthPlus || 0)), d && !d.dashstyle)) for (g = { "stroke-width": g }, d.animate(g, e(b.chart.options.chart.animation, f[a] && f[a].animation)); b["zone-graph-" + c];) {
          b["zone-graph-" + c].attr(g), c += 1;
        }
      }, setVisible: function setVisible(a, b) {
        var c = this,
            d = c.chart,
            e = c.legendItem,
            f,
            g = d.options.chart.ignoreHiddenSeries,
            k = c.visible;f = (c.visible = a = c.options.visible = c.userOptions.visible = void 0 === a ? !k : a) ? "show" : "hide";l(["group", "dataLabelsGroup", "markerGroup", "tracker", "tt"], function (a) {
          if (c[a]) c[a][f]();
        });if (d.hoverSeries === c || (d.hoverPoint && d.hoverPoint.series) === c) c.onMouseOut();e && d.legend.colorizeItem(c, a);c.isDirty = !0;c.options.stacking && l(d.series, function (a) {
          a.options.stacking && a.visible && (a.isDirty = !0);
        });l(c.linkedSeries, function (b) {
          b.setVisible(a, !1);
        });g && (d.isDirtyBox = !0);!1 !== b && d.redraw();u(c, f);
      }, show: function show() {
        this.setVisible(!0);
      }, hide: function hide() {
        this.setVisible(!1);
      }, select: function select(a) {
        this.selected = a = void 0 === a ? !this.selected : a;this.checkbox && (this.checkbox.checked = a);u(this, a ? "select" : "unselect");
      }, drawTracker: J.drawTrackerGraph });
  })(M);(function (a) {
    var C = a.Chart,
        A = a.each,
        F = a.inArray,
        E = a.isArray,
        m = a.isObject,
        f = a.pick,
        l = a.splat;C.prototype.setResponsive = function (f) {
      var l = this.options.responsive,
          m = [],
          g = this.currentResponsive;l && l.rules && A(l.rules, function (d) {
        void 0 === d._id && (d._id = a.uniqueKey());this.matchResponsiveRule(d, m, f);
      }, this);var d = a.merge.apply(0, a.map(m, function (d) {
        return a.find(l.rules, function (a) {
          return a._id === d;
        }).chartOptions;
      })),
          m = m.toString() || void 0;m !== (g && g.ruleIds) && (g && this.update(g.undoOptions, f), m ? (this.currentResponsive = { ruleIds: m, mergedOptions: d, undoOptions: this.currentOptions(d) }, this.update(d, f)) : this.currentResponsive = void 0);
    };C.prototype.matchResponsiveRule = function (a, l) {
      var m = a.condition;(m.callback || function () {
        return this.chartWidth <= f(m.maxWidth, Number.MAX_VALUE) && this.chartHeight <= f(m.maxHeight, Number.MAX_VALUE) && this.chartWidth >= f(m.minWidth, 0) && this.chartHeight >= f(m.minHeight, 0);
      }).call(this) && l.push(a._id);
    };C.prototype.currentOptions = function (f) {
      function r(f, d, k, b) {
        var e;a.objectEach(f, function (a, g) {
          if (!b && -1 < F(g, ["series", "xAxis", "yAxis"])) for (f[g] = l(f[g]), k[g] = [], e = 0; e < f[g].length; e++) {
            d[g][e] && (k[g][e] = {}, r(a[e], d[g][e], k[g][e], b + 1));
          } else m(a) ? (k[g] = E(a) ? [] : {}, r(a, d[g] || {}, k[g], b + 1)) : k[g] = d[g] || null;
        });
      }var t = {};r(f, this.options, t, 0);return t;
    };
  })(M);return M;
});
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../node_modules/webpack/buildin/module.js */ 1)(module)))

/***/ }),

/***/ 201:
/*!***************************************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-experiment-page-tsne-plot/lib/plotloader/SeriesPropTypes.js ***!
  \***************************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var SeriesPropTypes = _propTypes2.default.arrayOf(_propTypes2.default.shape({
  name: _propTypes2.default.string.isRequired,
  data: _propTypes2.default.arrayOf(_propTypes2.default.shape({
    name: _propTypes2.default.string.isRequired,
    x: _propTypes2.default.number.isRequired,
    y: _propTypes2.default.number.isRequired,
    expressionLevel: _propTypes2.default.number.isRequired,
    color: _propTypes2.default.string
  })).isRequired,
  color: _propTypes2.default.string
}));

exports.default = SeriesPropTypes;

/***/ }),

/***/ 28:
/*!*******************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/PathUtils.js ***!
  \*******************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;
var addLeadingSlash = exports.addLeadingSlash = function addLeadingSlash(path) {
  return path.charAt(0) === '/' ? path : '/' + path;
};

var stripLeadingSlash = exports.stripLeadingSlash = function stripLeadingSlash(path) {
  return path.charAt(0) === '/' ? path.substr(1) : path;
};

var hasBasename = exports.hasBasename = function hasBasename(path, prefix) {
  return new RegExp('^' + prefix + '(\\/|\\?|#|$)', 'i').test(path);
};

var stripBasename = exports.stripBasename = function stripBasename(path, prefix) {
  return hasBasename(path, prefix) ? path.substr(prefix.length) : path;
};

var stripTrailingSlash = exports.stripTrailingSlash = function stripTrailingSlash(path) {
  return path.charAt(path.length - 1) === '/' ? path.slice(0, -1) : path;
};

var parsePath = exports.parsePath = function parsePath(path) {
  var pathname = path || '/';
  var search = '';
  var hash = '';

  var hashIndex = pathname.indexOf('#');
  if (hashIndex !== -1) {
    hash = pathname.substr(hashIndex);
    pathname = pathname.substr(0, hashIndex);
  }

  var searchIndex = pathname.indexOf('?');
  if (searchIndex !== -1) {
    search = pathname.substr(searchIndex);
    pathname = pathname.substr(0, searchIndex);
  }

  return {
    pathname: pathname,
    search: search === '?' ? '' : search,
    hash: hash === '#' ? '' : hash
  };
};

var createPath = exports.createPath = function createPath(location) {
  var pathname = location.pathname,
      search = location.search,
      hash = location.hash;

  var path = pathname || '/';

  if (search && search !== '?') path += search.charAt(0) === '?' ? search : '?' + search;

  if (hash && hash !== '#') path += hash.charAt(0) === '#' ? hash : '#' + hash;

  return path;
};

/***/ }),

/***/ 29:
/*!**********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/es/PathUtils.js ***!
  \**********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
var addLeadingSlash = exports.addLeadingSlash = function addLeadingSlash(path) {
  return path.charAt(0) === '/' ? path : '/' + path;
};

var stripLeadingSlash = exports.stripLeadingSlash = function stripLeadingSlash(path) {
  return path.charAt(0) === '/' ? path.substr(1) : path;
};

var hasBasename = exports.hasBasename = function hasBasename(path, prefix) {
  return new RegExp('^' + prefix + '(\\/|\\?|#|$)', 'i').test(path);
};

var stripBasename = exports.stripBasename = function stripBasename(path, prefix) {
  return hasBasename(path, prefix) ? path.substr(prefix.length) : path;
};

var stripTrailingSlash = exports.stripTrailingSlash = function stripTrailingSlash(path) {
  return path.charAt(path.length - 1) === '/' ? path.slice(0, -1) : path;
};

var parsePath = exports.parsePath = function parsePath(path) {
  var pathname = path || '/';
  var search = '';
  var hash = '';

  var hashIndex = pathname.indexOf('#');
  if (hashIndex !== -1) {
    hash = pathname.substr(hashIndex);
    pathname = pathname.substr(0, hashIndex);
  }

  var searchIndex = pathname.indexOf('?');
  if (searchIndex !== -1) {
    search = pathname.substr(searchIndex);
    pathname = pathname.substr(0, searchIndex);
  }

  return {
    pathname: pathname,
    search: search === '?' ? '' : search,
    hash: hash === '#' ? '' : hash
  };
};

var createPath = exports.createPath = function createPath(location) {
  var pathname = location.pathname,
      search = location.search,
      hash = location.hash;

  var path = pathname || '/';

  if (search && search !== '?') path += search.charAt(0) === '?' ? search : '?' + search;

  if (hash && hash !== '#') path += hash.charAt(0) === '#' ? hash : '#' + hash;

  return path;
};

/***/ }),

/***/ 3:
/*!*****************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/warning/browser.js ***!
  \*****************************************************************/
/*! dynamic exports provided */
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

/***/ 425:
/*!****************************************************!*\
  !*** multi ./bundles/experiment-page/src/index.js ***!
  \****************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! ./bundles/experiment-page/src/index.js */426);


/***/ }),

/***/ 426:
/*!**********************************************!*\
  !*** ./bundles/experiment-page/src/index.js ***!
  \**********************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.render = exports.default = undefined;

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _reactDom = __webpack_require__(/*! react-dom */ 18);

var _reactDom2 = _interopRequireDefault(_reactDom);

var _ExperimentPageRouter = __webpack_require__(/*! ./ExperimentPageRouter */ 427);

var _ExperimentPageRouter2 = _interopRequireDefault(_ExperimentPageRouter);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var render = function render(options, mountNodeId) {
  _reactDom2.default.render(_react2.default.createElement(_ExperimentPageRouter2.default, { atlasUrl: options.atlasUrl,
    resourcesUrl: options.resourcesUrl,
    experimentAccession: options.content.experimentAccession,
    accessKey: options.content.accessKey,
    species: options.content.species,
    tabs: options.content.tabs
  }), document.getElementById(mountNodeId));
};

exports.default = _ExperimentPageRouter2.default;
exports.render = render;

/***/ }),

/***/ 427:
/*!*************************************************************!*\
  !*** ./bundles/experiment-page/src/ExperimentPageRouter.js ***!
  \*************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _reactRouterDom = __webpack_require__(/*! react-router-dom */ 430);

var _urijs = __webpack_require__(/*! urijs */ 15);

var _urijs2 = _interopRequireDefault(_urijs);

var _TSnePlotViewRoute = __webpack_require__(/*! ./TSnePlotViewRoute */ 457);

var _TSnePlotViewRoute2 = _interopRequireDefault(_TSnePlotViewRoute);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var RoutePropTypes = {
  match: _propTypes2.default.object.isRequired,
  location: _propTypes2.default.object.isRequired,
  history: _propTypes2.default.object.isRequired
};

var TabCommonPropTypes = {
  atlasUrl: _propTypes2.default.string.isRequired,
  experimentAccession: _propTypes2.default.string.isRequired,
  accessKey: _propTypes2.default.string,
  resourcesUrl: _propTypes2.default.string

  // What component each tab type should render, coupled to ExperimentController.java
};var tabTypeComponent = {
  // 'multipart' : ``,
  't-sne-plot': _TSnePlotViewRoute2.default
  // 'experiment-design' : `ExperimntDesign`,
  // 'resources' : `Resources`,
  // 'static-table' : StaticTable,
  // 'qc-report' : QCReport
};

var TopRibbon = function TopRibbon(_ref) {
  var tabNames = _ref.tabNames,
      routeProps = _ref.routeProps;
  return _react2.default.createElement(
    'ul',
    { className: 'tabs' },
    tabNames.map(function (tabName) {
      return _react2.default.createElement(
        'li',
        { title: tabName, key: tabName, className: 'tabs-title' },
        _react2.default.createElement(
          _reactRouterDom.NavLink,
          { to: { pathname: '/' + tabName, search: routeProps.location.search, hash: routeProps.location.hash },
            activeStyle: { color: '#0a0a0a', background: '#e6e6e6' } },
          tabName
        )
      );
    })
  );
};

TopRibbon.propTypes = {
  tabNames: _propTypes2.default.arrayOf(_propTypes2.default.string).isRequired,
  routeProps: _propTypes2.default.shape(RoutePropTypes)
};

var TabContent = function TabContent(_ref2) {
  var type = _ref2.type,
      tabProps = _ref2.tabProps,
      commonProps = _ref2.commonProps,
      routeProps = _ref2.routeProps;

  // Pass in the search from location
  var Tab = tabTypeComponent[type];

  return Tab ? _react2.default.createElement(Tab, _extends({}, tabProps, commonProps, routeProps)) : null;
};

TabContent.propTypes = {
  type: _propTypes2.default.string.isRequired,
  tabProps: _propTypes2.default.object,
  commonProps: _propTypes2.default.shape(TabCommonPropTypes),
  routeProps: _propTypes2.default.shape(RoutePropTypes)
};

var RedirectWithSearchAndHash = function RedirectWithSearchAndHash(props) {
  return _react2.default.createElement(_reactRouterDom.Redirect, { to: { pathname: props.pathname, search: props.location.search, hash: props.location.hash } });
};

var RedirectWithLocation = (0, _reactRouterDom.withRouter)(RedirectWithSearchAndHash);

var ExperimentPageRouter = function ExperimentPageRouter(_ref3) {
  var atlasUrl = _ref3.atlasUrl,
      resourcesUrl = _ref3.resourcesUrl,
      experimentAccession = _ref3.experimentAccession,
      accessKey = _ref3.accessKey,
      tabs = _ref3.tabs;


  var tabCommonProps = {
    atlasUrl: atlasUrl,
    resourcesUrl: resourcesUrl,
    experimentAccession: experimentAccession,
    accessKey: accessKey
  };

  return _react2.default.createElement(
    _reactRouterDom.BrowserRouter,
    { basename: (0, _urijs2.default)('experiments/' + experimentAccession, (0, _urijs2.default)(atlasUrl).path()).toString() },
    _react2.default.createElement(
      'div',
      null,
      _react2.default.createElement(_reactRouterDom.Route, { path: '/',
        render: function render(routeProps) {
          return _react2.default.createElement(TopRibbon, { tabNames: tabs.map(function (tab) {
              return tab.name;
            }), routeProps: routeProps });
        }
      }),
      _react2.default.createElement(
        _reactRouterDom.Switch,
        null,
        tabs.map(function (tab) {
          return _react2.default.createElement(_reactRouterDom.Route, { key: tab.name,
            path: '/' + tab.name,
            render: function render(routeProps) {
              return _react2.default.createElement(TabContent, { type: tab.type, tabProps: tab.props, commonProps: tabCommonProps, routeProps: routeProps });
            }
          });
        }),
        _react2.default.createElement(RedirectWithLocation, { pathname: '/' + tabs[0].name })
      )
    )
  );
};

ExperimentPageRouter.propTypes = _extends({}, TabCommonPropTypes, {
  tabs: _propTypes2.default.arrayOf(_propTypes2.default.shape({
    type: _propTypes2.default.string.isRequired,
    name: _propTypes2.default.string.isRequired,
    props: _propTypes2.default.object.isRequired
  })).isRequired
});

exports.default = ExperimentPageRouter;

/***/ }),

/***/ 428:
/*!************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/prop-types/factoryWithTypeCheckers.js ***!
  \************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright (c) 2013-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */



var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var emptyFunction = __webpack_require__(/*! fbjs/lib/emptyFunction */ 181);
var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 182);
var warning = __webpack_require__(/*! fbjs/lib/warning */ 183);
var assign = __webpack_require__(/*! object-assign */ 184);

var ReactPropTypesSecret = __webpack_require__(/*! ./lib/ReactPropTypesSecret */ 185);
var checkPropTypes = __webpack_require__(/*! ./checkPropTypes */ 429);

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
    shape: createShapeTypeChecker,
    exact: createStrictShapeTypeChecker
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
        warning(false, 'Invalid argument supplied to oneOfType. Expected an array of check functions, but ' + 'received %s at index %s.', getPostfixForTypeWarning(checker), i);
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

  function createStrictShapeTypeChecker(shapeTypes) {
    function validate(props, propName, componentName, location, propFullName) {
      var propValue = props[propName];
      var propType = getPropType(propValue);
      if (propType !== 'object') {
        return new PropTypeError('Invalid ' + location + ' `' + propFullName + '` of type `' + propType + '` ' + ('supplied to `' + componentName + '`, expected `object`.'));
      }
      // We need to check all keys in case some are required but missing from
      // props.
      var allKeys = assign({}, props[propName], shapeTypes);
      for (var key in allKeys) {
        var checker = shapeTypes[key];
        if (!checker) {
          return new PropTypeError('Invalid ' + location + ' `' + propFullName + '` key `' + key + '` supplied to `' + componentName + '`.' + '\nBad object: ' + JSON.stringify(props[propName], null, '  ') + '\nValid keys: ' + JSON.stringify(Object.keys(shapeTypes), null, '  '));
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

/***/ 429:
/*!***************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/prop-types/checkPropTypes.js ***!
  \***************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/**
 * Copyright (c) 2013-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */



var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

if (true) {
  var invariant = __webpack_require__(/*! fbjs/lib/invariant */ 182);
  var warning = __webpack_require__(/*! fbjs/lib/warning */ 183);
  var ReactPropTypesSecret = __webpack_require__(/*! ./lib/ReactPropTypesSecret */ 185);
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
          invariant(typeof typeSpecs[typeSpecName] === 'function', '%s: %s type `%s` is invalid; it must be a function, usually from ' + 'the `prop-types` package, but received `%s`.', componentName || 'React class', location, typeSpecName, _typeof(typeSpecs[typeSpecName]));
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

/***/ 43:
/*!**************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/es/LocationUtils.js ***!
  \**************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.locationsAreEqual = exports.createLocation = undefined;

var _resolvePathname = __webpack_require__(/*! resolve-pathname */ 186);

var _resolvePathname2 = _interopRequireDefault(_resolvePathname);

var _valueEqual = __webpack_require__(/*! value-equal */ 187);

var _valueEqual2 = _interopRequireDefault(_valueEqual);

var _PathUtils = __webpack_require__(/*! ./PathUtils */ 29);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var createLocation = exports.createLocation = function createLocation(path, state, key, currentLocation) {
  var location = void 0;
  if (typeof path === 'string') {
    // Two-arg form: push(path, state)
    location = (0, _PathUtils.parsePath)(path);
    location.state = state;
  } else {
    // One-arg form: push(location)
    location = _extends({}, path);

    if (location.pathname === undefined) location.pathname = '';

    if (location.search) {
      if (location.search.charAt(0) !== '?') location.search = '?' + location.search;
    } else {
      location.search = '';
    }

    if (location.hash) {
      if (location.hash.charAt(0) !== '#') location.hash = '#' + location.hash;
    } else {
      location.hash = '';
    }

    if (state !== undefined && location.state === undefined) location.state = state;
  }

  try {
    location.pathname = decodeURI(location.pathname);
  } catch (e) {
    if (e instanceof URIError) {
      throw new URIError('Pathname "' + location.pathname + '" could not be decoded. ' + 'This is likely caused by an invalid percent-encoding.');
    } else {
      throw e;
    }
  }

  if (key) location.key = key;

  if (currentLocation) {
    // Resolve incomplete/relative pathname relative to current location.
    if (!location.pathname) {
      location.pathname = currentLocation.pathname;
    } else if (location.pathname.charAt(0) !== '/') {
      location.pathname = (0, _resolvePathname2.default)(location.pathname, currentLocation.pathname);
    }
  } else {
    // When there is no prior location and pathname is empty, set it to /
    if (!location.pathname) {
      location.pathname = '/';
    }
  }

  return location;
};

var locationsAreEqual = exports.locationsAreEqual = function locationsAreEqual(a, b) {
  return a.pathname === b.pathname && a.search === b.search && a.hash === b.hash && a.key === b.key && (0, _valueEqual2.default)(a.state, b.state);
};

/***/ }),

/***/ 430:
/*!***************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/index.js ***!
  \***************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.withRouter = exports.matchPath = exports.Switch = exports.StaticRouter = exports.Router = exports.Route = exports.Redirect = exports.Prompt = exports.NavLink = exports.MemoryRouter = exports.Link = exports.HashRouter = exports.BrowserRouter = undefined;

var _BrowserRouter2 = __webpack_require__(/*! ./BrowserRouter */ 431);

var _BrowserRouter3 = _interopRequireDefault(_BrowserRouter2);

var _HashRouter2 = __webpack_require__(/*! ./HashRouter */ 433);

var _HashRouter3 = _interopRequireDefault(_HashRouter2);

var _Link2 = __webpack_require__(/*! ./Link */ 189);

var _Link3 = _interopRequireDefault(_Link2);

var _MemoryRouter2 = __webpack_require__(/*! ./MemoryRouter */ 435);

var _MemoryRouter3 = _interopRequireDefault(_MemoryRouter2);

var _NavLink2 = __webpack_require__(/*! ./NavLink */ 438);

var _NavLink3 = _interopRequireDefault(_NavLink2);

var _Prompt2 = __webpack_require__(/*! ./Prompt */ 441);

var _Prompt3 = _interopRequireDefault(_Prompt2);

var _Redirect2 = __webpack_require__(/*! ./Redirect */ 443);

var _Redirect3 = _interopRequireDefault(_Redirect2);

var _Route2 = __webpack_require__(/*! ./Route */ 190);

var _Route3 = _interopRequireDefault(_Route2);

var _Router2 = __webpack_require__(/*! ./Router */ 83);

var _Router3 = _interopRequireDefault(_Router2);

var _StaticRouter2 = __webpack_require__(/*! ./StaticRouter */ 449);

var _StaticRouter3 = _interopRequireDefault(_StaticRouter2);

var _Switch2 = __webpack_require__(/*! ./Switch */ 451);

var _Switch3 = _interopRequireDefault(_Switch2);

var _matchPath2 = __webpack_require__(/*! ./matchPath */ 453);

var _matchPath3 = _interopRequireDefault(_matchPath2);

var _withRouter2 = __webpack_require__(/*! ./withRouter */ 454);

var _withRouter3 = _interopRequireDefault(_withRouter2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.BrowserRouter = _BrowserRouter3.default;
exports.HashRouter = _HashRouter3.default;
exports.Link = _Link3.default;
exports.MemoryRouter = _MemoryRouter3.default;
exports.NavLink = _NavLink3.default;
exports.Prompt = _Prompt3.default;
exports.Redirect = _Redirect3.default;
exports.Route = _Route3.default;
exports.Router = _Router3.default;
exports.StaticRouter = _StaticRouter3.default;
exports.Switch = _Switch3.default;
exports.matchPath = _matchPath3.default;
exports.withRouter = _withRouter3.default;

/***/ }),

/***/ 431:
/*!***********************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/BrowserRouter.js ***!
  \***********************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _createBrowserHistory = __webpack_require__(/*! history/createBrowserHistory */ 432);

var _createBrowserHistory2 = _interopRequireDefault(_createBrowserHistory);

var _Router = __webpack_require__(/*! ./Router */ 83);

var _Router2 = _interopRequireDefault(_Router);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

/**
 * The public API for a <Router> that uses HTML5 history.
 */

var BrowserRouter = function (_React$Component) {
  _inherits(BrowserRouter, _React$Component);

  function BrowserRouter() {
    var _temp, _this, _ret;

    _classCallCheck(this, BrowserRouter);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, _React$Component.call.apply(_React$Component, [this].concat(args))), _this), _this.history = (0, _createBrowserHistory2.default)(_this.props), _temp), _possibleConstructorReturn(_this, _ret);
  }

  BrowserRouter.prototype.componentWillMount = function componentWillMount() {
    (0, _warning2.default)(!this.props.history, '<BrowserRouter> ignores the history prop. To use a custom history, ' + 'use `import { Router }` instead of `import { BrowserRouter as Router }`.');
  };

  BrowserRouter.prototype.render = function render() {
    return _react2.default.createElement(_Router2.default, { history: this.history, children: this.props.children });
  };

  return BrowserRouter;
}(_react2.default.Component);

BrowserRouter.propTypes = {
  basename: _propTypes2.default.string,
  forceRefresh: _propTypes2.default.bool,
  getUserConfirmation: _propTypes2.default.func,
  keyLength: _propTypes2.default.number,
  children: _propTypes2.default.node
};

exports.default = BrowserRouter;

/***/ }),

/***/ 432:
/*!******************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/createBrowserHistory.js ***!
  \******************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

exports.__esModule = true;

var _typeof = typeof Symbol === "function" && _typeof2(Symbol.iterator) === "symbol" ? function (obj) {
  return typeof obj === "undefined" ? "undefined" : _typeof2(obj);
} : function (obj) {
  return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof2(obj);
};

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _invariant = __webpack_require__(/*! invariant */ 8);

var _invariant2 = _interopRequireDefault(_invariant);

var _LocationUtils = __webpack_require__(/*! ./LocationUtils */ 81);

var _PathUtils = __webpack_require__(/*! ./PathUtils */ 28);

var _createTransitionManager = __webpack_require__(/*! ./createTransitionManager */ 82);

var _createTransitionManager2 = _interopRequireDefault(_createTransitionManager);

var _DOMUtils = __webpack_require__(/*! ./DOMUtils */ 188);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var PopStateEvent = 'popstate';
var HashChangeEvent = 'hashchange';

var getHistoryState = function getHistoryState() {
  try {
    return window.history.state || {};
  } catch (e) {
    // IE 11 sometimes throws when accessing window.history.state
    // See https://github.com/ReactTraining/history/pull/289
    return {};
  }
};

/**
 * Creates a history object that uses the HTML5 history API including
 * pushState, replaceState, and the popstate event.
 */
var createBrowserHistory = function createBrowserHistory() {
  var props = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};

  (0, _invariant2.default)(_DOMUtils.canUseDOM, 'Browser history needs a DOM');

  var globalHistory = window.history;
  var canUseHistory = (0, _DOMUtils.supportsHistory)();
  var needsHashChangeListener = !(0, _DOMUtils.supportsPopStateOnHashChange)();

  var _props$forceRefresh = props.forceRefresh,
      forceRefresh = _props$forceRefresh === undefined ? false : _props$forceRefresh,
      _props$getUserConfirm = props.getUserConfirmation,
      getUserConfirmation = _props$getUserConfirm === undefined ? _DOMUtils.getConfirmation : _props$getUserConfirm,
      _props$keyLength = props.keyLength,
      keyLength = _props$keyLength === undefined ? 6 : _props$keyLength;

  var basename = props.basename ? (0, _PathUtils.stripTrailingSlash)((0, _PathUtils.addLeadingSlash)(props.basename)) : '';

  var getDOMLocation = function getDOMLocation(historyState) {
    var _ref = historyState || {},
        key = _ref.key,
        state = _ref.state;

    var _window$location = window.location,
        pathname = _window$location.pathname,
        search = _window$location.search,
        hash = _window$location.hash;

    var path = pathname + search + hash;

    (0, _warning2.default)(!basename || (0, _PathUtils.hasBasename)(path, basename), 'You are attempting to use a basename on a page whose URL path does not begin ' + 'with the basename. Expected path "' + path + '" to begin with "' + basename + '".');

    if (basename) path = (0, _PathUtils.stripBasename)(path, basename);

    return (0, _LocationUtils.createLocation)(path, state, key);
  };

  var createKey = function createKey() {
    return Math.random().toString(36).substr(2, keyLength);
  };

  var transitionManager = (0, _createTransitionManager2.default)();

  var setState = function setState(nextState) {
    _extends(history, nextState);

    history.length = globalHistory.length;

    transitionManager.notifyListeners(history.location, history.action);
  };

  var handlePopState = function handlePopState(event) {
    // Ignore extraneous popstate events in WebKit.
    if ((0, _DOMUtils.isExtraneousPopstateEvent)(event)) return;

    handlePop(getDOMLocation(event.state));
  };

  var handleHashChange = function handleHashChange() {
    handlePop(getDOMLocation(getHistoryState()));
  };

  var forceNextPop = false;

  var handlePop = function handlePop(location) {
    if (forceNextPop) {
      forceNextPop = false;
      setState();
    } else {
      var action = 'POP';

      transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
        if (ok) {
          setState({ action: action, location: location });
        } else {
          revertPop(location);
        }
      });
    }
  };

  var revertPop = function revertPop(fromLocation) {
    var toLocation = history.location;

    // TODO: We could probably make this more reliable by
    // keeping a list of keys we've seen in sessionStorage.
    // Instead, we just default to 0 for keys we don't know.

    var toIndex = allKeys.indexOf(toLocation.key);

    if (toIndex === -1) toIndex = 0;

    var fromIndex = allKeys.indexOf(fromLocation.key);

    if (fromIndex === -1) fromIndex = 0;

    var delta = toIndex - fromIndex;

    if (delta) {
      forceNextPop = true;
      go(delta);
    }
  };

  var initialLocation = getDOMLocation(getHistoryState());
  var allKeys = [initialLocation.key];

  // Public interface

  var createHref = function createHref(location) {
    return basename + (0, _PathUtils.createPath)(location);
  };

  var push = function push(path, state) {
    (0, _warning2.default)(!((typeof path === 'undefined' ? 'undefined' : _typeof(path)) === 'object' && path.state !== undefined && state !== undefined), 'You should avoid providing a 2nd state argument to push when the 1st ' + 'argument is a location-like object that already has state; it is ignored');

    var action = 'PUSH';
    var location = (0, _LocationUtils.createLocation)(path, state, createKey(), history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      var href = createHref(location);
      var key = location.key,
          state = location.state;

      if (canUseHistory) {
        globalHistory.pushState({ key: key, state: state }, null, href);

        if (forceRefresh) {
          window.location.href = href;
        } else {
          var prevIndex = allKeys.indexOf(history.location.key);
          var nextKeys = allKeys.slice(0, prevIndex === -1 ? 0 : prevIndex + 1);

          nextKeys.push(location.key);
          allKeys = nextKeys;

          setState({ action: action, location: location });
        }
      } else {
        (0, _warning2.default)(state === undefined, 'Browser history cannot push state in browsers that do not support HTML5 history');

        window.location.href = href;
      }
    });
  };

  var replace = function replace(path, state) {
    (0, _warning2.default)(!((typeof path === 'undefined' ? 'undefined' : _typeof(path)) === 'object' && path.state !== undefined && state !== undefined), 'You should avoid providing a 2nd state argument to replace when the 1st ' + 'argument is a location-like object that already has state; it is ignored');

    var action = 'REPLACE';
    var location = (0, _LocationUtils.createLocation)(path, state, createKey(), history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      var href = createHref(location);
      var key = location.key,
          state = location.state;

      if (canUseHistory) {
        globalHistory.replaceState({ key: key, state: state }, null, href);

        if (forceRefresh) {
          window.location.replace(href);
        } else {
          var prevIndex = allKeys.indexOf(history.location.key);

          if (prevIndex !== -1) allKeys[prevIndex] = location.key;

          setState({ action: action, location: location });
        }
      } else {
        (0, _warning2.default)(state === undefined, 'Browser history cannot replace state in browsers that do not support HTML5 history');

        window.location.replace(href);
      }
    });
  };

  var go = function go(n) {
    globalHistory.go(n);
  };

  var goBack = function goBack() {
    return go(-1);
  };

  var goForward = function goForward() {
    return go(1);
  };

  var listenerCount = 0;

  var checkDOMListeners = function checkDOMListeners(delta) {
    listenerCount += delta;

    if (listenerCount === 1) {
      (0, _DOMUtils.addEventListener)(window, PopStateEvent, handlePopState);

      if (needsHashChangeListener) (0, _DOMUtils.addEventListener)(window, HashChangeEvent, handleHashChange);
    } else if (listenerCount === 0) {
      (0, _DOMUtils.removeEventListener)(window, PopStateEvent, handlePopState);

      if (needsHashChangeListener) (0, _DOMUtils.removeEventListener)(window, HashChangeEvent, handleHashChange);
    }
  };

  var isBlocked = false;

  var block = function block() {
    var prompt = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;

    var unblock = transitionManager.setPrompt(prompt);

    if (!isBlocked) {
      checkDOMListeners(1);
      isBlocked = true;
    }

    return function () {
      if (isBlocked) {
        isBlocked = false;
        checkDOMListeners(-1);
      }

      return unblock();
    };
  };

  var listen = function listen(listener) {
    var unlisten = transitionManager.appendListener(listener);
    checkDOMListeners(1);

    return function () {
      checkDOMListeners(-1);
      unlisten();
    };
  };

  var history = {
    length: globalHistory.length,
    action: 'POP',
    location: initialLocation,
    createHref: createHref,
    push: push,
    replace: replace,
    go: go,
    goBack: goBack,
    goForward: goForward,
    block: block,
    listen: listen
  };

  return history;
};

exports.default = createBrowserHistory;

/***/ }),

/***/ 433:
/*!********************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/HashRouter.js ***!
  \********************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _createHashHistory = __webpack_require__(/*! history/createHashHistory */ 434);

var _createHashHistory2 = _interopRequireDefault(_createHashHistory);

var _Router = __webpack_require__(/*! ./Router */ 83);

var _Router2 = _interopRequireDefault(_Router);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

/**
 * The public API for a <Router> that uses window.location.hash.
 */

var HashRouter = function (_React$Component) {
  _inherits(HashRouter, _React$Component);

  function HashRouter() {
    var _temp, _this, _ret;

    _classCallCheck(this, HashRouter);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, _React$Component.call.apply(_React$Component, [this].concat(args))), _this), _this.history = (0, _createHashHistory2.default)(_this.props), _temp), _possibleConstructorReturn(_this, _ret);
  }

  HashRouter.prototype.componentWillMount = function componentWillMount() {
    (0, _warning2.default)(!this.props.history, '<HashRouter> ignores the history prop. To use a custom history, ' + 'use `import { Router }` instead of `import { HashRouter as Router }`.');
  };

  HashRouter.prototype.render = function render() {
    return _react2.default.createElement(_Router2.default, { history: this.history, children: this.props.children });
  };

  return HashRouter;
}(_react2.default.Component);

HashRouter.propTypes = {
  basename: _propTypes2.default.string,
  getUserConfirmation: _propTypes2.default.func,
  hashType: _propTypes2.default.oneOf(['hashbang', 'noslash', 'slash']),
  children: _propTypes2.default.node
};

exports.default = HashRouter;

/***/ }),

/***/ 434:
/*!***************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/createHashHistory.js ***!
  \***************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _invariant = __webpack_require__(/*! invariant */ 8);

var _invariant2 = _interopRequireDefault(_invariant);

var _LocationUtils = __webpack_require__(/*! ./LocationUtils */ 81);

var _PathUtils = __webpack_require__(/*! ./PathUtils */ 28);

var _createTransitionManager = __webpack_require__(/*! ./createTransitionManager */ 82);

var _createTransitionManager2 = _interopRequireDefault(_createTransitionManager);

var _DOMUtils = __webpack_require__(/*! ./DOMUtils */ 188);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var HashChangeEvent = 'hashchange';

var HashPathCoders = {
  hashbang: {
    encodePath: function encodePath(path) {
      return path.charAt(0) === '!' ? path : '!/' + (0, _PathUtils.stripLeadingSlash)(path);
    },
    decodePath: function decodePath(path) {
      return path.charAt(0) === '!' ? path.substr(1) : path;
    }
  },
  noslash: {
    encodePath: _PathUtils.stripLeadingSlash,
    decodePath: _PathUtils.addLeadingSlash
  },
  slash: {
    encodePath: _PathUtils.addLeadingSlash,
    decodePath: _PathUtils.addLeadingSlash
  }
};

var getHashPath = function getHashPath() {
  // We can't use window.location.hash here because it's not
  // consistent across browsers - Firefox will pre-decode it!
  var href = window.location.href;
  var hashIndex = href.indexOf('#');
  return hashIndex === -1 ? '' : href.substring(hashIndex + 1);
};

var pushHashPath = function pushHashPath(path) {
  return window.location.hash = path;
};

var replaceHashPath = function replaceHashPath(path) {
  var hashIndex = window.location.href.indexOf('#');

  window.location.replace(window.location.href.slice(0, hashIndex >= 0 ? hashIndex : 0) + '#' + path);
};

var createHashHistory = function createHashHistory() {
  var props = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};

  (0, _invariant2.default)(_DOMUtils.canUseDOM, 'Hash history needs a DOM');

  var globalHistory = window.history;
  var canGoWithoutReload = (0, _DOMUtils.supportsGoWithoutReloadUsingHash)();

  var _props$getUserConfirm = props.getUserConfirmation,
      getUserConfirmation = _props$getUserConfirm === undefined ? _DOMUtils.getConfirmation : _props$getUserConfirm,
      _props$hashType = props.hashType,
      hashType = _props$hashType === undefined ? 'slash' : _props$hashType;

  var basename = props.basename ? (0, _PathUtils.stripTrailingSlash)((0, _PathUtils.addLeadingSlash)(props.basename)) : '';

  var _HashPathCoders$hashT = HashPathCoders[hashType],
      encodePath = _HashPathCoders$hashT.encodePath,
      decodePath = _HashPathCoders$hashT.decodePath;

  var getDOMLocation = function getDOMLocation() {
    var path = decodePath(getHashPath());

    (0, _warning2.default)(!basename || (0, _PathUtils.hasBasename)(path, basename), 'You are attempting to use a basename on a page whose URL path does not begin ' + 'with the basename. Expected path "' + path + '" to begin with "' + basename + '".');

    if (basename) path = (0, _PathUtils.stripBasename)(path, basename);

    return (0, _LocationUtils.createLocation)(path);
  };

  var transitionManager = (0, _createTransitionManager2.default)();

  var setState = function setState(nextState) {
    _extends(history, nextState);

    history.length = globalHistory.length;

    transitionManager.notifyListeners(history.location, history.action);
  };

  var forceNextPop = false;
  var ignorePath = null;

  var handleHashChange = function handleHashChange() {
    var path = getHashPath();
    var encodedPath = encodePath(path);

    if (path !== encodedPath) {
      // Ensure we always have a properly-encoded hash.
      replaceHashPath(encodedPath);
    } else {
      var location = getDOMLocation();
      var prevLocation = history.location;

      if (!forceNextPop && (0, _LocationUtils.locationsAreEqual)(prevLocation, location)) return; // A hashchange doesn't always == location change.

      if (ignorePath === (0, _PathUtils.createPath)(location)) return; // Ignore this change; we already setState in push/replace.

      ignorePath = null;

      handlePop(location);
    }
  };

  var handlePop = function handlePop(location) {
    if (forceNextPop) {
      forceNextPop = false;
      setState();
    } else {
      var action = 'POP';

      transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
        if (ok) {
          setState({ action: action, location: location });
        } else {
          revertPop(location);
        }
      });
    }
  };

  var revertPop = function revertPop(fromLocation) {
    var toLocation = history.location;

    // TODO: We could probably make this more reliable by
    // keeping a list of paths we've seen in sessionStorage.
    // Instead, we just default to 0 for paths we don't know.

    var toIndex = allPaths.lastIndexOf((0, _PathUtils.createPath)(toLocation));

    if (toIndex === -1) toIndex = 0;

    var fromIndex = allPaths.lastIndexOf((0, _PathUtils.createPath)(fromLocation));

    if (fromIndex === -1) fromIndex = 0;

    var delta = toIndex - fromIndex;

    if (delta) {
      forceNextPop = true;
      go(delta);
    }
  };

  // Ensure the hash is encoded properly before doing anything else.
  var path = getHashPath();
  var encodedPath = encodePath(path);

  if (path !== encodedPath) replaceHashPath(encodedPath);

  var initialLocation = getDOMLocation();
  var allPaths = [(0, _PathUtils.createPath)(initialLocation)];

  // Public interface

  var createHref = function createHref(location) {
    return '#' + encodePath(basename + (0, _PathUtils.createPath)(location));
  };

  var push = function push(path, state) {
    (0, _warning2.default)(state === undefined, 'Hash history cannot push state; it is ignored');

    var action = 'PUSH';
    var location = (0, _LocationUtils.createLocation)(path, undefined, undefined, history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      var path = (0, _PathUtils.createPath)(location);
      var encodedPath = encodePath(basename + path);
      var hashChanged = getHashPath() !== encodedPath;

      if (hashChanged) {
        // We cannot tell if a hashchange was caused by a PUSH, so we'd
        // rather setState here and ignore the hashchange. The caveat here
        // is that other hash histories in the page will consider it a POP.
        ignorePath = path;
        pushHashPath(encodedPath);

        var prevIndex = allPaths.lastIndexOf((0, _PathUtils.createPath)(history.location));
        var nextPaths = allPaths.slice(0, prevIndex === -1 ? 0 : prevIndex + 1);

        nextPaths.push(path);
        allPaths = nextPaths;

        setState({ action: action, location: location });
      } else {
        (0, _warning2.default)(false, 'Hash history cannot PUSH the same path; a new entry will not be added to the history stack');

        setState();
      }
    });
  };

  var replace = function replace(path, state) {
    (0, _warning2.default)(state === undefined, 'Hash history cannot replace state; it is ignored');

    var action = 'REPLACE';
    var location = (0, _LocationUtils.createLocation)(path, undefined, undefined, history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      var path = (0, _PathUtils.createPath)(location);
      var encodedPath = encodePath(basename + path);
      var hashChanged = getHashPath() !== encodedPath;

      if (hashChanged) {
        // We cannot tell if a hashchange was caused by a REPLACE, so we'd
        // rather setState here and ignore the hashchange. The caveat here
        // is that other hash histories in the page will consider it a POP.
        ignorePath = path;
        replaceHashPath(encodedPath);
      }

      var prevIndex = allPaths.indexOf((0, _PathUtils.createPath)(history.location));

      if (prevIndex !== -1) allPaths[prevIndex] = path;

      setState({ action: action, location: location });
    });
  };

  var go = function go(n) {
    (0, _warning2.default)(canGoWithoutReload, 'Hash history go(n) causes a full page reload in this browser');

    globalHistory.go(n);
  };

  var goBack = function goBack() {
    return go(-1);
  };

  var goForward = function goForward() {
    return go(1);
  };

  var listenerCount = 0;

  var checkDOMListeners = function checkDOMListeners(delta) {
    listenerCount += delta;

    if (listenerCount === 1) {
      (0, _DOMUtils.addEventListener)(window, HashChangeEvent, handleHashChange);
    } else if (listenerCount === 0) {
      (0, _DOMUtils.removeEventListener)(window, HashChangeEvent, handleHashChange);
    }
  };

  var isBlocked = false;

  var block = function block() {
    var prompt = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;

    var unblock = transitionManager.setPrompt(prompt);

    if (!isBlocked) {
      checkDOMListeners(1);
      isBlocked = true;
    }

    return function () {
      if (isBlocked) {
        isBlocked = false;
        checkDOMListeners(-1);
      }

      return unblock();
    };
  };

  var listen = function listen(listener) {
    var unlisten = transitionManager.appendListener(listener);
    checkDOMListeners(1);

    return function () {
      checkDOMListeners(-1);
      unlisten();
    };
  };

  var history = {
    length: globalHistory.length,
    action: 'POP',
    location: initialLocation,
    createHref: createHref,
    push: push,
    replace: replace,
    go: go,
    goBack: goBack,
    goForward: goForward,
    block: block,
    listen: listen
  };

  return history;
};

exports.default = createHashHistory;

/***/ }),

/***/ 435:
/*!**********************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/MemoryRouter.js ***!
  \**********************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _MemoryRouter = __webpack_require__(/*! react-router/es/MemoryRouter */ 436);

var _MemoryRouter2 = _interopRequireDefault(_MemoryRouter);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = _MemoryRouter2.default; // Written in this round about way for babel-transform-imports

/***/ }),

/***/ 436:
/*!******************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router/es/MemoryRouter.js ***!
  \******************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _createMemoryHistory = __webpack_require__(/*! history/createMemoryHistory */ 437);

var _createMemoryHistory2 = _interopRequireDefault(_createMemoryHistory);

var _Router = __webpack_require__(/*! ./Router */ 84);

var _Router2 = _interopRequireDefault(_Router);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

/**
 * The public API for a <Router> that stores location in memory.
 */

var MemoryRouter = function (_React$Component) {
  _inherits(MemoryRouter, _React$Component);

  function MemoryRouter() {
    var _temp, _this, _ret;

    _classCallCheck(this, MemoryRouter);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, _React$Component.call.apply(_React$Component, [this].concat(args))), _this), _this.history = (0, _createMemoryHistory2.default)(_this.props), _temp), _possibleConstructorReturn(_this, _ret);
  }

  MemoryRouter.prototype.componentWillMount = function componentWillMount() {
    (0, _warning2.default)(!this.props.history, '<MemoryRouter> ignores the history prop. To use a custom history, ' + 'use `import { Router }` instead of `import { MemoryRouter as Router }`.');
  };

  MemoryRouter.prototype.render = function render() {
    return _react2.default.createElement(_Router2.default, { history: this.history, children: this.props.children });
  };

  return MemoryRouter;
}(_react2.default.Component);

MemoryRouter.propTypes = {
  initialEntries: _propTypes2.default.array,
  initialIndex: _propTypes2.default.number,
  getUserConfirmation: _propTypes2.default.func,
  keyLength: _propTypes2.default.number,
  children: _propTypes2.default.node
};

exports.default = MemoryRouter;

/***/ }),

/***/ 437:
/*!*****************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/createMemoryHistory.js ***!
  \*****************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

exports.__esModule = true;

var _typeof = typeof Symbol === "function" && _typeof2(Symbol.iterator) === "symbol" ? function (obj) {
  return typeof obj === "undefined" ? "undefined" : _typeof2(obj);
} : function (obj) {
  return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof2(obj);
};

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _PathUtils = __webpack_require__(/*! ./PathUtils */ 28);

var _LocationUtils = __webpack_require__(/*! ./LocationUtils */ 81);

var _createTransitionManager = __webpack_require__(/*! ./createTransitionManager */ 82);

var _createTransitionManager2 = _interopRequireDefault(_createTransitionManager);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var clamp = function clamp(n, lowerBound, upperBound) {
  return Math.min(Math.max(n, lowerBound), upperBound);
};

/**
 * Creates a history object that stores locations in memory.
 */
var createMemoryHistory = function createMemoryHistory() {
  var props = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};
  var getUserConfirmation = props.getUserConfirmation,
      _props$initialEntries = props.initialEntries,
      initialEntries = _props$initialEntries === undefined ? ['/'] : _props$initialEntries,
      _props$initialIndex = props.initialIndex,
      initialIndex = _props$initialIndex === undefined ? 0 : _props$initialIndex,
      _props$keyLength = props.keyLength,
      keyLength = _props$keyLength === undefined ? 6 : _props$keyLength;

  var transitionManager = (0, _createTransitionManager2.default)();

  var setState = function setState(nextState) {
    _extends(history, nextState);

    history.length = history.entries.length;

    transitionManager.notifyListeners(history.location, history.action);
  };

  var createKey = function createKey() {
    return Math.random().toString(36).substr(2, keyLength);
  };

  var index = clamp(initialIndex, 0, initialEntries.length - 1);
  var entries = initialEntries.map(function (entry) {
    return typeof entry === 'string' ? (0, _LocationUtils.createLocation)(entry, undefined, createKey()) : (0, _LocationUtils.createLocation)(entry, undefined, entry.key || createKey());
  });

  // Public interface

  var createHref = _PathUtils.createPath;

  var push = function push(path, state) {
    (0, _warning2.default)(!((typeof path === 'undefined' ? 'undefined' : _typeof(path)) === 'object' && path.state !== undefined && state !== undefined), 'You should avoid providing a 2nd state argument to push when the 1st ' + 'argument is a location-like object that already has state; it is ignored');

    var action = 'PUSH';
    var location = (0, _LocationUtils.createLocation)(path, state, createKey(), history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      var prevIndex = history.index;
      var nextIndex = prevIndex + 1;

      var nextEntries = history.entries.slice(0);
      if (nextEntries.length > nextIndex) {
        nextEntries.splice(nextIndex, nextEntries.length - nextIndex, location);
      } else {
        nextEntries.push(location);
      }

      setState({
        action: action,
        location: location,
        index: nextIndex,
        entries: nextEntries
      });
    });
  };

  var replace = function replace(path, state) {
    (0, _warning2.default)(!((typeof path === 'undefined' ? 'undefined' : _typeof(path)) === 'object' && path.state !== undefined && state !== undefined), 'You should avoid providing a 2nd state argument to replace when the 1st ' + 'argument is a location-like object that already has state; it is ignored');

    var action = 'REPLACE';
    var location = (0, _LocationUtils.createLocation)(path, state, createKey(), history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      history.entries[history.index] = location;

      setState({ action: action, location: location });
    });
  };

  var go = function go(n) {
    var nextIndex = clamp(history.index + n, 0, history.entries.length - 1);

    var action = 'POP';
    var location = history.entries[nextIndex];

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (ok) {
        setState({
          action: action,
          location: location,
          index: nextIndex
        });
      } else {
        // Mimic the behavior of DOM histories by
        // causing a render after a cancelled POP.
        setState();
      }
    });
  };

  var goBack = function goBack() {
    return go(-1);
  };

  var goForward = function goForward() {
    return go(1);
  };

  var canGo = function canGo(n) {
    var nextIndex = history.index + n;
    return nextIndex >= 0 && nextIndex < history.entries.length;
  };

  var block = function block() {
    var prompt = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;
    return transitionManager.setPrompt(prompt);
  };

  var listen = function listen(listener) {
    return transitionManager.appendListener(listener);
  };

  var history = {
    length: entries.length,
    action: 'POP',
    location: entries[index],
    index: index,
    entries: entries,
    createHref: createHref,
    push: push,
    replace: replace,
    go: go,
    goBack: goBack,
    goForward: goForward,
    canGo: canGo,
    block: block,
    listen: listen
  };

  return history;
};

exports.default = createMemoryHistory;

/***/ }),

/***/ 438:
/*!*****************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/NavLink.js ***!
  \*****************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _Route = __webpack_require__(/*! ./Route */ 190);

var _Route2 = _interopRequireDefault(_Route);

var _Link = __webpack_require__(/*! ./Link */ 189);

var _Link2 = _interopRequireDefault(_Link);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var _typeof = typeof Symbol === "function" && _typeof2(Symbol.iterator) === "symbol" ? function (obj) {
  return typeof obj === "undefined" ? "undefined" : _typeof2(obj);
} : function (obj) {
  return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof2(obj);
};

function _objectWithoutProperties(obj, keys) {
  var target = {};for (var i in obj) {
    if (keys.indexOf(i) >= 0) continue;if (!Object.prototype.hasOwnProperty.call(obj, i)) continue;target[i] = obj[i];
  }return target;
}

/**
 * A <Link> wrapper that knows if it's "active" or not.
 */
var NavLink = function NavLink(_ref) {
  var to = _ref.to,
      exact = _ref.exact,
      strict = _ref.strict,
      location = _ref.location,
      activeClassName = _ref.activeClassName,
      className = _ref.className,
      activeStyle = _ref.activeStyle,
      style = _ref.style,
      getIsActive = _ref.isActive,
      ariaCurrent = _ref.ariaCurrent,
      rest = _objectWithoutProperties(_ref, ['to', 'exact', 'strict', 'location', 'activeClassName', 'className', 'activeStyle', 'style', 'isActive', 'ariaCurrent']);

  return _react2.default.createElement(_Route2.default, {
    path: (typeof to === 'undefined' ? 'undefined' : _typeof(to)) === 'object' ? to.pathname : to,
    exact: exact,
    strict: strict,
    location: location,
    children: function children(_ref2) {
      var location = _ref2.location,
          match = _ref2.match;

      var isActive = !!(getIsActive ? getIsActive(match, location) : match);

      return _react2.default.createElement(_Link2.default, _extends({
        to: to,
        className: isActive ? [className, activeClassName].filter(function (i) {
          return i;
        }).join(' ') : className,
        style: isActive ? _extends({}, style, activeStyle) : style,
        'aria-current': isActive && ariaCurrent
      }, rest));
    }
  });
};

NavLink.propTypes = {
  to: _Link2.default.propTypes.to,
  exact: _propTypes2.default.bool,
  strict: _propTypes2.default.bool,
  location: _propTypes2.default.object,
  activeClassName: _propTypes2.default.string,
  className: _propTypes2.default.string,
  activeStyle: _propTypes2.default.object,
  style: _propTypes2.default.object,
  isActive: _propTypes2.default.func,
  ariaCurrent: _propTypes2.default.oneOf(['page', 'step', 'location', 'true'])
};

NavLink.defaultProps = {
  activeClassName: 'active',
  ariaCurrent: 'true'
};

exports.default = NavLink;

/***/ }),

/***/ 439:
/*!**********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/path-to-regexp/index.js ***!
  \**********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var isarray = __webpack_require__(/*! isarray */ 440);

/**
 * Expose `pathToRegexp`.
 */
module.exports = pathToRegexp;
module.exports.parse = parse;
module.exports.compile = compile;
module.exports.tokensToFunction = tokensToFunction;
module.exports.tokensToRegExp = tokensToRegExp;

/**
 * The main path matching regexp utility.
 *
 * @type {RegExp}
 */
var PATH_REGEXP = new RegExp([
// Match escaped characters that would otherwise appear in future matches.
// This allows the user to escape special characters that won't transform.
'(\\\\.)',
// Match Express-style parameters and un-named parameters with a prefix
// and optional suffixes. Matches appear as:
//
// "/:test(\\d+)?" => ["/", "test", "\d+", undefined, "?", undefined]
// "/route(\\d+)"  => [undefined, undefined, undefined, "\d+", undefined, undefined]
// "/*"            => ["/", undefined, undefined, undefined, undefined, "*"]
'([\\/.])?(?:(?:\\:(\\w+)(?:\\(((?:\\\\.|[^\\\\()])+)\\))?|\\(((?:\\\\.|[^\\\\()])+)\\))([+*?])?|(\\*))'].join('|'), 'g');

/**
 * Parse a string for the raw tokens.
 *
 * @param  {string}  str
 * @param  {Object=} options
 * @return {!Array}
 */
function parse(str, options) {
  var tokens = [];
  var key = 0;
  var index = 0;
  var path = '';
  var defaultDelimiter = options && options.delimiter || '/';
  var res;

  while ((res = PATH_REGEXP.exec(str)) != null) {
    var m = res[0];
    var escaped = res[1];
    var offset = res.index;
    path += str.slice(index, offset);
    index = offset + m.length;

    // Ignore already escaped sequences.
    if (escaped) {
      path += escaped[1];
      continue;
    }

    var next = str[index];
    var prefix = res[2];
    var name = res[3];
    var capture = res[4];
    var group = res[5];
    var modifier = res[6];
    var asterisk = res[7];

    // Push the current path onto the tokens.
    if (path) {
      tokens.push(path);
      path = '';
    }

    var partial = prefix != null && next != null && next !== prefix;
    var repeat = modifier === '+' || modifier === '*';
    var optional = modifier === '?' || modifier === '*';
    var delimiter = res[2] || defaultDelimiter;
    var pattern = capture || group;

    tokens.push({
      name: name || key++,
      prefix: prefix || '',
      delimiter: delimiter,
      optional: optional,
      repeat: repeat,
      partial: partial,
      asterisk: !!asterisk,
      pattern: pattern ? escapeGroup(pattern) : asterisk ? '.*' : '[^' + escapeString(delimiter) + ']+?'
    });
  }

  // Match any characters still remaining.
  if (index < str.length) {
    path += str.substr(index);
  }

  // If the path exists, push it onto the end.
  if (path) {
    tokens.push(path);
  }

  return tokens;
}

/**
 * Compile a string to a template function for the path.
 *
 * @param  {string}             str
 * @param  {Object=}            options
 * @return {!function(Object=, Object=)}
 */
function compile(str, options) {
  return tokensToFunction(parse(str, options));
}

/**
 * Prettier encoding of URI path segments.
 *
 * @param  {string}
 * @return {string}
 */
function encodeURIComponentPretty(str) {
  return encodeURI(str).replace(/[\/?#]/g, function (c) {
    return '%' + c.charCodeAt(0).toString(16).toUpperCase();
  });
}

/**
 * Encode the asterisk parameter. Similar to `pretty`, but allows slashes.
 *
 * @param  {string}
 * @return {string}
 */
function encodeAsterisk(str) {
  return encodeURI(str).replace(/[?#]/g, function (c) {
    return '%' + c.charCodeAt(0).toString(16).toUpperCase();
  });
}

/**
 * Expose a method for transforming tokens into the path function.
 */
function tokensToFunction(tokens) {
  // Compile all the tokens into regexps.
  var matches = new Array(tokens.length);

  // Compile all the patterns before compilation.
  for (var i = 0; i < tokens.length; i++) {
    if (_typeof(tokens[i]) === 'object') {
      matches[i] = new RegExp('^(?:' + tokens[i].pattern + ')$');
    }
  }

  return function (obj, opts) {
    var path = '';
    var data = obj || {};
    var options = opts || {};
    var encode = options.pretty ? encodeURIComponentPretty : encodeURIComponent;

    for (var i = 0; i < tokens.length; i++) {
      var token = tokens[i];

      if (typeof token === 'string') {
        path += token;

        continue;
      }

      var value = data[token.name];
      var segment;

      if (value == null) {
        if (token.optional) {
          // Prepend partial segment prefixes.
          if (token.partial) {
            path += token.prefix;
          }

          continue;
        } else {
          throw new TypeError('Expected "' + token.name + '" to be defined');
        }
      }

      if (isarray(value)) {
        if (!token.repeat) {
          throw new TypeError('Expected "' + token.name + '" to not repeat, but received `' + JSON.stringify(value) + '`');
        }

        if (value.length === 0) {
          if (token.optional) {
            continue;
          } else {
            throw new TypeError('Expected "' + token.name + '" to not be empty');
          }
        }

        for (var j = 0; j < value.length; j++) {
          segment = encode(value[j]);

          if (!matches[i].test(segment)) {
            throw new TypeError('Expected all "' + token.name + '" to match "' + token.pattern + '", but received `' + JSON.stringify(segment) + '`');
          }

          path += (j === 0 ? token.prefix : token.delimiter) + segment;
        }

        continue;
      }

      segment = token.asterisk ? encodeAsterisk(value) : encode(value);

      if (!matches[i].test(segment)) {
        throw new TypeError('Expected "' + token.name + '" to match "' + token.pattern + '", but received "' + segment + '"');
      }

      path += token.prefix + segment;
    }

    return path;
  };
}

/**
 * Escape a regular expression string.
 *
 * @param  {string} str
 * @return {string}
 */
function escapeString(str) {
  return str.replace(/([.+*?=^!:${}()[\]|\/\\])/g, '\\$1');
}

/**
 * Escape the capturing group by escaping special characters and meaning.
 *
 * @param  {string} group
 * @return {string}
 */
function escapeGroup(group) {
  return group.replace(/([=!:$\/()])/g, '\\$1');
}

/**
 * Attach the keys as a property of the regexp.
 *
 * @param  {!RegExp} re
 * @param  {Array}   keys
 * @return {!RegExp}
 */
function attachKeys(re, keys) {
  re.keys = keys;
  return re;
}

/**
 * Get the flags for a regexp from the options.
 *
 * @param  {Object} options
 * @return {string}
 */
function flags(options) {
  return options.sensitive ? '' : 'i';
}

/**
 * Pull out keys from a regexp.
 *
 * @param  {!RegExp} path
 * @param  {!Array}  keys
 * @return {!RegExp}
 */
function regexpToRegexp(path, keys) {
  // Use a negative lookahead to match only capturing groups.
  var groups = path.source.match(/\((?!\?)/g);

  if (groups) {
    for (var i = 0; i < groups.length; i++) {
      keys.push({
        name: i,
        prefix: null,
        delimiter: null,
        optional: false,
        repeat: false,
        partial: false,
        asterisk: false,
        pattern: null
      });
    }
  }

  return attachKeys(path, keys);
}

/**
 * Transform an array into a regexp.
 *
 * @param  {!Array}  path
 * @param  {Array}   keys
 * @param  {!Object} options
 * @return {!RegExp}
 */
function arrayToRegexp(path, keys, options) {
  var parts = [];

  for (var i = 0; i < path.length; i++) {
    parts.push(pathToRegexp(path[i], keys, options).source);
  }

  var regexp = new RegExp('(?:' + parts.join('|') + ')', flags(options));

  return attachKeys(regexp, keys);
}

/**
 * Create a path regexp from string input.
 *
 * @param  {string}  path
 * @param  {!Array}  keys
 * @param  {!Object} options
 * @return {!RegExp}
 */
function stringToRegexp(path, keys, options) {
  return tokensToRegExp(parse(path, options), keys, options);
}

/**
 * Expose a function for taking tokens and returning a RegExp.
 *
 * @param  {!Array}          tokens
 * @param  {(Array|Object)=} keys
 * @param  {Object=}         options
 * @return {!RegExp}
 */
function tokensToRegExp(tokens, keys, options) {
  if (!isarray(keys)) {
    options = /** @type {!Object} */keys || options;
    keys = [];
  }

  options = options || {};

  var strict = options.strict;
  var end = options.end !== false;
  var route = '';

  // Iterate over the tokens and create our regexp string.
  for (var i = 0; i < tokens.length; i++) {
    var token = tokens[i];

    if (typeof token === 'string') {
      route += escapeString(token);
    } else {
      var prefix = escapeString(token.prefix);
      var capture = '(?:' + token.pattern + ')';

      keys.push(token);

      if (token.repeat) {
        capture += '(?:' + prefix + capture + ')*';
      }

      if (token.optional) {
        if (!token.partial) {
          capture = '(?:' + prefix + '(' + capture + '))?';
        } else {
          capture = prefix + '(' + capture + ')?';
        }
      } else {
        capture = prefix + '(' + capture + ')';
      }

      route += capture;
    }
  }

  var delimiter = escapeString(options.delimiter || '/');
  var endsWithDelimiter = route.slice(-delimiter.length) === delimiter;

  // In non-strict mode we allow a slash at the end of match. If the path to
  // match already ends with a slash, we remove it for consistency. The slash
  // is valid at the end of a path match, not in the middle. This is important
  // in non-ending mode, where "/test/" shouldn't match "/test//route".
  if (!strict) {
    route = (endsWithDelimiter ? route.slice(0, -delimiter.length) : route) + '(?:' + delimiter + '(?=$))?';
  }

  if (end) {
    route += '$';
  } else {
    // In non-ending mode, we need the capturing groups to match as much as
    // possible by using a positive lookahead to the end or next path segment.
    route += strict && endsWithDelimiter ? '' : '(?=' + delimiter + '|$)';
  }

  return attachKeys(new RegExp('^' + route, flags(options)), keys);
}

/**
 * Normalize the given path string, returning a regular expression.
 *
 * An empty array can be passed in for the keys, which will hold the
 * placeholder key descriptions. For example, using `/user/:id`, `keys` will
 * contain `[{ name: 'id', delimiter: '/', optional: false, repeat: false }]`.
 *
 * @param  {(string|RegExp|Array)} path
 * @param  {(Array|Object)=}       keys
 * @param  {Object=}               options
 * @return {!RegExp}
 */
function pathToRegexp(path, keys, options) {
  if (!isarray(keys)) {
    options = /** @type {!Object} */keys || options;
    keys = [];
  }

  options = options || {};

  if (path instanceof RegExp) {
    return regexpToRegexp(path, /** @type {!Array} */keys);
  }

  if (isarray(path)) {
    return arrayToRegexp( /** @type {!Array} */path, /** @type {!Array} */keys, options);
  }

  return stringToRegexp( /** @type {string} */path, /** @type {!Array} */keys, options);
}

/***/ }),

/***/ 440:
/*!***************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/isarray/index.js ***!
  \***************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = Array.isArray || function (arr) {
  return Object.prototype.toString.call(arr) == '[object Array]';
};

/***/ }),

/***/ 441:
/*!****************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/Prompt.js ***!
  \****************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _Prompt = __webpack_require__(/*! react-router/es/Prompt */ 442);

var _Prompt2 = _interopRequireDefault(_Prompt);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = _Prompt2.default; // Written in this round about way for babel-transform-imports

/***/ }),

/***/ 442:
/*!************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router/es/Prompt.js ***!
  \************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _invariant = __webpack_require__(/*! invariant */ 8);

var _invariant2 = _interopRequireDefault(_invariant);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

/**
 * The public API for prompting the user before navigating away
 * from a screen with a component.
 */

var Prompt = function (_React$Component) {
  _inherits(Prompt, _React$Component);

  function Prompt() {
    _classCallCheck(this, Prompt);

    return _possibleConstructorReturn(this, _React$Component.apply(this, arguments));
  }

  Prompt.prototype.enable = function enable(message) {
    if (this.unblock) this.unblock();

    this.unblock = this.context.router.history.block(message);
  };

  Prompt.prototype.disable = function disable() {
    if (this.unblock) {
      this.unblock();
      this.unblock = null;
    }
  };

  Prompt.prototype.componentWillMount = function componentWillMount() {
    (0, _invariant2.default)(this.context.router, 'You should not use <Prompt> outside a <Router>');

    if (this.props.when) this.enable(this.props.message);
  };

  Prompt.prototype.componentWillReceiveProps = function componentWillReceiveProps(nextProps) {
    if (nextProps.when) {
      if (!this.props.when || this.props.message !== nextProps.message) this.enable(nextProps.message);
    } else {
      this.disable();
    }
  };

  Prompt.prototype.componentWillUnmount = function componentWillUnmount() {
    this.disable();
  };

  Prompt.prototype.render = function render() {
    return null;
  };

  return Prompt;
}(_react2.default.Component);

Prompt.propTypes = {
  when: _propTypes2.default.bool,
  message: _propTypes2.default.oneOfType([_propTypes2.default.func, _propTypes2.default.string]).isRequired
};
Prompt.defaultProps = {
  when: true
};
Prompt.contextTypes = {
  router: _propTypes2.default.shape({
    history: _propTypes2.default.shape({
      block: _propTypes2.default.func.isRequired
    }).isRequired
  }).isRequired
};

exports.default = Prompt;

/***/ }),

/***/ 443:
/*!******************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/Redirect.js ***!
  \******************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _Redirect = __webpack_require__(/*! react-router/es/Redirect */ 444);

var _Redirect2 = _interopRequireDefault(_Redirect);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = _Redirect2.default; // Written in this round about way for babel-transform-imports

/***/ }),

/***/ 444:
/*!**************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router/es/Redirect.js ***!
  \**************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _invariant = __webpack_require__(/*! invariant */ 8);

var _invariant2 = _interopRequireDefault(_invariant);

var _history = __webpack_require__(/*! history */ 445);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

/**
 * The public API for updating the location programmatically
 * with a component.
 */

var Redirect = function (_React$Component) {
  _inherits(Redirect, _React$Component);

  function Redirect() {
    _classCallCheck(this, Redirect);

    return _possibleConstructorReturn(this, _React$Component.apply(this, arguments));
  }

  Redirect.prototype.isStatic = function isStatic() {
    return this.context.router && this.context.router.staticContext;
  };

  Redirect.prototype.componentWillMount = function componentWillMount() {
    (0, _invariant2.default)(this.context.router, 'You should not use <Redirect> outside a <Router>');

    if (this.isStatic()) this.perform();
  };

  Redirect.prototype.componentDidMount = function componentDidMount() {
    if (!this.isStatic()) this.perform();
  };

  Redirect.prototype.componentDidUpdate = function componentDidUpdate(prevProps) {
    var prevTo = (0, _history.createLocation)(prevProps.to);
    var nextTo = (0, _history.createLocation)(this.props.to);

    if ((0, _history.locationsAreEqual)(prevTo, nextTo)) {
      (0, _warning2.default)(false, 'You tried to redirect to the same route you\'re currently on: ' + ('"' + nextTo.pathname + nextTo.search + '"'));
      return;
    }

    this.perform();
  };

  Redirect.prototype.perform = function perform() {
    var history = this.context.router.history;
    var _props = this.props,
        push = _props.push,
        to = _props.to;

    if (push) {
      history.push(to);
    } else {
      history.replace(to);
    }
  };

  Redirect.prototype.render = function render() {
    return null;
  };

  return Redirect;
}(_react2.default.Component);

Redirect.propTypes = {
  push: _propTypes2.default.bool,
  from: _propTypes2.default.string,
  to: _propTypes2.default.oneOfType([_propTypes2.default.string, _propTypes2.default.object]).isRequired
};
Redirect.defaultProps = {
  push: false
};
Redirect.contextTypes = {
  router: _propTypes2.default.shape({
    history: _propTypes2.default.shape({
      push: _propTypes2.default.func.isRequired,
      replace: _propTypes2.default.func.isRequired
    }).isRequired,
    staticContext: _propTypes2.default.object
  }).isRequired
};

exports.default = Redirect;

/***/ }),

/***/ 445:
/*!******************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/es/index.js ***!
  \******************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.createPath = exports.parsePath = exports.locationsAreEqual = exports.createLocation = exports.createMemoryHistory = exports.createHashHistory = exports.createBrowserHistory = undefined;

var _LocationUtils = __webpack_require__(/*! ./LocationUtils */ 43);

Object.defineProperty(exports, 'createLocation', {
  enumerable: true,
  get: function get() {
    return _LocationUtils.createLocation;
  }
});
Object.defineProperty(exports, 'locationsAreEqual', {
  enumerable: true,
  get: function get() {
    return _LocationUtils.locationsAreEqual;
  }
});

var _PathUtils = __webpack_require__(/*! ./PathUtils */ 29);

Object.defineProperty(exports, 'parsePath', {
  enumerable: true,
  get: function get() {
    return _PathUtils.parsePath;
  }
});
Object.defineProperty(exports, 'createPath', {
  enumerable: true,
  get: function get() {
    return _PathUtils.createPath;
  }
});

var _createBrowserHistory2 = __webpack_require__(/*! ./createBrowserHistory */ 446);

var _createBrowserHistory3 = _interopRequireDefault(_createBrowserHistory2);

var _createHashHistory2 = __webpack_require__(/*! ./createHashHistory */ 447);

var _createHashHistory3 = _interopRequireDefault(_createHashHistory2);

var _createMemoryHistory2 = __webpack_require__(/*! ./createMemoryHistory */ 448);

var _createMemoryHistory3 = _interopRequireDefault(_createMemoryHistory2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.createBrowserHistory = _createBrowserHistory3.default;
exports.createHashHistory = _createHashHistory3.default;
exports.createMemoryHistory = _createMemoryHistory3.default;

/***/ }),

/***/ 446:
/*!*********************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/es/createBrowserHistory.js ***!
  \*********************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _invariant = __webpack_require__(/*! invariant */ 8);

var _invariant2 = _interopRequireDefault(_invariant);

var _LocationUtils = __webpack_require__(/*! ./LocationUtils */ 43);

var _PathUtils = __webpack_require__(/*! ./PathUtils */ 29);

var _createTransitionManager = __webpack_require__(/*! ./createTransitionManager */ 86);

var _createTransitionManager2 = _interopRequireDefault(_createTransitionManager);

var _DOMUtils = __webpack_require__(/*! ./DOMUtils */ 192);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _typeof = typeof Symbol === "function" && _typeof2(Symbol.iterator) === "symbol" ? function (obj) {
  return typeof obj === "undefined" ? "undefined" : _typeof2(obj);
} : function (obj) {
  return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof2(obj);
};

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var PopStateEvent = 'popstate';
var HashChangeEvent = 'hashchange';

var getHistoryState = function getHistoryState() {
  try {
    return window.history.state || {};
  } catch (e) {
    // IE 11 sometimes throws when accessing window.history.state
    // See https://github.com/ReactTraining/history/pull/289
    return {};
  }
};

/**
 * Creates a history object that uses the HTML5 history API including
 * pushState, replaceState, and the popstate event.
 */
var createBrowserHistory = function createBrowserHistory() {
  var props = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};

  (0, _invariant2.default)(_DOMUtils.canUseDOM, 'Browser history needs a DOM');

  var globalHistory = window.history;
  var canUseHistory = (0, _DOMUtils.supportsHistory)();
  var needsHashChangeListener = !(0, _DOMUtils.supportsPopStateOnHashChange)();

  var _props$forceRefresh = props.forceRefresh,
      forceRefresh = _props$forceRefresh === undefined ? false : _props$forceRefresh,
      _props$getUserConfirm = props.getUserConfirmation,
      getUserConfirmation = _props$getUserConfirm === undefined ? _DOMUtils.getConfirmation : _props$getUserConfirm,
      _props$keyLength = props.keyLength,
      keyLength = _props$keyLength === undefined ? 6 : _props$keyLength;

  var basename = props.basename ? (0, _PathUtils.stripTrailingSlash)((0, _PathUtils.addLeadingSlash)(props.basename)) : '';

  var getDOMLocation = function getDOMLocation(historyState) {
    var _ref = historyState || {},
        key = _ref.key,
        state = _ref.state;

    var _window$location = window.location,
        pathname = _window$location.pathname,
        search = _window$location.search,
        hash = _window$location.hash;

    var path = pathname + search + hash;

    (0, _warning2.default)(!basename || (0, _PathUtils.hasBasename)(path, basename), 'You are attempting to use a basename on a page whose URL path does not begin ' + 'with the basename. Expected path "' + path + '" to begin with "' + basename + '".');

    if (basename) path = (0, _PathUtils.stripBasename)(path, basename);

    return (0, _LocationUtils.createLocation)(path, state, key);
  };

  var createKey = function createKey() {
    return Math.random().toString(36).substr(2, keyLength);
  };

  var transitionManager = (0, _createTransitionManager2.default)();

  var setState = function setState(nextState) {
    _extends(history, nextState);

    history.length = globalHistory.length;

    transitionManager.notifyListeners(history.location, history.action);
  };

  var handlePopState = function handlePopState(event) {
    // Ignore extraneous popstate events in WebKit.
    if ((0, _DOMUtils.isExtraneousPopstateEvent)(event)) return;

    handlePop(getDOMLocation(event.state));
  };

  var handleHashChange = function handleHashChange() {
    handlePop(getDOMLocation(getHistoryState()));
  };

  var forceNextPop = false;

  var handlePop = function handlePop(location) {
    if (forceNextPop) {
      forceNextPop = false;
      setState();
    } else {
      var action = 'POP';

      transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
        if (ok) {
          setState({ action: action, location: location });
        } else {
          revertPop(location);
        }
      });
    }
  };

  var revertPop = function revertPop(fromLocation) {
    var toLocation = history.location;

    // TODO: We could probably make this more reliable by
    // keeping a list of keys we've seen in sessionStorage.
    // Instead, we just default to 0 for keys we don't know.

    var toIndex = allKeys.indexOf(toLocation.key);

    if (toIndex === -1) toIndex = 0;

    var fromIndex = allKeys.indexOf(fromLocation.key);

    if (fromIndex === -1) fromIndex = 0;

    var delta = toIndex - fromIndex;

    if (delta) {
      forceNextPop = true;
      go(delta);
    }
  };

  var initialLocation = getDOMLocation(getHistoryState());
  var allKeys = [initialLocation.key];

  // Public interface

  var createHref = function createHref(location) {
    return basename + (0, _PathUtils.createPath)(location);
  };

  var push = function push(path, state) {
    (0, _warning2.default)(!((typeof path === 'undefined' ? 'undefined' : _typeof(path)) === 'object' && path.state !== undefined && state !== undefined), 'You should avoid providing a 2nd state argument to push when the 1st ' + 'argument is a location-like object that already has state; it is ignored');

    var action = 'PUSH';
    var location = (0, _LocationUtils.createLocation)(path, state, createKey(), history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      var href = createHref(location);
      var key = location.key,
          state = location.state;

      if (canUseHistory) {
        globalHistory.pushState({ key: key, state: state }, null, href);

        if (forceRefresh) {
          window.location.href = href;
        } else {
          var prevIndex = allKeys.indexOf(history.location.key);
          var nextKeys = allKeys.slice(0, prevIndex === -1 ? 0 : prevIndex + 1);

          nextKeys.push(location.key);
          allKeys = nextKeys;

          setState({ action: action, location: location });
        }
      } else {
        (0, _warning2.default)(state === undefined, 'Browser history cannot push state in browsers that do not support HTML5 history');

        window.location.href = href;
      }
    });
  };

  var replace = function replace(path, state) {
    (0, _warning2.default)(!((typeof path === 'undefined' ? 'undefined' : _typeof(path)) === 'object' && path.state !== undefined && state !== undefined), 'You should avoid providing a 2nd state argument to replace when the 1st ' + 'argument is a location-like object that already has state; it is ignored');

    var action = 'REPLACE';
    var location = (0, _LocationUtils.createLocation)(path, state, createKey(), history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      var href = createHref(location);
      var key = location.key,
          state = location.state;

      if (canUseHistory) {
        globalHistory.replaceState({ key: key, state: state }, null, href);

        if (forceRefresh) {
          window.location.replace(href);
        } else {
          var prevIndex = allKeys.indexOf(history.location.key);

          if (prevIndex !== -1) allKeys[prevIndex] = location.key;

          setState({ action: action, location: location });
        }
      } else {
        (0, _warning2.default)(state === undefined, 'Browser history cannot replace state in browsers that do not support HTML5 history');

        window.location.replace(href);
      }
    });
  };

  var go = function go(n) {
    globalHistory.go(n);
  };

  var goBack = function goBack() {
    return go(-1);
  };

  var goForward = function goForward() {
    return go(1);
  };

  var listenerCount = 0;

  var checkDOMListeners = function checkDOMListeners(delta) {
    listenerCount += delta;

    if (listenerCount === 1) {
      (0, _DOMUtils.addEventListener)(window, PopStateEvent, handlePopState);

      if (needsHashChangeListener) (0, _DOMUtils.addEventListener)(window, HashChangeEvent, handleHashChange);
    } else if (listenerCount === 0) {
      (0, _DOMUtils.removeEventListener)(window, PopStateEvent, handlePopState);

      if (needsHashChangeListener) (0, _DOMUtils.removeEventListener)(window, HashChangeEvent, handleHashChange);
    }
  };

  var isBlocked = false;

  var block = function block() {
    var prompt = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;

    var unblock = transitionManager.setPrompt(prompt);

    if (!isBlocked) {
      checkDOMListeners(1);
      isBlocked = true;
    }

    return function () {
      if (isBlocked) {
        isBlocked = false;
        checkDOMListeners(-1);
      }

      return unblock();
    };
  };

  var listen = function listen(listener) {
    var unlisten = transitionManager.appendListener(listener);
    checkDOMListeners(1);

    return function () {
      checkDOMListeners(-1);
      unlisten();
    };
  };

  var history = {
    length: globalHistory.length,
    action: 'POP',
    location: initialLocation,
    createHref: createHref,
    push: push,
    replace: replace,
    go: go,
    goBack: goBack,
    goForward: goForward,
    block: block,
    listen: listen
  };

  return history;
};

exports.default = createBrowserHistory;

/***/ }),

/***/ 447:
/*!******************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/es/createHashHistory.js ***!
  \******************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _invariant = __webpack_require__(/*! invariant */ 8);

var _invariant2 = _interopRequireDefault(_invariant);

var _LocationUtils = __webpack_require__(/*! ./LocationUtils */ 43);

var _PathUtils = __webpack_require__(/*! ./PathUtils */ 29);

var _createTransitionManager = __webpack_require__(/*! ./createTransitionManager */ 86);

var _createTransitionManager2 = _interopRequireDefault(_createTransitionManager);

var _DOMUtils = __webpack_require__(/*! ./DOMUtils */ 192);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var HashChangeEvent = 'hashchange';

var HashPathCoders = {
  hashbang: {
    encodePath: function encodePath(path) {
      return path.charAt(0) === '!' ? path : '!/' + (0, _PathUtils.stripLeadingSlash)(path);
    },
    decodePath: function decodePath(path) {
      return path.charAt(0) === '!' ? path.substr(1) : path;
    }
  },
  noslash: {
    encodePath: _PathUtils.stripLeadingSlash,
    decodePath: _PathUtils.addLeadingSlash
  },
  slash: {
    encodePath: _PathUtils.addLeadingSlash,
    decodePath: _PathUtils.addLeadingSlash
  }
};

var getHashPath = function getHashPath() {
  // We can't use window.location.hash here because it's not
  // consistent across browsers - Firefox will pre-decode it!
  var href = window.location.href;
  var hashIndex = href.indexOf('#');
  return hashIndex === -1 ? '' : href.substring(hashIndex + 1);
};

var pushHashPath = function pushHashPath(path) {
  return window.location.hash = path;
};

var replaceHashPath = function replaceHashPath(path) {
  var hashIndex = window.location.href.indexOf('#');

  window.location.replace(window.location.href.slice(0, hashIndex >= 0 ? hashIndex : 0) + '#' + path);
};

var createHashHistory = function createHashHistory() {
  var props = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};

  (0, _invariant2.default)(_DOMUtils.canUseDOM, 'Hash history needs a DOM');

  var globalHistory = window.history;
  var canGoWithoutReload = (0, _DOMUtils.supportsGoWithoutReloadUsingHash)();

  var _props$getUserConfirm = props.getUserConfirmation,
      getUserConfirmation = _props$getUserConfirm === undefined ? _DOMUtils.getConfirmation : _props$getUserConfirm,
      _props$hashType = props.hashType,
      hashType = _props$hashType === undefined ? 'slash' : _props$hashType;

  var basename = props.basename ? (0, _PathUtils.stripTrailingSlash)((0, _PathUtils.addLeadingSlash)(props.basename)) : '';

  var _HashPathCoders$hashT = HashPathCoders[hashType],
      encodePath = _HashPathCoders$hashT.encodePath,
      decodePath = _HashPathCoders$hashT.decodePath;

  var getDOMLocation = function getDOMLocation() {
    var path = decodePath(getHashPath());

    (0, _warning2.default)(!basename || (0, _PathUtils.hasBasename)(path, basename), 'You are attempting to use a basename on a page whose URL path does not begin ' + 'with the basename. Expected path "' + path + '" to begin with "' + basename + '".');

    if (basename) path = (0, _PathUtils.stripBasename)(path, basename);

    return (0, _LocationUtils.createLocation)(path);
  };

  var transitionManager = (0, _createTransitionManager2.default)();

  var setState = function setState(nextState) {
    _extends(history, nextState);

    history.length = globalHistory.length;

    transitionManager.notifyListeners(history.location, history.action);
  };

  var forceNextPop = false;
  var ignorePath = null;

  var handleHashChange = function handleHashChange() {
    var path = getHashPath();
    var encodedPath = encodePath(path);

    if (path !== encodedPath) {
      // Ensure we always have a properly-encoded hash.
      replaceHashPath(encodedPath);
    } else {
      var location = getDOMLocation();
      var prevLocation = history.location;

      if (!forceNextPop && (0, _LocationUtils.locationsAreEqual)(prevLocation, location)) return; // A hashchange doesn't always == location change.

      if (ignorePath === (0, _PathUtils.createPath)(location)) return; // Ignore this change; we already setState in push/replace.

      ignorePath = null;

      handlePop(location);
    }
  };

  var handlePop = function handlePop(location) {
    if (forceNextPop) {
      forceNextPop = false;
      setState();
    } else {
      var action = 'POP';

      transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
        if (ok) {
          setState({ action: action, location: location });
        } else {
          revertPop(location);
        }
      });
    }
  };

  var revertPop = function revertPop(fromLocation) {
    var toLocation = history.location;

    // TODO: We could probably make this more reliable by
    // keeping a list of paths we've seen in sessionStorage.
    // Instead, we just default to 0 for paths we don't know.

    var toIndex = allPaths.lastIndexOf((0, _PathUtils.createPath)(toLocation));

    if (toIndex === -1) toIndex = 0;

    var fromIndex = allPaths.lastIndexOf((0, _PathUtils.createPath)(fromLocation));

    if (fromIndex === -1) fromIndex = 0;

    var delta = toIndex - fromIndex;

    if (delta) {
      forceNextPop = true;
      go(delta);
    }
  };

  // Ensure the hash is encoded properly before doing anything else.
  var path = getHashPath();
  var encodedPath = encodePath(path);

  if (path !== encodedPath) replaceHashPath(encodedPath);

  var initialLocation = getDOMLocation();
  var allPaths = [(0, _PathUtils.createPath)(initialLocation)];

  // Public interface

  var createHref = function createHref(location) {
    return '#' + encodePath(basename + (0, _PathUtils.createPath)(location));
  };

  var push = function push(path, state) {
    (0, _warning2.default)(state === undefined, 'Hash history cannot push state; it is ignored');

    var action = 'PUSH';
    var location = (0, _LocationUtils.createLocation)(path, undefined, undefined, history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      var path = (0, _PathUtils.createPath)(location);
      var encodedPath = encodePath(basename + path);
      var hashChanged = getHashPath() !== encodedPath;

      if (hashChanged) {
        // We cannot tell if a hashchange was caused by a PUSH, so we'd
        // rather setState here and ignore the hashchange. The caveat here
        // is that other hash histories in the page will consider it a POP.
        ignorePath = path;
        pushHashPath(encodedPath);

        var prevIndex = allPaths.lastIndexOf((0, _PathUtils.createPath)(history.location));
        var nextPaths = allPaths.slice(0, prevIndex === -1 ? 0 : prevIndex + 1);

        nextPaths.push(path);
        allPaths = nextPaths;

        setState({ action: action, location: location });
      } else {
        (0, _warning2.default)(false, 'Hash history cannot PUSH the same path; a new entry will not be added to the history stack');

        setState();
      }
    });
  };

  var replace = function replace(path, state) {
    (0, _warning2.default)(state === undefined, 'Hash history cannot replace state; it is ignored');

    var action = 'REPLACE';
    var location = (0, _LocationUtils.createLocation)(path, undefined, undefined, history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      var path = (0, _PathUtils.createPath)(location);
      var encodedPath = encodePath(basename + path);
      var hashChanged = getHashPath() !== encodedPath;

      if (hashChanged) {
        // We cannot tell if a hashchange was caused by a REPLACE, so we'd
        // rather setState here and ignore the hashchange. The caveat here
        // is that other hash histories in the page will consider it a POP.
        ignorePath = path;
        replaceHashPath(encodedPath);
      }

      var prevIndex = allPaths.indexOf((0, _PathUtils.createPath)(history.location));

      if (prevIndex !== -1) allPaths[prevIndex] = path;

      setState({ action: action, location: location });
    });
  };

  var go = function go(n) {
    (0, _warning2.default)(canGoWithoutReload, 'Hash history go(n) causes a full page reload in this browser');

    globalHistory.go(n);
  };

  var goBack = function goBack() {
    return go(-1);
  };

  var goForward = function goForward() {
    return go(1);
  };

  var listenerCount = 0;

  var checkDOMListeners = function checkDOMListeners(delta) {
    listenerCount += delta;

    if (listenerCount === 1) {
      (0, _DOMUtils.addEventListener)(window, HashChangeEvent, handleHashChange);
    } else if (listenerCount === 0) {
      (0, _DOMUtils.removeEventListener)(window, HashChangeEvent, handleHashChange);
    }
  };

  var isBlocked = false;

  var block = function block() {
    var prompt = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;

    var unblock = transitionManager.setPrompt(prompt);

    if (!isBlocked) {
      checkDOMListeners(1);
      isBlocked = true;
    }

    return function () {
      if (isBlocked) {
        isBlocked = false;
        checkDOMListeners(-1);
      }

      return unblock();
    };
  };

  var listen = function listen(listener) {
    var unlisten = transitionManager.appendListener(listener);
    checkDOMListeners(1);

    return function () {
      checkDOMListeners(-1);
      unlisten();
    };
  };

  var history = {
    length: globalHistory.length,
    action: 'POP',
    location: initialLocation,
    createHref: createHref,
    push: push,
    replace: replace,
    go: go,
    goBack: goBack,
    goForward: goForward,
    block: block,
    listen: listen
  };

  return history;
};

exports.default = createHashHistory;

/***/ }),

/***/ 448:
/*!********************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/es/createMemoryHistory.js ***!
  \********************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _PathUtils = __webpack_require__(/*! ./PathUtils */ 29);

var _LocationUtils = __webpack_require__(/*! ./LocationUtils */ 43);

var _createTransitionManager = __webpack_require__(/*! ./createTransitionManager */ 86);

var _createTransitionManager2 = _interopRequireDefault(_createTransitionManager);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _typeof = typeof Symbol === "function" && _typeof2(Symbol.iterator) === "symbol" ? function (obj) {
  return typeof obj === "undefined" ? "undefined" : _typeof2(obj);
} : function (obj) {
  return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof2(obj);
};

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var clamp = function clamp(n, lowerBound, upperBound) {
  return Math.min(Math.max(n, lowerBound), upperBound);
};

/**
 * Creates a history object that stores locations in memory.
 */
var createMemoryHistory = function createMemoryHistory() {
  var props = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};
  var getUserConfirmation = props.getUserConfirmation,
      _props$initialEntries = props.initialEntries,
      initialEntries = _props$initialEntries === undefined ? ['/'] : _props$initialEntries,
      _props$initialIndex = props.initialIndex,
      initialIndex = _props$initialIndex === undefined ? 0 : _props$initialIndex,
      _props$keyLength = props.keyLength,
      keyLength = _props$keyLength === undefined ? 6 : _props$keyLength;

  var transitionManager = (0, _createTransitionManager2.default)();

  var setState = function setState(nextState) {
    _extends(history, nextState);

    history.length = history.entries.length;

    transitionManager.notifyListeners(history.location, history.action);
  };

  var createKey = function createKey() {
    return Math.random().toString(36).substr(2, keyLength);
  };

  var index = clamp(initialIndex, 0, initialEntries.length - 1);
  var entries = initialEntries.map(function (entry) {
    return typeof entry === 'string' ? (0, _LocationUtils.createLocation)(entry, undefined, createKey()) : (0, _LocationUtils.createLocation)(entry, undefined, entry.key || createKey());
  });

  // Public interface

  var createHref = _PathUtils.createPath;

  var push = function push(path, state) {
    (0, _warning2.default)(!((typeof path === 'undefined' ? 'undefined' : _typeof(path)) === 'object' && path.state !== undefined && state !== undefined), 'You should avoid providing a 2nd state argument to push when the 1st ' + 'argument is a location-like object that already has state; it is ignored');

    var action = 'PUSH';
    var location = (0, _LocationUtils.createLocation)(path, state, createKey(), history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      var prevIndex = history.index;
      var nextIndex = prevIndex + 1;

      var nextEntries = history.entries.slice(0);
      if (nextEntries.length > nextIndex) {
        nextEntries.splice(nextIndex, nextEntries.length - nextIndex, location);
      } else {
        nextEntries.push(location);
      }

      setState({
        action: action,
        location: location,
        index: nextIndex,
        entries: nextEntries
      });
    });
  };

  var replace = function replace(path, state) {
    (0, _warning2.default)(!((typeof path === 'undefined' ? 'undefined' : _typeof(path)) === 'object' && path.state !== undefined && state !== undefined), 'You should avoid providing a 2nd state argument to replace when the 1st ' + 'argument is a location-like object that already has state; it is ignored');

    var action = 'REPLACE';
    var location = (0, _LocationUtils.createLocation)(path, state, createKey(), history.location);

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (!ok) return;

      history.entries[history.index] = location;

      setState({ action: action, location: location });
    });
  };

  var go = function go(n) {
    var nextIndex = clamp(history.index + n, 0, history.entries.length - 1);

    var action = 'POP';
    var location = history.entries[nextIndex];

    transitionManager.confirmTransitionTo(location, action, getUserConfirmation, function (ok) {
      if (ok) {
        setState({
          action: action,
          location: location,
          index: nextIndex
        });
      } else {
        // Mimic the behavior of DOM histories by
        // causing a render after a cancelled POP.
        setState();
      }
    });
  };

  var goBack = function goBack() {
    return go(-1);
  };

  var goForward = function goForward() {
    return go(1);
  };

  var canGo = function canGo(n) {
    var nextIndex = history.index + n;
    return nextIndex >= 0 && nextIndex < history.entries.length;
  };

  var block = function block() {
    var prompt = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;
    return transitionManager.setPrompt(prompt);
  };

  var listen = function listen(listener) {
    return transitionManager.appendListener(listener);
  };

  var history = {
    length: entries.length,
    action: 'POP',
    location: entries[index],
    index: index,
    entries: entries,
    createHref: createHref,
    push: push,
    replace: replace,
    go: go,
    goBack: goBack,
    goForward: goForward,
    canGo: canGo,
    block: block,
    listen: listen
  };

  return history;
};

exports.default = createMemoryHistory;

/***/ }),

/***/ 449:
/*!**********************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/StaticRouter.js ***!
  \**********************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _StaticRouter = __webpack_require__(/*! react-router/es/StaticRouter */ 450);

var _StaticRouter2 = _interopRequireDefault(_StaticRouter);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = _StaticRouter2.default; // Written in this round about way for babel-transform-imports

/***/ }),

/***/ 450:
/*!******************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router/es/StaticRouter.js ***!
  \******************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _invariant = __webpack_require__(/*! invariant */ 8);

var _invariant2 = _interopRequireDefault(_invariant);

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _PathUtils = __webpack_require__(/*! history/PathUtils */ 28);

var _Router = __webpack_require__(/*! ./Router */ 84);

var _Router2 = _interopRequireDefault(_Router);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

function _objectWithoutProperties(obj, keys) {
  var target = {};for (var i in obj) {
    if (keys.indexOf(i) >= 0) continue;if (!Object.prototype.hasOwnProperty.call(obj, i)) continue;target[i] = obj[i];
  }return target;
}

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

var normalizeLocation = function normalizeLocation(object) {
  var _object$pathname = object.pathname,
      pathname = _object$pathname === undefined ? '/' : _object$pathname,
      _object$search = object.search,
      search = _object$search === undefined ? '' : _object$search,
      _object$hash = object.hash,
      hash = _object$hash === undefined ? '' : _object$hash;

  return {
    pathname: pathname,
    search: search === '?' ? '' : search,
    hash: hash === '#' ? '' : hash
  };
};

var addBasename = function addBasename(basename, location) {
  if (!basename) return location;

  return _extends({}, location, {
    pathname: (0, _PathUtils.addLeadingSlash)(basename) + location.pathname
  });
};

var stripBasename = function stripBasename(basename, location) {
  if (!basename) return location;

  var base = (0, _PathUtils.addLeadingSlash)(basename);

  if (location.pathname.indexOf(base) !== 0) return location;

  return _extends({}, location, {
    pathname: location.pathname.substr(base.length)
  });
};

var createLocation = function createLocation(location) {
  return typeof location === 'string' ? (0, _PathUtils.parsePath)(location) : normalizeLocation(location);
};

var createURL = function createURL(location) {
  return typeof location === 'string' ? location : (0, _PathUtils.createPath)(location);
};

var staticHandler = function staticHandler(methodName) {
  return function () {
    (0, _invariant2.default)(false, 'You cannot %s with <StaticRouter>', methodName);
  };
};

var noop = function noop() {};

/**
 * The public top-level API for a "static" <Router>, so-called because it
 * can't actually change the current location. Instead, it just records
 * location changes in a context object. Useful mainly in testing and
 * server-rendering scenarios.
 */

var StaticRouter = function (_React$Component) {
  _inherits(StaticRouter, _React$Component);

  function StaticRouter() {
    var _temp, _this, _ret;

    _classCallCheck(this, StaticRouter);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, _React$Component.call.apply(_React$Component, [this].concat(args))), _this), _this.createHref = function (path) {
      return (0, _PathUtils.addLeadingSlash)(_this.props.basename + createURL(path));
    }, _this.handlePush = function (location) {
      var _this$props = _this.props,
          basename = _this$props.basename,
          context = _this$props.context;

      context.action = 'PUSH';
      context.location = addBasename(basename, createLocation(location));
      context.url = createURL(context.location);
    }, _this.handleReplace = function (location) {
      var _this$props2 = _this.props,
          basename = _this$props2.basename,
          context = _this$props2.context;

      context.action = 'REPLACE';
      context.location = addBasename(basename, createLocation(location));
      context.url = createURL(context.location);
    }, _this.handleListen = function () {
      return noop;
    }, _this.handleBlock = function () {
      return noop;
    }, _temp), _possibleConstructorReturn(_this, _ret);
  }

  StaticRouter.prototype.getChildContext = function getChildContext() {
    return {
      router: {
        staticContext: this.props.context
      }
    };
  };

  StaticRouter.prototype.componentWillMount = function componentWillMount() {
    (0, _warning2.default)(!this.props.history, '<StaticRouter> ignores the history prop. To use a custom history, ' + 'use `import { Router }` instead of `import { StaticRouter as Router }`.');
  };

  StaticRouter.prototype.render = function render() {
    var _props = this.props,
        basename = _props.basename,
        context = _props.context,
        location = _props.location,
        props = _objectWithoutProperties(_props, ['basename', 'context', 'location']);

    var history = {
      createHref: this.createHref,
      action: 'POP',
      location: stripBasename(basename, createLocation(location)),
      push: this.handlePush,
      replace: this.handleReplace,
      go: staticHandler('go'),
      goBack: staticHandler('goBack'),
      goForward: staticHandler('goForward'),
      listen: this.handleListen,
      block: this.handleBlock
    };

    return _react2.default.createElement(_Router2.default, _extends({}, props, { history: history }));
  };

  return StaticRouter;
}(_react2.default.Component);

StaticRouter.propTypes = {
  basename: _propTypes2.default.string,
  context: _propTypes2.default.object.isRequired,
  location: _propTypes2.default.oneOfType([_propTypes2.default.string, _propTypes2.default.object])
};
StaticRouter.defaultProps = {
  basename: '',
  location: '/'
};
StaticRouter.childContextTypes = {
  router: _propTypes2.default.object.isRequired
};

exports.default = StaticRouter;

/***/ }),

/***/ 451:
/*!****************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/Switch.js ***!
  \****************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _Switch = __webpack_require__(/*! react-router/es/Switch */ 452);

var _Switch2 = _interopRequireDefault(_Switch);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = _Switch2.default; // Written in this round about way for babel-transform-imports

/***/ }),

/***/ 452:
/*!************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router/es/Switch.js ***!
  \************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _invariant = __webpack_require__(/*! invariant */ 8);

var _invariant2 = _interopRequireDefault(_invariant);

var _matchPath = __webpack_require__(/*! ./matchPath */ 85);

var _matchPath2 = _interopRequireDefault(_matchPath);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

/**
 * The public API for rendering the first <Route> that matches.
 */

var Switch = function (_React$Component) {
  _inherits(Switch, _React$Component);

  function Switch() {
    _classCallCheck(this, Switch);

    return _possibleConstructorReturn(this, _React$Component.apply(this, arguments));
  }

  Switch.prototype.componentWillMount = function componentWillMount() {
    (0, _invariant2.default)(this.context.router, 'You should not use <Switch> outside a <Router>');
  };

  Switch.prototype.componentWillReceiveProps = function componentWillReceiveProps(nextProps) {
    (0, _warning2.default)(!(nextProps.location && !this.props.location), '<Switch> elements should not change from uncontrolled to controlled (or vice versa). You initially used no "location" prop and then provided one on a subsequent render.');

    (0, _warning2.default)(!(!nextProps.location && this.props.location), '<Switch> elements should not change from controlled to uncontrolled (or vice versa). You provided a "location" prop initially but omitted it on a subsequent render.');
  };

  Switch.prototype.render = function render() {
    var route = this.context.router.route;
    var children = this.props.children;

    var location = this.props.location || route.location;

    var match = void 0,
        child = void 0;
    _react2.default.Children.forEach(children, function (element) {
      if (!_react2.default.isValidElement(element)) return;

      var _element$props = element.props,
          pathProp = _element$props.path,
          exact = _element$props.exact,
          strict = _element$props.strict,
          sensitive = _element$props.sensitive,
          from = _element$props.from;

      var path = pathProp || from;

      if (match == null) {
        child = element;
        match = path ? (0, _matchPath2.default)(location.pathname, { path: path, exact: exact, strict: strict, sensitive: sensitive }) : route.match;
      }
    });

    return match ? _react2.default.cloneElement(child, { location: location, computedMatch: match }) : null;
  };

  return Switch;
}(_react2.default.Component);

Switch.contextTypes = {
  router: _propTypes2.default.shape({
    route: _propTypes2.default.object.isRequired
  }).isRequired
};
Switch.propTypes = {
  children: _propTypes2.default.node,
  location: _propTypes2.default.object
};

exports.default = Switch;

/***/ }),

/***/ 453:
/*!*******************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/matchPath.js ***!
  \*******************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _matchPath = __webpack_require__(/*! react-router/es/matchPath */ 85);

var _matchPath2 = _interopRequireDefault(_matchPath);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = _matchPath2.default; // Written in this round about way for babel-transform-imports

/***/ }),

/***/ 454:
/*!********************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/withRouter.js ***!
  \********************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _withRouter = __webpack_require__(/*! react-router/es/withRouter */ 455);

var _withRouter2 = _interopRequireDefault(_withRouter);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = _withRouter2.default; // Written in this round about way for babel-transform-imports

/***/ }),

/***/ 455:
/*!****************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router/es/withRouter.js ***!
  \****************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _hoistNonReactStatics = __webpack_require__(/*! hoist-non-react-statics */ 456);

var _hoistNonReactStatics2 = _interopRequireDefault(_hoistNonReactStatics);

var _Route = __webpack_require__(/*! ./Route */ 191);

var _Route2 = _interopRequireDefault(_Route);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

function _objectWithoutProperties(obj, keys) {
  var target = {};for (var i in obj) {
    if (keys.indexOf(i) >= 0) continue;if (!Object.prototype.hasOwnProperty.call(obj, i)) continue;target[i] = obj[i];
  }return target;
}

/**
 * A public higher-order component to access the imperative API
 */
var withRouter = function withRouter(Component) {
  var C = function C(props) {
    var wrappedComponentRef = props.wrappedComponentRef,
        remainingProps = _objectWithoutProperties(props, ['wrappedComponentRef']);

    return _react2.default.createElement(_Route2.default, { render: function render(routeComponentProps) {
        return _react2.default.createElement(Component, _extends({}, remainingProps, routeComponentProps, { ref: wrappedComponentRef }));
      } });
  };

  C.displayName = 'withRouter(' + (Component.displayName || Component.name) + ')';
  C.WrappedComponent = Component;
  C.propTypes = {
    wrappedComponentRef: _propTypes2.default.func
  };

  return (0, _hoistNonReactStatics2.default)(C, Component);
};

exports.default = withRouter;

/***/ }),

/***/ 456:
/*!*******************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/hoist-non-react-statics/index.js ***!
  \*******************************************************************************/
/*! dynamic exports provided */
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
    callee: true,
    arguments: true,
    arity: true
};

var defineProperty = Object.defineProperty;
var getOwnPropertyNames = Object.getOwnPropertyNames;
var getOwnPropertySymbols = Object.getOwnPropertySymbols;
var getOwnPropertyDescriptor = Object.getOwnPropertyDescriptor;
var getPrototypeOf = Object.getPrototypeOf;
var objectPrototype = getPrototypeOf && getPrototypeOf(Object);

module.exports = function hoistNonReactStatics(targetComponent, sourceComponent, blacklist) {
    if (typeof sourceComponent !== 'string') {
        // don't hoist over string (html) components

        if (objectPrototype) {
            var inheritedComponent = getPrototypeOf(sourceComponent);
            if (inheritedComponent && inheritedComponent !== objectPrototype) {
                hoistNonReactStatics(targetComponent, inheritedComponent, blacklist);
            }
        }

        var keys = getOwnPropertyNames(sourceComponent);

        if (getOwnPropertySymbols) {
            keys = keys.concat(getOwnPropertySymbols(sourceComponent));
        }

        for (var i = 0; i < keys.length; ++i) {
            var key = keys[i];
            if (!REACT_STATICS[key] && !KNOWN_STATICS[key] && (!blacklist || !blacklist[key])) {
                var descriptor = getOwnPropertyDescriptor(sourceComponent, key);
                try {
                    // Avoid failures from read-only properties
                    defineProperty(targetComponent, key, descriptor);
                } catch (e) {}
            }
        }

        return targetComponent;
    }

    return targetComponent;
};

/***/ }),

/***/ 457:
/*!**********************************************************!*\
  !*** ./bundles/experiment-page/src/TSnePlotViewRoute.js ***!
  \**********************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _queryString = __webpack_require__(/*! query-string */ 458);

var _queryString2 = _interopRequireDefault(_queryString);

var _urijs = __webpack_require__(/*! urijs */ 15);

var _urijs2 = _interopRequireDefault(_urijs);

var _expressionAtlasExperimentPageTsnePlot = __webpack_require__(/*! expression-atlas-experiment-page-tsne-plot */ 460);

var _expressionAtlasExperimentPageTsnePlot2 = _interopRequireDefault(_expressionAtlasExperimentPageTsnePlot);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var TSnePlotViewRoute = function TSnePlotViewRoute(props) {
  var location = props.location,
      history = props.history;


  var _updateUrl = function _updateUrl(parameter) {
    history.push((0, _urijs2.default)(location.search).setSearch(parameter.name, parameter.value).toString());
  };

  var atlasUrl = props.atlasUrl,
      resourcesUrl = props.resourcesUrl;
  var suggesterEndpoint = props.suggesterEndpoint,
      experimentAccession = props.experimentAccession,
      ks = props.ks,
      perplexities = props.perplexities;

  var search = (0, _urijs2.default)(location.search).search(true);

  return _react2.default.createElement(_expressionAtlasExperimentPageTsnePlot2.default, { atlasUrl: atlasUrl,
    suggesterEndpoint: suggesterEndpoint,
    experimentAccession: experimentAccession,
    ks: ks,
    k: Number(search.k) || props.ks[0],
    highlightClusters: search.clusterId ? JSON.parse(search.clusterId) : [],
    perplexities: perplexities,
    perplexity: Number(search.perplexity) || props.perplexities[0],
    geneId: search.geneId || '',
    height: 600,
    resourcesUrl: resourcesUrl,
    onSelectGeneId: function onSelectGeneId(geneId) {
      _updateUrl({ name: 'geneId', value: geneId });
    },
    onChangeK: function onChangeK(k) {
      _updateUrl({ name: 'k', value: k });
    },
    onChangePerplexity: function onChangePerplexity(perplexity) {
      _updateUrl({ name: 'perplexity', value: perplexity });
    }
  });
};

TSnePlotViewRoute.propTypes = {
  match: _propTypes2.default.object.isRequired,
  location: _propTypes2.default.object.isRequired,
  history: _propTypes2.default.object.isRequired,
  atlasUrl: _propTypes2.default.string.isRequired,
  resourcesUrl: _propTypes2.default.string,
  experimentAccession: _propTypes2.default.string.isRequired,
  ks: _propTypes2.default.arrayOf(_propTypes2.default.number).isRequired,
  perplexities: _propTypes2.default.arrayOf(_propTypes2.default.number).isRequired,
  suggesterEndpoint: _propTypes2.default.string.isRequired
};

exports.default = TSnePlotViewRoute;

/***/ }),

/***/ 458:
/*!********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/query-string/index.js ***!
  \********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var strictUriEncode = __webpack_require__(/*! strict-uri-encode */ 459);
var objectAssign = __webpack_require__(/*! object-assign */ 184);

function encoderForArrayFormat(opts) {
	switch (opts.arrayFormat) {
		case 'index':
			return function (key, value, index) {
				return value === null ? [encode(key, opts), '[', index, ']'].join('') : [encode(key, opts), '[', encode(index, opts), ']=', encode(value, opts)].join('');
			};

		case 'bracket':
			return function (key, value) {
				return value === null ? encode(key, opts) : [encode(key, opts), '[]=', encode(value, opts)].join('');
			};

		default:
			return function (key, value) {
				return value === null ? encode(key, opts) : [encode(key, opts), '=', encode(value, opts)].join('');
			};
	}
}

function parserForArrayFormat(opts) {
	var result;

	switch (opts.arrayFormat) {
		case 'index':
			return function (key, value, accumulator) {
				result = /\[(\d*)\]$/.exec(key);

				key = key.replace(/\[\d*\]$/, '');

				if (!result) {
					accumulator[key] = value;
					return;
				}

				if (accumulator[key] === undefined) {
					accumulator[key] = {};
				}

				accumulator[key][result[1]] = value;
			};

		case 'bracket':
			return function (key, value, accumulator) {
				result = /(\[\])$/.exec(key);
				key = key.replace(/\[\]$/, '');

				if (!result) {
					accumulator[key] = value;
					return;
				} else if (accumulator[key] === undefined) {
					accumulator[key] = [value];
					return;
				}

				accumulator[key] = [].concat(accumulator[key], value);
			};

		default:
			return function (key, value, accumulator) {
				if (accumulator[key] === undefined) {
					accumulator[key] = value;
					return;
				}

				accumulator[key] = [].concat(accumulator[key], value);
			};
	}
}

function encode(value, opts) {
	if (opts.encode) {
		return opts.strict ? strictUriEncode(value) : encodeURIComponent(value);
	}

	return value;
}

function keysSorter(input) {
	if (Array.isArray(input)) {
		return input.sort();
	} else if ((typeof input === 'undefined' ? 'undefined' : _typeof(input)) === 'object') {
		return keysSorter(Object.keys(input)).sort(function (a, b) {
			return Number(a) - Number(b);
		}).map(function (key) {
			return input[key];
		});
	}

	return input;
}

exports.extract = function (str) {
	return str.split('?')[1] || '';
};

exports.parse = function (str, opts) {
	opts = objectAssign({ arrayFormat: 'none' }, opts);

	var formatter = parserForArrayFormat(opts);

	// Create an object with no prototype
	// https://github.com/sindresorhus/query-string/issues/47
	var ret = Object.create(null);

	if (typeof str !== 'string') {
		return ret;
	}

	str = str.trim().replace(/^(\?|#|&)/, '');

	if (!str) {
		return ret;
	}

	str.split('&').forEach(function (param) {
		var parts = param.replace(/\+/g, ' ').split('=');
		// Firefox (pre 40) decodes `%3D` to `=`
		// https://github.com/sindresorhus/query-string/pull/37
		var key = parts.shift();
		var val = parts.length > 0 ? parts.join('=') : undefined;

		// missing `=` should be `null`:
		// http://w3.org/TR/2012/WD-url-20120524/#collect-url-parameters
		val = val === undefined ? null : decodeURIComponent(val);

		formatter(decodeURIComponent(key), val, ret);
	});

	return Object.keys(ret).sort().reduce(function (result, key) {
		var val = ret[key];
		if (Boolean(val) && (typeof val === 'undefined' ? 'undefined' : _typeof(val)) === 'object' && !Array.isArray(val)) {
			// Sort object keys, not values
			result[key] = keysSorter(val);
		} else {
			result[key] = val;
		}

		return result;
	}, Object.create(null));
};

exports.stringify = function (obj, opts) {
	var defaults = {
		encode: true,
		strict: true,
		arrayFormat: 'none'
	};

	opts = objectAssign(defaults, opts);

	var formatter = encoderForArrayFormat(opts);

	return obj ? Object.keys(obj).sort().map(function (key) {
		var val = obj[key];

		if (val === undefined) {
			return '';
		}

		if (val === null) {
			return encode(key, opts);
		}

		if (Array.isArray(val)) {
			var result = [];

			val.slice().forEach(function (val2) {
				if (val2 === undefined) {
					return;
				}

				result.push(formatter(key, val2, result.length));
			});

			return result.join('&');
		}

		return encode(key, opts) + '=' + encode(val, opts);
	}).filter(function (x) {
		return x.length > 0;
	}).join('&') : '';
};

/***/ }),

/***/ 459:
/*!*************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/strict-uri-encode/index.js ***!
  \*************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = function (str) {
	return encodeURIComponent(str).replace(/[!'()*]/g, function (c) {
		return '%' + c.charCodeAt(0).toString(16).toUpperCase();
	});
};

/***/ }),

/***/ 460:
/*!******************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-experiment-page-tsne-plot/lib/index.js ***!
  \******************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _TSnePlotView = __webpack_require__(/*! ./TSnePlotView */ 461);

var _TSnePlotView2 = _interopRequireDefault(_TSnePlotView);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

exports.default = _TSnePlotView2.default;

/***/ }),

/***/ 461:
/*!*************************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-experiment-page-tsne-plot/lib/TSnePlotView.js ***!
  \*************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () {
  function defineProperties(target, props) {
    for (var i = 0; i < props.length; i++) {
      var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
    }
  }return function (Constructor, protoProps, staticProps) {
    if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
  };
}();

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _urijs = __webpack_require__(/*! urijs */ 15);

var _urijs2 = _interopRequireDefault(_urijs);

var _ClusterTSnePlot = __webpack_require__(/*! ./ClusterTSnePlot */ 462);

var _ClusterTSnePlot2 = _interopRequireDefault(_ClusterTSnePlot);

var _GeneExpressionTSnePlot = __webpack_require__(/*! ./GeneExpressionTSnePlot */ 475);

var _GeneExpressionTSnePlot2 = _interopRequireDefault(_GeneExpressionTSnePlot);

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
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

function _asyncToGenerator(fn) {
  return function () {
    var gen = fn.apply(this, arguments);return new Promise(function (resolve, reject) {
      function step(key, arg) {
        try {
          var info = gen[key](arg);var value = info.value;
        } catch (error) {
          reject(error);return;
        }if (info.done) {
          resolve(value);
        } else {
          return Promise.resolve(value).then(function (value) {
            step("next", value);
          }, function (err) {
            step("throw", err);
          });
        }
      }return step("next");
    });
  };
}

var fetchResponseJson = function () {
  var _ref = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee(base, endpoint) {
    var response, responseJson;
    return regeneratorRuntime.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            _context.next = 2;
            return fetch((0, _urijs2.default)(endpoint, base).toString());

          case 2:
            response = _context.sent;
            _context.next = 5;
            return response.json();

          case 5:
            responseJson = _context.sent;
            return _context.abrupt('return', responseJson);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, undefined);
  }));

  return function fetchResponseJson(_x, _x2) {
    return _ref.apply(this, arguments);
  };
}();

var ExperimentPageView = function (_React$Component) {
  _inherits(ExperimentPageView, _React$Component);

  function ExperimentPageView(props) {
    _classCallCheck(this, ExperimentPageView);

    var _this = _possibleConstructorReturn(this, (ExperimentPageView.__proto__ || Object.getPrototypeOf(ExperimentPageView)).call(this, props));

    _this.state = {
      data: {
        series: [],
        unit: ''
      },
      errorMessage: null,
      loadingClusters: false,
      loadingGeneExpression: false
    };
    return _this;
  }

  _createClass(ExperimentPageView, [{
    key: '_fetchAndSetState',
    value: function _fetchAndSetState(_ref2) {
      var _this2 = this;

      var atlasUrl = _ref2.atlasUrl,
          experimentAccession = _ref2.experimentAccession,
          k = _ref2.k,
          perplexity = _ref2.perplexity,
          geneId = _ref2.geneId;

      var atlasEndpoint = 'json/experiments/' + experimentAccession + '/tsneplot/' + perplexity + '/clusters/' + k + '/expression/' + geneId;

      return fetchResponseJson(atlasUrl, atlasEndpoint).then(function (responseJson) {
        _this2.setState({
          data: responseJson,
          errorMessage: null,
          loadingClusters: false,
          loadingGeneExpression: false
        });
      }).catch(function (reason) {
        _this2.setState({
          errorMessage: reason.name + ': ' + reason.message,
          loadingClusters: false,
          loadingGeneExpression: false
        });
      });
    }
  }, {
    key: 'componentWillReceiveProps',
    value: function componentWillReceiveProps(nextProps) {
      if (nextProps.atlasUrl !== this.props.atlasUrl || // First two will never happen but it’s the right thing to do
      nextProps.experimentAccession !== this.props.experimentAccession || nextProps.perplexity !== this.props.perplexity || nextProps.k !== this.props.k) {

        this.setState({
          loadingClusters: true,
          loadingGeneExpression: true
        });
        this._fetchAndSetState(nextProps);
      } else if (nextProps.geneId !== this.props.geneId) {

        this.setState({
          loadingGeneExpression: true
        });
        this._fetchAndSetState(nextProps);
      }
    }
  }, {
    key: 'componentDidMount',
    value: function componentDidMount() {
      this.setState({
        loadingClusters: true,
        loadingGeneExpression: true
      });
      // Having _fetchAndSetState as callback is the right thing, but then we can’t return the promise; see tests
      return this._fetchAndSetState(this.props);
    }
  }, {
    key: 'render',
    value: function render() {
      var _props = this.props,
          height = _props.height,
          atlasUrl = _props.atlasUrl,
          resourcesUrl = _props.resourcesUrl;
      var _props2 = this.props,
          suggesterEndpoint = _props2.suggesterEndpoint,
          geneId = _props2.geneId,
          highlightClusters = _props2.highlightClusters,
          ks = _props2.ks,
          k = _props2.k,
          perplexities = _props2.perplexities,
          perplexity = _props2.perplexity;
      var _props3 = this.props,
          onChangePerplexity = _props3.onChangePerplexity,
          onChangeK = _props3.onChangeK,
          onSelectGeneId = _props3.onSelectGeneId;
      var _state = this.state,
          loadingClusters = _state.loadingClusters,
          loadingGeneExpression = _state.loadingGeneExpression,
          data = _state.data,
          errorMessage = _state.errorMessage;

      return _react2.default.createElement('div', { className: 'row' }, _react2.default.createElement('div', { className: 'small-12 medium-6 columns' }, _react2.default.createElement(_ClusterTSnePlot2.default, { height: height,
        plotData: data,
        perplexities: perplexities,
        perplexity: perplexity,
        onChangePerplexity: onChangePerplexity,
        ks: ks,
        k: k,
        onChangeK: onChangeK,
        highlightClusters: highlightClusters,
        loading: loadingClusters,
        resourcesUrl: resourcesUrl,
        errorMessage: errorMessage
      })), _react2.default.createElement('div', { className: 'small-12 medium-6 columns' }, _react2.default.createElement(_GeneExpressionTSnePlot2.default, { height: height,
        plotData: data,
        atlasUrl: atlasUrl,
        suggesterEndpoint: suggesterEndpoint,
        onSelectGeneId: onSelectGeneId,
        geneId: geneId,
        highlightClusters: highlightClusters,
        loading: loadingGeneExpression,
        resourcesUrl: resourcesUrl,
        errorMessage: errorMessage
      })));
    }
  }, {
    key: 'componentDidCatch',
    value: function componentDidCatch(error, info) {
      this.setState({
        errorMessage: '' + error
      });
    }
  }]);

  return ExperimentPageView;
}(_react2.default.Component);

ExperimentPageView.propTypes = {
  atlasUrl: _propTypes2.default.string.isRequired,
  suggesterEndpoint: _propTypes2.default.string.isRequired,
  experimentAccession: _propTypes2.default.string.isRequired,
  ks: _propTypes2.default.arrayOf(_propTypes2.default.number).isRequired,
  k: _propTypes2.default.number.isRequired,
  perplexities: _propTypes2.default.arrayOf(_propTypes2.default.number).isRequired,
  perplexity: _propTypes2.default.number.isRequired,
  highlightClusters: _propTypes2.default.arrayOf(_propTypes2.default.number),
  geneId: _propTypes2.default.string.isRequired,
  height: _propTypes2.default.number,
  resourcesUrl: _propTypes2.default.string,
  onSelectGeneId: _propTypes2.default.func,
  onChangeK: _propTypes2.default.func,
  onChangePerplexity: _propTypes2.default.func
};

ExperimentPageView.defaultProps = {
  highlightClusters: [],
  geneId: '',
  height: 600,
  onSelectGeneId: function onSelectGeneId() {},
  onKChange: function onKChange() {},
  onPerplexityChange: function onPerplexityChange() {}
};

exports.default = ExperimentPageView;

/***/ }),

/***/ 462:
/*!****************************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-experiment-page-tsne-plot/lib/ClusterTSnePlot.js ***!
  \****************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports._colourizeClusters = exports.default = undefined;

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _color = __webpack_require__(/*! color */ 196);

var _color2 = _interopRequireDefault(_color);

var _PlotLoader = __webpack_require__(/*! ./plotloader/PlotLoader */ 199);

var _PlotLoader2 = _interopRequireDefault(_PlotLoader);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var _colourizeClusters = function _colourizeClusters(highlightSeries) {
  return function (series) {
    return series.map(function (aSeries) {
      // I can’t think of a better way to reconcile series.name being a string and highlightSeries being an array of
      // numbers. For more flexibility we might think of having our series be identified by an arbitrary ID string
      if (!highlightSeries.length || highlightSeries.map(function (hs) {
        return String(hs);
      }).includes(aSeries.name)) {
        return aSeries;
      } else {
        return {
          name: aSeries.name,
          data: aSeries.data.map(function (point) {
            return _extends({}, point, {
              color: (0, _color2.default)('lightgrey').alpha(0.65).rgb().toString()
            });
          })
        };
      }
    });
  };
};

var ClusterTSnePlot = function ClusterTSnePlot(props) {
  var ks = props.ks,
      k = props.k,
      onChangeK = props.onChangeK,
      perplexities = props.perplexities,
      perplexity = props.perplexity,
      onChangePerplexity = props.onChangePerplexity; // Select

  var plotData = props.plotData,
      highlightClusters = props.highlightClusters,
      height = props.height; // Chart

  var loading = props.loading,
      resourcesUrl = props.resourcesUrl,
      errorMessage = props.errorMessage; // Overlay

  var highchartsConfig = {
    plotOptions: {
      scatter: {
        tooltip: {
          headerFormat: '<b>Cluster {series.name}</b><br>',
          pointFormat: '{point.name}'
        }
      }
    },
    colors: ['rgba(178, 95, 188, 0.7)', 'rgba(118, 179, 65, 0.7)', 'rgba(104, 130, 207, 0.7)', 'rgba(206, 155, 68, 0.7)', 'rgba(200, 87, 123, 0.7)', 'rgba(79, 174, 132, 0.7)', 'rgba(201, 92, 63, 0.7)', 'rgba(124, 127, 57, 0.7)'],
    chart: {
      height: height
    }
  };

  var perplexityOptions = perplexities.sort().map(function (perplexity) {
    return _react2.default.createElement('option', { key: perplexity, value: perplexity }, perplexity);
  });

  var kOptions = ks.sort().map(function (k) {
    return _react2.default.createElement('option', { key: k, value: k }, k);
  });

  return [_react2.default.createElement('div', { key: 'perplexity-k-select', className: 'row' }, _react2.default.createElement('div', { className: 'column medium-6' }, _react2.default.createElement('label', null, 'Perplexity'), _react2.default.createElement('select', { value: perplexity, onChange: function onChange(event) {
      onChangePerplexity(Number(event.target.value));
    } }, perplexityOptions)), _react2.default.createElement('div', { className: 'column medium-6' }, _react2.default.createElement('label', null, 'Number of clusters, ', _react2.default.createElement('i', null, 'k')), _react2.default.createElement('select', { value: k, onChange: function onChange(event) {
      onChangeK(Number(event.target.value));
    } }, kOptions))), _react2.default.createElement(_PlotLoader2.default, { key: 'cluster-plot',
    wrapperClassName: 'row column',
    series: _colourizeClusters(highlightClusters)(plotData.series),
    highchartsConfig: highchartsConfig,
    loading: loading,
    resourcesUrl: resourcesUrl,
    errorMessage: errorMessage
  })];
};

ClusterTSnePlot.propTypes = {
  height: _propTypes2.default.number.isRequired,

  plotData: _propTypes2.default.shape({
    series: _propTypes2.default.array.isRequired
  }),
  highlightClusters: _propTypes2.default.array,

  ks: _propTypes2.default.arrayOf(_propTypes2.default.number).isRequired,
  k: _propTypes2.default.number.isRequired,
  onChangeK: _propTypes2.default.func.isRequired,

  perplexities: _propTypes2.default.arrayOf(_propTypes2.default.number).isRequired,
  perplexity: _propTypes2.default.number.isRequired,
  onChangePerplexity: _propTypes2.default.func.isRequired,

  loading: _propTypes2.default.bool.isRequired,
  resourcesUrl: _propTypes2.default.string,
  errorMessage: _propTypes2.default.string
};

exports.default = ClusterTSnePlot;
exports._colourizeClusters = _colourizeClusters;

/***/ }),

/***/ 463:
/*!********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/color-string/index.js ***!
  \********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/* MIT license */
var colorNames = __webpack_require__(/*! color-name */ 197);
var swizzle = __webpack_require__(/*! simple-swizzle */ 464);

var reverseNames = {};

// create a list of reverse color names
for (var name in colorNames) {
	if (colorNames.hasOwnProperty(name)) {
		reverseNames[colorNames[name]] = name;
	}
}

var cs = module.exports = {
	to: {}
};

cs.get = function (string) {
	var prefix = string.substring(0, 3).toLowerCase();
	var val;
	var model;
	switch (prefix) {
		case 'hsl':
			val = cs.get.hsl(string);
			model = 'hsl';
			break;
		case 'hwb':
			val = cs.get.hwb(string);
			model = 'hwb';
			break;
		default:
			val = cs.get.rgb(string);
			model = 'rgb';
			break;
	}

	if (!val) {
		return null;
	}

	return { model: model, value: val };
};

cs.get.rgb = function (string) {
	if (!string) {
		return null;
	}

	var abbr = /^#([a-f0-9]{3,4})$/i;
	var hex = /^#([a-f0-9]{6})([a-f0-9]{2})?$/i;
	var rgba = /^rgba?\(\s*([+-]?\d+)\s*,\s*([+-]?\d+)\s*,\s*([+-]?\d+)\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)$/;
	var per = /^rgba?\(\s*([+-]?[\d\.]+)\%\s*,\s*([+-]?[\d\.]+)\%\s*,\s*([+-]?[\d\.]+)\%\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)$/;
	var keyword = /(\D+)/;

	var rgb = [0, 0, 0, 1];
	var match;
	var i;
	var hexAlpha;

	if (match = string.match(hex)) {
		hexAlpha = match[2];
		match = match[1];

		for (i = 0; i < 3; i++) {
			// https://jsperf.com/slice-vs-substr-vs-substring-methods-long-string/19
			var i2 = i * 2;
			rgb[i] = parseInt(match.slice(i2, i2 + 2), 16);
		}

		if (hexAlpha) {
			rgb[3] = Math.round(parseInt(hexAlpha, 16) / 255 * 100) / 100;
		}
	} else if (match = string.match(abbr)) {
		match = match[1];
		hexAlpha = match[3];

		for (i = 0; i < 3; i++) {
			rgb[i] = parseInt(match[i] + match[i], 16);
		}

		if (hexAlpha) {
			rgb[3] = Math.round(parseInt(hexAlpha + hexAlpha, 16) / 255 * 100) / 100;
		}
	} else if (match = string.match(rgba)) {
		for (i = 0; i < 3; i++) {
			rgb[i] = parseInt(match[i + 1], 0);
		}

		if (match[4]) {
			rgb[3] = parseFloat(match[4]);
		}
	} else if (match = string.match(per)) {
		for (i = 0; i < 3; i++) {
			rgb[i] = Math.round(parseFloat(match[i + 1]) * 2.55);
		}

		if (match[4]) {
			rgb[3] = parseFloat(match[4]);
		}
	} else if (match = string.match(keyword)) {
		if (match[1] === 'transparent') {
			return [0, 0, 0, 0];
		}

		rgb = colorNames[match[1]];

		if (!rgb) {
			return null;
		}

		rgb[3] = 1;

		return rgb;
	} else {
		return null;
	}

	for (i = 0; i < 3; i++) {
		rgb[i] = clamp(rgb[i], 0, 255);
	}
	rgb[3] = clamp(rgb[3], 0, 1);

	return rgb;
};

cs.get.hsl = function (string) {
	if (!string) {
		return null;
	}

	var hsl = /^hsla?\(\s*([+-]?\d*[\.]?\d+)(?:deg)?\s*,\s*([+-]?[\d\.]+)%\s*,\s*([+-]?[\d\.]+)%\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)$/;
	var match = string.match(hsl);

	if (match) {
		var alpha = parseFloat(match[4]);
		var h = (parseFloat(match[1]) % 360 + 360) % 360;
		var s = clamp(parseFloat(match[2]), 0, 100);
		var l = clamp(parseFloat(match[3]), 0, 100);
		var a = clamp(isNaN(alpha) ? 1 : alpha, 0, 1);

		return [h, s, l, a];
	}

	return null;
};

cs.get.hwb = function (string) {
	if (!string) {
		return null;
	}

	var hwb = /^hwb\(\s*([+-]?\d*[\.]?\d+)(?:deg)?\s*,\s*([+-]?[\d\.]+)%\s*,\s*([+-]?[\d\.]+)%\s*(?:,\s*([+-]?[\d\.]+)\s*)?\)$/;
	var match = string.match(hwb);

	if (match) {
		var alpha = parseFloat(match[4]);
		var h = (parseFloat(match[1]) % 360 + 360) % 360;
		var w = clamp(parseFloat(match[2]), 0, 100);
		var b = clamp(parseFloat(match[3]), 0, 100);
		var a = clamp(isNaN(alpha) ? 1 : alpha, 0, 1);
		return [h, w, b, a];
	}

	return null;
};

cs.to.hex = function () {
	var rgba = swizzle(arguments);

	return '#' + hexDouble(rgba[0]) + hexDouble(rgba[1]) + hexDouble(rgba[2]) + (rgba[3] < 1 ? hexDouble(Math.round(rgba[3] * 255)) : '');
};

cs.to.rgb = function () {
	var rgba = swizzle(arguments);

	return rgba.length < 4 || rgba[3] === 1 ? 'rgb(' + Math.round(rgba[0]) + ', ' + Math.round(rgba[1]) + ', ' + Math.round(rgba[2]) + ')' : 'rgba(' + Math.round(rgba[0]) + ', ' + Math.round(rgba[1]) + ', ' + Math.round(rgba[2]) + ', ' + rgba[3] + ')';
};

cs.to.rgb.percent = function () {
	var rgba = swizzle(arguments);

	var r = Math.round(rgba[0] / 255 * 100);
	var g = Math.round(rgba[1] / 255 * 100);
	var b = Math.round(rgba[2] / 255 * 100);

	return rgba.length < 4 || rgba[3] === 1 ? 'rgb(' + r + '%, ' + g + '%, ' + b + '%)' : 'rgba(' + r + '%, ' + g + '%, ' + b + '%, ' + rgba[3] + ')';
};

cs.to.hsl = function () {
	var hsla = swizzle(arguments);
	return hsla.length < 4 || hsla[3] === 1 ? 'hsl(' + hsla[0] + ', ' + hsla[1] + '%, ' + hsla[2] + '%)' : 'hsla(' + hsla[0] + ', ' + hsla[1] + '%, ' + hsla[2] + '%, ' + hsla[3] + ')';
};

// hwb is a bit different than rgb(a) & hsl(a) since there is no alpha specific syntax
// (hwb have alpha optional & 1 is default value)
cs.to.hwb = function () {
	var hwba = swizzle(arguments);

	var a = '';
	if (hwba.length >= 4 && hwba[3] !== 1) {
		a = ', ' + hwba[3];
	}

	return 'hwb(' + hwba[0] + ', ' + hwba[1] + '%, ' + hwba[2] + '%' + a + ')';
};

cs.to.keyword = function (rgb) {
	return reverseNames[rgb.slice(0, 3)];
};

// helpers
function clamp(num, min, max) {
	return Math.min(Math.max(min, num), max);
}

function hexDouble(num) {
	var str = num.toString(16).toUpperCase();
	return str.length < 2 ? '0' + str : str;
}

/***/ }),

/***/ 464:
/*!**********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/simple-swizzle/index.js ***!
  \**********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isArrayish = __webpack_require__(/*! is-arrayish */ 465);

var concat = Array.prototype.concat;
var slice = Array.prototype.slice;

var swizzle = module.exports = function swizzle(args) {
	var results = [];

	for (var i = 0, len = args.length; i < len; i++) {
		var arg = args[i];

		if (isArrayish(arg)) {
			// http://jsperf.com/javascript-array-concat-vs-push/98
			results = concat.call(results, slice.call(arg));
		} else {
			results.push(arg);
		}
	}

	return results;
};

swizzle.wrap = function (fn) {
	return function () {
		return fn(swizzle(arguments));
	};
};

/***/ }),

/***/ 465:
/*!*******************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/is-arrayish/index.js ***!
  \*******************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = function isArrayish(obj) {
	if (!obj || typeof obj === 'string') {
		return false;
	}

	return obj instanceof Array || Array.isArray(obj) || obj.length >= 0 && (obj.splice instanceof Function || Object.getOwnPropertyDescriptor(obj, obj.length - 1) && obj.constructor.name !== 'String');
};

/***/ }),

/***/ 466:
/*!*********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/color-convert/index.js ***!
  \*********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var conversions = __webpack_require__(/*! ./conversions */ 198);
var route = __webpack_require__(/*! ./route */ 467);

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
	Object.defineProperty(convert[fromModel], 'labels', { value: conversions[fromModel].labels });

	var routes = route(fromModel);
	var routeModels = Object.keys(routes);

	routeModels.forEach(function (toModel) {
		var fn = routes[toModel];

		convert[fromModel][toModel] = wrapRounded(fn);
		convert[fromModel][toModel].raw = wrapRaw(fn);
	});
});

module.exports = convert;

/***/ }),

/***/ 467:
/*!*********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/color-convert/route.js ***!
  \*********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var conversions = __webpack_require__(/*! ./conversions */ 198);

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

/***/ }),

/***/ 468:
/*!**************************************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-experiment-page-tsne-plot/lib/plotloader/LoadingOverlay.js ***!
  \**************************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _urijs = __webpack_require__(/*! urijs */ 15);

var _urijs2 = _interopRequireDefault(_urijs);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var LoadingOverlay = function LoadingOverlay(props) {
  return _react2.default.createElement('div', { style: {
      display: props.show ? 'flex' : 'none',
      position: 'absolute',
      top: 0, left: 0, right: 0, bottom: 0,
      background: 'rgba(255,255,255,0.8)',
      alignItems: 'center',
      justifyContent: 'center'
    } }, _react2.default.createElement('div', { style: { textAlign: 'center' } }, _react2.default.createElement('p', null, 'Loading, please wait...'), _react2.default.createElement('img', { src: (0, _urijs2.default)(__webpack_require__(/*! ./svg/flask-loader.svg */ 469), props.resourcesUrl).toString() }), _react2.default.createElement('p', null, _react2.default.createElement('small', null, 'Powered by ', _react2.default.createElement('a', { href: 'https://loading.io' }, 'loading.io')))));
};

LoadingOverlay.propTypes = {
  show: _propTypes2.default.bool.isRequired,
  resourcesUrl: _propTypes2.default.string
};

LoadingOverlay.defaultProps = {
  resourcesUrl: ''
};

exports.default = LoadingOverlay;

/***/ }),

/***/ 469:
/*!*****************************************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-experiment-page-tsne-plot/lib/plotloader/svg/flask-loader.svg ***!
  \*****************************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "bbe30b27d9320f575e5452cf2b930c40.svg";

/***/ }),

/***/ 470:
/*!***********************************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-experiment-page-tsne-plot/lib/plotloader/ScatterPlot.js ***!
  \***********************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _reactHighcharts = __webpack_require__(/*! react-highcharts */ 471);

var _reactHighcharts2 = _interopRequireDefault(_reactHighcharts);

var _exporting = __webpack_require__(/*! highcharts/modules/exporting */ 472);

var _exporting2 = _interopRequireDefault(_exporting);

var _boost = __webpack_require__(/*! highcharts/modules/boost */ 473);

var _boost2 = _interopRequireDefault(_boost);

var _deepmerge = __webpack_require__(/*! deepmerge */ 474);

var _deepmerge2 = _interopRequireDefault(_deepmerge);

var _SeriesPropTypes = __webpack_require__(/*! ./SeriesPropTypes */ 201);

var _SeriesPropTypes2 = _interopRequireDefault(_SeriesPropTypes);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var Highcharts = _reactHighcharts2.default.Highcharts;
// Only apply modules if Highcharts isn’t a *good* mock -- Boost/Exporting can break tests
// if (Highcharts.getOptions()) {
(0, _exporting2.default)(Highcharts);
(0, _boost2.default)(Highcharts);
// }

var highchartsBaseConfig = {
  credits: {
    enabled: false
  },
  legend: {
    labelFormat: '{name}'
  },
  chart: {
    type: 'scatter',
    zoomType: 'xy',
    borderWidth: 1,
    borderColor: 'dark blue',
    height: '100%'
  },
  boost: {
    useGPUTranslations: true,
    usePreAllocated: true,
    seriesThreshold: 5000
  },
  title: {
    text: null
  },
  xAxis: {
    title: {
      text: null
    },
    labels: {
      enabled: false
    },
    tickWidth: 0
  },
  yAxis: {
    title: {
      text: null
    },
    labels: {
      enabled: false
    },
    gridLineWidth: 0,
    lineWidth: 1
  },
  colors: ['#b25fbc', '#76b341', '#6882cf', '#ce9b44', '#c8577b', '#4fae84', '#c95c3f', '#7c7f39'],
  plotOptions: {
    series: {
      turboThreshold: 0,
      animation: false
    }
  }
};

var ScatterPlot = function ScatterPlot(props) {
  var wrapperClassName = props.wrapperClassName,
      chartClassName = props.chartClassName,
      series = props.series,
      highchartsConfig = props.highchartsConfig,
      children = props.children;

  var numPoints = series.reduce(function (acc, aSeries) {
    return acc + aSeries.data.length;
  }, 0);
  var config = _deepmerge2.default.all([highchartsBaseConfig, {
    plotOptions: {
      series: {
        marker: {
          radius: numPoints < 5000 ? 4 : 0.2
        }
      }
    }
  }, { series: series }, highchartsConfig], { arrayMerge: function arrayMerge(destination, source) {
      return source;
    } }); // Don’t merge

  return [_react2.default.createElement('div', { key: 'chart', className: chartClassName }, _react2.default.createElement(_reactHighcharts2.default, { config: config })), _react2.default.createElement('div', { key: 'children' }, children)];
};

ScatterPlot.propTypes = {
  wrapperClassName: _propTypes2.default.string,
  chartClassName: _propTypes2.default.string,
  series: _SeriesPropTypes2.default,
  highchartsConfig: _propTypes2.default.object,
  children: _propTypes2.default.object
};

ScatterPlot.defaultProps = {
  highchartsConfig: {},
  children: null
};

exports.default = ScatterPlot;

/***/ }),

/***/ 471:
/*!***************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-highcharts/dist/ReactHighcharts.js ***!
  \***************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {var __WEBPACK_AMD_DEFINE_FACTORY__, __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

(function webpackUniversalModuleDefinition(root, factory) {
  if (( false ? 'undefined' : _typeof(exports)) === 'object' && ( false ? 'undefined' : _typeof(module)) === 'object') module.exports = factory(__webpack_require__(/*! react */ 0), __webpack_require__(/*! highcharts */ 200));else if (true) !(__WEBPACK_AMD_DEFINE_ARRAY__ = [__webpack_require__(/*! react */ 0), __webpack_require__(/*! highcharts */ 200)], __WEBPACK_AMD_DEFINE_FACTORY__ = (factory),
				__WEBPACK_AMD_DEFINE_RESULT__ = (typeof __WEBPACK_AMD_DEFINE_FACTORY__ === 'function' ?
				(__WEBPACK_AMD_DEFINE_FACTORY__.apply(exports, __WEBPACK_AMD_DEFINE_ARRAY__)) : __WEBPACK_AMD_DEFINE_FACTORY__),
				__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));else if ((typeof exports === 'undefined' ? 'undefined' : _typeof(exports)) === 'object') exports["ReactHighcharts"] = factory(require("react"), require("highcharts"));else root["ReactHighcharts"] = factory(root["React"], root["Highcharts"]);
})(undefined, function (__WEBPACK_EXTERNAL_MODULE_4__, __WEBPACK_EXTERNAL_MODULE_17__) {
  return (/******/function (modules) {
      // webpackBootstrap
      /******/ // The module cache
      /******/var installedModules = {};
      /******/
      /******/ // The require function
      /******/function __webpack_require__(moduleId) {
        /******/
        /******/ // Check if module is in cache
        /******/if (installedModules[moduleId]) {
          /******/return installedModules[moduleId].exports;
          /******/
        }
        /******/ // Create a new module (and put it into the cache)
        /******/var module = installedModules[moduleId] = {
          /******/i: moduleId,
          /******/l: false,
          /******/exports: {}
          /******/ };
        /******/
        /******/ // Execute the module function
        /******/modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
        /******/
        /******/ // Flag the module as loaded
        /******/module.l = true;
        /******/
        /******/ // Return the exports of the module
        /******/return module.exports;
        /******/
      }
      /******/
      /******/
      /******/ // expose the modules object (__webpack_modules__)
      /******/__webpack_require__.m = modules;
      /******/
      /******/ // expose the module cache
      /******/__webpack_require__.c = installedModules;
      /******/
      /******/ // identity function for calling harmony imports with the correct context
      /******/__webpack_require__.i = function (value) {
        return value;
      };
      /******/
      /******/ // define getter function for harmony exports
      /******/__webpack_require__.d = function (exports, name, getter) {
        /******/if (!__webpack_require__.o(exports, name)) {
          /******/Object.defineProperty(exports, name, {
            /******/configurable: false,
            /******/enumerable: true,
            /******/get: getter
            /******/ });
          /******/
        }
        /******/
      };
      /******/
      /******/ // getDefaultExport function for compatibility with non-harmony modules
      /******/__webpack_require__.n = function (module) {
        /******/var getter = module && module.__esModule ?
        /******/function getDefault() {
          return module['default'];
        } :
        /******/function getModuleExports() {
          return module;
        };
        /******/__webpack_require__.d(getter, 'a', getter);
        /******/return getter;
        /******/
      };
      /******/
      /******/ // Object.prototype.hasOwnProperty.call
      /******/__webpack_require__.o = function (object, property) {
        return Object.prototype.hasOwnProperty.call(object, property);
      };
      /******/
      /******/ // __webpack_public_path__
      /******/__webpack_require__.p = "";
      /******/
      /******/ // Load entry module and return exports
      /******/return __webpack_require__(__webpack_require__.s = 18);
      /******/
    }(
    /************************************************************************/
    /******/[
    /* 0 */
    /***/function (module, exports) {

      // shim for using process in browser
      var process = module.exports = {};

      // cached from whatever global is present so that test runners that stub it
      // don't break things.  But we need to wrap it in a try catch in case it is
      // wrapped in strict mode code which doesn't define any globals.  It's inside a
      // function because try/catches deoptimize in certain engines.

      var cachedSetTimeout;
      var cachedClearTimeout;

      function defaultSetTimout() {
        throw new Error('setTimeout has not been defined');
      }
      function defaultClearTimeout() {
        throw new Error('clearTimeout has not been defined');
      }
      (function () {
        try {
          if (typeof setTimeout === 'function') {
            cachedSetTimeout = setTimeout;
          } else {
            cachedSetTimeout = defaultSetTimout;
          }
        } catch (e) {
          cachedSetTimeout = defaultSetTimout;
        }
        try {
          if (typeof clearTimeout === 'function') {
            cachedClearTimeout = clearTimeout;
          } else {
            cachedClearTimeout = defaultClearTimeout;
          }
        } catch (e) {
          cachedClearTimeout = defaultClearTimeout;
        }
      })();
      function runTimeout(fun) {
        if (cachedSetTimeout === setTimeout) {
          //normal enviroments in sane situations
          return setTimeout(fun, 0);
        }
        // if setTimeout wasn't available but was latter defined
        if ((cachedSetTimeout === defaultSetTimout || !cachedSetTimeout) && setTimeout) {
          cachedSetTimeout = setTimeout;
          return setTimeout(fun, 0);
        }
        try {
          // when when somebody has screwed with setTimeout but no I.E. maddness
          return cachedSetTimeout(fun, 0);
        } catch (e) {
          try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't trust the global object when called normally
            return cachedSetTimeout.call(null, fun, 0);
          } catch (e) {
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error
            return cachedSetTimeout.call(this, fun, 0);
          }
        }
      }
      function runClearTimeout(marker) {
        if (cachedClearTimeout === clearTimeout) {
          //normal enviroments in sane situations
          return clearTimeout(marker);
        }
        // if clearTimeout wasn't available but was latter defined
        if ((cachedClearTimeout === defaultClearTimeout || !cachedClearTimeout) && clearTimeout) {
          cachedClearTimeout = clearTimeout;
          return clearTimeout(marker);
        }
        try {
          // when when somebody has screwed with setTimeout but no I.E. maddness
          return cachedClearTimeout(marker);
        } catch (e) {
          try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't  trust the global object when called normally
            return cachedClearTimeout.call(null, marker);
          } catch (e) {
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error.
            // Some versions of I.E. have different rules for clearTimeout vs setTimeout
            return cachedClearTimeout.call(this, marker);
          }
        }
      }
      var queue = [];
      var draining = false;
      var currentQueue;
      var queueIndex = -1;

      function cleanUpNextTick() {
        if (!draining || !currentQueue) {
          return;
        }
        draining = false;
        if (currentQueue.length) {
          queue = currentQueue.concat(queue);
        } else {
          queueIndex = -1;
        }
        if (queue.length) {
          drainQueue();
        }
      }

      function drainQueue() {
        if (draining) {
          return;
        }
        var timeout = runTimeout(cleanUpNextTick);
        draining = true;

        var len = queue.length;
        while (len) {
          currentQueue = queue;
          queue = [];
          while (++queueIndex < len) {
            if (currentQueue) {
              currentQueue[queueIndex].run();
            }
          }
          queueIndex = -1;
          len = queue.length;
        }
        currentQueue = null;
        draining = false;
        runClearTimeout(timeout);
      }

      process.nextTick = function (fun) {
        var args = new Array(arguments.length - 1);
        if (arguments.length > 1) {
          for (var i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
          }
        }
        queue.push(new Item(fun, args));
        if (queue.length === 1 && !draining) {
          runTimeout(drainQueue);
        }
      };

      // v8 likes predictible objects
      function Item(fun, array) {
        this.fun = fun;
        this.array = array;
      }
      Item.prototype.run = function () {
        this.fun.apply(null, this.array);
      };
      process.title = 'browser';
      process.browser = true;
      process.env = {};
      process.argv = [];
      process.version = ''; // empty string to avoid regexp issues
      process.versions = {};

      function noop() {}

      process.on = noop;
      process.addListener = noop;
      process.once = noop;
      process.off = noop;
      process.removeListener = noop;
      process.removeAllListeners = noop;
      process.emit = noop;
      process.prependListener = noop;
      process.prependOnceListener = noop;

      process.listeners = function (name) {
        return [];
      };

      process.binding = function (name) {
        throw new Error('process.binding is not supported');
      };

      process.cwd = function () {
        return '/';
      };
      process.chdir = function (dir) {
        throw new Error('process.chdir is not supported');
      };
      process.umask = function () {
        return 0;
      };

      /***/
    },
    /* 1 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";
      /* WEBPACK VAR INJECTION */
      (function (process) {
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

        if (process.env.NODE_ENV !== 'production') {
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
        /* WEBPACK VAR INJECTION */
      }).call(exports, __webpack_require__(0));

      /***/
    },
    /* 2 */
    /***/function (module, exports, __webpack_require__) {

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

      /***/
    },
    /* 3 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";
      /* WEBPACK VAR INJECTION */
      (function (process) {
        /**
        * Copyright 2014-2015, Facebook, Inc.
        * All rights reserved.
        *
        * This source code is licensed under the BSD-style license found in the
        * LICENSE file in the root directory of this source tree. An additional grant
        * of patent rights can be found in the PATENTS file in the same directory.
        *
        */

        var emptyFunction = __webpack_require__(2);

        /**
         * Similar to invariant but only logs a warning if the condition is not met.
         * This can be used to log issues in development environments in critical
         * paths. Removing the logging code for production environments will keep the
         * same logic and follow the same code paths.
         */

        var warning = emptyFunction;

        if (process.env.NODE_ENV !== 'production') {
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
        /* WEBPACK VAR INJECTION */
      }).call(exports, __webpack_require__(0));

      /***/
    },
    /* 4 */
    /***/function (module, exports) {

      module.exports = __WEBPACK_EXTERNAL_MODULE_4__;

      /***/
    },
    /* 5 */
    /***/function (module, exports, __webpack_require__) {

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

      /***/
    },
    /* 6 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";
      /* WEBPACK VAR INJECTION */
      (function (process) {
        /**
        * Copyright 2013-present, Facebook, Inc.
        * All rights reserved.
        *
        * This source code is licensed under the BSD-style license found in the
        * LICENSE file in the root directory of this source tree. An additional grant
        * of patent rights can be found in the PATENTS file in the same directory.
        *
        */

        var _assign = __webpack_require__(9);

        var emptyObject = __webpack_require__(8);
        var _invariant = __webpack_require__(1);

        if (process.env.NODE_ENV !== 'production') {
          var warning = __webpack_require__(3);
        }

        var MIXINS_KEY = 'mixins';

        // Helper function to allow the creation of anonymous functions which do not
        // have .name set to the name of the variable being assigned to.
        function identity(fn) {
          return fn;
        }

        var ReactPropTypeLocationNames;
        if (process.env.NODE_ENV !== 'production') {
          ReactPropTypeLocationNames = {
            prop: 'prop',
            context: 'context',
            childContext: 'child context'
          };
        } else {
          ReactPropTypeLocationNames = {};
        }

        function factory(ReactComponent, isValidElement, ReactNoopUpdateQueue) {
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
            displayName: function displayName(Constructor, _displayName) {
              Constructor.displayName = _displayName;
            },
            mixins: function mixins(Constructor, _mixins) {
              if (_mixins) {
                for (var i = 0; i < _mixins.length; i++) {
                  mixSpecIntoComponent(Constructor, _mixins[i]);
                }
              }
            },
            childContextTypes: function childContextTypes(Constructor, _childContextTypes) {
              if (process.env.NODE_ENV !== 'production') {
                validateTypeDef(Constructor, _childContextTypes, 'childContext');
              }
              Constructor.childContextTypes = _assign({}, Constructor.childContextTypes, _childContextTypes);
            },
            contextTypes: function contextTypes(Constructor, _contextTypes) {
              if (process.env.NODE_ENV !== 'production') {
                validateTypeDef(Constructor, _contextTypes, 'context');
              }
              Constructor.contextTypes = _assign({}, Constructor.contextTypes, _contextTypes);
            },
            /**
             * Special case getDefaultProps which should move into statics but requires
             * automatic merging.
             */
            getDefaultProps: function getDefaultProps(Constructor, _getDefaultProps) {
              if (Constructor.getDefaultProps) {
                Constructor.getDefaultProps = createMergedResultFunction(Constructor.getDefaultProps, _getDefaultProps);
              } else {
                Constructor.getDefaultProps = _getDefaultProps;
              }
            },
            propTypes: function propTypes(Constructor, _propTypes) {
              if (process.env.NODE_ENV !== 'production') {
                validateTypeDef(Constructor, _propTypes, 'prop');
              }
              Constructor.propTypes = _assign({}, Constructor.propTypes, _propTypes);
            },
            statics: function statics(Constructor, _statics) {
              mixStaticSpecIntoComponent(Constructor, _statics);
            },
            autobind: function autobind() {} };

          function validateTypeDef(Constructor, typeDef, location) {
            for (var propName in typeDef) {
              if (typeDef.hasOwnProperty(propName)) {
                // use a warning instead of an _invariant so components
                // don't show up in prod but only in __DEV__
                process.env.NODE_ENV !== 'production' ? warning(typeof typeDef[propName] === 'function', '%s: %s type `%s` is invalid; it must be a function, usually from ' + 'React.PropTypes.', Constructor.displayName || 'ReactClass', ReactPropTypeLocationNames[location], propName) : void 0;
              }
            }
          }

          function validateMethodOverride(isAlreadyDefined, name) {
            var specPolicy = ReactClassInterface.hasOwnProperty(name) ? ReactClassInterface[name] : null;

            // Disallow overriding of base class methods unless explicitly allowed.
            if (ReactClassMixin.hasOwnProperty(name)) {
              _invariant(specPolicy === 'OVERRIDE_BASE', 'ReactClassInterface: You are attempting to override ' + '`%s` from your class specification. Ensure that your method names ' + 'do not overlap with React methods.', name);
            }

            // Disallow defining methods more than once unless explicitly allowed.
            if (isAlreadyDefined) {
              _invariant(specPolicy === 'DEFINE_MANY' || specPolicy === 'DEFINE_MANY_MERGED', 'ReactClassInterface: You are attempting to define ' + '`%s` on your component more than once. This conflict may be due ' + 'to a mixin.', name);
            }
          }

          /**
           * Mixin helper which handles policy validation and reserved
           * specification keys when building React classes.
           */
          function mixSpecIntoComponent(Constructor, spec) {
            if (!spec) {
              if (process.env.NODE_ENV !== 'production') {
                var typeofSpec = typeof spec === 'undefined' ? 'undefined' : _typeof(spec);
                var isMixinValid = typeofSpec === 'object' && spec !== null;

                process.env.NODE_ENV !== 'production' ? warning(isMixinValid, '%s: You\'re attempting to include a mixin that is either null ' + 'or not an object. Check the mixins included by the component, ' + 'as well as any mixins they include themselves. ' + 'Expected object but got %s.', Constructor.displayName || 'ReactClass', spec === null ? null : typeofSpec) : void 0;
              }

              return;
            }

            _invariant(typeof spec !== 'function', 'ReactClass: You\'re attempting to ' + 'use a component class or function as a mixin. Instead, just use a ' + 'regular object.');
            _invariant(!isValidElement(spec), 'ReactClass: You\'re attempting to ' + 'use a component as a mixin. Instead, just use a regular object.');

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
                    _invariant(isReactClassMethod && (specPolicy === 'DEFINE_MANY_MERGED' || specPolicy === 'DEFINE_MANY'), 'ReactClass: Unexpected spec policy %s for key %s ' + 'when mixing in component specs.', specPolicy, name);

                    // For methods which are defined more than once, call the existing
                    // methods before calling the new property, merging if appropriate.
                    if (specPolicy === 'DEFINE_MANY_MERGED') {
                      proto[name] = createMergedResultFunction(proto[name], property);
                    } else if (specPolicy === 'DEFINE_MANY') {
                      proto[name] = createChainedFunction(proto[name], property);
                    }
                  } else {
                    proto[name] = property;
                    if (process.env.NODE_ENV !== 'production') {
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
              _invariant(!isReserved, 'ReactClass: You are attempting to define a reserved ' + 'property, `%s`, that shouldn\'t be on the "statics" key. Define it ' + 'as an instance property instead; it will still be accessible on the ' + 'constructor.', name);

              var isInherited = name in Constructor;
              _invariant(!isInherited, 'ReactClass: You are attempting to define ' + '`%s` on your component more than once. This conflict may be ' + 'due to a mixin.', name);
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
            _invariant(one && two && (typeof one === 'undefined' ? 'undefined' : _typeof(one)) === 'object' && (typeof two === 'undefined' ? 'undefined' : _typeof(two)) === 'object', 'mergeIntoWithNoDuplicateKeys(): Cannot merge non-objects.');

            for (var key in two) {
              if (two.hasOwnProperty(key)) {
                _invariant(one[key] === undefined, 'mergeIntoWithNoDuplicateKeys(): ' + 'Tried to merge two objects with the same key: `%s`. This conflict ' + 'may be due to a mixin; in particular, this may be caused by two ' + 'getInitialState() or getDefaultProps() methods returning objects ' + 'with clashing keys.', key);
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
            if (process.env.NODE_ENV !== 'production') {
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
                  process.env.NODE_ENV !== 'production' ? warning(false, 'bind(): React component methods may only be bound to the ' + 'component instance. See %s', componentName) : void 0;
                } else if (!args.length) {
                  process.env.NODE_ENV !== 'production' ? warning(false, 'bind(): You are binding a component method to the component. ' + 'React does this for you automatically in a high-performance ' + 'way, so you can safely remove this call. See %s', componentName) : void 0;
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

          var IsMountedMixin = {
            componentDidMount: function componentDidMount() {
              this.__isMounted = true;
            },
            componentWillUnmount: function componentWillUnmount() {
              this.__isMounted = false;
            }
          };

          /**
           * Add more to the ReactClass base class. These are all legacy features and
           * therefore not already part of the modern ReactComponent.
           */
          var ReactClassMixin = {

            /**
             * TODO: This will be deprecated because state should always keep a consistent
             * type signature and the only use case for this, is to avoid that.
             */
            replaceState: function replaceState(newState, callback) {
              this.updater.enqueueReplaceState(this, newState, callback);
            },

            /**
             * Checks whether or not this composite component is mounted.
             * @return {boolean} True if mounted, false otherwise.
             * @protected
             * @final
             */
            isMounted: function isMounted() {
              if (process.env.NODE_ENV !== 'production') {
                process.env.NODE_ENV !== 'production' ? warning(this.__didWarnIsMounted, '%s: isMounted is deprecated. Instead, make sure to clean up ' + 'subscriptions and pending requests in componentWillUnmount to ' + 'prevent memory leaks.', this.constructor && this.constructor.displayName || this.name || 'Component') : void 0;
                this.__didWarnIsMounted = true;
              }
              return !!this.__isMounted;
            }
          };

          var ReactClassComponent = function ReactClassComponent() {};
          _assign(ReactClassComponent.prototype, ReactComponent.prototype, ReactClassMixin);

          /**
           * Creates a composite component class given a class specification.
           * See https://facebook.github.io/react/docs/top-level-api.html#react.createclass
           *
           * @param {object} spec Class specification (which must define `render`).
           * @return {function} Component constructor function.
           * @public
           */
          function createClass(spec) {
            // To keep our warnings more understandable, we'll use a little hack here to
            // ensure that Constructor.name !== 'Constructor'. This makes sure we don't
            // unnecessarily identify a class without displayName as 'Constructor'.
            var Constructor = identity(function (props, context, updater) {
              // This constructor gets overridden by mocks. The argument is used
              // by mocks to assert on what gets mounted.

              if (process.env.NODE_ENV !== 'production') {
                process.env.NODE_ENV !== 'production' ? warning(this instanceof Constructor, 'Something is calling a React component directly. Use a factory or ' + 'JSX instead. See: https://fb.me/react-legacyfactory') : void 0;
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
              if (process.env.NODE_ENV !== 'production') {
                // We allow auto-mocks to proceed as if they're returning null.
                if (initialState === undefined && this.getInitialState._isMockFunction) {
                  // This is probably bad practice. Consider warning here and
                  // deprecating this convenience.
                  initialState = null;
                }
              }
              _invariant((typeof initialState === 'undefined' ? 'undefined' : _typeof(initialState)) === 'object' && !Array.isArray(initialState), '%s.getInitialState(): must return an object or null', Constructor.displayName || 'ReactCompositeComponent');

              this.state = initialState;
            });
            Constructor.prototype = new ReactClassComponent();
            Constructor.prototype.constructor = Constructor;
            Constructor.prototype.__reactAutoBindPairs = [];

            injectedMixins.forEach(mixSpecIntoComponent.bind(null, Constructor));

            mixSpecIntoComponent(Constructor, IsMountedMixin);
            mixSpecIntoComponent(Constructor, spec);

            // Initialize the defaultProps property after all mixins have been merged.
            if (Constructor.getDefaultProps) {
              Constructor.defaultProps = Constructor.getDefaultProps();
            }

            if (process.env.NODE_ENV !== 'production') {
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

            _invariant(Constructor.prototype.render, 'createClass(...): Class specification must implement a `render` method.');

            if (process.env.NODE_ENV !== 'production') {
              process.env.NODE_ENV !== 'production' ? warning(!Constructor.prototype.componentShouldUpdate, '%s has a method called ' + 'componentShouldUpdate(). Did you mean shouldComponentUpdate()? ' + 'The name is phrased as a question because the function is ' + 'expected to return a value.', spec.displayName || 'A component') : void 0;
              process.env.NODE_ENV !== 'production' ? warning(!Constructor.prototype.componentWillRecieveProps, '%s has a method called ' + 'componentWillRecieveProps(). Did you mean componentWillReceiveProps()?', spec.displayName || 'A component') : void 0;
            }

            // Reduce time spent doing lookups by setting these on the prototype.
            for (var methodName in ReactClassInterface) {
              if (!Constructor.prototype[methodName]) {
                Constructor.prototype[methodName] = null;
              }
            }

            return Constructor;
          }

          return createClass;
        }

        module.exports = factory;

        /* WEBPACK VAR INJECTION */
      }).call(exports, __webpack_require__(0));

      /***/
    },
    /* 7 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";
      /**
       * Copyright 2013-present, Facebook, Inc.
       * All rights reserved.
       *
       * This source code is licensed under the BSD-style license found in the
       * LICENSE file in the root directory of this source tree. An additional grant
       * of patent rights can be found in the PATENTS file in the same directory.
       *
       */

      var React = __webpack_require__(4);
      var factory = __webpack_require__(6);

      // Hack to grab NoopUpdateQueue from isomorphic React
      var ReactNoopUpdateQueue = new React.Component().updater;

      module.exports = factory(React.Component, React.isValidElement, ReactNoopUpdateQueue);

      /***/
    },
    /* 8 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";
      /* WEBPACK VAR INJECTION */
      (function (process) {
        /**
        * Copyright (c) 2013-present, Facebook, Inc.
        * All rights reserved.
        *
        * This source code is licensed under the BSD-style license found in the
        * LICENSE file in the root directory of this source tree. An additional grant
        * of patent rights can be found in the PATENTS file in the same directory.
        *
        */

        var emptyObject = {};

        if (process.env.NODE_ENV !== 'production') {
          Object.freeze(emptyObject);
        }

        module.exports = emptyObject;
        /* WEBPACK VAR INJECTION */
      }).call(exports, __webpack_require__(0));

      /***/
    },
    /* 9 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";
      /*
      object-assign
      (c) Sindre Sorhus
      @license MIT
      */

      /* eslint-disable no-unused-vars */

      var getOwnPropertySymbols = Object.getOwnPropertySymbols;
      var hasOwnProperty = Object.prototype.hasOwnProperty;
      var propIsEnumerable = Object.prototype.propertyIsEnumerable;

      function toObject(val) {
        if (val === null || val === undefined) {
          throw new TypeError('Object.assign cannot be called with null or undefined');
        }

        return Object(val);
      }

      function shouldUseNative() {
        try {
          if (!Object.assign) {
            return false;
          }

          // Detect buggy property enumeration order in older V8 versions.

          // https://bugs.chromium.org/p/v8/issues/detail?id=4118
          var test1 = new String('abc'); // eslint-disable-line no-new-wrappers
          test1[5] = 'de';
          if (Object.getOwnPropertyNames(test1)[0] === '5') {
            return false;
          }

          // https://bugs.chromium.org/p/v8/issues/detail?id=3056
          var test2 = {};
          for (var i = 0; i < 10; i++) {
            test2['_' + String.fromCharCode(i)] = i;
          }
          var order2 = Object.getOwnPropertyNames(test2).map(function (n) {
            return test2[n];
          });
          if (order2.join('') !== '0123456789') {
            return false;
          }

          // https://bugs.chromium.org/p/v8/issues/detail?id=3056
          var test3 = {};
          'abcdefghijklmnopqrst'.split('').forEach(function (letter) {
            test3[letter] = letter;
          });
          if (Object.keys(Object.assign({}, test3)).join('') !== 'abcdefghijklmnopqrst') {
            return false;
          }

          return true;
        } catch (err) {
          // We don't expect any of the above to throw, but better to be safe.
          return false;
        }
      }

      module.exports = shouldUseNative() ? Object.assign : function (target, source) {
        var from;
        var to = toObject(target);
        var symbols;

        for (var s = 1; s < arguments.length; s++) {
          from = Object(arguments[s]);

          for (var key in from) {
            if (hasOwnProperty.call(from, key)) {
              to[key] = from[key];
            }
          }

          if (getOwnPropertySymbols) {
            symbols = getOwnPropertySymbols(from);
            for (var i = 0; i < symbols.length; i++) {
              if (propIsEnumerable.call(from, symbols[i])) {
                to[symbols[i]] = from[symbols[i]];
              }
            }
          }
        }

        return to;
      };

      /***/
    },
    /* 10 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";
      /* WEBPACK VAR INJECTION */
      (function (global) {

        var _extends = Object.assign || function (target) {
          for (var i = 1; i < arguments.length; i++) {
            var source = arguments[i];for (var key in source) {
              if (Object.prototype.hasOwnProperty.call(source, key)) {
                target[key] = source[key];
              }
            }
          }return target;
        };

        var React = __webpack_require__(4);
        var createReactClass = __webpack_require__(7);
        var PropTypes = __webpack_require__(14);
        var win = typeof global === 'undefined' ? window : global;

        module.exports = function (chartType, Highcharts) {
          var displayName = 'Highcharts' + chartType;
          var result = createReactClass({
            displayName: displayName,

            propTypes: {
              config: PropTypes.object,
              isPureConfig: PropTypes.bool,
              neverReflow: PropTypes.bool,
              callback: PropTypes.func,
              domProps: PropTypes.object
            },

            defaultProps: {
              callback: function callback() {},
              domProps: {}
            },

            renderChart: function renderChart(config) {
              var _this = this;

              if (!config) {
                throw new Error('Config must be specified for the ' + displayName + ' component');
              }
              var chartConfig = config.chart;
              this.chart = new Highcharts[chartType](_extends({}, config, {
                chart: _extends({}, chartConfig, {
                  renderTo: this.refs.chart
                })
              }), this.props.callback);

              if (!this.props.neverReflow) {
                win && win.requestAnimationFrame && requestAnimationFrame(function () {
                  _this.chart && _this.chart.options && _this.chart.reflow();
                });
              }
            },

            shouldComponentUpdate: function shouldComponentUpdate(nextProps) {
              if (nextProps.neverReflow || nextProps.isPureConfig && this.props.config === nextProps.config) {
                return true;
              }
              this.renderChart(nextProps.config);
              return false;
            },

            getChart: function getChart() {
              if (!this.chart) {
                throw new Error('getChart() should not be called before the component is mounted');
              }
              return this.chart;
            },

            componentDidMount: function componentDidMount() {
              this.renderChart(this.props.config);
            },

            componentWillUnmount: function componentWillUnmount() {
              this.chart.destroy();
            },

            render: function render() {
              return React.createElement('div', _extends({ ref: 'chart' }, this.props.domProps));
            }
          });

          result.Highcharts = Highcharts;
          result.withHighcharts = function (Highcharts) {
            return module.exports(chartType, Highcharts);
          };
          return result;
        };
        /* WEBPACK VAR INJECTION */
      }).call(exports, __webpack_require__(15));

      /***/
    },
    /* 11 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";
      /* WEBPACK VAR INJECTION */
      (function (process) {
        /**
        * Copyright 2013-present, Facebook, Inc.
        * All rights reserved.
        *
        * This source code is licensed under the BSD-style license found in the
        * LICENSE file in the root directory of this source tree. An additional grant
        * of patent rights can be found in the PATENTS file in the same directory.
        */

        if (process.env.NODE_ENV !== 'production') {
          var invariant = __webpack_require__(1);
          var warning = __webpack_require__(3);
          var ReactPropTypesSecret = __webpack_require__(5);
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
          if (process.env.NODE_ENV !== 'production') {
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

        /* WEBPACK VAR INJECTION */
      }).call(exports, __webpack_require__(0));

      /***/
    },
    /* 12 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";
      /**
       * Copyright 2013-present, Facebook, Inc.
       * All rights reserved.
       *
       * This source code is licensed under the BSD-style license found in the
       * LICENSE file in the root directory of this source tree. An additional grant
       * of patent rights can be found in the PATENTS file in the same directory.
       */

      var emptyFunction = __webpack_require__(2);
      var invariant = __webpack_require__(1);

      module.exports = function () {
        // Important!
        // Keep this list in sync with production version in `./factoryWithTypeCheckers.js`.
        function shim() {
          invariant(false, 'Calling PropTypes validators directly is not supported by the `prop-types` package. ' + 'Use PropTypes.checkPropTypes() to call them. ' + 'Read more at http://fb.me/use-check-prop-types');
        };
        shim.isRequired = shim;
        function getShim() {
          return shim;
        };
        var ReactPropTypes = {
          array: shim,
          bool: shim,
          func: shim,
          number: shim,
          object: shim,
          string: shim,
          symbol: shim,

          any: shim,
          arrayOf: getShim,
          element: shim,
          instanceOf: getShim,
          node: shim,
          objectOf: getShim,
          oneOf: getShim,
          oneOfType: getShim,
          shape: getShim
        };

        ReactPropTypes.checkPropTypes = emptyFunction;
        ReactPropTypes.PropTypes = ReactPropTypes;

        return ReactPropTypes;
      };

      /***/
    },
    /* 13 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";
      /* WEBPACK VAR INJECTION */
      (function (process) {
        /**
        * Copyright 2013-present, Facebook, Inc.
        * All rights reserved.
        *
        * This source code is licensed under the BSD-style license found in the
        * LICENSE file in the root directory of this source tree. An additional grant
        * of patent rights can be found in the PATENTS file in the same directory.
        */

        var emptyFunction = __webpack_require__(2);
        var invariant = __webpack_require__(1);
        var warning = __webpack_require__(3);

        var ReactPropTypesSecret = __webpack_require__(5);
        var checkPropTypes = __webpack_require__(11);

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
            if (process.env.NODE_ENV !== 'production') {
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
                } else if (process.env.NODE_ENV !== 'production' && typeof console !== 'undefined') {
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
              process.env.NODE_ENV !== 'production' ? warning(false, 'Invalid argument supplied to oneOf, expected an instance of array.') : void 0;
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
              process.env.NODE_ENV !== 'production' ? warning(false, 'Invalid argument supplied to oneOfType, expected an instance of array.') : void 0;
              return emptyFunction.thatReturnsNull;
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

          ReactPropTypes.checkPropTypes = checkPropTypes;
          ReactPropTypes.PropTypes = ReactPropTypes;

          return ReactPropTypes;
        };

        /* WEBPACK VAR INJECTION */
      }).call(exports, __webpack_require__(0));

      /***/
    },
    /* 14 */
    /***/function (module, exports, __webpack_require__) {

      /* WEBPACK VAR INJECTION */(function (process) {
        /**
        * Copyright 2013-present, Facebook, Inc.
        * All rights reserved.
        *
        * This source code is licensed under the BSD-style license found in the
        * LICENSE file in the root directory of this source tree. An additional grant
        * of patent rights can be found in the PATENTS file in the same directory.
        */

        if (process.env.NODE_ENV !== 'production') {
          var REACT_ELEMENT_TYPE = typeof Symbol === 'function' && Symbol.for && Symbol.for('react.element') || 0xeac7;

          var isValidElement = function isValidElement(object) {
            return (typeof object === 'undefined' ? 'undefined' : _typeof(object)) === 'object' && object !== null && object.$$typeof === REACT_ELEMENT_TYPE;
          };

          // By explicitly using `prop-types` you are opting into new development behavior.
          // http://fb.me/prop-types-in-prod
          var throwOnDirectAccess = true;
          module.exports = __webpack_require__(13)(isValidElement, throwOnDirectAccess);
        } else {
          // By explicitly using `prop-types` you are opting into new production behavior.
          // http://fb.me/prop-types-in-prod
          module.exports = __webpack_require__(12)();
        }

        /* WEBPACK VAR INJECTION */
      }).call(exports, __webpack_require__(0));

      /***/
    },
    /* 15 */
    /***/function (module, exports) {

      var g;

      // This works in non-strict mode
      g = function () {
        return this;
      }();

      try {
        // This works if eval is allowed (see CSP)
        g = g || Function("return this")() || (1, eval)("this");
      } catch (e) {
        // This works if the window reference is available
        if ((typeof window === 'undefined' ? 'undefined' : _typeof(window)) === "object") g = window;
      }

      // g can still be undefined, but nothing to do about it...
      // We return undefined, instead of nothing here, so it's
      // easier to handle this case. if(!global) { ...}

      module.exports = g;

      /***/
    },
    /* 16 */
    /***/function (module, exports, __webpack_require__) {

      "use strict";

      module.exports = __webpack_require__(10)('Chart', __webpack_require__(17));

      /***/
    },
    /* 17 */
    /***/function (module, exports) {

      module.exports = __WEBPACK_EXTERNAL_MODULE_17__;

      /***/
    },
    /* 18 */
    /***/function (module, exports, __webpack_require__) {

      module.exports = __webpack_require__(16);

      /***/
    }]
    /******/)
  );
});
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/module.js */ 1)(module)))

/***/ }),

/***/ 472:
/*!******************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/highcharts/modules/exporting.js ***!
  \******************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/*
 Highcharts JS v5.0.14 (2017-07-28)
 Exporting module

 (c) 2010-2017 Torstein Honsi

 License: www.highcharts.com/license
*/
(function (k) {
  "object" === ( false ? "undefined" : _typeof(module)) && module.exports ? module.exports = k : k(Highcharts);
})(function (k) {
  (function (f) {
    var k = f.defaultOptions,
        p = f.doc,
        B = f.Chart,
        x = f.addEvent,
        I = f.removeEvent,
        F = f.fireEvent,
        r = f.createElement,
        C = f.discardElement,
        v = f.css,
        n = f.merge,
        D = f.pick,
        h = f.each,
        G = f.objectEach,
        t = f.extend,
        J = f.isTouchDevice,
        E = f.win,
        H = E.navigator.userAgent,
        K = f.Renderer.prototype.symbols;/Edge\/|Trident\/|MSIE /.test(H);/firefox/i.test(H);t(k.lang, { printChart: "Print chart", downloadPNG: "Download PNG image", downloadJPEG: "Download JPEG image",
      downloadPDF: "Download PDF document", downloadSVG: "Download SVG vector image", contextButtonTitle: "Chart context menu" });k.navigation = { buttonOptions: { theme: {}, symbolSize: 14, symbolX: 12.5, symbolY: 10.5, align: "right", buttonSpacing: 3, height: 22, verticalAlign: "top", width: 24 } };n(!0, k.navigation, { menuStyle: { border: "1px solid #999999", background: "#ffffff", padding: "5px 0" }, menuItemStyle: { padding: "0.5em 1em", background: "none", color: "#333333", fontSize: J ? "14px" : "11px", transition: "background 250ms, color 250ms" }, menuItemHoverStyle: { background: "#335cad",
        color: "#ffffff" }, buttonOptions: { symbolFill: "#666666", symbolStroke: "#666666", symbolStrokeWidth: 3, theme: { fill: "#ffffff", stroke: "none", padding: 5 } } });k.exporting = { type: "image/png", url: "https://export.highcharts.com/", printMaxWidth: 780, scale: 2, buttons: { contextButton: { className: "highcharts-contextbutton", menuClassName: "highcharts-contextmenu", symbol: "menu", _titleKey: "contextButtonTitle", menuItems: "printChart separator downloadPNG downloadJPEG downloadPDF downloadSVG".split(" ") } }, menuItemDefinitions: { printChart: { textKey: "printChart",
          onclick: function onclick() {
            this.print();
          } }, separator: { separator: !0 }, downloadPNG: { textKey: "downloadPNG", onclick: function onclick() {
            this.exportChart();
          } }, downloadJPEG: { textKey: "downloadJPEG", onclick: function onclick() {
            this.exportChart({ type: "image/jpeg" });
          } }, downloadPDF: { textKey: "downloadPDF", onclick: function onclick() {
            this.exportChart({ type: "application/pdf" });
          } }, downloadSVG: { textKey: "downloadSVG", onclick: function onclick() {
            this.exportChart({ type: "image/svg+xml" });
          } } } };f.post = function (a, b, e) {
      var c = r("form", n({ method: "post", action: a, enctype: "multipart/form-data" }, e), { display: "none" }, p.body);G(b, function (a, b) {
        r("input", { type: "hidden", name: b, value: a }, null, c);
      });c.submit();C(c);
    };t(B.prototype, { sanitizeSVG: function sanitizeSVG(a, b) {
        if (b && b.exporting && b.exporting.allowHTML) {
          var e = a.match(/<\/svg>(.*?$)/);e && e[1] && (e = '\x3cforeignObject x\x3d"0" y\x3d"0" width\x3d"' + b.chart.width + '" height\x3d"' + b.chart.height + '"\x3e\x3cbody xmlns\x3d"http://www.w3.org/1999/xhtml"\x3e' + e[1] + "\x3c/body\x3e\x3c/foreignObject\x3e", a = a.replace("\x3c/svg\x3e", e + "\x3c/svg\x3e"));
        }a = a.replace(/zIndex="[^"]+"/g, "").replace(/isShadow="[^"]+"/g, "").replace(/symbolName="[^"]+"/g, "").replace(/jQuery[0-9]+="[^"]+"/g, "").replace(/url\(("|&quot;)(\S+)("|&quot;)\)/g, "url($2)").replace(/url\([^#]+#/g, "url(#").replace(/<svg /, '\x3csvg xmlns:xlink\x3d"http://www.w3.org/1999/xlink" ').replace(/ (NS[0-9]+\:)?href=/g, " xlink:href\x3d").replace(/\n/, " ").replace(/<\/svg>.*?$/, "\x3c/svg\x3e").replace(/(fill|stroke)="rgba\(([ 0-9]+,[ 0-9]+,[ 0-9]+),([ 0-9\.]+)\)"/g, '$1\x3d"rgb($2)" $1-opacity\x3d"$3"').replace(/&nbsp;/g, "\xA0").replace(/&shy;/g, "\xAD");return a = a.replace(/<IMG /g, "\x3cimage ").replace(/<(\/?)TITLE>/g, "\x3c$1title\x3e").replace(/height=([^" ]+)/g, 'height\x3d"$1"').replace(/width=([^" ]+)/g, 'width\x3d"$1"').replace(/hc-svg-href="([^"]+)">/g, 'xlink:href\x3d"$1"/\x3e').replace(/ id=([^" >]+)/g, ' id\x3d"$1"').replace(/class=([^" >]+)/g, 'class\x3d"$1"').replace(/ transform /g, " ").replace(/:(path|rect)/g, "$1").replace(/style="([^"]+)"/g, function (a) {
          return a.toLowerCase();
        });
      }, getChartHTML: function getChartHTML() {
        return this.container.innerHTML;
      },
      getSVG: function getSVG(a) {
        var b,
            e,
            c,
            w,
            m,
            g = n(this.options, a);p.createElementNS || (p.createElementNS = function (a, b) {
          return p.createElement(b);
        });e = r("div", null, { position: "absolute", top: "-9999em", width: this.chartWidth + "px", height: this.chartHeight + "px" }, p.body);c = this.renderTo.style.width;m = this.renderTo.style.height;c = g.exporting.sourceWidth || g.chart.width || /px$/.test(c) && parseInt(c, 10) || 600;m = g.exporting.sourceHeight || g.chart.height || /px$/.test(m) && parseInt(m, 10) || 400;t(g.chart, { animation: !1, renderTo: e, forExport: !0,
          renderer: "SVGRenderer", width: c, height: m });g.exporting.enabled = !1;delete g.data;g.series = [];h(this.series, function (a) {
          w = n(a.userOptions, { animation: !1, enableMouseTracking: !1, showCheckbox: !1, visible: a.visible });w.isInternal || g.series.push(w);
        });h(this.axes, function (a) {
          a.userOptions.internalKey || (a.userOptions.internalKey = f.uniqueKey());
        });b = new f.Chart(g, this.callback);a && h(["xAxis", "yAxis", "series"], function (c) {
          var d = {};a[c] && (d[c] = a[c], b.update(d));
        });h(this.axes, function (a) {
          var c = f.find(b.axes, function (b) {
            return b.options.internalKey === a.userOptions.internalKey;
          }),
              d = a.getExtremes(),
              e = d.userMin,
              d = d.userMax;!c || void 0 === e && void 0 === d || c.setExtremes(e, d, !0, !1);
        });c = b.getChartHTML();c = this.sanitizeSVG(c, g);g = null;b.destroy();C(e);return c;
      }, getSVGForExport: function getSVGForExport(a, b) {
        var e = this.options.exporting;return this.getSVG(n({ chart: { borderRadius: 0 } }, e.chartOptions, b, { exporting: { sourceWidth: a && a.sourceWidth || e.sourceWidth, sourceHeight: a && a.sourceHeight || e.sourceHeight } }));
      }, exportChart: function exportChart(a, b) {
        b = this.getSVGForExport(a, b);a = n(this.options.exporting, a);f.post(a.url, { filename: a.filename || "chart", type: a.type, width: a.width || 0, scale: a.scale, svg: b }, a.formAttributes);
      }, print: function print() {
        var a = this,
            b = a.container,
            e = [],
            c = b.parentNode,
            f = p.body,
            m = f.childNodes,
            g = a.options.exporting.printMaxWidth,
            d,
            u;if (!a.isPrinting) {
          a.isPrinting = !0;a.pointer.reset(null, 0);F(a, "beforePrint");if (u = g && a.chartWidth > g) d = [a.options.chart.width, void 0, !1], a.setSize(g, void 0, !1);h(m, function (a, b) {
            1 === a.nodeType && (e[b] = a.style.display, a.style.display = "none");
          });f.appendChild(b);E.focus();
          E.print();setTimeout(function () {
            c.appendChild(b);h(m, function (a, b) {
              1 === a.nodeType && (a.style.display = e[b]);
            });a.isPrinting = !1;u && a.setSize.apply(a, d);F(a, "afterPrint");
          }, 1E3);
        }
      }, contextMenu: function contextMenu(a, b, e, c, w, m, g) {
        var d = this,
            u = d.options.navigation,
            k = d.chartWidth,
            q = d.chartHeight,
            n = "cache-" + a,
            l = d[n],
            y = Math.max(w, m),
            z,
            A;l || (d[n] = l = r("div", { className: a }, { position: "absolute", zIndex: 1E3, padding: y + "px" }, d.container), z = r("div", { className: "highcharts-menu" }, null, l), v(z, t({ MozBoxShadow: "3px 3px 10px #888", WebkitBoxShadow: "3px 3px 10px #888",
          boxShadow: "3px 3px 10px #888" }, u.menuStyle)), A = function A() {
          v(l, { display: "none" });g && g.setState(0);d.openMenu = !1;
        }, d.exportEvents.push(x(l, "mouseleave", function () {
          l.hideTimer = setTimeout(A, 500);
        }), x(l, "mouseenter", function () {
          clearTimeout(l.hideTimer);
        }), x(p, "mouseup", function (b) {
          d.pointer.inClass(b.target, a) || A();
        })), h(b, function (a) {
          "string" === typeof a && (a = d.options.exporting.menuItemDefinitions[a]);if (f.isObject(a, !0)) {
            var b;a.separator ? b = r("hr", null, null, z) : (b = r("div", { className: "highcharts-menu-item",
              onclick: function onclick(b) {
                b && b.stopPropagation();A();a.onclick && a.onclick.apply(d, arguments);
              }, innerHTML: a.text || d.options.lang[a.textKey] }, null, z), b.onmouseover = function () {
              v(this, u.menuItemHoverStyle);
            }, b.onmouseout = function () {
              v(this, u.menuItemStyle);
            }, v(b, t({ cursor: "pointer" }, u.menuItemStyle)));d.exportDivElements.push(b);
          }
        }), d.exportDivElements.push(z, l), d.exportMenuWidth = l.offsetWidth, d.exportMenuHeight = l.offsetHeight);b = { display: "block" };e + d.exportMenuWidth > k ? b.right = k - e - w - y + "px" : b.left = e - y + "px";c + m + d.exportMenuHeight > q && "top" !== g.alignOptions.verticalAlign ? b.bottom = q - c - y + "px" : b.top = c + m - y + "px";v(l, b);d.openMenu = !0;
      }, addButton: function addButton(a) {
        var b = this,
            e = b.renderer,
            c = n(b.options.navigation.buttonOptions, a),
            f = c.onclick,
            m = c.menuItems,
            g,
            d,
            k = c.symbolSize || 12;b.btnCount || (b.btnCount = 0);b.exportDivElements || (b.exportDivElements = [], b.exportSVGElements = []);if (!1 !== c.enabled) {
          var h = c.theme,
              q = h.states,
              p = q && q.hover,
              q = q && q.select,
              l;delete h.states;f ? l = function l(a) {
            a.stopPropagation();f.call(b, a);
          } : m && (l = function l() {
            b.contextMenu(d.menuClassName, m, d.translateX, d.translateY, d.width, d.height, d);d.setState(2);
          });c.text && c.symbol ? h.paddingLeft = D(h.paddingLeft, 25) : c.text || t(h, { width: c.width, height: c.height, padding: 0 });d = e.button(c.text, 0, 0, l, h, p, q).addClass(a.className).attr({ "stroke-linecap": "round", title: b.options.lang[c._titleKey], zIndex: 3 });d.menuClassName = a.menuClassName || "highcharts-menu-" + b.btnCount++;c.symbol && (g = e.symbol(c.symbol, c.symbolX - k / 2, c.symbolY - k / 2, k, k).addClass("highcharts-button-symbol").attr({ zIndex: 1 }).add(d), g.attr({ stroke: c.symbolStroke,
            fill: c.symbolFill, "stroke-width": c.symbolStrokeWidth || 1 }));d.add().align(t(c, { width: d.width, x: D(c.x, b.buttonOffset) }), !0, "spacingBox");b.buttonOffset += (d.width + c.buttonSpacing) * ("right" === c.align ? -1 : 1);b.exportSVGElements.push(d, g);
        }
      }, destroyExport: function destroyExport(a) {
        var b = a ? a.target : this;a = b.exportSVGElements;var e = b.exportDivElements,
            c = b.exportEvents,
            f;a && (h(a, function (a, c) {
          a && (a.onclick = a.ontouchstart = null, f = "cache-" + a.menuClassName, b[f] && delete b[f], b.exportSVGElements[c] = a.destroy());
        }), a.length = 0);e && (h(e, function (a, c) {
          clearTimeout(a.hideTimer);I(a, "mouseleave");b.exportDivElements[c] = a.onmouseout = a.onmouseover = a.ontouchstart = a.onclick = null;C(a);
        }), e.length = 0);c && (h(c, function (a) {
          a();
        }), c.length = 0);
      } });K.menu = function (a, b, e, c) {
      return ["M", a, b + 2.5, "L", a + e, b + 2.5, "M", a, b + c / 2 + .5, "L", a + e, b + c / 2 + .5, "M", a, b + c - 1.5, "L", a + e, b + c - 1.5];
    };B.prototype.renderExporting = function () {
      var a = this,
          b = a.options.exporting,
          e = b.buttons,
          c = a.isDirtyExporting || !a.exportSVGElements;a.buttonOffset = 0;a.isDirtyExporting && a.destroyExport();
      c && !1 !== b.enabled && (a.exportEvents = [], G(e, function (b) {
        a.addButton(b);
      }), a.isDirtyExporting = !1);x(a, "destroy", a.destroyExport);
    };B.prototype.callbacks.push(function (a) {
      a.renderExporting();x(a, "redraw", a.renderExporting);h(["exporting", "navigation"], function (b) {
        a[b] = { update: function update(e, c) {
            a.isDirtyExporting = !0;n(!0, a.options[b], e);D(c, !0) && a.redraw();
          } };
      });
    });
  })(k);
});
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/module.js */ 1)(module)))

/***/ }),

/***/ 473:
/*!**************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/highcharts/modules/boost.js ***!
  \**************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(module) {

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/*
 Highcharts JS v5.0.14 (2017-07-28)
 Boost module

 (c) 2010-2017 Highsoft AS
 Author: Torstein Honsi

 License: www.highcharts.com/license
*/
(function (r) {
  "object" === ( false ? "undefined" : _typeof(module)) && module.exports ? module.exports = r : r(Highcharts);
})(function (r) {
  (function (h) {
    function r() {
      var a = Array.prototype.slice.call(arguments),
          c = -Number.MAX_VALUE;B(a, function (a) {
        if ("undefined" !== typeof a && "undefined" !== typeof a.length && 0 < a.length) return c = a.length, !0;
      });return c;
    }function z(a) {
      var c = 0,
          d;if (1 < a.series.length) for (var f = 0; f < a.series.length; f++) {
        d = a.series[f], r(d.processedXData, d.options.data, d.points) >= (d.options.boostThreshold || Number.MAX_VALUE) && c++;
      }return 5 < c || a.series.length >= E(a.options.boost && a.options.boost.seriesThreshold, 50);
    }function A(a) {
      return z(a.chart) || r(a.processedXData, a.options.data, a.points) >= (a.options.boostThreshold || Number.MAX_VALUE);
    }function da(a) {
      function c(b, c) {
        c = a.createShader("vertex" === c ? a.VERTEX_SHADER : a.FRAGMENT_SHADER);a.shaderSource(c, b);a.compileShader(c);return a.getShaderParameter(c, a.COMPILE_STATUS) ? c : !1;
      }function d() {
        function d(b) {
          return a.getUniformLocation(l, b);
        }var e = c("#version 100\nprecision highp float;\nattribute vec4 aVertexPosition;\nattribute vec4 aColor;\nvarying highp vec2 position;\nvarying highp vec4 vColor;\nuniform mat4 uPMatrix;\nuniform float pSize;\nuniform float translatedThreshold;\nuniform bool hasThreshold;\nuniform bool skipTranslation;\nuniform float xAxisTrans;\nuniform float xAxisMin;\nuniform float xAxisMinPad;\nuniform float xAxisPointRange;\nuniform float xAxisLen;\nuniform bool  xAxisPostTranslate;\nuniform float xAxisOrdinalSlope;\nuniform float xAxisOrdinalOffset;\nuniform float xAxisPos;\nuniform bool  xAxisCVSCoord;\nuniform float yAxisTrans;\nuniform float yAxisMin;\nuniform float yAxisMinPad;\nuniform float yAxisPointRange;\nuniform float yAxisLen;\nuniform bool  yAxisPostTranslate;\nuniform float yAxisOrdinalSlope;\nuniform float yAxisOrdinalOffset;\nuniform float yAxisPos;\nuniform bool  yAxisCVSCoord;\nuniform bool  isBubble;\nuniform bool  bubbleSizeByArea;\nuniform float bubbleZMin;\nuniform float bubbleZMax;\nuniform float bubbleZThreshold;\nuniform float bubbleMinSize;\nuniform float bubbleMaxSize;\nuniform bool  bubbleSizeAbs;\nuniform bool  isInverted;\nfloat bubbleRadius(){\nfloat value \x3d aVertexPosition.w;\nfloat zMax \x3d bubbleZMax;\nfloat zMin \x3d bubbleZMin;\nfloat radius \x3d 0.0;\nfloat pos \x3d 0.0;\nfloat zRange \x3d zMax - zMin;\nif (bubbleSizeAbs){\nvalue \x3d value - bubbleZThreshold;\nzMax \x3d max(zMax - bubbleZThreshold, zMin - bubbleZThreshold);\nzMin \x3d 0.0;\n}\nif (value \x3c zMin){\nradius \x3d bubbleZMin / 2.0 - 1.0;\n} else {\npos \x3d zRange \x3e 0.0 ? (value - zMin) / zRange : 0.5;\nif (bubbleSizeByArea \x26\x26 pos \x3e 0.0){\npos \x3d sqrt(pos);\n}\nradius \x3d ceil(bubbleMinSize + pos * (bubbleMaxSize - bubbleMinSize)) / 2.0;\n}\nreturn radius * 2.0;\n}\nfloat translate(float val,\nfloat pointPlacement,\nfloat localA,\nfloat localMin,\nfloat minPixelPadding,\nfloat pointRange,\nfloat len,\nbool  cvsCoord\n){\nfloat sign \x3d 1.0;\nfloat cvsOffset \x3d 0.0;\nif (cvsCoord) {\nsign *\x3d -1.0;\ncvsOffset \x3d len;\n}\nreturn sign * (val - localMin) * localA + cvsOffset + \n(sign * minPixelPadding);\n}\nfloat xToPixels(float value){\nif (skipTranslation){\nreturn value;// + xAxisPos;\n}\nreturn translate(value, 0.0, xAxisTrans, xAxisMin, xAxisMinPad, xAxisPointRange, xAxisLen, xAxisCVSCoord);// + xAxisPos;\n}\nfloat yToPixels(float value, float checkTreshold){\nfloat v;\nif (skipTranslation){\nv \x3d value;// + yAxisPos;\n} else {\nv \x3d translate(value, 0.0, yAxisTrans, yAxisMin, yAxisMinPad, yAxisPointRange, yAxisLen, yAxisCVSCoord);// + yAxisPos;\n}\nif (checkTreshold \x3e 0.0 \x26\x26 hasThreshold) {\nv \x3d min(v, translatedThreshold);\n}\nreturn v;\n}\nvoid main(void) {\nif (isBubble){\ngl_PointSize \x3d bubbleRadius();\n} else {\ngl_PointSize \x3d pSize;\n}\nvColor \x3d aColor;\nif (isInverted) {\ngl_Position \x3d uPMatrix * vec4(xToPixels(aVertexPosition.y) + yAxisPos, yToPixels(aVertexPosition.x, aVertexPosition.z) + xAxisPos, 0.0, 1.0);\n} else {\ngl_Position \x3d uPMatrix * vec4(xToPixels(aVertexPosition.x) + xAxisPos, yToPixels(aVertexPosition.y, aVertexPosition.z) + yAxisPos, 0.0, 1.0);\n}\n}", "vertex"),
            f = c("precision highp float;\nuniform vec4 fillColor;\nvarying highp vec2 position;\nvarying highp vec4 vColor;\nuniform sampler2D uSampler;\nuniform bool isCircle;\nuniform bool hasColor;\nvoid main(void) {\nvec4 col \x3d fillColor;\nif (hasColor) {\ncol \x3d vColor;\n}\nif (isCircle) {\ngl_FragColor \x3d col * texture2D(uSampler, gl_PointCoord.st);\n} else {\ngl_FragColor \x3d col;\n}\n}", "fragment");if (!e || !f) return l = !1;l = a.createProgram();a.attachShader(l, e);a.attachShader(l, f);a.linkProgram(l);
        a.useProgram(l);a.bindAttribLocation(l, 0, "aVertexPosition");h = d("uPMatrix");g = d("pSize");n = d("fillColor");Q = d("isBubble");k = d("bubbleSizeAbs");v = d("bubbleSizeByArea");C = d("uSampler");b = d("skipTranslation");p = d("isCircle");H = d("isInverted");return !0;
      }function f(b, c) {
        b = e[b] = e[b] || a.getUniformLocation(l, b);a.uniform1f(b, c);
      }var e = {},
          l,
          h,
          g,
          n,
          Q,
          k,
          v,
          b,
          p,
          H,
          C;a && d();return { psUniform: function psUniform() {
          return g;
        }, pUniform: function pUniform() {
          return h;
        }, fillColorUniform: function fillColorUniform() {
          return n;
        }, setBubbleUniforms: function setBubbleUniforms(b, c, d) {
          var e = b.options,
              l = Number.MAX_VALUE,
              h = -Number.MAX_VALUE;"bubble" === b.type && (l = E(e.zMin, Math.min(l, Math.max(c, !1 === e.displayNegative ? e.zThreshold : -Number.MAX_VALUE))), h = E(e.zMax, Math.max(h, d)), a.uniform1i(Q, 1), a.uniform1i(p, 1), a.uniform1i(v, "width" !== b.options.sizeBy), a.uniform1i(k, b.options.sizeByAbsoluteValue), f("bubbleZMin", l), f("bubbleZMax", h), f("bubbleZThreshold", b.options.zThreshold), f("bubbleMinSize", b.minPxSize), f("bubbleMaxSize", b.maxPxSize));
        }, bind: function bind() {
          a.useProgram(l);
        }, program: function program() {
          return l;
        },
        create: d, setUniform: f, setPMatrix: function setPMatrix(b) {
          a.uniformMatrix4fv(h, !1, b);
        }, setColor: function setColor(b) {
          a.uniform4f(n, b[0] / 255, b[1] / 255, b[2] / 255, b[3]);
        }, setPointSize: function setPointSize(b) {
          a.uniform1f(g, b);
        }, setSkipTranslation: function setSkipTranslation(c) {
          a.uniform1i(b, !0 === c ? 1 : 0);
        }, setTexture: function setTexture() {
          a.uniform1i(C, 0);
        }, setDrawAsCircle: function setDrawAsCircle(b) {
          a.uniform1i(p, b ? 1 : 0);
        }, reset: function reset() {
          a.uniform1i(Q, 0);a.uniform1i(p, 0);
        }, setInverted: function setInverted(b) {
          a.uniform1i(H, b);
        }, destroy: function destroy() {
          a && l && a.deleteProgram(l);
        } };
    }function U(a, c, d) {
      var f = !1,
          e = !1,
          l = d || 2,
          h = !1,
          g = 0,
          n;return { destroy: function destroy() {
          f && a.deleteBuffer(f);
        }, bind: function bind() {
          if (!f) return !1;a.vertexAttribPointer(e, l, a.FLOAT, !1, 0, 0);
        }, data: n, build: function build(d, k, g) {
          n = d || [];if (!(n && 0 !== n.length || h)) return f = !1;l = g || l;f && a.deleteBuffer(f);f = a.createBuffer();a.bindBuffer(a.ARRAY_BUFFER, f);a.bufferData(a.ARRAY_BUFFER, h || new Float32Array(n), a.STATIC_DRAW);e = a.getAttribLocation(c.program(), k);a.enableVertexAttribArray(e);return !0;
        }, render: function render(c, d, e) {
          var b = h ? h.length : n.length;if (!f || !b) return !1;
          if (!c || c > b || 0 > c) c = 0;if (!d || d > b) d = b;a.drawArrays(a[(e || "points").toUpperCase()], c / l, (d - c) / l);return !0;
        }, allocate: function allocate(a) {
          g = -1;h = new Float32Array(4 * a);
        }, push: function push(a, c, d, b) {
          h && (h[++g] = a, h[++g] = c, h[++g] = d, h[++g] = b);
        } };
    }function ea(a) {
      function c(a) {
        var b, c;return A(a) ? (b = !!a.options.stacking, c = a.xData || a.options.xData || a.processedXData, b = (b ? a.data : c || a.options.data).length, "treemap" === a.type ? b *= 12 : "heatmap" === a.type ? b *= 6 : V[a.type] && (b *= 2), b) : 0;
      }function d() {
        b.clear(b.COLOR_BUFFER_BIT | b.DEPTH_BUFFER_BIT);
      }
      function f(a, b) {
        function c(a) {
          a && (b.colorData.push(a[0]), b.colorData.push(a[1]), b.colorData.push(a[2]), b.colorData.push(a[3]));
        }function d(a, b, d, e, f) {
          c(f);q.usePreallocated ? v.push(a, b, d ? 1 : 0, e || 1) : (C.push(a), C.push(b), C.push(d ? 1 : 0), C.push(e || 1));
        }function e(a, b, e, f, w) {
          c(w);d(a + e, b);c(w);d(a, b);c(w);d(a, b + f);c(w);d(a, b + f);c(w);d(a + e, b + f);c(w);d(a + e, b);
        }function f(a) {
          q.useGPUTranslations || (b.skipTranslation = !0, a.x = D.toPixels(a.x, !0), a.y = x.toPixels(a.y, !0));d(a.x, a.y, 0, 2);
        }var w = a.pointArrayMap && "low,high" === a.pointArrayMap.join(","),
            k = a.chart,
            m = a.options,
            l = !!m.stacking,
            g = m.data,
            n = a.xAxis.getExtremes(),
            p = n.min,
            t = n.max,
            n = a.yAxis.getExtremes(),
            u = n.min,
            z = n.max,
            n = a.xData || m.xData || a.processedXData,
            H = a.yData || m.yData || a.processedYData,
            r = a.zData || m.zData || a.processedZData,
            x = a.yAxis,
            D = a.xAxis,
            A = !n || 0 === n.length,
            y = a.points || !1,
            F = !1,
            G,
            L,
            M,
            J = l ? a.data : n || g,
            I = { x: Number.MIN_VALUE, y: 0 },
            E = { x: Number.MIN_VALUE, y: 0 };m.boostData && 0 < m.boostData.length || (a.closestPointRangePx = Number.MAX_VALUE, y && 0 < y.length ? (b.skipTranslation = !0, b.drawMode = "triangles", y[0].node && y[0].node.levelDynamic && y.sort(function (a, b) {
          if (a.node) {
            if (a.node.levelDynamic > b.node.levelDynamic) return 1;if (a.node.levelDynamic < b.node.levelDynamic) return -1;
          }return 0;
        }), B(y, function (b) {
          var c = b.plotY,
              d;"undefined" === typeof c || isNaN(c) || null === b.y || (c = b.shapeArgs, d = b.series.pointAttribs(b), b = d["stroke-width"] || 0, L = h.color(d.fill).rgba, L[0] /= 255, L[1] /= 255, L[2] /= 255, "treemap" === a.type && (b = b || 1, M = h.color(d.stroke).rgba, M[0] /= 255, M[1] /= 255, M[2] /= 255, e(c.x, c.y, c.width, c.height, M), b /= 2), "heatmap" === a.type && k.inverted && (c.x = D.len - c.x, c.y = x.len - c.y, c.width = -c.width, c.height = -c.height), e(c.x + b, c.y + b, c.width - 2 * b, c.height - 2 * b, L));
        })) : (B(J, function (c, e) {
          var f,
              m,
              h,
              g = !1,
              N = !1,
              W = !1,
              n = !1,
              fa = V[a.type],
              v = !1,
              y = !0;if ("undefined" === typeof k.index) return !1;A ? (f = c[0], m = c[1], J[e + 1] && (N = J[e + 1][0]), J[e - 1] && (g = J[e - 1][0]), 3 <= c.length && (h = c[2], c[2] > b.zMax && (b.zMax = c[2]), c[2] < b.zMin && (b.zMin = c[2]))) : (f = c, m = H[e], J[e + 1] && (N = J[e + 1]), J[e - 1] && (g = J[e - 1]), r && r.length && (h = r[e], r[e] > b.zMax && (b.zMax = r[e]), r[e] < b.zMin && (b.zMin = r[e])));N && N >= p && N <= t && (W = !0);g && g >= p && g <= t && (n = !0);w ? (A && (m = c.slice(1, 3)), m = m[1]) : l && (f = c.x, m = c.stackY);a.requireSorting || (y = m >= u && m <= z);f > t && E.x < t && (E.x = f, E.y = m);f < p && I.x < p && (I.x = f, I.y = m);if (0 === m || m && y) if (f >= p && f <= t && (v = !0), v || W || n) q.useGPUTranslations || (b.skipTranslation = !0, f = D.toPixels(f, !0), m = x.toPixels(m, !0)), fa && (G = 0, 0 > m && (G = m, m = 0), q.useGPUTranslations || (G = x.toPixels(G, !0)), d(f, G, 0, 0, !1)), b.hasMarkers && !1 !== F && (a.closestPointRangePx = Math.min(a.closestPointRangePx, Math.abs(f - F))), d(f, m, 0, "bubble" === a.type ? h || 1 : 2, !1), F = f;
        }), F || (f(I), f(E))));
      }function e() {
        t = [];F.data = C = [];x = [];
      }function l(a) {
        k && (k.setUniform("xAxisTrans", a.transA), k.setUniform("xAxisMin", a.min), k.setUniform("xAxisMinPad", a.minPixelPadding), k.setUniform("xAxisPointRange", a.pointRange), k.setUniform("xAxisLen", a.len), k.setUniform("xAxisPos", a.pos), k.setUniform("xAxisCVSCoord", !a.horiz));
      }function g(a) {
        k && (k.setUniform("yAxisTrans", a.transA), k.setUniform("yAxisMin", a.min), k.setUniform("yAxisMinPad", a.minPixelPadding), k.setUniform("yAxisPointRange", a.pointRange), k.setUniform("yAxisLen", a.len), k.setUniform("yAxisPos", a.pos), k.setUniform("yAxisCVSCoord", !a.horiz));
      }function u(a, b) {
        k.setUniform("hasThreshold", a);k.setUniform("translatedThreshold", b);
      }function n(c) {
        if (c) p = c.chartWidth || 800, H = c.chartHeight || 400;else return !1;if (!b || !p || !H) return !1;q.timeRendering && console.time("gl rendering");k.bind();b.viewport(0, 0, p, H);k.setPMatrix([2 / p, 0, 0, 0, 0, -(2 / H), 0, 0, 0, 0, -2, 0, -1, 1, -1, 1]);1 < q.lineWidth && !h.isMS && b.lineWidth(q.lineWidth);v.build(F.data, "aVertexPosition", 4);v.bind();r && (b.bindTexture(b.TEXTURE_2D, D), k.setTexture(D));k.setInverted(c.options.chart ? c.options.chart.inverted : !1);B(t, function (a, c) {
          var d = a.series.options,
              e = d.threshold,
              f = K(e),
              e = a.series.yAxis.getThreshold(e),
              m = E(d.marker ? d.marker.enabled : null, a.series.xAxis.isRadial ? !0 : null, a.series.closestPointRangePx > 2 * ((d.marker ? d.marker.radius : 10) || 10)),
              w = a.series.fillOpacity ? new X(a.series.color).setOpacity(E(d.fillOpacity, .85)).get() : a.series.color;
          v.bind();d.colorByPoint && (w = a.series.chart.options.colors[c]);w = h.color(w).rgba;q.useAlpha || (w[3] = 1);"add" === d.boostBlending ? (b.blendFunc(b.SRC_ALPHA, b.ONE), b.blendEquation(b.FUNC_ADD)) : "mult" === d.boostBlending ? b.blendFunc(b.DST_COLOR, b.ZERO) : "darken" === d.boostBlending ? (b.blendFunc(b.ONE, b.ONE), b.blendEquation(b.FUNC_MIN)) : b.blendFuncSeparate(b.SRC_ALPHA, b.ONE_MINUS_SRC_ALPHA, b.ONE, b.ONE_MINUS_SRC_ALPHA);k.reset();0 < a.colorData.length && (k.setUniform("hasColor", 1), c = U(b, k), c.build(a.colorData, "aColor", 4), c.bind());k.setColor(w);l(a.series.xAxis);g(a.series.yAxis);u(f, e);"points" === a.drawMode && (d.marker && d.marker.radius ? k.setPointSize(2 * d.marker.radius) : k.setPointSize(1));k.setSkipTranslation(a.skipTranslation);"bubble" === a.series.type && k.setBubbleUniforms(a.series, a.zMin, a.zMax);k.setDrawAsCircle(ga[a.series.type] && r || !1);v.render(a.from, a.to, a.drawMode);a.hasMarkers && m && (d.marker && d.marker.radius ? k.setPointSize(2 * d.marker.radius) : k.setPointSize(10), k.setDrawAsCircle(!0), v.render(a.from, a.to, "POINTS"));
        });
        v.destroy();q.timeRendering && console.timeEnd("gl rendering");e();a && a();
      }function z(a) {
        d();if (a.renderer.forExport) return n(a);y ? n(a) : setTimeout(function () {
          z(a);
        }, 1);
      }var k = !1,
          v = !1,
          b = !1,
          p = 0,
          H = 0,
          C = !1,
          x = !1,
          r = !1,
          F = {},
          y = !1,
          t = [],
          G = R.createElement("canvas"),
          I = G.getContext("2d"),
          D,
          V = { column: !0, area: !0 },
          ga = { scatter: !0, bubble: !0 },
          q = { pointSize: 1, lineWidth: 3, fillColor: "#AA00AA", useAlpha: !0, usePreallocated: !1, useGPUTranslations: !1, timeRendering: !1, timeSeriesProcessing: !1, timeSetup: !1 };return F = { allocateBufferForSingleSeries: function allocateBufferForSingleSeries(a) {
          var b = 0;q.usePreallocated && (A(a) && (b = c(a)), v.allocate(b));
        }, pushSeries: function pushSeries(a) {
          0 < t.length && (t[t.length - 1].to = C.length, t[t.length - 1].hasMarkers && (t[t.length - 1].markerTo = x.length));q.timeSeriesProcessing && console.time("building " + a.type + " series");t.push({ from: C.length, markerFrom: x.length, colorData: [], series: a, zMin: Number.MAX_VALUE, zMax: -Number.MAX_VALUE, hasMarkers: a.options.marker ? !1 !== a.options.marker.enabled : !1, showMarksers: !0, drawMode: { area: "lines", arearange: "lines", areaspline: "line_strip", column: "lines",
              line: "line_strip", scatter: "points", heatmap: "triangles", treemap: "triangles", bubble: "points" }[a.type] || "line_strip" });f(a, t[t.length - 1]);q.timeSeriesProcessing && console.timeEnd("building " + a.type + " series");
        }, setSize: function setSize(a, b) {
          if (p !== a || b !== b) p = a, H = b, k.bind(), k.setPMatrix([2 / p, 0, 0, 0, 0, -(2 / H), 0, 0, 0, 0, -2, 0, -1, 1, -1, 1]);
        }, inited: function inited() {
          return y;
        }, setThreshold: u, init: function init(a, c) {
          var d = 0,
              f = ["webgl", "experimental-webgl", "moz-webgl", "webkit-3d"];y = !1;if (!a) return !1;for (q.timeSetup && console.time("gl setup"); d < f.length && !(b = a.getContext(f[d])); d++) {}if (b) c || e();else return !1;b.enable(b.BLEND);b.blendFunc(b.SRC_ALPHA, b.ONE_MINUS_SRC_ALPHA);b.disable(b.DEPTH_TEST);b.depthMask(b.FALSE);k = da(b);v = U(b, k);r = !1;D = b.createTexture();G.width = 512;G.height = 512;I.fillStyle = "#FFF";I.beginPath();I.arc(256, 256, 256, 0, 2 * Math.PI);I.fill();try {
            b.bindTexture(b.TEXTURE_2D, D), b.texImage2D(b.TEXTURE_2D, 0, b.RGBA, b.RGBA, b.UNSIGNED_BYTE, G), b.texParameteri(b.TEXTURE_2D, b.TEXTURE_WRAP_S, b.CLAMP_TO_EDGE), b.texParameteri(b.TEXTURE_2D, b.TEXTURE_WRAP_T, b.CLAMP_TO_EDGE), b.texParameteri(b.TEXTURE_2D, b.TEXTURE_MAG_FILTER, b.LINEAR), b.texParameteri(b.TEXTURE_2D, b.TEXTURE_MIN_FILTER, b.LINEAR_MIPMAP_LINEAR), b.generateMipmap(b.TEXTURE_2D), b.bindTexture(b.TEXTURE_2D, null), r = !0;
          } catch (ma) {}y = !0;q.timeSetup && console.timeEnd("gl setup");return !0;
        }, render: z, settings: q, valid: function valid() {
          return !1 !== b;
        }, clear: d, flush: e, setXAxis: l, setYAxis: g, data: C, gl: function gl() {
          return b;
        }, allocateBuffer: function allocateBuffer(a) {
          var b = 0;q.usePreallocated && (B(a.series, function (a) {
            A(a) && (b += c(a));
          }), v.allocate(b));
        }, destroy: function destroy() {
          v.destroy();k.destroy();b && (D && b.deleteTexture(D), b.canvas.width = 1, b.canvas.height = 1);
        }, setOptions: function setOptions(a) {
          ha(!0, q, a);
        } };
    }function Y(a, c) {
      var d = a.chartWidth,
          f = a.chartHeight,
          e = a,
          l = a.seriesGroup || c.group,
          e = z(a) ? a : c;e.image || (e.canvas = R.createElement("canvas"), e.image = a.renderer.image("", 0, 0, d, f).add(l), e.boostClipRect = a.renderer.clipRect(a.plotLeft, a.plotTop, a.plotWidth, a.chartHeight), e.image.clip(e.boostClipRect), e instanceof h.Chart && (e.markerGroup = e.renderer.g().add(l), e.markerGroup.translate(c.xAxis.pos, c.yAxis.pos)));e.canvas.width = d;e.canvas.height = f;e.image.attr({ x: 0, y: 0, width: d, height: f, style: "pointer-events: none" });e.boostClipRect.attr({ x: a.plotLeft, y: a.plotTop, width: a.plotWidth, height: a.chartHeight });e.ogl || (e.ogl = ea(function () {
        e.image.attr({ href: e.canvas.toDataURL("image/png") });e.ogl.destroy();e.ogl = !1;
      }), e.ogl.init(e.canvas), e.ogl.setOptions(a.options.boost || {}), e instanceof h.Chart && e.ogl.allocateBuffer(a));e.ogl.setSize(d, f);return e.ogl;
    }
    function Z(a, c, d) {
      a && c.image && c.canvas && !z(d || c.chart) && a.render(d || c.chart);
    }function aa(a, c) {
      a && c.image && c.canvas && !z(c.chart) && a.allocateBufferForSingleSeries(c);
    }function O(a, c, d, f, e, h) {
      e = e || 0;f = f || 5E4;for (var g = e + f, l = !0; l && e < g && e < a.length;) {
        l = c(a[e], e), ++e;
      }l && (e < a.length ? h ? O(a, c, d, f, e, h) : P.requestAnimationFrame ? P.requestAnimationFrame(function () {
        O(a, c, d, f, e);
      }) : setTimeout(function () {
        O(a, c, d, f, e);
      }) : d && d());
    }function ia(a) {
      if (!A(this)) return a.call(this);if (a = Y(this.chart, this)) aa(a, this), a.pushSeries(this);
      Z(a, this);
    }var P = h.win,
        R = P.document,
        ja = function ja() {},
        X = h.Color,
        x = h.Series,
        g = h.seriesTypes,
        B = h.each,
        ba = h.extend,
        ca = h.addEvent,
        ka = h.fireEvent,
        la = h.grep,
        K = h.isNumber,
        ha = h.merge,
        E = h.pick,
        u = h.wrap,
        S = h.getOptions().plotOptions,
        T;X.prototype.names = { aliceblue: "#f0f8ff", antiquewhite: "#faebd7", aqua: "#00ffff", aquamarine: "#7fffd4", azure: "#f0ffff", beige: "#f5f5dc", bisque: "#ffe4c4", black: "#000000", blanchedalmond: "#ffebcd", blue: "#0000ff", blueviolet: "#8a2be2", brown: "#a52a2a", burlywood: "#deb887", cadetblue: "#5f9ea0",
      chartreuse: "#7fff00", chocolate: "#d2691e", coral: "#ff7f50", cornflowerblue: "#6495ed", cornsilk: "#fff8dc", crimson: "#dc143c", cyan: "#00ffff", darkblue: "#00008b", darkcyan: "#008b8b", darkgoldenrod: "#b8860b", darkgray: "#a9a9a9", darkgreen: "#006400", darkkhaki: "#bdb76b", darkmagenta: "#8b008b", darkolivegreen: "#556b2f", darkorange: "#ff8c00", darkorchid: "#9932cc", darkred: "#8b0000", darksalmon: "#e9967a", darkseagreen: "#8fbc8f", darkslateblue: "#483d8b", darkslategray: "#2f4f4f", darkturquoise: "#00ced1", darkviolet: "#9400d3", deeppink: "#ff1493",
      deepskyblue: "#00bfff", dimgray: "#696969", dodgerblue: "#1e90ff", feldspar: "#d19275", firebrick: "#b22222", floralwhite: "#fffaf0", forestgreen: "#228b22", fuchsia: "#ff00ff", gainsboro: "#dcdcdc", ghostwhite: "#f8f8ff", gold: "#ffd700", goldenrod: "#daa520", gray: "#808080", green: "#008000", greenyellow: "#adff2f", honeydew: "#f0fff0", hotpink: "#ff69b4", indianred: "#cd5c5c", indigo: "#4b0082", ivory: "#fffff0", khaki: "#f0e68c", lavender: "#e6e6fa", lavenderblush: "#fff0f5", lawngreen: "#7cfc00", lemonchiffon: "#fffacd", lightblue: "#add8e6",
      lightcoral: "#f08080", lightcyan: "#e0ffff", lightgoldenrodyellow: "#fafad2", lightgrey: "#d3d3d3", lightgreen: "#90ee90", lightpink: "#ffb6c1", lightsalmon: "#ffa07a", lightseagreen: "#20b2aa", lightskyblue: "#87cefa", lightslateblue: "#8470ff", lightslategray: "#778899", lightsteelblue: "#b0c4de", lightyellow: "#ffffe0", lime: "#00ff00", limegreen: "#32cd32", linen: "#faf0e6", magenta: "#ff00ff", maroon: "#800000", mediumaquamarine: "#66cdaa", mediumblue: "#0000cd", mediumorchid: "#ba55d3", mediumpurple: "#9370d8", mediumseagreen: "#3cb371",
      mediumslateblue: "#7b68ee", mediumspringgreen: "#00fa9a", mediumturquoise: "#48d1cc", mediumvioletred: "#c71585", midnightblue: "#191970", mintcream: "#f5fffa", mistyrose: "#ffe4e1", moccasin: "#ffe4b5", navajowhite: "#ffdead", navy: "#000080", oldlace: "#fdf5e6", olive: "#808000", olivedrab: "#6b8e23", orange: "#ffa500", orangered: "#ff4500", orchid: "#da70d6", palegoldenrod: "#eee8aa", palegreen: "#98fb98", paleturquoise: "#afeeee", palevioletred: "#d87093", papayawhip: "#ffefd5", peachpuff: "#ffdab9", peru: "#cd853f", pink: "#ffc0cb", plum: "#dda0dd",
      powderblue: "#b0e0e6", purple: "#800080", red: "#ff0000", rosybrown: "#bc8f8f", royalblue: "#4169e1", saddlebrown: "#8b4513", salmon: "#fa8072", sandybrown: "#f4a460", seagreen: "#2e8b57", seashell: "#fff5ee", sienna: "#a0522d", silver: "#c0c0c0", skyblue: "#87ceeb", slateblue: "#6a5acd", slategray: "#708090", snow: "#fffafa", springgreen: "#00ff7f", steelblue: "#4682b4", tan: "#d2b48c", teal: "#008080", thistle: "#d8bfd8", tomato: "#ff6347", turquoise: "#40e0d0", violet: "#ee82ee", violetred: "#d02090", wheat: "#f5deb3", white: "#ffffff", whitesmoke: "#f5f5f5",
      yellow: "#ffff00", yellowgreen: "#9acd32" };x.prototype.getPoint = function (a) {
      var c = a,
          d = this.xData || this.options.xData || this.processedXData || !1;!a || a instanceof this.pointClass || (c = new this.pointClass().init(this, this.options.data[a.i], d ? d[a.i] : void 0), c.category = c.x, c.dist = a.dist, c.distX = a.distX, c.plotX = a.plotX, c.plotY = a.plotY, c.index = a.i);return c;
    };u(x.prototype, "searchPoint", function (a) {
      return this.getPoint(a.apply(this, [].slice.call(arguments, 1)));
    });u(x.prototype, "destroy", function (a) {
      var c = this,
          d = c.chart;d.markerGroup === c.markerGroup && (c.markerGroup = null);d.hoverPoints && (d.hoverPoints = la(d.hoverPoints, function (a) {
        return a.series === c;
      }));d.hoverPoint && d.hoverPoint.series === c && (d.hoverPoint = null);a.call(this);
    });u(x.prototype, "getExtremes", function (a) {
      if (!A(this) || !this.hasExtremes || !this.hasExtremes()) return a.apply(this, Array.prototype.slice.call(arguments, 1));
    });B("area arearange column line scatter heatmap bubble treemap heatmap".split(" "), function (a) {
      S[a] && (S[a].boostThreshold = 5E3, S[a].boostData = []);
    });B(["translate", "generatePoints", "drawTracker", "drawPoints", "render"], function (a) {
      function c(c) {
        var d = this.options.stacking && ("translate" === a || "generatePoints" === a);if (!A(this) || d || "heatmap" === this.type || "treemap" === this.type) "render" === a && this.image && !z(this.chart) && (this.image.attr({ href: "" }), this.animate = null), c.call(this);else if (this[a + "Canvas"]) this[a + "Canvas"]();
      }u(x.prototype, a, c);"translate" === a && (g.column && u(g.column.prototype, a, c), g.arearange && u(g.arearange.prototype, a, c), g.treemap && u(g.treemap.prototype, a, c), g.heatmap && u(g.heatmap.prototype, a, c));
    });(function () {
      var a = 0,
          c,
          d = ["webgl", "experimental-webgl", "moz-webgl", "webkit-3d"],
          f = !1;if ("undefined" !== typeof P.WebGLRenderingContext) for (c = R.createElement("canvas"); a < d.length; a++) {
        try {
          if (f = c.getContext(d[a]), "undefined" !== typeof f && null !== f) return !0;
        } catch (e) {}
      }return !1;
    })() ? (u(x.prototype, "processData", function (a) {
      A(this) && "heatmap" !== this.type && "treemap" !== this.type || a.apply(this, Array.prototype.slice.call(arguments, 1));this.hasExtremes && this.hasExtremes(!0) || a.apply(this, Array.prototype.slice.call(arguments, 1));
    }), h.extend(x.prototype, { pointRange: 0, directTouch: !1, allowDG: !1, hasExtremes: function hasExtremes(a) {
        var c = this.options,
            d = this.xAxis && this.xAxis.options,
            f = this.yAxis && this.yAxis.options;return c.data.length > (c.boostThreshold || Number.MAX_VALUE) && K(f.min) && K(f.max) && (!a || K(d.min) && K(d.max));
      }, destroyGraphics: function destroyGraphics() {
        var a = this,
            c = this.points,
            d,
            f;if (c) for (f = 0; f < c.length; f += 1) {
          (d = c[f]) && d.graphic && (d.graphic = d.graphic.destroy());
        }B(["graph", "area", "tracker"], function (c) {
          a[c] && (a[c] = a[c].destroy());
        });
      }, renderCanvas: function renderCanvas() {
        var a = this,
            c = a.options || {},
            d = !1,
            f = a.chart,
            e = this.xAxis,
            h = this.yAxis,
            g = c.xData || a.processedXData,
            r = c.yData || a.processedYData,
            n = c.data,
            d = e.getExtremes(),
            x = d.min,
            k = d.max,
            d = h.getExtremes(),
            v = d.min,
            b = d.max,
            p = {},
            u,
            C = !!a.sampling,
            A,
            E = !1 !== c.enableMouseTracking,
            F = h.getThreshold(c.threshold),
            y = a.pointArrayMap && "low,high" === a.pointArrayMap.join(","),
            t = !!c.stacking,
            G = a.cropStart || 0,
            I = a.requireSorting,
            D = !g,
            B,
            K,
            q,
            w,
            m = function m(a, b, c) {
          T = a + "," + b;E && !p[T] && (p[T] = !0, f.inverted && (a = e.len - a, b = h.len - b), A.push({ clientX: a, plotX: a, plotY: b, i: G + c }));
        },
            d = Y(f, a);this.visible ? ((this.points || this.graph) && this.destroyGraphics(), z(f) ? this.markerGroup = f.markerGroup : this.markerGroup = a.plotGroup("markerGroup", "markers", !0, 1, f.seriesGroup), A = this.points = [], a.buildKDTree = ja, d && (aa(d, this), d.pushSeries(a), Z(d, this, f)), O(t ? a.data : g || n, function (a, c) {
          var d,
              g,
              l,
              n = "undefined" === typeof f.index,
              p = !0;if (!n && (D ? (d = a[0], g = a[1]) : (d = a, g = r[c]), y ? (D && (g = a.slice(1, 3)), l = g[0], g = g[1]) : t && (d = a.x, g = a.stackY, l = g - a.y), I || (p = g >= v && g <= b), null !== g && d >= x && d <= k && p)) if (a = Math.ceil(e.toPixels(d, !0)), C) {
            if (void 0 === q || a === u) {
              y || (l = g);if (void 0 === w || g > K) K = g, w = c;if (void 0 === q || l < B) B = l, q = c;
            }a !== u && (void 0 !== q && (g = h.toPixels(K, !0), F = h.toPixels(B, !0), m(a, g, w), F !== g && m(a, F, q)), q = w = void 0, u = a);
          } else g = Math.ceil(h.toPixels(g, !0)), m(a, g, c);return !n;
        }, function () {
          ka(a, "renderedCanvas");a.directTouch = !1;a.options.stickyTracking = !0;delete a.buildKDTree;a.buildKDTree();
        }, f.renderer.forExport ? Number.MAX_VALUE : void 0)) : !z(f) && d && (d.clear(), this.image.attr({ href: "" }));
      } }), B(["heatmap", "treemap"], function (a) {
      g[a] && (u(g[a].prototype, "drawPoints", ia), g[a].prototype.directTouch = !1);
    }), g.bubble && (delete g.bubble.prototype.buildKDTree, g.bubble.prototype.directTouch = !1, u(g.bubble.prototype, "markerAttribs", function (a) {
      return A(this) ? !1 : a.apply(this, [].slice.call(arguments, 1));
    })), g.scatter.prototype.fill = !0, ba(g.area.prototype, { fill: !0, fillOpacity: !0, sampling: !0 }), ba(g.column.prototype, { fill: !0, sampling: !0 }), u(x.prototype, "setVisible", function (a, c) {
      a.call(this, c, !1);!1 === this.visible && this.ogl && this.canvas && this.image ? (this.ogl.clear(), this.image.attr({ href: "" })) : this.chart.redraw();
    }), h.Chart.prototype.callbacks.push(function (a) {
      ca(a, "predraw", function () {
        !z(a) && a.didBoost && (a.didBoost = !1, a.image && a.image.attr({ href: "" }));a.canvas && a.ogl && z(a) && (a.didBoost = !0, a.ogl.allocateBuffer(a));a.markerGroup && a.xAxis && 0 < a.xAxis.length && a.yAxis && 0 < a.yAxis.length && a.markerGroup.translate(a.xAxis[0].pos, a.yAxis[0].pos);
      });
      ca(a, "render", function () {
        a.ogl && z(a) && a.ogl.render(a);
      });
    })) : "undefined" !== typeof h.initCanvasBoost ? h.initCanvasBoost() : h.error(26);
  })(r);
});
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../node_modules/webpack/buildin/module.js */ 1)(module)))

/***/ }),

/***/ 474:
/*!*******************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/deepmerge/dist/es.js ***!
  \*******************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var isMergeableObject = function isMergeableObject(value) {
	return isNonNullObject(value) && !isSpecial(value);
};

function isNonNullObject(value) {
	return !!value && (typeof value === 'undefined' ? 'undefined' : _typeof(value)) === 'object';
}

function isSpecial(value) {
	var stringValue = Object.prototype.toString.call(value);

	return stringValue === '[object RegExp]' || stringValue === '[object Date]' || isReactElement(value);
}

// see https://github.com/facebook/react/blob/b5ac963fb791d1298e7f396236383bc955f916c1/src/isomorphic/classic/element/ReactElement.js#L21-L25
var canUseSymbol = typeof Symbol === 'function' && Symbol.for;
var REACT_ELEMENT_TYPE = canUseSymbol ? Symbol.for('react.element') : 0xeac7;

function isReactElement(value) {
	return value.$$typeof === REACT_ELEMENT_TYPE;
}

function emptyTarget(val) {
	return Array.isArray(val) ? [] : {};
}

function cloneUnlessOtherwiseSpecified(value, optionsArgument) {
	var clone = !optionsArgument || optionsArgument.clone !== false;

	return clone && isMergeableObject(value) ? deepmerge(emptyTarget(value), value, optionsArgument) : value;
}

function defaultArrayMerge(target, source, optionsArgument) {
	return target.concat(source).map(function (element) {
		return cloneUnlessOtherwiseSpecified(element, optionsArgument);
	});
}

function mergeObject(target, source, optionsArgument) {
	var destination = {};
	if (isMergeableObject(target)) {
		Object.keys(target).forEach(function (key) {
			destination[key] = cloneUnlessOtherwiseSpecified(target[key], optionsArgument);
		});
	}
	Object.keys(source).forEach(function (key) {
		if (!isMergeableObject(source[key]) || !target[key]) {
			destination[key] = cloneUnlessOtherwiseSpecified(source[key], optionsArgument);
		} else {
			destination[key] = deepmerge(target[key], source[key], optionsArgument);
		}
	});
	return destination;
}

function deepmerge(target, source, optionsArgument) {
	var sourceIsArray = Array.isArray(source);
	var targetIsArray = Array.isArray(target);
	var options = optionsArgument || { arrayMerge: defaultArrayMerge };
	var sourceAndTargetTypesMatch = sourceIsArray === targetIsArray;

	if (!sourceAndTargetTypesMatch) {
		return cloneUnlessOtherwiseSpecified(source, optionsArgument);
	} else if (sourceIsArray) {
		var arrayMerge = options.arrayMerge || defaultArrayMerge;
		return arrayMerge(target, source, optionsArgument);
	} else {
		return mergeObject(target, source, optionsArgument);
	}
}

deepmerge.all = function deepmergeAll(array, optionsArgument) {
	if (!Array.isArray(array)) {
		throw new Error('first argument should be an array');
	}

	return array.reduce(function (prev, next) {
		return deepmerge(prev, next, optionsArgument);
	}, {});
};

var deepmerge_1 = deepmerge;

exports.default = deepmerge_1;

/***/ }),

/***/ 475:
/*!***********************************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-experiment-page-tsne-plot/lib/GeneExpressionTSnePlot.js ***!
  \***********************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
exports._colourizeExpressionLevel = exports.default = undefined;

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _color = __webpack_require__(/*! color */ 196);

var _color2 = _interopRequireDefault(_color);

var _PlotLoader = __webpack_require__(/*! ./plotloader/PlotLoader */ 199);

var _PlotLoader2 = _interopRequireDefault(_PlotLoader);

var _expressionAtlasAutocomplete = __webpack_require__(/*! expression-atlas-autocomplete */ 476);

var _expressionAtlasAutocomplete2 = _interopRequireDefault(_expressionAtlasAutocomplete);

__webpack_require__(/*! ./util/MathRound */ 483);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var MAX_WHITE = 90;

var _colourizeExpressionLevel = function _colourizeExpressionLevel(hue, highlightSeries) {
  return function (plotData) {
    return plotData.series.map(function (aSeries) {
      // I can’t think of a better way to reconcile series.name being a string and highlightSeries being an array of
      // numbers. For more flexibility we might think of having our series be identified by an arbitrary ID string
      if (!highlightSeries.length || highlightSeries.map(function (hs) {
        return String(hs);
      }).includes(aSeries.name)) {
        return {
          name: aSeries.name,
          data: aSeries.data.map(function (point) {
            var white = plotData.max > plotData.min ? (1 - (point.expressionLevel - plotData.min) / (plotData.max - plotData.min)) * MAX_WHITE : 0;
            return _extends({}, point, {
              expressionLevel: Math.round10(point.expressionLevel, -2),
              color: (0, _color2.default)('hwb(' + hue + ', ' + white + '%, 0%)').alpha(0.65).rgb().toString()
            });
          })
        };
      } else {
        return {
          name: aSeries.name,
          data: aSeries.data.map(function (point) {
            return _extends({}, point, {
              expressionLevel: Math.round10(point.expressionLevel, -2),
              color: (0, _color2.default)('lightgrey').alpha(0.65).rgb().toString()
            });
          })
        };
      }
    });
  };
};

var GeneExpressionScatterPlot = function GeneExpressionScatterPlot(props) {
  var atlasUrl = props.atlasUrl,
      suggesterEndpoint = props.suggesterEndpoint,
      geneId = props.geneId,
      onSelectGeneId = props.onSelectGeneId; // Suggester

  var height = props.height,
      plotData = props.plotData,
      expressionColourHue = props.expressionColourHue,
      highlightClusters = props.highlightClusters; // Chart

  var loading = props.loading,
      resourcesUrl = props.resourcesUrl,
      errorMessage = props.errorMessage; // Overlay

  var highchartsConfig = {
    plotOptions: {
      scatter: {
        tooltip: {
          headerFormat: '<b>{point.key} \u2013 Cluster {series.name}</b><br>',
          pointFormat: geneId ? 'Expression level: {point.expressionLevel} ' + plotData.unit : 'No gene selected'
        }
      }
    },
    chart: {
      height: height
    }
  };

  var renderGradient = plotData.max && plotData.min && plotData.max > plotData.min;
  var chartClassName = renderGradient ? 'small-10 columns' : 'small-12 columns';
  var gradient = renderGradient ? _react2.default.createElement(Gradient, { height: height, colourHue: expressionColourHue, plotData: plotData }) : null;

  return [_react2.default.createElement(_expressionAtlasAutocomplete2.default, { key: 'expression-autocomplete',
    wrapperClassName: 'row column',
    atlasUrl: atlasUrl,
    suggesterEndpoint: suggesterEndpoint,
    enableSpeciesFilter: false,
    initialValue: geneId,
    onSelect: function onSelect(event) {
      onSelectGeneId(event);
    }
  }), _react2.default.createElement(_PlotLoader2.default, { key: 'expression-plot',
    wrapperClassName: 'row',
    chartClassName: chartClassName,
    series: _colourizeExpressionLevel(expressionColourHue, highlightClusters)(plotData),
    highchartsConfig: highchartsConfig,
    children: gradient,
    loading: loading,
    resourcesUrl: resourcesUrl,
    errorMessage: errorMessage
  })];
};

var Gradient = function Gradient(_ref) {
  var height = _ref.height,
      colourHue = _ref.colourHue,
      plotData = _ref.plotData;

  var topColour = (0, _color2.default)('hwb(' + colourHue + ', 0%, 0%)').rgb().toString();
  var bottomColour = (0, _color2.default)('hwb(' + colourHue + ', ' + MAX_WHITE + '%, 0%)').rgb().toString();

  return _react2.default.createElement('div', { className: 'small-2 columns text-center' }, _react2.default.createElement('div', null, _react2.default.createElement('small', null, Math.round10(plotData.max, -2), ' ', plotData.unit)), _react2.default.createElement('div', { style: { width: '20px', height: height - 100 + 'px', background: 'linear-gradient(' + topColour + ', ' + bottomColour + ')', verticalAlign: 'middle', margin: 'auto' } }), _react2.default.createElement('div', null, _react2.default.createElement('small', null, Math.round10(plotData.min, -2), ' ', plotData.unit)));
};

GeneExpressionScatterPlot.propTypes = {
  height: _propTypes2.default.number.isRequired,

  plotData: _propTypes2.default.shape({
    series: _propTypes2.default.array.isRequired,
    unit: _propTypes2.default.string.isRequired,
    max: _propTypes2.default.number,
    min: _propTypes2.default.number
  }),
  expressionColourHue: _propTypes2.default.number,
  highlightClusters: _propTypes2.default.array,

  atlasUrl: _propTypes2.default.string.isRequired,
  suggesterEndpoint: _propTypes2.default.string.isRequired,
  onSelectGeneId: _propTypes2.default.func.isRequired,

  loading: _propTypes2.default.bool.isRequired,
  resourcesUrl: _propTypes2.default.string,
  errorMessage: _propTypes2.default.string
};

GeneExpressionScatterPlot.defaultProps = {
  expressionColourHue: 240
};

exports.default = GeneExpressionScatterPlot;
exports._colourizeExpressionLevel = _colourizeExpressionLevel;

/***/ }),

/***/ 476:
/*!*****************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-autocomplete/lib/index.js ***!
  \*****************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _AtlasAutocomplete = __webpack_require__(/*! ./AtlasAutocomplete.js */ 477);

var _AtlasAutocomplete2 = _interopRequireDefault(_AtlasAutocomplete);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

exports.default = _AtlasAutocomplete2.default;

/***/ }),

/***/ 477:
/*!*****************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-autocomplete/lib/AtlasAutocomplete.js ***!
  \*****************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () {
  function defineProperties(target, props) {
    for (var i = 0; i < props.length; i++) {
      var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
    }
  }return function (Constructor, protoProps, staticProps) {
    if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
  };
}();

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _reactAutocomplete = __webpack_require__(/*! react-autocomplete */ 478);

var _reactAutocomplete2 = _interopRequireDefault(_reactAutocomplete);

var _urijs = __webpack_require__(/*! urijs */ 15);

var _urijs2 = _interopRequireDefault(_urijs);

var _SpeciesSelect = __webpack_require__(/*! ./SpeciesSelect.js */ 482);

var _SpeciesSelect2 = _interopRequireDefault(_SpeciesSelect);

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
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

var AtlasAutocomplete = function (_React$Component) {
  _inherits(AtlasAutocomplete, _React$Component);

  function AtlasAutocomplete(props) {
    _classCallCheck(this, AtlasAutocomplete);

    var _this = _possibleConstructorReturn(this, (AtlasAutocomplete.__proto__ || Object.getPrototypeOf(AtlasAutocomplete)).call(this, props));

    _this.state = {
      selectedItem: _this.props.initialValue,
      species: '',
      currentSuggestions: []
    };

    _this.updateSuggestions = _this._updateSuggestions.bind(_this);
    _this.speciesSelectOnChange = _this._speciesSelectOnChange.bind(_this);
    return _this;
  }

  _createClass(AtlasAutocomplete, [{
    key: 'componentWillReceiveProps',
    value: function componentWillReceiveProps(nextProps) {
      this.setState({
        selectedItem: nextProps.initialValue
      });
    }
  }, {
    key: '_speciesSelectOnChange',
    value: function _speciesSelectOnChange(event) {
      this.setState({ species: event.target.value });
    }
  }, {
    key: '_updateSuggestions',
    value: function _updateSuggestions(event, value) {
      var _this2 = this;

      this.setState({
        selectedItem: value
      });

      var suggesterUrl = (0, _urijs2.default)(this.props.suggesterEndpoint, this.props.atlasUrl).search({
        query: value,
        species: this.state.species
      }).toString();

      fetch(suggesterUrl).then(function (response) {
        return response.json();
      }).then(function (json) {
        _this2.setState({
          currentSuggestions: json
        });
      }).catch(function (ex) {
        console.log('Error parsing JSON: ' + ex);
      });
    }
  }, {
    key: 'render',
    value: function render() {
      var _this3 = this;

      var menuStyle = {
        borderRadius: '3px',
        boxShadow: '0 2px 12px rgba(0, 0, 0, 0.1)',
        fontSize: '90%',
        overflow: 'auto',
        maxHeight: '50%', // TODO: don't cheat, let it flow to the bottom
        position: 'absolute',
        top: 'auto',
        zIndex: '1'
      };

      return _react2.default.createElement('div', { className: this.props.wrapperClassName }, _react2.default.createElement('div', { className: this.props.autocompleteClassName }, _react2.default.createElement('label', null, 'Gene ID, gene name or gene feature'), _react2.default.createElement(_reactAutocomplete2.default, { wrapperStyle: { display: '' },
        inputProps: { type: 'text', name: 'geneId' },

        value: this.state.selectedItem,
        items: this.state.currentSuggestions,

        getItemValue: function getItemValue(item) {
          return item.category;
        },
        onSelect: function onSelect(value) {
          _this3.setState({
            selectedItem: value, currentSuggestions: [] });
          _this3.props.onSelect(value);
        },
        onChange: this.updateSuggestions,

        renderItem: function renderItem(item, isHighlighted) {
          return _react2.default.createElement('div', { key: item.value + '_' + item.category, style: { background: isHighlighted ? 'lightgray' : 'white', padding: '2px 10px' } }, _react2.default.createElement('span', { dangerouslySetInnerHTML: { __html: item.value + ' (' + item.category + ')' } }));
        },

        menuStyle: menuStyle })), this.props.enableSpeciesFilter && _react2.default.createElement('div', { className: this.props.speciesFilterClassName }, _react2.default.createElement(_SpeciesSelect2.default, { atlasUrl: this.props.atlasUrl, onChange: this.speciesSelectOnChange })));
    }
  }]);

  return AtlasAutocomplete;
}(_react2.default.Component);

AtlasAutocomplete.propTypes = {
  atlasUrl: _propTypes2.default.string.isRequired,
  suggesterEndpoint: _propTypes2.default.string.isRequired,
  enableSpeciesFilter: _propTypes2.default.bool,
  initialValue: _propTypes2.default.string,
  onSelect: _propTypes2.default.func,
  wrapperClassName: _propTypes2.default.string,
  autocompleteClassName: _propTypes2.default.string,
  speciesFilterClassName: _propTypes2.default.string
};

AtlasAutocomplete.defaultProps = {
  enableSpeciesFilter: false,
  initialValue: '',
  onSelect: function onSelect() {},
  wrapperClassName: '',
  autocompleteClassName: '',
  speciesFilterClassName: ''
};

exports.default = AtlasAutocomplete;

/***/ }),

/***/ 478:
/*!*******************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-autocomplete/build/lib/Autocomplete.js ***!
  \*******************************************************************************************/
/*! dynamic exports provided */
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

var _createClass = function () {
  function defineProperties(target, props) {
    for (var i = 0; i < props.length; i++) {
      var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
    }
  }return function (Constructor, protoProps, staticProps) {
    if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
  };
}();

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

var React = __webpack_require__(/*! react */ 0);
var PropTypes = __webpack_require__(/*! prop-types */ 2);

var _require = __webpack_require__(/*! react-dom */ 18),
    findDOMNode = _require.findDOMNode;

var scrollIntoView = __webpack_require__(/*! dom-scroll-into-view */ 479);

var IMPERATIVE_API = ['blur', 'checkValidity', 'click', 'focus', 'select', 'setCustomValidity', 'setSelectionRange', 'setRangeText'];

function getScrollOffset() {
  return {
    x: window.pageXOffset !== undefined ? window.pageXOffset : (document.documentElement || document.body.parentNode || document.body).scrollLeft,
    y: window.pageYOffset !== undefined ? window.pageYOffset : (document.documentElement || document.body.parentNode || document.body).scrollTop
  };
}

var Autocomplete = function (_React$Component) {
  _inherits(Autocomplete, _React$Component);

  function Autocomplete(props) {
    _classCallCheck(this, Autocomplete);

    var _this = _possibleConstructorReturn(this, (Autocomplete.__proto__ || Object.getPrototypeOf(Autocomplete)).call(this, props));

    _this.state = {
      isOpen: false,
      highlightedIndex: null
    };
    _this._debugStates = [];
    _this.ensureHighlightedIndex = _this.ensureHighlightedIndex.bind(_this);
    _this.exposeAPI = _this.exposeAPI.bind(_this);
    _this.handleInputFocus = _this.handleInputFocus.bind(_this);
    _this.handleInputBlur = _this.handleInputBlur.bind(_this);
    _this.handleChange = _this.handleChange.bind(_this);
    _this.handleKeyDown = _this.handleKeyDown.bind(_this);
    _this.handleInputClick = _this.handleInputClick.bind(_this);
    _this.maybeAutoCompleteText = _this.maybeAutoCompleteText.bind(_this);
    return _this;
  }

  _createClass(Autocomplete, [{
    key: 'componentWillMount',
    value: function componentWillMount() {
      // this.refs is frozen, so we need to assign a new object to it
      this.refs = {};
      this._ignoreBlur = false;
      this._ignoreFocus = false;
      this._scrollOffset = null;
      this._scrollTimer = null;
    }
  }, {
    key: 'componentWillUnmount',
    value: function componentWillUnmount() {
      clearTimeout(this._scrollTimer);
      this._scrollTimer = null;
    }
  }, {
    key: 'componentWillReceiveProps',
    value: function componentWillReceiveProps(nextProps) {
      if (this.state.highlightedIndex !== null) {
        this.setState(this.ensureHighlightedIndex);
      }
      if (nextProps.autoHighlight && (this.props.value !== nextProps.value || this.state.highlightedIndex === null)) {
        this.setState(this.maybeAutoCompleteText);
      }
    }
  }, {
    key: 'componentDidMount',
    value: function componentDidMount() {
      if (this.isOpen()) {
        this.setMenuPositions();
      }
    }
  }, {
    key: 'componentDidUpdate',
    value: function componentDidUpdate(prevProps, prevState) {
      if (this.state.isOpen && !prevState.isOpen || 'open' in this.props && this.props.open && !prevProps.open) this.setMenuPositions();

      this.maybeScrollItemIntoView();
      if (prevState.isOpen !== this.state.isOpen) {
        this.props.onMenuVisibilityChange(this.state.isOpen);
      }
    }
  }, {
    key: 'exposeAPI',
    value: function exposeAPI(el) {
      var _this2 = this;

      this.refs.input = el;
      IMPERATIVE_API.forEach(function (ev) {
        return _this2[ev] = el && el[ev] && el[ev].bind(el);
      });
    }
  }, {
    key: 'maybeScrollItemIntoView',
    value: function maybeScrollItemIntoView() {
      if (this.isOpen() && this.state.highlightedIndex !== null) {
        var itemNode = this.refs['item-' + this.state.highlightedIndex];
        var menuNode = this.refs.menu;
        scrollIntoView(findDOMNode(itemNode), findDOMNode(menuNode), { onlyScrollIfNeeded: true });
      }
    }
  }, {
    key: 'handleKeyDown',
    value: function handleKeyDown(event) {
      if (Autocomplete.keyDownHandlers[event.key]) Autocomplete.keyDownHandlers[event.key].call(this, event);else if (!this.isOpen()) {
        this.setState({
          isOpen: true
        });
      }
    }
  }, {
    key: 'handleChange',
    value: function handleChange(event) {
      this.props.onChange(event, event.target.value);
    }
  }, {
    key: 'getFilteredItems',
    value: function getFilteredItems(props) {
      var items = props.items;

      if (props.shouldItemRender) {
        items = items.filter(function (item) {
          return props.shouldItemRender(item, props.value);
        });
      }

      if (props.sortItems) {
        items.sort(function (a, b) {
          return props.sortItems(a, b, props.value);
        });
      }

      return items;
    }
  }, {
    key: 'maybeAutoCompleteText',
    value: function maybeAutoCompleteText(state, props) {
      var highlightedIndex = state.highlightedIndex;
      var value = props.value,
          getItemValue = props.getItemValue;

      var index = highlightedIndex === null ? 0 : highlightedIndex;
      var matchedItem = this.getFilteredItems(props)[index];
      if (value !== '' && matchedItem) {
        var itemValue = getItemValue(matchedItem);
        var itemValueDoesMatch = itemValue.toLowerCase().indexOf(value.toLowerCase()) === 0;
        if (itemValueDoesMatch) {
          return { highlightedIndex: index };
        }
      }
      return { highlightedIndex: null };
    }
  }, {
    key: 'ensureHighlightedIndex',
    value: function ensureHighlightedIndex(state, props) {
      if (state.highlightedIndex >= this.getFilteredItems(props).length) {
        return { highlightedIndex: null };
      }
    }
  }, {
    key: 'setMenuPositions',
    value: function setMenuPositions() {
      var node = this.refs.input;
      var rect = node.getBoundingClientRect();
      var computedStyle = global.window.getComputedStyle(node);
      var marginBottom = parseInt(computedStyle.marginBottom, 10) || 0;
      var marginLeft = parseInt(computedStyle.marginLeft, 10) || 0;
      var marginRight = parseInt(computedStyle.marginRight, 10) || 0;
      this.setState({
        menuTop: rect.bottom + marginBottom,
        menuLeft: rect.left + marginLeft,
        menuWidth: rect.width + marginLeft + marginRight
      });
    }
  }, {
    key: 'highlightItemFromMouse',
    value: function highlightItemFromMouse(index) {
      this.setState({ highlightedIndex: index });
    }
  }, {
    key: 'selectItemFromMouse',
    value: function selectItemFromMouse(item) {
      var _this3 = this;

      var value = this.props.getItemValue(item);
      // The menu will de-render before a mouseLeave event
      // happens. Clear the flag to release control over focus
      this.setIgnoreBlur(false);
      this.setState({
        isOpen: false,
        highlightedIndex: null
      }, function () {
        _this3.props.onSelect(value, item);
      });
    }
  }, {
    key: 'setIgnoreBlur',
    value: function setIgnoreBlur(ignore) {
      this._ignoreBlur = ignore;
    }
  }, {
    key: 'renderMenu',
    value: function renderMenu() {
      var _this4 = this;

      var items = this.getFilteredItems(this.props).map(function (item, index) {
        var element = _this4.props.renderItem(item, _this4.state.highlightedIndex === index, { cursor: 'default' });
        return React.cloneElement(element, {
          onMouseEnter: function onMouseEnter() {
            return _this4.highlightItemFromMouse(index);
          },
          onClick: function onClick() {
            return _this4.selectItemFromMouse(item);
          },
          ref: function ref(e) {
            return _this4.refs['item-' + index] = e;
          }
        });
      });
      var style = {
        left: this.state.menuLeft,
        top: this.state.menuTop,
        minWidth: this.state.menuWidth
      };
      var menu = this.props.renderMenu(items, this.props.value, style);
      return React.cloneElement(menu, {
        ref: function ref(e) {
          return _this4.refs.menu = e;
        },
        // Ignore blur to prevent menu from de-rendering before we can process click
        onMouseEnter: function onMouseEnter() {
          return _this4.setIgnoreBlur(true);
        },
        onMouseLeave: function onMouseLeave() {
          return _this4.setIgnoreBlur(false);
        }
      });
    }
  }, {
    key: 'handleInputBlur',
    value: function handleInputBlur(event) {
      var _this5 = this;

      if (this._ignoreBlur) {
        this._ignoreFocus = true;
        this._scrollOffset = getScrollOffset();
        this.refs.input.focus();
        return;
      }
      var setStateCallback = void 0;
      var highlightedIndex = this.state.highlightedIndex;

      if (this.props.selectOnBlur && highlightedIndex !== null) {
        var items = this.getFilteredItems(this.props);
        var item = items[highlightedIndex];
        var value = this.props.getItemValue(item);
        setStateCallback = function setStateCallback() {
          return _this5.props.onSelect(value, item);
        };
      }
      this.setState({
        isOpen: false,
        highlightedIndex: null
      }, setStateCallback);
      var onBlur = this.props.inputProps.onBlur;

      if (onBlur) {
        onBlur(event);
      }
    }
  }, {
    key: 'handleInputFocus',
    value: function handleInputFocus(event) {
      var _this6 = this;

      if (this._ignoreFocus) {
        this._ignoreFocus = false;
        var _scrollOffset = this._scrollOffset,
            x = _scrollOffset.x,
            y = _scrollOffset.y;

        this._scrollOffset = null;
        // Focus will cause the browser to scroll the <input> into view.
        // This can cause the mouse coords to change, which in turn
        // could cause a new highlight to happen, cancelling the click
        // event (when selecting with the mouse)
        window.scrollTo(x, y);
        // Some browsers wait until all focus event handlers have been
        // processed before scrolling the <input> into view, so let's
        // scroll again on the next tick to ensure we're back to where
        // the user was before focus was lost. We could do the deferred
        // scroll only, but that causes a jarring split second jump in
        // some browsers that scroll before the focus event handlers
        // are triggered.
        clearTimeout(this._scrollTimer);
        this._scrollTimer = setTimeout(function () {
          _this6._scrollTimer = null;
          window.scrollTo(x, y);
        }, 0);
        return;
      }
      this.setState({ isOpen: true });
      var onFocus = this.props.inputProps.onFocus;

      if (onFocus) {
        onFocus(event);
      }
    }
  }, {
    key: 'isInputFocused',
    value: function isInputFocused() {
      var el = this.refs.input;
      return el.ownerDocument && el === el.ownerDocument.activeElement;
    }
  }, {
    key: 'handleInputClick',
    value: function handleInputClick() {
      // Input will not be focused if it's disabled
      if (this.isInputFocused() && !this.isOpen()) this.setState({ isOpen: true });
    }
  }, {
    key: 'composeEventHandlers',
    value: function composeEventHandlers(internal, external) {
      return external ? function (e) {
        internal(e);external(e);
      } : internal;
    }
  }, {
    key: 'isOpen',
    value: function isOpen() {
      return 'open' in this.props ? this.props.open : this.state.isOpen;
    }
  }, {
    key: 'render',
    value: function render() {
      if (this.props.debug) {
        // you don't like it, you love it
        this._debugStates.push({
          id: this._debugStates.length,
          state: this.state
        });
      }

      var inputProps = this.props.inputProps;

      var open = this.isOpen();
      return React.createElement('div', _extends({ style: _extends({}, this.props.wrapperStyle) }, this.props.wrapperProps), this.props.renderInput(_extends({}, inputProps, {
        role: 'combobox',
        'aria-autocomplete': 'list',
        'aria-expanded': open,
        autoComplete: 'off',
        ref: this.exposeAPI,
        onFocus: this.handleInputFocus,
        onBlur: this.handleInputBlur,
        onChange: this.handleChange,
        onKeyDown: this.composeEventHandlers(this.handleKeyDown, inputProps.onKeyDown),
        onClick: this.composeEventHandlers(this.handleInputClick, inputProps.onClick),
        value: this.props.value
      })), open && this.renderMenu(), this.props.debug && React.createElement('pre', { style: { marginLeft: 300 } }, JSON.stringify(this._debugStates.slice(Math.max(0, this._debugStates.length - 5), this._debugStates.length), null, 2)));
    }
  }]);

  return Autocomplete;
}(React.Component);

Autocomplete.propTypes = {
  /**
   * The items to display in the dropdown menu
   */
  items: PropTypes.array.isRequired,
  /**
   * The value to display in the input field
   */
  value: PropTypes.any,
  /**
   * Arguments: `event: Event, value: String`
   *
   * Invoked every time the user changes the input's value.
   */
  onChange: PropTypes.func,
  /**
   * Arguments: `value: String, item: Any`
   *
   * Invoked when the user selects an item from the dropdown menu.
   */
  onSelect: PropTypes.func,
  /**
   * Arguments: `item: Any, value: String`
   *
   * Invoked for each entry in `items` and its return value is used to
   * determine whether or not it should be displayed in the dropdown menu.
   * By default all items are always rendered.
   */
  shouldItemRender: PropTypes.func,
  /**
   * Arguments: `itemA: Any, itemB: Any, value: String`
   *
   * The function which is used to sort `items` before display.
   */
  sortItems: PropTypes.func,
  /**
   * Arguments: `item: Any`
   *
   * Used to read the display value from each entry in `items`.
   */
  getItemValue: PropTypes.func.isRequired,
  /**
   * Arguments: `item: Any, isHighlighted: Boolean, styles: Object`
   *
   * Invoked for each entry in `items` that also passes `shouldItemRender` to
   * generate the render tree for each item in the dropdown menu. `styles` is
   * an optional set of styles that can be applied to improve the look/feel
   * of the items in the dropdown menu.
   */
  renderItem: PropTypes.func.isRequired,
  /**
   * Arguments: `items: Array<Any>, value: String, styles: Object`
   *
   * Invoked to generate the render tree for the dropdown menu. Ensure the
   * returned tree includes every entry in `items` or else the highlight order
   * and keyboard navigation logic will break. `styles` will contain
   * { top, left, minWidth } which are the coordinates of the top-left corner
   * and the width of the dropdown menu.
   */
  renderMenu: PropTypes.func,
  /**
   * Styles that are applied to the dropdown menu in the default `renderMenu`
   * implementation. If you override `renderMenu` and you want to use
   * `menuStyle` you must manually apply them (`this.props.menuStyle`).
   */
  menuStyle: PropTypes.object,
  /**
   * Arguments: `props: Object`
   *
   * Invoked to generate the input element. The `props` argument is the result
   * of merging `props.inputProps` with a selection of props that are required
   * both for functionality and accessibility. At the very least you need to
   * apply `props.ref` and all `props.on<event>` event handlers. Failing to do
   * this will cause `Autocomplete` to behave unexpectedly.
   */
  renderInput: PropTypes.func,
  /**
   * Props passed to `props.renderInput`. By default these props will be
   * applied to the `<input />` element rendered by `Autocomplete`, unless you
   * have specified a custom value for `props.renderInput`. Any properties
   * supported by `HTMLInputElement` can be specified, apart from the
   * following which are set by `Autocomplete`: value, autoComplete, role,
   * aria-autocomplete. `inputProps` is commonly used for (but not limited to)
   * placeholder, event handlers (onFocus, onBlur, etc.), autoFocus, etc..
   */
  inputProps: PropTypes.object,
  /**
   * Props that are applied to the element which wraps the `<input />` and
   * dropdown menu elements rendered by `Autocomplete`.
   */
  wrapperProps: PropTypes.object,
  /**
   * This is a shorthand for `wrapperProps={{ style: <your styles> }}`.
   * Note that `wrapperStyle` is applied before `wrapperProps`, so the latter
   * will win if it contains a `style` entry.
   */
  wrapperStyle: PropTypes.object,
  /**
   * Whether or not to automatically highlight the top match in the dropdown
   * menu.
   */
  autoHighlight: PropTypes.bool,
  /**
   * Whether or not to automatically select the highlighted item when the
   * `<input>` loses focus.
   */
  selectOnBlur: PropTypes.bool,
  /**
   * Arguments: `isOpen: Boolean`
   *
   * Invoked every time the dropdown menu's visibility changes (i.e. every
   * time it is displayed/hidden).
   */
  onMenuVisibilityChange: PropTypes.func,
  /**
   * Used to override the internal logic which displays/hides the dropdown
   * menu. This is useful if you want to force a certain state based on your
   * UX/business logic. Use it together with `onMenuVisibilityChange` for
   * fine-grained control over the dropdown menu dynamics.
   */
  open: PropTypes.bool,
  debug: PropTypes.bool
};
Autocomplete.defaultProps = {
  value: '',
  wrapperProps: {},
  wrapperStyle: {
    display: 'inline-block'
  },
  inputProps: {},
  renderInput: function renderInput(props) {
    return React.createElement('input', props);
  },
  onChange: function onChange() {},
  onSelect: function onSelect() {},
  renderMenu: function renderMenu(items, value, style) {
    return React.createElement('div', { style: _extends({}, style, this.menuStyle), children: items });
  },

  menuStyle: {
    borderRadius: '3px',
    boxShadow: '0 2px 12px rgba(0, 0, 0, 0.1)',
    background: 'rgba(255, 255, 255, 0.9)',
    padding: '2px 0',
    fontSize: '90%',
    position: 'fixed',
    overflow: 'auto',
    maxHeight: '50%' },
  autoHighlight: true,
  selectOnBlur: false,
  onMenuVisibilityChange: function onMenuVisibilityChange() {}
};
Autocomplete.keyDownHandlers = {
  ArrowDown: function ArrowDown(event) {
    event.preventDefault();
    var itemsLength = this.getFilteredItems(this.props).length;
    if (!itemsLength) return;
    var highlightedIndex = this.state.highlightedIndex;

    var index = highlightedIndex === null || highlightedIndex === itemsLength - 1 ? 0 : highlightedIndex + 1;
    this.setState({
      highlightedIndex: index,
      isOpen: true
    });
  },
  ArrowUp: function ArrowUp(event) {
    event.preventDefault();
    var itemsLength = this.getFilteredItems(this.props).length;
    if (!itemsLength) return;
    var highlightedIndex = this.state.highlightedIndex;

    var index = highlightedIndex === 0 || highlightedIndex === null ? itemsLength - 1 : highlightedIndex - 1;
    this.setState({
      highlightedIndex: index,
      isOpen: true
    });
  },
  Enter: function Enter(event) {
    var _this7 = this;

    // Key code 229 is used for selecting items from character selectors (Pinyin, Kana, etc)
    if (event.keyCode !== 13) return;
    if (!this.isOpen()) {
      // menu is closed so there is no selection to accept -> do nothing
      return;
    } else if (this.state.highlightedIndex == null) {
      // input has focus but no menu item is selected + enter is hit -> close the menu, highlight whatever's in input
      this.setState({
        isOpen: false
      }, function () {
        _this7.refs.input.select();
      });
    } else {
      // text entered + menu item has been highlighted + enter is hit -> update value to that of selected menu item, close the menu
      event.preventDefault();
      var item = this.getFilteredItems(this.props)[this.state.highlightedIndex];
      var value = this.props.getItemValue(item);
      this.setState({
        isOpen: false,
        highlightedIndex: null
      }, function () {
        //this.refs.input.focus() // TODO: file issue
        _this7.refs.input.setSelectionRange(value.length, value.length);
        _this7.props.onSelect(value, item);
      });
    }
  },
  Escape: function Escape() {
    // In case the user is currently hovering over the menu
    this.setIgnoreBlur(false);
    this.setState({
      highlightedIndex: null,
      isOpen: false
    });
  },
  Tab: function Tab() {
    // In case the user is currently hovering over the menu
    this.setIgnoreBlur(false);
  }
};

module.exports = Autocomplete;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(/*! ./../../../../../../node_modules/webpack/buildin/global.js */ 9)))

/***/ }),

/***/ 479:
/*!****************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/dom-scroll-into-view/index.js ***!
  \****************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = __webpack_require__(/*! ./lib/dom-scroll-into-view */ 480);

/***/ }),

/***/ 480:
/*!***********************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/dom-scroll-into-view/lib/dom-scroll-into-view.js ***!
  \***********************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var util = __webpack_require__(/*! ./util */ 481);

function scrollIntoView(elem, container, config) {
  config = config || {};
  // document 归一化到 window
  if (container.nodeType === 9) {
    container = util.getWindow(container);
  }

  var allowHorizontalScroll = config.allowHorizontalScroll;
  var onlyScrollIfNeeded = config.onlyScrollIfNeeded;
  var alignWithTop = config.alignWithTop;
  var alignWithLeft = config.alignWithLeft;

  allowHorizontalScroll = allowHorizontalScroll === undefined ? true : allowHorizontalScroll;

  var isWin = util.isWindow(container);
  var elemOffset = util.offset(elem);
  var eh = util.outerHeight(elem);
  var ew = util.outerWidth(elem);
  var containerOffset, ch, cw, containerScroll, diffTop, diffBottom, win, winScroll, ww, wh;

  if (isWin) {
    win = container;
    wh = util.height(win);
    ww = util.width(win);
    winScroll = {
      left: util.scrollLeft(win),
      top: util.scrollTop(win)
    };
    // elem 相对 container 可视视窗的距离
    diffTop = {
      left: elemOffset.left - winScroll.left,
      top: elemOffset.top - winScroll.top
    };
    diffBottom = {
      left: elemOffset.left + ew - (winScroll.left + ww),
      top: elemOffset.top + eh - (winScroll.top + wh)
    };
    containerScroll = winScroll;
  } else {
    containerOffset = util.offset(container);
    ch = container.clientHeight;
    cw = container.clientWidth;
    containerScroll = {
      left: container.scrollLeft,
      top: container.scrollTop
    };
    // elem 相对 container 可视视窗的距离
    // 注意边框, offset 是边框到根节点
    diffTop = {
      left: elemOffset.left - (containerOffset.left + (parseFloat(util.css(container, 'borderLeftWidth')) || 0)),
      top: elemOffset.top - (containerOffset.top + (parseFloat(util.css(container, 'borderTopWidth')) || 0))
    };
    diffBottom = {
      left: elemOffset.left + ew - (containerOffset.left + cw + (parseFloat(util.css(container, 'borderRightWidth')) || 0)),
      top: elemOffset.top + eh - (containerOffset.top + ch + (parseFloat(util.css(container, 'borderBottomWidth')) || 0))
    };
  }

  if (diffTop.top < 0 || diffBottom.top > 0) {
    // 强制向上
    if (alignWithTop === true) {
      util.scrollTop(container, containerScroll.top + diffTop.top);
    } else if (alignWithTop === false) {
      util.scrollTop(container, containerScroll.top + diffBottom.top);
    } else {
      // 自动调整
      if (diffTop.top < 0) {
        util.scrollTop(container, containerScroll.top + diffTop.top);
      } else {
        util.scrollTop(container, containerScroll.top + diffBottom.top);
      }
    }
  } else {
    if (!onlyScrollIfNeeded) {
      alignWithTop = alignWithTop === undefined ? true : !!alignWithTop;
      if (alignWithTop) {
        util.scrollTop(container, containerScroll.top + diffTop.top);
      } else {
        util.scrollTop(container, containerScroll.top + diffBottom.top);
      }
    }
  }

  if (allowHorizontalScroll) {
    if (diffTop.left < 0 || diffBottom.left > 0) {
      // 强制向上
      if (alignWithLeft === true) {
        util.scrollLeft(container, containerScroll.left + diffTop.left);
      } else if (alignWithLeft === false) {
        util.scrollLeft(container, containerScroll.left + diffBottom.left);
      } else {
        // 自动调整
        if (diffTop.left < 0) {
          util.scrollLeft(container, containerScroll.left + diffTop.left);
        } else {
          util.scrollLeft(container, containerScroll.left + diffBottom.left);
        }
      }
    } else {
      if (!onlyScrollIfNeeded) {
        alignWithLeft = alignWithLeft === undefined ? true : !!alignWithLeft;
        if (alignWithLeft) {
          util.scrollLeft(container, containerScroll.left + diffTop.left);
        } else {
          util.scrollLeft(container, containerScroll.left + diffBottom.left);
        }
      }
    }
  }
}

module.exports = scrollIntoView;

/***/ }),

/***/ 481:
/*!*******************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/dom-scroll-into-view/lib/util.js ***!
  \*******************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var RE_NUM = /[\-+]?(?:\d*\.|)\d+(?:[eE][\-+]?\d+|)/.source;

function getClientPosition(elem) {
  var box, x, y;
  var doc = elem.ownerDocument;
  var body = doc.body;
  var docElem = doc && doc.documentElement;
  // 根据 GBS 最新数据，A-Grade Browsers 都已支持 getBoundingClientRect 方法，不用再考虑传统的实现方式
  box = elem.getBoundingClientRect();

  // 注：jQuery 还考虑减去 docElem.clientLeft/clientTop
  // 但测试发现，这样反而会导致当 html 和 body 有边距/边框样式时，获取的值不正确
  // 此外，ie6 会忽略 html 的 margin 值，幸运地是没有谁会去设置 html 的 margin

  x = box.left;
  y = box.top;

  // In IE, most of the time, 2 extra pixels are added to the top and left
  // due to the implicit 2-pixel inset border.  In IE6/7 quirks mode and
  // IE6 standards mode, this border can be overridden by setting the
  // document element's border to zero -- thus, we cannot rely on the
  // offset always being 2 pixels.

  // In quirks mode, the offset can be determined by querying the body's
  // clientLeft/clientTop, but in standards mode, it is found by querying
  // the document element's clientLeft/clientTop.  Since we already called
  // getClientBoundingRect we have already forced a reflow, so it is not
  // too expensive just to query them all.

  // ie 下应该减去窗口的边框吧，毕竟默认 absolute 都是相对窗口定位的
  // 窗口边框标准是设 documentElement ,quirks 时设置 body
  // 最好禁止在 body 和 html 上边框 ，但 ie < 9 html 默认有 2px ，减去
  // 但是非 ie 不可能设置窗口边框，body html 也不是窗口 ,ie 可以通过 html,body 设置
  // 标准 ie 下 docElem.clientTop 就是 border-top
  // ie7 html 即窗口边框改变不了。永远为 2
  // 但标准 firefox/chrome/ie9 下 docElem.clientTop 是窗口边框，即使设了 border-top 也为 0

  x -= docElem.clientLeft || body.clientLeft || 0;
  y -= docElem.clientTop || body.clientTop || 0;

  return { left: x, top: y };
}

function getScroll(w, top) {
  var ret = w['page' + (top ? 'Y' : 'X') + 'Offset'];
  var method = 'scroll' + (top ? 'Top' : 'Left');
  if (typeof ret !== 'number') {
    var d = w.document;
    //ie6,7,8 standard mode
    ret = d.documentElement[method];
    if (typeof ret !== 'number') {
      //quirks mode
      ret = d.body[method];
    }
  }
  return ret;
}

function getScrollLeft(w) {
  return getScroll(w);
}

function getScrollTop(w) {
  return getScroll(w, true);
}

function getOffset(el) {
  var pos = getClientPosition(el);
  var doc = el.ownerDocument;
  var w = doc.defaultView || doc.parentWindow;
  pos.left += getScrollLeft(w);
  pos.top += getScrollTop(w);
  return pos;
}
function _getComputedStyle(elem, name, computedStyle) {
  var val = '';
  var d = elem.ownerDocument;

  // https://github.com/kissyteam/kissy/issues/61
  if (computedStyle = computedStyle || d.defaultView.getComputedStyle(elem, null)) {
    val = computedStyle.getPropertyValue(name) || computedStyle[name];
  }

  return val;
}

var _RE_NUM_NO_PX = new RegExp('^(' + RE_NUM + ')(?!px)[a-z%]+$', 'i');
var RE_POS = /^(top|right|bottom|left)$/,
    CURRENT_STYLE = 'currentStyle',
    RUNTIME_STYLE = 'runtimeStyle',
    LEFT = 'left',
    PX = 'px';

function _getComputedStyleIE(elem, name) {
  // currentStyle maybe null
  // http://msdn.microsoft.com/en-us/library/ms535231.aspx
  var ret = elem[CURRENT_STYLE] && elem[CURRENT_STYLE][name];

  // 当 width/height 设置为百分比时，通过 pixelLeft 方式转换的 width/height 值
  // 一开始就处理了! CUSTOM_STYLE.height,CUSTOM_STYLE.width ,cssHook 解决@2011-08-19
  // 在 ie 下不对，需要直接用 offset 方式
  // borderWidth 等值也有问题，但考虑到 borderWidth 设为百分比的概率很小，这里就不考虑了

  // From the awesome hack by Dean Edwards
  // http://erik.eae.net/archives/2007/07/27/18.54.15/#comment-102291
  // If we're not dealing with a regular pixel number
  // but a number that has a weird ending, we need to convert it to pixels
  // exclude left right for relativity
  if (_RE_NUM_NO_PX.test(ret) && !RE_POS.test(name)) {
    // Remember the original values
    var style = elem.style,
        left = style[LEFT],
        rsLeft = elem[RUNTIME_STYLE][LEFT];

    // prevent flashing of content
    elem[RUNTIME_STYLE][LEFT] = elem[CURRENT_STYLE][LEFT];

    // Put in the new values to get a computed value out
    style[LEFT] = name === 'fontSize' ? '1em' : ret || 0;
    ret = style.pixelLeft + PX;

    // Revert the changed values
    style[LEFT] = left;

    elem[RUNTIME_STYLE][LEFT] = rsLeft;
  }
  return ret === '' ? 'auto' : ret;
}

var getComputedStyleX;
if (typeof window !== 'undefined') {
  getComputedStyleX = window.getComputedStyle ? _getComputedStyle : _getComputedStyleIE;
}

// 设置 elem 相对 elem.ownerDocument 的坐标
function setOffset(elem, offset) {
  // set position first, in-case top/left are set even on static elem
  if (css(elem, 'position') === 'static') {
    elem.style.position = 'relative';
  }

  var old = getOffset(elem),
      ret = {},
      current,
      key;

  for (key in offset) {
    current = parseFloat(css(elem, key)) || 0;
    ret[key] = current + offset[key] - old[key];
  }
  css(elem, ret);
}

function each(arr, fn) {
  for (var i = 0; i < arr.length; i++) {
    fn(arr[i]);
  }
}

function isBorderBoxFn(elem) {
  return getComputedStyleX(elem, 'boxSizing') === 'border-box';
}

var BOX_MODELS = ['margin', 'border', 'padding'],
    CONTENT_INDEX = -1,
    PADDING_INDEX = 2,
    BORDER_INDEX = 1,
    MARGIN_INDEX = 0;

function swap(elem, options, callback) {
  var old = {},
      style = elem.style,
      name;

  // Remember the old values, and insert the new ones
  for (name in options) {
    old[name] = style[name];
    style[name] = options[name];
  }

  callback.call(elem);

  // Revert the old values
  for (name in options) {
    style[name] = old[name];
  }
}

function getPBMWidth(elem, props, which) {
  var value = 0,
      prop,
      j,
      i;
  for (j = 0; j < props.length; j++) {
    prop = props[j];
    if (prop) {
      for (i = 0; i < which.length; i++) {
        var cssProp;
        if (prop === 'border') {
          cssProp = prop + which[i] + 'Width';
        } else {
          cssProp = prop + which[i];
        }
        value += parseFloat(getComputedStyleX(elem, cssProp)) || 0;
      }
    }
  }
  return value;
}

/**
 * A crude way of determining if an object is a window
 * @member util
 */
function isWindow(obj) {
  // must use == for ie8
  /*jshint eqeqeq:false*/
  return obj != null && obj == obj.window;
}

var domUtils = {};

each(['Width', 'Height'], function (name) {
  domUtils['doc' + name] = function (refWin) {
    var d = refWin.document;
    return Math.max(
    //firefox chrome documentElement.scrollHeight< body.scrollHeight
    //ie standard mode : documentElement.scrollHeight> body.scrollHeight
    d.documentElement['scroll' + name],
    //quirks : documentElement.scrollHeight 最大等于可视窗口多一点？
    d.body['scroll' + name], domUtils['viewport' + name](d));
  };

  domUtils['viewport' + name] = function (win) {
    // pc browser includes scrollbar in window.innerWidth
    var prop = 'client' + name,
        doc = win.document,
        body = doc.body,
        documentElement = doc.documentElement,
        documentElementProp = documentElement[prop];
    // 标准模式取 documentElement
    // backcompat 取 body
    return doc.compatMode === 'CSS1Compat' && documentElementProp || body && body[prop] || documentElementProp;
  };
});

/*
 得到元素的大小信息
 @param elem
 @param name
 @param {String} [extra]  'padding' : (css width) + padding
 'border' : (css width) + padding + border
 'margin' : (css width) + padding + border + margin
 */
function getWH(elem, name, extra) {
  if (isWindow(elem)) {
    return name === 'width' ? domUtils.viewportWidth(elem) : domUtils.viewportHeight(elem);
  } else if (elem.nodeType === 9) {
    return name === 'width' ? domUtils.docWidth(elem) : domUtils.docHeight(elem);
  }
  var which = name === 'width' ? ['Left', 'Right'] : ['Top', 'Bottom'],
      borderBoxValue = name === 'width' ? elem.offsetWidth : elem.offsetHeight;
  var computedStyle = getComputedStyleX(elem);
  var isBorderBox = isBorderBoxFn(elem, computedStyle);
  var cssBoxValue = 0;
  if (borderBoxValue == null || borderBoxValue <= 0) {
    borderBoxValue = undefined;
    // Fall back to computed then un computed css if necessary
    cssBoxValue = getComputedStyleX(elem, name);
    if (cssBoxValue == null || Number(cssBoxValue) < 0) {
      cssBoxValue = elem.style[name] || 0;
    }
    // Normalize '', auto, and prepare for extra
    cssBoxValue = parseFloat(cssBoxValue) || 0;
  }
  if (extra === undefined) {
    extra = isBorderBox ? BORDER_INDEX : CONTENT_INDEX;
  }
  var borderBoxValueOrIsBorderBox = borderBoxValue !== undefined || isBorderBox;
  var val = borderBoxValue || cssBoxValue;
  if (extra === CONTENT_INDEX) {
    if (borderBoxValueOrIsBorderBox) {
      return val - getPBMWidth(elem, ['border', 'padding'], which, computedStyle);
    } else {
      return cssBoxValue;
    }
  } else if (borderBoxValueOrIsBorderBox) {
    return val + (extra === BORDER_INDEX ? 0 : extra === PADDING_INDEX ? -getPBMWidth(elem, ['border'], which, computedStyle) : getPBMWidth(elem, ['margin'], which, computedStyle));
  } else {
    return cssBoxValue + getPBMWidth(elem, BOX_MODELS.slice(extra), which, computedStyle);
  }
}

var cssShow = { position: 'absolute', visibility: 'hidden', display: 'block' };

// fix #119 : https://github.com/kissyteam/kissy/issues/119
function getWHIgnoreDisplay(elem) {
  var val,
      args = arguments;
  // in case elem is window
  // elem.offsetWidth === undefined
  if (elem.offsetWidth !== 0) {
    val = getWH.apply(undefined, args);
  } else {
    swap(elem, cssShow, function () {
      val = getWH.apply(undefined, args);
    });
  }
  return val;
}

each(['width', 'height'], function (name) {
  var first = name.charAt(0).toUpperCase() + name.slice(1);
  domUtils['outer' + first] = function (el, includeMargin) {
    return el && getWHIgnoreDisplay(el, name, includeMargin ? MARGIN_INDEX : BORDER_INDEX);
  };
  var which = name === 'width' ? ['Left', 'Right'] : ['Top', 'Bottom'];

  domUtils[name] = function (elem, val) {
    if (val !== undefined) {
      if (elem) {
        var computedStyle = getComputedStyleX(elem);
        var isBorderBox = isBorderBoxFn(elem);
        if (isBorderBox) {
          val += getPBMWidth(elem, ['padding', 'border'], which, computedStyle);
        }
        return css(elem, name, val);
      }
      return;
    }
    return elem && getWHIgnoreDisplay(elem, name, CONTENT_INDEX);
  };
});

function css(el, name, value) {
  if ((typeof name === 'undefined' ? 'undefined' : _typeof(name)) === 'object') {
    for (var i in name) {
      css(el, i, name[i]);
    }
    return;
  }
  if (typeof value !== 'undefined') {
    if (typeof value === 'number') {
      value = value + 'px';
    }
    el.style[name] = value;
  } else {
    return getComputedStyleX(el, name);
  }
}

function mix(to, from) {
  for (var i in from) {
    to[i] = from[i];
  }
  return to;
}

var utils = module.exports = {
  getWindow: function getWindow(node) {
    var doc = node.ownerDocument || node;
    return doc.defaultView || doc.parentWindow;
  },
  offset: function offset(el, value) {
    if (typeof value !== 'undefined') {
      setOffset(el, value);
    } else {
      return getOffset(el);
    }
  },
  isWindow: isWindow,
  each: each,
  css: css,
  clone: function clone(obj) {
    var ret = {};
    for (var i in obj) {
      ret[i] = obj[i];
    }
    var overflow = obj.overflow;
    if (overflow) {
      for (i in obj) {
        ret.overflow[i] = obj.overflow[i];
      }
    }
    return ret;
  },
  mix: mix,
  scrollLeft: function scrollLeft(w, v) {
    if (isWindow(w)) {
      if (v === undefined) {
        return getScrollLeft(w);
      } else {
        window.scrollTo(v, getScrollTop(w));
      }
    } else {
      if (v === undefined) {
        return w.scrollLeft;
      } else {
        w.scrollLeft = v;
      }
    }
  },
  scrollTop: function scrollTop(w, v) {
    if (isWindow(w)) {
      if (v === undefined) {
        return getScrollTop(w);
      } else {
        window.scrollTo(getScrollLeft(w), v);
      }
    } else {
      if (v === undefined) {
        return w.scrollTop;
      } else {
        w.scrollTop = v;
      }
    }
  },
  merge: function merge() {
    var ret = {};
    for (var i = 0; i < arguments.length; i++) {
      utils.mix(ret, arguments[i]);
    }
    return ret;
  },
  viewportWidth: 0,
  viewportHeight: 0
};

mix(utils, domUtils);

/***/ }),

/***/ 482:
/*!*************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-autocomplete/lib/SpeciesSelect.js ***!
  \*************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () {
  function defineProperties(target, props) {
    for (var i = 0; i < props.length; i++) {
      var descriptor = props[i];descriptor.enumerable = descriptor.enumerable || false;descriptor.configurable = true;if ("value" in descriptor) descriptor.writable = true;Object.defineProperty(target, descriptor.key, descriptor);
    }
  }return function (Constructor, protoProps, staticProps) {
    if (protoProps) defineProperties(Constructor.prototype, protoProps);if (staticProps) defineProperties(Constructor, staticProps);return Constructor;
  };
}();

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

var _urijs = __webpack_require__(/*! urijs */ 15);

var _urijs2 = _interopRequireDefault(_urijs);

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
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

function _asyncToGenerator(fn) {
  return function () {
    var gen = fn.apply(this, arguments);return new Promise(function (resolve, reject) {
      function step(key, arg) {
        try {
          var info = gen[key](arg);var value = info.value;
        } catch (error) {
          reject(error);return;
        }if (info.done) {
          resolve(value);
        } else {
          return Promise.resolve(value).then(function (value) {
            step("next", value);
          }, function (err) {
            step("throw", err);
          });
        }
      }return step("next");
    });
  };
}

var fetchResponseJson = function () {
  var _ref = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee(base, endpoint) {
    var response, responseJson;
    return regeneratorRuntime.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            _context.next = 2;
            return fetch((0, _urijs2.default)(endpoint, base).toString());

          case 2:
            response = _context.sent;
            _context.next = 5;
            return response.json();

          case 5:
            responseJson = _context.sent;
            return _context.abrupt('return', responseJson);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, undefined);
  }));

  return function fetchResponseJson(_x, _x2) {
    return _ref.apply(this, arguments);
  };
}();

var _option = function _option(label) {
  return _react2.default.createElement('option', { key: label, value: label }, label);
};

var SpeciesSelect = function (_React$Component) {
  _inherits(SpeciesSelect, _React$Component);

  function SpeciesSelect(props) {
    _classCallCheck(this, SpeciesSelect);

    var _this = _possibleConstructorReturn(this, (SpeciesSelect.__proto__ || Object.getPrototypeOf(SpeciesSelect)).call(this, props));

    _this.state = {
      loading: false,
      errorMessage: null,
      species: {
        topSpecies: [],
        separator: '',
        allSpecies: []
      }
    };

    _this._fetchAndSetState = _this._fetchAndSetState.bind(_this);
    return _this;
  }

  _createClass(SpeciesSelect, [{
    key: 'render',
    value: function render() {
      return _react2.default.createElement('div', null, _react2.default.createElement('label', null, 'Species'), this.state.loading ? _react2.default.createElement('select', { disabled: 'true' }, _option("Fetching species\u2026")) : this.state.errorMessage ? _react2.default.createElement('select', { disabled: 'true' }, _option(this.state.errorMessage)) : _react2.default.createElement('select', { onChange: this.props.onChange }, _react2.default.createElement('option', { value: '' }, 'Any'), this.state.species.topSpecies.map(_option), _react2.default.createElement('option', { value: '-', disabled: 'true' }, this.state.species.separator), this.state.species.allSpecies.map(_option)));
    }
  }, {
    key: '_fetchAndSetState',
    value: function _fetchAndSetState(baseUrl, relUrl) {
      var _this2 = this;

      this.setState({
        loading: true
      });
      return fetchResponseJson(baseUrl, relUrl).then(function (responseJson) {
        _this2.setState({
          species: responseJson,
          loading: false,
          errorMessage: null
        });
      }).catch(function (reason) {
        _this2.setState({
          errorMessage: reason.name + ': ' + reason.message,
          loading: false
        });
      });
    }
  }, {
    key: 'componentDidMount',
    value: function componentDidMount() {
      return this._fetchAndSetState(this.props.atlasUrl, 'json/suggestions/species');
    }
  }]);

  return SpeciesSelect;
}(_react2.default.Component);

SpeciesSelect.propTypes = {
  atlasUrl: _propTypes2.default.string.isRequired,
  onChange: _propTypes2.default.func.isRequired
};

exports.default = SpeciesSelect;

/***/ }),

/***/ 483:
/*!***************************************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/expression-atlas-experiment-page-tsne-plot/lib/util/MathRound.js ***!
  \***************************************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// Taken from https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math/round
// Beware: it pollutes Math!

// Closure

(function () {
  /**
   * Decimal adjustment of a number.
   *
   * @param {String}  type  The type of adjustment.
   * @param {Number}  value The number.
   * @param {Integer} exp   The exponent (the 10 logarithm of the adjustment base).
   * @returns {Number} The adjusted value.
   */
  function decimalAdjust(type, value, exp) {
    // If the exp is undefined or zero...
    if (typeof exp === 'undefined' || +exp === 0) {
      return Math[type](value);
    }
    value = +value;
    exp = +exp;
    // If the value is not a number or the exp is not an integer...
    if (isNaN(value) || !(typeof exp === 'number' && exp % 1 === 0)) {
      return NaN;
    }
    // If the value is negative...
    if (value < 0) {
      return -decimalAdjust(type, -value, exp);
    }
    // Shift
    value = value.toString().split('e');
    value = Math[type](+(value[0] + 'e' + (value[1] ? +value[1] - exp : -exp)));
    // Shift back
    value = value.toString().split('e');
    return +(value[0] + 'e' + (value[1] ? +value[1] + exp : exp));
  }

  // Decimal round
  if (!Math.round10) {
    Math.round10 = function (value, exp) {
      return decimalAdjust('round', value, exp);
    };
  }
  // Decimal floor
  if (!Math.floor10) {
    Math.floor10 = function (value, exp) {
      return decimalAdjust('floor', value, exp);
    };
  }
  // Decimal ceil
  if (!Math.ceil10) {
    Math.ceil10 = function (value, exp) {
      return decimalAdjust('ceil', value, exp);
    };
  }
})();

/***/ }),

/***/ 8:
/*!*******************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/invariant/browser.js ***!
  \*******************************************************************/
/*! dynamic exports provided */
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

/***/ 81:
/*!***********************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/LocationUtils.js ***!
  \***********************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;
exports.locationsAreEqual = exports.createLocation = undefined;

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

var _resolvePathname = __webpack_require__(/*! resolve-pathname */ 186);

var _resolvePathname2 = _interopRequireDefault(_resolvePathname);

var _valueEqual = __webpack_require__(/*! value-equal */ 187);

var _valueEqual2 = _interopRequireDefault(_valueEqual);

var _PathUtils = __webpack_require__(/*! ./PathUtils */ 28);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var createLocation = exports.createLocation = function createLocation(path, state, key, currentLocation) {
  var location = void 0;
  if (typeof path === 'string') {
    // Two-arg form: push(path, state)
    location = (0, _PathUtils.parsePath)(path);
    location.state = state;
  } else {
    // One-arg form: push(location)
    location = _extends({}, path);

    if (location.pathname === undefined) location.pathname = '';

    if (location.search) {
      if (location.search.charAt(0) !== '?') location.search = '?' + location.search;
    } else {
      location.search = '';
    }

    if (location.hash) {
      if (location.hash.charAt(0) !== '#') location.hash = '#' + location.hash;
    } else {
      location.hash = '';
    }

    if (state !== undefined && location.state === undefined) location.state = state;
  }

  try {
    location.pathname = decodeURI(location.pathname);
  } catch (e) {
    if (e instanceof URIError) {
      throw new URIError('Pathname "' + location.pathname + '" could not be decoded. ' + 'This is likely caused by an invalid percent-encoding.');
    } else {
      throw e;
    }
  }

  if (key) location.key = key;

  if (currentLocation) {
    // Resolve incomplete/relative pathname relative to current location.
    if (!location.pathname) {
      location.pathname = currentLocation.pathname;
    } else if (location.pathname.charAt(0) !== '/') {
      location.pathname = (0, _resolvePathname2.default)(location.pathname, currentLocation.pathname);
    }
  } else {
    // When there is no prior location and pathname is empty, set it to /
    if (!location.pathname) {
      location.pathname = '/';
    }
  }

  return location;
};

var locationsAreEqual = exports.locationsAreEqual = function locationsAreEqual(a, b) {
  return a.pathname === b.pathname && a.search === b.search && a.hash === b.hash && a.key === b.key && (0, _valueEqual2.default)(a.state, b.state);
};

/***/ }),

/***/ 82:
/*!*********************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/createTransitionManager.js ***!
  \*********************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

var createTransitionManager = function createTransitionManager() {
  var prompt = null;

  var setPrompt = function setPrompt(nextPrompt) {
    (0, _warning2.default)(prompt == null, 'A history supports only one prompt at a time');

    prompt = nextPrompt;

    return function () {
      if (prompt === nextPrompt) prompt = null;
    };
  };

  var confirmTransitionTo = function confirmTransitionTo(location, action, getUserConfirmation, callback) {
    // TODO: If another transition starts while we're still confirming
    // the previous one, we may end up in a weird state. Figure out the
    // best way to handle this.
    if (prompt != null) {
      var result = typeof prompt === 'function' ? prompt(location, action) : prompt;

      if (typeof result === 'string') {
        if (typeof getUserConfirmation === 'function') {
          getUserConfirmation(result, callback);
        } else {
          (0, _warning2.default)(false, 'A history needs a getUserConfirmation function in order to use a prompt message');

          callback(true);
        }
      } else {
        // Return false from a transition hook to cancel the transition.
        callback(result !== false);
      }
    } else {
      callback(true);
    }
  };

  var listeners = [];

  var appendListener = function appendListener(fn) {
    var isActive = true;

    var listener = function listener() {
      if (isActive) fn.apply(undefined, arguments);
    };

    listeners.push(listener);

    return function () {
      isActive = false;
      listeners = listeners.filter(function (item) {
        return item !== listener;
      });
    };
  };

  var notifyListeners = function notifyListeners() {
    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    listeners.forEach(function (listener) {
      return listener.apply(undefined, args);
    });
  };

  return {
    setPrompt: setPrompt,
    confirmTransitionTo: confirmTransitionTo,
    appendListener: appendListener,
    notifyListeners: notifyListeners
  };
};

exports.default = createTransitionManager;

/***/ }),

/***/ 83:
/*!****************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router-dom/es/Router.js ***!
  \****************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _Router = __webpack_require__(/*! react-router/es/Router */ 84);

var _Router2 = _interopRequireDefault(_Router);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = _Router2.default; // Written in this round about way for babel-transform-imports

/***/ }),

/***/ 84:
/*!************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router/es/Router.js ***!
  \************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

var _invariant = __webpack_require__(/*! invariant */ 8);

var _invariant2 = _interopRequireDefault(_invariant);

var _react = __webpack_require__(/*! react */ 0);

var _react2 = _interopRequireDefault(_react);

var _propTypes = __webpack_require__(/*! prop-types */ 2);

var _propTypes2 = _interopRequireDefault(_propTypes);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }return target;
};

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

function _possibleConstructorReturn(self, call) {
  if (!self) {
    throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
  }return call && ((typeof call === "undefined" ? "undefined" : _typeof(call)) === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
  if (typeof superClass !== "function" && superClass !== null) {
    throw new TypeError("Super expression must either be null or a function, not " + (typeof superClass === "undefined" ? "undefined" : _typeof(superClass)));
  }subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } });if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

/**
 * The public API for putting history on context.
 */

var Router = function (_React$Component) {
  _inherits(Router, _React$Component);

  function Router() {
    var _temp, _this, _ret;

    _classCallCheck(this, Router);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, _React$Component.call.apply(_React$Component, [this].concat(args))), _this), _this.state = {
      match: _this.computeMatch(_this.props.history.location.pathname)
    }, _temp), _possibleConstructorReturn(_this, _ret);
  }

  Router.prototype.getChildContext = function getChildContext() {
    return {
      router: _extends({}, this.context.router, {
        history: this.props.history,
        route: {
          location: this.props.history.location,
          match: this.state.match
        }
      })
    };
  };

  Router.prototype.computeMatch = function computeMatch(pathname) {
    return {
      path: '/',
      url: '/',
      params: {},
      isExact: pathname === '/'
    };
  };

  Router.prototype.componentWillMount = function componentWillMount() {
    var _this2 = this;

    var _props = this.props,
        children = _props.children,
        history = _props.history;

    (0, _invariant2.default)(children == null || _react2.default.Children.count(children) === 1, 'A <Router> may have only one child element');

    // Do this here so we can setState when a <Redirect> changes the
    // location in componentWillMount. This happens e.g. when doing
    // server rendering using a <StaticRouter>.
    this.unlisten = history.listen(function () {
      _this2.setState({
        match: _this2.computeMatch(history.location.pathname)
      });
    });
  };

  Router.prototype.componentWillReceiveProps = function componentWillReceiveProps(nextProps) {
    (0, _warning2.default)(this.props.history === nextProps.history, 'You cannot change <Router history>');
  };

  Router.prototype.componentWillUnmount = function componentWillUnmount() {
    this.unlisten();
  };

  Router.prototype.render = function render() {
    var children = this.props.children;

    return children ? _react2.default.Children.only(children) : null;
  };

  return Router;
}(_react2.default.Component);

Router.propTypes = {
  history: _propTypes2.default.object.isRequired,
  children: _propTypes2.default.node
};
Router.contextTypes = {
  router: _propTypes2.default.object
};
Router.childContextTypes = {
  router: _propTypes2.default.object.isRequired
};

exports.default = Router;

/***/ }),

/***/ 85:
/*!***************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/react-router/es/matchPath.js ***!
  \***************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _pathToRegexp = __webpack_require__(/*! path-to-regexp */ 439);

var _pathToRegexp2 = _interopRequireDefault(_pathToRegexp);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var patternCache = {};
var cacheLimit = 10000;
var cacheCount = 0;

var compilePath = function compilePath(pattern, options) {
  var cacheKey = '' + options.end + options.strict + options.sensitive;
  var cache = patternCache[cacheKey] || (patternCache[cacheKey] = {});

  if (cache[pattern]) return cache[pattern];

  var keys = [];
  var re = (0, _pathToRegexp2.default)(pattern, keys, options);
  var compiledPattern = { re: re, keys: keys };

  if (cacheCount < cacheLimit) {
    cache[pattern] = compiledPattern;
    cacheCount++;
  }

  return compiledPattern;
};

/**
 * Public API for matching a URL pathname to a path pattern.
 */
var matchPath = function matchPath(pathname) {
  var options = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

  if (typeof options === 'string') options = { path: options };

  var _options = options,
      _options$path = _options.path,
      path = _options$path === undefined ? '/' : _options$path,
      _options$exact = _options.exact,
      exact = _options$exact === undefined ? false : _options$exact,
      _options$strict = _options.strict,
      strict = _options$strict === undefined ? false : _options$strict,
      _options$sensitive = _options.sensitive,
      sensitive = _options$sensitive === undefined ? false : _options$sensitive;

  var _compilePath = compilePath(path, { end: exact, strict: strict, sensitive: sensitive }),
      re = _compilePath.re,
      keys = _compilePath.keys;

  var match = re.exec(pathname);

  if (!match) return null;

  var url = match[0],
      values = match.slice(1);

  var isExact = pathname === url;

  if (exact && !isExact) return null;

  return {
    path: path, // the path pattern used to match
    url: path === '/' && url === '' ? '/' : url, // the matched portion of the URL
    isExact: isExact, // whether or not we matched exactly
    params: keys.reduce(function (memo, key, index) {
      memo[key.name] = values[index];
      return memo;
    }, {})
  };
};

exports.default = matchPath;

/***/ }),

/***/ 86:
/*!************************************************************************************!*\
  !*** ./bundles/experiment-page/node_modules/history/es/createTransitionManager.js ***!
  \************************************************************************************/
/*! dynamic exports provided */
/*! all exports used */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _warning = __webpack_require__(/*! warning */ 3);

var _warning2 = _interopRequireDefault(_warning);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var createTransitionManager = function createTransitionManager() {
  var prompt = null;

  var setPrompt = function setPrompt(nextPrompt) {
    (0, _warning2.default)(prompt == null, 'A history supports only one prompt at a time');

    prompt = nextPrompt;

    return function () {
      if (prompt === nextPrompt) prompt = null;
    };
  };

  var confirmTransitionTo = function confirmTransitionTo(location, action, getUserConfirmation, callback) {
    // TODO: If another transition starts while we're still confirming
    // the previous one, we may end up in a weird state. Figure out the
    // best way to handle this.
    if (prompt != null) {
      var result = typeof prompt === 'function' ? prompt(location, action) : prompt;

      if (typeof result === 'string') {
        if (typeof getUserConfirmation === 'function') {
          getUserConfirmation(result, callback);
        } else {
          (0, _warning2.default)(false, 'A history needs a getUserConfirmation function in order to use a prompt message');

          callback(true);
        }
      } else {
        // Return false from a transition hook to cancel the transition.
        callback(result !== false);
      }
    } else {
      callback(true);
    }
  };

  var listeners = [];

  var appendListener = function appendListener(fn) {
    var isActive = true;

    var listener = function listener() {
      if (isActive) fn.apply(undefined, arguments);
    };

    listeners.push(listener);

    return function () {
      isActive = false;
      listeners = listeners.filter(function (item) {
        return item !== listener;
      });
    };
  };

  var notifyListeners = function notifyListeners() {
    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    listeners.forEach(function (listener) {
      return listener.apply(undefined, args);
    });
  };

  return {
    setPrompt: setPrompt,
    confirmTransitionTo: confirmTransitionTo,
    appendListener: appendListener,
    notifyListeners: notifyListeners
  };
};

exports.default = createTransitionManager;

/***/ })

},[425]);
//# sourceMappingURL=experimentPage.bundle.js.map