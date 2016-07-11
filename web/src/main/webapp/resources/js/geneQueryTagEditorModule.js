"use strict";

var geneQueryTagEditorModule = (function($) {

    function initAutocomplete(element, species, onChange) {
        $(element)
        // TODO paste items
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
                            var category_data = $.map(data, function (obj) {
                                return {
                                    label: obj.value,
                                    value: obj.value,
                                    category: obj.source
                                };
                            });
                            response(category_data);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log('Error. Status: ' + textStatus + ', errorThrown: ' + errorThrown);
                            response([]);
                        }
                    });
                },
                _renderItem: function(ul, item) {
                    var category_des ='&nbsp;';
                    if(item.category.length != 0) {
                        category_des = item.category;
                    }

                    return $('<li style="width: 280px;"></li>')
                        .attr('data-value', item.value )
                        .attr('data-category', item.category )
                        .append(
                            '<a>' +
                                '<span>' + item.label + '</span>' +
                                '<span style="float: right"><small><strong>' + category_des + '</strong></small></span>' +
                            '</a>'
                        )
                        .appendTo(ul);
                },
                select: function(event, ui) {
                    ui.item.value = '{"value":"' + ui.item.value + '", "category":"' + ui.item.category + '"}';
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