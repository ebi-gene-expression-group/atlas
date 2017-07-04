var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');

process.traceDeprecation = true;

module.exports = {
    entry: {
        experimentPage: ['whatwg-fetch','./index.js'],
        dependencies: ['prop-types', 'react', 'react-dom', 'react-router-dom']
    },

    output: {
        library: '[name]',
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].bundle.js',
        publicPath: '/dist/'
    },

    plugins: [
        new CleanWebpackPlugin(['dist'], {verbose: true, dry: false}),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'dependencies',
            filename: 'vendorCommons.bundle.js',
            minChunks: Infinity     // Explicit definition-based split. Don’t put shared modules between main and demo
        }),                          // entries in vendor.bundle.js
    ],

    module: {
        rules: [
            {
                test: /\.css$/,
                use: [
                    {
                        loader: 'style-loader'
                    },
                    {
                        loader: 'css-loader',
                        options: {
                            modules:false
                        }
                    }
                ]
            },
            {
                test: /\.less$/,
                use: [
                    {
                        loader: 'style-loader'
                    },
                    {
                        loader: 'css-loader',
                        options: {
                            modules:false
                        }
                    },
                    {
                        loader: 'less-loader'
                    }
                ]
            },
            {
                test: /\.(jpe?g|png|gif)$/i,
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            query: {
                                name: '[hash].[ext]',
                                hash: 'sha512',
                                digest: 'hex'
                            }
                        }
                    },
                    {
                        loader: 'image-webpack-loader',
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
                test: /\.(svg)$/i,
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            query: {
                                name: '[hash].[ext]',
                                hash: 'sha512',
                                digest: 'hex'
                            }
                        }
                    }
                ]
            },
            {
                test: /\.jsx?$/,
                // Place after node_modules packages owned by Expression Atlas to be transpiled, as they aren’t
                // distributed pre-bundled or with a dist kind of folder
                exclude: /node_modules\/(?!(expression-atlas|anatomogram|react-ebi-species))/,
                use: [
                    {
                        loader: 'babel-loader'
                    }
                ]
            }
        ]
    },

    devServer: {
        // hot: true,      // CLI --hot is equivalent to this option, but it also enables the HMR plugin (see above)
        // hotOnly: true,  // Won’t inject modules if there’s a compilation error (without this a full page reload is
        // done after a successful build and we lose state)
        historyApiFallback: true,
        contentBase: "html",
        port: 9000
    }
};
