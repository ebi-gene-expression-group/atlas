var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = Object.assign(require('../webpack.config.js'), {
    entry: {
        expressionAtlasHeatmap: './index.js',
        heatmap: './src/experimentPageHeatmapAnatomogramRenderer.js',
        dependencies: ['react', 'react-dom', 'react-radio-group', 'react-bootstrap',
                       'jquery', 'jquery-ui-bundle', 'jquery.browser', 'fancybox', 'jquery-hc-sticky', 'jquery-toolbar', 'jquery-hc-sticky',
                       'urijs', 'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js', 'atlas-modernizr',
                       'highcharts-more', 'react-highcharts',
                       'events', 'rc-slider']
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
