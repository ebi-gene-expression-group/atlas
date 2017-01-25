var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
    entry: {
        contrastTooltips: './index.js',
        dependencies: ['react', 'react-dom', 'jquery', 'jquery-ui-bundle']
    },

    output: {
        libraryTarget: 'var',
        library: '[name]',
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].bundle.js',
        publicPath: '/dist/'
    },

    plugins: [
        new CleanWebpackPlugin(['dist'], {verbose: true, dry: false}),
        new webpack.optimize.DedupePlugin(),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'dependencies',
            filename: 'vendorCommons.bundle.js',
            minChunks: Infinity     // Explicit definition-based split
        })
    ],

    module: {
        loaders: [
            {test: /\.jsx?$/, loader: 'babel', query: {presets: ['es2015', 'react']}},
            {test: /\.css$/, loader: 'style-loader!css-loader'}
        ]
    },

    devServer: {
        port: 9000
    }
};
