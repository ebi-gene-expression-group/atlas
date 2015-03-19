var webpack = require("webpack");
var path = require("path");

module.exports = {
    cache: true,
    devtool: 'inline-source-map',

    // define the bundles we want
    entry: {
        "search-results-differential-page": "./search-results-differential/page.js"
        ,"search-results-differential-specs": "./search-results-differential/test/specs.js"
        ,vendor: ["jquery", "react"]
        ,"specs-vendor": ["jquery", "react", "react-addons"]
    },

    output: {
        path: path.resolve(__dirname, "../webapp/resources/js-bundles")
        ,filename: '[name]-bundle.js' // [name] is substituted for the entry name, eg: specs-bundle.js

    },

    plugins: [
        new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"vendor-bundle.js", ["vendor", "search-results-differential-page"])
        //,new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"specs", /* filename= */"specs-bundle.js")
        ,new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"specs-vendor-bundle.js", ["specs-vendor", "search-results-differential-specs"])
    ],

    module: {
        loaders: [
            {test: /\.jsx$/, loader: 'jsx-loader'}
        ]
    }
};
