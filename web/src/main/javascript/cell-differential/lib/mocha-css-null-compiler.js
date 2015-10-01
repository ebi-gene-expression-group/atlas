// http://stackoverflow.com/questions/32683440/handle-webpack-css-imports-when-testing-with-mocha

function noop() {
    return null;
}

require.extensions['.css'] = noop;
require.extensions['.gif'] = noop;
require.extensions['.png'] = noop;
require.extensions['.jpg'] = noop;
require.extensions['.jpeg'] = noop;
require.extensions['.svg'] = noop;