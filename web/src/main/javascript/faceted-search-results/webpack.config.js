var webpack = require('webpack');
var path = require('path');

module.exports = {
    context: __dirname,
    entry: {
        "atlas-heatmap": "./index.js",
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
        "vendor": ['react', 'react-radio-group', 'jquery', 'jquery-ui']
    },
    output: {
        path: __dirname + "/dist",
        publicPath: "/dev-server/",
        filename: "[name].bundle.js"
    },

    plugins: [
        new webpack.HotModuleReplacementPlugin(),
        new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"vendor", /* filename= */"vendor.bundle.js")
    ],

    module: {
        loaders: [
            {test: /\.jsx?$/, loaders: ['react-hot', "jsx?harmony"], include: path.join(__dirname, "src")},
            {test: /\.jsx$/, loader: 'jsx-loader'},
            {test: /demo.js$/, loader: 'expose?exposed'},
            {test: /\.css$/, loader: 'style-loader!css-loader'}
        ]
    },

    devServer: {
        port: 9000
    }
};
