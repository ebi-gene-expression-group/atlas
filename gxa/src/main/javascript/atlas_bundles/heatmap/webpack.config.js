module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
            expressionAtlasHeatmap: ['whatwg-fetch', './index.js'],
            dependencies: ['react', 'react-dom', 'react-radio-group', 'react-bootstrap',
                'jquery', 'jquery-ui-bundle', 'jquery.browser', 'fancybox', 'jquery-hc-sticky', 'jquery-toolbar', 'jquery-hc-sticky',
                'urijs', 'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js', 'atlas-modernizr',
                'highcharts-more', 'react-highcharts',
                'events', 'rc-slider']
        }
    });
