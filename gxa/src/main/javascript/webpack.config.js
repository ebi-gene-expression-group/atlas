var webpack = require('webpack');
var path = require('path');
var CleanWebpackPlugin = require('clean-webpack-plugin');
var WebpackShellPlugin = require('webpack-shell-plugin');

const baselineDeps = [ 'expression-atlas-feedback', 'expression-atlas-heatmap-highcharts', 'prop-types', 'react',
  'react-dom']

const bioentityInfoDeps = ['react', 'react-dom']

const browseByDeps = ['react', 'react-dom', 'react-ebi-species', 'urijs']

const differentialDeps = [ 'expression-atlas-feedback',  'expression-atlas-number-format',  'jquery',
  'jquery-ui-bundle', 'prop-types', 'react', 'react-dom', 'react-ebi-species', 'urijs']

const heatmapDeps = ['expression-atlas-heatmap-highcharts']
const experimentPageDeps = ['expression-atlas-experiment-page']

const dependenciesArray =
  Array.from(
    new Set(
      [].concat(baselineDeps, bioentityInfoDeps, browseByDeps, differentialDeps, experimentPageDeps, heatmapDeps)))

const alias = dependenciesArray
  .reduce(function(acc, moduleName) {
    acc[moduleName] = path.resolve('./node_modules/' + moduleName)
    return acc
  }, {})

module.exports = {
  // define the bundles we want
  entry: {
    expressionAtlasHeatmapHighcharts: './atlas_bundles/heatmap-highcharts',
    experimentPage: './atlas_bundles/experiment-page',
    expressionAtlasBaselineExpression: './atlas_bundles/baseline-expression',
    expressionAtlasDifferentialExpression: './atlas_bundles/differential-expression',
    expressionAtlasBioentityInformation: './atlas_bundles/bioentity-information',
    expressionAtlasBrowseBySpecies: './atlas_bundles/browse-by-species',
    // polyfills: ['babel-polyfill', 'whatwg-fetch']
  },

  resolve: {
    alias
  },

  output: {
    libraryTarget: 'var',
    library: '[name]',
    path: path.resolve(__dirname, '../webapp/resources/js-bundles'),
    filename: '[name].bundle.js',
    publicPath: '/gxa/resources/js-bundles/'
  },

  plugins: [
    new CleanWebpackPlugin(['webapp/resources/js-bundles'], {
      root: path.resolve(__dirname , '..'),
      verbose: true,
      dry: false
    }),
    new webpack.optimize.CommonsChunkPlugin({
      name: 'dependencies', // According to the docs it defaults to filename, but it doesn’t
      filename: 'vendorCommons.bundle.js',
      minChunks: 3
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
        test: /\.css$/i,
        use: [ 'style-loader', 'css-loader' ]
      },
      {
        test: /\.less$/i,
        use: [ 'style-loader', 'css-loader', 'less-loader' ]
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
        test: /\.svg$/i,
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
        test: /\.jsx?$/i,
        exclude: /node_modules\//,
        use: 'babel-loader'
      }
    ]
  },

  devServer: {
    port: 9000
  }
};
