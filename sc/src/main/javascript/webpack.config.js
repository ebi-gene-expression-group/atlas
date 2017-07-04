var webpack = require('webpack')
var path = require('path')
var CleanWebpackPlugin = require('clean-webpack-plugin')
var WebpackShellPlugin = require('webpack-shell-plugin')

module.exports = {
  // define the bundles we want
  entry: {
    atlasAutocomplete: ['./bundles/autocomplete/index.js'],
    markerGenesSearchResults: ['./bundles/marker-genes/index.js'],
    experimentPage: ['./bundles/experiment-page/index.js'],
    // Put dependencies one line per package
    dependencies: [
      'prop-types', 'react', 'react-autocomplete', 'react-dom', 'react-refetch', 'urijs'  // autocomplete, marker-genes
    ],
    polyfills: ['babel-polyfill', 'whatwg-fetch']
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
    filename: '[name].bundle.js'
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
    }),
    // Beacuse copy-webpack-plugin doesn’t wait for WebPack to finish, and the output dir is cleaned above
    new WebpackShellPlugin({
      onBuildStart: ['echo Cleaning versioned-resouces...', 'rm -rf ../webapp/versioned-resources/js-bundles', 'echo Done!'],
      onBuildEnd: ['echo Populating versioned-resources...', 'cp -a ../webapp/resources/js-bundles ../webapp/versioned-resources', 'echo Done!']
    })
  ],

  module: {
    rules: [
      {
        test: /\.css$/,
        use: [
          {loader: 'style-loader'},
          {loader: 'css-loader', options: {modules: false}}
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
        use: [
          {loader: 'babel-loader'}
        ]
      }
    ]
  }
}
