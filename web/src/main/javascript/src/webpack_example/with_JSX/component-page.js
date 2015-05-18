/**
 * Created by amunoz on 18/05/15.
 */

"use strict";

var React = require('react');
var SimpleComponent = require('./component.jsx');

exports.render = function(mountNode) {
    React.render(
        React.createElement(SimpleComponent),
        mountNode
    );
};
