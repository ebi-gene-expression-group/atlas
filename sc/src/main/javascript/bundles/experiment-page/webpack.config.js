const path = require(`path`)
const CleanWebpackPlugin = require(`clean-webpack-plugin`)

const commonPublicPath = '/dist/'

module.exports = {
  entry: {
    experimentPageDemo: [`whatwg-fetch`, `babel-polyfill`, `./src/index.js`],
    // dependencies: [`prop-types`, `react`, `react-dom`, `react-router-dom`]
  },

  output: {
    library: `[name]`,
    filename: `[name].bundle.js`,
    publicPath: commonPublicPath
  },

  optimization: {
    splitChunks: {
      chunks: 'all',
      minSize: 1,
      cacheGroups: {
        experimentPage: {
          test: /[\\/]src[\\/]/,
          name: 'experimentPage',
          priority: -20
        },
        vendors: {
          test: /[\\/]node_modules[\\/]/,
          name: 'vendors',
          priority: -10
        }
      }
    }
  },

  plugins: [
    new CleanWebpackPlugin([`dist`])
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
    contentBase: path.resolve(__dirname, 'gxa/sc/experiments/E-MTAB-5061'),
    publicPath: commonPublicPath
  }
}