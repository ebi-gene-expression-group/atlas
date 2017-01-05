module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
            genomeBrowserLauncherRenderer: ['whatwg-fetch', './index.js'],
            dependencies: ['react', 'react-dom', 'react-refetch', 'react-bootstrap']
    }
});
