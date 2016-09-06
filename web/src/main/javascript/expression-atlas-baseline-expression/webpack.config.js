var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = Object.assign(require('../webpack.config.js'), {
    entry: {
      baselineExpression: './index.js',
      dependencies: ['react', 'react-dom', 'events', 'url', 'querystring', 'jquery', 'jquery.browser'
                     // 'jquery', 'jquery-ui-bundle', 'jquery.browser', 'jquery-hc-sticky', 'fancybox', 'jquery-toolbar',
                     // 'urijs', 'query-string', 'atlas-modernizr',
                     // 'events', 'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js',
                     // 'highcharts-more', 'react-highcharts'
      ]
    },

    output: {
        libraryTarget: 'var',
        library: '[name]',
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].bundle.js',
        publicPath: '/dist/'
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
    ]
});
