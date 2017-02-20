var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');
var WebpackShellPlugin = require('webpack-shell-plugin');

module.exports = {
    // define the bundles we want
    entry: {
        expressionAtlasHeatmap: ['babel-polyfill', 'whatwg-fetch', './atlas_bundles/heatmap'],
        expressionAtlasHeatmapHighcharts: ['babel-polyfill', './atlas_bundles/heatmap-highcharts'],
        experimentPageHeatmap: ['babel-polyfill', './atlas_bundles/heatmap/src/experimentPageHeatmapAnatomogramRenderer.js'],
        experimentPage: ['babel-polyfill', './atlas_bundles/experiment-page'],
        expressionAtlasBaselineExpression: ['babel-polyfill', './atlas_bundles/baseline-expression'],
        expressionAtlasDifferentialExpression: ['babel-polyfill', './atlas_bundles/differential-expression'],
        expressionAtlasBioentityInformation: ['babel-polyfill', './atlas_bundles/bioentity-information'],
        dependencies: [
            // Bundled dependencies: expressionAtlasHeatmapHighcharts, heatmap
            'anatomogram',
            // 'expression-atlas-number-format',
            // 'atlas-modernizr',
            // 'expression-atlas-cell-differential',
            // 'expression-atlas-contrast-tooltips',
            // 'expression-atlas-display-levels-button',
            // 'expression-atlas-download-profiles-button',
            'expression-atlas-feedback',
            // 'expression-atlas-genome-browser-launcher',
            // 'expression-atlas-heatmap-baseline-cell-variance',
            // 'expression-atlas-help-tooltips',
            // 'expression-atlas-legend',
            // 'expression-atlas-number-format',
            'babel-polyfill',
            // Required by: expressionAtlasHeatmapHighcharts

            'color',
            'downloadjs',
            'he',
            'highcharts-custom-events',
            'highcharts-heatmap',
            'jquery',
            'lodash',
            'object-hash',
            'rc-slider',
            'react',
            'react-bootstrap',
            'react-dom',
            'react-highcharts',
            'react-tooltip',

            // Required by: anatomogram
            'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js',

            // Required by: heatmap
            // Core dependencies
            // 'anatomogram',
            // 'atlas-modernizr',
            // 'expression-atlas-cell-differential',
            // 'expression-atlas-contrast-tooltips',
            // 'expression-atlas-display-levels-button',
            // 'expression-atlas-download-profiles-button',
            // 'expression-atlas-feedback',
            // 'expression-atlas-genome-browser-launcher',
            // 'expression-atlas-heatmap-baseline-cell-variance',
            // 'expression-atlas-help-tooltips',
            // 'expression-atlas-legend',
            // 'expression-atlas-number-format',
            'fancybox',
            // 'jquery',
            'jquery-hc-sticky',
            'jquery-toolbar',
            'jquery-ui-bundle',
            'jquery.browser',
            // 'rc-slider',
            // 'react',
            'react-addons-shallow-compare',
            // 'react-dom',
            'react-dom/server',
            'react-radio-group',
            'urijs',
            'events',

            // anatomogram
            // 'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js',

            // Node.js stuff
            'url',
            'path',
            'events',

            // feedback
            'react-addons-css-transition-group',
            // 'react-bootstrap',
            'react-emojione',
            'react-localstorage',
            'react-timer-mixin',

            // genome-browser-launcher
            'react-refetch',
            'whatwg-fetch',

            // baseline-cell
            // 'react-highcharts',
            'highcharts-more',
            'react-addons-css-transition-group',

            //
            'expression-atlas-heatmap-highcharts',
            'react-ebi-species'
        ]
    },

    output: {
        libraryTarget: 'var',
        library: '[name]',
        path: path.resolve(__dirname, '../webapp/resources/js-bundles'),
        filename: '[name].bundle.js',
        publicPath: '/gxa/resources/js-bundles/'
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
        }),
        // Beacuse copy-webpack-plugin doesn’t wait for WebPack to finish, and the output dir is cleaned above
        new WebpackShellPlugin({
            onBuildStart: ['echo Cleaning versioned-resouces...', 'rm -rf ../webapp/versioned-resources/js-bundles', 'echo Done!'],
            onBuildEnd: ['echo Populating versioned-resources...', 'cp -a ../webapp/resources/js-bundles ../webapp/versioned-resources', 'echo Done!']
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
