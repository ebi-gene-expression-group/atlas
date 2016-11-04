module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
            heatmapHighcharts: './index.js',
            dependencies: ['color', 'he', 'highcharts-custom-events', 'highcharts-heatmap', 'jquery', 'lodash',
                'object-hash', 'rc-slider', 'react', 'react-bootstrap', 'react-dom', 'react-highcharts',
                'react-prop-types-check']
    }
});
