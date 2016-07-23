"use strict";

var conditionAutocompleteModule = (function ($) {

    function initConditionAutocomplete (contextPath, onChange) {

        var autoCompleteFixSet = function() {
            $(this).attr('arrayExpressAutocomplete', 'off');
        };

        var autoCompleteFixUnset = function() {
            $(this).removeAttr('arrayExpressAutocomplete');
        };

        $('#conditionQuery')
            // don't navigate away from the field on tab when selecting an item
            .bind( 'keydown', function( event ) {
                if (event.keyCode === $.ui.keyCode.TAB &&
                    $(this).data('ui-autocomplete').menu.active ) {
                    event.preventDefault();
                }
            })
            .jsonTagEditor({
                autocomplete: {
                    plugin: 'arrayExpressAutocomplete',
                    urlOrData:  contextPath + 'efowords.txt',
                    matchContains: false
                    , selectFirst: false
                    , scroll: true
                    , max: 50
                    , requestTreeUrl: contextPath + 'efotree.txt'
                    , width: 300
                },
                onChange: onChange,
                placeholder: 'Enter condition query...',
                maxLength: 100,
                maxTagLength: 20
            })
            .focus(autoCompleteFixSet)
            .blur(autoCompleteFixUnset)
            .removeAttr('arrayExpressAutocomplete');

    }


    // .jsonTagEditor({
    //     autocomplete: {
    //         delay: 500,
    //         minLength: 1,
    //         autoFocus: false,
    //         focus: function () {
    //             // prevent value inserted on focus
    //             return false;
    //         },
    //         source: function (request, response) {
    //             $.ajax({
    //                 url: 'json/suggestions',
    //                 dataType: 'json',
    //                 data: {
    //                     'query': request.term,
    //                     'species': species
    //                 },
    //                 success: function(data) {
    //                     var category_data = $.map(data, function (obj) {
    //                         return {
    //                             label: obj.value,
    //                             value: obj.value,
    //                             category: obj.category
    //                         };
    //                     });
    //                     response(category_data);
    //                 },
    //                 error: function (jqXHR, textStatus, errorThrown) {
    //                     console.log('Error. Status: ' + textStatus + ', errorThrown: ' + errorThrown);
    //                     response([]);
    //                 }
    //             });
    //         },
    //         _renderItem: function(ul, item) {
    //             var category_des ='&nbsp;';
    //             if(item.category.length != 0) {
    //                 category_des = item.category;
    //             }
    //
    //             return $('<li style="width: 280px;"></li>')
    //                 .attr('data-value', item.value )
    //                 .attr('data-category', item.category )
    //                 .append(
    //                     '<a>' +
    //                     '<span>' + item.label + '</span>' +
    //                     '<span style="float: right"><small><strong>' + category_des + '</strong></small></span>' +
    //                     '</a>'
    //                 )
    //                 .appendTo(ul);
    //         },
    //         select: function(event, ui) {
    //             ui.item.value = '{"value":"' + ui.item.value + '", "category":"' + ui.item.category + '"}';
    //         }
    //     },
    //     maxLength: 100,
    //     maxTagLength: 20
    // });



    return {
        init: initConditionAutocomplete
    };

})(jQuery);

