/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

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

    var _species;

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

    function initWatermarks(defaultPValueCutOff, defaultFoldChangeCutOff){
        $("#geneQuery").watermark("(all genes)");

        $("#cutoff").watermark("(default "+ defaultPValueCutOff +")");

        if (defaultFoldChangeCutOff) {
            $("#foldChangeCutOff").watermark("(default "+ defaultFoldChangeCutOff +")");
        }
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

    function geneQuerySearchBoxInitAutocomplete(){
        var $buttons = $('#submit-button, #reset-button')
        $("#geneQuery")
            // don't navigate away from the field on tab when selecting an item
            .bind( "keydown", function( event ) {
                if ( event.keyCode === $.ui.keyCode.TAB &&
                    $( this ).data( "ui-autocomplete" ).menu.active ) {
                    event.preventDefault();
                }
            })
            .tagEditor({
                delimiter:",",
                maxLength: 50,
                autocomplete: {
                    delay: 500,
                    minLength: 1,
                    autoFocus: true,
                    focus: function() {
                        // prevent value inserted on focus
                        return false;
                    },
                    source: function (request, response) {
                        $.ajax({
                            url: 'json/suggestions',
                            data: {
                                'query': request.term,
                                'species': _species
                            },
                            success: function (data) {
                                response(data);
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                console.log("Error. Status: " + textStatus + ", errorThrown: " + errorThrown);
                            }
                        });
                    }
                },
                onChange: function(field, editor, tags) {
                    $buttons.button("option", "disabled", tags.length == 0);
                },

                placeholder: 'Start typing ...',
                forceLowercase: false
            });

    }

    function disableCarriageReturn(selector) {
        $(selector).keypress(function(event) {
            if (event.keyCode === 13) {
                event.preventDefault();
                $("#submit-button").click();
            }
        });
    }

    function init (watermarkLabel, species, defaultPValueCutOff, defaultFoldChangeCutOff) {

        _species = species;

        initButtons();

        initWatermarks(defaultPValueCutOff, defaultFoldChangeCutOff);

        initSelectBox(watermarkLabel);

        geneQuerySearchBoxInitAutocomplete();

        disableCarriageReturn("#geneQuery");

    }

    return {
        init: init,
        geneQuerySearchBoxInitAutocomplete: geneQuerySearchBoxInitAutocomplete,
        disableCarriageReturn: disableCarriageReturn,
        removeHttpParameters: removeHttpParameters
    };

}(jQuery));

//---------------------------------------------------


