var webpack = require("webpack");
var path = require("path");
var globule = require("globule");

function mochaloader(files) { return files.map(function (path) {return 'mocha!./' + path;}) }

var testfiles = globule.find('test/**/*-test.js');

console.log("Bundling test files " + testfiles);

module.exports = {
    cache: true,
    devtool: 'inline-source-map',

    entry: {
        // to run tests open http://localhost:9000/webpack-dev-server/testpages/mocha.html
        tests: [  "./build-util/install-source-map-support.js"].concat(mochaloader(testfiles))
        ,"search-results-differential-page": "./src/search-results/differential-page.js"
        ,vendor: ["jquery", "react", "react-addons", "chai", "source-map-support"]
    },

    output: {
        filename: '[name]-bundle.js' // [name] is substituted for the entry name, eg: tests-bundle.js.
    },

    module: {
        loaders: [
            {test: /\.jsx$/, loader: 'jsx-loader'}
            ,{test: /\-page.js$/, loader: 'expose?$page'}
        ]
    },

    plugins: [
        new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"vendor-bundle.js")
        ,new webpack.IgnorePlugin(/jsdom$/)
    ],

    node: {
        fs: "empty"     // required for install-source-map-support.js
    }
};
