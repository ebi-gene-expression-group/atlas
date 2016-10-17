/*global $,jQuery,console,loadSliderAndPlot: false */
/*jslint browser:true */
/*jslint nomen: true*/

/*
 Search form object encapsulating form related jquery plugin initializations and binding.
 I am using the Module pattern, it gives high control on what is implementation (not to be exposed)
 [http://www.adequatelygood.com/2010/3/JavaScript-Module-Pattern-In-Depth]
 and what is public (what is exposed through the returned object literal)
 I am not using any prototypal construction pattern because we will never have multiple instances of the entire form on the same page.
 */
var searchFormModule = (function($) {
    "use strict";

    function removeHttpParameters (string) {
        return string.split("?")[0];
    }

    function disableCarriageReturn(selector) {
        $(selector).keypress(function(event) {
            if (event.keyCode === 13) {
                event.preventDefault();
                $("#submit-button").click();
            }
        });
    }

    function searchBoxEnterEventHandler(element) {
        $('#searchForm .tag-editor').on('submit', function (e) {
            $(element).click();
        });
    }

    function init () {
        // Initialize buttons and select box
        $("#submit-button").button();
        $("#reset-button").button();
        $("#queryFactorValues").chosen();
    }

    return {
        init: init,
        disableCarriageReturn: disableCarriageReturn,
        searchBoxEnterEventHandler: searchBoxEnterEventHandler,
        removeHttpParameters: removeHttpParameters
    };

}(jQuery));



