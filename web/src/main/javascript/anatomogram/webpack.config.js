var webpack = require('webpack');
var path = require('path');

module.exports = {
    context: __dirname,
    entry: {
	    "anatomogram": "./index.js",
        "demo": [
            "webpack-dev-server/client?http://localhost:9000", // WebpackDevServer host and port
            "webpack/hot/only-dev-server",
            "./html/demo.js"
        ],
        "test": [
            "webpack-dev-server/client?http://localhost:9000", // WebpackDevServer host and port
            "webpack/hot/only-dev-server",
            'mocha!./test/test.js'
        ],
        "vendor": ['react', 'jquery', 'jquery-ui', 'wolfy87-eventemitter', 'snapsvg']
    },
    output: {
        path: __dirname + "/dist",
	    publicPath: "/dev-server/",
        filename: "[name].bundle.js"
    },

    plugins: [
        new webpack.HotModuleReplacementPlugin(),
        new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"vendor", /* filename= */"vendor.bundle.js"),
        new webpack.optimize.DedupePlugin(),
        new webpack.IgnorePlugin(/jsdom$/)
    ],

    module: {
        loaders: [

            {test: /\.jsx$/, loader: 'babel-loader'},
            {test: /demo.js$/, loader: 'expose?exposed'},
            {test: /index.js$/, loader: 'expose?exposed'},
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
