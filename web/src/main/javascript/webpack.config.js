var webpack = require('webpack');
var path = require('path');

module.exports = {
    cache: true,

    // define the bundles we want
    entry: {
        "anatomogram": './anatomogram/anatomogram-builder-index.js',
        "expression-atlas-heatmap": './heatmap-anatomogram',
        "internal-atlas-heatmap": './heatmap-anatomogram/internal-atlas-index.js',
        "faceted-search": './faceted-search',
        "vendor": ['react','react-dom', 'jquery', 'jquery-ui', 'jquery.browser', 'urijs' ]
    },
 
    output: {
        path: path.resolve(__dirname, "../webapp/resources/js-bundles"),
        filename: "[name].bundle.js" // [name] is substituted for the entry name, eg: search-results-differential-page-bundle.js

    },

    plugins: [
        new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"vendor", /* filename= */"vendor.bundle.js"),
        new webpack.optimize.DedupePlugin()
    ],

    module: {
        loaders: [
            {test: /\.jsx$/, loader: 'babel-loader'},
            {test: /.*index\.js$/, loader: 'expose?exposed'},
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
