"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var Feedback = require('../src/Feedback.jsx');

//*------------------------------------------------------------------*

module.exports = function(mountNode) {
    ReactDOM.render(
        React.createElement(
            Feedback, {collectionCallback : function (satisfactionScore, optionalComment) {
              console.log("Score: "+satisfactionScore +((typeof optionalComment === 'undefined' || optionalComment.length===0)
                ? ", no comment"
                : ", comment: "+optionalComment));
            }}
        ),
        mountNode
    );
};
