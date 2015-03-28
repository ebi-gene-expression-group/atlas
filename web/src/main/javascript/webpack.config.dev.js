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
        // to run tests open http://localhost:9000/webpack-dev-server/specs
        specs: [  "./build-util/install-source-map-support.js"].concat(mochaloader(testfiles)),
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
