'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _url = require('url');

var _url2 = _interopRequireDefault(_url);

var _querystring = require('querystring');

var _querystring2 = _interopRequireDefault(_querystring);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * Stringify the `query` object, assign it to the `ds` search field in the URL and store it in the History
 * @param {object} querySelect
 * @param {boolean} replace - use `replaceState` instead of `pushState`
 */
var differentialPush = function pushQueryIntoBrowserHistory(querySelect, replace) {
    var currentUrlObject = _url2.default.parse(window.location.toString());

    var newUrlQueryParams = _querystring2.default.parse(currentUrlObject.query);
    newUrlQueryParams.ds = JSON.stringify(querySelect);

    var newUrlObject = {
        protocol: currentUrlObject.protocol,
        host: currentUrlObject.host,
        hash: currentUrlObject.hash,
        pathname: currentUrlObject.pathname,
        query: newUrlQueryParams
    };

    if (replace) {
        history.replaceState(null, '', _url2.default.format(newUrlObject));
    } else {
        history.pushState(null, '', _url2.default.format(newUrlObject));
    }
};

var parseDifferentialUrlParameter = function getQuerySelectFromLocation() {
    var location = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : window.location;

    var currentURL = _url2.default.parse(location.toString());
    var differentialSelectParam = _querystring2.default.parse(currentURL.query).ds;
    return differentialSelectParam ? JSON.parse(differentialSelectParam) : {};
};

var UrlManager = {
    differentialPush: differentialPush,
    parseDifferentialUrlParameter: parseDifferentialUrlParameter
};

exports.default = UrlManager;