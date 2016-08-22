"use strict";

var url = require('url');
var querystring = require('querystring');

/**
 * Stringify the `query` object, assign it to the `ds` search field in the URL and store it in the History
 * @param {boolean} replace - use `replaceState` instead of `pushState`
 */
exports.differential = function pushQueryIntoBrowserHistory(querySelect, replace) {
    var currentUrlObject = url.parse(window.location.toString());

    var newUrlQueryParams = querystring.parse(currentUrlObject.query);
    newUrlQueryParams.ds = JSON.stringify(querySelect);

    var newUrlObject = {
        protocol: currentUrlObject.protocol,
        host: currentUrlObject.host,
        hash: currentUrlObject.hash,
        pathname: currentUrlObject.pathname,
        query: newUrlQueryParams
    };

    if (replace) {
        history.replaceState(null, "", url.format(newUrlObject));
    } else {
        history.pushState(null, "", url.format(newUrlObject));
    }
}
