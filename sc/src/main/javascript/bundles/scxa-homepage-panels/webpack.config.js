const CleanWebpackPlugin = require(`clean-webpack-plugin`)
const path = require(`path`)

const commonPublicPath = `/dist/`
const vendorsBundleName = `vendors`

module.exports = {
    entry: {
        HomePagePanel: [`@babel/polyfill`, `./index.js`]
    },

    plugins: [
        new CleanWebpackPlugin([`dist`])
    ],

    output: {
        library: `[name]`,
        path: path.resolve(__dirname, `dist`),
        filename: `[name].bundle.js`,
        publicPath: `/html/`
    },
    optimization: {
        runtimeChunk: {
            name: vendorsBundleName
        },
        splitChunks: {
            cacheGroups: {
                commons: {
                    test: /[\\/]node_modules[\\/]/,
                    name: vendorsBundleName,
                    chunks: 'all'
                }
            }
        }
    },

    module: {
        rules: [
            {
                test: /\.js$/i,
                exclude: /node_modules\//,
                use: `babel-loader`
            }
        ]
    },

    devServer: {
        port: 9000,
        contentBase: path.resolve(__dirname, `html`),
        publicPath: commonPublicPath
    }
}
