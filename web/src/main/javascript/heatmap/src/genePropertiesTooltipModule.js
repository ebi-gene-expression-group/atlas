"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');
var jQuery = $;
require('jquery-highlight');

//*------------------------------------------------------------------*

require('../css/atlas.css');

//*------------------------------------------------------------------*

function splitIntoWords(geneQuery){
    var words = [];
    geneQuery.replace(/"([^"]*)"|(\S+)/g,
        function(m,g1,g2){
            if (g1 || g2){
                words.push(g1 || g2);
            }
        });
    return words;
}

function initTooltip(contextRoot, highlightedWords, element){
    $(element).tooltip({
        tooltipClass:"gxaGeneNameTooltip",
        position: { my: "left+120 top", at: "left top", collision: "flipfit" },
        hide:false,
        show:false,

        open: function() {
            var actual = $(this);

            $('.ui-tooltip').each(function( index ) {
                if(actual.attr("aria-describedby") != undefined) {
                    if(actual.attr("aria-describedby") != $(this).attr("id")) {
                        $(this).remove();
                    }
                }

            }, actual);
        },

        close: function() {
            $('.ui-tooltip').each(function() {
               $(this).remove();
            });
        },

        content:function (callback) {

            var identifier = $(this).attr("id"),
                geneName = $.trim($(this).text());

            if (identifier)  {

                $("#genenametooltip-content").load(

                    contextRoot + "/rest/genename-tooltip?geneName=" + geneName + "&identifier=" + identifier,

                    function (response, status, xhr) {
                        var tooltipContent;
                        if (status === "error") {
                            tooltipContent = "Sorry but there was an error: " + xhr.status + " " + xhr.statusText;
                            callback(tooltipContent);
                            return;
                        }

                        if(highlightedWords){
                            $(this).highlight(highlightedWords);
                        }

                        tooltipContent = $(this).html();
                        if (!tooltipContent) {
                            tooltipContent = "Missing properties for id = " + identifier + " in Solr.";
                        }
                        callback(tooltipContent);
                    }
                );

            }
        }
    });

    $('.ui-tooltip').each(function() {
        $(this).remove();
    });
}

//*------------------------------------------------------------------*

exports.init =
    function(contextRoot, highlightedWords, element) {
            initTooltip(contextRoot, splitIntoWords(highlightedWords), element || ".genename");
    };

