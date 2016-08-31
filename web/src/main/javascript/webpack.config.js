var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
    // define the bundles we want
    entry: {
        expressionAtlasHeatmap: './expression-atlas-heatmap',
        expressionAtlasHeatmapHighcharts:'./expression-atlas-heatmap-highcharts',
        experimentPageHeatmap: './expression-atlas-heatmap/src/experimentPageHeatmapAnatomogramRenderer.js',
        expressionAtlasBaselineExpression: './expression-atlas-baseline-expression',
        expressionAtlasDifferentialExpression: './expression-atlas-differential-expression',
        dependencies: ['react', 'react-dom', 'react-radio-group','react-prop-types-check',
                       'react-bootstrap/lib/DropdownButton', 'react-bootstrap/lib/MenuItem', 'react-bootstrap/lib/FormGroup',
                       'react-bootstrap/lib/FormControl', 'react-bootstrap', 'react-bootstrap/lib/Button',
                       'rc-slider',
                       'jquery', 'jquery-ui-bundle', 'jquery.browser', 'jquery-hc-sticky', 'fancybox', 'jquery-toolbar',
                       'urijs', 'query-string', 'atlas-modernizr',
                       'events', 'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js',
                       'highcharts-more', 'react-highcharts', 'react-addons-css-transition-group', 'react-emojione',
                       'react-localstorage', 'react-timer-mixin', 'lodash', 'highcharts-heatmap', 'rc-slider/assets/index.css', 'fancybox/dist/css/jquery.fancybox.css',
                       'jquery-toolbar/jquery.toolbar.css']
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
            {test: /\.jsx?$/, loader: 'babel', query: {presets: ['es2015', 'react']}},
            {test: /\.css$/, loader: 'style-loader!css-loader'},
            {test: /\.less$/, loader: 'style-loader!css-loader!less-loader'},
            {test: /\.json$/, loader: 'json'},
            {test: /\.(jpe?g|png|gif|svg)$/i,
                loaders: [
                          'file?hash=sha512&digest=hex&name=[hash].[ext]',
                          'image-webpack?bypassOnDebug&optimizationLevel=7&interlaced=false'
                ]
            }
        ]
    },
};
