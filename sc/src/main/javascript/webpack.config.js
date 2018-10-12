var webpack = require('webpack')
var path = require('path')
var CleanWebpackPlugin = require('clean-webpack-plugin')

module.exports = {
  // define the bundles we want
  entry: {
    atlasAutocomplete: ['./bundles/autocomplete'],
    experimentPage: ['./bundles/experiment-page/src/index.js'],
    geneSearch: ['./bundles/gene-search/src/index.js'],
    geneSearchForm: ['./bundles/gene-search-form'],
    // Put dependencies one line per package
    dependencies: [
      'prop-types', 'react', 'react-dom', 'urijs',
      'react-autocomplete' // shared by atlasAutocomplete and experimentPage
    ]
  },

  resolve: {
    alias: {
      "react": path.resolve('./node_modules/react'),
      "react-dom": path.resolve('./node_modules/react-dom')
    },
  },

  output: {
    libraryTarget: 'var',
    library: '[name]',
    path: path.resolve(__dirname, '../webapp/resources/js-bundles'),
    filename: '[name].bundle.js',
    publicPath: '/gxa/sc/resources/js-bundles/'
  },

  plugins: [
    new CleanWebpackPlugin(['webapp/resources/js-bundles'], {
      root: path.resolve(__dirname, '..'),
      verbose: true,
      dry: false
    }),
    new webpack.optimize.CommonsChunkPlugin({
      name: 'dependencies',
      filename: 'vendorCommons.bundle.js',
      minChunks: Infinity     // Explicit definition-based split. Don’t put shared modules between main and demo entries in vendor.bundle.js
    }),
    new webpack.DefinePlugin({
      "process.env": {
        NODE_ENV: process.env.NODE_ENV === 'production' ? JSON.stringify("production") : JSON.stringify("development")
      }
    })
  ],

  module: {
    rules: [
      {
        test: /\.css$/,
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
        test: /\.less$/,
        use: [
          {loader: 'style-loader'},
          {loader: 'css-loader', options: {modules: false}},
          {loader: 'less-loader'}
        ]
      },
      {
        test: /\.(jpe?g|png|gif)$/i,
        use: [
          {loader: 'file-loader', options: {query: {name: '[hash].[ext]', hash: 'sha512', digest: 'hex'}}},
          {loader: 'image-webpack-loader',
            options: {
              query: {bypassOnDebug: true, mozjpeg: {progressive: true}, gifsicle: {interlaced: true}, optipng: {optimizationLevel: 7}}
            }
          }
        ]
      },
      {
        test: /\.(svg)$/i,
        use: [
          {loader: 'file-loader', options: {query: {name: '[hash].[ext]', hash: 'sha512', digest: 'hex'}}}
        ]
      },
      {
        test: /\.jsx?$/,
        exclude: /node_modules\//,
        use: [
          {loader: 'babel-loader'}
        ]
      }
    ]
  }
}
