"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');
var jQuery = $;

require('jquery-ui');


//*------------------------------------------------------------------*



//*------------------------------------------------------------------*

/**
 * @param {Object} options
 * @param {string} options.contextRoot
 * @param {string} options.geneName
 * @param {string} options.identifier
 * @param {Object} options.element
 */
function initTooltip(options){

    $(options.element).attr("title", "").tooltip({

        hide: false,

        show: false,

        tooltipClass: "gxaGeneNameTooltip",

        close: function() {
            $(".gxaGeneNameTooltip").remove();
        },

        position: { my: "left+120 top", at: "left top", collision: "flipfit" },

        content: function (callback) {
            if (options.identifier)  {
                $.ajax({
                    url: options.contextRoot + "/rest/genename-tooltip",
                    data: {
                        geneName: options.geneName,
                        identifier: options.identifier
                    },
                    type:"GET",
                    success: function (response) {
                        if (!response) {
                            callback("Missing properties for id = " + options.identifier + " in Solr.");
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
    initTooltip({contextRoot: contextRoot, element: element, identifier: identifier, geneName: geneName});
};
