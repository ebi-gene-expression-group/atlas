"use strict";

var geneQueryTagEditorModule = (function($) {

    function sanitize(str) {
        return $('<span>' + str + '</span>').text();
    }

    function initAutocomplete(element, species, onChange, placeholderText, contextPath) {
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
                        url: URI('json/suggestions', contextPath).toString(),
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
                                    category: obj.category
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

                    return $('<li style="width: 300px;"></li>')
                        .attr('data-value', item.value )
                        .attr('data-category', item.category )
                        .append(
                            '<div><a>' +
                                '<span>' + item.label + '</span>' +
                                '<span style="float: right"><small><strong>' + category_des + '</strong></small></span>' +
                            '</a></div>'
                        )
                        .appendTo(ul);
                },
                select: function(event, ui) {
                    ui.item.value = '{"value":"' + sanitize(ui.item.value) + '", "category":"' + ui.item.category + '"}';
                }
            },
            onChange: onChange,
            placeholder: placeholderText || 'Enter gene query…',
            maxLength: 100,
            maxTagLength: 20
        });
    }

    return {
        init: initAutocomplete
    };

}(jQuery));