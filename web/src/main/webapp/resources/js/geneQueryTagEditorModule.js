"use strict";

var geneQueryTagEditorModule = (function($) {

    function initAutocomplete(element, species, onChange) {
        $(element)
            // Don't navigate away from the field on tab when selecting an item
            // .bind('keydown', function(event) {
            //     if (event.keyCode === $.ui.keyCode.TAB && $(this).data('ui-autocomplete').menu.active) {
            //         event.preventDefault();
            //     }
            // })
            // .on('paste', function(e) {
            //     e.preventDefault();
            //     var text = (e.originalEvent || e).clipboardData.getData('text/plain') || prompt('Paste something...');
            //     window.document.execCommand('insertText', false, text);
            // })
            .jsonTagEditor({
                autocomplete: {
                    delay: 500,
                    minLength: 1,
                    autoFocus: false,
                    focus: function () {
                        // prevent value inserted on focus
                        return false;
                    },
                    source: function (request, response) {
                        $.ajax({
                            url: 'json/suggestions',
                            dataType: 'json',
                            data: {
                                'query': request.term,
                                'species': species
                            },
                            success: function(data) {
                                var source_data = $.map(data, function (obj) {
                                    return {
                                        label: obj.value,
                                        value: obj.value,
                                        source: obj.source
                                    };
                                });
                                response(source_data);
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                console.log('Error. Status: ' + textStatus + ', errorThrown: ' + errorThrown);
                                response([]);
                            }
                        });
                    },
                    _renderItem: function(ul, item) {
                        var source_des ='&nbsp;';
                        if(item.source.length != 0) {
                            source_des = item.source;
                        }

                        return $('<li style="width: 280px;"></li>')
                            .attr('data-value', item.value )
                            .attr('data-source', item.source )
                            .append(
                                '<a>' +
                                    '<span>' + item.label + '</span>' +
                                    '<span style="float: right"><small><strong>' + source_des + '</strong></small></span>' +
                                '</a>'
                            )
                            .appendTo(ul);
                    },
                    select: function(event, ui) {
                        ui.item.value = '{"tagValue":"' + ui.item.value + '", "tagSource":"' + ui.item.source + '"}';
                    }
                },
                onChange: onChange,
                placeholder: 'Enter gene query...',
                maxLength: 100,
                maxTagLength: 20
        });
    }

    return {
        init: initAutocomplete
    };

}(jQuery));