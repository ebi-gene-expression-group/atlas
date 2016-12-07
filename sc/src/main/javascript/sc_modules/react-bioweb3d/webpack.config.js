module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
        downloadProfilesButton: './index.js',
        renderer: './html/renderer.js',
        dependencies: ['react', 'react-dom']
    }
});
