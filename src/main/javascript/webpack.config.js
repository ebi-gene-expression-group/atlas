var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
    // define the bundles we want
    entry: {
        expressionAtlasHeatmap: ['babel-polyfill', './atlas_bundles/heatmap'],
        expressionAtlasHeatmapHighcharts: ['babel-polyfill', './atlas_bundles/heatmap-highcharts'],
        experimentPageHeatmap: ['babel-polyfill', './atlas_bundles/heatmap/src/experimentPageHeatmapAnatomogramRenderer.js'],
        expressionAtlasBaselineExpression: ['babel-polyfill', './atlas_bundles/baseline-expression'],
        expressionAtlasDifferentialExpression: ['babel-polyfill', './atlas_bundles/differential-expression'],
        expressionAtlasBioentityInformation: ['babel-polyfill', './atlas_bundles/bioentity-information'],
        dependencies: ['react', 'react-dom', 'react-radio-group','react-prop-types-check',
                       'react-bootstrap/lib/DropdownButton', 'react-bootstrap/lib/MenuItem', 'react-bootstrap/lib/FormGroup',
                       'react-bootstrap/lib/FormControl', 'react-bootstrap', 'react-bootstrap/lib/Button',
                       'rc-slider',
                       'anatomogram',
                       'jquery', 'jquery-ui-bundle', 'jquery.browser', 'jquery-hc-sticky', 'fancybox', 'jquery-toolbar',
                       'urijs', 'query-string', 'atlas-modernizr',
                       'events', 'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js',
                       'highcharts-more','react-highcharts', 'react-addons-css-transition-group', 'react-emojione',
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
        }),
        new webpack.DefinePlugin({
            "process.env": {
                NODE_ENV: process.env.NODE_ENV === 'production' ? JSON.stringify("production") : JSON.stringify("development")
            }
        })
    ],

    module: {
        loaders: [
            {test: /\.js$/, loader: 'babel', query: {presets: ['es2015']}, exclude: /node_modules\/(?!(expression-atlas|anatomogram|react-ebi-species))/},
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
    },
    
    devServer: {
      port: 9000
    }
};
