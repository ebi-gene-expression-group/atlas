/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 18/05/15.
 */

var jQueryModule = (function ($) {

    "use strict";

    var message;

    function init(msg) {
        message = msg == null ? "¡Vamos que nos vamos!" : msg;
    }

    function displayMessage() {
        $("#main").text(message);
    }

    return {
        init: init,
        displayMessage: displayMessage
    };

}(jQuery));
