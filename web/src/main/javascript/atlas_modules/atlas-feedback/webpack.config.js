var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
    entry: {
        "atlas-feedback": './index.js',
        feedbackRenderer: './html/feedbackRenderer.js',
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
        new CleanWebpackPlugin(['dist'], {verbose: true, dry: false}),
        new webpack.optimize.DedupePlugin(),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'dependencies',
            filename: 'vendor.bundle.js',
            minChunks: Infinity     // Explicit definition-based split. Don’t put shared modules between main and demo entries in vendor.bundle.js
        })
    ],

    module: {
        loaders: [
            {test: /\.jsx$/, loader: 'babel'},
            {test: /\.css$/, loader: 'style-loader!css-loader'}
        ]
    },

    devServer: {
        port: 9000
    }
};
