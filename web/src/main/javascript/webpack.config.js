var webpack = require("webpack");
var path = require("path");

module.exports = {
    cache: true,
    devtool: 'source-map',

    // define the bundles we want
    entry: {
        "search-results-differential-page": "./src/search-results/differential-page.js"
        ,"search-results-baseline-page": "./src/search-results/baseline-page.js"
        ,"atlas_heatmap-page": "./src/heatmap/atlasHeatmap-page.js"
        ,"webpack_example_with_plain_JS_scriptA-page": "./src/webpack_example/with_plain_JS/scriptA-page.js"
        ,"webpack_example_with_JS_2_modules-page": "./src/webpack_example/with_plain_JS/moduleC-page.js"
        ,"webpack_example_with_jQuery-page": "./src/webpack_example/with_plain_JS/jQueryModule-page.js"
        ,"webpack_example_with_JSX-page": "./src/webpack_example/with_JSX/component-page.js"
        ,"webpack_example_with_highcharts-page": "./src/webpack_example/highcharts/highcharts-page.js"
        ,"vendor": ["jquery", "react"]
    },

    output: {
        path: path.resolve(__dirname, "../webapp/resources/js-bundles")
        ,filename: '[name]-bundle.js' // [name] is substituted for the entry name, eg: search-results-differential-page-bundle.js

    },

    plugins: [
        new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"vendor-bundle.js")
    ],

    module: {
        loaders: [
            {test: /\.jsx$/, loader: 'jsx-loader'}
            ,{test: /\-page.js$/, loader: 'expose?$page'}
        ]
    }
};
