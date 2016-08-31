var webpack = require('webpack');
var path = require('path');

module.exports = {
    context: __dirname,
    entry: {
	    simpleComponent: './index.js',
        demo: [
            'webpack-dev-server/client?http://localhost:9000', // WebpackDevServer host and port
            'webpack/hot/only-dev-server',
            './html/demo.js'
        ],
        test: [
            'webpack-dev-server/client?http://localhost:9000', // WebpackDevServer host and port
            'webpack/hot/only-dev-server',
            'mocha!./test/test.js'
        ],
        vendor: ['react']
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
        }),
        new webpack.HotModuleReplacementPlugin(),
        new webpack.IgnorePlugin(/jsdom$/)
    ],

    module: {
        loaders: [
            {test: /\.jsx?$/, loader: 'babel', query: {presets: ['es2015', 'react']}},
            {test: /\.css$/, loader: 'style-loader!css-loader'},
            {test: /\.(jpe?g|png|gif|svg)$/i,
                loaders: [
                    'file?hash=sha512&digest=hex&name=[hash].[ext]',
                    'image-webpack?bypassOnDebug&optimizationLevel=7&interlaced=false'
                ]
            }
        ]
    },

    devServer: {
        port: 9000
    }
};
