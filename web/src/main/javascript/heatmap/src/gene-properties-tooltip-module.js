"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');
var jQuery = $;

//*------------------------------------------------------------------*

require('../css/atlas.css');

//*------------------------------------------------------------------*

function initTooltip(contextRoot, element, identifier, geneName){

    $(element).attr("title", "").tooltip({

        hide: false,

        show: false,

        tooltipClass: "gxaGeneNameTooltip",

        close: function() {
            $(".gxaGeneNameTooltip").remove();
        },

        position: { my: "left+120 top", at: "left top", collision: "flipfit" },

        content: function (callback) {
            if (identifier)  {
                $.ajax({
                    url: contextRoot + "/rest/genename-tooltip",
                    data: {
                        geneName: geneName,
                        identifier: identifier
                    },
                    type:"GET",
                    success: function (response) {
                        if (!response) {
                            callback("Missing properties for id = " + identifier + " in Solr.");
                        }

                        callback(response);
                    }
                }).fail(function (data) {
                    console.log("ERROR:  " + data);
                    callback("ERROR: " + data);
                });
            }
        }

    });

}

//*------------------------------------------------------------------*

exports.init = function(contextRoot, element, identifier, geneName) {
    initTooltip(contextRoot, element, identifier, geneName);
};
