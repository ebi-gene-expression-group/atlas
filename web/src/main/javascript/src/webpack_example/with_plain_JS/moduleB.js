/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 18/05/15.
 */

// This is the evil way:
//module.exports = function () {
//
//    function init() {
//        return "Hola mundo desde B";
//    }
//
//    return {
//        init: init
//    };
//
//}();

// This is the Webpack good way:
exports.init = function() {
    return "Hola mundo desde B";
}
