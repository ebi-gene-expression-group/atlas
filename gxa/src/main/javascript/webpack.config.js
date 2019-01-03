const path = require(`path`)
const CleanWebpackPlugin = require(`clean-webpack-plugin`)
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

const vendorsBundleName = `vendorCommons`

module.exports = {
  entry: {
    expressionAtlasHeatmapHighcharts: `./node_modules/expression-atlas-heatmap-highcharts`,
    experimentPage: `./node_modules/expression-atlas-experiment-page`,
    expressionAtlasBioentityInformation: `./node_modules/sc-atlas-bioentity-information`,
    expressionAtlasBaselineExpression: `./bundles/baseline-expression`,
    expressionAtlasDifferentialExpression: `./bundles/differential-expression`,
    expressionAtlasBrowseBySpecies: `./bundles/browse-by-species`,
    atlasHomepageCard: `./bundles/atlas-homepage-card`,
    homePagePanel: `./bundles/scxa-homepage-panels`
  },

  plugins: [
    new CleanWebpackPlugin([`webapp/resources/js-bundles`],
      {
        root: path.resolve(__dirname, `..`),
        verbose: true
      }
    ),
    new BundleAnalyzerPlugin()
  ],

  output: {
    library: `[name]`,
    filename: `[name].bundle.js`,
    publicPath: `/gxa/resources/js-bundles/`,
    path: path.resolve(__dirname, `../webapp/resources/js-bundles`)
  },

  resolve: {
    alias: {
      "expression-atlas-heatmap-highcharts": path.resolve(`./node_modules/expression-atlas-heatmap-highcharts`),
      "he": path.resolve(`./node_modules/he`),
      "highcharts": path.resolve(`./node_modules/highcharts`),
      "lodash": path.resolve(`./node_modules/lodash`),
      "react": path.resolve(`./node_modules/react`),
      "react-bootstrap": path.resolve(`./node_modules/react-bootstrap`),
      "react-dom": path.resolve(`./node_modules/react-dom`),
      "react-highcharts": path.resolve(`./node_modules/react-highcharts`),
      "prop-types": path.resolve(`./node_modules/prop-types`),
      "styled-components": path.resolve(`./node_modules/styled-components`),
      "react-router-dom": path.resolve(`./node_modules/react-router-dom`),
      "urijs": path.resolve(`./node_modules/urijs`)
    }
  },

  optimization: {
    runtimeChunk: {
      name: vendorsBundleName
    },
    splitChunks: {
      cacheGroups: {
        commons: {
          test: /[\\/]node_modules[\\/]/,
          minChunks: 2,
          name: vendorsBundleName,
          chunks: 'all'
        }
      }
    }
  },

  module: {
    rules: [
      {
        test: /\.css$/i,
        use: [ 'style-loader', 'css-loader' ]
      },
      {
        test: /\.js$/i,
        exclude: /node_modules\//,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env', '@babel/preset-react']
          }
        }
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
      }
    ]
  }
}


