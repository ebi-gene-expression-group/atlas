var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');
var WebpackShellPlugin = require('webpack-shell-plugin');

module.exports = {
    // define the bundles we want
    entry: {
        expressionAtlasHeatmapHighcharts: './atlas_bundles/heatmap-highcharts',
        experimentPage: './atlas_bundles/experiment-page',
        expressionAtlasBaselineExpression: './atlas_bundles/baseline-expression',
        expressionAtlasDifferentialExpression: './atlas_bundles/differential-expression',
        expressionAtlasBioentityInformation: './atlas_bundles/bioentity-information',
        expressionAtlasBrowseBySpecies: './atlas_bundles/browse-by-species',
        dependencies: [
            'babel-polyfill', 'whatwg-fetch',

            // Bundled dependencies: expressionAtlasHeatmapHighcharts
            'expression-atlas-heatmap-highcharts',
            'anatomogram',
            'expression-atlas-feedback',
            'react-refetch',
            'react-ebi-species',

            // Required by: expressionAtlasHeatmapHighcharts
            'color',
            'downloadjs',
            'expression-atlas-number-format',
            'he',
            'highcharts',
            'highcharts-custom-events',
            'jquery',
            'lodash',
            'object-hash',
            'rc-slider',
            'react',
            'react-bootstrap',
            'react-dom',
            'react-highcharts',
            'react-tooltip',
            'urijs',

            // Required by: anatomogram
            'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js',

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
        ]
    },

    resolve: {
        alias: {
            "react": path.resolve('./node_modules/react'),
            "react-dom": path.resolve('./node_modules/react-dom')
        },
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
            {test: /\.js$/, loader: 'babel', query: {presets: ['es2015'], plugins:['transform-object-rest-spread']}, exclude: /node_modules\/(?!(expression-atlas|anatomogram|react-ebi-species))/},
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
