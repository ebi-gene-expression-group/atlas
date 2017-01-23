var webpack = require('webpack');
var path = require('path');

module.exports = Object.assign(require('../../webpack.config.js'), {
    entry: {
      // numberFormat: './index.js',
        numberFormatRenderer: './html/numberFormatRenderer.jsx',
        dependencies: ['react', 'react-dom']
    },

    output: {
        libraryTarget: 'var',
        library: '[name]',
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].bundle.js',
        publicPath: '/dist/'
    },
    plugins: [
        new webpack.optimize.DedupePlugin(),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'dependencies',
            filename: 'vendorCommons.bundle.js',
            minChunks: Infinity     // Explicit definition-based split. Don’t put shared modules between main and demo entries in vendor.bundle.js
        })
    ]
});
