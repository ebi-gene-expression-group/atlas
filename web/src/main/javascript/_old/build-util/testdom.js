// add jsdom if we aren't in a browser
// from https://github.com/danvk/mocha-react/blob/master/tests/testdom.js
module.exports = function(markup) {
    if (typeof document !== 'undefined') return;
    var jsdom = require("jsdom").jsdom;
    global.document = jsdom(markup || '');
    global.window = document.defaultView;
    global.navigator = {
        userAgent: 'node.js'
    };
    // ... add whatever browser globals your tests might need ...
};