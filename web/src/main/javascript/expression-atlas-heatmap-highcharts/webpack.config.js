var path = require('path');

module.exports = Object.assign(require('../webpack.config.js'), {
    entry: {
        heatmapHighcharts: './index.js',
        dependencies: ['react', 'react-dom', 'jquery', 'react-highcharts', 'highcharts-heatmap']
    },

    output: {
        libraryTarget: 'var',
        library: '[name]',
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].bundle.js',
        publicPath: '/dist/'
    }
});
