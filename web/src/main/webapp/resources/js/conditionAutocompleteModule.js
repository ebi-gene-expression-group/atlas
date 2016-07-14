"use strict";

var conditionAutocompleteModule = (function ($) {

    function initConditionAutocomplete (contextPath, onChange) {

        var autoCompleteFixSet = function() {
            $(this).attr('arrayExpressAutocomplete', 'off');
        };

        var autoCompleteFixUnset = function() {
            $(this).removeAttr('arrayExpressAutocomplete');
        };

        $("#condition")
            // don't navigate away from the field on tab when selecting an item
            .bind( "keydown", function( event ) {
                if ( event.keyCode === $.ui.keyCode.TAB &&
                    $( this ).data( "ui-autocomplete" ).menu.active ) {
                    event.preventDefault();
                }
            })
            .tagEditor({
                delimiter:"\t",
                maxLength: 50,
                autocomplete: {
                    plugin: 'arrayExpressAutocomplete',
                    urlOrData:  contextPath + "efowords.txt",
                    matchContains: false
                    , selectFirst: false
                    , scroll: true
                    , max: 50
                    , requestTreeUrl: contextPath + "efotree.txt"
                    , width: 300
                },
                onChange: onChange,

                placeholder: 'Enter condition query...',
                forceLowercase: false
            })
            .focus(autoCompleteFixSet)
            .blur(autoCompleteFixUnset)
            .removeAttr('arrayExpressAutocomplete');

    }

    return {
        init: initConditionAutocomplete
    };

})(jQuery);

