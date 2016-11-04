module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
            bioentityInformation: './index.js',
            dependencies: ['react', 'react-dom']
        }
    });
