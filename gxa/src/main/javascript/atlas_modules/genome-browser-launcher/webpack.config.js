module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
            genomeBrowserLauncher: './index.js',
            genomeBrowserLauncherRenderer: './html/genomeBrowserLauncherRenderer.js',
            dependencies: ['react', 'react-dom', 'react-bootstrap']
    }
});
