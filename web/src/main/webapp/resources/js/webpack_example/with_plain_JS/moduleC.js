/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 18/05/15.
 */

var moduleC = (function (modB) {

    function init() {
        return 'Hola mundo desde C; el module B dice "' + modB.init() + '"';
    }

    return {
        init: init
    };

}(moduleB));