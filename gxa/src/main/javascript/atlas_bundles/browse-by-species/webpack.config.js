module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
            browseBySpecies: './index.js',
            dependencies: ['react', 'react-dom']
        }
    });
