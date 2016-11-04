module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
            differentialExpression: './index.js',
            dependencies: ['react', 'react-dom', 'jquery', 'jquery-ui-bundle', 'jquery.browser']
        }
    });
