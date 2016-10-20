module.exports = Object.assign(require('../webpack.config.js'),
    {
        entry: {
        downloadProfilesButton: './index.js',
        renderer: './html/renderer.js',
        dependencies: ['react', 'react-dom', 'react-bootstrap']
    }
});
