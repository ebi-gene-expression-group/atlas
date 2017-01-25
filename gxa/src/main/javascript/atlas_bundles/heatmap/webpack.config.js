module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
            expressionAtlasExperimentHeatmap: ['whatwg-fetch', './src/experimentPageHeatmapAnatomogramRenderer.js'],
            expressionAtlasHeatmap: ['whatwg-fetch', './index.js'],
            dependencies: [
                // Core dependencies
                'anatomogram',
                'atlas-modernizr',
                'expression-atlas-cell-differential',
                'expression-atlas-contrast-tooltips',
                'expression-atlas-display-levels-button',
                'expression-atlas-download-profiles-button',
                'expression-atlas-feedback',
                'expression-atlas-genome-browser-launcher',
                'expression-atlas-heatmap-baseline-cell-variance',
                'expression-atlas-help-tooltips',
                'expression-atlas-legend',
                'expression-atlas-number-format',
                'fancybox',
                'jquery',
                'jquery-hc-sticky',
                'jquery-toolbar',
                'jquery-ui-bundle',
                'jquery.browser',
                'rc-slider',
                'react',
                'react-addons-shallow-compare',
                'react-dom',
                'react-radio-group',
                'urijs',

                // anatomogram
                'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js',

                // feedback
                'react-addons-css-transition-group',
                'react-bootstrap',
                'react-emojione',
                'react-localstorage',
                'react-timer-mixin',

                // genome-browser-launcher
                'react-refetch',
                'whatwg-fetch',

                // baseline-cell
                'react-highcharts',
                'highcharts-more']
        }
    });
