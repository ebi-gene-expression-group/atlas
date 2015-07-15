module.exports = function () {
    return {
        env: {
            type: 'node'
        },

        files: [
            {pattern: 'node_modules/react/dist/react-with-addons.js', instrument: false},
            'src/*.js',
            'src/*.jsx'
        ],

        tests: [
            'test/*Spec.js'
        ],

        testFramework: "mocha",

        preprocessors: {
            '**/*.jsx': file => require('react-tools').transformWithDetails(file.content, {sourceMap: true, harmony: true})
        },

        debug: true
    };
};
