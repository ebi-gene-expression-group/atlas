const path = require(`path`)
const CleanWebpackPlugin = require(`clean-webpack-plugin`)

const commonPublicPath = '/dist/'

module.exports = {
  entry: {
    geneSearchDemo: ['whatwg-fetch', 'babel-polyfill', './src/index.js'],
    // dependencies: ['prop-types', 'react', 'react-dom', 'urijs']
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
        geneSearch: {
          test: /[\\/]src[\\/]/,
          name: 'geneSearch',
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
        test: /\.js$/i,
        exclude: /node_modules\//,
        use: 'babel-loader'
      }
    ]
  },

  devServer: {
    port: 9000,
    contentBase: path.resolve(__dirname, 'html'),
    publicPath: commonPublicPath
  }
}