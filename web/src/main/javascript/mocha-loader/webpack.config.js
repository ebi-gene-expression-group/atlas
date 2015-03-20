module.exports = {
    devtool: 'inline-source-map',
    entry: ["mocha!./NumberSpec.js", "mocha!./StringSpec.js"],
    output: {
        filename: 'test_bundle.js'
    }
};