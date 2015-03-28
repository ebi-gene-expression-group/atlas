var webpack = require("webpack");
var path = require("path");

module.exports = {
    cache: true,
    devtool: 'inline-source-map',

    entry: {
        // to run tests open http://localhost:9000/webpack-dev-server/specs
        specs: [  "./build-util/install-source-map-support.js"
                    , "mocha!./test/heatmap/heatmap-test.js"
                    , "mocha!./test/search-results/differential-facets-test.js"],
        vendor: ["jquery", "react", "react-addons"]
    },

    output: {
        filename: '[name].js' // [name] is substituted for the entry name, eg: specs.js.
    },

    module: {
        loaders: [
            {test: /\.jsx$/, loader: 'jsx-loader'}
        ]
    },

    plugins: [
        new webpack.IgnorePlugin(/jsdom$/)
    ],

    node: {
        fs: "empty"     // required for install-source-map-support.js
    }
};
