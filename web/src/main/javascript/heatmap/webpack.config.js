var webpack = require("webpack");

module.exports = {
    context: __dirname,
    entry: {
        "atlas-heatmap": "./index.js",
        "demo" : "./html/demo.js",
        "vendor": ["react"]
    },
    output: {
        path: __dirname + "/dist",
        publicPath: "/dev-server/",
        filename: "[name].bundle.js"
    },

    plugins: [
        new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"vendor", /* filename= */"vendor.bundle.js")
    ],

    module: {
        loaders: [
            {test: /\.jsx$/, loader: 'jsx-loader'},
            {test: /demo.js$/, loader: 'expose?exposed'}
        ]
    },

    devServer: {
        port: 9090
    }
};
