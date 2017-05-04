var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
    output: {
        libraryTarget: 'var',
        library: '[name]',
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].bundle.js',
        publicPath: '/dist/'
    },

    plugins: [
        new CleanWebpackPlugin(['dist'], {verbose: true, dry: false}),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'dependencies',
            filename: 'vendorCommons.bundle.js',
            minChunks: Infinity     // Explicit definition-based split. Don’t put shared modules between main and demo
        })                          // entries in vendor.bundle.js

    ],

    module: {
        loaders: [
            {test: /\.js$/, loader: 'babel', query: {presets: ['es2015'], plugins:['transform-object-rest-spread']},
                // Place here all the packages that we own
                exclude: /node_modules\/(?!(expression-atlas|anatomogram|react-ebi-species))/},
            {test: /\.jsx$/, loader: 'babel', query: {presets: ['es2015', 'react'], plugins:['transform-object-rest-spread']}},
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
    },
    
    devServer: {
      port: 9000
    }
};
