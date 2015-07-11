var webpack = require("webpack");

module.exports = {
    context: __dirname,
    entry: {
        //"heatmap-baseline-cell-variance": "./index.js",
        "app": "./index.js",
        "vendor": ["react"]
    },
    output: {
        path: __dirname + "/dist",
        filename: "bundle.js"
    },

    plugins: [
        new webpack.optimize.CommonsChunkPlugin(/* chunkName= */"vendor", /* filename= */"vendor.bundle.js")
    ],

    module: {
        loaders: [
            {test: /\.jsx$/, loader: 'jsx-loader'},
            {test: /index.js$/, loader: 'expose?exposed'}
        ]
    },

    devServer: {
        port: 9090
    }
};
