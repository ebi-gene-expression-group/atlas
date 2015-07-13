var webpack = require("webpack");
var path = require("path");

module.exports = {
    cache: true,

    // define the bundles we want
    entry: {
        "expression-atlas-heatmap": "./heatmap",
        "faceted-search-results": "./faceted-search-results",
        "vendor": ["react", "jquery"]
    },

    output: {
        path: path.resolve(__dirname, "../webapp/resources/js-bundles"),
        filename: '[name]-bundle.js' // [name] is substituted for the entry name, eg: search-results-differential-page-bundle.js

    },

    plugins: [
        new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"vendor", /* filename= */"vendor-bundle.js")
    ],

    module: {
        loaders: [
            {test: /\.jsx$/, loader: 'jsx-loader'},
            {test: /\-page.js$/, loader: 'expose?exposed'},
            {test: /index.js$/, loader: 'expose?exposed'}
        ]
    },

    devServer: {
        port: 9000
    }
};
