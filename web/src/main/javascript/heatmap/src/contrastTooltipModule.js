"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');
var jQuery = $;
var React = require('react');

//*------------------------------------------------------------------*

var ContrastTooltip = require('./contrastTooltip.jsx');
require('../css/atlas.css');
require('../css/heatmap-and-anatomogram.css');

//*------------------------------------------------------------------*

function initTooltip(contextRoot, accessKey, elements) {

    $(elements).attr("title", "").tooltip({

        hide:false,
        show:false,
        tooltipClass:"gxaHelpTooltip gxaPvalueTooltipStyling",
        content:function (callback) {

            //TODO: get this via parameter instead of from the DOM
            var experimentAccession = $(this).attr("data-experiment-accession"),
                contrastId = $(this).attr("data-contrast-id");
            if (experimentAccession === undefined) {
                experimentAccession = $(this).find(":nth-child(1)").attr("data-experiment-accession");
                contrastId = $(this).find(":nth-child(1)").attr("data-contrast-id");
            }

            $.ajax({
                url:contextRoot + "/rest/contrast-summary",
                data:{
                    experimentAccession:experimentAccession,
                    contrastId: contrastId,
                    accessKey: accessKey
                },
                type:"GET",
                success:function (data) {
                    var html =
                        React.renderToString(
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
                    //"Sorry but there was an error: " + xhr.status + " " + xhr.statusText
                    console.log("ERROR:  " + data);
                    callback("ERROR: " + data);
                });
        }
    });
};

//*------------------------------------------------------------------*

exports.init = function (contextRoot, accessKey, elements) {
            initTooltip(contextRoot, accessKey, elements || ".contrastNameCell");
};

