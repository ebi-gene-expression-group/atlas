var path = require('path');

module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
            baselineExpression: './index.js',
            dependencies: ['react', 'react-dom', 'events', 'url', 'querystring', 'jquery', 'jquery.browser'
                // 'jquery', 'jquery-ui-bundle', 'jquery.browser', 'jquery-hc-sticky', 'fancybox', 'jquery-toolbar',
                // 'urijs', 'query-string', 'atlas-modernizr',
                // 'events', 'imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js',
                // 'highcharts-more', 'react-highcharts'
            ]
        },
        resolve: {
            alias: {
                "react": path.resolve('./node_modules/react'),
                "react-dom": path.resolve('./node_modules/react-dom')
            },
        }
    }
);
