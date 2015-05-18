/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 18/05/15.
 */

var moduleB = (function () {

    function init() {
        return "Hola mundo desde B";
    }

    return {
        init: init
    };

}());