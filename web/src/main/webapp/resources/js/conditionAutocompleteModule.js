/**
 * Created by barrera on 18/06/2014.
 */

var conditonAutocompleteModule = (function ($) {
    "use strict";

    function initConditionAutocomplete (contextPath) {

        var autoCompleteFixSet = function() {
            $(this).attr('arrayExpressAutocomplete', 'off');
        }

        var autoCompleteFixUnset = function() {
            $(this).removeAttr('arrayExpressAutocomplete');
        }

        $("#condition")
            .arrayExpressAutocomplete(
                contextPath + "efowords.txt",
                { matchContains: false
                , selectFirst: false
                , scroll: true
                , max: 50
                , requestTreeUrl: contextPath + "efotree.txt"
            })
            .focus(autoCompleteFixSet)
            .blur(autoCompleteFixUnset)
            .removeAttr('arrayExpressAutocomplete');

    }

    return {
        init: initConditionAutocomplete
    };

})(jQuery);

