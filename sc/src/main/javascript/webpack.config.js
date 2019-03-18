const path = require(`path`)
const CleanWebpackPlugin = require(`clean-webpack-plugin`)
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

const vendorsBundleName = `vendorCommons`

module.exports = {
  entry: {
    atlasAutocomplete: `./bundles/autocomplete`,
    experimentPage: `./bundles/experiment-page`,
    geneSearch: `./bundles/gene-search`,
    geneSearchForm: `./bundles/gene-search-form`,
    feedbackForm: `atlas-feedback-form`,
    homepageSpeciesSummaryPanel: `./bundles/homepage-species-summary-panel`,
    homepageExperimentsSummaryPanel: `./bundles/homepage-experiments-summary-panel`,
    homepageCards: `./bundles/homepage-cards`
  },

  plugins: [
    new CleanWebpackPlugin({
      verbose: true,
      cleanOnceBeforeBuildPatterns: `../webapp/resources/js-bundles/**`
    }),
    new BundleAnalyzerPlugin()
  ],

  output: {
    library: `[name]`,
    filename: `[name].bundle.js`,
    publicPath: `/gxa/sc/resources/js-bundles/`,
    path: path.resolve(__dirname, `../webapp/resources/js-bundles`)
  },

  resolve: {
    alias: {
      "react": path.resolve(`./node_modules/react`),
      "react-dom": path.resolve(`./node_modules/react-dom`),
      "prop-types": path.resolve(`./node_modules/prop-types`),
      "styled-components": path.resolve(`./node_modules/styled-components`),
      "react-router-dom": path.resolve(`./node_modules/react-router-dom`),
      "urijs": path.resolve(`./node_modules/urijs`),
      "lodash": path.resolve(`./node_modules/lodash`),
      "react-select": path.resolve(`./node_modules/react-select`),
      "expression-atlas-autocomplete": path.resolve(`./node_modules/expression-atlas-autocomplete`),
      "scxa-gene-search-form": path.resolve(`./node_modules/scxa-gene-search-form`),
      "atlas-homepage-cards": path.resolve(`./node_modules/atlas-homepage-cards`),
      "atlas-react-fetch-loader": path.resolve(`./node_modules/atlas-react-fetch-loader`)
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
