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

    var _cutoff;

    function removeHttpParameters (string) {
        return string.split("?")[0];
    }

    function isNotIE7orOlder (){
        return !($.browser.msie && $.browser.version <= 8.0);
    }

    function initButtons(){

        $("#submit-button").button();

        $("#reset-button").button().click(function (event) {
            var urlWithoutParameters = removeHttpParameters(window.location.href);
            window.location.replace(urlWithoutParameters);
        });

    }

    function initWatermarks(){
        $("#geneQuery").watermark("(all genes)");

        $("#cutoff").watermark("(default 0.5)");
    }

    function initSelectBox(watermarkLabel){
        $("#queryFactorValues").chosen();

        //following code is a patch to reload the right watermark label in the 'chosen' plugin
        $("#queryFactorValues").change(function () {
            if ($(this).val()) {
                $(this).data("chosen").default_text = "";
            } else {
                $(this).data("chosen").default_text = watermarkLabel;
                $(this).trigger("liszt:updated");
            }

        });

    }

    function split( val ) {
        return val.split( /,\s*/ );
    }

    function extractLast( term ) {
        return split( term ).pop();
    }

    function initAutocomplete(){
        $("#geneQuery")
            // don't navigate away from the field on tab when selecting an item
            .bind( "keydown", function( event ) {
                if ( event.keyCode === $.ui.keyCode.TAB &&
                    $( this ).data( "ui-autocomplete" ).menu.active ) {
                    event.preventDefault();
                }
            })
            .autocomplete({
                delay:500,
                minLength: 2,
                autoFocus:true,
                focus: function() {
                    // prevent value inserted on focus
                    return false;
                },
                select: function( event, ui ) {
                    var terms = split( this.value );
                    // remove the current input
                    terms.pop();
                    // add the selected item
                    terms.push( ui.item.value );
                    // add placeholder to get the comma-and-space at the end
                    terms.push( "" );
                    this.value = terms.join( ", " );
                    return false;
                },
                source:function (request, response) {
                    var lastItem = extractLast( request.term );

                    $.ajax({
                        url:'http://localhost:9090/gxa/json/suggestions',
                        data:{'query': lastItem},
                        success:function (data) {
                            response(data);
                        },
                        error:function (jqXHR, textStatus, errorThrown) {
                            console.log("Error. Status: " + textStatus + ", errorThrown: " + errorThrown);
                        }
                    });
                }
            });
    }


    function init (cutoff, experimentAccession, watermarkLabel) {
        _cutoff = cutoff;

        initButtons();

        initWatermarks();

        initSelectBox(watermarkLabel);

        initAutocomplete();

    }

    return {
        init: init,
        removeHttpParameters: removeHttpParameters
    };

}(jQuery));

//---------------------------------------------------


