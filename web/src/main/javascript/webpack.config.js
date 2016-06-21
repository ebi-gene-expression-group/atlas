var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
    // define the bundles we want
    entry: {
        expressionAtlasAnatomogram: './expression-atlas-anatomogram',
        expressionAtlasHeatmap: './expression-atlas-heatmap',
        expressionAtlasHeatmapHighcharts:'./expression-atlas-heatmap-highcharts',
        experimentPageHeatmap: './expression-atlas-heatmap/src/experimentPageHeatmapAnatomogramRenderer.js',
        facetedSearch: './faceted-search',
        dependencies: ['react', 'react-dom', 'react-radio-group', 'react-bootstrap', 'rc-slider',
                       'jquery', 'jquery-ui-bundle', 'jquery.browser', 'jQuery-ajaxTransport-XDomainRequest', 'jquery-hc-sticky', 'fancybox', 'jquery-toolbar',
                       'urijs', 'query-string', 'atlas-modernizr',
                       'events', 'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js',
                       'highcharts-more', 'react-highcharts']
    },

    output: {
        libraryTarget: 'var',
        library: '[name]',
        path: path.resolve(__dirname, '../webapp/resources/js-bundles'),
        filename: '[name].bundle.js'
    },

    plugins: [
        new CleanWebpackPlugin(['webapp/resources/js-bundles'], {
	        root: path.resolve(__dirname , '..'),
            verbose: true,
            dry: false
        }),
        new webpack.optimize.DedupePlugin(),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'dependencies',
            filename: 'vendorCommons.bundle.js',
            minChunks: Infinity     // Explicit definition-based split. Don’t put shared modules between main and demo entries in vendor.bundle.js
        })
    ],

    module: {
        loaders: [
            {test: /\.jsx$/, loader: 'babel-loader'},
            {test: /\.css$/, loader: 'style-loader!css-loader'},
            {test: /\.(jpe?g|png|gif|svg)$/i,
                loaders: [
                          'file?hash=sha512&digest=hex&name=[hash].[ext]',
                          'image-webpack?bypassOnDebug&optimizationLevel=7&interlaced=false'
                ]
            }
        ]
    },
};
