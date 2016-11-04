const Url = require('url');
const QueryString = require('querystring');

/**
 * Stringify the `query` object, assign it to the `ds` search field in the URL and store it in the History
 * @param {object} querySelect
 * @param {boolean} replace - use `replaceState` instead of `pushState`
 */
exports.differentialPush = function pushQueryIntoBrowserHistory(querySelect, replace) {
    let currentUrlObject = Url.parse(window.location.toString());

    let newUrlQueryParams = QueryString.parse(currentUrlObject.query);
    newUrlQueryParams.ds = JSON.stringify(querySelect);

    let newUrlObject = {
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

exports.parseDifferentialUrlParameter = function getQuerySelectFromLocation(location = window.location) {
    let currentURL = Url.parse(location.toString());
    let differentialSelectParam = QueryString.parse(currentURL.query).ds;
    return differentialSelectParam ? JSON.parse(differentialSelectParam) : {};
};



