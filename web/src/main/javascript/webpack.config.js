var webpack = require('webpack');
var path = require('path');

module.exports = {
    cache: true,

    // define the bundles we want
    entry: {
        "expression-atlas-heatmap": './heatmap',
        "internal-atlas-heatmap": './heatmap/internal-atlas-index.js',
        "faceted-search": './faceted-search',
        "vendor": [ 'react', 'jquery', 'jquery.browser', 'URIjs' ]
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
            {test: /\.jsx$/, loader: 'jsx-loader'},
            {test: /\-page\.js$/, loader: 'expose?exposed'},
            {test: /.+index\.js$/, loader: 'expose?exposed'},
            {test: /\.css$/, loader: 'style-loader!css-loader'},
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
