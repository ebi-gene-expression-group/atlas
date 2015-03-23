var webpack = require('webpack');

module.exports = {
    devtool: 'inline-source-map',
    entry: ["./install.js", "mocha!./NumberSpec.js", "./StringSpec.js"],
    output: {
        filename: 'test_bundle.js'
    }
};