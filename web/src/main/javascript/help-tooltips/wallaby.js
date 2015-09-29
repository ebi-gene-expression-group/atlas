var path = require('path');

module.exports = function () {
    return {
        env: {
            type: 'node',
            runner: path.resolve(process.env.HOME, '.nvm/versions/node/v4.1.1/bin/node'),
            params: {
                runner: '--harmony --harmony_arrow_functions'
            }
        },

        files: [
            'src/*.js'
        ],

        tests: [
            'test/*.js'
        ],

        testFramework: "mocha",

        debug: true
    };
};
