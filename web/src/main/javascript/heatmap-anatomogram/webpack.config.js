var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
    entry: {
        heatmapAnatomogram: './index.js',
        internalHeatmapAnatomogram: './src/internal-heatmap-anatomogram.js',
        heatmapAnatomogramRenderer: './html/heatmapAnatomogramRenderer.js',
        dependencies: ['react', 'react-dom', 'react-radio-group', 'react-bootstrap',
                       'jquery', 'jquery-ui-bundle', 'jquery.browser', 'fancybox', 'jquery-hc-sticky', 'jquery-toolbar', 'jQuery-ajaxTransport-XDomainRequest', 'jquery-hc-sticky',
                       'urijs', 'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js', 'atlas-modernizr',
                       'highcharts-more', 'react-highcharts',
                       'events']
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
            {test: /\.css$/, loader: 'style-loader!css-loader'},
            {
                test: /\.(jpe?g|png|gif|svg)$/i,
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
