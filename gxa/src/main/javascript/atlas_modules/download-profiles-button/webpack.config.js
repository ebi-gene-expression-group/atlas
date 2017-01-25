module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
        downloadProfilesButton: './index.js',
        dependencies: ['react', 'react-dom', 'react-bootstrap']
    }
});
