module.exports = Object.assign(require('../../webpack.config.package-test-build.js'),
    {
        entry: {
            anatomogramBuilder: './index.js',
            dependencies: ['react', 'react-dom', 'anatomogram']
        }
    });
