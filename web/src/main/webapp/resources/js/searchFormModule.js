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

    function isEven(value) {
        if (value%2 === 0){
            return true;
        }
        return false;
    }

    function normalizeSpaces( term ){
        return term.replace(/^\s+/, '').replace(/\s+$/, ' ');
    }

    function extractLast( term ) {

        var splitByDoubleQuotes = term.split( /\s*"/),
            numberOfDoubleQuotes = splitByDoubleQuotes.length - 1,
            lastItem = splitByDoubleQuotes.pop();

        if (!isEven(numberOfDoubleQuotes)){
            return lastItem;
        }
        return lastItem.split( /\s+/).pop();
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
                initialTags:[],
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
                        var lastItem = extractLast(request.term);

                        $.ajax({
                            url: 'json/suggestions',
                            data: {
                                'query': lastItem,
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
                    console.log(tags);
                },
                placeholder: 'Start typing ...',
                forceLowercase: false
            });

        $("#submit-button").click(function() {
            var geneQuery = $("#geneQuery").val();
            console.log(geneQuery);

            var array_commaSeparated = geneQuery.split(",");
            console.log(array_commaSeparated);

            console.log(array_commaSeparated.length);

            if(array_commaSeparated.length > 1) {   //we have multiple terms comma-separated
                var new_query = "";
                for(var i = 0; i < array_commaSeparated.length; i++) {
                    var array_words = array_commaSeparated[i].match(/\S+/g); //split by whitespace
                    if(array_words.length > 1) {
                        var new_word = "\"" + array_commaSeparated[i] + "\"";
                        console.log(new_word);
                        new_query = new_query.concat(new_word, " ");
                    }
                    else {
                        new_query = new_query.concat(array_words, " ");
                    }
                }
                $("#geneQuery").val(new_query);
            }
            else { //single term but could be a multiple-word term or single.
                var array_words = array_commaSeparated[0].match(/\S+/g);
                if(array_words.length > 1) {
                    var newword = quoteTextThatContainsMoreThanOneWord(geneQuery) //"\"" + geneQuery + "\"";
                    console.log(newword);
                    $("#geneQuery").val(newword);
                }
            }
        });

    }

    function quoteTextThatContainsMoreThanOneWord (text) {
        return hasMoreThanOneWord(text) ? "\"" + text + "\"" : text;
    }

    function hasMoreThanOneWord(text) {
        return /\s/.test(text.trim());
    }

    function startsWith (str, prefix) {
        return str.lastIndexOf(prefix, 0) === 0;
    }

    function remoteExtraQuotesFromStart (str) {
        return str.substring(1, str.length);
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


