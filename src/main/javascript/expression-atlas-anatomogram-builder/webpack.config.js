var path = require('path');
var webpack = require('webpack');

module.exports = Object.assign(require('../webpack.config.js'), {
    entry: {
        anatomogramBuilder: './index.js',
        dependencies: ['react', 'react-dom', 'anatomogram']
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
        }),
        new webpack.DefinePlugin({
            "process.env": {
                NODE_ENV: process.env.NODE_ENV === 'production' ? JSON.stringify("production") : JSON.stringify("development")
            }
        })
    ],
    module: {
        loaders: [
            {test: /\.js$/, loader: 'babel', query: {presets: ['es2015']}},
            {test: /\.jsx$/, loader: 'babel', query: {presets: ['es2015', 'react']}},
            {test: /\.css$/, loader: 'style-loader!css-loader'},
            {test: /\.less$/, loader: 'style-loader!css-loader!less-loader'},
            {test: /\.json$/, loader: 'json'},
            {test: /\.(jpe?g|png|gif)$/i,
                loaders: [
                    'file?hash=sha512&digest=hex&name=[hash].[ext]',
                    'image-webpack?bypassOnDebug&optimizationLevel=7&interlaced=false'
                ]
            },
            {test: /\.(svg)$/i,
                loaders: [
                    'file?hash=sha512&digest=hex&name=[hash].[ext]'
                ]
            }

        ]
    }
});
