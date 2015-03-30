var webpack = require("webpack");
var path = require("path");

module.exports = {
    cache: true,
    devtool: 'source-map',

    // define the bundles we want
    entry: {
        "search-results-differential-page": "./src/search-results/differential-page.js"
        ,vendor: ["jquery", "react"]
    },

    output: {
        path: path.resolve(__dirname, "../webapp/resources/js-bundles")
        ,filename: '[name]-bundle.js' // [name] is substituted for the entry name, eg: search-results-differential-page-bundle.js

    },

    plugins: [
        new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"vendor-bundle.js", ["vendor", "search-results-differential-page"])
    ],

    module: {
        loaders: [
            {test: /\.jsx$/, loader: 'jsx-loader'}
        ]
    }
};
