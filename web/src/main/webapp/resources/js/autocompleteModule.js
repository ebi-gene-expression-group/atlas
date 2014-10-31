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

var autocompleteModule = (function($) {
    "use strict";

    function initAutocomplete(species){
        $("#local-searchbox")
            // don't navigate away from the field on tab when selecting an item
            .bind( "keydown", function( event ) {
                if ( event.keyCode === $.ui.keyCode.TAB &&
                    $( this ).data( "ui-autocomplete" ).menu.active ) {
                    event.preventDefault();
                }
            })
            .autocomplete({
                delay:500,
                minLength: 1,
                autoFocus:true,
                focus: function() {
                    // prevent value inserted on focus
                    return false;
                },
                select: function( event, ui ) {
                    var selectedValue = quoteTextThatContainsMoreThanOneWord(ui.item.value.trim()),
                        lastItem = extractLast(this.value);
                    if(this.value.startsWith("\"")) {
                        this.value = this.value.substr(1, this.value.length); //remove the single quotes at the beginning of the String
                    }
                    this.value = this.value.substr(0, this.value.length - lastItem.length).concat(selectedValue) + " ";
                    return false;
                },
                source:function (request, response) {
                    var lastItem = extractLast( request.term );

                    $.ajax({
                        url:'/gxa/json/suggestions',
                        data:{
                            'query': lastItem,
                            'species': species
                        },
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

    if ( typeof String.prototype.startsWith != 'function' ) {
        String.prototype.startsWith = function (str) {
            return this.substring(0, str.length) === str;
        }
    }

    function quoteTextThatContainsMoreThanOneWord (text) {
        return hasMoreThanOneWord(text) ? "\"" + text + "\"" : text;
    }

    function hasMoreThanOneWord(text) {
        return /\s/.test(text.trim());
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

    function isEven(value) {
        if (value%2 === 0){
            return true;
        }
        return false;
    }


    return {
        init: initAutocomplete
    };

}(jQuery));