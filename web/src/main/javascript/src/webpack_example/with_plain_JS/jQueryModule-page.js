/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 18/05/15.
 */

"use strict";

var $ = require('jquery');

//module.exports = function ($) {
//
//    "use strict";
//
//    var message;
//
//    function init(msg) {
//        message = msg == null ? "¡Vamos que nos vamos!" : msg;
//    }
//
//    function displayMessage() {
//        $("#main").text(message);
//    }
//
//    return {
//        init: init,
//        displayMessage: displayMessage
//    };
//
//}(jQuery);

var message;

exports.init = function(msg) {
    message = msg == null ? "¡Vamos que nos vamos!" : msg;
};

exports.displayMessage = function() {
    $("#main").text(message);
};

exports.$ = $;
