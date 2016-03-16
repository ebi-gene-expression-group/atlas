"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactDOMServer = require('react-dom/server');

var $ = require('jquery');
var jQuery = $;

require('jquery-ui');


//*------------------------------------------------------------------*

var ContrastTooltip = require('./ContrastTooltip.jsx');

//*------------------------------------------------------------------*



//*------------------------------------------------------------------*

function initTooltip(contextRoot, accessKey, element, experimentAccession, contrastId) {

    $(element).attr("title", "").tooltip({

        hide: false,

        show: false,

        tooltipClass: "gxaContrastTooltip",

        close: function() {
            $(".gxaContrastTooltip").remove();
        },

        content: function (callback) {
            $.ajax({
                url:contextRoot + "/rest/contrast-summary",
                data:{
                    experimentAccession: experimentAccession,
                    contrastId: contrastId,
                    accessKey: accessKey
                },
                type:"GET",
                success:function (data) {
                    var html =
                        ReactDOMServer.renderToString(
                            React.createElement(
                                ContrastTooltip,
                                {
                                    experimentDescription: data.experimentDescription,
                                    contrastDescription: data.contrastDescription,
                                    testReplicates: data.testReplicates,
                                    referenceReplicates: data.referenceReplicates,
                                    properties: data.properties
                                }
                            )
                        );
                    callback(html);
                }
            }).fail(function (data) {
                console.log("ERROR:  " + data);
                callback("ERROR: " + data);
            });
        }

    });

}

//*------------------------------------------------------------------*

exports.init = function (contextRoot, accessKey, elements, experimentAccession, contrastId) {
    initTooltip(contextRoot, accessKey, elements, experimentAccession, contrastId);
};

