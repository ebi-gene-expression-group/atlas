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

var geneQueryTagEditorModule = (function($) {
    "use strict";

    function initAutocomplete(element, species, onChange){
        $(element)
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
                    delay: 500,
                    minLength: 1,
                    autoFocus: true,
                    focus: function () {
                        // prevent value inserted on focus
                        return false;
                    },
                    source: function (request, response) {
                        $.ajax({
                            url: 'json/suggestions', //'json/suggestions',         json/suggestionsDataJson',
                            dataType: "json",
                            data: {
                                'query': request.term,
                                'species': species
                            },
                            success: function(data) {
                                var source_data = $.map(data, function (obj) {
                                    return {
                                        label: obj.term + ' --- ' + obj.source,
                                        value: obj.term

                                    };
                                });
                                response(source_data);
                            },

                            error: function (jqXHR, textStatus, errorThrown) {
                                console.log("Error. Status: " + textStatus + ", errorThrown: " + errorThrown);
                            }
                        });
                    },
                    _renderItem: function( ul, item) {
                                    return $( "<li></li>" )
                                        .data( "item.autocomplete", item )
                                        .append( "<a>!" + item.label + "!</a>" )
                                        .appendTo( ul );
                    }
                },

                onChange: onChange,

                placeholder: 'Enter gene query...',
                forceLowercase: false

        });

    }


    return {
        init: initAutocomplete
    };

    }(jQuery));