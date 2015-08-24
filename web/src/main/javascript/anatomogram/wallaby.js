var path = require('path');

module.exports = function () {
    return {
        env: {
            type: 'node',
            runner: path.resolve(process.env.HOME, '.nvm/versions/io.js/v2.3.4/bin/node'),
            params: {
                runner: '--harmony --harmony_arrow_functions'
            }
        },

        files: [
            {pattern: 'node_modules/react/dist/react-with-addons.js', instrument: false},
            'src/*.js',
            'src/*.jsx'
        ],

        tests: [
            'test/*.js'
        ],

        testFramework: "mocha",

        preprocessors: {
            '**/*.jsx': file => require('react-tools').transformWithDetails(file.content, {sourceMap: true, harmony: true})
        },

        debug: true
    };
};
