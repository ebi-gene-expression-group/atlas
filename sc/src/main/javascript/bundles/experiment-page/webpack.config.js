const webpack = require(`webpack`)
const path = require(`path`)
const CleanWebpackPlugin = require(`clean-webpack-plugin`)

config = {
    entry: {
        experimentPage: [`whatwg-fetch`, `babel-polyfill`, `./src/index.js`],
        dependencies: [`prop-types`, `react`, `react-dom`, `react-router-dom`]
    },

    output: {
        library: `[name]`,
        path: path.resolve(__dirname, `dist`),
        filename: `[name].bundle.js`,
        publicPath: `/gxa/sc/`
    },

    plugins: [
        new CleanWebpackPlugin([`dist`], {verbose: true, dry: false}),
        new webpack.optimize.CommonsChunkPlugin({
            name: `dependencies`,
            filename: `vendorCommons.bundle.js`,
            minChunks: Infinity    // Explicit definition-based split, see dependencies entry
        })
    ],

    module: {
        rules: [
            {
                test: /\.css$/i,
                // exclude: /node_modules\//,
                use: [
                    {loader: 'style-loader'},
                    {
                      loader: 'css-loader',
                      options: {
                        modules: true,
                        localIdentName: '[name]__[local]___[hash:base64:5]'
                      }
                    }
                ]
            },
            {
                test: /\.less$/i,
                use: [`style-loader`, `css-loader`, `less-loader`]
            },
            {
                test: /\.(jpe?g|png|gif)$/i,
                use: [
                    {
                        loader: `file-loader`,
                        options: {
                            query: {
                                name: `[hash].[ext]`,
                                hash: `sha512`,
                                digest: `hex`
                            }
                        }
                    },
                    {
                        loader: `image-webpack-loader`,
                        options: {
                            query: {
                                bypassOnDebug: true,
                                mozjpeg: {
                                    progressive: true,
                                },
                                gifsicle: {
                                    interlaced: true,
                                },
                                optipng: {
                                    optimizationLevel: 7,
                                }
                            }
                        }
                    }
                ]
            },
          {
            test: /\.svg$/i,
            use: `file-loader`
          },
            {
                test: /\.js$/i,
                exclude: /node_modules\//,
                use: `babel-loader`
            }
        ]
    },

    devServer: {
        port: 9000,
        historyApiFallback: {
          index: `/gxa/sc/experiments/E-EHCA-2/`
        }
    }
}

module.exports = config
